/**
 * 
 */
package com.egoveris.te.base.service.iface;

import java.io.Serializable;

/**
 * The Interface IDelegable.
 *
 * @author difarias
 */
public interface IDelegable extends Serializable {
	
	/**
	 * Initialize.
	 */
	public void initialize();
	public void setCallbackObject(Object callbackObject);
}
