/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.models;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mads
 */
public class CitaModulo implements Serializable, Comparable<CitaModulo> {

    //Propiedades
    private int idCita;
    private int idModulo;
    private Date fecha;

    //Propiedades de la Tabla Modulo
    private String modulo;
    private int tipo;

    //Propiedades Tabla Tratatmientos
    private String color;

    //Getters & Setters
    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    @Override
    public int compareTo(CitaModulo o) {
        if (idModulo < o.idModulo) {
            return -1;
        }
        if (idModulo > o.idModulo) {
            return 1;
        }
        return 0;
    }

}
