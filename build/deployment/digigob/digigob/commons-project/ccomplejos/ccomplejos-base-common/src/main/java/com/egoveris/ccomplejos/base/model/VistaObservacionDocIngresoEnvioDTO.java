
package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaObservacionDocIngresoEnvioDTO extends AbstractCComplejoDTO implements Serializable {

  
  private static final long serialVersionUID = 1L;

  
  protected Integer secuencialObservacion;
  protected String codigoObservacion;
  protected String descripcionObservacion;
  
  
  
  
  public Integer getSecuencialObservacion() {
    return secuencialObservacion;
  }
  public void setSecuencialObservacion(Integer secuencialObservacion) {
    this.secuencialObservacion = secuencialObservacion;
  }
  public String getCodigoObservacion() {
    return codigoObservacion;
  }
  public void setCodigoObservacion(String codigoObservacion) {
    this.codigoObservacion = codigoObservacion;
  }
  public String getDescripcionObservacion() {
    return descripcionObservacion;
  }
  public void setDescripcionObservacion(String descripcionObservacion) {
    this.descripcionObservacion = descripcionObservacion;
  }
	
}

