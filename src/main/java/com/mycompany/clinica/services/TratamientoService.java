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
import com.mycompany.clinica.models.Tratamiento;

/**
 *
 * @author mads
 */
public class TratamientoService extends Services {

    //Propiedades
    private ArrayList<Tratamiento> listaTratamientos;
    private final ResourceBundle rb = ResourceBundle.getBundle("mensajes");
    private boolean success;

    private Auditoria auditoria;
    private AuditoriaService auditoriaService;

    private Date fechaActual;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String fechaActualString;

    //Método que prepara la carga de todos los objetos de la BD
    public ArrayList<Tratamiento> cargarTratamientos(String and) {
        PreparedStatement stmt = null;
        ResultSet rset;
        try {
            listaTratamientos = new ArrayList();
            conectar();
            String sql = " SELECT tratamientos.id_tratamiento, "
                    + " tratamientos.nombre, tratamientos.duracion, "
                    + " tratamientos.precio, tratamientos.color "
                    + " FROM tratamientos "
                    + and;
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Tratamiento tratamiento = new Tratamiento();
                    tratamiento.setIdTratamiento(rset.getInt("id_tratamiento"));
                    tratamiento.setNombre(rset.getString("nombre"));
                    tratamiento.setDuracion(rset.getInt("duracion"));
                    tratamiento.setPrecio(rset.getDouble("precio"));
                    tratamiento.setColor("#" + rset.getString("color"));
                    listaTratamientos.add(tratamiento);
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return listaTratamientos;
    }

    public Tratamiento cargarUnTratamiento(String and) {
        Tratamiento tratamiento = new Tratamiento();
        PreparedStatement stmt = null;
        ResultSet rset;
        try {
            conectar();
            String sql = " SELECT tratamientos.id_tratamiento, "
                    + " tratamientos.nombre, tratamientos.duracion, "
                    + " tratamientos.precio, tratamientos.color "
                    + " FROM tratamientos "
                    + and;
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    tratamiento.setIdTratamiento(rset.getInt("id_tratamiento"));
                    tratamiento.setNombre(rset.getString("nombre"));
                    tratamiento.setDuracion(rset.getInt("duracion"));
                    tratamiento.setPrecio(rset.getDouble("precio"));
                    tratamiento.setColor("#" + rset.getString("color"));
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return tratamiento;
    }

    //Método que prepara la inserción de un tratamiento en la BD
    public void crearTratamiento(Tratamiento t, int idUsuario) {
        PreparedStatement stmt = null;
        FacesContext context = FacesContext.getCurrentInstance();
        success = false;

        boolean duplicado = false;

        //Compruebo si el nombre del tratamiento esta duplicado
        ArrayList<Tratamiento> listaTra = cargarTratamientos("");
        for (Tratamiento tratamiento : listaTra) {
            if (t.getNombre().toLowerCase().equals(tratamiento.getNombre().toLowerCase())) {
                duplicado = true;
                break;
            }
        }

        if (duplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("nombreTratamientoDuplicado")));
        } else {
            try {
                conectar();

                String sql = " INSERT INTO tratamientos (nombre, duracion, precio, color) "
                        + " VALUES ('" + t.getNombre() + "', " + t.getDuracion() + ", " + t.getPrecio() + ", '" + t.getColor()
                        + "')";

                stmt = conn.prepareStatement(sql);
                int numFilas = stmt.executeUpdate(sql);
                if (numFilas == 1) {
                    success = true;
                    fechaActual = new Date();
                    fechaActualString = sdf.format(fechaActual);
                    auditoria = new Auditoria(idUsuario, rb.getString("crearTratamiento"), rb.getString("creacionTratamiento") + " " + t.getNombre(), fechaActualString);
                    auditoriaService = new AuditoriaService();
                    auditoriaService.crearAuditoria(auditoria);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("tratamientoAnadido")));
                }
            } catch (SQLException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
                }
                desconectar();
            }
        }
    }

    //Método que prepara la edición de un tratamiento en la BD
    public void editarTratamiento(Tratamiento t, int idUsuario) {

        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        success = false;

        boolean duplicado = false;

        //Compruebo si el nombre del tratamiento esta duplicado
        ArrayList<Tratamiento> listaTra = cargarTratamientos("");
        for (Tratamiento tratamiento : listaTra) {
            if (t.getNombre().toLowerCase().equals(tratamiento.getNombre().toLowerCase()) && t.getIdTratamiento() != tratamiento.getIdTratamiento()) {
                duplicado = true;
                break;
            }
        }

        if (duplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("nombreTratamientoDuplicado")));
        } else {

            try {
                conectar();

                String sql = " UPDATE tratamientos SET nombre = '" + t.getNombre()
                        + "', duracion = " + t.getDuracion() + ", precio = " + t.getPrecio()
                        + ", color = '" + t.getColor()
                        + "' WHERE id_tratamiento = " + t.getIdTratamiento();

                stmt = conn.prepareStatement(sql);
                int numFilas = stmt.executeUpdate(sql);
                if (numFilas == 1) {
                    success = true;
                    fechaActual = new Date();
                    fechaActualString = sdf.format(fechaActual);
                    auditoria = new Auditoria(idUsuario, rb.getString("editarTratamiento"), rb.getString("edicionTratamiento") + " " + t.getNombre(), fechaActualString);
                    auditoriaService = new AuditoriaService();
                    auditoriaService.crearAuditoria(auditoria);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("tratamientoEditado")));
                }
            } catch (SQLException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
                }
                desconectar();
            }
        }

    }

    //Método que prepara la eliminación un tratamiento de la BD
    public void borrarTratamiento(Tratamiento tratamiento, int idUsuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = " DELETE FROM tratamientos WHERE id_tratamiento = " + tratamiento.getIdTratamiento();

            stmt = conn.prepareStatement(sql);
            int numFilas = stmt.executeUpdate(sql);
            if (numFilas == 1) {
                fechaActual = new Date();
                fechaActualString = sdf.format(fechaActual);
                auditoria = new Auditoria(idUsuario, rb.getString("eliminarTratamiento"), rb.getString("eliminacionTratamientoAuditorias") + " " + tratamiento.getNombre(), fechaActualString);
                auditoriaService = new AuditoriaService();
                auditoriaService.crearAuditoria(auditoria);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("tratamientoEliminado")));
            }
        } catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
            Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
            Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TratamientoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
    }

    //Getters & Setters
    public ArrayList<Tratamiento> getListaTratamientos() {
        return listaTratamientos;
    }

    public void setListaTratamientos(ArrayList<Tratamiento> listaTratamientos) {
        this.listaTratamientos = listaTratamientos;
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

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getFechaActualString() {
        return fechaActualString;
    }

    public void setFechaActualString(String fechaActualString) {
        this.fechaActualString = fechaActualString;
    }

}
