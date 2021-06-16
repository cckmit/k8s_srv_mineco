package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define el formato de respuesta de la consulta de un documeto en GEDO, por
 * número SADE.
 * 
 * numeroDocumento: Numero SADE asignado al documento cuando fue generado por
 * GEDO. (Básico) usuarioGenereador: Ultimo firmante del documento. (Básico)
 * fechaCreacion: Fecha de generación del documento. (Básico) urlArchivo.
 * Ubicación del archivo en WebDav. (Básico) tipoDocumento: Acrónimo del tipo de
 * documento correspondiente.(Básico) sistemaOrigen: Sistema que realizo la
 * solicitud de generación del documento.(Básico) usuarioIniciador: Usuario que
 * inicio la generación del documento. (Básico). archivosDeTrabajo: Lista de los
 * nombres de los archivos de trabajo asociados al documento. (Detalle)
 * datosPropios: Map con los datos propios del documento con su correspondiente
 * valor. (Detalle)
 * 
 * @author kmarroqu
 *
 */
public class ResponseExternalConsultaDocumento implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = -8344368837848656371L;

  private String numeroDocumento;
  private String usuarioGenerador;
  private Date fechaCreacion;
  private String urlArchivo;
  private String tipoDocumento;
  private String sistemaOrigen;
  private String usuarioIniciador;
  private List<String> archivosDeTrabajo;
  private String numeroEspecial;
  private String motivo;
  private Integer idTransaccion;

  private HashMap<String, Object> datosPropios;

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public String getUsuarioGenerador() {
    return usuarioGenerador;
  }

  public void setUsuarioGenerador(String usuarioGenerador) {
    this.usuarioGenerador = usuarioGenerador;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public void setUrlArchivo(String urlArchivo) {
    this.urlArchivo = urlArchivo;
  }

  public String getUrlArchivo() {
    return urlArchivo;
  }

  public List<String> getArchivosDeTrabajo() {
    return archivosDeTrabajo;
  }

  public void setArchivosDeTrabajo(List<String> archivosDeTrabajo) {
    this.archivosDeTrabajo = archivosDeTrabajo;
  }

  public Map<String, Object> getDatosPropios() {
    return datosPropios;
  }

  public void setDatosPropios(HashMap<String, Object> datosPropios) {
    this.datosPropios = datosPropios;
  }

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getSistemaOrigen() {
    return sistemaOrigen;
  }

  public void setSistemaOrigen(String sistemaOrigen) {
    this.sistemaOrigen = sistemaOrigen;
  }

  public String getUsuarioIniciador() {
    return usuarioIniciador;
  }

  public void setUsuarioIniciador(String usuarioIniciador) {
    this.usuarioIniciador = usuarioIniciador;
  }

  public String getNumeroEspecial() {
    return numeroEspecial;
  }

  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public String getMotivo() {
    return this.motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public Integer getIdTransaccion() {
    return idTransaccion;
  }

  public void setIdTransaccion(Integer idTransaccion) {
    this.idTransaccion = idTransaccion;
  }
}
