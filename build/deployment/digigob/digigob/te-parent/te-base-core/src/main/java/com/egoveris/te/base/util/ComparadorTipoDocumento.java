package com.egoveris.te.base.util;

import com.egoveris.te.base.model.TipoDocumentoDTO;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparadorTipoDocumento implements Comparator<TipoDocumentoDTO> {
	private static final Logger logger = LoggerFactory.getLogger(ComparadorTipoDocumento.class);

	public int compare(TipoDocumentoDTO tipoDocumento1, TipoDocumentoDTO tipoDocumento2) {
		if (logger.isDebugEnabled()) {
			logger.debug("compare(tipoDocumento1={}, tipoDocumento2={}) - start", tipoDocumento1, tipoDocumento2);
		}

		int returnint = tipoDocumento1.getAcronimo().compareTo(tipoDocumento2.getAcronimo());
		if (logger.isDebugEnabled()) {
			logger.debug("compare(TipoDocumento, TipoDocumento) - end - return value={}", returnint);
		}
		return returnint;
	}

}
