package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_participantessecundarios")
public class VistaParticipanteSecundario extends AbstractViewCComplejoJPA {

	@Column(name = "NOMBRE_CONTACTO")
	protected String nombreContacto;
	
	@Column(name = "EMAIL_CONTACTO")
	protected String emailContacto;
	
	public String getNombreContacto() {
		return nombreContacto;
	}
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	public String getEmailContacto() {
		return emailContacto;
	}
	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

	
	
	
}
