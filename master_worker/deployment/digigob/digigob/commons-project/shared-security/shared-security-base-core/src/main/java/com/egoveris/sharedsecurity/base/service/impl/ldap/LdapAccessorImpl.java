package com.egoveris.sharedsecurity.base.service.impl.ldap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Group;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapAccessor;

@Service("ldapAccessor")
public class LdapAccessorImpl implements ILdapAccessor {

  private static Logger logger = LoggerFactory.getLogger(LdapAccessorImpl.class);

  @Autowired
  private LdapTemplate ldapTemplate;
  

  public LdapTemplate getLdapTemplate() {
    return ldapTemplate;
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<String> buscarUsuarios(String stringNombreUsuario) {
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new LikeFilter("uid", "*" + stringNombreUsuario + "*"));
    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          @Override
          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
             String apeYNom = (String) attributes.get("uid").get();
            return apeYNom;
          }
        });

    return usuarios;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<String> buscarUsuarioPorUid(String stringNombreUsuario) {
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("uid", stringNombreUsuario));
    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          @Override
          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            logger.debug("--- {} ---", attributes);
            String apeYNom = (String) attributes.get("uid").get();
            return apeYNom;
          }
        });

    return usuarios;
  }


  @SuppressWarnings("unchecked")
  @Override
  public List<String> buscarUsuarioPorCn(String user) {
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", user));
    logger.debug("buscarUsuarioPorCn - user: " + user + " : " + andFilter.encode());
    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {
          @Override
          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            logger.debug("--- {} ---", attributes);
            String apeYNom = (String) attributes.get("uid").get();
            return apeYNom;
          }
        });

    return usuarios;
  }


  @Override
  public void cambiarPasswordUsuario(String userId, String newPassword) {
    ModificationItem repitem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
        new BasicAttribute("userPassword", encodePwd("MD5", newPassword)));
    DistinguishedName newPass = new DistinguishedName();
    newPass.add("ou", "sade");
    newPass.add("cn", userId);
    ldapTemplate.modifyAttributes(newPass, new ModificationItem[] { repitem });

  }
  
  private  String encodePwd(String typePwd, String pwd) {
    try {
      byte[] b = MessageDigest.getInstance(typePwd).digest(pwd.getBytes());
      return "{" + typePwd + "}" + new String(Base64.encodeBase64(b));
    } catch (NoSuchAlgorithmException ex) {
      logger.error(ex.getMessage(), ex);
    }
    return null;
  }

  @Override
  public boolean login(String username, String password) {
    AndFilter filter = new AndFilter();
    filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("cn", username));
    return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
  }

  @SuppressWarnings("unchecked")
  @Override
  public String buscarUsuarioLdap(String stringNombreUsuario) {
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", stringNombreUsuario));

    List<String> usuarios = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new AttributesMapper() {

          @Override
          public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
              throws javax.naming.NamingException {
            logger.debug("--- {} ---", attributes);
            return attributes.get("uid").get();
          }
        });

    if (usuarios.size() == 1) {
      return usuarios.get(0);
    } else {
      return null;
    }
  }


  @Override
  public String getNombreYApellido(String username) {
    List<String> ap = this.buscarUsuarioPorCn(username);
    if (ap != null) {
      switch (ap.size()) {
      case 0:
        logger.error("getNombreYApellido - El usuario {} no existe en LDAP!", username);
        return null;
      case 1:
        logger.debug("{} - nombre y apellido: {}", username, ap.get(0));
        return ap.get(0);
      default:
          throw new SecurityNegocioException(
              "Dos usuarios o mas tienen el mismo NombreUsuario. " + username);
      }
    }
    return null;
  }

  /**
   * Obtiene los grupos de Ldap
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Group> buscarPerfilesDeUsuarioLdap(String stringNombreUsuario) {
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("objectclass", "person"));
    andFilter.and(new EqualsFilter("cn", stringNombreUsuario));
    List<Group> grupos = ldapTemplate.search("ou=funcionarios", andFilter.encode(),
        new GroupTypeLdapAttributesMapper());
    return (List<Group>) grupos.iterator().next();
  }

}

@SuppressWarnings("rawtypes")
class GroupTypeLdapAttributesMapper implements AttributesMapper {

  @Override
  public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
      throws javax.naming.NamingException {
    List<Group> grupos = new ArrayList<>();
    NamingEnumeration namingEnumeration = attributes.get("employeetype").getAll();
    if (namingEnumeration != null) {
      return obtenerAtributosGrupo(namingEnumeration);
    }
    return grupos;
  }

  private List<Group> obtenerAtributosGrupo(NamingEnumeration namingEnumeration)
      throws NamingException {

    List<Group> grupos = new ArrayList<>();

    while (namingEnumeration.hasMoreElements()) {
      String uo = (String) namingEnumeration.next();
      String[] aux = StringUtils.split(uo, ',');
      for (int i = 0; aux.length > i; i++) {
        if (aux.length > 0 && aux[i].contains("ou=")) {
          String perm = aux[i].replace("ou=", "");

          if (validarGrupos(perm) != null) {
            grupos.add(validarGrupos(perm));
          }
        }
      }
    }

    return grupos;
  }

  private Group validarGrupos(String cadena) {
    if (!StringUtils.equals("grupos", cadena)) {
      Group grupo = new Group();
      grupo.setId(cadena);
      grupo.setName(cadena);
      return grupo;
    }
    return null;
  }
}