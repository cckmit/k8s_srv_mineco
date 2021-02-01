package com.egoveris.deo.base.util;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Util {

	@Value("${app.gedo.url}")
	private String appUrl;
	
	public String generateUrlDescargaDocumentoPublicable(String nombreArchivo) {
		byte[] hash = Base64.getEncoder().encode(nombreArchivo.getBytes());
		return String.format("%s/descargar-documento/%s", this.appUrl, hash);
	}
	
}
