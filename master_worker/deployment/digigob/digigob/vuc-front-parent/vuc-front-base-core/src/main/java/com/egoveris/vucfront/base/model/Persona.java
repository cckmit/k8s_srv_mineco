package com.egoveris.vucfront.base.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_PERSONA")
public class Persona {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "APELLIDO1")
  private String apellido1;

  @Column(name = "APELLIDO2")
  private String apellido2;

  @Column(name = "APELLIDO3")
  private String apellido3;

  @Column(name = "CUIT")
  private String cuit;

  @Column(name = "DOCUMENTO_IDENTIDAD")
  private String documentoIdentidad;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "ESTADO")
  private String estado;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ID_DOMICILIO_CONSTITUIDO")
  private Domicilio domicilioConstituido;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ID_DOMICILIO_REAL")
  private Domicilio domicilioReal;

  @ManyToOne
  @JoinColumn(name = "ID_TERMINOS")
  private TerminosCondiciones terminosCondiciones;

  @Column(name = "NOMBRE1")
  private String nombre1;

  @Column(name = "NOMBRE2")
  private String nombre2;

  @Column(name = "NOMBRE3")
  private String nombre3;

  @Column(name = "NUMERO_DOCUMENTO")
  private String numeroDocumento;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "RAZON_SOCIAL")
  private String razonSocial;

  @Column(name = "SEXO")
  private String sexo;

  @Column(name = "TELEFONO_CONTACTO")
  private String telefonoContacto;

  @Column(name = "TIPO_DOCUMENTO")
  private String tipoDocumento;

  @Column(name = "tipo_documento_identidad")
  private String tipoDocumentoIdentidad;

  @Column(name = "VERSION")
  private Long version;

  @Column(name = "AUTH_ID")
  private String authId;
  
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

  public Domicilio getDomicilioConstituido() {
    return domicilioConstituido;
  }

  public void setDomicilioConstituido(Domicilio domicilioConstituido) {
    this.domicilioConstituido = domicilioConstituido;
  }

  public Domicilio getDomicilioReal() {
    return domicilioReal;
  }

  public void setDomicilioReal(Domicilio domicilioReal) {
    this.domicilioReal = domicilioReal;
  }

  public TerminosCondiciones getTerminosCondiciones() {
    return terminosCondiciones;
  }

  public void setTerminosCondiciones(TerminosCondiciones terminosCondiciones) {
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

public String getAuthId() {
	return authId;
}

public void setAuthId(String authId) {
	this.authId = authId;
}

}