package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.ExpedientTransactionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.SubProcesoDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;


public interface OperacionService {

	/**
	 * guarda o actualiza una operacion.
	 * @param operacion
	 * @return
	 * @throws ServiceException
	 */
	public OperacionDTO saveOrUpdate(OperacionDTO operacion) throws NegocioException;
	
	/**
	 * retorna la lista completa de operaciones.
	 * @return
	 * @throws ServiceException
	 */
	public List<OperacionDTO> getOperaciones() throws NegocioException;
	
	/**
	 * Obtiene las transiciones que puede realizar una actividad.
	 * @param processId
	 * @return
	 * @throws ServiceException
	 */
	public List<String> getTransicionesOperacion(String processId) throws NegocioException;
	
	/**
	 * Confirma la operacion e inicia el flujo de JBPM.
	 * @param idOperacion
	 * @throws ServiceException
	 */
	public OperacionDTO confirmarOperacion(OperacionDTO operacion) throws NegocioException;
	
	/**
	 * Obtiene la lista de subprocesos que puede ver el estado
	 * @param stateFlow
	 * @return
	 */
	public List<SubProcesoDTO> getWorkFlowSubProcess(Long idWorkflow, String executionId, 
			String typeSubProcess, Integer versionProcedure) throws NegocioException;
	
	/**
	 * Inicia un subproceso a partir de un tramite seleccionado
	 * @throws ServiceException
	 */
  public ExpedienteElectronicoDTO iniciarSubProceso(SubProcesoDTO subproceso, OperacionDTO operacion, String username,
      String motivo) throws NegocioException;
	
	/**
	 * Obtiene la operacion por id
	 * @param id
	 * @return
	 */
	public OperacionDTO getOperacionById(Long id);
	
	/**
	 * Obtiene la operacion por nombre oficial
	 * @param numOfic
	 * @return
	 */
	public OperacionDTO getOperacionByNumOfic(String numOfic);
	
	/**
	 * Obtiene la operacion
	 * @param secto
	 * @param numOfic
	 * @Param estadoOpe
	 * @return
	 */
	public List<OperacionDTO> getOperacionBySectoAndNumOficAndEstado(Integer id, String numOfic, String estadoOpe);
	
	/**
	 * obtiene los procesos de la operacion
	 * @param idOperacion
	 * @return
	 */
	public List<SubProcesoOperacionDTO> getSubProcesos(Long idOperacion);
	
	/**
	 * Retorna las operaciones pertenecientes a un sector
	 * 
	 * @param idSector Id. del sector a que pertenece
	 * @return
	 * @throws ServiceException
	 */
	public List<OperacionDTO> getOperacionesBySector(Integer idSector) throws NegocioException;
	
	/**
	 * inicia los subprocesos de tipo automatico en TE
	 * - returns the initiated subprocess
	 */
	 public List<SubProcesoOperacionDTO> startAutomaticSubProcess(OperacionDTO operacion) throws NegocioException;
	 
	 /**
	  * init subproceso
	  */
	 public void iniciarSubProceso(Long idSubProcess, Long idOperacion, String username, String procedureName) throws NegocioException;
	 
	 
	 /**
	  * validate if the transaction parameter is a valid transaction
	  * @param idTransaction
	  * @return
	  */
	 public boolean isValidTransaction(String idTransaction)  throws NegocioException;
	 
	 
	 /**
	  * find the last transaction active
	  * @return
	  * @throws NegocioException
	  */
	 public ExpedientTransactionDTO findLastActiveTransaction(String idTransaction)  throws NegocioException;
	 
	 /**
	  * find the last transaction 
	  * @return
	  * @throws NegocioException
	  */
	 public ExpedientTransactionDTO findLastTransaction(String idTransaction)  throws NegocioException;
	 

	 /**
	  * init script
	  * @param operacion
	  */
	 public void initScriptSubprocess(OperacionDTO operacion)  throws NegocioException;
	 
	 
	 /**
	  * get the last version of procedures of a stateworkflow
	  * @param idWorkFlow
	  * @return
	  */
	 public Integer getVersionProcedureProject(Long idWorkFlow)  throws NegocioException;
	 
}
