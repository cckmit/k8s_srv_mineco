package com.egoveris.te.base.vm;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

public class DocumentosTabVM {

  private ExpedienteElectronicoDTO expediente;

  /**
   * Inicializa el Tab de Documentos.
   * 
   * @param expediente
   *          Expediente
   */
  @Init
  public void init(@ExecutionArgParam("expediente") ExpedienteElectronicoDTO expediente) {
    setExpediente(expediente);
  }

  // Getters - setters

  public ExpedienteElectronicoDTO getExpediente() {
    return expediente;
  }

  public void setExpediente(ExpedienteElectronicoDTO expediente) {
    this.expediente = expediente;
  }

}
