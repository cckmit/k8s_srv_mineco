package com.egoveris.ccomplejos.base.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_RESUMEN_OP_DUS")
public class ResumenOpDus extends AbstractCComplejoJPA {


 @Column(name="COD_OPERACION")
 protected String codOperacion;
  
 @Column(name="DESTINACION_ADUANERA")
 protected String destinacionAduanera;
  
 @Column(name="FECHA_CREACION")
 protected Date fechaCreacion;
  
 @Column(name="PROCESSING_STATUS")
 protected String processingStatus;
 
 
 
 
 

public String getCodOperacion() {
  return codOperacion;
}

public void setCodOperacion(String codOperacion) {
  this.codOperacion = codOperacion;
}

public String getDestinacionAduanera() {
  return destinacionAduanera;
}

public void setDestinacionAduanera(String destinacionAduanera) {
  this.destinacionAduanera = destinacionAduanera;
}

public Date getFechaCreacion() {
  return fechaCreacion;
}

public void setFechaCreacion(Date fechaCreacion) {
  this.fechaCreacion = fechaCreacion;
}

public String getProcessingStatus() {
  return processingStatus;
}

public void setProcessingStatus(String processingStatus) {
  this.processingStatus = processingStatus;
}


}