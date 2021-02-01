package com.egoveris.sharedsecurity.base.service;

import java.util.Collection;
import java.util.List;

import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;

public interface IUsuarioReparticionHabilitadaService {

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
  List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesHabilitadasByUsername(String userName);

  /**
   * Devuelve la reparticiobnesHabilitada a la que pertenece el el usuariop.
   *
   * @param userName
   *          the user name
   * @return UsuarioReparticionHabilitadaDTO
   */

  UsuarioReparticionHabilitadaDTO obtenerReparticionHabilitadaByReparticionAsignadaAlUsuario(
      String userName);

  /**
   * Eliminar reparticiones habilitadas.
   *
   * @param reparticionesHabilitadas
   *          the reparticiones habilitadas
   * @return true, if successful
   */
  boolean eliminarReparticionesHabilitadas(
      Collection<UsuarioReparticionHabilitadaDTO> reparticionesHabilitadas);

  /**
   * Guardar reparticiones habilitadas.
   *
   * @param reparticionesHabilitadas
   *          the reparticiones habilitadas
   * @return true, if successful
   */
  boolean guardarReparticionesHabilitadas(
      Collection<UsuarioReparticionHabilitadaDTO> reparticionesHabilitadas);

  /**
   * Devuelve un listado de todas las reparticiobnesHabilitadas para el usuario.
   *
   * @param userName
   *          the user name
   * @return Lista de las {@link UsuarioReparticionHabilitadaDTO}
   * @throws NegocioException
   *           the negocio exception
   */
  List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesHabilitadasByUsernameSinLaPropia(
      String userName);

  /**
   * Obtener reparticiones habilitadas by sector.
   *
   * @param codigoReparticion
   *          the codigo reparticion
   * @param codigoSector
   *          the codigo sector
   * @return the list
   */
  List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesHabilitadasBySector(
      String codigoReparticion, String codigoSector);

  /**
   * Obtener reparticiones by codigo.
   *
   * @param codigoReparticion
   *          the codigo reparticion
   * @return the list
   */
  List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesByCodigo(String codigoReparticion);

  /**
   * Obtener reparticiones habilitadas by codigo reparticion.
   *
   * @param codigoReparticion
   *          the codigo reparticion
   * @return the list
   */
  List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesHabilitadasByCodigoReparticion(
      String codigoReparticion);

  /**
   * Eliminar reparticion habilitada.
   *
   * @param reparticionHabilitada
   *          the reparticion habilitada
   */
  void eliminarReparticionHabilitada(UsuarioReparticionHabilitadaDTO reparticionHabilitada);

  /**
   * Guardar reparticion habilitada.
   *
   * @param usuarioReparticionHabilitada
   *          the usuario reparticion habilitada
   */
  void guardarReparticionHabilitada(UsuarioReparticionHabilitadaDTO usuarioReparticionHabilitada);

  /**
   * Obtener historico.
   *
   * @param nombreUsuario
   *          the nombre usuario
   * @return the historico
   */
  List<UsuarioReparticionHabilitadaDTO> getHistorico(String nombreUsuario);

}