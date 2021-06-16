package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaOtroImpuestoDinDTO extends AbstractCComplejoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  
  protected Integer secuencialOtroImpuesto;
  protected String porcentaje;
  protected String codigoCuenta;
  protected String signoCuenta;
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
