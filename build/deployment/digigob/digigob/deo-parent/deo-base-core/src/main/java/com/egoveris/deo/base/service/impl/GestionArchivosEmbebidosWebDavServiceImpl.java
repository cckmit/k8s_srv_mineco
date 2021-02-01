package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.service.GestionArchivosEmbebidosWebDavService;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shareddocument.base.exception.WebDAVConflictException;
import com.egoveris.shareddocument.base.exception.WebDAVConnectionException;
import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service("gestionArchivosEmbebidosWebDavServiceImpl")
@Transactional
public class GestionArchivosEmbebidosWebDavServiceImpl extends GestionArchivosWebDavServiceImpl
    implements GestionArchivosEmbebidosWebDavService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory
      .getLogger(GestionArchivosEmbebidosWebDavServiceImpl.class);

  public GestionArchivosEmbebidosWebDavServiceImpl() {
  }

  public void subirArchivoEmbebidoTemporalWebDav(ArchivoEmbebidoDTO archivoEmbebido,
      byte[] contenido) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoEmbebidoTemporalWebDav(ArchivoEmbebidoDTO, byte[]) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.crearEstructuraArchivosEmbebidos(archivoEmbebido.getPathRelativo());
    webDavService.createFile(url, archivoEmbebido.getNombreArchivo(), "", "",
        new ByteArrayInputStream(contenido));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoEmbebidoTemporalWebDav(ArchivoEmbebidoDTO, byte[]) - end"); //$NON-NLS-1$
    }
  }

  public byte[] obtenerArchivosEmbebidosWebDav(String pathRelativo, String nombreArchivo)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivosEmbebidosWebDav(String, String) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.obtenerUrlTemporalArchivoEmbebido(pathRelativo);
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
      LOGGER.debug("obtenerArchivosEmbebidosWebDav(String, String) - end"); //$NON-NLS-1$
    }
    return contenido;
  }

  public void borrarArchivoEmbebidoWebDav(String pathRelativo, String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoEmbebidoWebDav(String, String) - start"); //$NON-NLS-1$
    }

    String url;
    url = this.obtenerUrlTemporalArchivoEmbebido(pathRelativo);
    webDavService.deleteFile(url, nombreArchivo, "", "");

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoEmbebidoWebDav(String, String) - end"); //$NON-NLS-1$
    }
  }

  public String crearEstructuraArchivosEmbebidos(String pathRelativo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraArchivosEmbebidos(String) - start"); //$NON-NLS-1$
    }

    String[] espacios = pathRelativo.split("-");
    String anio = espacios[0];
    String mes = espacios[1];
    String dia = espacios[2];
    String idAleatorio = espacios[3];

    StringBuilder url = new StringBuilder(Constantes.CARPETA_TEMPORAL_ARCHIVOS_EMBEBIDOS);
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
      LOGGER.error("Error al crear estructura de archivos embebidos. " + e.getMessage(), e);

      url = new StringBuilder(Constantes.CARPETA_TEMPORAL_ARCHIVO_EMBEBIDO);
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

      String returnString = crearEstructuraArchivosEmbebidos(pathRelativo);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("crearEstructuraArchivosEmbebidos(String) - end"); //$NON-NLS-1$
      }
      return returnString;
    }

    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crearEstructuraArchivosEmbebidos(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  public String obtenerUrlTemporalArchivoEmbebido(String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlTemporalArchivoEmbebido(String) - start"); //$NON-NLS-1$
    }

    String[] espacios = nombreArchivo.split("-");
    StringBuilder url = new StringBuilder("");

    String[] urlArray = { Constantes.CARPETA_TEMPORAL_ARCHIVO_EMBEBIDO, espacios[0], espacios[1],
        espacios[2], espacios[3] };
    for (String espacio : urlArray)
      url.append(espacio + SLASH);
    String returnString = url.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUrlTemporalArchivoEmbebido(String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

}
