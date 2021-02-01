package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Excepcion usada para el caso en que una determinada variable no
 * se encuentre dentro del workflow
 * 
 * @author Juan Pablo Norverto
 *
 */
public class VariableWorkFlowNoExisteException extends ApplicationException {

	private static final long serialVersionUID = 2990773469341207027L;
	

 public VariableWorkFlowNoExisteException(Throwable cause) {
   super("No existe la variable en el Work Flow", cause);
 }

 public VariableWorkFlowNoExisteException(String mensaje, Throwable cause) {
   super(mensaje, cause); 
 }

}
