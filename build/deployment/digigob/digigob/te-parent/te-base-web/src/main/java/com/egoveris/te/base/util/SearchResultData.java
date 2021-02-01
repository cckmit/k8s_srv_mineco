package com.egoveris.te.base.util;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.iface.IExpediente;

import java.util.ArrayList;
import java.util.List;

public class SearchResultData {
  private List<ExpedienteElectronicoDTO> resultado;

  public void setResultado(List<ExpedienteElectronicoDTO> resultado) {
    this.resultado = resultado;
  }

  public List<ExpedienteElectronicoDTO> getResultado() {
    return resultado;
  }

  public List<IExpediente> toInterfaz() {
    List<IExpediente> result = new ArrayList<>();
    for (ExpedienteElectronicoDTO e : resultado) {
      result.add((IExpediente) e);
    }
    return result;
  }

}
