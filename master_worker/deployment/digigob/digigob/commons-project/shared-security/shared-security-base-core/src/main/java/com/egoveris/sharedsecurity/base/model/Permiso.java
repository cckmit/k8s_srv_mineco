package com.egoveris.sharedsecurity.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ADMINSADE_PERMISOS")
public class Permiso {

  private Integer idPermiso;
  private String permiso;
  private String rol;
  private String descripcion;
  private String sistema;
//  private List<GrupoPermisos> grupoPermiso;

  @Id
  @Column(name = "id")
  public Integer getIdPermiso() {
    return idPermiso;
  }

  public void setIdPermiso(Integer id) {
    this.idPermiso = id;
  }

  @Column(name = "permiso")
  public String getPermiso() {
    return permiso;
  }

  public void setPermiso(String permiso) {
    this.permiso = permiso;
  }

  @Column(name = "rol")
  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  @Column(name = "descripcion")
  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Column(name = "sistema")
  public String getSistema() {
    return sistema;
  }

  public void setSistema(String sistema) {
    this.sistema = sistema;
  }

//  public void setGrupoPermiso(List<GrupoPermisos> grupoPermiso) {
//    this.grupoPermiso = grupoPermiso;
//  }
//
//  @OneToMany(mappedBy = "permiso", targetEntity = GrupoPermisos.class, fetch = FetchType.LAZY)
//  public List<GrupoPermisos> getGrupoPermiso() {
//    return grupoPermiso;
//  }

}