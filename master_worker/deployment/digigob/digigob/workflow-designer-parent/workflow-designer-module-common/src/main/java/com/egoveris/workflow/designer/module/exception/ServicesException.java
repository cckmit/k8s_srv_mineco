package com.egoveris.workflow.designer.module.exception;

public class ServicesException   extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5546256669048634579L;

	public ServicesException(){
		super();
	}
	
	public ServicesException(String msj, Throwable e){
		super(msj, e);
	}
}
