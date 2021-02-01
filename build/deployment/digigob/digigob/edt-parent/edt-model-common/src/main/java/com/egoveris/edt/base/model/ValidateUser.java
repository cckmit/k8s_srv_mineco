package com.egoveris.edt.base.model;


import java.io.Serializable;
import java.util.List;

/**
 * La presente clase da forma a los datos que se retornan al logearse en la app.
 * 
 * @author everis
 */
public class ValidateUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1358291145144128010L;

	/**
	 * Nombre del usuario logueado, realizando la operación.
	 */
	private String idUser;
	/**
	 * Nombre usuario.
	 */
	private String name;
	/**
	 * LastName del usuario.
	 */
	private String lastName;
	
	/**
	 * Cargo del usuario.
	 */
	private String currentPosition;
	
	/**
	 * Lista de organamismos asocidos al usuario.
	 */
	private List<userOrganismosDTO> position;

	/**
	 * @return the idUser
	 */
	public String getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the currentPosition
	 */
	public String getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * @return the position
	 */
	public List<userOrganismosDTO> getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(List<userOrganismosDTO> position) {
		this.position = position;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
}
