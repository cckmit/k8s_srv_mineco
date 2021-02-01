package com.egoveris.edt.base.util;

public enum EstadoNovedadEnum {
  PENDIENTE("Pendiente"), ACTIVO("Activo"), FINALIZADO("Finalizado"), ELIMINADO("Eliminado");

  private String id;

  EstadoNovedadEnum(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
