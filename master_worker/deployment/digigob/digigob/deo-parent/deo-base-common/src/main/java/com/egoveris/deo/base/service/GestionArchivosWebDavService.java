package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;

import java.io.BufferedInputStream;

public interface GestionArchivosWebDavService {

  /**
   * Guardar un documento en Alfresco
   * 
   * @param request:
   *          Datos del documento
   * @param archivo:
   *          Archivo a almacenar.
   */
  public String subirDocumentoWebDav(RequestGenerarDocumento request);

  /**
   * Permite almacenar los archivos temporales pendientes de firma en una
   * carpeta temporal en el servidor WebDav.
   * 
   * @param nombre
   * @param contenido
   * @
   * 
   * 
   */
  public String subirDirectorioRevisionWebDav(RequestGenerarDocumento request);

  public void subirArchivoTemporalWebDav(String nombre, byte[] contenido);

  public void subirArchivoDirectorioTemporalRevisionWebDav(String nombreDirectorio,
      String nombreArchivo, byte[] contenido);

  /**
   * Permite almacenar las revisiones del pdf de rectificacion. el directorio es
   * el numero de sade.
   */
  public String subirArchivoRevisionRectificacion(String directorio, byte[] contenido);

  /**
   * Permite almacenar los archivos de limpieza de temporales a la carpeta de
   * Limpieza_Temporales en el servidor WebDav.
   * 
   * @param nombre
   * @param contenido
   * @
   */
  public void subirDocumentoLimpiezaTemporalesWebDav(String nombre, byte[] contenido);

  /**
   * Descara el archivo temporal de Alfresco y lo crea en el directorio temporal
   * del servidor de aplicaciones.
   * 
   * @param nombreArchivoTemporal
   * @return
   */
  public BufferedInputStream obtenerArchivoStreamTemporalWebDav(String nombreArchivoTemporal);

  /**
   * Obtiene el objeto WebDav del servicio WebDav
   * 
   * @param nombreArchivoTemporal:
   *          nombre del recurso
   * @return WebDAVResourceBean.
   */
  public byte[] obtenerRecursoTemporalWebDav(String nombreArchivoTemporal);

  /**
   * Borra el archivo dado de la carpeta de archivos temporales en alfresco.
   * 
   * @param nombreArchivo:
   *          Nombre del archivo a borrar.
   */
  public void borrarArchivoTemporalWebDav(String nombreArchivo);

  /**
   * Borrar un documento generado en Alfresco.
   * 
   * @param numero
   * @
   */
  public void borrarArchivoWebDav(String numero);

  /**
   * Obtiene el documento indicado desde el servidor de Alfresco
   * 
   * @param numeroDocumento
   * @return @
   */
  public FileAsStreamConnectionWebDav obtenerDocumento(String numeroDocumento);

  /**
   * Obtiene el documento temporal indicado desde el servidor de Alfresco
   * 
   * @param numeroDocumento
   * @return
   */
  public FileAsStreamConnectionWebDav obtenerRecursoTemporalWebDavAsStream(
      String nombreArchivoTemporal);

  /**
   * Crea el nombre del archivo temporal, que posteriormente ser� almacenado en
   * la carpeta de temporales del WebDav.
   * 
   * @return
   */
  public String crearNombreArchivoTemporal();

  public void borrarArchivoDeTrabajoTemporalWebDav(String pathRelativo, String nombreArchivo);

  /**
   * @author eumolina
   * @param nombreArchivoTemporal
   * @return
   */
  public byte[] obtenerArchivoDeTrabajoWebDav(String nombreArchivoTemporal, String nombreArchivo);

  public byte[] obtenerArchivoDeTrabajoTemporalWebDav(String nombreArchivoTemporal,
      String nombreArchivo);

  /**
   * @author eumolina Permite almacenar los archivos de trabajo temporales en
   *         una carpeta temporal en el servidor WebDav.
   * @param nombre
   * @param contenido
   * @
   */
  public void subirArchivoDeTrabajoTemporalWebDav(ArchivoDeTrabajoDTO archivoDeTrabajo,
      byte[] contenido);

  /**
   * @author eumolina
   * @param nombre
   * @param contenido
   */
  public String subirArchivoDeTrabajoWebDav(String numeroSade, byte[] contenido,
      String nombreArchivo);

  /**
   * Permite almacenar los documento adjuntos temporales en una carpeta temporal
   * en el servidor WebDav.
   * 
   * @param documentoAdjunto
   * @param contenido
   * @
   */
  public void subirDocumentoAdjuntoTemporalWebDav(DocumentoAdjuntoDTO documentoAdjunto,
      byte[] contenido);

  /**
   * Obtiene un determinado documento adjunto temporal de una carpeta temporal
   * en el servidor WebDav.
   * 
   * @param nombreDocumentoAdjunto
   * @param nombreDocumento
   * @return @
   */
  public byte[] obtenerDocumentoAdjuntoTemporalWebDav(String pathRelativo,
      String nombreDocumentoAdjunto);

  /**
   * Borra un documento adjunto temporaal de una carpeta temporal
   * 
   * @param pathRelativo
   * @param nombreDocumento
   * @
   */
  public void borrarDocumentoAdjuntoTemporalWebDav(String pathRelativo,
      String nombreDocumentoAdjunto);

  /**
   * Obtiene el path en donde se almacen� el documento temporal.
   * 
   * @param numeroDocumento:
   *          nombre del documento.
   * @return url.
   */
  public String obtenerUrlTemporal(String nombreArchivo);

  /**
   * Obtiene el tamaño del archivo
   * 
   * @param nombreArchivoTemporal
   */
  public int getFileSize(String nombreArchivoTemporal);

  /**
   * Obtiene el path completo de la ubicación del archivo pdf identificado por
   * el número de documento dado. Incluye el nombre del archivo.
   * 
   * @param numeroDocumento:
   *          Número SADE del documento en GEDO.
   * @return
   */
  public String obtenerUbicacionDescarga(String numeroDocumento);

  public byte[] obtenerArchivoDeTrabajoWebDav(String nombreRecurso);

  /**
   * @param numeroSade
   * @param contenido
   * @param nombreArchivo
   *          Sube un archivo como copia original para los documentos de
   *          rectificacion la ruta es /SADE/ORIGINAL/
   */
  public String subirArchivoDeTrabajoWebDavOriginal(String numeroSade, byte[] contenido,
      String nombreArchivo);

}