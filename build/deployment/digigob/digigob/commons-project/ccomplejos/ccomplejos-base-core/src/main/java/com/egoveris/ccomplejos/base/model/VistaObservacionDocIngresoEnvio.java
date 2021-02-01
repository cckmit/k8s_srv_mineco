package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_OBSERVACION_DOC_INGRESO_ENVIO")
public class VistaObservacionDocIngresoEnvio extends AbstractCComplejoJPA {

	
 @Column(name="SECUENCIAL_OBSERVACION")
 protected Integer secuencialObservacion;
 
 @Column(name="CODIGO_OBSERVACION")
 protected String codigoObservacion;
 
 @Column(name="DESCRIPCION_OBSERVACION")
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