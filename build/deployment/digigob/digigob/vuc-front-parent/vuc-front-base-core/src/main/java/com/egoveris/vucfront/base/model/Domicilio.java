package com.egoveris.vucfront.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_DOMICILIO")
public class Domicilio {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ALTURA")
  private String altura;

  @Column(name = "CGPC")
  private String cgpc;

  @Column(name = "COD_POSTAL")
  private String codPostal;

  @Column(name = "COMISARIA")
  private String comisaria;

  @Column(name = "DEPARTAMENTO")
  private String departamento;

  @Column(name = "DEPTO")
  private String depto;

  @Column(name = "DIRECCION")
  private String direccion;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "LOCAL")
  private String local;

  @Column(name = "LOCALIDAD")
  private String localidad;

  @Column(name = "OBSERVACIONES")
  private String observaciones;

  @Column(name = "PISO")
  private String piso;

  @Column(name = "PROVINCIA")
  private String provincia;

  @Column(name = "TELEFONO")
  private String telefono;

  @Column(name = "VERSION")
  private Long version;

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

}