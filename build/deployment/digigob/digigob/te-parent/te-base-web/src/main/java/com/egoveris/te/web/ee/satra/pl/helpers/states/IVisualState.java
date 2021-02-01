/**
 * 
 */
package com.egoveris.te.web.ee.satra.pl.helpers.states;

import java.util.Map;

import javax.script.ScriptException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.states.IState;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.TramitacionHelper;

/**
 * The Interface IVisualState.
 *
 * @author difarias
 */
public interface IVisualState extends IState {
	
	/**
	 * Method to set the user who do the operation
	 * @param userLogged
	 */
	public void setUserLogged(Usuario userLogged);
	
	/**
	 * Method to set the tramitacion helper (be careful, this generate circular reference)
	 * @param tramitacionHelper
	 */
	public void setTramitacionHelper(TramitacionHelper tramitacionHelper);
	
	/**
	 * Method to draw relative component to the container window.
	 *
	 * @param component the component
	 */
	public void drawInclude(final Component component);

	/**
	 * Method to validate data before sending to PASS.
	 *
	 * @return true or false
	 * @throws InterruptedException the interrupted exception
	 */
	public boolean validateBeforePass() throws InterruptedException;
	
	/**
	 * Method to customize windows PASE.
	 *
	 * @param windowPase the window pase
	 * @param nextState the next state
	 */
	public void customizePase(Component windowPase, String nextState);
	
	/**
	 * Method to get variables from the FORM.
	 *
	 * @param containerComponent Component where get variables
	 * @return the form variables
	 */
	public Map<String, Object> getFormVariables(Component containerComponent);
	
	/**
	 * Method to customize windows PASE .
	 *
	 * @param envio the envio
	 */
	public void customizePase(Window envio);
	
	/**
	 * Method to generate a document of PASE with a motive.
	 *
	 * @param motive the motive
	 */
	public void generateDocumentOfPase(String motive);
	
	/**
	 * Method that check if a STATE permit reject.
	 *
	 * @return true, if successful
	 */
	public boolean acceptReject();
	
	/**
	 * Method to set inner FLAG in rejecting...
	 */
	public void startReject();
	
	/**
	 * Method to set inner FLAG to stop rejecting...
	 */
	public void stopReject();

	/**
	 * Method to get the min user for parallel send to users
	 * @return
	 */
	public Integer getParallelMinUsers();
	/**
	 * Method to get the max users for parallel send to users
	 * @return
	 */
	public Integer getParallelMaxUsers();
	/**
	 * Method to get the min sector for parallel send to sector
	 * @return
	 */
	public Integer getParallelMinSector();
	/**
	 * Method to get the max sectors for parallel send to sector
	 * @return
	 */
	public Integer getParallelMaxSector();
	
	/**
	 * Method to know if the parallel send to a exlusive destination (user or sector)
	 * @return true or false
	 */
	public boolean isParallelExclusive();

	/**
	 * Method for enabling a button.
	 *
	 * @param component the component
	 */
	public void disableButton(Button component);

	public boolean is(String workflowname, String stateName);
	
	/**
	 * Executes an script related to a task (expedient)
	 * of a given type, if the task has any
	 * 
	 * @param type
	 * @return
   * @throws ScriptException
	 */
  public Map<String, Object> execScript(ScriptType type, Map<String, Object> params) throws ScriptException;
  
  /**
   * Executes an script manually passed by parameter.
   * This function is used for subprocess scripts.
   * 
   * @param script
   * @param params
   * @return
   * @throws ScriptException
   */
  public Map<String, Object> execScript(String script, Map<String, Object> params) throws ScriptException;
  
}
