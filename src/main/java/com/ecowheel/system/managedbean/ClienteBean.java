package com.ecowheel.system.managedbean;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import com.ecowheel.system.model.Cliente;
import com.ecowheel.system.model.ClienteHome;
import com.ecowheel.system.util.Contains;

/**
 * 
 * @author David Andres Betancourth Botero
 * 
 */
@ManagedBean(name = "cliente")
@ViewScoped
public class ClienteBean {

	private static final Logger log = Logger.getLogger(ClienteBean.class);
	private ResourceBundle bundle = ResourceBundle.getBundle("com.ecowheel.system.util.bundle");
	private Cliente cliente;
	private List<Cliente> listClientes;

	@EJB
	private ClienteHome clienteHome;

	public List<Cliente> getListClientes() {
		return listClientes;
	}

	public void setListClientes(List<Cliente> listClientes) {
		this.listClientes = listClientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteBean() {
	}

	@PostConstruct
	public void init() {
		inicializar();
	}

	public void inicializar() {
		this.cliente = new Cliente();
		llenarClientes();
	}

	public void llenarClientes() {
		this.listClientes = new ArrayList<Cliente>();
		try {
			this.listClientes = this.clienteHome.findAll();
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			log.error(bundle.getString("IODErr4"), e);
		}
	}

	public void eliminar(Cliente cliente) {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.clienteHome.remove(cliente);

			enunciado.append(bundle.getString("label3"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label19"));
			enunciado.append(Contains.VACIO);
			enunciado.append(cliente.getStrnit());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label3"));
		} catch (Exception e) {

			enunciado.append(bundle.getString("IODErr6"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label22"));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr6"), e);
		}

		llenarClientes();
	}

	public void editar(RowEditEvent event) {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.clienteHome.merge((Cliente) event.getObject());

			enunciado.append(bundle.getString("label17"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label19"));
			enunciado.append(Contains.VACIO);
			enunciado.append(((Cliente) event.getObject()).getStrnit());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			enunciado.append(bundle.getString("IODErr4"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("IODErr5"));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr4"), e);
		}
		
		inicializar();
	}

	public void guardar() {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.clienteHome.persist(this.cliente);

			enunciado.append(bundle.getString("label17"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label19"));
			enunciado.append(Contains.VACIO);
			enunciado.append(cliente.getStrnit());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			enunciado.append(bundle.getString("IODErr4"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("IODErr5"));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr4"), e);
		}
		
		inicializar();
	}
}
