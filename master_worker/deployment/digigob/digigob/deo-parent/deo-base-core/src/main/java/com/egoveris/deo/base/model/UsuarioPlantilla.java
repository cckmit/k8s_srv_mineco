package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_USUARIO_PLANTILLA")
public class UsuarioPlantilla {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "usuario")
  private String usuario;

  @ManyToOne
  @JoinColumn(name = "IDPLANTILLA")
  private Plantilla plantilla;

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

  public void setPlantilla(Plantilla plantilla) {
    this.plantilla = plantilla;
  }

  public Plantilla getPlantilla() {
    return plantilla;
  }

}
