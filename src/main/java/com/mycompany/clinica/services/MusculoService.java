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
import com.mycompany.clinica.models.Musculo;

/**
 *
 * @author mads
 */
public class MusculoService extends Services {

    //Propiedades
    private ArrayList<Musculo> listaMusculos;
    private ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    //Método que prepara la carga de todos los objetos de la BD
    public ArrayList<Musculo> cargarMusculos(String where) {
        PreparedStatement stmt = null;
        ResultSet rset;
        try {
            listaMusculos = new ArrayList();
            conectar();
            String sql = " SELECT musculos.id_musculo, "
                    + " musculos.nombre, musculos.id_zona, "
                    + " musculos.url_imagen "
                    + " FROM musculos "
                    + where;
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Musculo musculo = new Musculo();
                    musculo.setIdMusculo(rset.getInt("id_musculo"));
                    musculo.setNombre(rset.getString("nombre"));
                    musculo.setIdZona(rset.getInt("id_zona"));
                    musculo.setUrl_imagen(rset.getString("url_imagen"));
                    listaMusculos.add(musculo);
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MusculoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(MusculoService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MusculoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return listaMusculos;
    }
    
    //Método que carga un músculo por ID
    public Musculo cargarUnMusculo(int idMusc) {
        Musculo musculo = new Musculo();
        PreparedStatement stmt = null;
        ResultSet rset;
        try {
            listaMusculos = new ArrayList();
            conectar();
            String sql = " SELECT musculos.id_musculo, "
                    + " musculos.nombre, musculos.id_zona, "
                    + " musculos.url_imagen, "
                    + " zonas.nombre AS nombre_zona "
                    + " FROM musculos "
                    + " JOIN zonas "
                    + " ON musculos.id_zona = zonas.id_zona "
                    + " WHERE id_musculo = " + idMusc;
                    
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    musculo.setIdMusculo(rset.getInt("id_musculo"));
                    musculo.setIdZona(rset.getInt("id_zona"));
                    musculo.setNombre(rset.getString("nombre"));
                    musculo.setUrl_imagen(rset.getString("url_imagen"));
                    musculo.setNombreZona(rset.getString("nombre_zona"));
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MusculoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(MusculoService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MusculoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return musculo;
    }

    //Getters & Setters
    public ResourceBundle getRb() {
        return rb;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }

    public ArrayList<Musculo> getListaMusculos() {
        return listaMusculos;
    }

    public void setListaMusculos(ArrayList<Musculo> listaMusculos) {
        this.listaMusculos = listaMusculos;
    }

}
