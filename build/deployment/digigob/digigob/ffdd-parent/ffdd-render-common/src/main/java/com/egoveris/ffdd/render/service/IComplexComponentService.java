package com.egoveris.ffdd.render.service;

import com.egoveris.ffdd.render.model.ComplexComponent;

public interface IComplexComponentService {

	/**
	 * Gets the complex component definition.
	 * 
	 * @param name
	 * @return
	 */
	public ComplexComponent getComponent(String name);

	public Boolean hasComponentDTO(String name);
}
