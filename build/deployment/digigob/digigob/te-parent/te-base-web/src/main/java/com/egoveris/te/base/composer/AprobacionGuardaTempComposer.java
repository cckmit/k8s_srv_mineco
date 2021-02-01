  package com.egoveris.te.base.composer;

import com.egoveris.te.base.exception.GuardaTemporalException;
import com.egoveris.te.base.model.ActividadSolicGuardaTemp;
import com.egoveris.te.base.service.iface.IActivGuardaTempService;
import com.egoveris.te.base.util.ConstantesWeb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;



public class AprobacionGuardaTempComposer extends GenericActividadComposer {

	private static final long serialVersionUID = -9024692812522251881L;

	// Service
	private IActivGuardaTempService activGuardaTempService;

	private Label expLabel;
	private Textbox motivoTextBox;
	private Hbox hboxExterior;
	private ActividadSolicGuardaTemp solicitud;
	private String loggedUsername;
	private Window guardaTempWindow;

	@Override
	protected void modoLecturaComposer(Component c) throws Exception {
		initComposer(c);
		 renderHboxDate();
	}

	@Override
	protected void modoEdicionComposer(Component c) throws Exception {
		initComposer(c);
		renderHboxButtons();
		
	}
	
	private void initComposer(Component c) throws GuardaTemporalException {
		activGuardaTempService = (IActivGuardaTempService) SpringUtil.getBean("activGuardaTempServiceImpl");
		solicitud = activGuardaTempService.buscarActividadSolicitudGuardaTemp(getPopupActividad());
		loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);

		// Titulo de la ventana con tipo de actividad
		((Window) this.self).setTitle(getPopupActividad().getTipoActividadDecrip());

		// nro expediente
		this.expLabel.setValue(getPopupActividad().getNroExpediente());

		// motivo
		this.motivoTextBox.setValue(solicitud.getMotivo());
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
			Toolbarbutton button2 = new Toolbarbutton(Labels.getLabel("ee.act.label.rechazar"), "/imagenes/decline.png");
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
			Label labelFechaAprob = new Label(getPopupActividad().getEstado() + " ( " + sdf.format(fechaBaja) + " )");
			labelFechaAprob.setParent(hboxExterior);
		}
	}

	public void onClick$cerrar() {
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}

	// onclick visualizar
	public void onVerExpediente() throws SuspendNotAllowedException, InterruptedException {
		DetalleExpedienteElectronicoComposer.openInModal(this.self, getPopupActividad().getNroExpediente());
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

			Messagebox.show(Labels.getLabel("ee.act.msg.body.guardaDoc.aprob"), Labels.getLabel("ee.act.msg.title.ok"),
					Messagebox.OK, Messagebox.INFORMATION);
		}
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

			Messagebox.show(Labels.getLabel("ee.act.msg.body.guardaDoc.recha"), Labels.getLabel("ee.act.msg.title.ok"),
					Messagebox.OK, Messagebox.INFORMATION);
		}
	}
}
