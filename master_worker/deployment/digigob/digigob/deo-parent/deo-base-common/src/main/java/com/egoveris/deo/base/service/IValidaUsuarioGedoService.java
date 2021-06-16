package com.egoveris.deo.base.service;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface IValidaUsuarioGedoService {

	public boolean validaUsuarioGedo(String username ) throws ApplicationException;
}
