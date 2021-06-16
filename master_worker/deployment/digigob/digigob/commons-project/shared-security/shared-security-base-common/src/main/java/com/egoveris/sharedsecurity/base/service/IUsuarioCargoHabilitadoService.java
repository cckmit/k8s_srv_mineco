package com.egoveris.sharedsecurity.base.service;

import java.util.Collection;
import java.util.List;

import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;

public interface IUsuarioCargoHabilitadoService {

  /**
   * Devuelve un listado de todas las reparticiobnesHabilitadas para el usuario
   * incluido la reparticion a la que pertenece.
   *
   * @param userName
   *          the user name
   * @return Lista de las {@link UsuarioReparticionHabilitadaDTO}
   * @throws NegocioException
   *           the negocio exception
   */
  List<UsuarioReparticionHabilitadaDTO> obtenerCargosHabilitadosByUsername(String userName);

  /**
   * Devuelve la reparticiobnesHabilitada a la que pertenece el el usuariop.
   *
   * @param userName
   *          the user name
   * @return UsuarioReparticionHabilitadaDTO
   */

  UsuarioReparticionHabilitadaDTO obtenerCargoHabilitadoByReparticionAsignadaAlUsuario(
      String userName);

  /**
   * Buscar permisos por cargo.
   *
   * @param cargo
   *          the cargo
   * @return the list
   */
//  List<String> buscarPermisosPorCargo(CargoDTO cargo);

}