package com.egoveris.te.base.util;

import com.egoveris.te.base.model.TrataDTO;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparadorTrata implements Comparator<TrataDTO>{
	private static final Logger logger = LoggerFactory.getLogger(ComparadorTrata.class);

	

	public int compare(TrataDTO o1, TrataDTO o2) {
		if (logger.isDebugEnabled()) {
			logger.debug("compare(o1={}, o2={}) - start", o1, o2);
		}
	
		int returnint = o1.getCodigoTrata().compareTo(o2.getCodigoTrata());
		if (logger.isDebugEnabled()) {
			logger.debug("compare(Trata, Trata) - end - return value={}", returnint);
		}
		return returnint;
	}

}
