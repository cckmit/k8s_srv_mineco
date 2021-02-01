package com.egoveris.te.base.composer;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.CaratulacionService;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.ObtenerReparticionServices;
import com.egoveris.te.base.service.SolicitudExpedienteService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TrataReparticionService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.DatosSolicitud2MapTransformer;
import com.egoveris.te.base.util.TipoDocumentoUtils;
import com.egoveris.te.base.util.ValidacionesUtils;
import com.egoveris.te.base.util.ValidadorDeCuit;
import com.egoveris.te.base.vm.SolicitanteDireccionVM;
import com.egoveris.te.model.util.MailUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.SQLGrammarException;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.zkoss.bind.BindUtils;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;

/**
 *
 * @author Sebastian Roidzaid
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CaratularExternoComposer extends ValidarFormularioControladoComposer {

  private final static Logger logger = LoggerFactory.getLogger(CaratularExternoComposer.class);
  private Window caratularExternoWindow;
  private Textbox motivoInternoExpediente;
  private Textbox motivoExternoExpediente;
  private Radio persona;
  private Radio empresa;
  private Bandbox codigoTrata;
  private TrataDTO selectedTrata;
  private Textbox descripcion;
  private Combobox tipoDocumento;
  private Textbox numeroDocumento;
  private Textbox apellido;

  private Textbox segundoApellido;

  private Textbox tercerApellido;

  private Textbox razonSocial;
  private Textbox nombre;

  private Textbox segundoNombre;

  private Textbox tercerNombre;

  private Textbox email;
  private Textbox telefono;

  private Longbox cuitCuilTipo;

  private Longbox cuitCuilDocumento;

  private Longbox cuitCuilVerificador;

  private Checkbox noDeclaraNoPosee;

  private Row cuilcuit;

  private Textbox direccion;

  private Textbox piso;

  private Textbox departamento;

  private Textbox codigoPostal;

  private Button guardar;

  protected Task workingTask = null;
  protected ProcessEngine processEngine;
  private List<ExpedienteMetadataDTO> expedienteMetadata;
  private boolean esEE = false;
  private ReparticionBean rep;
  private List<String> tiposDocumentos;
  private List<TrataDTO> tratas;
  private List<TrataDTO> tratasSeleccionadas = new ArrayList();
  private String trataSel;
  private boolean existe;
  private AnnotateDataBinder binder;

  private String selectedTiposDocumentos;

  private String cuitCuil;

  /**
   * Defino los servicios que voy a utilizar
   */
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  @WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
  private HistorialOperacionService historialOperacionService;
  @WireVariable(ConstantesServicios.SOLICITUD_EXPEDIENTE_SERVICE)
  private SolicitudExpedienteService solicitudExpedienteService;
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;
  @WireVariable(ConstantesServicios.CARATULACION_SERVICE)
  private CaratulacionService caratulador;
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;

  private SolicitudExpedienteDTO solicitud;
  private String usuarioSolicitante = null;
  @WireVariable(ConstantesServicios.TRATA_REPARTICION_SERVICE)
  private TrataReparticionService trataReparticionService;
  @WireVariable(ConstantesServicios.OBTENER_REPARTICION_SERVICE)
  private ObtenerReparticionServices obtenerReparticionService;
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;
  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;
  private boolean permiso = false;

  private Window formularioControladoWindows;

  private Usuario user;

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    component.addEventListener(Events.ON_NOTIFY, new CaratularExternoOnNotifyWindowListener(this));
    component.addEventListener(Events.ON_USER, new CaratularExternoOnNotifyWindowListener(this));
    formularioControladoWindows.addEventListener(Events.ON_NOTIFY,
        new CaratularExternoOnNotifyWindowListener(this));

    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));

    String username = Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
    this.rep = obtenerReparticionService.buscarReparticionByUsuario(username);
    this.usuarioSolicitante = username;

    user = usuariosSADEService.getDatosUsuario(username);

    this.tratas = this.getTratasServices();
    this.tratasSeleccionadas = new ArrayList<>(this.tratas);
    this.existe = true;
    this.trataSel = "";
    this.apellido.setDisabled(false);
    this.nombre.setDisabled(false);
    this.razonSocial.setDisabled(true);
    this.razonSocial.setValue(null);
    this.motivoInternoExpediente.setFocus(true);
    this.binder = new AnnotateDataBinder(component);
    this.binder.loadAll();

    this.logger(": catatularexternocomposer", this.toString());

    try {
      Assert.assertNotNull("No esta inicializada la variable selectedTask ",
          ((Task) component.getDesktop().getAttribute("selectedTask")));
    } catch (AssertionFailedError assertionFailedError) {
      this.logger(": Hubo un error en el metodo doAfterCompose con la excepción "
          + new Exception(assertionFailedError.getMessage()).getMessage(), null);
    }

    this.logger(": username",
        Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString());
    this.logger(": reparticion ", this.rep.toString());
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public List<String> getTiposDocumentos() {
    if (this.tiposDocumentos == null) {
      this.tiposDocumentos = TipoDocumentoUtils.getTiposDocumentos();
      ;
    }

    return this.tiposDocumentos;
  }

  public void setTiposDocumentos(List<String> tiposDocumentos) {
    this.tiposDocumentos = tiposDocumentos;
  }

  public SolicitudExpedienteService getSolicitudExpedienteService() {
    return solicitudExpedienteService;
  }

  public void setSolicitudExpedienteService(
      SolicitudExpedienteService solicitudExpedienteService) {
    this.solicitudExpedienteService = solicitudExpedienteService;
  }

  public void onClickExpedienteInterno() {
    this.tipoDocumento.setDisabled(true);
    this.numeroDocumento.setDisabled(true);
    this.nombre.setDisabled(true);
    this.segundoNombre.setDisabled(true);
    this.tercerNombre.setDisabled(true);
  }

  public void onClickExpedienteExterno() {
    this.tipoDocumento.setDisabled(false);
    this.numeroDocumento.setDisabled(false);
    this.apellido.setDisabled(false);
    this.segundoApellido.setDisabled(false);
    this.tercerApellido.setDisabled(false);
  }

  public Textbox getMotivoInternoExpediente() {
    return motivoInternoExpediente;
  }

  public void setMotivoInternoExpediente(Textbox motivoInternoExpediente) {
    this.motivoInternoExpediente = motivoInternoExpediente;
  }

  public Textbox getMotivoExternoExpediente() {
    return motivoExternoExpediente;
  }

  public void setMotivoExternoExpediente(Textbox motivoExternoExpediente) {
    this.motivoExternoExpediente = motivoExternoExpediente;
  }

  public Textbox getEmail() {
    return email;
  }

  public void setEmail(Textbox email) {
    this.email = email;
  }

  public Textbox getTelefono() {
    return telefono;
  }

  public void setTelefono(Textbox telefono) {
    this.telefono = telefono;
  }

  public Textbox getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(Textbox descripcion) {
    this.descripcion = descripcion;
  }

  public String getSelectedTiposDocumentos() {
    if (null == selectedTiposDocumentos) {
      return "";
    }
    return selectedTiposDocumentos;
  }

  public void setSelectedTiposDocumentos(String selectedTiposDocumentos) {
    this.selectedTiposDocumentos = selectedTiposDocumentos;
  }

  public Combobox getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(Combobox tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public Textbox getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(Textbox numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public Textbox getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(Textbox razonSocial) {
    this.razonSocial = razonSocial;
  }

  public List<TrataDTO> getTratas() {
    return tratas;
  }

  public List<TrataDTO> getTratasServices() {
    // return this.trataService.buscarTratasManuales(true); OLD
    return this.trataService.buscarTratasByTipoExpediente(true);
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

  public SolicitudExpedienteDTO getSolicitud() {
    return solicitud;
  }

  public void setSolicitud(SolicitudExpedienteDTO solicitud) {
    this.solicitud = solicitud;
  }

  public Radio getPersona() {
    return persona;
  }

  public void setPersona(Radio persona) {
    this.persona = persona;
  }

  public Radio getEmpresa() {
    return empresa;
  }

  public void setEmpresa(Radio empresa) {
    this.empresa = empresa;
  }

  public void onGuardarExpediente() throws InterruptedException {
    if (!this.permiso) {
      validarDatosDelFormulario();
      this.mostrarForegroundBloqueanteToken();
      Events.echoEvent("onUser", this.self, "guardarExpediente");
    } else {
      Clients.clearBusy();

      Messagebox.show(
          Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
              new String[] { rep.getCodigo().trim() }),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.ERROR);

      return;
    }
  }

  private void mostrarForegroundBloqueanteToken() {
    Clients.showBusy(Labels.getLabel("ee.nuevoexpediente.popup.label.caratularExterno.value"));
  }

  public void guardarExpediente() throws InterruptedException {

    try {
      guardar.setDisabled(true);

      String username = Executions.getCurrent().getSession()
          .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
      IngresoSolicitudExpedienteDTO ingresoSolicitudExpediente;
      DatosSolicitud2MapTransformer transformer = new DatosSolicitud2MapTransformer();
      String motivoSolicitud = this.motivoInternoExpediente.getValue();
      TrataDTO trata = null;

      if (motivoSolicitud == null || motivoSolicitud.isEmpty()) {
        motivoSolicitud = this.motivoExternoExpediente.getValue();
      }

      if (codigoTrata.getValue() != null) {
        trata = trataService.buscarTrataByCodigo(this.codigoTrata.getValue());
      }

      ingresoSolicitudExpediente = (IngresoSolicitudExpedienteDTO) solicitudExpedienteService
          .generarSolicitudExpediente((Map<String, Object>) transformer.transform(this),
              processEngine, trata, this.descripcion.getValue(), expedienteMetadata, username,
              motivoSolicitud, true);

      // Save direccion
      SolicitudExpedienteDTO solicitudExpedienteDTO = solicitudExpedienteService.obtenerSolitudByIdSolicitud(ingresoSolicitudExpediente.getSolicitudExpediente().getId());
      SolicitanteDireccionVM.saveDireccion(solicitudExpedienteDTO.getSolicitante().getId());
      
      if (trata != null && trata.getAcronimoDocumento() != null
          && trata.getAcronimoDocumento().length() > 0) {
        crearFormularioControlado(ingresoSolicitudExpediente,
            (Window) caratularExternoWindow.getParent(), trata);
      } else {
        ExpedienteElectronicoDTO expedienteElectronico = ingresoSolicitudExpediente
            .getExpedienteElectronico();

        if (expedienteElectronico.getEsReservado()) {
          expedienteElectronicoService.actualizarReservaEnLaTramitacion(expedienteElectronico,
              username);
        }
        
        if (logger.isDebugEnabled()) {
          logger.debug("on guardarExpediente request "
              + Executions.getCurrent().getNativeRequest().toString());
        }

        // ***********************************************************************
        // ** JIRA BISADE-2515:
        // https://quark.everis.com/jira/browse/BISADE-2515
        // ** - Se crea el parámetro "cacheQueries" para la consulta de
        // inbox no cachee cambios de statemachine.
        // ***********************************************************************
        Executions.getCurrent().getDesktop().setAttribute("cacheQueries", new Boolean(false));

        // DG_DF-EE-Diseño_Funcional
        // -Modificaciones_En_EE_Formularios_Controlados
        // Se mostrara la caratula generada solo despues de haber
        // completado el formulario controlado.
        this.esEE = true;
        mostrarCaratulaGenerada(expedienteElectronico);
      }

    } catch (DynFormException de) {
      logger.error("Se ha producido un error generando el documento.", de);
      Messagebox.show(Labels.getLabel("ee.caratularExtComp.msgbox.errorGenDoc"),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    } catch (SQLGrammarException de) {
      logger.error("Se ha producido un error generando el documento.", de);
      Messagebox.show(Labels.getLabel("ee.caratularExtComp.msgbox.errorGenDoc"),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    } catch (WrongValueException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.error"), Messagebox.OK,
          Messagebox.ERROR);
    } catch (RemoteAccessException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK,
          Messagebox.ERROR);
    } catch (Exception e) {
      String error = Labels.getLabel("ee.servicio.gedo.errorComunicacionGEDO");
      logger.error(error, e);
      Messagebox.show(error, Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    } finally {
      Clients.clearBusy();
      closeAssociatedWindow();
    }
  }

  /**
   * Valida lo datos requeridos para generar el Expediente.
   */
  private void validarDatosDelFormulario() {

    if ((getMotivoExternoExpediente().getValue() == null)
        || (getMotivoExternoExpediente().getValue().trim().equals(""))) {
      getMotivoExternoExpediente().focus();
      throw new WrongValueException(getMotivoExternoExpediente(),
          "Debe ingresar un motivo");
    }

    verificarExistencia();

    if ((this.trataSel == null) || this.trataSel.trim().equals("")) {
      this.codigoTrata.focus();
      throw new WrongValueException(this.codigoTrata,
          Labels.getLabel("ee.nuevoexpediente.faltacodigotrata"));
    } else {
      if (!this.existe) {
        throw new WrongValueException(this.codigoTrata,
            Labels.getLabel("ee.caratulas.caratulaNoExiste"));
      }
    }

    if (this.trataService.buscarTrataByCodigo(this.selectedTrata.getCodigoTrata()) == null) {
      throw new WrongValueException(this.codigoTrata,
          Labels.getLabel("ee.nuevoexpediente.tratainactiva"));
    }

    TrataDTO trata = trataService.buscarTrataByCodigo(this.codigoTrata.getValue());
    if (!trata.getEsExterno()) {
      throw new WrongValueException(this.codigoTrata, "La trata \"" + trata.getCodigoTrata()
          + "\" tiene que caratularse como Carátula Externa.");
    }

    if ((this.descripcion.getValue() == null) || (this.descripcion.getValue().equals(""))) {
      this.descripcion.focus();
      throw new WrongValueException(this.descripcion,
          Labels.getLabel("ee.nuevoexpediente.faltadescripciontrata"));
    }
    
    /*
    if (getPersona().isChecked() && ((getTipoDocumento().getValue() == null)
        || (getTipoDocumento().getValue().equals("")))) {
      throw new WrongValueException(this.tipoDocumento,
          Labels.getLabel("ee.nuevasolicitud.faltatipodocumento"));
    }
    */
    
    /*
    if (getPersona().isChecked() && ((getNumeroDocumento().getValue() == null))) {
      throw new WrongValueException(this.numeroDocumento,
          Labels.getLabel("ee.nuevasolicitud.faltanumerodocumento"));
    }

    if (getEmpresa().isChecked()
        && (getNumeroDocumento().getValue() == null || "".equals(getNumeroDocumento()))
        && (getTipoDocumento().getValue() != null && !getTipoDocumento().getValue().equals(""))) {
      throw new WrongValueException(this.numeroDocumento,
          Labels.getLabel("ee.nuevasolicitud.faltanumerodocumento"));
    }
    */
    
    if (StringUtils.isBlank(getNumeroDocumento().getValue())) {
    	throw new WrongValueException(this.numeroDocumento,
    	          Labels.getLabel("ee.nuevasolicitud.faltanumerodocumento"));
    }

    /*
    if (getEmpresa().isChecked()
        && (getNumeroDocumento().getValue() != null && !"".equals(getNumeroDocumento().getValue()))
        && (getTipoDocumento().getValue() == null || getTipoDocumento().getValue().equals(""))) {
      throw new WrongValueException(this.tipoDocumento,
          Labels.getLabel("ee.nuevasolicitud.faltatipodocumento"));
    }
    */

    // TODO PASAR LAS RESTRICCIONES A LA BBDD O *AL MENOS* A UNA PROPIEDAD
    // DEL ENUM!!!!!!!!!!
    // TODO REESTRUCTURAR IF!!!!
    // TODO ¿Que diablos quiso decir el TODO anterior?
    /*
    if (getPersona().isChecked()
        && (getTipoDocumento().getValue().equals("LC - LIBRETA CIVICA"))) {
      if (getNumeroDocumento().getValue().toString().length() > 7) {
        getNumeroDocumento().focus();
        throw new WrongValueException(getNumeroDocumento(),
            Labels.getLabel("ee.nuevasolicitud.lcincorrecto"));
      }
    }
    */
    if ((getPersona().isChecked())
        && ((getApellido().getValue() == null) || (getApellido().getValue().equals("")))) {
      throw new WrongValueException(this.apellido,
          Labels.getLabel("ee.nuevasolicitud.faltaapellido"));
    }

    if ((getPersona().isChecked())
        && ((getNombre().getValue() == null) || (getNombre().getValue().equals("")))) {
      throw new WrongValueException(this.nombre,
          Labels.getLabel("ee.nuevasolicitud.faltanombres"));
    }

    if ((getEmpresa().isChecked())
        && ((getRazonSocial().getValue() == null) || (getRazonSocial().getValue().equals("")))) {
      throw new WrongValueException(this.razonSocial,
          Labels.getLabel("ee.nuevasolicitud.faltarazonsocial"));
    }

    if ((getEmail().getValue() == null) || (getEmail().getValue().equals(""))) {
    } else {
      boolean valor = MailUtil.validateMail(getEmail().getValue().toString());

      if (valor == false) {
        throw new WrongValueException(this.email, Labels.getLabel("ee.nuevasolicitud.erroremail"));
      }
    }

    if (!this.telefono.getValue().equals("")) {
      boolean valor = ValidacionesUtils.poseeSoloNumeros(this.telefono.getValue());

      if (!valor) {
        throw new WrongValueException(this.telefono,
            Labels.getLabel("ee.nuevasolicitud.errorTelefono"));
      }
    }
    /*
    if (!noDeclaraNoPosee.isChecked()) {
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
      String cadena = this.cuitCuilDocumento.getValue().toString();
      if (this.cuitCuilDocumento.getValue() != null
          && this.cuitCuilDocumento.getValue().toString().length() != 8) {
        while (cadena.length() != 8) {
          cadena = "0" + cadena;
        }
        this.cuitCuilDocumento.setValue(new Long(cadena));
      }

      if (this.cuitCuilVerificador.getValue() != null
          && this.cuitCuilVerificador.getValue().toString().length() != 1) {
        throw new WrongValueException(this.cuitCuilVerificador,
            Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
      }
      this.setCuitCuil(this.cuitCuilTipo.getValue().toString() + cadena
          + this.cuitCuilVerificador.getValue().toString());
      ValidadorDeCuit validadorDeCuit = new ValidadorDeCuit();
      try {
        validadorDeCuit.validarNumeroDeCuit(getCuitCuil());
      } catch (NegocioException e) {
        throw new WrongValueException(this.cuilcuit, e.getMessage());
      }
    }
    */

    /*
    if ((this.direccion.getValue() == null) || (this.direccion.getValue().equals(""))) {
      this.direccion.focus();
      throw new WrongValueException(this.direccion,
          Labels.getLabel("ee.nuevoexpediente.faltadomicilio"));
    }

    if ((this.codigoPostal.getValue() == null) || (this.codigoPostal.getValue().equals(""))) {
      this.codigoPostal.focus();
      throw new WrongValueException(this.codigoPostal,
          Labels.getLabel("ee.nuevoexpediente.faltacodigopostal"));
    }
    */

    String direccion = this.direccion.getValue();
    this.direccion.setValue(direccion.toUpperCase());
  }

  /*
  public void onSelectTipoDocumento() {
    if (getSelectedTiposDocumentos().equals("DU - DOCUMENTO UNICO")
        && !getNoDeclaraNoPosee().isChecked()) {
      this.numeroDocumento.setValue(this.cuitCuilDocumento.getValue());
    }
  }
  */

  @SuppressWarnings("unchecked")
  public void onSelectCheck() {
    if (getNoDeclaraNoPosee().isChecked()) {
      getCuitCuilDocumento().setValue(null);
      getCuitCuilVerificador().setValue(null);
      getCuitCuilTipo().setValue(null);

      getCuitCuilDocumento().setDisabled(true);
      getCuitCuilVerificador().setDisabled(true);
      getCuitCuilTipo().setDisabled(true);
    } else {
      getCuitCuilDocumento().setDisabled(false);
      getCuitCuilVerificador().setDisabled(false);
      getCuitCuilTipo().setDisabled(false);
    }
  }

  public void onCancelar() {
    this.closeAssociatedWindow();
  }

  public Window getCaratularExternoWindow() {
    return caratularExternoWindow;
  }

  public void setCaratularExternoWindow(Window caratularExternoWindow) {
    this.caratularExternoWindow = caratularExternoWindow;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  @Deprecated
  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
        value);
  }

  public void setVariablesWorkFlow(Map<String, Object> variables) {
    workFlowService.setVariables(processEngine, this.workingTask.getExecutionId(), variables);
  }

  public void signalExecution(String nameTransition, String usernameDerivador) {
    Map<String, Object> variables = new HashMap<>();
    variables.put(ConstantesWeb.USUARIO_SOLICITANTE, usernameDerivador);
    variables.put(ConstantesWeb.USUARIO_SELECCIONADO, usernameDerivador);
    variables.put(ConstantesWeb.GRUPO_SELECCIONADO, null);
    setVariablesWorkFlow(variables);
    processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
        nameTransition);
  }

  public CaratulacionService getCaratulador() {
    return caratulador;
  }

  public void setCaratulador(CaratulacionService caratulador) {
    this.caratulador = caratulador;
  }

  public void onClickApellidoNombres() {
    this.apellido.setDisabled(false);
    this.nombre.setDisabled(false);

    this.segundoApellido.setDisabled(false);
    this.segundoNombre.setDisabled(false);

    this.tercerApellido.setDisabled(false);
    this.tercerNombre.setDisabled(false);

    this.razonSocial.setDisabled(true);
    this.razonSocial.setValue(null);
  }

  public void onClickEmpresa() {
    this.apellido.setDisabled(true);
    this.apellido.setValue(null);
    this.nombre.setDisabled(true);
    this.nombre.setValue(null);

    this.segundoApellido.setDisabled(true);
    this.segundoApellido.setValue(null);
    this.segundoNombre.setDisabled(true);
    this.segundoNombre.setValue(null);

    this.tercerApellido.setDisabled(true);
    this.tercerApellido.setValue(null);
    this.tercerNombre.setDisabled(true);
    this.tercerNombre.setValue(null);

    this.razonSocial.setDisabled(false);
  }

  public void mostrarCaratulaGenerada(ExpedienteElectronicoDTO expedienteElectronico)
      throws InterruptedException {

    Messagebox.show(
        Labels.getLabel("ee.caratularExtComp.msgbox.seGeneroExp") + " "
            + expedienteElectronico.getCodigoCaratula(),
        Labels.getLabel("ee.tramitacion.titulo.nuevoExpediente"), Messagebox.OK,
        Messagebox.INFORMATION

    );
  }

  public void onChanging$codigoTrata(InputEvent e) {
    this.cargarTratas(e);
  }

  public void onChange$tipoDocumento(InputEvent e) {

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
      this.tratasSeleccionadas = new ArrayList<>(this.tratas);
    }

    this.binder.loadAll();
  }

  public void onBlur$codigoTrata() {

    TrataDTO trata = null;

    if ((this.codigoTrata.getValue() != null) && !this.codigoTrata.getValue().trim().equals("")) {
      this.trataSel = this.codigoTrata.getValue().toUpperCase();
      trata = trataService.buscarTrataByCodigo(this.codigoTrata.getValue());
    }

    this.verificarExistencia();
    this.permiso = false;

    if (trata != null) {
      if (!trataReparticionService.validarPermisoReparticion(trata, this.rep.getCodigo(),
          this.user)) {
        this.permiso = true;

        Clients.clearBusy();
        throw new WrongValueException(this.codigoTrata,
            Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
                new String[] { user.getCodigoReparticion() }));
      } else {

        List<MetadataDTO> metadatas = trata.getDatoVariable();
        expedienteMetadata = new ArrayList<>(metadatas.size());

        for (int i = 0; metadatas.size() > i; i++) {
          ExpedienteMetadataDTO temp = new ExpedienteMetadataDTO();
          temp.setNombre(metadatas.get(i).getNombre());
          temp.setObligatoriedad(metadatas.get(i).isObligatoriedad());
          temp.setOrden(metadatas.get(i).getOrden());
          temp.setTipo(metadatas.get(i).getTipo());
          expedienteMetadata.add(temp);
        }
      }
      if (!trata.getEsExterno()) {
        throw new WrongValueException(this.codigoTrata, "La trata \"" + trata.getCodigoTrata()
            + "\" tiene que caratularse como Carátula Externa.");
      }
    }

  }

  private void verificarExistencia() {
    this.existe = false;
    if (!(this.codigoTrata == null) || (this.codigoTrata.getValue() == "")
        || this.codigoTrata.getValue().isEmpty()) {
      if (this.tratas != null) {
        for (TrataDTO aux : this.tratas) {
          if (aux.getCodigoTrata().toUpperCase().trim().equals(this.codigoTrata.getValue())) {
            this.existe = true;
            break;
          }
        }
      }
    }
  }

  public void onSelect$trataListbox() {

    mostrarForegroundBloqueanteToken();
    Events.echoEvent(Events.ON_USER, this.self, "selectTrata");
  }

  private void seleccionarTrata() {
    // Si trata no tiene caratula entonces pregunto si tiene datos propios -
    // BEGIN
    try {
      if (this.selectedTrata != null) {

        Assert.assertTrue("seleccionTrata ", this.selectedTrata.toString() != null);
        trataSel = this.selectedTrata.getCodigoTrata();
        this.codigoTrata.setValue(trataSel.toUpperCase());
        Assert.assertTrue("codigoTrata ", this.codigoTrata != null);
        this.binder.loadAll();
        this.codigoTrata.close();
        this.onBlur$codigoTrata();
      }
    } catch (AssertionFailedError assertionFailedError) {
      logger.error(assertionFailedError.getMessage());
      this.logger("Hubo un error en el metodo onSelect$trataListbox con la excepción  "
          + new Exception(assertionFailedError.getMessage()).getMessage(), null);
    } finally {
      Clients.clearBusy();
    }
  }

  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  public void setTratas(List<TrataDTO> tratas) {
    this.tratas = tratas;
  }

  public List<TrataDTO> getTratasSeleccionadas() {
    return tratasSeleccionadas;
  }

  public void setTratasSeleccionadas(List<TrataDTO> tratasSeleccionadas) {
    this.tratasSeleccionadas = tratasSeleccionadas;
  }

  public Bandbox getCodigoTrata() {
    return codigoTrata;
  }

  public void setCodigoTrata(Bandbox codigoTrata) {
    this.codigoTrata = codigoTrata;
  }

  public void logger(String message, String toString) {
    logger.debug(" ThreadId=" + Thread.currentThread().getId() + " toString " + toString + " "
        + message + " ");
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

  public void setNoDeclaraNoPosee(Checkbox noDeclaraNoPosee) {
    this.noDeclaraNoPosee = noDeclaraNoPosee;
  }

  public Checkbox getNoDeclaraNoPosee() {
    return noDeclaraNoPosee;
  }

  public void setCuilcuit(Row cuilcuit) {
    this.cuilcuit = cuilcuit;
  }

  public Row getCuilcuit() {
    return cuilcuit;
  }

  public void setSegundoApellido(Textbox segundoApellido) {
    this.segundoApellido = segundoApellido;
  }

  public Textbox getSegundoApellido() {
    return segundoApellido;
  }

  public void setTercerApellido(Textbox tercerApellido) {
    this.tercerApellido = tercerApellido;
  }

  public Textbox getTercerApellido() {
    return tercerApellido;
  }

  public void setSegundoNombre(Textbox segundoNombre) {
    this.segundoNombre = segundoNombre;
  }

  public Textbox getSegundoNombre() {
    return segundoNombre;
  }

  public void setTercerNombre(Textbox tercerNombre) {
    this.tercerNombre = tercerNombre;
  }

  public Textbox getTercerNombre() {
    return tercerNombre;
  }

  public void setApellido(Textbox apellido) {
    this.apellido = apellido;
  }

  public Textbox getApellido() {
    return apellido;
  }

  public void setNombre(Textbox nombre) {
    this.nombre = nombre;
  }

  public Textbox getNombre() {
    return nombre;
  }

  public void setDireccion(Textbox direccion) {
    this.direccion = direccion;
  }

  public Textbox getDireccion() {
    return direccion;
  }

  public void setPiso(Textbox piso) {
    this.piso = piso;
  }

  public Textbox getPiso() {
    return piso;
  }

  public void setCodigoPostal(Textbox codigoPostal) {
    this.codigoPostal = codigoPostal;
  }

  public Textbox getCodigoPostal() {
    return codigoPostal;
  }

  public void setDepartamento(Textbox departamento) {
    this.departamento = departamento;
  }

  public Textbox getDepartamento() {
    return departamento;
  }

  public void setCuitCuil(String cuitCuil) {
    this.cuitCuil = cuitCuil;
  }

  public String getCuitCuil() {
    return cuitCuil;
  }

  public void setGuardar(Button guardar) {
    this.guardar = guardar;
  }

  public Button getGuardar() {
    return guardar;
  }

  final class CaratularExternoOnNotifyWindowListener implements EventListener {
    private CaratularExternoComposer composer;

    public CaratularExternoOnNotifyWindowListener(
        CaratularExternoComposer caratularExternoComposer) {
      this.composer = caratularExternoComposer;
    }

    public void onEvent(Event event) throws Exception {

      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          Map<String, Object> map = (Map<String, Object>) event.getData();
          Boolean origenClose = Boolean.FALSE;
          Boolean origenCancel = Boolean.FALSE;

          String origen = (String) map.get("origen");
          if (StringUtils.equals(origen, ConstantesWeb.COMPOSER)) {
            if (!(Boolean) map.get("beforeExecuteTask")) {
              Messagebox.show((String) map.get("msg"),
                  Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK,
                  Messagebox.INFORMATION);
            }
          }
          origenClose = (Boolean) map.get(Events.ON_CLOSE);
          origenCancel = (Boolean) map.get(Events.ON_CANCEL);

          if (origenClose == Boolean.TRUE) {
            Events.sendEvent(this.composer.getCaratularExternoWindow(),
                new Event(Events.ON_CLOSE));
          }

          if (origenCancel == Boolean.TRUE) {
            Events.sendEvent(this.composer.getCaratularExternoWindow(),
                new Event(Events.ON_CLOSE));
          }
        } else {

        }
      }
      if (event.getName().equals(Events.ON_CLOSE)) {

        Events.sendEvent(this.composer.getCaratularExternoWindow(), new Event(Events.ON_CLOSE));

      }
      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("guardarExpediente")) {
          this.composer.guardarExpediente();
        }
        if (event.getData().equals("selectTrata")) {
          this.composer.seleccionarTrata();
        }
      }
    }
  }
}
