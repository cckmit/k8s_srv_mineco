package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.DocumentoAdjuntoService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.firma.FirmaDocumentoGenericComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.shareddocument.base.exception.AlfrescoException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
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
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VerificarDocumentoComposer extends FirmaDocumentoGenericComposer {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(VerificarDocumentoComposer.class);

  private static final String IMG_MAIL_SIN_MENSAJE = "/imagenes/email.png";
  private static final String IMG_MAIL_CON_MENSAJE = "/imagenes/ANIMADO_iconosobre.gif";

  protected Window verificarDocumentoWindow;
  protected Window archivosDeTrabajoWindow;
  protected Window datosPropiosWindow;
  protected Button previsualizar;
  protected Combobox usuarioRevisor;
  protected Textbox mensajeARevisor;
  protected Textbox usuariosAgregadosTxt;
  protected Textbox revisoresAgregados;
  protected Button datosPropios;
  protected Button volver;
  protected Cell cellMotivo;
  protected Image iconoFirmaConjunta;
  protected Image iconoRevisoresFirmaConjunta;
  protected Button selfFirmaConjuntaButton;
  protected Button historialDocumento;
  protected Row enviarFirmaRow;

  @WireVariable("historialServiceImpl")
  protected HistorialService historialService;
  @WireVariable("tipoDocumentoServiceImpl")
  protected TipoDocumentoService tipoDocumentoService;
  @WireVariable("firmaConjuntaServiceImpl")
  protected FirmaConjuntaService firmaConjuntaService;
  @WireVariable("archivoDeTrabajoServiceImpl")
  protected ArchivoDeTrabajoService archivoDeTrabajoService;
  @WireVariable("generarDocumentoServiceImpl")
  protected GenerarDocumentoService generarDocumentoService;
  @WireVariable("buscarDocumentosGedoServiceImpl")
  protected BuscarDocumentosGedoService buscarDocumentosGedoService;
  @WireVariable("documentoAdjuntoServiceImpl")
  protected DocumentoAdjuntoService documentoAdjuntoService;
  @WireVariable("usuarioServiceImpl")
  protected IUsuarioService usuarioService;

  protected List<Usuario> usuariosFirmantes;
  protected AnnotateDataBinder verificarDocumentoBinder;
  protected List<DocumentoMetadataDTO> listaDocMetadata;
  protected List<ArchivoDeTrabajoDTO> listaArchivosDeTrabajo = null;

  private FirmanteDTO firmante;

  protected String usuarioDerivador;
  protected String tareaAsignada;
  protected String mensajeArchivosDeTrabajo;

  private boolean firmoYoMismo;
  private byte[] dataFile;
  private Label nombreArchivoLabel;
  private String pathFileTemporal;
  private String mensajeRevisor;

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    component.addEventListener(Events.ON_NOTIFY, new VerificarDocumentoComposerListener(this));
    component.addEventListener(Events.ON_USER, new VerificarDocumentoComposerListener(this));

    this.cargarVariablesInstancia();

    this.usuarioRevisor.setModel(
        ListModels.toListSubModel(new ListModelList(this.getUsuarioService().obtenerUsuarios()),
            new UsuariosComparator(), 30));

    this.self.setAttribute("dontAskBeforeClose", true);

    listaArchivosDeTrabajo = archivoDeTrabajoService
        .buscarArchivosDeTrabajoPorProceso(this.workingTask.getExecutionId());

    this.armadoDePantalla();

    this.verificarDocumentoBinder = new AnnotateDataBinder(component);
    this.verificarDocumentoBinder.loadAll();

  }

  /**
   * Carga valores desde las variables almacenadas en el Workflow
   */
  private void cargarVariablesInstancia() {
    super.acronimo = (String) super.getVariableWorkFlow(Constantes.VAR_TIPO_DOCUMENTO);
    super.tipoDocumento = super.tipoDocumentoService
        .buscarTipoDocumentoPorId(Integer.valueOf(this.acronimo));
    this.usuarioDerivador = (String) this.processEngine.getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_DERIVADOR);

    if (this.usuarioDerivador == null) {
      this.usuarioDerivador = (String) this.processEngine.getExecutionService()
          .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_PRODUCTOR);
      this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(),
          Constantes.VAR_USUARIO_DERIVADOR, this.usuarioDerivador);
    }

    super.nombreArchivoTemporal = (String) super.getVariableWorkFlow(
        Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
    try {
//      super.idGuardaDocumentalTemporal = (String) super.getVariableWorkFlow(
//          Constantes.VAR_ID_GUARDA_DOCUMENTAL);
    } catch (VariableWorkFlowNoExisteException ex) {
      logger.error("Error al cargar las variables de instancia de la tarea " + ex.getMessage(),
          ex);
      super.idGuardaDocumentalTemporal = null;
    }

  }

  /**
   * Verifica si el tipo de documento tiene datos propios, y carga los valores
   * en la vista.
   */
  @SuppressWarnings("unchecked")
  public void cargarDatosPropios() {
    List<DocumentoMetadataDTO> listaDocumentoMetadatos = null;
    try {
      listaDocumentoMetadatos = (List<DocumentoMetadataDTO>) this
          .getVariableWorkFlow(Constantes.VAR_DOCUMENTO_DATA);
    } catch (VariableWorkFlowNoExisteException ve) {
      logger.error("Error al cargar datos propios de la tarea " + ve.getMessage(), ve);
      listaDocumentoMetadatos = new ArrayList<DocumentoMetadataDTO>();
    }

    if (listaDocumentoMetadatos != null && listaDocumentoMetadatos.size() > 0) {
      this.datosPropios.setDisabled(false);
      this.listaDocMetadata = listaDocumentoMetadatos;
    } else {
      this.datosPropios.setDisabled(true);
      this.listaDocMetadata = new ArrayList<DocumentoMetadataDTO>();
    }
  }

  /**
   * Realiza las actividades necesarias para enviar a firmar el documento.
   * 
   * @param usuarioActual
   *          : Flag que indica si se envía a firmar a otro usuario o lo firma
   *          el usuario acutal.
   * @throws InterruptedException
   */
  private void enviarAFirmar(boolean usuarioActual) throws InterruptedException {

    String userIdRevisor;
    FirmanteDTO usuarioFirmante=null;

    try {

      this.setVariableWorkFlow(Constantes.VAR_SOLICITUD_ENVIO_MAIL,
          solicitudEnvioCorreo.isChecked());

      if (super.tipoDocumento.getEsFirmaConjunta()) {
        String usuarioBuscar = getCurrentUser();
        String usuarioApoderador = (String) processEngine.getExecutionService()
            .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR);

        if (usuarioApoderador != null) {
          if (this.usuarioService.licenciaActiva(usuarioApoderador, new Date())) {
            usuarioBuscar = usuarioApoderador;
          }
        }

        usuarioFirmante = this.firmaConjuntaService.buscarRevisor(usuarioBuscar,
            this.getWorkingTask().getExecutionId(), false);

        
        String userFirm = getCurrentUser();
		String useRev = getCurrentUser();
		List<FirmanteDTO> firmantes = this.firmaConjuntaService.buscarRevisorFirmante(this.getWorkingTask().getExecutionId());
		for(FirmanteDTO firm : firmantes) {
			
			if(!(firm.getEstadoFirma() )) {
				if(firm.getEstadoFirma()) {
					useRev = firm.getUsuarioRevisor();
					userFirm = firm.getUsuarioRevisor();
					break;
				}else if(!firm.getEstadoFirma()) {
					useRev = firm.getUsuarioRevisor();
					userFirm = firm.getUsuarioFirmante();
					break;
				}
			}
			

		}
		setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, useRev);
		setVariableWorkFlow(Constantes.VAR_USUARIO_FIRMANTE, useRev);
		
      if (useRev != null) {
	      userIdRevisor = useRev;
	      if (userIdRevisor != null) {
        	this.firmaConjuntaService.actualizarRevisor(userIdRevisor, true, this.getWorkingTask().getExecutionId());
	      }
	    }
//        if (usuarioFirmante != null) {
//          userIdRevisor = usuarioFirmante.getUsuarioRevisor();
//          if (userIdRevisor != null) {
//            if (userIdRevisor.equals(usuarioBuscar)) {
//              this.actualizarFirmaConjunta();
//            }
//          }
//        }

        this.mostrarMensajeEnvioMail();
        usuarioFirmante = this.firmaConjuntaService.buscarRevisor(usuarioBuscar,
                this.getWorkingTask().getExecutionId(), true);
      }

      this.actualizarEstadoAvisoFirma();
      this.restaurarVentanaFirma();
      this.historialService.actualizarHistorial(this.workingTask.getExecutionId());
      this.workingTask.setProgress(100);
      String usuarioFirm=getCurrentUser();
      if (super.tipoDocumento.getEsFirmaConjunta() && usuarioFirmante!=null) {
    	  usuarioFirm=usuarioFirmante.getUsuarioRevisor();
      }
//		setVariableWorkFlow(Constantes.REVISAR_DOCUMENTO_CON_FIRMA_CONJUNTA, true);
		setVariableWorkFlow(Constantes.REVISAR_FIRMA, true);
		setVariableWorkFlow(Constantes.REVISAR_DOCUMENTO_CON_CERTIFICADO, false);
		
      this.signalExecution(Constantes.TRANSICION_FIRMA_PENDIENTE, usuarioFirm);

    } catch (Exception e) {
      logger.error("Error al enviar a firmar en circuito de revision. Error: " + e.getMessage(),
          e);
      Messagebox.show(Labels.getLabel("gedo.error.enviarAFirmar"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    } finally {
      Clients.clearBusy();
      super.closeAndNotifyAssociatedWindow(null);
    }
  }

  /**
   * Actualiza el revisor de la tabla de firmantes, para los tipos de documento
   * que requieren firma conjunta.
   */
  private void actualizarFirmaConjunta() throws InterruptedException {

    try {
      String usuarioApoderador = (String) processEngine.getExecutionService()
          .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR);
      String usuarioRevisorBuscar = StringUtils.EMPTY;

      if (StringUtils.isEmpty(usuarioApoderador)) {
        usuarioRevisorBuscar = getCurrentUser();
      } else {
        usuarioRevisorBuscar = usuarioApoderador;
      }

      firmaConjuntaService.actualizarRevisor(usuarioRevisorBuscar, true,
          this.workingTask.getExecutionId());

    } catch (Exception e) {
      logger.error("Error actualizando usuario revisor asociado al firmante " + e, e);
      Messagebox.show(Labels.getLabel("gedo.firmarDocumento.errores.fallaActualizandoFirmante"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Realiza las validaciones requeridas para iniciar el proceso de firma
   * conjunta.
   * 
   * @throws Exception
   */
  public void onClick$selfFirmaConjuntaButton() throws Exception {

    // Validación de datos propios obligatorios del tipo de documento.
    if (this.listaDocMetadata != null && this.listaDocMetadata.size() > 0) {
      for (DocumentoMetadataDTO doc : this.listaDocMetadata) {
        if (doc.isObligatoriedad() && StringUtils.isEmpty(doc.getValor())) {
          Messagebox.show(
              Labels.getLabel("gedo.iniciarDocumento.errores.faltaDatosPropiosObligatorio",
                  new String[] { this.tipoDocumento.getAcronimo() }),
              Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
          return;
        }
      }
    }
    enviarAFirmar(false);
  }

  public void onClick$volver() {
    this.closeAndNotifyAssociatedWindow(null);
  }

  public PdfService getPdfService() {
    return pdfService;
  }

  public void setPdfService(PdfService pdfService) {
    this.pdfService = pdfService;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public GestionArchivosWebDavService getGestionArchivosWebDavService() {
    return gestionArchivosWebDavService;
  }

  public void setGestionArchivosWebDavService(
      GestionArchivosWebDavService gestionArchivosWebDavService) {
    this.gestionArchivosWebDavService = gestionArchivosWebDavService;
  }

  /**
   * Actualiza el textbox que permite mostrar la lista actual de usuarios
   * firmantes.
   * 
   * @param usuariosFirmaConjunta
   */
  protected void refrescarUsuariosFirmantes() {
    if (this.usuariosFirmantes.size() != 0) {
      this.usuariosAgregadosTxt.setMultiline(true);
      this.revisoresAgregados.setMultiline(true);
      StringBuilder listaUsuarios = new StringBuilder();
      StringBuilder listaRevisores = new StringBuilder();
      for (Usuario datosUsuario : this.usuariosFirmantes) {
        listaUsuarios.append(datosUsuario.getNombreApellido());
        if (this.usuariosFirmantes.indexOf(datosUsuario) == this.usuariosFirmantes.size() - 1) {
          listaUsuarios.append("(" + datosUsuario.getUsername() + ")");
        } else {
          listaUsuarios.append("(" + datosUsuario.getUsername() + "),\n");
        }



      }

      List<Usuario> usuariosRevisores = firmaConjuntaService
          .buscarRevisoresPorProceso(this.workingTask.getExecutionId());
      for (Usuario usuarioRevisor : usuariosRevisores) {
        listaRevisores.append(usuarioRevisor.getNombreApellido());
        if (usuariosRevisores.indexOf(usuarioRevisor) == usuariosRevisores.size() - 1) {
          listaRevisores.append("(" + usuarioRevisor.getUsername() + ")");
        } else {
          listaRevisores.append("(" + usuarioRevisor.getUsername() + "),\n");
        }

      }

      this.usuariosAgregadosTxt.setValue(listaUsuarios.toString());
      this.revisoresAgregados.setValue(listaRevisores.toString());
      this.usuariosAgregadosTxt.setRows(this.usuariosFirmantes.size() + 1);
      this.revisoresAgregados.setRows(this.usuariosFirmantes.size() + 1);
    } else {
      this.usuariosAgregadosTxt.setValue(Labels.getLabel("gedo.general.noUsuariosFirmantes"));
      this.revisoresAgregados.setValue(Labels.getLabel("gedo.general.noUsuariosRevisores"));
      this.verificarDocumentoBinder.loadComponent(this.revisoresAgregados);
      this.verificarDocumentoBinder.loadComponent(this.usuariosAgregadosTxt);
    }
  }

  public void setUsuariosFirmantes(List<Usuario> usuariosFirmantes) {
    this.usuariosFirmantes = usuariosFirmantes;
  }

  protected List<Usuario> getUsuariosFirmantes() {
    this.usuariosFirmantes = firmaConjuntaService
        .buscarFirmantesPorProceso(this.workingTask.getExecutionId());
    return this.usuariosFirmantes;
  }

  /**
   * Invoca la ventana para modificar los datos propios de un documento.
   */
  public void onClick$datosPropios() {
    Map<String, Object> hm = new HashMap<String, Object>();
    hm.put(DatosPropiosDocumentoComposer.METADATA, this.listaDocMetadata);
    hm.put(DatosPropiosDocumentoComposer.HABILITAR_PANTALLA, Boolean.FALSE);
    hm.put(DatosPropiosDocumentoComposer.HABILITAR_EDICION, Boolean.FALSE);
    this.datosPropiosWindow = (Window) Executions
        .createComponents("/inbox/datosPropiosDelDocumento.zul", this.self, hm);
    this.datosPropiosWindow.setParent(this.getVentanaPadre());
    this.datosPropiosWindow.setClosable(true);
    this.datosPropiosWindow.setTitle(Labels.getLabel("gedo.iniciarDocumento.label.datosPropios"));
    this.datosPropiosWindow.setHeight("446px");

    this.datosPropiosWindow.doModal();
  }

  @Override
  protected void asignarTarea() throws InterruptedException {
    if (this.tareaAsignada.compareTo(Constantes.TASK_REVISAR_DOCUMENTO) == 0)
      this.enviarARevisar();
    if (this.tareaAsignada.compareTo(Constantes.TASK_FIRMAR_DOCUMENTO) == 0)
      this.enviarAFirmar(firmoYoMismo);
  }

  @Override
  protected void enviarBloqueoPantalla(Object data) {
    Clients.showBusy(Labels.getLabel("gedo.general.procesando.procesandoSolicitud"));
    Events.echoEvent("onUser", this.self, data);
  }

  private void mostrarMensajeEnvioMail() throws InterruptedException {
    Boolean falloEnvioSolicitudMail = (Boolean) this.processEngine.getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_SOLICITUD_ENVIO_MAIL_FAIL);
    if (falloEnvioSolicitudMail != null && falloEnvioSolicitudMail) {
      Messagebox.show(Labels.getLabel("gedo.error.enviarCorreo"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

  public Image getIconoFirmaConjunta() {
    return iconoFirmaConjunta;
  }

  public void setIconoFirmaConjunta(Image iconoFirmaConjunta) {
    this.iconoFirmaConjunta = iconoFirmaConjunta;
  }

  public String getMensajeArchivosDeTrabajo() {
    return mensajeArchivosDeTrabajo;
  }

  public void setMensajeArchivosDeTrabajo(String mensajeArchivosDeTrabajo) {
    this.mensajeArchivosDeTrabajo = mensajeArchivosDeTrabajo;
  }

  private void armadoDePantalla() {
    this.cargarDatosPropios();
    this.getUsuariosFirmantes();
    this.refrescarUsuariosFirmantes();

    mensajeRevisor = (String) this.processEngine.getExecutionService()
        .getVariable(this.getWorkingTask().getExecutionId(), Constantes.VAR_MENSAJE_A_REVISOR);
  }

  public String iconoMensaje() {
    if (mensajeRevisor != null && !StringUtils.isEmpty(mensajeRevisor)) {
      return IMG_MAIL_CON_MENSAJE;
    } else {
      return IMG_MAIL_SIN_MENSAJE;
    }
  }

  public void onCloseWindow() {
    Map<String, Object> datos = new HashMap<String, Object>();
    datos.put("notificacion", "activada");
    Events.sendEvent(new Event(Events.ON_CLOSE, this.self.getParent(), datos));
  }

  protected String getFirmarTransitionName() {
    return Constantes.TRANSICION_USO_PORTAFIRMA;
  }

  protected Window getVentanaPadre() {
    return this.verificarDocumentoWindow;
  }

  public String getMensajeRevisor() {
    return mensajeRevisor;
  }

  public void setMensajeRevisor(String mensajeRevisor) {
    this.mensajeRevisor = mensajeRevisor;
  }

  public void setDataFile(byte[] dataFile) {
    this.dataFile = dataFile;
  }

  public byte[] getDataFile() {
    return dataFile;
  }

  // TODO porq retornas algo si seteas nomas? --> public void
  // setNombreArchivoLabel no return
  public Label setNombreArchivoLabel(Label nombreArchivoLabel) {
    return this.nombreArchivoLabel = nombreArchivoLabel;
  }

  public Label getNombreArchivoLabel() {
    return nombreArchivoLabel;
  }

  // TODO idem lo otro poner void
  public String setPathFileTemporal(String pathFileTemporal) {
    return this.pathFileTemporal = pathFileTemporal;
  }

  public String getPathFileTemporal() {
    return pathFileTemporal;
  }

  public Image getIconoRevisoresFirmaConjunta() {
    return iconoRevisoresFirmaConjunta;
  }

  public void setIconoRevisoresFirmaConjunta(Image iconoRevisoresFirmaConjunta) {
    this.iconoRevisoresFirmaConjunta = iconoRevisoresFirmaConjunta;
  }

  /**
   * Cargar la información para previsualización en el momento en el que se crea
   * la ventana.
   * 
   * @param event
   * @throws InterruptedException
   */
  public void onCreate$verificarDocumentoWindow(Event event) throws InterruptedException {
    try {
      //if (idGuardaDocumentalTemporal != null) {

        super.contenidoTemporal = this.gestionArchivosWebDavService
            .obtenerRecursoTemporalWebDav(nombreArchivoTemporal);
      //}
    } catch (Exception e) {
      logger.error("Error al descargar archivo del repositorio: " + nombreArchivoTemporal + e, e);
      Messagebox.show(
          Labels.getLabel("gedo.error.obteniendoArchivoRepositorio",
              new String[] { e.getMessage() }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    }
    if (super.contenidoTemporal == null || !super.generarPreviewInicial()) {
      super.closeAndNotifyAssociatedWindow(null);
    }
  }


  public void onClick$archivosDeTrabajo() {
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put(ArchivosDeTrabajoComposer.MOSTRAR_UPLOAD, false);
    hm.put(ArchivosDeTrabajoComposer.WORK_FLOW_ORIGEN, this.workingTask.getExecutionId());
    hm.put(ArchivoDeTrabajoDTO.ARCHIVOS_DE_TRABAJO, this.listaArchivosDeTrabajo);
    this.archivosDeTrabajoWindow = (Window) Executions.createComponents("archivosDeTrabajo.zul",
        this.self, hm);
    this.archivosDeTrabajoWindow.setTitle(Labels.getLabel("gedo.archivosDeTrabajo.window.titulo"));
    this.archivosDeTrabajoWindow.setClosable(true);
    this.archivosDeTrabajoWindow.setHeight("440px");
    this.archivosDeTrabajoWindow.doModal();
  }

  public void onClick$historialDocumento() {
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("executionId", this.workingTask.getExecutionId());
    Window historialDocumentoWindow = (Window) Executions
        .createComponents("/consultas/consultaHistorialDelDocumento.zul", this.self, hm);
    historialDocumentoWindow.setClosable(true);
    historialDocumentoWindow.doModal();
  }

  public void onClick$revisionButton()
      throws InterruptedException, UnsupportedEncodingException, SecurityNegocioException {
    if (this.usuarioRevisor.getSelectedItem() == null) {
      this.usuarioRevisor.setValue(null);
      throw new WrongValueException(this.usuarioRevisor,
          Labels.getLabel("gedo.error.faltaUsuarioRevisor"));
    }

    this.tareaAsignada = Constantes.TASK_REVISAR_DOCUMENTO;
    Usuario usuarioReducido = (Usuario) this.usuarioRevisor.getSelectedItem().getValue();
    Usuario revisor = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());
    Map<String, Object> datos = new HashMap<String, Object>();
    datos.put("funcion", "validarApoderamiento");
    datos.put("datos", revisor);
    enviarBloqueoPantalla(datos);
  }

  public String iconoArchivosDeTrabajoMensaje() {
    if (!CollectionUtils.isEmpty(listaArchivosDeTrabajo)) {
      this.setMensajeArchivosDeTrabajo(
          Labels.getLabel("gedo.archivosDeTrabajo.popup.conArchivos"));
      return Constantes.IMAGEN_CON_ARCHIVO_DE_TRABAJO;
    } else {
      this.setMensajeArchivosDeTrabajo(
          Labels.getLabel("gedo.archivosDeTrabajo.popup.sinArchivos"));
      return Constantes.IMAGEN_SIN_ARCHIVO_DE_TRABAJO;
    }
  }

  /**
   * Actualiza la lista de receptores de aviso de firma dependiendo del estado
   * de la solicitud.
   */
  private void actualizarEstadoAvisoFirma() {
    if (this.solicitudAvisoFirma.isChecked())
      adicionarReceptoresAviso();
    else
      eliminarReceptoresAviso();
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
   * Realiza las actividades necesarias para enviar el documento a revisión.
   */
  protected void enviarARevisar() throws InterruptedException {
    try {
      this.actualizarEstadoAvisoFirma();

      this.prepararVariablesWorkFlowRevision();
      String idGuardaDocumental = null;
      if (esPrimerFimante()) {
        try {
          idGuardaDocumental = super.getVariableWorkFlow(Constantes.VAR_ID_GUARDA_DOCUMENTAL)
              .toString();
        } catch (VariableWorkFlowNoExisteException e) {
          logger.error(
              "Error al obtener id de guarda documental -continua proceso- " + e.getMessage(), e);
          idGuardaDocumental = null;
        }
        if (idGuardaDocumental != null) {
          // WEBDAV
          this.gestionArchivosWebDavService.borrarArchivoTemporalWebDav(
              super.getVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA).toString());
        }
      } else {
        FirmanteDTO revisor = this.firmaConjuntaService.buscarRevisor(this.getCurrentUser(),
            this.workingTask.getExecutionId(), false);

        if (revisor != null) {
          if (this.getVariableWorkFlow(Constantes.VAR_USUARIO_VERIFICADOR_ORIGINAL) != null) {
            if (this.getVariableWorkFlow(Constantes.VAR_USUARIO_VERIFICADOR_ORIGINAL)
                .equals(revisor.getUsuarioRevisor())) {
              this.firmaConjuntaService.actualizarRevisor(this.getCurrentUser(), true,
                  this.workingTask.getExecutionId());
              this.setVariableWorkFlow(Constantes.VAR_USUARIO_VERIFICADOR_ORIGINAL, "");
            }
          }
        }
      }
      this.historialService.actualizarHistorial(this.workingTask.getExecutionId());
      this.workingTask.setProgress(100);
      this.signalExecution(Constantes.TRANSICION_REVISION_FIRMA_CONJUNTA, this.getCurrentUser());
      this.mostrarMensajeEnvioMail();
    } catch (AlfrescoException e) {
      logger.error("Error borrando archivos temporales", e);
      Messagebox.show(Labels.getLabel("gedo.firmarDocumento.error.borrandoArchivosTemporales"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      logger.error("Error al enviar a revisar en circuito de revision. Error: " + e.getMessage(),
          e);
      Messagebox.show(Labels.getLabel("gedo.error.enviarARevisar"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    } finally {
      Clients.clearBusy();
      this.closeAndNotifyAssociatedWindow(null);
    }
  }

  private Boolean esPrimerFimante() {
    Boolean esPrimerFirmante = Boolean.TRUE;
    List<FirmanteDTO> listadoFirmantes;

    listadoFirmantes = this.firmaConjuntaService
        .obtenerFirmantesPorEstado(this.getWorkingTask().getExecutionId(), false);

    if (listadoFirmantes.size() > 0) {
      this.firmante = listadoFirmantes.get(0);

      if (this.firmante.getOrden().intValue() != 1) {
        esPrimerFirmante = Boolean.FALSE;
      }
    }

    return esPrimerFirmante;
  }

  /**
   * Metodo que prepara las variables de JBPM4 en caso de realizarse una accion
   * de Revision de Documento. Se diferencia enter varaibles para documento
   * Template y No Tempate
   * 
   * @throws SecurityNegocioException
   */
  public void prepararVariablesWorkFlowRevision() throws SecurityNegocioException {
    Usuario usuarioReducido = (Usuario) this.usuarioRevisor.getSelectedItem().getValue();

    this.setVariableWorkFlow(Constantes.VAR_SOLICITUD_ENVIO_MAIL,
        solicitudEnvioCorreo.isChecked());
    this.setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, usuarioReducido.getUsername());
    this.setVariableWorkFlow(Constantes.VAR_MENSAJE_A_REVISOR, this.mensajeARevisor.getValue());
    this.setVariableWorkFlow(Constantes.VAR_USUARIO_VERIFICADOR, usuarioReducido.getUsername());

  }

}

final class VerificarDocumentoComposerListener implements EventListener {
  private VerificarDocumentoComposer composer;

  public VerificarDocumentoComposerListener(VerificarDocumentoComposer comp) {
    this.composer = comp;
  }

  @SuppressWarnings({ "unchecked", "unused" })
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() != null) {
        Map<String, Object> map = (Map<String, Object>) event.getData();
        String origen = (String) map.get("origen");
        if (StringUtils.equals(origen, Constantes.EVENTO_USUARIOS_FIRMANTES)) {
          List<Usuario> datosUsuarioBean = (List<Usuario>) map.get("datos");
        }

      }
      composer.iconoArchivosDeTrabajoMensaje();
      composer.verificarDocumentoBinder.loadAll();
    } else if (event.getName().equals(Events.ON_USER)) {
      Map<String, Object> datos = (Map<String, Object>) event.getData();
      String funcion = (String) datos.get("funcion");

      if (funcion.equals("validarApoderamiento")) {
        Usuario usuario = (Usuario) datos.get("datos");
        this.composer.validarUsuarios(usuario);
      }

      if (funcion.equals("validarReparticion")) {
        Usuario usuario = (Usuario) datos.get("datos");
        // Usuario usuario = datos.get
        this.composer.validacionesReparticion(usuario);
      }

      if (funcion.equals("asignarTarea")) {
        this.composer.asignarTarea();
      }

    } else if (event.getName().equals(Events.ON_UPLOAD)) {
      Map<String, Object> datos = (Map<String, Object>) event.getData();
      byte[] dataFile = (byte[]) datos.get("dataFile");
      Label nombreArchivoLabel = (Label) datos.get("nombreArchivoLabel");
      composer.setDataFile(dataFile);
      composer.setNombreArchivoLabel(nombreArchivoLabel);
      if (dataFile != null)
        ;
    }
  }
}