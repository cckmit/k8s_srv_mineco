/**
 * 
 */
package com.egoveris.deo.web.satra;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.service.IValidaUsuarioGedoService;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zkplus.spring.SpringUtil;

/**
 * @author pfolgar
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InitPanelUsuario implements Initiator {

  private static Logger logger = LoggerFactory.getLogger(InitPanelUsuario.class);
  
  @WireVariable("validaUsuarioGedoServiceImpl")
  private IValidaUsuarioGedoService validaUsuarioGedoService;

  public void doAfterCompose(Page page) throws Exception {

  }

  public boolean doCatch(Throwable ex) throws Exception {
    return false;
  }

  public void doFinally() throws Exception {

  }

  @SuppressWarnings("rawtypes")
  public void doInit(Page page, Map args) throws Exception {
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null)); 
    AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    commonInit(page, appProperty);
    cargarInformacionUsuario();
    Executions.getCurrent().getDesktop().getSession().setAttribute("CCOO", null);
  }

  protected void commonInit(Page page, AppProperty appProperty) {

    StringBuffer cadenaLogo = new StringBuffer();
    cadenaLogo.append("/imagenes/");
    cadenaLogo.append(appProperty.getString("app.name"));
    cadenaLogo.append("/favicon.ico");
    page.setAttribute("favicon", cadenaLogo.toString());
    Session csession = Executions.getCurrent().getDesktop().getSession();
    String userExistente = (String) csession.getAttribute(Constantes.SESSION_USERNAME);
    if (StringUtils.isEmpty(userExistente)) {
      this.doLogin();
    }
  }

  /**
   * Cuestiones para el login que luego se pasaran al CAS... o algo
   */
  private void doLogin() {

//    this.setValidaUsuarioGedoService(
//        (IValidaUsuarioGedoService) SpringUtil.getBean("validacionUsuarioGedo"));
    String user = Executions.getCurrent().getRemoteUser();
    // Valido si el usuario puede ingresar en GEDO
    // Esto se hace aca pq como se implemento el CAS y se saco el login de
    // la aplicacion
    // esa validacion ahora se hace a traves de este servicio
    if (!StringUtils.isEmpty(user)) {

      try {
        boolean usuarioValido = this.validaUsuarioGedoService.validaUsuarioGedo(user);
        if (!usuarioValido) {
          usuarioInvalidoRedirect();
        } else {
          // cacheUser
          Usuario usuario = Utilitarios.obtenerUsuarioActual();
          Session csession = Executions.getCurrent().getDesktop().getSession();
          csession.setAttribute(Constantes.SESSION_USERNAME, user);
          csession.setAttribute(Constantes.SESSION_USER_NOMBRE_APELLIDO,
              usuario.getNombreApellido());
          Executions.getCurrent().getDesktop().getSession().setAttribute(
              Constantes.SESSION_USER_REPARTICION, usuario.getCodigoReparticion().trim());
        }

      } catch (Exception e) {
        logger.error("Error al validar usuario '" + user + "' en DEO - Redirigiendo al logout. "
            + e.getMessage(), e);
        Utilitarios.doLogout();
      }

    } else {
      logger.error("Remote user nulo! Redirigiendo al logout...");
      Utilitarios.doLogout();
    }

  }

  public void usuarioInvalidoRedirect() {
    Executions.sendRedirect("/usuarioInvalido.zul");
  }

  public void setValidaUsuarioGedoService(IValidaUsuarioGedoService validaUsuarioGedoService) {
    this.validaUsuarioGedoService = validaUsuarioGedoService;
  }

  private void cargarInformacionUsuario() {

    Usuario user = null;
    try {
      user = Utilitarios.obtenerUsuarioActual();

      Session sesionActual = Executions.getCurrent().getDesktop().getSession();
      sesionActual.setAttribute("userName", user.getUsername());
    } catch (Exception e) {
      logger.error("Error al cargar informacion de usuario " + e.getMessage(), e);
      Utilitarios.doLogout();
    } finally {
      if (user == null) {
        Utilitarios.doLogout();
      }
    }
  }

}
