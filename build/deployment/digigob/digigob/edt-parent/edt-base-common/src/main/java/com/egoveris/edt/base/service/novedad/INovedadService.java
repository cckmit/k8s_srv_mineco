package com.egoveris.edt.base.service.novedad;

import com.egoveris.edt.base.model.eu.CategoriaDTO;
import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.util.EstadoNovedadEnum;

import java.util.List;

/**
 * The Interface INovedadService.
 */
public interface INovedadService {

  /**
   * Save.
   *
   * @param novedad
   *          the novedad
   */
  NovedadDTO save(NovedadDTO novedad);

  /**
   * Geyby id.
   *
   * @param id
   *          the id
   * @return the novedad DTO
   */
  NovedadDTO geybyId(Integer id);

  /**
   * Gets the by categoria.
   *
   * @param cat
   *          the cat
   * @return the by categoria
   */
  List<NovedadDTO> getByCategoria(CategoriaDTO cat);

  /**
   * Gets the activas.
   *
   * @return the activas
   */
  List<NovedadDTO> getActivas();

  /**
   * Gets the pendientes.
   *
   * @return the pendientes
   */
  List<NovedadDTO> getPendientes();

  /**
   * Gets the historial.
   *
   * @return the historial
   */
  List<NovedadDTO> gethistorial();

  /**
   * Gets the by estado.
   *
   * @param estado
   *          the estado
   * @return the by estado
   */
  List<NovedadDTO> getbyEstado(EstadoNovedadEnum estado);

  /**
   * Delete.
   *
   * @param novedad
   *          the novedad
   */
  void delete(NovedadDTO novedad);

  /**
   * Cambiar estado job.
   */
  void cambiarEstadoJob();
}
