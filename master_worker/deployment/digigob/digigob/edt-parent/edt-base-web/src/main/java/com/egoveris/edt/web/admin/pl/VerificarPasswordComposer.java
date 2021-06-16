package com.egoveris.edt.web.admin.pl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapAccessor;

public class VerificarPasswordComposer extends GenericForwardComposer {

	
  private static final Logger logger = LoggerFactory.getLogger(VerificarPasswordComposer.class);

  private static final long serialVersionUID = -2795457502593411568L;
  private String sessionUsername;
  private IUsuarioService usuarioService;
  private Textbox txbx_password;
  private ILdapAccessor ldapAccessor;
  private String ldapEntorno;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
    ldapAccessor = (ILdapAccessor) SpringUtil.getBean("ldapAccesor");
    ldapEntorno = (String) SpringUtil.getBean("ldapEntorno");
    sessionUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesSesion.SESSION_USERNAME);
  }

  public void onOK$txbx_password() throws InterruptedException {
    onClick$btn_ok();
  }

  public void onClick$btn_ok() throws InterruptedException {
    try {
      boolean passwordActualOk = false;
      Map<String, Boolean> resultado = new HashMap<String, Boolean>();

      if (txbx_password.getValue().isEmpty()) {
        throw new WrongValueException(this.txbx_password,
            Labels.getLabel("eu.adminSade.validacion.passActual"));
      } else {
        if (!"".equals(sessionUsername)) {
          if ("SADE".equalsIgnoreCase(ldapEntorno)) {
            Usuario u = usuarioService.obtenerUsuario(sessionUsername);
//            passwordActualOk = ldapAccessor.authenticate(u.getCuit(), txbx_password.getValue());
          } else {
            passwordActualOk = usuarioService.validarPasswordUsuario(sessionUsername,
                txbx_password.getValue());
          }
        }
      }

      resultado.put("isOk", passwordActualOk);
      Events.sendEvent(new Event(Events.ON_OK, this.self.getParent(), resultado));
      Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
    } catch (SecurityNegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }

  }
}
