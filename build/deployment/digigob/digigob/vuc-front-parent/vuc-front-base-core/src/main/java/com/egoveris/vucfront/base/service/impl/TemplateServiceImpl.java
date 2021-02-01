package com.egoveris.vucfront.base.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.base.repository.TemplateRepository;
import com.egoveris.vucfront.base.service.TemplateService;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Service
public class TemplateServiceImpl implements TemplateService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateServiceImpl.class);

	
	private StringTemplateLoader templates;
	private Configuration templateCfg;

	private TemplateRepository repository;

	@Autowired
	public TemplateServiceImpl(TemplateRepository repository) {
		this.repository = repository;
    	this.templates = new StringTemplateLoader();
    	this.templateCfg = new Configuration();
    	this.templateCfg.setTemplateLoader(templates);
    	
    	LOGGER.info("Inicializando templates Freemaker...");
		for (com.egoveris.vucfront.base.model.Template template : repository.findAll()) {
    		this.templates.putTemplate(template.getCodigo(), template.getTemplate());
    		LOGGER.info("Template '" + template.getCodigo() + "' cargado.");
    	}
    	LOGGER.info("Templates Freemaker inicializados.");

	}

	@Override
	public Template getTemplate(String codigo) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		return this.templateCfg.getTemplate(codigo);
	}

}
