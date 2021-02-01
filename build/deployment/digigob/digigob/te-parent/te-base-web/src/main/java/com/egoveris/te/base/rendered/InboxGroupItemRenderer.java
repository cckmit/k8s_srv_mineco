package com.egoveris.te.base.rendered;

import com.egoveris.te.base.composer.InboxGrupoComposer;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.SolicitudExpedienteService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.util.ConstantesServicios;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

public class InboxGroupItemRenderer implements ListitemRenderer<Object> {
	private static final String ANULAR_MODIFICAR = "Anular/Modificar Solicitud";
	private static final String INICIAR_EXPEDIENTE = "Iniciar Expediente";

	@Autowired
	ProcessEngine processEngine;
	@Autowired
	private ExecutionService executionService;
	@Autowired
	private SolicitudExpedienteService solicitudExpedienteService;
	@Autowired
	private TrataService trataService;

	public void render(Listitem item, Object data, int arg1) throws Exception {
		processEngine = (ProcessEngine) SpringUtil.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE);
		ExecutionService executionService = processEngine.getExecutionService();
		solicitudExpedienteService = (SolicitudExpedienteService) SpringUtil.getBean(ConstantesServicios.SOLICITUD_EXPEDIENTE_SERVICE);
		trataService = (TrataService) SpringUtil.getBean(ConstantesServicios.TRATA_SERVICE);
		
		Tarea task = (Tarea) data;

		SolicitudExpedienteDTO solicitud;

		if (task.getIdSolicitud() != null) {
			solicitud = solicitudExpedienteService.obtenerSolitudByIdSolicitud(task.getIdSolicitud());
		} else {
			// En caso de que el idSolicitud (sera el caso de una solicitud
			// anterior a
			// esta actualizacion) se obtendrá la
			// solicitud seteada en el workflow y se setea el id para que se
			// vayan
			// actualizando todas las solicitudes que hayan quedado viejas
			solicitud = (SolicitudExpedienteDTO) executionService.getVariable(task.getTask().getExecutionId(),
					"solicitud");

		}

		Window winGrupal = (Window) item.getParent().getParent();
		InboxGrupoComposer inboxGrupalWindow = (InboxGrupoComposer) winGrupal
				.getAttribute("inboxGrupalWindow$composer");

		if (solicitud != null) {
			// se dibuja la fila
			dibujarFila(solicitud, item, task);
			inboxGrupalWindow.refreshPaginator("SUMA");
		} else {
			item.setVisible(false);
			inboxGrupalWindow.refreshPaginator("RESTA");
		}
	}

	private void dibujarFila(SolicitudExpedienteDTO solicitud, Listitem item, Tarea task) {
		Listcell currentCell;
		new Listcell("").setParent(item);

		// Nombre Tarea
		new Listcell(task.getNombreTarea()).setParent(item);

		new Listcell(task.getFechaCreacion()).setParent(item);

		// Codigo Expediente
		String codigoExpediente = task.getCodigoExpediente();
		if (codigoExpediente != null) {
			new Listcell(codigoExpediente).setParent(item);
		} else {
			new Listcell("").setParent(item);
		}

		if (StringUtils.isEmpty(task.getCodigoTrata()) && StringUtils.isEmpty(task.getDescripcionTrata())) {

			TrataDTO trataSugerida = null;
			Long idTrataSugerida;
			if (solicitud != null && solicitud.getIdTrataSugerida() != null) {
				idTrataSugerida = solicitud.getIdTrataSugerida();
				trataSugerida = trataService.obtenerTrataByIdTrataSugerida(idTrataSugerida);
			}

			if (trataSugerida == null) {
				// Codigo Trata
				new Listcell(task.getCodigoTrata()).setParent(item);
				// Descripcion
				new Listcell(task.getDescripcionTrata()).setParent(item);
			} else {
				// Codigo Trata
				new Listcell(trataSugerida.getCodigoTrata()).setParent(item);
				// Descripcion
				new Listcell(trataService.obtenerDescripcionTrataByCodigo(trataSugerida.getCodigoTrata()))
						.setParent(item);
			}

		} else {
			// Codigo Trata
			new Listcell(task.getCodigoTrata()).setParent(item);
			// Descripcion
			new Listcell(task.getDescripcionTrata()).setParent(item);
		}
		// Motivo
		if (task.getNombreTarea().equals(ANULAR_MODIFICAR) && solicitud != null) {
			String motivo = solicitud.getMotivoDeRechazo();
			Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
			listCellMotivo.setTooltiptext(motivo);
			listCellMotivo.setParent(item);
		} else {
			if (task.getNombreTarea().equals(INICIAR_EXPEDIENTE) && solicitud != null) {
				String motivo = solicitud.getMotivo();
				if (motivo == null) {
					solicitud.setMotivo(solicitud.getMotivoExterno());
				}
				Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
				listCellMotivo.setTooltiptext(motivo);
				listCellMotivo.setParent(item);
			} else {

				String motivo = task.getMotivo();

				Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
				listCellMotivo.setTooltiptext(motivo);
				listCellMotivo.setParent(item);

			}
		}

		// Usuario Anterior
		String usuarioAnterior = task.getUsuarioAnterior();
		new Listcell(usuarioAnterior).setParent(item);

		// Accion a realizar
		currentCell = new Listcell();
		currentCell.setParent(item);
		Hbox hbox = new Hbox();
		Image runImage = new Image("/imagenes/egovReturn.png");
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
				"onClick=inboxWindow$InboxGrupoComposer.onAdquirirTarea");
		runImage.setParent(hbox);
		Label adquirir = new Label(Labels.getLabel("ee.inbox.adquirir"));
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(adquirir,
				"onClick=inboxWindow$InboxGrupoComposer.onAdquirirTarea");
		adquirir.setParent(hbox);
		hbox.setParent(currentCell);
	}

	private String motivoParseado(String motivo) {
		int cantidadCaracteres = 10;
		String substringMotivo;
		if (motivo != null) {
			if (motivo.length() > cantidadCaracteres) {
				substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
			} else {
				substringMotivo = motivo.substring(0, motivo.length());
			}
		} else {
			substringMotivo = "SIN MOTIVO";
		}
		return substringMotivo;
	}
}