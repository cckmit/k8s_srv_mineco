package com.egoveris.ccomplejos.base.model;

public class ParticipanteSecundarioDTO extends AbstractCComplejoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4721792755807119982L;
	
	String participanteID;
	String personaTypeEnum;
	String docPersonaType;
	String docPersonaNum;
	String participanteNombre;
	AddressDTO participanteAddress;
	String participanteEmail;
	String participanteNumeroTelefonoFijo;
	String participanteNumeroTelefonoMovil;
	
	/**
	 * @return the participanteID
	 */
	public String getParticipanteID() {
		return participanteID;
	}
	/**
	 * @param participanteID the participanteID to set
	 */
	public void setParticipanteID(String participanteID) {
		this.participanteID = participanteID;
	}
	/**
	 * @return the personaTypeEnum
	 */
	public String getPersonaTypeEnum() {
		return personaTypeEnum;
	}
	/**
	 * @param personaTypeEnum the personaTypeEnum to set
	 */
	public void setPersonaTypeEnum(String personaTypeEnum) {
		this.personaTypeEnum = personaTypeEnum;
	}
	/**
	 * @return the docPersonaType
	 */
	public String getDocPersonaType() {
		return docPersonaType;
	}
	/**
	 * @param docPersonaType the docPersonaType to set
	 */
	public void setDocPersonaType(String docPersonaType) {
		this.docPersonaType = docPersonaType;
	}
	/**
	 * @return the docPersonaNum
	 */
	public String getDocPersonaNum() {
		return docPersonaNum;
	}
	/**
	 * @param docPersonaNum the docPersonaNum to set
	 */
	public void setDocPersonaNum(String docPersonaNum) {
		this.docPersonaNum = docPersonaNum;
	}
	/**
	 * @return the participanteNombre
	 */
	public String getParticipanteNombre() {
		return participanteNombre;
	}
	/**
	 * @param participanteNombre the participanteNombre to set
	 */
	public void setParticipanteNombre(String participanteNombre) {
		this.participanteNombre = participanteNombre;
	}
	/**
	 * @return the participanteAddress
	 */
	public AddressDTO getParticipanteAddress() {
		return participanteAddress;
	}
	/**
	 * @param participanteAddress the participanteAddress to set
	 */
	public void setParticipanteAddress(AddressDTO participanteAddress) {
		this.participanteAddress = participanteAddress;
	}
	/**
	 * @return the participanteEmail
	 */
	public String getParticipanteEmail() {
		return participanteEmail;
	}
	/**
	 * @param participanteEmail the participanteEmail to set
	 */
	public void setParticipanteEmail(String participanteEmail) {
		this.participanteEmail = participanteEmail;
	}
	/**
	 * @return the participanteNumeroTelefonoFijo
	 */
	public String getParticipanteNumeroTelefonoFijo() {
		return participanteNumeroTelefonoFijo;
	}
	/**
	 * @param participanteNumeroTelefonoFijo the participanteNumeroTelefonoFijo to set
	 */
	public void setParticipanteNumeroTelefonoFijo(String participanteNumeroTelefonoFijo) {
		this.participanteNumeroTelefonoFijo = participanteNumeroTelefonoFijo;
	}
	/**
	 * @return the participanteNumeroTelefonoMovil
	 */
	public String getParticipanteNumeroTelefonoMovil() {
		return participanteNumeroTelefonoMovil;
	}
	/**
	 * @param participanteNumeroTelefonoMovil the participanteNumeroTelefonoMovil to set
	 */
	public void setParticipanteNumeroTelefonoMovil(String participanteNumeroTelefonoMovil) {
		this.participanteNumeroTelefonoMovil = participanteNumeroTelefonoMovil;
	}

}
