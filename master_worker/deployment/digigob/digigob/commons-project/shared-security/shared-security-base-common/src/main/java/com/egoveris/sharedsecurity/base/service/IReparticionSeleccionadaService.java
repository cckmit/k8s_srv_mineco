package com.egoveris.sharedsecurity.base.service;

import java.util.List;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionSeleccionadaDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;

/**
 * The Interface IReparticionSeleccionadaService.
 */
public interface IReparticionSeleccionadaService {

  /**
   * Se encarga de actualizar o guardar la reparticion actual en la que trabaja
   * un Usuario, a partir de su seleccion.
   *
   * @param rep
   *          la ReparticionDTO seleccionada
   * @param user
   *          El Usuario al que que se setea la ReparticionDTO
   * @throws NegocioException
   *           the negocio exception
   */

  public void establecerReparticionSeleccionada(ReparticionSeleccionadaDTO rep, Usuario user)
      throws  SecurityNegocioException;

  /**
   * Obtener reparticion por repartición.
   *
   * @param reparticion
   *          the reparticion
   * @return the list
   */
  public List<ReparticionSeleccionadaDTO> obtenerReparticionPorRepa(ReparticionDTO reparticion);

  /**
   * Obtener reparticion por repa Y sector.
   *
   * @param reparticion
   *          the reparticion
   * @param sector
   *          the sector
   * @return the list
   */
  public List<ReparticionSeleccionadaDTO> obtenerReparticionPorRepaYSector(
      ReparticionDTO reparticion, SectorDTO sector);
}
