/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.controllers;

import java.util.ArrayList;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.mycompany.clinica.models.Tratamiento;
import org.primefaces.component.datatable.DataTable;
import com.mycompany.clinica.services.TratamientoService;

/**
 *
 * @author mads
 */
@ManagedBean(name = "gestionTratamientosView")
@ViewScoped
public class GestionTratamientosView {

    //Propiedades Diálogos
    private int tratamientoId;
    private String nombreTratamiento;
    private int duracionTratamiento;
    private double precioTratamiento;
    private String colorTratamiento;
    private String colorT;
    private boolean nuevaGestion;

    //Propiedades
    private Tratamiento tratamiento;
    private TratamientoService tratamientoService;
    private ArrayList<Tratamiento> listarTratamientos;
    private boolean exito;

    //Inyectamos al empleado logueado para disponer de los atributos de éste que
    //no se pierden hasta que no se cierra sesión
    @ManagedProperty(value = "#{gestionSesionView}")
    private GestionSesionView gestionSesionView;

    //Métodos
    @PostConstruct
    public void initialize() {
        tratamientoService = new TratamientoService();
        listarTratamientos = new ArrayList();
        listarTratamientos = tratamientoService.cargarTratamientos("");
    }

    public void inicializarVariables() {
        nombreTratamiento = "";
        duracionTratamiento = 0;
        precioTratamiento = 0;
        colorTratamiento = "";
        exito = false;
    }

    //Método que limpia el objeto
    public void guardarTratamientoPrev() {
        nuevaGestion = true;
        inicializarVariables();
    }

    //Método que inserta el objeto en la BD
    public void guardarTratamiento() {
        tratamiento = new Tratamiento();
        tratamiento.setNombre(nombreTratamiento);
        tratamiento.setDuracion(duracionTratamiento);
        tratamiento.setPrecio(precioTratamiento);
        tratamiento.setColor(colorTratamiento);

        tratamientoService.crearTratamiento(tratamiento, gestionSesionView.getEmpleado().getIdUser());
        exito = tratamientoService.isSuccess();
        resetearFiltrosTabla(":tratamiento:dtTratamientos");
        listarTratamientos = tratamientoService.cargarTratamientos("");
    }

    //Método que carga el objeto
    public void modificarTratamientoPrev(Tratamiento t) {
        nuevaGestion = false;
        inicializarVariables();
        tratamientoId = t.getIdTratamiento();
        nombreTratamiento = t.getNombre();
        duracionTratamiento = t.getDuracion();
        precioTratamiento = t.getPrecio();
        colorT = t.getColor();
        colorTratamiento = colorT.substring(1);
    }

    //Método que edita el objeto de la BD
    public void modificarTratamiento() {
        tratamiento = new Tratamiento();
        tratamiento.setIdTratamiento(tratamientoId);
        tratamiento.setNombre(nombreTratamiento);
        tratamiento.setDuracion(duracionTratamiento);
        tratamiento.setPrecio(precioTratamiento);
        tratamiento.setColor(colorTratamiento);

        tratamientoService.editarTratamiento(tratamiento, gestionSesionView.getEmpleado().getIdUser());
        exito = tratamientoService.isSuccess();
        resetearFiltrosTabla(":tratamiento:dtTratamientos");
        listarTratamientos = tratamientoService.cargarTratamientos("");
    }

    //Método que elimina el objeto de la BD
    public void eliminarTratamiento(Tratamiento tratamiento) {
        tratamientoService.borrarTratamiento(tratamiento, gestionSesionView.getEmpleado().getIdUser());
        resetearFiltrosTabla(":tratamiento:dtTratamientos");
        listarTratamientos = tratamientoService.cargarTratamientos("");
    }

    public void resetearFiltrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(Double.valueOf(filterText)) > 0;
    }

    //Getters & Setters
    public int getTratamientoId() {
        return tratamientoId;
    }

    public void setTratamientoId(int tratamientoId) {
        this.tratamientoId = tratamientoId;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public double getPrecioTratamiento() {
        return precioTratamiento;
    }

    public void setPrecioTratamiento(double precioTratamiento) {
        this.precioTratamiento = precioTratamiento;
    }

    public void setPrecioTratamiento(int precioTratamiento) {
        this.precioTratamiento = precioTratamiento;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public TratamientoService getTratamientoService() {
        return tratamientoService;
    }

    public void setTratamientoService(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    public ArrayList<Tratamiento> getListarTratamientos() {
        return listarTratamientos;
    }

    public void setListarTratamientos(ArrayList<Tratamiento> listarTratamientos) {
        this.listarTratamientos = listarTratamientos;
    }

    public String getColorTratamiento() {
        return colorTratamiento;
    }

    public void setColorTratamiento(String colorTratamiento) {
        this.colorTratamiento = colorTratamiento;
    }

    public int getDuracionTratamiento() {
        return duracionTratamiento;
    }

    public void setDuracionTratamiento(int duracionTratamiento) {
        this.duracionTratamiento = duracionTratamiento;
    }

    public String getColorT() {
        return colorT;
    }

    public void setColorT(String colorT) {
        this.colorT = colorT;
    }

    public boolean isNuevaGestion() {
        return nuevaGestion;
    }

    public void setNuevaGestion(boolean nuevaGestion) {
        this.nuevaGestion = nuevaGestion;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public GestionSesionView getGestionSesionView() {
        return gestionSesionView;
    }

    public void setGestionSesionView(GestionSesionView gestionSesionView) {
        this.gestionSesionView = gestionSesionView;
    }
    
}
