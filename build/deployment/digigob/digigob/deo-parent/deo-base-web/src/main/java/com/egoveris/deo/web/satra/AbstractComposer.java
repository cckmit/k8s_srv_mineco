package com.egoveris.deo.web.satra;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public abstract class AbstractComposer extends GenericForwardComposer {
	
	private static final long serialVersionUID = 6003981943181046935L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractComposer.class);
	
	protected HttpSession getNativeHttpSession() {
		Session zkSession = Sessions.getCurrent();
		return (HttpSession) zkSession
				.getNativeSession();
	}

	public Logger getLogger() {
		return logger;
	}
}
