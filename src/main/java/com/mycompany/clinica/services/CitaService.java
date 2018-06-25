/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.mycompany.clinica.models.Auditoria;
import com.mycompany.clinica.models.Cita;
import com.mycompany.clinica.models.CitaModulo;
import com.mycompany.clinica.models.Complemento;
import com.mycompany.clinica.models.Modulo;

/**
 *
 * @author mads
 */
public class CitaService extends Services {

    //Propiedades
    private ArrayList<Cita> listaCitas;
    private ArrayList<Cita> listaCitasFecha;
    private ArrayList<Cita> listaHistorialCliente;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private boolean success;
    private int numFilasModulo;

    private Auditoria auditoria;
    private AuditoriaService auditoriaService;

    private Date fechaActual;
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String fechaActualString;

    private ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    //Método que carga todas las citas
    public ArrayList<Cita> cargarCitas(String where) {
        /*
            Fecha es un campo DATE, esto significa que no solo contiene la fecha, 
            debido a ello, uso la función CURDATE() en la claúsula WHERE ya que de
            este forma solo comparamos por la fecha.
            El formato en el que se muestra la fecha en el datatable es controlado
            de la siguiente forma: 
                <f:convertDateTime pattern="dd/MM/yyyy"  />
         */

        listaCitas = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT citas.id_cita , "
                    + " clientes.id_cliente, "
                    + " cita_modulo.fecha, "
                    + " modulos.modulo, "
                    + " modulos.id_modulo, "
                    + " clientes.nombre AS nombreCliente, clientes.apellidos,"
                    + " citas.grado, citas.estado,"
                    + " citas.observaciones, "
                    + " citas.id_lesion, citas.id_tratamiento,"
                    + " citas.atendida "
                    + " FROM citas "
                    + " JOIN clientes "
                    + " ON citas.id_cliente = clientes.id_cliente "
                    + " JOIN cita_modulo "
                    + " ON citas.id_cita = cita_modulo.id_cita"
                    + " JOIN modulos"
                    + " ON cita_modulo.id_modulo = modulos.id_modulo "
                    + where
                    + " GROUP BY citas.id_cita "
                    + " ORDER BY cita_modulo.fecha, modulos.modulo ";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Cita c = new Cita();
                    c.setIdCita(rset.getInt("id_cita"));
                    c.setIdCliente(rset.getInt("id_cliente"));
                    c.setFecha(rset.getDate("fecha"));
                    c.setIdModulo(rset.getInt("id_modulo"));
                    c.setModulo(rset.getString("modulo"));
                    c.setNombreCliente(rset.getString("nombreCliente"));
                    c.setApellidos(rset.getString("apellidos"));
                    c.setIdLesion(rset.getInt("id_lesion"));
                    c.setIdTratamiento(rset.getInt("id_tratamiento"));
                    c.setGrado(rset.getString("grado"));
                    c.setEstado(rset.getString("estado"));
                    c.setObservaciones(rset.getString("observaciones"));
                    c.setAtendida(rset.getBoolean("atendida"));
                    listaCitas.add(c);
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return listaCitas;
    }

//  Método que prepara la inserción de una Cita en la BD
    public void crearCita(Cita c, Modulo moduloSeleccionado, int numModulos, ArrayList<CitaModulo> listaModOcupados, int idUsuario) {

        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        ResultSet rset;
        success = false;
        // boolean exito = true;

        boolean moduloDuplicado = false;

        // Recorremos los modulos ocupados para una fecha
        for (CitaModulo modulo : listaModOcupados) {
            int aux = moduloSeleccionado.getIdModulo();
            for (int i = 0; i < numModulos; i++) {
                if (aux == modulo.getIdModulo()) {
                    moduloDuplicado = true;
                }
                aux++;
            }
        }

        if (moduloDuplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("modDuplicado")));
        } else if (moduloSeleccionado.getIdModulo() == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("reqHora")));
        } else {
            try {
                conectar();

                conn.setAutoCommit(false);

                //  Inserto en la tabla citas
                String sql = " INSERT INTO citas (id_cliente, id_lesion, id_tratamiento) "
                        + " VALUES (" + c.getIdCliente() + "," + c.getIdLesion() + "," + c.getIdTratamiento() + ")";

                stmt = conn.prepareStatement(sql);

                int numFilas = stmt.executeUpdate(sql);

                // Obtengo el id de la última cita introducida en la tabla Citas
                int lastIdCita = 0;
                String sql2 = "SELECT LAST_INSERT_ID()";
                stmt = conn.prepareStatement(sql2);
                rset = stmt.executeQuery();

                if (rset != null) {
                    while (rset.next()) {
                        lastIdCita = rset.getInt(1);
                    }
                    rset.close();
                }

                int moduloSel = moduloSeleccionado.getIdModulo();

                for (int i = 0; i < numModulos; i++) {
                    String sql3 = "INSERT INTO cita_modulo (id_cita, id_modulo, fecha) "
                            + "values (" + lastIdCita + ", " + moduloSel + ",'" + sdf.format(c.getFecha()) + "') ";
                    stmt = conn.prepareStatement(sql3);
                    numFilasModulo = stmt.executeUpdate(sql3);
                    moduloSel++;
                }

                if (numFilas == 1 && numFilasModulo == 1) {
                    conn.commit();
                    fechaActual = new Date();
                    fechaActualString = sdf2.format(fechaActual);
                    auditoria = new Auditoria(idUsuario, rb.getString("crearCita"), rb.getString("creacionCita") + " " + lastIdCita , fechaActualString);
                    auditoriaService = new AuditoriaService();
                    auditoriaService.crearAuditoria(auditoria);
                    success = true;
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("citaAnadida")));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("info"), rb.getString("errorTransaction")));
                    conn.rollback();
                }

            } catch (SQLException ex) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex1);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("info"), rb.getString("errorTransaction")));
                Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex);
                }
                desconectar();
            }

        }
    }

    //  Método que prepara la edición de una cita en la BD
    public void editarCita(Cita c, Modulo moduloSeleccionado, int numModulos, ArrayList<CitaModulo> listaModOcupados, int idUsuario) {

        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        success = false;

        boolean moduloDuplicado = false;

        // Recorremos los modulos ocupados para una fecha
        for (CitaModulo modulo : listaModOcupados) {
            int aux = moduloSeleccionado.getIdModulo();
            for (int i = 0; i < numModulos; i++) {
                if (aux == modulo.getIdModulo() && c.getIdCita() != modulo.getIdCita()) {
                    moduloDuplicado = true;
                }
                aux++;
            }
        }

        if (moduloDuplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("modDuplicado")));
        } else if (moduloSeleccionado.getIdModulo() == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("info"), rb.getString("reqHora")));
        } else {
            try {
                conectar();
                conn.setAutoCommit(false);

                String sql = " UPDATE citas SET id_cliente = " + c.getIdCliente()
                        + ", id_lesion = " + c.getIdLesion() + ", id_tratamiento = " + c.getIdTratamiento()
                        + " WHERE id_cita = " + c.getIdCita();

                stmt = conn.prepareStatement(sql);
                int numFilas = stmt.executeUpdate(sql);

                // Borramos de la tabla cita_modulo los modulos que contenga la cita que va ser modificada
                String sql2 = " DELETE from cita_modulo where cita_modulo.id_cita = " + c.getIdCita();
                stmt = conn.prepareStatement(sql2);
                int numFilasEliminadas = stmt.executeUpdate(sql2);

                // Insertamos en la tabla cita_modulo los modulos que van a ser utilizados para el tratamiento
                int moduloSel = moduloSeleccionado.getIdModulo();

                for (int i = 0; i < numModulos; i++) {
                    String sql3 = "INSERT INTO cita_modulo (id_cita, id_modulo, fecha) "
                            + "values (" + c.getIdCita() + ", " + moduloSel + ",'" + sdf.format(c.getFecha()) + "') ";
                    stmt = conn.prepareStatement(sql3);
                    numFilasModulo = stmt.executeUpdate(sql3); // Quiero utilizar un booleano para ver si tiene éxito la ejecución
                    moduloSel++;
                }

                if (numFilas == 1 && numFilasModulo == 1) {
                    conn.commit();
                    fechaActual = new Date();
                    fechaActualString = sdf2.format(fechaActual);
                    auditoria = new Auditoria(idUsuario, rb.getString("editarCita") , rb.getString("edicionCita") + " " + c.getIdCita(), fechaActualString);
                    auditoriaService = new AuditoriaService();
                    auditoriaService.crearAuditoria(auditoria);
                    success = true;
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("citaEditada")));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("info"), rb.getString("errorTransaction")));
                    conn.rollback();
                }

            } catch (SQLException ex) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex1);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, null, ex);
                }
                desconectar();
            }

        }

    }

//  Método que prepara la eliminación una cita de la BD
    public void borrarCita(Cita cita, int idUsuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;

        try {
            conectar();
            conn.setAutoCommit(false);

            String sql = "DELETE FROM cita_modulo WHERE id_cita = " + cita.getIdCita();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);

            String sql2 = " DELETE FROM citas WHERE id_cita = " + cita.getIdCita();
            stmt = conn.prepareStatement(sql2);
            int numFilas = stmt.executeUpdate(sql2);

            if (numFilas == 1) {
                conn.commit();
                fechaActual = new Date();
                fechaActualString = sdf2.format(fechaActual);
                auditoria = new Auditoria(idUsuario, rb.getString("eliminarCita"), rb.getString("eliminacionCitaAuditorias") + " " + cita.getIdCita(), fechaActualString);
                auditoriaService = new AuditoriaService();
                auditoriaService.crearAuditoria(auditoria);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("citaEliminada")));
            } else {
                conn.rollback();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
            }
        } catch (SQLException ex) {
            try {
                conn.rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(CitaService.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
            Logger
                    .getLogger(CitaService.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            try {
                conn.rollback();

            } catch (SQLException ex) {
                Logger.getLogger(CitaService.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
            Logger
                    .getLogger(CitaService.class
                            .getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(CitaService.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
    }

    //  Método que completa el contenido de una cita en la BD
    public void completarCita(Cita c, ArrayList<Complemento> listaComplementosSel, int idUsuario) {

        success = false;
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;

        try {
            conectar();
            conn.setAutoCommit(false);

            String sql = " UPDATE citas SET id_lesion = " + c.getIdLesion() + ", id_zona = " + c.getIdZona() + ", id_musculo = " + c.getIdMusculo() + ", grado = '" + c.getGrado()
                    + "', estado = '" + c.getEstado() + "', observaciones = '" + c.getObservaciones()
                    + "', atendida = " + c.isAtendida()
                    + " WHERE id_cita = " + c.getIdCita();

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);

            for (Complemento complemento : listaComplementosSel) {
                String sql2 = " INSERT INTO cita_complementos (id_cita, id_complemento) "
                        + " VALUES (" + c.getIdCita() + "," + complemento.getIdComplemento() + ")";
                stmt = conn.prepareStatement(sql2);
                stmt.executeUpdate(sql2);
            }

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("citaAtendida")));
            conn.commit();
            fechaActual = new Date();
            fechaActualString = sdf2.format(fechaActual);
            auditoria = new Auditoria(idUsuario, rb.getString("atenderCita"), rb.getString("atencionCita") + " " + c.getIdCita(), fechaActualString);
            auditoriaService = new AuditoriaService();
            auditoriaService.crearAuditoria(auditoria);
            success = true;

        } catch (SQLException ex) {
            try {
                conn.rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(CitaService.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
            Logger
                    .getLogger(CitaService.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            try {
                conn.rollback();

            } catch (SQLException ex) {
                Logger.getLogger(CitaService.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
            Logger
                    .getLogger(CitaService.class
                            .getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(CitaService.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
    }

    public ArrayList<Cita> cargarHistorial(int idClienteC, String limit) {

        listaHistorialCliente = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT cita_modulo.fecha, "
                    + " lesiones.nombre AS nombreLesion, "
                    + " CASE citas.grado "
                    + " WHEN 1 THEN 'Primero' "
                    + " WHEN 2 THEN 'Segundo' "
                    + " WHEN 3 THEN 'Tercero' "
                    + " ELSE 'Indefinido' "
                    + " END AS grado, "
                    + " CASE citas.estado "
                    + " WHEN 0 THEN 'Agudo' "
                    + " WHEN 1 THEN 'Crónico' "
                    + " ELSE 'Indefinido'"
                    + " END AS estado, "
                    + " citas.id_cita, "
                    + " tratamientos.nombre AS nombreTratamiento, "
                    + " IF (citas.observaciones = '','Indefinido',observaciones) AS observaciones, "
                    + " clientes.nombre AS nombreCliente, "
                    + " clientes.apellidos, "
                    + " modulos.modulo, "
                    + " musculos.nombre AS nombreMusculo, "
                    + " musculos.url_imagen "
                    + " FROM citas "
                    + " JOIN lesiones "
                    + " ON citas.id_lesion = lesiones.id_lesion "
                    + " JOIN tratamientos "
                    + " ON citas.id_tratamiento = tratamientos.id_tratamiento"
                    + " JOIN clientes "
                    + " ON citas.id_cliente = clientes.id_cliente"
                    + " JOIN cita_modulo "
                    + " ON citas.id_cita = cita_modulo.id_cita "
                    + " JOIN modulos "
                    + " ON cita_modulo.id_modulo = modulos.id_modulo"
                    + " JOIN musculos"
                    + " ON citas.id_musculo = musculos.id_musculo "
                    + " WHERE citas.id_cliente = " + idClienteC
                    + " AND citas.atendida = true "
                    + " GROUP BY citas.id_cita "
                    + " ORDER BY cita_modulo.fecha DESC, modulos.modulo DESC "
                    + limit;
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Cita c = new Cita();
                    c.setNombreCliente(rset.getString("nombreCliente"));
                    c.setApellidos(rset.getString("apellidos"));
                    c.setFecha(rset.getDate("fecha"));
                    c.setModulo(rset.getString("modulo"));
                    c.setNombreLesion(rset.getString("nombreLesion"));
                    c.setGrado(rset.getString("grado"));
                    c.setEstado(rset.getString("estado"));
                    c.setNombreTratamiento(rset.getString("nombreTratamiento"));
                    c.setObservaciones(rset.getString("observaciones"));
                    c.setNombreMusculo(rset.getString("nombreMusculo"));
                    c.setUrlImagenMusculo(rset.getString("url_imagen"));
                    c.setIdCita(rset.getInt("id_cita"));
                    listaHistorialCliente.add(c);
                }
                rset.close();

            }
        } catch (SQLException ex) {
            Logger.getLogger(CitaService.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (Exception e) {
            Logger.getLogger(CitaService.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();

                }
            } catch (SQLException ex) {
                Logger.getLogger(CitaService.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return listaHistorialCliente;
    }

    //Getters & Setters
    public ArrayList<Cita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(ArrayList<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public ArrayList<Cita> getListaCitasFecha() {
        return listaCitasFecha;
    }

    public void setListaCitasFecha(ArrayList<Cita> listaCitasFecha) {
        this.listaCitasFecha = listaCitasFecha;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Cita> getListaHistorialCliente() {
        return listaHistorialCliente;
    }

    public void setListaHistorialCliente(ArrayList<Cita> listaHistorialCliente) {
        this.listaHistorialCliente = listaHistorialCliente;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public int getNumFilasModulo() {
        return numFilasModulo;
    }

    public void setNumFilasModulo(int numFilasModulo) {
        this.numFilasModulo = numFilasModulo;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }

    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public SimpleDateFormat getSdf2() {
        return sdf2;
    }

    public void setSdf2(SimpleDateFormat sdf2) {
        this.sdf2 = sdf2;
    }

    public String getFechaActualString() {
        return fechaActualString;
    }

    public void setFechaActualString(String fechaActualString) {
        this.fechaActualString = fechaActualString;
    }

}
