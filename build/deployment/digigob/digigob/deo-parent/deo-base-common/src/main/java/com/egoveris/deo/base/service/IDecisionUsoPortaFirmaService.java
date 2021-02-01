package com.egoveris.deo.base.service;

import org.jbpm.api.model.OpenExecution;

public interface IDecisionUsoPortaFirmaService {

  public String decide(OpenExecution execution);

  public boolean firmaEnPortaFirma(String username);

  public void setUsuarioPortaFirma(String userName, boolean tienePortafirma);

  public void subirRecursoRevisionWebDav(OpenExecution execution);

}
