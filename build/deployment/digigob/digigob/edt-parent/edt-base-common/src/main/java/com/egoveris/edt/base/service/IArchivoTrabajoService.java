package com.egoveris.edt.base.service;

import com.egoveris.edt.base.exception.NegocioException;

/**
 * @author psettino,javsolis
 * 
 */
public interface IArchivoTrabajoService {

  /**
   * Obtiene el contenido de un archivo de trabajo determinado, previamente
   * almacenado en el repositorio de documentos.
   * 
   * @param archivoTrabajo
   * @return Un arreglo de bytes con el contenido del archivo
   * @throws NegocioException
   */
  public byte[] obtenerArchivoTrabajo(String numeroGEDO);

  /**
   * Obtiene el contenido de un archivo de trabajo determinado, previamente
   * almacenado en el repositorio de documentos.
   * 
   * @param archivoTrabajo
   * @return Un arreglo de bytes con el contenido del archivo
   * @throws NegocioException
   */
  public byte[] obtenerArchivoTrabajo(String numeroGEDO, String nombreUsuario);

}