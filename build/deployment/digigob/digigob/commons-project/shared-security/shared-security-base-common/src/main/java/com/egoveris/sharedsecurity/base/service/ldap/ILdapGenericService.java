package com.egoveris.sharedsecurity.base.service.ldap;


import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;

import org.springframework.ldap.core.AttributesMapper;

public interface ILdapGenericService {

  /**
   * Devuelve el/los usuario/s con los datos completos que coinciden con el
   * criterio ingresado.
   *
   * @param base the base
   * @param filter the filter
   * @param mapper the mapper
   * @return List<?>
   * @throws NegocioException the negocio exception
   */
  List<?> obtenerUsuarios(String base, String filter, AttributesMapper mapper);

  /**
   * Guarda un usuario en LDAP.
   *
   * @param dn the dn
   * @param obj the obj
   * @param attributes the attributes
   * @throws NegocioException the negocio exception
   */
  void guardarUsuario(Name dn, Object obj, Attributes attributes);

  /**
   * Borra un usuario de LDAP.
   *
   * @param dn the dn
   * @throws NegocioException the negocio exception
   */
  void borrarUsuario(Name dn);

  /**
   * Modifica una entrada de LDAP en base al DN pasado.
   *
   * @param dn the dn
   * @param attributes the attributes
   * @throws NegocioException the negocio exception
   */
  void modificarUsuario(Name dn, ModificationItem[] attributes);

  /**
   * Verifica que la password enviada sea la correspondiente al usuario enviado.
   *
   * @param base the base
   * @param filter the filter
   * @param password the password
   * @return true si coincide la password, false si no coincide
   */
  boolean verificarPassword(String base, String filter, String password);

  boolean existeUsuario(Name dn);
}
