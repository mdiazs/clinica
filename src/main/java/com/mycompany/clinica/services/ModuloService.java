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
import com.mycompany.clinica.models.Modulo;

/**
 *
 * @author mads
 */
public class ModuloService extends Services {

    //Propiedades
    private ArrayList<Modulo> listaModulos;

    //Método que carga todos los módulos
    public ArrayList<Modulo> cargarModulos(String where) {

        listaModulos = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT modulos.id_modulo, modulos.tipo, "
                    + " modulos.modulo FROM modulos "
                    + where;
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Modulo m = new Modulo();
                    m.setIdModulo(rset.getInt("id_modulo"));
                    m.setTipo(rset.getInt("tipo"));
                    m.setModulo(rset.getString("modulo"));
                    listaModulos.add(m);
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
        return listaModulos;
    }

    //Método que carga un módulo trás pasarle una hora
    public Modulo obtenerModulo(String hora) {

        Modulo mod = new Modulo();
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT modulos.id_modulo, modulos.tipo, modulos.modulo FROM modulos "
                    + " WHERE modulos.modulo = '" + hora + "'";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    mod.setIdModulo(rset.getInt("id_modulo"));
                    mod.setTipo(rset.getInt("tipo"));
                    mod.setModulo(rset.getString("modulo"));
                }
                rset.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return mod;
    }

    //Método que carga un módulo trás pasarle el id de la cita
    public Modulo obtenerModuloPorId(int idCita) {

        Modulo mod = new Modulo();
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT modulos.id_modulo, modulos.tipo, modulos.modulo FROM modulos"
                    + " JOIN cita_modulo "
                    + " ON modulos.id_modulo = cita_modulo.id_modulo "
                    + " WHERE cita_modulo.id_cita = " + idCita
                    + " GROUP BY cita_modulo.id_cita";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    mod.setIdModulo(rset.getInt("id_modulo"));
                    mod.setTipo(rset.getInt("tipo"));
                    mod.setModulo(rset.getString("modulo"));
                }
                rset.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return mod;
    }

    //Método que obtiene el nombre de un modulo trás pasarle el id del mismo
    public String obtenerNombreModulo(int idModulo) {

        String nombreMod = "";
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT modulos.modulo FROM modulos "
                    + " WHERE modulos.id_modulo = '" + idModulo + "'";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    nombreMod = rset.getString(1);
                }
                rset.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModuloService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return nombreMod;
    }

    //Getters & Setters
    public ArrayList<Modulo> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(ArrayList<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

}
