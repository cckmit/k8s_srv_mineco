package com.egoveris.te.core.api.ee.asignacion;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.util.ConstantesWeb;

/**
 * CLASE COPIADA TEMPORALMENTE PARA PROBAR EL FUNCIONAMIENTO
 * DE INICIO DE SUBPROCESOS. NOTESE QUE EL PACKAGE ES INCORRECTO 
 * (com.egoveris.te.core.api.ee.asignacion)
 * 
 * @author everis
 *
 */
@Service
@Transactional
@SuppressWarnings("serial")
public class AsignarTarea implements AssignmentHandler {
  private static final Logger logger = LoggerFactory.getLogger(AsignarTarea.class);

  public void assign(Assignable assignable, OpenExecution execution) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("assign(assignable={}, execution={}) - start", assignable, execution);
    }

    String asignacionUsuario = (String) execution.getVariable(ConstantesWeb.USUARIO_SELECCIONADO);
    String asignacionGrupo = (String) execution.getVariable(ConstantesWeb.GRUPO_SELECCIONADO);

    if (StringUtils.isNotEmpty(asignacionUsuario)) {
      assignable.setAssignee(asignacionUsuario);
    } else if (StringUtils.isNotEmpty(asignacionGrupo)) {
      assignable.addCandidateGroup(asignacionGrupo);

    }

    if (logger.isDebugEnabled()) {
      logger.debug("assign(Assignable, OpenExecution) - end");
    }
  }

}