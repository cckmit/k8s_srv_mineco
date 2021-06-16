package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptException;

import org.apache.commons.collections4.CollectionUtils;
import org.jbpm.api.Execution;
import org.jbpm.api.JbpmException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.SubProceso;
import com.egoveris.te.base.model.SubProcesoDesginer;
import com.egoveris.te.base.repository.SubProcesoDesginerRepository;
import com.egoveris.te.base.repository.SubProcesoRepository;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.WorkFlow;
import com.egoveris.te.base.util.WorkFlowScriptUtils;


@Transactional
public class WorkFlowServiceImpl implements WorkFlowService {
  private static final Logger logger = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private ITaskViewService taskViewService;
  @Autowired
  private SubProcesoRepository subProcessRepository;
  @Autowired
  private SubProcesoDesginerRepository subProcesoDesig;
  
  private String activityName;
  
  
  /**
   * Crea un <code>WorkFlow</code>, por tipo
   *
   * @param <code>ExpedienteElectronico</code>
   *          expedienteElectronico
   * @param <code>java.util.String</code>
   *          activityName del WorkFlow que se quiere crear
   * @return la nueva instancia de <code>WorkFlow</code>
   */

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.te.core.api.ee.services.workflow.impl.WorkFlowService#
   * createWorkFlow(com.egoveris.te.core.api.expedientes.dominio.
   * ExpedienteElectronico)
   */
  @Override
  public WorkFlow createWorkFlow(final Long idExpedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("createWorkFlow(idExpedienteElectronico={}) - start", idExpedienteElectronico);
    }

    this.activityName = null;
    ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
        .buscarExpedienteElectronico(idExpedienteElectronico);

    WorkFlow returnWorkFlow = getWorkFlow(expedienteElectronico);
    if (logger.isDebugEnabled()) {
      logger.debug("createWorkFlow(Integer) - end - return value={}", returnWorkFlow);
    }
    return returnWorkFlow;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.te.core.api.ee.services.workflow.impl.WorkFlowService#
   * createWorkFlow(com.egoveris.te.core.api.expedientes.dominio.
   * ExpedienteElectronico)
   */
  @Override
  public WorkFlow createWorkFlow(final Long idExpedienteElectronico,
      final String activityName) {
    if (logger.isDebugEnabled()) {
      logger.debug("createWorkFlow(idExpedienteElectronico={}, activityName={}) - start",
          idExpedienteElectronico, activityName);
    }

    this.activityName = activityName;
    ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
        .buscarExpedienteElectronico(idExpedienteElectronico);

    WorkFlow returnWorkFlow = getWorkFlow(expedienteElectronico);
    if (logger.isDebugEnabled()) {
      logger.debug("createWorkFlow(Integer, String) - end - return value={}", returnWorkFlow);
    }
    return returnWorkFlow;
  }

  /**
   * Inicializa el <code>WorkFlow</code>
   *
   * @param <code>ProcessEngine</code>
   *          processEngine
   * @param <code>java.lang.String</code>
   *          activityName
   * @param <code>Map<String,
   *          Object></code> variables
   * @return la nueva instancia de <code>ProcessInstance</code>
   * @exception <code>InterruptedException</code>
   *              interruptedException
   */
  private WorkFlow getWorkFlow(final ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlow(expedienteElectronico={}) - start", expedienteElectronico);
    }

    WorkFlow workFlow = new WorkFlow(this.taskViewService, this.expedienteElectronicoService,
        this.processEngine, expedienteElectronico, this.activityName);

    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlow(ExpedienteElectronico) - end - return value={}", workFlow);
    }
    return workFlow;
  }

  /**
   * Inicializa el <code>WorkFlow</code>
   * 
   * @param <code>ProcessEngine</code>
   *          processEngine
   * @param <code>java.lang.String</code>
   *          activityName
   * @param <code>Map<String,
   *          Object></code> variables
   * @return la nueva instancia de <code>ProcessInstance</code>
   * @exception <code>InterruptedException</code>
   *              interruptedException
   */
  @Override
  public String startWorkFlow(final ProcessEngine processEngine, final String activityName,
      final Map<String, Object> variables) throws InterruptedException {
    if (logger.isDebugEnabled()) {
      logger.debug("startWorkFlow(processEngine={}, activityName={}, variables={}) - start",
          processEngine, activityName, variables);
    }

    ProcessInstance processInstance = processEngine.getExecutionService()
        .startProcessInstanceByKey(activityName, variables);
    String returnString = processInstance.getId();

    if (logger.isDebugEnabled()) {
      logger.debug(
          "startWorkFlow(ProcessEngine, String, Map<String,Object>) - end - return value={}",
          returnString);
    }
    return returnString;
  }
  
  @Override
  public ProcessInstance startWorkFlowAndReturnInstance(ProcessEngine processEngine, String activityName,
      Map<String, Object> variables) throws InterruptedException {
    if (logger.isDebugEnabled()) {
      logger.debug("startWorkFlowAndReturnInstance(processEngine={}, activityName={}, variables={}) - start",
          processEngine, activityName, variables);
    }

    ProcessInstance processInstance = processEngine.getExecutionService().startProcessInstanceByKey(activityName,
        variables);

    if (logger.isDebugEnabled()) {
      logger.debug("startWorkFlow(ProcessEngine, String, Map<String,Object>) - end - return value={}",
          processInstance);
    }

    return processInstance;
  }

  @Override
  public Task startWorkFlowByName(final String workflowName, final Map<String, Object> variables)
      throws InterruptedException {
    if (logger.isDebugEnabled()) {
      logger.debug("startWorkFlowByName(workflowName={}, variables={}) - start", workflowName,
          variables);
    }

    ProcessInstance procIn = getProcessEngine().getExecutionService()
        .startProcessInstanceByKey(workflowName, variables);
    TaskQuery taskQuery = getProcessEngine().getTaskService().createTaskQuery()
        .executionId(procIn.getId());

    Task returnTask = taskQuery.uniqueResult();
    if (logger.isDebugEnabled()) {
      logger.debug("startWorkFlowByName(String, Map<String,Object>) - end - return value={}",
          returnTask);
    }
    return returnTask;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.te.core.api.ee.services.workflow.WorkFlowService#
   * removeProcessInstance(java.lang.String)
   */
  @Override
  public void removeProcessInstance(String executionId) {
    if (logger.isDebugEnabled()) {
      logger.debug("removeProcessInstance(executionId={}) - start", executionId);
    }

    getProcessEngine().getExecutionService().deleteProcessInstance(executionId);

    if (logger.isDebugEnabled()) {
      logger.debug("removeProcessInstance(String) - end");
    }
  }

  @Override
  public void setVariables(final ProcessEngine processEngine, final String executionId,
      final Map<String, Object> variables) {
    if (logger.isDebugEnabled()) {
      logger.debug("setVariables(processEngine={}, executionId={}, variables={}) - start",
          processEngine, executionId, variables);
    }

    processEngine.getExecutionService().setVariables(executionId, variables);

    if (logger.isDebugEnabled()) {
      logger.debug("setVariables(ProcessEngine, String, Map<String,Object>) - end");
    }
  }

  @Override
  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public ITaskViewService getTaskViewService() {
    return this.taskViewService;
  }

  public void setTaskViewService(ITaskViewService taskViewService) {
    this.taskViewService = taskViewService;
  }

  @Override
  public List<String> nextTasksName(String executionId) {
    if (logger.isDebugEnabled()) {
      logger.debug("nextTasksName(executionId={}) - start", executionId);
    }

    TaskQuery taskQuery = getProcessEngine().getTaskService().createTaskQuery()
        .executionId(executionId);
    Task task = taskQuery.uniqueResult();
    final String taskId = task.getId();
    final String actualState = task.getName();

    List<String> nextTasks = processEngine.execute(new Command<List<String>>() {
      /**
     * 
     */
    private static final long serialVersionUID = 1L;

  /*
       * (non-Javadoc)
       * 
       * @see org.jbpm.api.cmd.Command#execute(org.jbpm.api.cmd.Environment)
       */
      @Override
      public List<String> execute(Environment environment) throws Exception {
        if (logger.isDebugEnabled()) {
          logger.debug("$Command<List<String>>.execute(environment={}) - start", environment);
        }

        List<String> returnList = getJoinedTask(environment, taskId, actualState);
        if (logger.isDebugEnabled()) {
          logger.debug("$Command<List<String>>.execute(Environment) - end - return value={}",
              returnList);
        }
        return returnList;
      }
    });

    if (logger.isDebugEnabled()) {
      logger.debug("nextTasksName(String) - end - return value={}", nextTasks);
    }
    return nextTasks;
  };

  /**
   * Method to quering the workflow tree to search the transition who have an
   * outgoing activity by name
   * 
   * @param environment
   *          JBPM4 Environment
   * @param taskId
   *          String ID of the task
   * @param activityName
   *          String name of the activity to search
   * @return Join name how contain the activity name
   */
  @Override
  public List<String> getJoinedTask(Environment environment, String taskId, String activityName) {
    if (logger.isDebugEnabled()) {
      logger.debug("getJoinedTask(environment={}, taskId={}, activityName={}) - start",
          environment, taskId, activityName);
    }
    DbSession dbSession = environment.get(DbSession.class);
    TaskImpl task = dbSession.get(TaskImpl.class, Long.valueOf(Long.parseLong(taskId)));
    List<String> tasks = new ArrayList<String>();
    if (task == null) {
      throw new JbpmException("task " + taskId + " doesn't exist");
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
            if (cameFrom != null && cameFrom.equalsIgnoreCase(activityName)) {
              tasks.add(destinationName);
            }
          }
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getJoinedTask(Environment, String, String) - end - return value={}", tasks);
    }
    return tasks;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.te.core.api.ee.services.workflow.WorkFlowService#getVariables(
   * java.lang.String)
   */
  @Override
  public Map<String, Object> getVariables(String executionId) {
    if (logger.isDebugEnabled()) {
      logger.debug("getVariables(executionId={}) - start", executionId);
    }

    try {
      Set<String> keys = getProcessEngine().getExecutionService().getVariableNames(executionId);

      if (keys != null && !keys.isEmpty()) {
        keys.remove("workingTask");
        Map<String, Object> data = getProcessEngine().getExecutionService()
            .getVariables(executionId, keys);

        if (logger.isDebugEnabled()) {
          logger.debug("getVariables(String) - end - return value={}", data);
        }
        return data;
      }
    } catch (Exception e) {
      logger.error("getVariables(String)", e);
    }

    Map<String, Object> returnMap = new HashMap<>();
    if (logger.isDebugEnabled()) {
      logger.debug("getVariables(String) - end - return value={}", returnMap);
    }
    return returnMap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.te.core.api.ee.services.workflow.WorkFlowService#
   * changeToWorkFlow(org.jbpm.api.task.Task, java.lang.String, java.util.Map)
   */
  @Override
  public Task changeToWorkFlow(Task oldTask, String workflowName, Map<String, Object> variables) {
    if (logger.isDebugEnabled()) {
      logger.debug("changeToWorkFlow(oldTask={}, workflowName={}, variables={}) - start", oldTask,
          workflowName, variables);
    }

    ProcessInstance procIn = null;

    try {
      Task prevTask = getProcessEngine().getTaskService().getTask(oldTask.getId());
      String oldAssignee = String.format("-%s-", prevTask.getAssignee());
      getProcessEngine().getTaskService().assignTask(prevTask.getId(), oldAssignee);

      variables.put("from_task", oldTask.getExecutionId()); // indico de que
                                                            // tarea vino
                                                            // originariamente

      procIn = getProcessEngine().getExecutionService().startProcessInstanceById(workflowName,
          variables);
      TaskQuery taskQuery = getProcessEngine().getTaskService().createTaskQuery()
          .executionId(procIn.getId());
      Task task = taskQuery.uniqueResult();

      if (logger.isDebugEnabled()) {
        logger.debug("changeToWorkFlow(Task, String, Map<String,Object>) - end - return value={}",
            task);
      }
      return task;
    } catch (Exception e) {
      logger.error("changeToWorkFlow(Task, String, Map<String,Object>)", e);
    }

    if (logger.isDebugEnabled()) {
      logger
          .debug("changeToWorkFlow(Task, String, Map<String,Object>) - end - return value={null}");
    }
    return null;
  }

  @Override
  public String nextState(String workflow, Map<String, Object> variables) {
    ProcessInstance process = processEngine.getExecutionService()
        .findProcessInstanceById(workflow);
    Execution execution = process.getProcessInstance();
    process = processEngine.getExecutionService().signalExecutionById(execution.getId());
    return process.getId();

  }

  @Override
  public String nextState(String workflow, String transition, OperacionDTO operation, 
		  String user) throws  ServiceException {
	  try {
		  	String idProcess = null;
			Map<String, Object> obj =WorkFlowScriptUtils.getInstance().executeScript(ScriptType.END_STATE,
					operation, null, user);
			if(obj == null || obj.isEmpty()
					|| (String) obj.get("RESULT") == null){
			    ProcessInstance process = processEngine.getExecutionService().signalExecutionById(workflow,
			        transition);
			    idProcess =  process.getId();
			} else {
				idProcess = (String) obj.get("RESULT");
			}
		    WorkFlowScriptUtils.getInstance().executeScript(ScriptType.START_STATE,
					operation, null, user);
		    return idProcess;
	  } catch (ScriptException e) {
		logger.error("Error change state workflow",e);
		throw new ServiceException(e);
	}
  }

  
  @Override
  public String nextState(String workflow, String transition) throws  ServiceException {
	  try {
			ProcessInstance process = processEngine.getExecutionService().signalExecutionById(workflow,
		        transition);
		    return process.getId();
	  } catch (Exception e) {
		logger.error("Error change state workflow",e);
		throw new ServiceException(e);
	}
  }
  
  @Override
  public void copyStates(String workFlowName, int version) {
  		List<SubProcesoDesginer> procesoDesginer = subProcesoDesig.findByStateFlowAndVersion(workFlowName, version);
	    List<SubProceso> processProduction = subProcessRepository.findByStateFlowAndVersion(workFlowName, version);
	    //New version of Workflow
	    if(CollectionUtils.isEmpty(processProduction)){
	    	for (SubProcesoDesginer s : procesoDesginer) {
		    	SubProceso entity = new SubProceso();
		        entity.setLockType(s.getLockType());
		        entity.setStateFlow(s.getStateFlow());
		        entity.setStateName(s.getStateName());
		        entity.setVersion(s.getVersion());
		        entity.setTramite(s.getTramite());
		        entity.setStartType(s.getStartType());
		        entity.setVersionProcedure(s.getVersionProcedure());
		        entity.setScriptStart(s.getScriptStart());
		        entity.setScriptEnd(s.getScriptEnd());
		        subProcessRepository.save(entity);
		    }
	    } else{
	    	// same version of Workflow but different version of procedures
	    	for(SubProcesoDesginer pd : procesoDesginer){
	    		boolean insert = true;
	    		for(SubProceso p : processProduction){
	    			String codTrata = pd.getTramite().getCodigoTrata();
	    			String codTrataP = p.getTramite().getCodigoTrata();
    				if(codTrata.equalsIgnoreCase(codTrataP)
    						&& pd.getVersionProcedure() == p.getVersionProcedure()){
    							insert = false;
    				}
    			}
	    		
	    		if(insert){
		    		SubProceso entity = new SubProceso();
			        entity.setLockType(pd.getLockType());
			        entity.setStateFlow(pd.getStateFlow());
			        entity.setStateName(pd.getStateName());
			        entity.setVersion(pd.getVersion());
			        entity.setTramite(pd.getTramite());
			        entity.setStartType(pd.getStartType());
			        entity.setVersionProcedure(pd.getVersionProcedure());
			        entity.setScriptStart(pd.getScriptStart());
			        entity.setScriptEnd(pd.getScriptEnd());
			        subProcessRepository.save(entity);
	    		}
	    	}
	    }
  }
}
