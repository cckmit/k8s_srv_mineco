package com.egoveris.te.base.composer;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.service.DocumentoManagerService;
import com.egoveris.te.base.service.TipoDocumentoDAO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TramitacionTabsConditional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;


/**
 * @author dpadua
 * @Improved by Jorge Flores
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VinculacionActoAdministrativoComposer extends EEGenericForwardComposer {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Autowired
	private Component acordeon;

	protected ProcessEngine processEngine;

	public static String MEMORANDUM = "ME";
	public static String NOTA = "NO";
	private static final String TRAMITACION_EN_PARALELO = "Paralelo";
	private static final String ES_RESERVADO = " - Expediente Reservado";

	@WireVariable("tipoDocumentoDAO")
	private TipoDocumentoDAO tipoDocumentoDAO;
	private List<TrataTipoDocumentoDTO> tiposDocumentosGEDOBD;

	private String loggedUsername;

	// windows
	private Window vinculacionActoAdministrativoWindow;

	// button
	@Autowired
	private Button vincularDocumento;
	@Autowired
	private Button cancelar;

	// bandbox
	@Autowired
	protected Bandbox tiposDocumentoActoAdministrativoBandbox;
	@Autowired
	protected Bandbox reparticionBusquedaDocumento;

	// intbox
	@Autowired
	protected Intbox anioSADEDocumentoActAdm;
	@Autowired
	protected Intbox numeroSADEDocumentoActAdm;

	@WireVariable(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE)
	private DocumentoManagerService documentoManagerService;
  
	protected TipoDocumentoService tipoDocumentoService;

	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	protected ExpedienteElectronicoDTO ee;

	@Autowired
	private Button reservar;

	@Autowired
	private Button tramitacionParalelo;

	@Autowired
	private Button quitarReserva;

	@Autowired
	public Tab expedienteTramitacionConjunta;

	@Autowired
	public Tab expedienteFusion;

	@Autowired
	private Window tramitacionWindow;

	protected Task workingTask;

	private DocumentoDTO documentoEstandard;

	@Autowired
	private AppProperty appProperty;

	private String tipoOperacion;
	// private String tipoOperacion1;

	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;

	protected IAccesoWebDavService visualizaDocumentoService;

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null)); 

		this.ee = (ExpedienteElectronicoDTO) Executions.getCurrent().getArg().get("expedienteElectronico");
		this.tipoOperacion = (String) Executions.getCurrent().getArg().get("tipoOperacion");
		this.reservar = (Button) Executions.getCurrent().getDesktop().getAttribute("reservarButton");
		this.quitarReserva = (Button) Executions.getCurrent().getDesktop().getAttribute("quitarReservaButton");
		this.tramitacionParalelo = (Button) Executions.getCurrent().getDesktop()
				.getAttribute("tramitacionParaleloButton");
		this.tramitacionWindow = (Window) Executions.getCurrent().getDesktop().getAttribute("tramitacionWindow");
		this.acordeon = (Component) Executions.getCurrent().getDesktop().getAttribute("acordeonWindow");
		this.expedienteTramitacionConjunta = (Tab) Executions.getCurrent().getDesktop()
				.getAttribute("expedienteTramitacionConjuntaTab");
		this.expedienteFusion = (Tab) Executions.getCurrent().getDesktop().getAttribute("expedienteFusionTab"); 
		 
		 
		this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));

	}

	public void onCancelar() {
		this.vinculacionActoAdministrativoWindow.detach();
	}

	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	/**
	 * 
	 * @param name
	 *            : nombre de la variable que quiero setear
	 * @param value
	 *            : valor de la variable
	 */
	public void setVariableWorkFlow(String name, Object value) {
		this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name, value);
	}

	public void onBuscarDocumento() throws InterruptedException {
		validarDatosBuscarDocumento();
		String tipoDocumento = (String) this.tiposDocumentoActoAdministrativoBandbox.getValue();
		Integer anioDocumento = this.anioSADEDocumentoActAdm.getValue();
		Integer numeroDocumento = this.numeroSADEDocumentoActAdm.getValue();
		String reparticionDocumento = (String) this.reparticionBusquedaDocumento.getValue().trim();
		DocumentoDTO documentoEstandard = documentoManagerService.buscarDocumentoEstandar(tipoDocumento, anioDocumento,
				numeroDocumento, reparticionDocumento);

		if (documentoEstandard != null) {

			if (documentoEstandard.getMotivoDepuracion() != null) {
				Messagebox.show(Labels.getLabel("ee.tramitacion.documentoDepurado"),
						Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
						Messagebox.EXCLAMATION);
				return;
			}

			if (!this.ee.contieneDoc(documentoEstandard.getNumeroSade())) {
				// TODO FALLAR SI NO SE ENCUENTRA
				String tipoDocAcronimo = null;
				if ((tipoDocumento.equals(NOTA)) || (tipoDocumento.equals(MEMORANDUM))) {
					tipoDocAcronimo = tipoDocumento;
				} else {
					tipoDocAcronimo = tipoDocumentoService
							.obtenerTiposDocumentoGEDO(documentoEstandard.getTipoDocGedo()).getAcronimo();
				}

				if (!estaHabilitado(tipoDocAcronimo)) {
					throw new WrongValueException(this.tiposDocumentoActoAdministrativoBandbox,
							"Tipo de Documento No habilitado para la trata. Verifique los datos ingresados.");
				}

				documentoEstandard.setTipoDocAcronimo(tipoDocAcronimo);
				documentoEstandard.setFechaAsociacion(new Date());
				documentoEstandard.setUsuarioAsociador(loggedUsername);
				documentoEstandard.setIdTask(workingTask.getExecutionId());
				// Si el expediente es cabeceraTC, la tramitacion conjunta ya se
				// ha confirmado y los documentos que se adjunten a partir de
				// ese
				// momento tendran el id del expediente cabecera para luego
				// poder copiarlos a los expedientes adjuntos.
				if (this.ee.getEsCabeceraTC() != null && this.ee.getEsCabeceraTC()) {
					documentoEstandard.setIdExpCabeceraTC(this.ee.getId());
				}
				documentoEstandard.setDefinitivo(true);

				this.documentoEstandard = documentoEstandard;

				if (tipoOperacion.equals("ALTA")) {
					String[] ar = new String[1];
					ar[0] = "\n";
					Messagebox.show(Labels.getLabel("ee.tramitacion.reserva.question.actoAdministrativo", ar),
							Labels.getLabel("ee.tramitacion.reserva.titulo"), Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event evt) throws InterruptedException {
									switch (((Integer) evt.getData()).intValue()) {
									case Messagebox.YES:
										reservarExpediente();
										break;
									case Messagebox.NO:
										break;
									}
								}
							});
				} else {
					String[] ar = new String[1];
					ar[0] = "\n";
					Messagebox.show(
							Labels.getLabel("ee.tramitacion.reserva.question.actoAdministrativoQuitarReserva", ar),
							Labels.getLabel("ee.tramitacion.reserva.titulo"), Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event evt) throws InterruptedException {
									switch (((Integer) evt.getData()).intValue()) {
									case Messagebox.YES:
										quitarReservaExpediente();
										break;
									case Messagebox.NO:
										break;
									}
								}
							});
				}
			} else {
				if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociado"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
				} else {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociadoParalelo"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
				}
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExiste"),
					Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	private void quitarReservaExpediente() throws InterruptedException {

		String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		Usuario user = usuariosSADEService.getDatosUsuario(loggedUsername);
		this.ee.hacerDefinitivosDocumentosVinculadosPor(loggedUsername);
		this.ee.hacerDefinitivosArchivosDeTranajoVinculadosPor(loggedUsername);
		expedienteElectronicoService.actualizarDocumentosEnGedo(ee, loggedUsername, null,
				ee.getTrata().getCodigoTrata());
		expedienteElectronicoService.actualizarReservaEnLaTramitacion(ee, loggedUsername);
		expedienteElectronicoService.actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(ee, loggedUsername,
				new ArrayList<ReparticionParticipanteDTO>(), user);
		ee.setEsReservado(false);
		documentoEstandard.setFechaAsociacion(new Date());
		documentoEstandard.setUsuarioAsociador(this.loggedUsername);
		ee.agregarDocumento(documentoEstandard);
		documentoEstandard.setDefinitivo(true);
		expedienteElectronicoService.modificarExpedienteElectronico(ee);

		this.enviarEventoAcordeon(Events.ON_RENDER, this.ee, false);

		this.reservar.setDisabled(false);
		this.reservar.setVisible(true);
		this.quitarReserva.setVisible(false);
		this.expedienteTramitacionConjunta.setDisabled(
				new TramitacionTabsConditional(this.workingTask).condition(this.ee).getIsTramitacionConjunta());
		this.expedienteFusion.setDisabled(false);
		this.tramitacionWindow.setTitle(workingTask.getActivityName());

		this.vinculacionActoAdministrativoWindow.detach();

		Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "quitarReservaExpediente");
	}

	public void reservarExpediente() throws InterruptedException {
		String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		Usuario user = usuariosSADEService.getDatosUsuario(loggedUsername);

		this.ee.setUsuarioReserva(loggedUsername);
		this.ee.setEsReservado(true);
		this.ee.setFechaReserva(new Date());

		this.documentoEstandard.setFechaAsociacion(new Date());
		this.documentoEstandard.setUsuarioAsociador(loggedUsername);
		this.ee.getDocumentos().add(this.documentoEstandard);
		this.ee.hacerDefinitivosDocumentosVinculadosPor(loggedUsername);
		this.ee.hacerDefinitivosArchivosDeTranajoVinculadosPor(loggedUsername);
		expedienteElectronicoService.actualizarDocumentosEnGedo(ee, loggedUsername, null,
				ee.getTrata().getCodigoTrata());
		expedienteElectronicoService.actualizarReservaEnLaTramitacion(ee, loggedUsername);
		expedienteElectronicoService.actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(ee, loggedUsername,
				new ArrayList<ReparticionParticipanteDTO>(), user);
		this.reservar.setVisible(false);
		this.quitarReserva.setVisible(true);
		// con la nueva logica de reserva no se deben deshabilitar estas
		// pestañas

		this.tramitacionWindow.setTitle(workingTask.getActivityName() + ES_RESERVADO);

		// Esto no va, xq se reserva a partir de la vinculacion definitiva, o
		// bien
		// desde el pase.

		expedienteElectronicoService.modificarExpedienteElectronico(this.ee);

		this.enviarEventoAcordeon(Events.ON_RENDER, this.ee, false);

		this.vinculacionActoAdministrativoWindow.detach();

		Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "modificarTituloReservaExpediente");
	}

	private void enviarEventoAcordeon(String evento, ExpedienteElectronicoDTO expedienteElectronico, Boolean inicial) {
		Map<String, Object> dataHm = new HashMap<>();
		dataHm.put("expedienteElectronico", expedienteElectronico);
		dataHm.put("inicial", inicial);
		Events.sendEvent(evento, this.acordeon, dataHm);
	}

	private void validarDatosBuscarDocumento() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = sdf.format(new Date());
		String anioFormateado = fechaActual.substring(6, 10);
		int anioActual = Integer.parseInt(anioFormateado);
		Integer anioValido = new Integer(anioActual);
		if ((this.tiposDocumentoActoAdministrativoBandbox.getValue() == null)
				|| (this.tiposDocumentoActoAdministrativoBandbox.getValue().equals(""))) {
			throw new WrongValueException(this.tiposDocumentoActoAdministrativoBandbox,
					Labels.getLabel("ee.tramitacion.faltaTipoDocumento"));
		}
		if ((this.anioSADEDocumentoActAdm.getValue() == null) || (this.anioSADEDocumentoActAdm.getValue().equals(""))) {
			throw new WrongValueException(this.anioSADEDocumentoActAdm, Labels.getLabel("ee.tramitacion.faltaAnio"));
		}
		if (this.anioSADEDocumentoActAdm.getValue() > anioValido) {
			throw new WrongValueException(this.anioSADEDocumentoActAdm, Labels.getLabel("ee.tramitacion.anioInvalido"));
		}
		if ((this.numeroSADEDocumentoActAdm.getValue() == null)
				|| (this.numeroSADEDocumentoActAdm.getValue().equals(""))) {
			throw new WrongValueException(this.numeroSADEDocumentoActAdm,
					Labels.getLabel("ee.tramitacion.faltaNumeroDeDocumento"));
		}
		if ((this.reparticionBusquedaDocumento.getValue() == null)
				|| (this.reparticionBusquedaDocumento.getValue().equals(""))) {
			throw new WrongValueException(this.reparticionBusquedaDocumento,
					Labels.getLabel("ee.tramitacion.faltaReparticion"));
		}
	}

	private boolean estaHabilitado(String acronimo) {
		this.tiposDocumentosGEDOBD = new ArrayList<TrataTipoDocumentoDTO>();
		this.tiposDocumentosGEDOBD = tipoDocumentoDAO.buscarTrataTipoDocumento(this.ee.getTrata());
		if (this.tiposDocumentosGEDOBD.size() > 0
				&& !this.tiposDocumentosGEDOBD.get(0).getAcronimoGEDO().trim().equals(ConstantesWeb.SELECCIONAR_TODOS)) {
			for (TrataTipoDocumentoDTO documentoBD : this.tiposDocumentosGEDOBD) {
				if (acronimo.trim().equals(documentoBD.getAcronimoGEDO().trim())) {
					return true;
				}
			}
		} else {
			if (this.tiposDocumentosGEDOBD.size() > 0 && this.tiposDocumentosGEDOBD.get(0).getAcronimoGEDO().trim()
					.equals(ConstantesWeb.SELECCIONAR_TODOS)) {
				return true;
			}
		}
		return false;
	}

	public AppProperty getAppProperty() {
		return appProperty;
	}

	public void setAppProperty(AppProperty appProperty) {
		this.appProperty = appProperty;
	}

}