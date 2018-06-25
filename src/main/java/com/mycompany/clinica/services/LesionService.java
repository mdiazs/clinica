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
import com.mycompany.clinica.models.Lesion;
import com.mycompany.clinica.models.Complemento;
import com.mycompany.clinica.models.Tratamiento;

/**
 *
 * @author mads
 */
public class LesionService extends Services {

    //Propiedades
    private ArrayList<Lesion> listaLesiones;
    private boolean success;

    private final ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    private Auditoria auditoria;
    private AuditoriaService auditoriaService;

    private Date fechaActual;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String fechaActualString;

    //Método que carga todas las lesiones
    public ArrayList<Lesion> cargarLesiones(String where) {
        listaLesiones = new ArrayList();

        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = " SELECT lesiones.id_lesion, "
                    + " lesiones.nombre, "
                    + " lesiones.descripcion, "
                    + " lesiones.tipo, "
                    + " lesiones.causas, "
                    + " lesiones.prevencion, "
                    + " lesiones.sintomas "
                    + " FROM lesiones "
                    + where
                    + " ORDER BY lesiones.nombre ";

            stmt = conn.prepareStatement(sql);

            ResultSet rset = stmt.executeQuery();

            if (rset != null) {
                while (rset.next()) {
                    Lesion lesion = new Lesion();
                    lesion.setIdLesion(rset.getInt("id_lesion"));
                    lesion.setNombre(rset.getString("nombre"));
                    lesion.setDescripcion(rset.getString("descripcion"));
                    lesion.setTipo(rset.getBoolean("tipo"));
                    lesion.setCausas(rset.getString("causas"));
                    lesion.setPrevencion(rset.getString("prevencion"));
                    lesion.setSintomas(rset.getString("sintomas"));
                    listaLesiones.add(lesion);
                }

                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaLesiones;
    }

    //Método que carga una lesión
    public Lesion cargarUnaLesion(String and) {
        Lesion lesion = new Lesion();
        PreparedStatement stmt = null;
        ResultSet rset;
        try {
            conectar();
            String sql = " SELECT lesiones.nombre, "
                    + " lesiones.descripcion, lesiones.tipo, "
                    + " lesiones.causas, lesiones.prevencion, "
                    + " lesiones.sintomas "
                    + " FROM lesiones "
                    + and;
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    lesion.setNombre(rset.getString("nombre"));
                    lesion.setDescripcion(rset.getString("descripcion"));
                    lesion.setTipo(rset.getBoolean("tipo"));
                    lesion.setCausas(rset.getString("causas"));
                    lesion.setPrevencion(rset.getString("prevencion"));
                    lesion.setSintomas(rset.getString("sintomas"));
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return lesion;
    }

    //Método que prepara la inserción de una lesión en la BD
    public void crearLesion(Lesion l, ArrayList<Tratamiento> listTratamientosSelec, ArrayList<Complemento> listComplementosSelec, int idUsuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        success = false;

        boolean duplicado = false;

        //Compruebo si el nombre de la lesión esta duplicado
        ArrayList<Lesion> listaLes = cargarLesiones("");
        for (Lesion lesion : listaLes) {
            if (l.getNombre().toLowerCase().equals(lesion.getNombre().toLowerCase())) {
                duplicado = true;
                break;
            }
        }

        if (duplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("nombreLesionDuplicado")));
        } else {
            try {
                conectar();
                conn.setAutoCommit(false);
                //Inserto lesión
                String sql = " INSERT INTO lesiones (nombre, descripcion, tipo, causas, prevencion, sintomas) "
                        + " VALUES ('" + l.getNombre() + "','" + l.getDescripcion() + "'," + l.isTipo()
                        + ",'" + l.getCausas() + "','" + l.getPrevencion() + "','" + l.getSintomas()
                        + "')";

                stmt = conn.prepareStatement(sql);
                stmt.executeUpdate(sql);

                //Obtengo el id de la lesión insertada
                String sqlLast = "SELECT LAST_INSERT_ID()";
                stmt = conn.prepareStatement(sqlLast);

                int lastId;
                try (ResultSet rset = stmt.executeQuery()) {
                    lastId = 0;
                    while (rset.next()) {
                        lastId = rset.getInt(1);
                    }
                }

                //Inserto los tratamientos en la tabla lesiones_tratamientos
                for (Tratamiento tratamiento : listTratamientosSelec) {
                    String sql2 = " INSERT INTO lesiones_tratamientos (id_lesion, id_tratamiento) "
                            + " VALUES (" + lastId + "," + tratamiento.getIdTratamiento() + ")";
                    stmt = conn.prepareStatement(sql2);
                    stmt.executeUpdate();
                }

                //Inserto los complementos en la tabla lesiones_complementos
                for (Complemento complemento : listComplementosSelec) {
                    String sql3 = " INSERT INTO lesiones_complementos (id_lesion, id_complemento) "
                            + " VALUES (" + lastId + "," + complemento.getIdComplemento() + ")";
                    stmt = conn.prepareStatement(sql3);
                    stmt.executeUpdate();
                }

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("lesionAnadida")));
                conn.commit();
                success = true;
                fechaActual = new Date();
                fechaActualString = sdf.format(fechaActual);
                auditoria = new Auditoria(idUsuario, rb.getString("crearLesion"), rb.getString("creacionLesion") + " " + l.getNombre(), fechaActualString);
                auditoriaService = new AuditoriaService();
                auditoriaService.crearAuditoria(auditoria);

            } catch (SQLException ex) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex1);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //Método que prepara la edición de un tratamiento en la BD
    public void editarLesion(Lesion l, ArrayList<Tratamiento> listTratamientosSelec, ArrayList<Complemento> listComplementosSelec, int idUsuario) {

        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        success = false;

        boolean duplicado = false;

        //Compruebo si el nombre de la lesión esta duplicado
        ArrayList<Lesion> listaLes = cargarLesiones("");
        for (Lesion lesion : listaLes) {
            if (l.getNombre().toLowerCase().equals(lesion.getNombre().toLowerCase()) && l.getIdLesion() != lesion.getIdLesion()) {
                duplicado = true;
                break;
            }
        }

        if (duplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("nombreLesionDuplicado")));
        } else {

            try {
                conectar();
                conn.setAutoCommit(false);

                String sql = " UPDATE lesiones SET nombre = '" + l.getNombre() + "', descripcion = '" + l.getDescripcion()
                        + "', tipo = " + l.isTipo() + ", causas = '" + l.getCausas() + "', prevencion = '" + l.getPrevencion()
                        + "', sintomas = '" + l.getSintomas()
                        + "' WHERE id_lesion = " + l.getIdLesion();

                stmt = conn.prepareStatement(sql);
                stmt.executeUpdate(sql);

                String sql2 = "DELETE FROM lesiones_tratamientos WHERE id_lesion = " + l.getIdLesion();
                stmt = conn.prepareStatement(sql2);
                stmt.executeUpdate(sql2);

                //Inserto los tratamientos en la tabla lesiones_tratamientos
                for (Tratamiento tratamiento : listTratamientosSelec) {
                    String sql4 = " INSERT INTO lesiones_tratamientos (id_lesion, id_tratamiento) "
                            + " VALUES (" + l.getIdLesion() + "," + tratamiento.getIdTratamiento() + ")";
                    stmt = conn.prepareStatement(sql4);
                    stmt.executeUpdate();
                }

                String sql3 = "DELETE FROM lesiones_complementos WHERE id_lesion = " + l.getIdLesion();
                stmt = conn.prepareStatement(sql3);
                stmt.executeUpdate(sql3);

                //Inserto los complementos en la tabla lesiones_complementos
                for (Complemento complemento : listComplementosSelec) {
                    String sql5 = " INSERT INTO lesiones_complementos (id_lesion, id_complemento) "
                            + " VALUES (" + l.getIdLesion() + "," + complemento.getIdComplemento() + ")";
                    stmt = conn.prepareStatement(sql5);
                    stmt.executeUpdate();
                }

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("lesionEditada")));
                conn.commit();
                success = true;
                fechaActual = new Date();
                fechaActualString = sdf.format(fechaActual);
                auditoria = new Auditoria(idUsuario, rb.getString("editarLesion"), rb.getString("edicionLesion") + " " + l.getNombre(), fechaActualString);
                auditoriaService = new AuditoriaService();
                auditoriaService.crearAuditoria(auditoria);

            } catch (SQLException ex) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex1);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //Método que prepara la eliminación una lesión de la BD
    public void borrarLesion(Lesion lesion, int idUsuario) {
        FacesContext context = FacesContext.getCurrentInstance();

        PreparedStatement stmt = null;

        try {
            conectar();
            conn.setAutoCommit(false);

            String sql = "DELETE FROM lesiones_tratamientos WHERE id_lesion = " + lesion.getIdLesion();
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);

            String sql2 = "DELETE FROM lesiones_complementos WHERE id_lesion = " + lesion.getIdLesion();
            stmt = conn.prepareStatement(sql2);
            stmt.executeUpdate(sql2);

            String sql3 = " DELETE FROM lesiones WHERE id_lesion = " + lesion.getIdLesion();
            stmt = conn.prepareStatement(sql3);
            int numFilas = stmt.executeUpdate(sql3);
            if (numFilas == 1) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("lesionEliminada")));
                fechaActual = new Date();
                fechaActualString = sdf.format(fechaActual);
                auditoria = new Auditoria(idUsuario, rb.getString("eliminarLesion"), rb.getString("eliminacionLesionAuditorias") + " " + lesion.getNombre(), fechaActualString);
                auditoriaService = new AuditoriaService();
                auditoriaService.crearAuditoria(auditoria);
                conn.commit();
            }

        } catch (SQLException ex) {
            try {
                conn.rollback();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
            Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LesionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            desconectar();
        }
    }

    //Getters & Setters
    public ArrayList<Lesion> getListaLesiones() {
        return listaLesiones;
    }

    public void setListaLesiones(ArrayList<Lesion> listaLesiones) {
        this.listaLesiones = listaLesiones;
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
