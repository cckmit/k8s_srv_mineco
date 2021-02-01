package com.egoveris.vucfront.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_GRUPO")
public class Grupo {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "NOMBRE")
  private String nombre;

  @OneToMany(mappedBy = "grupo")
  private List<TipoTramite> tipoTramiteList;

  @Column(name = "VERSION")
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<TipoTramite> getTipoTramiteList() {
    return tipoTramiteList;
  }

  public void setTipoTramiteList(List<TipoTramite> tipoTramiteList) {
    this.tipoTramiteList = tipoTramiteList;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

}