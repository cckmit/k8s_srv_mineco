package com.egoveris.edt.base.service;

import com.egoveris.edt.base.model.eu.PeriodoLicenciaDTO;

/**
 * The Interface IPeriodoLicenciaService.
 */
public interface IPeriodoLicenciaService {

  /**
   * Save.
   *
   * @param periodoLicencia
   *          the periodo licencia
   */
  public void save(PeriodoLicenciaDTO periodoLicencia);

  /**
   * Traer periodo licencia por user name.
   *
   * @param userName
   *          the user name
   * @return the periodo licencia DTO
   */
  public PeriodoLicenciaDTO traerPeriodoLicenciaPorUserName(String userName);

  /**
   * Cancelar licencia.
   *
   * @param periodoLicencia
   *          the periodo licencia
   */
  public void cancelarLicencia(PeriodoLicenciaDTO periodoLicencia);

  public void terminarLicenciasPasadas();
  
}