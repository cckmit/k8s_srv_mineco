/**
 * 
 */
package com.egoveris.te.base.states;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

import java.io.Serializable;

import org.jbpm.api.task.Task;


/**
 * @author difarias
 *
 */
public interface IState extends Serializable {
	/**
	 * Method to initialize the State
	 */
	public void initState();

	/**
	 * Method to check if the state is valid
	 */
	public boolean isValid();

	/**
	 * Method to take a decision
	 * @return Object generic return;
	 */
	public Object takeDecision();
	
	/**
	 * Method to set the ExpedienteElectronico to the STATE
	 * @param ee
	 */
	public void setExpedienteElectronico(ExpedienteElectronicoDTO ee);
	/**
	 * Method to set the active task to the STATE
	 * @param task
	 */
	public void setWorkingTask(Task task);
	/**
	 * Method to get the name of the state
	 * @return
	 */
	public String getName();
	
	/**
	 * Method to get the name of the workflow where lives this state
	 * @return
	 */
	public String getWorkflowName();
	/**
	 * Method to know if a state is applicable
	 * @param IState
	 * @return boolean
	 */
	public boolean toApply(IState State);	
	
	/**
	 * Method to know if State correspond to a state name
	 * @param workflowName
	 * @param stateName
	 * @return
	 */
	public boolean is(String workflowName, String stateName);
	
	/**
	 * Method to know if a state correspond to a workflow
	 * @param workflowName
	 * @return
	 */
	public boolean isWorkflow(String workflowName);
	
	/**
	 * Method to know if a state correspond exactly to a workflow
	 * @param workflowName
	 * @return
	 */
	public boolean isWorkflowEqual(String workflowName);
	
}
