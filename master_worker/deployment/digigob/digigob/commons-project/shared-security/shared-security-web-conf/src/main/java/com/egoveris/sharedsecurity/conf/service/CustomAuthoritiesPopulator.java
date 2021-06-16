package com.egoveris.sharedsecurity.conf.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

public class CustomAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	// Inyectados por Spring
	private String rolePrefix;
	private String groupAttr;
	private String groupSimpleAttr;
	private static final String GRUPO_PUBLIC_SIN_MAPEAR = "public";

	private static final Logger logger = Logger
			.getLogger(CustomAuthoritiesPopulator.class.getCanonicalName());

	public Collection<GrantedAuthority> getGrantedAuthorities(
			DirContextOperations dirContextOperations, String userName) {

		List<GrantedAuthority> result = null;
		try {
			String[] attributes = dirContextOperations
					.getStringAttributes(groupAttr);

			result = new ArrayList<GrantedAuthority>(attributes.length);

			for (String attribute : attributes) {
				// Si el atributo es "public", no se agrega a la lista...
				if (!attribute.equals(GRUPO_PUBLIC_SIN_MAPEAR)) {
					String role = this.getSimpleRole(attribute);
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
					result.add(grantedAuthority);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"Error al calcular los authorities para usuario "
							+ userName, e);
			result = new ArrayList<GrantedAuthority>();
		}

		return result;
	}

	public String getSimpleRole(String attributoGrupo) {
		// Calcula el nombre simple del rol con varias estrategias:
		// a) Cuando empieza con groupSimpleAttr, y hasta la primer ","
		// encontrada
		// b) Igual que el anterior, pero hasta el final del string (cuando no
		// tiene ",ou=grupos,...")
		// C) Cuando no empieza con groupSimpleAttr, se devuelve tal cual

		String result = null;

		if (attributoGrupo.startsWith(groupSimpleAttr)) {
			int i = attributoGrupo.indexOf(",");
			if (i >= 0) {
				result = (rolePrefix + attributoGrupo.substring(
						groupSimpleAttr.length() + 1, i)).toUpperCase();
			} else {
				result = (rolePrefix + attributoGrupo.substring(groupSimpleAttr
						.length() + 1)).toUpperCase();
			}
		} else {
			result = attributoGrupo.toUpperCase();
		}
		logger.finest(attributoGrupo + " - Rol encontrado: " + result);
		return result;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public void setGroupAttr(String groupAttr) {
		this.groupAttr = groupAttr;
	}

	public void setGroupSimpleAttr(String groupSimpleAttr) {
		this.groupSimpleAttr = groupSimpleAttr;
	}

}
