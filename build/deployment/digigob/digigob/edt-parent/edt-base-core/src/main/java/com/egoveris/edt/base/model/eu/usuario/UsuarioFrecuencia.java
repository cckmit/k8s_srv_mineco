package com.egoveris.edt.base.model.eu.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EU_USUARIO_FRECUENCIAS")
public class UsuarioFrecuencia {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "usuario")
  private String usuario;

  @Column(name = "frecuenciaMayor", nullable = false)
  private int frecuenciaMayor;

  @Column(name = "frecuenciaMedia", nullable = false)
  private int frecuenciaMedia;

  @Column(name = "frecuenciaMenor", nullable = false)
  private int frecuenciaMenor;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public int getFrecuenciaMayor() {
    return frecuenciaMayor;
  }

  public void setFrecuenciaMayor(int frecuenciaMayor) {
    this.frecuenciaMayor = frecuenciaMayor;
  }

  public int getFrecuenciaMedia() {
    return frecuenciaMedia;
  }

  public void setFrecuenciaMedia(int frecuenciaMedia) {
    this.frecuenciaMedia = frecuenciaMedia;
  }

  public int getFrecuenciaMenor() {
    return frecuenciaMenor;
  }

  public void setFrecuenciaMenor(int frecuenciaMenor) {
    this.frecuenciaMenor = frecuenciaMenor;
  }

}
