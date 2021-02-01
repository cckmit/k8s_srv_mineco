package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shareddocument.base.exception.WebDAVConflictException;
import com.egoveris.shareddocument.base.exception.WebDAVConnectionException;
import com.egoveris.shareddocument.base.exception.WebDAVResourceNotFoundException;
import com.egoveris.shareddocument.base.exception.WevDAVMovedPermanentlyException;
import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;
import com.egoveris.shareddocument.base.model.WebDAVResourceBean;
import com.egoveris.shareddocument.base.service.IWebDavService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Permite, crear y borrar archivos de Alfresco.
 * 
 * @author
 *
 */
@Service
@Transactional
public class GestionArchivosWebDavServiceImpl implements GestionArchivosWebDavService {
  protected static final Logger LOGGER = LoggerFactory      
      .getLogger(GestionArchivosWebDavServiceImpl.class);
  protected static final String SLASH = "/";

  @Autowired
  protected IWebDavService webDavService;

  @Override
  public String subirDocumentoWebDav(RequestGenerarDocumento request) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDocumentoWebDav(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    StringBuilder ubicacionArchivo = new StringBuilder();
    try {
      String url = this.crearEstructuraEspacios(request.getNumero(),
          Constantes.CARPETA_RAIZ_DOCUMENTOS);
      webDavService.createFile(url, request.getNumero() + ".pdf", "", "",
          new ByteArrayInputStream(request.getData()));
      ubicacionArchivo.append(url);
      ubicacionArchivo.append(SLASH);
      ubicacionArchivo.append(request.getNumero() + ".pdf");
    } catch (ApplicationException e) {
      LOGGER.debug("No se puede adjuntar  el archivo en WebDav: " + request.getNumero());
      throw e;
    }
    String returnString = ubicacionArchivo.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDocumentoWebDav(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  @Override
  public String subirDirectorioRevisionWebDav(RequestGenerarDocumento request) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDirectorioRevisionWebDav(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    String ubicacionVersionado = null;
    String carpetaTemporal = request.getNombreArchivo();
    carpetaTemporal = carpetaTemporal.substring(0, carpetaTemporal.lastIndexOf('.'));
    try {
      String url = this.crearEstructuraEspaciosVersionado(request.getNumero(),
          Constantes.CARPETA_RAIZ_DOCUMENTOS);

      String urlTemp = this.obtenerUrlTemporalRevision(carpetaTemporal);

      ubicacionVersionado = url;

      webDavService.moveFolder(urlTemp, ubicacionVersionado, "", "");

    } catch (ApplicationException e) {
      LOGGER.debug("No se puede adjuntar  el directorio en WebDav: " + request.getNumero());
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDirectorioRevisionWebDav(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return ubicacionVersionado;
  }

  @Override
  public void subirArchivoTemporalWebDav(String nombre, byte[] contenido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoTemporalWebDav(String, byte[]) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.crearEstructuraTemporales(nombre);
    webDavService.createFile(url, nombre, "", "", new ByteArrayInputStream(contenido));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoTemporalWebDav(String, byte[]) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void subirArchivoDirectorioTemporalRevisionWebDav(String nombreDirectorio,
      String nombreArchivo, byte[] contenido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDirectorioTemporalRevisionWebDav(String, String, byte[]) - start"); //$NON-NLS-1$
    }

    String urlnicial;
    urlnicial = this.crearEstructuraTemporalesVersionado(nombreDirectorio);

    StringBuilder url = new StringBuilder(urlnicial);
    webDavService.createFile(url.toString(), nombreArchivo, "", "",
        new ByteArrayInputStream(contenido));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDirectorioTemporalRevisionWebDav(String, String, byte[]) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void subirArchivoDeTrabajoTemporalWebDav(ArchivoDeTrabajoDTO archivoDeTrabajo,
      byte[] contenido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoTemporalWebDav(ArchivoDeTrabajoDTO, byte[]) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.crearEstructuraArchivosDeTrabajoTemporales(archivoDeTrabajo.getPathRelativo());
    webDavService.createFile(url, archivoDeTrabajo.getNombreArchivo(), "", "",
        new ByteArrayInputStream(contenido));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoTemporalWebDav(ArchivoDeTrabajoDTO, byte[]) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void subirDocumentoAdjuntoTemporalWebDav(DocumentoAdjuntoDTO documentoAdjunto,
      byte[] contenido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDocumentoAdjuntoTemporalWebDav(DocumentoAdjuntoDTO, byte[]) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.crearEstructuraDocumentoAdjuntosTemporales(documentoAdjunto.getPathRelativo());
    webDavService.createFile(url, documentoAdjunto.getNombreArchivo(), "", "",
        new ByteArrayInputStream(contenido));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDocumentoAdjuntoTemporalWebDav(DocumentoAdjuntoDTO, byte[]) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void subirDocumentoLimpiezaTemporalesWebDav(String nombre, byte[] contenido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDocumentoLimpiezaTemporalesWebDav(String, byte[]) - start"); //$NON-NLS-1$
    }

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    String nombreSpace = format.format(new Date());
    String url = Constantes.CARPETA_RAIZ_LIMPIEZA_TEMPORALES + SLASH + nombreSpace;

    try {
      this.webDavService.createSpace(Constantes.CARPETA_RAIZ_LIMPIEZA_TEMPORALES, nombreSpace, "",
          "");
    } catch (WevDAVMovedPermanentlyException e) {
      LOGGER.debug("Ya existe la carpeta a generar en el repositorio de Limpieza Temporales: "
          + url + e.getMessage(), e);
    }

    webDavService.createFile(url, nombre, "", "", new ByteArrayInputStream(contenido));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDocumentoLimpiezaTemporalesWebDav(String, byte[]) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public byte[] obtenerDocumentoAdjuntoTemporalWebDav(String pathRelativo,
      String nombreDocumentoAdjunto) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerDocumentoAdjuntoTemporalWebDav(String, String) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.obtenerUrlDocumentoAdjuntoTemporal(pathRelativo);
    FileAsStreamConnectionWebDav file = null;
    byte[] contenido = null;
    String fileName = url.concat(nombreDocumentoAdjunto);
    try {
      file = this.webDavService.getFileAsStream(fileName, null, null);
      contenido = IOUtils.toByteArray(file.getFileAsStream());
    } catch (WebDAVConnectionException wde) {
      LOGGER.error("Error obteniendo archivo del WebDav", wde);
    } catch (IOException e) {
      LOGGER.error("Error obteniendo contenido del archivo del WebDav", e);
    } finally {
      if (file != null) {
        file.closeConnection();
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerDocumentoAdjuntoTemporalWebDav(String, String) - end"); //$NON-NLS-1$
    }
    return contenido;
  }

  @Override
  public void borrarDocumentoAdjuntoTemporalWebDav(String pathRelativo,
      String nombreDocumentoAdjunto) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarDocumentoAdjuntoTemporalWebDav(String, String) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.obtenerUrlDocumentoAdjuntoTemporal(pathRelativo);
    webDavService.deleteFile(url, nombreDocumentoAdjunto, "", "");

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarDocumentoAdjuntoTemporalWebDav(String, String) - end"); //$NON-NLS-1$
    }
  }

  // TODO: A la fecha nadie usa este metodo. Revisar que no devuelve un byte[]
  // ni
  // tampoco cierra la conexion del getFileAsStream().
  @Override
  public BufferedInputStream obtenerArchivoStreamTemporalWebDav(String nombreArchivoTemporal) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoStreamTemporalWebDav(String) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.obtenerUrlTemporal(nombreArchivoTemporal);
    String fileName = url + nombreArchivoTemporal;
    try {
      FileAsStreamConnectionWebDav file = webDavService.getFileAsStream(fileName, null, null);
      BufferedInputStream returnBufferedInputStream = file.getFileAsStream();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("obtenerArchivoStreamTemporalWebDav(String) - end"); //$NON-NLS-1$
      }
      return returnBufferedInputStream;
    } catch (Exception e) {
      LOGGER.error("Error obteniendo archivo WebDav: " + nombreArchivoTemporal, e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoStreamTemporalWebDav(String) - end"); //$NON-NLS-1$
    }
    return null;
  }

  @Override
  public byte[] obtenerRecursoTemporalWebDav(String nombreArchivoTemporal) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerRecursoTemporalWebDav(String) - start"); //$NON-NLS-1$
    }

    String url;
    byte[] contenido = null;
    url = this.obtenerUrlTemporal(nombreArchivoTemporal);
    String fileName = url + nombreArchivoTemporal;
    FileAsStreamConnectionWebDav fileStream = null;
    try {
      fileStream = webDavService.getFileAsStream(fileName, null, null);
      contenido = IOUtils.toByteArray(fileStream.getFileAsStream());
    } catch (WebDAVConnectionException wde) {
      LOGGER.error("Error obteniendo archivo del WebDav", wde);
    } catch (IOException e) {
      LOGGER.error("Error obteniendo contenido del archivo del WebDav", e);
    } finally {
      if (fileStream != null) {
        fileStream.closeConnection();
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerRecursoTemporalWebDav(String) - end"); //$NON-NLS-1$
    }
    return contenido;
  }

  @Override
  public byte[] obtenerArchivoDeTrabajoTemporalWebDav(String pathRelativo, String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoTemporalWebDav(String, String) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.obtenerUrlTemporalArchivoDeTrabajo(pathRelativo);
    FileAsStreamConnectionWebDav file = null;
    byte[] contenido = null;
    String fileName = url.concat(nombreArchivo);
    try {
      file = this.webDavService.getFileAsStream(fileName, null, null);
      contenido = IOUtils.toByteArray(file.getFileAsStream());
    } catch (WebDAVConnectionException wde) {
      LOGGER.error("Error obteniendo archivo del WebDav", wde);
      throw wde;
    } catch (IOException e) {
      LOGGER.error("Error obteniendo contenido del archivo del WebDav", e);
    } finally {
      if (file != null) {
        file.closeConnection();
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoTemporalWebDav(String, String) - end"); //$NON-NLS-1$
    }
    return contenido;
  }

  @Override
  public byte[] obtenerArchivoDeTrabajoWebDav(String pathRelativo, String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoWebDav(String, String) - start"); //$NON-NLS-1$
    }

    String fileName = pathRelativo + SLASH + nombreArchivo;
    FileAsStreamConnectionWebDav file = null;
    byte[] contenido = null;
    try {
      file = webDavService.getFileAsStream(fileName, null, null);
      contenido = IOUtils.toByteArray(file.getFileAsStream());
    } catch (WebDAVConnectionException wde) {
      LOGGER.error("Error obteniendo archivo del WebDav", wde);
      throw wde;
    } catch (IOException e) {
      LOGGER.error("Error obteniendo contenido del archivo del WebDav", e);
    } finally {
      if (file != null) {
        file.closeConnection();
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoWebDav(String, String) - end"); //$NON-NLS-1$
    }
    return contenido;
  }

  @Override
  public byte[] obtenerArchivoDeTrabajoWebDav(String nombreRecurso) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoWebDav(String) - start"); //$NON-NLS-1$
    }

    FileAsStreamConnectionWebDav file = null;
    byte[] contenido = null;
    try {
      file = webDavService.getFileAsStream(nombreRecurso, null, null);
      contenido = IOUtils.toByteArray(file.getFileAsStream());
    } catch (WebDAVConnectionException wde) {
      LOGGER.error("Error obteniendo archivo del WebDav", wde);
      throw wde;
    } catch (IOException e) {
      LOGGER.error("Error obteniendo contenido del archivo del WebDav", e);
    } finally {
      if (file != null) {
        file.closeConnection();
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoWebDav(String) - end"); //$NON-NLS-1$
    }
    return contenido;
  }

  @Override
  public void borrarArchivoTemporalWebDav(String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoTemporalWebDav(String) - start"); //$NON-NLS-1$
    }

    String url = this.obtenerUrlTemporal(nombreArchivo);
    webDavService.deleteFile(url, nombreArchivo, "", "");

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoTemporalWebDav(String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void borrarArchivoDeTrabajoTemporalWebDav(String pathRelativo, String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoDeTrabajoTemporalWebDav(String, String) - start"); //$NON-NLS-1$
    }
    String url = null;
    try {
			
    	url = this.obtenerUrlTemporalArchivoDeTrabajo(pathRelativo);
    	webDavService.deleteFile(url, nombreArchivo, "", "");

    }catch (WebDAVResourceNotFoundException e) {
    	LOGGER.warn("El recurso " + e.getMessage() + " no se pudo encotrar para eliminar, puede ser que se haya eliminado en un proceso previamente");
		}
    
    catch (Exception e) {
    	StringBuilder str = new StringBuilder();
    	str.append("Error al borrar el archivo temporal url=")
    	.append(url).append(" y nombre de archivo=").append(nombreArchivo)
    	.append(" mensaje de error: ").append(e.getMessage());
    	LOGGER.error(str.toString(), e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoDeTrabajoTemporalWebDav(String, String) - end"); //$NON-NLS-1$
    }
  }

  private String obtenerUrlTemporalArchivoDeTrabajo(String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlTemporalArchivoDeTrabajo(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = nombreArchivo.split("-");
    StringBuilder url = new StringBuilder("");

    String urlArray[] = { Constantes.CARPETA_TEMPORAL_DOCUMENTO_DE_TRABAJO, espacios[0],
        espacios[1], espacios[2], espacios[3] };
    for (String espacio : urlArray) {
      url.append(espacio + SLASH);
    }
    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlTemporalArchivoDeTrabajo(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  private String obtenerUrlDocumentoAdjuntoTemporal(String nombreDocumentoAdjunto) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlDocumentoAdjuntoTemporal(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = nombreDocumentoAdjunto.split("-");
    StringBuilder url = new StringBuilder("");
    String urlArray[] = { Constantes.CARPETA_TEMPORAL_DOCUMENTO_ADJUNTO, espacios[0], espacios[1],
        espacios[2], espacios[3] };
    for (String espacio : urlArray) {
      url.append(espacio + SLASH);
    }
    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlDocumentoAdjuntoTemporal(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  @Override
  public void borrarArchivoWebDav(String numero) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoWebDav(String) - start"); //$NON-NLS-1$
    }

    String url = obtenerUrl(numero);
    this.webDavService.deleteFile(url, numero + ".pdf", "", "");
    LOGGER.debug("Archivo borrado " + numero);
    this.webDavService.deleteSpace("", url, "", "");
    LOGGER.debug("Space borrado " + url);
  }

  @Override
  public FileAsStreamConnectionWebDav obtenerDocumento(String numeroDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerDocumento(String) - start"); //$NON-NLS-1$
    }

    String url = obtenerUrl(numeroDocumento) + numeroDocumento + ".pdf";
    FileAsStreamConnectionWebDav file = this.webDavService.getFileAsStream(url, null, null);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerDocumento(String) - end"); //$NON-NLS-1$
    }
    return file;
  }

  public FileAsStreamConnectionWebDav obtenerRecursoTemporalWebDavAsStream(
      String nombreArchivoTemporal) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerRecursoTemporalWebDavAsStream(String) - start"); //$NON-NLS-1$
    }

    FileAsStreamConnectionWebDav file;
    String url = this.obtenerUrlTemporal(nombreArchivoTemporal);
    String fileName = url + nombreArchivoTemporal;
    file = this.webDavService.getFileAsStream(fileName, null, null);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerRecursoTemporalWebDavAsStream(String) - end"); //$NON-NLS-1$
    }
    return file;
  }

  /**
   * Crea la estructura de directorios atendiendo la siguiente estructura:
   * Anio/Reparticion/2 primeros digitos n�mero SADE/3 siguientes digitos n�mero
   * SADE/nombre archivo/ Crea las ultimas 2 carpetas. Si no existe alguna
   * carpeta padre crea todas las carpetas y vuelve a ejecutar.
   * 
   * @param numeroDocumento:
   *          nombre del documento
   * @return url: El path en donde se almacenar� el documento.
   */
  private String crearEstructuraEspacios(String numDocumento, String carpetaRaiz) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraEspacios(String, String) - start"); //$NON-NLS-1$
    }

    String espacios[] = numDocumento.split("-");
    String anio = espacios[1];
    String numeroSADE = espacios[2];
    String numeroSADE1 = numeroSADE.substring(0, 2);
    String numeroSADE2 = numeroSADE.substring(2, 5);
    String reparticion = espacios[4];

    StringBuilder url = new StringBuilder(carpetaRaiz);
    url.append(SLASH);
    url.append(anio);
    url.append(SLASH);
    url.append(reparticion);
    url.append(SLASH);
    url.append(numeroSADE1);
    url.append(SLASH);

    try {
      this.webDavService.createSpace(url.toString(), numeroSADE2, "", "");
      url.append(numeroSADE2);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), numDocumento, "", "");
      url.append(numDocumento);

    } catch (WebDAVConflictException e) {
      LOGGER.error("Error al crear estructura de espacios. " + e.getMessage(), e);
      url = new StringBuilder(carpetaRaiz);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), anio, "", "");
      url.append(anio);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), reparticion, "", "");
      url.append(reparticion);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), numeroSADE1, "", "");

      String returnString = this.crearEstructuraEspacios(numDocumento, carpetaRaiz);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("crearEstructuraEspacios(String, String) - end"); //$NON-NLS-1$
      }
      return returnString;
    }

    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraEspacios(String, String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  private String crearEstructuraEspaciosVersionado(String numDocumento, String carpetaRaiz) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraEspaciosVersionado(String, String) - start"); //$NON-NLS-1$
    }

    String espacios[] = numDocumento.split("-");
    String anio = espacios[1];
    String numeroSADE = espacios[2];
    String numeroSADE1 = numeroSADE.substring(0, 2);
    String numeroSADE2 = numeroSADE.substring(2, 5);
    String reparticion = espacios[4];

    StringBuilder url = new StringBuilder(carpetaRaiz);
    url.append(SLASH);
    url.append(anio);
    url.append(SLASH);
    url.append(reparticion);
    url.append(SLASH);
    url.append(numeroSADE1);
    url.append(SLASH);
    url.append(numeroSADE2);
    url.append(SLASH);

    try {
      this.webDavService.createSpace(url.toString(), numDocumento, "", "");
      url.append(numDocumento);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(),
          numDocumento.replace("-", "").replace(" ", ""), "", "");
      url.append(numDocumento.replace("-", "").replace(" ", ""));

    } catch (WebDAVConflictException e) {
      LOGGER.error("Error al crear estructuras de espacios. " + e.getMessage(), e);
      // Faltan crear carpetas. Las creo de vuelta
      url = new StringBuilder(carpetaRaiz);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), anio, "", "");
      url.append(anio);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), reparticion, "", "");
      url.append(reparticion);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), numeroSADE1, "", "");
      url.append(numeroSADE1);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), numeroSADE2, "", "");
      url.append(numeroSADE2);
      url.append(SLASH);
      // lo vuelvo a ejecutar
      String returnString = this.crearEstructuraEspaciosVersionado(numDocumento, carpetaRaiz);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("crearEstructuraEspaciosVersionado(String, String) - end"); //$NON-NLS-1$
      }
      return returnString;
    }

    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraEspaciosVersionado(String, String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  /**
   * Obtiene el path en donde se almacen� el documento.
   * 
   * @param numeroDocumento:
   *          nombre del documento.
   * @return url.
   */
  private String obtenerUrl(String numeroDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrl(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = numeroDocumento.split("-");
    StringBuilder url = new StringBuilder("");
    String urlArray[] = { Constantes.CARPETA_RAIZ_DOCUMENTOS, espacios[1], espacios[4],
        espacios[2].substring(0, 2), espacios[2].substring(2, 5), numeroDocumento };
    for (String espacio : urlArray) {
      url.append(espacio + SLASH);
    }
    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrl(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  /**
   * Crea estructura de espacios para documentos de firma temporales Crea la
   * ultima carpeta del path. Si no existe alguna carpeta padre crea todas las
   * carpetas y vuelve a ejecutar.
   * 
   * @param nombreArchivo:
   *          Nombre del archivo temporal
   * @return url.
   */
  private String crearEstructuraTemporales(String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraTemporales(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = nombreArchivo.split("-");
    String anio = espacios[0];
    String mes = espacios[1];
    String dia = espacios[2];

    StringBuilder url = new StringBuilder(Constantes.CARPETA_TEMPORAL_PDFS_FIRMA);
    url.append(SLASH);
    url.append(anio);
    url.append(SLASH);
    url.append(mes);
    url.append(SLASH);

    try {
      this.webDavService.createSpace(url.toString(), dia, "", "");
      url.append(dia);
    } catch (WebDAVConflictException e) {
      LOGGER.error("Error al crear estructura de temporales. " + e.getMessage(), e);
      url = new StringBuilder(Constantes.CARPETA_TEMPORAL_PDFS_FIRMA);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), anio, "", "");
      url.append(anio);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), mes, "", "");
      url.append(mes);
      url.append(SLASH);

      String returnString = this.crearEstructuraTemporales(nombreArchivo);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("crearEstructuraTemporales(String) - end"); //$NON-NLS-1$
      }
      return returnString;
    }

    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraTemporales(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  private String crearEstructuraTemporalesVersionado(String nombreDirectorio) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraTemporalesVersionado(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = nombreDirectorio.split("-");
    String anio = espacios[0];
    String mes = espacios[1];
    String dia = espacios[2];

    StringBuilder url = new StringBuilder(Constantes.CARPETA_TEMPORAL_PDFS_FIRMA);
    url.append(SLASH);
    url.append(anio);
    url.append(SLASH);
    url.append(mes);
    url.append(SLASH);
    url.append(dia);
    url.append(SLASH);

    try {
      this.webDavService.createSpace(url.toString(), nombreDirectorio.replace("-", ""), "", "");
      url.append(nombreDirectorio.replace("-", ""));
      url.append(SLASH);
    } catch (WebDAVConflictException e) {
      LOGGER.error("Error al crear estructuras de temporales. " + e.getMessage(), e);

      url = new StringBuilder(Constantes.CARPETA_TEMPORAL_PDFS_FIRMA);
      url.append(SLASH);

      this.webDavService.createSpace(url.toString(), anio, "", "");
      url.append(anio);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), mes, "", "");
      url.append(mes);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), dia, "", "");
      url.append(dia);
      url.append(SLASH);

      String returnString = this.crearEstructuraTemporalesVersionado(nombreDirectorio);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("crearEstructuraTemporalesVersionado(String) - end"); //$NON-NLS-1$
      }
      return returnString;
    }

    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraTemporalesVersionado(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  /**
   * Crea estructura de espacios para documentos de firma temporales Crea la
   * ultima carpeta del path. Si no existe alguna carpeta padre crea todas las
   * carpetas y vuelve a ejecutar.
   * 
   * @param nombreArchivo:
   *          Nombre del archivo temporal
   * @return url.
   */
  private String crearEstructuraArchivosDeTrabajoTemporales(String pathRelativo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraArchivosDeTrabajoTemporales(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = pathRelativo.split("-");
    String anio = espacios[0];
    String mes = espacios[1];
    String dia = espacios[2];
    String idAleatorio = espacios[3];

    StringBuilder url = new StringBuilder(Constantes.CARPETA_TEMPORAL_DOCUMENTO_DE_TRABAJO);
    url.append(SLASH);
    url.append(anio);
    url.append(SLASH);
    url.append(mes);
    url.append(SLASH);
    url.append(dia);
    url.append(SLASH);

    try {

      this.webDavService.createSpace(url.toString(), idAleatorio, "", "");
      url.append(idAleatorio);

    } catch (WebDAVConflictException e) {
      LOGGER.error("Error al crear estructuras de archivos de trabajo. " + e.getMessage(), e);
      url = new StringBuilder(Constantes.CARPETA_TEMPORAL_DOCUMENTO_DE_TRABAJO);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), anio, "", "");
      url.append(anio);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), mes, "", "");
      url.append(mes);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), dia, "", "");
      url.append(dia);
      url.append(SLASH);

      String returnString = crearEstructuraArchivosDeTrabajoTemporales(pathRelativo);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("crearEstructuraArchivosDeTrabajoTemporales(String) - end"); //$NON-NLS-1$
      }
      return returnString;
    }

    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraArchivosDeTrabajoTemporales(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  /**
   * Crea estructura de espacios para documentos de firma temporales Crea la
   * ultima carpeta del path. Si no existe alguna carpeta padre crea todas las
   * carpetas y vuelve a ejecutar.
   * 
   * @param nombreArchivo:
   *          Nombre del archivo temporal
   * @return url.
   */

  /**
   * Crea estructura de espacios para documentos adjuntos temporales Crea la
   * ultima carpeta del path. Si no existe alguna carpeta padre crea todas las
   * carpetas y vuelve a ejecutar.
   * 
   * @param nombreArchivo:
   *          Nombre del documento adjunto temporal
   * @return url.
   */
  private String crearEstructuraDocumentoAdjuntosTemporales(String pathRelativo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraDocumentoAdjuntosTemporales(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = pathRelativo.split("-");
    String anio = espacios[0];
    String mes = espacios[1];
    String dia = espacios[2];
    String idAleatorio = espacios[3];

    StringBuilder url = new StringBuilder(Constantes.CARPETA_TEMPORAL_DOCUMENTO_ADJUNTO);
    url.append(SLASH);
    url.append(anio);
    url.append(SLASH);
    url.append(mes);
    url.append(SLASH);
    url.append(dia);
    url.append(SLASH);

    try {

      this.webDavService.createSpace(url.toString(), idAleatorio, "", "");
      url.append(idAleatorio);

    } catch (WebDAVConflictException e) {
      LOGGER.error("Error al crear estructuras de documentos adjuntos. " + e.getMessage(), e);
      url = new StringBuilder(Constantes.CARPETA_TEMPORAL_DOCUMENTO_ADJUNTO);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), anio, "", "");
      url.append(anio);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), mes, "", "");
      url.append(mes);
      url.append(SLASH);
      this.webDavService.createSpace(url.toString(), dia, "", "");
      url.append(dia);
      url.append(SLASH);

      String returnString = crearEstructuraDocumentoAdjuntosTemporales(pathRelativo);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("crearEstructuraDocumentoAdjuntosTemporales(String) - end"); //$NON-NLS-1$
      }
      return returnString;
    }

    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraDocumentoAdjuntosTemporales(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  @Override
  public String obtenerUrlTemporal(String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlTemporal(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = nombreArchivo.split("-");
    StringBuilder url = new StringBuilder("");
    String urlArray[] = { Constantes.CARPETA_TEMPORAL_PDFS_FIRMA, espacios[0], espacios[1],
        espacios[2] };
    for (String espacio : urlArray) {
      url.append(espacio + SLASH);
    }
    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlTemporal(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  public String obtenerUrlTemporalRevision(String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlTemporalRevision(String) - start"); //$NON-NLS-1$
    }

    String espacios[] = nombreArchivo.split("-");
    StringBuilder url = new StringBuilder("");
    String urlArray[] = { Constantes.CARPETA_TEMPORAL_PDFS_FIRMA, espacios[0], espacios[1],
        espacios[2] };
    for (String espacio : urlArray) {
      url.append(espacio + SLASH);
    }
    url.append(nombreArchivo.replace("-", ""));
    url.append(SLASH);
    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlTemporalRevision(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  /**
   * Crea el prefijo correspondiente a los archivos temporales de firma. Formato
   * YYYY-MM-
   * 
   * @return
   */
  private String obtenerPrefijoArchivoTemporal() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPrefijoArchivoTemporal() - start"); //$NON-NLS-1$
    }

    StringBuilder prefijo;
    Calendar cal = Calendar.getInstance();
    prefijo = new StringBuilder(String.valueOf(cal.get(Calendar.YEAR)) + "-");
    int mesActual = cal.get(Calendar.MONTH) + 1;
    int diaActual = cal.get(Calendar.DAY_OF_MONTH);
    String mes;
    if (mesActual < 10) {
      mes = "0" + mesActual;
    } else {
      mes = String.valueOf(mesActual);
    }
    String dia;
    if (diaActual < 10) {
      dia = "0" + diaActual;
    } else {
      dia = String.valueOf(diaActual);
    }
    prefijo.append(mes + "-");
    prefijo.append(dia + "-");
    String returnString = prefijo.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPrefijoArchivoTemporal() - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  @Override
  public String crearNombreArchivoTemporal() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearNombreArchivoTemporal() - start"); //$NON-NLS-1$
    }

    StringBuilder nombreArchivoTemporal = new StringBuilder(this.obtenerPrefijoArchivoTemporal());
    Long time = Calendar.getInstance().getTimeInMillis();
    nombreArchivoTemporal.append(time.toString());
    nombreArchivoTemporal.append(".pdf");
    String returnString = nombreArchivoTemporal.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearNombreArchivoTemporal() - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  @Override
  public String subirArchivoDeTrabajoWebDav(String numeroSade, byte[] contenido,
      String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoWebDav(String, byte[], String) - start"); //$NON-NLS-1$
    }

    String url = null;
    try {
      url = this.crearEstructuraEspacios(numeroSade, Constantes.CARPETA_RAIZ_DOCUMENTOS);
      webDavService.createFile(url, nombreArchivo, "", "", new ByteArrayInputStream(contenido));
    } catch (Exception e) {
      LOGGER.debug("No se puede adjuntar  el archivo en WebDav: " + numeroSade);
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoWebDav(String, byte[], String) - end"); //$NON-NLS-1$
    }
    return url;
  }

  /**
   * Permite almacenar las revisiones del pdf de rectificacion. el directorio es
   * el numero de sade.
   */
  @Override
  public String subirArchivoRevisionRectificacion(String directorio, byte[] contenido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoRevisionRectificacion(String, byte[]) - start"); //$NON-NLS-1$
    }

    StringBuilder url = new StringBuilder();
    try {
      url.append(this.crearEstructuraEspacios(directorio, Constantes.CARPETA_RAIZ_DOCUMENTOS));
      this.webDavService.createSpace(url.toString(), directorio, "", "");
      url.append(SLASH).append(directorio);
      List<WebDAVResourceBean> documentos = webDavService.listSpace(url.toString(), null, null);
      List<String> versiones = new ArrayList<>();
      List<Integer> versionRec;
      Integer ultimaVersion = Integer.valueOf(0);
      for (WebDAVResourceBean doc : documentos) {
        if (!doc.isDirectorio() && doc.getNombre().contains("rev")) {
          String[] salida = doc.getNombre().split("rev");
          salida = salida[1].split("\\.");
          String version = salida[0];
          versiones.add(version);
        }
      }
      if (!versiones.isEmpty()) {
        versionRec = new ArrayList<>();
        for (String s : versiones) {
          versionRec.add(Integer.valueOf(s));
        }
        ultimaVersion = Collections.max(versionRec) + 1;
      }
      StringBuilder nombreArchivo = new StringBuilder(directorio);
      nombreArchivo.append("-rev").append(ultimaVersion.toString()).append(".pdf");
      webDavService.createFile(url.toString(), nombreArchivo.toString(), "", "",
          new ByteArrayInputStream(contenido));
      LOGGER.info("Se subio correctamente la revision del pdf rectificado.");
    } catch (Exception e) {
      LOGGER.debug("No se puede adjuntar  el archivo en WebDav:", e);

    }
    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoRevisionRectificacion(String, byte[]) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  @Override
  public int getFileSize(String nombreArchivoTemporal) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getFileSize(String) - start"); //$NON-NLS-1$
    }

    String url;
    int size;
    url = this.obtenerUrlTemporal(nombreArchivoTemporal);
    String fileName = url + nombreArchivoTemporal;
    try {
      size = webDavService.getFileSize(fileName, null, null);
    } catch (WebDAVConnectionException wde) {
      LOGGER.error(wde.getMessage());
      throw wde;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getFileSize(String) - end"); //$NON-NLS-1$
    }
    return size;
  }

  @Override
  public String obtenerUbicacionDescarga(String numeroDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUbicacionDescarga(String) - start"); //$NON-NLS-1$
    }

    String returnString = obtenerUrl(numeroDocumento) + numeroDocumento + ".pdf";
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUbicacionDescarga(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  @Override
  public String subirArchivoDeTrabajoWebDavOriginal(String numeroSade, byte[] contenido,
      String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoWebDavOriginal(String, byte[], String) - start"); //$NON-NLS-1$
    }

    String url = null;
    try {
      url = this.crearEstructuraEspacios(numeroSade, Constantes.CARPETA_RAIZ_DOCUMENTOS_ORIGINAL);
      webDavService.createFile(url, nombreArchivo, "", "", new ByteArrayInputStream(contenido));
    } catch (Exception e) {
      LOGGER.debug("No se puede adjuntar  el archivo en WebDav: " + numeroSade);
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoWebDavOriginal(String, byte[], String) - end"); //$NON-NLS-1$
    }
    return url;
  }

}
