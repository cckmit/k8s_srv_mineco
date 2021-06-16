package com.egoveris.deo.web.satra.firma;

import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RechazoDocumentoComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -539681599103186956L;

  private Textbox motivoRechazo;
  private Window rechazoDocumentoWindow;
  @WireVariable("processEngine")
  private ProcessEngine processEngine;
  private Checkbox solicitudEnvioCorreo;
  private String workflowId;
  private String usuarioReceptorRechazo;
  @WireVariable("historialServiceImpl")
  private HistorialService historialService;

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    Map<String, String> datos = (HashMap<String, String>) Executions.getCurrent().getArg();
    this.workflowId = datos.get("workflowId");
    this.usuarioReceptorRechazo = datos.get("usuarioReceptorRechazo");
    component.addEventListener(Events.ON_USER, new RechazoEventListener(this));
  }

  /**
   * Cancela el envío del rechazo.
   */
  public void onClick$cancelar() {
    this.rechazoDocumentoWindow.detach();
  }

  /**
   * Confirmar el envío del rechazo de la tarea de firma. Cierra la ventana hija
   * y la ventana padre.
   * 
   * @throws InterruptedException
   */
  public void onClick$confirmar() throws InterruptedException {
    if (StringUtils.isEmpty(this.motivoRechazo.getValue())) {
      throw new WrongValueException(this.motivoRechazo,
          Labels.getLabel("gedo.rechazarDocumento.motivo.error"));
    }
    Clients.showBusy(Labels.getLabel("gedo.rechazarDocumento.mensajeEnvioRechazo"));
    Events.echoEvent("onUser", this.self, "rechazoFirma");
  }

  /**
   * Envia la tarea de Rechazado al usuario Derivador
   * 
   * @throws InterruptedException
   */
  public void enviarTareaRechazado() throws InterruptedException {
    this.processEngine.getExecutionService().setVariable(workflowId, Constantes.VAR_MOTIVO_RECHAZO,
        this.motivoRechazo.getValue());
    this.processEngine.getExecutionService().setVariable(workflowId,
        Constantes.VAR_SOLICITUD_ENVIO_MAIL, this.solicitudEnvioCorreo.isChecked());
    ProcessInstance pIns = this.processEngine.getExecutionService()
        .findProcessInstanceById(this.workflowId);
    Set<String> acts = pIns.findActiveActivityNames();
    String actividad = acts.iterator().next();
    this.historialService.actualizarHistorial(this.workflowId);
    this.processEngine.getExecutionService().signalExecutionById(this.workflowId,
        Constantes.TRANSICION_RECHAZAR_DOCUMENTO);
    Clients.clearBusy();
    this.mostrarMensajeEnvioMail();
    Messagebox.show(
        Labels.getLabel("gedo.rechazarDocumento.envioRechazo",
            new String[] { this.usuarioReceptorRechazo }),
        Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);

    Map<String, Object> datos = new HashMap<>();
    datos.put("funcion", "regularizacionDocumentoAdjuntoNuevoRepositorio");

    Events.sendEvent(new Event(Events.ON_USER, this.self.getParent(), datos));

    Events.sendEvent(new Event(Events.ON_CLOSE, this.self.getParent()));
    this.self.detach();
  }

  private void mostrarMensajeEnvioMail() throws InterruptedException {
    Boolean falloEnvioSolicitudMail = (Boolean) this.processEngine.getExecutionService()
        .getVariable(this.workflowId, Constantes.VAR_SOLICITUD_ENVIO_MAIL_FAIL);
    if (falloEnvioSolicitudMail != null && falloEnvioSolicitudMail) {

      Messagebox.show(Labels.getLabel("gedo.error.enviarCorreo"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

}

final class RechazoEventListener implements EventListener {
  private RechazoDocumentoComposer composer;

  public RechazoEventListener(RechazoDocumentoComposer composer) {
    this.composer = composer;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER) && "rechazoFirma".equals(event.getData())) {
      this.composer.enviarTareaRechazado();

    }
  }
}
