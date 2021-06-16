package com.egoveris.te.base.composer;

import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.te.base.composer.genrico.BandBoxUsuarioComposer;
import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.DocumentoManagerService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.service.iface.IRehabilitarExpedienteService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EnvioDesarchivarComposer extends EnvioExpedienteComposer {

  private static final long serialVersionUID = 6266287038479709703L;
  @Autowired
  private Combobox estado;
  @Autowired
//  private Combobox usuario;
  
  private Include inclBandboxUsuario;
  
  private ExpedienteElectronicoDTO expedienteElectronico;
  private String estadoSeleccionado = null;

  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;
  @Autowired
  private Bandbox reparticionBusquedaSADE;
  @Autowired
  private Textbox tipoDocumento;
  @Autowired
  private Intbox anioDocumento;
  @Autowired
  private Intbox numeroDocumento;

  private String usuarioApoderado;
  private Usuario usuarioAEnviar;
  private String destinatario;
  private DocumentoDTO documentoEstandard; 
  
  private UsuarioReducido usuarioSeleccionado;

  @WireVariable(ConstantesServicios.ACCESO_WEBDAV_SERVICE)
  protected IAccesoWebDavService visualizaDocumentoService;

  @WireVariable(ConstantesServicios.DOCUMENTO_GEDO_SERVICE)
  private DocumentoGedoService documentoGedoService; 
  
  @WireVariable(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE)
  private DocumentoManagerService documentoManagerService; 
  
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)  
  private TipoDocumentoService tipoDocumentoService; 

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)  
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)  
  private UsuariosSADEService usuariosSADEService;
  
  @WireVariable("rehabilitarExpedienteServiceImpl")
  private IRehabilitarExpedienteService rehabilitarExpedienteService;

  public static final String NOTA = "NO";
  public static final String TRAMITACION = "Tramitacion";
  public static final String SUBSANACION = "Subsanacion";
  public static final String EJECUCION = "Ejecucion";
  public static final String INICIACION = "Iniciacion";

  public static final String ESTADO_ANTERIOR = "Guarda Temporal";

  private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";

  /**
   * Según el estado en el que este, cargo la lista de estados con los estados a
   * los que se puede pasar. Esto se logra con el outcomes de jbpm, que devuelve
   * todas las salidas (transiciones) que tiene la tarea acctual
   * 
   * @exception se
   *              utiliza throws Exception por el doAfterCompose de ZK el cual
   *              esta contenido en el GenericForwardComposer
   */
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component); 
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null)); 
    
    // Obtengo Execution
    Execution exec = Executions.getCurrent();
    @SuppressWarnings("rawtypes")
    Map map = exec.getArg();

    // Obtengo el idExpedienteElectronico desde el Map y con este me traigo el
    // Objeto EE.
    Long idExpedienteElectronico = (Long) map.get("idExpedienteElectronico");
    this.expedienteElectronico = this.expedienteElectronicoService
        .buscarExpedienteElectronico(idExpedienteElectronico);

    if (this.expedienteElectronico.getDocumentos().size() == 2
        && this.getExpedienteElectronico().getTrata().getAcronimoDocumento() != null) {
      this.estado.appendItem(INICIACION);
      this.estado.setDisabled(true);
      this.estado.setSelectedIndex(0);
    } else {
      this.estado.appendItem(TRAMITACION);
      // this.estado.appendItem(SUBSANACION);
      this.estado.appendItem(EJECUCION);
    }

    // Obtengo los usuarios para y se lo seteo como lista al combobox de
    // usuarios

		configurarBandboxUsuario(component, true);
    
//    usuario.setModel(ListModels.toListSubModel(
//        new ListModelList(this.getUsuariosSADEService().getTodosLosUsuarios()),
//        new UsuariosComparatorConsultaExpediente(), 30));
  }
  
	private void configurarBandboxUsuario(Component component, boolean deshabilitado) {
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.DISABLED_BANDBOX, deshabilitado);
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.COMPONENTE_PADRE, component);
    inclBandboxUsuario.setSrc(BandBoxUsuarioComposer.SRC);
    
		component.addEventListener(BandBoxUsuarioComposer.ON_SELECT_USUARIO,
				new EnvioDesarchivarListener());

    
	}

  public void onEnviar() throws InterruptedException {

    validarDatosBuscarDocumento();
    String stTipoDocumento = tipoDocumento.getValue();
    Integer stAnioDocumento = anioDocumento.getValue();
    Integer stNumeroDocumento = numeroDocumento.getValue();
    String stReparticionDocumento = reparticionBusquedaSADE.getValue().toUpperCase();

    // Obtengo el usuario seleccionado
   //  Usuario item = (Usuario) this.usuario.getSelectedItem().getValue();

    this.usuarioAEnviar = usuariosSADEService.getDatosUsuario(usuarioSeleccionado.getUsername());

    /*
     * Si el usuario que se selecciono para enviar el expediente se encuentra de
     * vacaciones el metodo validarLicencia devuelve quien es el apoderado, si
     * no se cuentra de vacaciones devuelve null
     */
    if (usuariosSADEService.licenciaActiva(this.usuarioAEnviar.getUsername())) {
      usuarioApoderado = this.validarLicencia(this.usuarioAEnviar.getUsername());
    }

    /*
     * Si usuarioApoderado es distinto de null, pregunto si desea enviar el
     * expediente al usuario apoderado, en caso de Si se continua el desarchivo
     * normalmente, en caso de no se pide que ingrese otro usuario. En cambio si
     * el usuarioApoderado es igual a null, empieza el desarchivo normalmente.
     */
    if (usuarioApoderado != null) {
      preguntarEnvioApoderado();
    } else {
      destinatario = this.usuarioAEnviar.getUsername();
      desarchivarExpediente(stTipoDocumento, stAnioDocumento, stNumeroDocumento,
          stReparticionDocumento);
    }

  }

  /**
   * Este metodo primero lo que hace es buscar el documento que se va a asociar
   * (el documento es el motivo por el cual se desea desarchivar el expediente)
   * si lo encuentra continua con el flujo normal de desarchivo, si no lo
   * encuentra o si el documento ya se encuentra asociado, informa que no existe
   * o que ya se encuentra asociado.
   * 
   * @param stTipoDocumento
   * @param stAnioDocumento
   * @param stNumeroDocumento
   * @param stReparticionDocumento
   * @throws InterruptedException
   */
  private void desarchivarExpediente(String stTipoDocumento, Integer stAnioDocumento,
      Integer stNumeroDocumento, String stReparticionDocumento) throws InterruptedException {
    boolean result = asociarDocumento(stTipoDocumento, stAnioDocumento, stNumeroDocumento,
        stReparticionDocumento);

    if (result) {
      // Desarchiva expediente.
      continuarDesarchivo();
    }
  }

  /**
   * Metodo final de desarchivo del expediente, en este metodo se encuentra la
   * llamada al servicio que se encarga de desarchivar el mismo. Una vez
   * finalizado con el exito el desarchivo se informa que el expediente "tal" se
   * desarchivo con exito y a que usuario le fue enviado.
   * 
   * @throws InterruptedException
   */
  private void continuarDesarchivo() throws InterruptedException {
    // Obtengo el usuario logueado.
    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    /*
     * Llamo al servicio que desarchiva el expediente. en caso de que haya algun
     * problema y no se pueda desarchivar devuelve false, si todo sale bien
     * devuelve true.
     */

    // Envio el estado anterior para que al generar el pase de desarchivo se
    // guarde en el historial el estado anterior.
    // Como estoy desarchivando, el estado anterior es "archivo".
    super.definirMotivo();
    
    // 13-05-2020
    boolean result = rehabilitarExpedienteService.desarchivarExpediente(expedienteElectronico, loggedUsername,
			destinatario, ConstantesWeb.ESTADO_TRAMITACION_PARA_REHABILITACION, motivoExpediente.getValue(), null);
    
    // OLD
    //boolean result = expedienteElectronicoService.desarchivarExpediente(expedienteElectronico,
    //    loggedUsername, destinatario, this.estado.getValue(), motivoExpedienteStr);

    if (result) {
      // Muestro mensaje de desarchivo exitoso por pantalla.
      Messagebox.show(Labels.getLabel("ee.envioComp.msgbox.exp") + " "
          + expedienteElectronico.getCodigoCaratula()
          + Labels.getLabel("ee.envioComp.msgbox.reabilitadoCorrectamente") + " " + destinatario,
          Labels.getLabel("ee.tramitacion.titulo.nuevoExpediente"), Messagebox.OK,
          Messagebox.INFORMATION);
      Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), null));
      this.envioWindow.detach();
    } else {
      // Desasocio la nota para que la pueda volver a cargar.
      List<DocumentoDTO> documentos = expedienteElectronico.getDocumentos();
      documentos.remove(documentoEstandard);
      expedienteElectronico.setDocumentos(documentos);

      // Muestro mensaje de error.
      Messagebox.show(Labels.getLabel("ee.envioComp.msgbox.errorDesarchivarExp"),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Cierra la ventana de envio.
   */
  public void onCancelar() {
    super.onCancelar();
  }

  /**
   * Retorna true si encuentra y asocia el documento y retorna false si no
   * encuentra el documento
   * 
   * @param tipoDocumento
   * @param anioDocumento
   * @param numeroDocumento
   * @param reparticionDocumento
   * @return
   * @throws InterruptedException
   */

  private boolean asociarDocumento(String tipoDocumento, Integer anioDocumento,
      Integer numeroDocumento, String reparticionDocumento) throws InterruptedException {

    boolean retorno = false;

    this.documentoEstandard = documentoManagerService.buscarDocumentoEstandar(tipoDocumento,
        anioDocumento, numeroDocumento, reparticionDocumento);

    List<DocumentoDTO> documentos = this.expedienteElectronico.getDocumentos();

    if (documentoEstandard != null) {

      if (!this.expedienteElectronico.getDocumentos().contains(documentoEstandard)) {
        try {
          String numeroSadeConEspacio = documentoEstandard.getNumeroSade();
          String pathDocumento = "SADE";
          String fileName;
          String pathGedo = BusinessFormatHelper.nombreCarpetaWebDavGedo(numeroSadeConEspacio);
          fileName = pathDocumento + "/" + pathGedo + "/" + documentoEstandard.getNombreArchivo();
          BufferedInputStream file = this.visualizaDocumentoService.visualizarDocumento(fileName);
          // FIXME se harcodea porque ya se encuentra harcodeado en el zul.
          // si se habilitan otros tipos de documento debe obtenerse el acronimo
          // gedo antes de setear.
          // documentoEstandard.setTipoDocAcronimo(NOTA);
          documentoEstandard.setFechaAsociacion(new Date());

          documentos.add(documentoEstandard);
          expedienteElectronico.setDocumentos(documentos);
          retorno = true;
        } catch (DocumentoOArchivoNoEncontradoException e) {
          Messagebox.show(Labels.getLabel("ee.tramitacion.documentoDepurado"),
              Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
              Messagebox.EXCLAMATION);
          retorno = false;
        }
      } else {
        Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociado"),
            Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
            Messagebox.EXCLAMATION);
      }

    } else {
      Messagebox.show(Labels.getLabel("ee.busquedadocumento.noexiste.label"),
          Labels.getLabel("ee.busquedadocumento.noexiste.title"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    }
    return retorno;
  }

  /**
   * Pregunta si se desea enviar el expediente al apoderado, en caso de si lo
   * envio, en caso de no muestra un mensaje por pantalla.
   * 
   * @throws InterruptedException
   */
  private void preguntarEnvioApoderado() throws InterruptedException {
    Messagebox.show(
        Labels.getLabel("ee.licencia.question.value",
            new String[] { usuarioAEnviar.getUsername(), this.usuarioApoderado }),
        Labels.getLabel("ee.licencia.question.title"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              destinatario = usuarioApoderado;
              desarchivarExpediente(tipoDocumento.getText(), anioDocumento.getValue(),
                  numeroDocumento.getValue(), reparticionBusquedaSADE.getText());
              break;
            case Messagebox.NO:
              alert("Seleccione otro destino.");
              break;
            }
          }
        });
  }
  
	private void mensajeValidadorBandboxUsuario(String mensaje) {
		Events.sendEvent(BandBoxUsuarioComposer.EVENT_VALIDAR, inclBandboxUsuario.getChildren().get(0), mensaje);
	}

  /**
   * Valida si los datos ingresados son correctos.
   */
  private void validarDatosBuscarDocumento() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String fechaActual = sdf.format(new Date());
    String anioFormateado = fechaActual.substring(6, 10);

    int anioActual = Integer.parseInt(anioFormateado);
    Integer anioValido = Integer.valueOf(anioActual);

    if (StringUtils.isEmpty(anioDocumento.getText())) {
      throw new WrongValueException(this.anioDocumento,
          Labels.getLabel("ee.historialpase.faltaanio"));
    }

    if (this.anioDocumento.getValue() > anioValido) {
      throw new WrongValueException(this.anioDocumento,
          Labels.getLabel("ee.historialpase.anioInvalido"));
    }

    if (StringUtils.isEmpty(numeroDocumento.getText())) {
      throw new WrongValueException(this.numeroDocumento,
          Labels.getLabel("ee.historialpase.faltatnumero"));
    }

    if (StringUtils.isEmpty(reparticionBusquedaSADE.getText())) {
      throw new WrongValueException(this.reparticionBusquedaSADE,
          Labels.getLabel("ee.historialpase.faltaReparcicion"));
    }

    if (usuarioSeleccionado == null) {

    	this.mensajeValidadorBandboxUsuario(Labels.getLabel("ee.desarchivar.expediente.seleccionarusuario"));
//      throw new WrongValueException(this.usuario,
//          Labels.getLabel("ee.desarchivar.expediente.seleccionarusuario"));
    }
    super.definirMotivo();
    super.validarMotivo();

    if (this.estado.getText().equals(ESTADO_GUARDA_TEMPORAL)) {
      throw new WrongValueException(this.estado,
          Labels.getLabel("ee.desarchivar.expediente.estado.invalido"));
    }

    if (this.estado == null || "".equals(this.estado.getText())) {
      throw new WrongValueException(this.estado,
          Labels.getLabel("ee.desarchivar.expediente.estado"));
    }
  }

  /**
   * Se guarda el estado que se encontraba seleccionado antes de seleccionar
   * otro y este último y se los setea en variables del WF para poder mantener
   * una lógica con la pantalla anterior.
   */

  /**
   * Valida si el usuario esta de licencia, si es asi devuelve un string con el
   * usuario apoderado, sino devuelve null
   * 
   * @param username
   * @return
   */
  private String validarLicencia(String username) {
    return this.usuariosSADEService.getDatosUsuario(username).getApoderado();
  }

  /**
   * 
   * @param nameTransition
   *          : nombre de la transición que voy a usar para la proxima tarea
   * @param usernameDerivador
   *          : usuario que va a tener asignada la tarea
   */
  public void signalExecution(String nameTransition, String usernameDerivador) {
    processEngine.getExecutionService()
        .signalExecutionById(this.expedienteElectronico.getIdWorkflow(), nameTransition);
  }

  /**
   * 
   * @param name
   *          : nombre de la variable que quiero setear
   * @param value
   *          : valor de la variable
   */

  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService()
        .setVariable(this.expedienteElectronico.getIdWorkflow(), name, value);
  }

  public Combobox getEstado() {
    return estado;
  }

  public void setEstado(Combobox estado) {
    this.estado = estado;
  }

//  public Combobox getUsuario() {
//    return usuario;
//  }
//
//  public void setUsuario(Combobox usuario) {
//    this.usuario = usuario;
//  }

  public ExpedienteElectronicoDTO getExpedienteElectronico() {
    return expedienteElectronico;
  }

  public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
    this.expedienteElectronico = expedienteElectronico;
  }

  public String getEstadoSeleccionado() {
    return estadoSeleccionado;
  }

  public void setEstadoSeleccionado(String estadoSeleccionado) {
    this.estadoSeleccionado = estadoSeleccionado;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public Bandbox getReparticionBusquedaSADE() {
    return reparticionBusquedaSADE;
  }

  public void setReparticionBusquedaSADE(Bandbox reparticionBusquedaSADE) {
    this.reparticionBusquedaSADE = reparticionBusquedaSADE;
  }

  public DocumentoGedoService getDocumentoGedoService() {
    return documentoGedoService;
  }

  public void setDocumentoGedoService(DocumentoGedoService documentoGedoService) {
    this.documentoGedoService = documentoGedoService;
  }

  public Textbox getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(Textbox tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public Intbox getAnioDocumento() {
    return anioDocumento;
  }

  public void setAnioDocumento(Intbox anioDocumento) {
    this.anioDocumento = anioDocumento;
  }

  public Intbox getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(Intbox numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public DocumentoManagerService getDocumentoManagerService() {
    return documentoManagerService;
  }

  public void setDocumentoManagerService(DocumentoManagerService documentoManagerService) {
    this.documentoManagerService = documentoManagerService;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }
  
  public UsuarioReducido getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioReducido usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public class EnvioDesarchivarListener implements EventListener<Event>{

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(BandBoxUsuarioComposer.ON_SELECT_USUARIO)) {
				setUsuarioSeleccionado((UsuarioReducido) event.getData());
			}
		}
  	
  }
}
