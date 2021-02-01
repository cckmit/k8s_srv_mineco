package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_OTRO_IMPUESTO_DIN")
public class VistaOtroImpuestoDin extends AbstractCComplejoJPA {

  @Column(name = "SECUENCIAL_OTRO_IMPUESTO")
  protected Integer secuencialOtroImpuesto;
  
  @Column(name = "PORCENTAJE")
  protected String porcentaje;
  
  @Column(name = "CODIGO_CUENTA")
  protected String codigoCuenta;
  
  @Column(name = "SIGNO_CUENTA")
  protected String signoCuenta;
  
  @Column(name = "MONTO_IMPUESTO")
  protected String montoImpuesto;
  
  
  
  

   public Integer getSecuencialOtroImpuesto() {
     return secuencialOtroImpuesto;
   }
 
   public void setSecuencialOtroImpuesto(Integer secuencialOtroImpuesto) {
     this.secuencialOtroImpuesto = secuencialOtroImpuesto;
   }
 
   public String getPorcentaje() {
     return porcentaje;
   }
 
   public void setPorcentaje(String porcentaje) {
     this.porcentaje = porcentaje;
   }
 
   public String getCodigoCuenta() {
     return codigoCuenta;
   }
 
   public void setCodigoCuenta(String codigoCuenta) {
     this.codigoCuenta = codigoCuenta;
   }
 
   public String getSignoCuenta() {
     return signoCuenta;
   }
 
   public void setSignoCuenta(String signoCuenta) {
     this.signoCuenta = signoCuenta;
   }
 
   public String getMontoImpuesto() {
     return montoImpuesto;
   }
 
   public void setMontoImpuesto(String montoImpuesto) {
     this.montoImpuesto = montoImpuesto;
   }
 
}
