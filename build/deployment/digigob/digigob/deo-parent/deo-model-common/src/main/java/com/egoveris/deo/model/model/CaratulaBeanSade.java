package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class CaratulaBeanSade implements Serializable {


  private static final long serialVersionUID = -4962416875163415422L;
  private Integer idActuacion;
  private Integer idReparticionActuacion;
  private Integer idReparticionUsuario;
  private Integer idSecuencia;
  private Integer anioActuacion;
  private String codigoTipoDocumento;
  private String numeroDocumento;
  private String apellidoRazon;
  private Integer idTipoActuacionInicia;
  private Integer anioActuacionInicia;
  private Integer numeroActuacionInicia;
  private Integer idReparticionActuacionInicia;
  private Integer idSecuenciaInicia;
  private Integer idReparticionSolicitante;
  private Integer idReparticionJudicial;
  private String actExtramunicipal;
  private String cuerposAnexos;
  private String codigoExtracto;
  private Integer numeroSecuencia;
  private String descripcionTrata;
  private String issib;
  private String dominio;
  private String denuncia;
  private Integer idReparticionOrigen;
  private Integer idReparticionDestino;
  private Integer idSectorInternoOrigen;
  private Integer idSectorInternoDestino;
  private Integer fojas;
  private String consNombreCalleExt;
  private String consNombreCalle1Ext;
  private String consNombreCalle2Ext;
  private String consNombreCalleEsquinaExt;
  private String consCodigoPostalCalleExt;
  private String consCodigoPostalCalle1Ext;
  private String consCodigoPostalCalle2Ext;
  private String consCodigoPostalCalleEsquinaExt;
  private String consAltura;
  private String consNumero;
  private String consEdificio;
  private String consPiso;
  private String consDepartamento;
  private String consUnidadFuncional;
  private String consLocal;
  private String consPartida;
  private String consCatastral;
  private String observaciones;
  private String usuarioCreacion;
  private Integer numeroSigaf;
  private Integer anioSigaf;
  private String accion;
  private String estado;
  private String tipoActuacion;
  private String moduloOrigen;
  private Date fechaCreacion;
  private Integer idCodigoExtracto;
  
  public CaratulaBeanSade() {
  }
  public String getAccion() {
   return accion;
  }
  public void setAccion(String accion) {
   this.accion = accion;
  }
  public String getEstado() {
   return estado;
  }
  public void setEstado(String estado) {
   this.estado = estado;
  }
  public String getTipoActuacion() {
   return tipoActuacion;
  }
  public void setTipoActuacion(String tipoActuacion) {
   this.tipoActuacion = tipoActuacion;
  }
  public Integer getIdActuacion() {
   return idActuacion;
  }
  public void setIdActuacion(Integer idActuacion) {
   this.idActuacion = idActuacion;
  }
  public Integer getIdReparticionActuacion() {
   return idReparticionActuacion;
  }
  public void setIdReparticionActuacion(Integer idReparticionActuacion) {
   this.idReparticionActuacion = idReparticionActuacion;
  }
  public Integer getIdReparticionUsuario() {
   return idReparticionUsuario;
  }
  public void setIdReparticionUsuario(Integer idReparticionUsuario) {
   this.idReparticionUsuario = idReparticionUsuario;
  }
  public Integer getIdSecuencia() {
   return idSecuencia;
  }
  public void setIdSecuencia(Integer idSecuencia) {
   this.idSecuencia = idSecuencia;
  }
  public String getCodigoTipoDocumento() {
   return codigoTipoDocumento;
  }
  public void setCodigoTipoDocumento(String codigoTipoDocumento) {
   this.codigoTipoDocumento = codigoTipoDocumento;
  }
  public String getNumeroDocumento() {
   return numeroDocumento;
  }
  public void setNumeroDocumento(String numeroDocumento) {
   this.numeroDocumento = numeroDocumento;
  }
  public String getApellidoRazon() {
   return apellidoRazon;
  }
  public void setApellidoRazon(String apellidoRazon) {
   this.apellidoRazon = apellidoRazon;
  }
  public Integer getCodigoTipoActuacionInicia() {
   return idTipoActuacionInicia;
  }
  public void setidTipoActuacionInicia(Integer idTipoActuacionInicia) {
   this.idTipoActuacionInicia = idTipoActuacionInicia;
  }
  public Integer getAnioActuacionInicia() {
   return anioActuacionInicia;
  }
  public void setAnioActuacionInicia(Integer anioActuacionInicia) {
   this.anioActuacionInicia = anioActuacionInicia;
  }
  public Integer getNumeroActuacionInicia() {
   return numeroActuacionInicia;
  }
  public void setNumeroActuacionInicia(Integer numeroActuacionInicia) {
   this.numeroActuacionInicia = numeroActuacionInicia;
  }
  public Integer getIdReparticionActuacionInicia() {
   return idReparticionActuacionInicia;
  }
  public void setIdReparticionActuacionInicia(
    Integer idReparticionActuacionInicia) {
   this.idReparticionActuacionInicia = idReparticionActuacionInicia;
  }
  public Integer getIdSecuenciaInicia() {
   return idSecuenciaInicia;
  }
  public void setIdSecuenciaInicia(Integer idSecuenciaInicia) {
   this.idSecuenciaInicia = idSecuenciaInicia;
  }
  public Integer getIdReparticionSolicitante() {
   return idReparticionSolicitante;
  }
  public void setIdReparticionSolicitante(Integer idReparticionSolicitante) {
   this.idReparticionSolicitante = idReparticionSolicitante;
  }
  public Integer getIdReparticionJudicial() {
   return idReparticionJudicial;
  }
  public void setIdReparticionJudicial(Integer idReparticionJudicial) {
   this.idReparticionJudicial = idReparticionJudicial;
  }
  public String getActExtramunicipal() {
   return actExtramunicipal;
  }
  public void setActExtramunicipal(String actExtramunicipal) {
   this.actExtramunicipal = actExtramunicipal;
  }
  public String getCuerposAnexos() {
   return cuerposAnexos;
  }
  public void setCuerposAnexos(String cuerposAnexos) {
   this.cuerposAnexos = cuerposAnexos;
  }
  public String getCodigoExtracto() {
   return codigoExtracto;
  }
  public void setCodigoExtracto(String codigoExtracto) {
   this.codigoExtracto = codigoExtracto;
  }
  public String getDescripcionTrata() {
   return descripcionTrata;
  }
  public void setDescripcionTrata(String descripcionTrata) {
   this.descripcionTrata = descripcionTrata;
  }
  public String getIssib() {
   return issib;
  }
  public void setIssib(String issib) {
   this.issib = issib;
  }
  public String getDominio() {
   return dominio;
  }
  public void setDominio(String dominio) {
   this.dominio = dominio;
  }
  public String getDenuncia() {
   return denuncia;
  }
  public void setDenuncia(String denuncia) {
   this.denuncia = denuncia;
  }
  public Integer getIdReparticionOrigen() {
   return idReparticionOrigen;
  }
  public void setIdReparticionOrigen(Integer idReparticionOrigen) {
   this.idReparticionOrigen = idReparticionOrigen;
  }
  public Integer getIdReparticionDestino() {
   return idReparticionDestino;
  }
  public void setIdReparticionDestino(Integer idReparticionDestino) {
   this.idReparticionDestino = idReparticionDestino;
  }
  public Integer getIdSectorInternoOrigen() {
   return idSectorInternoOrigen;
  }
  public void setIdSectorInternoOrigen(Integer idSectorInternoOrigen) {
   this.idSectorInternoOrigen = idSectorInternoOrigen;
  }
  public Integer getIdSectorInternoDestino() {
   return idSectorInternoDestino;
  }
  public void setIdSectorInternoDestino(Integer idSectorInternoDestino) {
   this.idSectorInternoDestino = idSectorInternoDestino;
  }
  public Integer getFojas() {
   return fojas;
  }
  public void setFojas(Integer fojas) {
   this.fojas = fojas;
  }
  public String getConsNombreCalleExt() {
   return consNombreCalleExt;
  }
  public void setConsNombreCalleExt(String consNombreCalleExt) {
   this.consNombreCalleExt = consNombreCalleExt;
  }
  public String getConsNombreCalle1Ext() {
   return consNombreCalle1Ext;
  }
  public void setConsNombreCalle1Ext(String consNombreCalle1Ext) {
   this.consNombreCalle1Ext = consNombreCalle1Ext;
  }
  public String getConsNombreCalle2Ext() {
   return consNombreCalle2Ext;
  }
  public void setConsNombreCalle2Ext(String consNombreCalle2Ext) {
   this.consNombreCalle2Ext = consNombreCalle2Ext;
  }
  public String getConsNombreCalleEsquinaExt() {
   return consNombreCalleEsquinaExt;
  }
  public void setConsNombreCalleEsquinaExt(String consNombreCalleEsquinaExt) {
   this.consNombreCalleEsquinaExt = consNombreCalleEsquinaExt;
  }
  public String getConsCodigoPostalCalleExt() {
   return consCodigoPostalCalleExt;
  }
  public void setConsCodigoPostalCalleExt(String consCodigoPostalCalleExt) {
   this.consCodigoPostalCalleExt = consCodigoPostalCalleExt;
  }
  public String getConsCodigoPostalCalle1Ext() {
   return consCodigoPostalCalle1Ext;
  }
  public void setConsCodigoPostalCalle1Ext(String consCodigoPostalCalle1Ext) {
   this.consCodigoPostalCalle1Ext = consCodigoPostalCalle1Ext;
  }
  public String getConsCodigoPostalCalle2Ext() {
   return consCodigoPostalCalle2Ext;
  }
  public void setConsCodigoPostalCalle2Ext(String consCodigoPostalCalle2Ext) {
   this.consCodigoPostalCalle2Ext = consCodigoPostalCalle2Ext;
  }
  public String getConsCodigoPostalCalleEsquinaExt() {
   return consCodigoPostalCalleEsquinaExt;
  }
  public void setConsCodigoPostalCalleEsquinaExt(
    String consCodigoPostalCalleEsquinaExt) {
   this.consCodigoPostalCalleEsquinaExt = consCodigoPostalCalleEsquinaExt;
  }
  public String getConsAltura() {
   return consAltura;
  }
  public void setConsAltura(String consAltura) {
   this.consAltura = consAltura;
  }
  public String getConsNumero() {
   return consNumero;
  }
  public void setConsNumero(String consNumero) {
   this.consNumero = consNumero;
  }
  public String getConsEdificio() {
   return consEdificio;
  }
  public void setConsEdificio(String consEdificio) {
   this.consEdificio = consEdificio;
  }
  public String getConsPiso() {
   return consPiso;
  }
  public void setConsPiso(String consPiso) {
   this.consPiso = consPiso;
  }
  public String getConsDepartamento() {
   return consDepartamento;
  }
  public void setConsDepartamento(String consDepartamento) {
   this.consDepartamento = consDepartamento;
  }
  public String getConsUnidadFuncional() {
   return consUnidadFuncional;
  }
  public void setConsUnidadFuncional(String consUnidadFuncional) {
   this.consUnidadFuncional = consUnidadFuncional;
  }
  public String getConsLocal() {
   return consLocal;
  }
  public void setConsLocal(String consLocal) {
   this.consLocal = consLocal;
  }
  public String getConsPartida() {
   return consPartida;
  }
  public void setConsPartida(String consPartida) {
   this.consPartida = consPartida;
  }
  public String getConsCatastral() {
   return consCatastral;
  }
  public void setConsCatastral(String consCatastral) {
   this.consCatastral = consCatastral;
  }
  public String getObservaciones() {
   return observaciones;
  }
  public void setObservaciones(String observaciones) {
   this.observaciones = observaciones;
  }
  public String getUsuarioCreacion() {
   return usuarioCreacion;
  }
  public void setUsuarioCreacion(String usuarioCreacion) {
   this.usuarioCreacion = usuarioCreacion;
  }
  public Integer getNumeroSigaf() {
   return numeroSigaf;
  }
  public void setNumeroSigaf(Integer numeroSigaf) {
   this.numeroSigaf = numeroSigaf;
  }
  public Integer getAnioSigaf() {
   return anioSigaf;
  }
  public void setAnioSigaf(Integer anioSigaf) {
   this.anioSigaf = anioSigaf;
  }
  /**
   * @return the numeroSecuencia
   */
  public Integer getNumeroSecuencia() {
   return numeroSecuencia;
  }
  /**
   * @param numeroSecuencia the numeroSecuencia to set
   */
  public void setNumeroSecuencia(Integer numeroSecuencia) {
   this.numeroSecuencia = numeroSecuencia;
  }
  public Integer getAnioActuacion() {
   return anioActuacion;
  }
  public void setAnioActuacion(Integer anioActuacion) {
   this.anioActuacion = anioActuacion;
  }
  /**
   * 
   * @return
   */
  
  public String getModuloOrigen() {
   return moduloOrigen;
  }
  /**
   * 
   * @param moduloOrigen
   */
  
  public void setModuloOrigen(String moduloOrigen) {
   this.moduloOrigen = moduloOrigen;
  }
  
  public Date getFechaCreacion() {
   return fechaCreacion;
  }
  public void setFechaCreacion(Date fechaCreacion) {
   this.fechaCreacion = fechaCreacion;
  }
  public Integer getIdCodigoExtracto() {
   return idCodigoExtracto;
  }
  public void setIdCodigoExtracto(Integer idCodigoExtracto) {
   this.idCodigoExtracto = idCodigoExtracto;
  }

}
