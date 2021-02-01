/**
 * 
 */
package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TipoDocumentoPosible;
import com.egoveris.te.base.vm.SolicitanteDireccionVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author jnorvert
 *
 */
@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenericDatosExpedienteComposer extends GenericForwardComposer {

  final static Logger logger = LoggerFactory.getLogger(GenericDatosExpedienteComposer.class);

  public static String MEMORANDUM = "ME";
  public static String NOTA = "NO";
  @Autowired
  private Window genericDatosPropiosWindow;
  @Autowired
  private Radio expedienteInterno;
  @Autowired
  private Radio expedienteExterno;
  @Autowired
  private Combobox codigoTrata;
  @Autowired
  private Combobox tipoDocumento;
  @Autowired
  private Longbox cuitCuilTipo;
  @Autowired
  private Longbox cuitCuilDocumento;
  @Autowired
  private Longbox cuitCuilVerificador;
  @Autowired
  private Checkbox noDeclaraNoPosee;
  @Autowired
  private Textbox descripcion;
  @Autowired
  private Textbox estado;
  @Autowired
  private Textbox numeroDocumento;
  @Autowired
  private Textbox motivoExpediente;
  @Autowired
  private Textbox razonSocial;
  @Autowired
  private Textbox nombre;
  @Autowired
  private Textbox segundoNombre;
  @Autowired
  private Textbox tercerNombre;
  @Autowired
  private Textbox apellido;
  @Autowired
  private Textbox segundoApellido;
  @Autowired
  private Textbox tercerApellido;
  @Autowired
  private Textbox email;
  @Autowired
  private Textbox telefono;

  @Autowired
  private Textbox direccion;

  @Autowired
  private Textbox piso;

  @Autowired
  private Textbox departamento;

  @Autowired
  private Textbox codigoPostal;

  private Grid gridMotivoExp;
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;
  private List<ExpedienteMetadataDTO> metadatos = new ArrayList<ExpedienteMetadataDTO>();
  private ExpedienteElectronicoDTO expedienteElectronico;
  protected Task workingTask = null;
  private String codigoExpedienteElectronico;

  /**
   * Una vez que el componente se creo, cargo datos, habilito/deshabilito campos
   */
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null)); 
    ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE); 
    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));
    try {
      this.codigoExpedienteElectronico = (String) Executions.getCurrent().getDesktop()
          .getAttribute("codigoExpedienteElectronico");
    } catch (Exception e) {
      logger.debug(e.getMessage());
      throw new WrongValueException("Error al obtener el Expediente Electrónico seleccionado.");
    }

    this.expedienteElectronico = expedienteElectronicoService
        .obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);

    String estado = this.expedienteElectronico.getEstado();
    if (!estado.equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)
        && !estado.equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)
        && !estado.equals(ConstantesWeb.ESTADO_ARCHIVO)) {
      String motivo = (String) this.processEngine.getExecutionService()
          .getVariable(this.expedienteElectronico.getIdWorkflow(), ConstantesWeb.MOTIVO);
      if (motivo == null) {
        motivo = "SIN MOTIVO";
      }
      this.motivoExpediente.setValue(motivo);

    } else {
      // ocultar
      gridMotivoExp.detach();
    }

    String tipoExpediente;
    if (this.expedienteElectronico.getSolicitudIniciadora().isEsSolicitudInterna()) {
      tipoExpediente = "expedienteInterno";
    } else {
      tipoExpediente = "expedienteExterno";
    }
    String descripcion = this.expedienteElectronico.getDescripcion();

    String codigoTrata = this.expedienteElectronico.getTrata().getCodigoTrata();
    String descripcionTrata = this.trataService
        .obtenerDescripcionTrataByCodigo(expedienteElectronico.getTrata().getCodigoTrata());
    String telefono = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
        .getTelefono();
    String email = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getEmail();

    this.motivoExpediente.setReadonly(true);
    this.tipoDocumento.setDisabled(true);
    if (this.expedienteExterno.getId().equals(tipoExpediente)) {
      this.expedienteExterno.setChecked(true);
      this.expedienteExterno.setDisabled(true);
      this.expedienteInterno.setChecked(false);
      this.expedienteInterno.setDisabled(true);

      String tipoDocumentoTemp = this.expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getDocumento().getTipoDocumento();
      String tipoDocumento = null;
      if (tipoDocumentoTemp != null) {
        if (tipoDocumentoTemp.equals(ConstantesWeb.CU_VALOR)) {
          tipoDocumento = ConstantesWeb.CU_VALOR + "-"
              + ConstantesWeb.CU_DESCRIPCION;
        } else { 
          tipoDocumento = TipoDocumentoPosible.valueOf(tipoDocumentoTemp).getDescripcionCombo();
        }
      }

      String numeroDocumentoString = this.expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getDocumento().getNumeroDocumento();
      
      /*
      Long numeroDocumento = null;

      if (numeroDocumentoString != null && !numeroDocumentoString.equals("")) {
        numeroDocumento = Long.parseLong(numeroDocumentoString);
      }
      */
      
      String razonSocial = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getRazonSocialSolicitante();
      String apellido = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getApellidoSolicitante();
      String nombre = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getNombreSolicitante();

      String segundoApellido = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getSegundoApellidoSolicitante();
      String segundoNombre = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getSegundoNombreSolicitante();

      String tercerApellido = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getTercerApellidoSolicitante();
      String tercerNombre = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getTercerNombreSolicitante();

      String cuitcuil = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getCuitCuil();

      if (cuitcuil != null) {
        String tipoCuitCuil = cuitcuil.substring(0, 2);
        String documentoCuitCuil = cuitcuil.substring(2, 10);
        String verificadorCuitCuil = cuitcuil.substring(10, 11);
        this.cuitCuilTipo.setValue(new Long(tipoCuitCuil));
        this.cuitCuilDocumento.setValue(new Long(documentoCuitCuil));
        this.cuitCuilVerificador.setValue(new Long(verificadorCuitCuil));
      } else {
        this.cuitCuilTipo.setValue(null);
        this.cuitCuilDocumento.setValue(null);
        this.cuitCuilVerificador.setValue(null);
      }

      this.direccion.setValue(
          this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDomicilio());

      this.departamento.setValue(
          this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDepartamento());

      this.piso.setValue(
          this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getPiso());

      this.codigoPostal.setValue(
          this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCodigoPostal());

      this.tipoDocumento.setValue(tipoDocumento);
      this.numeroDocumento.setValue(numeroDocumentoString);
      this.razonSocial.setValue(razonSocial);
      this.nombre.setValue(nombre);
      this.apellido.setValue(apellido);
      this.segundoNombre.setValue(segundoNombre);
      this.segundoApellido.setValue(segundoApellido);
      this.tercerNombre.setValue(tercerNombre);
      this.tercerApellido.setValue(tercerApellido);
    } else {
      this.expedienteExterno.setChecked(false);
      this.expedienteInterno.setChecked(true);
      this.expedienteExterno.setDisabled(true);
      this.expedienteInterno.setDisabled(true);
      this.tipoDocumento.setDisabled(true);
      this.numeroDocumento.setDisabled(true);
      this.razonSocial.setDisabled(true);

    }
    this.descripcion.setValue(descripcion);
    this.codigoTrata.setValue(codigoTrata + "-" + descripcionTrata);
    this.codigoTrata.setDisabled(true);
    this.estado.setValue(estado);
    this.email.setValue(email);
    this.telefono.setValue(telefono);

    this.cuitCuilTipo.setReadonly(true);
    this.cuitCuilDocumento.setReadonly(true);
    this.cuitCuilVerificador.setReadonly(true);
    this.direccion.setReadonly(true);
    this.departamento.setReadonly(true);
    this.piso.setReadonly(true);
    this.codigoPostal.setReadonly(true);
    
    // Precarga direccion
 	SolicitanteDireccionVM.precargaDireccion(this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getId());
 		
	// Habilita readOnly direccion
	SolicitanteDireccionVM.setReadOnlyDireccion(true);
  }

  @SuppressWarnings("unchecked")
  public void onClick$datosPropios() {
    @SuppressWarnings("rawtypes")
    HashMap hm = new HashMap();
    this.metadatos = this.expedienteElectronico.getMetadatosDeTrata();
    hm.put(DatosPropiosTrataCaratulaComposer.METADATOS, this.metadatos);
    hm.put(DatosPropiosTrataCaratulaComposer.ES_SOLO_LECTURA, true);
    this.genericDatosPropiosWindow = (Window) Executions
        .createComponents("/expediente/macros/datosPropiosTrata.zul", this.self, hm);
    this.genericDatosPropiosWindow.setClosable(true);
    try {
      this.genericDatosPropiosWindow.doModal();
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage());
    }
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public String getCodigoExpedienteElectronico() {
    return codigoExpedienteElectronico;
  }

  public void setCodigoExpedienteElectronico(String codigoExpedienteElectronico) {
    this.codigoExpedienteElectronico = codigoExpedienteElectronico;
  }

  public TrataService getTrataService() {
    return trataService;
  }

  public void setTrataService(TrataService trataService) {
    this.trataService = trataService;
  }

}
