/**
 * 
 */
package com.egoveris.workflow.designer.module.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.egoveris.workflow.designer.module.model.Join;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.model.State;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author difarias
 *
 */
public class ConfigTransformer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigTransformer.class);
	private final List<String> HARD_STATES=Arrays.asList("Tramitacion Libre","Guarda Temporal", "Subsanacion");
	
	private String getStringFromResource(String resource) {
		if (logger.isDebugEnabled()) {
			logger.debug("getStringFromResource(String) - start"); //$NON-NLS-1$
		}

		URL urlFileDesicion = this.getClass().getResource(resource);
		
		String wrappedScript = "";
		try {
			URLConnection connection = urlFileDesicion.openConnection();
			wrappedScript = IOUtils.toString(connection.getInputStream());
		} catch (Exception e) {
			logger.error("getStringFromResource(String)", e); //$NON-NLS-1$
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("getStringFromResource(String) - end"); //$NON-NLS-1$
		}
		return wrappedScript;
	}
	
	public Map<String,Object> makeModelMap(Project project) {
		if (logger.isDebugEnabled()) {
			logger.debug("makeModelMap(Project) - start"); //$NON-NLS-1$
		}

		String wrappedScriptGeneric = getStringFromResource("/spring/apiScriptGeneric.js").replace("&", "&amp;");
		String wrappedScripStart = String.format(wrappedScriptGeneric,getStringFromResource("/spring/apiScriptStart.js")).replace("&", "&amp;");;
		String wrappedScriptDesicion = String.format(wrappedScriptGeneric,getStringFromResource("/spring/apiScriptDesicion.js")).replace("&", "&amp;");;
		String wrappedScriptValidation = String.format(wrappedScriptGeneric,getStringFromResource("/spring/apiScriptValidation.js")).replace("&", "&amp;");;
		String wrappedScriptInitialize = String.format(wrappedScriptGeneric,getStringFromResource("/spring/apiScriptInitialize.js")).replace("&", "&amp;");;
		String wrappedScriptFuseTask = String.format(wrappedScriptGeneric,getStringFromResource("/spring/apiScriptFuseTask.js")).replace("&", "&amp;");;
		String wrappedScriptFuseGeneric = String.format(wrappedScriptGeneric,getStringFromResource("/spring/apiScriptFuseGeneric.js")).replace("&", "&amp;");;
		String wrappedScriptStartState = String.format(wrappedScriptGeneric,getStringFromResource("/spring/apiScriptStartState.js")).replace("&", "&amp;");;
		String wrappedScriptEndState = String.format(wrappedScriptGeneric,getStringFromResource("/spring/apiScriptEndState.js")).replace("&", "&amp;");;
		
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("projName", StringUtil.camelName(project.getName()));
		dataModel.put("projNameLower", StringUtil.camelName(project.getName()).toLowerCase());
		List<State> lstStates = new ArrayList<>();
		for (State state : project.getStates()) {
			//Paralelo-Shit
			if(state.getProperties().getStateConnectedToJoinNamed()!= null && 
			  !state.getProperties().getStateConnectedToJoinNamed().isEmpty()){
				for(Join join:project.getJoins()){
					if(join.getProperties().getName().equalsIgnoreCase(state.getProperties().getStateConnectedToJoinNamed())){
						String joinDecition = join.getProperties().getForwardDesicion();
						StringBuilder finalDecition = new StringBuilder(state.getProperties().getForwardDesicion()).append("\n"+joinDecition);
						state.getProperties().setForwardDesicion(finalDecition.toString());	
					}
				}
			}
			//endof Paralelo-Shit
			
			state.getProperties().setStartScript(String.format(wrappedScripStart,state.getProperties().getStartScript()));
			state.getProperties().setInitialize(String.format(wrappedScriptInitialize,state.getProperties().getInitialize()));
			state.getProperties().setForwardValidation(String.format(wrappedScriptValidation,state.getProperties().getForwardValidation()));
			state.getProperties().setForwardDesicion(String.format(wrappedScriptDesicion,state.getProperties().getForwardDesicion()));
			state.getProperties().setScriptFuseTask(String.format(wrappedScriptFuseTask,state.getProperties().getScriptFuseTask()));
			state.getProperties().setScriptFuseTask(String.format(wrappedScriptFuseTask,state.getProperties().getScriptFuseTask()));
			state.getProperties().setScriptStartState(String.format(wrappedScriptStartState,state.getProperties().getScriptStartState()));
			state.getProperties().setScriptEndState(String.format(wrappedScriptEndState,state.getProperties().getScriptEndState()));
			
			if (!HARD_STATES.contains(state.getAttributes().getType())) {
				lstStates.add(state);
			}
		}
		project.setScriptFuseGeneric(String.format(wrappedScriptFuseGeneric,project.getScriptFuseGeneric()));
		dataModel.put("lstStates", lstStates);
		dataModel.put("projScriptFuseGeneric", project.getScriptFuseGeneric());
		
		if (logger.isDebugEnabled()) {
			logger.debug("makeModelMap(Project) - end"); //$NON-NLS-1$
		}
		return dataModel;
	}

	@SuppressWarnings("deprecation")
	public void createConfigFile(Project project, String directory) throws FileNotFoundException, IOException, TemplateException {
		if (logger.isDebugEnabled()) {
			logger.debug("createConfigFile(Project, String) - start"); //$NON-NLS-1$
		}

		String workflowName =StringUtil.camelName(project.getName()); 
		Map<String, Object> dataModel = makeModelMap(project);
		StringUtil sU = new StringUtil(); 
		dataModel.put("stringUtil", sU);
		
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), "/");
		Template temp = cfg.getTemplate("/spring/configTemplate.ftl");

		String directoryDestino = directory+File.separator+"resources";
		String jpdlFile = String.format("%s/config%s.xml", directoryDestino, workflowName);
		FileUtils.forceMkdir(new File(directoryDestino));
		
		FileOutputStream fos = new FileOutputStream(new File(jpdlFile));
		Writer consoleWriter = new OutputStreamWriter(fos);
		temp.process(dataModel, consoleWriter);
		consoleWriter.flush();
		consoleWriter.close();
		fos.close();

		if (logger.isDebugEnabled()) {
			logger.debug("createConfigFile(Project, String) - end"); //$NON-NLS-1$
		}
	}
	
}
