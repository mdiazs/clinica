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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.clinica.models.Auditoria;

/**
 *
 * @author mads
 */
public class AuditoriaService extends Services {

    //Propiedades
    private ArrayList<Auditoria> listaAuditorias;
    private boolean success;

    private final ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    //Método que prepara la carga de todos los objetos de la BD
    public ArrayList<Auditoria> cargarAuditorias(String where) {
        listaAuditorias = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT auditorias.id_auditoria, "
                    + " auditorias.id_user, "
                    + " auditorias.accion, "
                    + " auditorias.descripcion, "
                    + " date_format(auditorias.fecha,'%d/%m/%Y %H:%i:%s') as fecha, "
                    + " empleados.user AS user_empleado "
                    + " FROM auditorias "
                    + " JOIN empleados "
                    + " ON auditorias.id_user = empleados.id_user "
                    + where
                    + " ORDER BY auditorias.fecha DESC ";
            stmt = getConn().prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setIdAuditoria(rset.getInt("id_auditoria"));
                    auditoria.setIdUser(rset.getInt("id_user"));
                    auditoria.setAccion(rset.getString("accion"));
                    auditoria.setDescripcion(rset.getString("descripcion"));
                    auditoria.setFecha(rset.getString("fecha"));
                    auditoria.setUserEmpleado(rset.getString("user_empleado"));
                    listaAuditorias.add(auditoria);
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuditoriaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(AuditoriaService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AuditoriaService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return listaAuditorias;
    }

    //Método que prepara la inserción de una auditoría en la BD
    public void crearAuditoria(Auditoria auditoria) {

        PreparedStatement stmt = null;

        try {
            conectar();

            String sql = " INSERT INTO auditorias (id_user, accion, descripcion, fecha) "
                    + " VALUES (" + auditoria.getIdUser() + ", '" + auditoria.getAccion()
                    + "','" + auditoria.getDescripcion() + "','" + auditoria.getFecha()
                    + "')";

            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }

    }

    //Getters & Setters
    public ArrayList<Auditoria> getListaAuditorias() {
        return listaAuditorias;
    }

    public void setListaAuditorias(ArrayList<Auditoria> listaAuditorias) {
        this.listaAuditorias = listaAuditorias;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
