package com.egoveris.ccomplejos.base.model;

import com.egoveris.ccomplejos.base.model.GenericEnum.SsppEnum;

import java.io.Serializable;
import java.util.List;

public class InteropHeaderDTO extends AbstractCComplejoDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  protected String idTransaccion;
  protected String idMensaje;
  protected List<SsppEnum> destinatarios;
  protected String destinatario;

  public String getIdTransaccion() {
    return idTransaccion;
  }

  public void setIdTransaccion(String idTransaccion) {
    this.idTransaccion = idTransaccion;
  }

  public String getIdMensaje() {
    return idMensaje;
  }

  public void setIdMensaje(String idMensaje) {
    this.idMensaje = idMensaje;
  }

  public List<SsppEnum> getDestinatarios() {
    return destinatarios;
  }

  public void setDestinatarios(List<SsppEnum> destinatarios) {
    this.destinatarios = destinatarios;
  }

  public String getDestinatario() {
    return destinatario;
  }

  public void setDestinatario(String destinatario) {
    this.destinatario = destinatario;
  }

}
