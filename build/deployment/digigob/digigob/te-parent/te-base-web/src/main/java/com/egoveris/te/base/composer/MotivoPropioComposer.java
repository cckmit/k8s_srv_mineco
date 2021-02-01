package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DatosEnvioParalelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class MotivoPropioComposer extends EEGenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -5482804392052279318L;

  @Autowired
  private Window motivoPropioWindow;
  @Autowired
  private Textbox motivo;
  @Autowired
  private Label tituloMotivo;
  @Autowired
  private AnnotateDataBinder binder;

  private DatosEnvioParalelo destinatario;

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    comp.addEventListener(Events.ON_NOTIFY, new MotivoPropioOnNotifyWindowListener(this));

    this.destinatario = (DatosEnvioParalelo) Executions.getCurrent().getArg().get("destinatario");

    if (destinatario.getUser() != null) {
      this.tituloMotivo
          .setValue("Motivo de Pase para el usuario: " + destinatario.getApellidoYNombre());
      if (destinatario.getUserApoderado() != null) {
        this.tituloMotivo
            .setValue("Motivo de Pase para el usuario: " + destinatario.getUserApoderado()
                + " en reemplazo por licencia de: " + destinatario.getApellidoYNombre());
      }
    } else {
      this.tituloMotivo.setValue(
          "Motivo de Pase para la reparticion: " + destinatario.getReparticionesSectores());
    }
    this.motivo.setValue(destinatario.getMotivo());
    this.motivo.setFocus(true);
  }
  
  public void onClick$guardar() {

    destinatario.setMotivo(motivo.getValue());

    Event event = new Event(Events.ON_NOTIFY, (Window) motivoPropioWindow.getParent(),
        destinatario);
    Events.sendEvent(event);

    this.closeAssociatedWindow();

  }

  public void onClick$cancelar() {

    this.closeAssociatedWindow();
  }

  public Window getMotivoPropioWindow() {
    return motivoPropioWindow;
  }

  public void setMotivoPropioWindow(Window motivoPropioWindow) {
    this.motivoPropioWindow = motivoPropioWindow;
  }

  final class MotivoPropioOnNotifyWindowListener implements EventListener {
    private MotivoPropioComposer composer;

    public MotivoPropioOnNotifyWindowListener(MotivoPropioComposer tramitacionComposer) {
      this.composer = tramitacionComposer;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        this.composer.closeAssociatedWindow();
      }
    }

  }

}
