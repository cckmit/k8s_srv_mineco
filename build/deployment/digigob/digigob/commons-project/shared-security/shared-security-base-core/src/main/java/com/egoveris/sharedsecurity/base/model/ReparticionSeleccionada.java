package com.egoveris.sharedsecurity.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "edt_sade_reparticion_seleccionada")
public class ReparticionSeleccionada {

  @Id
  @Column(name = "ID_REPARTICION_SELECCIONADA")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "ID_REPARTICION", nullable = false)
  private Reparticion reparticion;

  @Column(name = "NOMBRE_USUARIO")
  private String usuario;

  @ManyToOne
  @JoinColumn(name = "ID_SECTOR_INTERNO", nullable = false)
  private Sector sector;

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

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Sector getSector() {
    return sector;
  }

  public void setSector(Sector sector) {
    this.sector = sector;
  }

}