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
public class Cita implements Serializable {

    //Propiedades
    private int idCita;
    private int idCliente;
    private int idLesion;
    private String grado;
    private String estado;
    private int idZona;
    private int idMusculo;
    private String observaciones;
    private int idTratamiento;
    private boolean atendida;

    //Propiedades TABLA CLIENTES
    private String nombreCliente;
    private String apellidos;

    //Propiedades TAVLA LESIONES
    private String nombreLesion;

    //Propiedades TABLA TRATAMIENTOS
    private String nombreTratamiento;
    
    //Propiedades TABLA CITA_MODULO
    private Date fecha;
    
    //Propiedades TABLA MODULO
    private String modulo;
    private int idModulo;
    
    //Propiedades TABLA MUSCULOS
    private String nombreMusculo;
    private String urlImagenMusculo;

    //Guetters y Setters
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdLesion() {
        return idLesion;
    }

    public void setIdLesion(int idLesion) {
        this.idLesion = idLesion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }

    public String getNombreLesion() {
        return nombreLesion;
    }

    public void setNombreLesion(String nombreLesion) {
        this.nombreLesion = nombreLesion;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public int getIdMusculo() {
        return idMusculo;
    }

    public void setIdMusculo(int idMusculo) {
        this.idMusculo = idMusculo;
    }

    public String getNombreMusculo() {
        return nombreMusculo;
    }

    public void setNombreMusculo(String nombreMusculo) {
        this.nombreMusculo = nombreMusculo;
    }

    public String getUrlImagenMusculo() {
        return urlImagenMusculo;
    }

    public void setUrlImagenMusculo(String urlImagenMusculo) {
        this.urlImagenMusculo = urlImagenMusculo;
    }

}
