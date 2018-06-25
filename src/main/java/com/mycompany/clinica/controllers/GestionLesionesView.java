/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.mycompany.clinica.models.Lesion;
import com.mycompany.clinica.models.Complemento;
import com.mycompany.clinica.models.Tratamiento;
import org.primefaces.component.datatable.DataTable;
import com.mycompany.clinica.services.LesionService;
import com.mycompany.clinica.services.ComplementoService;
import com.mycompany.clinica.services.TratamientoService;

/**
 *
 * @author mads
 */
@ManagedBean(name = "gestionLesionesView")
@ViewScoped
public class GestionLesionesView {

    //Propiedades Diálogos
    private int lesionId;
    private String nombreLesion;
    private String descripcionLesion;
    private String causasLesion;
    private String prevencionLesion;
    private String sintomasLesion;
    private boolean tipoLesion;
    private boolean nuevaGestion;

    //Propiedades
    private Lesion lesion;
    private LesionService lesionService;
    private ArrayList<Lesion> listarLesiones;
    private ArrayList<Tratamiento> listaTratamientosDisponibles;
    private ArrayList<Tratamiento> listaTratamientosSeleccionados;
    private ArrayList<Complemento> listaComplementosDisponibles;
    private ArrayList<Complemento> listaComplementosSeleccionados;
    private Map mapAmpliacion;
    private ResourceBundle rb;
    private boolean exito;

    //Servicios
    private TratamientoService tratamientoService;
    private ComplementoService complementoService;

    //Inyectamos al empleado logueado para disponer de los atributos de éste que
    //no se pierden hasta que no se cierra sesión
    @ManagedProperty(value = "#{gestionSesionView}")
    private GestionSesionView gestionSesionView;

    //Métodos
    @PostConstruct
    public void initialize() {
        lesion = new Lesion();
        lesionService = new LesionService();
        listarLesiones = new ArrayList();
        listarLesiones = lesionService.cargarLesiones("WHERE lesiones.id_lesion <> 1");
        listaTratamientosDisponibles = new ArrayList();
        listaTratamientosSeleccionados = new ArrayList();
        listaComplementosDisponibles = new ArrayList();
        listaComplementosSeleccionados = new ArrayList();
        tratamientoService = new TratamientoService();
        complementoService = new ComplementoService();
        mapAmpliacion = new HashMap();
        rb = ResourceBundle.getBundle("mensajes");
    }

    public void inicializarVariables() {
        nombreLesion = "";
        descripcionLesion = "";
        tipoLesion = false;
        causasLesion = "";
        prevencionLesion = "";
        sintomasLesion = "";
        listaTratamientosDisponibles = new ArrayList();
        listaTratamientosSeleccionados = new ArrayList();
        listaComplementosDisponibles = new ArrayList();
        listaComplementosSeleccionados = new ArrayList();
        exito = false;
    }

    //Método que limpia el objeto
    public void guardarLesionPrev() {
        nuevaGestion = true;
        inicializarVariables();
        listaTratamientosDisponibles = tratamientoService.cargarTratamientos("");
        listaComplementosDisponibles = complementoService.cargarComplementos("");
    }

    //Método que inserta el objeto en la BD
    public void guardarLesion() {
        lesion = new Lesion();
        lesion.setNombre(nombreLesion);
        lesion.setDescripcion(descripcionLesion);
        lesion.setTipo(tipoLesion);
        lesion.setCausas(causasLesion);
        lesion.setPrevencion(prevencionLesion);
        lesion.setSintomas(sintomasLesion);

        lesionService.crearLesion(lesion, listaTratamientosSeleccionados, listaComplementosSeleccionados, gestionSesionView.getEmpleado().getIdUser());
        exito = lesionService.isSuccess();
        resetearFiltrosTabla(":lesion:dtLesiones");
        listarLesiones = lesionService.cargarLesiones("WHERE lesiones.id_lesion <> 1");
    }

    //Método que carga el objeto
    public void modificarLesionPrev(Lesion l) {
        nuevaGestion = false;
        inicializarVariables();
        lesionId = l.getIdLesion();
        nombreLesion = l.getNombre();
        descripcionLesion = l.getDescripcion();
        tipoLesion = l.isTipo();
        causasLesion = l.getCausas();
        prevencionLesion = l.getPrevencion();
        sintomasLesion = l.getSintomas();

        listaTratamientosSeleccionados = tratamientoService.cargarTratamientos(" JOIN lesiones_tratamientos "
                + " ON tratamientos.id_tratamiento = lesiones_tratamientos.id_tratamiento "
                + " WHERE id_lesion = " + lesionId);

        listaComplementosSeleccionados = complementoService.cargarComplementos(" JOIN lesiones_complementos "
                + " ON complementos.id_complemento = lesiones_complementos.id_complemento "
                + " WHERE id_lesion = " + lesionId);

        listaTratamientosDisponibles = tratamientoService.cargarTratamientos(" WHERE NOT EXISTS (SELECT * FROM lesiones_tratamientos "
                + " WHERE tratamientos.id_tratamiento = lesiones_tratamientos.id_tratamiento AND id_lesion = " + lesionId + ")");

        listaComplementosDisponibles = complementoService.cargarComplementos(" WHERE NOT EXISTS (SELECT * FROM lesiones_complementos "
                + " WHERE complementos.id_complemento = lesiones_complementos.id_complemento AND id_lesion = " + lesionId + ")");
    }

    //Método que edita el objeto de la BD
    public void modificarLesion() {
        lesion = new Lesion();
        lesion.setIdLesion(lesionId);
        lesion.setNombre(nombreLesion);
        lesion.setDescripcion(descripcionLesion);
        lesion.setTipo(tipoLesion);
        lesion.setCausas(causasLesion);
        lesion.setPrevencion(prevencionLesion);
        lesion.setSintomas(sintomasLesion);

        lesionService.editarLesion(lesion, listaTratamientosSeleccionados, listaComplementosSeleccionados, gestionSesionView.getEmpleado().getIdUser());
        exito = lesionService.isSuccess();
        resetearFiltrosTabla(":lesion:dtLesiones");
        listarLesiones = lesionService.cargarLesiones("WHERE lesiones.id_lesion <> 1");
    }

    //Método que elimina el objeto de la BD
    public void eliminarLesion(Lesion lesion) {
        lesionService.borrarLesion(lesion, gestionSesionView.getEmpleado().getIdUser());
        resetearFiltrosTabla(":lesion:dtLesiones");
        listarLesiones = lesionService.cargarLesiones("WHERE lesiones.id_lesion <> 1");
    }

    public void resetearFiltrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    //Método que añade un tratamiento a una lesión
    public void addLesionTratamiento(Tratamiento tratamiento) {
        listaTratamientosSeleccionados.add(tratamiento);
        listaTratamientosDisponibles.remove(tratamiento);
    }

    //Método que elimina un tratamiento de una lesión
    public void deleteLesionTratamiento(Tratamiento tratamiento) {
        listaTratamientosDisponibles.add(tratamiento);
        listaTratamientosSeleccionados.remove(tratamiento);
    }

    //Método que añade un complemento a una lesión
    public void addLesionComplemento(Complemento complemento) {
        listaComplementosSeleccionados.add(complemento);
        listaComplementosDisponibles.remove(complemento);
    }

    //Método que elimina un tratamiento de una lesión
    public void deleteLesionComplemento(Complemento complemento) {
        listaComplementosDisponibles.add(complemento);
        listaComplementosSeleccionados.remove(complemento);
    }

    public Map<String, String> ampliarLesion(Lesion l) {
        nombreLesion = l.getNombre();
        mapAmpliacion = new HashMap();
        mapAmpliacion.put(rb.getString("causasLesion"), l.getCausas());
        mapAmpliacion.put(rb.getString("prevencionLesion"), l.getPrevencion());
        mapAmpliacion.put(rb.getString("sintomasLesion"), l.getSintomas());
        return mapAmpliacion;
    }

    //Getters & Setters
    public int getLesionId() {
        return lesionId;
    }

    public void setLesionId(int lesionId) {
        this.lesionId = lesionId;
    }

    public String getNombreLesion() {
        return nombreLesion;
    }

    public void setNombreLesion(String nombreLesion) {
        this.nombreLesion = nombreLesion;
    }

    public String getDescripcionLesion() {
        return descripcionLesion;
    }

    public void setDescripcionLesion(String descripcionLesion) {
        this.descripcionLesion = descripcionLesion;
    }

    public String getCausasLesion() {
        return causasLesion;
    }

    public void setCausasLesion(String causasLesion) {
        this.causasLesion = causasLesion;
    }

    public String getPrevencionLesion() {
        return prevencionLesion;
    }

    public void setPrevencionLesion(String prevencionLesion) {
        this.prevencionLesion = prevencionLesion;
    }

    public String getSintomasLesion() {
        return sintomasLesion;
    }

    public void setSintomasLesion(String sintomasLesion) {
        this.sintomasLesion = sintomasLesion;
    }

    public boolean isTipoLesion() {
        return tipoLesion;
    }

    public void setTipoLesion(boolean tipoLesion) {
        this.tipoLesion = tipoLesion;
    }

    public Lesion getLesion() {
        return lesion;
    }

    public void setLesion(Lesion lesion) {
        this.lesion = lesion;
    }

    public LesionService getLesionService() {
        return lesionService;
    }

    public void setLesionService(LesionService lesionService) {
        this.lesionService = lesionService;
    }

    public ArrayList<Lesion> getListarLesiones() {
        return listarLesiones;
    }

    public void setListarLesiones(ArrayList<Lesion> listarLesiones) {
        this.listarLesiones = listarLesiones;
    }

    public ArrayList<Tratamiento> getListaTratamientosDisponibles() {
        return listaTratamientosDisponibles;
    }

    public void setListaTratamientosDisponibles(ArrayList<Tratamiento> listaTratamientosDisponibles) {
        this.listaTratamientosDisponibles = listaTratamientosDisponibles;
    }

    public ArrayList<Tratamiento> getListaTratamientosSeleccionados() {
        return listaTratamientosSeleccionados;
    }

    public void setListaTratamientosSeleccionados(ArrayList<Tratamiento> listaTratamientosSeleccionados) {
        this.listaTratamientosSeleccionados = listaTratamientosSeleccionados;
    }

    public ArrayList<Complemento> getListaComplementosDisponibles() {
        return listaComplementosDisponibles;
    }

    public void setListaComplementosDisponibles(ArrayList<Complemento> listaComplementosDisponibles) {
        this.listaComplementosDisponibles = listaComplementosDisponibles;
    }

    public ArrayList<Complemento> getListaComplementosSeleccionados() {
        return listaComplementosSeleccionados;
    }

    public void setListaComplementosSeleccionados(ArrayList<Complemento> listaComplementosSeleccionados) {
        this.listaComplementosSeleccionados = listaComplementosSeleccionados;
    }

    public TratamientoService getTratamientoService() {
        return tratamientoService;
    }

    public void setTratamientoService(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    public ComplementoService getComplementoService() {
        return complementoService;
    }

    public void setComplementoService(ComplementoService complementoService) {
        this.complementoService = complementoService;
    }

    public Map getMapAmpliacion() {
        return mapAmpliacion;
    }

    public void setMapAmpliacion(Map mapAmpliacion) {
        this.mapAmpliacion = mapAmpliacion;
    }

    public boolean isNuevaGestion() {
        return nuevaGestion;
    }

    public void setNuevaGestion(boolean nuevaGestion) {
        this.nuevaGestion = nuevaGestion;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
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
