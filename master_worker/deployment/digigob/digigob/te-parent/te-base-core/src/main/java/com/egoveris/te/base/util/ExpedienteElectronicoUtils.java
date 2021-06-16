package com.egoveris.te.base.util;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ExpedienteElectronicoUtils {
	private static final Logger logger = LoggerFactory.getLogger(ExpedienteElectronicoUtils.class);
	
	public static void agregarOquitarSufijoBloqueado(Task task, boolean bloqueado, ProcessEngine processEngine){
		if (logger.isDebugEnabled()) {
			logger.debug("agregarOquitarSufijoBloqueado(task={}, bloqueado={}, processEngine={}) - start", task, bloqueado, processEngine);
		}
		
		List<Participation> listaGrupo = processEngine.getTaskService().getTaskParticipations(task.getId());
		for (Participation grupo : listaGrupo) {
			processEngine.getTaskService().removeTaskParticipatingGroup(task.getId(), grupo.getGroupId(),
					Participation.CANDIDATE);
			String nombreGrupo = grupo.getGroupId();
			if (bloqueado) {
				if (!nombreGrupo.contains(ConstantesWeb.SUFIJO_BLOQUEADO)) {
					nombreGrupo = nombreGrupo + ConstantesWeb.SUFIJO_BLOQUEADO;
				}
			} else {
				if (nombreGrupo.contains(ConstantesWeb.SUFIJO_BLOQUEADO)) {
					nombreGrupo = nombreGrupo.split(ConstantesWeb.SUFIJO_BLOQUEADO)[0];
				}
			}
			processEngine.getTaskService()
					.addTaskParticipatingGroup(task.getId(), nombreGrupo, Participation.CANDIDATE);
		}
		String assignee = task.getAssignee();
		if(StringUtils.isNotEmpty(assignee)){
			if(bloqueado){
				if (!assignee.contains(ConstantesWeb.SUFIJO_BLOQUEADO)) {
					assignee= assignee + ConstantesWeb.SUFIJO_BLOQUEADO;
				}
			}else{
				if(assignee.contains(ConstantesWeb.SUFIJO_BLOQUEADO)){
					assignee= assignee.split(ConstantesWeb.SUFIJO_BLOQUEADO)[0];
				}	
			}
		}
		processEngine.getTaskService().assignTask(task.getId(), assignee);

		if (logger.isDebugEnabled()) {
			logger.debug("agregarOquitarSufijoBloqueado(Task, boolean, ProcessEngine) - end");
		}
	}
	
	public static void agregarOquitarSufijoBloqueado(ExpedienteElectronicoDTO expedienteElectronico, ProcessEngine processEngine) {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarOquitarSufijoBloqueado(expedienteElectronico={}, processEngine={}) - start", expedienteElectronico, processEngine);
		}

		Task task = processEngine.getTaskService().createTaskQuery().executionId(expedienteElectronico.getIdWorkflow()).uniqueResult();
		
		if (task != null) {
			agregarOquitarSufijoBloqueado(task, expedienteElectronico.getBloqueado(), processEngine);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarOquitarSufijoBloqueado(ExpedienteElectronico, ProcessEngine) - end");
		}
	}
}
