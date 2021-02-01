package com.egoveris.deo.base.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TIPODOCUMENTO_FAMILIA")
public class FamiliaTipoDocumento {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @OneToMany(mappedBy = "familia")
  private Set<TipoDocumento> listaTipoDocumento;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Set<TipoDocumento> getListaTipoDocumento() {
    return listaTipoDocumento;
  }

  public void setListaTipoDocumento(Set<TipoDocumento> listaTipoDocumento) {
    this.listaTipoDocumento = listaTipoDocumento;
  }

}
