package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;

import com.egoveris.te.base.model.DocumentoDeIdentidadDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.util.MailUtil;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenerarNuevaSolicitudComposer extends AbstractSolicitudComposer {

  private static Logger logger = LoggerFactory.getLogger(GenerarNuevaSolicitudComposer.class);

  /**
  * 
  */
  private static final long serialVersionUID = -5292797902756896204L;
  @Autowired
  private Window nuevaSolicitudWindow;
  @Autowired
  private Window envioSolicitudWindow;
  private boolean existe;
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;

  public Long idTrataSugerida = 0l;

  protected AnnotateDataBinder binder;

  public Window getNuevaSolicitudWindow() {
    return nuevaSolicitudWindow;
  }

  public void setNuevaSolicitudWindow(Window nuevaSolicitudWindow) {
    this.nuevaSolicitudWindow = nuevaSolicitudWindow;
  }

  public Window getEnvioSolicitudWindow() {
    return envioSolicitudWindow;
  }

  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(c); 
    super.tratas = this.getTratasServices();
    this.tratasSeleccionadas = new ArrayList<TrataDTO>(this.tratas);
    c.addEventListener(Events.ON_NOTIFY, new GenerarNuevaSolicitudOnNotifyWindowListener(this));
    c.addEventListener(Events.ON_USER, new GenerarNuevaSolicitudOnNotifyWindowListener(this));
    idTrataSugerida = 0l;
    this.existe = true;
    binder.loadAll();
  }

  public void setEnvioSolicitudWindow(Window envioSolicitudWindow) {
    this.envioSolicitudWindow = envioSolicitudWindow;
  }

  /*
  @SuppressWarnings("unchecked")
  public void onSelectDocumento() {
    if (getSelectedTiposDocumentos().equals("DU - DOCUMENTO UNICO")
        && !getNoDeclaraNoPosee().isChecked()) {
      getNumeroDocumento().setValue(getCuitCuilDocumento().getValue());
    }
  }
  */

  @SuppressWarnings("unchecked")
  public void onSelectCheck() {
    if (getNoDeclaraNoPosee().isChecked()) {
      getCuitCuilDocumento().setValue(null);
      getCuitCuilTipo().setValue(null);
      getCuitCuilVerificador().setValue(null);

      getCuitCuilDocumento().setDisabled(true);
      getCuitCuilVerificador().setDisabled(true);
      getCuitCuilTipo().setDisabled(true);
    } else {
      getCuitCuilDocumento().setDisabled(false);
      getCuitCuilVerificador().setDisabled(false);
      getCuitCuilTipo().setDisabled(false);
    }
  }

  public void onClick$enviarSolicitud() throws InterruptedException {

    if (this.codigoTrata.getValue() != null && this.existe && !StringUtils.isBlank(this.codigoTrata.getValue())) {
      TrataDTO trata = trataService.buscarTrataByCodigo(this.codigoTrata.getValue());
      if (trata != null) {
        idTrataSugerida = trata.getId();
      }
    }

    validarDatosDelFormulario();

    SolicitudExpedienteDTO solicitud = new SolicitudExpedienteDTO();
    SolicitanteDTO solicitante = new SolicitanteDTO();

    String loggeduser = (String) Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    solicitud.setFechaCreacion(new Date());
    solicitud.setUsuarioCreacion(loggeduser);
    solicitud.setIdTrataSugerida(idTrataSugerida);// Funcionalidad nueva

    DocumentoDeIdentidadDTO documentoIdentidad = new DocumentoDeIdentidadDTO();

    documentoIdentidad.setTipoDocumento(null);
    documentoIdentidad.setNumeroDocumento(getNumeroDocumento().getValue());

    solicitante.setDocumento(documentoIdentidad);

    if (getExpedienteInterno().isChecked()) {
      solicitud.setEsSolicitudInterna(true);
      solicitud.setMotivo(getMotivoInterno().getValue());
      solicitud.setMotivoExterno(getMotivoExterno().getValue());

      solicitante.setEmail(getEmail().getValue());
      if (("".equals(getTelefono().getValue())) || (getTelefono().getValue() == null)) {
        solicitante.setTelefono("");
      } else {

        solicitante.setTelefono(getTelefono().getValue());
      }

      solicitud.setSolicitante(solicitante);
    } else {
      solicitud.setEsSolicitudInterna(false);
      solicitud.setMotivo(getMotivoInterno().getValue());
      solicitud.setMotivoExterno(getMotivoExterno().getValue());
      solicitante.setApellidoSolicitante(getApellido().getValue());
      solicitante.setSegundoApellidoSolicitante(getSegundoApellido().getValue());
      solicitante.setTercerApellidoSolicitante(getTercerApellido().getValue());
      solicitante.setNombreSolicitante(getNombre().getValue());
      solicitante.setSegundoNombreSolicitante(getSegundoNombre().getValue());
      solicitante.setTercerNombreSolicitante(getTercerNombre().getValue());
      solicitante.setRazonSocialSolicitante(getRazonSocial().getValue());

      if (!getNoDeclaraNoPosee().isChecked()) {
        solicitante.setCuitCuil(getCuitCuil());
      } else {
        solicitante.setCuitCuil(null);
      }
      solicitante.setDomicilio(getDireccion().getValue().toUpperCase());

      solicitante.setPiso(getPiso().getValue());

      solicitante.setDepartamento(getDepartamento().getValue());

      solicitante.setCodigoPostal(getCodigoPostal().getValue());

      if (("".equals(getEmail().getValue())) || (getEmail().getValue() == null)) {
        solicitante.setEmail("");
      } else {
        solicitante.setEmail(getEmail().getValue());
      }
      if (("".equals(getTelefono().getValue())) || (getTelefono().getValue() == null)) {
        solicitante.setTelefono("");
      } else {
        solicitante.setTelefono(getTelefono().getValue().toString());
      }

      /*
      String tipoDocumentoTemp = getTipoDocumento().getValue();
      Long numeroDocumentoTemp = getNumeroDocumento().getValue();
      if ((tipoDocumentoTemp != null && !tipoDocumentoTemp.equals(""))
          && (numeroDocumentoTemp != null && !numeroDocumentoTemp.equals(""))) {

        documentoIdentidad.setTipoDocumento(tipoDocumentoTemp.substring(0, 2));
        documentoIdentidad.setNumeroDocumento(numeroDocumentoTemp.toString());
      } else {
        documentoIdentidad.setTipoDocumento(null);
        documentoIdentidad.setNumeroDocumento(null);
      }
      */
      
      solicitud.setSolicitante(solicitante);
    }

    Map<String, Object> variables = new HashMap<>();
    variables.put("usuarioSolicitante", loggeduser);
    variables.put("solicitud", solicitud);
    variables.put("descripcion", "");

    this.execution.getDesktop().setAttribute("variables", variables);
    this.envioSolicitudWindow = (Window) Executions
        .createComponents("/solicitud/envioSolicitud.zul", null, new HashMap<String, Object>());
    this.envioSolicitudWindow.setParent(this.nuevaSolicitudWindow);
    this.envioSolicitudWindow.setPosition("center");
    this.envioSolicitudWindow.setClosable(true);
    this.envioSolicitudWindow.doModal();

  }

  /**
   * Validamos todos los datos ingresados en el formulario de confección de la
   * solicitud.
   */
  private void validarDatosDelFormulario() {

    if ((getMotivoExterno().getValue() == null) || (getMotivoExterno().getValue().equals(""))) {
      getMotivoExterno().focus();
      throw new WrongValueException(getMotivoExterno(),
          "Debe ingresar motivo externo. Éste corresponde a la motivación que "
              + "se visualizará en la consulta pública.");
    }
    
    if (StringUtils.isEmpty(codigoTrata.getValue())) {
    	throw new WrongValueException(codigoTrata,
  	          "Debe especificar el Tipo de Trámite");
    }

    verificarExistencia();

    if (!this.existe && !StringUtils.isEmpty(codigoTrata.getValue())) {
      throw new WrongValueException(this.codigoTrata,
          Labels.getLabel("ee.caratulas.caratulaNoExiste"));
    }

    if (getExpedienteExterno().isChecked()
        && (this.selectedTrata != null && !this.selectedTrata.getEsExterno())) {
      throw new WrongValueException(this.codigoTrata,
          "La trata \"" + this.selectedTrata.getCodigoTrata()
              + "\" tiene que caratularse como Carátula Externa.");
    }

    if (getExpedienteInterno().isChecked()
        && (this.selectedTrata != null && !this.selectedTrata.getEsInterno())) {
      throw new WrongValueException(this.codigoTrata,
          "La trata \"" + this.selectedTrata.getCodigoTrata()
              + "\" tiene que caratularse como Carátula Interna.");
    }
   
    if (getExpedienteExterno().isChecked() && StringUtils.isEmpty(getNumeroDocumento().getValue())) {
    	throw new WrongValueException(getNumeroDocumento(),
    	          Labels.getLabel("ee.nuevasolicitud.faltanumerodocumento"));
    }
    
    /*
    if ((getExpedienteExterno().isChecked() && getPersona().isChecked())
        && ((getTipoDocumento().getValue() == null)
            || (getTipoDocumento().getValue().equals("")))) {
      getTipoDocumento().focus();
      throw new WrongValueException(getTipoDocumento(),
          Labels.getLabel("ee.nuevasolicitud.faltatipodocumento"));
    }

    if ((getExpedienteExterno().isChecked() && getPersona().isChecked())
        && ((getNumeroDocumento().getValue() == null))) {
      getNumeroDocumento().focus();
      throw new WrongValueException(getNumeroDocumento(),
          Labels.getLabel("ee.nuevasolicitud.faltanumerodocumento"));
    }
    
    
    if ((getExpedienteExterno().isChecked() && getEmpresa().isChecked())
        && (getNumeroDocumento().getValue() == null || getNumeroDocumento().getValue().equals(""))
        && (getTipoDocumento().getValue() != null && !getTipoDocumento().getValue().equals(""))) {
      throw new WrongValueException(getNumeroDocumento(),
          Labels.getLabel("ee.nuevasolicitud.faltanumerodocumento"));
    }

    if ((getExpedienteExterno().isChecked() && getEmpresa().isChecked())
        && (getTipoDocumento().getValue() == null || getTipoDocumento().getValue().equals(""))
        && (getNumeroDocumento().getValue() != null
            && !getNumeroDocumento().getValue().equals(""))) {
      throw new WrongValueException(getTipoDocumento(),
          Labels.getLabel("ee.nuevasolicitud.faltatipodocumento"));
    }
    */
    
    //
    // TODO PASAR LAS RESTRICCIONES A LA BBDD O *AL MENOS* A UNA PROPIEDAD
    // DEL ENUM!!!!!!!!!!
    // TODO REESTRUCTURAR IF!!!!
    /*
    if ((getExpedienteExterno().isChecked())
        && (getTipoDocumento().getValue().equals("LC - LIBRETA CIVICA"))) {
      if (getNumeroDocumento().getValue().toString().length() > 7) {
        getNumeroDocumento().focus();
        throw new WrongValueException(getNumeroDocumento(),
            Labels.getLabel("ee.nuevasolicitud.lcincorrecto"));
      }
    }
    */

    if ((getExpedienteExterno().isChecked()) && (getPersona().isChecked())
        && ((getApellido().getValue() == null) || (getApellido().getValue().equals("")))) {
      getApellido().focus();
      throw new WrongValueException(getApellido(),
          Labels.getLabel("ee.nuevasolicitud.faltaapellido"));
    }

    if ((getExpedienteExterno().isChecked()) && (getPersona().isChecked())
        && ((getNombre().getValue() == null) || (getNombre().getValue().equals("")))) {
      getNombre().focus();
      throw new WrongValueException(getNombre(),
          Labels.getLabel("ee.nuevasolicitud.faltanombres"));
    }

    if ((getExpedienteExterno().isChecked()) && (getEmpresa().isChecked())
        && ((getRazonSocial().getValue() == null) || (getRazonSocial().getValue().equals("")))) {
      getRazonSocial().focus();
      throw new WrongValueException(getRazonSocial(),
          Labels.getLabel("ee.nuevasolicitud.faltarazonsocial"));
    }

    if ((getEmail().getValue() == null) || (getEmail().getValue().equals(""))) {

    } else {

      boolean valor = MailUtil.validateMail(getEmail().getValue().toString());

      if (valor == false) {

        getEmail().focus();
        throw new WrongValueException(getEmail(), Labels.getLabel("ee.nuevasolicitud.erroremail"));

      }

    }
    
    if (getExpedienteExterno().isChecked()) {
      /*
      if (!getNoDeclaraNoPosee().isChecked()) {

        if (getCuitCuilTipo().getValue() == null) {
          throw new WrongValueException(getCuitCuilTipo(),
              Labels.getLabel("ee.nuevasolicitud.lenghtcuitCuilTipo"));
        }
        if (getCuitCuilDocumento().getValue() == null) {
          throw new WrongValueException(getCuitCuilDocumento(),
              Labels.getLabel("ee.nuevasolicitud.nocuitincorrecto"));
        }

        if (getCuitCuilVerificador().getValue() == null) {
          throw new WrongValueException(getCuitCuilVerificador(),
              Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
        }

        if (getCuitCuilTipo().getValue() != null
            && getCuitCuilTipo().getValue().toString().length() != 2) {
          throw new WrongValueException(getCuitCuilTipo(),
              Labels.getLabel("ee.nuevasolicitud.lenghtcuitCuilTipo"));
        }

        String valor = getCuitCuilDocumento().getValue().toString();

        if (getCuitCuilDocumento().getValue() != null
            && getCuitCuilDocumento().getValue().toString().length() != 8) {
          while (valor.length() != 8) {
            valor = "0" + valor;
          }
          Integer numero = new Integer(valor);
          setCuitCuilDocumento(new Longbox(numero));
        }

        if (getCuitCuilVerificador().getValue() != null
            && getCuitCuilVerificador().getValue().toString().length() != 1) {
          throw new WrongValueException(getCuitCuilVerificador(),
              Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
        }
        setCuitCuil(getCuitCuilTipo().getValue().toString() + valor
            + getCuitCuilVerificador().getValue().toString());
        ValidadorDeCuit validadorDeCuit = new ValidadorDeCuit();
        try {
          validadorDeCuit.validarNumeroDeCuit(getCuitCuil());
        } catch (NegocioException e) {
          throw new WrongValueException(getCuitCuilTipo(), e.getMessage());
        }
      }
      */
    
      /*
      if ((getDireccion().getValue() == null) || (getDireccion().getValue().equals(""))) {
        getDireccion().focus();
        throw new WrongValueException(getDireccion(),
            Labels.getLabel("ee.nuevoexpediente.faltadomicilio"));
      }

      if ((getCodigoPostal().getValue() == null) || (getCodigoPostal().getValue().equals(""))) {
        getCodigoPostal().focus();
        throw new WrongValueException(getCodigoPostal(),
            Labels.getLabel("ee.nuevoexpediente.faltacodigopostal"));
      }

      String direccion = getDepartamento().getValue();
      getDepartamento().setValue(direccion.toUpperCase());
      */
    }
  }

  final class GenerarNuevaSolicitudOnNotifyWindowListener implements EventListener {
    private GenerarNuevaSolicitudComposer composer;

    public GenerarNuevaSolicitudOnNotifyWindowListener(GenerarNuevaSolicitudComposer comp) {
      this.composer = comp;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        this.composer.closeAssociatedWindow();
      }
      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("selectTipoTrataInterna")) {
          this.composer.seleccionarSolicitudInterna();
        }
        if (event.getData().equals("selectTipoTrataExterna")) {
          this.composer.seleccionarSolicitudExterna();
        }
      }
    }

  }

  public void onSelect$trata() {
    if (this.selectedTrata != null) {
      trataSel = this.selectedTrata.getCodigoTrata();
      this.codigoTrata.setValue(trataSel.toUpperCase());
      this.binder.loadAll();
      this.codigoTrata.close();
      this.verificarExistencia();
    }
  }

  public void onBlur$codigoTrata() {
    if ((this.codigoTrata.getValue() != null) && !this.codigoTrata.getValue().trim().equals("")) {
      this.trataSel = this.codigoTrata.getValue().toUpperCase();
    }
    this.verificarExistencia();

    validarPermiso();

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
                || this.getTrataService().obtenerDescripcionTrataByCodigo(trata.getCodigoTrata())
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

  public void onClickSolicitudInterna() {
    mostrarForegroundBloqueanteToken();
    Events.echoEvent(Events.ON_USER, this.self, "selectTipoTrataInterna");
  }

  private void mostrarForegroundBloqueanteToken() {
    Clients
        .showBusy(Labels.getLabel("ee.nuevasolicitud.popup.label.seleccionandoTipoTrata.value"));
  }

  public void seleccionarSolicitudInterna() {
    super.onClickSolicitudInterna();

    validarPermiso();
    Clients.clearBusy();
  }

  public void onClickSolicitudExterna() {
    mostrarForegroundBloqueanteToken();
    Events.echoEvent(Events.ON_USER, this.self, "selectTipoTrataExterna");
  }

  public void seleccionarSolicitudExterna() {
    super.onClickSolicitudExterna();

    validarPermiso();
    Clients.clearBusy();
  }

  private void verificarExistencia() {
    this.existe = false;
    if (!(this.codigoTrata == null) || (this.codigoTrata.getValue() == "")
        || this.codigoTrata.getValue().isEmpty()) {
      for (TrataDTO aux : this.tratas) {
        if (aux.getCodigoTrata().toUpperCase().trim().equals(this.codigoTrata.getValue())) {
          this.existe = true;
          break;
        }
      }
    }
  }

  public List<TrataDTO> getTratasServices() {
    //return this.trataService.buscarTratasManuales(true); OLD
    return this.trataService.buscarTratasByTipoExpediente(true);
  }

  public void validarPermiso() {
    if (this.selectedTrata != null) {
      if (getExpedienteExterno().isChecked() && !this.selectedTrata.getEsExterno()) {
        Clients.clearBusy();
        throw new WrongValueException(this.codigoTrata,
            "La trata \"" + this.selectedTrata.getCodigoTrata()
                + "\" tiene que caratularse como Carátula Externa.");
      }

      if (getExpedienteInterno().isChecked() && !this.selectedTrata.getEsInterno()) {
        Clients.clearBusy();
        throw new WrongValueException(this.codigoTrata,
            "La trata \"" + this.selectedTrata.getCodigoTrata()
                + "\" tiene que caratularse como Carátula Interna.");
      }
    }
  }

}
