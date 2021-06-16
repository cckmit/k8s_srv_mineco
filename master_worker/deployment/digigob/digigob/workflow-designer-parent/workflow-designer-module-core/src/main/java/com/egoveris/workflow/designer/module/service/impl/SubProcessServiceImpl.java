package com.egoveris.workflow.designer.module.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.workflow.designer.module.exception.ServicesException;
import com.egoveris.workflow.designer.module.model.SubProcess;
import com.egoveris.workflow.designer.module.model.SubProcessDTO;
import com.egoveris.workflow.designer.module.service.RepositoryService;
import com.egoveris.workflow.designer.module.service.SubProcessService;

@Service
public class SubProcessServiceImpl implements SubProcessService {

	Logger logger  = Logger.getLogger(getClass());
	@Autowired
	private RepositoryService repository;

	private DozerBeanMapper mapper = new DozerBeanMapper();

	@SuppressWarnings("unchecked")
	@Override
	public List<SubProcessDTO> findSubProcessByStateFlow(final String stateFlow) throws ServicesException {
		return ListMapper.mapList(repository.findSubProcessByStateFlow(stateFlow), mapper, SubProcessDTO.class);
	}

	@Override
	public SubProcessDTO saveOrUpdate(final SubProcessDTO subProcess) throws ServicesException {
		return mapper.map(repository.saveOrUpdate(mapper.map(subProcess, SubProcess.class)), SubProcessDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubProcessDTO> findAllByStateNameVersion(final String stateFlow, final String stateName,
			final int version) throws ServicesException {
		return (List<SubProcessDTO>) ListMapper.mapList(repository.findAllByStateNameVersion(stateFlow, stateName, version), mapper,
				SubProcessDTO.class);
	}

	@Override
	public void delete(final SubProcessDTO subprocess) throws ServicesException {
		repository.delete(mapper.map(subprocess, SubProcess.class));
	}

	@Override
	public void updateSubProcessVersion(final String projectName, final int projectVersion) {
		repository.updateSubProcessVersion(projectName, projectVersion);

	}

	@Override
	public void updateSubProcessProject(final String projectName, final String projectNameOld,
			final int projectVersion) {
		repository.updateSubProcessProject(projectName, projectNameOld, projectVersion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getAllSubProcessProject(String projectName) {
		Map<String,String> map = new HashMap<>();
		List<SubProcessDTO> subprocess = (List<SubProcessDTO>) ListMapper.mapList(repository.findSubProcessByStateFlow(projectName), mapper,
				SubProcessDTO.class);
		for(SubProcessDTO  s : subprocess){
			if(map.containsKey(s.getStateFlow())){
				map.put(s.getStateFlow(), map.get(s.getStateFlow()) + "," + s.getProcedureName());
			} else {
				map.put(s.getStateFlow(), s.getProcedureName());
			}	
			
		}
		return map;
	}

	@Override
	public void updateVersionProcedure(String projectName, int version) {
		try {
			repository.updateVersionProcedureSubProcess(projectName, version);
		} catch (Exception e) {
			logger.error("Error update version procedure",e);
		}
		
	}

	@Override
	public int getLastVersionOfProcedure(String projectName) {
		try {
			return repository.lastVersionProcedure(projectName);
		} catch (Exception e) {
			logger.error("Error update version procedure",e);
		}
		return 0;
	}

	@Override
	public void copySubprocessOfState(String projectName, Integer version, String oldState, String newState) {
		List<SubProcess> subprocessFromList = repository.findAllByStateNameVersion(projectName, oldState, version);
		List<SubProcess> subprocessToList = repository.findAllByStateNameVersion(projectName, newState, version);
		
		// Check: if there isn't subprocess in new state
		if (subprocessToList == null || subprocessToList.isEmpty()) {
			subprocessToList = new ArrayList<>();
			
			// Check: if there are subprocess to copy
			if (subprocessFromList != null && !subprocessFromList.isEmpty()) {
				// Copy
				for (SubProcess subprocessFrom : subprocessFromList) {
					SubProcess subProcessCopy = mapper.map(subprocessFrom, SubProcess.class);
					subProcessCopy.setId(null);
					subProcessCopy.setStateName(newState);
					repository.saveOrUpdate(subProcessCopy);
				}
			}
		}	
	}

	@Override
	public void removeUnusedStateSubprocess(String projectName, Integer version, List<String> statesToKeep) {
		List<SubProcess> subprocessList = repository.findSubProcessByStateFlow(projectName);
		
		if (subprocessList != null && !subprocessList.isEmpty() && statesToKeep != null) {
			for (SubProcess subProcess : subprocessList) {
				if (version != null && version.equals(subProcess.getVersion())
				    && !statesToKeep.contains(subProcess.getStateName())) {
					repository.delete(subProcess);
				}
			}
		}
	}
	
}
