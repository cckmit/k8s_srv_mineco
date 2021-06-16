package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_DECLARACION_DIN")
public class VistaDeclaracionDin extends AbstractCComplejoJPA {


  @Column(name = "NUMERO_IDENTIFICACION")
  protected String numeroIdentificacion;
  
  @Column(name = "CODIGO_ADUANA")
  protected String codigoAduana;
  
  @Column(name = "CAMPO_FORM")
  protected String campoForm;
  
  @Column(name = "FECHA_VENCIMIENTO")
  protected String fechaDeVencimiento;
  
  @Column(name = "CODIGO_TIPO_OPERACION")
  protected String codigoTipoOperacion;
  
  @Column(name = "TIPO_INGRESO")
  protected String tipoDeIngreso;
  
  @Column(name = "URL_DIN")
  protected String urlDin;
  
  @Column(name = "TIPO_DESTINACION")
  protected String tipoDestinacion;
  
  @Column(name = "REGION_INGRESO")
  protected String regionIngreso;
  
  
  
  

     public String getNumeroIdentificacion() {
       return numeroIdentificacion;
     }
   
     public void setNumeroIdentificacion(String numeroIdentificacion) {
       this.numeroIdentificacion = numeroIdentificacion;
     }
   
     public String getCodigoAduana() {
       return codigoAduana;
     }
   
     public void setCodigoAduana(String codigoAduana) {
       this.codigoAduana = codigoAduana;
     }
   
     public String getCampoForm() {
       return campoForm;
     }
   
     public void setCampoForm(String campoForm) {
       this.campoForm = campoForm;
     }
   
     public String getFechaDeVencimiento() {
       return fechaDeVencimiento;
     }
   
     public void setFechaDeVencimiento(String fechaDeVencimiento) {
       this.fechaDeVencimiento = fechaDeVencimiento;
     }
   
     public String getCodigoTipoOperacion() {
       return codigoTipoOperacion;
     }
   
     public void setCodigoTipoOperacion(String codigoTipoOperacion) {
       this.codigoTipoOperacion = codigoTipoOperacion;
     }
   
     public String getTipoDeIngreso() {
       return tipoDeIngreso;
     }
   
     public void setTipoDeIngreso(String tipoDeIngreso) {
       this.tipoDeIngreso = tipoDeIngreso;
     }
   
     public String getUrlDin() {
       return urlDin;
     }
   
     public void setUrlDin(String urlDin) {
       this.urlDin = urlDin;
     }
   
     public String getTipoDestinacion() {
       return tipoDestinacion;
     }
   
     public void setTipoDestinacion(String tipoDestinacion) {
       this.tipoDestinacion = tipoDestinacion;
     }
   
     public String getRegionIngreso() {
       return regionIngreso;
     }
   
     public void setRegionIngreso(String regionIngreso) {
       this.regionIngreso = regionIngreso;
     }

}
