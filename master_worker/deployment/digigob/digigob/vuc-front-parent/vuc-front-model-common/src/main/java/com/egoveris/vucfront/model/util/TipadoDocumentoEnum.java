package com.egoveris.vucfront.model.util;

public enum TipadoDocumentoEnum {

  LIBRE(4l), IMPORTADO(5l), TEMPLATE(6l), IMPORTADO_TEMPLATE(7l), LIBRE_EMEMBIDO(8l);

  private final Long id;

  private TipadoDocumentoEnum(final Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

}