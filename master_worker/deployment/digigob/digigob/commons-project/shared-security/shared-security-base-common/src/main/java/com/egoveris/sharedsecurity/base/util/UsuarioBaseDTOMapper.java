package com.egoveris.sharedsecurity.base.util;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;

/**
 * Clase que mapea un objeto Attributes a un objeto UsuarioSade.
 */
public class UsuarioBaseDTOMapper implements AttributesMapper {
  @Override
  public Object mapFromAttributes(Attributes att) throws NamingException {
    
    final String employeeType = "employeeType";
    UsuarioBaseDTO user = new UsuarioBaseDTO();

    if (att.get("cn") != null) {
      user.setUid((String) att.get("cn").get());
    } else {
      user.setUid("");
    }
    if (att.get("uid") != null) {
      user.setNombre((String) att.get("uid").get());
    } else {
      user.setNombre("");
    }
    if (att.get("mail") != null) {
      user.setMail((String) att.get("mail").get());
    } else {
      user.setMail("");
    }
    if (att.get("employeeNumber") != null) {
      user.setLegajo((String) att.get("employeeNumber").get());
    } else {
      user.setLegajo("");
    }
    if (att.get(employeeType) != null) {
      List<String> listaPermisos = new ArrayList<String>();
      for (int i = 0; i < att.get(employeeType).size(); i++) {
        listaPermisos.add(att.get(employeeType).get(i).toString());
      }
      user.setPermisos(listaPermisos);
    } else {
      user.setPermisos(null);
    }
    return user;
    
  }
}