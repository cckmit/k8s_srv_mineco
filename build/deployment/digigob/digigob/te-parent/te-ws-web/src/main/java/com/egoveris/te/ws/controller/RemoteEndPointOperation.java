package com.egoveris.te.ws.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zkoss.zk.ui.Component;

import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.te.base.model.CopyOpResponseAppDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.OperationAppDTO;
import com.egoveris.te.base.model.OperationResponseAppDTO;
import com.egoveris.te.base.model.OperationUserAppDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.SubProcesoDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.OperacionService;
import com.egoveris.te.base.service.SubprocessService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.IGenerarExpedienteService;
import com.egoveris.te.base.util.BloqueoOperacion;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.base.util.StateUtil;
import com.egoveris.te.model.model.CaratulacionExpedienteRequest;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;
import com.egoveris.te.model.model.Status;
import com.egoveris.te.ws.util.OperationHelper;

@RestController
@RequestMapping("/api/operation")
public class RemoteEndPointOperation {

	private Log logger = LogFactory.getLog(RemoteEndPointOperation.class);
	private static final String NULL_VALUE = "null";

	@Autowired
	private SectorInternoServ sectorIntService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private OperacionService operacionService;
	
	@Autowired
	private TrataService trataService;


	@Autowired
	private SubprocessService subprocessService;

	@Autowired
	private WorkFlowService workflowService;

	@Autowired
	private IExpedienteFormularioService expedienteFormularioService;
	
	@Autowired
	private ExpedienteElectronicoService expedienteService;
	
	@Autowired
	private IGenerarExpedienteService generarService;

	@Autowired
	private IFormManagerFactory<IFormManager<Component>> formManagerFactory;

	@RequestMapping(value = "/getOperation", method = RequestMethod.POST)
	public OperationResponseAppDTO getOperation(@RequestParam("user") String user,
			@RequestParam("organism") String organism, @RequestParam("idSector") String idSector,
			@RequestParam("operationCode") String operationCode,
			@RequestParam("operationStatus") String operationStatus) {

		// Status
		Status status = null;

		// Out
		OperationResponseAppDTO out = new OperationResponseAppDTO();
		List<OperationAppDTO> operacionOut = new ArrayList<>();

		List<Usuario> usuario = null;
		List<OperacionDTO> operaciones = null;

		if (user != null) {
			try {
				usuario = usuarioService.obtenerUsuariosPorNombre(user.toUpperCase());
			} catch (Exception e) {
				logger.debug("Username not found: ", e);
				status = new Status(Status.ERROR);
			}
		}

		// Valid user
		if (usuario != null && !usuario.isEmpty()) {
			Integer sector = OperationHelper.searchSectorListByCodigo(sectorIntService.buscarTodosSectores(),
					usuario.get(0).getCodigoSectorInterno());
			// Get operation according to filters
			try {
				operaciones = getOperationsByFilter(operationCode, operationStatus, sector);
			} catch (Exception e) {
				logger.debug("Error getting operations: ", e);
				status = new Status(Status.ERROR);
			}
		}

		if (operaciones != null && !operaciones.isEmpty()) {
			for (OperacionDTO operacion : operaciones) {
				// operation
				OperationAppDTO operacionUser = OperationHelper.mapToOperationAppDTO(operacion);

				try {
					// get subProcesos operation
					List<SubProcesoOperacionDTO> subProcessOp = operacionService
							.getSubProcesos(operacion.getId().longValue());
					operacionUser.setSubprocess(OperationHelper.mapToSubprocessAppDTO(subProcessOp));
				} catch (Exception e) {
					logger.debug("Error getting subprocess for operation " + operacion.getNumOficial() + ": ", e);
				}

				try {
					// get subProcesos to start
					List<SubProcesoDTO> availableSubprocess = operacionService.getWorkFlowSubProcess(
							operacion.getTipoOperacionOb().getWorkflow(), operacion.getJbpmExecutionId(), "Manual",
							operacion.getVersionProcedure());
					operacionUser.setAvailableSubprocess(OperationHelper.mapToSubprocessDTO(availableSubprocess));
				} catch (Exception e) {
					logger.debug("Error getting available subprocess for operation " + operacion.getNumOficial() + ": ",
							e);
				}

				// add to list
				operacionOut.add(operacionUser);
			}

			if (!operacionOut.isEmpty()) {
				status = new Status(Status.OK);
				out.setOperacion(operacionOut);
			}
		}

		if (status == null) {
			status = new Status(Status.NOK);
		}

		out.setStatus(status);

		return out;
	}

	@RequestMapping(value = "/doOperation", method = RequestMethod.POST)
	public Status doOperation(@RequestBody OperationUserAppDTO operationUser) {
		Status status = null;
		OperacionDTO operacionDTO = null;

		if (operationUser != null && operationUser.getOperation() != null) {
			operacionDTO = operacionService.getOperacionByNumOfic(operationUser.getOperation().getCode());
		}

		if (operacionDTO != null && operacionDTO.getEstadoOperacion() != null
				&& !operacionDTO.getEstadoOperacion().equalsIgnoreCase(ConstantesCommon.ESTADO_CIERRE)) {
			List<String> transitions = operacionService.getTransicionesOperacion(operacionDTO.getJbpmExecutionId());

			if (transitions != null && !transitions.isEmpty()) {
				// Since it's a POC, this is a reduced version
				// without automatic subprocess start/scripts
				try {
					changeStateOperation(operacionDTO, transitions);
					status = new Status(Status.OK);
				} catch (Exception e) {
					logger.debug("Error changing operation to nextState: ", e);
					status = new Status(Status.ERROR);
				}
			}
		}

		if (status == null) {
			status = new Status(Status.NOK);
		}

		return status;
	}

	@RequestMapping(value = "/startSubprocess", method = RequestMethod.POST)
	public Status startSubprocess(@RequestBody OperationUserAppDTO operationUser) {
		Status status = null;

		if (operationUser != null && operationUser.getOperation() != null
				&& operationUser.getSubprocessToStart() != null && operationUser.getUser() != null) {
			try {
				OperacionDTO operacionDTO = operacionService
						.getOperacionByNumOfic(operationUser.getOperation().getCode());
				SubProcesoDTO subProcesoDTO = null;

				if (operationUser.getSubprocessToStart().getSubprocessCode() != null
						&& StringUtils.isNumeric(operationUser.getSubprocessToStart().getSubprocessCode())) {
					subProcesoDTO = subprocessService.getSubprocessFromId(
							Long.valueOf(operationUser.getSubprocessToStart().getSubprocessCode()));
				}

				operacionService.iniciarSubProceso(subProcesoDTO, operacionDTO, operationUser.getUser().getIdUser(),
						null);
				status = new Status(Status.OK);
			} catch (Exception e) {
				logger.debug("Error starting subprocess: ", e);
				status = new Status(Status.ERROR);
			}
		}

		if (status == null) {
			status = new Status(Status.NOK);
		}

		return status;
	}

	@RequestMapping(value = "/copyOperation", method = RequestMethod.POST)
	public CopyOpResponseAppDTO copyOperation(@RequestBody OperationUserAppDTO operationUser) {
		CopyOpResponseAppDTO response = new CopyOpResponseAppDTO();
		Status status = null;

		OperacionDTO operationToCopy = null;
		OperacionDTO newOperation = null;

		if (operationUser != null && operationUser.getOperation() != null && operationUser.getUser() != null) {
			try {
				operationToCopy = operacionService.getOperacionByNumOfic(operationUser.getOperation().getCode());
			} catch (Exception e) {
				logger.debug("Error getting operation to copy: ", e);
				status = new Status(Status.ERROR);
			}
		}

		if (operationToCopy != null) {
			// Minimal data to copy operation, before confirming it
			newOperation = new OperacionDTO();
			newOperation.setFechaInicio(new Date());
			newOperation.setTipoOperacionOb(operationToCopy.getTipoOperacionOb());
			newOperation.setIdSectorInterno(operationToCopy.getIdSectorInterno());
			newOperation.setVersionProcedure(operationToCopy.getVersionProcedure());

			try {
				newOperation = operacionService.saveOrUpdate(newOperation);
			} catch (Exception e) {
				logger.debug("Error copying minimal data for operation: ", e);
				status = new Status(Status.ERROR);
				newOperation = null;
			}
		}

		if (newOperation != null) {
			try {
				// Copy of forms
				ExpedienteFormularioDTO searchExpedienteForm = new ExpedienteFormularioDTO();
				searchExpedienteForm.setIdEeExpedient(operationToCopy.getId());
				List<ExpedienteFormularioDTO> listExpedienteFormulario = expedienteFormularioService
						.buscarFormulariosPorExpediente(searchExpedienteForm);

				for (ExpedienteFormularioDTO expedienteFormularioDTO : listExpedienteFormulario) {
					// Get form
					IFormManager<Component> formManager = formManagerFactory
							.create(expedienteFormularioDTO.getFormName());
					formManager.fillCompValues(operationToCopy.getId().intValue(),
							expedienteFormularioDTO.getIdDfTransaction());

					// Save copy
					Integer uuid = formManager.saveValues(newOperation.getId().intValue());
					ExpedienteFormularioDTO newExpedienteFormulario = new ExpedienteFormularioDTO();
					newExpedienteFormulario.setIdDfTransaction(uuid);
					newExpedienteFormulario.setIdEeExpedient(newOperation.getId());
					newExpedienteFormulario.setIdDefForm(uuid);
					newExpedienteFormulario.setDateCration(new Date());
					newExpedienteFormulario.setFormName(expedienteFormularioDTO.getFormName());
					newExpedienteFormulario.setIsDefinitive(0);
					newExpedienteFormulario.setUserCreation(operationUser.getUser().getIdUser());
					newExpedienteFormulario.setOrganism("");

					expedienteFormularioService.guardarFormulario(newExpedienteFormulario);
				}

				// Copy of documents?

				// Confirm operation
				newOperation = operacionService.confirmarOperacion(newOperation);
				response.setOperationCode(newOperation.getNumOficial());
				status = new Status(Status.OK);
			} catch (Exception e) {
				logger.debug("Error copying operation: ", e);
				status = new Status(Status.ERROR);
			}
		}

		if (status == null) {
			status = new Status(Status.NOK);
		}

		response.setStatus(status);
		return response;
	}

	/*
	 * Este parece ser el metodo antiguo de "startProductReqSubprocess"
	 */
	@RequestMapping(value = "/validateAndInitSubProcess", method = RequestMethod.POST)
	public Status validateAndInitSubprocess(@RequestParam("idOperation") Long idOperation,
			@RequestParam("nameSubprocess") String nameSubProcess, @RequestParam("user") String user) {
		Status status = new Status();
		try {
			// TODO recuperar canasta y obtener los productos y crear una
			// solicitud de
			// visto bueno por producto
			for (int i = 0; i < 3; i++) {
				operacionService.iniciarSubProceso(null, idOperation, user, nameSubProcess);
			}

		} catch (Exception e) {
			logger.error("Error  validate and init subprocess", e);
		}
		return status;
	}

	@RequestMapping(value = "/initProductReqSubprocess", method = RequestMethod.POST)
	public Status startProductReqSubprocess(@RequestParam("idOperation") Long idOperation,
			@RequestParam("state") String state, @RequestParam("user") String user,
			@RequestParam("motivo") String motivo) {
		Status status = null;

		OperacionDTO operacionDTO = operacionService.getOperacionById(idOperation);

		if (operacionDTO != null
				&& !operacionDTO.getEstadoOperacion().equalsIgnoreCase(ConstantesCommon.ESTADO_CIERRE)) {
			try {
				ProcessInstance processInstance = workflowService.getProcessEngine().getExecutionService()
						.findProcessInstanceById(operacionDTO.getJbpmExecutionId());

				ProcessDefinitionQuery query = workflowService.getProcessEngine().getRepositoryService()
						.createProcessDefinitionQuery();
				query.processDefinitionId(processInstance.getProcessDefinitionId());
				ProcessDefinition processDefinition = query.uniqueResult();

				SubProcesoDTO solProductOpSubprocess = subprocessService
						.getSolProductSubprocessForState(processDefinition.getKey(), state, user);

				operacionService.iniciarSubProceso(solProductOpSubprocess, operacionDTO, user, motivo);
				status = new Status(Status.OK);
			} catch (Exception e) {
				logger.debug("Error in RemoteEndPointOperation.startProductReqSubprocess(): ", e);
				status = new Status(Status.ERROR);
			}
		}

		if (status == null) {
			status = new Status(Status.NOK);
		}

		return status;
	}

	@RequestMapping(value = "/scriptStartSubprocess", method = RequestMethod.POST)
	public Status scriptStartSubprocess(@RequestParam("idOperation") Long idOperation,
			@RequestParam("subprocess") String subprocess, @RequestParam("user") String user,
			@RequestParam("motivo") String motivo) {
		Status status = null;

		OperacionDTO operacionDTO = operacionService.getOperacionById(idOperation);

		if (operacionDTO != null
				&& !operacionDTO.getEstadoOperacion().equalsIgnoreCase(ConstantesCommon.ESTADO_CIERRE)) {
			try {
				List<SubProcesoDTO> subprocessOp = operacionService.getWorkFlowSubProcess(
						operacionDTO.getTipoOperacionOb().getWorkflow(), operacionDTO.getJbpmExecutionId(), "Manual",
						operacionDTO.getVersionProcedure());

				SubProcesoDTO subprocessToStart = null;

				for (SubProcesoDTO subProcesoDTO : subprocessOp) {
					if (subProcesoDTO.getTramite().getCodigoTrata().equalsIgnoreCase(subprocess)) {
						subprocessToStart = subProcesoDTO;
						break;
					}
				}

				if (subprocessToStart != null) {
					operacionService.iniciarSubProceso(subprocessToStart, operacionDTO, user, motivo);
				}

				status = new Status(Status.OK);
			} catch (Exception e) {
				logger.debug("Error in RemoteEndPointOperation.startProductReqSubprocess(): ", e);
				status = new Status(Status.ERROR);
			}
		}

		if (status == null) {
			status = new Status(Status.NOK);
		}

		return status;
	}

	@RequestMapping(value = "/valSubprocessFinished", method = RequestMethod.GET)
	public Status validateSubprocessFinished(@RequestParam("idOperation") Long idOperation,
			@RequestParam("state") String state) {
		Status status = null;
		List<SubProcesoOperacionDTO> subprocessOperation = null;

		try {
			boolean finished = true;
			subprocessOperation = operacionService.getSubProcesos(idOperation);

			for (SubProcesoOperacionDTO subProcesoOperacionDTO : subprocessOperation) {
				if (subProcesoOperacionDTO.getOperacion().getEstadoOperacion() != null
						&& subProcesoOperacionDTO.getOperacion().getEstadoOperacion().equalsIgnoreCase(state)
						&& !subProcesoOperacionDTO.getExpediente().getEstado()
								.equalsIgnoreCase(ConstantesCommon.ESTADO_GUARDA_TEMPORAL)) {
					finished = false;
					break;
				}
			}

			if (finished) {
				status = new Status(Status.OK);
			} else {
				status = new Status(Status.NOK);
			}
		} catch (Exception e) {
			logger.debug("Error obtaining subprocess of operation: ", e);
			status = new Status(Status.ERROR);
		}

		return status;
	}
	
	

	// ======================================
	// PRIVATE FUNCTIONS
	// ======================================

	/**
	 * @param operationCode
	 * @param operationStatus
	 * @param sector
	 * @return
	 */
	private List<OperacionDTO> getOperationsByFilter(String operationCode, String operationStatus, Integer sector) {
		List<OperacionDTO> operaciones;

		String opCode = operationCode;
		String opStatus = operationStatus;

		if (opCode != null && opCode.equalsIgnoreCase(NULL_VALUE)) {
			opCode = null;
		}

		if (opStatus != null && opStatus.equalsIgnoreCase(NULL_VALUE)) {
			opStatus = null;
		}

		// validation
		if ((null != operationCode && !operationCode.equals(NULL_VALUE))
				|| (null != operationStatus && !operationStatus.equals(NULL_VALUE))) {
			// get operation by sector
			operaciones = operacionService.getOperacionBySectoAndNumOficAndEstado(sector, opCode, opStatus);
		} else {
			operaciones = operacionService.getOperacionesBySector(sector);
		}

		// Added for limit operations [output of 20]
		if (operaciones != null && !operaciones.isEmpty()) {
			List<OperacionDTO> operationsLimit = new ArrayList<>();

			for (int i = 0; i < operaciones.size() && operationsLimit.size() < 19; i++) {
				boolean add = false;
				// Estado shouldn't be "Cierre", unless is specified or blank ""
				if (operaciones.get(i).getEstadoOperacion() != null
						&& !StringUtils.isBlank(operaciones.get(i).getEstadoOperacion())
						&& (opStatus != null || !operaciones.get(i).getEstadoOperacion()
								.equalsIgnoreCase(ConstantesCommon.ESTADO_CIERRE))) {
					add = true;
				}

				if (!add && opCode != null) {
					add = true;
				}

				if (add) {
					operationsLimit.add(operaciones.get(i));
				}
			}

			operaciones.clear();
			operaciones.addAll(operationsLimit);
		}

		return operaciones;
	}

	private void changeStateOperation(OperacionDTO operacionDTO, List<String> transitions) {
		Map<String, Object> mapVars = workflowService.getVariables(operacionDTO.getJbpmExecutionId());

		if (mapVars == null) {
			mapVars = new HashMap<>();
		}

		String actualState = operacionDTO.getEstadoOperacion();
		String previousState = mapVars.containsKey(ConstantesCommon.ESTADO_ANTERIOR)
				? mapVars.get(ConstantesCommon.ESTADO_ANTERIOR).toString() : null;

		String nextState = StateUtil.calculateNextStateOperation(transitions, actualState, previousState);

		String executionId = workflowService.nextState(operacionDTO.getJbpmExecutionId(), nextState);
		operacionDTO.setEstadoOperacion(nextState);
		operacionDTO.setJbpmExecutionId(executionId);
		operacionDTO.setEstadoBloq(BloqueoOperacion.NINGUNO.getValue());
		operacionDTO.setFechaFin(nextState.equalsIgnoreCase(ConstantesCommon.ESTADO_CIERRE) ? new Date() : null);
		operacionService.saveOrUpdate(operacionDTO);

		// Set estadoAnterior variable
		mapVars.put(ConstantesCommon.ESTADO_ANTERIOR, actualState);
		workflowService.setVariables(workflowService.getProcessEngine(), executionId, mapVars);
	}

}
