package com.egoveris.sharedsecurity.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EDT_CARGOS")
public class Cargo {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "CARGO")
  private String cargoNombre;

  @Column(name = "RESTRINGIDO")
  private boolean esTipoBaja;

  @Column(name = "USUARIO_CREACION")
  private String usuarioCreacion;

  @Column(name = "FECHA_MODIFICACION")
  private Date fechaModificacion;

  @Column(name = "USUARIO_MODIFICACION")
  private String usuarioModificacion;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "VIGENTE")
  private Boolean vigente;

  @Column(name = "ID_REPARTICION")
  private Integer idReparticion;

//  @ManyToMany
//  @JoinTable(name = "EDT_CARGO_ROL", joinColumns = {
//      @JoinColumn(name = "CARGO_ID") }, inverseJoinColumns = { @JoinColumn(name = "EDTRL_ID") })
//  private List<Rol> listaRoles;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCargoNombre() {
    return cargoNombre;
  }

  public void setCargoNombre(String cargoNombre) {
    this.cargoNombre = cargoNombre;
  }

  public boolean isEsTipoBaja() {
    return esTipoBaja;
  }

  public void setEsTipoBaja(boolean esTipoBaja) {
    this.esTipoBaja = esTipoBaja;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Boolean getVigente() {
    return vigente;
  }

  public void setVigente(Boolean vigente) {
    this.vigente = vigente;
  }

  public Integer getIdReparticion() {
    return idReparticion;
  }

  public void setIdReparticion(Integer idReparticion) {
    this.idReparticion = idReparticion;
  }

//  public List<Rol> getListaRoles() {
//    return listaRoles;
//  }
//
//  public void setListaRoles(List<Rol> listaRoles) {
//    this.listaRoles = listaRoles;
//  }

}