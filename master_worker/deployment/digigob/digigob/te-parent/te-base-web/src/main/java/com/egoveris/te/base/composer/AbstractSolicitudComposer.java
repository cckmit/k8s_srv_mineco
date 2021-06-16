package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DocumentoDeIdentidadDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.SolicitudExpedienteService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TipoDocumentoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class AbstractSolicitudComposer extends EEGenericForwardComposer {

  private static final long serialVersionUID = 8962719310364896702L;
  @Autowired
  private Window nuevaSolicitudWindow;
  @Autowired
  private Window anularModificarSolicitudWindow;
  @Autowired
  private Radio expedienteInterno;
  @Autowired
  private Radio expedienteExterno;
  @Autowired
  private Radio persona;
  @Autowired
  private Radio empresa;
  @Autowired
  private Combobox tipoDocumento;
  @Autowired
  private Textbox numeroDocumento;
  @Autowired
  private Textbox razonSocial;
  @Autowired
  private Button guardar;
  @Autowired
  private Button cancelar;
  @Autowired
  private Textbox motivoInterno;
  @Autowired
  private Textbox motivoExterno;
  @Autowired
  private Textbox telefono;
  @Autowired
  private Textbox email;
  @Autowired
  private Textbox apellido;
  @Autowired
  private Textbox nombre;

  @Autowired
  private Textbox segundoApellido;
  @Autowired
  private Textbox segundoNombre;

  @Autowired
  private Textbox tercerApellido;

  @Autowired
  private Textbox tercerNombre;

  @Autowired
  private Longbox cuitCuilTipo;

  @Autowired
  private Longbox cuitCuilDocumento;

  @Autowired
  private Longbox cuitCuilVerificador;

  @Autowired
  private Checkbox noDeclaraNoPosee;

  @Autowired
  private Textbox direccion;

  @Autowired
  private Textbox piso;

  @Autowired
  private Textbox departamento;

  @Autowired
  private Textbox codigoPostal;

  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;

  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;

  private List<String> tiposDocumentos;
  private DocumentoDeIdentidadDTO documento;
  private String selectedTiposDocumentos;
  @Autowired
  protected Bandbox codigoTrata;
  protected String trataSel;
  public List<TrataDTO> tratas;
  public List<TrataDTO> tratasSeleccionadas;
  public TrataDTO selectedTrata;

  private String cuitCuil;

  /**
   * Defino los servicios que voy a utilizar
   */
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @WireVariable(ConstantesServicios.SOLICITUD_EXPEDIENTE_SERVICE)
  private SolicitudExpedienteService solicitudExpedienteService;
  
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;

  public void doAfterCompose(Component comp) throws Exception {
	  super.doAfterCompose(comp);
	  Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
	  
	  // Se instancian como New porque no están en la vista.
	  // Es para evitar el Nullpointer
	  this.cuitCuilTipo = new Longbox();
	  this.cuitCuilDocumento = new Longbox();
	  this.cuitCuilVerificador = new Longbox();
	  this.noDeclaraNoPosee = new Checkbox();
	  this.tipoDocumento = new Combobox();
	  
	  if (numeroDocumento == null) {
		  numeroDocumento = new Textbox();
	  }
  }
  
  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public SolicitudExpedienteService getSolicitudExpedienteService() {
    return solicitudExpedienteService;
  }

  public void setSolicitudExpedienteService(
      SolicitudExpedienteService solicitudExpedienteService) {
    this.solicitudExpedienteService = solicitudExpedienteService;
  }

  public Window getNuevaSolicitudWindow() {
    return nuevaSolicitudWindow;
  }

  public void setNuevaSolicitudWindow(Window nuevaSolicitudWindow) {
    this.nuevaSolicitudWindow = nuevaSolicitudWindow;
  }

  public Window getAnularModificarSolicitudWindow() {
    return anularModificarSolicitudWindow;
  }

  public String getTrataSel() {
    return trataSel;
  }

  public void setTrataSel(String trataSel) {
    this.trataSel = trataSel;
  }

  public void setAnularModificarSolicitudWindow(Window anularModificarSolicitudWindow) {
    this.anularModificarSolicitudWindow = anularModificarSolicitudWindow;
  }

  public Radio getExpedienteInterno() {
    return expedienteInterno;
  }

  public void setExpedienteInterno(Radio expedienteInterno) {
    this.expedienteInterno = expedienteInterno;
  }

  public Radio getExpedienteExterno() {
    return expedienteExterno;
  }

  public void setExpedienteExterno(Radio expedienteExterno) {
    this.expedienteExterno = expedienteExterno;
  }

  public Combobox getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(Combobox tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public Textbox getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(Textbox razonSocial) {
    this.razonSocial = razonSocial;
  }

  public Textbox getEmail() {
    return email;
  }

  public void setEmail(Textbox email) {
    this.email = email;
  }

  public Button getGuardar() {
    return guardar;
  }

  public void setGuardar(Button guardar) {
    this.guardar = guardar;
  }

  public Button getCancelar() {
    return cancelar;
  }

  public void setCancelar(Button cancelar) {
    this.cancelar = cancelar;
  }

  public Textbox getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(Textbox numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public Textbox getApellido() {
    return apellido;
  }

  public void setApellido(Textbox apellido) {
    this.apellido = apellido;
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

  public void onClickSolicitudInterna() {

    this.persona.setDisabled(true);
    this.empresa.setDisabled(true);
    this.expedienteInterno.setChecked(true);
    this.expedienteExterno.setChecked(false);
    //this.setTratas(null);
    //this.getTratas();
    if (this.persona.isChecked()) {

      this.tipoDocumento.setDisabled(true);
      this.tipoDocumento.setValue(null);
      //this.numeroDocumento.setDisabled(true);
      //this.numeroDocumento.setValue(null);
      this.razonSocial.setDisabled(true);
      this.razonSocial.setValue(null);
      this.apellido.setDisabled(true);
      this.apellido.setValue(null);
      this.nombre.setValue(null);
      this.nombre.setDisabled(true);

      this.segundoNombre.setValue(null);
      this.segundoNombre.setDisabled(true);

      this.tercerNombre.setValue(null);
      this.tercerNombre.setDisabled(true);

      this.segundoApellido.setValue(null);
      this.segundoApellido.setDisabled(true);

      this.tercerApellido.setValue(null);
      this.tercerApellido.setDisabled(true);

      this.email.setDisabled(false);
      this.telefono.setDisabled(false);

      this.cuitCuilTipo.setValue(null);
      this.cuitCuilDocumento.setValue(null);
      this.cuitCuilVerificador.setValue(null);

      this.cuitCuilTipo.setDisabled(true);
      this.cuitCuilDocumento.setDisabled(true);
      this.cuitCuilVerificador.setDisabled(true);

      this.noDeclaraNoPosee.setValue(null);
      this.noDeclaraNoPosee.setDisabled(true);

      this.direccion.setDisabled(true);
      this.piso.setDisabled(true);
      this.departamento.setDisabled(true);
      this.codigoPostal.setDisabled(true);
      // this.comuna.setDisabled(true);
      // this.barrio.setDisabled(true);

      this.direccion.setValue(null);
      this.piso.setValue(null);
      this.departamento.setValue(null);
      this.codigoPostal.setValue(null);
      // this.comuna.setValue(null);
      // this.barrio.setValue(null);

    }

    if (this.empresa.isChecked()) {
      this.tipoDocumento.setDisabled(true);
      this.tipoDocumento.setValue(null);
      this.numeroDocumento.setDisabled(true);
      this.numeroDocumento.setValue(null);
      this.razonSocial.setDisabled(true);
      this.razonSocial.setValue(null);
      this.apellido.setDisabled(true);
      this.apellido.setValue(null);

      this.segundoApellido.setDisabled(true);
      this.segundoApellido.setValue(null);

      this.tercerApellido.setDisabled(true);
      this.tercerApellido.setValue(null);

      this.nombre.setDisabled(true);
      this.nombre.setValue(null);

      this.segundoNombre.setDisabled(true);
      this.segundoNombre.setValue(null);

      this.tercerNombre.setDisabled(true);
      this.tercerNombre.setValue(null);

      this.email.setDisabled(false);
      this.telefono.setDisabled(false);

      ///// desabilito los campos
      this.cuitCuilTipo.setValue(null);
      this.cuitCuilDocumento.setValue(null);
      this.cuitCuilVerificador.setValue(null);

      this.cuitCuilTipo.setDisabled(true);
      this.cuitCuilDocumento.setDisabled(true);
      this.cuitCuilVerificador.setDisabled(true);

      this.noDeclaraNoPosee.setValue(null);
      this.noDeclaraNoPosee.setDisabled(true);

      this.direccion.setDisabled(true);
      this.piso.setDisabled(true);
      this.departamento.setDisabled(true);
      this.codigoPostal.setDisabled(true);
      // this.barrio.setDisabled(true);
      // this.comuna.setDisabled(true);

      this.direccion.setValue(null);
      this.piso.setValue(null);
      this.departamento.setValue(null);
      this.codigoPostal.setValue(null);
      // this.barrio.setValue(null);
      // this.comuna.setValue(null);

    }
  }

  public void onClickSolicitudExterna() {

    this.persona.setDisabled(false);
    this.empresa.setDisabled(false);
    this.expedienteInterno.setChecked(false);
    this.expedienteExterno.setChecked(true);
    //this.setTratas(null);
    //this.getTratas();
    if (this.persona.isChecked()) {

      this.tipoDocumento.setDisabled(false);
      this.numeroDocumento.setDisabled(false);
      this.apellido.setDisabled(false);
      this.segundoApellido.setDisabled(false);
      this.tercerApellido.setDisabled(false);
      this.nombre.setDisabled(false);
      this.segundoNombre.setDisabled(false);
      this.tercerNombre.setDisabled(false);
      this.email.setDisabled(false);
      this.telefono.setDisabled(false);
    }

    if (this.empresa.isChecked()) {

      this.tipoDocumento.setDisabled(false);
      this.numeroDocumento.setDisabled(false);
      this.razonSocial.setDisabled(false);
      this.email.setDisabled(false);
      this.telefono.setDisabled(false);

    }

    this.cuitCuilTipo.setDisabled(false);
    this.cuitCuilDocumento.setDisabled(false);
    this.cuitCuilVerificador.setDisabled(false);
    this.noDeclaraNoPosee.setDisabled(false);

    this.direccion.setDisabled(false);
    this.piso.setDisabled(false);
    this.departamento.setDisabled(false);
    this.codigoPostal.setDisabled(false);
    // this.barrio.setDisabled(false);
    // this.comuna.setDisabled(false);

  }

  public void onSelectTipoDocumento() {
    // if(this.tipoDocumento.getValue().equals("CUIT/CUIL")){
    // this.numeroDocumento.setDisabled(true);
    // this.razonSocial.setDisabled(false);
    // }else{
    // this.numeroDocumento.setDisabled(false);
    // this.razonSocial.setDisabled(true);
    // }
  }

  public void onClickApellidoNombres() {
    this.persona.setChecked(true);
    this.empresa.setChecked(false);
    if (this.expedienteInterno.isChecked()) {
      this.tipoDocumento.setDisabled(true);
      this.tipoDocumento.setValue(null);
      this.numeroDocumento.setDisabled(true);
      this.numeroDocumento.setValue(null);
      this.apellido.setDisabled(true);
      this.apellido.setValue(null);

      this.segundoApellido.setDisabled(true);
      this.segundoApellido.setValue(null);

      this.tercerApellido.setDisabled(true);
      this.tercerApellido.setValue(null);

      this.nombre.setDisabled(true);
      this.nombre.setValue(null);

      this.segundoNombre.setDisabled(true);
      this.segundoNombre.setValue(null);

      this.tercerNombre.setDisabled(true);
      this.tercerNombre.setValue(null);

      this.razonSocial.setDisabled(true);
      this.razonSocial.setValue(null);
      this.email.setDisabled(false);
      this.telefono.setDisabled(false);

      this.cuitCuilTipo.setValue(null);
      this.cuitCuilDocumento.setValue(null);
      this.cuitCuilVerificador.setValue(null);

      this.cuitCuilTipo.setDisabled(true);
      this.cuitCuilDocumento.setDisabled(true);
      this.cuitCuilVerificador.setDisabled(true);

      this.noDeclaraNoPosee.setValue(null);
      this.noDeclaraNoPosee.setDisabled(true);

      this.direccion.setDisabled(true);
      this.piso.setDisabled(true);
      this.departamento.setDisabled(true);
      this.codigoPostal.setDisabled(true);
      // this.barrio.setDisabled(true);
      // this.comuna.setDisabled(true);

      this.direccion.setValue(null);
      this.piso.setValue(null);
      this.departamento.setValue(null);
      this.codigoPostal.setValue(null);
      // this.barrio.setValue(null);
      // this.comuna.setValue(null);

    }

    if (this.expedienteExterno.isChecked()) {

      this.apellido.setDisabled(false);
      this.segundoApellido.setDisabled(false);
      this.tercerApellido.setDisabled(false);
      this.nombre.setDisabled(false);
      this.segundoNombre.setDisabled(false);
      this.tercerNombre.setDisabled(false);
      this.razonSocial.setDisabled(true);
      this.razonSocial.setValue(null);

      //// habilito
      this.cuitCuilTipo.setDisabled(false);
      this.cuitCuilDocumento.setDisabled(false);
      this.cuitCuilVerificador.setDisabled(false);
      this.noDeclaraNoPosee.setDisabled(false);

      this.direccion.setDisabled(false);
      this.piso.setDisabled(false);
      this.departamento.setDisabled(false);
      this.codigoPostal.setDisabled(false);
      // this.barrio.setDisabled(false);
      // this.comuna.setDisabled(false);
    }

  }

  public void onClickEmpresa() {
    this.persona.setChecked(false);
    this.empresa.setChecked(true);
    if (this.expedienteInterno.isChecked()) {

      this.tipoDocumento.setDisabled(true);
      this.tipoDocumento.setValue(null);
      this.numeroDocumento.setDisabled(true);
      this.numeroDocumento.setValue(null);
      this.apellido.setDisabled(true);
      this.apellido.setValue(null);

      this.segundoApellido.setDisabled(true);
      this.segundoApellido.setValue(null);

      this.tercerApellido.setDisabled(true);
      this.tercerApellido.setValue(null);

      this.nombre.setDisabled(true);
      this.nombre.setValue(null);

      this.segundoNombre.setDisabled(true);
      this.segundoNombre.setValue(null);

      this.tercerNombre.setDisabled(true);
      this.tercerNombre.setValue(null);

      this.razonSocial.setDisabled(true);
      this.razonSocial.setValue(null);
      this.email.setDisabled(false);
      this.telefono.setDisabled(false);
      ///// desabilito los campos
      this.cuitCuilTipo.setValue(null);
      this.cuitCuilDocumento.setValue(null);
      this.cuitCuilVerificador.setValue(null);

      this.cuitCuilTipo.setDisabled(true);
      this.cuitCuilDocumento.setDisabled(true);
      this.cuitCuilVerificador.setDisabled(true);

      this.noDeclaraNoPosee.setValue(null);
      this.noDeclaraNoPosee.setDisabled(true);

      this.direccion.setDisabled(true);
      this.piso.setDisabled(true);
      this.departamento.setDisabled(true);
      this.codigoPostal.setDisabled(true);
      // this.barrio.setDisabled(true);
      // this.comuna.setDisabled(true);

      this.direccion.setValue(null);
      this.piso.setValue(null);
      this.departamento.setValue(null);
      this.codigoPostal.setValue(null);
      // this.barrio.setValue(null);
      // this.comuna.setValue(null);

    }

    if (this.expedienteExterno.isChecked()) {
      this.apellido.setDisabled(true);
      this.apellido.setValue(null);

      this.segundoApellido.setDisabled(true);
      this.segundoApellido.setValue(null);

      this.tercerApellido.setDisabled(true);
      this.tercerApellido.setValue(null);

      this.nombre.setDisabled(true);
      this.nombre.setValue(null);

      this.segundoNombre.setDisabled(true);
      this.segundoNombre.setValue(null);

      this.tercerNombre.setDisabled(true);
      this.tercerNombre.setValue(null);

      this.razonSocial.setDisabled(false);

      this.cuitCuilTipo.setDisabled(false);
      this.cuitCuilDocumento.setDisabled(false);
      this.cuitCuilVerificador.setDisabled(false);

      this.direccion.setDisabled(false);
      this.piso.setDisabled(false);
      this.departamento.setDisabled(false);
      this.codigoPostal.setDisabled(false);
      // this.barrio.setDisabled(false);
      // this.comuna.setDisabled(false);
    }
  }

  // public abstract void onGuardarSolicitud()throws InterruptedException;

  public void onCancelar() {
    this.closeAssociatedWindow();
  }

  public Textbox getMotivoInterno() {
    return motivoInterno;
  }

  public void setMotivoInterno(Textbox motivoInterno) {
    this.motivoInterno = motivoInterno;
  }

  public Textbox getMotivoExterno() {
    return motivoExterno;
  }

  public void setMotivoExterno(Textbox motivoExterno) {
    this.motivoExterno = motivoExterno;
  }

  public Textbox getTelefono() {
    return telefono;
  }

  public void setTelefono(Textbox telefono) {
    this.telefono = telefono;
  }

  public Textbox getMail() {
    return email;
  }

  public void setMail(Textbox mail) {
    this.email = mail;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public List<String> getTiposDocumentos() {
    if (this.tiposDocumentos == null) {
      this.tiposDocumentos = TipoDocumentoUtils.getTiposDocumentos();
    }
    return this.tiposDocumentos;
  }

  public void setTiposDocumentos(List<String> tiposDocumentos) {
    this.tiposDocumentos = tiposDocumentos;
  }

  public DocumentoDeIdentidadDTO getDocumento() {
    return documento;
  }

  public void setDocumento(DocumentoDeIdentidadDTO documento) {
    this.documento = documento;
  }

  public String getSelectedTiposDocumentos() {
    return selectedTiposDocumentos;
  }

  public void setSelectedTiposDocumentos(String selectedTiposDocumentos) {
    this.selectedTiposDocumentos = selectedTiposDocumentos;
  }

  public List<TrataDTO> getTratas() {
    if (this.tratas == null) {
      this.tratas = obtenerTratas();
      this.tratasSeleccionadas = new ArrayList<>(this.tratas);
    }
    return this.tratas;
  }

  public void setTratas(List<TrataDTO> tratas) {
    this.tratas = tratas;
  }

  public List<TrataDTO> obtenerTratas() {

    List<TrataDTO> listaAux = new ArrayList<>();

    Boolean esInterno = false;
    if (this.getExpedienteInterno().isChecked()) {
      esInterno = true;
    }
    listaAux.addAll(
        this.trataService.buscarTratasManualesYTipoCaratulacion(TrataDTO.MANUAL, esInterno));
    Collections.sort(listaAux, new Comparator<TrataDTO>() {
      public int compare(TrataDTO td1, TrataDTO td2) {
        return trataService.formatoToStringTrata(td1.getCodigoTrata(), td1.getDescripcion())
            .compareTo(
                trataService.formatoToStringTrata(td2.getCodigoTrata(), td2.getDescripcion()));
      }
    });
    return listaAux;
  }

  public TrataDTO getSelectedTrata() {
    return selectedTrata;
  }

  public void setSelectedTrata(TrataDTO selectedTrata) {
    this.selectedTrata = selectedTrata;
  }

  public Bandbox getCodigoTrata() {
    return codigoTrata;
  }

  public void setCodigoTrata(Bandbox codigoTrata) {
    this.codigoTrata = codigoTrata;
  }

  public List<TrataDTO> getTratasSeleccionadas() {
    return tratasSeleccionadas;
  }

  public void setTratasSeleccionadas(List<TrataDTO> tratasSeleccionadas) {
    this.tratasSeleccionadas = tratasSeleccionadas;
  }

  public TrataService getTrataService() {
    return trataService;
  }

  public void setTrataService(TrataService trataService) {
    this.trataService = trataService;
  }

  public void setWorkFlowService(WorkFlowService workFlowService) {
    this.workFlowService = workFlowService;
  }

  public WorkFlowService getWorkFlowService() {
    return workFlowService;
  }

  public void setNombre(Textbox nombre) {
    this.nombre = nombre;
  }

  public Textbox getNombre() {
    return nombre;
  }

  public void setSegundoApellido(Textbox segundoApellido) {
    this.segundoApellido = segundoApellido;
  }

  public Textbox getSegundoApellido() {
    return segundoApellido;
  }

  public void setSegundoNombre(Textbox segundoNombre) {
    this.segundoNombre = segundoNombre;
  }

  public Textbox getSegundoNombre() {
    return segundoNombre;
  }

  public void setTercerApellido(Textbox tercerApellido) {
    this.tercerApellido = tercerApellido;
  }

  public Textbox getTercerApellido() {
    return tercerApellido;
  }

  public void setTercerNombre(Textbox tercerNombre) {
    this.tercerNombre = tercerNombre;
  }

  public Textbox getTercerNombre() {
    return tercerNombre;
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

  public void setDepartamento(Textbox departamento) {
    this.departamento = departamento;
  }

  public Textbox getDepartamento() {
    return departamento;
  }

  public void setCodigoPostal(Textbox codigoPostal) {
    this.codigoPostal = codigoPostal;
  }

  public Textbox getCodigoPostal() {
    return codigoPostal;
  }

  public void setCuitCuil(String cuitCuil) {
    this.cuitCuil = cuitCuil;
  }

  public String getCuitCuil() {
    return cuitCuil;
  }
  // public Textbox getBarrio() {
  // return barrio;
  // }
  // public void setBarrio(Textbox barrio) {
  // this.barrio = barrio;
  // }
  // public Textbox getComuna() {
  // return comuna;
  // }
  // public void setComuna(Textbox comuna) {
  // this.comuna = comuna;
  // }

}