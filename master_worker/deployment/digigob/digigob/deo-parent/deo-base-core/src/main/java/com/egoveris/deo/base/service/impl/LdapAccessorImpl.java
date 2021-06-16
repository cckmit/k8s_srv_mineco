package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.service.LdapAccessor;
import com.egoveris.deo.base.util.UtilitariosServicios;

import java.util.List;

import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

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

@Service
public class LdapAccessorImpl implements LdapAccessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(LdapAccessorImpl.class);

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
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUsuarios(String) - start"); //$NON-NLS-1$
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new LikeFilter("uid", "*" + stringNombreUsuario + "*"));
    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - start"); //$NON-NLS-1$
            }
            
             String apeYNom = (String) attributes.get("uid").get();

            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end"); //$NON-NLS-1$
            }
            return apeYNom;
          }
        });

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUsuarios(String) - end"); //$NON-NLS-1$
    }
    return usuarios;
  }

  @SuppressWarnings("unchecked")

  public List<String> buscarUsuarioPorUid(String stringNombreUsuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUsuarioPorUid(String) - start"); //$NON-NLS-1$
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("uid", stringNombreUsuario));
    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {
          @Override
          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            LOGGER.debug("---" + attributes + "---");
            String apeYNom;
            apeYNom = (String) attributes.get("uid").get();

            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end"); //$NON-NLS-1$
            }
            return apeYNom;
          }
        });

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUsuarioPorUid(String) - end"); //$NON-NLS-1$
    }
    return usuarios;
  }

  @SuppressWarnings("rawtypes")
  public boolean isAdministradorCentral(String user) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("isAdministradorCentral(String) - start"); //$NON-NLS-1$
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("sn", user));

    List administrador = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - start"); //$NON-NLS-1$
            }

            Attribute employeeType = attributes.get("employeeType");
            int i = 0;
            while (i < employeeType.size()) {
              if (((String) employeeType.get(i++)).equals(LdapAccessor.ADMINISTRADOR_CENTRAL)) {
                if (LOGGER.isDebugEnabled()) {
                  LOGGER.debug(
                      "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end"); //$NON-NLS-1$
                }
                return Boolean.TRUE;
              }
            }

            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end"); //$NON-NLS-1$
            }
            return Boolean.FALSE;
          }
        });
    boolean returnboolean = (Boolean) administrador.get(0);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("isAdministradorCentral(String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  @SuppressWarnings("unchecked")
  public List<String> buscarUsuarioPorCn(String user) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUsuarioPorCn(String) - start"); //$NON-NLS-1$
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", user));

    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          @Override
          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            LOGGER.debug("---" + attributes + "---");
            String apeYNom;
            apeYNom = (String) attributes.get("uid").get();

            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end"); //$NON-NLS-1$
            }
            return apeYNom;
          }
        });

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUsuarioPorCn(String) - end"); //$NON-NLS-1$
    }
    return usuarios;
  }

  public void cambiarPasswordUsuario(String userId, String newPassword) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cambiarPasswordUsuario(String, String) - start"); //$NON-NLS-1$
    }

    ModificationItem repitem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
        new BasicAttribute("userPassword", UtilitariosServicios.encodePwd("MD5", newPassword)));
    DistinguishedName newPass = new DistinguishedName();
    newPass.add("ou", "sade");
    newPass.add("cn", userId);
    ldapTemplate.modifyAttributes(newPass, new ModificationItem[] { repitem });

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cambiarPasswordUsuario(String, String) - end"); //$NON-NLS-1$
    }
  }

  public boolean login(String username, String password) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("login(String, String) - start"); //$NON-NLS-1$
    }

    AndFilter filter = new AndFilter();
    filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("cn", username));
    boolean returnboolean = ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH,
        filter.toString(), password);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("login(String, String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  @SuppressWarnings("unchecked")
  public String buscarUsuarioLdap(String stringNombreUsuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUsuarioLdap(String) - start"); //$NON-NLS-1$
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", stringNombreUsuario));

    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          @Override
          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            LOGGER.debug("---" + attributes + "---");
            String apeYNom;
            apeYNom = (String) attributes.get("uid").get();

            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end"); //$NON-NLS-1$
            }
            return apeYNom;
          }
        });

    if (usuarios.size() == 1) {
      String returnString = usuarios.get(0);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("buscarUsuarioLdap(String) - end"); //$NON-NLS-1$
      }
      return returnString;
    } else {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("buscarUsuarioLdap(String) - end"); //$NON-NLS-1$
      }
      return null;
    }
  }

  public String getNombreYApellido(String username) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getNombreYApellido(String) - start"); //$NON-NLS-1$
    }

    List<String> ap = this.buscarUsuarioPorCn(username);
    if (ap != null) {
      switch (ap.size()) {
      case 0:
        LOGGER.debug("El usuario " + username + " no existe en LDAP!");
        return null;
      case 1:
        return ap.get(0);
      default:
        throw new RuntimeException("Dos o mas usuarios con el mismo USERNAME. " + username);
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getNombreYApellido(String) - end"); //$NON-NLS-1$
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  public boolean puedeVerDocumentosConfidenciales(String username) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("puedeVerDocumentosConfidenciales(String) - start"); //$NON-NLS-1$
    }

    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("sn", username));

    List administrador = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - start"); //$NON-NLS-1$
            }

            Attribute employeeType = attributes.get("employeeType");
            int i = 0;
            while (i < employeeType.size()) {
              if (((String) employeeType.get(i++))
                  .equals(LdapAccessor.DOCUMENTACION_CONFIDENCIAL)) {
                if (LOGGER.isDebugEnabled()) {
                  LOGGER.debug(
                      "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end"); //$NON-NLS-1$
                }
                return Boolean.TRUE;
              }
            }

            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "$AttributesMapper.mapFromAttributes(javax.naming.directory.Attributes) - end"); //$NON-NLS-1$
            }
            return Boolean.FALSE;
          }
        });
    boolean returnboolean = (Boolean) administrador.get(0);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("puedeVerDocumentosConfidenciales(String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }
}