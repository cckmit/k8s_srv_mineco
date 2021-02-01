package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;


public class VistaAlmacenistaDinDTO extends AbstractCComplejoDTO implements Serializable {


  private static final long serialVersionUID = 1L;
  
  
  protected String codigoAlmacenista;
  protected Date fechaRecepcionMercancias;
  protected Date fechaRetiroMercancias;
  protected String numeroRegReconocimiento;
  protected String anioRegReconocimiento;
  protected String codigoDeclaracionRegla1;
  
  
  
  
  
   public String getCodigoAlmacenista() {
     return codigoAlmacenista;
   }
   public void setCodigoAlmacenista(String codigoAlmacenista) {
     this.codigoAlmacenista = codigoAlmacenista;
   }
   public Date getFechaRecepcionMercancias() {
     return fechaRecepcionMercancias;
   }
   public void setFechaRecepcionMercancias(Date fechaRecepcionMercancias) {
     this.fechaRecepcionMercancias = fechaRecepcionMercancias;
   }
   public Date getFechaRetiroMercancias() {
     return fechaRetiroMercancias;
   }
   public void setFechaRetiroMercancias(Date fechaRetiroMercancias) {
     this.fechaRetiroMercancias = fechaRetiroMercancias;
   }
   public String getNumeroRegReconocimiento() {
     return numeroRegReconocimiento;
   }
   public void setNumeroRegReconocimiento(String numeroRegReconocimiento) {
     this.numeroRegReconocimiento = numeroRegReconocimiento;
   }
   public String getAnioRegReconocimiento() {
     return anioRegReconocimiento;
   }
   public void setAnioRegReconocimiento(String anioRegReconocimiento) {
     this.anioRegReconocimiento = anioRegReconocimiento;
   }
   public String getCodigoDeclaracionRegla1() {
     return codigoDeclaracionRegla1;
   }
   public void setCodigoDeclaracionRegla1(String codigoDeclaracionRegla1) {
     this.codigoDeclaracionRegla1 = codigoDeclaracionRegla1;
   }

}
