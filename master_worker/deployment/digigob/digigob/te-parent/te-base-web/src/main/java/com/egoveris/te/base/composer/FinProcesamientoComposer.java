package com.egoveris.te.base.composer;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.DocumentoService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.ISolrService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.DocumentoCaratulaGedoTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FinProcesamientoComposer extends EEGenericForwardComposer {

  private final static Logger logger = LoggerFactory.getLogger(FinProcesamientoComposer.class);

  private static final long serialVersionUID = 1968714188431815192L;

  private Window descargaWindow;
  private Label informacionVentanaPadre;
  private String numeroDocumento;
  private String mensajeVentanaPadre;
  private String titulo;
  private Row buttonsRow;
  private Row buttonRow;

  private IExternalGenerarDocumentoService generarDocumentoService;

  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService eEService;

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private DocumentoService documentoService;

  @WireVariable(ConstantesServicios.SOLR_SERVICE)
  private ISolrService solrService;

  @WireVariable(ConstantesServicios.DOC_CARATULA_GEDO_TEMPLATE)
  private DocumentoCaratulaGedoTemplate documentoCaratulaGedoTemplate;
  
  private ExpedienteElectronicoDTO ee;
  private RequestExternalGenerarDocumento request;
  private String sade = "";
  private TipoDocumentoDTO tipoDocumento;
  private IngresoSolicitudExpedienteDTO solicitud;
  private boolean beforeExecuteTask;
  private boolean fromGenerarNuevoExpediente;
  private boolean emptyForm;
  private Window taskView;
  private Window envio;
  private Window motivoGuardaTemporalWindow;
  private Window formularioControlado;
  private InboxComposer inboxComposer;

  private static final String PENDIENTE_INICIACION = "Pendiente Iniciacion";
  private static final String INICIACION = "Iniciacion";
  private static String CARATULA_VARIABLE = "CARATULA";

  Integer idTransaccion;

  private boolean fromCancelar;

  private Map eventData;

  private Button procesar;

  public Map getEventData() {
    return eventData;
  }

  public void setEventData(Map eventData) {
    this.eventData = eventData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.zkoss.zk.ui.util.GenericForwardComposer#doAfterCompose(org.zkoss.zk.ui.
   * Component)
   */
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    Map map = (Map) Executions.getCurrent().getArg();
    HashMap<String, Object> mensajes = (HashMap<String, Object>) component.getDesktop()
        .getAttribute("mensajes");

    idTransaccion = (Integer) Executions.getCurrent().getArg().get("idTransaccion");

    String formComp = (String) Executions.getCurrent().getArg().get("nombreFormulario");
    this.setSade((String) Executions.getCurrent().getArg().get("numeroSade"));
    this.setEe((ExpedienteElectronicoDTO) Executions.getCurrent().getArg().get("expElect"));
    this.setTipoDocumento((TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDoc"));
    setSolicitud(
        (IngresoSolicitudExpedienteDTO) Executions.getCurrent().getArg().get("solicitud"));
    this.setBeforeExecuteTask(
        (Boolean) Executions.getCurrent().getArg().get("doBeforeExecuteTask"));
    this.setInboxComposer((InboxComposer) Executions.getCurrent().getArg().get("inboxComposer"));

    this.setFromCancelar((Boolean) Executions.getCurrent().getArg().get("fromCancelar"));

    this.setBeforeExecuteTask((Boolean) Executions.getCurrent().getArg().get("beforeExecuteTask"));

    this.setEventData((Map) Executions.getCurrent().getArg().get("eventData"));

    if (null != (Boolean) Executions.getCurrent().getArg().get("fromGenerarNuevoExpediente")) {
      this.setFromGenerarNuevoExpediente(
          (Boolean) Executions.getCurrent().getArg().get("fromGenerarNuevoExpediente"));
    }
  }

  public boolean isFromCancelar() {
    return fromCancelar;
  }

  public void setFromCancelar(boolean fromCancelar) {
    this.fromCancelar = fromCancelar;
  }

  /**
   * Permite descargar el documento.
   * 
   * @throws InterruptedException
   */

  public void onClick$procesar() throws InterruptedException {
    try {
      procesar.setDisabled(true);
      if (this.getBeforeExecuteTask()) {
        // Continuar con ejecucion expediente.
        this.guardarExpedienteElectronico(fromCancelar, eventData);
      } else {
        // Volver a inbox
        this.guardarExpedienteElectronico(fromCancelar, eventData);
      }
    } catch (ParametroInvalidoException e) {
      try {
        throw new ParametroInvalidoException(e.getMessage());
      } catch (ParametroInvalidoException e1) {
        logger.error(e.getMessage());
      }
    }
  }

  public int guardarExpedienteElectronico(boolean fromCancelar, Map<String, Object> eventData)
      throws ParametroInvalidoException {

    Map<String, Object> camposFc = new HashMap<String, Object>();
    try {

      // CU01 - Flujo Alternativo: El usuario no completa el formulario
      // controlado
      if (fromCancelar) {

        if (!this.getBeforeExecuteTask()) {
          eventData.put("beforeExecuteTask", Boolean.FALSE);
          eventData.put("msg", "Se generó el expediente: " + this.getEe().getCodigoCaratula());
        } else {
          // se esta ejecutando el ee
          eventData.put("beforeExecuteTask", Boolean.TRUE);
        }

        // 8. El sistema deja el expedienteElectronico inhabilitado para ser
        // tramitado.
        // 9. Avisar al usuario que el EE quedara bloqueado.
        this.getEe().setEstado(PENDIENTE_INICIACION);
        this.registrarExpedienteElectronico(this.getEe());

        if (this.getInboxComposer() == null) {
          eventData.put("origen", ConstantesWeb.COMPOSER);
          eventData.put("fulfilledForm", Boolean.FALSE);
          eventData.put("msg", "Se generó el expediente: " + this.getEe().getCodigoCaratula()
              + " y quedará pendiente de iniciación hasta que el formulario controlado asociado sea completado.");
        }
        eventData.put(Events.ON_CANCEL, Boolean.TRUE);
        super.closeAndNotifyAssociatedWindow(eventData);

        return 0;
      }
      // CU01 - 9. Habilitar expedienteElectronico para ser tramitado

      request = this.armarRequestParaGenerarCaratulaEnGedo(this.getEe(), this.getSolicitud(),
          idTransaccion);

      // CU01 - 8. Asociar al expedienteElectronico un nuevo documento
      // GEDO que contiene los datos cargados en el formulario controlado.
      try {

        ResponseExternalGenerarDocumento response;

        try {
          response = generarDocumentoService.generarDocumentoGEDO(request);

          if (response == null) {
            throw new TeRuntimeException("Se produjo un error al generar la caratula", null);
          }

          logger.debug("Url archivo generado: " + response.getUrlArchivoGenerado().toString());

          DocumentoDTO documento = generarDocumento(this.getEe(), this.getEe().getUsuarioCreador(),
              response);
          documento.setTipoDocAcronimo(request.getAcronimoTipoDocumento());

          // Cuando se vincula el documento GEDO del formulario controlado vamos
          // a tener que guardar
          // el id de transacción en la BD de EE en la tabla Documentos.
          documento.setIdTransaccionFC(idTransaccion.longValue());

          this.getEe().getDocumentos().add(documento);
          this.getEe().setEstado(INICIACION);

        } catch (CantidadDatosNoSoportadaException e) {

          logger.error(e.getMessage());
        } catch (ErrorGeneracionDocumentoException e) {

          logger.error(e.getMessage());
        } catch (ClavesFaltantesException e) {

          logger.error(e.getMessage());
        }

      } catch (ParametroInvalidoException piExc) {
        logger.error("Error en validación del contenido del documento con referencia: "
            + request.getReferencia() + ", " + piExc.getMessage());

        eventData.put("origen", ConstantesWeb.COMPOSER);
        eventData.put("noPermitido", Boolean.TRUE);
        eventData.put("fulfilledForm", Boolean.FALSE);
        eventData.put("beforeExecuteTask", Boolean.FALSE);
        eventData.put("fromGenerarNuevoExpediente", Boolean.FALSE);
        eventData.put("acronimo", request.getAcronimoTipoDocumento());
        eventData.put("msg",
            "Su reparticion no esta habilitada para caratular un expediente electronico con el tipo de documento que posee acronimo: "
                + request.getAcronimoTipoDocumento()
                + " el cual se encuentra asociado a la trata.");

        super.closeAndNotifyAssociatedWindow(eventData);
        return 0;
      }

      if (!this.getBeforeExecuteTask()) {
        eventData.put("fulfilledForm", Boolean.TRUE);
        eventData.put("beforeExecuteTask", Boolean.FALSE);
        eventData.put("fromGenerarNuevoExpediente", Boolean.TRUE);
        eventData.put("msg", "Se generó el expediente: " + this.getEe().getCodigoCaratula());
      } else {

        eventData.put("fulfilledForm", Boolean.TRUE);
        eventData.put("beforeExecuteTask", Boolean.TRUE);

      }

      eventData.put(Events.ON_CLOSE, Boolean.TRUE);

      eventData.put("origen", ConstantesWeb.COMPOSER);
      this.registrarExpedienteElectronico(this.getEe());

      super.closeAndNotifyAssociatedWindow(eventData);

      solrService.indexarFormularioControlado(this.getEe(), camposFc);

    } catch (NegocioException e) {
      logger.error("Se produjo un error indexando los campos del formulario controlado.");
      logger.error(e.getMessage());
      throw new TeRuntimeException(
          "Se produjo un error indexando los campos del formulario controlado.", e);
    }

    return 1;
  }

  private RequestExternalGenerarDocumento armarRequestParaGenerarCaratulaEnGedo(
      ExpedienteElectronicoDTO ee, IngresoSolicitudExpedienteDTO solicitud, Integer idTrans)
      throws RuntimeException {

    TipoDocumentoDTO tipoDocumentoCaratulaDeEE;

    tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
        .buscarTipoDocumentoByUso(CARATULA_VARIABLE);

    if (tipoDocumentoCaratulaDeEE == null) {
      throw new TeRuntimeException(
          "El administrador debe seleccionar un documento Caratula para poder generar el trámite.",
          null);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    request.setData(documentoCaratulaGedoTemplate.generarCaratulaComoByteArray(ee, solicitud.getSolicitudExpediente()));
    request.setReferencia("Carátula Variable " + ee.getCodigoCaratula());
    request.setIdTransaccion(idTrans);
    request.setAcronimoTipoDocumento(this.getTipoDocumento().getAcronimo());
    request.setUsuario(ee.getUsuarioCreador());
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    return request;
  }

  private DocumentoDTO generarDocumento(ExpedienteElectronicoDTO ee, String usuarioCreador,
      ResponseExternalGenerarDocumento response) {

    DocumentoDTO documento = new DocumentoDTO();
    documento.setFechaCreacion(ee.getFechaCreacion());
    documento.setFechaAsociacion(new Date());
    documento.setNombreUsuarioGenerador(usuarioCreador);
    documento.setDefinitivo(true);
    // TODO revisar necesidad de almacenar nombre de archivo tal como se hace
    // aquí (sin ruta absoluta, asumiendo .pdf)
    documento.setNombreArchivo(response.getNumero() + ".pdf");
    documento.setNumeroSade(response.getNumero());
    documento.setUsuarioAsociador(usuarioCreador);
    if (ee.getFechaModificacion() == null) {
      documento.setMotivo(ConstantesWeb.MOTIVO_CARATULA);
    } else {
      documento.setMotivo(ConstantesWeb.MOTIVO_MODIFICACION_CARATULA);
    }
    return documento;
  }

  public void onCancelar() {

    boolean fromCancelar = Boolean.TRUE;
    Map eventData = new HashMap();
    this.getEe().setEstado(PENDIENTE_INICIACION);

    try {
      this.guardarExpedienteElectronico(fromCancelar, eventData);
    } catch (ParametroInvalidoException e) {

      logger.error(e.getMessage());
    }
  }

  /**
   * Cerrar la ventana que informa si el proceso fue exitoso.
   * 
   * @throws InterruptedException
   */
  public void onClick$cerrar() throws InterruptedException {
    super.closeAndNotifyAssociatedWindow(null);
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public String getMensajeVentanaPadre() {
    return mensajeVentanaPadre;
  }

  public void setMensajeVentanaPadre(String mensajeVentanaPadre) {
    this.mensajeVentanaPadre = mensajeVentanaPadre;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public IExternalGenerarDocumentoService getGenerarDocumentoService() {
    return this.generarDocumentoService;
  }

  public void setGenerarDocumentoService(
      IExternalGenerarDocumentoService generarDocumentoService) {
    this.generarDocumentoService = generarDocumentoService;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return this.tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  public ExpedienteElectronicoService geteEService() {
    return this.eEService;
  }

  public void seteEService(ExpedienteElectronicoService eEService) {
    this.eEService = eEService;
  }

  public ExpedienteElectronicoDTO getEe() {
    return this.ee;
  }

  public void setEe(ExpedienteElectronicoDTO ee) {
    this.ee = ee;
  }

  public RequestExternalGenerarDocumento getRequest() {
    return this.request;
  }

  public void setRequest(RequestExternalGenerarDocumento request) {
    this.request = request;
  }

  public String getSade() {
    return this.sade;
  }

  public void setSade(String sade) {
    this.sade = sade;
  }

  public IngresoSolicitudExpedienteDTO getSolicitud() {
    return this.solicitud;
  }

  public void setSolicitud(IngresoSolicitudExpedienteDTO solicitud) {
    this.solicitud = solicitud;
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return this.tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public boolean getBeforeExecuteTask() {
    return this.beforeExecuteTask;
  }

  public void setBeforeExecuteTask(boolean beforeExecuteTask) {
    this.beforeExecuteTask = beforeExecuteTask;
  }

  public Window getTaskView() {
    return taskView;
  }

  public void setTaskView(Window taskView) {
    this.taskView = taskView;
  }

  public InboxComposer getInboxComposer() {
    return inboxComposer;
  }

  public void setInboxComposer(InboxComposer inboxComposer) {
    this.inboxComposer = inboxComposer;
  }

  public DocumentoService getDocumentoService() {
    return this.documentoService;
  }

  public void setDocumentoService(DocumentoService documentoService) {
    this.documentoService = documentoService;
  }

  public boolean getEmptyForm() {
    return this.emptyForm;
  }

  public void setEmptyForm(boolean emptyForm) {
    this.emptyForm = emptyForm;
  }

  public boolean getFromGenerarNuevoExpediente() {
    return this.fromGenerarNuevoExpediente;
  }

  public void setFromGenerarNuevoExpediente(boolean fromGenerarNuevoExpediente) {
    this.fromGenerarNuevoExpediente = fromGenerarNuevoExpediente;
  }

  private void registrarExpedienteElectronico(ExpedienteElectronicoDTO ee) {
    eEService.grabarExpedienteElectronico(ee);
  }

  public void mostrarCaratulaGenerada(ExpedienteElectronicoDTO expedienteElectronico)
      throws InterruptedException {
    Messagebox.show(Labels.getLabel("ee.caratularExtComp.msgbox.seGeneroExp") + " " + this.getEe().getCodigoCaratula(),
        Labels.getLabel("ee.tramitacion.titulo.nuevoExpediente"), Messagebox.OK,
        Messagebox.INFORMATION);
  }

  public void setProcesar(Button procesar) {
    this.procesar = procesar;
  }

  public Button getProcesar() {
    return procesar;
  }

}
