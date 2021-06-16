package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_AUD_TAREA_CAMBIO_SIGLA")
public class TareaCambioDeSigla {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "TAREA")
  private String tarea;

  @Column(name = "TOKEN")
  private String token;

  @Column(name = "CODIGO_REPARTICION_ORIGEN")
  private String codigoReparticionOrigen;

  @Column(name = "CODIGO_REPARTICION_DESTINO")
  private String codigoReparticionDestino;

  @Column(name = "CODIGO_SECTOR_ORIGEN")
  private String codigoSectorOrigen;

  @Column(name = "CODIGO_SECTOR_DESTINO")
  private String codigoSectorDestino;

  @Column(name = "USUARIO_BAJA")
  private String usuarioBaja;

  @Column(name = "FECHA")
  private Date fecha;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTarea() {
    return tarea;
  }

  public void setTarea(String tarea) {
    this.tarea = tarea;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getCodigoReparticionOrigen() {
    return codigoReparticionOrigen;
  }

  public void setCodigoReparticionOrigen(String codigReparticionOrigen) {
    this.codigoReparticionOrigen = codigReparticionOrigen;
  }

  public String getCodigoReparticionDestino() {
    return codigoReparticionDestino;
  }

  public void setCodigoReparticionDestino(String codigoReparticionDestino) {
    this.codigoReparticionDestino = codigoReparticionDestino;
  }

  public String getCodigoSectorOrigen() {
    return codigoSectorOrigen;
  }

  public void setCodigoSectorOrigen(String codigoSectorOrigen) {
    this.codigoSectorOrigen = codigoSectorOrigen;
  }

  public String getCodigoSectorDestino() {
    return codigoSectorDestino;
  }

  public void setCodigoSectorDestino(String codigoSectorDestino) {
    this.codigoSectorDestino = codigoSectorDestino;
  }

  public String getUsuarioBaja() {
    return usuarioBaja;
  }

  public void setUsuarioBaja(String usuarioBaja) {
    this.usuarioBaja = usuarioBaja;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

}
