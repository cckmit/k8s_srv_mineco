package com.egoveris.edt.web.admin.pl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class DatosReparticionComposer extends GenericForwardComposer {

  private static Logger logger = LoggerFactory.getLogger(DatosReparticionComposer.class);

  private static final long serialVersionUID = -5362549948387867821L;
  private ILdapService iLdapService;
  private IReparticionEDTService reparticionService;

  private ReparticionDTO reparticion;

  @Autowired
  protected AnnotateDataBinder binder;
  private Textbox txbx_codigo;
  private Textbox txbx_calle;
  private Textbox txbx_telefono;
  private Textbox txbx_numCalle;
  private Textbox txbx_descripcion;
  private Textbox txbx_piso;
  private Textbox txbx_fax;
  private Textbox txbx_oficina;
  private Textbox txbx_email;
  private Datebox dbx_vigenciaDesde;
  private Datebox dbx_vigenciaHasta;
  private Hbox hbox_visu;

  private Hbox hbox_botones;
  private Vbox vbox_headerVer;
  private Vbox vbox_headerModificar;
  private Vbox vbox_modificar;

  private Map<?, ?> parametros;
  private Session session = null;
  private Boolean passwordOk = false;

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.iLdapService = (ILdapService) SpringUtil.getBean("ldapServiceImpl");
    reparticionService = (IReparticionEDTService) SpringUtil.getBean("reparticionEDTService");

    comp.addEventListener(Events.ON_NOTIFY, new DatosReparticionOnNotifyWindowListener(this));
    comp.addEventListener(Events.ON_OK, new DatosReparticionOnNotifyWindowListener(this));
    session = Executions.getCurrent().getDesktop().getSession();

    parametros = Executions.getCurrent().getArg();
    if (!parametros.isEmpty()) {
      if (parametros.get(ConstantesSesion.KEY_REPARTICION) != null) {
        reparticion = (ReparticionDTO) parametros.get(ConstantesSesion.KEY_REPARTICION);
        session.setAttribute(ConstantesSesion.KEY_REPARTICION_MODIFICAR,
            reparticion.getCodigo());
        // Visualizacion desde Administrador
        if (parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
            && (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR) == false) {
          vbox_headerVer.setVisible(true);
          vbox_headerModificar.setVisible(false);
          vbox_modificar.setVisible(false);
          habilitarCampos(false);

          // Modificar desde Administrador
        } else if (parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
            && (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR) == true) {
          habilitarCampos(true);
          vbox_headerVer.setVisible(false);
          vbox_headerModificar.setVisible(true);
          vbox_modificar.setVisible(false);

        }
      }
    } else {
      obtenerDatosUsuarioLogueado();
    }

    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
  }

  private UsuarioBaseDTO obtenerDatosUsuarioLogueado() throws InterruptedException {
    try {
      UsuarioBaseDTO usuarioLoggeado = new UsuarioBaseDTO();
      usuarioLoggeado.setUid((String) Executions.getCurrent().getDesktop().getSession()
          .getAttribute(ConstantesSesion.SESSION_USERNAME));
      usuarioLoggeado = this.iLdapService.obtenerUsuarioPorUid(usuarioLoggeado.getUid());
      session.setAttribute("usuarioModificar", usuarioLoggeado.getUid());

      return usuarioLoggeado;
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
    return null;
  }

  public void onClick$btn_guardar() throws InterruptedException {
    if (validarCampos()) {
      verificarPasswordPopup();
    }
  }

  public void guardar(Boolean paswordOk) throws InterruptedException {

    if (passwordOk) {
      try {
        //
        habilitarCampos(false);

        if (parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
            && (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR) == true) {
          if (this.self == null) {
            throw new IllegalAccessError(
                Labels.getLabel("eu.datosRepComposer.WrongValueException.componenteNoPresente"));
          }
        }
        reparticionService.modificarReparticion(reparticion);
        Messagebox.show(Labels.getLabel("eu.adminSade.reparticion.mensajes.modificacionExitosa"),
            Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
            Messagebox.INFORMATION);

      } catch (NegocioException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
            Messagebox.OK, Messagebox.ERROR);
      }
    } else {
      Messagebox.show(Labels.getLabel("eu.adminSade.validacion.passActual"),
          Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
    }
  }

  public void onClick$btn_salir() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$btn_cancelar() {
    habilitarCampos(false);
    if (parametros.get(ConstantesSesion.KEY_MODIFICAR) != null
        && (Boolean) parametros.get(ConstantesSesion.KEY_MODIFICAR) == true) {
      if (this.self == null) {
        throw new IllegalAccessError(
            Labels.getLabel("eu.datosRepComposer.WrongValueException.componenteNoPresente"));
      }
      Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
      Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
    }
  }

  public void onClick$tbbtn_ModificarPerfil() {
    habilitarCampos(true);
  }

  private void habilitarCampos(boolean habilitado) {
    txbx_codigo.setReadonly(!habilitado);
    txbx_numCalle.setReadonly(!habilitado);
    txbx_telefono.setReadonly(!habilitado);
    txbx_descripcion.setReadonly(!habilitado);
    txbx_piso.setReadonly(!habilitado);
    txbx_fax.setReadonly(!habilitado);
    txbx_oficina.setReadonly(!habilitado);
    txbx_email.setReadonly(!habilitado);
    dbx_vigenciaDesde.setDisabled(!habilitado);
    dbx_vigenciaHasta.setDisabled(!habilitado);
    hbox_botones.setVisible(habilitado);
    hbox_visu.setVisible(!habilitado);
  }

  public boolean verificarPasswordPopup() throws InterruptedException {
    // LLama a la verificacion del Password y esta verificacion llama al
    // evento guardar
		Utilitarios.closePopUps("win_verificarPassword");
    Window win = (Window) Executions.createComponents("/administrator/verificarPassword.zul",
        this.self, parametros);
    win.setMode("modal");
    win.setClosable(true);
    win.setTitle(Labels.getLabel("eu.datosPersonalesCompose.winTitle.verificarPass"));
    win.setWidth("350px");
    win.setHeight("100px");
    win.setPosition("center");
    win.setVisible(true);
    win.setBorder("normal");
    win.doModal();

    return false;
  }

  public boolean validarCampos() {

    // TODO: AHA revisar bien la validacion
    if (txbx_codigo.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_codigo,
          Labels.getLabel("eu.adminSade.validacion.codigo"));
    }
    if (txbx_calle.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_calle,
          Labels.getLabel("eu.adminSade.validacion.calle"));
    }
    if (txbx_numCalle.getValue().isEmpty()) {
      throw new WrongValueException(this.txbx_numCalle,
          Labels.getLabel("eu.adminSade.validacion.numCalle"));
    }

    return true;
  }

  /**
   * Refresca los datos al viajar entre las pestañas
   *
   */
  final class DatosReparticionOnNotifyWindowListener implements EventListener {
    private DatosReparticionComposer composer;

    public DatosReparticionOnNotifyWindowListener(DatosReparticionComposer comp) {
      this.composer = comp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          this.composer.obtenerDatosUsuarioLogueado();
        }
      }
      if (event.getName().equals(Events.ON_OK)) {
        if (event.getData() != null) {
          Map<String, Boolean> map = (Map<String, Boolean>) event.getData();
          passwordOk = (Boolean) map.get(ConstantesSesion.KEY_IS_OK);
          this.composer.guardar(passwordOk);
        }
      }
    }
  }

  public ReparticionDTO getReparticion() {
    return reparticion;
  }

  public void setReparticion(ReparticionDTO reparticion) {
    this.reparticion = reparticion;
  }

}
