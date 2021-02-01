package com.egoveris.ffdd.web.adm;

import org.terasoluna.plus.core.properties.util.SpringProperties;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

public class HeaderComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -2303462081637986502L;
	private AnnotateDataBinder binder;
	private String loggedUser;
	private Label name;
	private Label version;
	private Image logo;
	@WireVariable("logo")
	private String logoName;	
	
	public void onCreate$header(Event event) {
		StringBuffer sb = new StringBuffer();
		sb.append("- version ");
		sb.append(SpringProperties.getProperty("application.version"));
		sb.append(" - build.");
		sb.append(SpringProperties.getProperty("application.build.number"));
		sb.append(" -");
		this.version.setValue(sb.toString());
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute(
				"binder", true);
		this.loggedUser = Executions.getCurrent().getRemoteUser();
		this.binder.loadComponent(name);
		
		StringBuilder cadenaLogo = new StringBuilder();
		cadenaLogo.append("/imagenes/");
		cadenaLogo.append(logoName);
		cadenaLogo.append("/logo2.50ppx.png");
		logo.setSrc(cadenaLogo.toString());
	}
	
	public void onClick$logout() {
			String urlLogOutCAS = "/j_spring_security_logout";
			Executions.getCurrent().getSession().invalidate();
			Executions.sendRedirect(urlLogOutCAS);
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getLoggedUser() {
		return loggedUser;
	}
}
