package com.egoveris.workflow.designer.module.exception;

public class DesginerException  extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4647359922190080810L;

	public DesginerException(String msg){
		super(msg);
	}
	
	public DesginerException(String msg, Throwable e){
		super(msg, e);
	}
}
