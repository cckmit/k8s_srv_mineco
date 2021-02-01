package com.egoveris.deo.base.decision;

import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.List;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.springframework.beans.factory.annotation.Autowired;

public class DecisionFirmaPendiente implements DecisionHandler {

  private static final long serialVersionUID = -1370312407681398527L;

  @Autowired
  private FirmaConjuntaService firmaConjuntaService;
  @Autowired
  private ProcessEngine processEngine;

  /**
   * Este método decide sí el documento debe ser cerrado o debe enviarse a
   * firmar nuevamente. Además, define el próximo firmante
   * 
   */
  public String decide(OpenExecution execution) {
    String executionId = execution.getId();
    String transicion = Constantes.TRANSICION_CERRAR;
    
    List<Usuario> usuarios = this.firmaConjuntaService.buscarFirmantesPorEstado(executionId,
        false);
    if (usuarios != null && usuarios.size() > 0) {

      String usuario = usuarios.get(0).getUsername();
      this.processEngine.getExecutionService().setVariable(executionId,
          Constantes.VAR_USUARIO_FIRMANTE, usuario);
      transicion = Constantes.TRANSICION_USO_PORTAFIRMA;
    }
    return transicion;
  }
}