package com.egoveris.te.base.rendered;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.expediente.ExpedienteSadeService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ExpedientesAsociadosItemRenderer extends GenericListitemRenderer {

	@WireVariable(ConstantesServicios.EXP_SADE_SERVICE)
	private ExpedienteSadeService expedienteSadeService;
	private ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
	.getBean("expedienteElectronicoServiceImpl");
	@Autowired
	private ExecutionService executionService;
	@Autowired
	ProcessEngine processEngine = (ProcessEngine) SpringUtil.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE);
	String loggedUsername = new String("");
	@Autowired
	private TareaParaleloService tareaParaleloService;
	private TareaParaleloDTO tareaParalelo = null;
	private Task workingTask;
	private ExpedienteElectronicoDTO ee;

	public ExpedienteElectronicoDTO getEe() {
		return ee;
	}

	public void setEe(ExpedienteElectronicoDTO ee) {
		this.ee = ee;
	}

	public void render(Listitem item, Object data, int arg1) throws Exception {

		ExpedienteAsociadoEntDTO expedienteAsociado = (ExpedienteAsociadoEntDTO) data;
		ExecutionService executionService = processEngine.getExecutionService();
		workingTask = (Task) item.getDesktop().getAttribute("selectedTask");

		Listcell currentCell;
		// TipoDocumento (EX) Expediente
		new Listcell(expedienteAsociado.getTipoDocumento()).setParent(item);
		// Anio Expediente
		new Listcell(expedienteAsociado.getAnio().toString()).setParent(item);
		// Número Expediente
		new Listcell(expedienteAsociado.getNumero().toString()).setParent(item);
		// Repartición Expediente + Repartición Usuario
		String codigoReparticionActuacionYUsuario = "";
		if (expedienteAsociado.getCodigoReparticionActuacion() != null)
			codigoReparticionActuacionYUsuario = expedienteAsociado.getCodigoReparticionActuacion().trim() + "-";
		if (expedienteAsociado.getCodigoReparticionUsuario() != null)
			codigoReparticionActuacionYUsuario = codigoReparticionActuacionYUsuario
					+ (expedienteAsociado.getCodigoReparticionUsuario().trim());
		new Listcell(codigoReparticionActuacionYUsuario).setParent(item);

		// Código de Trata
		ExpedienteElectronicoDTO ee2 = null;
		if (!expedienteAsociado.getEsElectronico()) {
			String codigoTrataSADE = expedienteSadeService.obtenerCodigoTrataSADE(expedienteAsociado);
			new Listcell(codigoTrataSADE).setParent(item);

		} else {

			// TODO refactorizar porque esta mal hecho.
			ee2 = expedienteElectronicoService.obtenerExpedienteElectronico(expedienteAsociado.getTipoDocumento(),
					expedienteAsociado.getAnio(), expedienteAsociado.getNumero(),
					expedienteAsociado.getCodigoReparticionUsuario());

			String codigoTrata = ee2.getTrata().getCodigoTrata();

			new Listcell(codigoTrata).setParent(item);

		}

		// ES Electrónico/SADE
		String tipoExpediente;
		if (expedienteAsociado.getEsElectronico()) {
			tipoExpediente = "Elect.";
		} else {
			tipoExpediente = "TRACK";
		}
		new Listcell(tipoExpediente).setParent(item);

		// Estado
		if (ee2 != null && StringUtils.isNotEmpty(ee2.getEstado())) {
			String estado = ee2.getEstado();
			new Listcell(estado).setParent(item);
		} else {
			new Listcell("").setParent(item);
		}
		// Acción Expediente
		// new Listcell();
		currentCell = new Listcell();
		currentCell.setParent(item);
		Hbox hbox = new Hbox();

		Long idExpedienteElectronico = (Long) executionService.getVariable(workingTask.getExecutionId(),
				"idExpedienteElectronico");
		ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
				.buscarExpedienteElectronico(idExpedienteElectronico);

		if (!expedienteElectronico.getListaExpedientesAsociados().contains(expedienteAsociado)) {
			// ASOCIAR
			Image runImage = new Image("/imagenes/asociarexpedientes.png");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
					"onClick=tramitacionWindow$composer.onExecuteAsociar");
			Label ejecutar = new Label(Labels.getLabel("ee.asociarexpediente.label.value.asociar"));
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,
					"onClick=tramitacionWindow$composer.onExecuteAsociar");
			runImage.setParent(hbox);
			ejecutar.setParent(hbox);
		} else {
			// --- ver expediente asociado ---
			Image visualizarImage = new Image("/imagenes/ver_expediente.png");
			Label visualizar = new Label("Visualizar");
			visualizarImage.setParent(hbox);
			visualizar.setParent(hbox);
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
					"onClick=tramitacionWindow$composer.onVerExpedienteAsociado");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
					"onClick=tramitacionWindow$composer.onVerExpedienteAsociado");

			if (!expedienteAsociado.getDefinitivo()) {
				// --- boton de desasociar expediente ---
				Image runImage = new Image("/imagenes/desasociarexpedientes.png");
				org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
						"onClick=tramitacionWindow$composer.onExecuteDesasociar");
				Label ejecutar = new Label(Labels.getLabel("ee.asociarexpediente.label.value.desasociar"));
				org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,
						"onClick=tramitacionWindow$composer.onExecuteDesasociar");
				runImage.setParent(hbox);
				ejecutar.setParent(hbox);

			}
		}

		hbox.setParent(currentCell);

		// INICIO A COLOREAR FILA MODIFICADA PENDIENTE DE DEFINIR

		if (this.workingTask == null) {
			workingTask = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
		}
		 

		if (workingTask.getActivityName().equals(ConstantesWeb.ESTADO_PARALELO)) {
			// Obtengo la tarea en paralelo
			tareaParalelo = this.tareaParaleloService.buscarTareaEnParaleloByIdTask(workingTask.getExecutionId());
			if (tareaParalelo != null) {
				loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
						.toString();
				if (!loggedUsername.equals("")) {
					if (tareaParalelo.getUsuarioOrigen().equals(loggedUsername)) {// ¿soy
																					// el
																					// usuario
																					// propietario?
						if (!expedienteAsociado.getDefinitivo()) {
							if (!expedienteAsociado.getUsuarioAsociador().equals(loggedUsername)) {
								if ((expedienteAsociado.getFechaAsociacion().getTime()) >= (workingTask.getCreateTime()
										.getTime())) {
									item.setStyle("background-color:" + ConstantesWeb.COLOR_ILUMINACION_FILA);
								}
							}
						}
					}
				} else {
					throw new TeException("No se ha podido recuperar el usuario loggeado.", null);
				}
			} else {
				throw new TeException("No se ha podido recuperar la tarea.", null);
			}
		}
		// FIN A COLOREAR FILA MODIFICADA PENDIENTE DE DEFINIR
	}
}
