package com.egoveris.deo.base.task;

import com.egoveris.deo.util.Constantes;

import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class ImportarDocumento implements ExternalActivityBehaviour {

  @Autowired
  private transient ProcessEngine processEngine;

  @Override
  public void signal(ActivityExecution execution, String signalName, Map<String, ?> parameters)
      throws Exception {
    execution.take(signalName);
  }

  @Override
  public void execute(ActivityExecution execution) throws Exception {
    String executionId = execution.getId();
    this.processEngine.getExecutionService().signalExecutionById(executionId,
        Constantes.TRANSICION_USO_PORTAFIRMA);
  }

}
