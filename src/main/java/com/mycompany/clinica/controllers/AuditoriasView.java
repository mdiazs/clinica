/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.mycompany.clinica.models.Auditoria;
import com.mycompany.clinica.services.AuditoriaService;

/**
 *
 * @author mads
 */
@ManagedBean(name = "auditoriasView")
@ViewScoped
public class AuditoriasView {

    //Propiedades
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaActual;
    
    private ArrayList <Auditoria> listarAuditorias;
    
    private AuditoriaService auditoriaService;
    
    private SimpleDateFormat sdf;

    //Métodos
    @PostConstruct
    public void initialize() {
       sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       fechaActual = new Date();
    }
    
    public void mostrarAuditorias(){
        auditoriaService = new AuditoriaService();
        listarAuditorias=auditoriaService.cargarAuditorias(" WHERE auditorias.fecha >= '" + sdf.format(fechaInicio) + 
                "' AND auditorias.fecha <= '" + sdf.format(fechaFin) + "'");
        fechaActual = new Date();
    }
    
    public void actualizarFecha(){
        fechaActual = new Date();
    }
    
    //Getters & Setters
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ArrayList<Auditoria> getListarAuditorias() {
        return listarAuditorias;
    }

    public void setListarAuditorias(ArrayList<Auditoria> listarAuditorias) {
        this.listarAuditorias = listarAuditorias;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }

    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
    
}
