package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DatosLdap;

@Service
@Transactional
public class LdapAccessorImpl implements LdapAccessor {

  private static transient Logger logger = LoggerFactory.getLogger(LdapAccessorImpl.class);
  @Autowired
  private LdapTemplate ldapTemplate;

  public LdapTemplate getLdapTemplate() {
    return ldapTemplate;
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  @SuppressWarnings("unchecked")
  public List<String> buscarUsuarios(String stringNombreUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarUsuarios(stringNombreUsuario={}) - start", stringNombreUsuario);
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new LikeFilter("uid", "*" + stringNombreUsuario + "*"));
    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new UidLdapAttributesMapper());

    if (logger.isDebugEnabled()) {
      logger.debug("buscarUsuarios(String) - end - return value={}", usuarios);
    }
    return usuarios;
  }

  @SuppressWarnings("unchecked")
  public List<String> buscarUsuarioPorUid(String stringNombreUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarUsuarioPorUid(stringNombreUsuario={}) - start", stringNombreUsuario);
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("uid", stringNombreUsuario));
    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new UidLdapAttributesMapper());

    if (logger.isDebugEnabled()) {
      logger.debug("buscarUsuarioPorUid(String) - end - return value={}", usuarios);
    }
    return usuarios;
  }

  /**
   * Autentica un usuario contra el LDAP.
   */
  public boolean login(String username, String password) {
    if (logger.isDebugEnabled()) {
      logger.debug("login username, password - start");
    }

    AndFilter filter = new AndFilter();
    filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("cn", username));
    boolean returnboolean = ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH,
        filter.toString(), password);
    if (logger.isDebugEnabled()) {
      logger.debug("login(String, String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  /**
   * Obtiene los datos almacenados en el LDAP de una determinada persona que se
   * corresponda al nombre de usuario suministrado. Si no se encuentra ninguna
   * persona que concuerde con ese nombre o mas de una, se devuelve null.
   * 
   */
  @SuppressWarnings("unchecked")
  public String buscarUsuarioLdap(String nombreUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarUsuarioLdap(nombreUsuario={}) - start", nombreUsuario);
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", nombreUsuario));
    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new UidLdapAttributesMapper());
    if (usuarios.size() == 1) {
      String returnString = usuarios.get(0);
      if (logger.isDebugEnabled()) {
        logger.debug("buscarUsuarioLdap(String) - end - return value={}", returnString);
      }
      return returnString;
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug("buscarUsuarioLdap(String) - end - return value={null}");
      }
      return null;
    }
  }

  /**
   * Obtiene los grupos de Ldap en mayuscula
   */
  @SuppressWarnings("unchecked")
  public List<String> buscarPerfilesDeUsuarioLdap(String stringNombreUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarPerfilesDeUsuarioLdap(stringNombreUsuario={}) - start",
          stringNombreUsuario);
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", stringNombreUsuario));

    Iterator it = ldapTemplate
        .search("ou=funcionarios", andFilter.encode(), new GroupTypeLdapAttributesMapper())
        .iterator();
    if (!it.hasNext()) {
      logger.error("El usuario " + stringNombreUsuario + "no tiene roles en el LDAP");
      return null;
    }
    return (List<String>) it.next();
  }

  /**
   * Obtiene los grupos de Ldap en mayuscula y el nombre y apellido
   */
  public DatosLdap buscarDatos(String stringNombreUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDatos(stringNombreUsuario={}) - start", stringNombreUsuario);
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", stringNombreUsuario));

    Iterator it = ldapTemplate
        .search("ou=funcionarios", andFilter.encode(), new DatosLdapAttributesMapper()).iterator();
    if (!it.hasNext()) {
      logger.error("El usuario " + stringNombreUsuario + "no existe en LDAP");
      return new DatosLdap();
    }
    return (DatosLdap) it.next();
  }

  /**
   * Busca el nombre y apellido de un determinado usuario en el LDAP
   */
  public String getNombreYApellido(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("getNombreYApellido(username={}) - start", username);
    }

    List<String> ap = this.buscarUsuarioPorCn(username);
    if (ap != null) {
      switch (ap.size()) {
      case 0:
        logger.debug("El usuario " + username + " no existe en LDAP!");
        return null;
      case 1:
        return ap.get(0);
      default:
        throw new TeRuntimeException("Two or more users with same USERNAME. " + username, null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getNombreYApellido(String) - end - return value={null}");
    }
    return null;
  }

  /**
   * Obtiene todos los atributos almacenados en LDAP de un determinado usuario
   */
  @SuppressWarnings("unchecked")
  public List<String> buscarUsuarioPorCn(String user) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarUsuarioPorCn(user={}) - start", user);
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", user));

    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            if (logger.isDebugEnabled()) {
              logger.debug("$AttributesMapper.mapFromAttributes(attributes={}) - start",
                  attributes);
            }

            String apeYNom;
            apeYNom = (String) attributes.get("uid").get();

            if (logger.isDebugEnabled()) {
              logger.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end - return value={}",
                  apeYNom);
            }
            return apeYNom;
          }
        });

    if (logger.isDebugEnabled()) {
      logger.debug("buscarUsuarioPorCn(String) - end - return value={}", usuarios);
    }
    return usuarios;
  }
}

/**
 * Implementacion del AttributesMapper que utiliza esta clase, originalmente la
 * implementacion era una clase anonima y se paso a inner class con el fin de
 * evaluar su extraccion.
 * 
 * @author rgalloci
 *
 */
class UidLdapAttributesMapper implements AttributesMapper {
  private static Logger logger = LoggerFactory.getLogger(UidLdapAttributesMapper.class);

  public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
      throws javax.naming.NamingException {
    logger.debug("---" + attributes + "---");
    String apeYNom;
    apeYNom = (String) attributes.get("uid").get();

    if (logger.isDebugEnabled()) {
      logger.debug("mapFromAttributes(javax.naming.directory.Attributes) - end - return value={}",
          apeYNom);
    }
    return apeYNom;
  }
}

/**
 * Implementacion del AttributesMapper que utiliza esta clase, originalmente la
 * implementacion era una clase anonima y se paso a inner class con el fin de
 * evaluar su extraccion.
 * 
 * @author rgalloci
 *
 */
class GroupTypeLdapAttributesMapper implements AttributesMapper {
  private static final Logger logger = LoggerFactory
      .getLogger(GroupTypeLdapAttributesMapper.class);

  @SuppressWarnings("rawtypes")
  public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
      throws javax.naming.NamingException {
    if (logger.isDebugEnabled()) {
      logger.debug("mapFromAttributes(attributes={}) - start", attributes);
    }

    List<String> grupos = new ArrayList<>();
    NamingEnumeration namingEnumeration = (NamingEnumeration) attributes.get("employeetype")
        .getAll();
    if (namingEnumeration != null) {
      while (namingEnumeration.hasMoreElements()) {
        String uo = (String) namingEnumeration.next();
        String[] aux = StringUtils.split(uo, ',');
        for (int i = 0; aux.length > i; i++) {
          if (aux.length > 0 && aux[i].contains("ou=")) {
            String perm = aux[i].replace("ou=", "");
            if (!StringUtils.equals("grupos", perm)) {
              grupos.add(perm.toUpperCase().trim());
            }
          }
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("mapFromAttributes(javax.naming.directory.Attributes) - end - return value={}",
          grupos);
    }
    return grupos;
  }
}

class DatosLdapAttributesMapper implements AttributesMapper {
  private static final Logger logger = LoggerFactory.getLogger(DatosLdapAttributesMapper.class);

  @SuppressWarnings("rawtypes")
  public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
      throws javax.naming.NamingException {
    if (logger.isDebugEnabled()) {
      logger.debug("mapFromAttributes(attributes={}) - start", attributes);
    }

    DatosLdap datosLdap = new DatosLdap();
    List<String> grupos = new ArrayList<>();
    NamingEnumeration namingEnumeration = (NamingEnumeration) attributes.get("employeetype")
        .getAll();
    if (namingEnumeration != null) {
      while (namingEnumeration.hasMoreElements()) {
        String uo = (String) namingEnumeration.next();
        String[] aux = StringUtils.split(uo, ',');
        for (int i = 0; aux.length > i; i++) {
          if (aux.length > 0 && aux[i].contains("ou=")) {
            String perm = aux[i].replace("ou=", "");
            if (!StringUtils.equals("grupos", perm)) {
              grupos.add(perm.toUpperCase().trim());
            }
          }
        }
      }
    }

    datosLdap.setRoles(grupos);
    datosLdap.setApeNombre((String) attributes.get("uid").get());

    if (logger.isDebugEnabled()) {
      logger.debug("mapFromAttributes(javax.naming.directory.Attributes) - end - return value={}",
          datosLdap);
    }
    return datosLdap;
  }
}