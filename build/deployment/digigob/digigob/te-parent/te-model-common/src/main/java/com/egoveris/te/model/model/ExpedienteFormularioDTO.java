package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Documento que conforma la relacion entre un expediente y un formulario.
 * 
 * @author everis
 *
 */
public class ExpedienteFormularioDTO implements Serializable{
	private static final long serialVersionUID = -1019229952034246571L;
	

	private Integer idDfTransaction;

	private Long idEeExpedient;
	
	private Integer idDefForm;
	
	private Date dateCration;
	
	private String userCreation;
	
	private String formName;
	
	private String observation;
	
	private String organism;

	private Integer isDefinitive;

	/**
	 * 
	 * @return
	 */
	public Integer getIsDefinitive() {
		return isDefinitive;
	}
	/**
	 * 
	 * @param isDefinitive
	 */
	public void setIsDefinitive(Integer isDefinitive) {
		this.isDefinitive = isDefinitive;
	}
	/**
	 * @return the idDfTransaction
	 */
	public Integer getIdDfTransaction() {
		return idDfTransaction;
	}
	/**
	 * @param idDfTransaction the idDfTransaction to set
	 */
	public void setIdDfTransaction(Integer idDfTransaction) {
		this.idDfTransaction = idDfTransaction;
	}
	/**
	 * @return the idEeExpedient
	 */
	public Long getIdEeExpedient() {
		return idEeExpedient;
	}
	/**
	 * @param idEeExpedient the idEeExpedient to set
	 */
	public void setIdEeExpedient(Long idEeExpedient) {
		this.idEeExpedient = idEeExpedient;
	}
	/**
	 * @return the idDefForm
	 */
	public Integer getIdDefForm() {
		return idDefForm;
	}
	/**
	 * @param idDefForm the idDefForm to set
	 */
	public void setIdDefForm(Integer idDefForm) {
		this.idDefForm = idDefForm;
	}
	/**
	 * @return the dateCration
	 */
	public Date getDateCration() {
		return dateCration;
	}
	/**
	 * @param dateCration the dateCration to set
	 */
	public void setDateCration(Date dateCration) {
		this.dateCration = dateCration;
	}
	/**
	 * @return the userCreation
	 */
	public String getUserCreation() {
		return userCreation;
	}
	/**
	 * @param userCreation the userCreation to set
	 */
	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}
	/**
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}
	/**
	 * @param formName the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}
	/**
	 * @return the organism
	 */
	public String getOrganism() {
		return organism;
	}
	/**
	 * @param reparticion the organism to set
	 */
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	/**
	 * @return the observation
	 */
	public String getObservation() {
		return observation;
	}
	/**
	 * @param observation the observation to set
	 */
	public void setObservation(String observation) {
		this.observation = observation;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCration == null) ? 0 : dateCration.hashCode());
		result = prime * result + ((formName == null) ? 0 : formName.hashCode());
		result = prime * result + ((idDefForm == null) ? 0 : idDefForm.hashCode());
		result = prime * result + ((idDfTransaction == null) ? 0 : idDfTransaction.hashCode());
		result = prime * result + ((idEeExpedient == null) ? 0 : idEeExpedient.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((userCreation == null) ? 0 : userCreation.hashCode());
		result = prime * result + ((organism == null) ? 0 : organism.hashCode());
		result = prime * result + ((isDefinitive== null) ? 0 : isDefinitive.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpedienteFormularioDTO other = (ExpedienteFormularioDTO) obj;
		if (dateCration == null) {
			if (other.dateCration != null)
				return false;
		} else if (!dateCration.equals(other.dateCration))
			return false;
		if (formName == null) {
			if (other.formName != null)
				return false;
		} else if (!formName.equals(other.formName))
			return false;
		if (idDefForm == null) {
			if (other.idDefForm != null)
				return false;
		} else if (!idDefForm.equals(other.idDefForm))
			return false;
		if (idDfTransaction == null) {
			if (other.idDfTransaction != null)
				return false;
		} else if (!idDfTransaction.equals(other.idDfTransaction))
			return false;
		if (idEeExpedient == null) {
			if (other.idEeExpedient != null)
				return false;
		} else if (!idEeExpedient.equals(other.idEeExpedient))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (userCreation == null) {
			if (other.userCreation != null)
				return false;
		} else if (!userCreation.equals(other.userCreation))
			return false;
		if (organism == null) {
			if (other.organism != null)
				return false;
		} else if (!organism.equals(other.organism))
			return false;
		if (isDefinitive == null) {
			if (other.isDefinitive != null)
				return false;
		} else if (!isDefinitive.equals(other.isDefinitive))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExpedienteFormulario [idDfTransaction=");
		builder.append(idDfTransaction);
		builder.append(", idEeExpedient=");
		builder.append(idEeExpedient);
		builder.append(", idDefForm=");
		builder.append(idDefForm);
		builder.append(", dateCration=");
		builder.append(dateCration);
		builder.append(", userCreation=");
		builder.append(userCreation);
		builder.append(", formName=");
		builder.append(formName);
		builder.append(", observation=");
		builder.append(observation);
		builder.append(", organism=");
		builder.append(organism);
		builder.append(", isDefinitive=");
		builder.append(isDefinitive);
		builder.append("]");
		return builder.toString();
	}

}
