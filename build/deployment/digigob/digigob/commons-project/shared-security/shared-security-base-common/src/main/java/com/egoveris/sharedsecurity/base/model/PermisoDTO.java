package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.List;

public class PermisoDTO implements Serializable {

  private static final long serialVersionUID = -6060795053322508204L;

  private Integer idPermiso;
  private String permiso;
  private String rol;
  private String descripcion;
  private String sistema;
  private List<GrupoPermisosDTO> grupoPermiso;

  public Integer getIdPermiso() {
    return idPermiso;
  }

  public void setIdPermiso(Integer idPermiso) {
    this.idPermiso = idPermiso;
  }

  public String getPermiso() {
    return permiso;
  }

  public void setPermiso(String permiso) {
    this.permiso = permiso;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getSistema() {
    return sistema;
  }

  public void setSistema(String sistema) {
    this.sistema = sistema;
  }

  public List<GrupoPermisosDTO> getGrupoPermiso() {
    return grupoPermiso;
  }

  public void setGrupoPermiso(List<GrupoPermisosDTO> grupoPermiso) {
    this.grupoPermiso = grupoPermiso;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idPermiso == null) ? 0 : idPermiso.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    
    if (this.getClass() != obj.getClass())
      return false;
    
    PermisoDTO other = (PermisoDTO) obj;
    if (permiso == null) {
      if (other.permiso != null)
        return false;
    } else if (!permiso.equals(other.permiso))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PermisoDTO [idPermiso=").append(idPermiso).append(", permiso=").append(permiso)
        .append(", rol=").append(rol).append(", descripcion=").append(descripcion)
        .append(", sistema=").append(sistema).append(", grupoPermiso=").append(grupoPermiso)
        .append("]");
    return builder.toString();
  }

}