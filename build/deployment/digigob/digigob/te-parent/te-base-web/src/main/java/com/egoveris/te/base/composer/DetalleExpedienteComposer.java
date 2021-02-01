package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DocumentoDeIdentidadDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DetalleExpedienteComposer extends AbstractComposer {

	private static Logger logger = LoggerFactory.getLogger(DetalleExpedienteComposer.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1538367998718043842L;
	private HistorialOperacionDTO pase;
	private ExpedienteElectronicoDTO expedienteElectronico;
	private List<ExpedienteMetadataDTO> metadatos = new ArrayList<>();
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	protected ProcessEngine processEngine;
	protected Task workingTask = null;
	
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;

	// Componentes de la vista.
	@Autowired
	private Window detalleExpedienteWindow;
	@Autowired
	private Window datosPropiosWindow;
	@Autowired
	private Textbox motivoExpediente;
	@Autowired
	private Textbox descripcion;
	@Autowired
	private Textbox apellido;
	@Autowired
	private Textbox nombre;
	@Autowired
	private Textbox razonSocial;
	@Autowired
	private Textbox email;
	@Autowired
	private Textbox telefono;
	@Autowired
	private Textbox estado;
	@Autowired
	private Combobox codigoTrata;
	@Autowired
	private Combobox tipoDocumento;
	@Autowired
	private Radio expedienteInterno;
	@Autowired
	private Radio expedienteExterno;
	@Autowired
	private Longbox numeroDocumento;
	private TrataService trataService;

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		Execution exec = Executions.getCurrent();
		Map<?, ?> map = exec.getArg();
		this.expedienteElectronico = (ExpedienteElectronicoDTO) map.get("ee");
		this.pase = (HistorialOperacionDTO) map.get("pase");

		SolicitudExpedienteDTO solicitudExpediente = this.expedienteElectronico.getSolicitudIniciadora();
		SolicitanteDTO solicitante = solicitudExpediente.getSolicitante();
		DocumentoDeIdentidadDTO documentoIdentidad = solicitante.getDocumento();
		TrataDTO trata = this.expedienteElectronico.getTrata();

		String motivo = pase.getMotivo();
		if (StringUtils.isEmpty(motivo)) {
			motivo = "SIN MOTIVO";
		}
		boolean tipoExpediente = solicitudExpediente.isEsSolicitudInterna();
		String descripcion = this.expedienteElectronico.getDescripcion();
		String codigoTrata = trata.getCodigoTrata();
		String descripcionTrata = this.trataService.obtenerDescripcionTrataByCodigo(trata.getCodigoTrata());
		String estado = pase.getEstado();
		String telefono = solicitante.getTelefono();
		String email = solicitante.getEmail();

		this.detalleExpedienteWindow.setTitle(estado);

		this.motivoExpediente.setValue(motivo);
		this.motivoExpediente.setReadonly(true);
		this.tipoDocumento.setDisabled(true);
		if (!tipoExpediente) {
			this.expedienteExterno.setChecked(true);
			this.expedienteExterno.setDisabled(true);
			this.expedienteInterno.setChecked(false);
			this.expedienteInterno.setDisabled(true);
			String tipoDocumento = this.expedienteElectronico.getTipoDocumento();
			String numeroDocumento = documentoIdentidad.getNumeroDocumento();
			String razonSocial = solicitante.getRazonSocialSolicitante();
			String apellido = solicitante.getApellidoSolicitante();
			String nombre = solicitante.getNombreSolicitante();
			this.tipoDocumento.setValue(tipoDocumento);
			this.tipoDocumento.setReadonly(true);
			this.numeroDocumento.setValue(Long.parseLong(numeroDocumento));
			this.numeroDocumento.setReadonly(true);
			this.razonSocial.setValue(razonSocial);
			this.nombre.setValue(nombre);
			this.apellido.setValue(apellido);
			this.razonSocial.setReadonly(true);
			this.apellido.setReadonly(true);
			this.nombre.setReadonly(true);
		} else {
			this.expedienteExterno.setChecked(false);
			this.expedienteInterno.setChecked(true);
			this.expedienteExterno.setDisabled(true);
			this.expedienteInterno.setDisabled(true);
			this.tipoDocumento.setDisabled(true);
			this.numeroDocumento.setDisabled(true);
			this.razonSocial.setDisabled(true);
			this.apellido.setReadonly(true);
			this.nombre.setReadonly(true);
		}
		this.descripcion.setValue(descripcion);
		this.descripcion.setReadonly(true);
		this.codigoTrata.setValue(codigoTrata + "-" + descripcionTrata);
		this.codigoTrata.setReadonly(true);
		this.codigoTrata.setDisabled(true);
		this.estado.setValue(estado);
		this.estado.setReadonly(true);
		this.email.setValue(email);
		this.email.setReadonly(true);
		this.telefono.setValue(telefono);
		this.telefono.setReadonly(true);

	}

	public void onClick$datosPropios() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		this.metadatos = this.expedienteElectronico.getMetadatosDeTrata();
		hm.put(DatosPropiosTrataCaratulaComposer.METADATOS, this.metadatos);
		hm.put(DatosPropiosTrataCaratulaComposer.ES_SOLO_LECTURA, true);

		this.datosPropiosWindow = (Window) Executions.createComponents("/expediente/datosPropiosDeTrataCaratula.zul",
				this.self, hm);
		this.datosPropiosWindow.setClosable(true);
		this.datosPropiosWindow.doModal();
	}

	public void onCancelar() throws InterruptedException {
		this.detalleExpedienteWindow.detach();
	}

	public HistorialOperacionDTO getPase() {
		return pase;
	}

	public void setPase(HistorialOperacionDTO pase) {
		this.pase = pase;
	}

	public ExpedienteElectronicoDTO getExpedienteElectronico() {
		return expedienteElectronico;
	}

	public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
		this.expedienteElectronico = expedienteElectronico;
	}

	public List<ExpedienteMetadataDTO> getMetadatos() {
		return metadatos;
	}

	public void setMetadatos(List<ExpedienteMetadataDTO> metadatos) {
		this.metadatos = metadatos;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	public ExpedienteElectronicoService getExpedienteElectronicoService() {
		return expedienteElectronicoService;
	}

	public void setExpedienteElectronicoService(ExpedienteElectronicoService expedienteElectronicoService) {
		this.expedienteElectronicoService = expedienteElectronicoService;
	}

	public Window getDetalleExpedienteWindow() {
		return detalleExpedienteWindow;
	}

	public void setDetalleExpedienteWindow(Window detalleExpedienteWindow) {
		this.detalleExpedienteWindow = detalleExpedienteWindow;
	}

	public Window getDatosPropiosWindow() {
		return datosPropiosWindow;
	}

	public void setDatosPropiosWindow(Window datosPropiosWindow) {
		this.datosPropiosWindow = datosPropiosWindow;
	}

	public Textbox getMotivoExpediente() {
		return motivoExpediente;
	}

	public void setMotivoExpediente(Textbox motivoExpediente) {
		this.motivoExpediente = motivoExpediente;
	}

	public Textbox getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(Textbox descripcion) {
		this.descripcion = descripcion;
	}

	public Textbox getApellido() {
		return apellido;
	}

	public void setApellido(Textbox apellido) {
		this.apellido = apellido;
	}

	public Textbox getNombre() {
		return nombre;
	}

	public void setNombre(Textbox nombre) {
		this.nombre = nombre;
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

	public Textbox getTelefono() {
		return telefono;
	}

	public void setTelefono(Textbox telefono) {
		this.telefono = telefono;
	}

	public Textbox getEstado() {
		return estado;
	}

	public void setEstado(Textbox estado) {
		this.estado = estado;
	}

	public Combobox getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(Combobox codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public Combobox getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Combobox tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public Longbox getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Longbox numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public TrataService getTrataService() {
		return trataService;
	}

	public void setTrataService(TrataService trataService) {
		this.trataService = trataService;
	}

}
