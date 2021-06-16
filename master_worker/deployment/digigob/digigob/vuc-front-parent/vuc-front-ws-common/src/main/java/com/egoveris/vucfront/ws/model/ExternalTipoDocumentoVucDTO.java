package com.egoveris.vucfront.ws.model;

import java.io.Serializable;

public class ExternalTipoDocumentoVucDTO implements Serializable {

  private static final long serialVersionUID = 4955086867142578674L;

  private String nombre;
  private String descripcion;
  private String acronimoTad;
  private String acronimoGedo;

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

  public String getAcronimoTad() {
    return acronimoTad;
  }

  public void setAcronimoTad(String acronimoTad) {
    this.acronimoTad = acronimoTad;
  }

  public String getAcronimoGedo() {
    return acronimoGedo;
  }

  public void setAcronimoGedo(String acronimoGedo) {
    this.acronimoGedo = acronimoGedo;
  }

}