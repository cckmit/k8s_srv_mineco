package com.egoveris.deo.model.model;

import java.io.Serializable;

public class MetadataResponse implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = -8977797304515373173L;
  private String nombre;
  private boolean obligatoriedad;
  private String tipo;
  private int orden;

  public String toString() {
    return nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public boolean isObligatoriedad() {
    return obligatoriedad;
  }

  public void setObligatoriedad(boolean obligatoriedad) {
    this.obligatoriedad = obligatoriedad;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public int getOrden() {
    return orden;
  }

  public void setOrden(int orden) {
    this.orden = orden;
  }

}
