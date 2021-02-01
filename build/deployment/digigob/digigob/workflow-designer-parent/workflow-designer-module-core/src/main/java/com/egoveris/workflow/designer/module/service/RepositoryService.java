package com.egoveris.workflow.designer.module.service;

import java.util.List;

import com.egoveris.workflow.designer.module.model.SubProcess;

public interface RepositoryService {

	/**
	 * Busca todos los subprocesos de un stateflow
	 * @param stateFlow
	 * @return
	 */
	public List<SubProcess> findSubProcessByStateFlow(String stateFlow);
	
	/**
	 * guarda o actualiza un subproceso asociado a un estado
	 * @param subProcess
	 * @return
	 */
	public SubProcess saveOrUpdate(SubProcess subProcess);
	
	
	/**
	 * busca todos los subprocesos asociados a un estado
	 * @param stateFlow
	 * @param stateName
	 * @param version
	 * @return
	 */
	public List<SubProcess> findAllByStateNameVersion(String stateFlow, String stateName, int version);
	
	/**
	 * elimina un subproceso asociado aun estado
	 * @param subprocess
	 */
	public void delete(SubProcess subprocess);
	
	
	/**
	 * update subprocess with the last version of project
	 * @param projectName
	 * @param projectVersion
	 */
	public void updateSubProcessVersion(String projectName, int projectVersion);
	
	/**
	 * update subprocess
	 * @param projectName
	 * @param projectNameOld
	 * @param projectVersion
	 */
	public void updateSubProcessProject(String projectName, String projectNameOld,  int projectVersion);
	
	/**
	 * 
	 * @param projectName
	 * @param versionProcedure
	 */
	public void updateVersionProcedureSubProcess(String projectName, int versionProcedure);
	
	
	/**
	 * 
	 * @param projectName
	 * @return
	 */
	public int lastVersionProcedure(String projectName);
}
