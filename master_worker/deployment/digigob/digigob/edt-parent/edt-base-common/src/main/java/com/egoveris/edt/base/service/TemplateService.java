package com.egoveris.edt.base.service;

import java.util.List;

import com.egoveris.edt.base.model.eu.TemplateDTO;

public interface TemplateService {

	public String getByCodigo(String codigo);
	public List<TemplateDTO> getByFormato(String formato);
	
}
