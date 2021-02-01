package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_LISTA_DOCUMENTO_INGRESO")
public class ListaDocumentoIngreso extends AbstractCComplejoJPA {


 @Column(name = "ID_SOLICITUD")
 protected String idSolicitud;
  
 @Column(name = "NOMBRE_SSPP")
 protected String nombreSSPP;
  
 @Column(name = "NOMBRE_DOCUMENTO")
 protected String nombreDocumento;
  
 @Column(name = "NOMBRE_INSTALACION_DESTINO")
 protected String nombreInstalacionDestino;
 
 
 
 

 public String getIdSolicitud() {
   return idSolicitud;
 }
 
 public void setIdSolicitud(String idSolicitud) {
   this.idSolicitud = idSolicitud;
 }
 
 public String getNombreSSPP() {
   return nombreSSPP;
 }
 
 public void setNombreSSPP(String nombreSSPP) {
   this.nombreSSPP = nombreSSPP;
 }
 
 public String getNombreDocumento() {
   return nombreDocumento;
 }
 
 public void setNombreDocumento(String nombreDocumento) {
   this.nombreDocumento = nombreDocumento;
 }
 
 public String getNombreInstalacionDestino() {
   return nombreInstalacionDestino;
 }
 
 public void setNombreInstalacionDestino(String nombreInstalacionDestino) {
   this.nombreInstalacionDestino = nombreInstalacionDestino;
 }
   
}
