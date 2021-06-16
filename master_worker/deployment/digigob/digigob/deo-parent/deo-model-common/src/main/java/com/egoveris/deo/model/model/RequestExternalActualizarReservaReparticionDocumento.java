package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.List;

public class RequestExternalActualizarReservaReparticionDocumento implements Serializable {

  private static final long serialVersionUID = 7531743577524386564L;
  private String usuarioOReparticionConsulta;
  private String numeroDocumento;
  private String reservaDocumento;
  private List<String> reparticionesRectoras;

  public String getUsuarioOReparticionConsulta() {
    return usuarioOReparticionConsulta;
  }

  public void setUsuarioOReparticionConsulta(String usuarioOReparticionConsulta) {
    this.usuarioOReparticionConsulta = usuarioOReparticionConsulta;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public String getReservaDocumento() {
    return reservaDocumento;
  }

  public void setReservaDocumento(String reservaDocumento) {
    this.reservaDocumento = reservaDocumento;
  }

  public List<String> getReparticionesRectoras() {
    return reparticionesRectoras;
  }

  public void setReparticionesRectoras(List<String> reparticionesRectoras) {
    this.reparticionesRectoras = reparticionesRectoras;
  }
}
