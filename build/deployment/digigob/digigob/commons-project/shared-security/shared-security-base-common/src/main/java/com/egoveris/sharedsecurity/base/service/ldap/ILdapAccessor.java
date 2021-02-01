package com.egoveris.sharedsecurity.base.service.ldap;

import java.util.List;

import javax.naming.directory.Attributes;

import com.egoveris.sharedsecurity.base.model.Group;

/**
 * The Interface ILdapAccessor.
 */
public interface ILdapAccessor {

  /** The Constant ADMINISTRADOR_CENTRAL. */
  public static final String ADMINISTRADOR_CENTRAL = "ou=admin.central,ou=grupos,dc=egoveris,dc=com";

  /**
   * Buscar usuarios.
   *
   * @param stringNombreUsuario
   *          the string nombre usuario
   * @return the list
   */
  List<String> buscarUsuarios(String stringNombreUsuario);

  /**
   * Buscar usuario por uid.
   *
   * @param stringNombreUsuario
   *          the string nombre usuario
   * @return the list
   */
  List<String> buscarUsuarioPorUid(String stringNombreUsuario);

  /**
   * Buscar usuario por cn.
   *
   * @param user
   *          the user
   * @return the list
   */
  List<String> buscarUsuarioPorCn(String user);

  /**
   * Cambiar password usuario.
   *
   * @param userWF
   *          the user WF
   * @param newPassword
   *          the new password
   */
  void cambiarPasswordUsuario(String userWF, String newPassword);

  /**
   * Buscar usuario ldap.
   *
   * @param stringNombreUsuario
   *          the string nombre usuario
   * @return the string
   */
  String buscarUsuarioLdap(String stringNombreUsuario);

  /**
   * Login.
   *
   * @param username
   *          the username
   * @param password
   *          the password
   * @return true, if successful
   */
  boolean login(String username, String password);


  /**
   * Gets the nombre Y apellido.
   *
   * @param username
   *          the username
   * @return the nombre Y apellido
   */
  String getNombreYApellido(String username);

  
  List<Group> buscarPerfilesDeUsuarioLdap(String stringNombreUsuario);
  
}
