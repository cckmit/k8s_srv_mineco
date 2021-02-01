package com.egoveris.deo.web.satra.produccion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
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
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.ComunicacionService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.ComunicacionDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.model.model.MacroEventData;
import com.egoveris.deo.model.model.MetadataDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.UsuarioExternoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.ValidarApoderamientoComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InicioDocumentoComposer extends ValidarApoderamientoComposer {

  private static final long serialVersionUID = -1911208049935423635L;

  private static transient Log logger = LogFactory.getFactory()
      .getInstance(InicioDocumentoComposer.class);

  private Label labelDetalle;
  private Image imagenDocumentoProduccion;
  private Image imagenDocumentoEspecial;
  private Image imagenDocumentoFirmaExterna;
  private Image imagenDocumentoReservado;
  private Image imagenDocumentoNotificable;
  private Image imagenDocumentoFirmaConToken;
  private Image imagenDocumentoFirmaConjunta;
  private Combobox usuarioProductor;
  private Window agregarUsuariosFirmaConjuntaView;
  private Window inicioDocumentoWindow;
  private Window archivosDeTrabajoWindow;
  private Window definirDestinatarioWindow;
  private Textbox usuariosAgregadosTxt;
  private Button datosPropios;
  private Button definirDestinatarios;
  private Window datosPropiosWindow;
  private Button usuariosFirmaConjunta;
  private Bandbox familiaEstructuraTree;
  private Checkbox solicitudAvisoFirma;
  private Checkbox solicitudEnvioCorreo;

  private List<String> listaUsuariosDestinatarios;
  private List<String> listaUsuariosDestinatariosCopia;
  private List<String> listaUsuariosDestinatariosCopiOculta;
  private List<UsuarioExternoDTO> listaUsuariosDestinatariosExternos;
  private String mensajeDestinatario;
  private String usuarioSupervisado;
  private Integer idComunicacionAnterior = null;
  private Integer idDestinatario = null;

  @WireVariable("processEngine")
  private ProcessEngine processEngine;
  @WireVariable("historialServiceImpl")
  private HistorialService historialService;
  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  @WireVariable("firmaConjuntaServiceImpl")
  private FirmaConjuntaService firmaConjuntaService;
  @WireVariable("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @WireVariable("reparticionHabilitadaServiceImpl")
  private ReparticionHabilitadaService reparticionesHabilitadaService;
  @WireVariable("comunicacionServiceImpl")
  private ComunicacionService comunicacionService;
  @WireVariable("archivoDeTrabajoServiceImpl")
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  private Task selectedTask = null;
  private Usuario selectedUsuarioProductor;
  private String motivo;
  private String mensajeTipoDocumentoComunicable;
  private TipoDocumentoDTO selectedTipoDocumento;
  private String mensaje;
  private AnnotateDataBinder inicioDocumentoBinder;
  private List<Usuario> usuariosFirmantes;
  private String user;
  private List<DocumentoMetadataDTO> listaDocMetadata;
  private String numeroUsuariosFirmantes = "0";
  private boolean enviarAProducir = false;
  private List<ArchivoDeTrabajoDTO> archivosDeTrabajo = null;
  private String nombreArchivoTemporal;
  private String reparticionSeleccionada;
  private String gestorDocumental;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.gestorDocumental = (String) SpringUtil.getBean("gestorDocumentalName");

    if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
      // WEBDAV
      nombreArchivoTemporal = this.gestionArchivosWebDavService.crearNombreArchivoTemporal();
    }
    usuarioProductor.setModel(
        ListModels.toListSubModel(new ListModelList(this.getUsuarioService().obtenerUsuarios()),
            new UsuariosComparator(), 30));
    user = (String) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);
    this.datosPropios.setDisabled(true);
    familiaEstructuraTree.addEventListener(Events.ON_NOTIFY,
        new InicioDocumentoComposerListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new InicioDocumentoComposerListener(this));
    comp.addEventListener(Events.ON_USER, new InicioDocumentoComposerListener(this));
    this.mensajeDestinatario = new String();
    this.listaUsuariosDestinatarios = new ArrayList<>();
    this.listaUsuariosDestinatariosCopia = new ArrayList<>();
    this.listaUsuariosDestinatariosCopiOculta = new ArrayList<>();
    this.listaUsuariosDestinatariosExternos = new ArrayList<>();
    this.inicioDocumentoBinder = new AnnotateDataBinder(comp);
    this.inicioDocumentoBinder.loadAll();
    this.labelDetalle.setValue("");
    this.reparticionSeleccionada = this.getUsuarioService().obtenerUsuario(user)
        .getCodigoReparticion();

    if (Executions.getCurrent().getArg().get("id_comunicacion_anterior") != null) {
      this.idComunicacionAnterior = (Integer) Executions.getCurrent().getArg()
          .get("id_comunicacion_anterior");
      cargarListasDestinatarios();
    }

    if (Executions.getCurrent().getArg().get("id_destinatario") != null) {
      this.idDestinatario = (Integer) Executions.getCurrent().getArg().get("id_destinatario");
    }

    if (Executions.getCurrent().getArg().get("usuarioActual") != null) {
      this.usuarioSupervisado = (String) Executions.getCurrent().getArg().get("usuarioActual");
    }
  }

  @SuppressWarnings("unchecked")
  public void cargarListasDestinatarios() {
    this.listaUsuariosDestinatarios.clear();
    this.listaUsuariosDestinatariosCopia.clear();
    this.listaUsuariosDestinatariosCopiOculta.clear();
    this.listaUsuariosDestinatariosExternos.clear();
    setListaUsuariosDestinatarios(new ArrayList<String>(
        (HashSet<String>) Executions.getCurrent().getArg().get("listaDestinatarios")));
    setListaUsuariosDestinatariosCopia(new ArrayList<String>(
        (HashSet<String>) Executions.getCurrent().getArg().get("listaDestinatariosCC")));
    setListaUsuariosDestinatariosExternos(
        new ArrayList<UsuarioExternoDTO>((HashSet<UsuarioExternoDTO>) Executions.getCurrent()
            .getArg().get("listaDestinatariosExternos")));
    getFamiliaEstructuraTree()
        .setValue((String) Executions.getCurrent().getArg().get("tipoDocumento"));
  }

  /**
   * TODO Revisar si hace falta la funcionalidad en iniciar
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void onClick$archivosDeTrabajo() {
    Map hm = new HashMap();
    hm.put(ArchivoDeTrabajoDTO.ARCHIVOS_DE_TRABAJO, this.archivosDeTrabajo);
    hm.put(ArchivoDeTrabajoDTO.PATH_RELATIVO, nombreArchivoTemporal);
    hm.put(ArchivosDeTrabajoComposer.MOSTRAR_UPLOAD, true);
    this.archivosDeTrabajoWindow = (Window) Executions.createComponents("archivosDeTrabajo.zul",
        this.self, hm);
    this.archivosDeTrabajoWindow.setTitle(Labels.getLabel("gedo.archivosDeTrabajo.window.titulo"));

    this.archivosDeTrabajoWindow.setClosable(true);
    this.archivosDeTrabajoWindow.setHeight("440px");
    this.archivosDeTrabajoWindow.doModal();

  }

  public void onVolver() {
    Messagebox.show(Labels.getLabel("gedo.inicioDocumento.cancelarOperacion"),
        Labels.getLabel("gedo.inicioDocComp.msgbox.prompt"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new InicioDocumentoComposerListener(this));
  }

  void producirYoMismo() throws InterruptedException, SecurityNegocioException {

    // Valido que el tipo de documento para el que quiero crear un documento
    // esté habilitado de ser creado por la repartición del usuario creador
    if (reparticionesHabilitadaService.validarPermisoReparticion(this.getSelectedTipoDocumento(),
        user, Constantes.REPARTICION_PERMISO_INICIAR)) {
      this.mensaje = null;
      validarDatosPropios();
    } else {
      Clients.clearBusy();
      Messagebox.show(
          Labels.getLabel("gedo.iniciarDocumento.sinAutorizacionInicioReparticion",
              new String[] { reparticionSeleccionada }),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
      return;
    }
  }

  /**
   * Permite que el usuario actual produzca el documento.
   * 
   * @throws InterruptedException
   * @throws TipoDocumentoEstadoBajaException
   */
  public void onClick$selfProduccion() throws InterruptedException {

    if (this.getSelectedTipoDocumento() == null) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("gedo.general.tipoDocumentoInvalido"));
    }

    if (this.getSelectedTipoDocumento().getEstado().equals(TipoDocumentoDTO.ESTADO_INACTIVO)) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("gedo.abmTipoDocumento.estadoBaja",
              new Object[] { String.valueOf(this.familiaEstructuraTree) }));
    }

    if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null
        && !this.getSelectedTipoDocumento().getEsComunicable()) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("ccoo.panelUsuario.tipoDocumentoComunicable.valida"));
    }

    if (reparticionesHabilitadaService.validarPermisoReparticion(this.getSelectedTipoDocumento(),
        user, Constantes.REPARTICION_PERMISO_INICIAR)) {
      this.enviarAProducir = false;
      Map<String, Object> datos = new HashMap<>();
      datos.put("funcion", "producirYoMismo");
      enviarBloqueoPantalla(datos);
    } else {
      Clients.clearBusy();
      Messagebox.show(
          Labels.getLabel("gedo.iniciarDocumento.sinAutorizacionInicioReparticion",
              new String[] { reparticionSeleccionada }),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
      return;
    }
  }

  /*
   * Se setean las variables JBPM
   */
  public String iniciarDocumento(String usuarioProductor) throws InterruptedException {
    Map<String, Object> variables = new HashMap<>();
    if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
      nombreArchivoTemporal = this.gestionArchivosWebDavService.crearNombreArchivoTemporal();
      variables.put(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA, nombreArchivoTemporal);
    }
    variables.put(Constantes.VAR_SISTEMA_INICIADOR, Constantes.NOMBRE_APLICACION);
    variables.put(Constantes.VAR_USUARIO_PRODUCTOR, usuarioProductor);

    variables.put(Constantes.VAR_USUARIO_CREADOR,
        Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME));
    variables.put(Constantes.VAR_MOTIVO, this.getMotivo());
    variables.put(Constantes.VAR_TIPO_DOCUMENTO,
        this.getSelectedTipoDocumento().getId().toString());
    variables.put(Constantes.VAR_MENSAJE_PRODUCTOR, mensaje);
    if (this.getSelectedTipoDocumento().getEsComunicable()) {

      variables.put(Constantes.VAR_USUARIOS_DESTINATARIOS, this.listaUsuariosDestinatarios);
      variables.put(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA,
          this.listaUsuariosDestinatariosCopia);
      variables.put(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA_OCULTA,
          this.listaUsuariosDestinatariosCopiOculta);
      variables.put(Constantes.VAR_USUARIOS_DESTINATARIOS_EXTERNOS,
          this.listaUsuariosDestinatariosExternos);
      variables.put(Constantes.VAR_MENSAJE_DESTINATARIO,
          this.mensajeDestinatario.isEmpty() ? " " : this.mensajeDestinatario);
      variables.put(Constantes.VAR_ID_COMUNICACION_RESPONDIDA, this.idComunicacionAnterior);
      variables.put(Constantes.VAR_USUARIO_SUPERVISADO,
          this.usuarioSupervisado == null ? " " : this.usuarioSupervisado);
      variables.put(Constantes.VAR_ID_DESTINATARIO_COMUNICACION, this.idDestinatario);
    }

    variables.put(Constantes.VAR_DOCUMENTO_DATA, this.listaDocMetadata == null
        ? new ArrayList<DocumentoMetadataDTO>() : this.listaDocMetadata);
    variables.put(Constantes.VAR_SOLICITUD_ENVIO_MAIL, this.solicitudEnvioCorreo.isChecked());
    // Adicionar receptores de aviso de firma si así lo solicita el usuario.
    List<String> receptores = new ArrayList<>();
    if (this.solicitudAvisoFirma.isChecked()) {
      receptores.add(user);
    }
    variables.put(Constantes.VAR_RECEPTORES_AVISO_FIRMA, receptores);
    if (this.getSelectedTipoDocumento().getEsComunicable()) {
      variables.put(Constantes.VAR_COMUNICABLE, true);
    }

    ProcessInstance pInstance = this.processEngine.getExecutionService()
        .startProcessInstanceByKey("procesoGEDO", variables);
    // El inicio del documento, y del historial
    HistorialDTO inicio = new HistorialDTO(this.user, "Iniciar Documento", pInstance.getId());
    inicio.setFechaFin(new Date());
    inicio.setMensaje(mensaje);
    inicio.setFechaInicio(new Date());
    this.historialService.guardarHistorial(inicio);
    // Si el tipo de documento es de firma conjunta, guardar lista de
    // firmantes.
    if (this.getSelectedTipoDocumento().getEsFirmaConjunta() && this.usuariosFirmantes != null) {
      try {
        this.firmaConjuntaService.guardarFirmantes(usuariosFirmantes, pInstance.getId());
      } catch (Exception e) {
        logger.error("Error guardando lista de firmantes " + e);
        Messagebox.show(
            Labels.getLabel("gedo.iniciarDocumento.errores.fallaGuardarUsuariosFirmantes"),
            Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
      }

    }
    mostrarMensajeEnvioMail(pInstance.getId());
    if (this.idComunicacionAnterior != null) {
      ComunicacionDTO co = comunicacionService.buscarComunicacionPorId(idComunicacionAnterior);
      String usuarioActualizar = this.usuarioSupervisado != null ? this.usuarioSupervisado
          : this.user;
      comunicacionService.actualizarComunicacion(co);
    }
    return pInstance.getId();
  }

  /**
   * Invoca diferentes validaciones antes de enviar a producir el documento por
   * otro usuario.
   * 
   * @throws InterruptedException
   * @throws SecurityNegocioException
   */
  public void onIniciarDocumento() throws InterruptedException, SecurityNegocioException {

    if (this.getSelectedTipoDocumento() == null) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("gedo.general.tipoDocumentoInvalido"));
    }

    if (this.getSelectedTipoDocumento().getEstado().equals(TipoDocumentoDTO.ESTADO_INACTIVO)) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("gedo.abmTipoDocumento.estadoBaja",
              new Object[] { String.valueOf(this.familiaEstructuraTree) }));
    }

    if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null
        && !this.getSelectedTipoDocumento().getEsComunicable()) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("ccoo.panelUsuario.tipoDocumentoComunicable.valida"));
    }

    if (reparticionesHabilitadaService.validarPermisoReparticion(this.getSelectedTipoDocumento(),
        user, Constantes.REPARTICION_PERMISO_INICIAR)) {
      this.enviarAProducir = true;

      if (this.usuarioProductor.getSelectedItem() == null) {
        throw new WrongValueException(this.usuarioProductor,
            Labels.getLabel("gedo.iniciarDocumento.errores.faltausuarioProductor"));
      }

      // FIXME CAMBIA CON MULTIREPARTICION
      // Validaci�n de que el usuario productor sea de la misma
      // repartici�n.

      Usuario usuarioReducido = (Usuario) this.usuarioProductor.getSelectedItem().getValue();
      Usuario usuarioProductorInfo = getUsuarioService()
          .obtenerUsuario(usuarioReducido.getUsername());

      Map<String, Object> datos = new HashMap<>();
      datos.put("funcion", "validarApoderamiento");
      datos.put("datos", usuarioProductorInfo);
      enviarBloqueoPantalla(datos);
    } else {
      Clients.clearBusy();
      Messagebox.show(
          Labels.getLabel("gedo.iniciarDocumento.sinAutorizacionInicioReparticion",
              new String[] { reparticionSeleccionada }),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
      return;
    }
  }

  /**
   * Valida si existen datos propios, y si estos son obligatorios, de lo
   * contrario, contin�a con otras validaciones.
   * 
   * @throws InterruptedException
   */
  public void validarFirmantesEcho() {
    Clients.showBusy("Procesando");
    Events.echoEvent(Events.ON_USER, this.self, "validarFirmantes");
  }

  private void validarDatosPropios() throws InterruptedException, SecurityNegocioException {
    this.selectedTipoDocumento = this.getSelectedTipoDocumento();
    if (this.listaDocMetadata != null && !this.listaDocMetadata.isEmpty()) {
      for (DocumentoMetadataDTO documentoMetadata : listaDocMetadata) {
        if (documentoMetadata.isObligatoriedad()
            && StringUtils.isEmpty(documentoMetadata.getValor())) {
          Clients.clearBusy();
          Messagebox.show(
              Labels.getLabel("gedo.iniciarDocumento.warnings.faltaDatosPropiosObligatorio",
                  new String[] { this.getSelectedTipoDocumento().getAcronimo() }),
              Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
              Messagebox.QUESTION, new EventListener() {
                public void onEvent(Event evt) throws InterruptedException {
                  switch (((Integer) evt.getData()).intValue()) {
                  case Messagebox.YES:
                    Map<String, Object> datos = new HashMap<String, Object>();
                    datos.put("funcion", "validarFirmantes");
                    datos.put("datos", selectedTipoDocumento.getEsFirmaConjunta());
                    enviarBloqueoPantalla(datos);
                    break;
                  case Messagebox.NO:
                    return;
                  }
                }
              });
          return;
        }
      }
    }
    if (getSelectedTipoDocumento().getEsFirmaConjunta()) {
      validarUsuariosFirmantes();
    } else {
      enviarDocumentoProduccion();
    }
  }

  /**
   * Envia el documento a producción al usuario seleccionado, o en caso
   * contrario llama la ventana de producción para que el documento sea
   * producido por el usuario que lo creo.
   * 
   * @throws InterruptedException
   * @throws SecurityNegocioException
   */
  public void enviarDocumentoProduccion() throws InterruptedException, SecurityNegocioException {
    if (this.enviarAProducir) {
      if (this.usuarioProductor.getSelectedItem() != null) {
        Usuario usuarioReducido = (Usuario) this.usuarioProductor.getSelectedItem().getValue();
        Usuario usuarioProductorInfo = getUsuarioService()
            .obtenerUsuario(usuarioReducido.getUsername());

        String taskID = iniciarDocumento(usuarioProductorInfo.getUsername());
        // Actualiza los archivos de Trabajo
        actualizarArchivosDeTrabajo(taskID);
        Messagebox.show(Labels.getLabel("gedo.iniciarDocumento.inicioExitoso"),
            Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
        closeAndNotifyAssociatedWindow(null);
      }
    } else {
      String executionId = iniciarDocumento(user);
      // Actualiza los archivos de Trabajo
      actualizarArchivosDeTrabajo(executionId);
      MacroEventData med = new MacroEventData();
      med.setExecutionId(executionId);
      closeAndNotifyAssociatedWindow(med);
    }
    Clients.clearBusy();
  }

  private void actualizarArchivosDeTrabajo(String taskID) {
    if (!CollectionUtils.isEmpty(archivosDeTrabajo)) {
      for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivosDeTrabajo) {
        archivoDeTrabajo.setIdTask(taskID);
        archivoDeTrabajoService.grabarArchivoDeTrabajo(archivoDeTrabajo);
      }
    }
  }

  public void onClick$datosPropios() {
    Map<String, Object> hm = new HashMap<>();
    hm.put(DatosPropiosDocumentoComposer.METADATA, this.listaDocMetadata);
    hm.put(DatosPropiosDocumentoComposer.HABILITAR_PANTALLA, true);
    hm.put(DatosPropiosDocumentoComposer.HABILITAR_CIERRE, true);
    this.datosPropiosWindow = (Window) Executions
        .createComponents("/inbox/datosPropiosDelDocumento.zul", this.self, hm);
    this.datosPropiosWindow.setTitle("Datos Propios");
    this.datosPropiosWindow.setHeight("440px");
    this.datosPropiosWindow.setClosable(true);
    this.datosPropiosWindow.doModal();
  }

  /**
   * Actualiza el TextBox correspondiente a los usuarios agregados para firmar
   */
  public void actualizarUsuariosFirmantes(List<Usuario> usuariosFirmaConjunta) {

    this.setUsuariosFirmantes(usuariosFirmaConjunta);
    if (usuariosFirmaConjunta != null && !usuariosFirmaConjunta.isEmpty())

    {
      this.usuariosAgregadosTxt.setMultiline(true);
      this.usuariosAgregadosTxt.setRows(this.usuariosFirmantes.size() + 1);
      StringBuilder listaUsuarios = new StringBuilder();
      for (Usuario datosUsuario : usuariosFirmaConjunta) {
        listaUsuarios.append(datosUsuario.getNombreApellido());
        if (usuariosFirmaConjunta.indexOf(datosUsuario) == usuariosFirmaConjunta.size() - 1) {
          listaUsuarios.append("(" + datosUsuario.getUsername() + ")");
        } else {
          listaUsuarios.append("(" + datosUsuario.getUsername() + "),\n");
        }
      }
      this.usuariosAgregadosTxt.setValue(listaUsuarios.toString());
    } else {
      this.usuariosAgregadosTxt.setValue(Labels.getLabel("gedo.general.noUsuariosFirmantes"));
    }
    this.inicioDocumentoBinder.loadComponent(this.usuariosAgregadosTxt);
  }

  /**
  * 
  */
  public void onSelectTipoDocumento() {
    /**
     * Se cambia en .zul, forward="onSelect" por "onChange" porque al
     * seleccionar el tipo de documento desde teclado, el item seleccionado
     * llega en null.
     */
    imagenDocumentoProduccion.setSrc(null);
    imagenDocumentoEspecial.setSrc(null);
    imagenDocumentoFirmaExterna.setSrc(null);
    imagenDocumentoReservado.setSrc(null);
    imagenDocumentoNotificable.setSrc(null);
    imagenDocumentoFirmaConToken.setSrc(null);
    imagenDocumentoFirmaConjunta.setSrc(null);

    if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null
        && !this.getSelectedTipoDocumento().getEsComunicable()) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("ccoo.panelUsuario.tipoDocumentoComunicable.valida"));
    }

    if (!this.getSelectedTipoDocumento().getEsManual()) {
      this.setSelectedTipoDocumento(null);
      this.familiaEstructuraTree.setText(null);
      throw new WrongValueException(familiaEstructuraTree,
          Labels.getLabel("gedo.iniciarDocumento.exeception.documentoManual"));
    }
    this.labelDetalle.setValue(this.getSelectedTipoDocumento().getDescripcion());
    
    if (this.getSelectedTipoDocumento().getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {
      this.imagenDocumentoProduccion.setSrc(Constantes.IMG_TIENE_LIBRE);
      this.imagenDocumentoProduccion.setVisible(true);
      this.imagenDocumentoProduccion
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.libre"));
    } else if (this.getSelectedTipoDocumento()
        .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
      this.imagenDocumentoProduccion.setSrc(Constantes.IMG_TIENE_IMPORTADO);
      this.imagenDocumentoProduccion.setVisible(true);
      this.imagenDocumentoProduccion
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.importado"));
    } else if (this.getSelectedTipoDocumento()
        .getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {
      this.imagenDocumentoProduccion.setSrc(Constantes.IMG_TIENE_TEMPLATE);
      this.imagenDocumentoProduccion.setVisible(true);
      this.imagenDocumentoProduccion
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.template"));
    } else if (this.getSelectedTipoDocumento()
        .getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      this.imagenDocumentoProduccion.setSrc(Constantes.IMG_TIENE_IMPORTADO_TEMPLATE);
      this.imagenDocumentoProduccion.setVisible(true);
      this.imagenDocumentoProduccion
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.importadoTemplate"));
    }

    if (this.getSelectedTipoDocumento().getEsEspecial().equals(Boolean.TRUE)) {
      this.imagenDocumentoEspecial.setSrc(Constantes.IMG_ES_ESPECIAL);
      this.imagenDocumentoEspecial.setVisible(true);
      this.imagenDocumentoEspecial
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.especial"));
    } else {
      this.imagenDocumentoEspecial.setVisible(false);
    }

    if (this.getSelectedTipoDocumento().getEsFirmaExterna().equals(Boolean.TRUE)) {
      this.imagenDocumentoFirmaExterna.setSrc(Constantes.IMG_ES_FIRMA_EXTERNA);
      this.imagenDocumentoFirmaExterna.setVisible(true);
      this.imagenDocumentoFirmaExterna
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.firmaExterna"));
    } else {
      this.imagenDocumentoFirmaExterna.setVisible(false);
    }

    if (this.getSelectedTipoDocumento().getEsConfidencial().equals(Boolean.TRUE)) {
      this.imagenDocumentoReservado.setSrc(Constantes.IMG_ES_CONFIDENCIAL);
      this.imagenDocumentoReservado.setVisible(true);
      this.imagenDocumentoReservado
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.reservado"));
    } else {
      this.imagenDocumentoReservado.setVisible(false);
    }

    if (this.getSelectedTipoDocumento().getEsNotificable().equals(Boolean.TRUE)) {
      this.imagenDocumentoNotificable.setSrc(Constantes.IMG_ES_NOTIFICABLE);
      this.imagenDocumentoNotificable.setVisible(true);
      this.imagenDocumentoNotificable
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.notificable"));
    } else {
      this.imagenDocumentoNotificable.setVisible(false);
    }

    if (this.getSelectedTipoDocumento().getTieneToken().equals(Boolean.TRUE)) {
      this.imagenDocumentoFirmaConToken.setSrc(Constantes.IMG_TIENE_TOKEN);
      this.imagenDocumentoFirmaConToken.setVisible(true);
      this.imagenDocumentoFirmaConToken
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.token"));
    } else {
      this.imagenDocumentoFirmaConToken.setVisible(false);
    }

    if (this.getSelectedTipoDocumento().getEsFirmaConjunta().equals(Boolean.TRUE)) {
      this.imagenDocumentoFirmaConjunta.setSrc(Constantes.IMG_ES_FIRMA_CONJUNTA);
      this.imagenDocumentoFirmaConjunta.setVisible(true);
      this.imagenDocumentoFirmaConjunta
          .setTooltip(Labels.getLabel("gedo.general.imagenesCaracteristicas.firmaConjunta"));
    } else {
      this.imagenDocumentoFirmaConjunta.setVisible(false);
    }

    if (!reparticionesHabilitadaService.validarPermisoReparticion(this.getSelectedTipoDocumento(),
        user, Constantes.REPARTICION_PERMISO_INICIAR)) {
      throw new WrongValueException(this.familiaEstructuraTree,
          Labels.getLabel("gedo.iniciarDocumento.sinAutorizacionInicioReparticion",
              new String[] { reparticionSeleccionada }));
    }

    if (!usuariosFirmaConjunta.isVisible()
        && this.getSelectedTipoDocumento().getEsFirmaConjunta()) {
      usuariosFirmaConjunta.setVisible(true);
      // Limpiar lista de firmantes
      if (this.usuariosFirmantes != null) {
        this.usuariosFirmantes.clear();
      }
      this.usuariosAgregadosTxt.setValue(Labels.getLabel("gedo.general.noUsuariosFirmantes"));
    } else if (usuariosFirmaConjunta.isVisible()
        && !this.getSelectedTipoDocumento().getEsFirmaConjunta()) {
      usuariosFirmaConjunta.setVisible(false);
      usuariosAgregadosTxt.setValue(StringUtils.EMPTY);
    }

    if (!this.definirDestinatarios.isVisible()
        && this.getSelectedTipoDocumento().getEsComunicable()) {
      this.definirDestinatarios.setVisible(true);
    } else {
      if (this.definirDestinatarios.isVisible()
          && !this.getSelectedTipoDocumento().getEsComunicable()) {
        this.definirDestinatarios.setVisible(false);
      }
    }
    // METADATA
    List<MetadataDTO> metadatas = this.getSelectedTipoDocumento().getListaDatosVariables();

    if (metadatas != null && !metadatas.isEmpty()) {
      this.datosPropios.setDisabled(false);
      listaDocMetadata = new ArrayList<>(metadatas.size());

      for (int i = 0; metadatas.size() > i; i++) {
        DocumentoMetadataDTO temp = new DocumentoMetadataDTO();
        temp.setNombre(metadatas.get(i).getNombre());
        temp.setObligatoriedad(metadatas.get(i).isObligatoriedad());
        temp.setOrden(metadatas.get(i).getOrden());
        temp.setTipo(metadatas.get(i).getTipo());
        listaDocMetadata.add(temp);
      }
    } else {
      this.datosPropios.setDisabled(true);
      listaDocMetadata = null;
    }

    if (!this.getSelectedTipoDocumento().getTieneAviso()) {
      solicitudAvisoFirma.setDisabled(true);
      solicitudAvisoFirma.setChecked(false);
    } else {
      solicitudAvisoFirma.setDisabled(false);
    }

    this.inicioDocumentoBinder.loadComponent(this.labelDetalle);
  }

  /**
   * Invocación de la ventana para la selección de los usuarios firmantes.
   */
  public void onAgregarUsuariosFirmaConjunta() {

    if (this.agregarUsuariosFirmaConjuntaView != null) {
      Map<String, Object> datos = new HashMap<>();
      datos.put("modoRenderizado", Constantes.MODO_RENDERIZADO_FIRMACONJUNTA);
      datos.put("usuariosAgregados", this.usuariosFirmantes);
      datos.put("tipoDocumento", this.getSelectedTipoDocumento());
      this.agregarUsuariosFirmaConjuntaView.invalidate();
      this.agregarUsuariosFirmaConjuntaView = (Window) Executions
          .createComponents("agregarUsuariosFirmaConjunta.zul", this.self, datos);
      this.agregarUsuariosFirmaConjuntaView.setParent(this.inicioDocumentoWindow);
      this.agregarUsuariosFirmaConjuntaView.setPosition("center");
      this.agregarUsuariosFirmaConjuntaView.setVisible(true);
      this.agregarUsuariosFirmaConjuntaView.doModal();
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
          Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }

  }

  public void onDefinirDestinatarios() throws InterruptedException {

    Map<String, Object> map = new HashMap<>();
    map.put("mensaje", this.mensajeDestinatario);
    map.put("destinatario", this.listaUsuariosDestinatarios);
    map.put("destinatarioCopia", this.listaUsuariosDestinatariosCopia);
    map.put("destinatarioCopiaOculta", this.listaUsuariosDestinatariosCopiOculta);
    map.put("destinatariosExternos", this.listaUsuariosDestinatariosExternos);

    this.definirDestinatarioWindow = (Window) Executions
        .createComponents("/co/definirDestinatarios.zul", this.self, map);
    this.definirDestinatarioWindow.setClosable(true);
    this.definirDestinatarioWindow.setPosition("center");
    this.definirDestinatarioWindow.doModal();
  }

  /**
   * Si el tipo de documento exige firma conjunta, y no se han definido los
   * usuarios firmantes, pregunta al usuario, s� desea continuar.
   * 
   * @throws InterruptedException
   * @throws SecurityNegocioException
   */
  public void validarUsuariosFirmantes() throws InterruptedException, SecurityNegocioException {

    if (this.usuariosFirmantes == null || this.usuariosFirmantes.size() <= 1) {
      Clients.clearBusy();
      Messagebox.show(Labels.getLabel("gedo.general.warnings.faltanUsuariosFirmantes"),
          Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
          Messagebox.QUESTION, new EventListener() {
            public void onEvent(Event evt) throws InterruptedException {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:
                Map<String, Object> datos = new HashMap<String, Object>();
                datos.put("funcion", "enviarDocumentoProduccion");
                enviarBloqueoPantalla(datos);
                break;
              case Messagebox.NO:
                return;
              }
            }
          });
      return;
    } else {
      this.enviarDocumentoProduccion();
    }
  }

  public Label getLabelDetalle() {
    return labelDetalle;
  }

  public void setLabelDetalle(Label labelDetalle) {
    this.labelDetalle = labelDetalle;
  }

  public Usuario getSelectedUsuarioProductor() {
    return selectedUsuarioProductor;
  }

  public void setSelectedUsuarioProductor(Usuario selectedUsuarioProductor) {
    this.selectedUsuarioProductor = selectedUsuarioProductor;
  }

  public Task getSelectedTask() {
    return selectedTask;
  }

  public void setSelectedTask(Task selectedTask) {
    this.selectedTask = selectedTask;
  }

  public Window getInicioDocumentoWindow() {
    return inicioDocumentoWindow;
  }

  public void setInicioDocumentoWindow(Window inicioDocumentoWindow) {
    this.inicioDocumentoWindow = inicioDocumentoWindow;
  }

  public AnnotateDataBinder getInicioDocumentoBinder() {
    return inicioDocumentoBinder;
  }

  public void setInicioDocumentoBinder(AnnotateDataBinder inicioDocumentoBinder) {
    this.inicioDocumentoBinder = inicioDocumentoBinder;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public Button getDatosPropios() {
    return datosPropios;
  }

  public void setDatosPropios(Toolbarbutton datosPropios) {
    this.datosPropios = datosPropios;
  }

  public List<DocumentoMetadataDTO> getListaDocMetadata() {
    return listaDocMetadata;
  }

  public void setListaDocMetadata(List<DocumentoMetadataDTO> listaDocMetadata) {
    this.listaDocMetadata = listaDocMetadata;
  }

  public void setUsuariosFirmantes(List<Usuario> usuariosFirmantes) {
    this.usuariosFirmantes = usuariosFirmantes;
  }

  public List<Usuario> getUsuariosFirmantes() {
    return usuariosFirmantes;
  }

  public String getMensaje() {

    return mensaje;
  }

  public void setMensaje(String mensaje) {

    this.mensaje = mensaje;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public TipoDocumentoDTO getSelectedTipoDocumento() {

    return selectedTipoDocumento;
  }

  public List<ArchivoDeTrabajoDTO> getArchivosDeTrabajo() {
    return archivosDeTrabajo;
  }

  public void setArchivosDeTrabajo(List<ArchivoDeTrabajoDTO> archivosDeTrabajo) {
    this.archivosDeTrabajo = archivosDeTrabajo;
  }

  public void setSelectedTipoDocumento(TipoDocumentoDTO selectedTipoDocumento) {
    this.selectedTipoDocumento = selectedTipoDocumento;
  }

  public String getNumeroUsuariosFirmantes() {
    return numeroUsuariosFirmantes;
  }

  public void setNumeroUsuariosFirmantes(String numeroUsuariosFirmantes) {
    this.numeroUsuariosFirmantes = numeroUsuariosFirmantes;
  }

  @Override
  protected void asignarTarea() throws InterruptedException, SecurityNegocioException {
    this.validarDatosPropios();
  }

  @Override
  protected void enviarBloqueoPantalla(Object data) {
    Clients.showBusy(Labels.getLabel("gedo.general.procesando.procesandoSolicitud"));
    Events.echoEvent(Events.ON_USER, this.self, data);
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

  private void mostrarMensajeEnvioMail(String processInstanceId) throws InterruptedException {
    Boolean falloEnvioSolicitudMail = (Boolean) this.processEngine.getExecutionService()
        .getVariable(processInstanceId, Constantes.VAR_SOLICITUD_ENVIO_MAIL_FAIL);
    if (falloEnvioSolicitudMail != null && falloEnvioSolicitudMail) {

      Messagebox.show(Labels.getLabel("gedo.error.enviarCorreo"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

  public Bandbox getFamiliaEstructuraTree() {
    return familiaEstructuraTree;
  }

  public void setFamiliaEstructuraTree(Bandbox familiaEstructuraTree) {
    this.familiaEstructuraTree = familiaEstructuraTree;
  }

  public void cargarTipoDocumento(TipoDocumentoDTO data) {
    this.familiaEstructuraTree.setText(data.getAcronimo().toUpperCase());
    this.familiaEstructuraTree.close();
    setSelectedTipoDocumento(data);
    onSelectTipoDocumento();
    this.inicioDocumentoBinder.loadAll();
  }

  public Image getImagenDocumentoTemplate() {
    return imagenDocumentoProduccion;
  }

  public void setImagenDocumentoTemplate(Image imagenDocumentoTemplate) {
    this.imagenDocumentoProduccion = imagenDocumentoTemplate;
  }

  public Image getImagenDocumentoEspecial() {
    return imagenDocumentoEspecial;
  }

  public void setImagenDocumentoEspecial(Image imagenDocumentoEspecial) {
    this.imagenDocumentoEspecial = imagenDocumentoEspecial;
  }

  public Image getImagenDocumentoFirmaExterna() {
    return imagenDocumentoFirmaExterna;
  }

  public void setImagenDocumentoFirmaExterna(Image imagenDocumentoFirmaExterna) {
    this.imagenDocumentoFirmaExterna = imagenDocumentoFirmaExterna;
  }

  public Image getImagenDocumentoReservado() {
    return imagenDocumentoReservado;
  }

  public void setImagenDocumentoReservado(Image imagenDocumentoReservado) {
    this.imagenDocumentoReservado = imagenDocumentoReservado;
  }

  public Image getImagenDocumentoFirmaConToken() {
    return imagenDocumentoFirmaConToken;
  }

  public void setImagenDocumentoFirmaConToken(Image imagenDocumentoFirmaConToken) {
    this.imagenDocumentoFirmaConToken = imagenDocumentoFirmaConToken;
  }

  public Image getImagenDocumentoNotificable() {
    return imagenDocumentoNotificable;
  }

  public void setImagenDocumentoNotificable(Image imagenDocumentoNotificable) {
    this.imagenDocumentoNotificable = imagenDocumentoNotificable;
  }

  public Image getImagenDocumentoFirmaConjunta() {
    return imagenDocumentoFirmaConjunta;
  }

  public void setImagenDocumentoFirmaConjunta(Image imagenDocumentoFirmaConjunta) {
    this.imagenDocumentoFirmaConjunta = imagenDocumentoFirmaConjunta;
  }

  public List<String> getListaUsuariosDestinatarios() {
    return listaUsuariosDestinatarios;
  }

  public void setListaUsuariosDestinatarios(List<String> listaUsuariosDestinatarios) {
    this.listaUsuariosDestinatarios = listaUsuariosDestinatarios;
  }

  public List<String> getListaUsuariosDestinatariosCopia() {
    return listaUsuariosDestinatariosCopia;
  }

  public void setListaUsuariosDestinatariosCopia(List<String> listaUsuariosDestinatariosCopia) {
    this.listaUsuariosDestinatariosCopia = listaUsuariosDestinatariosCopia;
  }

  public List<String> getListaUsuariosDestinatariosCopiOculta() {
    return listaUsuariosDestinatariosCopiOculta;
  }

  public void setListaUsuariosDestinatariosCopiOculta(
      List<String> listaUsuariosDestinatariosCopiOculta) {
    this.listaUsuariosDestinatariosCopiOculta = listaUsuariosDestinatariosCopiOculta;
  }

  public String getMensajeDestinatario() {
    return mensajeDestinatario;
  }

  public void setMensajeDestinatario(String mensajeDestinatario) {
    this.mensajeDestinatario = mensajeDestinatario;
  }

  public String getMensajeTipoDocumentoComunicable() {
    return mensajeTipoDocumentoComunicable;
  }

  public void setMensajeTipoDocumentoComunicable(String mensajeTipoDocumentoComunicable) {
    this.mensajeTipoDocumentoComunicable = mensajeTipoDocumentoComunicable;
  }

  public List<UsuarioExternoDTO> getListaUsuariosDestinatariosExternos() {
    return listaUsuariosDestinatariosExternos;
  }

  public void setListaUsuariosDestinatariosExternos(
      List<UsuarioExternoDTO> listaUsuariosDestinatariosExternos) {
    this.listaUsuariosDestinatariosExternos = listaUsuariosDestinatariosExternos;
  }

  public Integer getIdComunicacionAnterior() {
    return idComunicacionAnterior;
  }

  public void setIdComunicacionAnterior(Integer idComunicacionAnterior) {
    this.idComunicacionAnterior = idComunicacionAnterior;
  }

  public String getUsuarioSupervisado() {
    return usuarioSupervisado;
  }

  public void setUsuarioSupervisado(String usuarioSupervisado) {
    this.usuarioSupervisado = usuarioSupervisado;
  }

  public Integer getIdDestinatario() {
    return idDestinatario;
  }

  public void setIdDestinatario(Integer idDestinatario) {
    this.idDestinatario = idDestinatario;
  }

}

final class InicioDocumentoComposerListener implements EventListener {
  private InicioDocumentoComposer composer;

  public InicioDocumentoComposerListener(InicioDocumentoComposer comp) {
    this.composer = comp;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onEvent(Event event) throws Exception {

    if (event.getName().equals(Events.ON_USER)) {
      Map<String, Object> datos = (Map<String, Object>) event.getData();
      String funcion = (String) datos.get("funcion");
      if ("producirYoMismo".equals(funcion)) {
        this.composer.producirYoMismo();
      }
      if ("enviarDocumentoProduccion".equals(funcion)) {
        this.composer.enviarDocumentoProduccion();
      }
      if ("validarFirmantes".equals(funcion)) {
        Boolean firmaConjunta = (Boolean) datos.get("datos");
        if (firmaConjunta) {
          composer.validarUsuariosFirmantes();
        } else {
          composer.enviarDocumentoProduccion();
        }
      }
      if ("validarApoderamiento".equals(funcion)) {
        Usuario usuario = (Usuario) datos.get("datos");
        this.composer.validarUsuarios(usuario);
      }
      if ("validarReparticion".equals(funcion)) {
        Usuario usuario = (Usuario) datos.get("datos");
        this.composer.validacionesReparticion(usuario);
      }
      if ("asignarTarea".equals(funcion)) {
        this.composer.asignarTarea();
      }
    }
    if (event.getName().equals(Events.ON_CLOSE)) {
      switch (((Integer) event.getData()).intValue()) {
      case Messagebox.YES:
        Events.sendEvent(this.composer.getInicioDocumentoWindow(), new Event(Events.ON_CLOSE));
        break;
      case Messagebox.NO:
        break;
      }
    } else if (event.getName().equals(Events.ON_NOTIFY) && event.getData() != null) {

      if (event.getData() instanceof TipoDocumentoDTO) {
        TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();

        this.composer.cargarTipoDocumento(data);

      } else {
        Map<String, Object> map = (Map<String, Object>) event.getData();
        String origen = (String) map.get("origen");
        if (StringUtils.equals(origen, Constantes.EVENTO_USUARIOS_FIRMANTES)) {
          List<Usuario> usuarios = (List<Usuario>) map.get("datos");
          if (usuarios != null) {
            composer.actualizarUsuariosFirmantes(usuarios);
          }
        } else if (StringUtils.equals(origen, Constantes.EVENTO_ARCHIVO_TRABAJO)) {
          List<ArchivoDeTrabajoDTO> data = (List<ArchivoDeTrabajoDTO>) map.get("datos");
          if (data != null) {
            this.composer.setArchivosDeTrabajo(data);
          }
        }
        if (StringUtils.equals(origen, Constantes.EVENTO_DEFINIR_DESTINATARIOS)) {
          composer.setListaUsuariosDestinatarios((List<String>) map.get("destinatario"));
          composer.setListaUsuariosDestinatariosCopia((List<String>) map.get("destinatarioCopia"));
          composer.setListaUsuariosDestinatariosCopiOculta(
              (List<String>) map.get("destinatarioCopiaOculta"));
          composer.setListaUsuariosDestinatariosExternos(
              (List<UsuarioExternoDTO>) map.get("destinatariosExternos"));
          composer.setMensajeDestinatario((String) map.get("mensaje"));
        }
      }

    }
  }
}
