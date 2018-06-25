/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import com.mycompany.clinica.models.Auditoria;
import com.mycompany.clinica.models.Cita;
import com.mycompany.clinica.models.CitaModulo;
import com.mycompany.clinica.models.Cliente;
import com.mycompany.clinica.models.Complemento;
import com.mycompany.clinica.models.Lesion;
import com.mycompany.clinica.models.Modulo;
import com.mycompany.clinica.models.Musculo;
import com.mycompany.clinica.models.Tratamiento;
import com.mycompany.clinica.models.Zona;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import com.mycompany.clinica.services.AuditoriaService;
import com.mycompany.clinica.services.CitaModuloService;
import com.mycompany.clinica.services.CitaService;
import com.mycompany.clinica.services.ClienteService;
import com.mycompany.clinica.services.ComplementoService;
import com.mycompany.clinica.services.LesionService;
import com.mycompany.clinica.services.ModuloService;
import com.mycompany.clinica.services.MusculoService;
import com.mycompany.clinica.services.TratamientoService;
import com.mycompany.clinica.services.ZonaService;

/**
 *
 * @author mads
 */
@ManagedBean(name = "gestionCitasView")
@ViewScoped
public class GestionCitasView {

    private Cita cita;
    private CitaService citaService;
    private LesionService lesionService;
    private Lesion lesion;
    private TratamientoService tratamientoService;
    private Tratamiento tratamiento;
    private ModuloService moduloService;
    private ClienteService clienteService;
    private CitaModuloService citaModuloService;
    private ComplementoService complementoService;
    private Modulo moduloElegido;
    private ZonaService zonaService;
    private MusculoService musculoService;

    private boolean verTodas;

    //Propiedades Diálogo
    private Date fechaCita;
    private String horaCita;
    private int idLesionCita;
    private int idTratamientoLesion;
    private double precioTratamientoLesion;
    private int duracionTratamientoLesion;
    private String dniClienteCita;
    private int idCita;
    private boolean nuevaGestion;
    private int idClienteCita;
    private String gradoLesion;
    private String estadoLesion;
    private String nombreTratamiento;
    private String nombreLesion;
    private String observacionesCita;
    private int idModuloCita;
    private int idZonaCita;
    private int idMusculoCita;
    private String nombreCliente;
    private String apellidosCliente;

    //Propiedades Generales
    private ArrayList<Cita> listarCitas;
    private ArrayList<CitaModulo> listaModulosOcupados;
    private ArrayList<Cita> listarCitasFecha;
    private ArrayList<Modulo> listaHorarioManana;
    private ArrayList<Modulo> listaHorarioTarde;
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdfEs;
    private Date fechaActual;
    private boolean mostrarDtCitasFecha;
    private ArrayList<Lesion> listaNombresLesiones;
    private ArrayList<Tratamiento> listaTratamientosLesion;
    private ArrayList<Cliente> listaPrueba;
    private Map<String, String> mapHorarioManana;
    private Map<String, String> mapHorarioTarde;
    private boolean exito;
    private ArrayList<Complemento> listaComplementosDisponibles;
    private ArrayList<Complemento> listaComplementosSeleccionados;
    private ArrayList<Cita> listaHistorial;
    private Date fechaTomorrow;
    private int modulos;
    private ArrayList<Modulo> listaModulos;
    private ArrayList<CitaModulo> listaModulosDisponibles;
    private ArrayList<Zona> listaZonas;
    private ArrayList<Musculo> listaMusculos;
    private ArrayList<Tratamiento> listaTratamientosCompleta;

    private ResourceBundle rb;

    private Auditoria auditoria;
    private AuditoriaService auditoriaService;

    private Date fechaActu;
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String fechaActualString;

    //Inyectamos al empleado logueado para disponer de los atributos de éste que
    //no se pierden hasta que no se cierra sesión
    @ManagedProperty(value = "#{gestionSesionView}")
    private GestionSesionView gestionSesionView;

    //Métodos
    @PostConstruct
    public void initialize() {
        citaService = new CitaService();
        lesionService = new LesionService();
        tratamientoService = new TratamientoService();
        clienteService = new ClienteService();
        listarCitas = new ArrayList();
        listarCitasFecha = new ArrayList();
        listaNombresLesiones = lesionService.cargarLesiones("");
        listaTratamientosLesion = new ArrayList();
        listaPrueba = new ArrayList();
        moduloService = new ModuloService();
        citaModuloService = new CitaModuloService();
        complementoService = new ComplementoService();
        listaModulos = new ArrayList();
        listaModulos = moduloService.cargarModulos("");
        zonaService = new ZonaService();
        listaZonas = zonaService.cargarZonas();
        listaTratamientosCompleta = new ArrayList();
        listaTratamientosCompleta = tratamientoService.cargarTratamientos("");
        
        //Cargamos todas las citas cuya fecha sea la actual
        listarCitas = citaService.cargarCitas("WHERE cita_modulo.fecha = CURDATE()");

        //Controlamos que el día inicial del calendario sea el siguiente al actual
        //Esto es debido a que queremos que en un principio el datatable de citas
        //muestre las citas del día pero al hacer que se muestren todas, dicho calendario
        //no va a permitir que se seleccione el día actual, ya que eso lo controla el botón
        //de mostrar citas
        fechaActual = new Date();
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        fechaTomorrow = dt;

        sdf = new SimpleDateFormat("yyyy-MM-dd");

        sdfEs = new SimpleDateFormat("dd/MM/yyyy");

        //Con ésto, podemos acceder a los mensajes del archivo mensajes.property
        rb = ResourceBundle.getBundle("mensajes");

    }

    public void inicializarVariables() {
        fechaCita = null;
        idCita = 0;
        horaCita = "";
        idLesionCita = 0;
        idTratamientoLesion = 0;
        dniClienteCita = "";
        mostrarDtCitasFecha = false;
        listarCitasFecha = new ArrayList();
        listaHorarioManana = new ArrayList();
        listaHorarioTarde = new ArrayList();
        duracionTratamientoLesion = 0;
        precioTratamientoLesion = 0;
        listaPrueba = new ArrayList();
        idClienteCita = 0;
        exito = false;
        gradoLesion = "";
        estadoLesion = "";
        observacionesCita = "";
        idZonaCita = 0;
        idMusculoCita = 0;
        listaComplementosSeleccionados = new ArrayList();
    }

    //Método que limpia el objeto
    public void guardarCitaPrev() {
        nuevaGestion = true;
        inicializarVariables();
    }

//    Método que inserta el objeto en la BD
    public void guardarCita(int clienteId) {
        cita = new Cita();
        cita.setFecha(fechaCita);
        cita.setIdCliente(clienteId);
        cita.setIdLesion(idLesionCita);
        cita.setIdTratamiento(idTratamientoLesion);

        // Obtengo el id del modulo elegido
        moduloElegido = new Modulo();
        moduloElegido = moduloService.obtenerModulo(horaCita);

        if (duracionTratamientoLesion >= 15) {
            modulos = duracionTratamientoLesion / 15;
        }

        citaService.crearCita(cita, moduloElegido, modulos, listaModulosOcupados, gestionSesionView.getEmpleado().getIdUser());
        exito = citaService.isSuccess();
        resetearFiltrosTabla(":cita:dtCitas");
        verTodas = false;
        listarCitas = citaService.cargarCitas("WHERE cita_modulo.fecha = CURDATE()");

    }

//  Método que carga el objeto
    public void modificarCitaPrev(Cita c) {
        nuevaGestion = false;
        inicializarVariables();
        idCita = c.getIdCita();
        idClienteCita = c.getIdCliente();
        dniClienteCita = clienteService.obtenerDniCliente(idClienteCita);
        obtenerCliente(dniClienteCita);
        fechaCita = c.getFecha();
        horaCita = c.getModulo();
        idLesionCita = c.getIdLesion();
        idTratamientoLesion = c.getIdTratamiento();
        mostrarDtCitasFecha = true;
        listarCitasFecha = new ArrayList();
        listarCitasFecha = citaService.cargarCitas("WHERE cita_modulo.fecha = '" + sdf.format(fechaCita) + "'");

        cargarTratamientosLesion(idLesionCita);

        tratamiento = tratamientoService.cargarUnTratamiento(" WHERE id_tratamiento = " + idTratamientoLesion);
        precioTratamientoLesion = tratamiento.getPrecio();
        duracionTratamientoLesion = tratamiento.getDuracion();

        idModuloCita = c.getIdModulo();

    }

//  Método que edita el objeto de la BD
    public void modificarCita() {
        cita = new Cita();

        cita.setIdCita(idCita);
        cita.setIdCliente(idClienteCita);
        cita.setFecha(fechaCita);
//      cita.setHora(horaCita);
        cita.setIdLesion(idLesionCita);
        cita.setIdTratamiento(idTratamientoLesion);

        // Obtengo el id del modulo elegido
        moduloElegido = new Modulo();
        moduloElegido = moduloService.obtenerModulo(horaCita);

        if (duracionTratamientoLesion >= 15) {
            modulos = duracionTratamientoLesion / 15;
        }

        citaService.editarCita(cita, moduloElegido, modulos, listaModulosOcupados, gestionSesionView.getEmpleado().getIdUser());
        exito = citaService.isSuccess();
        resetearFiltrosTabla(":cita:dtCitas");
        verTodas = false;

        listarCitas = citaService.cargarCitas("WHERE cita_modulo.fecha = CURDATE()");
    }

    // Método que elimina el objeto de la BD
    public void eliminarCita(Cita cita) {
        citaService.borrarCita(cita, gestionSesionView.getEmpleado().getIdUser());
        resetearFiltrosTabla(":cita:dtCitas");
        verTodas = false;
        listarCitas = citaService.cargarCitas("WHERE cita_modulo.fecha = CURDATE()");
    }

    //Este método es el encargado de controlar el horario disponible. Restringe las horas en la parte del cliente
    //trás cargar los datos de la bd.
    public void cargarFechaModulos(Date fecha) {

        //Cargamos todos los módulos de la mañana
        listaHorarioManana = new ArrayList();
        listaHorarioManana = moduloService.cargarModulos(" WHERE modulos.tipo = 0 ");

        //Cargamos todos los módulos de la tarde
        listaHorarioTarde = new ArrayList();
        listaHorarioTarde = moduloService.cargarModulos(" WHERE modulos.tipo = 1 ");

        mapHorarioManana = new TreeMap();
        mapHorarioTarde = new TreeMap();

        //Introducimos los nombres de los módulos en un map y les asignamos un color inicial
        for (Modulo horaManana : listaHorarioManana) {
            mapHorarioManana.put(horaManana.getModulo(), "#98FB98");
        }

        for (Modulo horaTarde : listaHorarioTarde) {
            mapHorarioTarde.put(horaTarde.getModulo(), "#98FB98");
        }

        // Controlo las horas para el día actual. Las horas y minutos anteriores a la hora actual del día actual
        // son deshabilitados
        String fechaHoy = sdf.format(fechaActual);
        String fechaRecibida = sdf.format(fecha);

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String horaActualString = sdf2.format(fechaActual);
        int horaActual = Integer.parseInt(horaActualString.substring(11, 13));
        int minActual = Integer.parseInt(horaActualString.substring(14, 16));

        if (fechaRecibida.equals(fechaHoy)) {
            for (Entry<String, String> horaManana : mapHorarioManana.entrySet()) {
                int mananaHora = Integer.parseInt(horaManana.getKey().substring(0, 2));
                int mananaMin = Integer.parseInt(horaManana.getKey().substring(3, 5));

                if (mananaHora < horaActual) {
                    mapHorarioManana.put(horaManana.getKey(), "#DDDDDD");
                }

                if (mananaHora == horaActual) {
                    if (mananaMin < minActual) {
                        mapHorarioManana.put(horaManana.getKey(), "#DDDDDD");
                    }
                }
            }

            for (Entry<String, String> horaTarde : mapHorarioTarde.entrySet()) {
                int tardeHora = Integer.parseInt(horaTarde.getKey().substring(0, 2));
                int tardeMin = Integer.parseInt(horaTarde.getKey().substring(3, 5));

                if (tardeHora < horaActual) {
                    mapHorarioTarde.put(horaTarde.getKey(), "#DDDDDD");
                }

                if (tardeHora == horaActual) {
                    if (tardeMin < minActual) {
                        mapHorarioTarde.put(horaTarde.getKey(), "#DDDDDD");
                    }
                }
            }
        }

        //Cargo una lista con los modulos ocupados
        listaModulosOcupados = new ArrayList();
        listaModulosOcupados = citaModuloService.cargarModulosFecha("WHERE cita_modulo.fecha = '" + sdf.format(fecha) + "'");
        Collections.sort(listaModulosOcupados);

        //Cargo una lista con los modulos disponibles
        listaModulosDisponibles = new ArrayList();
        listaModulosDisponibles = citaModuloService.cargarModulosDisponiblesFecha(sdf.format(fecha) + "')");
        Collections.sort(listaModulosDisponibles);

        //En caso de que sea una modificación de la cita, los modulos que dicha cita ocupan deben aparecer disponibles
        if (!nuevaGestion) {

            Modulo modul = moduloService.obtenerModuloPorId(idCita);
            int idModu = modul.getIdModulo();
            int numModu = citaModuloService.cargarNumeroModulos(idCita);

            //Si estamos modificando una cita, el rango de horas de dicha cita debería
            //estar disponible.
            for (int i = 0; i < numModu; i++) {

                for (CitaModulo cmOcup : listaModulosOcupados) {
                    if (cmOcup.getIdModulo() == idModu) {
                        listaModulosDisponibles.add(cmOcup);
                        listaModulosOcupados.remove(cmOcup);
                        break;
                    }
                }
                idModu++;

            }

        }

        //Ordeno las listas por id
        Collections.sort(listaModulosDisponibles);
        Collections.sort(listaModulosOcupados);

        //Recorro la lista de módulos ocupados y les voy añadiendo el color que los diferencia
        for (CitaModulo cm : listaModulosOcupados) {
            if (cm.getTipo() == 0) {
                for (Modulo horaManana : listaHorarioManana) {
                    if (cm.getModulo().equals(horaManana.getModulo())) {
                        mapHorarioManana.put(horaManana.getModulo(), "#" + cm.getColor());
                    }
                }
            } else {
                for (Modulo horaTarde : listaHorarioTarde) {
                    if (cm.getModulo().equals(horaTarde.getModulo())) {
                        mapHorarioTarde.put(horaTarde.getModulo(), "#" + cm.getColor());
                    }
                }
            }
        }

        //Obtengo el número de módulos que va a ocupar cada tratamiento
        if (duracionTratamientoLesion >= 15) {
            modulos = duracionTratamientoLesion / 15;
        }

        CitaModulo cts;
        CitaModulo ctsAux;
        int modId;
        for (int cont = 0; cont <= listaModulosDisponibles.size() - 1; cont++) {
            cts = (CitaModulo) listaModulosDisponibles.get(cont);
            modId = cts.getIdModulo();

            //Solo hacemos algo si el módulo es mayor que 1
            if (modulos > 1) {
                // Miramos si los n modulos que necesitamos están disponibles
                if (modulos + cont <= listaModulosDisponibles.size()) {
                    // esto Significa que los n consecutivos existen
                    // Realizo lectura adelantada
                    int idModAux;
                    boolean resultcom = true;
                    for (int j = 1; j < modulos; j++) {
                        ctsAux = (CitaModulo) listaModulosDisponibles.get(cont + j);
                        idModAux = ctsAux.getIdModulo();

                        if (idModAux != modId + j) {
                            resultcom = false;
                        }
                    }

                    if (!resultcom) {
                        if (listaModulosDisponibles.get(cont).getIdModulo() <= 50) {
                            mapHorarioManana.put(listaModulosDisponibles.get(cont).getModulo(), "#DDDDDD");
                        } else {
                            mapHorarioTarde.put(listaModulosDisponibles.get(cont).getModulo(), "#DDDDDD");
                        }
                    }
                } else {
                    if (listaModulosDisponibles.get(cont).getIdModulo() <= 50) {
                        mapHorarioManana.put(listaModulosDisponibles.get(cont).getModulo(), "#DDDDDD");
                    } else {
                        mapHorarioTarde.put(listaModulosDisponibles.get(cont).getModulo(), "#DDDDDD");
                    }
                }
            }
        }

        RequestContext context = RequestContext.getCurrentInstance();

        context.execute(
                "PF('dlgHoras').show();");

    }

    public void cargarCitasFecha(Date fecha) {
        horaCita = "";
        mostrarDtCitasFecha = true;
        listarCitasFecha = citaService.cargarCitas("WHERE cita_modulo.fecha = '" + sdf.format(fecha) + "'");
    }

    // Método que va comparando las fechas del Datatable
    public int compararFechas(Date d1, Date d2) {
        int resultado;
        resultado = d1.compareTo(d2);
        return resultado;
    }

    public void resetearFiltrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }

//  Método que carga el cliente al introducir el dni en una nueva cita
    public void obtenerCliente(String dni) {
        listaPrueba = new ArrayList();
        listaPrueba = clienteService.cargarCitaCliente(dni);
        idClienteCita = 0;
        idClienteCita = clienteService.obtenerIdCliente(dni);
    }

    public void obtenerHora(String hora) {
        this.horaCita = hora;
    }

    public void cargarTratamientosLesion(int idLesion) {
        duracionTratamientoLesion = 0;
        precioTratamientoLesion = 0;
        listaTratamientosLesion = new ArrayList();
        listaTratamientosLesion = tratamientoService.cargarTratamientos(" JOIN "
                + " lesiones_tratamientos ON tratamientos.id_tratamiento = lesiones_tratamientos.id_tratamiento "
                + " WHERE id_lesion = " + idLesion);
    }

    public void cargarCarateristicasTratamientoLesion(int idTratamiento) {
        tratamiento = new Tratamiento();
        duracionTratamientoLesion = 0;
        precioTratamientoLesion = 0;
        tratamiento = tratamientoService.cargarUnTratamiento(" WHERE id_tratamiento = " + idTratamientoLesion);
        duracionTratamientoLesion = tratamiento.getDuracion();
        precioTratamientoLesion = tratamiento.getPrecio();
    }

    // Método que permite el filtrado de fechas en el datatable principal de Gestión de Citas
    public boolean filtrarFechas(Object value, Object filter, Locale locale) {
        if (filter == null) {
            return true;
        }

        if (value == null) {
            return false;
        }

        Date dt2 = (Date) filter;

        String date2 = sdf.format(dt2);
        boolean status = date2.equals(value.toString());
        return status;
    }

    //Este método va a permitir mostrar en el datatable de citas, si vamos a mostrar las citas del día o las pendientes
    public void controlarCitas() {
        if (verTodas == true) {
            listarCitas = citaService.cargarCitas("WHERE cita_modulo.fecha > CURDATE()");
        } else {
            listarCitas = citaService.cargarCitas("WHERE cita_modulo.fecha = CURDATE()");
        }

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dtCitas').clearFilters()");
    }

    public void atenderCitaPrev(Cita c) {
        inicializarVariables();
        idCita = c.getIdCita();

        fechaCita = c.getFecha();
        horaCita = c.getModulo();
        nombreCliente = c.getNombreCliente();
        apellidosCliente = c.getApellidos();

        idClienteCita = c.getIdCliente();
        dniClienteCita = clienteService.obtenerDniCliente(idClienteCita);
        obtenerCliente(dniClienteCita);

        tratamiento = tratamientoService.cargarUnTratamiento(" WHERE id_tratamiento = " + c.getIdTratamiento());
        precioTratamientoLesion = tratamiento.getPrecio();
        duracionTratamientoLesion = tratamiento.getDuracion();
        nombreTratamiento = tratamiento.getNombre();

        idLesionCita = c.getIdLesion();

        lesion = new Lesion();
        lesion = lesionService.cargarUnaLesion(" WHERE id_lesion = " + c.getIdLesion());
        nombreLesion = lesion.getNombre();

        listaComplementosDisponibles = new ArrayList();
        listaComplementosDisponibles = complementoService.cargarComplementos(" JOIN lesiones_complementos "
                + " ON complementos.id_complemento = lesiones_complementos.id_complemento "
                + " WHERE id_lesion = " + c.getIdLesion());

        verHistorial(idClienteCita);
    }

    //Método que es ejecutado cuando se atiende una cita
    public void atenderCita() {
        cita = new Cita();
        cita.setIdCita(idCita);
        cita.setIdLesion(idLesionCita);
        cita.setGrado(gradoLesion);
        cita.setEstado(estadoLesion);
        cita.setObservaciones(observacionesCita);
        cita.setAtendida(true);
        cita.setIdZona(idZonaCita);
        cita.setIdMusculo(idMusculoCita);
        citaService.completarCita(cita, listaComplementosSeleccionados, gestionSesionView.getEmpleado().getIdUser());

        listarCitas = citaService.cargarCitas("WHERE cita_modulo.fecha = CURDATE()");
        exito = citaService.isSuccess();
    }

    //Método llamada ajax, solo se utiliza cuando una lesión es incierta al ser detectada, en el atender citas
    public void cargarComplementosLesion() {

        lesion = new Lesion();
        lesion = lesionService.cargarUnaLesion(" WHERE id_lesion = " + idLesionCita);
        nombreLesion = lesion.getNombre();

        listaComplementosDisponibles = new ArrayList();
        listaComplementosSeleccionados = new ArrayList();
        listaComplementosDisponibles = complementoService.cargarComplementos(" JOIN lesiones_complementos "
                + " ON complementos.id_complemento = lesiones_complementos.id_complemento "
                + " WHERE id_lesion = " + idLesionCita);
    }

    //Método que genera el PDF de la cita
    public void crearPDF() {

        FacesContext context = FacesContext.getCurrentInstance();
        String estadoLesionPdf = "";

        //La única forma de obtener la URL del músculo es por medio de una consulta   
        Musculo musc = new Musculo();
        musc = musculoService.cargarUnMusculo(idMusculoCita);

        switch (estadoLesion) {
            case "0":
                estadoLesionPdf = rb.getString("agudo");
                break;
            case "1":
                estadoLesionPdf = rb.getString("cronico");
                break;
            default:
                break;
        }

        // Se crea el documento
        Document documento = new Document(PageSize.A4, 35, 30, 50, 50);

        try {
            // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream("citas" + idCita + ".pdf");

            // Se asocia el documento al OutputStream y se indica que el espaciado entre
            // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
            PdfWriter.getInstance(documento, ficheroPdf);

            // Se abre el documento.
            documento.open();

            // Creamos el título
            Chunk title = new Chunk(rb.getString("tituloClinica"));
            Font font = new Font();
            font.setStyle(Font.UNDERLINE);
            title.setFont(font);
            title.setBackground(BaseColor.YELLOW);
            title.getFont().setSize(20);

            Paragraph paragraph = new Paragraph();
            paragraph.add(title);
            paragraph.setAlignment(ALIGN_CENTER);
            documento.add(paragraph);

            // Añadimos un salto de línea
            documento.add(Chunk.NEWLINE);

            // Creación de la tabla Cita
            PdfPTable tablaCita = new PdfPTable(2);

            PdfPCell celda21 = new PdfPCell();
            celda21.setColspan(2);
            celda21.setBackgroundColor(BaseColor.DARK_GRAY);
            celda21.setHorizontalAlignment(ALIGN_CENTER);
            celda21.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase21 = new Phrase(rb.getString("citaM"));
            phrase21.getFont().setStyle(Font.BOLD);
            phrase21.getFont().setColor(BaseColor.YELLOW);
            celda21.addElement(phrase21);
            tablaCita.addCell(celda21);

            PdfPCell celda22 = new PdfPCell();
            celda22.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase22 = new Phrase(rb.getString("clienteM"));
            phrase22.getFont().setStyle(Font.BOLD);
            phrase22.getFont().setSize(10);
            celda22.addElement(phrase22);
            tablaCita.addCell(celda22);

            PdfPCell celda23 = new PdfPCell();
            celda23.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase23 = new Phrase(nombreCliente + " " + apellidosCliente);
            phrase23.getFont().setSize(10);
            celda23.addElement(phrase23);
            tablaCita.addCell(celda23);

            PdfPCell celda24 = new PdfPCell();
            celda24.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase24 = new Phrase(rb.getString("fechaM"));
            phrase24.getFont().setStyle(Font.BOLD);
            phrase24.getFont().setSize(10);
            celda24.addElement(phrase24);
            tablaCita.addCell(celda24);

            PdfPCell celda25 = new PdfPCell();
            celda25.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase25 = new Phrase(sdfEs.format(fechaCita));
            phrase25.getFont().setSize(10);
            celda25.addElement(phrase25);
            tablaCita.addCell(celda25);

            PdfPCell celda26 = new PdfPCell();
            celda22.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase26 = new Phrase(rb.getString("horaM"));
            phrase26.getFont().setStyle(Font.BOLD);
            phrase26.getFont().setSize(10);
            celda26.addElement(phrase26);
            tablaCita.addCell(celda26);

            PdfPCell celda27 = new PdfPCell();
            celda27.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase27 = new Phrase(horaCita);
            phrase27.getFont().setSize(10);
            celda27.addElement(phrase27);
            tablaCita.addCell(celda27);

            documento.add(tablaCita);

            // Añadimos un salto de línea
            documento.add(Chunk.NEWLINE);

            // Creación de la tabla Lesión
            PdfPTable tablaLesion = new PdfPTable(3);

            PdfPCell celda1 = new PdfPCell();
            celda1.setColspan(3);
            celda1.setBackgroundColor(BaseColor.DARK_GRAY);
            celda1.setHorizontalAlignment(ALIGN_CENTER);
            celda1.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase1 = new Phrase(rb.getString("lesionM"));
            phrase1.getFont().setStyle(Font.BOLD);
            phrase1.getFont().setColor(BaseColor.YELLOW);
            celda1.addElement(phrase1);
            tablaLesion.addCell(celda1);

            PdfPCell celda2 = new PdfPCell();
            celda2.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase2 = new Phrase(rb.getString("nombreM"));
            phrase2.getFont().setStyle(Font.BOLD);
            phrase2.getFont().setSize(10);
            celda2.addElement(phrase2);
            tablaLesion.addCell(celda2);

            PdfPCell celda3 = new PdfPCell();
            celda3.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase3 = new Phrase(nombreLesion);
            phrase3.getFont().setSize(10);
            celda3.addElement(phrase3);
            tablaLesion.addCell(celda3);

            PdfPCell celda30 = new PdfPCell();
            celda30.setRowspan(5);

            //Obtenemos la ruta de la aplicacion y el separador del SO
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            String ruta = ctx.getRealPath("/");

            String separador = System.getProperty("file.separator");

            // direccion de la ruta donde se ubicaran las imagenes de los informes
            String rutaImagen = ruta + "resources" + separador + musc.getUrl_imagen();

            //  Image foto = Image.getInstance(path + musc.getUrl_imagen());
            Image foto = Image.getInstance(rutaImagen);
            foto.scaleToFit(80, 80);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            celda30.addElement(foto);
            tablaLesion.addCell(celda30);

            PdfPCell celda4 = new PdfPCell();
            celda4.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase4 = new Phrase(rb.getString("gradoM"));
            phrase4.getFont().setStyle(Font.BOLD);
            phrase4.getFont().setSize(10);
            celda4.addElement(phrase4);
            tablaLesion.addCell(celda4);

            PdfPCell celda5 = new PdfPCell();
            celda5.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase5 = new Phrase(gradoLesion);
            phrase5.getFont().setSize(10);
            celda5.addElement(phrase5);
            tablaLesion.addCell(celda5);

            PdfPCell celda6 = new PdfPCell();
            celda6.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase6 = new Phrase(rb.getString("estadoM"));
            phrase6.getFont().setStyle(Font.BOLD);
            phrase6.getFont().setSize(10);
            celda6.addElement(phrase6);
            tablaLesion.addCell(celda6);

            PdfPCell celda7 = new PdfPCell();
            celda7.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase7 = new Phrase(estadoLesionPdf);
            phrase7.getFont().setSize(10);
            celda7.addElement(phrase7);
            tablaLesion.addCell(celda7);

            PdfPCell celda33 = new PdfPCell();
            celda33.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase33 = new Phrase(rb.getString("zonaM"));
            phrase33.getFont().setStyle(Font.BOLD);
            phrase33.getFont().setSize(10);
            celda33.addElement(phrase33);
            tablaLesion.addCell(celda33);

            PdfPCell celda34 = new PdfPCell();
            celda34.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase34 = new Phrase(musc.getNombreZona());
            phrase34.getFont().setSize(10);
            celda34.addElement(phrase34);
            tablaLesion.addCell(celda34);

            PdfPCell celda31 = new PdfPCell();
            celda31.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase31 = new Phrase(rb.getString("musculoM"));
            phrase31.getFont().setStyle(Font.BOLD);
            phrase31.getFont().setSize(10);
            celda31.addElement(phrase31);
            tablaLesion.addCell(celda31);

            PdfPCell celda32 = new PdfPCell();
            celda31.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase32 = new Phrase(musc.getNombre());
            phrase32.getFont().setSize(10);
            celda32.addElement(phrase32);
            tablaLesion.addCell(celda32);

            documento.add(tablaLesion);

            // Añadimos un salto de línea
            documento.add(Chunk.NEWLINE);

            // Creación de la tabla Tratamiento
            PdfPTable tablaTratamiento = new PdfPTable(2);

            PdfPCell celda8 = new PdfPCell();
            celda8.setColspan(2);
            celda8.setBackgroundColor(BaseColor.DARK_GRAY);
            Phrase phrase8 = new Phrase(rb.getString("tratamientoM"));
            phrase8.getFont().setStyle(Font.BOLD);
            phrase8.getFont().setColor(BaseColor.YELLOW);
            celda8.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda8.setVerticalAlignment(Element.ALIGN_CENTER);
            celda8.addElement(phrase8);
            tablaTratamiento.addCell(celda8);

            PdfPCell celda9 = new PdfPCell();
            celda9.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase9 = new Phrase(rb.getString("nombreM"));
            phrase9.getFont().setStyle(Font.BOLD);
            phrase9.getFont().setSize(10);
            celda9.addElement(phrase9);
            tablaTratamiento.addCell(celda9);

            PdfPCell celda10 = new PdfPCell();
            celda10.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase10 = new Phrase(nombreTratamiento);
            phrase10.getFont().setSize(10);
            celda10.addElement(phrase10);
            tablaTratamiento.addCell(celda10);

            PdfPCell celda11 = new PdfPCell();
            celda11.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase11 = new Phrase(rb.getString("duracionM"));
            phrase11.getFont().setStyle(Font.BOLD);
            phrase11.getFont().setSize(10);
            celda11.addElement(phrase11);
            tablaTratamiento.addCell(celda11);

            PdfPCell celda12 = new PdfPCell();
            celda12.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase12 = new Phrase(String.valueOf(duracionTratamientoLesion + " min"));
            phrase12.getFont().setSize(10);
            celda12.addElement(phrase12);
            tablaTratamiento.addCell(celda12);

            PdfPCell celda13 = new PdfPCell();
            celda13.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase13 = new Phrase(rb.getString("precioM"));
            phrase13.getFont().setStyle(Font.BOLD);
            phrase13.getFont().setSize(10);
            celda13.addElement(phrase13);
            tablaTratamiento.addCell(celda13);

            PdfPCell celda14 = new PdfPCell();
            celda14.setVerticalAlignment(ALIGN_CENTER);
            Phrase phrase14 = new Phrase(String.valueOf(precioTratamientoLesion + " €"));
            phrase14.getFont().setSize(10);
            celda14.addElement(phrase14);
            tablaTratamiento.addCell(celda14);

            documento.add(tablaTratamiento);

            // Añadimos un salto de línea
            documento.add(Chunk.NEWLINE);

            // Creación de la tabla Complementos
            if (!listaComplementosSeleccionados.isEmpty()) {

                PdfPTable tablaComplementos = new PdfPTable(3);

                PdfPCell celda15 = new PdfPCell();
                celda15.setColspan(3);
                celda15.setBackgroundColor(BaseColor.DARK_GRAY);
                Phrase phrase15 = new Phrase(rb.getString("complementoM"));
                phrase15.getFont().setStyle(Font.BOLD);
                phrase15.getFont().setColor(BaseColor.YELLOW);
                celda15.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda15.setVerticalAlignment(Element.ALIGN_CENTER);
                celda15.addElement(phrase15);
                tablaComplementos.addCell(celda15);

                PdfPCell celda16 = new PdfPCell();
                celda16.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda16.setVerticalAlignment(ALIGN_CENTER);
                celda16.setBackgroundColor(BaseColor.LIGHT_GRAY);
                Phrase phrase16 = new Phrase(rb.getString("nombreM"));
                phrase16.getFont().setStyle(Font.BOLD);
                phrase16.getFont().setSize(10);
                celda16.addElement(phrase16);
                tablaComplementos.addCell(celda16);

                PdfPCell celda17 = new PdfPCell();
                celda17.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda17.setVerticalAlignment(ALIGN_CENTER);
                celda17.setBackgroundColor(BaseColor.LIGHT_GRAY);
                Phrase phrase17 = new Phrase(rb.getString("tipoM"));
                phrase17.getFont().setStyle(Font.BOLD);
                phrase17.getFont().setSize(10);
                celda17.addElement(phrase17);
                tablaComplementos.addCell(celda17);

                PdfPCell celda18 = new PdfPCell();
                celda18.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda18.setVerticalAlignment(ALIGN_CENTER);
                celda18.setBackgroundColor(BaseColor.LIGHT_GRAY);
                Phrase phrase18 = new Phrase(rb.getString("descripcionM"));
                phrase18.getFont().setStyle(Font.BOLD);
                phrase18.getFont().setSize(10);
                celda18.addElement(phrase18);
                tablaComplementos.addCell(celda18);

                Phrase phm1 = null;
                Phrase phm2 = null;
                Phrase phm3 = null;
                for (Complemento comp : listaComplementosSeleccionados) {

                    phm1 = new Phrase(comp.getNombre());
                    phm1.getFont().setSize(10);
                    tablaComplementos.addCell(phm1);

                    phm2 = new Phrase(comp.getTipo());
                    phm2.getFont().setSize(10);
                    tablaComplementos.addCell(phm2);

                    phm3 = new Phrase(comp.getDescripcion());
                    phm3.getFont().setSize(10);
                    tablaComplementos.addCell(phm3);

                }

                documento.add(tablaComplementos);

                // Añadimos un salto de línea
                documento.add(Chunk.NEWLINE);

            }

            // Creación de la tabla Observaciones
            if (!observacionesCita.equals("")) {

                PdfPTable tablaObservaciones = new PdfPTable(1);

                PdfPCell celda19 = new PdfPCell();
                celda19.setBackgroundColor(BaseColor.DARK_GRAY);
                Phrase phrase19 = new Phrase(rb.getString("observacionesM"));
                phrase19.getFont().setStyle(Font.BOLD);
                phrase19.getFont().setColor(BaseColor.YELLOW);
                celda19.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda19.setVerticalAlignment(Element.ALIGN_CENTER);
                celda19.addElement(phrase19);
                tablaObservaciones.addCell(celda19);

                PdfPCell celda20 = new PdfPCell();
                celda20.setVerticalAlignment(ALIGN_CENTER);
                Phrase phrase20 = new Phrase(observacionesCita);
                phrase20.getFont().setSize(10);
                celda20.addElement(phrase20);
                tablaObservaciones.addCell(celda20);

                documento.add(tablaObservaciones);
            }

            // Cerramos el documento
            documento.close();

            fechaActu = new Date();
            fechaActualString = sdf2.format(fechaActu);
            auditoria = new Auditoria(gestionSesionView.getEmpleado().getIdUser(), rb.getString("generarPdf"), rb.getString("generacionPdf") + " " + idCita, fechaActualString);
            auditoriaService = new AuditoriaService();
            auditoriaService.crearAuditoria(auditoria);

            try {
                // Abrimos el fichero en el escritorio
                File file = new File("citas" + idCita + ".pdf");
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                }
            } catch (IOException e) {
            }

        } catch (FileNotFoundException | DocumentException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("cerrarArchivo")));
            Logger.getLogger(GestionCitasView.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(GestionCitasView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Método que nos permite ver el historial del cliente desde la cita
    public void verHistorial(int idClienteC) {
        listaHistorial = new ArrayList();
        listaHistorial = citaService.cargarHistorial(idClienteC, "LIMIT 3");
    }

    //Método que añade un complemento a una cita
    public void addCitaComplemento(Complemento comp) {
        listaComplementosSeleccionados.add(comp);
        listaComplementosDisponibles.remove(comp);
    }

    //Método que elimina un complemento de una cita
    public void deleteCitaComplemento(Complemento comp) {
        listaComplementosSeleccionados.remove(comp);
        listaComplementosDisponibles.add(comp);
    }

    //Método que carga los músculos según la zona seleccionada en el selectOneMenu de atender citas
    public void cargarMusculosZona() {
        musculoService = new MusculoService();
        listaMusculos = musculoService.cargarMusculos("WHERE id_zona = " + idZonaCita);
    }

//Getters & Setters
    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public CitaService getCitaService() {
        return citaService;
    }

    public void setCitaService(CitaService citaService) {
        this.citaService = citaService;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public int getIdLesionCita() {
        return idLesionCita;
    }

    public void setIdLesionCita(int idLesionCita) {
        this.idLesionCita = idLesionCita;
    }

    public ArrayList<Cita> getListarCitas() {
        return listarCitas;
    }

    public void setListarCitas(ArrayList<Cita> listarCitas) {
        this.listarCitas = listarCitas;
    }

    public ArrayList<Cita> getListarCitasFecha() {
        return listarCitasFecha;
    }

    public void setListarCitasFecha(ArrayList<Cita> listarCitasFecha) {
        this.listarCitasFecha = listarCitasFecha;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public boolean isMostrarDtCitasFecha() {
        return mostrarDtCitasFecha;
    }

    public void setMostrarDtCitasFecha(boolean mostrarDtCitasFecha) {
        this.mostrarDtCitasFecha = mostrarDtCitasFecha;
    }

    public ArrayList<Lesion> getListaNombresLesiones() {
        return listaNombresLesiones;
    }

    public void setListaNombresLesiones(ArrayList<Lesion> listaNombresLesiones) {
        this.listaNombresLesiones = listaNombresLesiones;
    }

    public LesionService getLesionService() {
        return lesionService;
    }

    public void setLesionService(LesionService lesionService) {
        this.lesionService = lesionService;
    }

    public TratamientoService getTratamientoService() {
        return tratamientoService;
    }

    public void setTratamientoService(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    public ArrayList<Tratamiento> getListaTratamientosLesion() {
        return listaTratamientosLesion;
    }

    public void setListaTratamientosLesion(ArrayList<Tratamiento> listaTratamientosLesion) {
        this.listaTratamientosLesion = listaTratamientosLesion;
    }

    public int getIdTratamientoLesion() {
        return idTratamientoLesion;
    }

    public void setIdTratamientoLesion(int idTratamientoLesion) {
        this.idTratamientoLesion = idTratamientoLesion;
    }

    public double getPrecioTratamientoLesion() {
        return precioTratamientoLesion;
    }

    public void setPrecioTratamientoLesion(double precioTratamientoLesion) {
        this.precioTratamientoLesion = precioTratamientoLesion;
    }

    public int getDuracionTratamientoLesion() {
        return duracionTratamientoLesion;
    }

    public void setDuracionTratamientoLesion(int duracionTratamientoLesion) {
        this.duracionTratamientoLesion = duracionTratamientoLesion;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getDniClienteCita() {
        return dniClienteCita;
    }

    public void setDniClienteCita(String dniClienteCita) {
        this.dniClienteCita = dniClienteCita;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ArrayList<Cliente> getListaPrueba() {
        return listaPrueba;
    }

    public void setListaPrueba(ArrayList<Cliente> listaPrueba) {
        this.listaPrueba = listaPrueba;
    }

    public int getIdClienteCita() {
        return idClienteCita;
    }

    public void setIdClienteCita(int idClienteCita) {
        this.idClienteCita = idClienteCita;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public boolean isNuevaGestion() {
        return nuevaGestion;
    }

    public void setNuevaGestion(boolean nuevaGestion) {
        this.nuevaGestion = nuevaGestion;
    }

    public Map<String, String> getMapHorarioManana() {
        return mapHorarioManana;
    }

    public void setMapHorarioManana(Map<String, String> mapHorarioManana) {
        this.mapHorarioManana = mapHorarioManana;
    }

    public Map<String, String> getMapHorarioTarde() {
        return mapHorarioTarde;
    }

    public void setMapHorarioTarde(Map<String, String> mapHorarioTarde) {
        this.mapHorarioTarde = mapHorarioTarde;
    }

    public boolean isVerTodas() {
        return verTodas;
    }

    public void setVerTodas(boolean verTodas) {
        this.verTodas = verTodas;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public ArrayList<Modulo> getListaHorarioManana() {
        return listaHorarioManana;
    }

    public void setListaHorarioManana(ArrayList<Modulo> listaHorarioManana) {
        this.listaHorarioManana = listaHorarioManana;
    }

    public ArrayList<Modulo> getListaHorarioTarde() {
        return listaHorarioTarde;
    }

    public void setListaHorarioTarde(ArrayList<Modulo> listaHorarioTarde) {
        this.listaHorarioTarde = listaHorarioTarde;
    }

    public ModuloService getModuloService() {
        return moduloService;
    }

    public void setModuloService(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    public ArrayList<CitaModulo> getListaModulosOcupados() {
        return listaModulosOcupados;
    }

    public void setListaModulosOcupados(ArrayList<CitaModulo> listaModulosOcupados) {
        this.listaModulosOcupados = listaModulosOcupados;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getNombreLesion() {
        return nombreLesion;
    }

    public void setNombreLesion(String nombreLesion) {
        this.nombreLesion = nombreLesion;
    }

    public Lesion getLesion() {
        return lesion;
    }

    public void setLesion(Lesion lesion) {
        this.lesion = lesion;
    }

    public String getObservacionesCita() {
        return observacionesCita;
    }

    public void setObservacionesCita(String observacionesCita) {
        this.observacionesCita = observacionesCita;
    }

    public CitaModuloService getCitaModuloService() {
        return citaModuloService;
    }

    public void setCitaModuloService(CitaModuloService citaModuloService) {
        this.citaModuloService = citaModuloService;
    }

    public ComplementoService getComplementoService() {
        return complementoService;
    }

    public void setComplementoService(ComplementoService complementoService) {
        this.complementoService = complementoService;
    }

    public String getGradoLesion() {
        return gradoLesion;
    }

    public void setGradoLesion(String gradoLesion) {
        this.gradoLesion = gradoLesion;
    }

    public String getEstadoLesion() {
        return estadoLesion;
    }

    public void setEstadoLesion(String estadoLesion) {
        this.estadoLesion = estadoLesion;
    }

    public ArrayList<Cita> getListaHistorial() {
        return listaHistorial;
    }

    public void setListaHistorial(ArrayList<Cita> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Date getFechaTomorrow() {
        return fechaTomorrow;
    }

    public void setFechaTomorrow(Date fechaTomorrow) {
        this.fechaTomorrow = fechaTomorrow;
    }

    public int getIdModuloCita() {
        return idModuloCita;
    }

    public void setIdModuloCita(int idModuloCita) {
        this.idModuloCita = idModuloCita;
    }

    public ArrayList<Modulo> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(ArrayList<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public Modulo getModuloElegido() {
        return moduloElegido;
    }

    public void setModuloElegido(Modulo moduloElegido) {
        this.moduloElegido = moduloElegido;
    }

    public ArrayList<CitaModulo> getListaModulosDisponibles() {
        return listaModulosDisponibles;
    }

    public void setListaModulosDisponibles(ArrayList<CitaModulo> listaModulosDisponibles) {
        this.listaModulosDisponibles = listaModulosDisponibles;
    }

    public int getIdZonaCita() {
        return idZonaCita;
    }

    public void setIdZonaCita(int idZonaCita) {
        this.idZonaCita = idZonaCita;
    }

    public int getIdMusculoCita() {
        return idMusculoCita;
    }

    public void setIdMusculoCita(int idMusculoCita) {
        this.idMusculoCita = idMusculoCita;
    }

    public ZonaService getZonaService() {
        return zonaService;
    }

    public void setZonaService(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    public MusculoService getMusculoService() {
        return musculoService;
    }

    public void setMusculoService(MusculoService musculoService) {
        this.musculoService = musculoService;
    }

    public ArrayList<Zona> getListaZonas() {
        return listaZonas;
    }

    public void setListaZonas(ArrayList<Zona> listaZonas) {
        this.listaZonas = listaZonas;
    }

    public ArrayList<Musculo> getListaMusculos() {
        return listaMusculos;
    }

    public void setListaMusculos(ArrayList<Musculo> listaMusculos) {
        this.listaMusculos = listaMusculos;
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

    public ArrayList<Tratamiento> getListaTratamientosCompleta() {
        return listaTratamientosCompleta;
    }

    public void setListaTratamientosCompleta(ArrayList<Tratamiento> listaTratamientosCompleta) {
        this.listaTratamientosCompleta = listaTratamientosCompleta;
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

    public int getModulos() {
        return modulos;
    }

    public void setModulos(int modulos) {
        this.modulos = modulos;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }

    public SimpleDateFormat getSdfEs() {
        return sdfEs;
    }

    public void setSdfEs(SimpleDateFormat sdfEs) {
        this.sdfEs = sdfEs;
    }

    public GestionSesionView getGestionSesionView() {
        return gestionSesionView;
    }

    public void setGestionSesionView(GestionSesionView gestionSesionView) {
        this.gestionSesionView = gestionSesionView;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }

    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    public Date getFechaActu() {
        return fechaActu;
    }

    public void setFechaActu(Date fechaActu) {
        this.fechaActu = fechaActu;
    }

    public SimpleDateFormat getSdf2() {
        return sdf2;
    }

    public void setSdf2(SimpleDateFormat sdf2) {
        this.sdf2 = sdf2;
    }

    public String getFechaActualString() {
        return fechaActualString;
    }

    public void setFechaActualString(String fechaActualString) {
        this.fechaActualString = fechaActualString;
    }

}
