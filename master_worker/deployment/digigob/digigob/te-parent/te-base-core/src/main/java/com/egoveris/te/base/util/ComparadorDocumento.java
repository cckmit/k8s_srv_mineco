package com.egoveris.te.base.util;

import com.egoveris.te.base.model.DocumentoDTO;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ComparadorDocumento implements Comparator<DocumentoDTO> {
	private static final Logger logger = LoggerFactory.getLogger(ComparadorDocumento.class);

	@Override
	public int compare(DocumentoDTO o1, DocumentoDTO o2) {
		if (logger.isDebugEnabled()) {
			logger.debug("compare(o1={}, o2={}) - start", o1, o2);
		}

		int returnint = o1.getFechaAsociacion().compareTo(o2.getFechaAsociacion());
		if (logger.isDebugEnabled()) {
			logger.debug("compare(Documento, Documento) - end - return value={}", returnint);
		}
		return returnint;
	}

}
