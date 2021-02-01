package com.egoveris.te.base.composer;

import com.egoveris.te.base.util.ConstantesWeb;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;

/**
 * Controlador de la ventana de confirmación de ejecución de tarea después de
 * adquirirla, desde la ventana de consulta de expedientes.
 * 
 *
 */
@SuppressWarnings("serial")
public class ConfirmacionEjecutarTareaComposer extends EEGenericForwardComposer {

  Label informacionVentanaPadre;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.zkoss.zk.ui.util.GenericForwardComposer#doAfterCompose(org.zkoss.zk.ui.
   * Component)
   */
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    String codigoExpedienteElectronico = (String) Executions.getCurrent().getDesktop()
        .getAttribute("codigoExpediente");
    this.informacionVentanaPadre
        .setValue(Labels.getLabel("ee.consultaExpedientes.adquisicionTara.mensaje.confirmacion",
            new String[] { codigoExpedienteElectronico }));
  }

  /**
   * Permite ejecutar la tarea que se adquirió
   * 
   * @throws InterruptedException
   */
  public void onClick$ejecutar() throws InterruptedException {
    Map<String, Object> datosSender = new HashMap<>();
    datosSender.put("operacion", ConstantesWeb.CONFIRMACION_EJECUCION_TAREA);
    Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), datosSender));
    this.self.detach();
  }

  /**
   * Cerrar la ventana si el usuario no desea ejecutar la tarea.
   * 
   * @throws InterruptedException
   */
  public void onClick$cerrar() throws InterruptedException {
    super.closeAndNotifyAssociatedWindow(null);
  }
}
