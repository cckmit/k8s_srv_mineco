/**
 * 
 */
package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.dao.TaskGedoDAO;
import com.egoveris.deo.base.service.ITaskViewService;
import com.egoveris.deo.model.model.TaskViewDTO;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pfolgar
 *
 */
@Service
@Transactional
public class TaskViewImpl implements ITaskViewService {

	public static final String NOMBRE_TAREA = "nombreTarea";
	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private TaskGedoDAO taskGedoDAO;

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskViewImpl.class);

	public List<TaskViewDTO> getTaskViewList(List<Task> jbpmTaskList) {

		List<TaskViewDTO> listTaskView = new ArrayList<>();
		TaskViewDTO taskView;

		for (Task task : jbpmTaskList) {
			taskView = new TaskViewDTO();

			// Tarea JBPM
			taskView.setTarea(task);

			// Nombre Tarea
			taskView.setNombreTarea(task.getActivityName());

			// Fecha Mod Tarea
			taskView.setFechaUltModifi(task.getCreateTime());

			// Tarea ENVIADA POR
			String originator = (String) this.processEngine.getExecutionService().getVariable(task.getExecutionId(),
					"usuarioCreador");
			String nyAOriginator = "Desc.";
			Usuario usuarioOriginator = null;
			if (StringUtils.isNotEmpty(originator)) {
				try {
					usuarioOriginator = this.usuarioService.obtenerUsuario(originator);
				} catch (SecurityNegocioException e) {
					LOGGER.error("Mensaje de error", e);
				}
				if (usuarioOriginator != null) {
					nyAOriginator = usuarioOriginator.getNombreApellido();
				}
				if (StringUtils.isNotEmpty(nyAOriginator)) {
					taskView.setEnviadoPor(nyAOriginator);
				} else {
					taskView.setEnviadoPor(originator);
				}
			} else {
				taskView.setEnviadoPor("N/D");
			}

			// Tarea DERIVADA A
			String derivator = (String) this.processEngine.getExecutionService().getVariable(task.getExecutionId(),
					"usuarioDerivador");
			String nyADerivator = "Desc.";
			Usuario usuarioDerivator = null;
			if (StringUtils.isNotEmpty(derivator)) {
				try {
					usuarioDerivator = this.usuarioService.obtenerUsuario(derivator);
				} catch (SecurityNegocioException e) {
					LOGGER.error("Mensaje de error", e);
				}
				if (usuarioDerivator != null) {
					nyADerivator = usuarioDerivator.getNombreApellido();
				}
				if (StringUtils.isNotEmpty(nyADerivator)) {
					taskView.setDerivadoPor(nyADerivator);
				} else {
					taskView.setDerivadoPor(derivator);
				}
			} else {
				taskView.setDerivadoPor("N/D");
			}

			// MOTIVO
			taskView.setMotivoDocumento(
					(String) this.processEngine.getExecutionService().getVariable(task.getExecutionId(), "motivo"));

			// TIPO DOCUMENTO
			taskView.setTipoDocumento((String) this.processEngine.getExecutionService()
					.getVariable(task.getExecutionId(), "tipoDocumento"));

			listTaskView.add(taskView);
		}

		return listTaskView;
	}

	public List<Task> buscarTasksPorUsuario(Map<String, Object> parametrosConsulta,
			Map<String, String> parametrosOrden) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTasksPorUsuario(Map<String,Object>, Map<String,String>) - start"); //$NON-NLS-1$
		}

		String usuario = (String) parametrosConsulta.get("usuario");
		String inicioCarga = parametrosOrden.get("inicioCarga");
		String tamanoPaginacion = parametrosOrden.get("tamanoPaginacion");
		String criterio = parametrosOrden.get("criterio");
		String orden = parametrosOrden.get("orden");

		if (criterio.equals(NOMBRE_TAREA)) {
			criterio = TaskQuery.PROPERTY_NAME;
		} else {
			criterio = TaskQuery.PROPERTY_CREATEDATE;
		}

		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		tq.assignee(usuario);
		if (orden.compareTo("ascending") == 0)
			tq.orderAsc(criterio);
		else
			tq.orderDesc(criterio);
		tq.page(Integer.parseInt(inicioCarga), Integer.parseInt(tamanoPaginacion));
		List<Task> returnList = tq.list();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTasksPorUsuario(Map<String,Object>, Map<String,String>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	public List<Task> buscarTasksPorUsuarioYComunicable(Map<String, Object> parametrosConsulta,
			Map<String, String> parametrosOrden) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTasksPorUsuarioYComunicable(Map<String,Object>, Map<String,String>) - start"); //$NON-NLS-1$
		}

		String usuario = (String) parametrosConsulta.get("usuario");
		String inicioCarga = null;
		String tamanoPaginacion = null;
		String criterio = null;
		String orden = null;

		if (parametrosOrden != null) {
			inicioCarga = parametrosOrden.get("inicioCarga");
			tamanoPaginacion = parametrosOrden.get("tamanoPaginacion");
			criterio = parametrosOrden.get("criterio");
			orden = parametrosOrden.get("orden");

			if (criterio != null) {
				if (criterio.equals(TaskViewImpl.NOMBRE_TAREA)) {
					criterio = TaskQuery.PROPERTY_NAME;
				} else {
					criterio = TaskQuery.PROPERTY_CREATEDATE;
				}
			}
		}
		List<Task> returnList = taskGedoDAO.buscarTasksPorUsuarioYCaractDeTipoDoc(usuario, "esComunicable", inicioCarga,
				tamanoPaginacion, criterio, orden);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTasksPorUsuarioYComunicable(Map<String,Object>, Map<String,String>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	public List<Task> buscarTask(String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTask(String) - start"); //$NON-NLS-1$
		}

		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		tq.executionId(workflowId);
		List<Task> returnList = tq.list();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTask(String) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	public int countTasks(String usuario) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("countTasks(String) - start"); //$NON-NLS-1$
		}
		Long numero;
		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		tq.assignee(usuario);
		numero = tq.count();
		int returnint = numero.intValue();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("countTasks(String) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	public int countTasksComunicables(String usuarioActual) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("countTasksComunicables(String) - start"); //$NON-NLS-1$
		}

		int returnint = (int) taskGedoDAO.countTasksPorUsuarioYCaractDeTipoDoc(usuarioActual, "esComunicable");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("countTasksComunicables(String) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public TaskGedoDAO getTaskGedoDAO() {
		return taskGedoDAO;
	}

	public void setTaskGedoDAO(TaskGedoDAO taskGedoDAO) {
		this.taskGedoDAO = taskGedoDAO;
	}
}
