package com.egoveris.vucfront.base.service;

import java.io.IOException;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

public interface TemplateService {

	public Template getTemplate(String codigo) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException ;
	
}
