/**
 * 
 */
package com.egoveris.plugins.manager.plugins.interfaces;

import com.egoveris.plugins.manager.model.ExecutableInfo;

/**
 * @author difarias
 *
 */
public interface IAuthorization {
	public boolean isAutorized(ExecutableInfo execInfo);
}
