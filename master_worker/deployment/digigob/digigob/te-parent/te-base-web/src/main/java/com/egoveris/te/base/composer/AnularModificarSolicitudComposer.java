package com.egoveris.te.base.composer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.service.SolicitudExpedienteService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.TipoDocumentoPosible;
import com.egoveris.te.base.vm.SolicitanteDireccionVM;
import com.egoveris.te.model.util.MailUtil;

public class AnularModificarSolicitudComposer extends AbstractSolicitudComposer {
  /**
  * 
  */
  private static final long serialVersionUID = -7514617907467543322L;

  private static final Logger logger = LoggerFactory.getLogger("AnularModificarSolicitudComposer");
  protected Task workingTask = null;
  private SolicitudExpedienteDTO solicitudExpediente;

  // SERVICIOS
  @WireVariable(ConstantesServicios.SOLICITUD_EXPEDIENTE_SERVICE)
  private SolicitudExpedienteService solicitudExpedienteService;

  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public void onAnular() {
    this.signalExecution("cancelar", "usuarioPrueba");
    this.closeAssociatedWindow();
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

  public void onGuardarSolicitud() throws InterruptedException {

    validarDatosFormulario();

    SolicitanteDTO solicitante = solicitudExpediente.getSolicitante();

    if (getExpedienteInterno().isChecked()) {

      solicitudExpediente.setMotivo(getMotivoInterno().getValue());

      if ((getEmail().getValue().equals("")) || (getEmail().getValue() == null)) {

        solicitante.setEmail("");

      } else {

        solicitante.setEmail(getEmail().getValue());

      }

      if (getTelefono().getValue() == null) {

        solicitante.setTelefono("");
      } else {

        solicitante.setTelefono(getTelefono().getValue().toString());

      }
      solicitudExpediente.setSolicitante(solicitante);

    } else {
      solicitudExpediente.setMotivo(getMotivoInterno().getValue());
      solicitante.setApellidoSolicitante(getApellido().getValue());
      solicitante.setSegundoApellidoSolicitante(getSegundoApellido().getValue());
      solicitante.setTercerApellidoSolicitante(getTercerApellido().getValue());
      solicitante.setNombreSolicitante(getNombre().getValue());
      solicitante.setSegundoNombreSolicitante(getSegundoNombre().getValue());
      solicitante.setTercerNombreSolicitante(getTercerNombre().getValue());

      solicitante.setDomicilio(getDireccion().getValue().toUpperCase());
      solicitante.setPiso(getPiso().getValue());
      solicitante.setDepartamento(getDepartamento().getValue());
      solicitante.setCodigoPostal(getCodigoPostal().getValue());

      if (!getNoDeclaraNoPosee().isChecked()) {
        solicitante.setCuitCuil(getCuitCuil());
      } else {
        solicitante.setCuitCuil(null);
      }

      solicitante.setRazonSocialSolicitante(getRazonSocial().getValue());

      if ((getEmail().getValue().equals("")) || (getEmail().getValue() == null)) {

        solicitante.setEmail("");
      } else {

        solicitante.setEmail(getEmail().getValue());

      }

      if (("".equals(getTelefono().getValue())) || (getTelefono().getValue() == null)) {

        solicitante.setTelefono("");
      } else {

        solicitante.setTelefono(getTelefono().getValue().toString());
      }

      //solicitante.getDocumento().setTipoDocumento(getTipoDocumento().getValue().substring(0, 2));
      solicitante.getDocumento().setNumeroDocumento(getNumeroDocumento().getValue().toString());

      solicitudExpediente.setSolicitante(solicitante);
    }

    Map<String, Object> variables = new HashMap();
    variables.put(ConstantesWeb.USUARIO_PRODUCTOR,
        Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME));
    variables.put(ConstantesWeb.MOTIVO, getMotivoInterno().getValue());

    String usuarioCandidato = (String) getVariableWorkFlow(ConstantesWeb.USUARIO_CANDIDATO);

    variables.put(ConstantesWeb.USUARIO_SELECCIONADO, usuarioCandidato);

    this.signalExecution(ConstantesWeb.ESTADO_INICIAR_EXPEDIENTE, usuarioCandidato);

    getSolicitudExpedienteService().modificarSolicitud(solicitudExpediente);
    
    // Save direccion
    SolicitudExpedienteDTO solicitudExpedienteDTO = getSolicitudExpedienteService().obtenerSolitudByIdSolicitud(solicitudExpediente.getId());
    SolicitanteDireccionVM.saveDireccion(solicitudExpedienteDTO.getSolicitante().getId());

    variables.put(ConstantesWeb.ID_SOLUCITUD, solicitudExpediente.getId());

    this.setVariablesWorkFlow(variables);
    this.closeAssociatedWindow();
  }

  private void validarDatosFormulario() {
    if (getExpedienteInterno().isChecked() && ((getMotivoInterno().getValue() == null)
        || (getMotivoInterno().getValue().equals("")))) {
      throw new WrongValueException(getMotivoInterno(),
          Labels.getLabel("ee.anularmodificarsolicitud.faltamotivo"));
    }

    if (getExpedienteExterno().isChecked() && ((getMotivoInterno().getValue() == null)
        || (getMotivoInterno().getValue().equals("")))) {
      throw new WrongValueException(getMotivoInterno(),
          Labels.getLabel("ee.anularmodificarsolicitud.faltamotivo"));
    }

    /*if (getExpedienteExterno().isChecked() && ((getTipoDocumento().getValue() == null)
        || (getTipoDocumento().getValue().equals("")))) {
      throw new WrongValueException(getTipoDocumento(),
          Labels.getLabel("ee.anularmodificarsolicitud.faltatipodocumento"));
    }*/

    /*if (getExpedienteExterno().isChecked() && ((getNumeroDocumento().getValue() == null)
        || (getNumeroDocumento().getValue().equals("")))) {
      throw new WrongValueException(getNumeroDocumento(),
          Labels.getLabel("ee.anularmodificarsolicitud.faltanumerodocumento"));
    }*/
    
    if (StringUtils.isBlank(getNumeroDocumento().getValue())) {
    	throw new WrongValueException(getNumeroDocumento(),
    	          Labels.getLabel("ee.anularmodificarsolicitud.faltanumerodocumento"));
    }


    // TODO PASAR LAS RESTRICCIONES A LA BBDD O *AL MENOS* A UNA PROPIEDAD DEL
    // ENUM!!!!!!!!!!
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
      throw new WrongValueException(getApellido(),
          Labels.getLabel("ee.nuevasolicitud.faltaapellido"));
    }

    if ((getExpedienteExterno().isChecked()) && (getPersona().isChecked())
        && ((getNombre().getValue() == null) || (getNombre().getValue().equals("")))) {
      throw new WrongValueException(getNombre(),
          Labels.getLabel("ee.nuevasolicitud.faltanombres"));
    }

    if ((getExpedienteExterno().isChecked()) && (getEmpresa().isChecked())
        && ((getRazonSocial().getValue() == null) || (getRazonSocial().getValue().equals("")))) {
      throw new WrongValueException(getRazonSocial(),
          Labels.getLabel("ee.nuevasolicitud.faltarazonsocial"));
    }

    if ((getEmail().getValue() == null) || (getEmail().getValue().equals(""))) {

    } else {

      boolean valor = MailUtil.validateMail(getEmail().getValue().toString());

      if (valor == false) {

        throw new WrongValueException(getEmail(), Labels.getLabel("ee.nuevasolicitud.erroremail"));

      }
    }

    if (getExpedienteExterno().isChecked()) {
      /*if (!getNoDeclaraNoPosee().isChecked()) {

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
        if (valor == null) {
          valor = "";
        }
        if (getCuitCuilDocumento().getValue() != null
            && getCuitCuilDocumento().getValue().toString().length() != 8) {
          valor = getCuitCuilDocumento().getValue().toString();
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
          logger.error("error validarDatosFormulario - NegocioException", e);
          throw new WrongValueException(getCuitCuilTipo(), e.getMessage());
        }
      }
      */

      /*if ((getDireccion().getValue() == null) || (getDireccion().getValue().equals(""))) {
        getDireccion().focus();
        throw new WrongValueException(getDireccion(),
            Labels.getLabel("ee.nuevoexpediente.faltadomicilio"));
      }*/

      /*
      if ((getCodigoPostal().getValue() == null) || (getCodigoPostal().getValue().equals(""))) {
        getCodigoPostal().focus();
        throw new WrongValueException(getCodigoPostal(),
            Labels.getLabel("ee.nuevoexpediente.faltacodigopostal"));
      }
      */

      String direccion = getDepartamento().getValue();
      getDepartamento().setValue(direccion.toUpperCase());
    }
  }

  public void doAfterCompose(Component component) throws Exception {

    super.doAfterCompose(component);

    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));

    component.getDesktop().removeAttribute("selectedTask");

    Long idSolicitud = (Long) getVariableWorkFlow(ConstantesWeb.ID_SOLUCITUD);
    this.solicitudExpediente = solicitudExpedienteService.obtenerSolitudByIdSolicitud(idSolicitud);

    String tipoExpediente;
    boolean esExpedienteInterno = this.solicitudExpediente.isEsSolicitudInterna();
    if (esExpedienteInterno) {
      tipoExpediente = "expedienteInterno";
    } else {
      tipoExpediente = "expedienteExterno";
    }
    String telefono = this.solicitudExpediente.getSolicitante().getTelefono();
    String email = this.solicitudExpediente.getSolicitante().getEmail();

    getMotivoInterno().setValue(solicitudExpediente.getMotivo());

    this.getMotivoInterno().setReadonly(false);

    if (getExpedienteExterno().getId().equals(tipoExpediente)) {

      getExpedienteExterno().setChecked(true);
      getExpedienteExterno().setDisabled(true);
      getExpedienteInterno().setChecked(false);
      getExpedienteInterno().setDisabled(true);

      String tipoDocumento = this.solicitudExpediente.getSolicitante().getDocumento()
          .getTipoDocumento();
      String numeroDocumentoTemp = this.solicitudExpediente.getSolicitante().getDocumento()
          .getNumeroDocumento();
      String razonSocial = this.solicitudExpediente.getSolicitante().getRazonSocialSolicitante();
      String apellido = this.solicitudExpediente.getSolicitante().getApellidoSolicitante();
      String segundoApellido = this.solicitudExpediente.getSolicitante()
          .getSegundoApellidoSolicitante();
      String tercerApellido = this.solicitudExpediente.getSolicitante()
          .getTercerApellidoSolicitante();

      String nombre = this.solicitudExpediente.getSolicitante().getNombreSolicitante();
      String segundoNombre = this.solicitudExpediente.getSolicitante()
          .getSegundoNombreSolicitante();
      String tercerNombre = this.solicitudExpediente.getSolicitante().getTercerNombreSolicitante();

      String direccion = this.solicitudExpediente.getSolicitante().getDomicilio();
      String piso = this.solicitudExpediente.getSolicitante().getPiso();
      String dpto = this.solicitudExpediente.getSolicitante().getDepartamento();
      String codigoPostal = this.solicitudExpediente.getSolicitante().getCodigoPostal();

      String cuitcuil = this.solicitudExpediente.getSolicitante().getCuitCuil();

      String tipoCuitCuil = null;
      String documentoCuitCuil = null;
      String verificadorCuitCuil = null;

      if (cuitcuil != null) {
        tipoCuitCuil = cuitcuil.substring(0, 2);
        documentoCuitCuil = cuitcuil.substring(2, 10);
        verificadorCuitCuil = cuitcuil.substring(10, 11);
      }

      String tipoDocumentoDescripcion = null;
      if (tipoDocumento != null && !tipoDocumento.equals("")) {
        if (tipoDocumento.equals(ConstantesWeb.CU_VALOR)) {
          tipoDocumentoDescripcion = ConstantesWeb.CU_VALOR + "-" + ConstantesWeb.CU_DESCRIPCION;
        } else {
          tipoDocumentoDescripcion = TipoDocumentoPosible.valueOf(tipoDocumento)
              .getDescripcionCombo();
        }
      }

      /*
      Long numeroDocumento = null;
      
      if (numeroDocumentoTemp != null) {
        numeroDocumento = Long.parseLong(numeroDocumentoTemp);
      }
      */

      getTipoDocumento().setValue(tipoDocumentoDescripcion);
      getNumeroDocumento().setValue(numeroDocumentoTemp);

      if ((razonSocial == null) || razonSocial.equals("")) {

        getApellido().setValue(apellido);
        getSegundoApellido().setValue(segundoApellido);
        getTercerApellido().setValue(tercerApellido);
        getNombre().setValue(nombre);
        getSegundoNombre().setValue(segundoNombre);
        getTercerNombre().setValue(tercerNombre);

        getRazonSocial().setReadonly(true);

        getPersona().setChecked(true);
        getEmpresa().setChecked(false);
      } else {
        getRazonSocial().setValue(razonSocial);
        getApellido().setReadonly(true);
        getSegundoApellido().setReadonly(true);
        getTercerApellido().setReadonly(true);
        getNombre().setReadonly(true);
        getSegundoNombre().setReadonly(true);
        getTercerNombre().setReadonly(true);

        getPersona().setChecked(false);
        getEmpresa().setChecked(true);
      }

      if (cuitcuil != null) {
        getCuitCuilTipo().setValue(new Long(tipoCuitCuil));
        getCuitCuilDocumento().setValue(new Long(documentoCuitCuil));
        getCuitCuilVerificador().setValue(new Long(verificadorCuitCuil));
      }

      getPiso().setValue(piso);
      getDireccion().setValue(direccion);
      getDepartamento().setValue(dpto);
      getCodigoPostal().setValue(codigoPostal);

      getEmail().setValue(email);
      getEmail().setReadonly(false);

      if ((telefono == null) || telefono.equals("")) {
        getTelefono().setReadonly(false);
      } else {
        getTelefono().setValue(telefono);
        getTelefono().setReadonly(false);

      }
    } else {

      getExpedienteExterno().setChecked(false);
      getExpedienteInterno().setChecked(true);
      getExpedienteExterno().setDisabled(true);
      getExpedienteInterno().setDisabled(true);
      getTipoDocumento().setDisabled(true);
      getNumeroDocumento().setDisabled(true);
      getApellido().setDisabled(true);
      getSegundoApellido().setDisabled(true);
      getTercerApellido().setDisabled(true);

      getNombre().setDisabled(true);
      getSegundoNombre().setDisabled(true);
      getTercerNombre().setDisabled(true);

      getRazonSocial().setDisabled(true);

      getCuitCuilDocumento().setDisabled(true);
      getCuitCuilTipo().setDisabled(true);
      getCuitCuilVerificador().setDisabled(true);
      getNoDeclaraNoPosee().setDisabled(true);

      getDireccion().setDisabled(true);
      getPiso().setDisabled(true);
      getDepartamento().setDisabled(true);
      getCodigoPostal().setDisabled(true);

      if ((email == null) || email.equals("")) {

        getEmail().setValue("");
        getEmail().setReadonly(false);

      } else {
        getEmail().setValue(email);
        getEmail().setReadonly(false);
      }
      if ((telefono == null) || telefono.equals("")) {
        getTelefono().setReadonly(false);

      } else {
        getTelefono().setValue(telefono);
        getTelefono().setReadonly(false);

      }
    }
  }

  public Object getVariableWorkFlow(String name) {
    Object obj = getProcessEngine().getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), name);
    if (obj == null) {
      throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
          + this.workingTask.getExecutionId() + ", " + name, null);
    }
    return obj;
  }

  public void setVariablesWorkFlow(Map<String, Object> variables) {
    workFlowService.setVariables(getProcessEngine(), this.workingTask.getExecutionId(), variables);
  }

  public void signalExecution(String nameTransition, String usernameDerivador) {
    getProcessEngine().getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
        nameTransition);
  }

}
