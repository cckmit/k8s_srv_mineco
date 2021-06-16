package com.egoveris.te.base.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrUtils {
	private static final Logger logger = LoggerFactory.getLogger(SolrUtils.class);

	private static SimpleDateFormat getSDF() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSDF() - start");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
		if (logger.isDebugEnabled()) {
			logger.debug("getSDF() - end - return value={}", sdf);
		}
		return sdf;
	}
	
	public static String dateConvert(Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("dateConvert(fecha={}) - start", fecha);
		}

		String ret=null;
		if(fecha!=null){
			DateFormat df = (DateFormat) SolrUtils.getSDF().clone();
			ret = df.format(fecha);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("dateConvert(Date) - end - return value={}", ret);
		}
		return ret;
	}

	public static String dateFilter(Date fechaDesde, Date fechaHasta) {
		if (logger.isDebugEnabled()) {
			logger.debug("dateFilter(fechaDesde={}, fechaHasta={}) - start", fechaDesde, fechaHasta);
		}

		String desde=SolrUtils.dateConvert(fechaDesde);
		String hasta=SolrUtils.dateConvert(fechaHasta);
		if(desde==null){
			desde="*";
		}
		if(hasta==null){
			hasta="NOW";
		}
		String returnString = "[" + desde + " TO " + hasta + "]";
		if (logger.isDebugEnabled()) {
			logger.debug("dateFilter(Date, Date) - end - return value={}", returnString);
		}
		return returnString;
	}

}

