package com.egoveris.edt.base.service.impl.mail;

import freemarker.template.Template;

public interface TemplateUtil {

	public Template getFreemakerTemplate(String nombreTemplate) throws GetTemplateException;
	public String getStringTemplate(String nombreTemplate) throws GetTemplateException;

}
