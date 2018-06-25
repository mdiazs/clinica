/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import com.mycompany.clinica.models.CitaModulo;

/**
 *
 * @author mads
 */
public class CitaModuloService extends Services {

    //Propiedades
    private ArrayList<CitaModulo> listaModulosFecha;
    private ArrayList<CitaModulo> listaModulosDisponiblesFecha;

    public ArrayList<CitaModulo> cargarModulosFecha(String where) {
        listaModulosFecha = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;
        try {

            conectar();

            String sql = " SELECT cita_modulo.id_cita, cita_modulo.id_modulo, "
                    + " tratamientos.color, modulos.modulo, modulos.tipo "
                    + " FROM cita_modulo "
                    + " JOIN citas "
                    + " ON cita_modulo.id_cita = citas.id_cita "
                    + " JOIN modulos "
                    + " ON cita_modulo.id_modulo = modulos.id_modulo "
                    + " JOIN tratamientos "
                    + " ON citas.id_tratamiento = tratamientos.id_tratamiento "
                    + where;

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    CitaModulo cm = new CitaModulo();
                    cm.setIdCita(rset.getInt("id_cita"));
                    cm.setIdModulo(rset.getInt("id_modulo"));
                    cm.setModulo(rset.getString("modulo"));
                    cm.setTipo(rset.getInt("tipo"));
                    cm.setColor(rset.getString("color"));
                    listaModulosFecha.add(cm);
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
        return listaModulosFecha;
    }

    public ArrayList<CitaModulo> cargarModulosDisponiblesFecha(String fecha) {
        listaModulosDisponiblesFecha = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;
        try {

            conectar();

            String sql = " SELECT * FROM modulos "
                    + " WHERE NOT EXISTS (SELECT * FROM cita_modulo "
                    + " WHERE modulos.id_modulo = cita_modulo.id_modulo "
                    + " AND cita_modulo.fecha = '"
                    + fecha;

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    CitaModulo cm = new CitaModulo();
                    cm.setIdModulo(rset.getInt("id_modulo"));
                    cm.setModulo(rset.getString("modulo"));
                    cm.setTipo(rset.getInt("tipo"));
                    listaModulosDisponiblesFecha.add(cm);
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
        return listaModulosDisponiblesFecha;
    }


    //  Método que prepara la eliminación de una cita de la BD
    public void eliminarCitaFecha(int idCitaMod) {
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;

        try {
            conectar();

            String sql = "DELETE FROM cita_modulo WHERE id_cita = " + idCitaMod;
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);

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
    }
    
    public int cargarNumeroModulos(int idCit) {
        int numeroModulos = 0;
        PreparedStatement stmt = null;
        ResultSet rset;
        try {

            conectar();

            String sql = " SELECT count(id_modulo) AS numModulos "
                    + " FROM cita_modulo WHERE id_cita = "
                    + idCit;

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            rset.next();
            numeroModulos = rset.getInt("numModulos");

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
        return numeroModulos;
    }
    
    //Getters & Setters

    public ArrayList<CitaModulo> getListaModulosFecha() {
        return listaModulosFecha;
    }

    public void setListaModulosFecha(ArrayList<CitaModulo> listaModulosFecha) {
        this.listaModulosFecha = listaModulosFecha;
    }

    public ArrayList<CitaModulo> getListaModulosDisponiblesFecha() {
        return listaModulosDisponiblesFecha;
    }

    public void setListaModulosDisponiblesFecha(ArrayList<CitaModulo> listaModulosDisponiblesFecha) {
        this.listaModulosDisponiblesFecha = listaModulosDisponiblesFecha;
    }

}
