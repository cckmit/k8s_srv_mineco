package com.egoveris.sharedsecurity.base.repository.impl;


import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.repository.IUsuarioLdapDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

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
import org.springframework.security.crypto.codec.Base64;
//import org.springframework.security.core.codec.Base64;
import org.springframework.stereotype.Repository;

@Repository("usuarioDaoLdap")
public class UsuarioDaoLdap implements IUsuarioLdapDao {

	private static Logger logger = LoggerFactory.getLogger(UsuarioDaoLdap.class);

	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Value("${ldap.ou.base}")
	private String base;
	
	@Value("${ldap.ou}")
	private String ou;

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	@SuppressWarnings("unchecked")
	public List<String> buscarUsuarioPorUid(String stringNombreUsuario) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));
		andFilter.and(new EqualsFilter("uid", stringNombreUsuario));
		List<String> usuarios = ldapTemplate.search(this.base, andFilter.encode(), new AttributesMapper() {
			public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
					throws javax.naming.NamingException {
				logger.debug("---" + attributes + "---");
				String apeYNom = null;
				apeYNom = (String) attributes.get("uid").get();
				return apeYNom;
			}
		});

		return usuarios;
	}

	public boolean isAdministradorCentral(String user) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));
		andFilter.and(new EqualsFilter("sn", user));

		List<?> administrador = ldapTemplate.search(this.base, andFilter.encode(), new AttributesMapper() {

			public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
					throws javax.naming.NamingException {
				Attribute employeeType = attributes.get("employeeType");
				int i = 0;
				// List<String> permisos = new ArrayList<String>();
				while (i < employeeType.size()) {
					if (((String) employeeType.get(i++)).equals(IUsuarioLdapDao.ADMINISTRADOR_CENTRAL)) {
						return Boolean.TRUE;
					}
				}
				return Boolean.FALSE;
			}
		});
		return (Boolean) administrador.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioReducido> buscarUsuarioPorCn(String user) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));
		andFilter.and(new EqualsFilter("cn", user));

		List<UsuarioReducido> usuarios = ldapTemplate.search(this.base, andFilter.encode(), new AttributesMapper() {
			public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
					throws javax.naming.NamingException {
				UsuarioReducido user = new UsuarioReducido();
				user.setUsername((String) attributes.get("cn").get());
				user.setNombreApellido((String) attributes.get("uid").get());
				return user;
			}
		});

		return usuarios;
	}

	/**
	 * Metodo para levantar todos los usuarios de LDAP en una sola consulta
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<UsuarioReducido> buscarTodosUsuariosLdap() {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));

		List<UsuarioReducido> usuarios = ldapTemplate.search(this.base, andFilter.encode(), new AttributesMapper() {
			public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
					throws javax.naming.NamingException {
				UsuarioReducido user = new UsuarioReducido();
				user.setUsername((String) attributes.get("cn").get());
				user.setNombreApellido((String) attributes.get("uid").get());
				return user;
			}
		});

		return usuarios;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Boolean existeUsuario(String username) {

		Boolean existe = false;
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));
		andFilter.and(new EqualsFilter("cn", username));

		List<String> usuarios = ldapTemplate.search(this.base, andFilter.encode(), new AttributesMapper() {
			public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
					throws javax.naming.NamingException {
				String username = null;
				username = (String) attributes.get("cn").get();
				return username;
			}
		});
		if (!usuarios.isEmpty()) {
			existe = true;
		}
		return existe;
	}

	public boolean login(String username, String password) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("cn", username));
		return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
	}	

	public boolean puedeVerDocumentosConfidenciales(String username) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));
		andFilter.and(new EqualsFilter("sn", username));

		List<?> administrador = ldapTemplate.search(this.base, andFilter.encode(), new AttributesMapper() {

			public Object mapFromAttributes(javax.naming.directory.Attributes attributes)
					throws javax.naming.NamingException {
				Attribute employeeType = attributes.get("employeeType");
				int i = 0;
				while (i < employeeType.size()) {
					if (((String) employeeType.get(i++)).equals(IUsuarioLdapDao.DOCUMENTACION_CONFIDENCIAL)) {
						return Boolean.TRUE;
					}
				}
				return Boolean.FALSE;
			}
		});
		return (Boolean) administrador.get(0);
	}

	public List<UsuarioReducido> buscarUsuariosPorRol(String rol) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));
		andFilter.and(new LikeFilter("employeeType", "*" + rol + "*"));

		@SuppressWarnings("unchecked")
		List<UsuarioReducido> usuarios = ldapTemplate.search(this.base, andFilter.encode(), new AttributesMapper() {
			public Object mapFromAttributes(Attributes attributes) throws NamingException {
				UsuarioReducido user = new UsuarioReducido();
				String userName = null;
				if (attributes.get("cn") != null) {
					userName = (String) attributes.get("cn").get();
				}
				user.setUsername(userName);
				String apeYNom = null;
				if (attributes.get("uid") != null) {
					apeYNom = (String) attributes.get("uid").get();
				}
				user.setNombreApellido(apeYNom);

				return user;
			}
		});

		return usuarios;
	}

	@Override
	public void cambiarPasswordUsuario(String username, String newPassword) {
		ModificationItem repitem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
				"userPassword", encodePwd("MD5", newPassword)));
		DistinguishedName userForNewPass = new DistinguishedName();
		userForNewPass.add("ou", this.ou);
		userForNewPass.add("cn", username);
		ldapTemplate.modifyAttributes(userForNewPass, new ModificationItem[] { repitem });
	}
	
	@Override
	public void cambiarCN(String username) {
		ModificationItem repitem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
				"cn", username.toUpperCase()));
		DistinguishedName user = new DistinguishedName();
		user.add("ou", this.ou);
		user.add("cn", username);
		ldapTemplate.modifyAttributes(user, new ModificationItem[] { repitem });
	}

	@Override
	public boolean esPasswordUsuarioValido(String base, String filter, String password) {
			return this.ldapTemplate.authenticate(base, filter, password);
	}

	private String encodePwd(String typePwd, String pwd) {

		try {
			byte[] b = (MessageDigest.getInstance(typePwd).digest(pwd.getBytes()));

			return "{" + typePwd + "}" + new String(Base64.encode(b));
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error en mEtodo encodePwd: " + e);
		}
		return null;
	}

}