package com.egoveris.te.base.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipoDocumentoUtils {
	private static final Logger logger = LoggerFactory.getLogger(TipoDocumentoUtils.class);

	
	public static final List<String> getTiposDocumentos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTiposDocumentos() - start");
		}
		
		
		List<String> tiposDeDocumentos = new ArrayList<>(TipoDocumentoPosible.values().length);
			
		for (TipoDocumentoPosible tipoDocumentoPosible : TipoDocumentoPosible.values()) {
			tiposDeDocumentos.add(tipoDocumentoPosible.getDescripcionCombo());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTiposDocumentos() - end - return value={}", tiposDeDocumentos);
		}
		return tiposDeDocumentos;
	
	}
	
}
