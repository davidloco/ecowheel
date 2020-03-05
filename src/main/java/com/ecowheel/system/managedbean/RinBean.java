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

import com.ecowheel.system.model.Rin;
import com.ecowheel.system.model.RinHome;
import com.ecowheel.system.util.Contains;

/**
 * 
 * @author David Andres Betancourth Botero
 * 
 */
@ManagedBean(name = "rin")
@ViewScoped
public class RinBean {

	private static final Logger log = Logger.getLogger(RinBean.class);
	private ResourceBundle bundle = ResourceBundle.getBundle("com.ecowheel.system.util.bundle");
	private Rin rin;
	private List<Rin> listRins;

	@EJB
	private RinHome rinHome;

	public List<Rin> getListRins() {
		return listRins;
	}

	public void setListRins(List<Rin> listRins) {
		this.listRins = listRins;
	}

	public Rin getRin() {
		return rin;
	}

	public void setRin(Rin rin) {
		this.rin = rin;
	}

	public RinBean() {
	}

	@PostConstruct
	public void init() {
		inicializar();
	}

	public void inicializar() {
		this.rin = new Rin();
		llenarRins();
	}

	public void llenarRins() {
		this.listRins = new ArrayList<Rin>();
		try {
			this.listRins = this.rinHome.findAll();
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			log.error(bundle.getString("IODErr4"), e);
		}
	}

	public void eliminar(Rin rin) {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.rinHome.remove(rin);

			enunciado.append(bundle.getString("label3"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label20"));
			enunciado.append(Contains.VACIO);
			enunciado.append(rin.getStrnombre());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Contains.VACIO, enunciado.toString()));
			log.info(bundle.getString("label3"));
		} catch (Exception e) {

			enunciado.append(bundle.getString("IODErr6"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label22"));

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Contains.VACIO, enunciado.toString()));
			log.error(bundle.getString("IODErr6"), e);
		}

		llenarRins();
	}

	public void guardar() {
		StringBuilder enunciado = new StringBuilder();
		try {
			this.rinHome.persist(this.rin);

			enunciado.append(bundle.getString("label17"));
			enunciado.append(Contains.VACIO);
			enunciado.append(bundle.getString("label20"));
			enunciado.append(Contains.VACIO);
			enunciado.append(rin.getStrnombre());

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
