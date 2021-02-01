package com.egoveris.deo.base.decision;

import com.egoveris.deo.util.Constantes;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

/**
 * 
 * Determina que transición seguir dependiendo de si el tipo de documento se
 * genera a partir de template o no.
 * 
 * @author kmarroqu
 *
 */

@SuppressWarnings("serial")
public class DecisionUsoTemplate implements DecisionHandler {

  public String decide(OpenExecution execution) {
    return Constantes.TRANSICION_CONFECCIONAR_DOCUMENTO;
  }

}
