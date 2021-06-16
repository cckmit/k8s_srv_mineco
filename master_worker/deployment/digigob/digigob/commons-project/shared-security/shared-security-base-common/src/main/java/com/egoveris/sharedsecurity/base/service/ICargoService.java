package com.egoveris.sharedsecurity.base.service;

import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

import java.util.List;

/**
 * The Interface ICargoService.
 */
public interface ICargoService {

  /**
   * Gets the cargos activos.
   *
   * @return the cargos activos
   */
  List<CargoDTO> getCargosActivos();

  /**
   * Gets the cargo by usuario.
   *
   * @param username
   *          the username
   * @return the cargo by usuario
   */
  CargoDTO getCargoByUsuario(String username);

  /**
   * Gets the cargos activos vigentes.
   *
   * @return the cargos activos vigentes
   */
  List<CargoDTO> getCargosActivosVigentes();

  /**
   * Buscar cargos por reparticion.
   *
   * @param reparticion
   *          the reparticion
   * @return the list
   */
  List<CargoDTO> buscarCargosPorReparticion(ReparticionDTO reparticion);

  /**
   * Buscar permisos por cargo.
   *
   * @param cargo
   *          the cargo
   * @return the list
   */
//  List<String> buscarPermisosPorCargo(CargoDTO cargo);

  /**
   * Obtener cargo usuario.
   *
   * @param usuario
   *          the usuario
   * @return the cargo DTO
   */
  CargoDTO obtenerCargoUsuario(String usuario);

  /**
   * Obtener by id.
   *
   * @param id
   *          the id
   * @return the cargo DTO
   */
  CargoDTO obtenerById(Integer id);

  /**
   * Eliminar.
   *
   * @param cargo
   *          the cargo
   * @return true, if successful
   */
  boolean eliminar(CargoDTO cargo);

  /**
   * Save.
   *
   * @param cargo
   *          the cargo
   */
  void save(CargoDTO cargo);

  /**
   * Gets the cargos by rol id.
   *
   * @return the cargos by rol id
   */
//  List<CargoDTO> getCargosByRolId(Integer id);
  
}