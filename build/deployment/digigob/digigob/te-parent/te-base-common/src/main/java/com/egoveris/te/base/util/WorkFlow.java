package com.egoveris.te.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.ITaskViewService;


/**
 * Esta clase genérica <code>WorkFlow</code>. Esta asociado a las actividades ( Activity ).<br>
 * El <code>WorkFlow</code> podra ser un atributo de un objeto de modelo denominado<code>DomainObject</code>,
 * ellos podran usar interfaces WorkFlow<code>Aware</code>.
 */
public class WorkFlow {
    private transient static Logger logger = LoggerFactory.getLogger(WorkFlow.class);
    @Autowired
    private ProcessEngine processEngine = null;
    @Autowired
    private ExpedienteElectronicoService expedienteElectronicoService;
    private ExpedienteElectronicoDTO expedienteElectronico;
    private String activityName = null;
    private Map parametersMap = new HashMap<String, String>();
    private ITaskViewService taskViewService = null; 

    /**
     * Constructor por default
     */
    public WorkFlow() {
    }

    /**
     * Constructor con
     * @param <code>java.lang.String<code>activityName
     */
    public WorkFlow(String activityName) {
        this.activityName = activityName;
    }

    /**
     * Constructor con
     * @param <code>java.lang.String<code>activityName
     * @param <code>Map<String, String><code>parametersMap
     */
    public WorkFlow(String activityName, Map<String, String> detalles) {
        this.activityName = activityName;
        this.initParameters(detalles);
    }

    /**
     * Constructor con parametros
     * @param <code>org.jbpm.api.ProcessEngine</code>processEngine
     * @param <code>org.jbpm.api.task.Task</code>task
     * @param <code>com.egoveris.te.base.model.expediente.ExpedienteElectronico</code>expedienteElectronico
     * @param <code>java.lang.String<code>activityName
     */
    public WorkFlow(final ITaskViewService taskViewService, final ExpedienteElectronicoService expedienteElectronicoService, final ProcessEngine processEngine, final ExpedienteElectronicoDTO expedienteElectronico2, final String activityName) {
    	this.taskViewService = taskViewService;
    	this.expedienteElectronicoService = expedienteElectronicoService;
        this.processEngine = processEngine;
        this.expedienteElectronico = expedienteElectronico2;
        this.activityName = activityName;
    }

	@Transactional (propagation = Propagation.REQUIRED)
	public void initParameters(final Map<String, String> parameters) {
		if (logger.isDebugEnabled()) {
			logger.debug("initParameters(parameters={}) - start", parameters);
		}

    if ("".equalsIgnoreCase((String) getMapValue(ConstantesCommon.USUARIO_SELECCIONADO, parameters))) {
        this.parametersMap.put(ConstantesCommon.DESTINATARIO, (String) getMapValue(ConstantesCommon.DESTINATARIO, parameters));
        this.parametersMap.put(ConstantesCommon.GRUPO_SELECCIONADO, (String) getMapValue(ConstantesCommon.GRUPO_SELECCIONADO, parameters));
        this.parametersMap.put(ConstantesCommon.USUARIO_SELECCIONADO, null);
        this.parametersMap.put(ConstantesCommon.TAREA_GRUPAL, "esTareaGrupal");
    } else {
        this.parametersMap.put(ConstantesCommon.USUARIO_SELECCIONADO, (String) getMapValue(ConstantesCommon.USUARIO_SELECCIONADO, parameters));
        this.parametersMap.put(ConstantesCommon.DESTINATARIO, null);
        this.parametersMap.put(ConstantesCommon.GRUPO_SELECCIONADO, null);
        this.parametersMap.put(ConstantesCommon.TAREA_GRUPAL, "noEsTareaGrupal");
    }
    
    this.parametersMap.put("resultLabel", parameters.get("resultLabel"));
    this.parametersMap.put("resultValue", parameters.get("resultValue"));
    
		if (logger.isDebugEnabled()) {
			logger.debug("initParameters(Map<String,String>) - end");
		}
        return;
    }

    private String getMapValue(String key, Map<String, String> detalles) {
		if (logger.isDebugEnabled()) {
			logger.debug("getMapValue(key={}, detalles={}) - start", key, detalles);
		}

        String mapValue = "";

        if (detalles.containsKey(key) && (detalles.get(key) != null)) {
            mapValue = detalles.get(key);
        }

		if (logger.isDebugEnabled()) {
			logger.debug("getMapValue(String, Map<String,String>) - end - return value={}", mapValue);
		}
        return mapValue;
    }
    
    /**
     * Se valida qué la <code>org.jbpm.api.task.Task</code> sea la ejecucción correcta.
     * @param <code>org.jbpm.api.task.Task</code>task del workflow.
     * @param <code>ExpedienteElectronico</code>expedienteElectronico modelo de workflow.
     * @throws <code>Exception</code> excepcion, en el caso de la excepción está se informara al cliente.
     */


	@Transactional (propagation = Propagation.REQUIRED)
	public boolean execute(Task task, ProcessEngine refProcessEngine) throws InterruptedException {
       		if (logger.isDebugEnabled()) {
       			logger.debug("execute(task={}, refProcessEngine={}) - start", task, refProcessEngine);
       		}

        boolean ret;

        try {
            /* JIRA
             * BISADE-2241: https://quark.everis.com/jira/i#browse/BISADE-2241
             * en el caso que la activity transaction es registracion se elimina removeTaskParticipatingGroup
             */
            if (logger.isDebugEnabled()) {
            	logger.debug("[ThreadId= " + Thread.currentThread().getId() + "], before call assignTask - workflow.id=" + task.getId() + ", getAssignee=" +
                		task.getAssignee());
            }

            try {
	        	List<Participation> participants = getTaskParticipations(task);
	            for (Participation participant : participants) {
	                	logger.debug("[ThreadId= " + Thread.currentThread().getId() + "], removeTaskParticipatingGroup workflow.id=" + task.getId() + ", groupId=" + participant.getGroupId() +
	                        ",candidate=" + Participation.CANDIDATE + ", this.workingTask.getExecutionId() " + task.getExecutionId());
	
	                refProcessEngine.getTaskService().removeTaskParticipatingGroup(task.getId(), participant.getGroupId(), Participation.CANDIDATE);
	            }

            
	            if (parametersMap.containsKey(ConstantesCommon.GRUPO_SELECCIONADO) && (this.parametersMap.get(ConstantesCommon.GRUPO_SELECCIONADO) != null)) {
	            	refProcessEngine.getTaskService().removeTaskParticipatingUser(task.getId(), task.getAssignee(), Participation.OWNER);
	            	refProcessEngine.getTaskService().removeTaskParticipatingUser(task.getId(), task.getAssignee(), Participation.CANDIDATE);
	            }

            } catch(JbpmException jbpmException) {
              logger.error("JbpmException", jbpmException);
            	logger.error("[ThreadId= " + Thread.currentThread().getId() + "],  task.getId() "+task.getId()+" was not found. ");
            }

	        if (parametersMap.containsKey(ConstantesCommon.GRUPO_SELECCIONADO) && (this.parametersMap.get(ConstantesCommon.GRUPO_SELECCIONADO) != null)) {

	        	refProcessEngine.getTaskService().addTaskParticipatingGroup(task.getId(), parametersMap.get(ConstantesCommon.GRUPO_SELECCIONADO).toString(), Participation.CANDIDATE);
	        	refProcessEngine.getTaskService().assignTask(task.getId(), null);

                if (logger.isDebugEnabled()) {
                	logger.debug("[ThreadId= " + Thread.currentThread().getId() + "], workflow.id=" + task.getId() + ", getAssignee=" +
                        parametersMap.get(ConstantesCommon.GRUPO_SELECCIONADO) + " grupo seleccionado.");
                }
            } else {
            	refProcessEngine.getTaskService().assignTask(task.getId(), parametersMap.get(ConstantesCommon.USUARIO_SELECCIONADO).toString());

                if (logger.isDebugEnabled()) {
                	logger.debug("[ThreadId= " + Thread.currentThread().getId() + "],  workflow.id=" + task.getId() + ", getAssignee=" + task.getAssignee() +
                        "usuarioSeleccionado?=" + this.parametersMap.get(ConstantesCommon.USUARIO_SELECCIONADO));
                }
            }
            
            parametersMap.put("idExpedienteElectronico", this.expedienteElectronico.getId());
            refProcessEngine.getExecutionService().setVariables(task.getExecutionId(), parametersMap);
            refProcessEngine.getExecutionService().signalExecutionById(task.getExecutionId(), activityName);

            if (logger.isDebugEnabled()) {
            	logger.debug("[ThreadId= " + Thread.currentThread().getId() + "], end method - workflow.executionId=" + task.getExecutionId() + ", activityName=" + activityName +
                    ", getAssignee=" + task.getAssignee());
            }

            ret = true;
        } catch (Exception e) {
            logger.error("error al ejecutar task", e);
            logger.error("[ThreadId= " + Thread.currentThread().getId() + "], ocurrio un error inesperado en la ejecución de WFK." + e.getMessage());
            throw new InterruptedException(e.getMessage());
        }

      		if (logger.isDebugEnabled()) {
      			logger.debug("execute(Task, ProcessEngine) - end - return value={}", ret);
      		}
        return ret;
    }

    /**
     * Obtiene la cantidad total tasks (participant.type = 'owner')
     * @param <code>java.lang.String</code>assignee (el)
     * @return <code>long</code> totalTaskAssignee
     */
    public long totalTaskAssignee(final String assignee) {
		if (logger.isDebugEnabled()) {
			logger.debug("totalTaskAssignee(assignee={}) - start", assignee);
		}

        long countTaskAssignee = processEngine.getTaskService().createTaskQuery().executionId(expedienteElectronico.getIdWorkflow()).assignee(assignee).count();

		if (logger.isDebugEnabled()) {
			logger.debug("totalTaskAssignee(String) - end - return value={}", countTaskAssignee);
		}
        return countTaskAssignee;
    }

    /**
     * Obtiene la cantidad total grupos (participant.type = 'candidate') candidatos que pertenece el assignee.
     * @param <code>java.lang.String</code>assignee (el)
     * @return <code>long</code> totalTaskCandidate
     */
    public long totalTaskCandidate(final String assignee) {
		if (logger.isDebugEnabled()) {
			logger.debug("totalTaskCandidate(assignee={}) - start", assignee);
		}

        long countTaskCandidate = processEngine.getTaskService().createTaskQuery().executionId(expedienteElectronico.getIdWorkflow()).candidate(assignee).count();

		if (logger.isDebugEnabled()) {
			logger.debug("totalTaskCandidate(String) - end - return value={}", countTaskCandidate);
		}
        return countTaskCandidate;
    }

	@Transactional (propagation = Propagation.REQUIRED)
    public org.jbpm.api.task.Task getWorkingTask() {
		if (logger.isDebugEnabled()) {
			logger.debug("getWorkingTask() - start");
		}

		ProcessInstance processInstance = processEngine.getExecutionService().findProcessInstanceById(expedienteElectronico.getIdWorkflow());
		TaskQuery query = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId());
		Task workingTask  = query.uniqueResult();

		if (logger.isDebugEnabled()) {
			logger.debug("getWorkingTask() - end - return value={}", workingTask);
		}
    	return workingTask;
    }

    /**
     * Comprueba que el <code>java.lang.String</code>assignee no este en ningun grupo o tarea del <code>com.egoveris.te.core.api.expedientes.dominio.ExpedienteElectronico</code>
     * @param <code>java.lang.String</code>assignee (el)
     * @return <code>boolean</code> si assignee no existe ninguna tarea o grupo de la misma que este ya trabajando.
     */
    public boolean existeTareaGrupalParaAssignee(final String assignee) {
		if (logger.isDebugEnabled()) {
			logger.debug("existeTareaGrupalParaAssignee(assignee={}) - start", assignee);
		}

		boolean returnboolean = (totalTaskAssignee(assignee) == 0) && (totalTaskCandidate(assignee) == 0);
		if (logger.isDebugEnabled()) {
			logger.debug("existeTareaGrupalParaAssignee(String) - end - return value={}", returnboolean);
		}
        return returnboolean;
    }

    /**
     * get roles candidateGroupIds: 
	 * [MGEYA, MGEYA-MESI, 
	 * MGEYA-SADE.EXTERNOS, 
	 * MGEYA-MESI-SADE.EXTERNOS, 
	 * MGEYA-SADE.INTERNOS, 
	 * MGEYA-MESI-SADE.INTERNOS], related to a given working task. 
     * @return <code>java.util.Set<Participation></code>getTaskParticipations(), relaciondada a la getWorkingTask().
     */
    public List<Participation> getTaskParticipations(Task task) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTaskParticipations() - start");
		}

        List<Participation> participants = processEngine.getTaskService().getTaskParticipations(task.getId());

		if (logger.isDebugEnabled()) {
			logger.debug("getTaskParticipations() - end - return value={}", participants);
		}
    	return participants; 
    }

    public ExpedienteElectronicoDTO getExpedienteElectronico() {
    	return this.expedienteElectronico;
    }
}
