package com.egoveris.workflow.designer.module.service;

import java.net.URL;
import java.util.List;

import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;

public interface WebDavIntService {
	/**
	 * Crea una carpeta temporal para almacenar los archivos .jar generados duarante la aplicacion
	 * @param directory
	 */
	public void init(String directory);
	
	/**
	 * Retorna la ruta de la carpeta temporal
	 * @return URL
	 */
	public URL getDir();
	
	/**
	 * Setea la ruta de la carpeta temporal
	 * @param dir
	 */
	public void setDir(URL dir);
	
	/**
	 * Guarda el proyecto en formato JSON en el WebDav
	 * @param obj
	 * @return
	 */
	public Project save(Project obj);
	
	/**
	 * retorna la lista de proyectos guardados en el WebDav
	 * @return
	 */
	public List<Project> getAllProjects(TypeWorkFlow type);
	
	/**
	 * Busca un proyecto por nombre
	 * @param projectName
	 * @return
	 */
	public Project findProjectByName(String projectName);
	
	/**
	 * 
	 * @param source
	 * @param destiny
	 * @return
	 */
	public <T> T copy(Object source, Class<?> destiny) ;
}
