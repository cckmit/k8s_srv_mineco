package com.egoveris.edt.base.model.eu.cargo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "edt_cargos_hist")
public class CargoHistorico {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "CARGO")
  private String cargo;

  @Column(name = "VISIBLE")
  private Boolean esTipoBaja;

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

  @Column(name = "REVISION")
  private Integer revision;

  @Column(name = "TIPO_REVISION")
  private Integer tipoRevision;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }


  public Boolean getEsTipoBaja() {
    return esTipoBaja;
  }

 
  public void setEsTipoBaja(Boolean esTipoBaja) {
    this.esTipoBaja = esTipoBaja;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
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

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public Integer getRevision() {
    return revision;
  }

  public void setRevision(Integer revision) {
    this.revision = revision;
  }

  public Integer getTipoRevision() {
    return tipoRevision;
  }

  public void setTipoRevision(Integer tipoRevision) {
    this.tipoRevision = tipoRevision;
  }

}