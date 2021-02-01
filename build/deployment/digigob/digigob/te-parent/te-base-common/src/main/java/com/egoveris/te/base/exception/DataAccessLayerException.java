package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Indica que hubo un problema en la capa de acceso a datos
 * 
 * @author Juan Pablo Norverto
 *
 */
public class DataAccessLayerException extends ApplicationException {

	private static final long serialVersionUID = 6700145704366231855L;

 public DataAccessLayerException(Throwable cause) {
   super("Se ha producido un error al acceder a la aplicación", cause);
 }

 public DataAccessLayerException(String mensaje, Throwable cause) {
   super(mensaje, cause); 
 }
 
}
