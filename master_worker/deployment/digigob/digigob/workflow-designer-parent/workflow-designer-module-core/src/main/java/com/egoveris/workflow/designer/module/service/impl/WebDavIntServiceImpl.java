/**
 * 
 */
package com.egoveris.workflow.designer.module.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.service.DesignerService;
import com.egoveris.workflow.designer.module.service.WebDavIntService;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;

@Service
public class WebDavIntServiceImpl implements WebDavIntService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(WebDavIntServiceImpl.class);

	private URL dir = null;
	private String nameSpace="Workflow_Designer/Workflows/SRC/";
	@Autowired
	private DesignerService designerHelper; 
	
	public void init(String directory) {
		if (logger.isDebugEnabled()) {
			logger.debug("init(String) - start"); //$NON-NLS-1$
		}
		try {
			dir = new URL("file:///"+System.getProperty("java.io.tmpdir")+"/Designer");
			FileUtils.forceMkdir(new File(dir.getPath()));
		} catch (IOException  e) {
			logger.error("init(String)", e); //$NON-NLS-1$

		}
		if (logger.isDebugEnabled()) {
			logger.debug("init(String) - end"); //$NON-NLS-1$
		}
	}
	
	public URL getDir() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDir() - start"); //$NON-NLS-1$
		}

		if (dir==null) {
			init(null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDir() - end"); //$NON-NLS-1$
		}
		return dir;
	}

	public void setDir(URL dir) {
		this.dir = dir;
	}

	public Project save(Project obj) {
		if (logger.isDebugEnabled()) {
			logger.debug("save(Project) - start"); //$NON-NLS-1$
		}

		String json = designerHelper.toJson(obj);
		if(json!=null){
			try{
				String nameSpaceWithout = "";
				if (nameSpace.length() > 0 && nameSpace.charAt(nameSpace.length()-1)=='/') {
					nameSpaceWithout = nameSpace.substring(0, nameSpace.length()-1);
				}
				designerHelper.saveOnWevDav(json,obj.getName()+".json",nameSpaceWithout);
			}catch (Exception e) {
				logger.error("save(Project)", e); //$NON-NLS-1$

				
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("save(Project) - end"); //$NON-NLS-1$
		}
		return obj;
	}
	
	public List<Project> getAllProjects(TypeWorkFlow type) {
		if (logger.isDebugEnabled()) {
			logger.debug("getAllProjects() - start"); //$NON-NLS-1$
		}

		List<String> fileNames = new ArrayList<String>();
		fileNames = designerHelper.getListOfWebDav(nameSpace);
		List<Project> lstProjects = new ArrayList<Project>();
		for(String fileName : fileNames){
			Project project = designerHelper.getProjectFromWebDav(nameSpace,fileName,type);
			
			if(project != null){
				lstProjects.add(project);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getAllProjects() - end"); //$NON-NLS-1$
		}
		return lstProjects;
	}
	
	public Project findProjectByName(String projectName) {
		if (logger.isDebugEnabled()) {
			logger.debug("findProjectByName(String) - start"); //$NON-NLS-1$
		}

		List<Project> lstProjects = getAllProjects(TypeWorkFlow.ALL);
		for(Project project: lstProjects) {
			if (project.getName().equalsIgnoreCase(projectName)) return project;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("findProjectByName(String) - end"); //$NON-NLS-1$
		}
		return null;
	}	
	
	@SuppressWarnings("unchecked")
	public <T> T copy(Object source, Class<?> destiny) {
		if (logger.isDebugEnabled()) {
			logger.debug("copy(Object, Class<?>) - start"); //$NON-NLS-1$
		}
		Object destObj = null;
		try {
			destObj = destiny.newInstance();
			for (Field field: destObj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Field myData;
				try {
					myData = source.getClass().getDeclaredField(field.getName());
					if (myData!=null) {
						myData.setAccessible(true);
						try {
							field.set(destObj, myData.get(source));
						} catch (IllegalAccessException e) {
							logger.error("copy(Object, Class<?>)", e); //$NON-NLS-1$
							field.set(destObj,copy(myData.get(source), field.getType()));
						}
					}
				} catch (SecurityException |  NoSuchFieldException e) {
					logger.error("copy(Object, Class<?>)", e); //$NON-NLS-1$
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("copy(Object, Class<?>)", e); //$NON-NLS-1$
		}
		
		T returnT = (T) destObj;
		if (logger.isDebugEnabled()) {
			logger.debug("copy(Object, Class<?>) - end"); //$NON-NLS-1$
		}
		return returnT;
	}
	
}
