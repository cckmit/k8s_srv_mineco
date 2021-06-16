package com.egoveris.deo.base.exception;

import java.io.Serializable;

import org.terasoluna.plus.common.exception.ApplicationException;

public class TemplateMalArmadoException extends ApplicationException implements Serializable{

	private static final long serialVersionUID = 1L;

	public TemplateMalArmadoException(String mensajeErrorParametro) {
		super(mensajeErrorParametro);
	}

	public TemplateMalArmadoException(String message, Throwable cause) {
		super(message, cause);
	}
}
