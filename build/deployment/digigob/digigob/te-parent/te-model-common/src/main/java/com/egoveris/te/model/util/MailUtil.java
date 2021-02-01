package com.egoveris.te.model.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Clase que posee metodo para validar un email
 * @author eduavega
 */
public class MailUtil {
	private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

	public static boolean validateMail(String hex){
		if (logger.isDebugEnabled()) {
			logger.debug("validateMail(hex={}) - start", hex);
		}
		
		Pattern pattern;
		
		Matcher matcher;
	 
		pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
	 
		matcher = pattern.matcher(hex);
	 
		boolean returnboolean = matcher.matches();
		if (logger.isDebugEnabled()) {
			logger.debug("validateMail(String) - end - return value={}", returnboolean);
		}
		return returnboolean;
	}
}
