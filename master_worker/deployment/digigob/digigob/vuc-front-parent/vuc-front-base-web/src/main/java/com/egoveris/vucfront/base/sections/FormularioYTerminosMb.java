package com.egoveris.vucfront.base.sections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.i18n.DefaultLocaleMessageSource;

import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.model.Partido;
import com.egoveris.vucfront.base.model.Provincia;
import com.egoveris.vucfront.base.service.ProvinciaService;
import com.egoveris.vucfront.base.service.TerminosCondicionesService;
import com.egoveris.vucfront.base.service.UserService;
import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.base.util.MessageType;
import com.egoveris.vucfront.model.model.DomicilioDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.model.TerminosCondicionesDTO;

@ManagedBean
@ViewScoped
public class FormularioYTerminosMb extends AbstractMb {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2944655947408596329L;

	private static final Logger LOG = LoggerFactory.getLogger(FormularioYTerminosMb.class);

	@ManagedProperty("#{loginMb}")
	private LoginMb login;

	@ManagedProperty("#{userServiceImpl}")
	private UserService userService;
	
	@ManagedProperty("#{provinciaServiceImpl}")	
	private ProvinciaService provinciaService;
	
	@ManagedProperty("#{terminosCondicionesServiceImpl}")	
	private TerminosCondicionesService terminosCondicionesService;
	
	@ManagedProperty("#{msg}")
	private DefaultLocaleMessageSource bundle;

	private String direccion;
	private String piso;
	private String dpto;
	private String provincia;
	private String partido;
	private String localidad;
	private String codigoPostal;
	private String telefono;
	private Boolean terminosOk = false;
	private String terminos = "Estos son los terminos de prueba";

	private Boolean formularioOk = false;

	private TerminosCondicionesDTO terminosCondiciones;
	
	private List<Provincia> provinciasList;
	
	private Provincia selectedProvincia;
	
	private List<SelectItem> provincias;
	private List<SelectItem> partidos;
	private List<SelectItem> localidades;

	public FormularioYTerminosMb() {
		this.provincias = new ArrayList<SelectItem>();
		this.partidos = new ArrayList<SelectItem>();
		this.localidades = new ArrayList<SelectItem>();
	}
	
	public void init() {
		// Login
		if (login.getPersona() == null) {
			redirect(ConstantsUrl.LOGIN);
		}
		
		this.provinciasList = this.provinciaService.getAll();
		this.provincias = this.provinciasList.stream().map(p -> new SelectItem(p.getNombre(), p.getNombre()))
				.collect(Collectors.toList());
		
		this.terminosCondiciones = this.terminosCondicionesService.getUltimoTerminosYCondiciones();
		if (this.terminosCondiciones != null) {
			this.terminos = this.terminosCondiciones.getCodigoContenido();
		}
		
	}

	public LoginMb getLogin() {
		return login;
	}

	public void setLogin(LoginMb login) {
		this.login = login;
	}

	public void selectProvincia() {
		this.selectedProvincia = this.provinciasList.stream().filter(p -> p.getNombre().equals(this.provincia))
				.findFirst().orElse(null);
		this.partidos = this.selectedProvincia.getPartidos().stream()
				.map(p -> new SelectItem(p.getNombre(), p.getNombre())).collect(Collectors.toList());
		this.partido = null;
		this.localidad = null;
		this.localidades = new ArrayList<>();
		this.validar();
	}
	
	public void selectPartido() {
		Partido partido = this.selectedProvincia.getPartidos().stream().filter(p -> p.getNombre().equals(this.partido)).findFirst().orElse(null);
		this.localidades = partido.getLocalidades().stream().map(p -> new SelectItem(p.getNombre(), p.getNombre())).collect(Collectors.toList());
		this.localidad = null;
		this.validar();
	}
	
	public List<SelectItem> getProvincias() {
		return provincias;
	}

	public List<SelectItem> getPartidos() {
		return partidos;
	}

	public List<SelectItem> getLocalidades() {
		return localidades;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDpto() {
		return dpto;
	}

	public void setDpto(String dpto) {
		this.dpto = dpto;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Boolean getTerminosOk() {
		return terminosOk;
	}

	public void setTerminosOk(Boolean terminosOk) {
		this.terminosOk = terminosOk;
	}

	public String getTerminos() {
		return terminos;
	}

	public void setTerminos(String terminos) {
		this.terminos = terminos;
	}

	public Boolean getFormularioOk() {
		return formularioOk;
	}

	public void setFormularioOk(Boolean formularioOk) {
		this.formularioOk = formularioOk;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public void validar() {
	}

	public void confirmar() {
		if (this.direccion != null && !this.direccion.isEmpty() && this.provincia != null
				&& this.partido != null && this.localidad != null && this.terminosOk
				&& telefono.matches("\\d+")) {
			try {
				PersonaDTO persona = this.login.getPersona();
				persona.setTelefonoContacto(this.telefono);
				DomicilioDTO domicilio = new DomicilioDTO();
				domicilio.setDireccion(direccion);
				domicilio.setPiso(piso);
				domicilio.setDepto(dpto);
				domicilio.setLocalidad(localidad);
				domicilio.setCodPostal(codigoPostal);
				domicilio.setAltura("0");
				domicilio.setProvincia(provincia);
				domicilio.setDepartamento(partido);
				persona.setDomicilioConstituido(domicilio);
				persona.setTerminosCondiciones(terminosCondiciones);
				this.userService.save(persona);
				this.redirect(ConstantsUrl.INDEX);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				showDialogMessage(bundle.getMessage("tYCErrorActualizar", null) + e.getMessage(), MessageType.ERROR);
			}
			
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(bundle.getMessage("tYCFaltaCompletar", null));
			if (direccion == null || direccion.isEmpty()) {
				sb.append(bundle.getMessage("tYCFaltaDireccion", null));
			}
			if (provincia == null) {
				sb.append(bundle.getMessage("tYCFaltaProvincia", null));
			}
			if (partido == null) {
				sb.append(bundle.getMessage("tYCFaltaPartido", null));
			}
			if (localidad == null) {
				sb.append(bundle.getMessage("tYCFaltaLocalidad", null));
			}
			if (codigoPostal == null || codigoPostal.isEmpty()) {
				sb.append(bundle.getMessage("tYCFaltaCP", null));
			}
			if (telefono == null || telefono.isEmpty()) {
				sb.append(bundle.getMessage("tYCFaltaTel", null));
			}
			if (telefono != null && !telefono.matches("\\d+")) {
				sb.append(bundle.getMessage("tYCFaltaTel2", null));
			}
			if (!terminosOk) {
				sb.append(bundle.getMessage("tYCFaltaAceptar", null));
			}
			showDialogMessage(sb.toString(), MessageType.FALTAN_DATOS);
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ProvinciaService getProvinciaService() {
		return provinciaService;
	}

	public void setProvinciaService(ProvinciaService provinciaService) {
		this.provinciaService = provinciaService;
	}

	public TerminosCondicionesService getTerminosCondicionesService() {
		return terminosCondicionesService;
	}

	public void setTerminosCondicionesService(TerminosCondicionesService terminosCondicionesService) {
		this.terminosCondicionesService = terminosCondicionesService;
	}
	
	public void setBundle(DefaultLocaleMessageSource bundle) {
		this.bundle = bundle;
	}
}
