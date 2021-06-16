package com.egoveris.sharedsecurity.base.service.impl.ldap;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapGenericService;
import com.egoveris.sharedsecurity.base.util.UsuarioBaseDTOMapper;

@Service
public class LdapGenericServiceImpl implements ILdapGenericService {

  @Autowired
  private LdapTemplate ldapTemplate;

  private static Logger logger = LoggerFactory.getLogger(LdapGenericServiceImpl.class);

  @Override	
  public List<?> obtenerUsuarios(String base, String filter, AttributesMapper mapper) {
    try {
      return this.ldapTemplate.search(base, filter, mapper);
    } catch (Exception ex) {
      logger.error("Error al realizar la busqueda: " + ex.getMessage(), ex);
      throw new SecurityNegocioException("Error al realizar la busqueda", ex);
    }
  }

  @Override
  public void guardarUsuario(Name dn, Object obj, Attributes attributes) {
    this.ldapTemplate.bind(dn, obj, attributes);
  }

  @Override
  public void borrarUsuario(Name dn) {
    try {
      this.ldapTemplate.unbind(dn);
    } catch (Exception ex) {
      logger.error("Error al borrar el usuario: " + ex.getMessage(), ex);
      throw new SecurityNegocioException("Error al borrar el usuario", ex);
    }
  }

  @Override
  public void modificarUsuario(Name dn, ModificationItem[] attributes) {
    try {
      this.ldapTemplate.modifyAttributes(dn, attributes);
    } catch (Exception ex) {
      logger.error("Error al modificar el usuario: " + ex.getMessage(), ex);
      throw new SecurityNegocioException("Error al modificar el usuario", ex);
    }
  }

  @Override
  public boolean verificarPassword(String base, String filter, String password) {
    try {
      return this.ldapTemplate.authenticate(base, filter, password);
    } catch (Exception ex) {
      throw new SecurityNegocioException("Error al autenticar el usuario", ex);
    }
  }

	@SuppressWarnings("unchecked")
	@Override
	public boolean existeUsuario(Name dn) {
		Object rValue = null;

		try {
			rValue = this.ldapTemplate.search(dn, "(objectclass=person)", new UsuarioBaseDTOMapper());
		} catch (NameNotFoundException e) {
			// Si el usuario no existe, tira NameNotFoundException
			rValue = null;
		} catch (Exception ex) {
			logger.info("Error al autenticar el usuario: " + ex.getMessage(), ex);
			throw new SecurityNegocioException("Error al autenticar el usuario", ex);
		}

		return rValue != null;
	}

}