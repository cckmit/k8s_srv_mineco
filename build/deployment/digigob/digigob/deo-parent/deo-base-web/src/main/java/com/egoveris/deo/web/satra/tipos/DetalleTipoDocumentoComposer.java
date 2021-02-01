package com.egoveris.deo.web.satra.tipos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.exception.GEDOServicesExceptions;
import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.base.service.FamiliaTipoDocumentoService;
import com.egoveris.deo.base.service.NumeracionEspecialService;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.service.TipoActuacionService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.ActuacionSADEBean;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.model.model.MetadataDTO;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplatePKDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.produccion.ABMTipoDocumentoTemplateComposer;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DetalleTipoDocumentoComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = 1L;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @WireVariable("archivosEmbebidosServiceImpl")
  private ArchivosEmbebidosService archivosEmbebidosService;
  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  @WireVariable("familiaTipoDocumentoServiceImpl")
  private FamiliaTipoDocumentoService familiaTipoDocumentoService;
  @WireVariable("tipoActuacionServiceImpl")
  private TipoActuacionService tipoActuacionService;
  @WireVariable("reparticionHabilitadaServiceImpl")
  private ReparticionHabilitadaService reparticionHabilitadaService;
  @WireVariable("numeracionEspecialServiceImpl")
  private NumeracionEspecialService numeracionEspecialService;

  private TipoDocumentoDTO tipoDocumentoAuxiliar;

  private Window detalleDocumentoWindow, extensionesPermitidasWindow,
      abmTipoDocumentoTemplateWindow, verHistorialWindow;
  private Textbox textboxDescripcion;
  private TipoDocumentoDTO tipoDocumento;
  private Boolean produccionLibre = false, produccionImportado = false, produccionTemplate = false,
      produccionImportadoTemplate = false;

  private String userSession;
  private AnnotateDataBinder binder;
  private String detalle;
  @Autowired
  private Combobox familia, documentoSadecb;
  private List<FamiliaTipoDocumentoDTO> listaFamilia = new ArrayList<>();
  private Checkbox permiteEmbebidos, esFirmaExterna, esFirmaExternaConEncabezado, esEspecial,
      esFirmaConjunta, esConfidencial, tieneAviso, esNotificable, esOculto, resultado;
  private Checkbox tieneToken;
  private Checkbox esDobleFactor;
  private Checkbox esPublicable;
  private Button cargarTemplate;

  @Autowired
  private FamiliaTipoDocumentoDTO selectedlistaFamilia;
  private FamiliaTipoDocumentoDTO selectedNombreFamilia;

  private Button guardar, modificarbtn, tiposDeArchivo;
  private Radio libre, importado, template, importadoTemplate, ambosrd, manualrd, automaticard;
  private boolean ambos = false, automatica = false, manual = false;
  private TipoDocumentoDTO tipoDocumentoOriginal;

  private List<ActuacionSADEBean> actuacionesSADE = new ArrayList<>();
  private ActuacionSADEBean selectedActuacionSADE;

  private Row filaTamanio;
  private Intbox tamanioMax;
  private Integer tamanioMaximo;

  private String valueCkeditor = "";
  private String idFormularioControlado = "";
  private String descripcionTemplate = "";
  private String mensajeEsEspecial = "";
  private boolean cambioEspecial = false;

  private AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");

  private TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos;

  private List<ReparticionHabilitadaDTO> listaDeReparticiones = new ArrayList<>();
  Set<ReparticionHabilitadaDTO> listaNumerosEspeciales = new HashSet<>();

  private Set<TipoDocumentoEmbebidosDTO> listaTipoDocumentosEmbebidosOriginal = new HashSet<>();

  private TipoDocumentoTemplateDTO templateOriginal = null;

  @SuppressWarnings("rawtypes")
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.detalleDocumentoWindow.addEventListener(Events.ON_NOTIFY,
        new DetalleTipoDocumentoComposerListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new DetalleTipoDocumentoComposerListener(this));

    Map map = (Map) Executions.getCurrent().getArg();
    TipoDocumentoDTO tipoDocumento2 = (TipoDocumentoDTO) map.get("tipoDocumento");

    this.tipoDocumento = tipoDocumentoService
        .buscarTipoDocumentoPorAcronimoConFamilia(tipoDocumento2.getAcronimo());
    if (tipoDocumento.getListaTemplates().iterator().hasNext()) {
      TipoDocumentoTemplateDTO tdt = tipoDocumento.getListaTemplates().iterator().next();
    if(tdt.getTemplate() != null){
      byte[] data = tdt.getTemplate().getBytes();
      valueCkeditor = new String(data);
    }
      idFormularioControlado = tdt.getIdFormulario();
      descripcionTemplate = tdt.getDescripcion();
    }

    listaTipoDocumentosEmbebidosOriginal.addAll(
        tipoDocumentoService.buscarTipoDocumentoByAcronimo(this.tipoDocumento.getAcronimo())
            .getTipoDocumentoEmbebidos());

    listaDeReparticiones.addAll(this.tipoDocumento.getListaReparticiones());

    // proabable crash. left commented for help.
    // if (!Hibernate.isInitialized(tipoDocumento.getTipoDocumentoEmbebidos()))
    // {
    // this.tipoDocumento.getTipoDocumentoEmbebidos()
    // .addAll((this.archivosEmbebidosService.buscarTipoDocEmbebidos(this.tipoDocumento)));
    // }

    this.userSession = (String) self.getDesktop().getSession()
        .getAttribute(Constantes.SESSION_USERNAME);

    this.listaFamilia.addAll(familiaTipoDocumentoService.traerfamilias());
    if (this.tipoDocumento.getFamilia() != null) {
      this.familia.setValue(this.tipoDocumento.getFamilia().getNombre());
    }
    this.familia.setValue(this.tipoDocumento.getFamilia().getNombre());
    tamanioMaximo = Integer.valueOf(appProperty.getString("gedo.maximoArchivos"))
        / Constantes.FACTOR_CONVERSION;
    textboxDescripcion.setText(this.tipoDocumento.getDescripcion());
    this.manual = this.tipoDocumento.getEsManual();
    this.automatica = this.tipoDocumento.getEsAutomatica();
    setChecks();
    this.detalle = tipoDocumento.getDescripcion();
    setActuacionSade();

    initializeComponents();

    if (!this.tipoDocumento.getPermiteEmbebidos()) {
      this.tiposDeArchivo.setDisabled(true);
    }

    if (this.tipoDocumento.getTipoProduccion() == 1) {
      this.setProduccionLibre(true);
      cargarTemplate.setDisabled(true);
      esFirmaExternaConEncabezado.setVisible(true);
    } else if (this.tipoDocumento.getTipoProduccion() == 2) {
      this.setProduccionImportado(true);
      esFirmaExterna.setVisible(true);
      cargarTemplate.setDisabled(true);
    } else if (this.tipoDocumento.getTipoProduccion() == 3) {
      this.setProduccionTemplate(true);
      cargarTemplate.setDisabled(true);
      templateOriginal = this.tipoDocumento.getListaTemplates().iterator().next();
    } else if (this.tipoDocumento.getTipoProduccion() == 4) {
      this.setProduccionImportadoTemplate(true);
      cargarTemplate.setDisabled(true);
      templateOriginal = this.tipoDocumento.getListaTemplates().iterator().next();
    }
    this.tamanioMax.setConstraint(new TamanioConstraint());
    if (this.tipoDocumento.getTipoProduccion() == 2
        || this.tipoDocumento.getTipoProduccion() == 4) {
      this.filaTamanio.setVisible(true);
      if (this.tipoDocumento.getSizeImportado() != null) {
        this.tamanioMax
            .setValue(this.tipoDocumento.getSizeImportado() / Constantes.FACTOR_CONVERSION);
      }
    }

    guardarTipoDocumentoOriginal();

    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
  }

  private void initializeComponents() {
    textboxDescripcion.setReadonly(true);
    permiteEmbebidos.setVisible(true);
    permiteEmbebidos.setDisabled(true);
    esNotificable.setDisabled(true);
    modificarbtn.setVisible(true);
    guardar.setVisible(false);
    familia.setDisabled(true);
    tiposDeArchivo.setDisabled(true);
    documentoSadecb.setDisabled(true);
    esFirmaExterna.setVisible(false);
    esFirmaExternaConEncabezado.setDisabled(true);
    tamanioMax.setDisabled(true);
    esOculto.setDisabled(true);
    esPublicable.setDisabled(true);
    resultado.setDisabled(true);
  }

  private void guardarTipoDocumentoOriginal() {
    tipoDocumentoOriginal = new TipoDocumentoDTO(this.tipoDocumento.getId(),
        this.tipoDocumento.getNombre(), this.tipoDocumento.getVersion(),
        this.tipoDocumento.getAcronimo(), this.tipoDocumento.getDescripcion(),
        this.tipoDocumento.getEsEspecial(), this.tipoDocumento.getEsManual(),
        this.tipoDocumento.getEsAutomatica(), this.tipoDocumento.getTieneToken(),
        this.tipoDocumento.getEsDobleFactor(),
        this.tipoDocumento.getTieneTemplate(), this.tipoDocumento.getPermiteEmbebidos(),
        this.tipoDocumento.getEsNotificable(), this.tipoDocumento.getTipoProduccion(),
        this.tipoDocumento.getEstado(), this.tipoDocumento.getIdTipoDocumentoSade(),
        this.tipoDocumento.getCodigoTipoDocumentoSade(), this.tipoDocumento.getEsFirmaConjunta(),
        this.tipoDocumento.getEsConfidencial(), this.tipoDocumento.getListaReparticiones(),
        this.tipoDocumento.getListaDatosVariables(), this.tipoDocumento.getEstaHabilitado(),
        this.tipoDocumento.getEsFirmaExterna(),
        this.tipoDocumento.getEsFirmaExternaConEncabezado(), this.tipoDocumento.getFamilia(),
        this.tipoDocumento.getTieneAviso(), this.tipoDocumento.getTipoDocumentoEmbebidos(),
        this.tipoDocumento.getListaTemplates(), this.tipoDocumento.getSizeImportado(),
        this.tipoDocumento.getEsOculto(), this.tipoDocumento.getEsComunicable(), this.tipoDocumento.getResultado(),
        this.tipoDocumento.getEsPublicable());
  }

  private void setChecks() {
    this.esEspecial.setChecked(this.tipoDocumento.getEsEspecial());
    // this.tieneToken.setChecked(this.tipoDocumento.getTieneToken());
    this.esFirmaConjunta.setChecked(this.tipoDocumento.getEsFirmaConjunta());
    this.esConfidencial.setChecked(this.tipoDocumento.getEsConfidencial());
    this.tieneAviso.setChecked(this.tipoDocumento.getTieneAviso());
    if (!this.tipoDocumento.getPermiteEmbebidos()) {
      this.permiteEmbebidos.setVisible(false);
    }
    this.permiteEmbebidos.setChecked(this.tipoDocumento.getPermiteEmbebidos());
    this.esNotificable.setChecked(this.tipoDocumento.getEsNotificable());
    this.esFirmaExterna.setChecked(this.tipoDocumento.getEsFirmaExterna());
    this.esFirmaExternaConEncabezado
        .setChecked(this.tipoDocumento.getEsFirmaExternaConEncabezado());
    this.esOculto.setChecked(this.tipoDocumento.getEsOculto());
    this.resultado.setChecked(this.tipoDocumento.getResultado());

    if (this.tipoDocumento.getEsAutomatica() == true && this.tipoDocumento.getEsManual() == true) {
      ambos = true;
      ambosrd.setChecked(true);
    } else {
      automatica = this.tipoDocumento.getEsAutomatica();
      automaticard.setChecked(automatica);
      manual = this.tipoDocumento.getEsManual();
      manualrd.setChecked(manual);
    }

  }

  private void setActuacionSade() {
    if (actuacionesSADE.size() == 0) {
      actuacionesSADE = tipoActuacionService.obtenerListaTodasLasActuaciones();
    }
    for (int i = 0; i < actuacionesSADE.size(); i++) {
      if (this.tipoDocumento.getIdTipoDocumentoSade().equals(actuacionesSADE.get(i).getId())) {
        selectedActuacionSADE = actuacionesSADE.get(i);
        break;
      }
    }
  }

  public void onCancelar() throws InterruptedException {
    this.detalleDocumentoWindow.detach();
  }

  public void onGuardar() throws InterruptedException {

    if (this.tipoDocumento.getEsEspecial() && this.tipoDocumentoOriginal.getEsEspecial()) {
      Clients.clearBusy();
      Messagebox.show(Labels.getLabel("gedo.reparticionesHabilitadas.conservarNumeroEspecial"),
          Labels.getLabel("gedo.agregarReparticiones.windows.title"),
          Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
            public void onEvent(Event evt) throws InterruptedException {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:
                listaNumerosEspeciales = obtenerListaNumerosEspeciales();
                guardar(true);
                break;
              case Messagebox.NO:
                guardar(false);
                break;
              }
            }
          });

    } else {
      guardar(false);
    }

  }

  private void guardar(boolean guardarReparticion) throws InterruptedException {
    validarParametros();
    this.tipoDocumentoAuxiliar = new TipoDocumentoDTO();
    this.tipoDocumentoAuxiliar.setId(this.tipoDocumento.getId());

    if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
        || this.tipoDocumento
            .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      this.tipoDocumento
          .setSizeImportado(this.tamanioMax.getValue() * Constantes.FACTOR_CONVERSION);
    } else {
      this.tipoDocumento.setSizeImportado(null);
    }

    guardarTemplate();
    setTiposProduccion();

    if (setNuevaVersion()) {

      setProduccion();
      if (this.tipoDocumento.getEsEspecial() && !this.tipoDocumentoOriginal.getEsEspecial()) {
        cambioEspecial = true;
      }

      if (this.tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_IMPORTADO) {
        this.tipoDocumento.setEsFirmaExterna(false);
      }

      this.tipoDocumento.setId(null);
      this.tipoDocumento.setUsuarioCreador(userSession);
      this.tipoDocumento.setFechaCreacion((Date) Calendar.getInstance().getTime());
      guardarReparticiones();
      guardarEmbebidos();
      guardarDatosVariables();

      this.tipoDocumento.setDescripcion(textboxDescripcion.getValue());

      if (this.selectedlistaFamilia != null) {
        this.tipoDocumento.setFamilia(this.selectedlistaFamilia);
      }

      try {
        tipoDocumentoService.crearTipoDocumento(this.tipoDocumento, userSession);
        if (guardarReparticion) {
          guardarNumeroEspecial(listaNumerosEspeciales);
        }
        if (cambioEspecial) {
          mensajeEsEspecial = Labels.getLabel("gedo.abmTipoDocumento.esEspecial");
        }
        Messagebox.show(
            Labels.getLabel("gedo.detalleDocumento.msgbox.tipoDoc") + " \""
                + tipoDocumento.getNombre() + " \" "
                + Labels.getLabel("gedo.detalleDocumento.msgbox.modificadoExito") + " "
                + Labels.getLabel("gedo.abmTipoDocumento.exito") + ". " + mensajeEsEspecial,
            Labels.getLabel("gedo.abmTipoDocumento.informacion"), Messagebox.OK,
            Messagebox.INFORMATION);

        guardarTipoDocumentoOriginal();
        this.tipoDocumentoService.actualizarTareasExistentesConNuevaVersion(this.tipoDocumento,
            this.tipoDocumentoAuxiliar);

      } catch (GEDOServicesExceptions e) {
        logger.error("Error al guardar tipo de documento " + e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("gedo.abmTipoDocumento.informacion"),
            Messagebox.OK, Messagebox.INFORMATION);
      }

      Map<String, String> map = new HashMap<>();
      map.put("onNotify", "onNotify");
      map.put("accion", "noGuardar");
      Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));
      // this.closeAndNotifyAssociatedWindow(map);
      this.detalleDocumentoWindow.detach();

    } else {
      Messagebox.show(
          Labels.getLabel("gedo.detalleDocumento.msgbox.tipoDoc") +  " \"" + tipoDocumento.getNombre()
              + "\" " + Labels.getLabel("gedo.detalleDocumento.msgbox.noModificaciones"),
          Labels.getLabel("gedo.abmTipoDocumento.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
      volverDeModificar();
    }
  }

  private void setTiposProduccion() {
    this.tipoDocumento.setEsManual(this.manual);
    this.tipoDocumento.setEsAutomatica(this.automatica);
  }

  private void guardarReparticiones() {
    Set<ReparticionHabilitadaDTO> listaReparticion = new TreeSet<>();
    Set<ReparticionHabilitadaDTO> lista = this.tipoDocumento.getListaReparticiones();

    for (ReparticionHabilitadaDTO r : lista) {
      ReparticionHabilitadaDTO reparticion = new ReparticionHabilitadaDTO();
      reparticion.setCodigoReparticion(r.getCodigoReparticion());
      reparticion.setEdicionNumeracionEspecial(r.getEdicionNumeracionEspecial());
      reparticion.setEstado(r.getEstado());
      if (this.tipoDocumento.getEsEspecial()
          && r.getCodigoReparticion().compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) == 0) {
        reparticion.setPermisoFirmar(false);
      } else {
        reparticion.setPermisoFirmar(r.getPermisoFirmar());
      }
      reparticion.setPermisoIniciar(r.getPermisoIniciar());
      reparticion.setTipoDocumento(this.tipoDocumento);
      listaReparticion.add(reparticion);
    }
    this.tipoDocumento.setListaReparticiones(listaReparticion);

  }

  private Set<ReparticionHabilitadaDTO> obtenerListaNumerosEspeciales() {
    TipoDocumentoDTO tipoDocProvisorio = tipoDocumentoService
        .buscarTipoDocumentoPorAcronimoConFamilia(tipoDocumento.getAcronimo());
    return reparticionHabilitadaService.cargarReparticionesHabilitadas(tipoDocProvisorio);
  }

  private void guardarNumeroEspecial(Set<ReparticionHabilitadaDTO> reparticiones) {
    for (ReparticionHabilitadaDTO reparticion : reparticiones) {
      if (reparticion.getCodigoReparticion().compareTo("--TODAS--") != 0) {
        NumeracionEspecialDTO numeroEspecial = new NumeracionEspecialDTO();
        numeroEspecial.setAnio(reparticion.getNumeracionEspecial().getAnio());
        numeroEspecial.setCodigoReparticion(reparticion.getCodigoReparticion());
        numeroEspecial.setNumero(reparticion.getNumeracionEspecial().getNumero());
        numeroEspecial.setNumeroInicial(reparticion.getNumeracionEspecial().getNumeroInicial());
        numeroEspecial.setIdTipoDocumento(this.tipoDocumento.getId());
        numeroEspecial
            .setCodigoEcosistema(reparticion.getNumeracionEspecial().getCodigoEcosistema());

        String tipoOperacion = (numeroEspecial.getId() == null) ? Constantes.AUDITORIA_OP_ALTA
            : Constantes.AUDITORIA_OP_MODIFICACION;
        numeracionEspecialService.guardar(numeroEspecial);

        numeracionEspecialService.numeracionEspecialAuditoria(numeroEspecial, this.userSession,
            tipoOperacion);
      }
    }
  }

  private void guardarEmbebidos() {
    Set<TipoDocumentoEmbebidosDTO> listaEmbebidos = new HashSet<>();
    Set<TipoDocumentoEmbebidosDTO> lista = this.tipoDocumento.getTipoDocumentoEmbebidos();
    for (TipoDocumentoEmbebidosDTO e : lista) {
      TipoDocumentoEmbebidosDTO embebido = new TipoDocumentoEmbebidosDTO();
      embebido.setDescripcion(e.getDescripcion());
      embebido.setFechaCreacion(e.getFechaCreacion());
//      embebido.setFormatoTamanoId(e.getFormatoTamanoId());
      embebido.setTipoDocumentoEmbebidosPK(e.getTipoDocumentoEmbebidosPK());
      embebido.setObligatorio(e.getObligatorio());
      embebido.setUserName(e.getUserName());
      embebido.setSizeArchivoEmb(e.getSizeArchivoEmb());
      embebido.getTipoDocumentoEmbebidosPK().setTipoDocumentoId(this.tipoDocumento);
      listaEmbebidos.add(embebido);
    }
    this.tipoDocumento.setTipoDocumentoEmbebidos(listaEmbebidos);
  }

  private void guardarDatosVariables() {
    List<MetadataDTO> listaDatos = new ArrayList<>();
    List<MetadataDTO> lista = this.tipoDocumento.getListaDatosVariables();
    for (MetadataDTO m : lista) {
      MetadataDTO datosVariables = new MetadataDTO();
      datosVariables.setNombre(m.getNombre());
      datosVariables.setObligatoriedad(m.isObligatoriedad());
      datosVariables.setOrden(m.getOrden());
      datosVariables.setTipo(m.getTipo());
      listaDatos.add(datosVariables);
    }
    this.tipoDocumento.setListaDatosVariables(listaDatos);
  }

  private void guardarTemplate() {
    if (this.tipoDocumento.getTipoProduccion() == 3
        || this.tipoDocumento.getTipoProduccion() == 4) {
      TipoDocumentoTemplateDTO template = new TipoDocumentoTemplateDTO();
      TipoDocumentoTemplatePKDTO pk = new TipoDocumentoTemplatePKDTO();

      pk.setVersion(1);
      template.setDescripcion(descripcionTemplate);
      template.setFechaCreacion(new Date());
      template.setIdFormulario(idFormularioControlado);
      template.setTemplate(valueCkeditor);
      template.setUsuarioAlta(super.execution.getUserPrincipal().getName());
      template.setTipoDocumento(this.tipoDocumento);
      template.setTipoDocumentoTemplatePK(pk);

      this.tipoDocumento.getListaTemplates().clear();
      this.tipoDocumento.getListaTemplates().add(template);
    }
  }

  private void validarParametros() {
    if (this.tipoDocumento.getNombre() == null || this.tipoDocumento.getAcronimo() == null
        || StringUtils.isEmpty(this.tipoDocumento.getDescripcion())) {
      throw new WrongValueException(
          Labels.getLabel("gedo.crearTipoDocComposer.exception.completeRecuadrosVacios"));
    }
    if (this.textboxDescripcion == null) {
      throw new WrongValueException(
          Labels.getLabel("gedo.detalleDocumento.msgbox.completeCuadroDescripcion"));
    }
    if (this.selectedActuacionSADE == null) {
      throw new WrongValueException(Labels.getLabel("gedo.abmTipoDocumento.errorActuacionSade"));
    }
    if (this.familia.getValue().trim().equals("")) {
      throw new WrongValueException(this.familia,
          Labels.getLabel("gedo.crearTipoDocComposer.exception.ingreseFamilia"));
    }

    if (this.permiteEmbebidos.isChecked()) {
      if (this.tipoDocumento.getTipoDocumentoEmbebidos() == null
          || this.tipoDocumento.getTipoDocumentoEmbebidos().size() == 0) {
        throw new WrongValueException(this.tiposDeArchivo,
            Labels.getLabel("gedo.crearTipoDocComposer.exception.ingreseExtencionPermitida"));
      }
    }
    if (this.template.isChecked() || this.importadoTemplate.isChecked()) {
      if ((idFormularioControlado.isEmpty())
          && (this.tipoDocumento.getListaTemplates().size() == 0)) {
        throw new WrongValueException(this.cargarTemplate,
            Labels.getLabel("gedo.abmTipoDocumento.errorNoTemplate"));
      }
    }
    if (selectedlistaFamilia != null && familia.getValue() != null) {
      selectedlistaFamilia = familiaTipoDocumentoService
          .traerFamiliaTipoDocumentoByNombre(familia.getValue());
    }

  }

  public void onCargarTemplate() {
    Map<String, Object> hm = new HashMap<>();

    if (this.tipoDocumento.getListaTemplates().isEmpty()) {
      if (this.getValueCkeditor() != null && this.getIdFormularioControlado() != null) {
        hm.put("modo", ABMTipoDocumentoTemplateComposer.MODO_MODIFICACION_TEMPLATE);
        hm.put("datos", this.getValueCkeditor());
        hm.put("idFormularioControlado", this.getIdFormularioControlado());
        hm.put("descripcionTemplate", this.getDescripcionTemplate());
        hm.put("tipoDocumento", this.tipoDocumento);
      } else {
        hm.put("modo", ABMTipoDocumentoTemplateComposer.MODO_ALTA_TEMPLATE);
      }
    } else {
      String stringEditor = this.tipoDocumento.getListaTemplates().iterator().next().getTemplate();

      setValueCkeditor(stringEditor);
      setIdFormularioControlado(
          this.tipoDocumento.getListaTemplates().iterator().next().getIdFormulario());
      setDescripcionTemplate(
          this.tipoDocumento.getListaTemplates().iterator().next().getDescripcion());
      hm.put("modo", ABMTipoDocumentoTemplateComposer.MODO_MODIFICACION_TEMPLATE);
      hm.put("datos", this.getValueCkeditor());
      hm.put("idFormularioControlado", this.getIdFormularioControlado());
      hm.put("descripcionTemplate", this.getDescripcionTemplate());
      hm.put("tipoDocumento", this.tipoDocumento);
    }
    this.abmTipoDocumentoTemplateWindow = (Window) Executions
        .createComponents("abmTipoDocumentoTemplate.zul", this.self, hm);
    this.abmTipoDocumentoTemplateWindow.setClosable(true);
    this.abmTipoDocumentoTemplateWindow.addEventListener(Events.ON_NOTIFY,
        new DetalleTipoDocumentoComposerListener(this));

    this.abmTipoDocumentoTemplateWindow.doModal();
  }

  private byte[] getTemplateBytes(InputStream is) {
    byte buffer[] = new byte[4000];
    int dataSize = 0;

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    if(is != null){
         try {
           while ((dataSize = is.read(buffer)) != -1) {
             baos.write(buffer, 0, dataSize);
           }
         } catch (IOException e) {
           logger.error("Mensaje de error", e);
         } finally {
             try {
               is.close();
             } catch (IOException e) {
               logger.error("Mensaje de error", e);
             }
         }
    }
    byte[] templateBytes = baos.toByteArray();

    return templateBytes;
  }

  private void setProduccion() {

    if (produccionLibre) {
      this.tipoDocumento.setTipoProduccion(1);
      borrarTemplate();
    } else {
      if (produccionImportado) {
        this.tipoDocumento.setTipoProduccion(2);
        borrarTemplate();
      } else {
        if (produccionTemplate) {
          this.tipoDocumento.setTipoProduccion(3);
        } else {
          if (produccionImportadoTemplate) {
            this.tipoDocumento.setTipoProduccion(4);
          }
        }
      }
    }
  }

  private void borrarTemplate() {
    this.tipoDocumento.getListaTemplates().clear();
  }

  private boolean setNuevaVersion() {

    boolean change = false;
    String version = tipoDocumentoOriginal.getVersion();
    int primerNumero = Integer.valueOf(version.substring(0, version.indexOf(".")));
    int segundoNumero = Integer.valueOf(version.substring(version.indexOf(".") + 1));

    if (!this.tipoDocumento.getTipoProduccion()
        .equals(tipoDocumentoOriginal.getTipoProduccion())) {
      if ((tipoDocumentoOriginal.getTipoProduccion() != 3
          && tipoDocumentoOriginal.getTipoProduccion() != 4)
          || (tipoDocumento.getTipoProduccion() < 3)) {
        this.tipoDocumento.setVersion(String.valueOf(++primerNumero + ".0"));
      } else {
        if (segundoNumero == 9) {
          primerNumero++;
          segundoNumero = 0;
          this.tipoDocumento.setVersion(primerNumero + "." + segundoNumero);
        } else {
          this.tipoDocumento.setVersion(primerNumero + "." + ++segundoNumero);
        }
      }
      change = true;
    } else {
      if ((!this.textboxDescripcion.getValue().equals(tipoDocumentoOriginal.getDescripcion()))
          || (!tipoDocumento.getEsEspecial().equals(tipoDocumentoOriginal.getEsEspecial()))
          || (!tipoDocumento.getEsFirmaExterna().equals(tipoDocumentoOriginal.getEsFirmaExterna()))
          || (!tipoDocumento.getEsFirmaExternaConEncabezado()
              .equals(tipoDocumentoOriginal.getEsFirmaExternaConEncabezado()))
          || (!tipoDocumento.getTieneToken().equals(tipoDocumentoOriginal.getTieneToken()))
          || (!tipoDocumento.getEsDobleFactor().equals(tipoDocumentoOriginal.getEsDobleFactor()))
          || (!tipoDocumento.getEsFirmaConjunta()
              .equals(tipoDocumentoOriginal.getEsFirmaConjunta()))
          || (!tipoDocumento.getEsConfidencial().equals(tipoDocumentoOriginal.getEsConfidencial()))
          || (!tipoDocumento.getTieneAviso().equals(tipoDocumentoOriginal.getTieneAviso()))
          || (!tipoDocumento.getPermiteEmbebidos()
              .equals(tipoDocumentoOriginal.getPermiteEmbebidos()))
          || (!tipoDocumento.getEsNotificable().equals(tipoDocumentoOriginal.getEsNotificable()))
          || (!tipoDocumento.getEsComunicable().equals(tipoDocumentoOriginal.getEsComunicable()))
          || (!tipoDocumento.getEsAutomatica().equals(tipoDocumentoOriginal.getEsAutomatica()))
          || (!tipoDocumento.getEsManual().equals(tipoDocumentoOriginal.getEsManual()))
          || (!tipoDocumento.getCodigoTipoDocumentoSade()
              .equals(tipoDocumentoOriginal.getCodigoTipoDocumentoSade()))
          || (!tipoDocumento.getFamilia().getId()
              .equals(tipoDocumentoOriginal.getFamilia().getId()))
          || (!tipoDocumentoOriginal.getCodigoTipoDocumentoSade()
              .equals(this.tipoDocumento.getCodigoTipoDocumentoSade()))
          || (!tipoDocumentoOriginal.getEsOculto().equals(this.tipoDocumento.getEsOculto()))
          || (!tipoDocumentoOriginal.getEsPublicable().equals(this.tipoDocumento.getEsPublicable()))
          || (!tipoDocumentoOriginal.getResultado().equals(this.tipoDocumento.getResultado()))
          || (changeExtensionsList()) || (cambioTamanioArchivo()) || (cambioElTemplate())) {

        if (segundoNumero == 9) {
          primerNumero++;
          segundoNumero = 0;
          this.tipoDocumento.setVersion(primerNumero + "." + segundoNumero);
        } else {
          this.tipoDocumento.setVersion(primerNumero + "." + ++segundoNumero);
        }
        change = true;
      }
    }
    return change;
  }

  private boolean cambioTamanioArchivo() {
    boolean rdo = false;
    if (this.tipoDocumento.getTipoProduccion() == 2
        || this.tipoDocumento.getTipoProduccion() == 4) {
      if (!this.tipoDocumento.getSizeImportado()
          .equals(this.tipoDocumentoOriginal.getSizeImportado())) {
        rdo = true;
      }
    }
    return rdo;
  }

  private boolean cambioElTemplate() {
    boolean template = false;
    boolean descripcion = false;
    if (this.tipoDocumento.getTipoProduccion() == 3
        || this.tipoDocumento.getTipoProduccion() == 4) {
      String stringEditor = templateOriginal.getTemplate();
      String stringEditor2 = this.tipoDocumento.getListaTemplates().iterator().next()
          .getTemplate();

      template = !stringEditor.equals(stringEditor2);
      descripcion = !templateOriginal.getDescripcion()
          .equals(this.tipoDocumento.getListaTemplates().iterator().next().getDescripcion());
    }
    if (template || descripcion) {
      return true;
    } else {
      return false;
    }
  }

  private boolean changeExtensionsList() {

    if (listaTipoDocumentosEmbebidosOriginal.size() != this.tipoDocumento
        .getTipoDocumentoEmbebidos().size()) {

      return true;
    } else {
      boolean equalsFlag = false;
      for (TipoDocumentoEmbebidosDTO tipoEmbOri : listaTipoDocumentosEmbebidosOriginal) {

        for (TipoDocumentoEmbebidosDTO tipoEmbNuevo : this.tipoDocumento
            .getTipoDocumentoEmbebidos()) {
//          if (tipoEmbOri.getFormatoTamanoId().getFormato()
//              .equals(tipoEmbNuevo.getFormatoTamanoId().getFormato())) {
          if (tipoEmbOri.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato()
              .equals(tipoEmbNuevo.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato())) {
        		if (!tipoEmbOri.getSizeArchivoEmb().equals(tipoEmbNuevo.getSizeArchivoEmb())) {
              equalsFlag = false;
              break;
            }
            equalsFlag = true;
            break;
          }
        }
        if (!equalsFlag) {
          return true;
        }
        equalsFlag = false;
      }
    }
    return false;
  }

  public void volverDeModificar() {
    guardar.setVisible(false);
    modificarbtn.setVisible(true);

    textboxDescripcion.setReadonly(true);

    libre.setDisabled(true);
    importado.setDisabled(true);
    template.setDisabled(true);
    importadoTemplate.setDisabled(true);

    esEspecial.setDisabled(true);
    // tieneToken.setDisabled(true);
    esFirmaConjunta.setDisabled(true);
    esFirmaExterna.setDisabled(true);
    esFirmaExternaConEncabezado.setDisabled(true);
    esConfidencial.setDisabled(true);
    tieneAviso.setDisabled(true);
    permiteEmbebidos.setDisabled(true);
    esNotificable.setDisabled(true);
    esOculto.setDisabled(true);
    esPublicable.setDisabled(true);
    resultado.setDisabled(true);

    familia.setDisabled(true);

    documentoSadecb.setDisabled(true);

    cargarTemplate.setDisabled(true);
    tiposDeArchivo.setDisabled(true);
  }

  public void onModificar() throws InterruptedException {

    guardar.setVisible(true);
    guardar.setDisabled(false);

    modificarbtn.setVisible(false);
    textboxDescripcion.setReadonly(false);

    libre.setDisabled(false);
    importado.setDisabled(false);
    template.setDisabled(false);
    importadoTemplate.setDisabled(false);

    esEspecial.setDisabled(false);
    // tieneToken.setDisabled(false);
    esFirmaConjunta.setDisabled(false);
    esFirmaExterna.setDisabled(false);
    esFirmaExternaConEncabezado.setDisabled(false);
    esConfidencial.setDisabled(false);
    tieneAviso.setDisabled(false);
    permiteEmbebidos.setDisabled(false);
    esNotificable.setDisabled(false);
    esOculto.setDisabled(false);
    esPublicable.setDisabled(false);
    resultado.setDisabled(false);
    tamanioMax.setDisabled(false);
    ambosrd.setDisabled(false);
    automaticard.setDisabled(false);
    manualrd.setDisabled(false);

    familia.setDisabled(false);
    familia.setVisible(true);

    documentoSadecb.setDisabled(false);

    if (template.isChecked())
      cargarTemplate.setDisabled(false);

    if (this.tipoDocumento.getPermiteEmbebidos())
      tiposDeArchivo.setDisabled(false);
    if ((this.tipoDocumento.getTipoProduccion() == 3)
        || (this.tipoDocumento.getTipoProduccion() == 4)) {
      cargarTemplate.setDisabled(false);
    }

    this.esConfidencial.setDisabled(false);
    this.tieneAviso.setDisabled(false);
    this.esOculto.setDisabled(false);
  }

  public void onCheck$automaticard() {
    this.automatica = true;
    this.manual = false;
    this.ambos = false;
  }

  public void onCheck$manualrd() {
    this.manual = true;
    this.automatica = false;
    this.ambos = false;
  }

  public void onCheck$ambosrd() {
    this.automatica = true;
    this.manual = true;
  }

  public void onCheck$libre() {
    this.produccionLibre = true;
    this.produccionImportado = false;
    this.produccionImportadoTemplate = false;
    this.produccionTemplate = false;

    if (this.tipoDocumento.getPermiteEmbebidos()) {
      permiteEmbebidos.setChecked(true);
      tiposDeArchivo.setDisabled(false);
      this.tipoDocumento.setPermiteEmbebidos(true);
    } else {
      tiposDeArchivo.setDisabled(true);
      permiteEmbebidos.setChecked(false);
      this.tipoDocumento.setPermiteEmbebidos(false);
    }
    if (this.tipoDocumento.getListaTemplates().size() > 0) {
      this.tipoDocumento.getListaTemplates().clear();
    }

    this.filaTamanio.setVisible(false);

    permiteEmbebidos.setVisible(true);
    permiteEmbebidos.setDisabled(false);
    cargarTemplate.setDisabled(true);
    esFirmaExterna.setVisible(false);
    esFirmaExternaConEncabezado.setVisible(true);
    this.tipoDocumento.setTipoProduccion(1);
    this.tipoDocumento.setTieneTemplate(false);
  }

  public void onCheck$importado() {
    this.produccionImportado = true;
    this.produccionLibre = false;
    this.produccionImportadoTemplate = false;
    this.produccionTemplate = false;

    this.filaTamanio.setVisible(true);
    if (this.tipoDocumento.getSizeImportado() != null) {
      this.tamanioMax
          .setValue(this.tipoDocumento.getSizeImportado() / Constantes.FACTOR_CONVERSION);
    }

    cargarTemplate.setDisabled(true);
    esFirmaExterna.setVisible(true);
    esFirmaExternaConEncabezado.setVisible(false);
    permiteEmbebidos.setChecked(false);
    permiteEmbebidos.setDisabled(false);
    permiteEmbebidos.setVisible(true);
    this.tiposDeArchivo.setDisabled(true);
    this.tipoDocumento.setPermiteEmbebidos(false);
    this.tipoDocumento.setTipoProduccion(2);
    this.tipoDocumento.getTipoDocumentoEmbebidos().clear();
    this.tipoDocumento.setTieneTemplate(false);
  }

  public void onCheck$template() {
    this.produccionTemplate = true;
    this.produccionImportado = false;
    this.produccionLibre = false;
    this.produccionImportadoTemplate = false;

    this.filaTamanio.setVisible(false);

    cargarTemplate.setDisabled(false);
    esFirmaExterna.setVisible(false);
    esFirmaExternaConEncabezado.setVisible(false);
    permiteEmbebidos.setChecked(false);
    permiteEmbebidos.setDisabled(false);
    permiteEmbebidos.setVisible(true);
    permiteEmbebidos.setChecked(false);
    this.tiposDeArchivo.setDisabled(true);
    this.tipoDocumento.setPermiteEmbebidos(false);
    this.tipoDocumento.setTipoProduccion(3);
    this.tipoDocumento.getTipoDocumentoEmbebidos().clear();
    this.tipoDocumento.setTieneTemplate(true);
  }

  public void onCheck$importadoTemplate() {
    this.produccionImportadoTemplate = true;
    this.produccionTemplate = false;
    this.produccionImportado = false;
    this.produccionLibre = false;

    this.filaTamanio.setVisible(true);
    if (this.tipoDocumento.getSizeImportado() != null) {
      this.tamanioMax
          .setValue(this.tipoDocumento.getSizeImportado() / Constantes.FACTOR_CONVERSION);
    }

    cargarTemplate.setDisabled(false);
    esFirmaExterna.setVisible(false);
    esFirmaExternaConEncabezado.setVisible(false);
    permiteEmbebidos.setChecked(false);
    permiteEmbebidos.setDisabled(false);
    permiteEmbebidos.setVisible(true);
    tiposDeArchivo.setDisabled(true);
    this.tipoDocumento.setPermiteEmbebidos(false);
    this.tipoDocumento.setTipoProduccion(4);
    this.tipoDocumento.getTipoDocumentoEmbebidos().clear();
    this.tipoDocumento.setTieneTemplate(true);
  }

  public void onCheck$permiteEmbebidos() {
    if (this.permiteEmbebidos.isChecked()) {
      this.tiposDeArchivo.setDisabled(false);
      this.tipoDocumento.setPermiteEmbebidos(true);
    } else {
      this.tiposDeArchivo.setDisabled(true);
      this.tipoDocumento.setPermiteEmbebidos(false);
      if (tipoDocumento.getTipoDocumentoEmbebidos() != null
          && tipoDocumento.getTipoDocumentoEmbebidos().size() > 0) {
        this.tipoDocumento.getTipoDocumentoEmbebidos().clear();
      }
    }
  }

  public void onChanging$textboxDescripcion() {
    this.tipoDocumento.setDescripcion(this.textboxDescripcion.getValue());
  }

  public void onCheck$esFirmaExterna() {
    if (esFirmaExterna.isChecked()) {
      // tieneToken.setChecked(false);
      esFirmaConjunta.setChecked(false);
      this.tipoDocumento.setTieneToken(false);
      this.tipoDocumento.setEsDobleFactor(false);
      this.tipoDocumento.setEsFirmaConjunta(false);
      this.tipoDocumento.setEsFirmaExterna(true);
    } else {
      // tieneToken.setDisabled(false);
      esFirmaConjunta.setDisabled(false);
      this.tipoDocumento.setEsFirmaExterna(false);
    }
  }

  public void onCheck$esFirmaExternaConEncabezado() {
    if (esFirmaExternaConEncabezado.isChecked()) {
      this.tipoDocumento.setEsFirmaConjunta(false);
      this.tipoDocumento.setEsFirmaExternaConEncabezado(true);
    } else {
      esFirmaConjunta.setDisabled(false);
      this.tipoDocumento.setEsFirmaExternaConEncabezado(false);
    }
  }

  public void onCheck$esEspecial() {
    if (this.esEspecial.isChecked()) {
      this.tipoDocumento.setEsEspecial(true);
    } else {
      this.tipoDocumento.setEsEspecial(false);
    }
  }

  public void onCheck$esOculto() {
    if (this.esOculto.isChecked()) {
      this.tipoDocumento.setEsOculto(true);
    } else {
      this.tipoDocumento.setEsOculto(false);
    }
  }
  
  public void onCheck$resultado() {
    if (this.resultado.isChecked()) {
      this.tipoDocumento.setResultado(true);
    } else {
      this.tipoDocumento.setResultado(false);
    }
  }
  
  public void onCheck$tieneToken() {
	    if (this.tieneToken.isChecked()) {
		      this.tipoDocumento.setEsDobleFactor(false);
		      this.tipoDocumento.setTieneToken(true);
	    } else {
		      this.tipoDocumento.setTieneToken(false);
	    }
  }
  
  public void onCheck$esDobleFactor() {
	    if (this.esDobleFactor.isChecked()) {
	      this.tipoDocumento.setEsDobleFactor(true);
	      this.tipoDocumento.setTieneToken(false);
	    } else {
	      this.tipoDocumento.setEsDobleFactor(false);
	    }
  }

  public void onCheck$esPublicable() {
	    if (this.esPublicable.isChecked()) {
	      this.tipoDocumento.setEsPublicable(true);
	    } else {
	      this.tipoDocumento.setEsPublicable(false);
	    }
  }
  
  public void onCheck$esFirmaConjunta() {
    if (this.esFirmaConjunta.isChecked()) {
      this.tipoDocumento.setEsFirmaConjunta(true);
      this.esFirmaExterna.setChecked(false);
      this.esFirmaExternaConEncabezado.setChecked(false);

    } else {
      this.tipoDocumento.setEsFirmaConjunta(false);
    }
  }

  public void onCheck$esConfidencial() {
    if (this.esConfidencial.isChecked()) {
      this.tipoDocumento.setEsConfidencial(true);
    } else {
      this.tipoDocumento.setEsConfidencial(false);
    }
  }

  public void onCheck$tieneAviso() {
    if (this.tieneAviso.isChecked()) {
      this.tipoDocumento.setTieneAviso(true);
    } else {
      this.tipoDocumento.setTieneAviso(false);
    }
  }

  public void onCheck$esNotificable() {
    if (this.esNotificable.isChecked()) {
      this.tipoDocumento.setEsNotificable(true);
    } else {
      this.tipoDocumento.setEsNotificable(false);
    }
  }

  public void onClick$tiposDeArchivo() {

    try {
      HashMap<String, Object> map = new HashMap<>();
      map.put("tipoDocumento", this.tipoDocumento);
      this.extensionesPermitidasWindow = (Window) Executions
          .createComponents("/inbox/extensionesPermitdasTipoDoc.zul", this.self, map);
      this.extensionesPermitidasWindow.doModal();
    } catch (Exception e) {
      logger.error("Error", e);
    }
  }

  public void onSelect$familia() {
    this.selectedlistaFamilia = this.familiaTipoDocumentoService
        .traerFamiliaTipoDocumentoById(this.familia.getSelectedItem().getId());
    this.tipoDocumento.setFamilia(selectedlistaFamilia);
  }

  public void onSelect$documentoSadecb() {
    for (int i = 0; i < actuacionesSADE.size(); i++) {
      if (documentoSadecb.getValue().equals(
          actuacionesSADE.get(i).getCodigo() + " - " + actuacionesSADE.get(i).getDescripcion())) {
        selectedActuacionSADE = actuacionesSADE.get(i);
        this.tipoDocumento.setIdTipoDocumentoSade(selectedActuacionSADE.getId());
        this.tipoDocumento.setCodigoTipoDocumentoSade(selectedActuacionSADE.getCodigo());
        break;
      }
    }
  }

  public void onVerHistorial() {
    HashMap<String, Object> hm = new HashMap<>();
    hm.put("tipoDocumento", this.tipoDocumento);
    this.verHistorialWindow = (Window) Executions.createComponents("historialTipoDocumento.zul",
        this.self, hm);
    this.verHistorialWindow.setClosable(true);
    this.verHistorialWindow.doModal();
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public FamiliaTipoDocumentoDTO getSelectedlistaFamilia() {
    return selectedlistaFamilia;
  }

  public void setSelectedlistaFamilia(FamiliaTipoDocumentoDTO selectedlistaFamilia) {
    this.selectedlistaFamilia = selectedlistaFamilia;
  }

  public Combobox getFamilia() {
    return familia;
  }

  public void setFamilia(Combobox familia) {
    this.familia = familia;
  }

  public List<FamiliaTipoDocumentoDTO> getListaFamilia() {
    return listaFamilia;
  }

  public void setListaFamilia(List<FamiliaTipoDocumentoDTO> listaFamilia) {
    this.listaFamilia = listaFamilia;
  }

  public FamiliaTipoDocumentoDTO getSelectedNombreFamilia() {
    return selectedNombreFamilia;
  }

  public void setSelectedNombreFamilia(FamiliaTipoDocumentoDTO selectedNombreFamilia) {
    this.selectedNombreFamilia = selectedNombreFamilia;
  }

  public Boolean getProduccionLibre() {
    return produccionLibre;
  }

  public void setProduccionLibre(Boolean produccionLibre) {
    this.produccionLibre = produccionLibre;
  }

  public Boolean getProduccionImportado() {
    return produccionImportado;
  }

  public void setProduccionImportado(Boolean produccionImportado) {
    this.produccionImportado = produccionImportado;
  }

  public Boolean getProduccionTemplate() {
    return produccionTemplate;
  }

  public void setProduccionTemplate(Boolean produccionTemplate) {
    this.produccionTemplate = produccionTemplate;
  }

  public Boolean getProduccionImportadoTemplate() {
    return produccionImportadoTemplate;
  }

  public void setProduccionImportadoTemplate(Boolean produccionImportadoTemplate) {
    this.produccionImportadoTemplate = produccionImportadoTemplate;
  }

  public List<ActuacionSADEBean> getActuacionesSADE() {
    return actuacionesSADE;
  }

  public void setActuacionesSADE(List<ActuacionSADEBean> actuacionesSADE) {
    this.actuacionesSADE = actuacionesSADE;
  }

  public ActuacionSADEBean getSelectedActuacionSADE() {
    return selectedActuacionSADE;
  }

  public void setSelectedActuacionSADE(ActuacionSADEBean selectedActuacionSADE) {
    this.selectedActuacionSADE = selectedActuacionSADE;
  }

  public Window getAbmTipoDocumentoTemplateWindow() {
    return abmTipoDocumentoTemplateWindow;
  }

  public void setAbmTipoDocumentoTemplateWindow(Window abmTipoDocumentoTemplateWindow) {
    this.abmTipoDocumentoTemplateWindow = abmTipoDocumentoTemplateWindow;
  }

  public Button getCargarTemplate() {
    return cargarTemplate;
  }

  public void setCargarTemplate(Button cargarTemplate) {
    this.cargarTemplate = cargarTemplate;
  }

  public String getIdFormularioControlado() {
    return idFormularioControlado;
  }

  public void setIdFormularioControlado(String idFormularioControlado) {
    this.idFormularioControlado = idFormularioControlado;
  }

  public String getDescripcionTemplate() {
    return descripcionTemplate;
  }

  public void setDescripcionTemplate(String descripcionTemplate) {
    this.descripcionTemplate = descripcionTemplate;
  }

  public String getValueCkeditor() {
    return valueCkeditor;
  }

  public void setValueCkeditor(String valueCkeditor) {
    this.valueCkeditor = valueCkeditor;
  }

  public TipoDocumentoEmbebidosDTO getTipoDocumentoEmbebidos() {
    return tipoDocumentoEmbebidos;
  }

  public void setTipoDocumentoEmbebidos(TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos) {
    this.tipoDocumentoEmbebidos = tipoDocumentoEmbebidos;
  }

  public boolean isAmbos() {
    return ambos;
  }

  public void setAmbos(boolean ambos) {
    this.ambos = ambos;
  }

  public boolean isAutomatica() {
    return automatica;
  }

  public void setAutomatica(boolean automatica) {
    this.automatica = automatica;
  }

  public boolean isManual() {
    return manual;
  }

  public void setManual(boolean manual) {
    this.manual = manual;
  }

  public void setEsOculto(Checkbox esOculto) {
    this.esOculto = esOculto;
  }

  public Checkbox getEsOculto() {
    return esOculto;
  }

  public Window getVerHistorialWindow() {
    return verHistorialWindow;
  }

  public void setVerHistorialWindow(Window verHistorialWindow) {
    this.verHistorialWindow = verHistorialWindow;
  }
  
  public Checkbox getResultado() {
    return resultado;
  }

  public void setResultado(Checkbox resultado) {
    this.resultado = resultado;
  }

  private class TamanioConstraint implements Constraint {
    // Constraint//
    public void validate(Component comp, Object value) {
      if (value == null) {
        throw new WrongValueException(comp,
            Labels.getLabel("gedo.crearTipoDocComposer.exception.vacioNoPermitido"));
      } else if (((Integer) value).intValue() <= 0) {
        throw new WrongValueException(comp,
            Labels.getLabel("gedo.detalleDocumento.exception.valorNoNegativo"));
      } else if (((Integer) value).intValue() > tamanioMaximo) {
        throw new WrongValueException(comp,
            Labels.getLabel("gedo.crearTipoDocComposer.exception.valorMenorMaximo") + "("
                + tamanioMaximo + ")");

      }
    }
  }

  public String getDetalle() {
    return detalle;
  }

  public void setDetalle(String detalle) {
    this.detalle = detalle;
  }

  final class DetalleTipoDocumentoComposerListener implements EventListener {
    private DetalleTipoDocumentoComposer composer;

    public DetalleTipoDocumentoComposerListener(DetalleTipoDocumentoComposer comp) {
      this.composer = comp;
    }

    @SuppressWarnings("unchecked")
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          Map<String, Object> map = (Map<String, Object>) event.getData();
          String origen = (String) map.get("origen");
          if (origen.equals(Constantes.EVENTO_ABM_DOCUMENTO_TEMPLATE)) {
            composer.setValueCkeditor((String) map.get("datos"));
            composer.setIdFormularioControlado((String) map.get("idFormularioControlado"));
            composer.setDescripcionTemplate((String) map.get("descripcionTemplate"));

            idFormularioControlado = composer.getIdFormularioControlado();
            descripcionTemplate = composer.getDescripcionTemplate();
            valueCkeditor = composer.getValueCkeditor();

          }
        }
      }
    }
  }
}
