package com.egoveris.te.base.composer;

import com.egoveris.shared.encryption.URLEncryptor;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.service.LdapAccessor;
import com.egoveris.te.base.service.SupervisadosService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PanelUsuarioComposer extends GenericForwardComposer {
  /**
  * 
  */
  private static final long serialVersionUID = 6700874480566404964L;

  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;
  @WireVariable("LdapAccessorImpl")
  private LdapAccessor ldapAccessor;
  
  @Autowired
  Tabbox eeTabs;
  @Autowired
  Tab abm;
  @Autowired
  Tab buzondetareas;
  @Autowired
  Tab buzondetareasgrupales;
  @Autowired
  Tab supervisados;
  @Autowired
  Tab consultas;
  @Autowired
  Tab desarchivar;
  @Autowired
  Tab administrar;
  @Autowired
  Tab tareasEnParalelo;
  Tab buzonTad;
  @Autowired
  Tab herramientas;
  @Autowired
  Image logo;

  private boolean administrador;
  private boolean administradorPases;
  private boolean fusionador;
  private boolean desarchivador;
  private boolean asignador;
  private boolean veHerramientas;

  private SupervisadosService supervisadosService;

  private Map<String, Object> pathMap;

  private static final Logger logger = LoggerFactory.getLogger(PanelUsuarioComposer.class);

  public void onSelect$eeTabs() {
    if (eeTabs.getSelectedPanel().getFirstChild() != null) {
      Window currentWindow = (Window) eeTabs.getSelectedPanel().getFirstChild().getFirstChild();
      Event event = null;
      // Validación para identificar en que casos refrescar los tabs
      if (currentWindow.getId().equalsIgnoreCase("consultaExpedientesWindow")) {
        event = new Event(Events.ON_NOTIFY, currentWindow, true);
      } else {
        event = new Event(Events.ON_NOTIFY, currentWindow);
      }
      Events.sendEvent(event);
    }
  }

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
 

    String loggedUsername = (String) Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    Boolean administrador = (Boolean) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_ADMIN_CENTRAL);
     
    Usuario usuario = this.usuariosSADEService.obtenerUsuarioActual();

    if (administrador != null) {
      this.abm.setVisible(administrador);
    }

    administrador = usuariosSADEService.usuarioTieneRol(usuario.getUsername(),
        ConstantesWeb.ROL_ADMIN_CENTRAL);
    setFusionador(
        usuariosSADEService.usuarioTieneRol(usuario.getUsername(), ConstantesWeb.ROL_FUSIONADOR));
    setDesarchivador(
        usuariosSADEService.usuarioTieneRol(usuario.getUsername(), ConstantesWeb.ROL_DESARCHIVADOR));
    setAdministradorPases(usuariosSADEService.usuarioTieneRol(usuario.getUsername(),
        ConstantesWeb.ROL_ADMINISTRADOR_PASES));
    setAsignador(
        usuariosSADEService.usuarioTieneRol(usuario.getUsername(), ConstantesWeb.ROL_ASIGNADOR));
    setDesarchivador(
        usuariosSADEService.usuarioTieneRol(usuario.getUsername(), ConstantesWeb.ROL_DESARCHIVADOR));

    this.cargarPermisos(administrador, fusionador, desarchivador);

    veHerramientas = usuariosSADEService.usuarioTieneRol(usuario.getUsername(),
        ConstantesWeb.ROL_HERRAMIENTAS);

    // Verifico permisos de LDAP para mostrar las diferentes solapas.
    this.desarchivar.setVisible(this.desarchivador);
    this.administrar.setVisible(this.administradorPases);

    this.herramientas.setVisible(veHerramientas); // solamente para
                                                  // administradores

    if (usuariosSADEService.licenciaActiva(loggedUsername)) {
      this.buzondetareas.setDisabled(true);
      this.buzondetareasgrupales.setDisabled(true);
      this.supervisados.setDisabled(true);
      this.buzonTad.setDisabled(true);
      this.abm.setDisabled(true);
      this.desarchivar.setDisabled(true);
      this.tareasEnParalelo.setDisabled(true);

      this.consultas.setSelected(true);
    }
  }

  @SuppressWarnings("unchecked")
  public void onCreate$panelUsuarioWindow() throws InterruptedException {

    String loggedUsername = (String) Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    // Si hay un usuario apoderado seteado, "apreto" el tab de consulta:
    if (usuariosSADEService.licenciaActiva(loggedUsername)) {
      this.consultas.setSelected(true);
      this.eeTabs.setSelectedTab(this.consultas);
      Events.sendEvent(new Event("onClick", this.eeTabs.getSelectedTab()));
    } else {
      // Si no es null o empty significa que se recibio como parametro el
      // nombre de un usuario subordinado para trabajar con su buzon
      String encryptedUsername = (String) Executions.getCurrent().getParameter("idUS");
      String codigoExp = (String) Executions.getCurrent().getParameter("vExp");

      String keyWord = null;
      if (StringUtils.isNotEmpty(encryptedUsername)) {
        if (!encryptedUsername.equals("buzon") && !encryptedUsername.equals("activ")) {
          URLEncryptor codec = URLEncryptor.getInstance();
          try {
            String usernameSupervisado = codec.decrypt(encryptedUsername);
            String username = (String) Executions.getCurrent().getSession()
                .getAttribute(ConstantesWeb.SESSION_USERNAME);
            Usuario supervisado = this.supervisadosService.getSupervisado(username,
                usernameSupervisado);
            if (supervisado != null) {
              if (supervisado.getNombreApellido() == null) {
                supervisado.setNombreApellido("");
              }
              Executions.getCurrent().getDesktop().setAttribute("supervisado", supervisado);
              Window supervisadosInbox = (Window) Executions.createComponents(
                  "/supervisados/supervisadosInbox.zul", this.self.getParent(),
                  new HashMap<String, Object>());
              this.self.getParent().appendChild(supervisadosInbox);
              supervisadosInbox.setPosition("center");
              supervisadosInbox.doModal();
            } else {
              String errorLabel = Labels.getLabel("ee.error.errorObteniendoDatosSupervisado",
                  new String[] { usernameSupervisado });
              this.showError(errorLabel);
            }
          } catch (SecurityException se) {
            logger.error("Error decoding URL parameter", se);
            this.showError(Labels.getLabel("ee.error.parametroIncorrecto"));
          }
        } else {
          if (encryptedUsername.equals("buzon")) {

            buzondetareasgrupales.setSelected(true);
            Events.sendEvent(new Event("onClick", buzondetareasgrupales));
          } else if (encryptedUsername.equals("activ")) {
            buzonTad.setSelected(true);
            Events.sendEvent(new Event("onClick", buzonTad));
          } else {
            this.showError(Labels.getLabel("ee.error.parametroIncorrecto"));
          }
        }
      } else if (StringUtils.isNotEmpty(codigoExp)) {

        Executions.getCurrent().getDesktop().setAttribute("codigoExpedienteElectronico",
            codigoExp);
        Window codExp = (Window) Executions.createComponents(
            "/expediente/detalleExpedienteElectronico.zul", this.self.getParent(),
            new HashMap<String, Object>());
        this.self.getParent().appendChild(codExp);
        codExp.setPosition("center");
        codExp.setWidth("75%");
        codExp.doModal();

      } else {

        this.pathMap = (Map<String, Object>) Executions.getCurrent().getSession()
            .getAttribute(ConstantesWeb.PATHMAP);
        if (!CollectionUtils.isEmpty(this.pathMap)) {
          if ((Boolean) this.pathMap.get(ConstantesWeb.REDIRECT) != null
              && (Boolean) this.pathMap.get(ConstantesWeb.REDIRECT)) {
            pathMap.put(ConstantesWeb.REDIRECT, false);
            Executions.sendRedirect("/");
          }
          keyWord = (String) this.pathMap.get(ConstantesWeb.KEYWORD);
          if (StringUtils.isNotEmpty(keyWord) && keyWord.equals(ConstantesWeb.KEYWORD_EXPEDIENTES)) {
            Events.sendEvent(this.consultas, new Event(Events.ON_CLICK));
            this.eeTabs.setSelectedTab(this.consultas);
          }
        }

      }
    }
  }

  private void showError(String message) throws InterruptedException {
    Messagebox.show(message, Labels.getLabel("gedo.general.error"), Messagebox.OK,
        Messagebox.ERROR, new EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            return;
          }
        });
  }

  public void setAdministrador(boolean administrador) {
    this.administrador = administrador;
  }

  public boolean isAdministrador() {
    return administrador;
  }

  public void setAdministradorPases(boolean administradorPases) {
    this.administradorPases = administradorPases;
  }

  public boolean isAdministradorPases() {
    return administradorPases;
  }

  public void setFusionador(boolean fusionador) {
    this.fusionador = fusionador;
  }

  public boolean isFusionador() {
    return fusionador;
  }

  public void setDesarchivador(boolean desarchivador) {
    this.desarchivador = desarchivador;
  }

  public boolean isDesarchivador() {
    return desarchivador;
  }

  public void setAsignador(boolean asignador) {
    this.asignador = asignador;
  }

  public boolean isAsignador() {
    return asignador;
  }

  private void cargarPermisos(boolean administrador, boolean fusionador, boolean desarchivador) {
    Executions.getCurrent().getDesktop().getSession()
        .setAttribute(ConstantesWeb.SESSION_ADMIN_CENTRAL, administrador);

    Executions.getCurrent().getDesktop().getSession()
        .setAttribute(ConstantesWeb.SESSION_DESARCHIVADOR, desarchivador);

    Executions.getCurrent().getDesktop().getSession().setAttribute(ConstantesWeb.SESSION_FUSIONADOR,
        fusionador);
  }

}
