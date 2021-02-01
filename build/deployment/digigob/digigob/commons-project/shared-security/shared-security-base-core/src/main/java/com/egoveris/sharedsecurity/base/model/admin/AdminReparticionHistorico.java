package com.egoveris.sharedsecurity.base.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.egoveris.sharedsecurity.base.model.Reparticion;

@Entity
@Table(name = "edt_sade_admin_reparticion_hist")
public class AdminReparticionHistorico {

  @Id
  @Column(name = "ID_ADMIN_REPARTICION")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Reparticion.class)
  @JoinColumn(name = "ID_REPARTICION")
  private Reparticion reparticion;

  @Column(name = "NOMBRE_USUARIO")
  private String nombreUsuario;

  @Column(name = "REVISION")
  private Long fechaRevision;

  @Column(name = "TIPO_REVISION")
  private String tipoRevision;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Reparticion getReparticion() {
    return reparticion;
  }

  public void setReparticion(Reparticion reparticion) {
    this.reparticion = reparticion;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public Long getFechaRevision() {
    return fechaRevision;
  }

  public void setFechaRevision(Long fechaRevision) {
    this.fechaRevision = fechaRevision;
  }

  public String getTipoRevision() {
    return tipoRevision;
  }

  public void setTipoRevision(String tipoRevision) {
    this.tipoRevision = tipoRevision;
  }

}