package com.egoveris.workflow.designer.module.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.workflow.designer.module.model.SubProcess;
import com.egoveris.workflow.designer.module.repository.SubProcessRepository;
import com.egoveris.workflow.designer.module.service.RepositoryService;

@Service
@Transactional
public class RepositoryServiceImpl implements RepositoryService{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RepositoryServiceImpl.class);

	@Autowired
	private SubProcessRepository subProcessRepository;
	

	
	@Override
	public List<SubProcess> findSubProcessByStateFlow(String stateFlow) {
		if (logger.isDebugEnabled()) {
			logger.debug("findSubProcessByStateFlow(String) - start"); //$NON-NLS-1$
		}
		List<SubProcess> returnList = subProcessRepository.findSubProcessByStateFlow(stateFlow);
		if (logger.isDebugEnabled()) {
			logger.debug("findSubProcessByStateFlow(String) - end"); //$NON-NLS-1$
		}
		if (logger.isDebugEnabled()) {
			logger.debug("findSubProcessByStateFlow(String) - end"); //$NON-NLS-1$
		}
		return returnList;
	}



	@Override
	public SubProcess saveOrUpdate(SubProcess subProcess) {
		if (logger.isDebugEnabled()) {
			logger.debug("saveOrUpdate(SubProcess) - start"); //$NON-NLS-1$
		}

		SubProcess returnSubProcess = subProcessRepository.save(subProcess);
		if (logger.isDebugEnabled()) {
			logger.debug("saveOrUpdate(SubProcess) - end"); //$NON-NLS-1$
		}
		return returnSubProcess;
	}



	@Override
	public List<SubProcess> findAllByStateNameVersion(String stateFlow, String stateName, int version) {
		if (logger.isDebugEnabled()) {
			logger.debug("findAllByStateNameVersion(String, String, int) - start"); //$NON-NLS-1$
		}

		List<SubProcess> returnList = subProcessRepository.findAllByStateNameVersion(stateFlow, stateName, version);
		if (logger.isDebugEnabled()) {
			logger.debug("findAllByStateNameVersion(String, String, int) - end"); //$NON-NLS-1$
		}
		return returnList;
	}



	@Override
	public void delete(SubProcess subprocess) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete(SubProcess) - start"); //$NON-NLS-1$
		}

		subProcessRepository.delete(subprocess);
		
		if (logger.isDebugEnabled()) {
			logger.debug("delete(SubProcess) - end"); //$NON-NLS-1$
		}
	}



	@Override
	public void updateSubProcessVersion(String projectName, int projectVersion) {
		subProcessRepository.updateSubProcessVersion(projectName, projectVersion);
	}



	@Override
	public void updateSubProcessProject(String projectName, String projectNameOld, int projectVersion) {
		subProcessRepository.updateSubProcessProject(projectName, projectNameOld, projectVersion);
	}



	@Override
	public void updateVersionProcedureSubProcess(String projectName, int versionProcedure) {
		subProcessRepository.updateSubProcessVersionProcedure(projectName, versionProcedure);
	}



	@Override
	public int lastVersionProcedure(String projectName) {
		PageRequest p = new PageRequest(0,1);
		List<Integer> subVersion = subProcessRepository.getLastVersionProcedure(projectName, p);
		if(CollectionUtils.isNotEmpty(subVersion)){
			return subVersion.get(0);
		}
		return 0;
	}
}
