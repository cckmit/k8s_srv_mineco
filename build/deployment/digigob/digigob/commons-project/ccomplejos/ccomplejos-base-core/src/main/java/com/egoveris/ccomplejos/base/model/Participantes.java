package com.egoveris.ccomplejos.base.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_PARTICIPANTES")
public class Participantes extends AbstractCComplejoJPA {

	@Column(name = "PARTICIPANTE_ID")
	Long participanteId;

	@Column(name = "PARTICIPANTE_TYPE")
	String participanteType;

	@Column(name = "PERSONA_TYPE")
	String personaTypeEnum;

	@Column(name = "DOC_PERSONA_TYPE")
	String docPersonaType;

	@Column(name = "DOC_PERSONA_NUM")
	String docPersonaNum;

	@Column(name = "PARTICIPANTE_NOMBRE")
	String participanteNombre;

	@Column(name = "PARTICIPANTE_APELLIDO")
	String participanteApellido;

	@Column(name = "PARTICIPANTE_PORCENTAJE")
	Double participantePorcentaje;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PARTICIPANTE_ADDRESS")
	Address participanteAddress;

	@Column(name = "PARTICIPANTE_EMAIL")
	String participanteEmail;

	@ManyToOne
	@JoinColumn(name = "ID_ITEM", referencedColumnName = "id")
	ItemJPA item;

	public Long getParticipanteId() {
		return participanteId;
	}

	public void setParticipanteId(Long participanteId) {
		this.participanteId = participanteId;
	}

	public String getParticipanteType() {
		return participanteType;
	}

	public void setParticipanteType(String participanteType) {
		this.participanteType = participanteType;
	}

	public String getPersonaTypeEnum() {
		return personaTypeEnum;
	}

	public void setPersonaTypeEnum(String personaTypeEnum) {
		this.personaTypeEnum = personaTypeEnum;
	}

	public String getDocPersonaType() {
		return docPersonaType;
	}

	public void setDocPersonaType(String docPersonaType) {
		this.docPersonaType = docPersonaType;
	}

	public String getDocPersonaNum() {
		return docPersonaNum;
	}

	public void setDocPersonaNum(String docPersonaNum) {
		this.docPersonaNum = docPersonaNum;
	}

	public String getParticipanteNombre() {
		return participanteNombre;
	}

	public void setParticipanteNombre(String participanteNombre) {
		this.participanteNombre = participanteNombre;
	}

	public String getParticipanteApellido() {
		return participanteApellido;
	}

	public void setParticipanteApellido(String participanteApellido) {
		this.participanteApellido = participanteApellido;
	}

	public Double getParticipantePorcentaje() {
		return participantePorcentaje;
	}

	public void setParticipantePorcentaje(Double participantePorcentaje) {
		this.participantePorcentaje = participantePorcentaje;
	}

	public String getParticipanteEmail() {
		return participanteEmail;
	}

	public void setParticipanteEmail(String participanteEmail) {
		this.participanteEmail = participanteEmail;
	}

	public Address getParticipanteAddress() {
		return participanteAddress;
	}

	public void setParticipanteAddress(Address participanteAddress) {
		this.participanteAddress = participanteAddress;
	}

	/**
	 * @return the item
	 */
	public ItemJPA getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(ItemJPA item) {
		this.item = item;
	}

	// public Set<Transport> getTransportes() {
	// return transportes;
	// }
	//
	// public void setTransportes(Set<Transport> transportes) {
	// this.transportes = transportes;
	// }

}
