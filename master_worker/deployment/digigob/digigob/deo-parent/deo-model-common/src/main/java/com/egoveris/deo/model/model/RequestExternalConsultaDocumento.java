package com.egoveris.deo.model.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Establece los parámetros de entrada requeridos para la consulta de documentos
 * en GEDO. usuarioConsulta: Usuario que realiza la consulta. (Obligatorio).
 * numeroDocumento: Numero SADE asignado al documento cuando fue generado por
 * GEDO. Con el siguiente formato "AA-2012-00006723- -MGEYA".
 * 
 * @author kmarroqu
 *
 */
@XmlRootElement(name = "RequestExternalConsultaDocumento")
public class RequestExternalConsultaDocumento implements Serializable {

  private static final long serialVersionUID = 3475878491362553253L;

  private String usuarioConsulta;
  private String numeroDocumento;
  private String numeroEspecial;
  private boolean assignee;

  public void setUsuarioConsulta(String usuarioConsulta) {
    this.usuarioConsulta = usuarioConsulta;
  }

  public String getUsuarioConsulta() {
    return usuarioConsulta;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public String getNumeroEspecial() {
    return this.numeroEspecial;
  }

  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public boolean getAssignee() {
    return assignee;
  }

  public void setAssignee(boolean assignee) {
    this.assignee = assignee;
  }

}
