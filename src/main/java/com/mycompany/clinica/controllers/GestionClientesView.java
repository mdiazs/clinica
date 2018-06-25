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
import com.mycompany.clinica.models.Cita;
import com.mycompany.clinica.models.Cliente;
import org.primefaces.component.datatable.DataTable;
import com.mycompany.clinica.services.CitaService;
import com.mycompany.clinica.services.ClienteService;

/**
 *
 * @author mads
 */
@ManagedBean(name = "gestionClientesView")
@ViewScoped
public class GestionClientesView {

    //Propiedades Diálogos
    private int clienteId;
    private String dniCliente;
    private String nombreCliente;
    private String apellidosCliente;
    private String localidadCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private boolean nuevaGestion;

    //Propiedades Generales
    private Cliente cliente;
    private ClienteService clienteService;
    private CitaService citaService;
    private ArrayList<Cliente> listarClientes;
    private ArrayList<String> listarLocalidades;
    private ArrayList<String> listaDniFiltro;
    private ArrayList<Cita> listaHistorialCli;
    private boolean exito;

    //Inyectamos al empleado logueado para disponer de los atributos de éste que
    //no se pierden hasta que no se cierra sesión
    @ManagedProperty(value = "#{gestionSesionView}")
    private GestionSesionView gestionSesionView;

    //Métodos
    @PostConstruct
    public void initialize() {
        clienteService = new ClienteService();
        listarClientes = new ArrayList();
        listarClientes = clienteService.cargarClientes();
        listarLocalidades = new ArrayList();
        listarLocalidades = clienteService.cargarLocalidadCliente();
        citaService = new CitaService();
    }

    public void inicializarVariables() {
        exito = false;
        dniCliente = "";
        nombreCliente = "";
        apellidosCliente = "";
        localidadCliente = "";
        direccionCliente = "";
        telefonoCliente = "";
        listarLocalidades = new ArrayList();
        listarLocalidades = clienteService.cargarLocalidadCliente();
    }

    //Método que limpia el objeto
    public void guardarClientePrev() {
        nuevaGestion = true;
        inicializarVariables();
    }

    //Método que inserta el objeto en la BD
    public void guardarCliente() {

        cliente = new Cliente();
        cliente.setDni(dniCliente.toUpperCase());
        cliente.setNombre(nombreCliente);
        cliente.setApellidos(apellidosCliente);
        cliente.setLocalidad(localidadCliente);
        cliente.setDireccion(direccionCliente);
        cliente.setTelefono(telefonoCliente);

        clienteService.crearCliente(cliente, gestionSesionView.getEmpleado().getIdUser());
        exito = clienteService.isSuccess();
        resetearFiltrosTabla(":cliente:dtClientes");
        listarClientes = clienteService.cargarClientes();

    }

    //Método que carga el objeto
    public void modificarClientePrev(Cliente c) {
        nuevaGestion = false;
        inicializarVariables();
        clienteId = c.getIdCliente();
        dniCliente = c.getDni();
        nombreCliente = c.getNombre();
        apellidosCliente = c.getApellidos();
        localidadCliente = c.getLocalidad();
        direccionCliente = c.getDireccion();
        telefonoCliente = c.getTelefono();
    }

    //Método que edita el objeto de la BD
    public void modificarCliente() {

        cliente = new Cliente();

        cliente.setIdCliente(clienteId);
        cliente.setDni(dniCliente);
        cliente.setNombre(nombreCliente);
        cliente.setApellidos(apellidosCliente);
        cliente.setLocalidad(localidadCliente);
        cliente.setDireccion(direccionCliente);
        cliente.setTelefono(telefonoCliente);

        clienteService.editarCliente(cliente, gestionSesionView.getEmpleado().getIdUser());
        exito = clienteService.isSuccess();
        resetearFiltrosTabla(":cliente:dtClientes");
        listarClientes = clienteService.cargarClientes();
    }

    //Método que elimina el objeto de la BD
    public void eliminarCliente(Cliente cliente) {
        clienteService.borrarCliente(cliente, gestionSesionView.getEmpleado().getIdUser());
        resetearFiltrosTabla(":cliente:dtClientes");
        listarClientes = clienteService.cargarClientes();
    }

    public void resetearFiltrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

    public void verHistorial(int idCli) {
        listaHistorialCli = new ArrayList();
        listaHistorialCli = citaService.cargarHistorial(idCli, "");

        //Esto lo tengo que hacer porque no funciona el f:facet del panelGrid de Historial de Clientes
        cliente = clienteService.obtenerUnCliente(idCli);
        nombreCliente = cliente.getNombre();
        apellidosCliente = cliente.getApellidos();
    }

    //Método que filtra por DNI al crear una nueva cita
    public ArrayList<String> cargarClientesCita(String query) {
        ArrayList<Cliente> listaClientes = new ArrayList();
        listaClientes = clienteService.cargarClientes();
        ArrayList<String> listaDocumento = new ArrayList();
        listaDniFiltro = new ArrayList();

        for (Cliente c : listaClientes) {
            listaDocumento.add(c.getDni());
        }

        for (int i = 0; i < listaDocumento.size(); i++) {
            String dni = listaDocumento.get(i);
            if (dni.toLowerCase().startsWith(query)) {
                listaDniFiltro.add(dni);
            }
        }

        return listaDniFiltro;
    }

    //Getters & Setters
    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ArrayList<Cliente> getListarClientes() {
        return listarClientes;
    }

    public void setListarClientes(ArrayList<Cliente> listarClientes) {
        this.listarClientes = listarClientes;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getLocalidadCliente() {
        return localidadCliente;
    }

    public void setLocalidadCliente(String localidadCliente) {
        this.localidadCliente = localidadCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<String> getListarLocalidades() {
        return listarLocalidades;
    }

    public void setListarLocalidades(ArrayList<String> listarLocalidades) {
        this.listarLocalidades = listarLocalidades;
    }

    public ArrayList<String> getListaDniFiltro() {
        return listaDniFiltro;
    }

    public void setListaDniFiltro(ArrayList<String> listaDniFiltro) {
        this.listaDniFiltro = listaDniFiltro;
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

    public ArrayList<Cita> getListaHistorialCli() {
        return listaHistorialCli;
    }

    public void setListaHistorialCli(ArrayList<Cita> listaHistorialCli) {
        this.listaHistorialCli = listaHistorialCli;
    }

    public CitaService getCitaService() {
        return citaService;
    }

    public void setCitaService(CitaService citaService) {
        this.citaService = citaService;
    }

    public void setGestionSesionView(GestionSesionView gestionSesionView) {
        this.gestionSesionView = gestionSesionView;
    }

}
