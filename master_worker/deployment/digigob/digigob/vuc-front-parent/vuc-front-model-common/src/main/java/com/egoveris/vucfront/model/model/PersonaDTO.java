package com.egoveris.vucfront.model.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class PersonaDTO implements Serializable {

  private static final long serialVersionUID = -3450453530527078619L;

  private Long id;
  private String apellido1;
  private String apellido2;
  private String apellido3;
  private String cuit;
  private String documentoIdentidad;
  private String email;
  private String estado;
  private DomicilioDTO domicilioConstituido;
  private DomicilioDTO domicilioReal;
  private TerminosCondicionesDTO terminosCondiciones;
  private String nombre1;
  private String nombre2;
  private String nombre3;
  private String numeroDocumento;
  private String password;
  private String razonSocial;
  private String sexo;
  private String telefonoContacto;
  private String tipoDocumento;
  private String tipoDocumentoIdentidad;
  private Long version;
  private String authId;

  public String getNombreApellido() {
    if (checkDataIsPresent()) {
    	
    	
    	
      return nombre1.concat(" ").concat(apellido1).concat(" ").concat(apellido2);
    }
    
    StringBuilder componerNombre = new StringBuilder();
    componerNombre.append(nombre1)
    .append(" ")
    .append(apellido1);
    
    if(StringUtils.isNotBlank(apellido2)) {
    	componerNombre.append(" ")
    	.append(apellido2);
    }
    
    
    return componerNombre.toString();
  }

  private boolean checkDataIsPresent() {
    if (nombre1 == null || nombre1.isEmpty()) {
      return false;
    }
    if (apellido1 == null || apellido1.isEmpty()) {
      return false;
    }
    if (apellido2 == null || apellido2.isEmpty()) {
      return false;
    }

    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getApellido1() {
    return apellido1;
  }

  public void setApellido1(String apellido1) {
    this.apellido1 = apellido1;
  }

  public String getApellido2() {
    return apellido2;
  }

  public void setApellido2(String apellido2) {
    this.apellido2 = apellido2;
  }

  public String getApellido3() {
    return apellido3;
  }

  public void setApellido3(String apellido3) {
    this.apellido3 = apellido3;
  }

  public String getCuit() {
    return cuit;
  }

  public void setCuit(String cuit) {
    this.cuit = cuit;
  }

  public String getDocumentoIdentidad() {
    return documentoIdentidad;
  }

  public void setDocumentoIdentidad(String documentoIdentidad) {
    this.documentoIdentidad = documentoIdentidad;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public DomicilioDTO getDomicilioConstituido() {
    return domicilioConstituido;
  }

  public void setDomicilioConstituido(DomicilioDTO domicilioConstituido) {
    this.domicilioConstituido = domicilioConstituido;
  }

  public DomicilioDTO getDomicilioReal() {
    return domicilioReal;
  }

  public void setDomicilioReal(DomicilioDTO domicilioReal) {
    this.domicilioReal = domicilioReal;
  }

  public TerminosCondicionesDTO getTerminosCondiciones() {
    return terminosCondiciones;
  }

  public void setTerminosCondiciones(TerminosCondicionesDTO terminosCondiciones) {
    this.terminosCondiciones = terminosCondiciones;
  }

  public String getNombre1() {
    return nombre1;
  }

  public void setNombre1(String nombre1) {
    this.nombre1 = nombre1;
  }

  public String getNombre2() {
    return nombre2;
  }

  public void setNombre2(String nombre2) {
    this.nombre2 = nombre2;
  }

  public String getNombre3() {
    return nombre3;
  }

  public void setNombre3(String nombre3) {
    this.nombre3 = nombre3;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String razonSocial) {
    this.razonSocial = razonSocial;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getTelefonoContacto() {
    return telefonoContacto;
  }

  public void setTelefonoContacto(String telefonoContacto) {
    this.telefonoContacto = telefonoContacto;
  }

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getTipoDocumentoIdentidad() {
    return tipoDocumentoIdentidad;
  }

  public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
    this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PersonaDTO [id=").append(id).append(", apellido1=").append(apellido1)
        .append(", apellido2=").append(apellido2).append(", apellido3=").append(apellido3)
        .append(", cuit=").append(cuit).append(", documentoIdentidad=").append(documentoIdentidad)
        .append(", email=").append(email).append(", estado=").append(estado)
        .append(", domicilioConstituido=").append(domicilioConstituido).append(", domicilioReal=")
        .append(domicilioReal).append(", terminosCondiciones=").append(terminosCondiciones)
        .append(", nombre1=").append(nombre1).append(", nombre2=").append(nombre2)
        .append(", nombre3=").append(nombre3).append(", numeroDocumento=").append(numeroDocumento)
        .append(", password=").append(password).append(", razonSocial=").append(razonSocial)
        .append(", sexo=").append(sexo).append(", telefonoContacto=").append(telefonoContacto)
        .append(", tipoDocumento=").append(tipoDocumento).append(", tipoDocumentoIdentidad=")
        .append(", authId=").append(authId)
        .append(tipoDocumentoIdentidad).append(", version=").append(version).append("]");
    return builder.toString();
  }

public String getAuthId() {
	return authId;
}

public void setAuthId(String authId) {
	this.authId = authId;
}

}