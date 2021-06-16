package com.egoveris.ffdd.render.service;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioDTO;

public interface IFormManagerFactory<T extends IFormManager<?>> {

	public T create(String formId) throws DynFormException;
	
	public T create(FormularioDTO form) throws DynFormException;
	
	public FormularioDTO buscarFormulario(String formId) throws DynFormException;	
}
