package com.egoveris.edt.base.service.feriado;

import com.egoveris.edt.base.model.eu.feriado.FeriadoDTO;

import java.util.List;

/**
 * The Interface FeriadoService.
 */
public interface FeriadoService {
  /**
   * Obtiene los feriados ordenados por fecha.
   * 
   * @return
   */
  public List<FeriadoDTO> obtenerFeriados();

  /**
   * Guarda o modifica el feriado.
   * 
   * @param feriado
   * @throws DataAccessLayerException
   */
  public void guardarOModificarFeriado(FeriadoDTO feriado, String usuario);

  /**
   * Elimina feriado.
   * 
   * @param feriado
   * @param usuario
   */
  public void eliminarFeriado(FeriadoDTO feriado, String usuario);

}