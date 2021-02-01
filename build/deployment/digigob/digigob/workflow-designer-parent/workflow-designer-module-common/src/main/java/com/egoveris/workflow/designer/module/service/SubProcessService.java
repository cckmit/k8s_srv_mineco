package com.egoveris.workflow.designer.module.service;

import java.util.List;
import java.util.Map;

import com.egoveris.workflow.designer.module.exception.ServicesException;
import com.egoveris.workflow.designer.module.model.SubProcessDTO;

public interface SubProcessService {
	
	/**
	 * Busca todos los subprocesos de un workdlow de estados
	 * @param stateFlow
	 * @return
	 * @throws ServicesException
	 */
	public List<SubProcessDTO> findSubProcessByStateFlow(String stateFlow) throws ServicesException;
	
	/**
	 * Inserta o actualiza un subproceso
	 * @param subProcess
	 * @return
	 * @throws ServicesException
	 */
	public SubProcessDTO saveOrUpdate(SubProcessDTO subProcess) throws ServicesException;
	
	/**
	 * busca todos los subprocesos pertenecientes a un estado del flujo
	 * @param stateFlow
	 * @param stateName
	 * @param version
	 * @return
	 * @throws ServicesException
	 */
	public List<SubProcessDTO> findAllByStateNameVersion(String stateFlow, String stateName, int version) throws ServicesException;
	
	/**
	 * elimina un subproceso asociado a un estado
	 * @param subprocess
	 * @throws ServicesException
	 */
	public void delete(SubProcessDTO subprocess) throws ServicesException;
	
	
	/**
	 * Actualiza la version de los subprocesos
	 * @param projectName
	 * @param projectVersion
	 */
	public void updateSubProcessVersion(String projectName, int projectVersion);
	
	
	/**
	 * Update subrpocess with the name of new project
	 * @param projectName
	 * @param projectNameOld
	 * @param projectVersion
	 */
	public void updateSubProcessProject(String projectName, String projectNameOld, int projectVersion);
	
	/**
	 * find all subprocess by project name
	 * @param projectName
	 * @return
	 */
	public Map<String,String> getAllSubProcessProject(String projectName);
	
	/**
	 * update the version of procedures associates to state workflow
	 * @param projectName
	 * @param version
	 */
	public void updateVersionProcedure(String projectName, int version);
	
	/**
	 * return last version of procedures of a stateflow
	 * @param projectName
	 * @return
	 */
	public int getLastVersionOfProcedure(String projectName);
	
	/**
	 * Copies subprocess of a state (oldState) to a newState.
	 * Is used for renaming in WD editor
	 * 
	 * @param projectName
	 * @param version
	 * @param oldState
	 * @param newState
	 */
	public void copySubprocessOfState(String projectName, Integer version, String oldState, String newState);
	
	/**
	 * Searchs all subprocess of a determined project/version and deletes all
	 * that doesn't belong to specified statesToKeep list
	 * 
	 * @param projectName
	 * @param version
	 * @param statesToKeep
	 */
	public void removeUnusedStateSubprocess(String projectName, Integer version, List<String> statesToKeep);
	
} 
