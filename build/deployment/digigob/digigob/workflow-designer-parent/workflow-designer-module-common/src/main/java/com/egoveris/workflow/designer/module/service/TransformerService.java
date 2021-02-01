package com.egoveris.workflow.designer.module.service;

import java.io.File;
import java.util.jar.Manifest;

import com.egoveris.workflow.designer.module.exception.TransformerException;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;

public interface TransformerService {

	/**
	 * Crea archivo MANIFEST.MF que contiene la metadata del archivo .jar
	 * @param project
	 * @param activationClassName
	 * @return
	 * @throws TransformerException
	 */
	public Manifest createMetaInf(Project project, String activationClassName) throws TransformerException;
	
	/**
	 * Crea el metodo que ejecutara el deploy del archivo
	 * @param workflowName
	 * @return
	 */
	public String onDeployMethodTemplate(String workflowName, int version, TypeWorkFlow  type) ;
	
	/**
	 * 
	 * @param workflowName
	 * @param stateClassName
	 * @return
	 */
	public String onActivateMethodTemplate(String workflowName, String stateClassName) ;
	
	/**
	 * 
	 * @param project
	 * @param stateClassObj
	 * @param directory
	 * @return
	 * @throws TransformerException
	 */
	public String createActivationClass(Project project, Object stateClassObj, String directory) throws TransformerException;
	
	/**
	 * 
	 * @param project
	 * @param directory
	 * @param templateClass
	 * @param stateName
	 * @return
	 * @throws TransformerException
	 */
	public Object createStateClass(Project project, String directory, Class<?> templateClass, String stateName) throws TransformerException;
	
	/**
	 * 
	 * @param manifest
	 * @param archiveFile
	 * @param tobeJared
	 * @param baseDirectory
	 * @throws TransformerException
	 */
	public void createJarArchive(Manifest manifest, File archiveFile, File[] tobeJared, String baseDirectory) throws TransformerException;
	
	/**
	 * 
	 * @param project
	 * @return
	 * @throws TransformerException
	 */
	public String createProjectJar(Project project) throws TransformerException;

}
