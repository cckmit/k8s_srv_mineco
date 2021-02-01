package com.egoveris.vucfront.model.model;

import java.io.Serializable;

public class DomicilioDTO implements Serializable {

  private static final long serialVersionUID = -5003211926877490705L;

  private Long id;
  private String altura;
  private String cgpc;
  private String codPostal;
  private String comisaria;
  private String departamento;
  private String depto;
  private String direccion;
  private String email;
  private String local;
  private String localidad;
  private String observaciones;
  private String piso;
  private String provincia;
  private String telefono;
  private Long version;

  public String getDireccionAltura() {
    String retorno = null;
    if (direccion != null && altura != null && !direccion.isEmpty() && !altura.isEmpty()) {
      retorno = direccion.concat(" ").concat(altura);
    }
    return retorno;
  }

  public String getPisoDepto() {
    String retorno = null;
    if (piso != null && depto != null && !piso.isEmpty() && !depto.isEmpty()) {
      retorno = piso.concat(" / ").concat(depto);
    }
    return retorno;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAltura() {
    return altura;
  }

  public void setAltura(String altura) {
    this.altura = altura;
  }

  public String getCgpc() {
    return cgpc;
  }

  public void setCgpc(String cgpc) {
    this.cgpc = cgpc;
  }

  public String getCodPostal() {
    return codPostal;
  }

  public void setCodPostal(String codPostal) {
    this.codPostal = codPostal;
  }

  public String getComisaria() {
    return comisaria;
  }

  public void setComisaria(String comisaria) {
    this.comisaria = comisaria;
  }

  public String getDepartamento() {
    return departamento;
  }

  public void setDepartamento(String departamento) {
    this.departamento = departamento;
  }

  public String getDepto() {
    return depto;
  }

  public void setDepto(String depto) {
    this.depto = depto;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLocal() {
    return local;
  }

  public void setLocal(String local) {
    this.local = local;
  }

  public String getLocalidad() {
    return localidad;
  }

  public void setLocalidad(String localidad) {
    this.localidad = localidad;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public String getPiso() {
    return piso;
  }

  public void setPiso(String piso) {
    this.piso = piso;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
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
    builder.append("DomicilioDTO [id=").append(id).append(", altura=").append(altura)
        .append(", cgpc=").append(cgpc).append(", codPostal=").append(codPostal)
        .append(", comisaria=").append(comisaria).append(", departamento=").append(departamento)
        .append(", depto=").append(depto).append(", direccion=").append(direccion)
        .append(", email=").append(email).append(", local=").append(local).append(", localidad=")
        .append(localidad).append(", observaciones=").append(observaciones).append(", piso=")
        .append(piso).append(", provincia=").append(provincia).append(", telefono=")
        .append(telefono).append(", version=").append(version).append("]");
    return builder.toString();
  }

}