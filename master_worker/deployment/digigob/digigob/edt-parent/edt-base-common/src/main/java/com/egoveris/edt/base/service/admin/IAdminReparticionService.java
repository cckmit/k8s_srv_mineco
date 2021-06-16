package com.egoveris.edt.base.service.admin;

import com.egoveris.sharedsecurity.base.model.AdminReparticionDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

import java.util.List;

/**
 * The Interface IAdminReparticionService.
 */
public interface IAdminReparticionService {

  /**
   * Obtener reparticiones relacionadas según username.
   *
   * @param userAdmin
   *          the user admin
   * @return the list
   */
  List<ReparticionDTO> obtenerReparticionesRelacionadasByUsername(String userAdmin);

  /**
   * Verifica si usuario pertenece a las reparticiones.
   *
   * @param usuario
   *          the usuario
   * @param listaRepartcionesDelUsuario
   *          the lista repartciones del usuario
   * @return true, if successful
   */
  boolean usuarioPerteneceAlasReparticiones(String usuario,
      List<ReparticionDTO> listaRepartcionesDelUsuario);

  /**
   * Obtener reparticiones administradas por nombre de usuario.
   *
   * @param userAdmin
   *          the user admin
   * @return the list
   */
  public List<AdminReparticionDTO> obtenerReparticionesAdministradasByUsername(String userAdmin);


  /**
   * Agregar administrador reparticion.
   *
   * @param adminReparticion
   *          the admin reparticion
   */
  public void agregarAdminReparticion(AdminReparticionDTO adminReparticion);

  /**
   * Eliminar admin reparticion.
   *
   * @param adminReparticionAEliminar
   *          the admin reparticion A eliminar
   */
  public void eliminarAdminReparticion(AdminReparticionDTO adminReparticionAEliminar);

  /**
   * Obtener historico.
   *
   * @param nombreUsuario
   *          the nombre usuario
   * @return the historico
   */
  public List<AdminReparticionDTO> getHistorico(String nombreUsuario);
}
