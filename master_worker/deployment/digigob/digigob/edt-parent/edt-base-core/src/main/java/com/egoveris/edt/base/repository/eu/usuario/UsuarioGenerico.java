package com.egoveris.edt.base.repository.eu.usuario;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UsuarioGenerico {

  private Integer id;
  private Integer aplicacionID;
  private String usuario;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "aplicacionId")
  public Integer getAplicacionID() {
    return aplicacionID;
  }

  public void setAplicacionID(Integer aplicacionID) {
    this.aplicacionID = aplicacionID;
  }

  @Column(name = "usuario")
  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

}