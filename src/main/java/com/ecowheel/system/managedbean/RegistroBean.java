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
import org.primefaces.event.SelectEvent;

import com.ecowheel.system.model.Cliente;
import com.ecowheel.system.model.ClienteHome;
import com.ecowheel.system.model.Llanta;
import com.ecowheel.system.model.LlantaHome;
import com.ecowheel.system.model.Registro;
import com.ecowheel.system.model.RegistroHome;
import com.ecowheel.system.model.Rin;
import com.ecowheel.system.model.RinHome;
import com.ecowheel.system.util.Contains;

/**
 * 
 * @author David Andres Betancourth Botero
 * 
 */
@ManagedBean(name = "registro")
@ViewScoped
public class RegistroBean {

	private static final Logger log = Logger.getLogger(RegistroBean.class);
	private ResourceBundle bundle = ResourceBundle
			.getBundle("com.ecowheel.system.util.bundle");
	private Registro registro;
	private Registro registroSession;
	private List<Registro> listRegistros;
	private List<Rin> listRins;
	private List<Llanta> listLlantas;
	private List<Cliente> listClientes;

	private String intunidades;
	private String intunidadesEdit;

	@EJB
	private RegistroHome registroHome;

	@EJB
	private RinHome rinHome;

	@EJB
	private ClienteHome clienteHome;

	@EJB
	private LlantaHome llantaHome;

	public String getIntunidadesEdit() {
		return intunidadesEdit;
	}

	public void setIntunidadesEdit(String intunidadesEdit) {
		this.intunidadesEdit = intunidadesEdit;
	}

	public List<Registro> getListRegistros() {
		return listRegistros;
	}

	public void setListRegistros(List<Registro> listRegistros) {
		this.listRegistros = listRegistros;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public List<Rin> getListRins() {
		return listRins;
	}

	public void setListRins(List<Rin> listRins) {
		this.listRins = listRins;
	}

	public List<Llanta> getListLlantas() {
		return listLlantas;
	}

	public void setListLlantas(List<Llanta> listLlantas) {
		this.listLlantas = listLlantas;
	}

	public List<Cliente> getListClientes() {
		return listClientes;
	}

	public void setListClientes(List<Cliente> listClientes) {
		this.listClientes = listClientes;
	}

	public String getIntunidades() {
		return intunidades;
	}

	public void setIntunidades(String intunidades) {
		this.intunidades = intunidades;
	}

	public RegistroBean() {
	}

	@PostConstruct
	public void init() {
		inicializar();
	}

	private void inicializar() {
		this.registro = new Registro();
		this.registro.setLlanta(new Llanta());
		this.registro.setRin(new Rin());
		this.registro.setCliente(new Cliente());

		this.intunidades = new String();

		llenarListRegistros();
		cargarCombos();
	}

	public List<String> completeText(String query) {
		List<String> results = new ArrayList<String>();

		for (Llanta pibote : this.listLlantas) {
			if (pibote.getStrnombre().toUpperCase()
					.contains(query.toUpperCase())) {
				results.add(pibote.getStrnombre());
			}
		}
		return results;
	}

	public void onItemSelect(SelectEvent event) {
		for (Llanta pibote : this.listLlantas) {
			if (pibote.getStrnombre().contains(event.getObject().toString())) {
				this.registro.setLlanta(pibote);
			}
		}
	}

	private void cargarCombos() {
		llenarListRins();
		llenarListLlantas();
		llenarListClientes();
	}

	private void llenarListRegistros() {
		this.listRegistros = new ArrayList<Registro>();
		try {
			this.listRegistros = this.registroHome.findAll();
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			log.error(bundle.getString("IODErr4"), e);
		}
	}

	private void llenarListRins() {
		this.listRins = new ArrayList<Rin>();
		try {
			this.listRins = this.rinHome.findAll();
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			log.error(bundle.getString("IODErr4"), e);
		}
	}

	private void llenarListLlantas() {
		this.listLlantas = new ArrayList<Llanta>();
		try {
			this.listLlantas = this.llantaHome.findAll();
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			log.error(bundle.getString("IODErr4"), e);
		}
	}

	private void llenarListClientes() {
		this.listClientes = new ArrayList<Cliente>();
		try {
			this.listClientes = this.clienteHome.findAll();
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			log.error(bundle.getString("IODErr4"), e);
		}
	}

	public void eliminar(Registro registro) {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.registroHome.remove(registro);

			enunciado.append(bundle.getString("label3"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label18"));
			enunciado.append(Contains.VACIO);
			enunciado.append(registro.getIntcertificado());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label3"));
		} catch (Exception e) {

			enunciado.append(bundle.getString("IODErr6"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label22"));

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr6"), e);
		}

		llenarListRegistros();
	}

	public void guardar() {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.registro.setIntunidades(Integer.parseInt(this.intunidades));
			this.registroHome.persist(this.registro);

			enunciado.append(bundle.getString("label17"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label18"));
			enunciado.append(Contains.VACIO);
			enunciado.append(registro.getIntcertificado());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			enunciado.append(bundle.getString("IODErr4"));
			enunciado.append(Contains.VACIO);
			enunciado.append(e.getCause());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr4"), e);
		}

		this.registroSession = new Registro();
		this.registroSession.setCliente(new Cliente());
		this.registroSession.setCliente(this.registro.getCliente());
		this.registroSession.setLlanta(new Llanta());
		this.registroSession.setRin(new Rin());
		this.registroSession.setDatefecha(this.registro.getDatefecha());
		this.registroSession.setIntcertificado(this.registro
				.getIntcertificado());

		inicializar();

		this.registro = this.registroSession;
	}

	public void editar(RowEditEvent event) {
		StringBuilder enunciado = new StringBuilder();

		Registro pibote = new Registro();
		pibote = (Registro) event.getObject();
		try {
			pibote.setIntunidades(Integer.parseInt(this.intunidadesEdit));
			this.registroHome.merge(pibote);

			enunciado.append(bundle.getString("label17"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label18"));
			enunciado.append(Contains.VACIO);
			enunciado.append(registro.getIntcertificado());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			enunciado.append(bundle.getString("IODErr4"));
			enunciado.append(Contains.VACIO);
			enunciado.append(e.getCause());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr4"), e);
		}
		
		this.intunidadesEdit = "";
		
		this.registroSession = new Registro();
		this.registroSession.setCliente(new Cliente());
		this.registroSession.setCliente(this.registro.getCliente());
		this.registroSession.setLlanta(new Llanta());
		this.registroSession.setRin(new Rin());
		this.registroSession.setDatefecha(this.registro.getDatefecha());
		this.registroSession.setIntcertificado(this.registro
				.getIntcertificado());

		inicializar();

		this.registro = this.registroSession;
	}
}
