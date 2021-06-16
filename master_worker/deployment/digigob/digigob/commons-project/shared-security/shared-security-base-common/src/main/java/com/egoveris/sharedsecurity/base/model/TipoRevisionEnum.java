package com.egoveris.sharedsecurity.base.model;

public enum TipoRevisionEnum {
  AGREGADO("Agregado"), MODIFICADO("Modificado"), ELIMINADO("Eliminado");

  private String id;

  TipoRevisionEnum(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
