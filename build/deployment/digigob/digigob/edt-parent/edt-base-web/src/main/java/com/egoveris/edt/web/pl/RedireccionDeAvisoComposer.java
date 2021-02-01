package com.egoveris.edt.web.pl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.TipoEstadoAlertaAviso;
import com.egoveris.edt.base.model.eu.AlertaAvisoDTO;
import com.egoveris.edt.base.service.IAlertaAvisoService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

public class RedireccionDeAvisoComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -2543343678845333790L;

  private AnnotateDataBinder binder;
  private Map<?, ?> parametros;
  private List<UsuarioReducido> listaTodosLosUsuarios;
  private List<UsuarioReducido> listaUsuariosSeleccionados;
  private UsuarioReducido asesorRevisorSeleccionado;
  private AlertaAvisoDTO alertaAvisoSeleccionada;
  private IUsuarioService usuarioService;
  private UsuarioReducido usuarioSeleccionado;
  private Bandbox bandBoxUsuarios;
  private IAlertaAvisoService alertaAvisoService;
  private Listbox usuarioListbox;
  private Listfooter totalUsuarios;

  @Override
  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component c) throws Exception {

    super.doAfterCompose(c);
    usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
    alertaAvisoService = (IAlertaAvisoService) SpringUtil.getBean("alertaAvisoService");
    parametros = Executions.getCurrent().getArg();
    listaTodosLosUsuarios = new ArrayList<UsuarioReducido>(usuarioService.obtenerUsuariosDeSolr());
    listaUsuariosSeleccionados = new ArrayList<UsuarioReducido>();
    if (!parametros.isEmpty()) {
      alertaAvisoSeleccionada = (AlertaAvisoDTO) parametros
          .get(ConstantesSesion.ALERTA_AVISO_SELECCIONADA);
      binder = new AnnotateDataBinder(c);
      binder.loadAll();
    }
  }

  private boolean validarCampos() throws InterruptedException, SecurityNegocioException {

    if ((usuarioSeleccionado == null)
        || !usuarioSeleccionado.getUsername().equals(bandBoxUsuarios.getValue())) {
      throw new WrongValueException(this.bandBoxUsuarios,
          Labels.getLabel("eu.avisosalertas.label.buscadorUsuario.usuarioInvalido"));
      
    }

    return true;
  }

  public void onClick$btn_guardar()
      throws InterruptedException, NegocioException, SecurityNegocioException {

      validarCampos();
      if (alertaAvisoSeleccionada.getEstado().equals(TipoEstadoAlertaAviso.LEIDO.getEstado())) {
        alertaAvisoSeleccionada.setEstado(TipoEstadoAlertaAviso.NO_LEIDO.getEstado());
      }

      alertaAvisoService.guardarAviso(alertaAvisoSeleccionada);
      Component comp = this.self.getParent();
      Events.sendEvent(Events.ON_OK, comp, null);
      Messagebox.show(Labels.getLabel("eu.avisosalertas.redireccion.ok"),
          Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
      Events.sendEvent(this.self.getParent(), new Event(Events.ON_USER));
      Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
      this.self.detach();
    
  }

  public void onChanging$bandBoxUsuarios(InputEvent e) {
    this.cargarUsuarios(e);
  }

  private void cargarUsuarios(InputEvent e) {
    String matchingText = e.getValue();
    if (matchingText.trim().length() > 2 && (!"*".equals(matchingText.trim()))) {
      listaUsuariosSeleccionados.clear();
      if (listaTodosLosUsuarios != null) {
        matchingText = matchingText.toUpperCase();
        for (UsuarioReducido usuarioReducido : listaTodosLosUsuarios) {
          if ((usuarioReducido != null) && (usuarioReducido.getUsername() != null)) {
            if ((usuarioReducido.toString().contains(matchingText))) {
              listaUsuariosSeleccionados.add(usuarioReducido);
            }
          }
        }
      }
    } else if ("*".equals(matchingText.trim())) {
      listaUsuariosSeleccionados.addAll(listaTodosLosUsuarios);
    } else if (matchingText.trim().length() < 2) {
      listaUsuariosSeleccionados.clear();
      usuarioSeleccionado = null;
    }
    usuarioListbox.setActivePage(0);
    totalUsuarios.setLabel("Total de Usuarios" + ": " + listaUsuariosSeleccionados.size());
    this.binder.loadComponent(usuarioListbox);
  }

  public void onSelect$usuarioListbox() {
    bandBoxUsuarios.setText(usuarioSeleccionado.getUsername());
    alertaAvisoSeleccionada.setUserName(bandBoxUsuarios.getText());
    bandBoxUsuarios.close();
  }

  public List<UsuarioReducido> getListaUsuariosSeleccionados() {
    return listaUsuariosSeleccionados;
  }

  public void setListaUsuariosSeleccionados(List<UsuarioReducido> listaUsuariosSeleccionados) {
    this.listaUsuariosSeleccionados = listaUsuariosSeleccionados;
  }

  public UsuarioReducido getAsesorRevisorSeleccionado() {
    return asesorRevisorSeleccionado;
  }

  public void setAsesorRevisorSeleccionado(UsuarioReducido asesorRevisorSeleccionado) {
    this.asesorRevisorSeleccionado = asesorRevisorSeleccionado;
  }

  public AlertaAvisoDTO getAlertaAvisoSeleccionada() {
    return alertaAvisoSeleccionada;
  }

  public void setAlertaAvisoSeleccionada(AlertaAvisoDTO alertaAvisoSeleccionada) {
    this.alertaAvisoSeleccionada = alertaAvisoSeleccionada;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public UsuarioReducido getUsuarioSeleccionado() {
    return usuarioSeleccionado;
  }

  public void setUsuarioSeleccionado(UsuarioReducido usuarioSeleccionado) {
    this.usuarioSeleccionado = usuarioSeleccionado;
  }

}
