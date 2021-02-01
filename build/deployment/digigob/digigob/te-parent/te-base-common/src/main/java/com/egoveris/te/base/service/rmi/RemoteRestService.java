package com.egoveris.te.base.service.rmi;

import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.jbpm.api.task.Task;

import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ExpedientTransactionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.OperacionDTO;

public interface RemoteRestService {

	
	/**
	 * invoke a methods remote to FUSE
	 * @param code
	 * @param message
	 * @throws NegocioException
	 */
	public Object invokeRemoteService(String code, String message, ExpedienteElectronicoDTO  ee, 
			String idTransaction, String nameFFCC, String typeCall) throws NegocioException, ScriptException;
	
	
	/**
	 * invoke a methods remote FUSE from operation
	 * @param operation
	 * @param code
	 * @param message
	 * @param typeCall
	 * @return
	 * @throws NegocioException
	 * @throws ScriptException
	 */
	public Object invokeRemoteServiceOperation(OperacionDTO operation,String code, 
			String message, String typeCall) throws NegocioException, ScriptException; 
	/**
	 * exeute script from FUSE ESB
	 * @param workingTask
	 * @param exp
	 * @param user
	 * @param estadoSeleccionado
	 * @param motivoExpediente
	 * @param detalles
	 * @throws NegocioException
	 */
	public void executeDesicion(Task workingTask, ExpedienteElectronicoDTO exp,
			String user, String estadoSeleccionado, String motivoExpediente, Map<String, String> detalles) throws NegocioException;
	
	
	/**
	 * Update the transaction the next time the transaction is not valid
	 * @param idTransaction
	 * @throws NegocioException
	 */
	public void updateTransaction(String idTransaction) throws NegocioException;
	
	
	/**
	 * proced to test ESB resquest
	 * @return
	 * @throws NegocioException
	 */
	public List<ExpedientTransactionDTO> getTransactions() throws NegocioException;
	
	
	/**
	 * validate the atrributes of forms whit the response of fuse
	 * @param nameForm
	 * @return
	 * @throws NegocioException
	 */
	public boolean validateStructureForm(String nameForm, FormularioDTO formRemote) throws NegocioException;
	
	
	
	/**
	 * blocked expediente from JavaScript execution
	 * @param ee
	 * @throws NegocioException
	 */
	public void blockExpedient(ExpedienteElectronicoDTO ee) throws NegocioException; 
	
	/**
	 * blocked operation
	 * @param ee
	 * @throws NegocioException
	 */
	public void blockOperation(ExpedienteElectronicoDTO ee) throws NegocioException;
	
	/**
	 * init subprocess
	 * @param ee
	 * @param nameSubprocess
	 */
	public void initSubprocess(ExpedienteElectronicoDTO  ee, String nameSubprocess);
}
