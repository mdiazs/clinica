/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinica.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.mycompany.clinica.models.Auditoria;
import com.mycompany.clinica.models.Cliente;

/**
 *
 * @author mads
 */
public class ClienteService extends Services {

    //Propiedades
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Cliente> clienteCita;
    private boolean success;
    
    private Auditoria auditoria;
    private AuditoriaService auditoriaService;
    
    private Date fechaActual;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String fechaActualString;

    private final ResourceBundle rb = ResourceBundle.getBundle("mensajes");

    //Método que carga todos los cliente
    public ArrayList<Cliente> cargarClientes() {
        PreparedStatement stmt = null;
        ResultSet rset;
        try {
            listaClientes = new ArrayList();
            conectar();
            String sql = "SELECT id_cliente, dni, nombre, apellidos, localidad, direccion, telefono FROM clientes";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    Cliente c = new Cliente();
                    c.setIdCliente(rset.getInt("id_cliente"));
                    c.setDni(rset.getString("dni"));
                    c.setNombre(rset.getString("nombre"));
                    c.setApellidos(rset.getString("apellidos"));
                    c.setLocalidad(rset.getString("localidad"));
                    c.setDireccion(rset.getString("direccion"));
                    c.setTelefono(rset.getString("telefono"));
                    listaClientes.add(c);
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaClientes;
    }

    //Método que prepara la inserción de un cliente en la BD
    public void crearCliente(Cliente c, int idUsuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        success = false;

        boolean duplicado = false;

        //Compruebo si el DNI del cliente esta duplicado
        ArrayList<Cliente> listaCli = cargarClientes();
        for (Cliente cliente : listaCli) {
            if (c.getDni().toLowerCase().equals(cliente.getDni().toLowerCase())) {
                duplicado = true;
                break;
            }
        }

        if (duplicado) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("dniDuplicado")));
        } else {
            try {
                conectar();

                String sql = " INSERT INTO clientes (dni, nombre, apellidos, localidad, direccion, telefono) "
                        + " VALUES ('" + c.getDni() + "', '" + c.getNombre()
                        + "','" + c.getApellidos() + "','" + c.getLocalidad()
                        + "','" + c.getDireccion() + "','" + c.getTelefono() + "')";

                stmt = conn.prepareStatement(sql);
                int numFilas = stmt.executeUpdate(sql);
                if (numFilas == 1) {
                    success = true;
                    fechaActual = new Date();
                    fechaActualString = sdf.format(fechaActual);
                    auditoria = new Auditoria(idUsuario, rb.getString("crearCliente"), rb.getString("creacionCliente") + " " + c.getDni(), fechaActualString);
                    auditoriaService = new AuditoriaService();
                    auditoriaService.crearAuditoria(auditoria);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("clienteAnadido")));
                }
            } catch (SQLException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
                }
                desconectar();
            }
        }

    }

    //Método que prepara la edición un cliente en la BD
    public void editarCliente(Cliente c, int idUsuario) {

        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;
        success = false;

        try {
            conectar();

            String sql = " UPDATE clientes SET localidad = '" + c.getLocalidad()
                    + "', direccion = '" + c.getDireccion() + "', telefono = '" + c.getTelefono()
                    + "' WHERE id_cliente = " + c.getIdCliente();

            stmt = conn.prepareStatement(sql);
            int numFilas = stmt.executeUpdate(sql);
            if (numFilas == 1) {
                success = true;
                fechaActual = new Date();
                fechaActualString = sdf.format(fechaActual);
                auditoria = new Auditoria(idUsuario, rb.getString("editarCliente"), rb.getString("edicionCliente") + " " + c.getDni(), fechaActualString);
                auditoriaService = new AuditoriaService();
                auditoriaService.crearAuditoria(auditoria);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("clienteEditado")));
            }
        } catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
    }

    //Método que prepara la eliminación un cliente de la BD
    public void borrarCliente(Cliente cliente, int idUsuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        PreparedStatement stmt = null;

        try {
            conectar();
            String sql = " DELETE FROM clientes WHERE id_cliente = '" + cliente.getIdCliente() + "'";

            stmt = conn.prepareStatement(sql);
            int numFilas = stmt.executeUpdate(sql);
            if (numFilas == 1) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("info"), rb.getString("clienteEliminado")));
                fechaActual = new Date();
                fechaActualString = sdf.format(fechaActual);
                auditoria = new Auditoria(idUsuario, rb.getString("eliminarCliente"), rb.getString("eliminacionClienteAuditorias") + " " + cliente.getDni(), fechaActualString);
                auditoriaService = new AuditoriaService();
                auditoriaService.crearAuditoria(auditoria);
            }
        } catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorTransaction")));
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, rb.getString("error"), rb.getString("errorGeneral")));
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
    }

    public ArrayList<Cliente> cargarCitaCliente(String dniCliente) {
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            clienteCita = new ArrayList();
            Cliente c = new Cliente();

            conectar();

            String sql = " SELECT clientes.id_cliente, clientes.dni, "
                    + " clientes.nombre, "
                    + " clientes.apellidos, clientes.localidad, "
                    + " clientes.direccion, clientes.telefono "
                    + " FROM clientes WHERE dni = '" + dniCliente + "'";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    c.setIdCliente(rset.getInt("id_cliente"));
                    c.setDni(rset.getString("dni"));
                    c.setNombre(rset.getString("nombre"));
                    c.setApellidos(rset.getString("apellidos"));
                    c.setLocalidad(rset.getString("localidad"));
                    c.setDireccion(rset.getString("direccion"));
                    c.setTelefono(rset.getString("telefono"));
                    clienteCita.add(c);
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return clienteCita;
    }

    public ArrayList<String> cargarLocalidadCliente() {
        ArrayList<String> result = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();

            String sql = " SELECT DISTINCT clientes.localidad "
                    + " FROM clientes ";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    result.add(rset.getString("localidad"));
                }
                rset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return result;
    }

    public int obtenerIdCliente(String dni) {
        int idClienteCita;
        idClienteCita = 0;
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT clientes.id_cliente FROM clientes "
                    + " WHERE clientes.dni = '" + dni + "'";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    idClienteCita = rset.getInt(1);
                }
                rset.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return idClienteCita;
    }

    public String obtenerDniCliente(int idCliente) {
        String dniClienteCita = "";
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT clientes.dni FROM clientes "
                    + " WHERE clientes.id_cliente = " + idCliente;
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    dniClienteCita = rset.getString(1);
                }
                rset.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return dniClienteCita;
    }

    public Cliente obtenerUnCliente(int idCliente) {
        Cliente c = null;
        PreparedStatement stmt = null;
        ResultSet rset;

        try {
            conectar();
            String sql = " SELECT clientes.nombre, clientes.apellidos FROM clientes "
                    + " WHERE clientes.id_cliente = " + idCliente;
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            if (rset != null) {
                while (rset.next()) {
                    c = new Cliente();
                    c.setNombre(rset.getString("nombre"));
                    c.setApellidos(rset.getString("apellidos"));
                }
                rset.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            desconectar();
        }
        return c;
    }

    //Getters & Setters
    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ArrayList<Cliente> getClienteCita() {
        return clienteCita;
    }

    public void setClienteCita(ArrayList<Cliente> clienteCita) {
        this.clienteCita = clienteCita;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getFechaActualString() {
        return fechaActualString;
    }

    public void setFechaActualString(String fechaActualString) {
        this.fechaActualString = fechaActualString;
    }

}
