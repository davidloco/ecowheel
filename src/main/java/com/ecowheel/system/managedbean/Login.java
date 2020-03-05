package com.ecowheel.system.managedbean;

import java.security.MessageDigest;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ecowheel.system.model.ParametricaHome;
import com.ecowheel.system.model.Tercero;
import com.ecowheel.system.model.TerceroHome;
import com.ecowheel.system.model.Usuario;
import com.ecowheel.system.model.UsuarioHome;
import com.ecowheel.system.util.Contains;

/**
 * 
 * @author David Andres Betancourth Botero
 * 
 */
@ManagedBean(name = "login")
@SessionScoped
public class Login {

	private StringBuilder userDatos = new StringBuilder();
	private Datos datos = new Datos();
	private MessageDigest md;
	private Usuario usuario;
	private Tercero tercero;
	private static final Logger log = Logger.getLogger(Login.class);
	private ResourceBundle bundle = ResourceBundle.getBundle("com.ecowheel.system.util.bundle");

	private boolean bandera = false;;
	private boolean reportes = false;;

	@EJB
	private UsuarioHome usuarioHome;

	@EJB
	private TerceroHome terceroHome;

	@EJB
	private ParametricaHome parametricaHome;

	public boolean isReportes() {
		return reportes;
	}

	public void setReportes(boolean reportes) {
		this.reportes = reportes;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	public Tercero getTercero() {
		return tercero;
	}

	public void setTercero(Tercero tercero) {
		this.tercero = tercero;
	}

	public MessageDigest getMd() {
		return md;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setMd(MessageDigest md) {
		this.md = md;
	}

	public StringBuilder getUserDatos() {
		return userDatos;
	}

	public void setUserDatos(StringBuilder userDatos) {
		this.userDatos = userDatos;
	}

	public Datos getDatos() {
		return datos;
	}

	public void setDatos(Datos datos) {
		this.datos = datos;
	}

	@PostConstruct
	public void init() {
	}

	public Login() {
	}

	public String cargarOperario() {
		reportes = false;
		bandera = true;
		return "inhome";
	}

	public String cargarAdministrador() {
		reportes = true;
		bandera = true;
		return "inhome";
	}

	public String autenticarUsuario() {
		List<Usuario> usuarios = this.usuarioHome.findByLogin(this.datos.getUsername());

		if (!usuarios.isEmpty()) {
			try {
				// System.out.println(encrypt(this.datos.getPassword()));

				if (usuarios.get(0).getStrpassword().equals(encrypt(this.datos.getPassword()))) {

					this.usuario = usuarios.get(0);

					HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
					session.setAttribute("datosLogin", usuario);

					userDatos = new StringBuilder();
					userDatos.append(bundle.getString("label4"));
					userDatos.append(" ");
					userDatos.append(usuario.getStrlogin());
					userDatos.append(", ");
					userDatos.append(bundle.getString("label6"));
					userDatos.append(this.usuario.getIdusuario());
					log.info(userDatos.toString());

					this.tercero = terceroHome.findById(usuario.getTercero().getIntid());

					switch (this.tercero.getTipoTecero().getIntid()) {
					case Contains.OPERARIO:
						return cargarOperario();

					case Contains.ADMINISTRADOR:
						return cargarAdministrador();
					}

				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("verificarusuario"), bundle
									.getString("contrasenaincorrecta")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("verificarusuario"), bundle.getString("usuarionoencontrado")));
		}
		return "";
	}

	public String encrypt(String input) throws Exception {
		this.md = MessageDigest.getInstance("SHA1");
		byte[] result = this.md.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public String logout() {
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

			userDatos = new StringBuilder();

			userDatos.append(bundle.getString("label5"));
			userDatos.append(" ");
			userDatos.append(((Usuario) session.getAttribute("datosLogin")).getStrlogin());
			userDatos.append(", ");
			userDatos.append(bundle.getString("label6"));
			userDatos.append(((Usuario) session.getAttribute("datosLogin")).getIdusuario());
			log.info(userDatos.toString());

			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.invalidate();
			datos = new Datos();

			reportes = false;
			bandera = false;

			return "salir";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("usuario"), bundle.getString("label15"));

			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
		return "";
	}

}
