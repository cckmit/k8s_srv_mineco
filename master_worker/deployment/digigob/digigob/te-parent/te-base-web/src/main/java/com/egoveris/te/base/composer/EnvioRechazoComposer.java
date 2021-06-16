package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.service.SolicitudExpedienteService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EnvioRechazoComposer extends EEGenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 6995314325727794155L;
  @Autowired
  private Window envioRechazoWindow;
  @Autowired
  private Textbox motivoRechazoSolicitud;
  protected Task workingTask = null;
  
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  protected ProcessEngine processEngine;
  
  @WireVariable(ConstantesServicios.SOLICITUD_EXPEDIENTE_SERVICE)
  private SolicitudExpedienteService solicitudExpedienteService;

  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;

  public Window getEnvioRechazoWindow() {
    return envioRechazoWindow;
  }

  public void setEnvioRechazoWindow(Window envioRechazoWindow) {
    this.envioRechazoWindow = envioRechazoWindow;
  }

  public Textbox getMotivoRechazoSolicitud() {
    return motivoRechazoSolicitud;
  }

  public void setMotivoRechazoSolicitud(Textbox motivoRechazoSolicitud) {
    this.motivoRechazoSolicitud = motivoRechazoSolicitud;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public SolicitudExpedienteService getSolicitudExpedienteService() {
    return solicitudExpedienteService;
  }

  public void setSolicitudExpedienteService(
      SolicitudExpedienteService solicitudExpedienteService) {
    this.solicitudExpedienteService = solicitudExpedienteService;
  }

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));

  }

  @SuppressWarnings("unchecked")
  public void onEnviar() throws InterruptedException {

    if ((this.motivoRechazoSolicitud.getValue() == null)
        || (this.motivoRechazoSolicitud.getValue().equals(""))) {
      throw new WrongValueException(motivoRechazoSolicitud,
          Labels.getLabel("ee.tramitacion.rechazo.motivoDeRechazo"));
    }

    String motivoDeRechazo = (String) this.motivoRechazoSolicitud.getText();

    Map<String, Object> variables = (Map<String, Object>) this.envioRechazoWindow
        .getAttribute("variables");

    Long idSolicitud = (Long) variables.get("idSolicitud");
    SolicitudExpedienteDTO solicitud = solicitudExpedienteService
        .obtenerSolitudByIdSolicitud(idSolicitud);

    solicitud.setMotivoDeRechazo(motivoDeRechazo);

    solicitud.setMotivo(motivoDeRechazo);

    solicitudExpedienteService.modificarSolicitud(solicitud);

    String usuarioSolicitante = variables.get("usuarioSolicitante").toString();
    String usuarioCandidato = variables.get("usuarioCandidato").toString();

    Map<String, Object> mapa = new HashMap<String, Object>();
    mapa.put("usuarioCandidato", usuarioCandidato);
    mapa.put("idsolicitud", solicitud.getId());
    setVariablesWorkFlow(mapa);

    this.signalExecution("Anular/Modificar Solicitud", usuarioSolicitante);

    this.closeAssociatedWindow();

  }

  public void onCancelar() {

    this.envioRechazoWindow.detach();

  }

  @Deprecated
  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
        value);
  }

  public void signalExecution(String nameTransition, String usernameDerivador) {
  
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("usuarioSolicitante", usernameDerivador);
    variables.put("usuarioSeleccionado", usernameDerivador);
    variables.put("tareaGrupal", "noEsTareaGrupal");
    variables.put("grupoSeleccionado", null);
    setVariablesWorkFlow(variables);
    processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
        nameTransition);
  }

  public void setVariablesWorkFlow(Map<String, Object> variables) {
    workFlowService.setVariables(processEngine, this.workingTask.getExecutionId(), variables);
  }

}
