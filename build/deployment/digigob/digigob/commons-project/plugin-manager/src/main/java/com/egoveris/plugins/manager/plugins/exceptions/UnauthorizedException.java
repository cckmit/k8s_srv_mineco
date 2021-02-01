/**
 * 
 */
package com.egoveris.plugins.manager.plugins.exceptions;

import com.egoveris.plugins.manager.model.ExecutableInfo;

/**
 * @author difarias
 *
 */
@SuppressWarnings("serial")
public class UnauthorizedException extends Exception {
	private transient ExecutableInfo execInfo;
	
	public UnauthorizedException(ExecutableInfo execInfo,String msg) {
		super(msg);
		this.execInfo=execInfo;
	}
	
	public ExecutableInfo getExecutableInfo() {
		return this.execInfo;
	}
}
