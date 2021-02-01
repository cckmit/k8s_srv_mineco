package com.egoveris.deo.base.service;

import java.util.List;

import com.egoveris.deo.model.model.TemplateDTO;

public interface TemplateService {

	public String getByCodigo(String codigo);

	public List<TemplateDTO> getByFormato(String formato);
	
}
