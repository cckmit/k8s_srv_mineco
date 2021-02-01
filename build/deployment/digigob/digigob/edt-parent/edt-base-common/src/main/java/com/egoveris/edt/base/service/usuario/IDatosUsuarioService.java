package com.egoveris.edt.base.service.usuario;

import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;

import java.util.List;

/**
 * The Interface IDatosUsuarioService.
 */
public interface IDatosUsuarioService {

  /**
   * Gets the datos usuario by username.
   *
   * @param userName
   *          the user name
   * @return the datos usuario by username
   */
  DatosUsuarioDTO getDatosUsuarioByUsername(String userName);

  /**
   * Guardar datos usuario.
   *
   * @param datosUsuario
   *          the datos usuario
   */
  void guardarDatosUsuario(DatosUsuarioDTO datosUsuario);

  /**
   * Modificar datos usuario.
   *
   * @param datosUsuario
   *          the datos usuario
   */
  void modificarDatosUsuario(DatosUsuarioDTO datosUsuario);

  /**
   * Eliminar datos usuario.
   *
   * @param datosUsuario
   *          the datos usuario
   */
  void eliminarDatosUsuario(DatosUsuarioDTO datosUsuario);

  /**
   * Update cargo usuario.
   *
   * @param userName
   *          the user name
   * @param cargo
   *          the cargo
   */
  void updateCargoUsuario(String userName, CargoDTO cargo);

  /**
   * Existe cuit.
   *
   * @param cuit
   *          the cuit
   * @param usuario
   *          the usuario
   * @return the boolean
   */
  Boolean existeCuit(String cuit, String usuario);

  /**
   * Existe datos usuario.
   *
   * @param userName
   *          the user name
   * @return the boolean
   */
  Boolean existeDatosUsuario(String userName);

  /**
   * Modificar datos usuario.
   *
   * @param userName
   *          the user name
   * @param nombre
   *          the nombre
   * @param email
   *          the email
   */
  void modificarDatosUsuario(String userName, String nombre, String email);

  /**
   * Persistir ultimo movimiento.
   *
   * @param datosUsuario
   *          the datos usuario
   */
  void persistirUltimoMovimiento(DatosUsuarioDTO datosUsuario);

  /**
   * Actualizarbandera sector mesa.
   *
   * @param idSectorInterno
   *          the id sector interno
   * @param igualReparticion
   *          the igual reparticion
   */
  public void actualizarbanderaSectorMesa(Integer idSectorInterno, Boolean igualReparticion);

  /**
   * Gets the historico.
   *
   * @param datosUsuario
   *          the datos usuario
   * @return the historico
   */
  public List<DatosUsuarioDTO> getHistorico(DatosUsuarioDTO datosUsuario);
  
  /**
   * Gets the datos usuarios by cargo.
   *
   * @param cargo the cargo
   * @return the datos usuarios by cargo
   */
  public List<DatosUsuarioDTO> getDatosUsuariosByCargo(CargoDTO cargo);

	public void guardarDatosUsuarioRol(DatosUsuarioDTO datosUsuario);
  
}
