/**
 * 
 */
package com.egoveris.plugins.manager.plugins.interfaces;

import java.util.Map;

import com.egoveris.plugins.manager.plugins.exceptions.ExecutableException;

/**
 * @author difarias
 *
 */
public interface IExecutable {
	public Object execute(Map<String,Object> context) throws ExecutableException;
}
