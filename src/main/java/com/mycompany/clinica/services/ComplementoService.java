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
import com.mycompany.clinica.models.Complemento;

/**
 *
 * @author mads
 */
public class ComplementoService extends Services {

    //Propiedades
    private ArrayList<Complemento> listaComplementos;
    private ArrayList<String> listaComplementosTipos;
    private ArrayList<Complemento> listaComplementosLesion;
    private boolean success;

    private final ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    private Auditoria auditoria;
    private AuditoriaService auditoriaService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date fechaActual;
    private String fechaActualString;

    //Método que prepara la carga de todos los objetos de la BD
    public ArrayList<Complemento> cargarComplementos(String and) {
        listaComplementos = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT complementos.id_complemento,"
                    + " complementos.nombre, "
                    + " complementos.tipo, "
                    + " complementos.descripcion "
                    + " FROM complementos "
                    + and
                    + " ORDER BY complementos.tipo ";
            stmt = getConn().prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Complemento complemento = new Complemento();
                    complemento.setIdComplemento(rset.getInt("id_complemento"));
                    complemento.setNombre(rset.getString("nombre"));
                    complemento.setTipo(rset.getString("tipo"));
                    complemento.setDescripcion(rset.getString("descripcion"));
                    listaComplementos.add(complemento);
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return listaComplementos;
    }

    //Método que prepara la inserción de un complemento en la BD
    public void crearComplemento(Complemento c, int idUsuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        success = false;

        boolean duplicado = false;

        //Compruebo si el nombre del complemento esta duplicado
        ArrayList<Complemento> listaComp = cargarComplementos("");
        for (Complemento complemento : listaComp) {
            if (c.getNombre().toLowerCase().equals(complemento.getNombre().toLowerCase())) {
                duplicado = true;
                break;
            }
        }

        if (duplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("nombreComplementoDuplicado")));
        } else {
            try {
                conectar();

                String sql = " INSERT INTO complementos (nombre, tipo, descripcion) "
                        + " VALUES ('" + c.getNombre() + "','" + c.getTipo() + "','" + c.getDescripcion()
                        + "')";

                stmt = conn.prepareStatement(sql);
                int numFilas = stmt.executeUpdate(sql);
                if (numFilas == 1) {
                    success = true;
                    fechaActual = new Date();
                    fechaActualString = sdf.format(fechaActual);
                    auditoria = new Auditoria(idUsuario, rb.getString("crearComplemento"), rb.getString("creacionComplemento") + " " + c.getNombre(), fechaActualString);
                    auditoriaService = new AuditoriaService();
                    auditoriaService.crearAuditoria(auditoria);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("complementoAnadido")));
                }
            } catch (SQLException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
                }
                desconectar();
            }
        }
    }

    //Método que prepara la edición de un complemento en la BD
    public void editarComplemento(Complemento c, int idUsuario) {

        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        success = false;

        boolean duplicado = false;

        //Compruebo si el nombre del complemento esta duplicado
        ArrayList<Complemento> listaComp = cargarComplementos("");
        for (Complemento complemento : listaComp) {
            if (c.getNombre().toLowerCase().equals(complemento.getNombre().toLowerCase()) && c.getIdComplemento() != complemento.getIdComplemento()) {
                duplicado = true;
                break;
            }
        }

        if (duplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("nombreComplementoDuplicado")));
        } else {
            try {
                conectar();

                String sql = " UPDATE complementos SET nombre = '" + c.getNombre() + "', tipo = '" + c.getTipo() + "', descripcion = '"
                        + c.getDescripcion() + "' WHERE id_complemento = " + c.getIdComplemento();

                stmt = conn.prepareStatement(sql);
                int numFilas = stmt.executeUpdate(sql);
                if (numFilas == 1) {
                    success = true;
                    fechaActual = new Date();
                    fechaActualString = sdf.format(fechaActual);
                    auditoria = new Auditoria(idUsuario, rb.getString("editarComplemento"), rb.getString("edicionComplemento") + " " + c.getNombre(), fechaActualString);
                    auditoriaService = new AuditoriaService();
                    auditoriaService.crearAuditoria(auditoria);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("complementoEditado")));
                }
            } catch (SQLException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
                }
                desconectar();
            }
        }
    }

    //Método que prepara la eliminación un remedio de la BD
    public void borrarComplemento(Complemento complemento, int idUsuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        try {
            conectar();
            String sql = " DELETE FROM complementos WHERE id_complemento = " + complemento.getIdComplemento();

            stmt = conn.prepareStatement(sql);
            int numFilas = stmt.executeUpdate(sql);
            if (numFilas == 1) {
                fechaActual = new Date();
                fechaActualString = sdf.format(fechaActual);
                auditoria = new Auditoria(idUsuario, rb.getString("eliminarComplemento"), rb.getString("eliminacionComplementoAuditorias") + " " + complemento.getNombre(), fechaActualString);
                auditoriaService = new AuditoriaService();
                auditoriaService.crearAuditoria(auditoria);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("complementoEliminado")));
            }
        } catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
            Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
            Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
    }

    //Método que carga solo los tipos de complementos
    public ArrayList<String> cargarComplementoTipos() {
        listaComplementosTipos = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;
        try {

            conectar();
            String sql = " SELECT DISTINCT "
                    + " complementos.tipo "
                    + " FROM complementos ";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    listaComplementosTipos.add(rset.getString(1));
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ComplementoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return listaComplementosTipos;
    }

    //Getters & Setters
    public ArrayList<Complemento> getListaComplementos() {
        return listaComplementos;
    }

    public void setListaComplementos(ArrayList<Complemento> listaComplementos) {
        this.listaComplementos = listaComplementos;
    }

    public ArrayList<String> getListaComplementosTipos() {
        return listaComplementosTipos;
    }

    public void setListaComplementosTipos(ArrayList<String> listaComplementosTipos) {
        this.listaComplementosTipos = listaComplementosTipos;
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

    public ArrayList<Complemento> getListaComplementosLesion() {
        return listaComplementosLesion;
    }

    public void setListaComplementosLesion(ArrayList<Complemento> listaComplementosLesion) {
        this.listaComplementosLesion = listaComplementosLesion;
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
