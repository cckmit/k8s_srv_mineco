package com.egoveris.te.base.service;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shareddocument.base.exception.WebDAVConnectionException;
import com.egoveris.shareddocument.base.exception.WebDAVResourceNotFoundException;
import com.egoveris.shareddocument.base.model.FileAsStreamConnection;
import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.util.ConstantesWeb;

@Service
@Transactional
public class AccesoWebDavServicesImpl implements IAccesoWebDavService {

  // Servicios que llamo
  @Autowired
  private IWebDavService webDavService;
  private static transient Logger logger = LoggerFactory.getLogger(AccesoWebDavServicesImpl.class);

  /**
   * Metodo que permite buscar un archivo o documento de trabajo en el
   * repositorio
   * 
   * @autor IES
   * 
   *        Servicios utilizados:IWebDavService webDavService Métodos utilizados
   *        del servicio:getFileAsStream( String path, "", "");
   * 
   *        Variables importantes utilizadas:
   * 
   * @param String
   *          path :Cadena que se usa como parámetro en el método
   *          BufferedInputStream getFileAsStream(String path,"","") para
   *          busqueda del archivo o documento de trabajo.
   * 
   * 
   *          *BufferedInputStream fileInputStream : Variable que recibe el
   *          resultado tipo InputStream del método getFileAsStream().
   * 
   * @return fileInputStream :Contiene el documento o archvo en bytes
   *         (fileAsStream).
   * 
   * 
   * 
   */

  public BufferedInputStream visualizarDocumento(String path)
      throws DocumentoOArchivoNoEncontradoException {
    if (logger.isDebugEnabled()) {
      logger.debug("visualizarDocumento(path={}) - start", path);
    }

    BufferedInputStream fileInputStream = null;
    FileAsStreamConnection fileAsStream = null;
    try {
      fileAsStream = this.webDavService.getFileAsStream(path, "", "");
      fileInputStream = fileAsStream.getFileAsStream();

    } catch (WebDAVResourceNotFoundException e) {
      logger.error("visualizarDocumento(String)", e);

      throw new DocumentoOArchivoNoEncontradoException("No se pudo obtener el documento " + path,
          e);

    } catch (NullPointerException e) {
      logger.error("visualizarDocumento(String)", e);

      throw new DocumentoOArchivoNoEncontradoException("No se pudo obtener el documento " + path,
          e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("visualizarDocumento(String) - end - return value={}", fileInputStream);
    }
    return fileInputStream;
  }

  public byte[] obtenerArchivoDeTrabajoWebDav(String path, String nombre) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerArchivoDeTrabajoWebDav(path={}, nombre={}) - start", path, nombre);
    }

    String fileName = path + "/" + nombre;
    FileAsStreamConnectionWebDav file = null;
    byte[] contenido = null;
    try {
      file = webDavService.getFileAsStream(fileName, null, null);
      contenido = IOUtils.toByteArray(file.getFileAsStream());
    } catch (WebDAVConnectionException wde) {
      logger.error("Error obteniendo archivo del WebDav", wde);
      throw wde;
    } catch (IOException e) {
      logger.error("Error obteniendo contenido del archivo del WebDav", e);
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerArchivoDeTrabajoWebDav(String, String) - end - return value={}",
          contenido);
    }
    return contenido;
  }

  @Override
  public FileAsStreamConnectionWebDav obtenerDocumento(String numeroDocumento) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumento(numeroDocumento={}) - start", numeroDocumento);
    }

    String url = obtenerUrl(numeroDocumento) + numeroDocumento + ".pdf";
    FileAsStreamConnectionWebDav file = this.webDavService.getFileAsStream(url, null, null);

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumento(String) - end - return value={}", file);
    }
    return file;
  }

  private String obtenerUrl(String numeroDocumento) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUrl(numeroDocumento={}) - start", numeroDocumento);
    }

    String espacios[] = numeroDocumento.split("-");
    StringBuilder url = new StringBuilder("");
    String urlArray[] = { ConstantesWeb.CARPETA_RAIZ_DOCUMENTOS, espacios[1], espacios[4],
        espacios[2].substring(0, 2), espacios[2].substring(2, 5), numeroDocumento };
    for (String espacio : urlArray)
      url.append(espacio + "/");
    String returnString = url.toString();
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUrl(String) - end - return value={}", returnString);
    }
    return returnString;
  }
}
