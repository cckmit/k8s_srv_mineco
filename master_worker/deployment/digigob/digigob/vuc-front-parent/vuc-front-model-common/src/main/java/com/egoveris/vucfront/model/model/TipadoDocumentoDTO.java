package com.egoveris.vucfront.model.model;

import com.egoveris.vucfront.model.util.TipadoDocumentoEnum;

import java.io.Serializable;

public class TipadoDocumentoDTO implements Serializable {

  private static final long serialVersionUID = 7265656541391494503L;

  private Long id;
  private String tipado;

  public TipadoDocumentoDTO(TipadoDocumentoEnum tipadoDocumento) {
    this.id = tipadoDocumento.getId();
  }

  public TipadoDocumentoDTO() {
    // Default constructor needed for Mapper
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTipado() {
    return tipado;
  }

  public void setTipado(String tipado) {
    this.tipado = tipado;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TipadoDocumentoDTO [id=").append(id).append(", tipado=").append(tipado)
        .append("]");
    return builder.toString();
  }

}