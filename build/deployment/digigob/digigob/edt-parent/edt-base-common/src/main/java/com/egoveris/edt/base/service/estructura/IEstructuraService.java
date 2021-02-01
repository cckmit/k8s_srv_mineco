package com.egoveris.edt.base.service.estructura;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.EstructuraDTO;

/**
 * The Interface IEstructuraService.
 */
public interface IEstructuraService {

  /**
   * Obtener Lista estructuras.
   *
   * @return the list
   */
  List<EstructuraDTO> listEstructuras();

  /**
   * Gets the by id.
   *
   * @param id
   *          the id
   * @return the by id
   */
  EstructuraDTO getById(Integer id);

  /**
   * Obtener poder ejecutivo.
   *
   * @param estructuraPoderEjecutivo
   *          the estructura poder ejecutivo
   * @return the by poder ejecutivo
   */
  List<EstructuraDTO> getByPoderEjecutivo(String estructuraPoderEjecutivo);

  /**
   * Gets the by exepciones.
   *
   * @param nombresEstructura
   *          the nombres estructura
   * @return the by exepciones
   */
  List<EstructuraDTO> getByExepciones(List<String> nombresEstructura);

  /**
   * Buscar estructura by codigo Y nombre comodin.
   *
   * @param nombreOrCodigo
   *          the nombre or codigo
   * @return the list
   */
  List<EstructuraDTO> buscarEstructuraByCodigoYNombreComodin(String nombreOrCodigo);

  /**
   * Gets the estructura by codigo.
   *
   * @param codigoEstructura
   *          the codigo estructura
   * @return the estructura by codigo
   */
  EstructuraDTO getEstructuraByCodigo(Integer codigoEstructura);

  /**
   * Guardar estructura.
   *
   * @param estructura
   *          the estructura
   */
  void guardarEstructura(EstructuraDTO estructura);

}
