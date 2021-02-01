package com.egoveris.vucfront.base.service.impl;

import com.egoveris.shareddocument.base.model.WebDAVResourceBean;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.vucfront.base.service.WebDavService;
import com.egoveris.vucfront.base.util.Constantes;
import com.egoveris.vucfront.base.util.RandomFileNameGenerator;
import com.egoveris.vucfront.model.model.DocumentoDTO;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WebDavServiceImpl implements WebDavService {

  private Logger logger = LoggerFactory.getLogger(WebDavServiceImpl.class);

  @Autowired
  private IWebDavService webDavService;

  @Override
  public Map<String, String> uploadDocuments(List<DocumentoDTO> documentList) {
    // nombreOriginal, path
    Map<String, String> retorno = new HashMap<>();
    // Get the URL for uploading the documents.
    String urlToUpload = this.crearEstructuraEspacios();

    // Upload only if the Documento has an array of bytes (recently uploaded
    // documents)
    for (DocumentoDTO aux : documentList) {
      // Generate unique filename
      final String fileName = RandomFileNameGenerator.generateFilename(aux.getNombreOriginal());
      // Upload
      webDavService.createFile(urlToUpload, fileName, "", "",
          new ByteArrayInputStream(aux.getArchivo()));
      // Add the path of the saved document in the return map.
      retorno.put(aux.getNombreOriginal(), urlToUpload.concat(fileName));
    }

    return retorno;
  }

  @Override
  public void deleteDocument(DocumentoDTO documento) {
    if (webDavService.existFile(documento.getUrlTemporal(), "", "")) {
      webDavService.deleteFile("", documento.getUrlTemporal(), "", "");
      logger.info("### DOCUMENTO BORRADO DE WEBDAV: ".concat(documento.getUrlTemporal()));
    }
  }

  /**
   * Creates the folders in WebDav to upload the VUC Documents with the
   * following structure:
   * Constantes.CARPETA_RAIZ_DOCUMENTOS/currentYear/currentMonth
   * 
   * @return The full path of the upload directory.
   */
  private String crearEstructuraEspacios() {
    String urlToUpload;
    final String slash = "/";
    LocalDate date = LocalDate.now();
    final String year = String.valueOf(date.getYear()).concat(slash);
    final String month = year.concat(String.valueOf(date.getMonthValue())).concat(slash);

    // Check if root folder exists or create it.
    if (!existsFullpathInWebDav(Constantes.CARPETA_RAIZ_DOCUMENTOS)) {
      this.webDavService.createSpace(null, Constantes.CARPETA_RAIZ_DOCUMENTOS, "", "");
    }
    // Check if year folder exists or create it.
    if (!existsFullpathInWebDav(Constantes.CARPETA_RAIZ_DOCUMENTOS.concat(year))) {
      this.webDavService.createSpace(Constantes.CARPETA_RAIZ_DOCUMENTOS, year, "", "");
    }
    // Check if month folder exists or create it.
    if (!existsFullpathInWebDav(Constantes.CARPETA_RAIZ_DOCUMENTOS.concat(month))) {
      this.webDavService.createSpace(Constantes.CARPETA_RAIZ_DOCUMENTOS, month, "", "");
    }

    urlToUpload = Constantes.CARPETA_RAIZ_DOCUMENTOS.concat(month);
    return urlToUpload;
  }

  @Override
  public byte[] getDocumentoByteArray(String fullFilePath) {
    byte[] retorno = null;
    WebDAVResourceBean result = webDavService.getFile(fullFilePath, "", "");
    if (result != null) {
      retorno = result.getArchivo();
    }
    return retorno;
  }

  /**
   * Checks if the selected full path exits in WebDav.
   * 
   * @param fullPath
   * @return
   */
  private boolean existsFullpathInWebDav(String fullPath) {
    boolean result = false;
    if (webDavService.existFile(fullPath, "", "")) {
      result = true;
    }
    return result;
  }

}
