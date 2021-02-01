package com.egoveris.workflow.designer.module.exception;

public class TransformerException  extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5224491130008513535L;
	public TransformerException(){
		super();
	}
	
	public TransformerException(String msj){
		super(msj);
	}
	
	public TransformerException(String msj, Throwable e){
		super(msj, e);
	}
}
