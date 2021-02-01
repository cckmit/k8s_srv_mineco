package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Indica que la entidad que se deseaba persistir no pudo ser persistida.
 * 
 * @author Juan Pablo Norverto
 *
 */
public class SinPersistirException extends ApplicationException {

	private static final long serialVersionUID = 5377904919550727751L;

 public SinPersistirException(Throwable cause) {
   super("No se ha podido persistir en base de datos", cause);
 }

 public SinPersistirException(String mensaje, Throwable cause) {
   super(mensaje, cause); 
 }

}
