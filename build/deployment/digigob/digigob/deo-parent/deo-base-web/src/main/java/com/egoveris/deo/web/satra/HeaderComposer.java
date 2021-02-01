package com.egoveris.deo.web.satra;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.util.Constantes;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.properties.util.SpringProperties;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

public class HeaderComposer extends GenericForwardComposer {
  /**
  * 
  */
  private static final long serialVersionUID = -5076767065295892529L;

  private static final Logger logger = LoggerFactory.getLogger(HeaderComposer.class);
  /**
  * 
  */
  private AnnotateDataBinder binder;
  private String loggedUser;
  private String urlEscritorioUnico;
  private Label name;
  private Label labelServidor;
  private String hostname;
  private Label nombreServidor;
  private Label userName;
  private Image imagenLogo;

  public void onCreate$header(Event event) {
    AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    StringBuilder cadenaLogo = new StringBuilder();
    cadenaLogo.append("/imagenes/");
    cadenaLogo.append(appProperty.getString("app.name"));
    cadenaLogo.append("/logoPrincipal.png");
    imagenLogo.setSrc(cadenaLogo.toString());
    labelServidor.setValue(SpringProperties.getProperty("application.version"));
    nombreServidor.setValue(this.obtenerHostname());
    this.urlEscritorioUnico = (String) SpringUtil.getBean("urlEU");
    this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
    Session csession = Executions.getCurrent().getDesktop().getSession();
    if (csession != null && csession.getAttribute(Constantes.SESSION_USERNAME) != null) {
      String a = (String) csession.getAttribute(Constantes.SESSION_USER_NOMBRE_APELLIDO);
      String b = (String) csession.getAttribute(Constantes.SESSION_USER_REPARTICION);

      if (a != null && b != null) {
        this.userName.setValue(a + "-" + b);
      }
      this.loggedUser = (String) csession.getAttribute(Constantes.SESSION_USERNAME);

    } else {
      Utilitarios.doLogout();
    }
    this.binder.loadComponent(name);
  }

  public void onClick$logout() {
    Utilitarios.doLogout();
  }

  public void onClick$irEscritorioUnico() {
    Executions.sendRedirect(urlEscritorioUnico);
  }

  public void setLoggedUser(String loggedUser) {
    this.loggedUser = loggedUser;
  }

  public String getLoggedUser() {
    return loggedUser;
  }

  private String obtenerHostname() {
    try {
      InetAddress inetAddr = InetAddress.getLocalHost();
      byte[] addr = inetAddr.getAddress();

      StringBuilder ipAddr = new StringBuilder();
      for (int i = 0; i < addr.length; ++i) {
        if (i > 0) {
          ipAddr.append(".");
        }
        ipAddr.append(addr[i] & 0xFF);
      }
      this.hostname = inetAddr.getHostName();
    } catch (UnknownHostException e) {
      logger.error("Error al obtener nombre de host " + e.getMessage(), e);
    }
    return this.hostname;
  }

}
