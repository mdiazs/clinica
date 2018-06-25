/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mads
 */
public class Services {

    //Propiedades
    public Connection conn;

    //Métodos
    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.setConn(DriverManager.getConnection("jdbc:mysql://localhost:3306/clinica?useSSL=false", "root",
                    ""));
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void desconectar() {
        try {
            this.getConn().close();
        } catch (SQLException e) {
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //Getters & Setters
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

}
