package com.egoveris.vucfront.base.sections;

import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.mbeans.steps.StepParentMb;
import com.egoveris.vucfront.base.repository.EstilosRepository;
import com.egoveris.vucfront.base.service.ApplicationService;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.model.model.EstilosDTO;
import com.egoveris.vucfront.model.model.NotificacionDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.model.TareaDTO;
import com.egoveris.vucfront.model.model.TipoTramiteDTO;
import com.egoveris.vucfront.model.service.EstilosService;
import com.egoveris.vucfront.model.service.NotificacionService;
import com.egoveris.vucfront.model.service.TareaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.terasoluna.plus.core.util.ApplicationContextUtil;

@ManagedBean
@ViewScoped
public class IndexMb extends StepParentMb {

	private static final long serialVersionUID = 4770656613254597526L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ManagedProperty("#{applicationServiceImpl}")
	private ApplicationService applicationService;
	@ManagedProperty("#{loginMb}")
	private LoginMb login;
	@ManagedProperty("#{notificacionServiceImpl}")
	private NotificacionService notificacionService;
	@ManagedProperty("#{tareaServiceImpl}")
	private TareaService tareaService;

	@ManagedProperty("#{estilosServiceImpl}")
	private EstilosService estilosService;
	
	private Long idTramite;
	private TipoTramiteDTO tipoTramite;

	private List<NotificacionDTO> notificacionesList;
	private Map<String, List<NotificacionDTO>> mapaExpedienteListaNotificaciones;
	
	public void init() {
		try {
			if (!login.isPersonaActiva()) {
				if (login.isIdentidadExternal()) {
					this.redirect(ConstantsUrl.INDEX);
				} else {
					this.redirect(ConstantsUrl.PENDIENTE);					
				}
				return;
			}
			
			if (this.login.getPersona().getTerminosCondiciones() == null) {
				this.redirect(ConstantsUrl.FORMULARIO_Y_TERMINOS);
				return;
			}
		} catch (NullPointerException e) {
			if (login.isIdentidadExternal()) {
				this.redirect(ConstantsUrl.INDEX);
			} else {
				this.redirect(ConstantsUrl.PENDIENTE);					
			}
			return;
		} catch (Exception e) {
			this.redirect(ConstantsUrl.PENDIENTE);
			return;
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("persona", login.getPersona());
		// obtener lista y detalle de notificaciones
		if (notificacionesList == null) {
			notificacionesList = notificacionService.getNotificacionesByPersona(login.getPersona());
		}
		if (mapaExpedienteListaNotificaciones == null) {
			mapaExpedienteListaNotificaciones = new HashMap<>();
			fillExpedienteNotificacionesMap(notificacionesList);
		}
		// obtener lista de tareas
		List<TareaDTO> tareasList;
		tareasList = tareaService.getTareasPendientesByPersona(login.getPersona());

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mapaExpedienteListaNotificaciones",
				mapaExpedienteListaNotificaciones);
		// contadores
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("numBorrador", 4);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("numProgreso", 6);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("numTareas", tareasList.size());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("numNotificaciones",
				notificacionesList.size());
		EstilosDTO estilos = this.estilosService.getEstilosByCodigo("EGOV");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("logo", estilos.getLogo());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("colorHeader", estilos.getColorHeader());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("colorBoton1", estilos.getColorBoton1());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("colorBoton2", estilos.getColorBoton2());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("colorIndBorrador", estilos.getColorIndBorrador());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("colorIndProceso", estilos.getColorIndProceso());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("colorIndTareas", estilos.getColorIndTareas());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("colorIndNotificaciones", estilos.getColorIndNotificaciones());
	}

	public Long getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
		init();
	}

	public TipoTramiteDTO getTipoTramite() {
		return tipoTramite;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	public void setLogin(LoginMb login) {
		this.login = login;
	}

	private void fillExpedienteNotificacionesMap(List<NotificacionDTO> notificacionesList) {
		for (NotificacionDTO aux : notificacionesList) {
			if (mapaExpedienteListaNotificaciones.get(aux.getExpediente().getCodigoSade()) == null) {
				mapaExpedienteListaNotificaciones.put(aux.getExpediente().getCodigoSade(),
						new ArrayList<NotificacionDTO>());
			}
			mapaExpedienteListaNotificaciones.get(aux.getExpediente().getCodigoSade()).add(aux);
		}
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	public void setTareaService(TareaService tareaService) {
		this.tareaService = tareaService;
	}

	public void setEstilosService(EstilosService estilosService) {
		this.estilosService = estilosService;
	}
	
}