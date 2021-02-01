package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TareasUsuario;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;

@Service
@Transactional
public class SupervisadosServiceImpl implements SupervisadosService {
	private static final Logger logger = LoggerFactory.getLogger(SupervisadosServiceImpl.class);

	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;
	@Autowired
	private UsuariosSADEService usuariosSADEService;

	@Override
	public void cancelarTarea(final Task task) {
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarTarea(task={}) - start", task);
		}

		processEngine.getExecutionService().endProcessInstance(task.getExecutionId(), ProcessInstance.STATE_ENDED);

		if (logger.isDebugEnabled()) {
			logger.debug("cancelarTarea(Task) - end");
		}
	}

	@Override
	public Usuario getSupervisado(final String username, final String usernameSupervisado) {
		if (logger.isDebugEnabled()) {
			logger.debug("getSupervisado(username={}, usernameSupervisado={}) - start", username, usernameSupervisado);
		}

		final Usuario result = usuariosSADEService.getDatosUsuario(usernameSupervisado);
		if (result.getSupervisor() != null && result.getSupervisor().equalsIgnoreCase(username)) {
			if (logger.isDebugEnabled()) {
				logger.debug("getSupervisado(String, String) - end - return value={}", result);
			}
			return result;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("getSupervisado(String, String) - end - return value={null}");
			}
			return null;
		}

	}

	@Override
	public List<TareasUsuario> obtenerResumenTareasSupervisados(final String username) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerResumenTareasSupervisados(username={}) - start", username);
		}

		final List<TareasUsuario> tareasSupervisados = new ArrayList<TareasUsuario>();
		final List<Usuario> supervisados = usuariosSADEService.obtenerUsuariosSupervisados(username);

		final TaskService taskService = processEngine.getTaskService();
		if (supervisados != null) {
			for (final Usuario datosUsuario : supervisados) {
				final List<Task> tareas = taskService.findPersonalTasks(datosUsuario.getUsername());

				if (datosUsuario.getNombreApellido() == null) {
					datosUsuario.setNombreApellido("");
				}

				if (tareas != null) {
					tareasSupervisados.add(new TareasUsuario(datosUsuario, tareas.size()));
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerResumenTareasSupervisados(String) - end - return value={}", tareasSupervisados);
		}
		return tareasSupervisados;
	}

	public void reasignarTarea(final Task task, final String username, final String usernameSupervisado,
			final String motivo, final String usuarioLogin) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"reasignarTarea(task={}, username={}, usernameSupervisado={}, motivo={}, usuarioLogin={}) - start",
					task, username, usernameSupervisado, motivo, usuarioLogin);
		}

		final StringBuilder excutionID = new StringBuilder();

		if (task.getName().equals(ConstantesWeb.TASK_NOMBRE_PARALELO)) {
			final String[] parser = StringUtils.split(task.getExecutionId(), '.');
			excutionID.append(parser[0]);
			excutionID.append(".");
			excutionID.append(parser[1]);
		} else {
			excutionID.append(task.getExecutionId());
		}

		final ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
				.buscarExpedienteElectronicoByIdTask(excutionID.toString());

		final Map<String, String> detalles = new HashMap();
		if (expedienteElectronico != null) {

			// Se registra el pase historial de movimiento

			detalles.put(ConstantesWeb.ESTADO, task.getActivityName());
			detalles.put(ConstantesWeb.MOTIVO, motivo);
			detalles.put(ConstantesWeb.DESTINATARIO, username);

			final Usuario datosUsuario = usuariosSADEService.getDatosUsuario(usuarioLogin);

			detalles.put(ConstantesWeb.REPARTICION_USUARIO, datosUsuario.getCodigoReparticion());
			detalles.put(ConstantesWeb.SECTOR_USUARIO, datosUsuario.getCodigoSectorInterno());

			if (expedienteElectronico.getEsCabeceraTC() != null && expedienteElectronico.getEsCabeceraTC()) {
				// En caso de que sea una tramitacion conjunta, asignara todos
				// los hijos.
				for (final ExpedienteAsociadoEntDTO expedienteAsociado : expedienteElectronico
						.getListaExpedientesAsociados()) {
					if (expedienteAsociado != null) {
						if (expedienteAsociado.getEsExpedienteAsociadoTC() != null
								&& expedienteAsociado.getEsExpedienteAsociadoTC()) {

							// Obtengo el expediente de la tabla
							// expedienteElectronico
							final ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
									.obtenerExpedienteElectronico(expedienteAsociado.getTipoDocumento(),
											expedienteAsociado.getAnio(), expedienteAsociado.getNumero(),
											expedienteAsociado.getCodigoReparticionUsuario());

							// obtengo la task de cada expediente electronico.
							final TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
									.executionId(expElectronico.getIdWorkflow());
							final Task taskExpAsociado = taskQuery.uniqueResult();

							/**
							 * Se debe actualizar el assignee de cada task de
							 * cada expediente hijo.
							 *
							 */

							final Calendar cal = Calendar.getInstance();
							cal.setTime(new Date());
							taskExpAsociado.getCreateTime().setTime(cal.getTimeInMillis());
							this.processEngine.getTaskService().saveTask(taskExpAsociado);

							this.expedienteElectronicoService.guardarDatosEnHistorialOperacionP(expedienteElectronico,
									usernameSupervisado, detalles);

							// Si se advoca una tarea siempre sera un usuario,
							// por
							// eso trabaja con el assignee
							this.processEngine.getTaskService().assignTask(taskExpAsociado.getId(),
									username + ".conjunta");

						}
					}

				}
			}
		}

		if (StringUtils.isNotEmpty(username)) {
			processEngine.getTaskService().assignTask(task.getId(), username);
			processEngine.getExecutionService().createVariable(task.getExecutionId(), "supervisado",
					usernameSupervisado, true);
			if (expedienteElectronico != null) {
				this.expedienteElectronicoService.guardarDatosEnHistorialOperacionP(expedienteElectronico,
						usernameSupervisado, detalles);
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("reasignarTarea(Task, String, String, String, String) - end");
		}
	}

	@Override
	public List<TareasUsuario> obtenerUsuarioPorSector(final String sector) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerUsuarioPorSector(username={}) - start", sector);
		}

		final List<TareasUsuario> tareasSupervisados = new ArrayList<TareasUsuario>();
		final List<Usuario> supervisados = usuariosSADEService.obtenerUsuariosPorSector(sector);

		final TaskService taskService = processEngine.getTaskService();
		if (supervisados != null) {
			for (final Usuario datosUsuario : supervisados) {
				final List<Task> tareas = taskService.findPersonalTasks(datosUsuario.getUsername());

				if (datosUsuario.getNombreApellido() == null) {
					datosUsuario.setNombreApellido("");
				}

				if (tareas != null) {
					tareasSupervisados.add(new TareasUsuario(datosUsuario, tareas.size()));
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerUsuarioPorSector(String) - end - return value={}", tareasSupervisados);
		}
		return tareasSupervisados;
	}
}