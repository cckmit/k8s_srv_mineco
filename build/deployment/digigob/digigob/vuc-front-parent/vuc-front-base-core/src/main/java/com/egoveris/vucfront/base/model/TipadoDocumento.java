package com.egoveris.vucfront.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_TIPADO_DOCUMENTO")
public class TipadoDocumento {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "TIPADO")
  private String tipado;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTipado() {
    return tipado;
  }

  public void setTipado(String tipado) {
    this.tipado = tipado;
  }

}