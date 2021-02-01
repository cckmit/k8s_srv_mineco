package com.egoveris.deo.base.decision;

import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.deo.util.Constantes;

import java.util.List;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class DecisionRevisionFirmaConjunta implements DecisionHandler {

  @Autowired
  private FirmaConjuntaService firmaConjuntaService;

  private FirmanteDTO firmante;

  /**
   * Este método decide sí el documento debe ser cerrado o debe enviarse a
   * firmar nuevamente. Además, define el próximo firmante
   * 
   */
  public String decide(OpenExecution execution) {
    String executionId = execution.getId();
    String transicion;

    Boolean esPrimerFirmante = Boolean.TRUE;
    List<FirmanteDTO> listadoFirmantes;

    listadoFirmantes = this.firmaConjuntaService.obtenerFirmantesPorEstado(executionId, false);

    if (listadoFirmantes.size() > 0) {
      this.firmante = listadoFirmantes.get(0);

      if (this.firmante.getOrden().intValue() != 1) {
        esPrimerFirmante = Boolean.FALSE;
      }
    }

    if (esPrimerFirmante.equals(Boolean.TRUE)) {
      transicion = Constantes.TRANSICION_REVISAR;
    } else {
      transicion = Constantes.TRANSICION_REVISAR_DOCUMENTO_FIRMA_CONJUNTA;
    }

    return transicion;
  }
}
