package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_RESUMEN_DECLARACION_INGRESO_CC")
public class ResumenDeclaracionIngreso extends AbstractCComplejoJPA {

	@Column(name = "NUMERO_OP")
	String numeroOp;

	@Column(name = "DESTINACION_ADUANERA")
	String destinacionAduanera;

	@Column(name = "TIPO_OPERACION")
	String tipoOperacion;
	
 @Column(name = "FECHA_CREACION")
	String fechaCreacion;
 
 @Column(name = "ESTADO_OP")
 String estadoOp;
 
 @Column(name = "TOTAL_GIRO_US")
 String totalGiroUSD;
 
 
 
 

public String getNumeroOp() {
  return numeroOp;
}

public void setNumeroOp(String numeroOp) {
  this.numeroOp = numeroOp;
}

public String getDestinacionAduanera() {
  return destinacionAduanera;
}

public void setDestinacionAduanera(String destinacionAduanera) {
  this.destinacionAduanera = destinacionAduanera;
}

public String getTipoOperacion() {
  return tipoOperacion;
}

public void setTipoOperacion(String tipoOperacion) {
  this.tipoOperacion = tipoOperacion;
}

public String getFechaCreacion() {
  return fechaCreacion;
}

public void setFechaCreacion(String fechaCreacion) {
  this.fechaCreacion = fechaCreacion;
}

public String getEstadoOp() {
  return estadoOp;
}

public void setEstadoOp(String estadoOp) {
  this.estadoOp = estadoOp;
}

public String getTotalGiroUSD() {
  return totalGiroUSD;
}

public void setTotalGiroUSD(String totalGiroUSD) {
  this.totalGiroUSD = totalGiroUSD;
}

}
