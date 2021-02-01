package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ResumenDeclaracionIngresoDTO extends AbstractCComplejoDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	protected String numeroOp;
	protected String destinacionAduanera;
	protected String tipoOperacion;
	protected String fechaCreacion;
	protected String estadoOp;
	protected String totalGiroUSD;
	
	
	
	
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
