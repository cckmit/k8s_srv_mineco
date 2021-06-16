package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Exception en el proceso de firma del documento electronico </br>
 * </br>
 * Codigos de error:</br>
 * --Validacion--</br>
 * 201: Los campos xxx, yyy no pueden ser vacios o nulos</br>
 * 202: No se encuentra el usuario xxx</br>
 * 203: El usuario contiene inconsistencia en sus datos xxx</br>
 * 204: No se encuentra una tarea de firma activa para el usuario: xxx</br>
 * 205: No puede firmar el tipo de documento xxx ya que no pertence a una
 * repartición habilitada</br>
 * 
 * --Negocio--</br>
 * 301: Error al obtener el documento del repositorio</br>
 * 302: Error al subir el documento al repositorio</br>
 * 303: El tipo de documento yyy ya no existe en GEDO</br>
 * 304: No existen campos de firma disponibles en el documento</br>
 * 305: El campo de firma ya se encuentra firmado</br>
 * 306: No existe el campo de firma en el documento</br>
 * 307: Error al verificar la validez de las firmas (message con descripción de
 * servicio)</br>
 * 308: Error firmando documento con certificado</br>
 * 311: Error al obtener el estado final del workflow</br>
 * 
 * --Inesperado--</br>
 * 330: Error inesperado al preparar la firma con token</br>
 * 331: Error al inesperado al firmar con token </br>
 * 332: Error al inesperado al firmar con servidor </br>
 */
public class FirmaDocumentoException extends ApplicationException {

  public static final String ERROR = "ERROR";
  public static final String INFO = "INFO";
  private static final long serialVersionUID = -7028710800299271642L;

  private int errorCode;
  private String errorType = ERROR;

  public FirmaDocumentoException(final String message, int errorCode) {
    super(message);
    setErrorCode(errorCode);
  }

  public FirmaDocumentoException(final String message, int errorCode, Throwable cause) {
    super(message, cause);
    setErrorCode(errorCode);
  }

  public FirmaDocumentoException(final String message, int errorCode, String errorType) {
    super(message);
    setErrorCode(errorCode);
    setErrorType(errorType);
  }

  public FirmaDocumentoException(final String message, int errorCode, String errorType,
      Throwable cause) {
    super(message, cause);
    setErrorCode(errorCode);
    setErrorType(errorType);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }
}