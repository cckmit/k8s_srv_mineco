package com.egoveris.te.base.composer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.service.DocumentoManagerService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VincularDocumentoComposer extends EEGenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 362717200142667320L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(VincularDocumentoComposer.class);
	
	@Autowired
	private Bandbox tiposDocumentoBandbox;
	
	// bandbox
	@Autowired
	private Bandbox tiposDocumentoEspecialBandbox;
	
	@Autowired
	private Intbox anioSADEDocumento;
	
	
	@Autowired
	protected Intbox numeroSADEDocumento;
	
	@Autowired
	protected Bandbox reparticionBusquedaDocumento;
	

	private Button comunicarTadButton;
	
	@Autowired
	private Intbox anioSADEDocumentoNumeroEspecial;
	@Autowired
	private Intbox numeroSADEDocumentoNumeroEspecial;
	@Autowired
	private Bandbox reparticionBusquedaDocumentoNumeroEspecial;
	

	@WireVariable(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE)
	private DocumentoManagerService documentoManagerService;
	
	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	protected TipoDocumentoService tipoDocumentoService;
	
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	public static final String ON_MENSAJE_ACORDEON = "onMensajeAcordeon";
	
	public static final String ON_ADD_DOC_NOTIFICABLE = "onAddDocNotificableSinNotificar";
	
	private static final String TRAMITACION_EN_PARALELO = "Paralelo";
	
	
	private ExpedienteElectronicoDTO ee;
	
	private List<TrataTipoDocumentoDTO> tiposDocumentosGEDOBD;
	
	
	private String loggedUsername;
	
	private Task workingTask = null;
	
	private boolean cerrarVentana;
	
	private String rehabilitarExpediente;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component component) throws Exception {
		
		super.doAfterCompose(component);
		this.documentoManagerService = (DocumentoManagerService) 
				SpringUtil.getBean(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE);
		
		this.tipoDocumentoService = (TipoDocumentoService) 
				SpringUtil.getBean(ConstantesServicios.TIPO_DOCUMENTO_SERVICE);
		
		this.expedienteElectronicoService = (ExpedienteElectronicoService) 
				SpringUtil.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
		
		Map<String,Object> parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		
		this.ee = (ExpedienteElectronicoDTO) parametros.get("ee");
		
		this.workingTask = (Task) parametros.get("workingTask");
		
		this.comunicarTadButton = (Button) parametros.get("botonVuc");
		
		this.rehabilitarExpediente = (String) parametros.get("rehabilitar");
				
		loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

	}
	
	public void onBuscarDocumento() throws InterruptedException {
		validarDatosBuscarDocumento();
//         Quito la ejecucion de extraerAcronimo porque ahora en tiposDocumentoBandbox se guarda solo el tipo de documento
//		String actuacionSADETipoDoc = (String) extraerAcronimo(this.tiposDocumentoBandbox.getValue());
		String actuacionSADETipoDoc = (String) this.tiposDocumentoBandbox.getValue();
		Integer anioDocumento = this.anioSADEDocumento.getValue();
		Integer numeroDocumento = this.numeroSADEDocumento.getValue();
		String reparticionDocumento = (String) this.reparticionBusquedaDocumento.getValue().trim();

		buscarDoc(actuacionSADETipoDoc, anioDocumento, numeroDocumento, reparticionDocumento);
	}
	
	public void onBuscarDocumentoValidar() throws InterruptedException {
		boolean esdocumento = false;
		boolean esdocumentoespecial = false;

		cerrarVentana = true;
		
		if ((this.tiposDocumentoBandbox.getValue() != null)
				&& (!this.tiposDocumentoBandbox.getValue().trim().isEmpty())) {
			esdocumento = true;
		}

		if ((this.tiposDocumentoEspecialBandbox.getValue() != null)
				&& (!this.tiposDocumentoEspecialBandbox.getValue().trim().isEmpty())) {
			esdocumentoespecial = true;
		}

		if (esdocumento && !esdocumentoespecial) {
			onBuscarDocumento();
		} else {
			if (esdocumentoespecial && !esdocumento) {
				onBuscarDocumentoNumeroEspecial();
			} else {
				throw new WrongValueException(this.tiposDocumentoBandbox,
						Labels.getLabel("ee.tramitacion.seleccioneUnDocumento"));
			}
		}
		if(cerrarVentana) {			
			Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
		}
	}
	
	
	private void validarDatosBuscarDocumento() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = sdf.format(new Date());
		String anioFormateado = fechaActual.substring(6, 10);
		int anioActual = Integer.parseInt(anioFormateado);
		Integer anioValido = new Integer(anioActual);

		if ((this.tiposDocumentoBandbox.getValue() == null) || (this.tiposDocumentoBandbox.getValue().equals(""))) {
			throw new WrongValueException(this.tiposDocumentoBandbox,
					Labels.getLabel("ee.tramitacion.faltaTipoDocumento"));
		}

		if ((this.anioSADEDocumento.getValue() == null) || (this.anioSADEDocumento.getValue().equals(""))) {
			throw new WrongValueException(this.anioSADEDocumento, Labels.getLabel("ee.tramitacion.faltaAnio"));
		}

		if (this.anioSADEDocumento.getValue() > anioValido) {
			throw new WrongValueException(this.anioSADEDocumento, Labels.getLabel("ee.tramitacion.anioInvalido"));
		}

		if ((this.numeroSADEDocumento.getValue() == null) || (this.numeroSADEDocumento.getValue().equals(""))) {
			throw new WrongValueException(this.numeroSADEDocumento,
					Labels.getLabel("ee.tramitacion.faltaNumeroDeDocumento"));
		}

		if ((this.reparticionBusquedaDocumento.getValue() == null)
				|| (this.reparticionBusquedaDocumento.getValue().equals(""))) {
			throw new WrongValueException(this.reparticionBusquedaDocumento,
					Labels.getLabel("ee.tramitacion.faltaReparticion"));
		}

	}
	
	
	public void buscarDoc(String actuacionSADETipoDoc, Integer anioDocumento, Integer numeroDocumento,
			String reparticionDocumento) throws InterruptedException {
		DocumentoDTO documentoEstandard = null;

		documentoEstandard = documentoManagerService.buscarDocumentoEstandar(actuacionSADETipoDoc, anioDocumento,
				numeroDocumento, reparticionDocumento);

		if (documentoEstandard != null) {

			if (documentoEstandard.getMotivoDepuracion() != null) {
				Messagebox.show(Labels.getLabel("ee.tramitacion.documentoDepurado"),
						Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
						Messagebox.EXCLAMATION);
				cerrarVentana = false;
				return;
			}

			if (!this.ee.getDocumentos().contains(documentoEstandard)) {
				// TODO FALLAR SI NO SE ENCUENTRA
				TipoDocumentoDTO tipoDeDocumento = tipoDocumentoService
						.obtenerTipoDocumento(documentoEstandard.getTipoDocAcronimo());

				if (!estaHabilitado(documentoEstandard.getTipoDocAcronimo())) {
					throw new WrongValueException(this.tiposDocumentoBandbox,
							"Tipo de Documento No habilitado para la trata. Verifique los datos ingresados.");
				}

				// Habilita el botón de Notificar VUC
				if (tipoDeDocumento.getEsNotificable() && comunicarTadButton != null && comunicarTadButton.isDisabled()) {
					
					//TODO: PASAR todo del if al enviar el evento
					agregarDocNotificableSinNotf(documentoEstandard);
				}

				documentoEstandard.setTipoDocumento(tipoDeDocumento);
				documentoEstandard.setFechaAsociacion(new Date());
				documentoEstandard.setUsuarioAsociador(loggedUsername);
				
				if(this.rehabilitarExpediente != null) {
					documentoEstandard.setIdTask(this.rehabilitarExpediente);
				}else {	
					documentoEstandard.setIdTask( this.workingTask!=null ? 
							this.workingTask.getExecutionId() : null);
				}

				// Si el expediente es cabeceraTC, la tramitacion conjunta ya se
				// ha confirmado y los documentos que se adjunten a partir de
				// ese
				// momento tendran el id del expediente cabecera para luego
				// poder copiarlos a los expedientes adjuntos.
				if ((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()) {
					documentoEstandard.setIdExpCabeceraTC(this.ee.getId());
				}
				this.ee.getDocumentos().add(documentoEstandard);
				this.ee.setFechaModificacion(new Date());
				expedienteElectronicoService.modificarExpedienteElectronico(this.ee);
				
				this.enviarEventoAcordeon(Events.ON_RENDER, this.ee, false);

				if (ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL)
						&& !tipoDeDocumento.getEsConfidencial()) {
					Messagebox.show(
							Labels.getLabel("te.tramitacion.documento") + actuacionSADETipoDoc + "-" + anioDocumento.toString() + "-"
									+ BusinessFormatHelper.completarConCerosNumActuacion(numeroDocumento) + "-"
									+ reparticionDocumento + " " + Labels.getLabel("ee.tramitacion.documentoAsociado")
									+ Labels.getLabel("te.tramitacion.confidencial.question"),
							Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociado"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
					cerrarVentana = false;
				} else {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociadoParalelo"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
					cerrarVentana = false;
				}
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExiste"),
					Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
			cerrarVentana = false;
		}

		this.tiposDocumentoBandbox.setValue("");
		this.anioSADEDocumento.setValue(null);
		this.numeroSADEDocumento.setValue(null);
		this.reparticionBusquedaDocumento.setValue("");

	}

	private void agregarDocNotificableSinNotf(DocumentoDTO documentoEstandard) {
//		comunicarTadButton.setDisabled(false);
		
		// Agrega el documento al set del composer
//		if (documentosNotificablesSinNotificar == null) {
//			this.documentosNotificablesSinNotificar = new HashSet<>();
//		}
		
		Events.echoEvent(ON_ADD_DOC_NOTIFICABLE, this.self.getParent(), 
				documentoEstandard.getNumeroSade());
		
	}
	
	
	private void enviarEventoAcordeon(String onRender, ExpedienteElectronicoDTO ee2, boolean b) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("event", onRender);
		map.put("ee", ee);
		map.put("incial", b);
		
		Events.echoEvent(ON_MENSAJE_ACORDEON, this.self.getParent(), map);
		
	}

	// TODO metodo duplicado. Extraer
	private boolean estaHabilitado(String acronimo) {
		this.tiposDocumentosGEDOBD = new ArrayList<TrataTipoDocumentoDTO>();
		this.tiposDocumentosGEDOBD = tipoDocumentoService.buscarTrataTipoDocumentoPorTrata(this.ee.getTrata());

		if ((this.tiposDocumentosGEDOBD.size() > 0) && !this.tiposDocumentosGEDOBD.get(0).getAcronimoGEDO().trim()
				.equals(ConstantesWeb.SELECCIONAR_TODOS)) {
			for (TrataTipoDocumentoDTO documentoBD : this.tiposDocumentosGEDOBD) {
				if (acronimo.trim().equals(documentoBD.getAcronimoGEDO().trim())) {
					return true;
				}
			}
		} else {
			if ((this.tiposDocumentosGEDOBD.size() > 0) && this.tiposDocumentosGEDOBD.get(0).getAcronimoGEDO().trim()
					.equals(ConstantesWeb.SELECCIONAR_TODOS)) {
				return true;
			}
		}

		return false;
	}
	
	public void onBuscarDocumentoNumeroEspecial() throws InterruptedException {
		validarDatosBuscarDocumentoPorNumeroEspecial();

		String actuacionSADETipoDoc = (String) extraerAcronimo(this.tiposDocumentoEspecialBandbox.getValue());
		Integer anioDocumento = this.anioSADEDocumentoNumeroEspecial.getValue();
		Integer numeroDocumento = this.numeroSADEDocumentoNumeroEspecial.getValue();
		String reparticionDocumento = (String) this.reparticionBusquedaDocumentoNumeroEspecial.getValue().trim();
		DocumentoDTO documentoEstandard = documentoManagerService.buscarDocumentoEspecial(actuacionSADETipoDoc,
				anioDocumento.toString(), numeroDocumento.toString(), reparticionDocumento);

		if (documentoEstandard != null) {

			if (documentoEstandard.getMotivoDepuracion() != null) {
				Messagebox.show(Labels.getLabel("ee.tramitacion.documentoDepurado"),
						Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
						Messagebox.EXCLAMATION);
				cerrarVentana = false;
				return;
			}

			if (!this.ee.getDocumentos().contains(documentoEstandard)) {
				TipoDocumentoDTO tipoDeDocumento = tipoDocumentoService
						.obtenerTipoDocumento(documentoEstandard.getTipoDocAcronimo());

				if (!estaHabilitado(documentoEstandard.getTipoDocAcronimo())) {
					throw new WrongValueException(this.tiposDocumentoEspecialBandbox,
							"Tipo de Documento No habilitado para la trata. Verifique los datos ingresados.");
				}

				documentoEstandard.setFechaAsociacion(new Date());
				documentoEstandard.setUsuarioAsociador(loggedUsername);
				documentoEstandard.setIdTask(this.workingTask.getExecutionId());
				// nueva funcionalidad (cuando se desvincula una TC, agrega un
				// doc vinculado a todos los exp desvinculados)
				documentoEstandard.setIdExpCabeceraTC(this.ee.getId());

				this.ee.getDocumentos().add(documentoEstandard);
				expedienteElectronicoService.modificarExpedienteElectronico(this.ee);
				enviarEventoAcordeon(Events.ON_RENDER, this.ee, false);

				if (ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL)
						&& !tipoDeDocumento.getEsConfidencial()) {
					Messagebox.show(
							Labels.getLabel("te.tramitacion.documento") + documentoEstandard.getNumeroSade() + Labels.getLabel("te.tramitacion.confidencia.numeroEspecial")
									+ actuacionSADETipoDoc + "-" + anioDocumento + "-" + numeroDocumento + "-"
									+ reparticionDocumento + " " + Labels.getLabel("ee.tramitacion.documentoAsociado")
									+ Labels.getLabel("te.tramitacion.confidencial.question"),
							Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociado"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
					cerrarVentana = false;
				} else {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociadoParalelo"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
					cerrarVentana = false;
				}
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExiste"),
					Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
			cerrarVentana = false;
		}

		this.tiposDocumentoEspecialBandbox.setValue("");
		this.anioSADEDocumentoNumeroEspecial.setValue(null);
		this.numeroSADEDocumentoNumeroEspecial.setValue(null);
		this.reparticionBusquedaDocumentoNumeroEspecial.setValue("");

	}
	
	
	private void validarDatosBuscarDocumentoPorNumeroEspecial() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = sdf.format(new Date());
		String anioFormateado = fechaActual.substring(6, 10);
		int anioActual = Integer.parseInt(anioFormateado);
		Integer anioValido = new Integer(anioActual);

		if ((this.tiposDocumentoEspecialBandbox.getValue() == null)
				|| (this.tiposDocumentoEspecialBandbox.getValue().equals(""))) {
			throw new WrongValueException(this.tiposDocumentoEspecialBandbox,
					Labels.getLabel("ee.tramitacion.faltaTipoDocumento"));
		}

		if ((this.anioSADEDocumentoNumeroEspecial.getValue() == null)
				|| (this.anioSADEDocumentoNumeroEspecial.getValue().equals(""))) {
			throw new WrongValueException(this.anioSADEDocumentoNumeroEspecial,
					Labels.getLabel("ee.tramitacion.faltaAnio"));
		}

		if (this.anioSADEDocumentoNumeroEspecial.getValue() > anioValido) {
			throw new WrongValueException(this.anioSADEDocumentoNumeroEspecial,
					Labels.getLabel("ee.tramitacion.anioInvalido"));
		}

		if ((this.numeroSADEDocumentoNumeroEspecial.getValue() == null)
				|| (this.numeroSADEDocumentoNumeroEspecial.getValue().equals(""))) {
			throw new WrongValueException(this.numeroSADEDocumentoNumeroEspecial,
					Labels.getLabel("ee.tramitacion.faltaNumeroDeDocumento"));
		}

		if ((this.reparticionBusquedaDocumentoNumeroEspecial.getValue() == null)
				|| (this.reparticionBusquedaDocumentoNumeroEspecial.getValue().equals(""))) {
			throw new WrongValueException(this.reparticionBusquedaDocumentoNumeroEspecial,
					Labels.getLabel("ee.tramitacion.faltaReparticion"));
		}
	}
	
//	public void onFocus$reparticionBusquedaDocumentoNumeroEspecial() {
//		this.reparticionBusquedaDocumentoNumeroEspecial.open();
//	}
//	
//	
//	public void onFocus$reparticionBusquedaDocumento() {
//		this.reparticionBusquedaDocumento.open();
//		Events.sendEvent(Events.ON_NOTIFY, reparticionBusquedaDocumento.getChildren().get(0), null);
//	}
	
	public void onChanging$reparticionBusquedaDocumento(InputEvent e) {
		//String textBusquedaOrganismo = e.getValue();
		Events.sendEvent(Events.ON_CHANGING,reparticionBusquedaDocumento.getChildren().get(0), e);
	}
	
//	public void onOpen$reparticionBusquedaDocumento() {
//		System.out.println("abrir popup");
//		Events.sendEvent(Events.ON_NOTIFY, reparticionBusquedaDocumento.getChildren().get(0), null);
//
//	}
	
	
	private String extraerAcronimo(String value) {
		value = value.substring(value.indexOf("-") + 2);

		return value;
	}

	
}
