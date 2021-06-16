package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cc_interop_header")
public class InteropHeader extends AbstractCComplejoJPA {

  @Column(name = "ID_TRANSACCION")
  String idTransaccion;

  @Column(name = "ID_MENSAJE")
  String idMensaje;

  @Column(name = "DESTINATARIO")
  String destinatario;

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

  public String getDestinatario() {
    return destinatario;
  }

  public void setDestinatario(String destinatario) {
    this.destinatario = destinatario;
  }

}
