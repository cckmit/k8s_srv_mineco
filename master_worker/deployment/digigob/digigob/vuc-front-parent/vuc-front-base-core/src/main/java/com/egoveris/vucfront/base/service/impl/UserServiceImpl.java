package com.egoveris.vucfront.base.service.impl;

import com.egoveris.vucfront.base.model.Persona;
import com.egoveris.vucfront.base.repository.PersonaRepository;
import com.egoveris.vucfront.base.service.UserService;
import com.egoveris.vucfront.model.model.PersonaDTO;

import javax.swing.Spring;
import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.SpringSecurityTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
  @Autowired
  @Qualifier("vucMapper")
  private Mapper mapper;

  @Autowired
  private PersonaRepository personaRepository;
  
  @Value("${identidad.externa}")
  private Boolean indentidadExterna;
    
  @Override
  public PersonaDTO getPersonaByCuit(String cuit) {
    PersonaDTO retorno = null;
    Persona resultado = personaRepository.findByCuit(cuit);
    if (resultado != null) {
      retorno = mapper.map(resultado, PersonaDTO.class);
    }
    return retorno;
  }

	@Override
	public PersonaDTO save(PersonaDTO persona) {
		Persona personaReg = this.mapper.map(persona, Persona.class);
		personaReg = this.personaRepository.save(personaReg);
		return this.mapper.map(personaReg, PersonaDTO.class);
	}
  
	public Boolean isIdentidadExterna() {
		return this.indentidadExterna;
	}
}
