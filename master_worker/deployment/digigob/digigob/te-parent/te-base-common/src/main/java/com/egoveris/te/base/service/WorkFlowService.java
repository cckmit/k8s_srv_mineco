package com.egoveris.te.base.service;

import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.util.WorkFlow;

/**
 *  Esta interface es un template para <code>WorkFlow</code> por tipo.
 *  No presenta un coportamiento propido, solo se limita a delegar en el los servicios que manejen los tipos de <code>WorkFlow</code>.
 *  <ul>
 *   <li><code>TareaWorkFlow</code></li>
 *  </ul>
 *
 */
public interface WorkFlowService {
    public WorkFlow createWorkFlow(final Long idExpedienteElectronico, final String activityName);
    public WorkFlow createWorkFlow(final Long idExpedienteElectronico);
    
    /** 
	 * Inicializa el <code>WorkFlow</code>
	 * 
	 * @param <code>ProcessEngine</code> processEngine
	 * @param <code>java.lang.String</code> activityName
	 * @param <code>Map<String, Object></code> variables
	 * @return la nueva instancia de <code>ProcessInstance</code>
	 * @exception <code>InterruptedException</code> interruptedException
	 */    
    public String startWorkFlow(final ProcessEngine processEngine, final String activityName, final Map<String, Object> variables) throws InterruptedException;
    
    public ProcessInstance startWorkFlowAndReturnInstance(final ProcessEngine processEngine, final String activityName, final Map<String, Object> variables) throws InterruptedException;
    
    public Task startWorkFlowByName(final String workflowName, final Map<String, Object> variables) throws InterruptedException;
    
    public void setVariables(final ProcessEngine processEngine, final String idExecution, final Map<String, Object> variables);
    
    public List<String> nextTasksName(String executionId);
    
    public List<String> getJoinedTask(Environment environment, String taskId, String activityName);
    
	/**
	 * Method to get variables of an execution
	 * @param executionId String Execution ID
	 * @return Map<String,Object>
	 */
    public Map<String,Object> getVariables(String executionId);
    
	/**
	 * Method to change workflow and get Task
	 * @param workflowName
	 * @return
	 */
	public Task changeToWorkFlow(Task oldTask, String workflowName, Map<String,Object> variables);

    /**
     * Method to remove a processInstance
     * @param String executionId
     */
    public void removeProcessInstance(String executionId);
    
    /**
     * Retorna el motor de JBMP
     * @return
     */
    public ProcessEngine getProcessEngine();
    
    /**
     * Avanza al siguiente estado
     * @param workflow
     * @param variables
     * @return
     */
    public String  nextState(String workflow, Map<String,Object>variables);
    
    /**
     * cambia la transicion de JBPM of task
     * @param workflow
     * @param transition
     * @return
     */
    public String  nextState(String workflow, String transition) throws  ServiceException;
    
    /**
     * cambia la transicion de JBPM of operation
     * @param workflow
     * @param transition
     * @return
     */
    public String  nextState(String workflow, String transition, OperacionDTO operation, 
  		  String user) throws  ServiceException;
    
    
    public void copyStates(String workFlowName, int version);
    
}
