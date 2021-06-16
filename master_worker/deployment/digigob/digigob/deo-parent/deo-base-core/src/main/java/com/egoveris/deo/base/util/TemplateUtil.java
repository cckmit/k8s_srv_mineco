package com.egoveris.deo.base.util;

import freemarker.template.Template;

public interface TemplateUtil {

	public Template getFreemakerTemplate(String nombreTemplate) throws GetTemplateException;
	public String getStringTemplate(String nombreTemplate) throws GetTemplateException;

}
