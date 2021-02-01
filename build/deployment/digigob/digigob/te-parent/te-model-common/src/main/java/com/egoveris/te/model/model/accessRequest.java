package com.egoveris.te.model.model;


import java.io.Serializable;
import java.util.List;

/**
 * La presente clase da forma a los datos que se retornan al logearse en la app.
 * 
 * @author everis
 */
public class accessRequest implements Serializable {
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
	private String cargo;
	
	/**
	 * Lista de organamismos asocidos al usuario.
	 */
	private List<userOrganismosDTO> userOrganismo;
	
	/**
	 * Status del acceso del usuario.
	 */
	private Status status;

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
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the userOrganismo
	 */
	public List<userOrganismosDTO> getUserOrganismo() {
		return userOrganismo;
	}

	/**
	 * @param userOrganismo the userOrganismo to set
	 */
	public void setUserOrganismo(List<userOrganismosDTO> userOrganismo) {
		this.userOrganismo = userOrganismo;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	

}
