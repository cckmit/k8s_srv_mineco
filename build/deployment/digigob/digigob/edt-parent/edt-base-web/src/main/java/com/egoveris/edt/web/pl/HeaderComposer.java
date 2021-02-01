package com.egoveris.edt.web.pl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

public class HeaderComposer extends GenericForwardComposer {

  private static Logger logger = LoggerFactory.getLogger(HeaderComposer.class);

  /**
  * 
  */
  private static final long serialVersionUID = -7362863281173204847L;
  private AnnotateDataBinder binder;
  private String loggedUser;
  private String loggedUserName;
  private String urlEscritorioUnico;
  private Label labelServidor;
  private String hostname;
  private Label nombreServidor;
  private Toolbarbutton cambiarParticion;
  @Autowired
  private Image imagenLogo;
  private Session csession;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    comp.addEventListener(Events.ON_CHANGE, new HeaderComposerListener(this));
  }

  public void onCreate$header(Event event) {
    this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
    labelServidor.setValue("VERSIONINFO REMOVED");
    this.urlEscritorioUnico = (String) SpringUtil.getBean("urlEU");

    AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    StringBuilder cadenaLogo = new StringBuilder();
    cadenaLogo.append("/imagenes/");
    cadenaLogo.append(appProperty.getString("url.archivo.logo"));
    cadenaLogo.append("/Escudo_Gobierno.png");
    this.imagenLogo.setSrc(cadenaLogo.toString());
    nombreServidor.setValue(this.obtenerHostname());
    csession = Executions.getCurrent().getDesktop().getSession();
    if (csession != null && csession.getAttribute(ConstantesSesion.SESSION_USERNAME) != null) {
      recargarUserNameDeLaSession();

    } else {
      this.doLogout();
    }
    this.binder.loadComponent(cambiarParticion);
  }

  public void onClick$logout() {
    this.doLogout();
  }

  public void onClick$cambiarParticion() {
    Map<String, Object> params = new HashMap<>(1);
    Component panelUsuarioComponent = Path.getComponent("/panelUsuarioWindow/euTabs");
    params.put("panelComposer", Components.getComposer(panelUsuarioComponent));
    Window repaWin = (Window) Executions.createComponents("/seleccionDeReparticion.zul",
        this.self.getParent(), params);
    repaWin.doModal();
  }

  private void doLogout() {
    String urlLogOutCAS = "/logout";
    Executions.getCurrent().getSession().invalidate();
    Executions.sendRedirect(urlLogOutCAS);
  }

  private void recargarUserNameDeLaSession() {
    String a = (String) csession.getAttribute(ConstantesSesion.SESSION_USERNAME);
    String b = csession.getAttribute(ConstantesSesion.REPARTICION_USADA_LOGIN) != null
        ? ((ReparticionDTO) csession.getAttribute(ConstantesSesion.REPARTICION_USADA_LOGIN))
            .getCodigo()
        : null;
    StringBuilder sb = new StringBuilder();
    sb.append(a);
    if (null != b) {
      sb.append(" - ").append(b);
    }
    this.loggedUser = sb.toString();
    this.loggedUserName = (String) csession.getAttribute(ConstantesSesion.SESSION_USER_NOMBRE_APELLIDO);
  }

  private String obtenerHostname() {
    try {
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
    } catch (UnknownHostException e) {
      logger.error(e.getMessage(), e);
    }
    return this.hostname;
  }

  public void setLoggedUser(String loggedUser) {
    this.loggedUser = loggedUser;
  }

  public String getLoggedUser() {
    return loggedUser;
  }

  public void setLoggedUserName(String loggedUserName) {
    this.loggedUserName = loggedUserName;
  }

  public String getLoggedUserName() {
    return loggedUserName;
  }

  public void setUrlEscritorioUnico(String urlEscritorioUnico) {
    this.urlEscritorioUnico = urlEscritorioUnico;
  }

  public String getUrlEscritorioUnico() {
    return urlEscritorioUnico;
  }

  public void setLabelServidor(Label labelServidor) {
    this.labelServidor = labelServidor;
  }

  public Label getLabelServidor() {
    return labelServidor;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getHostname() {
    return hostname;
  }

  public void setNombreServidor(Label nombreServidor) {
    this.nombreServidor = nombreServidor;
  }

  public Label getNombreServidor() {
    return nombreServidor;
  }

  public void setImagenLogo(Image imagenLogo) {
    this.imagenLogo = imagenLogo;
  }

  public Image getImagenLogo() {
    return imagenLogo;
  }

  final class HeaderComposerListener implements EventListener {
    @SuppressWarnings("unused")
    private HeaderComposer composer;

    public HeaderComposerListener(HeaderComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_CHANGE)) {
        recargarUserNameDeLaSession();
      }
    }

  }

}
