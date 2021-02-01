package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.SolicitudSubs;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.iface.IActivSubsanacionService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.INotificacionEEService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ZkUtil;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;

public class SubsanacionExpedienteComposer extends GenericActividadComposer {

  private static final long serialVersionUID = 5591559609341288548L;
  private static final Logger logger = LoggerFactory
      .getLogger(SubsanacionExpedienteComposer.class);

  public static final String SOLICITUD_SUBSANACION_TAD = "Solicitud de subsanación a VUC";

  @WireVariable(ConstantesServicios.ACTIV_SUBSANACION_SERVICE)
  private IActivSubsanacionService activSubsanacionServiceImpl;
  @WireVariable(ConstantesServicios.EXP_FORMULARIO_SERVICE)
  protected IExpedienteFormularioService expedienteFormularioService;
  @WireVariable(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE)
  private IActividadExpedienteService actividadExpedienteService;
  @WireVariable(ConstantesServicios.NOTIFICACION_SERVICE)
  private INotificacionEEService notificacionEEService;
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;

  private Textbox motivoTextBox;
  private Checkbox check1;
  private Checkbox check2;
  private Checkbox check3;
  private Toolbarbutton subsaDocs;
  private Button subsaForms;
  private Toolbarbutton agregarDocs;
  private Button guardar;
  private Button cancelarActividad;
  private List<ExternalDocumentoVucDTO> listaDocsAgregar;
  private List<ExternalDocumentoVucDTO> listaTipoDocsSubsanar;
  private Boolean docsEditables;
  private ExpedienteElectronicoDTO ee;
  private List<ExpedienteFormularioDTO> expedientFormsList;
  private List<ExternalDocumentoVucDTO> docsUsuarioVucList;

  private Combobox tipoDestinoComboBox;
  private List<String> tiposDestino;

  @Autowired
  private Window subsanarWindow;
  private String selectedTipoDestino;
  @Autowired
  private Window tramitacionWindow;
  private final String expedienteElectronico = "expedienteElectronico";

  private List<ExternalDocumentoVucDTO> docsAsubsanar;

  @Override
  protected void modoEdicionComposer(Component c) throws Exception {

    initComposer(c);

    ee = (ExpedienteElectronicoDTO) Executions.getCurrent().getDesktop()
        .getAttribute(expedienteElectronico);
    try {
      docsUsuarioVucList = actividadExpedienteService
          .getDocumentosByCodigoExpediente(ee.getCodigoCaratula(), ee.getTrata().getCodigoTrata());
    } catch (Exception e) {
      logger.error("Error al recuperar documentos", e);
    }

    loadExpedientForms(ee.getTrata().getId());

    this.docsEditables = true;
    this.subsaDocs.setDisabled(true);
    this.agregarDocs.setDisabled(true);
    this.subsaForms.setDisabled(true);
    this.cancelarActividad.setDisabled(true);
  }

  public void loadExpedientForms(Long idTramite) {
    expedientFormsList = new ArrayList<>();
    ExpedienteFormularioDTO expedienteFormulario = new ExpedienteFormularioDTO();
    expedienteFormulario.setIdEeExpedient(idTramite);
    logger.info("id de expediente " + idTramite);
    try {
      expedientFormsList = expedienteFormularioService
          .buscarFormulariosPorExpediente(expedienteFormulario);
    } catch (Exception e) {
      logger.error(e.toString());
    }
  }

  @Override
  protected void modoLecturaComposer(Component c) throws Exception {

    initComposer(c);
    ee = (ExpedienteElectronicoDTO) Executions.getCurrent().getDesktop()
        .getAttribute(expedienteElectronico);
    this.docsEditables = false;

    this.listaTipoDocsSubsanar = activSubsanacionServiceImpl
        .getTipoDocumentosVucParaExp(getPopupActividad().getNroExpediente());
    SolicitudSubs solicitudSubs = this.activSubsanacionServiceImpl
        .buscarActividadSolicitudSubs(getPopupActividad().getId());

    motivoTextBox.setValue(solicitudSubs.getMotivo());

    if (solicitudSubs.getListaSubsDocs() != null) {
      checkearListaDocs(solicitudSubs.getListaSubsDocs(), this.listaTipoDocsSubsanar);
    }
    if (solicitudSubs.getListaPedidoDocs() != null) {
      checkearListaDocs(solicitudSubs.getListaPedidoDocs(), this.listaDocsAgregar);
    }

    check1.setChecked(solicitudSubs.isFormulario());
    check2.setChecked(solicitudSubs.isSubsDoc());
    check3.setChecked(solicitudSubs.isPedidoDoc());

    motivoTextBox.setDisabled(true);
    check1.setDisabled(true);
    check2.setDisabled(true);
    check3.setDisabled(true);
    subsaDocs.setDisabled(true);
    agregarDocs.setDisabled(true);
    guardar.setDisabled(true);
    Boolean attrib = (Boolean) Executions.getCurrent().getSession()
        .getAttribute("habilitarCancelacion");
    if (attrib != null && !attrib) {
      cancelarActividad.setDisabled(true);
    } else {
      cancelarActividad.setDisabled(estaVigenteActividadSub());
    }

    loadComboDestinos(solicitudSubs.getDestino());

    tipoDestinoComboBox.setDisabled(true);
  }

  private void loadComboDestinos(final String destinoAux) {
    tipoDestinoComboBox.setModel(new ListModelArray(this.getDestinos()));
    tipoDestinoComboBox.setItemRenderer(new ComboitemRenderer() {

      @Override
      public void render(Comboitem item, Object data, int arg1) throws Exception {
        String destino = (String) data;
        item.setLabel(destino);
        item.setValue(destino);

        if (destinoAux != null && destino.equals(destinoAux)) {
          tipoDestinoComboBox.setSelectedItem(item);
        }
      }
    });
  }

  private List<String> getDestinos() {
    List<String> destinos = new ArrayList<>();
    //destinos.add(ConstantesWeb.DESTINO_INTERVINIENTE);
    destinos.add(ConstantesWeb.DESTINO_TITULAR);
    return destinos;
  }

  private boolean estaVigenteActividadSub() {
    if (getPopupActividad().getEstado().equals("PENDIENTE")) {
      return false;
    }
    return true;
  }

  protected void initComposer(Component c) {
    listaTipoDocsSubsanar = new ArrayList<>();
    c.addEventListener(Events.ON_USER, new SubsanacionExpedienteOnNotifyWindowListener(this));
    this.listaDocsAgregar = activSubsanacionServiceImpl.getTipoDocumentosTadSoportados();
    loadComboDestinos(ConstantesWeb.DESTINO_TITULAR);

  }

  private void mostrarForegroundBloqueanteToken() {
    Clients.showBusy(Labels.getLabel("ee.subsanacion.msg.procesando"));
  }

  public void onClick$agregarDocs() {
    Executions.getCurrent().getDesktop().setAttribute(ListaDocsTadComposer.ES_SUBSANAR, false);
    Executions.getCurrent().getDesktop().setAttribute("tipoDocService", tipoDocumentoService);
    editarListaDocsTad(this.listaDocsAgregar);
  }

  public void onClick$subsaDocs() {
    Executions.getCurrent().getDesktop().setAttribute(ListaDocsTadComposer.ES_SUBSANAR, true);
    editarListaDocs();
  }

  public void onClick$subsaForms() {
    // implementar vista para seleccionar formularios a subsanar
    // objeto con los formularios expedientFormsList
  }

  public void onClick$check2() {
    subsaDocs.setDisabled(!check2.isChecked());
  }

  public void onClick$check3() {
    agregarDocs.setDisabled(!check3.isChecked());
  }

  public void onClick$cancelarActividad() throws InterruptedException, NegocioException {
    mostrarForegroundBloqueanteToken();
    Events.echoEvent(Events.ON_USER, this.self, "cancelarActividad");
  }

  public void cancelarActividad() throws InterruptedException, NegocioException {
    try {
      actividadExpedienteService.eliminarActividadSubsanacion(ee, getUserName());
    } catch (RuntimeException e) {
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.subsanacion.msg.title.error"),
          Messagebox.OK, Messagebox.ERROR);
      return;
    } finally {
      Clients.clearBusy();
    }

    Messagebox.show(Labels.getLabel("ee.act.msg.body.gedo.recha"),
        Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);

    Events.sendEvent(tramitacionWindow, new Event(Events.ON_CHANGE));
    Events.sendEvent(new Event(Events.ON_USER, tramitacionWindow, "elimActividad"));
    Clients.clearBusy();
    onClick$salir();
  }

  public void onClick$salir() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  public void onClick$guardar() throws InterruptedException {
    mostrarForegroundBloqueanteToken();
    Events.echoEvent(Events.ON_USER, this.self, "guardar");
  }

  public void onClick$guardarDoc() throws InterruptedException {
    mostrarForegroundBloqueanteToken();
    Events.echoEvent(Events.ON_USER, this.self, "guardarDoc");
  }

  public void guardar() throws InterruptedException {

	 List<String> selectedDocsAgregar = (List<String>) Executions.getCurrent().getDesktop().getAttribute("docsAgregar");
    validateInputs(selectedDocsAgregar);

    // arma la solicitud con la lista de docs checkeados
    SolicitudSubs solicitudSubs = generarSolicitudSubs(selectedDocsAgregar);

    try {
      activSubsanacionServiceImpl.enviarSubsanacionDocsConNombreExp(solicitudSubs);

      // evento subsanacion realizada
      Events.sendEvent(Events.ON_USER, this.self.getParent(), "subsRealizada");
      // onclose --> evento actCerrada
      Events.sendEvent(this.self, new Event(Events.ON_CLOSE));

      Messagebox.show(Labels.getLabel("ee.subsanacion.msg.body.ok"),
          Labels.getLabel("ee.subsanacion.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);

    } catch (ServiceException e) {
      logger.error("Error al guardar la tarea o actividad", e);

      Messagebox.show(e.getMessage(), Labels.getLabel("ee.subsanacion.msg.title.error"),
          Messagebox.OK, Messagebox.ERROR);

    } finally {
      Clients.clearBusy();
    }
  }

  private SolicitudSubs generarSolicitudSubs(List<String> selectedDocsAgregar) {

    SolicitudSubs solicitudSubs = new SolicitudSubs();

    solicitudSubs.setWorkFlowId(ee.getIdWorkflow());
    solicitudSubs.setNroExpediente(ee.getCodigoCaratula());
    solicitudSubs.setTipo(Labels.getLabel("ee.subsanacion.radio.subsanacion"));
    solicitudSubs.setMotivo(this.motivoTextBox.getValue());
    solicitudSubs.setFormulario(this.check1.isChecked());
    solicitudSubs.setCodigoTrata(ee.getTrata().getCodigoTrata());
    solicitudSubs.setUsuarioAlta(getUserName());

    List<String> tiposDocAsolicitar = new ArrayList<>();
    if (docsAsubsanar != null && !docsAsubsanar.isEmpty()) {
      solicitudSubs.setSubsDoc(true);
      solicitudSubs.setListaPedidoDocConNombres(docsAsubsanar);
      for (ExternalDocumentoVucDTO aux : docsAsubsanar) {
        tiposDocAsolicitar.add(aux.getTipoDocumento().getAcronimoTad());
      }
      solicitudSubs.setListaPedidoDocs(tiposDocAsolicitar);
      solicitudSubs.setListaSubsDocConNombres(docsAsubsanar);
    } else if (selectedDocsAgregar != null && !selectedDocsAgregar.isEmpty()) {
    	solicitudSubs.setListaPedidoDocs(selectedDocsAgregar);
    	solicitudSubs.setPedidoDoc(true);
    }

    solicitudSubs.setDestino((String) tipoDestinoComboBox.getSelectedItem().getValue());
    return solicitudSubs;
  }

  private void validateInputs(List<String> selectedDocsAgregar) {
    if (motivoTextBox.getValue() == null || motivoTextBox.getValue().trim().isEmpty()) {
      Clients.clearBusy();
      this.motivoTextBox.focus();
      throw new WrongValueException(this.motivoTextBox, "Debe ingresar un motivo.");
    }
    // if (!this.check1.isChecked() && !this.check2.isChecked() &&
    // !this.check3.isChecked()) {
    // Clients.clearBusy();
    // throw new WrongValueException(this.check1,
    // Labels.getLabel("ee.subsanacion.checkbox.validation"));
    // }

    if (this.check2.isChecked() && !userHasSelectedDocuments()) {
      Clients.clearBusy();
      throw new WrongValueException(this.subsaDocs,
          Labels.getLabel("ee.subsanacion.lists.validation"));
    }
    
		if (this.check3.isChecked() && (selectedDocsAgregar == null || selectedDocsAgregar.isEmpty())) {
			Clients.clearBusy();
			throw new WrongValueException(this.agregarDocs, Labels.getLabel("ee.subsanacion.lists.validation"));
		}
  }

  private boolean hasCheckedDoc(List<ExternalDocumentoVucDTO> listaDocs) {
    if (!listaDocs.isEmpty()) {
      for (ExternalDocumentoVucDTO docVuc : listaDocs) {
        if (docVuc.isSeleccionado()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Valida que el usuario haya escogido documentos para ser subsanados.
   * 
   * @return
   */
  private boolean userHasSelectedDocuments() {
    if (docsAsubsanar != null && !docsAsubsanar.isEmpty()) {
      return true;
    }
    return false;
  }

  private void editarListaDocsTad(List<ExternalDocumentoVucDTO> listaDoc) {
    Executions.getCurrent().getDesktop().setAttribute(ListaDocsTadComposer.LISTA_DOCS_TAD,
        listaDoc);
    Executions.getCurrent().getDesktop().setAttribute(ListaDocsTadComposer.LISTA_EDITABLE,
        this.docsEditables);
    Map<String, Object> m = new HashMap<>();
    Window docsList = (Window) Executions.createComponents("/expediente/listaDocsTad.zul",
        this.self, m);
    docsList.setClosable(true);

    docsList.doModal();
  }

  private void editarListaDocs() {
    // Envía a la vista los documentos subidos por el usuario en VUC asociados a
    // su expediente para ser listados.
    Map<String, Object> datos = new HashMap<>();
    datos.put("documentosUsuarioVuc", docsUsuarioVucList);
    // Envía los documentos que fueron seleccionados previamente.
    datos.put("documentosSeleccionados", docsAsubsanar);
    Window win = (Window) Executions.createComponents("/expediente/listaDocsVuc.zul", this.self,
        datos);

    Button btnGuardar = ZkUtil.findById(win, "btnGuardar");
    btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
      @Override
      public void onEvent(Event event) throws Exception {
        try {
          // Setea o limpia la lista definitiva de tipos de documento a subsanar
          // en vuc
          if (docsAsubsanar == null) {
            docsAsubsanar = new ArrayList<>();
          } else {
            docsAsubsanar.clear();
          }

          // Setea los documentos a subsanar desde los documentos seleccionados
          // en la vista.
          Listbox listboxDocumentos = ZkUtil.findById(win, "documentosUsuarioVuc");
          Set<Listitem> documentosSeleccionados = (Set<Listitem>) listboxDocumentos
              .getSelectedItems();
          for (Listitem i : documentosSeleccionados) {
            ExternalDocumentoVucDTO dctoVucAsubsanar = (ExternalDocumentoVucDTO) i.getValue();
            docsAsubsanar.add(dctoVucAsubsanar);
          }
          win.detach();
        } catch (Exception e) {
          Messagebox.show(Labels.getLabel("ee.subsanacionDocumentos.error"),
              Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
          logger.error("ocurrio un error al seleccionar el documento", e);
        }
      }
    });

    win.setClosable(true);
    win.doModal();
  }

  private void checkearListaDocs(List<String> listaDocsAct,
      List<ExternalDocumentoVucDTO> listaDocsVuc) {
    for (String docAcr : listaDocsAct) {
      boolean encontro = false;
      for (ExternalDocumentoVucDTO docVuc : listaDocsVuc) {
        if (docVuc.getTipoDocumento().getAcronimoTad().equals(docAcr)) {
          encontro = true;
          docVuc.setSeleccionado(true);
          break;
        }
      }
      if (!encontro) {
        ExternalDocumentoVucDTO docNoEncontrado = new ExternalDocumentoVucDTO();
        docNoEncontrado.setSeleccionado(true);
        docNoEncontrado.getTipoDocumento().setAcronimoTad(docAcr);
        docNoEncontrado.getTipoDocumento()
            .setDescripcion("Documento " + docAcr + " (Error: no disponible en TAD)");
        listaDocsVuc.add(docNoEncontrado);
      }
    }
  }

  public Combobox getTipoDestinoComboBox() {
    return tipoDestinoComboBox;
  }

  public void setTipoDestinoComboBox(Combobox tipoDestinoComboBox) {
    this.tipoDestinoComboBox = tipoDestinoComboBox;
  }

  public void setTiposDestino(List<String> tiposDestino) {
    this.tiposDestino = tiposDestino;
  }

  public List<String> getTiposDestino() {
    return tiposDestino;
  }

  public String getSelectedTipoDestino() {
    return selectedTipoDestino;
  }

  public void setSelectedTipoDestino(String selectedTipoDestino) {
    this.selectedTipoDestino = selectedTipoDestino;
  }

}

final class SubsanacionExpedienteOnNotifyWindowListener implements EventListener {
  private SubsanacionExpedienteComposer composer;

  public SubsanacionExpedienteOnNotifyWindowListener(SubsanacionExpedienteComposer comp) {
    this.composer = comp;
  }

  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {
      if (event.getData().equals("cancelarActividad")) {
        this.composer.cancelarActividad();
      }
      if (event.getData().equals("guardar")) {
        this.composer.guardar();
      }
    }
  }
}
