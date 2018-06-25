/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.controllers;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.mycompany.clinica.models.Complemento;
import org.primefaces.component.datatable.DataTable;
import com.mycompany.clinica.services.ComplementoService;

/**
 *
 * @author mads
 */
@ManagedBean(name = "gestionComplementosView")
@ViewScoped
public class GestionComplementosView {

    //Propiedades Diálogos
    private int complementoId;
    private String nombreComplemento;
    private String tipoComplemento;
    private String descripcionComplemento;
    private boolean nuevaGestion;

    //Propiedades
    private Complemento complemento;
    private ComplementoService complementoService;
    private ArrayList<Complemento> listarComplementos;
    private ArrayList<String> listarComplementosTipos;
    private boolean exito;

    //Inyectamos al empleado logueado para disponer de los atributos de éste que
    //no se pierden hasta que no se cierra sesión
    @ManagedProperty(value = "#{gestionSesionView}")
    private GestionSesionView gestionSesionView;

    //Métodos
    @PostConstruct
    public void initialize() {
        complementoService = new ComplementoService();
        listarComplementos = new ArrayList();
        listarComplementos = complementoService.cargarComplementos("");
        listarComplementosTipos = new ArrayList();
        listarComplementosTipos = complementoService.cargarComplementoTipos();
    }

    public void inicializarVariables() {
        nombreComplemento = "";
        tipoComplemento = "";
        descripcionComplemento = "";
        listarComplementosTipos = complementoService.cargarComplementoTipos();
        exito = false;
    }

    //Método que limpia el objeto
    public void guardarComplementoPrev() {
        nuevaGestion = true;
        inicializarVariables();
    }

    //Método que inserta el objeto en la BD
    public void guardarComplemento() {
        complemento = new Complemento();
        complemento.setNombre(nombreComplemento);
        complemento.setTipo(tipoComplemento);
        complemento.setDescripcion(descripcionComplemento);

        complementoService.crearComplemento(complemento, gestionSesionView.getEmpleado().getIdUser());
        exito = complementoService.isSuccess();
        resetearFiltrosTabla(":complemento:dtComplementos");
        listarComplementos = complementoService.cargarComplementos("");
    }

    //Método que carga el objeto
    public void modificarComplementoPrev(Complemento c) {
        nuevaGestion = false;
        inicializarVariables();
        complementoId = c.getIdComplemento();
        nombreComplemento = c.getNombre();
        tipoComplemento = c.getTipo();
        descripcionComplemento = c.getDescripcion();
    }

    //Método que edita el objeto de la BD
    public void modificarComplemento() {
        complemento = new Complemento();
        complemento.setNombre(nombreComplemento);
        complemento.setTipo(tipoComplemento);
        complemento.setIdComplemento(complementoId);
        complemento.setDescripcion(descripcionComplemento);

        complementoService.editarComplemento(complemento, gestionSesionView.getEmpleado().getIdUser());
        exito = complementoService.isSuccess();
        resetearFiltrosTabla(":complemento:dtComplementos");
        listarComplementos = complementoService.cargarComplementos("");
    }

    //Método que elimina el objeto de la BD
    public void eliminarComplemento(Complemento complemento) {
        complementoService.borrarComplemento(complemento, gestionSesionView.getEmpleado().getIdUser());
        resetearFiltrosTabla(":complemento:dtComplementos");
        listarComplementos = complementoService.cargarComplementos("");
    }

    public void resetearFiltrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    //Getters & Setters
    public int getComplementoId() {
        return complementoId;
    }

    public void setComplementoId(int complementoId) {
        this.complementoId = complementoId;
    }

    public String getTipoComplemento() {
        return tipoComplemento;
    }

    public void setTipoComplemento(String tipoComplemento) {
        this.tipoComplemento = tipoComplemento;
    }

    public Complemento getComplemento() {
        return complemento;
    }

    public void setComplemento(Complemento complemento) {
        this.complemento = complemento;
    }

    public ComplementoService getComplementoService() {
        return complementoService;
    }

    public void setComplementoService(ComplementoService complementoService) {
        this.complementoService = complementoService;
    }

    public ArrayList<Complemento> getListarComplementos() {
        return listarComplementos;
    }

    public void setListarComplementos(ArrayList<Complemento> listarComplementos) {
        this.listarComplementos = listarComplementos;
    }

    public boolean isNuevaGestion() {
        return nuevaGestion;
    }

    public void setNuevaGestion(boolean nuevaGestion) {
        this.nuevaGestion = nuevaGestion;
    }

    public ArrayList<String> getListarComplementosTipos() {
        return listarComplementosTipos;
    }

    public void setListarComplementosTipos(ArrayList<String> listarComplementosTipos) {
        this.listarComplementosTipos = listarComplementosTipos;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getNombreComplemento() {
        return nombreComplemento;
    }

    public void setNombreComplemento(String nombreComplemento) {
        this.nombreComplemento = nombreComplemento;
    }

    public String getDescripcionComplemento() {
        return descripcionComplemento;
    }

    public void setDescripcionComplemento(String descripcionComplemento) {
        this.descripcionComplemento = descripcionComplemento;
    }

    public GestionSesionView getGestionSesionView() {
        return gestionSesionView;
    }

    public void setGestionSesionView(GestionSesionView gestionSesionView) {
        this.gestionSesionView = gestionSesionView;
    }

}
