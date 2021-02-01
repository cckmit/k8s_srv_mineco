package com.egoveris.te.base.composer;

import com.egoveris.ffdd.model.model.CampoBusqueda;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.AuditoriaDeConsultaDTO;
import com.egoveris.te.base.model.DatosDeBusqueda;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.rendered.ValoresMetadatosRenderer;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAuditoriaService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.SearchResultData;
import com.egoveris.te.base.util.TipoDocumentoUtils;
import com.egoveris.te.base.util.ValidacionesUtils;
import com.egoveris.te.base.util.ValidadorDeCuit;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class ConsultasComposer extends EEGenericForwardComposer {

  /**
  * 
  */
  private final static Logger logger = LoggerFactory.getLogger(ConsultasComposer.class);
  private static final long serialVersionUID = -4218306124772951269L;
  private Datebox fechaDesde;
  private Datebox fechaHasta;
  protected Listbox trata;
  protected Combobox nombreMetadato;
  @Autowired
  protected Combobox tiposDocumentoscbb;

  private String estado;

  @Autowired
  private ExternalFormularioService formularioService;

  public ExternalFormularioService getFormularioService() {
    return formularioService;
  }

  public void setFormularioService(ExternalFormularioService formularioService) {
    this.formularioService = formularioService;
  }

  private Grid grillaValoresAgregados;
  private Button agregar;
  private Button blanquearDatos;
  @Autowired
  private Button agregarCampo;
  private String trataSel;

  private Bandbox codigoTrata;
  protected List<TrataDTO> tratas;
  protected List<MetadataDTO> listaMetadata = new ArrayList<>();
  protected List<ExpedienteMetadataDTO> listaExpMetadata = new ArrayList<>();
  protected List<DatosDeBusqueda> datosDeBusqueda = new ArrayList<>();

  protected TrataDTO selectedTrata;
  protected MetadataDTO selectedMetadata;

  protected AnnotateDataBinder binder;

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  protected TrataService trataService;
  
  @WireVariable(ConstantesServicios.AUDITORIA_SERVICE)
  private IAuditoriaService auditoriaService;
  
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  protected TipoDocumentoService tipoDocumentoService;
  @Autowired
  protected List<String> tiposDocumentos;
  protected List<TrataDTO> tratasSeleccionadas;
  @Autowired
  protected String tipoDocumento;
  @Autowired
  protected Textbox numeroDocumentotbx;
  @Autowired
  protected Textbox datoVariabletbx;
  @Autowired
  protected Listbox listboxDatosDeBusqueda;
  protected String selectedDato;
  protected DatosDeBusqueda selectedDatoDeBusqueda;

  @Autowired
  protected Datebox fechaDato;

  @WireVariable(ConstantesServicios.FORM_MANAGER_FACTORY)
  protected IFormManagerFactory<IFormManager<Component>> formManagerFact;

  @Autowired
  protected Combobox datosVariable;
  protected List<String> datosVariables;
  @Autowired
  protected Longbox cuitCuilTipo;

  @Autowired
  protected Longbox cuitCuilDocumento;

  @Autowired
  protected Longbox cuitCuilVerificador;

  public void buscar() {
    if ((!numeroDocumentotbx.getValue().trim().equals("") && tipoDocumento != null)
        || (tipoDocumento == null && numeroDocumentotbx.getValue().trim().equals(""))) {
      boolean cumplePatron = ValidacionesUtils.poseeSoloNumeros(numeroDocumentotbx.getValue());
      if (!cumplePatron && !numeroDocumentotbx.getValue().trim().equals("")) {
        throw new WrongValueException(numeroDocumentotbx, "Debe ingresar solamente números");
      }
    } else {
      if (tipoDocumento == null) {
        throw new WrongValueException(tiposDocumentoscbb, "Debe seleccionar Tipo de Documento");
      } else {
        if (numeroDocumentotbx.getValue().trim().equals("")) {
          throw new WrongValueException(numeroDocumentotbx, "Debe ingresar un número");

        }

      }
    }
    validarCuitCuil();
  }

  private String validarCuitCuil() {

    String cuitCuil = null;

    if (this.cuitCuilTipo.getValue() != null || this.cuitCuilDocumento.getValue() != null
        || this.cuitCuilVerificador.getValue() != null) {

      if (this.cuitCuilTipo.getValue() == null) {
        throw new WrongValueException(this.cuitCuilTipo,
            Labels.getLabel("ee.nuevasolicitud.lenghtcuitCuilTipo"));
      }
      if (this.cuitCuilDocumento.getValue() == null) {
        throw new WrongValueException(this.cuitCuilDocumento,
            Labels.getLabel("ee.nuevasolicitud.nocuitincorrecto"));
      }

      if (this.cuitCuilVerificador.getValue() == null) {
        throw new WrongValueException(this.cuitCuilVerificador,
            Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
      }

      if (this.cuitCuilTipo.getValue() != null
          && this.cuitCuilTipo.getValue().toString().length() != 2) {
        throw new WrongValueException(this.cuitCuilTipo,
            Labels.getLabel("ee.nuevasolicitud.lenghtcuitCuilTipo"));
      }

      String numeroCuitCuil = this.cuitCuilDocumento.getValue().toString();
      if (this.cuitCuilDocumento.getValue() != null
          && this.cuitCuilDocumento.getValue().toString().length() != 8) {
        while (numeroCuitCuil.length() != 8) {
          numeroCuitCuil = "0" + numeroCuitCuil;
        }
        this.cuitCuilDocumento.setValue(new Long(numeroCuitCuil));
      }
      if (this.cuitCuilVerificador.getValue() != null
          && this.cuitCuilVerificador.getValue().toString().length() != 1) {
        throw new WrongValueException(this.cuitCuilVerificador,
            Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
      }

      cuitCuil = this.cuitCuilTipo.getValue().toString() + numeroCuitCuil
          + this.cuitCuilVerificador.getValue().toString();

      ValidadorDeCuit validadorDeCuit = new ValidadorDeCuit();
      try {
        validadorDeCuit.validarNumeroDeCuit(cuitCuil);
      } catch (NegocioException e) {
        throw new WrongValueException(this.cuitCuilTipo, e.getMessage());
      }

    }
    return cuitCuil;
  }

  public void validarCampos(String valor) {
    if (valor.isEmpty() && !fechaDato.isVisible()) {
      throw new WrongValueException(datoVariabletbx, "No se permiten valores vacios o nulos");
    }

    if (selectedDato == null || selectedDato.equals("")) {
      throw new WrongValueException(datosVariable, "Se debe seleccionar un valor");
    }

  }

  public void agregarAGrilla() {
    try {
      String campo = selectedDato;
      String valor = "";

      if (!fechaDato.isVisible()) {
        valor = datoVariabletbx.getValue();
      }

      DatosDeBusqueda campos;
      validarCampos(valor);
      if (!valor.isEmpty() && !fechaDato.isVisible()) {
        campos = new DatosDeBusqueda(campo, valor);
      } else {
        campos = new DatosDeBusqueda();
        campos.setFecha(fechaDato.getValue());
        campos.setCampo(campo);
      }

      if (datosDeBusqueda.contains(campos)) {
        alert("Valores repetidos no estan permitidos");
        return;
      }
      datosDeBusqueda.add(campos);
      datoVariabletbx.setValue("");
      binder.loadComponent(listboxDatosDeBusqueda);
      binder.loadAll();
    } finally {
      quitarForegroundBloqueante();
    }

  }

  public void onClick$agregarCampo() {
    mostrarForegroundBloqueante();
    Events.echoEvent("onUser", this.self, "agregarCampoAGrilla");
  }

  public List<String> pasarCamposAString(List<CampoBusqueda> list) {
    List<String> xs = new ArrayList<String>();
    for (CampoBusqueda dto : list) {
      if (dto.getRelevanciaBusqueda() > 0) {
        xs.add(dto.getEtiqueta());
      }
    }
    if (xs.size() == 0) {
      logger.info("El FFCC no posee campos indexados");
      throw new TeRuntimeException("El FFCC no posee campos indexados!", null);
    }
    return xs;
  }

  public void onEliminarSeleccionado() {
    datosDeBusqueda.remove(selectedDatoDeBusqueda);
    binder.loadAll();
  }

  public List<String> getTiposDocumentos() {

    if (this.tiposDocumentos == null) {
      this.tiposDocumentos = TipoDocumentoUtils.getTiposDocumentos();
      ;
    }
    return this.tiposDocumentos;

  }

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    binder = new AnnotateDataBinder(comp);
    this.estado = (String) Executions.getCurrent().getArg().get("tipo");

    ((Window) this.self).setTitle((String) Executions.getCurrent().getArg().get("titulo"));

    comp.addEventListener(Events.ON_USER, new ManejadorEventoConsultas(this));

    tratas = obtenerTratas();
    tratasSeleccionadas = new ArrayList<TrataDTO>(this.tratas);

    binder.loadComponent(comp);
    if (listboxDatosDeBusqueda != null)
      binder.loadComponent(listboxDatosDeBusqueda);
  }

  public List<String> getDatosVariables() {
    return datosVariables;
  }

  public void setDatosVariables(List<String> datosVariables) {
    this.datosVariables = datosVariables;
  }

  protected void mostrarForegroundBloqueante() {
    Clients.showBusy("Procesando...");
  }

  protected void quitarForegroundBloqueante() {
    Clients.clearBusy();
  }

  public void validarFechas() {
    if (this.fechaDesde.getValue() == null && this.fechaHasta.getValue() != null) {
      throw new WrongValueException("Debe ingresar fecha desde");
    }
    if (this.fechaDesde.getValue() != null && this.fechaHasta.getValue() == null) {
      throw new WrongValueException("Debe ingresar fecha hasta");
    }

    if (fechaDesde.getValue() != null && fechaHasta.getValue() != null) {
      Date desde = fechaDesde.getValue();
      Date hasta = fechaHasta.getValue();
      if (desde.after(hasta)) {
        throw new WrongValueException(fechaDesde,
            "La fecha de inicio no debe ser mayor que la fecha de fin");
      }
    }
  }

  public void onClick$buscar() throws InterruptedException {

    // Se quita la validación para que cargue los 300 expedientes
    // encontrados creados por mi o por mi repartición.
    // validarCriterioBusqueda();

    validarFechas();
    String username = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    String cuitCuil = validarCuitCuil();
    // Auditoria de consulta
    grabarAuditoriaDeConsulta(username);

    List<ExpedienteElectronicoDTO> result = this.buscar(username, selectedTrata, listaExpMetadata,
        datosDeBusqueda, fechaDesde.getValue(), fechaHasta.getValue(), cuitCuil, estado);
    if (result == null || result.size() == 0) {
      Messagebox.show(Labels.getLabel("ee.general.noHayResultados"));
    }
    SearchResultData eventData = new SearchResultData();
    eventData.setResultado(result);
    if (result != null && result.size() != 0) {
      this.closeAndNotifyAssociatedWindow(eventData);
    }
  }

  /**
   * protected abstract List<ExpedienteElectronico> buscar(String username,
   * Trata trata, List<ExpedienteMetadata> expedienteMetaDataList, Date desde,
   * Date hasta, String cuitCuil);
   **/

  protected abstract List<ExpedienteElectronicoDTO> buscar(String username, TrataDTO trata,
      List<ExpedienteMetadataDTO> expedienteMetaDataList,
      List<DatosDeBusqueda> camposYvaloresMetadato, Date desde, Date hasta, String cuitCuil,
      String estado);

  protected ExpedienteElectronicoService getExpedienteElectronicoService() {
    return this.expedienteElectronicoService;
  }

  public List<TrataDTO> obtenerTratas() {
    if (this.selectedTrata == null) {
      this.tratas = trataService.buscarTratasByEstado(TrataDTO.ACTIVO);

      TrataDTO todasLasTratas = new TrataDTO();
      todasLasTratas.setCodigoTrata(" ");
      this.tratas.add(0, todasLasTratas);

    }
    return tratas;
  }

  public List<TrataDTO> getTratas() {
    return this.tratas;
  }

  public List<TrataDTO> getTratasSeleccionadas() {
    return tratasSeleccionadas;
  }

  public void setTratasSeleccionadas(List<TrataDTO> tratasSeleccionadas) {
    this.tratasSeleccionadas = tratasSeleccionadas;
  }

  public void onSelectTrata() {
    this.listaMetadata = selectedTrata.getDatoVariable();
    this.selectedMetadata = null;
    Events.echoEvent("onUser", this.self, null);
  }

  public void onBlur$codigoTrata() {
    if ((this.codigoTrata.getValue() != null) && !this.codigoTrata.getValue().trim().equals("")) {
      this.trataSel = this.codigoTrata.getValue().toUpperCase();
    }
  }

  public void llenarCombo() {
    TipoDocumentoDTO tp = tipoDocumentoService
        .consultarTipoDocumentoPorAcronimo(selectedTrata.getAcronimoDocumento());
    IFormManager<Component> manager;
    try {
      manager = formManagerFact.create(tp.getIdFormulario());
      datosVariables = pasarCamposAString(manager.searchFields());
    } catch (Exception e) {
      // La trata no tiene formulario asociado, entonces no habilito los campos
      // de busqueda por campos de un FFCC
      logger.info("La trata: " + selectedTrata
          + "no poseé un FFCC asociado o no tiene campos indexables...", e.getCause());
      if (listaMetadata != null && listaMetadata.size() > 0) {
        llenarDatosVariablesConDatosPropios();
      } else {
        disablearCamposDeBusqueda(true);
        datosVariables = null;
      }
      quitarForegroundBloqueante();
    }
    if (datosVariables != null && datosVariables.size() > 0) {
      disablearCamposDeBusqueda(false);
    }
    binder.loadComponent(datosVariable);
    quitarForegroundBloqueante();
  }

  public void llenarDatosVariablesConDatosPropios() {
    datosVariables = new ArrayList<String>();
    for (MetadataDTO m : listaMetadata) {
      datosVariables.add(m.getNombre());
    }
  }

  public void disablearCamposDeBusqueda(boolean bool) {
    datosVariable.setDisabled(bool);
    datoVariabletbx.setDisabled(bool);
    listboxDatosDeBusqueda.setVisible(!bool);
    agregarCampo.setDisabled(bool);
  }

  public void onClick$cancelar() {
    ((Window) this.self).onClose();
  }

  public Combobox getNombreMetadato() {
    return nombreMetadato;
  }

  public void setNombreMetadato(Combobox nombreMetadato) {
    this.nombreMetadato = nombreMetadato;
  }

  @SuppressWarnings("unchecked")
  public void onClick$agregar() {

    if (selectedMetadata != null && (this.selectedMetadata.getNombre() != " ")) {

      String nombre = this.selectedMetadata.getNombre();

      for (ExpedienteMetadataDTO metadata : this.listaExpMetadata) {
        if (metadata.getNombre().equals(nombre)) {
          throw new WrongValueException("Los nombres de los valores no pueden repetirse.");
        }
      }
      this.grillaValoresAgregados.setVisible(true);
      this.listaExpMetadata = (List<ExpedienteMetadataDTO>) grillaValoresAgregados.getModel();
      ExpedienteMetadataDTO newMetadata = new ExpedienteMetadataDTO();
      newMetadata.setNombre(nombre);
      listaExpMetadata.add(newMetadata);
      grillaValoresAgregados.setRowRenderer(new ValoresMetadatosRenderer());

      refreshGrid();
    }
  }

  public void onSelect$datosVariable() {
    if (selectedDato.equalsIgnoreCase("fecha") || selectedDato.contains("fecha")) {
      fechaDato.setVisible(true);
      datoVariabletbx.setVisible(false);
    } else {
      fechaDato.setVisible(false);
      datoVariabletbx.setVisible(true);
    }
  }

  public void refreshGrid() {
    if (this.listaExpMetadata.size() >= 3) {
      this.agregar.setDisabled(true);
    }

    this.binder.loadComponent(this.grillaValoresAgregados);
  }

  public List<MetadataDTO> getListaMetadata() {
    return listaMetadata;
  }

  public void setListaMetadata(List<MetadataDTO> listaMetadata) {
    this.listaMetadata = listaMetadata;
  }

  public List<ExpedienteMetadataDTO> getListaExpMetadata() {
    return listaExpMetadata;
  }

  public void setListaExpMetadata(List<ExpedienteMetadataDTO> listaExpMetadata) {
    this.listaExpMetadata = listaExpMetadata;
  }

  public MetadataDTO getSelectedMetadata() {
    return selectedMetadata;
  }

  public void setSelectedMetadata(MetadataDTO selectedMetadata) {
    this.selectedMetadata = selectedMetadata;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocService) {
    this.tipoDocumentoService = tipoDocService;
  }

  public TrataDTO getSelectedTrata() {
    return selectedTrata;
  }

  public void setSelectedTrata(TrataDTO selectedTrata) {
    this.selectedTrata = selectedTrata;
  }

  public TrataService getTrataService() {
    return trataService;
  }

  public void setTrataService(TrataService trataService) {
    this.trataService = trataService;
  }

  public void setTratas(List<TrataDTO> tratas) {
    this.tratas = tratas;
  }

  private void grabarAuditoriaDeConsulta(String username) {
    
    AuditoriaDeConsultaDTO auditoriaDeConsulta = new AuditoriaDeConsultaDTO();

    auditoriaDeConsulta.setFechaDesde(this.fechaDesde.getValue());
    auditoriaDeConsulta.setFechaHasta(this.fechaHasta.getValue());
    auditoriaDeConsulta.setTipoDocumento(this.tipoDocumento);
    auditoriaDeConsulta.setNumeroDocumento(this.numeroDocumentotbx.getValue());

    if (this.selectedTrata != null) {
      auditoriaDeConsulta.setTrata(this.selectedTrata.getCodigoTrata());

      if (this.listaExpMetadata.size() == 1) {
        auditoriaDeConsulta.setMetadato1(this.listaExpMetadata.get(0).getNombre());
        //
        auditoriaDeConsulta.setValorMetadato1(this.listaExpMetadata.get(0).getValor());
      }
      if (this.listaExpMetadata.size() == 2) {
        auditoriaDeConsulta.setMetadato1(this.listaExpMetadata.get(0).getNombre());
        auditoriaDeConsulta.setMetadato2(this.listaExpMetadata.get(1).getNombre());
        //
        auditoriaDeConsulta.setValorMetadato1(this.listaExpMetadata.get(0).getValor());
        auditoriaDeConsulta.setValorMetadato2(this.listaExpMetadata.get(1).getValor());
      }
      if (this.listaExpMetadata.size() == 3) {
        auditoriaDeConsulta.setMetadato1(this.listaExpMetadata.get(0).getNombre());
        auditoriaDeConsulta.setMetadato2(this.listaExpMetadata.get(1).getNombre());
        auditoriaDeConsulta.setMetadato3(this.listaExpMetadata.get(2).getNombre());
        //
        auditoriaDeConsulta.setValorMetadato1(this.listaExpMetadata.get(0).getValor());
        auditoriaDeConsulta.setValorMetadato2(this.listaExpMetadata.get(1).getValor());
        auditoriaDeConsulta.setValorMetadato3(this.listaExpMetadata.get(2).getValor());
      }
    }

    auditoriaDeConsulta.setUsuario(username);
    auditoriaDeConsulta.setFechaConsulta(new Date());

    auditoriaService.grabarAuditoriaDeConsulta(auditoriaDeConsulta);
  }

  public void setBlanquearDatos(Button blanquearDatos) {
    this.blanquearDatos = blanquearDatos;
  }

  public Button getBlanquearDatos() {
    return blanquearDatos;
  }

  public void onClick$blanquearDatos() {
    this.fechaDesde.setText(null);
    this.binder.loadComponent(this.fechaDesde);
    this.fechaHasta.setText(null);
    this.binder.loadComponent(this.fechaHasta);
    this.selectedTrata = null;
    this.trata.clearSelection();
    this.binder.loadComponent(this.trata);
    this.selectedMetadata = null;
    this.datoVariabletbx.setText("");
    this.binder.loadComponent(this.datoVariabletbx);
    this.listaExpMetadata.clear();
    this.grillaValoresAgregados.setVisible(false);
    this.binder.loadComponent(this.grillaValoresAgregados);
    this.tipoDocumento = null;
    this.numeroDocumentotbx.setText("");
    this.tiposDocumentoscbb.setText(null);

    this.cuitCuilDocumento.setValue(null);
    this.cuitCuilTipo.setValue(null);
    this.cuitCuilVerificador.setValue(null);
    listboxDatosDeBusqueda.setVisible(false);
    datosDeBusqueda.clear();
    agregarCampo.setDisabled(true);
    datosVariable.setDisabled(true);
    selectedDato = "";
    datoVariabletbx.setDisabled(true);
    fechaDato.setVisible(false);
    this.binder.loadComponent(this.tiposDocumentoscbb);
    this.binder.loadAll();
  }

  public void onSelect$trata() {
    mostrarForegroundBloqueante();
    Events.echoEvent(Events.ON_USER, this.self, "selectTrata");
  }

  public void seleccionarTrata() {
    // Si trata no tiene caratula entonces pregunto si tiene datos propios -
    // BEGIN
    if (this.selectedTrata != null) {
      TipoDocumentoDTO tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
          .obtenerTipoDocumento(this.selectedTrata.getAcronimoDocumento());

      // Si trata no tiene caratula entonces pregunto si tiene datos propios -
      // END
      trataSel = this.selectedTrata.getCodigoTrata();
      this.codigoTrata.setValue(trataSel.toUpperCase());
      this.binder.loadAll();
      this.codigoTrata.close();
      this.onSelectTrata();
    }
  }

  public void onChanging$codigoTrata(InputEvent e) {
    this.cargarTratas(e);
  }

  public void cargarTratas(InputEvent e) {
    String matchingText = e.getValue();
    this.tratasSeleccionadas.clear();

    if (!matchingText.equals("") && (matchingText.length() >= 3)) {
      if (this.tratas != null) {
        matchingText = matchingText.toUpperCase();

        Iterator<TrataDTO> iterator = this.tratas.iterator();
        TrataDTO trata = null;

        while ((iterator.hasNext())) {
          trata = iterator.next();

          if ((trata != null)) {
            if ((trata.getCodigoTrata().toUpperCase().trim().contains(matchingText.trim())
                || trataService.obtenerDescripcionTrataByCodigo(trata.getCodigoTrata())
                    .toUpperCase().trim().contains(matchingText.trim()))) {
              this.tratasSeleccionadas.add(trata);
            }
          }
        }
      }
    } else if (matchingText.trim().equals("")) {
      this.tratasSeleccionadas = new ArrayList<TrataDTO>(this.tratas);
    }

    this.binder.loadAll();
  }

  public Combobox getTiposDocumentocbb() {
    return tiposDocumentoscbb;
  }

  public void setTiposDocumentocbb(Combobox tiposDocumentocbb) {
    this.tiposDocumentoscbb = tiposDocumentocbb;
  }

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public DatosDeBusqueda getSelectedDatoDeBusqueda() {
    return selectedDatoDeBusqueda;
  }

  public void setSelectedDatoDeBusqueda(DatosDeBusqueda selectedDatoDeBusqueda) {
    this.selectedDatoDeBusqueda = selectedDatoDeBusqueda;
  }

  public void setNumeroDocumentotbx(Textbox numeroDocumentotbx) {
    this.numeroDocumentotbx = numeroDocumentotbx;
  }

  public void setCuitCuilTipo(Longbox cuitCuilTipo) {
    this.cuitCuilTipo = cuitCuilTipo;
  }

  public Longbox getCuitCuilTipo() {
    return cuitCuilTipo;
  }

  public void setCuitCuilDocumento(Longbox cuitCuilDocumento) {
    this.cuitCuilDocumento = cuitCuilDocumento;
  }

  public Longbox getCuitCuilDocumento() {
    return cuitCuilDocumento;
  }

  public void setCuitCuilVerificador(Longbox cuitCuilVerificador) {
    this.cuitCuilVerificador = cuitCuilVerificador;
  }

  public Longbox getCuitCuilVerificador() {
    return cuitCuilVerificador;
  }

  public List<DatosDeBusqueda> getDatosDeBusqueda() {
    return datosDeBusqueda;
  }

  public void setDatosDeBusqueda(List<DatosDeBusqueda> datosDeBusqueda) {
    this.datosDeBusqueda = datosDeBusqueda;
  }

  public String getSelectedDato() {
    return selectedDato;
  }

  public void setSelectedDato(String selectedDato) {
    this.selectedDato = selectedDato;
  }

  public Datebox getFechaDato() {
    return fechaDato;
  }

  public void setFechaDato(Datebox fechaValor) {
    this.fechaDato = fechaValor;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getEstado() {
    return estado;
  }

}

class ManejadorEventoConsultas implements EventListener {
  ConsultasComposer composer;

  public ManejadorEventoConsultas(ConsultasComposer c) {
    composer = c;
  }

  @Override
  public void onEvent(Event e) throws Exception {
    if (e.getName().equals(Events.ON_USER) && e.getData() == null) {
      composer.llenarCombo();
    }

    if (e.getName().equals(Events.ON_USER) && e.getData() != null
        && e.getData().equals("selectTrata")) {
      composer.seleccionarTrata();
    }

    if (e.getData() != null && ((String) e.getData()).equals("agregarCampoAGrilla")) {
      composer.agregarAGrilla();
    }

  }

}


