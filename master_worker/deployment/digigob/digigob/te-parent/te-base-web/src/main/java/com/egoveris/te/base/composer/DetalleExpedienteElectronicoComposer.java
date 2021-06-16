package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;


/**
 * @author jnorvert
 * 
 */
public class DetalleExpedienteElectronicoComposer extends EEGenericForwardComposer {

  private static final long serialVersionUID = -1364238888954151118L;
	private static final Logger logger = LoggerFactory.getLogger(DetalleExpedienteElectronicoComposer.class);
	private static final String ES_RESERVADO = " - Expediente Reservado";
	private static final String ATTR_SELECTED_TASK = "selectedTask";
	private static final String ATTR_CODIGO_EE = "codigoExpedienteElectronico";

	private Label titulo;
	private Window detalleExpedienteElectronicoWindow;
	private String codigoExpedienteElectronico;
	private List<DocumentoDTO> listaDocumento = new ArrayList<>();
	private DocumentoDTO selectedDocumento = new DocumentoDTO();
	protected Task workingTask = null;
	private ExpedienteElectronicoDTO expedienteElectronico;
	private Component acordeon;

	private Tab datosDeLaCaratula;
	private Tab tramitacionConjunta;
	private Tab fusion;

	private String loggedUsername;
	private IExternalConsultaDocumentoService consultaDocumentoService;

	/**
	 * Una vez que el componente se creo, cargo datos, habilito/deshabilito
	 * campos
	 */
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);

		Executions.getCurrent().getDesktop().setAttribute("soloLectura", true);
		ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
				.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);

		this.consultaDocumentoService = (IExternalConsultaDocumentoService) SpringUtil
				.getBean("consultaDocumentoService");

		try {

			this.codigoExpedienteElectronico = (String) Executions.getCurrent().getDesktop()
					.getAttribute(ATTR_CODIGO_EE);

			if (this.codigoExpedienteElectronico == null) {
				// se obtiene de la url
				this.codigoExpedienteElectronico = (String) Executions.getCurrent().getParameter(
						ATTR_CODIGO_EE);
				Executions.getCurrent().getDesktop()
						.setAttribute(ATTR_CODIGO_EE, this.codigoExpedienteElectronico);
				detalleExpedienteElectronicoWindow.setClosable(false);
			}

		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new WrongValueException("Error al obtener el Expediente Electrónico seleccionado.");
		}


		this.expedienteElectronico = expedienteElectronicoService
				.obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);
		Executions.getCurrent().getDesktop().setAttribute(ATTR_SELECTED_TASK,null);
		workingTask = expedienteElectronicoService.obtenerWorkingTask(expedienteElectronico);
		if(workingTask != null){
			Executions.getCurrent().getDesktop().setAttribute(ATTR_SELECTED_TASK,workingTask);
		}
		
		// lo requiere actividades
		Executions.getCurrent().getDesktop().setAttribute("expedienteElectronico", this.expedienteElectronico);
		// Lo necesita el historial
		Executions.getCurrent().getDesktop()
				.setAttribute("idExpedienteElectronico", this.expedienteElectronico.getId());

		String estado = this.expedienteElectronico.getEstado();

		if (this.expedienteElectronico.getEsReservado()) {
			loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

			estado = estado + ES_RESERVADO;

			this.tramitacionConjunta.setDisabled(true);
			this.fusion.setDisabled(true);
			//Aaca hay q formatear los documentos!
			if (!puedeVerSolapaCaratula())
				this.datosDeLaCaratula.setDisabled(true);
		}
		
		if (detalleExpedienteElectronicoWindow.isClosable()) {
			if(estado.equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)){
				this.detalleExpedienteElectronicoWindow.setTitle(ConstantesWeb.ESTADO_GUARDA_TEMPORAL);				
			}else
				this.detalleExpedienteElectronicoWindow.setTitle(estado);
		}

		this.titulo.setValue("Expediente: " + this.expedienteElectronico.getCodigoCaratula());
		this.intercambiarVariablesAcordeon();
		this.enviarEventoAcordeon(Events.ON_NOTIFY, null, null);
		this.enviarEventoAcordeon(Events.ON_RENDER, this.expedienteElectronico, true);
		
		detalleExpedienteElectronicoWindow.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (Executions.getCurrent().getSession().getAttribute("goBackDb") != null) {
					String hostEDT = getdBProperty().getString("host.edt") != null
							? getdBProperty().getString("host.edt") : null;
					
					if (hostEDT != null) {
						Executions.getCurrent().getSession().removeAttribute("goBackDb");
						Executions.sendRedirect(hostEDT);
					}
				}
			}
		});
	}

	/***
	 * 
	 * Condiciones para ver la solapa caratula en la consulta: 1. Usuarios con
	 * permiso GEDO Confidencial y que participo en la tramitación 2. Usuarios
	 * con permiso GEDO Confidencial y que pertenece a la repartición rectora 3.
	 * Usuario que caratuló el EE
	 * 
	 * @return Boolean
	 */
	private Boolean puedeVerSolapaCaratula() throws InterruptedException {

		try {
			RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
			request.setNumeroDocumento(this.expedienteElectronico.getDocumentos().get(0).getNumeroSade());
			request.setUsuarioConsulta(loggedUsername);

			// Si puede ver el documento caratula, puede ver la solapa caratula
			this.consultaDocumentoService.consultarDocumentoPorNumero(request);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void intercambiarVariablesAcordeon() {
		if (this.workingTask != null) {
			Executions.getCurrent().getDesktop().setAttribute(ATTR_SELECTED_TASK, this.workingTask);
		}
		Executions.getCurrent().getDesktop().setAttribute("ventanaPadre", this.self);
		this.acordeon = (Component) Executions.getCurrent().getDesktop().getAttribute("acordeonWindow");
	}

	private void enviarEventoAcordeon(String evento, ExpedienteElectronicoDTO expedienteElectronico2, Boolean inicial) {
		Map<String, Object> dataHm = new HashMap<>();
		dataHm.put("expedienteElectronico", expedienteElectronico2);
		dataHm.put("inicial", inicial);
		if(evento.equals(Events.ON_RENDER) && expedienteElectronico2.getEsReservado()){
			dataHm.put("reservado", true);
		}
		Events.sendEvent(evento, this.acordeon, dataHm);
	}

	/*
	 * GETTERS & SETTERS
	 */

	public List<DocumentoDTO> getListaDocumento() {
		return listaDocumento;
	}

	public void setListaDocumento(List<DocumentoDTO> listaDocumento) {
		this.listaDocumento = listaDocumento;
	}

	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	public DocumentoDTO getSelectedDocumento() {
		return selectedDocumento;
	}

	public void setSelectedDocumento(DocumentoDTO selectedDocumento) {
		this.selectedDocumento = selectedDocumento;
	}

	public ExpedienteElectronicoDTO getExpedienteElectronico() {
		return expedienteElectronico;
	}

	public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
		this.expedienteElectronico = expedienteElectronico;
	}

	public String getCodigoExpedienteElectronico() {
		return codigoExpedienteElectronico;
	}

	public void setCodigoExpedienteElectronico(String codigoExpedienteElectronico) {
		this.codigoExpedienteElectronico = codigoExpedienteElectronico;
	}

	public Label getTitulo() {
		return titulo;
	}

	public void setTitulo(Label titulo) {
		this.titulo = titulo;
	}
	
	public void onClose$detalleExpedienteElectronicoWindow(){
		Executions.getCurrent().getSession().setAttribute("habilitarCancelacion",null);
		Executions.getCurrent().getDesktop().setAttribute("soloLectura", null);
		Executions.getCurrent().getDesktop().setAttribute(ATTR_SELECTED_TASK, null);
	}

	public static Window openInModal(Component parent, String codigoExp) throws SuspendNotAllowedException, InterruptedException {
		
		Executions.getCurrent().getDesktop().setAttribute(ATTR_CODIGO_EE, codigoExp);
		Window detExp = (Window) Executions.createComponents("/expediente/detalleExpedienteElectronico.zul", parent,
				new HashMap<String, Object>());
		detExp.setPosition("center");
		detExp.setWidth("85%");
		detExp.doModal();
		return detExp;
	}
}
