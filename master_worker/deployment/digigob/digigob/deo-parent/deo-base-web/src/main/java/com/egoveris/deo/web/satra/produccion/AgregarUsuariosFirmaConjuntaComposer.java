package com.egoveris.deo.web.satra.produccion;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.ValidarApoderamientoComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AgregarUsuariosFirmaConjuntaComposer extends ValidarApoderamientoComposer {

  private static final long serialVersionUID = 7452445516173787257L;

  private final static Logger logger = LoggerFactory
      .getLogger(AgregarUsuariosFirmaConjuntaComposer.class);

  private Window agregarUsuariosFirmaConjuntaWindow;
  private Combobox usuarioFirmaConjuntaCombo;
  private Button agregarUsuarioFirmaConjunta;
  private Listbox usuariosAgregadosListBox;
  private Button finAgregarUsuarioFirmaConjunta;
  private Label mensajeUsuarioGenerador;
  private Label textoFirmaConjunta;
  private Listheader usuario, usuarioRevisor, accion;

  private List<Usuario> usuariosParaAgregarLista;
  private List<String> usuariosReservadosList;
  private List<Usuario> listaUsuariosInicial;
  private List<String> usuariosReservadosListInicial;
  private AnnotateDataBinder binder;
  private Usuario usuarioSelected;

  @WireVariable("usuarioServiceImpl")
  private IUsuarioService usuarioService;
  private TipoDocumentoDTO tipoDocumento;
  @WireVariable("reparticionHabilitadaServiceImpl")
  private ReparticionHabilitadaService reparticionesHabilitadaService;
  private Usuario revisorAgregado;
  @WireVariable("dBProperty")
  private AppProperty appProperty;

  private Window agregarRevisorFirmaConjunta;
  private String modoRenderizado;
  private String[] listaTipoDocs;

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    comp.addEventListener(Events.ON_USER, new FirmaConjuntaListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new FirmaConjuntaListener(this));

    usuarioFirmaConjuntaCombo.setModel(
        ListModels.toListSubModel(new ListModelList(this.getUsuarioService().obtenerUsuarios()),
            new UsuariosComparator(), 30));
    this.usuariosReservadosList = new ArrayList<String>();
    this.usuariosReservadosListInicial = new ArrayList<String>();
    Map<String, Object> datos = (HashMap<String, Object>) Executions.getCurrent().getArg();
    modoRenderizado = ((String) datos.get("modoRenderizado"));
    if (modoRenderizado.equals(Constantes.MODO_RENDERIZADO_FIRMACONJUNTA)) {
      tipoDocumento = (TipoDocumentoDTO) (datos.get("tipoDocumento"));
      this.listaTipoDocs = appProperty.getString("validarTipoDocumento.firmaConjunta").split(";");
      this.usuariosParaAgregarLista = (List<Usuario>) datos.get("usuariosAgregados");
      if (usuariosParaAgregarLista == null) {
        usuariosParaAgregarLista = new ArrayList<Usuario>();
      } else if (usuariosParaAgregarLista.size() > 0) {
        refreshListboxUsuariosAgregados(true);
      }

    } else if (modoRenderizado.equals(Constantes.MODO_RENDERIZADO_USUARIOSRESERVADOS)) {
      agregarUsuariosFirmaConjuntaWindow.setTitle("Usuarios Reservados");
      this.usuariosReservadosList = (List<String>) datos.get("usuariosReservadosAgregados");
      this.usuariosParaAgregarLista = new ArrayList<Usuario>();
      for (String usuario : usuariosReservadosList) {
        this.usuariosParaAgregarLista.add(this.usuarioService.obtenerUsuario(usuario));
      }

      usuariosAgregadosListBox.setItemRenderer(
          "com.egoveris.deo.web.satra.pl.renderers.UsuariosAgregadosReservaItemRenderer");
      this.usuario.setWidth("80%");
      this.usuarioRevisor.setVisible(false);
      this.accion.setWidth("20%");
      this.textoFirmaConjunta.setVisible(false);

    }
    this.listaUsuariosInicial = new ArrayList<Usuario>();
    for (Usuario usuario : this.usuariosParaAgregarLista) {
      listaUsuariosInicial.add(usuario);
    }
    for (String usuario : usuariosReservadosList) {
      this.usuariosReservadosListInicial.add(usuario);
    }
    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
  }

  /**
   * Realiza validaciones antes de adicionar el usuario a la lista de firmantes.
   * 
   * @throws InterruptedException
   * @throws SecurityNegocioException
   */
  public void onAgregarUsuario() throws InterruptedException, SecurityNegocioException {

    if ((this.usuarioFirmaConjuntaCombo.getSelectedItem() == null)) {
      throw new WrongValueException(this.usuarioFirmaConjuntaCombo,
          Labels.getLabel("gedo.firmaConjunta.errores.faltaUsuario"));
    }
    Usuario usuarioReducido = (Usuario) this.usuarioFirmaConjuntaCombo.getSelectedItem()
        .getValue();
    this.usuarioSelected = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());

    if (modoRenderizado.equals(Constantes.MODO_RENDERIZADO_FIRMACONJUNTA)) {
      Boolean noFirmanteDuplicado = false;
      for (String tipoDoc : this.listaTipoDocs) {
        if (tipoDoc.equals(this.tipoDocumento.getAcronimo())) {
          noFirmanteDuplicado = true;
          break;
        }
      }

      if (!noFirmanteDuplicado) {
        if (this.usuariosParaAgregarLista.contains(this.usuarioSelected)) {
          throw new WrongValueException(this.usuarioFirmaConjuntaCombo,
              Labels.getLabel("gedo.firmaConjunta.validaRepetidos"));
        }
      }

      // Validación que el usuario esté autorizado para firmar el
      // documento.
      if (!reparticionesHabilitadaService.validarPermisosUsuariosDeSuListaReparticiones(
          this.tipoDocumento, this.usuarioSelected.getUsername(),
          Constantes.REPARTICION_PERMISO_FIRMAR)) {
        throw new WrongValueException(this.usuarioFirmaConjuntaCombo,
            Labels.getLabel("gedo.iniciarDocumento.sinAutorizacionFirma",
                new String[] { this.tipoDocumento.getAcronimo() }));

      }
      Map<String, Object> datos = new HashMap<String, Object>();
      datos.put("funcion", "validarApoderamiento");
      datos.put("datos", this.usuarioSelected);
      enviarBloqueoPantalla(datos);
    }
    if (modoRenderizado.equals(Constantes.MODO_RENDERIZADO_USUARIOSRESERVADOS)) {
      if (!usuariosParaAgregarLista.contains(usuarioSelected)) {
        usuariosParaAgregarLista.add(usuarioSelected);
        this.usuariosReservadosList.add(usuarioSelected.getUsername());
        usuarioFirmaConjuntaCombo.setValue(null);
        binder.loadComponent(usuariosAgregadosListBox);
      } else {
        throw new WrongValueException(this.usuarioFirmaConjuntaCombo,
            Labels.getLabel("gedo.general.agregarUsuariosFirmaConjunta.usuarioExiste"));
      }
    }
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
   * Agrega el usuario a la lista de firmantes
   * 
   * @param dub
   */
  public void agregarUsuario(Usuario us) {
    // Es el primer usuario que se carga
    if (this.usuariosParaAgregarLista == null) {
      this.usuariosParaAgregarLista = new ArrayList<Usuario>();
      this.usuariosParaAgregarLista.add(us);
    } else {
      usuariosParaAgregarLista.add(us);
    }
    refreshListboxUsuariosAgregados(false);
  }

  /**
   * Cerrar y volver a la ventana padre.
   */
  public void onFinalizarAgregacion() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (modoRenderizado.equals(Constantes.MODO_RENDERIZADO_FIRMACONJUNTA)) {
      map.put("origen", Constantes.EVENTO_USUARIOS_FIRMANTES);
      map.put("datos", this.usuariosParaAgregarLista);
    } else if (modoRenderizado.equals(Constantes.MODO_RENDERIZADO_USUARIOSRESERVADOS)) {
      map.put("origen", Constantes.EVENTO_USUARIOS_RESERVADOS);
      map.put("datos", this.usuariosReservadosList);
    }
    this.closeAndNotifyAssociatedWindow(map);
  }

  public void onCancelarAgregacion() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (modoRenderizado.equals(Constantes.MODO_RENDERIZADO_FIRMACONJUNTA)) {
      map.put("origen", Constantes.EVENTO_USUARIOS_FIRMANTES);
      map.put("datos", this.listaUsuariosInicial);
    } else if (modoRenderizado.equals(Constantes.MODO_RENDERIZADO_USUARIOSRESERVADOS)) {
      map.put("origen", Constantes.EVENTO_USUARIOS_RESERVADOS);
      map.put("datos", this.usuariosReservadosListInicial);
    }
    this.closeAndNotifyAssociatedWindow(map);
  }

  public void onModificarRevisor() {
    try {
      this.agregarRevisor();
    } catch (Exception e) {
      logger.error("Error al agregar el revisor. Error: " + e.getMessage(), e);
    }
  }

  /**
   * Envía el valor inicial de la lista antes de los valores actuales.
   */
  public void enviarCancelacion() {
    this.closeAndNotifyAssociatedWindow(this.listaUsuariosInicial);
  }

  public void onEliminarUsuario() throws InterruptedException {
    Messagebox.show(Labels.getLabel("gedo.firmaConjunta.eliminarUsuario"),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              eliminarUsuario();
              refreshListboxUsuariosAgregados(false);
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
  }

  public void onEliminarUsuarioReservado() throws InterruptedException {
    Messagebox.show(Labels.getLabel("gedo.firmaConjunta.eliminarUsuario"),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              getUsuariosReservadosList().remove(getUsuarioSelected().getUsername());
              getUsuariosParaAgregarEnFirmaConjunta().remove(getUsuarioSelected());
              getBinder().loadComponent(getUsuariosAgregadosListBox());
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
  }

  public void eliminarUsuario() {
    Usuario user = this.usuarioSelected;
    if (user != null) {
      Iterator<Usuario> it = this.usuariosParaAgregarLista.iterator();
      while (it.hasNext()) {
        Usuario us = it.next();
        if (us.getUsername().equals(user.getUsername())) {
          it.remove();
          break;
        }
      }
    }
  }

  public void onSubir() {
    if (this.usuarioSelected != null) {
      int posic = 0;
      for (int i = 0; i < this.usuariosParaAgregarLista.size(); i++) {
        if (this.usuariosParaAgregarLista.get(i).getUsername()
            .equals(this.usuarioSelected.getUsername())) {
          posic = i;
          break;
        }
      }
      if (posic != 0) {
        // Una vez q se en q posicion esta el elemento que quiero subir,
        // pongo en una variable auxiliar el elemento que va a
        // reemplazar
        Usuario dubParaBajar = this.usuariosParaAgregarLista.get(posic - 1);
        // Pongo en la nueva posicion al que quiero subir
        this.usuariosParaAgregarLista.set(posic - 1, this.usuarioSelected);
        // Pongo en la posicion vieja el que cambie
        this.usuariosParaAgregarLista.set(posic, dubParaBajar);
        refreshListboxUsuariosAgregados(false);
      } else {
        return;
      }
    }
  }

  public void onBajar() {
    if (this.usuarioSelected != null) {
      int posic = 0;
      for (int i = 0; i < this.usuariosParaAgregarLista.size(); i++) {
        if (this.usuariosParaAgregarLista.get(i).getUsername()
            .equals(this.usuarioSelected.getUsername())) {
          posic = i;
          break;
        }
      }
      if (posic != this.usuariosParaAgregarLista.size() - 1) {
        // Una vez q se en q posicion esta el elemento que quiero bajar,
        // pongo en una variable auxiliar el elemento que va a
        // reemplazar
        Usuario dubParaSubir = this.usuariosParaAgregarLista.get(posic + 1);
        // Pongo en la nueva posicion al que quiero bajar
        this.usuariosParaAgregarLista.set(posic + 1, this.usuarioSelected);
        // Pongo en la posicion vieja el que cambie
        this.usuariosParaAgregarLista.set(posic, dubParaSubir);
        refreshListboxUsuariosAgregados(false);
      } else {
        return;
      }
    }
  }

  public void refreshListboxUsuariosAgregados(Boolean desdeInicio) {
    if (!desdeInicio) {
      this.binder.loadComponent(this.usuariosAgregadosListBox);
    }
    this.usuarioFirmaConjuntaCombo.setValue(null);
    if (usuariosParaAgregarLista != null && usuariosParaAgregarLista.size() != 0) {
      Usuario ultimoUsuario = usuariosParaAgregarLista.get(usuariosParaAgregarLista.size() - 1);
      this.mensajeUsuarioGenerador
          .setValue(Labels.getLabel("gedo.firmaConjunta.usuarioGenerador", new String[] {
              ultimoUsuario.getNombreApellido(), ultimoUsuario.getCodigoReparticion() }));
    } else {
      this.mensajeUsuarioGenerador.setValue(null);
    }
  }

  public Combobox getUsuarioFirmaConjuntaCombo() {
    return usuarioFirmaConjuntaCombo;
  }

  public void setUsuarioFirmaConjuntaCombo(Combobox usuarioFirmaConjuntaCombo) {
    this.usuarioFirmaConjuntaCombo = usuarioFirmaConjuntaCombo;
  }

  public Window getAgregarUsuariosFirmaConjuntaWindow() {
    return agregarUsuariosFirmaConjuntaWindow;
  }

  public void setAgregarUsuariosFirmaConjuntaWindow(Window agregarUsuariosFirmaConjuntaWindow) {
    this.agregarUsuariosFirmaConjuntaWindow = agregarUsuariosFirmaConjuntaWindow;
  }

  public Button getAgregarUsuarioFirmaConjunta() {
    return agregarUsuarioFirmaConjunta;
  }

  public void setAgregarUsuarioFirmaConjunta(Button agregarUsuarioFirmaConjunta) {
    this.agregarUsuarioFirmaConjunta = agregarUsuarioFirmaConjunta;
  }

  public Button getFinAgregarUsuarioFirmaConjunta() {
    return finAgregarUsuarioFirmaConjunta;
  }

  public void setFinAgregarUsuarioFirmaConjunta(Button finAgregarUsuarioFirmaConjunta) {
    this.finAgregarUsuarioFirmaConjunta = finAgregarUsuarioFirmaConjunta;
  }

  public Listbox getUsuariosAgregadosListBox() {
    return usuariosAgregadosListBox;
  }

  public void setUsuariosAgregadosListBox(Listbox usuariosAgregadosListBox) {
    this.usuariosAgregadosListBox = usuariosAgregadosListBox;
  }

  public AnnotateDataBinder getAgregarUsuariosFirmaConjuntaBinder() {
    return binder;
  }

  public void setAgregarUsuariosFirmaConjuntaBinder(
      AnnotateDataBinder agregarUsuariosFirmaConjuntaBinder) {
    this.binder = agregarUsuariosFirmaConjuntaBinder;
  }

  public List<Usuario> getUsuariosParaAgregarEnFirmaConjunta() {
    return usuariosParaAgregarLista;
  }

  public void setUsuariosParaAgregarEnFirmaConjunta(
      List<Usuario> usuariosParaAgregarEnFirmaConjunta) {
    this.usuariosParaAgregarLista = usuariosParaAgregarEnFirmaConjunta;
  }

  public Usuario getUsuarioSelected() {
    return usuarioSelected;
  }

  public void setUsuarioSelected(Usuario usuarioSelected) {
    this.usuarioSelected = usuarioSelected;
  }

  @Override
  protected void asignarTarea() throws InterruptedException {
    this.agregarUsuario(this.usuarioSelected);

    Clients.clearBusy();

    if (this.usuarioSelected.getUsuarioRevisor() == null
        || this.usuarioSelected.getUsuarioRevisor().trim().isEmpty()) {
      Messagebox.show(Labels.getLabel("gedo.firmaConjunta.faltaUsuarioRevisor"),
          Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
          Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            public void onEvent(Event evt) throws InterruptedException {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:
                agregarRevisor();
                break;
              case Messagebox.NO:
                break;
              }
            }
          });
    }
  }

  private void agregarRevisor() throws InterruptedException {
    if (agregarRevisorFirmaConjunta != null) {
      this.agregarRevisorFirmaConjunta.invalidate();

      Map<String, Object> datos = new HashMap<String, Object>();
      datos.put("usuarioFirmante", this.usuarioSelected);

      this.agregarRevisorFirmaConjunta = (Window) Executions
          .createComponents("agregarRevisorFirmaConjunta.zul", this.self, datos);
      this.agregarRevisorFirmaConjunta.setParent(this.agregarUsuariosFirmaConjuntaWindow);
      this.agregarRevisorFirmaConjunta.doModal();
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
          Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  @Override
  protected void enviarBloqueoPantalla(Object data) {
    Clients.showBusy(Labels.getLabel("gedo.general.procesando.procesandoSolicitud"));
    Events.echoEvent("onUser", this.self, data);
  }

  public ReparticionHabilitadaService getReparticionesHabilitadaService() {
    return reparticionesHabilitadaService;
  }

  public void setReparticionesHabilitadaService(
      ReparticionHabilitadaService reparticionesHabilitadaService) {
    this.reparticionesHabilitadaService = reparticionesHabilitadaService;
  }

  public Usuario getRevisorAgregado() {
    return revisorAgregado;
  }

  public void setRevisorAgregado(Usuario revisorAgregado) {
    this.revisorAgregado = revisorAgregado;
  }

  public String getModoRenderizado() {
    return modoRenderizado;
  }

  public void setModoRenderizado(String modoRenderizado) {
    this.modoRenderizado = modoRenderizado;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public void agregarRevisor(Usuario usuarioRevisor) throws InterruptedException {
    for (Usuario usuarioParaAgregar : usuariosParaAgregarLista) {
      if (usuarioParaAgregar.getUsername().equals(usuarioSelected.getUsername())) {
        int index = usuariosParaAgregarLista.indexOf(usuarioParaAgregar);
        usuariosParaAgregarLista.set(index, usuarioRevisor);
      }
    }
    refreshListboxUsuariosAgregados(false);
  }

  public IUsuarioService getUsuarioService() {
    return usuarioService;
  }

  public List<String> getUsuariosReservadosList() {
    return usuariosReservadosList;
  }

  public void setUsuariosReservadosList(List<String> usuariosReservadosList) {
    this.usuariosReservadosList = usuariosReservadosList;
  }

  public List<String> getUsuariosReservadosListInicial() {
    return usuariosReservadosListInicial;
  }

  public void setUsuariosReservadosListInicial(List<String> usuariosReservadosListInicial) {
    this.usuariosReservadosListInicial = usuariosReservadosListInicial;
  }

}

/**
 * Escucha eventos de notificación para refrescar datos.
 */
final class FirmaConjuntaListener implements EventListener {
  private AgregarUsuariosFirmaConjuntaComposer composer;

  public FirmaConjuntaListener(AgregarUsuariosFirmaConjuntaComposer comp) {
    this.composer = comp;
  }

  @SuppressWarnings("unchecked")
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {

      Map<String, Object> datos = (Map<String, Object>) event.getData();
      if (datos.get("funcion").equals("validarApoderamiento")) {
        Usuario usuario = (Usuario) datos.get("datos");
        this.composer.validarUsuarios(usuario);
      }
      if (datos.get("funcion").equals("validarReparticion")) {
        Usuario usuario = (Usuario) datos.get("datos");
        this.composer.validacionesReparticion(usuario);
      }

      if (datos.get("funcion").equals("asignarTarea")) {
        this.composer.asignarTarea();
      }
    }

    if (event.getName().equals(Events.ON_NOTIFY)) {
      Map<String, Object> datos = (Map<String, Object>) event.getData();
      if (datos.get("origen").equals(Constantes.EVENTO_ADICION_REVISOR)) {
        Usuario usuarioRevisor = (Usuario) datos.get("revisor");
        this.composer.agregarRevisor(usuarioRevisor);
      }
    }
  }
}
