package com.egoveris.workflow.designer.module.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.model.ProjectDesginer;
import com.egoveris.workflow.designer.module.model.ProjectDesignerDTO;
import com.egoveris.workflow.designer.module.repository.ProjectRepository;
import com.egoveris.workflow.designer.module.service.DesignerService;
import com.egoveris.workflow.designer.module.service.ProjectService;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectDAO;
	private DozerBeanMapper mapper = new DozerBeanMapper();

	@Autowired
	private DesignerService desginerServiceimpl;

	@Override
	public ProjectDesignerDTO saveOrUpdate(final Project project) {
		final ProjectDesginer projectDb = mapper.map(project, ProjectDesginer.class);
		final String jsonModel = desginerServiceimpl.toJson(project);
		projectDb.setJsonModel(jsonModel);
		projectDb.setVersionProcedure(project.getVersionProcedure());
		return mapper.map(projectDAO.save(projectDb), ProjectDesignerDTO.class);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDesignerDTO> getAllProjects(final TypeWorkFlow type) {
		return ListMapper.mapList(projectDAO.findAllByTypeWorkFlow(type.name()), mapper, ProjectDesignerDTO.class);
	}

	@Override
	public void changeNameProject(final Project project, final String projectNameOld) {
		projectDAO.changeNameProject(project.getName(), projectNameOld);
	}
}
