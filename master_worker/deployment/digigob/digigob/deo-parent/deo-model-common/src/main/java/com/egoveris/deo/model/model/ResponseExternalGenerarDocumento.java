package com.egoveris.deo.model.model;

import java.io.Serializable;

/**
 * Define el formato de respuesta después de procesar la solicitud de generación
 * automática de documentos desde un sistema externo.
 * 
 * - urlArchivoGenerado: Ubicación en el repositorio del archivo generado.
 * Obligatorio. - numero: Número SADE generado asociado al documento.
 * Obligatorio. - numeroEspecial: Número Especial asociado al documento.
 * Obligatorio si el tipo de documento así lo exigiera.
 */

public class ResponseExternalGenerarDocumento implements Serializable {

  private static final long serialVersionUID = -2881614998485530288L;

  private String numero;
  private String urlArchivoGenerado;
  private String numeroEspecial;
  private String licencia;

  public String getLicencia() {
    return licencia;
  }

  public void setLicencia(String licencia) {
    this.licencia = licencia;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getNumeroEspecial() {
    return numeroEspecial;
  }

  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public String getUrlArchivoGenerado() {
    return urlArchivoGenerado;
  }

  public void setUrlArchivoGenerado(String urlArchivoGenerado) {
    this.urlArchivoGenerado = urlArchivoGenerado;
  }
}
