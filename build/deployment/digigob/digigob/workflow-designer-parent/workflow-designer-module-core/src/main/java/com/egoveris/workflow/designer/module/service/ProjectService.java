package com.egoveris.workflow.designer.module.service;

import java.util.List;

import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.model.ProjectDesignerDTO;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;

public interface ProjectService {

	/**
	 * 
	 * @param project
	 * @return
	 */
	public ProjectDesignerDTO saveOrUpdate(Project  project);
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<ProjectDesignerDTO> getAllProjects(TypeWorkFlow type);
	
	/**
	 * 
	 * @param project
	 * @param projectNameOld
	 */
	public void changeNameProject(Project project, String projectNameOld);
	
}
