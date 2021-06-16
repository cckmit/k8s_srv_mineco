/**
 * 
 */
package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * @author jnorvert
 *
 */
public class AsignacionException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 84203383776291536L;

 public AsignacionException(Throwable cause) {
   super("Se ha producido un error al acceder a la aplicación", cause);
 }

 public AsignacionException(String mensaje, Throwable cause) {
   super(mensaje, cause); 
 }
}
