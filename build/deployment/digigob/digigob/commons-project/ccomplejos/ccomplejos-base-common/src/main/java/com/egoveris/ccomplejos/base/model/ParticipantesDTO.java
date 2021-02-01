package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ParticipantesDTO extends AbstractCComplejoDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  protected Long participanteId;
  protected String participanteType;
  protected String personaTypeEnum;
  protected String docPersonaType;
  protected String docPersonaNum;
  protected String participanteNombre;
  protected String participanteApellido;
  protected Double participantePorcentaje;
  protected AddressDTO participanteAddress;
  protected String participanteEmail;

  public Long getParticipanteId() {
    return participanteId;
  }

  public void setParticipanteId(Long participanteId) {
    this.participanteId = participanteId;
  }

  public String getDocPersonaNum() {
    return docPersonaNum;
  }

  public void setDocPersonaNum(String docPersonaNum) {
    this.docPersonaNum = docPersonaNum;
  }

  public AddressDTO getParticipanteAddress() {
    return participanteAddress;
  }

  public void setParticipanteAddress(AddressDTO participanteAddress) {
    this.participanteAddress = participanteAddress;
  }

  public String getParticipanteEmail() {
    return participanteEmail;
  }

  public void setParticipanteEmail(String participanteEmail) {
    this.participanteEmail = participanteEmail;
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
  
  

}
