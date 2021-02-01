package com.egoveris.edt.base.model.eu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EU_GRUPO_PERMISOS")
public class GrupoPermisos {

  @Id
  @Column(name = "ID")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_ADMINSADE_PERMISOS")
  private Permiso permiso;

  @Column(name = "GRUPO_USUARIO")
  private String grupoUsuario;

  public Integer getId() {
    return id;
  }

  public Permiso getPermiso() {
    return permiso;
  }

  public void setPermiso(Permiso permisos) {
    this.permiso = permisos;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setGrupoUsuario(String grupoUsuario) {
    this.grupoUsuario = grupoUsuario;
  }

  public String getGrupoUsuario() {
    return grupoUsuario;
  }

}