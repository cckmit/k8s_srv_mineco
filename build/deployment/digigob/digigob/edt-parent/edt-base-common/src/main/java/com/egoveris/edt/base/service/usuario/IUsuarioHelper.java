package com.egoveris.edt.base.service.usuario;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;

import java.util.List;

/**
 * The Interface IUsuarioHelper.
 */
public interface IUsuarioHelper {

  /**
   * Cachear lista usuarios.
   */
  void cachearListaUsuarios();

  /**
   * Obtener todos usuarios.
   *
   * @return the list
   */
  List<UsuarioReducido> obtenerTodosUsuarios();

  /**
   * Validar usuario asignador.
   *
   * @param codigoUsuario
   *          the codigo usuario
   * @return true, if successful
   * @throws SecurityNegocioException
   *           the security negocio exception
   */
  boolean validarUsuarioAsignador(String codigoUsuario) throws SecurityNegocioException;

  /**
   * Tiene usuario asignador.
   *
   * @param idSectorInterno
   *          the id sector interno
   * @return true, if successful
   * @throws SecurityNegocioException
   *           the security negocio exception
   */
  boolean tieneUsuarioAsignador(Integer idSectorInterno) throws SecurityNegocioException;

  /**
   * Es usuario asignador.
   *
   * @param codigoUsuario
   *          the codigo usuario
   * @return true, if successful
   * @throws SecurityNegocioException
   *           the security negocio exception
   */
  boolean esUsuarioAsignador(String codigoUsuario) throws SecurityNegocioException;

  /**
   * Obtener usuarios asignadores por sector.
   *
   * @param codigoSectorInterno
   *          the codigo sector interno
   * @return the list
   */
  List<String> obtenerUsuariosAsignadoresPorSector(Integer codigoSectorInterno);

}
