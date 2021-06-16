package com.egoveris.te.base.composer;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.PaseExpedienteService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MotivoGuardaTemporalComposer extends EEGenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5216585857834507659L;
	private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
	
	private static Logger logger = LoggerFactory
			.getLogger(MotivoGuardaTemporalComposer.class);
	
	@WireVariable(ConstantesServicios.PASE_EXPEDIENTE_SERVICE)
	private PaseExpedienteService paseExpedienteService;

	@Autowired
	private Textbox motivoExpediente;

	private Window motivoGuardaTemporalWindow;

	@Autowired
	private AnnotateDataBinder binder;

	private Map<String, String> detalles;

	protected Task workingTask = null;
	
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	private ProcessEngine processEngine;
	
	private String estadoAnterior;

	private ExpedienteElectronicoDTO expedienteElectronico;
	@WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
	private WorkFlowService workFlowService;

	private Boolean fromDynForm;
	
	private String supervisado = null;

	
	
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;


	private String loggedUsername;

	private Button enviar;

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Window getMotivoGuardaTemporalWindow() {
		return motivoGuardaTemporalWindow;
	}

	public void setMotivoGuardaTemporalWindow(Window motivoGuardaTemporalWindow) {
		this.motivoGuardaTemporalWindow = motivoGuardaTemporalWindow;
	}

	public Button getEnviar() {
		return enviar;
	}

	public void setEnviar(Button enviar) {
		this.enviar = enviar;
	}

	public void onCancelar() {
		this.self.detach();
	}

	public void setExpedienteElectronicoService(
			ExpedienteElectronicoService expedienteElectronicoService) {
		this.expedienteElectronicoService = expedienteElectronicoService;
	}

	public ExpedienteElectronicoService getExpedienteElectronicoService() {
		return expedienteElectronicoService;
	}

	public void setExpedienteElectronico(
			ExpedienteElectronicoDTO expedienteElectronico) {
		this.expedienteElectronico = expedienteElectronico;
	}

	public ExpedienteElectronicoDTO getExpedienteElectronico() {
		return expedienteElectronico;
	}

	public void setMotivoExpediente(Textbox motivoExpediente) {
		this.motivoExpediente = motivoExpediente;
	}

	public Textbox getMotivoExpediente() {
		return motivoExpediente;
	}

	public void setDetalles(Map<String, String> detalles) {
		this.detalles = detalles;
	}

	public Map<String, String> getDetalles() {
		return detalles;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setLoggedUsername(String loggedUsername) {
		this.loggedUsername = loggedUsername;
	}

	public String getLoggedUsername() {
		return loggedUsername;
	}

	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	public Boolean getFromDynForm() {
		return this.fromDynForm;
	}

	public void setFromDynForm(Boolean fromDynForm) {
		this.fromDynForm = fromDynForm;
	}

	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		component.addEventListener(Events.ON_USER,
				new EnvioExpedienteElectronicoOnNotifyWindowListener(this));

		this.workingTask = (Task) Executions.getCurrent().getArg()
				.get("currentSelectedTask");
		this.supervisado = (String) Executions.getCurrent().getArg()
		.get("supervisados");
		this.expedienteElectronico = this.expedienteElectronicoService
				.buscarExpedienteElectronicoByIdTask(this.workingTask
						.getExecutionId());
		this.setWorkingTask((Task) this.workingTask);
		
		 
		
		this.fromDynForm = (Boolean) Executions.getCurrent().getArg().get("fromDynForm"); 
	}

	public void onEnviar() throws InterruptedException {

		// Verificar que el motivo no sea nulo
		if ((this.motivoExpediente.getValue() == null)
				|| (this.motivoExpediente.getValue().equals(""))
				|| (this.motivoExpediente.getValue().isEmpty())) {
			this.enviar.setDisabled(false);
			throw new WrongValueException(this.motivoExpediente,
					"Debe ingresar un motivo.");
		}
		
		// NEW: Verifica que el expediente electronico no sea nulo
		if (expedienteElectronico != null) {
			detalles = new HashMap<String, String>();
			detalles.put("estadoAnterior", this.expedienteElectronico.getEstado());
			detalles.put("estadoAnteriorParalelo", null);
	
			loggedUsername = (String) Executions.getCurrent().getDesktop()
					.getSession().getAttribute(ConstantesWeb.SESSION_USERNAME);
	
			if (this.expedienteElectronico.getEsCabeceraTC() != null) {
				preguntarEnvioArchivo();
	
			} else {
				preguntarEnvioArchivoEnConjunta();
			}
		}
		else {
			Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteGuardaTemporal.motivo") + getWorkingTask().getName() 
					+ Labels.getLabel("ee.tramitacion.expedienteGuardaTemporal.motivos"),
					Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	private void preguntarEnvioArchivo() throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.envio.archivo.question.value"),
				Labels.getLabel("ee.envio.archivo.question"), Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							// Esto se coloca para bloquear la ventana de atras
							// mientras se procesa el pase
							mostrarForegroundBloqueante();
							Events.echoEvent("onUser", self, "archivar");
							break;
						case Messagebox.NO:

							break;
						}
					}
				});

	}

	private void preguntarEnvioArchivoEnConjunta() throws InterruptedException {

		Messagebox.show(
				Labels.getLabel("ee.envio.archivo.questionEnConjunta.value"),
				Labels.getLabel("ee.envio.archivo.question"), Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							// Esto se coloca para bloquear la ventana de atras
							// mientras se procesa el pase
							mostrarForegroundBloqueante();
							Events.echoEvent("onUser", self, "archivar");
							

							break;
						case Messagebox.NO:

							break;
						}

					}
				});

	}



	private void archivar() throws InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>(); 
		
		try {
			//Servicio de Pase de Guarda Temporal
			this.paseExpedienteService.paseGuardaTemporal(
					this.expedienteElectronico, this.workingTask,
					this.loggedUsername, this.detalles, 
					estadoAnterior, this.motivoExpediente.getValue());

			// Avanza la tarea en el workflow
			signalExecution(ESTADO_GUARDA_TEMPORAL);
			// Vuelve a avanzar la tarea en el workflow para cerrar la misma.
			
			// 11-05-2020: No ejecutar el cierre, o desaparece la tarea
			//processEngine.getExecutionService().signalExecutionById(
			//		this.workingTask.getExecutionId(), "Cierre");

			this.expedienteElectronico.setEstado(ESTADO_GUARDA_TEMPORAL);
			
			if(getFromDynForm()!=null){
				if(!this.getFromDynForm()) {
				Messagebox.show(Labels.getLabel("te.base.composer.enviocomposer.generic.elexpediente") + expedienteElectronico.getCodigoCaratula()
						+ Labels.getLabel("te.base.composer.enviocomposer.generic.seguardatemporalmente"),
						Labels.getLabel("ee.tramitacion.titulo.pase"),
						Messagebox.OK, Messagebox.INFORMATION);
	
						enviarEvento();
				} else {
					params.put("origen", ConstantesWeb.ESTADO_GUARDA_TEMPORAL);
					params.put("msg", "El expediente: " + expedienteElectronico.getCodigoCaratula()
							+ " , se ha guardado temporalmente de forma correcta.");
				}
			}
			if(supervisado!=null){
				enviarEvento();
			}

			this.closeAndNotifyAssociatedWindow(params);

        } catch (RemoteAccessException e){
        	logger.error(e.getMessage(), e);
        	Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
    	} catch (Exception e) {
		logger.error("Error al archivar el expediente", e);
		Messagebox
				.show("Se ha producido un error al archivar el expediente electrónico.",
						"ERROR", Messagebox.OK, Messagebox.ERROR);
		} finally {

			Clients.clearBusy();

		}

	}
	
	// Envio el evento onClick a mi boton btnrefresh del padre ,para que
	// me refresque el listbox del mismo.
	private void enviarEvento(){
		Events.sendEvent(new Event("onClick",
				(Button) ((Window) motivoGuardaTemporalWindow.getParent())
						.getFellow("btnrefresh")));
	}

	private void mostrarForegroundBloqueante() {
		Clients.showBusy(Labels
				.getLabel("ee.tramitacion.enviarExpedienteElectronico.value"));
	}

	public void signalExecution(String nameTransition) {
		// PASO TODAS LAS VARIABLES ANTES DE REALIZAR EL SIGNAL
		setearVariablesAlWorkflow();

		// Paso a la siguiente Tarea/Estado definida en el Workflow
		processEngine.getExecutionService().signalExecutionById(
				this.workingTask.getExecutionId(), nameTransition);

	}

	private void setearVariablesAlWorkflow() throws NumberFormatException {
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("estadoAnterior", detalles.get("estadoAnterior"));
		variables.put("estadoAnteriorParalelo",detalles.get("estadoAnteriorParalelo"));
		variables.put("grupoSeleccionado",detalles.get("grupoSeleccionado"));
		variables.put("tareaGrupal", detalles.get("tareaGrupal"));
		variables.put("usuarioSeleccionado",detalles.get("usuarioSeleccionado"));
		variables.put("idExpedienteElectronico",Integer.parseInt(detalles.get("idExpedienteElectronico")));
		setVariablesWorkFlow(variables);
	}

	@Deprecated
	public void setVariableWorkFlow(String name, Object value) {
		this.processEngine.getExecutionService().setVariable(
				this.workingTask.getExecutionId(), name, value);
	}
	
	public void setVariablesWorkFlow(Map<String, Object> variables) {
    	workFlowService.setVariables(processEngine, this.workingTask.getExecutionId(), variables);
    }

	final class EnvioExpedienteElectronicoOnNotifyWindowListener implements
			EventListener {
		private MotivoGuardaTemporalComposer composer;

		public EnvioExpedienteElectronicoOnNotifyWindowListener(
				MotivoGuardaTemporalComposer comp) {
			this.composer = comp;
		}

		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_USER)) {
				if (event.getData().equals("archivar")) {
					this.composer.archivar();
				}
			}
		}

	}
}
