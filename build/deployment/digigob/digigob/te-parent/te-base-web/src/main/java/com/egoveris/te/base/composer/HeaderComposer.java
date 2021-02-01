package com.egoveris.te.base.composer;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.Utils;
import com.egoveris.te.base.util.VersionInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HeaderComposer extends EEGenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1818698949918766023L;
	private AnnotateDataBinder binder;
	private String loggedUser;
	@Autowired	
	private Label name;
	@Autowired
	private Label nombreServidor;
	private String urlEscritorioUnico;
	private String hostname;
	
	@WireVariable("DBAppPropertyImpl")
	private AppProperty appProperty;
	
	@Autowired
	private Label titulo;
	
	@Autowired
	private Image logo;
	
	private Label labelServidor;
	
	private Label userNameComplete;
	
	public void onCreate$header(Event event) {
		
		 
		
		this.nombreServidor.setValue(this.obtenerHostname()) ;

		this.urlEscritorioUnico = appProperty.getString("eu.url");
		
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
		
		Session csession = Executions.getCurrent().getDesktop().getSession();
		
		labelServidor.setValue(VersionInfo.getInstance().getFullVersion());
		
		StringBuffer cadenaLogo = new StringBuffer();
		
		cadenaLogo.append("/imagenes/");
		cadenaLogo.append(appProperty.getString("nombre.entorno"));
		cadenaLogo.append("/logoPrincipal.png");
		
		this.logo.setSrc(cadenaLogo.toString());
				
		if (csession != null && csession.getAttribute(ConstantesWeb.SESSION_USERNAME) != null) {
			String a = (String) csession.getAttribute(ConstantesWeb.SESSION_USER_NOMBRE_APELLIDO);
			String b = (String) csession.getAttribute(ConstantesWeb.SESSION_USER_REPARTICION);
			
			
			this.loggedUser = (String) csession.getAttribute(ConstantesWeb.SESSION_USERNAME);
			
			if (a != null && b != null) {
				this.userNameComplete.setValue(a+"-"+b);
			}
			

		} else {
			Utils.doLogout();
		}
		this.binder.loadComponent(name);
	}
	
	public void onClick$logout() {
		Utils.doLogout();
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void onClick$irEscritorioUnico() {
		Executions.sendRedirect(urlEscritorioUnico);
	}	
	
	 private String obtenerHostname()
	  {
	    try
	    {
	      InetAddress inetAddr = InetAddress.getLocalHost();
	      byte[] addr = inetAddr.getAddress();

	      String ipAddr = "";
	      for (int i = 0; i < addr.length; ++i) {
	        if (i > 0) {
	          ipAddr = ipAddr + ".";
	        }
	        ipAddr = ipAddr + (addr[i] & 0xFF);
	      }
	      this.hostname = inetAddr.getHostName();
	    }
	    catch (UnknownHostException e) {
	     
	    }
	    return this.hostname;
	  }

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}   

	
}
