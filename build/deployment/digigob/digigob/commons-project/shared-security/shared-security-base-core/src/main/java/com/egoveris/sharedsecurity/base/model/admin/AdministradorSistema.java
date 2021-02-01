package com.egoveris.sharedsecurity.base.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SADE_ADMINISTRADOR_SISTEMA")
public class AdministradorSistema {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "NOMBRE_USUARIO")
  private String nombreUsuario;

  @Column(name = "SISTEMA")
  private String sistema;

  public AdministradorSistema() {
    //Constructor
  }

  public AdministradorSistema(String nombreUsuario, String sistema) {
    this.nombreUsuario = nombreUsuario;
    this.sistema = sistema;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getSistema() {
    return sistema;
  }

  public void setSistema(String sistema) {
    this.sistema = sistema;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AdministradorSistema other = (AdministradorSistema) obj;
    if (id == null && other.id != null) {
        return false;
    }
    return true;
  }

}
