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

import com.ecowheel.system.model.Llanta;
import com.ecowheel.system.model.LlantaHome;
import com.ecowheel.system.util.Contains;

/**
 * 
 * @author David Andres Betancourth Botero
 * 
 */
@ManagedBean(name = "llanta")
@ViewScoped
public class LlantaBean {

	private static final Logger log = Logger.getLogger(LlantaBean.class);
	private ResourceBundle bundle = ResourceBundle.getBundle("com.ecowheel.system.util.bundle");
	private Llanta llanta;
	private List<Llanta> listLlantas;

	@EJB
	private LlantaHome llantaHome;

	public List<Llanta> getListLlantas() {
		return listLlantas;
	}

	public void setListLlantas(List<Llanta> listLlantas) {
		this.listLlantas = listLlantas;
	}

	public Llanta getLlanta() {
		return llanta;
	}

	public void setLlanta(Llanta llanta) {
		this.llanta = llanta;
	}

	public LlantaBean() {
	}

	@PostConstruct
	public void init() {
		inicializar();
	}

	public void inicializar() {
		this.llanta = new Llanta();
		llenarLlantas();
	}

	public void llenarLlantas() {
		this.listLlantas = new ArrayList<Llanta>();
		try {
			this.listLlantas = this.llantaHome.findAll();
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			log.error(bundle.getString("IODErr4"), e);
		}
	}

	public void eliminar(Llanta llanta) {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.llantaHome.remove(llanta);

			enunciado.append(bundle.getString("label3"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label21"));
			enunciado.append(Contains.VACIO);
			enunciado.append(llanta.getStrnombre());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label3"));
		} catch (Exception e) {

			enunciado.append(bundle.getString("IODErr6"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label22"));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr6"), e);
		}

		llenarLlantas();
	}

	public void guardar() {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.llantaHome.persist(this.llanta);

			enunciado.append(bundle.getString("label17"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label21"));
			enunciado.append(Contains.VACIO);
			enunciado.append(llanta.getStrnombre());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label17"));
		} catch (Exception e) {

			enunciado.append(bundle.getString("IODErr4"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("IODErr7"));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr4"), e);
		}

		inicializar();
	}
}
