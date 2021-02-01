package com.egoveris.workflow.designer.module.service;

import java.io.IOException;
import java.util.List;

import com.egoveris.workflow.designer.module.exception.DesginerException;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.model.ProjectDesignerDTO;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;


public interface DesignerService {
	
	/**
	 * Tranforma un Objeto a String con formato JSON.
	 * @param obj
	 * @return
	 */
	public String toJson(Object obj);
	
	/**
	 * Transforma un String con formato JSON  a una clase especifica que recibe por parametro
	 * @param json
	 * @param clazz
	 * @return
	 */
	public <V> V fromJson(String json, Class<?> clazz);
	
	/**
	 * Obtiene contenido de un archivo desde una ruta especifica.
	 * @param resource
	 * @return
	 * @exception IOException
	 */
	public String getStringFromResource(String resource) throws IOException;
	
	/**
	 * Guarda en el proyecto en formato JSON en la aplicacion guarda-documental.
	 * @param projectJson
	 * @param name
	 * @param nameSpace
	 * @return
	 */
	public Boolean saveOnWevDav(String projectJson, String name, String nameSpace);
	
	/**
	 * Guarda el archivo .jar en la aplicacion guarda-documental.
	 * @param project
	 * @param name
	 * @param nameSpace
	 * @return
	 * @throws DesginerException
	 */
	public Boolean saveJarOnWevDav(Project project, String name, String nameSpace) throws DesginerException;
	
	/**
	 * Recupera los proyectos almacenados en el WebDav.
	 * @param nameSpace
	 * @return
	 */
	public List<String> getListOfWebDav(String nameSpace);
	
	/**
	 * Obtiene un proyecto especifico guardado anteriormente en el WebDav (proyecto en formato JSON).
	 * @param nameSpace
	 * @param fileName
	 * @return
	 */
	public Project getProjectFromWebDav(String nameSpace, String fileName, TypeWorkFlow type);
	
	
	/**
	 * Save or update project
	 */
	public void saveOrUpdateProject(Project project);
	
	
	/**
	 * find all projects en database by type of workflow
	 * @return
	 */
	public List<ProjectDesignerDTO> getAllProjects(TypeWorkFlow type);
	
	/**
	 * 
	 * @param project
	 * @param projectNameOld
	 */
	public void changeNameProject(Project project , String projectNameOld);
	
}
