package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ListaProductosDiDTO extends AbstractCComplejoDTO implements Serializable {

  private static final long serialVersionUID = 1L;
	
  
  protected String codigoProducto;
  protected String descripcionProducto;
  protected String cantidadTotal;
  protected String cantidadUOM;
  
  
  
  
  public String getCodigoProducto() {
    return codigoProducto;
  }
  public void setCodigoProducto(String codigoProducto) {
    this.codigoProducto = codigoProducto;
  }
  public String getDescripcionProducto() {
    return descripcionProducto;
  }
  public void setDescripcionProducto(String descripcionProducto) {
    this.descripcionProducto = descripcionProducto;
  }
  public String getCantidadTotal() {
    return cantidadTotal;
  }
  public void setCantidadTotal(String cantidadTotal) {
    this.cantidadTotal = cantidadTotal;
  }
  public String getCantidadUOM() {
    return cantidadUOM;
  }
  public void setCantidadUOM(String cantidadUOM) {
    this.cantidadUOM = cantidadUOM;
  }

}
