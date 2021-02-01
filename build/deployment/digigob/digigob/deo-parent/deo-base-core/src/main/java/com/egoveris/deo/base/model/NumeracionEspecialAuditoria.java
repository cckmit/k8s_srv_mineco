package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_NUMERACIONESPECIAL_AUD")
public class NumeracionEspecialAuditoria {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "codigoReparticion")
  private String codigoReparticion;

  @Column(name = "tipoDocumento")
  private Integer idTipoDocumento;

  @Column(name = "anio")
  private String anio;

  @Column(name = "numero")
  private Integer numero;

  @Column(name = "fechaModificacion")
  private Date fechaModificacion;

  @Column(name = "tipoOperacion")
  private String tipoOperacion;

  @Column(name = "userName")
  private String userName;

  public NumeracionEspecialAuditoria() {
  }

  public NumeracionEspecialAuditoria(NumeracionEspecial numeracionEspecial, String usuario) {
    // TODO El id es un campo autoincremental!!!!! Si se requiere copiar el id
    // de la numeracionespecial para reflejarlo en la auditoria, DEBE ser un
    // campo nuevo
    // this.id = numeracionEspecial.getId();
    this.codigoReparticion = numeracionEspecial.getCodigoReparticion();
    this.idTipoDocumento = numeracionEspecial.getIdTipoDocumento();
    this.anio = numeracionEspecial.getAnio();
    this.numero = numeracionEspecial.getNumero();
    this.userName = usuario;
    this.fechaModificacion = new Date();
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getTipoOperacion() {
    return tipoOperacion;
  }

  public void setTipoOperacion(String tipoOperacion) {
    this.tipoOperacion = tipoOperacion;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIdTipoDocumento(Integer idTipoDocumento) {
    this.idTipoDocumento = idTipoDocumento;
  }

  public Integer getIdTipoDocumento() {
    return idTipoDocumento;
  }

}
