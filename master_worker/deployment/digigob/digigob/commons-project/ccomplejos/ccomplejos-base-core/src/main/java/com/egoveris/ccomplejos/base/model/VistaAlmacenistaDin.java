package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_ALMACENISTA_DIN")
public class VistaAlmacenistaDin extends AbstractCComplejoJPA {


 @Column(name = "CODIGO_ALMACENISTA")
 protected String codigoAlmacenista;
 
 @Column(name = "FECHA_RECEPCION_MERCANCIAS")
 protected Date fechaRecepcionMercancias;
 
 @Column(name = "FECHA_RETIRO_MERCANCIAS")
 protected Date fechaRetiroMercancias;
 
 @Column(name = "NUMERO_REG_RECONOCIMIENTO")
 protected String numeroRegReconocimiento;
 
 @Column(name = "ANIO_REG_RECONOCIMIENTO")
 protected String anioRegReconocimiento;
 
 @Column(name = "CODIGO_DECLARACION_REGLA1")
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
