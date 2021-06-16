package com.egoveris.plugins.manager.deployers.strategies;

import java.util.List;
import java.util.Map;

import com.egoveris.plugins.manager.Plugin;
import com.egoveris.plugins.manager.dao.IDao;
import com.egoveris.plugins.manager.model.PluginInfo;
import com.egoveris.plugins.manager.model.Version;
import com.egoveris.plugins.manager.tools.JarClassLoader;

public interface IDeployStrategy {

	public abstract Version getActiveVersion();

	/**
	 * @return the classInstances
	 */
	public abstract Map<String, List<Object>> getClassInstances();

	public abstract List<Object> getAllClassInstances();

	/**
	 * @param workingDirectory the workingDirectory to set
	 */
	public abstract void setWorkingDirectory(String workingDirectory);

	/**
	 * @return the plugins
	 */
	public abstract List<Plugin> getPlugins();

	/**
	 * @return the lstPluginInfo
	 */
	public abstract List<PluginInfo> getLstPluginInfo();

	/**
	 * @param dao the dao to set
	 */
	public abstract void setDao(IDao dao);

	public abstract JarClassLoader getActiveClassLoader();

	/**
	 * Method to get the PluginInfo from all installed plugins
	 * @return List<PluginInfo>
	 */
	public abstract List<PluginInfo> getPluginsInfo();

	public abstract void printVersions();

	public abstract void switchVersion(String newVersion);

	/**
	 * Method to do a deploy
	 * @param context
	 * @param pluginJar
	 * @param isRedeploy
	 */
	public abstract void deploy(Map<String, Object> context, List<String> pluginJar, boolean isRedeploy);

	/**
	 * Method to initialize
	 * @param context
	 */
	public abstract void initialize(Map<String, Object> context);

}