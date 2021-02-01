package com.egoveris.te.base.util;

import com.egoveris.te.base.model.HistorialOperacionDTO;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparadorHistorial implements Comparator<HistorialOperacionDTO>{
	private static final Logger logger = LoggerFactory.getLogger(ComparadorHistorial.class);

	@Override
	public int compare(HistorialOperacionDTO o1, HistorialOperacionDTO o2) {
		if (logger.isDebugEnabled()) {
			logger.debug("compare(o1={}, o2={}) - start", o1, o2);
		}
	
		int returnint = o2.getFechaOperacion().compareTo(o1.getFechaOperacion());
		if (logger.isDebugEnabled()) {
			logger.debug("compare(HistorialOperacion, HistorialOperacion) - end - return value={}", returnint);
		}
		return returnint;
	}
}
	