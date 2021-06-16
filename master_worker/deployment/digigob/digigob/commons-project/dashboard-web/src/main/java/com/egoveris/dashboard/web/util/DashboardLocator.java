package com.egoveris.dashboard.web.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.zkoss.util.resource.LabelLocator;

public class DashboardLocator implements LabelLocator {
	
	private URL location;
	
	private String baseUrl;
	/**
	 * Instancia de DashboardLocator
	 * 
	 * @param fileName Nombre del archivo properties
	 * @param locale Idioma
	 * @throws MalformedURLException 
	 */
	public DashboardLocator(String fileName, Locale locale, String baseUrl) throws MalformedURLException {
		// Intenta cargar el idioma especificado
//		location = getClass().getClassLoader().getResource("web/" + fileName + "_" + locale.getLanguage() + ".properties");
		this.baseUrl = baseUrl;
		location = new URL(this.baseUrl + "/" + fileName + "_" + locale.getLanguage() + ".properties");
		
		// Idioma por defecto, si no encuentra el idioma especificado
		if (location == null) {
			location =  new URL(this.baseUrl + "/" + fileName + ".properties");
		}
	}
	
	@Override
	public URL locate(Locale locale) {
		return location;
	}
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
}
