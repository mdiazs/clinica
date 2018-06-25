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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.clinica.models.Zona;

/**
 *
 * @author mads
 */
public class ZonaService extends Services {

    //Propiedades
    private ArrayList<Zona> listaZonas;
    private ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    //Método que prepara la carga de todos los objetos de la BD
    public ArrayList<Zona> cargarZonas() {
        PreparedStatement stmt = null;
        ResultSet rset;
        try {
            listaZonas = new ArrayList();
            conectar();
            String sql = " SELECT zonas.id_zona, "
                    + " zonas.nombre "
                    + " FROM zonas ";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Zona zona = new Zona();
                    zona.setIdZona(rset.getInt("id_zona"));
                    zona.setNombre(rset.getString("nombre"));
                    listaZonas.add(zona);
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ZonaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ZonaService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ZonaService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return listaZonas;
    }
    
    //Getters & Setters
    public ArrayList<Zona> getListaZonas() {
        return listaZonas;
    }

    public void setListaZonas(ArrayList<Zona> listaZonas) {
        this.listaZonas = listaZonas;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }
    

}
