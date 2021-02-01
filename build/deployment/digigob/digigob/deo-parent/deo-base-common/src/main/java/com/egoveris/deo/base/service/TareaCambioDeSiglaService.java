package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.model.model.TareaCambioDeSiglaDTO;
import com.egoveris.deo.model.model.TareaCambioDeSiglaErrorDTO;

public interface TareaCambioDeSiglaService {

  /**
   * Permite guardar la tarea que se ejecutó en la tabla
   * GEDO_AUD_TAREA_CAMBIO_SIGLA
   * 
   * @param tareaCambioDeSigla
   */
  public void guardarTarea(TareaCambioDeSiglaDTO tareaCambioDeSigla)
      throws EjecucionSiglaException;

  /**
   * Permite guardar el error de la tarea que se ejecutó en la tabla
   * GEDO_AUD_ERROR_CAMBIO_SIGLA, en caso de error, se puede buscar la tarea por
   * el id_tarea
   * 
   * @param tareaCambioDeSiglaError
   */
  public void guardarErrorTarea(TareaCambioDeSiglaErrorDTO tareaCambioDeSiglaError);

}
