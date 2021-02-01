package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gedo_documento_usuariosreserva")
public class UsuarioReserva {

	@Id
	@Column(name = "ID_DOCUMENTO")
	private Integer idDocumento;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "RESER_POR_SCRIPT")
	private String reservaScript;

	/**
	 * @return the idDocumento
	 */
	public Integer getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento
	 *            the idDocumento to set
	 */
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the reservaScript
	 */
	public String getReservaScript() {
		return reservaScript;
	}

	/**
	 * @param reservaScript
	 *            the reservaScript to set
	 */
	public void setReservaScript(String reservaScript) {
		this.reservaScript = reservaScript;
	}

}
