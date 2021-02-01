package com.egoveris.te.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.util.ApplicationContextUtil;
import org.zkoss.zul.Messagebox;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.script.core.ScriptApi;
import com.egoveris.script.core.ScriptBase;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.rmi.RemoteRestService;
import com.egoveris.te.web.ee.satra.pl.helpers.states.GenericState;
import com.egoveris.te.web.ee.satra.pl.helpers.states.IVisualState;
import com.egoveris.te.base.service.IEEWorkflowUtils;

public  class WorkFlowScriptUtils {

  private static Logger logger = LoggerFactory.getLogger(GenericState.class);
  private  PluginManager pluginManager;
  private  WorkFlowService workflowService;
  private  ExpedienteElectronicoService expedienteService;
  private  ProcessEngine processEngine;
  private  RemoteRestService remoteRestService;
  private  AppProperty dBProperty;
  private  IEEWorkflowUtils eeWorkflowUtils;
  private static WorkFlowScriptUtils instance;
  
  private WorkFlowScriptUtils() {
    //
  }
  
  public static WorkFlowScriptUtils getInstance() {
    if (instance == null) {
      instance = new WorkFlowScriptUtils();
    }
    return instance;
  }
  
  /**
   * Executes an script related to a task (expedient)
   * of a determinated type, if the task has any
   * 
   * @param type - Script type {@link ScriptType}
   * @param eeObject - Expedient object
   * @param params
   * @return
   * @throws NegocioException
   */
  public  Map<String, Object> executeScript(ScriptType type, Object object, Map<String, Object> parameters, String user) throws ScriptException {
    Map<String, Object> response = new HashMap<>();
    Map<String,Object> params = initParams(parameters, object, user);
    
    try {
      TramitacionHelper helper = getTramitacionHelperFromObject(object,user);
      response = helper.getActiveState().execScript(type, params);
    }
    catch (NegocioException | ScriptException e) {
      logger.error("Error en WorkFlowScriptUtils.executeScript(): ", e);
      throw new ScriptException(e);
    }
    
    return response;
  }
  
  /**
   * Executes an script manually passed by parameter.
   * This function is used for subprocess scripts.
   * 
   * @param script
   * @param eeObject - Expedient object
   * @param params
   * @return
   * @throws ScriptException
   */
  public Map<String, Object> executeSubprocessScript(String script, Object object, Map<String, Object> params, String user) throws ScriptException {
    Map<String, Object> response = new HashMap<>();
    params = initParams(params, object, user);
    
    if (script != null && !StringUtils.isBlank(script)) {
      // Reads from bdd the ee.script.subprocessGeneric property
      String scriptSubprocessGeneric = null;
      
      if (!params.containsKey("notIncScriptGeneric")) {
        scriptSubprocessGeneric = getDbProperty().getString("ee.script.subprocessGeneric");
      }
      
      if (scriptSubprocessGeneric == null) {
        scriptSubprocessGeneric = "";
      }
      
      StringBuilder strBuilder = new StringBuilder(scriptSubprocessGeneric);
      strBuilder.append(script);
      
      try {
        TramitacionHelper helper = getTramitacionHelperFromObject(object, user);
        response = helper.getActiveState().execScript(strBuilder.toString(), params);
      }
      catch (NegocioException | ScriptException e) {
        logger.error("Error en WorkFlowScriptUtils.executeSubprocessScript(): ", e.getCause());
        Messagebox.show("Problema en la ejecución de script\n\nTipo: Subproceso\nCausa:" + e.getMessage(),
            "Problema en la ejecución de script - Subproceso", Messagebox.OK, Messagebox.EXCLAMATION);
      }
    }
    
    return response;
  }
  
  /**
   * Initialize params object
   * 
   * @param params
   * @return
   */
  private Map<String, Object> initParams(Map<String, Object> params, Object object, String user) {
	Map<String, Object> parameters = params; 
    if (parameters == null) {
    	parameters = new HashMap<>();
    }
    
    if (!parameters.containsKey(ConstantesCommon.SCRIPT_ID_TRANS_FFCC)) {
    	parameters.put(ConstantesCommon.SCRIPT_ID_TRANS_FFCC, "");
    }
    
    if (!parameters.containsKey(ConstantesCommon.SCRIPT_NAME_FFCC)) {
    	parameters.put(ConstantesCommon.SCRIPT_NAME_FFCC, "");
    }
    
    if(!parameters.containsKey(ConstantesCommon.SCRIPT_WINDOW_PASE)){
    	parameters.put(ConstantesCommon.SCRIPT_WINDOW_PASE, null);
    }
    
    parameters.put("remoteRestService", getRemoteRestService());
    parameters.put("workflowService", getWorkflowService());
    parameters.put("user", user);
    parameters.put("utils", getEEWorkflowUtils());
    
    // This will put the objects teApi, deoApi, edtApi, ffddApi and wdApi
    ScriptBase scriptBase = ScriptBase.getInstance(getDbProperty());
    
    for (ScriptApi scriptApi : scriptBase.getAvailableApis()) {
      parameters.put(scriptApi.getApiName(), scriptApi);
    }
    
    
      if (object != null && object instanceof ExpedienteElectronicoDTO) {
    	  parameters.put("ee", (ExpedienteElectronicoDTO) object);
      } else if(object instanceof OperacionDTO){
    	  parameters.put("operation", (OperacionDTO) object); 
      }
    
    return parameters;
  }
  
  /**
   * Inits and returns a TramitacionHelper from
   * a given object that may be a ExpedienteElectronico
   * or a given Id
   * 
   * @param eeObject
   * @param params
   * @return
   * @throws NegocioException
   */
  private TramitacionHelper getTramitacionHelperFromObject(Object object, String user) throws NegocioException {
    TramitacionHelper helper = null;
    ExpedienteElectronicoDTO ee = null;
    String workFlowId= null;
    
    if (object != null && object instanceof ExpedienteElectronicoDTO) {
      ee = (ExpedienteElectronicoDTO) object;
      workFlowId = ee.getIdWorkflow();
    } else if (object instanceof Long) {
      Long idEe = (Long) object;
      ee = getExpedienteService().buscarExpedienteElectronico(idEe);
      workFlowId = ee.getIdWorkflow();
    } else if(object instanceof OperacionDTO){
    	workFlowId = ((OperacionDTO)object).getJbpmExecutionId(); 
    }
    
    ProcessInstance processInstance = null;
    if (StringUtils.isNotBlank(workFlowId)) {
      processInstance = getProcessEngine().getExecutionService().findProcessInstanceById(workFlowId);
    }
    
    if (processInstance == null) {
      throw new NegocioException("Error the process instance of workflow bpm is null or not exist");
    }
    
    String nameActivity = getActivityName(processInstance);
   
    
    String userExec = user;
    
    if (userExec == null) {
      userExec = ee.getUsuarioCreador();
    }
    if(ee == null){
    	helper = new TramitacionHelper(userExec, (OperacionDTO) object);
    } else {
    	helper = new TramitacionHelper(userExec, ee);
    }
    getPluginManager().getContext().put("tramitacionHelper", helper);
    getPluginManager().getContext().put("workflowService", getWorkflowService());
    
    configHelper(helper, nameActivity, workFlowId);
    String workflowName =  workFlowId.substring(0,workFlowId.indexOf("."));
    helper.setActiveState(workflowName, nameActivity);
    helper.getActiveState().setTramitacionHelper(helper);
    if(ee != null){
    	helper.getActiveState().setExpedienteElectronico(ee);
    }
    
    return helper;
  }
  
  private void configHelper(TramitacionHelper helper, String activityName, String workflowId) {
    helper.getStates().clear();
    helper.getStates().addAll(ReflectionUtil.searchClasses(IVisualState.class));

    final String workflowname = workflowId.substring(0, workflowId.indexOf("."));
    final String stateName = activityName;

    List<Object> instances = getPluginManager().getInstancesOf(new Predicate<Class<?>>() {
      @Override
      public boolean evaluate(Class<?> clazz) { // CLASS EVALUATOR
        return ReflectionUtil.hasInterface(clazz, IVisualState.class);
      }
    }, new Predicate<Object>() { // OBJECT EVALUATOR
      @Override
      public boolean evaluate(Object object) {
        return ((object != null) && ((IVisualState) object).is(workflowname, stateName));
      }
    });
    for (Object obj : instances) {
      helper.getStates().add((IVisualState) obj);
    }
  }
  
  
  
  
  private String getActivityName(ProcessInstance processInstance){
	    TaskQuery query = getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processInstance.getId());
	    List<Task> tasks = query.list();
	    Task workingTask = null;
	    if(CollectionUtils.isNotEmpty(tasks)){
	    	workingTask = tasks.get(0);
	    } else if (null == workingTask) {
		      query = getProcessEngine().getTaskService().createTaskQuery().executionId(processInstance.getId());
		      tasks = query.list();
		      if(CollectionUtils.isNotEmpty(tasks)){
		    	  workingTask = tasks.get(0);	  
		      }
		    
	    }
	    if(workingTask == null){
	    	return getActiveState(processInstance);
	    }
	    return workingTask.getActivityName();
  }
  
  private String getActiveState(ProcessInstance processInstance){
	  ExecutionImpl exe = (ExecutionImpl) getWorkflowService().getProcessEngine()
              .getExecutionService().findExecutionById(processInstance.getId());
	  return exe.getActivityName();
}
  
  public PluginManager getPluginManager() {
    if (pluginManager == null) {
      pluginManager = (PluginManager) ApplicationContextUtil.getBean("pluginManager");
    }
    return pluginManager;
  }

  public WorkFlowService getWorkflowService() {
    if (workflowService == null) {
      workflowService = (WorkFlowService) ApplicationContextUtil.getBean("workflowService");
    }
    return workflowService;
  }
  
	public IEEWorkflowUtils getEEWorkflowUtils() {
		if (eeWorkflowUtils == null) {
			eeWorkflowUtils = (IEEWorkflowUtils) ApplicationContextUtil.getBean(IEEWorkflowUtils.class);
		}
		return eeWorkflowUtils;
	}

  public ExpedienteElectronicoService getExpedienteService() {
    if (expedienteService == null) {
      expedienteService = (ExpedienteElectronicoService) ApplicationContextUtil
          .getBean("expedienteElectronicoServiceImpl");
    }
    return expedienteService;
  }

  public ProcessEngine getProcessEngine() {
    if (processEngine == null) {
      processEngine = (ProcessEngine) ApplicationContextUtil.getBean("processEngine");
    }
    return processEngine;
  }

  public RemoteRestService getRemoteRestService() {
    if (remoteRestService == null) {
      remoteRestService = (RemoteRestService) ApplicationContextUtil.getBean("remoteRestServiceImpl");
    }
    return remoteRestService;
  }
  
  public AppProperty getDbProperty() {
    if (dBProperty == null) {
      dBProperty = (AppProperty) ApplicationContextUtil.getBean(ConstantesServicios.APP_PROPERTY);
    }
    return dBProperty;
  }
  
}
