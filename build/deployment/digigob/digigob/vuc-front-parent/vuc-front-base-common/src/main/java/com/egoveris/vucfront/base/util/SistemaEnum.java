package com.egoveris.vucfront.base.util;

public enum SistemaEnum {
  VUC("VUC");

  private final String nombre;

  private SistemaEnum(final String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

}