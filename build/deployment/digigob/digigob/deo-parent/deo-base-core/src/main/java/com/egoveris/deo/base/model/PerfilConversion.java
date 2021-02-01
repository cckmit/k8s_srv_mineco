package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lfishkel
 *
 */
@Entity
@Table(name = "GEDO_PERFILESCONVERSION")
public class PerfilConversion {

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "tipo")
  private String tipo;

  @Column(name = "defaultSetting")
  private boolean defaultSetting;

  @Column(name = "habilitado")
  private boolean habilitado;

  private boolean cambioDeEstado = false;

  public PerfilConversion(String nombre, String tipo) {
    this.nombre = nombre;
    this.tipo = tipo;
  }

  public PerfilConversion() {
  }

  public boolean isDefaultSetting() {
    return defaultSetting;
  }

  public void setDefaultSetting(boolean defaultSetting) {
    this.defaultSetting = defaultSetting;
  }

  public boolean isCambioDeEstado() {
    return cambioDeEstado;
  }

  public void setCambioDeEstado(boolean cambioDeEstado) {
    this.cambioDeEstado = cambioDeEstado;
  }

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

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  @Override
  public String toString() {
    return nombre;
  }

  public boolean isHabilitado() {
    return habilitado;
  }

  public void setHabilitado(boolean habilitado) {
    this.habilitado = habilitado;
  }

  /**
   * Compara con el parametro, si tienen el mismo nombre y son del mismo tipo
   * devuelve 0 sino devuelve 1 (no es case sensitive para el nombre)
   * 
   * @param perfil
   * @return
   */
  public int compareTo(PerfilConversion perfil) {
    if (getNombre().equalsIgnoreCase(perfil.getNombre()) && getTipo().equals(perfil.getTipo())) {
      return 0;
    } else {
      return 1;
    }
  }

}
