package com.egoveris.workflow.designer.module.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	// Inyectados por Spring
	private String rolePrefix;
	private String groupAttr;
	private String groupSimpleAttr;
	private static final String GRUPO_PUBLIC_SIN_MAPEAR = "public";

	private static final Logger logger = Logger.getLogger(CustomLdapAuthoritiesPopulator.class.getCanonicalName());
	private static final String ROLE_PREFIX = "ROLE_";

	@Override
	public Collection<GrantedAuthority> getGrantedAuthorities(final DirContextOperations dirContextOperations,
			final String userName) {

		List<GrantedAuthority> result = null;
		try {
			final String[] attributes = dirContextOperations.getStringAttributes(groupAttr);

			result = new ArrayList<GrantedAuthority>(attributes.length);

			for (final String attribute : attributes) {
				// Si el atributo es "public", no se agrega a la lista...
				if (!attribute.equals(GRUPO_PUBLIC_SIN_MAPEAR)) {
					final String role = this.getSimpleRole(attribute);
					final GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLE_PREFIX + role);
					result.add(grantedAuthority);
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, "Error al calcular los authorities para usuario " + userName, e);
			result = new ArrayList<GrantedAuthority>();
		}

		return result;
	}

	private String getSimpleRole(final String attributoGrupo) {
		// Calcula el nombre simple del rol con varias estrategias:
		// a) Cuando empieza con groupSimpleAttr, y hasta la primer ","
		// encontrada
		// b) Igual que el anterior, pero hasta el final del string (cuando no
		// tiene ",ou=grupos,...")
		// C) Cuando no empieza con groupSimpleAttr, se devuelve tal cual

		String result = null;

		if (attributoGrupo.startsWith(groupSimpleAttr)) {
			final int i = attributoGrupo.indexOf(",");
			if (i >= 0) {
				result = (rolePrefix + attributoGrupo.substring(groupSimpleAttr.length() + 1, i)).toUpperCase();
			} else {
				result = (rolePrefix + attributoGrupo.substring(groupSimpleAttr.length() + 1)).toUpperCase();
			}
		} else {
			result = attributoGrupo.toUpperCase();
		}
		logger.finest(attributoGrupo + " - Rol encontrado: " + result);
		return result;
	}

	public void setRolePrefix(final String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public void setGroupAttr(final String groupAttr) {
		this.groupAttr = groupAttr;
	}

	public void setGroupSimpleAttr(final String groupSimpleAttr) {
		this.groupSimpleAttr = groupSimpleAttr;
	}

}
