package com.egoveris.sharedsecurity.base.model;

public enum TipoUsuarioEnum {

  AC("AC"), AL("AL"), PL("PL");

  private final String codigo;

  TipoUsuarioEnum(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return codigo;
  }

}
