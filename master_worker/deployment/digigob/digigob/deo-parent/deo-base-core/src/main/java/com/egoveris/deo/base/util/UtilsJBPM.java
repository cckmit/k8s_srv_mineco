package com.egoveris.deo.base.util;

import com.egoveris.deo.base.exception.VariableWorkflowException;

import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessEngine;

public abstract class UtilsJBPM {

  public static Object getVariableWorkFlow(ProcessEngine processEngine, String executionId,
      String variableName, boolean debeExistir) {
    Object obj = processEngine.getExecutionService().getVariable(executionId, variableName);
    if (obj == null && debeExistir) {
      throw new VariableWorkflowException(
          "No existe la variable para el id de ejecucion. " + executionId + ", " + variableName);
    }
    return obj;
  }

  public static Map<String, Object> getVariablesWorkFlow(ProcessEngine processEngine,
      String executionId, Set<String> variablesName) {
    return processEngine.getExecutionService().getVariables(executionId, variablesName);
  }

  public static boolean existeTarea(ProcessEngine processEngine, String executionId, String user,
      String taskName) {
    return processEngine.getTaskService().createTaskQuery().executionId(executionId).assignee(user)
        .activityName(taskName).count() == 1;
  }
}
