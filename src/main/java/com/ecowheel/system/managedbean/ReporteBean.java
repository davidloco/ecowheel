package com.ecowheel.system.managedbean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ecowheel.system.model.Cliente;
import com.ecowheel.system.model.ClienteHome;
import com.ecowheel.system.model.RegistroHome;
import com.ecowheel.system.util.Contains;

/**
 * 
 * @author David Andres Betancourth Botero
 * 
 */
@ManagedBean(name = "reporte")
@ViewScoped
public class ReporteBean {

	private static final Logger log = Logger.getLogger(ReporteBean.class);
	private ResourceBundle bundle = ResourceBundle
			.getBundle("com.ecowheel.system.util.bundle");

	private String certificado;
	private String texto1;
	private String texto2;
	private Date fechaIni;
	private Date fechaFin;

	private int cliente;
	private Date fecha;
	private List<Cliente> listClientes;
	private List<Integer> listRegistros;

	@EJB
	private ClienteHome clienteHome;

	@EJB
	private RegistroHome registroHome;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Integer> getListRegistros() {
		return listRegistros;
	}

	public void setListRegistros(List<Integer> listRegistros) {
		this.listRegistros = listRegistros;
	}

	public String getTexto1() {
		return texto1;
	}

	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}

	public String getTexto2() {
		return texto2;
	}

	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}

	public List<Cliente> getListClientes() {
		return listClientes;
	}

	public void setListClientes(List<Cliente> listClientes) {
		this.listClientes = listClientes;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public ReporteBean() {
	}

	@PostConstruct
	public void init() {
		llenarListClientes();
		llenarListRegistros();
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

	public void llenarListRegistros() {
		this.listRegistros = new ArrayList<Integer>();
		try {
			if (this.fecha == null) {
				this.listRegistros = registroHome.actasXFecha(new Date());
			} else {
				this.listRegistros = registroHome.actasXFecha(this.fecha);
			}
			log.info(bundle.getString("label17"));
		} catch (Exception e) {
			log.error(bundle.getString("IODErr4"), e);
		}
	}

	public String generarCertificado(String tipo, boolean bandera) {
		StringBuilder url = new StringBuilder();
		StringBuilder param = new StringBuilder();

		switch (Integer.parseInt(tipo)) {
		case Contains.PDF:
			param.append(Contains.PDFTXT);
			break;
		case Contains.XLS:
			param.append(Contains.XLSTXT);
			break;
		case Contains.DOC:
			param.append(Contains.DOCTXT);
			break;
		}

		if (bandera) {
			this.fechaIni = null;
			this.fechaFin = null;
			this.cliente = 0;
			this.texto2 = "";
			param.append("&text=");
			param.append(texto1);
		} else {
			this.certificado = "";
			this.texto1 = "";
			param.append("&text=");
			param.append(texto2);
		}

		if (!(this.fechaIni == null) && !(this.fechaFin == null)) {
			param.append("&fecini=");
			param.append(new SimpleDateFormat("yyyy-MM-dd")
					.format(this.fechaIni));
			param.append("&fecfin=");
			param.append(new SimpleDateFormat("yyyy-MM-dd")
					.format(this.fechaFin));
		} else {
			param.append("&fecini=");
			param.append(0);
			param.append("&fecfin=");
			param.append(0);
		}

		if (this.cliente != 0) {
			param.append("&idcliente=");
			param.append(this.cliente);
		} else {
			param.append("&idcliente=");
			param.append(0);
		}

		if (this.certificado.length() == 0) {
			param.append("&certificado=");
			param.append(0);
		} else {
			param.append("&certificado=");
			param.append(this.certificado);
		}

		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		url.append("http://");
		url.append(httpServletRequest.getRemoteAddr());
		url.append(":");
		url.append(FacesContext.getCurrentInstance().getExternalContext()
				.getRequestServerPort());
		url.append("/birt/run?__report=report/ecowheel/informecertificado.rptdesign&__format=");
		url.append(param);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(url.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String generarInformeXMes(String tipo) {
		StringBuilder url = new StringBuilder();
		StringBuilder param = new StringBuilder();

		switch (Integer.parseInt(tipo)) {
		case Contains.PDF:
			param.append(Contains.PDFTXT);
			break;
		case Contains.XLS:
			param.append(Contains.XLSTXT);
			break;
		case Contains.DOC:
			param.append(Contains.DOCTXT);
			break;
		}

		if (!(this.fechaIni == null) && !(this.fechaFin == null)) {
			param.append("&fechaIni=");
			param.append(new SimpleDateFormat("yyyy-MM-dd")
					.format(this.fechaIni));
			param.append("&fechaFin=");
			param.append(new SimpleDateFormat("yyyy-MM-dd")
					.format(this.fechaFin));
		} else {
			if (this.fechaIni == null && !(this.fechaFin == null)) {
				param.append("&fechaFin=");
				param.append(new SimpleDateFormat("yyyy-MM-dd")
						.format(this.fechaFin));
			}
			if (this.fechaFin == null && !(this.fechaIni == null)) {
				param.append("&fechaIni=");
				param.append(new SimpleDateFormat("yyyy-MM-dd")
						.format(this.fechaIni));
			}
		}

		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		url.append("http://");
		url.append(httpServletRequest.getRemoteAddr());
		url.append(":");
		url.append(FacesContext.getCurrentInstance().getExternalContext()
				.getRequestServerPort());
		url.append("/birt/run?__report=report/ecowheel/informexmes.rptdesign&__format=");
		url.append(param);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(url.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.fechaIni = new Date();
		this.fechaFin = new Date();
		return null;
	}

	public String generarInformeXNfu(String tipo) {
		StringBuilder url = new StringBuilder();
		StringBuilder param = new StringBuilder();

		switch (Integer.parseInt(tipo)) {
		case Contains.PDF:
			param.append(Contains.PDFTXT);
			break;
		case Contains.XLS:
			param.append(Contains.XLSTXT);
			break;
		case Contains.DOC:
			param.append(Contains.DOCTXT);
			break;
		}

		if (!(this.fechaIni == null) && !(this.fechaFin == null)) {
			param.append("&fechaIni=");
			param.append(new SimpleDateFormat("yyyy-MM-dd")
					.format(this.fechaIni));
			param.append("&fechaFin=");
			param.append(new SimpleDateFormat("yyyy-MM-dd")
					.format(this.fechaFin));
		} else {
			if (this.fechaIni == null && !(this.fechaFin == null)) {
				param.append("&fechaFin=");
				param.append(new SimpleDateFormat("yyyy-MM-dd")
						.format(this.fechaFin));
			}
			if (this.fechaFin == null && !(this.fechaIni == null)) {
				param.append("&fechaIni=");
				param.append(new SimpleDateFormat("yyyy-MM-dd")
						.format(this.fechaIni));
			}
		}

		if (this.cliente != 0) {
			param.append("&idcliente=");
			param.append(this.cliente);
		}

		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		url.append("http://");
		url.append(httpServletRequest.getRemoteAddr());
		url.append(":");
		url.append(FacesContext.getCurrentInstance().getExternalContext()
				.getRequestServerPort());
		url.append("/birt/run?__report=report/ecowheel/informexnfu.rptdesign&__format=");
		url.append(param);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(url.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.fechaIni = new Date();
		this.fechaFin = new Date();
		this.cliente = 0;
		return null;
	}

}