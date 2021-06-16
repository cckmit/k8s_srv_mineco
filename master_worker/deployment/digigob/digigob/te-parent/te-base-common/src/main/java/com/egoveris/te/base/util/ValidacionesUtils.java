package com.egoveris.te.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;



/**
 * 
 * @author matalvar
 *
 */
public class ValidacionesUtils {
	private static final Logger logger = LoggerFactory.getLogger(ValidacionesUtils.class);

	 public static boolean poseeSoloNumeros (String numeroDocumentotbx){
		if (logger.isDebugEnabled()) {
			logger.debug("poseeSoloNumeros(numeroDocumentotbx={}) - start", numeroDocumentotbx);
		}

		boolean returnboolean = (Pattern.matches("^\\d+$", numeroDocumentotbx));
		if (logger.isDebugEnabled()) {
			logger.debug("poseeSoloNumeros(String) - end - return value={}", returnboolean);
		}
				return  returnboolean;
	 }
}
