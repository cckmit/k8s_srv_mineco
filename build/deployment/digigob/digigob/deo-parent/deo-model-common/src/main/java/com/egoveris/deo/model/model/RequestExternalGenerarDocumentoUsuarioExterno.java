package com.egoveris.deo.model.model;

import java.io.Serializable;

/**
 * Establece los parámetros de entrada requeridos para la generación automática
 * de documentos electrónicos en GEDO con usuario externo.
 * 
 * - nombreYApellido: nombre y apellido del usuario generador del documento.
 * Obligatorio. - cargo: cargo que ocupa el usuario generador del documento.
 * Obligatorio. - codigoReparticion: codigo identificatorio de la reparticion
 * seleccionada para el usuario. Obligatorio
 */

public class RequestExternalGenerarDocumentoUsuarioExterno extends RequestExternalGenerarDocumento
    implements Serializable {

  private static final long serialVersionUID = 8058182609128984731L;

  private String nombreYApellido;
  private String cargo;
  private String reparticion;
  private String sector;

  public String getNombreYApellido() {
    return nombreYApellido;
  }

  public void setNombreYApellido(String nombreYApellido) {
    this.nombreYApellido = nombreYApellido;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getReparticion() {
    return reparticion;
  }

  public void setReparticion(String reparticion) {
    this.reparticion = reparticion;
  }

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
  
}
