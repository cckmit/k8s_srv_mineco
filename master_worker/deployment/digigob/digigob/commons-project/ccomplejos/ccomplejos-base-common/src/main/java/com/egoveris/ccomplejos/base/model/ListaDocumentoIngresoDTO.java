package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ListaDocumentoIngresoDTO extends AbstractCComplejoDTO implements Serializable {

 
  private static final long serialVersionUID = 1L;
	

  protected String idSolicitud;
  protected String nombreSSPP;
  protected String nombreDocumento;
  protected String nombreInstalacionDestino;
  
  
  
  
  
  public String getIdSolicitud() {
    return idSolicitud;
  }
  public void setIdSolicitud(String idSolicitud) {
    this.idSolicitud = idSolicitud;
  }
  public String getNombreSSPP() {
    return nombreSSPP;
  }
  public void setNombreSSPP(String nombreSSPP) {
    this.nombreSSPP = nombreSSPP;
  }
  public String getNombreDocumento() {
    return nombreDocumento;
  }
  public void setNombreDocumento(String nombreDocumento) {
    this.nombreDocumento = nombreDocumento;
  }
  public String getNombreInstalacionDestino() {
    return nombreInstalacionDestino;
  }
  public void setNombreInstalacionDestino(String nombreInstalacionDestino) {
    this.nombreInstalacionDestino = nombreInstalacionDestino;
  }

}
