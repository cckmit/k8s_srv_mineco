package com.egoveris.te.base.dao;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;

public interface ExpedienteSadeDAO {
  
  public ExpedienteAsociadoEntDTO obtenerExpedienteSade(String tipoDocumento,
      Integer anio, Integer numero, String reparticionUsuario);
  
  public String obtenerCodigoTrataSADE(ExpedienteAsociadoEntDTO expedienteAsociado);

}
