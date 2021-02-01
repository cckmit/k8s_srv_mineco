package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class PlantillaDTO implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 6898341811032193880L;

  private Integer id;
  private String nombre;
  private String descripcion;
  private String contenido;
  private Date fechaModificacion;
  private Set<UsuarioPlantillaDTO> setUsuarioPlantilla;

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

  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public void setSetUsuarioPlantilla(Set<UsuarioPlantillaDTO> setUsuarioPlantilla) {
    this.setUsuarioPlantilla = setUsuarioPlantilla;
  }

  public Set<UsuarioPlantillaDTO> getSetUsuarioPlantilla() {
    return setUsuarioPlantilla;
  }

  public void addUsuarioPlantilla(UsuarioPlantillaDTO usuarioPlantilla) {
    usuarioPlantilla.setPlantilla(this);
    setUsuarioPlantilla.add(usuarioPlantilla);
  }
}
