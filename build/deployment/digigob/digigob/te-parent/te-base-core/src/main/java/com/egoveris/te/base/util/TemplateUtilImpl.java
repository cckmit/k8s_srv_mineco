package com.egoveris.te.base.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.egoveris.te.base.model.TemplateDTO;
import com.egoveris.te.base.service.TemplateService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Service
public class TemplateUtilImpl implements TemplateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateUtilImpl.class);

	private TemplateService templateService;
	
	private StringTemplateLoader templates;
	private Configuration templateCfg;
	
	@Autowired
	public TemplateUtilImpl(TemplateService templateService) {
		this.templateService = templateService;
    	this.templates = new StringTemplateLoader();
    	this.templateCfg = new Configuration();
    	this.templateCfg.setTemplateLoader(templates);
    	
    	LOGGER.info("Inicializando templates Freemaker...");
    	for (TemplateDTO template : this.templateService.getByFormato("FREEMAKER")) {
    		this.templates.putTemplate(template.getCodigo(), template.getTemplate());
    		LOGGER.info("Template '" + template.getCodigo() + "' cargado.");
    	}
    	LOGGER.info("Templates Freemaker inicializados.");
	}

	@Override
	public Template getFreemakerTemplate(String nombreTemplate) throws GetTemplateException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getTemplate(String) - start"); //$NON-NLS-1$
        }
            
        Template temp = null;
  
        try {
            temp = this.templateCfg.getTemplate(nombreTemplate);        
        } catch(TemplateNotFoundException e) {
        	LOGGER.info("No se encuentra el template con codigo '" + nombreTemplate + "' en la cache. Se procede a cargarlo.");
        } catch (IOException e) {
			throw new GetTemplateException(e.getMessage(), e);
		}
        

        return temp;
	}

	@Override
	public String getStringTemplate(String nombreTemplate) throws GetTemplateException {
		return this.templateService.getByCodigo(nombreTemplate);
	}

}
