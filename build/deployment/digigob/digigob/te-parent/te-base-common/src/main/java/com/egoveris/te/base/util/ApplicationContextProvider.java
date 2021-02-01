package com.egoveris.te.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Permite modificar el contexto de spring que esta utilizandose.
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextProvider.class);

	@Autowired
	private static Object returnObject;
	private static ApplicationContext applicationContext = null;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ApplicationContextProvider.applicationContext=applicationContext;

	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getService(String serviceName){
		if (logger.isDebugEnabled()) {
			logger.debug("getService(serviceName={}) - start", serviceName);
		}

		 
		if (logger.isDebugEnabled()) {
			logger.debug("getService(String) - end - return value={}", returnObject);
		}
		return returnObject;
	}
	
}
