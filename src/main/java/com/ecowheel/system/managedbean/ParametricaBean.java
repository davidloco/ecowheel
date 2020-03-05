package com.ecowheel.system.managedbean;

import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ecowheel.system.model.Parametrica;
import com.ecowheel.system.model.ParametricaHome;

/**
 * 
 * @author David Andres
 * 
 */

@ManagedBean(name = "parametrica")
@ViewScoped
public class ParametricaBean {

	private static final Logger log = Logger.getLogger(ClienteBean.class);
	private ResourceBundle bundle = ResourceBundle.getBundle("com.ecowheel.system.util.bundle");
	private List<Parametrica> listImage;

	@EJB
	private ParametricaHome parametricaHome;

	public List<Parametrica> getListImage() {
		return listImage;
	}
	
	public void setListImage(List<Parametrica> listImage) {
		this.listImage = listImage;
	}

	public String cargarMision() {
		log.info(bundle.getString("label25"));
		return parametricaHome.findXNombre(bundle.getString("mision")).getStrvalor();
	}

	public String cargarVision() {
		log.info(bundle.getString("label26"));
		return parametricaHome.findXNombre(bundle.getString("vision")).getStrvalor();
	}

	public List<Parametrica> cargarImagen() {
		return parametricaHome.findXNombres(bundle.getString("img"));
	}
}
