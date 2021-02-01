package com.egoveris.edt.base.model.eu;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EU_ALERTAS_AVISOS")

public class AlertaAviso {

  @Id
  @Column(name = "ID_ALERTA_AVISO")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_ID_MODULO")
  private Aplicacion aplicacion;

  @Column(name = "REFERENCIA")
  private String referencia;

  @Column(name = "MOTIVO")
  private String motivo;

  @Column(name = "NOMBRE_USUARIO")
  private String userName;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "NUMERO_GEDO")
  private String nroGedo;

  @Column(name = "REDIRIGIBLE")
  private Boolean redirigible;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Aplicacion getAplicacion() {
    return aplicacion;
  }

  public void setAplicacion(Aplicacion aplicacion) {
    this.aplicacion = aplicacion;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaDeCreacion) {
    this.fechaCreacion = fechaDeCreacion;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getNroGedo() {
    return nroGedo;
  }

  public void setNroGedo(String nroGedo) {
    this.nroGedo = nroGedo;
  }

  public Boolean getRedirigible() {
    return redirigible;
  }

  public void setRedirigible(Boolean redirigible) {
    this.redirigible = redirigible;
  }

}