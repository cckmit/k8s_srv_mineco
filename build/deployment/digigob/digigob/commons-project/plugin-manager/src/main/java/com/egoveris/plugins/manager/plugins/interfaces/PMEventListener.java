/**
 * 
 */
package com.egoveris.plugins.manager.plugins.interfaces;

import java.util.List;

import com.egoveris.plugins.manager.model.PluginInfo;

/**
 * PluginManager EventListener
 * @author difarias
 *
 */
public interface PMEventListener {
	/**
	 * Method to know when a plugin was deployed 
	 * @param pluginInfo
	 */
	public void onDeploy(PluginInfo pluginInfo);
	/**
	 * Method to know when a plugin was activated 
	 * @param pluginInfo
	 */
	public void onActivation(PluginInfo pluginInfo);
	/**
	 * Method to know when all plugins was activated 
	 * @param pluginInfo
	 */
	public void onFullActivation(PluginInfo... lstPluginInfo);
}
