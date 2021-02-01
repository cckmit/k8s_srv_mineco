package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;

public class GrupoPermisosDTO implements Serializable {

  private static final long serialVersionUID = -3131667938894028567L;

  private Integer id;
  private PermisoDTO permiso;
  private String grupoUsuario;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PermisoDTO getPermiso() {
    return permiso;
  }

  public void setPermiso(PermisoDTO permiso) {
    this.permiso = permiso;
  }

  public String getGrupoUsuario() {
    return grupoUsuario;
  }

  public void setGrupoUsuario(String grupoUsuario) {
    this.grupoUsuario = grupoUsuario;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("GrupoPermisosDTO [id=").append(id).append(", grupoUsuario=")
        .append(grupoUsuario).append("]");
    return builder.toString();
  }

}