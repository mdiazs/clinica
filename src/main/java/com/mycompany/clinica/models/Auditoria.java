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
public class Auditoria implements Serializable {

    //Propiedades
    private int idAuditoria;
    private int idUser;
    private String accion;
    private String descripcion;
    private String fecha;
    
    //Propiedades tabla Empleados
    private String userEmpleado;

    public Auditoria(){
        
    }

    public Auditoria(int idUser, String accion, String descripcion, String fecha) {
        this.idUser = idUser;
        this.accion = accion;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    //Getters & Setters
    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUserEmpleado() {
        return userEmpleado;
    }

    public void setUserEmpleado(String userEmpleado) {
        this.userEmpleado = userEmpleado;
    }

}
