package com.egoveris.ffdd.web.security;

import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getCanonicalName());
	private LdapUserSearch userSearch;
	private final UserDetailsContextMapper userDetailsMapper = new LdapUserDetailsMapper();
	private LdapAuthoritiesPopulator authoritiesPopulator;

	CustomUserDetailsService() {
	}

	@Override
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException, DataAccessException {
		logger.fine("Inicio loadUserByUsername - userName: " + userName);
		final DirContextOperations dirContextOperations = userSearch.searchForUser(userName);
		final Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) authoritiesPopulator
				.getGrantedAuthorities(dirContextOperations, userName);

		final UserDetails userDetails = userDetailsMapper.mapUserFromContext(dirContextOperations, userName,
				grantedAuthorities);
		logger.fine(
				"Fin loadUserByUsername - userName: " + userName + " - Authorities: " + userDetails.getAuthorities());
		return userDetails;
	}

	public void setUserSearch(final LdapUserSearch userSearch) {
		this.userSearch = userSearch;
	}

	public void setAuthoritiesPopulator(final LdapAuthoritiesPopulator authoritiesPopulator) {
		this.authoritiesPopulator = authoritiesPopulator;
	}

}
