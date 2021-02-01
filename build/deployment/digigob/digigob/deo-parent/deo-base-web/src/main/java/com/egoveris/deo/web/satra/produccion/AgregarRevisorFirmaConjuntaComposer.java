package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.ValidarApoderamientoComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AgregarRevisorFirmaConjuntaComposer extends ValidarApoderamientoComposer {

  private static final long serialVersionUID = 1L;

  private Window agregarRevisorFirmaConjuntaWindow;

  private Combobox usuarioRevisorCombo;
  private Button finAgregarRevisorFirmaConjunta;

  private AnnotateDataBinder agregarRevisorFirmaConjuntaBinder;

  @WireVariable("usuarioServiceImpl")
  private IUsuarioService usuarioService;

  private Usuario usuarioRevisorSeleccionado;
  private Usuario usuarioFirmante;

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    comp.addEventListener(Events.ON_USER, new RevisorFirmaConjunta(this));

    Map<String, Object> datos = (HashMap<String, Object>) Executions.getCurrent().getArg();
    usuarioFirmante = (Usuario) (datos.get("usuarioFirmante"));

    usuarioRevisorCombo.setModel(
        ListModels.toListSubModel(new ListModelList(this.getUsuarioService().obtenerUsuarios()),
            new UsuariosComparator(), 30));

    this.agregarRevisorFirmaConjuntaBinder = new AnnotateDataBinder(comp);
    this.agregarRevisorFirmaConjuntaBinder.loadAll();
  }

  /**
   * Validaciones de adoderamiento y usuarios que pertenecen a la misma
   * repartición.
   * 
   * @throws InterruptedException
   */
  public void validarUsuarios(Usuario usuarioAValidar) throws InterruptedException {
    super.validarApoderamiento(usuarioAValidar, null);
  }

  /**
   * Cerrar y volver a la ventana padre.
   * 
   * @throws SecurityNegocioException
   */
  public void onFinalizarAgregacion() throws SecurityNegocioException {
    if (this.usuarioRevisorCombo.getSelectedItem() == null) {
      this.usuarioRevisorCombo.setValue(null);
      throw new WrongValueException(this.usuarioRevisorCombo,
          Labels.getLabel("gedo.error.faltaUsuarioRevisor"));
    }

    Usuario usuarioReducido = (Usuario) this.usuarioRevisorCombo.getSelectedItem().getValue();
    usuarioRevisorSeleccionado = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());

    Map<String, Object> datos = new HashMap<>();
    datos.put("funcion", "validarApoderamiento");
    datos.put("datos", usuarioRevisorSeleccionado);

    enviarBloqueoPantalla(datos);
  }

  public void onCancelarAgregacion() {
    Map<String, Object> datos = new HashMap<>();
    datos.put("origen", Constantes.EVENTO_USUARIOS_FIRMANTES);
    datos.put("usuarioFirmante", this.usuarioFirmante);

    this.closeAndNotifyAssociatedWindow(datos);
  }

  private Usuario armarDatosUsuarioBeanConUsuarioRevisor(Usuario usuarioRevisor) {
    Usuario nuevoUsuarioFirmante = new Usuario();

    nuevoUsuarioFirmante.setAceptacionTYC(usuarioFirmante.getAceptacionTYC());
    nuevoUsuarioFirmante.setNombreApellido(usuarioFirmante.getNombreApellido());
    nuevoUsuarioFirmante.setCargo(usuarioFirmante.getCargo());
    nuevoUsuarioFirmante.setExternalizarFirmaGEDO(usuarioFirmante.getExternalizarFirmaGEDO());
    nuevoUsuarioFirmante.setCuit(usuarioFirmante.getCuit());
    nuevoUsuarioFirmante.setEmail(usuarioFirmante.getEmail());
    nuevoUsuarioFirmante.setCodigoReparticion(usuarioFirmante.getCodigoReparticion());
    nuevoUsuarioFirmante.setUsername(usuarioFirmante.getUsername());
    nuevoUsuarioFirmante.setCargo(usuarioFirmante.getCargo());
    nuevoUsuarioFirmante.setUsuarioRevisor(usuarioRevisor.getUsername());

    return nuevoUsuarioFirmante;
  }

  public Combobox getUsuarioRevisorCombo() {
    return usuarioRevisorCombo;
  }

  public void setUsuarioRevisorCombo(Combobox usuarioRevisorCombo) {
    this.usuarioRevisorCombo = usuarioRevisorCombo;
  }

  public Window getAgregarRevisorFirmaConjuntaWindow() {
    return agregarRevisorFirmaConjuntaWindow;
  }

  public void setAgregarRevisorFirmaConjuntaWindow(Window agregarRevisorFirmaConjuntaWindow) {
    this.agregarRevisorFirmaConjuntaWindow = agregarRevisorFirmaConjuntaWindow;
  }

  public Button getFinAgregarRevisorFirmaConjunta() {
    return finAgregarRevisorFirmaConjunta;
  }

  public void setFinAgregarRevisorFirmaConjunta(Button finAgregarRevisorFirmaConjunta) {
    this.finAgregarRevisorFirmaConjunta = finAgregarRevisorFirmaConjunta;
  }

  public AnnotateDataBinder getAgregarRevisorFirmaConjuntaBinder() {
    return agregarRevisorFirmaConjuntaBinder;
  }

  public void setAgregarRevisorFirmaConjuntaBinder(
      AnnotateDataBinder agregarRevisorFirmaConjuntaBinder) {
    this.agregarRevisorFirmaConjuntaBinder = agregarRevisorFirmaConjuntaBinder;
  }

  public Usuario getUsuarioSelected() {
    return usuarioRevisorSeleccionado;
  }

  public void setUsuarioSelected(Usuario usuarioSelected) {
    this.usuarioRevisorSeleccionado = usuarioSelected;
  }

  @Override
  protected void asignarTarea() throws InterruptedException {
    Clients.clearBusy();

    Map<String, Object> datos = new HashMap<>();

    datos.put("origen", Constantes.EVENTO_ADICION_REVISOR);
    datos.put("revisor", this.armarDatosUsuarioBeanConUsuarioRevisor(usuarioRevisorSeleccionado));

    this.closeAndNotifyAssociatedWindow(datos);
  }

  @Override
  protected void enviarBloqueoPantalla(Object data) {
    Clients.showBusy(Labels.getLabel("gedo.general.procesando.procesandoSolicitud"));
    Events.echoEvent("onUser", this.self, data);
  }

  public IUsuarioService getUsuarioService() {
    return usuarioService;
  }

}

/**
 * Escucha eventos de notificación para refrescar datos.
 * 
 */
final class RevisorFirmaConjunta implements EventListener {
  private AgregarRevisorFirmaConjuntaComposer composer;

  public RevisorFirmaConjunta(AgregarRevisorFirmaConjuntaComposer comp) {
    this.composer = comp;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {

      Map<String, Object> datos = (Map<String, Object>) event.getData();
      if ("validarApoderamiento".equals(datos.get("funcion"))) {
        Usuario usuario = (Usuario) datos.get("datos");
        this.composer.validarUsuarios(usuario);
      }

      if ("validarReparticion".equals(datos.get("funcion"))) {
        Usuario usuario = (Usuario) datos.get("datos");
        this.composer.validacionesReparticion(usuario);
      }

      if ("asignarTarea".equals(datos.get("funcion"))) {
        this.composer.asignarTarea();
      }
    }
  }
}
