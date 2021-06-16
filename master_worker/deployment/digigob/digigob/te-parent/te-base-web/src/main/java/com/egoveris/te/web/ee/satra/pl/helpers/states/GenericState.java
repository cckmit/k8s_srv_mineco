/**
 * 
 */
package com.egoveris.te.web.ee.satra.pl.helpers.states;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.Dates;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.plugins.manager.tools.scripts.ScriptUtils;
import com.egoveris.plugins.tools.FormularioControladoUtils;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.te.base.component.DocumentViewerComponent;
import com.egoveris.te.base.composer.BandboxOrganismoComposer;
import com.egoveris.te.base.composer.genrico.BandBoxUsuarioComposer;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.iface.IDelegable;
import com.egoveris.te.base.states.IState;
import com.egoveris.te.base.util.ApplicationContextProvider;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.CustomInstanceCreator;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.TramitacionHelper;
import com.egoveris.te.base.util.Utils;
import com.egoveris.te.base.util.WorkFlowScriptUtils;
import com.egoveris.te.base.util.ZkUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author difarias
 *
 */
@SuppressWarnings("serial")
public abstract class GenericState implements IVisualState {

	private static Logger logger = LoggerFactory.getLogger(GenericState.class);

	private static final String TRAMITACION_LIBRE = "Tramitación Libre";
	private static final String TRAMITACION_EN_PARALELO = "Paralelo";
	protected static final String USER_KEY = "USER.";
	protected static final String ALL_WORKFLOW = "*";
	private static final String DEFAULT_WINDOWS_ID = "genericStateWindow";
	private static final String DEFAULT_ZUL = "";
	protected static final String ESTADO_PREVIO_TL_KEY = "estado_PrevioTL";
	protected static final String USUARIO_PREVIO_TL_KEY = "usuario_PrevioTL";

	private Map<String, Object> emptyVariables = Maps.newHashMap();
	private Map<String, String> stateVariables = Maps.newHashMap();
	private ExpedienteElectronicoDTO ee;
	private Task workingTask;

	private TramitacionHelper tramitacionHelper;
	private Component parentContainer;
	private Usuario userLogged;
	private boolean rejecting = false;
	private List<DocumentoDTO> notificationsToSend;
	private String filterNotificaciones;

	// ------------------------------------------------
	private String forward = "";
	private String backward = null;
	private String name = "";
	private String stateMacroURI = null;
	private String windowsId = null;
	private String acronymPase = "";
	private String workflowName = null;
	private String tipoDocumentoFFCC = "";
	private boolean acceptReject = false;
	private boolean acceptTramitacionLibre = true;
	private String forwardDesicion = "";
	private String startScript;
	private String scriptFuseTask;
	private String scriptFuseGeneric;
	private String scriptStartState;
	private String scriptEndState;

	private IFormManager<Component> manager;
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;

	private String forwardValidation = null;
	private String initialize = null;
	private boolean showPassInfo = false;
	private Component winPase; 
	// --------
	private Integer idTransaction = null;

	private Component componentTarget;
	

	public String getForward() {
		return forward;
	}

	/**
	 * @return the showPassInfo
	 */
	public boolean isShowPassInfo() {
		return showPassInfo;
	}

	/**
	 * @param showPassInfo
	 *            the showPassInfo to set
	 */
	public void setShowPassInfo(boolean showPassInfo) {
		this.showPassInfo = showPassInfo;
	}

	/**
	 * @return the manager
	 */
	public IFormManager<Component> getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(IFormManager<Component> manager) {
		this.manager = manager;
	}

	/**
	 * @return the formManagerFact
	 */
	public IFormManagerFactory<IFormManager<Component>> getFormManagerFact() {
		return formManagerFact;
	}

	/**
	 * @param formManagerFact
	 *            the formManagerFact to set
	 */
	public void setFormManagerFact(IFormManagerFactory<IFormManager<Component>> formManagerFact) {
		this.formManagerFact = formManagerFact;
	}

	/**
	 * @return the forwardValidation
	 */
	public String getForwardValidation() {
		return forwardValidation;
	}

	/**
	 * @param forwardValidation
	 *            the forwardValidation to set
	 */
	public void setForwardValidation(String forwardValidation) {
		this.forwardValidation = forwardValidation;
	}

	/**
	 * @return the initialize
	 */
	public String getInitialize() {
		return initialize;
	}

	/**
	 * @param initialize
	 *            the initialize to set
	 */
	public void setInitialize(String initialize) {
		this.initialize = initialize;
	}

	public String getBackwardState() {
		return backward;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the acceptTramitacionLibre
	 */
	public boolean isAcceptTramitacionLibre() {
		return acceptTramitacionLibre;
	}

	/**
	 * @param acceptTramitacionLibre
	 *            the aceptTramitacionLibre to set
	 */
	public void setAcceptTramitacionLibre(boolean acceptTramitacionLibre) {
		this.acceptTramitacionLibre = acceptTramitacionLibre;
	}

	@Override
	public String getWorkflowName() {
		if (workflowName == null) {
			return ALL_WORKFLOW;
		}
		
		return workflowName;
	}

	public String getWindowId() {
		if (windowsId == null) {
			return DEFAULT_WINDOWS_ID;
		}
		
		return windowsId;
	}

	public String getAcronymPase() {
		return acronymPase;
	}

	public String getStateMacroURI() {
		if (stateMacroURI == null) {
			return DEFAULT_ZUL;
		}
		
		return stateMacroURI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#aceptReject()
	 */
	@Override
	public boolean acceptReject() {
		return isAcceptReject();
	}

	/**
	 * @return the acceptReject
	 */
	public boolean isAcceptReject() {
		return acceptReject;
	}

	/**
	 * @param aceptReject
	 *            the aceptReject to set
	 */
	public void setAcceptReject(boolean acceptReject) {
		this.acceptReject = acceptReject;
	}

	/**
	 * @param acronymPase
	 *            the acronymPase to set
	 */
	public void setAcronymPase(String acronymPase) {
		this.acronymPase = acronymPase;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public void setBackward(String backward) {
		this.backward = backward;
	}

	public void setWindows_id(String windows_id) {
		this.windowsId = windows_id;
	}

	public void setWorkflow_name(String workflow_name) {
		this.workflowName = workflow_name;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public void setStateMacroURI(String stateMacroURI) {
		this.stateMacroURI = stateMacroURI;
	}

	public String getTipoDocumentoFFCC() {
		return tipoDocumentoFFCC;
	}

	public void setTipoDocumentoFFCC(String tipoDocumentoFFCC) {
		this.tipoDocumentoFFCC = tipoDocumentoFFCC;
	}

	/**
	 * @return the forwardDesicion
	 */
	public String getForwardDesicion() {
		return forwardDesicion;
	}

	/**
	 * @param forwardDesicion
	 *            the forwardDesicion to set
	 */
	public void setForwardDesicion(String forwardDesicion) {
		this.forwardDesicion = forwardDesicion;
	}

	/**
	 * @return the backward
	 */
	public String getBackward() {
		return backward;
	}

	/**
	 * Method to get the backward state
	 * 
	 * @return
	 */
	public String getLastState() {
		List<HistorialOperacionDTO> operations = getTramitacionHelper().getHistorialService()
				.buscarHistorialporExpediente(this.getEe().getId());

		String actualState = ((getForward() != null && !getForward().isEmpty()) ? getForward()
				: getWorkingTask().getActivityName());

		List<String> previos = getTramitacionHelper().getPreviousTasks(getWorkingTask().getId(), actualState);
		previos.remove(TRAMITACION_LIBRE); // quito tramitacion libre

		for (HistorialOperacionDTO history : operations) {
			if (previos.contains(history.getEstado())) {
				return history.getEstado();
			}
		}

		return null;
	}

	/**
	 * @return the userLogged
	 */
	public Usuario getUserLogged() {
		return userLogged;
	}

	/**
	 * @param userLogged
	 *            the userLogged to set
	 */
	public void setUserLogged(Usuario userLogged) {
		this.userLogged = userLogged;
	}

	/**
	 * @return the tramitacionHelper
	 */
	public TramitacionHelper getTramitacionHelper() {
		return tramitacionHelper;
	}

	/**
	 * @param tramitacionHelper
	 *            the tramitacionHelper to set
	 */
	public void setTramitacionHelper(TramitacionHelper tramitacionHelper) {
		this.tramitacionHelper = tramitacionHelper;
	}

	/**
	 * @return the ee
	 */
	public ExpedienteElectronicoDTO getEe() {
		return ee;
	}

	/**
	 * @param ee
	 *            the ee to set
	 */
	public void setExpedienteElectronico(ExpedienteElectronicoDTO ee) {
		this.ee = ee;
	}

	/**
	 * @return the workingTask
	 */
	public Task getWorkingTask() {
		return workingTask;
	}

	/**
	 * @param workingTask
	 *            the workingTask to set
	 */
	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#validateBeforePase()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean validateBeforePass() throws InterruptedException {
		if (isRejecting())
			return true;

		Boolean result = true;
		Map<String, Object> data = null;

		if (getForwardValidation() != null && !getForwardValidation().isEmpty()) {
			getStateVariables().clear();

			try {
				Map<String, Object> returnResult = WorkFlowScriptUtils.getInstance()
						.executeScript(ScriptType.VALIDATION, ee, getSharedObject(), getUserLogged().getUsername());
				data = (Map<String, Object>) returnResult.get(ScriptUtils.DATA_KEY);
				result = (Boolean) returnResult.get(ScriptUtils.RESULT_KEY);

				if(validarFormularioDinamico()) {
					return false;
				}
				
			} catch (ScriptException e) {
				logger.error("EXEC SCRIPT: " + e.getMessage());
				Messagebox.show(e.getMessage());
				result = false;
			}

			if (data != null) {
				Map<String, Object> stateVariables = (Map<String, Object>) data.get("VARS"); // State
				if (stateVariables != null && !stateVariables.isEmpty()) {
					getStateVariables().clear();
					for (String key : stateVariables.keySet()) {
						getStateVariables().put(key, (String) stateVariables.get(key));
					}
				}
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#customizePase(org.zkoss.zk
	 * .ui.Component, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void customizePase(final Component windowPase, String nextState) {
		String siguienteEstado = nextState;
		Map<String, Object> data = getSharedObject();
		data.put(ConstantesCommon.SCRIPT_WINDOW_PASE, windowPase);
		data.put(ConstantesCommon.SCRIPT_ID_TRANS_FFCC, "");
		setWinPase(windowPase);
		if (getForwardDesicion() != null && !getForwardDesicion().isEmpty()) {

			try {
				Map<String, Object> returnResult = WorkFlowScriptUtils.getInstance().executeScript(ScriptType.DECISION, ee, data, getUserLogged().getUsername());
				data = (Map<String, Object>) returnResult.get(ScriptUtils.DATA_KEY);
				siguienteEstado = (String) returnResult.get(ScriptUtils.RESULT_KEY);

			} catch (ScriptException e) {
				logger.warn("Error en GenericState.customizePase: ", e);
			}

			if (data != null) {
				Map<String, Object> bpm = (Map<String, Object>) data.get("BPM");
				if (bpm != null && !bpm.isEmpty()) {
					getTramitacionHelper().addVariables(getWorkingTask(), bpm); // agrego las variables
				}
			}
		}

		if (!isShowPassInfo()) {
			Grid grid = ZkUtil.findById(windowPase, "gridEstado");
			if(grid != null) {
				grid.setHeight("0px");
				grid = ZkUtil.findById(windowPase, "grillaDestino");
				grid.setHeight("0px");
			}
			Radio rUser = ZkUtil.findById(windowPase, "usuarioRadio");
			Radio rSector = ZkUtil.findById(windowPase, "sectorRadio");
			Radio rRepa = ZkUtil.findById(windowPase, "reparticionRadio");
			
			if (rUser != null && rSector != null && rRepa != null) {
				if (!rUser.isChecked() && !rSector.isChecked() && !rRepa.isChecked()) {
					rUser.setChecked(true);
				} 
			}
			
			Listbox cUser = ZkUtil.findById(windowPase, "listBoxUsuario");
			if(cUser != null) {
				//cUser.setValue("NONE");
			}
		}

		Combobox estadoCmb = ZkUtil.findById(windowPase, "estado");
		if(estadoCmb != null) {
			estadoCmb.setValue(siguienteEstado);
			//estadoCmb.setReadonly(true);
			//estadoCmb.setDisabled(true);
		}
		if (isRejecting()) {
			//estadoCmb.setValue(nextState);
			customizeReject(windowPase);
			return;
		}

		if (getTipoDocumentoFFCC() != null && !getTipoDocumentoFFCC().isEmpty()) {
			Button enviarBtn = ZkUtil.findById(windowPase, "enviar");
	
			final List<EventListener> events = ZkUtil.getListeners(enviarBtn, Events.ON_CLICK);
			ZkUtil.removeListeners(enviarBtn, Events.ON_CLICK);

			enviarBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
        public void onEvent(Event event) throws Exception {
          try {
            // Comentado por que en futuro desarrollo no todos los FFDD tendran
            // que generar documento.
            /*
            boolean generarDocPase = false;

            if (dBProperty == null) {
              dBProperty = (AppProperty) SpringUtil.getBean(ConstantesServicios.APP_PROPERTY);
            }

            if (dBProperty.getString("GENERAR_DOC_PASE") != null
                && "1".equals(dBProperty.getString("GENERAR_DOC_PASE"))) {
              generarDocPase = true;
            }

            if (generarDocPase) {
            */
          	if(windowPase!=null) {
          		Events.sendEvent(Events.ON_NOTIFY, windowPase, null);
          	}
          	
        	 createDocumentThroughFFCC();
            //}

            ZkUtil.raiseEvents(events, event);
          }catch (WrongValueException e) {
          	throw e;
          }
          catch (Exception e) {
            logger.error(e.getMessage());
            event.stopPropagation(); // detengo la propagacion del evento
          }
        }
			});
		}
	}
	
	public boolean validarFormularioDinamico() {

		
		if (getTipoDocumentoFFCC() != null && !getTipoDocumentoFFCC().isEmpty()
				 && getManager() != null) {
			try {
				if (ee != null && ee.getIdOperacion() != null) {
					this.idTransaction = getManager().saveValues(ee.getIdOperacion());
				} else {
					this.idTransaction = getManager().saveValues();
				}				
			} catch (WrongValueException e) {
				logger.error(e.getMessage(),e);
				
				Tabbox tabbox = ZkUtil.findById(componentTarget, "tabbox");
				Tabpanel tabPanel = ZkUtil.findById(componentTarget, "winNombramientoPanel");		
				Tab tabNombramiento = ZkUtil.findById(componentTarget, "controlNombramiento");
				
				if(tabbox!=null && tabPanel!=null && tabNombramiento!=null) {
					tabbox.setSelectedPanel(tabPanel);
					tabbox.setSelectedTab(tabNombramiento);
					Events.postEvent("onClick", tabNombramiento, null);
				}
				
//				Messagebox.show(e.getMessage(),
//						"Campos incompletos en el tab " + getTabName(),
//						Messagebox.OK, Messagebox.EXCLAMATION);
				throw e;
			}


		}
		
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#getFormVariables(org.zkoss
	 * .zk.ui.Component)
	 */
	@Override
	public Map<String, Object> getFormVariables(Component containerComponent) {
		if (!isRejecting()) {
			Gson gson = new Gson();
			emptyVariables.put(getKeyForPass(), gson.toJson(getUserLogged()));
		}
		return emptyVariables;
	}

	public Map<String, String> getStateVariables() {
		if (stateVariables == null) {
			stateVariables = new HashMap<String, String>();
		}
		return stateVariables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#toApply(ar.gob.gcaba.ee.
	 * satra.pl.helpers.states.IState)
	 */
	@Override
	public boolean toApply(IState state) {
		return state.getName().equals(getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#is(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean is(String workflowName, String stateName) {
		return isWorkflow(workflowName) && getName().equals(stateName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#isWorkflow(java.lang.
	 * String)
	 */
	@Override
	public boolean isWorkflow(String workflowName) {
		return (getWorkflowName().equals(ALL_WORKFLOW) || getWorkflowName().equalsIgnoreCase(workflowName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#isWorkflowEqual(java.lang.
	 * String)
	 */
	@Override
	public boolean isWorkflowEqual(String workflowName) {
		return getWorkflowName().equals(workflowName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#customizePase(org.
	 * zkoss.zul.Window)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void customizePase(final Window envio) {
		if (!isRejecting()) {
			Button enviarBtn = ZkUtil.findById(envio, "enviar");
			final List<EventListener> events = ZkUtil.getListeners(enviarBtn, Events.ON_CLICK);
			ZkUtil.removeListeners(enviarBtn, Events.ON_CLICK);

			enviarBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					event.stopPropagation();
					// ---- do something here, previouly raise events ---
					// --------------------------------------------------
					ZkUtil.raiseEvents(events, event);
				}
			});
		}

		String lastState = (getBackward() != null ? getBackward() : getLastState());
		customizePase(envio, (!isRejecting() ? getForward() : lastState));
	}

	/**
	 * Method to customize ENVIO window to do a REJECT operation
	 * 
	 * @param envio
	 */
	public void customizeReject(Component envio) {
		((Window) envio).setTitle("Rechazar expediente : " + getEe().getCodigoCaratula());

		Map<String, Object> data = getTramitacionHelper().getVariables(getWorkingTask().getExecutionId());
		String reparticion = "MGEYA";
		String sector = "MESI";

		// backward=null;
		if (data != null) {
			String lastValues = (String) data.get(getKeyForReject());
			if (lastValues != null && !lastValues.isEmpty()) {
				Gson gson = new GsonBuilder().registerTypeAdapter(GrantedAuthority.class, new CustomInstanceCreator())
						.create();
				Usuario dub = gson.fromJson(lastValues, Usuario.class);
				reparticion = dub.getCodigoReparticion();
				sector = dub.getCodigoSectorInterno();
			}
		}
		/*
		 * Radio sectorRadio = (Radio) envio.getFellow("sectorRadio"); Bandbox
		 * sectorReparticionBusquedaSadeBandBox = (Bandbox)
		 * envio.getFellow("sectorReparticionBusquedaSADE"); Bandbox
		 * sectorBusquedaSADEBandBox = (Bandbox)
		 * envio.getFellow("sectorBusquedaSADE");
		 * 
		 * sectorRadio.setChecked(true);
		 * sectorReparticionBusquedaSadeBandBox.setValue(reparticion);
		 * sectorBusquedaSADEBandBox.setValue(sector);
		 * Events.sendEvent(sectorRadio, new Event(Events.ON_CLICK));
		 */
		selectSector(envio, reparticion, sector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#generateDocumentOfPase(
	 * java.lang.String)
	 */
	@Override
	public void generateDocumentOfPase(String motive) {
		// TODO: Volver a habilitar cuando esten definidos los documentos de
		// PASE (designacion transitoria y definitiva)
		if (getAcronymPase().isEmpty())
			return;
		if (!isRejecting()) { // generate a interpass document, only if the reject flag is false
			// System.out.println("GenericState.generateDocumentOfPase()");
			getTramitacionHelper().vincularDocumentoPase(getWorkingTask(), motive, getAcronymPase(),
					getStateVariables());
		}
	}

	public String getTabName() {
		return "Designación";
	}

	
	public void componentStates(Component mainParent, boolean showTab) {
		
		this.componentTarget = mainParent;
		
		Tab tabNombramiento = ZkUtil.findById(mainParent, "controlNombramiento");
		tabNombramiento.setLabel(getTabName());
		tabNombramiento.setVisible(showTab);
		tabNombramiento.setFocus(true);
		Toolbarbutton tramitacionParaleloBtn = ZkUtil.findById(mainParent, "tramitacionParalelo");
		tramitacionParaleloBtn.setIconSclass("z-icon-random");
		tramitacionParaleloBtn.setLabel(Labels.getLabel("te.states.genericstate.btn.gestioncontrolada"));
		tramitacionParaleloBtn.setZclass("z-button");
		tramitacionParaleloBtn.setStyle("padding:7px 10px;");
		tramitacionParaleloBtn.setDisabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#drawMacro(org.zkoss.zul.
	 * Window, java.lang.String)
	 */
	@Override
	public void drawInclude(final Component includeComp) {
		// --- quito solapas de fusion y tramitacion conjunta ---
		final Window mainParent = ZkUtil.findParentByType(includeComp, Window.class);

		componentStates(mainParent, true);

		if (mainParent != null) {
			Tab tabFusion = ZkUtil.findById(mainParent, "expedienteFusion");
			Tab tabTramitacionConjunta = ZkUtil.findById(mainParent, "expedienteTramitacionConjunta");

			if (tabFusion != null) {
				tabFusion.setDisabled(false);
			}

			if (tabTramitacionConjunta != null) {
				tabTramitacionConjunta.setDisabled(false);
			}
		}
		// -----------------------------

		Include inc = (Include) includeComp;
		if (!getStateMacroURI().isEmpty()) {
			try {
				inc.setSrc(Utils.formatZulPath(getStateMacroURI()));
	
				setParentContainer((Component) ZkUtil.findById(inc, getWindowId()));
	
				for (Object obj : inc.getChildren()) {
					if (obj instanceof Window) {
						for (Object objx : ((Window) obj).getChildren()) {
							if (objx instanceof IDelegable) {
								((IDelegable) objx).setCallbackObject(this);
								((IDelegable) objx).initialize(); // execute this
																	// method,
																	// because the
																	// doAfterComposer
																	// method was
																	// invoked
																	// previously
							}
						}
					}
				}
			} catch (Exception e){
				
			}
		}
		//INI - solicitud : quitar boton pase interno
		//drawPaseInternoBtn(includeComp); // TODO: Habilitarlo despues cuando
											// este el icono para el boton
		drawTramitacionLibreBtn(includeComp); // Boton para Tramitacion Libre
		String formularioControladoName = "";

		// ------- si existe FFCC lo cargo ----------
		if (getTipoDocumentoFFCC() != null && !getTipoDocumentoFFCC().isEmpty()) {
			try {
				setFormManagerFact(getTramitacionHelper().getFormManagerFactory());
				Div ffccContainer = ZkUtil.findById(getParentContainer(), "genericStateDiv");

				ResponseTipoDocumento tipoDocumento = getTramitacionHelper().getExternalTipoDocumento()
						.consultarTipoDocumentoPorAcronimo(getTipoDocumentoFFCC());

				if (tipoDocumento != null && tipoDocumento.getIdFormulario() != null) {
					formularioControladoName = tipoDocumento.getIdFormulario();
					try {
						setManager(getFormManagerFact().create(formularioControladoName));
					} catch (Exception e) {
						Messagebox.show(
								Labels.getLabel("te.formulario.error.acceder") + formularioControladoName + "]",
								Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.ERROR, new EventListener<Event>() {
									@Override
									public void onEvent(Event event) throws Exception {
										mainParent.detach();
									}
								});
					}
					
					Component comp = getManager().getFormComponent();
					comp.setParent(ffccContainer);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		// ------- obtengo los datos de las variables --------
		Map<String, Object> vals = getTramitacionHelper().getVariables(getWorkingTask().getExecutionId());
		if (vals != null && !vals.isEmpty()) {
			String valores = (String) vals.get("form_values");
			if (valores != null && !valores.isEmpty()) {
				// System.out.println("Saved values -> "+valores);
				ZkUtil.setJsonValues(includeComp, valores);
				ZkUtil.setValuesFFCC(getManager(), ZkUtil.jsonToMap(valores));
			}
		}

		if (this.getEe().getSistemaCreador().equals("TAD")) {
			// Window mainParent =
			// ZkUtil.findParentByType(getParentContainer().getParent(),
			// Window.class);
			Toolbarbutton comunicarTadButton = ZkUtil.findById(mainParent, "comunicarTadButton");
			if (comunicarTadButton != null) {
				comunicarTadButton.setDisabled(false);
			}
		}

		// Initialize the state
		initState();
	}

	public void initState() {
		if (getInitialize() != null && !getInitialize().isEmpty()) {
			try {
				String user = "";
				if(getUserLogged()  != null){
					user = getUserLogged().getUsername();
				}
				WorkFlowScriptUtils.getInstance().executeScript(ScriptType.INITIATION,ee, getSharedObject(), 
						user);
			} catch (ScriptException e) {
				String message = "";
				logger.error("Error execute init script",e);
				if (e.getMessage() != null) {
					 String[] ss = e.getMessage().split(":");
					 if (ss.length > 0)
						 message = ss[ss.length - 1];
				}
				Messagebox.show(message);
			}
		}
	}

	protected void drawTramitacionLibreBtn(final Component includeComp) {
		final Window winContainer = ZkUtil.findParentByType(includeComp, Window.class);
		Toolbarbutton btnTramitacion = ZkUtil.findById(winContainer, "tramitacionParalelo");

		if (!isAcceptTramitacionLibre()) { // si no acepta el pase libre
											// entonces lo deshabilito
			btnTramitacion.setDisabled(true);
			return;
		}

		ZkUtil.removeListeners(btnTramitacion, Events.ON_CLICK);

		btnTramitacion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				event.stopPropagation();

				final HashMap<String, Object> hma = new HashMap<>();

				if (!getWorkingTask().getActivityName().equalsIgnoreCase(TRAMITACION_LIBRE)) {
					hma.put(ESTADO_PREVIO_TL_KEY, getWorkingTask().getActivityName());
					hma.put(USUARIO_PREVIO_TL_KEY, getUserLogged().getUsername());
					getTramitacionHelper().addVariables(getWorkingTask(), hma);
					hma.clear();
				}

				hma.put("expedienteElectronico", getEe());

				// Mensaje de aviso cuando se pulsa el boton de tramitacion
				// libre
				Messagebox.show(
						Labels.getLabel("te.formulario.tramitacionLibre"),
						Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.INFORMATION, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {

								Window envio = (Window) Executions.createComponents("/pase/envio.zul", null, hma);

								envio.setParent(winContainer);
								envio.setPosition("center");
								envio.setClosable(true);
								envio.doModal();

								Combobox estadoCmb = (Combobox) envio.getFellow("estado");
								estadoCmb.setValue(TRAMITACION_LIBRE);
								estadoCmb.setReadonly(true);
								estadoCmb.setDisabled(true);
							}
						});

			}
		});
	}

	@SuppressWarnings("unused")
	private void drawPaseInternoBtn(final Component includeComp) {
		final Window winContainer = ZkUtil.findParentByType(includeComp, Window.class);
		Hbox botonera = ZkUtil.findById(winContainer, "pie");
		Toolbarbutton cerrarButton = new org.zkoss.zul.Toolbarbutton();
		cerrarButton.setIconSclass("z-icon-exchange");
		cerrarButton.setTabindex(10);
		cerrarButton.setStyle("padding:7px 10px;");
		cerrarButton.setId("paseInternoBtn");
		cerrarButton.setZclass("z-button");
		cerrarButton.setLabel(Labels.getLabel("te.states.genericstate.btn.paseinterno"));
		cerrarButton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event paramEvent) throws Exception {
				asignarPaseInternoAUsuario(includeComp);
			}
		});
		cerrarButton.setParent(botonera);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void asignarPaseInternoAUsuario(final Component includeComp) {
		final Window openModalWindow = (Window) Executions
				.createComponents("/common/componentes/paseInternoUsuario.zul", null, null);
		final Window mainParent = ZkUtil.findParentByType(includeComp, Window.class);

		Label repSect = ZkUtil.findById(openModalWindow, "reparticionSector");
		final String reparticion = getUserLogged().getCodigoReparticion();
		final String sectorInterno = getUserLogged().getCodigoSectorInterno();

		repSect.setValue(reparticion + " - " + sectorInterno);

		List<Usuario> lst = getTramitacionHelper().getUsuariosSADEService()
				.getTodosLosUsuariosXReparticionYSectorEE(reparticion, sectorInterno);

		final Listbox lstUsuarios = ZkUtil.findById(openModalWindow, "lstUsuarios");
		final Checkbox enviarBuzonGrupal = ZkUtil.findById(openModalWindow, "enviarBuzonGrupal");
		lstUsuarios.setModel(new SimpleListModel(lst));

		Button btnAceptar = ZkUtil.findById(openModalWindow, "btnAceptar");
		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String msg = "";

				if (enviarBuzonGrupal.isChecked()) {
					returnEEToInbox(mainParent, includeComp);
					msg = String.format("Expediente fue enviado al buzon grupal: %s - %s", reparticion, sectorInterno);
				} else {
					if (lstUsuarios.getSelectedItem() == null) {
						msg = "Debe seleccionar usuario a quien derivar el pedido";
						Messagebox.show(msg, Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.EXCLAMATION, Messagebox.OK);
						return;
					}

					Usuario selectedUser = (Usuario) lstUsuarios.getSelectedItem().getValue();

					getTramitacionHelper().getProcessEngine().getTaskService().assignTask(getWorkingTask().getId(),
							selectedUser.getUsername());
					saveInputData(includeComp);
					msg = String.format("Se asigno el expediente al usuario [ %s ]", selectedUser.toString());
				}

				Messagebox.show(msg, Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.EXCLAMATION, Messagebox.OK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								Events.sendEvent(Events.ON_NOTIFY, mainParent.getParent(), null);
								mainParent.detach();
							}
						});
				openModalWindow.detach();
			}
		});

		Button btnCancelar = ZkUtil.findById(openModalWindow, "btnCancelar");
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				openModalWindow.detach();
			}
		});

		openModalWindow.doModal();
	}

	/**
	 * Metodo para devolver los datos del formulario controlado;
	 * 
	 * @param manager
	 * @return
	 */
	public Map<String, Object> getValueFFCC() {
		return ZkUtil.getValuesFFCC(getManager());
	}

	public void returnEEToInbox(Window winContainer, Component includeComp) {
		Task selectedTask = getWorkingTask();
		String grupoSeleccionado = String.format("%s-%s", getUserLogged().getCodigoReparticion(),
				getUserLogged().getCodigoSectorInterno());
		getTramitacionHelper().getProcessEngine().getTaskService().addTaskParticipatingGroup(selectedTask.getId(),
				grupoSeleccionado, Participation.CANDIDATE);
		getTramitacionHelper().getProcessEngine().getTaskService().assignTask(selectedTask.getId(), null);

		if (selectedTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
			TareaParaleloDTO tareaParalelo = getTramitacionHelper().getTareaParaleloService()
					.buscarTareaEnParaleloByIdTask(selectedTask.getExecutionId());
			tareaParalelo.setEstado("Pendiente");
			tareaParalelo.setUsuarioGrupoAnterior(getUserLogged().getUsername());
			tareaParalelo.setTareaGrupal(true);
			getTramitacionHelper().getTareaParaleloService().modificar(tareaParalelo);
		}

		saveInputData(includeComp);
	}

	public void saveInputData(Component includeComp) {
		String values = ZkUtil.getJsonValues(includeComp, getValueFFCC());
		// Events.sendEvent(winContainer, new Event(Events.ON_CLOSE));

		if (values != null && !values.isEmpty()) {
			Map<String, Object> vals = new HashMap<String, Object>();
			vals.put("form_values", values);
			getTramitacionHelper().addVariables(getWorkingTask(), vals);
		}

		getTramitacionHelper().getExpedienteElectronicoService().modificarExpedienteElectronico(getEe());
	}

	public ExpedienteElectronicoDTO loadLinkedExpedient() {
		return getTramitacionHelper().getExpedienteAsociado(getEe(), 0);
	}

	public Map<String, Object> getDatosCaratulaVariable(List<DocumentoDTO> list) {
		
		if (null != list && !list.isEmpty()) {
		for (DocumentoDTO doc : list) {
			String motivo = StringUtils.stripAccents(doc.getMotivo());
			if ("caratula".equalsIgnoreCase(motivo)  && doc.getIdTransaccionFC() != null){
				ResponseExternalConsultaDocumento response = null;
				try {
					response = getTramitacionHelper().getDocumentacionGedoService()
							.consultarDocumentoPorNumero(doc.getNumeroSade(), getUserLogged().getUsername());

					if (response.getIdTransaccion() != null) {
						TipoDocumentoDTO tp = getTramitacionHelper().getTipoDocumentoService()
								.obtenerTipoDocumento(response.getTipoDocumento());

						if (tp != null && tp.getIdFormulario() != null) {
							IFormManager<Component> manager = null;

							try {
								manager = getTramitacionHelper().getFormManagerFactory().create(tp.getIdFormulario());
								if (manager != null) {
								  if(ee != null && ee.getIdOperacion() != null){
								    manager.fillCompValues(ee.getIdOperacion(), Integer.valueOf(response.getIdTransaccion()));
								  }else{
								    manager.fillCompValues(Integer.valueOf(response.getIdTransaccion()));
								  }
									return FormularioControladoUtils.getFFCCValues(manager);
								}
							} catch (Exception e) {
							}

						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				// getEe().getDocumentos().get(1).getIdTransaccionFC()
			}
		}
		}
		return null;
	}

	public Map<String, Object> getDatosCaratulaVariable() {
		if(getEe() != null){
			return getDatosCaratulaVariable(getEe().getDocumentos());
		}
		return null;
	}

	/**
	 * Method to shared object in script execution
	 * 
	 * @return
	 */
	protected Map<String, Object> getSharedObject() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ee", getEe());
		data.put("state", this);
		data.put("tramitacionHelper", getTramitacionHelper());
		data.put("parentContainer", getParentContainer());
		data.put("usuario", getUserLogged());
		//data.put("VARS", new HashMap<String, Object>());

		Map<String, Object> ZUL = ZkUtil.getValues(getParentContainer());
		if (ZUL != null && !ZUL.isEmpty()) {
			data.put("ZUL", ZUL);
		}

		Map<String, Object> CV = getDatosCaratulaVariable(); // caratula
																// variable
		if (CV == null) {
			CV = new HashMap<>();
		}
		
		data.put("CV", CV);
		if(this.getWorkingTask() != null){
			data.put("BPM", getTramitacionHelper().getVariables(this.getWorkingTask().getExecutionId()));
		}
		
		if (getManager() != null && getTipoDocumentoFFCC() != null && !getTipoDocumentoFFCC().isEmpty()) {
			data.put("FFCC", FormularioControladoUtils.getFFCCValues(getManager()));
		}
		
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#startReject()
	 */
	@Override
	public void startReject() {
		this.rejecting = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#stopReject()
	 */
	@Override
	public void stopReject() {
		this.rejecting = false;
	}

	/**
	 * @return the rejecting
	 */
	public boolean isRejecting() {
		return rejecting;
	}

	/**
	 * @return the parentContainer
	 */
	public Component getParentContainer() {
		return parentContainer;
	}

	/**
	 * @param parentContainer
	 *            the parentContainer to set
	 */
	public void setParentContainer(Component parentContainer) {
		this.parentContainer = parentContainer;
	}

	/**
	 * Method to get the Key to realize and save the pass
	 * 
	 * @return String
	 */
	protected String getKeyForPass() {
		return USER_KEY + getName().replace(" ", "_");
	}

	/**
	 * Method to get the Key to realize and save the reject
	 * 
	 * @return String
	 */
	protected String getKeyForReject() {
		String lastState = (getBackward() != null ? getBackward() : getLastState());
		return USER_KEY + lastState.replace(" ", "_");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#getParallelMinUsers()
	 */
	@Override
	public Integer getParallelMinUsers() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#getParallelMaxUsers()
	 */
	@Override
	public Integer getParallelMaxUsers() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#getParallelMinSector()
	 */
	@Override
	public Integer getParallelMinSector() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#getParallelMaxSector()
	 */
	@Override
	public Integer getParallelMaxSector() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.IState#isParallelExclusive()
	 */
	public boolean isParallelExclusive() {
		return true;
	}

	/**
	 * Method to select sector option, to set reparticion and sector in
	 * customized windows of a pass
	 * 
	 * @param container
	 * @param reparticion
	 * @param sector
	 * @return
	 */
	public Radio selectUsuario(Component container, String usuario) {
		Radio usuarioRadio = ZkUtil.findById(container, "usuarioRadio");
		usuarioRadio.setChecked(true);
		Bandbox bandbox = ZkUtil.findById(container, "bandBoxUsuario");
		if(bandbox != null) {
				Usuario usuarioBean = getTramitacionHelper().getUsuariosSADEService().getDatosUsuario(usuario);
				logger.info("User find {}", usuarioBean);
  				UsuarioReducido data = new UsuarioReducido();
				data.setUsername(usuarioBean.getUsername());
				data.setNombreApellido(usuarioBean.getNombreApellido());
				final Event event = new Event(BandBoxUsuarioComposer.ON_SELECT_USUARIO, container, data);
				Events.postEvent(event);
				bandbox.setValue(usuario);
		}
		Events.sendEvent(usuarioRadio, new Event(Events.ON_CLICK));
		return usuarioRadio;
	}

	/**
	 * Method to select sector option, to set reparticion and sector in
	 * customized windows of a pass
	 * 
	 * @param container
	 * @param reparticion
	 * @param sector
	 * @return
	 */
	public Radio selectSector(Component container, String reparticion, String sector) {
		Radio sectorRadio = ZkUtil.findById(container, "sectorRadio");
		Bandbox bandboxOrganismo = ZkUtil.findById(container, "bandboxOrganismo");
		Bandbox bandBoxSector = ZkUtil.findById(container, "sectorBusquedaSADE");
		
		if(sectorRadio != null) {
			sectorRadio.setChecked(true); 
			sectorRadio.setValue(sector);
			bandboxOrganismo.setValue(reparticion);
			bandBoxSector.setValue(sector);
			ReparticionServ reparticionServ = (ReparticionServ) SpringUtil.getBean(ConstantesServicios.REPARTICION_SERVICE);
			ReparticionBean reparticionBean = reparticionServ.buscarReparticionPorCodigo(reparticion);
			final Event event = new Event(BandboxOrganismoComposer.ON_SELECT_ORGANISMO, 
					container, reparticionBean);
			Events.postEvent(event);
			Events.sendEvent(sectorRadio, new Event(Events.ON_CLICK));
		}
		return sectorRadio;
	}

	/**
	 * Method to select reparticion option, to set reparticion in customized
	 * windows of a pass
	 * 
	 * @param container
	 * @param reparticion
	 * @param sector
	 * @return
	 */
	public Radio selectReparticion(Component container, String reparticion) {
		Radio reparticionRadio = ZkUtil.findById(container, "reparticionRadio");
		Bandbox reparticionBusquedaSadeBandBox = ZkUtil.findById(container, "reparticionBusquedaSADE");

		reparticionRadio.setChecked(true);
		reparticionBusquedaSadeBandBox.setValue(reparticion);

		Events.sendEvent(reparticionRadio, new Event(Events.ON_CLICK));

		return reparticionRadio;
	}

	protected void refreshAcordeon(Component container) {
		Map<String, Object> dataHm = new HashMap<String, Object>();
		dataHm.put("expedienteElectronico", getEe());
		dataHm.put("inicial", false);
		Events.sendEvent(Events.ON_RENDER, container, dataHm);
	};

	/**
	 * Method to enable the document's linker
	 * 
	 * @param title
	 *            String title of the linker
	 */
	public void enableDocumentLinker(String title) {
		Div linkerContainer = ZkUtil.findById(getParentContainer(), "documentLinker");
		Toolbarbutton buscarDocumentoBtn = ZkUtil.findById(getParentContainer(), "buscarDocumentoButton");
		Label titleComp = ZkUtil.findById(getParentContainer(), "documentLinkerTitle");

		linkerContainer.setVisible(true);
		titleComp.setValue(title);

		if (buscarDocumentoBtn != null) {
			buscarDocumentoBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event paramEvent) throws Exception {
					vincularDocumento(getParentContainer());

					Component comp = ZkUtil.findParentByType(getParentContainer().getParent(), Window.class);
					if (comp != null) {
						DocumentViewerComponent docViewer = ZkUtil.findById(comp, "docAsociado");
						docViewer.initialize();
					}
				}
			});
		}
	}

	/**
	 * Method to link documents to the expedient
	 * 
	 * @param container
	 */
	protected void vincularDocumento(final Component container) {
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

		String anioFormateado = yearFormat.format(Dates.today());
		Integer anioValido = new Integer(anioFormateado);

		final Bandbox tiposDocumentoBandbox = (Bandbox) container.getFellow("tiposDocumentoBandbox");
		final Intbox anioSADEDocumento = (Intbox) container.getFellow("anioSADEDocumento");
		final Intbox numeroSADEDocumento = (Intbox) container.getFellow("numeroSADEDocumento");
		final Bandbox reparticionBusquedaDocumento = (Bandbox) container.getFellow("reparticionBusquedaDocumento");

		if ((tiposDocumentoBandbox.getValue() == null) || (tiposDocumentoBandbox.getValue().equals(""))) {
			throw new WrongValueException(tiposDocumentoBandbox, Labels.getLabel("ee.tramitacion.faltaTipoDocumento"));
		}

		if ((anioSADEDocumento.getValue() == null)) {
			throw new WrongValueException(anioSADEDocumento, Labels.getLabel("ee.tramitacion.faltaAnio"));
		}

		if (anioSADEDocumento.getValue() > anioValido) {
			throw new WrongValueException(anioSADEDocumento, Labels.getLabel("ee.tramitacion.anioInvalido"));
		}

		if ((numeroSADEDocumento.getValue() == null) || (numeroSADEDocumento.getValue().equals(""))) {
			throw new WrongValueException(numeroSADEDocumento,
					Labels.getLabel("ee.tramitacion.faltaNumeroDeDocumento"));
		}

		if ((reparticionBusquedaDocumento.getValue() == null) || (reparticionBusquedaDocumento.getValue().equals(""))) {
			throw new WrongValueException(reparticionBusquedaDocumento,
					Labels.getLabel("ee.tramitacion.faltaReparticion"));
		}

		// -----------------------------------------------------

		String acronimo = tiposDocumentoBandbox.getValue();
		acronimo = acronimo.substring(acronimo.indexOf("-") + 2);
		String actuacionSADETipoDoc = (String) acronimo;
		Integer anioDocumento = anioSADEDocumento.getValue();
		Integer numeroDocumento = numeroSADEDocumento.getValue();
		String reparticionDocumento = (String) reparticionBusquedaDocumento.getValue().trim();

		getTramitacionHelper().vincularDocumento(this.getWorkingTask(), actuacionSADETipoDoc, anioDocumento,
				numeroDocumento, reparticionDocumento, new TramitacionHelper.CallbackHandler() {
					@Override
					public void onSuccess(Object... event) {

						refreshAcordeon(container);

						String message = (String) event[0];
						String title = (String) event[1];
						Messagebox.show(message, title, Messagebox.OK, Messagebox.INFORMATION);
					}

					@Override
					public void onException(Exception e, Object event) {
						if (e instanceof WrongValueException) {
							throw new WrongValueException(tiposDocumentoBandbox,
									"Tipo de Documento No habilitado para la trata. Verifique los datos ingresados.");
						}
					}

					@Override
					public void onError(Object... event) {
						String message = (String) event[0];
						String title = (String) event[1];
						Messagebox.show(message, title, Messagebox.OK, Messagebox.INFORMATION);
					}
				});

		tiposDocumentoBandbox.setValue("");
		anioSADEDocumento.setValue(null);
		numeroSADEDocumento.setValue(null);
		reparticionBusquedaDocumento.setValue("");
	}

	/**
	 * @return the notificationsToSend
	 */
	public List<DocumentoDTO> getNotificationsToSend() {
		if (notificationsToSend == null) {
			notificationsToSend = new ArrayList<DocumentoDTO>();
		}
		return notificationsToSend;
	}

	public String getFilterNotificaciones() {
		return filterNotificaciones;
	}

	/**
	 * @param filterNotificaciones
	 *            the filterNotificaciones to set
	 */
	public void setFilterNotificaciones(String filterNotificaciones) {
		this.filterNotificaciones = filterNotificaciones;
	}

	/**
	 * Method to filter documents and select
	 * 
	 * @param parentWindow
	 * @param loadMethodName
	 * @param onCloseListener
	 * @throws InterruptedException
	 */
	public void openNotificacionWindow(Component parentWindow, String title, String loadMethodName,
			final EventListener<Event> onCloseListener) throws InterruptedException {

		if (!validateBeforePass())
			return;

		final Window openModalWindow = (Window) Executions
				.createComponents("/expediente/macros/popupNotificaciones.zul", null, null);

		if (title != null && !title.isEmpty()) {
			openModalWindow.setTitle(title);
		}

		DocumentViewerComponent docViewer = ZkUtil.findById(openModalWindow, "docAsociado");
		docViewer.setDynamicProperty("filter", "getFilterNotificaciones");
		if (loadMethodName != null && !loadMethodName.isEmpty()) {
			docViewer.setDynamicProperty("loadMethod", loadMethodName);
		}
		docViewer.setCallbackObject(this);
		docViewer.initialize();

		Button btnAceptar = ZkUtil.findById(openModalWindow, "btnAceptar");
		Button btnCancelar = ZkUtil.findById(openModalWindow, "btnCancelar");

		btnAceptar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event paramEvent) throws Exception {
				DocumentViewerComponent docViewer = ZkUtil.findById(openModalWindow, "docAsociado");
				getNotificationsToSend().addAll(docViewer.getSelectedDocuments());
				openModalWindow.detach();
				onCloseListener.onEvent(paramEvent);
			}
		});

		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event paramEvent) throws Exception {
				getNotificationsToSend().clear();
				Events.sendEvent(openModalWindow, new Event(Events.ON_CLOSE));

				onCloseListener.onEvent(paramEvent);
			}
		});

		openModalWindow.doModal();
	}

	/**
	 * Wrapped method
	 * 
	 * @see GenericState#openNotificacionWindow(Component, String,
	 *      EventListener)
	 * 
	 * @param parentWindow
	 * @param title
	 * @param onCloseListener
	 * @throws InterruptedException
	 */
	public void openNotificacionWindow(Component parentWindow, String title, final EventListener<Event> onCloseListener)
			throws InterruptedException {
		openNotificacionWindow(parentWindow, title, null, onCloseListener);
	}

	/**
	 * Wrapped method
	 * 
	 * @see GenericState#openNotificacionWindow(Component, String,
	 *      EventListener)
	 * 
	 * @param parentWindow
	 * @param onCloseListener
	 * @throws InterruptedException
	 */
	public void openNotificacionWindow(Component parentWindow, final EventListener<Event> onCloseListener)
			throws InterruptedException {
		openNotificacionWindow(parentWindow, null, null, onCloseListener);
	}

	public void createDocumentThroughFFCC() throws Exception {
		// if the result of the validation is OK, try to generate the FFCC
		//Integer idTransaction = null;
		if (getTipoDocumentoFFCC() != null && !getTipoDocumentoFFCC().isEmpty() && getManager() != null) {
//		  if(ee != null && ee.getIdOperacion() != null){
//			      idTransaction = getManager().saveValues(ee.getIdOperacion());
//		  }else{
//		       idTransaction = getManager().saveValues();
		       
		       //Si no estamos en una operacion aqui tendremos que vincular la transaccion al expediente
		       //para que aparezca en la pestaña Formularios. De esta manera podremos tener tareas como formularios que no 
		       //terminen en un documento pero que quede como informacion vinculada al expediente.
		       //Ver servicio IExpedienteFormularioService
//		      }
			if (idTransaction != null) {
				getTramitacionHelper().createDocument(getTipoDocumentoFFCC(), idTransaction, getEe(),
						getUserLogged().getUsername());
			}
			
			Textbox tbxTransaction = (Textbox) getWinPase().getFellow("tbxIdTransaction");
			if(idTransaction != null)
				tbxTransaction.setValue(idTransaction.toString());
			
			Textbox tbx = (Textbox) getWinPase().getFellow("tbxNameForm");
			tbx.setValue(getTipoDocumentoFFCC());
			
		}
	}

	
	public Map<DocumentoDTO, TipoDocumentoDTO> getDocumentMaps(ExpedienteElectronicoDTO ee, boolean ascendingSort) {
		final Map<DocumentoDTO, TipoDocumentoDTO> documentMaps = new LinkedHashMap<DocumentoDTO, TipoDocumentoDTO>();

		List<DocumentoDTO> documentos = new ArrayList<DocumentoDTO>(ee.getDocumentos().size());
		// Collections.copy(documentos, ee.getDocumentos());
		documentos.addAll(ee.getDocumentos());

		// --- sort the document's collections ---
		Comparator<DocumentoDTO> ascending = new Comparator<DocumentoDTO>() {
			@Override
			public int compare(DocumentoDTO o1, DocumentoDTO o2) {
				return o1.getFechaAsociacion().compareTo(o2.getFechaAsociacion());
			}
		};
		Comparator<DocumentoDTO> descending = new Comparator<DocumentoDTO>() {
			@Override
			public int compare(DocumentoDTO o1, DocumentoDTO o2) {
				return o2.getFechaAsociacion().compareTo(o1.getFechaAsociacion());
			}
		};

		Collections.sort(documentos, (ascendingSort ? ascending : descending));

		// --- link Documento <-> TipoDocumento ---
		for (DocumentoDTO doc : documentos) {
			try {
				ResponseExternalConsultaDocumento response = getTramitacionHelper().getDocumentacionGedoService()
						.consultarDocumentoPorNumero(doc.getNumeroSade(), getUserLogged().getUsername());
				if (response != null) {
					TipoDocumentoDTO tipoDocumento = getTramitacionHelper().getTipoDocumentoService()
							.obtenerTipoDocumento(response.getTipoDocumento());
					if (tipoDocumento != null) {
						documentMaps.put(doc, tipoDocumento);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		return documentMaps;
	}

	public TipoDocumentoDTO getTipoDocumentoByFFCC(ExpedienteElectronicoDTO ee, String FFCCName) {
		Map<DocumentoDTO, TipoDocumentoDTO> documentos = getDocumentMaps(ee, false);

		for (TipoDocumentoDTO tipoDocumento : documentos.values()) {
			if (tipoDocumento.getIdFormulario() != null && !tipoDocumento.getIdFormulario().isEmpty()
					&& tipoDocumento.getIdFormulario().equalsIgnoreCase(FFCCName)) {
				return tipoDocumento;
			}
		}

		return null;
	}

	public void disableButton(Button component) {
		component.setDisabled(true);
	}

	@Override
	public String toString() {
		return "GenericState ["

				+ (getStateMacroURI() != null ? "getStateMacroURI()=" + getStateMacroURI() + ", " : "")
				+ (getName() != null ? "getName()=" + getName() + ", " : "")
				+ (getWorkflowName() != null ? "getWorkflowName()=" + getWorkflowName() : "") + "]";
	}

	public boolean existDocumentByAcronymous(String acronymous) {
		Map<DocumentoDTO, TipoDocumentoDTO> documentos = getDocumentMaps(this.getEe(), false);

		for (Map.Entry<DocumentoDTO, TipoDocumentoDTO> data : documentos.entrySet()) {
			if (data.getValue().getAcronimo().equalsIgnoreCase(acronymous))
				return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.gob.gcaba.ee.common.workflow.IState#isValid()
	 */
	@Override
	public boolean isValid() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.gob.gcaba.ee.common.workflow.IState#takeDecision()
	 */
	@Override
	public Object takeDecision() {

		return null;
	}

	public String getStartScript() {
		return startScript;
	}

	public void setStartScript(String startScript) {
		this.startScript = startScript;
	}

	public String getScriptFuseTask() {
		return scriptFuseTask;
	}

	public void setScriptFuseTask(String scriptFuseTask) {
		this.scriptFuseTask = scriptFuseTask;
	}

	public String getScriptFuseGeneric() {
		return scriptFuseGeneric;
	}

	public void setScriptFuseGeneric(String scriptFuseGeneric) {
		this.scriptFuseGeneric = scriptFuseGeneric;
	}
	
	public String getScriptStartState() {
		return scriptStartState;
	}

	public void setScriptStartState(String scriptStartState) {
		this.scriptStartState = scriptStartState;
	}

	public String getScriptEndState() {
		return scriptEndState;
	}

	public void setScriptEndState(String scriptEndState) {
		this.scriptEndState = scriptEndState;
	}

	@Override
  public Map<String, Object> execScript(ScriptType type, Map<String, Object> params) throws ScriptException {
    Map<String, Object> response = null;
    Map<String, Object> parameters = params;
    
    if (parameters == null) {
      parameters = new HashMap<>();
    }
    
    parameters.putAll(getSharedObject());
    
    switch (type) {
      case START:
        if (!StringUtils.isBlank(getStartScript()))
          response = ScriptUtils.executeScript(getStartScript(), parameters, "startScript");
        break;
      case INITIATION:
        if (!StringUtils.isBlank(getInitialize()))
          response = ScriptUtils.executeScript(getInitialize(), parameters, "initialize");
        break;
      case VALIDATION:
        if (!StringUtils.isBlank(getForwardValidation()))
          response = ScriptUtils.executeScript(getForwardValidation(), parameters, "forwardValidation");
        break;
      case DECISION:
        if (!StringUtils.isBlank(getForwardDesicion()))
          response = ScriptUtils.executeScript(getForwardDesicion(), parameters, "forwardDesicion");
        break;
      case REMOTE_TASK:
        if (!StringUtils.isBlank(getScriptFuseTask()))
          response = ScriptUtils.executeScript(getScriptFuseTask(), parameters, "scriptFuseTask");
        break;
      case REMOTE_GENERIC:
        if (!StringUtils.isBlank(getScriptFuseGeneric()))
          response = ScriptUtils.executeScript(getScriptFuseGeneric(), parameters, "scriptFuseGeneric");
        break;
      case START_STATE:
          if (!StringUtils.isBlank(getScriptStartState()))
            response = ScriptUtils.executeScript(getScriptStartState(), parameters, "scriptStartState");
          break;
      case END_STATE:
          if (!StringUtils.isBlank(getScriptEndState()))
            response = ScriptUtils.executeScript(getScriptEndState(), parameters, "scriptEndState");
          break;
      default:
        break;
    }
    
    return response;
  }
  
  @Override
  public Map<String, Object> execScript(String script, Map<String, Object> params) throws ScriptException {
    Map<String, Object> parameters = params;
    
    if (parameters == null) {
      parameters = new HashMap<>();
    }
    
    parameters.putAll(getSharedObject());
    
    return ScriptUtils.executeScript(script, parameters);
  }

	public Component getWinPase() {
		return winPase;
	}

	public void setWinPase(Component winPase) {
		this.winPase = winPase;
	}
	
	
	
}
