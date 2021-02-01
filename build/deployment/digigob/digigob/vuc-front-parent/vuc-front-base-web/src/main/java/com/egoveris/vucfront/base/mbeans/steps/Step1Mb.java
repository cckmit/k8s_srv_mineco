package com.egoveris.vucfront.base.mbeans.steps;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.i18n.DefaultLocaleMessageSource;

import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.service.ApplicationService;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.base.util.MessageType;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.EstadoTramiteDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.model.TipoTramiteDTO;
import com.egoveris.vucfront.model.util.EstadoTramiteEnum;

@ManagedBean
@ViewScoped
public class Step1Mb extends StepParentMb {

	private static final long serialVersionUID = 4770656613254597526L;
	private static final Logger LOG = LoggerFactory.getLogger(Step1Mb.class);
	private static final Long STEP_1 = 1l;
	
	@ManagedProperty("#{applicationServiceImpl}")
	private ApplicationService applicationService;
	@ManagedProperty("#{loginMb}")
	private LoginMb login;
	@ManagedProperty("#{msg}")
	private DefaultLocaleMessageSource bundle;

	private Long idTramite;
	private TipoTramiteDTO tipoTramite;

	public void init() {
		// Login
		if (login.getPersona() == null) {
			redirect(ConstantsUrl.LOGIN);
		} else {
			setPersona(login.getPersona());
		}

		// Desde newApplication hacia Step1
		if (getIdExpediente() == null) {
			setExpediente(new ExpedienteFamiliaSolicitudDTO());
			// Set estado to BORRADOR
			getExpediente().setEstadoTramite(new EstadoTramiteDTO(EstadoTramiteEnum.BORRADOR));
			tipoTramite = applicationService.getTipoTramiteById(idTramite);
			getExpediente().setTipoTramite(tipoTramite);
		} else {
			// Desde Step2 hacia Step1
			setExpediente(getExpedienteService().getExpedienteFamiliaSolicitudById(getIdExpediente()));
			tipoTramite = getExpediente().getTipoTramite();
		}
		getExpediente().setPersona(getPersona());
	}

	// Si es que el usuario puede ingresar datos, hay que validar que estos no
	// estén vacíos.
	private Boolean validateInput() {
		boolean validated = true;

		return validated;
	}

	public void cmdBack() {
		redirect(ConstantsUrl.NEWAPP);
	}

	public void cmdSave() {
		if (isBorrador()) {
			getExpediente().setMotivo(getExpediente().getDetalleSolicitud());
			if (getExpediente().getDocumentosList() == null) {
				getExpediente().setDocumentosList(new ArrayList<DocumentoDTO>());
			}
			if (getExpediente().getStep() == null || getExpediente().getStep() < STEP_1) {
				getExpediente().setStep(STEP_1);
			}
			setExpediente(getExpedienteBaseService().saveExpedienteFamiliaSolicitud(getExpediente()));
			LOG.info("### PASO 1: Expediente guardado ID: ".concat(getExpediente().getId().toString()));
			showDialogMessage(bundle.getMessage("step1SolicitudGuardada", null), MessageType.INFO);
		}
	}

	public void cmdNext() {
		if (validateInput()) {
			cmdSave();
			redirect(ConstantsUrl.STEP2.concat(getExpediente().getId().toString()));
		} else {
			showDialogMessage(bundle.getMessage("step1CamposObligatorios", null), MessageType.ERROR);
		}
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
	
	public void setBundle(DefaultLocaleMessageSource bundle) {
		this.bundle = bundle;
	}

}