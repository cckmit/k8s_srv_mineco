package com.egoveris.vucfront.webapp.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade.Request;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakSpringConfigResolverWrapper;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class KeycloakConfig extends KeycloakWebSecurityConfigurerAdapter {
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	    SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
//	    grantedAuthorityMapper.setPrefix("ROLE_");
//
//	    KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//	    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
//	    auth.authenticationProvider(keycloakAuthenticationProvider);
	    auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.csrf().disable()
			.headers().frameOptions().sameOrigin().and()
			.authorizeRequests()
			.antMatchers("/remoting/**").permitAll()
			.antMatchers("/api/**").permitAll()
			.antMatchers("/**").authenticated()
            .and()
            .logout()
            .addLogoutHandler(keycloakLogoutHandler())
            .logoutUrl("/logout").permitAll()
            .logoutSuccessUrl("/");
	}
	
	@Bean
	public KeycloakConfigResolver keycloakConfigResolver() {
		return new KeycloakConfigResolver() {

			@Override
			public KeycloakDeployment resolve(Request facade) {
				
				String realm = System.getProperty("vuc.keycloak.realm", "everis");				
				String authServerUrl = System.getProperty("vuc-keycloak.authServerUrl", "https://docker1-host.egoveris.com:8443/auth");
				String resource = System.getProperty("vuc.keycloak.resource", "vuc-front");
				AdapterConfig cfg = new AdapterConfig();
				cfg.setRealm(realm);
				cfg.setAuthServerUrl(authServerUrl);
				cfg.setResource(resource);
				cfg.setSslRequired("external");
				cfg.setPublicClient(true);
				cfg.setUseResourceRoleMappings(true);
				
				return KeycloakDeploymentBuilder.build(cfg);
			}
			
		};
	}
	     	
}
