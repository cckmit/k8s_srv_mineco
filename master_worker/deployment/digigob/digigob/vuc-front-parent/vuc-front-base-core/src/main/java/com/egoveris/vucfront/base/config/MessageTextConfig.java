package com.egoveris.vucfront.base.config;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MessageTextConfig {

	
	private ResourceBundle mensajeResource;
	

	@Value("${idioma.resource:ES}")
	private String idioma;
	
	@Bean
	public ResourceBundle configurarResource() {
		
		if(idioma.equals("ES")) {			
			return ResourceBundle.getBundle("/config/i18n/vuc-front-base-core", new Locale("es", "ES"));
		}else {
			return ResourceBundle.getBundle("/config/i18n/vuc-front-base-core", new Locale("es", "ES"));

		}
		
	}
	
	public String getMensaje(String clave) {
		
		return mensajeResource.getString(clave);
	}
	
	
}
