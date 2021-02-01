package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DocumentoOArchivoNoEncontradoException extends ApplicationException{

  private static final long serialVersionUID = 9085187254293169875L;

  public DocumentoOArchivoNoEncontradoException(String msg, Throwable e){
		super(msg, e);
	}
	
	public DocumentoOArchivoNoEncontradoException(Throwable e){
		super("Documento o archivo no encontrado", e);
	}
}
