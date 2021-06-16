/**
 * 
 */
package com.egoveris.plugins.manager.deployers.strategies;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.egoveris.plugins.manager.Plugin;
import com.egoveris.plugins.manager.helpers.EventManagerHelper;
import com.egoveris.plugins.manager.tools.JarClassLoader;

/**
 * @author difarias
 *
 */
public class ProgressiveDeploy extends RawDeploy {
	
	private void copyToSnapshotDir(File jarFile) {
		try {
			URL url = new URL(getWorkingDirectory());
			String destDir = url.getFile()+File.separator+getActiveVersion().getSnapshotDir();
			File destination = new File(destDir);
			FileUtils.copyFileToDirectory(jarFile, destination, false);
			String destinationFile = destination.getAbsolutePath()+File.separator+FilenameUtils.getName(jarFile.getAbsolutePath());
			getActiveVersion().getFiles().add(destinationFile);
			getDao().save(getActiveVersion());
		} catch (Exception e) {
		  logger.error("copyToSnapshotDir", e);		
		}
	}
	
	private void activateScripts(Plugin plug, Map<String, Object> context) {
		Map<String, Map<String, Class>> scriptGroup = new HashMap<String, Map<String,Class>>();

		plug.activate(getActiveClassLoader(), true, context);
		
		Map<String, Map<String, Class>> obj = plug.findScriptables(getActiveClassLoader());
		if (obj!=null) {
			for(Map.Entry<String, Map<String,Class>> entry: obj.entrySet()) {
				if (scriptGroup.containsKey(entry.getKey())) {
					for (Map.Entry<String, Class> entryClass: entry.getValue().entrySet()) {
						scriptGroup.get(entry.getKey()).put(entryClass.getKey(),entryClass.getValue());
					}
				} else {
					scriptGroup.put(entry.getKey(),entry.getValue());
				}
			}
		}
		
		EventManagerHelper.getInstance().notifyActivation(plug.getPluginInfo());
		activarScriptables(scriptGroup);
	}
	
	private void addNewJarFiles(List<String> jarFilesToAdd, Map<String, Object> context) {
		//getActiveClassLoader().includeJar(jarFilename);
		File newJarFile;
		for (String jarFile: jarFilesToAdd) {
			System.out.println("ARCHIVO NUEVO -->"+jarFile);
			newJarFile = new File(jarFile);
			
			copyToSnapshotDir(newJarFile);
			
			Plugin newPlugin = new Plugin(jarFile);
			newPlugin.activate(getActiveClassLoader(), true, context);
			getPlugins().add(newPlugin);
			getActiveClassLoader().includeJar(newPlugin.getMetadata().getFullFilename());
			
			activateScripts(newPlugin, context);
			getLastDeploys().add(jarFile);
		}
	}
	
	private void probeNewFiles(Map<String, Object> context, final List<String> pluginsToReview, boolean isRedeploy) {
		getActiveVersion().getFiles();
		final List<String> newFiles = new ArrayList<String>();
		List<String> modFiles = (List<String>) CollectionUtils.select(pluginsToReview, new Predicate<String>() {
			/* (non-Javadoc)
			 * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
			 */
			@Override
			public boolean evaluate(String filename) {
				Plugin modifiedPlugin = null;
				for (Plugin plugin : getPlugins()) {
					String deployed = FilenameUtils.getName(plugin.getMetadata().getFilename());
					String fileToDeploy = FilenameUtils.getName(filename);
					if (deployed.equalsIgnoreCase(fileToDeploy)) {
						modifiedPlugin=plugin;
						break;
					}
				}
				
				try {
					if (modifiedPlugin!=null ) {						
						return !FileUtils.contentEquals(modifiedPlugin.getJarFile(), new File(filename));
					}
				} catch (IOException e) {
			   logger.error("error evaluate", e);
				}
				
				newFiles.add(filename);
				return false;
			}
		});
		
		// --- estimate removed files ---
		List<String> removedFiles = new ArrayList<String>();
		for (Plugin plugin: getPlugins()) {
			String toCheckRemove = FilenameUtils.getName(plugin.getMetadata().getFilename());
			boolean fileExist=false;
			for (String filename: pluginsToReview) {
				String fileToDeploy = FilenameUtils.getName(filename);
				if (toCheckRemove.equals(fileToDeploy)) {
					fileExist = true;
					break;
				}
			}
			if (!fileExist) {
				removedFiles.add(plugin.getMetadata().getFilename());
			}
		}
		
		boolean needNewClassLoader=(removedFiles!=null && !removedFiles.isEmpty()) || (modFiles!=null && !modFiles.isEmpty()) ;
		boolean newFileExist=(newFiles!=null && !newFiles.isEmpty()) ;
		
		for (String filename: removedFiles) {
			System.out.println("ARCHIVO ELIMINADO -->"+filename);
		}
		for (String filename: modFiles) {
			System.out.println("ARCHIVO MODIFICADO -->"+filename);
		}
//		for (String filename: newFiles) {
//			System.out.println("ARCHIVO NUEVO -->"+filename);
//		}
		
		if (needNewClassLoader) {
			super.deploy(context, pluginsToReview, isRedeploy);
		} else {
			if (newFileExist) {
				addNewJarFiles(newFiles,context);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.strategies.RawDeploy#deploy(java.util.Map, java.util.List, boolean)
	 */
	@Override
	public void deploy(Map<String, Object> context, List<String> pluginJar, boolean isRedeploy) {
		JarClassLoader currentClassLoader = getActiveClassLoader();
		
		if (currentClassLoader!=null) {
			probeNewFiles(context,pluginJar,isRedeploy);
		} else {
			super.deploy(context, pluginJar, isRedeploy);
		}
	}

	/* (non-Javadoc)
	 * @see com.egoveris.plugins.manager.deployers.strategies.RawDeploy#initialize(java.util.Map)
	 */
	@Override
	public void initialize(Map<String, Object> context) {
		super.initialize(context);
	}
	
}
