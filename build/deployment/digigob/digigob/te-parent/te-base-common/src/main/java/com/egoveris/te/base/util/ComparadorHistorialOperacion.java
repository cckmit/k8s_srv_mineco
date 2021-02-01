package com.egoveris.te.base.util;

import com.egoveris.te.base.model.HistorialOperacionDTO;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparadorHistorialOperacion implements Comparator<HistorialOperacionDTO> {
	private static final Logger logger = LoggerFactory.getLogger(ComparadorHistorialOperacion.class);

	public int compare(HistorialOperacionDTO hist1, HistorialOperacionDTO hist2) {
		if (logger.isDebugEnabled()) {
			logger.debug("compare(hist1={}, hist2={}) - start", hist1, hist2);
		}

		int returnint = hist2.getFechaOperacion().compareTo(hist1.getFechaOperacion());
		if (logger.isDebugEnabled()) {
			logger.debug("compare(HistorialOperacion, HistorialOperacion) - end - return value={}", returnint);
		}
		return returnint;
	}

}
