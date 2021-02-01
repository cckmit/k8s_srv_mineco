/**
 * 
 */
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListitemRenderer;

/**
 * @author jnorvert
 *
 */
public abstract class GenericListitemRenderer implements ListitemRenderer {
	@Autowired
	ProcessEngine processEngine;
	Task workingTask;
	
	/**
	 * 
	 * @param name
	 *            : nombre de la variable del WF que quiero encontrar
	 * @return objeto guardado en la variable
	 */
	public Object getVariableWorkFlow(String name) {
		
		 
		if(this.workingTask==null){
			workingTask = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
		}
		
		Object obj = this.processEngine.getExecutionService().getVariable(
				this.workingTask.getExecutionId(), name);
		if (obj == null) {
			throw new VariableWorkFlowNoExisteException(
					"No existe la variable para el id de ejecucion. "
							+ this.workingTask.getExecutionId() + ", " + name, null);
		}
		return obj;
	}
	
	/**
	 * 
	 * @param name
	 *            : nombre de la variable que quiero setear
	 * @param value
	 *            : valor de la variable
	 */
	public void setVariableWorkFlow(String name, Object value) {
		this.processEngine.getExecutionService().setVariable(
				this.workingTask.getExecutionId(), name, value);
	}

}
