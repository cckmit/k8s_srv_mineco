package com.egoveris.te.base.util;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.DocumentoManagerService;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TipoDocumentoDAO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.ITomaVistaService;
import com.egoveris.te.web.ee.satra.pl.helpers.states.IVisualState;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.terasoluna.plus.core.util.ApplicationContextUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;


/**
 * @author difarias
 *
 */

public class TramitacionHelper implements ApplicationContextAware {
	
	private static Logger logger = LoggerFactory.getLogger(TramitacionHelper.class);
	private static final String WORKFLOW_VERSION = "34";
	private static final String TRAMITACION_EN_PARALELO = "Paralelo";
	private static final String REFERENCIA_DOCUMENTO_PASE = "referenciaDocumentoPase";
	private List<IVisualState> visualStates;
	
	public interface CallbackHandler {
		public void onSuccess(Object... event);
		public void onException(Exception e, Object event);
		public void onError(Object... event);
	}

	public enum WORKFLOW_TABS {
		TRAMITACION(),
		NOMBRAMIENTO(new String[]{
				"documentos",
				"documentosdetrabajo",
				"asociarexpediente",
			    "expedienteTramitacionConjunta",
			    "expedienteFusion",
			    "datosdelacaratula",
			    "controlNombramiento"
		});
		
		private boolean useAllTabs;
		private String[] tabs;
		
		WORKFLOW_TABS() {
			useAllTabs=true;
		}
		
		WORKFLOW_TABS(String[] tabsname) {
			useAllTabs = false;
			tabs = Arrays.copyOf(tabsname, tabsname.length);
		}

		/**
		 * @return the tabs
		 */
		public String[] getTabs() {
			if (tabs == null) {
				tabs = new String[] {};
			}
			return tabs;
		}

		/**
		 * @param tabs the tabs to set
		 */
		public void setTabs(String[] tabs) {
			this.tabs = tabs;
		}
		
		/**
		 * Method to get a workflow by the name
		 * @param workflowName
		 * @return
		 */
		public static final WORKFLOW_TABS getByName(String workflowName) {
			for (WORKFLOW_TABS wf : values()) {
				if (wf.name().equalsIgnoreCase(workflowName))
					return wf;
			}
			
			return TRAMITACION;
		}

		/**
		 * Method to set tabs enabled
		 * @param composer
		 */
		public final void prepareTabs(Component comp) {
			List<Component> list = comp.getChildren();
			boolean isFirst = true;
			boolean isEnabled = false;
			
			for (Component child : list) {
				if ("zul.tab.Tabbox".equals(child.getWidgetClass()) || "zul.tab.Tabs".equals(child.getWidgetClass())) {
					prepareTabs(child);
				}
		    	
		    	if ("zul.tab.Tab".equals(child.getWidgetClass())) {
	    			Tab tab = (Tab) child;
	    			tab.setDisabled(true);
	    			
		    		if (this.useAllTabs) {
		    			tab.setDisabled(false);
		    			isEnabled=true;
		    		} else if (getTabs()!=null) {
						for (int c = 0; c < getTabs().length; c++) {
		    				String tabname = getTabs()[c];
		    				if (child.getId().equalsIgnoreCase(tabname)) {
				    			isEnabled=true;
		    					break;
		    				}
		    			}
		    		}
		    		
					if (isFirst && isEnabled) {
						tab.setSelected(true);
						isFirst=false;
		    		}
		    	}
		    }
		}		
	}
	
	private ProcessEngine processEngine;
	private IVisualState activeState;
	private ExpedienteElectronicoDTO ee;
	private DocumentoManagerService documentoManagerService;
	private TipoDocumentoDAO tipoDocumentoDAO;
	private TipoDocumentoService tipoDocumentoService;
	private DocumentoGedoService documentacionGedoService;
	private ExpedienteElectronicoService expedienteElectronicoService;
	private HistorialOperacionService historialService;
	private ITomaVistaService tomaVistaService;
	private TareaParaleloService tareaParaleloService;
    private UsuariosSADEService usuariosSADEService;
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;
	private IExternalConsultaDocumentoService consultaDocumentoService;
	private IExternalTipoDocumentoService externalTipoDocumento;
	private IExternalGenerarDocumentoService generarDocumentoService;	
	private String loggedUser;
	private ApplicationContext ctx;
	private static TramitacionHelper instance;
	private OperacionDTO  operation;

	/**
	 * Default Constructor 
	 */
	public TramitacionHelper() {
		// Constructor
	}
	
	@PostConstruct
	public void registerInstance() {
		instance = this;
	}
	
	/**
	 * Constructor with parameter
	 * @param loggedUser String logged User
	 * @param ee Expediente Electronico
	 */
	public TramitacionHelper(String loggedUser, ExpedienteElectronicoDTO ee) {
		this.loggedUser = loggedUser;
		this.ee = ee;
		getStates();
	}
	
	public TramitacionHelper(String loggedUser, OperacionDTO op) {
		this.loggedUser = loggedUser;
		this.operation = op;
		getStates();
	}
	
	public void reinitialize(String loggedUser, ExpedienteElectronicoDTO ee) {
		this.loggedUser = loggedUser;
		this.ee = ee;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}
	
	/**
	 * @return the ctx
	 */
	public ApplicationContext getCtx() {
		return ctx;
	}

	/**
	 * @return the loggedUser
	 */
	public String getLoggedUser() {
		return loggedUser;
	}

	/**
	 * @param bean
	 * @return
	 */
	public TramitacionHelper setDocumentoGedoService(DocumentoGedoService bean) {
		this.documentacionGedoService = bean;
		return this;
	}
	
	/**
	 * @return the generarDocumentoService
	 */
	public IExternalGenerarDocumentoService getGenerarDocumentoService() {
		if (generarDocumentoService == null) {
			generarDocumentoService = (IExternalGenerarDocumentoService) SpringUtil.getBean("generarDocumentoService");
		}
		
		return generarDocumentoService;
	}

	/**
	 * @return the consultaDocumentoService
	 */
	public IExternalConsultaDocumentoService getConsultaDocumentoService() {
		if (consultaDocumentoService == null) {
			consultaDocumentoService = (IExternalConsultaDocumentoService) SpringUtil
					.getBean("consultaDocumentoService");
		}

		return consultaDocumentoService;
	}

	/**
	 * @return the tareaParaleloService
	 */
	public TareaParaleloService getTareaParaleloService() {
		if (tareaParaleloService == null) {
			tareaParaleloService = (TareaParaleloService) SpringUtil.getBean(ConstantesServicios.TAREA_PARALELO_SERVICE);
		}
		
		return tareaParaleloService;
	}

	/**
	 * @return the usuariosSADEService
	 */
	public UsuariosSADEService getUsuariosSADEService() {
		if (usuariosSADEService == null) {
			usuariosSADEService = (UsuariosSADEService) SpringUtil.getBean(ConstantesServicios.USUARIOS_SADE_SERVICE);
		}
		
		return usuariosSADEService;
	}

	@SuppressWarnings("unchecked")
	public IFormManagerFactory<IFormManager<Component>> getFormManagerFactory() {
		if (formManagerFact == null) {
			formManagerFact = (IFormManagerFactory<IFormManager<Component>>) SpringUtil
					.getBean(ConstantesServicios.FORM_MANAGER_FACTORY);
		}
		
		return formManagerFact;
	}

	public ITomaVistaService getTomaVistaService() {
		if (tomaVistaService == null) {
			tomaVistaService = (ITomaVistaService) SpringUtil.getBean("tomaVistaService");
		}
		
		return tomaVistaService;
	}

	/**
	 * @return the historialService
	 */
	public HistorialOperacionService getHistorialService() {
		if (historialService == null) {
			historialService = (HistorialOperacionService) SpringUtil
					.getBean(ConstantesServicios.HISTORIAL_OPERACION_SERVICE);
		}
		
		return historialService;
	}

	/**
	 * @param historialService the historialService to set
	 */
	public void setHistorialService(HistorialOperacionService historialService) {
		this.historialService = historialService;
	}
	
	public IExternalTipoDocumentoService getExternalTipoDocumento() {		
		if (externalTipoDocumento == null) {
			externalTipoDocumento = (IExternalTipoDocumentoService) SpringUtil
					.getBean(ConstantesServicios.EXTERNAL_TIPO_DOC_SERVICE);
		}
		
		return externalTipoDocumento;
	}

	/**
	 * @return the expedienteElectronicoService
	 */
	public ExpedienteElectronicoService getExpedienteElectronicoService() {
		if (expedienteElectronicoService == null) {
			expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
		}
		
		return expedienteElectronicoService;
	}

	/**
	 * @param expedienteElectronicoService the expedienteElectronicoService to set
	 */
	public TramitacionHelper setExpedienteElectronicoService(ExpedienteElectronicoService expedienteElectronicoService) {
		this.expedienteElectronicoService = expedienteElectronicoService;
		
		return this;
	}

	/**
	 * @return the documentacionGedoService
	 */
	public DocumentoGedoService getDocumentacionGedoService() {
		if (documentacionGedoService == null) {
			documentacionGedoService = (DocumentoGedoService) SpringUtil.getBean(ConstantesServicios.DOCUMENTO_GEDO_SERVICE);
		}
		
		return documentacionGedoService;
	}

	/**
	 * @param documentacionGedoService the documentacionGedoService to set
	 */
	public void setDocumentacionGedoService(
			DocumentoGedoService documentacionGedoService) {
		this.documentacionGedoService = documentacionGedoService;
	}

	/**
	 * @return the documentoManagerService
	 */
	public DocumentoManagerService getDocumentoManagerService() {
		if (documentoManagerService == null) {
			documentoManagerService = (DocumentoManagerService) SpringUtil.getBean(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE);
		}
		
		return documentoManagerService;
	}

	/**
	 * @param documentoManagerService the documentoManagerService to set
	 */
	public TramitacionHelper setDocumentoManagerService(DocumentoManagerService documentoManagerService) {
		this.documentoManagerService = documentoManagerService;
		return this;
	}
	
	/**
	 * @return the tipoDocumentoDAO
	 */
	public TipoDocumentoDAO getTipoDocumentoDAO() {
		return tipoDocumentoDAO;
	}

	/**
	 * @param tipoDocumentoDAO the tipoDocumentoDAO to set
	 */
	public TramitacionHelper setTipoDocumentoDAO(TipoDocumentoDAO tipoDocumentoDAO) {
		this.tipoDocumentoDAO = tipoDocumentoDAO;
		return this;
	}

	/**
	 * @return the tipoDocumentoService
	 */
	public TipoDocumentoService getTipoDocumentoService() {
		if(tipoDocumentoService == null){
			tipoDocumentoService = (TipoDocumentoService) ApplicationContextUtil.getBean("tipoDocumentoServiceImpl");
		}
		return tipoDocumentoService;
	}

	/**
	 * @param tipoDocumentoService the tipoDocumentoService to set
	 */
	public TramitacionHelper setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
		this.tipoDocumentoService = tipoDocumentoService;
		return this;
	}

	/**
	 * Method to set a instance of Class Service to a same type variable in the class
	 * @param service
	 */
	public TramitacionHelper addService(Object service) {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (isTypeOf(field.getName(),field.getType(), service)) {
				try {
					field.set(this, service);
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				}
			}
		}
		
		return this;
	}
	
	public boolean isTypeOf(String fieldName, Class<?> type, Object obj) {
		List<Class<?>> interfaces = Arrays.asList(obj.getClass().getInterfaces());
		Class<?> superClass = obj.getClass().getSuperclass();
		
		if (superClass != null) {
			if (superClass.getName().equalsIgnoreCase(type.getName()))
				return true;
			else {
				List<Class<?>> superIf = Arrays.asList(obj.getClass().getInterfaces());
				for (Class<?> clazz : superIf) {
					if (clazz.getName().equalsIgnoreCase(type.getName()))
						return true;
				}
			}
		}
		
		for (Class<?> clazz : interfaces) {
			if (clazz.getName().equalsIgnoreCase(type.getName()))
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return the states
	 */
	public List<IVisualState> getStates() {
		if (visualStates == null) {
			visualStates = new ArrayList<IVisualState>();
		}

		return visualStates;
	}
	
	private IVisualState findState(String clsName) {
		for (IVisualState visualState : getStates()) {
			if (visualState.getClass().getName().toLowerCase().contains(clsName.toLowerCase())) {
				return visualState;
			}
		}

		if(getStates() == null || getStates().isEmpty()){
			return null;
		}
		return getStates().get(0);
	}

	/**
	 * @return the activeState
	 */
	public IVisualState getActiveState() {
		if (activeState == null) {
			activeState = findState("NoneState");
			if(activeState != null){
				activeState.setExpedienteElectronico(this.ee);
				activeState.setTramitacionHelper(this);
			}
		}
		
		return activeState;
	}

	/**
	 * @param activeState the activeState to set
	 */
	public void setActiveState(Task task) {
		String stateName=task.getName();
		String workflowname = task.getExecutionId().substring(0,task.getExecutionId().indexOf("."));
		
		if (stateName != null && !stateName.isEmpty()) {
			for (IVisualState visualState : getStates()) {
				if (visualState.is(workflowname, stateName)) {
					visualState.setWorkingTask(task);
					this.activeState = visualState;
					// this.activeState.setWorkingTask(task);
					break;
				}
			}
		}
	}
	
	/**
	 * Setea un estado visual activo a partir de la lista de estados
	 * que posee la clase helper
	 * 
	 * @param workflowName Nombre del workflow
	 * @param stateName Nombre del estado
	 */
	public void setActiveState(String workflowName, String stateName) {
		if (stateName != null && !stateName.isEmpty()) {
			for (IVisualState visualState : getStates()) {
				if (visualState.is(workflowName, stateName)) {
					this.activeState = visualState;
					break;
				}
			}
		}
	}

	/**
	 * @return the processEngine
	 */
	public ProcessEngine getProcessEngine() {
		if (processEngine==null) {
			try {
				setProcessEngine((ProcessEngine) SpringUtil.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE));
			} catch (UiException uex){
				logger.error("No estamos en contexto ZK", uex);
			}
			
			if (processEngine == null) {
				setProcessEngine((ProcessEngine) TramitacionHelper.instance.getCtx()
						.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE));
			}
		}
		
		return processEngine;
	}

	/**
	 * Setter for processEngine
	 * @param object
	 */
	public TramitacionHelper setProcessEngine(ProcessEngine object) {
		this.processEngine = object;
		return this;
	}
	
	/**
	 * Method to find Active workflows [Taskflows]
	 * @return
	 */
	public static List<String> findActiveWorkflows() {
		List<String> result = new ArrayList<>();
		ProcessEngine processEngine = (ProcessEngine) SpringUtil.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE);
		
		// Order by name asc and latest version
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		query.orderAsc(ProcessDefinitionQuery.PROPERTY_NAME);
		query.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION);
		
		List<ProcessDefinition> lst = query.list();
		
		for (ProcessDefinition pd : lst) {
		  if (!pd.isSuspended() && !pd.getName().startsWith("state") && !result.contains(pd.getKey())) {
				result.add(pd.getKey());
			}
		}
		
		return result; 
	}
	
	/**
	 * Method to find Actuaciones
	 * @return
	 */
	public static List<String> findActuaciones() {
		List<String> result = new ArrayList<>();
		result.add(ConstantesWeb.ACTUACION_EX);
//		result.add(ConstantesWeb.ACTUACION_LE);
//		result.add(ConstantesWeb.ACTUACION_LP);
		return result; 
	}
	
	/**
	 * Method to find last version of a workflow
	 * @param workflowName
	 * @return
	 */
	public static Integer findLastVersion(String workflowName) {
		Integer result=-1;
		
		ProcessEngine processEngine = (ProcessEngine) SpringUtil.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE);
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> lst = query.list();
		
		for (ProcessDefinition pd : lst) {
			if (pd.getName().equalsIgnoreCase(workflowName) && !pd.isSuspended() && pd.getVersion()>result) {
				result=pd.getVersion();
			}
		}
		
		return result; 
	}
	
	/**
	 * Method to get variables of an execution
	 * @param executionId String Execution ID
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getVariables(String executionId) {
		try {
			Set<String> keys = getProcessEngine().getExecutionService().getVariableNames(executionId);
			
			if (keys!=null && !keys.isEmpty()) {
				keys.remove("workingTask");
				Map<String,Object> data = getProcessEngine().getExecutionService().getVariables(executionId, keys);
				return data;
			}
		} catch (Exception e) {
			
		}
		
		return new HashMap<String, Object>();
	}
	
	/**
	 * Method to change workflow and get Task
	 * @param workflow
	 * @return
	 */
	public Task changeToWorkFlow(Task oldTask, WORKFLOW_TABS workflow, Map<String,Object> variables) {
		ProcessInstance procIn = null;
		
    	try {	
    		Task prevTask = getProcessEngine().getTaskService().getTask(oldTask.getId());
        	String oldAssignee = String.format("-%s-",prevTask.getAssignee());
        	getProcessEngine().getTaskService().assignTask(prevTask.getId(), oldAssignee);
        	
        	variables.put("from_task", oldTask.getExecutionId()); // indico de que tarea vino originariamente

    		procIn = getProcessEngine().getExecutionService().startProcessInstanceById(workflow.name().toLowerCase()+"-"+WORKFLOW_VERSION, variables);
    		TaskQuery taskQuery = getProcessEngine().getTaskService().createTaskQuery().executionId(procIn.getId());
        	Task task = taskQuery.uniqueResult();
        
        	return task;
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    	}
    	
    	return null;
	}
	
	public Task restoreWorkFlow(Task oldTask) {
		String oldAssignee = oldTask.getAssignee().substring(0, oldTask.getAssignee().length());
		getProcessEngine().getTaskService().assignTask(oldTask.getId(), oldAssignee);
		return oldTask;
	}
	
	/**
	 * Method to setting data in ENVIO windows
	 * @param envioWindow ZK's Window
	 * @param sectorReparticionBusquedaSADE String
	 * @param SectorBusquedaSADE String
	 */
	public void setDefaultEnvioSector(Window envioWindow, String sectorReparticionBusquedaSADE, String SectorBusquedaSADE, String estado) {
		Combobox estadoCmb = (Combobox) envioWindow.getFellow("estado");
		Radio sectorRadio = (Radio) envioWindow.getFellow("sectorRadio");
		Bandbox sectorReparticionBusquedaSadeBandBox = (Bandbox) envioWindow.getFellow("sectorReparticionBusquedaSADE");
		Bandbox sectorBusquedaSADEBandBox = (Bandbox) envioWindow.getFellow("sectorBusquedaSADE");

		estadoCmb.setValue(estado);
		sectorRadio.setChecked(true);
		sectorReparticionBusquedaSadeBandBox.setValue(sectorReparticionBusquedaSADE);
		sectorBusquedaSADEBandBox.setValue(SectorBusquedaSADE);
		
		Events.sendEvent(sectorRadio, new Event(Events.ON_CLICK));
	}
	
	/**
	 * Method to know if a determinated acronym is enabled
	 * @param acronimo String of acronym
	 * @return
	 */
	private boolean isEnabledAcronym(String acronimo) {
        List<TrataTipoDocumentoDTO> tiposDocumentosGEDOBD;
        tiposDocumentosGEDOBD = tipoDocumentoDAO.buscarTrataTipoDocumento(this.ee.getTrata());
        
        if (!tiposDocumentosGEDOBD.isEmpty()) {
        	if (!tiposDocumentosGEDOBD.get(0).getAcronimoGEDO().trim().equals(ConstantesWeb.SELECCIONAR_TODOS)) {
                for (TrataTipoDocumentoDTO documentoBD : tiposDocumentosGEDOBD) {
                    if (acronimo.trim().equals(documentoBD.getAcronimoGEDO().trim())) {
                        return true;
                    }
                }
        	} else {
        		return true;
        	}
        }
        
        return false;
    }
	
	/**
	 * Metodo to generate a document of PASS
	 * @param workingTask Actual TASK
	 * @param motivo String
	 * @param acronimoPase String 
	 * @param camposTemplate String
	 */
	public void vincularDocumentoPase(Task workingTask, String motivo, String acronimoPase, Map<String,String> camposTemplate) {
		String referenciaPase = String.format("Informe de %s",workingTask.getName().toLowerCase());
		DocumentoDTO doc = getDocumentacionGedoService().generarDocumentoGEDOPase(this.ee, motivo, getLoggedUser(), acronimoPase, referenciaPase, camposTemplate);
        doc.setFechaAsociacion(new Date());
        doc.setUsuarioAsociador(getLoggedUser());
        doc.setIdTask(workingTask.getExecutionId());

        if ((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()) {
            doc.setIdExpCabeceraTC(this.ee.getId());
        }
        
        this.ee.getDocumentos().add(doc);
	}
	
	/**
	 * Generic method to links documents
	 * 
	 * @param workingTask Actual TASK
	 * @param tipoDocumento String
	 * @param anioDocumento String
	 * @param numeroDocumento String
	 * @param reparticionDocumento String
	 * @param handler
	 */
	public void vincularDocumento(Task workingTask, String tipoDocumento, Integer anioDocumento, Integer numeroDocumento, String reparticionDocumento, CallbackHandler handler)
	{
        String messageResult="";
        String titleResult = "";
        
        DocumentoDTO documentoEstandard = null;
        
        try {
        	documentoEstandard=getDocumentoManagerService().buscarDocumentoEstandar(tipoDocumento, anioDocumento, numeroDocumento, reparticionDocumento);
        } catch (NullPointerException e) {
            handler.onError("Error en busqueda de documento", "ERROR INESPERADO : "+e.getMessage());
            return;
        }
        
        if (documentoEstandard != null) {
            if (!this.ee.getDocumentos().contains(documentoEstandard)) {
                // TODO FALLAR SI NO SE ENCUENTRA
                TipoDocumentoDTO tipoDeDocumento = getTipoDocumentoService().obtenerTipoDocumento(documentoEstandard.getTipoDocAcronimo());

                if (!isEnabledAcronym(documentoEstandard.getTipoDocAcronimo())) {
                	handler.onException(new WrongValueException(), "");
                    //throw new WrongValueException(tipoDocumento, "Tipo de Documento No habilitado para la trata. Verifique los datos ingresados.");
                }

                documentoEstandard.setFechaAsociacion(new Date());
                documentoEstandard.setUsuarioAsociador(getLoggedUser());
                documentoEstandard.setIdTask(workingTask.getExecutionId());

                // Si el expediente es cabeceraTC, la tramitacion conjunta ya se
                // ha confirmado y los documentos que se adjunten a partir de
                // ese
                // momento tendran el id del expediente cabecera para luego
                // poder copiarlos a los expedientes adjuntos.
                if ((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()) {
                    documentoEstandard.setIdExpCabeceraTC(this.ee.getId());
                }
                
                this.ee.getDocumentos().add(documentoEstandard);
                
                if (ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL) && !tipoDeDocumento.getEsConfidencial()) {
                    messageResult = "El documento " + tipoDocumento + "-" + anioDocumento.toString() + "-" + BusinessFormatHelper.completarConCerosNumActuacion(numeroDocumento) + "-" +
                        reparticionDocumento + " " + Labels.getLabel("ee.tramitacion.documentoAsociado") +
                        " Al asociar un documento no confidencial a un expediente de reserva total, el documento pasará a ser confidencial";
                    titleResult = Labels.getLabel("ee.tramitacion.titulo.asociado");
                } else {
                	messageResult = "El documento " + tipoDocumento + "-" + anioDocumento.toString() + "-" + BusinessFormatHelper.completarConCerosNumActuacion(numeroDocumento) + "-" +
                        reparticionDocumento + " " + Labels.getLabel("ee.tramitacion.documentoAsociado");
                    titleResult = Labels.getLabel("ee.tramitacion.titulo.asociado");
                }
                
                handler.onSuccess(messageResult,titleResult);
            } else {
                if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
                    messageResult = Labels.getLabel("ee.tramitacion.documentoNoAsociado");
                    titleResult = Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado");
                } else {
                    messageResult = Labels.getLabel("ee.tramitacion.documentoNoAsociadoParalelo");
                    titleResult = Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado");
                }
                handler.onError(messageResult,titleResult);
            }
        } else {
            handler.onError(Labels.getLabel("ee.tramitacion.documentoNoExiste"), Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"));
        }
	
	}

	/**
	 * Method to update assigned variables of a task
	 * @param component Window Form from we get the variables
	 * @param task Task
	 */
	public void updateVariables(Component component, Task task) {
    	Map<String,Object> vars = getVariables(task.getExecutionId());
    	vars.putAll(getActiveState().getFormVariables(component.getFellow("incStatesNombramiento")));
    	getProcessEngine().getTaskService().setVariables(task.getId(), vars);
	}

	public void addVariables(Task task, Map<String,Object> values) {
		if (values!=null) {
	    	Map<String,Object> vars = getVariables(task.getExecutionId());
	    	vars.putAll(values);
	    	getProcessEngine().getTaskService().setVariables(task.getId(), vars);
		}
	}

	/**
	 * Method to get an Linked ExpedienteElectronico from a ExpedienteElectronico parent
	 * @param ee Expediente Electornico
	 * @param index Index of the document, if index<0 then index is set to 0, in the other side if index > size() then index=size()
	 * @return ExpedienteElectronico Linked ExpedienteAsociado
	 */
	public ExpedienteElectronicoDTO getExpedienteAsociado(ExpedienteElectronicoDTO ee, int index) {
		if (ee!=null && ee.getListaExpedientesAsociados()!=null && !ee.getListaExpedientesAsociados().isEmpty()) {
			
			if (index < 0) {
				index = 0;
			}
			
			if (index > ee.getListaExpedientesAsociados().size()) {
				index = ee.getListaExpedientesAsociados().size();
			}
			
			ExpedienteAsociadoEntDTO ea = ee.getListaExpedientesAsociados().get(index); // tomo solamente el primer documento asociado
			return this.expedienteElectronicoService.obtenerExpedienteElectronico(ea.getTipoDocumento(), ea.getAnio(), ea.getNumero(), ea.getCodigoReparticionUsuario());
		}
		
		return null;
	}
	
	public ProcessInstance getWorkflowWhatApply(String workflowName, Map<String,Object> variables) {
		String name = String.format("workflow-%s", workflowName);
		ProcessDefinitionQuery query = getProcessEngine().getRepositoryService().createProcessDefinitionQuery();
		query.processDefinitionName(name);
		query.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION);
		List<ProcessDefinition> lst = query.list();

		if (lst != null && !lst.isEmpty()) {
			ProcessDefinition procDef = lst.get(0);
			if (procDef != null) {
				return getProcessEngine().getExecutionService().startProcessInstanceById(procDef.getId(), variables);
			}
		}
		
		return getProcessEngine().getExecutionService().startProcessInstanceByKey("solicitud", variables);
	}

	/**
	 * Method to quering the workflow tree to search the transition who have an outgoing activity by name 
	 * @param environment JBPM4 Environment
	 * @param taskId String ID of the task
 	 * @param activityName String name of the activity to search
	 * @return Join name how contain the activity name
	 */
	public String getJoinedTask(Environment environment, String taskId, String activityName) {
		DbSession dbSession = (DbSession) environment.get(DbSession.class);
		TaskImpl task = (TaskImpl) dbSession.get(TaskImpl.class,Long.valueOf(Long.parseLong(taskId)));
		
		if (task == null) {
			throw new JbpmException("task " + taskId+ " doesn't exist");
		}

		ExecutionImpl execution = task.getExecution();
		
		if (execution != null) {
			ActivityImpl activity = execution.getActivity();

			if (activity != null) {
				List<? extends Transition> outgoingTransitions = activity.getOutgoingTransitions();

				if ((outgoingTransitions != null) && (!(outgoingTransitions.isEmpty()))) {
					for (Transition transition : outgoingTransitions) {
						String destinationName = transition.getDestination().getName();
						String cameFrom = transition.getSource().getName();
						if (cameFrom!=null && cameFrom.equalsIgnoreCase(activityName)) 
							return destinationName;
					}
				}
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("serial")
	public List<String> getPreviousTasks(final String taskId, final String nextTask) {
		return getProcessEngine().execute(new Command<List<String>>() {
			/* (non-Javadoc)
			 * @see org.jbpm.api.cmd.Command#execute(org.jbpm.api.cmd.Environment)
			 */
			@Override
			public List<String> execute(Environment environment) throws Exception {
				return getPreviousTask(environment, taskId, nextTask);
			}
		});
	}
	
	private List<String> getPreviousTask(Environment environment, String taskId, String nextTask) {
		List<String> previosTask = new ArrayList<String>();
		DbSession dbSession = (DbSession) environment.get(DbSession.class);
		TaskImpl task = (TaskImpl) dbSession.get(TaskImpl.class,Long.valueOf(Long.parseLong(taskId)));
		
		if (task == null) {
			throw new JbpmException("task " + taskId+ " doesn't exist");
		}

		ExecutionImpl execution = task.getExecution();
		
		if (execution != null) {
			ActivityImpl activity = execution.getActivity();

			if (activity != null) {
				List<? extends Transition> incomingTransitions = activity.getIncomingTransitions();

				if ((incomingTransitions != null) && (!(incomingTransitions.isEmpty()))) {
					for (Transition transition : incomingTransitions) {
						String cameFrom = transition.getSource().getName();
						if (cameFrom!=null && !cameFrom.equalsIgnoreCase(nextTask) && !cameFrom.startsWith("join"))
							previosTask.add(cameFrom);
					}
				}
			}
		}
		
		return previosTask;
	}

	public void configureInstances() {
		if (getStates() != null) {
			try {
				URL url = this.getClass().getResource("/config.xml");
				XMLConfiguration config = new XMLConfiguration(url);
	        
				for (IVisualState visualState : getStates()){
					try {
						XMLBeanDeclaration bean = new XMLBeanDeclaration(config ,visualState.getClass().getSimpleName()); 
								BeanHelper.initBean(visualState, bean);
								logger.info("%s \n", visualState.toString());
					} catch (Exception ex) {
						
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
        }
	}

	public void createDocument(String tipoDocumentoFFCC, Integer idTransaction, ExpedienteElectronicoDTO ee, String usuario) throws Exception {
		if (tipoDocumentoFFCC != null) {
			ResponseTipoDocumento tipoDocumento = getExternalTipoDocumento().consultarTipoDocumentoPorAcronimo(tipoDocumentoFFCC);
			String sistemaOrigen = ConstantesWeb.SIGLA_MODULO_ORIGEN;

			if (tipoDocumento != null) {
				RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
				Object referencia = this.processEngine.getExecutionService().getVariable(ee.getIdWorkflow(), REFERENCIA_DOCUMENTO_PASE);
				request.setReferencia((referencia != null) ? referencia.toString() : "Pase - Formulario Controlado");
				request.setAcronimoTipoDocumento(tipoDocumento.getAcronimo());
				request.setIdTransaccion(idTransaction);
				request.setSistemaOrigen(sistemaOrigen);
				request.setUsuario(usuario);
				
				ResponseExternalGenerarDocumento documentoGenerado =  getGenerarDocumentoService().generarDocumentoGEDO(request);
				
				if (documentoGenerado != null) {
					List<String> listaDocumentos = new ArrayList<String>();
					listaDocumentos.add(documentoGenerado.getNumero());
					getExpedienteElectronicoService().vincularDocumentosOficiales(sistemaOrigen, usuario, ee, listaDocumentos);
				}
			}
		}
	}
}