package com.egoveris.vucfront.base.mbeans;

import com.egoveris.vucfront.base.service.UserService;
import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.base.util.MessageType;
import com.egoveris.vucfront.model.model.PersonaDTO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.context.SecurityContextImpl;

@ManagedBean
@SessionScoped
public class LoginMb extends AbstractMb {

  private static final long serialVersionUID = -6647179696294826606L;

  @ManagedProperty("#{userServiceImpl}")
  private UserService userService;

  private PersonaDTO persona;

  private String dni;
  private String password;

  @PostConstruct
  public void init() {
      redirect(ConstantsUrl.INDEX);
  }
  
  public void cmdLogin() {
    if (validateInputs()) {
      persona = userService.getPersonaByCuit(dni);
      if (persona != null && password.equals(persona.getPassword())) {
        redirect(ConstantsUrl.INDEX);
      } else {
        addMessageById("login:loginError", "Datos incorrectos.", MessageType.ERROR);
      }
    } else {
      addMessageById("login:loginError", "Datos sin completar.", MessageType.ERROR);
    }
  }

  private boolean validateInputs() {
    if (dni != null && !dni.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
      return true;
    }
    return false;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public PersonaDTO getPersona() {
		if (persona == null) {
			SecurityContextImpl securityContext = (SecurityContextImpl) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("SPRING_SECURITY_CONTEXT");
			String cuit = ((KeycloakPrincipal) securityContext.getAuthentication().getPrincipal())
					.getKeycloakSecurityContext().getIdToken().getPreferredUsername();
			this.persona = this.userService.getPersonaByCuit(cuit);
		}
	    return persona;
  }
  
  public boolean isPersonaActiva() {
		SecurityContextImpl securityContext = (SecurityContextImpl) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("SPRING_SECURITY_CONTEXT");
		String cuit = ((KeycloakPrincipal) securityContext.getAuthentication().getPrincipal())
				.getKeycloakSecurityContext().getIdToken().getPreferredUsername();
		PersonaDTO persona = this.userService.getPersonaByCuit(cuit);
		return persona.getEstado().equals("ACTIVO");
  }
  
  public void setPersona(String cuit) {
	  this.persona = this.userService.getPersonaByCuit(cuit);
  }
  
  public Boolean isIdentidadExternal() {
	  return this.userService.isIdentidadExterna();
  }

}