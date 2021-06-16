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
@Table(name = "edt_sade_admin_reparticion")
public class AdminReparticion {

  @Id
  @Column(name = "ID_ADMIN_REPARTICION")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Reparticion.class)
  @JoinColumn(name = "ID_REPARTICION")
  private Reparticion reparticion;

  @Column(name = "NOMBRE_USUARIO")
  private String nombreUsuario;

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

}