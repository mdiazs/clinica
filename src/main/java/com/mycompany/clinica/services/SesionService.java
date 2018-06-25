/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.mycompany.clinica.models.Empleado;

/**
 *
 * @author mads
 */
public class SesionService extends Services {

    private final ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    //Método que prepara la comprobación del login
    public boolean comprobarLogin(Empleado emp) {

        FacesContext context = FacesContext.getCurrentInstance();

        PreparedStatement stmt = null;
        try {
            conectar();
            String sql = "SELECT pass FROM empleados WHERE user = '" + emp.getUser() + "'";
            stmt = conn.prepareStatement(sql);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset != null) {
                    while (rset.next()) {
                        String cap = rset.getString("pass");
                        if (emp.getPass().equals(cap)) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return false;
    }
    
    //Método que obtiene el id del empleado logueado para ser usado en la tabla auditorías
    public int obtenerIdUser(String userLogueado) {
        int idUserLogueado;
        idUserLogueado = 0;
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT empleados.id_user FROM empleados "
                    + " WHERE empleados.user = '" + userLogueado + "'";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    idUserLogueado = rset.getInt(1);
                }
                rset.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return idUserLogueado;
    }

}
