package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.TemplateDTO;

public interface TemplateService {

	public String getByCodigo(String codigo);
	public List<TemplateDTO> getByFormato(String formato);
	
}
