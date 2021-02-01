package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ActividadSolicGuardaTemp;
import com.egoveris.te.base.service.iface.IActivGuardaTempService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.FiltroEE;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;


public class VerPeticionExternaComposer extends GenericActividadComposer {

  private static final long serialVersionUID = -9024692812522251881L;

  // Service
  private IActivGuardaTempService activGuardaTempService;

  private Label expLabel;
  private Textbox motivoTextBox;
  private Label mailDestinoValorLabel;
  private Label mailDestinoLabel;
  private Hbox hboxExterior;
  private ActividadSolicGuardaTemp solicitud;
  private String loggedUsername;
  private Window guardaTempWindow;
  private Toolbarbutton cancelar_act;
  private IActividadExpedienteService actividadExpedienteService;

  @Autowired
  private Window tramitacionWindow;

  private AnnotateDataBinder verActividadBinder;

  @Override
  protected void modoLecturaComposer(Component c) throws Exception {
    initComposer(c);
    renderHboxDate();
  }

  @Override
  protected void modoEdicionComposer(Component c) throws Exception {
    initComposer(c);

  }

  private void initComposer(Component c) throws Exception {
    actividadExpedienteService = (IActividadExpedienteService) SpringUtil
        .getBean(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE);
    activGuardaTempService = (IActivGuardaTempService) SpringUtil
        .getBean(ConstantesServicios.ACTIV_GUARDA_TEMP_SERVICE);
    solicitud = activGuardaTempService.buscarActividadSolicitudGuardaTemp(getPopupActividad());
    loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    // Titulo de la ventana con tipo de actividad
    ((Window) this.self).setTitle(getPopupActividad().getTipoActividadDecrip());

    // nro expediente
    this.expLabel.setValue(getPopupActividad().getNroExpediente());

    // motivo
    this.motivoTextBox.setValue(solicitud.getMotivo());

    if (solicitud.getMailDestino() != null && solicitud.getMailDestino().length() != 0) {
      // mail destino
      mailDestinoValorLabel.setVisible(true);
      mailDestinoLabel.setVisible(true);
      this.mailDestinoValorLabel.setValue(solicitud.getMailDestino());
    } else {
      mailDestinoValorLabel.setVisible(false);
      mailDestinoLabel.setVisible(false);
    }

    this.verActividadBinder = new AnnotateDataBinder(c);
    this.verActividadBinder.loadAll();
    this.renderHboxButtonsRechazar();
  }

  private void renderHboxButtons() {
    // tiene fecha de baja no dibujo los botones - inconsistencia
    if (getPopupActividad().getFechaBaja() == null) {
      // accion - Aprobar
      Toolbarbutton button = new Toolbarbutton(Labels.getLabel("ee.act.label.aprobar"),
          "/imagenes/control_add_blue.png");
      button.setParent(hboxExterior);
      button.addEventListener(Events.ON_CLICK, new AprobacionEventListener(hboxExterior));

      // accion - Rechazar
      Toolbarbutton button2 = new Toolbarbutton(Labels.getLabel("ee.act.label.rechazar"),
          "/imagenes/decline.png");
      button2.setParent(hboxExterior);
      button2.addEventListener(Events.ON_CLICK, new RechazoEventListener(hboxExterior));
    }
  }
  
  private void renderHboxButtonsRechazar() {
	    // tiene fecha de baja no dibujo los botones - inconsistencia
	    if (getPopupActividad().getFechaBaja() == null) { 
	      // accion - Rechazar
	      Toolbarbutton button2 = new Toolbarbutton(Labels.getLabel("ee.act.label.rechazar"),
	          "/imagenes/decline.png");
	      button2.setParent(hboxExterior);
	      button2.addEventListener(Events.ON_CLICK, new RechazoEventListener(hboxExterior));
	    }
	  }

  private void renderHboxDate() {
    // sino tiene fecha de baja no dibujo ni la fecha ni los botones
    if (getPopupActividad().getFechaBaja() != null) {
      // fecha
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date fechaBaja = getPopupActividad().getFechaBaja();
      Label labelFechaAprob = new Label(
          getPopupActividad().getEstado() + " ( " + sdf.format(fechaBaja) + " )");
      labelFechaAprob.setParent(hboxExterior);
    }
  }

  public void onClick$cerrar() {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  // onclick visualizar
  public void onVerExpediente() throws SuspendNotAllowedException, InterruptedException {
    // nro expediente
    Executions.getCurrent().getDesktop().setAttribute("codigoExpedienteElectronico",
        getPopupActividad().getNroExpediente());
    Window popupDocumentoWindow = (Window) Executions
        .createComponents("/expediente/detalleExpedienteElectronico.zul", null, null);
    popupDocumentoWindow.setClosable(true);
    popupDocumentoWindow.doModal();

  }

  // onclick cancelar actividad
  public void onCancelarActividad() throws SuspendNotAllowedException, InterruptedException {
    actividadExpedienteService.eliminarActividadGedo(solicitud.getNroExpediente());
    Messagebox.show(Labels.getLabel("ee.act.msg.body.gedo.recha"),
        Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
    Events.sendEvent(new Event(Events.ON_USER, (Component) tramitacionWindow, "elimActividad"));
    onClick$cerrar();
  }

  public void setCancelar_act(Toolbarbutton cancelar_act) {
    this.cancelar_act = cancelar_act;
  }

  public Toolbarbutton getCancelar_act() {
    return cancelar_act;
  }

  private class AprobacionEventListener implements EventListener {

    Component comp;

    public AprobacionEventListener(Component comp) {
      this.comp = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {

      activGuardaTempService.aprobarPaseGuardaTemporal(solicitud, loggedUsername);

      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Calendar cal = Calendar.getInstance();
      Date date = cal.getTime();
      Label labelFechaAprob = new Label("APROBADA ( " + sdf.format(date) + " )");
      comp.getChildren().clear();
      labelFechaAprob.setParent(comp);

      Events.sendEvent(new Event(Events.ON_USER, guardaTempWindow.getParent(), "aprobGuardaTemp"));

      Messagebox.show(Labels.getLabel("ee.act.msg.body.guardaDoc.aprob"),
          Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  public Window getGuardaTempWindow() {
    return guardaTempWindow;
  }

  public void setGuardaTempWindow(Window guardaTempWindow) {
    this.guardaTempWindow = guardaTempWindow;
  }

  private class RechazoEventListener implements EventListener {

    Component comp;

    public RechazoEventListener(Component comp) {
      this.comp = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {

      activGuardaTempService.rechazarPaseGuardaTemporal(solicitud, loggedUsername);

      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Calendar cal = Calendar.getInstance();
      Date date = cal.getTime();
      Label labelFechaAprob = new Label("RECHAZADA ( " + sdf.format(date) + " )");
      comp.getChildren().clear();
      labelFechaAprob.setParent(comp);

      Messagebox.show(Labels.getLabel("ee.act.msg.body.guardaDoc.recha"),
          Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
      
      FiltroEE.blockedChildren(tramitacionWindow.getChildren(), false);
    }
  }

  // private class CancelarActividadEventListener implements EventListener {
  //
  // private VerPeticionExternaComposer composer;
  //
  // Component comp;
  //
  // public CancelarActividadEventListener(Component comp) {
  // this.comp = comp;
  // }
  //
  // @Override
  // public void onEvent(Event event) throws Exception {
  //
  // actividadExpedienteService.eliminarActividad(solicitud.getNroExpediente());
  //
  // Events.sendEvent(this.composer.getGuardaTempWindow(),new
  // Event(Events.ON_CLOSE));
  //
  // Events.sendEvent(new Event(Events.ON_USER, comp.getParent(),
  // "elimActividad"));
  //
  // Messagebox.show(Labels.getLabel("ee.act.msg.body.gedo.recha"),
  // Labels.getLabel("ee.act.msg.title.ok"),Messagebox.OK,
  // Messagebox.INFORMATION);
  // }
  // }
}
