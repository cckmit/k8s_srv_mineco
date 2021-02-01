/**
 * 
 */
package com.egoveris.plugins.manager;

import java.util.List;

/**
 * @author difarias
 *
 */
public interface IDeployHandler {
	public void deploy(String... pluginJar);
	public void deploy(List<String> pluginJar);
	public void redeploy(String... pluginJar);
	public void redeploy(List<String> pluginJar);
}
