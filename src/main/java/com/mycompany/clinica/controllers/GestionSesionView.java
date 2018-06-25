/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.mycompany.clinica.models.Auditoria;
import com.mycompany.clinica.models.Empleado;
import com.mycompany.clinica.services.AuditoriaService;
import com.mycompany.clinica.services.SesionService;

/**
 *
 * @author mads
 */
@ManagedBean(name = "gestionSesionView")
@SessionScoped
public class GestionSesionView {

    //Propiedades
    private SesionService sesionService;
    private Empleado empleado;
    private Auditoria auditoria;
    private AuditoriaService auditoriaService;

    private final ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    private Date fechaActual;
    private String fechaActualString;
    private SimpleDateFormat sdf;

    //Métodos
    @PostConstruct
    public void initialize() {
        sesionService = new SesionService();
        empleado = new Empleado();

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    //Método que comprueba el login
    public String iniciarSesion() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (sesionService.comprobarLogin(empleado) == true) {
            int idUsuario = sesionService.obtenerIdUser(empleado.getUser());
            empleado.setIdUser(idUsuario);
            fechaActual = new Date();
            fechaActualString = sdf.format(fechaActual);
            auditoria = new Auditoria(idUsuario, rb.getString("iniciarSesion"), empleado.getUser() + " " + rb.getString("inicioSesion"), fechaActualString);
            auditoriaService = new AuditoriaService();
            auditoriaService.crearAuditoria(auditoria);
            return "menu.xhtml?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorLogin")));
            return null;
        }
    }

    //Getters & Setters
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public SesionService getSesionService() {
        return sesionService;
    }

    public void setSesionService(SesionService sesionService) {
        this.sesionService = sesionService;
    }
}
