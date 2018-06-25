/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.models;

import java.io.Serializable;

/**
 *
 * @author mads
 */

public class Complemento implements Serializable{
    
    //Propiedades
    private int idComplemento;
    private String nombre;
    private String tipo;
    private String descripcion;
    
    //Getters & Setters
    public int getIdComplemento() {
        return idComplemento;
    }

    public void setIdComplemento(int idComplemento) {
        this.idComplemento = idComplemento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
