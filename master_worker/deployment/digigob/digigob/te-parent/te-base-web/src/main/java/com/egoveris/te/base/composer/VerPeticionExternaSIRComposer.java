package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ActividadSirAprobDocTad;
import com.egoveris.te.base.model.ActividadSolicGuardaTemp;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.rendered.ActivDocumentoSirItemRender;
import com.egoveris.te.base.service.iface.IActivAsociarDocsSIRService;
import com.egoveris.te.base.service.iface.IActivGuardaTempService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.ConstTipoActividad;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.model.exception.ProcesoFallidoException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VerPeticionExternaSIRComposer extends GenericActividadComposer {
	
	private final static Logger logger = LoggerFactory.getLogger(VerPeticionExternaSIRComposer.class);
	
	private static final long serialVersionUID = -9024692812522251881L;

	// Service
	@WireVariable("ActivGuardaTempServiceImpl")
	private IActivGuardaTempService activGuardaTempService;

	private Label expLabel;
	private Textbox motivoTextBox;
	private Label mailDestinoValorLabel;
	private Label mailDestinoLabel;
	private Hbox hboxExterior;
	private ActividadSolicGuardaTemp solicitud;
	private String loggedUsername;
	private Window peticionExternaWindow;
	private Toolbarbutton cancelar_act;
	@WireVariable(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE)
	private IActividadExpedienteService actividadExpedienteService;
	@WireVariable(ConstantesServicios.ACTIVIDAD_ASOC_DOC_SIR_SERVICE)
	private IActivAsociarDocsSIRService activAsociarDocsSIRService;
	// services
	private DocumentoDTO documento;
	
	private ActividadSirAprobDocTad data;

	@Autowired
	private Window tramitacionWindow;

	private AnnotateDataBinder verActividadBinder;

	private Listbox actividadesListbox;

	protected void modoLecturaComposer(Component c) throws Exception {
		initComposer(c);
		renderHboxDate();
	}

	protected void modoEdicionComposer(Component c) throws Exception {
		initComposer(c);
		renderHboxButtons();

	}

	private void initComposer(Component c) throws Exception {  
	 
		
		solicitud = activGuardaTempService.buscarActividadSolicitudGuardaTemp(getPopupActividad());
		
		ActividadSirAprobDocTad actividadSIR = activAsociarDocsSIRService.buscarActividad(getPopupActividad().getId());
		
		
		actividadesListbox.setItemRenderer(new ActivDocumentoSirItemRender());
		
		actividadesListbox.setModel(new BindingListModelList(actividadSIR.getNrosDocumento(), true));
		
		data = actividadSIR;
		
		loggedUsername = (String) Executions.getCurrent().getDesktop().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME);

		// Titulo de la ventana con tipo de actividad
		((Window) this.self).setTitle(getPopupActividad().getTipoActividadDecrip());

		// nro expediente
		this.expLabel.setValue(getPopupActividad().getNroExpediente());
		
		// motivo
		this.motivoTextBox.setValue(solicitud.getMotivo());

		if (solicitud.getMailDestino() != null
				&& solicitud.getMailDestino().length() != 0) {
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
	}

	private void renderHboxButtons() {
		// tiene fecha de baja no dibujo los botones - inconsistencia
		if (getPopupActividad().getFechaBaja() == null) {
			// accion - Aprobar
			Toolbarbutton button = new Toolbarbutton(
					Labels.getLabel("ee.act.label.aprobar"),
					"/imagenes/control_add_blue.png");
			button.setParent(hboxExterior);
			button.addEventListener(Events.ON_CLICK,new AprobacionEventListener(hboxExterior,data));

			// accion - Rechazar
			Toolbarbutton button2 = new Toolbarbutton(
					Labels.getLabel("ee.act.label.rechazar"),
					"/imagenes/decline.png");
			button2.setParent(hboxExterior);
			button2.addEventListener(Events.ON_CLICK, new RechazoEventListener(hboxExterior,data));
		}
	}

	private void renderHboxDate() {
		// sino tiene fecha de baja no dibujo ni la fecha ni los botones
		if (getPopupActividad().getFechaBaja() != null) {
			// fecha
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaBaja = getPopupActividad().getFechaBaja();
			Label labelFechaAprob = new Label(getPopupActividad().getEstado()
					+ " ( " + sdf.format(fechaBaja) + " )");
			labelFechaAprob.setParent(hboxExterior);
		}
	}

	public void onClick$cerrar() {
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}

	// onclick visualizar
	public void onVerExpediente() throws SuspendNotAllowedException,
			InterruptedException {
		// nro expediente
		Executions
				.getCurrent()
				.getDesktop()
				.setAttribute("codigoExpedienteElectronico",
						getPopupActividad().getNroExpediente());
		Window popupDocumentoWindow = (Window) Executions.createComponents(
				"/expediente/detalleExpedienteElectronico.zul", null, null);
		popupDocumentoWindow.setClosable(true);
		popupDocumentoWindow.doModal();

	}
	
	public void onVerDocumento() throws SuspendNotAllowedException,InterruptedException {
	// nro expediente
	Executions
			.getCurrent()
			.getDesktop()
			.setAttribute("codigoExpedienteElectronico",
					getPopupActividad().getNroExpediente());
	Window popupDocumentoWindow = (Window) Executions.createComponents(
			"/expediente/detalleExpedienteElectronico.zul", null, null);
	popupDocumentoWindow.setClosable(true);
	popupDocumentoWindow.doModal();

	}

	// onclick cancelar actividad
	public void onCancelarActividad() throws SuspendNotAllowedException,
			InterruptedException {
		actividadExpedienteService.eliminarActividad(getPopupActividad().getId(),getPopupActividad().getNroExpediente(),ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_PAGO_SIR);
		Messagebox.show(Labels.getLabel("ee.act.msg.body.gedo.recha"),
				Labels.getLabel("ee.general.information"), Messagebox.OK,
				Messagebox.INFORMATION);
		Events.sendEvent(new Event(Events.ON_USER, (Component) tramitacionWindow, "elimActividad"));
		onClick$cerrar();
	}
	
	public void setCancelar_act(Toolbarbutton cancelar_act) {
		this.cancelar_act = cancelar_act;
	}

	public Toolbarbutton getCancelar_act() {
		return cancelar_act;
	}


	public Window getPeticionExternaWindow() {
		return peticionExternaWindow;
	}

	public void setPeticionExternaWindow(Window peticionExternaWindow) {
		this.peticionExternaWindow = peticionExternaWindow;
	}

	
	public void setData(ActividadSirAprobDocTad data) {
		this.data = data;
	}

	public ActividadSirAprobDocTad getData() {
		return data;
	}

	private class AprobacionEventListener implements EventListener {

		ActividadSirAprobDocTad data;
		Component comp;

		public AprobacionEventListener(Component comp, ActividadSirAprobDocTad actAprob) {
			this.data = actAprob;
			this.comp = comp;
		}

		@Override
		public void onEvent(Event event) throws Exception {

			String codigoExp = solicitud.getNroExpediente();

			 

			String loggedUser = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
					.toString();

			String estado;
			String msgFin;
			try {
				activAsociarDocsSIRService.aprobarActividad(data, codigoExp, loggedUser);
				estado = Labels.getLabel("ee.act.aprob.vinculado");
				msgFin = Labels.getLabel("ee.act.aprob.msg.aprob");
			} catch (ProcesoFallidoException e) {
				estado = Labels.getLabel("ee.act.aprob.rechazado");
				msgFin = Labels.getLabel("ee.act.aprob.msg.recha.excep");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			Label labelFechaAprob = new Label(estado + " ( " + sdf.format(date) + " )");
			comp.getChildren().clear();
			labelFechaAprob.setParent(comp);

			Messagebox.show(msgFin, Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
			
			Events.sendEvent(new Event(Events.ON_USER, (Component) tramitacionWindow, "elimActividad"));
			
			closeAssociatedWindow(); 
		}
	}

	private class RechazoEventListener implements EventListener {

		ActividadSirAprobDocTad data;
		Component comp;

		public RechazoEventListener(Component comp, ActividadSirAprobDocTad actAprob) {
			this.data = actAprob;
			this.comp = comp;
		}

		@Override
		public void onEvent(Event event) throws Exception {

			  
			String loggedUser = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

			activAsociarDocsSIRService.rechazarActividad(this.data, loggedUser);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			Label labelFechaAprob = new Label(Labels.getLabel("ee.act.aprob.rechazado") + " ( " + sdf.format(date)
					+ " )");
			comp.getChildren().clear();
			labelFechaAprob.setParent(comp);

			Messagebox.show(Labels.getLabel("ee.act.aprob.msg.recha"), Labels.getLabel("ee.general.information"),
					Messagebox.OK, Messagebox.INFORMATION);
			
			closeAssociatedWindow(); 
		}
	}
	 
}
