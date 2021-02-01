/**
 * 
 */
package com.egoveris.plugins.manager.deployers;

import com.egoveris.plugins.manager.IDeployHandler;

/**
 * @author difarias
 *
 */
public interface IDeployer {
	public void setDeployHandler(IDeployHandler deployHandler);
	public void setUrlToMonitoring(String UrlToMonitoring);
	public String getUrlToMonitoring();
	public void start();
	public void pause();
	public void stop();
}
