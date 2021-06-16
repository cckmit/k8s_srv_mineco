package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_LISTA_PRODUCTOS_DI")
public class ListaProductosDI extends AbstractCComplejoJPA {

 @Column(name = "CODIGO_PRODUCTO")
 protected String codigoProducto;
  
 @Column(name = "DESCRIPCION_PRODUCTO")
 protected String descripcionProducto;
  
 @Column(name = "CANTIDAD_TOTAL")
 protected String cantidadTotal;
  
 @Column(name = "CANTIDAD_UOM")
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
