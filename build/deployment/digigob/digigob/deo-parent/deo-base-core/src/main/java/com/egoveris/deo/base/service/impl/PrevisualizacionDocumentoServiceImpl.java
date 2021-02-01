package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.IPrevisualizacionDocumentoService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class PrevisualizacionDocumentoServiceImpl implements IPrevisualizacionDocumentoService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory
      .getLogger(PrevisualizacionDocumentoServiceImpl.class);

  @Autowired
  private BuscarDocumentosGedoService buscarDocumentosGedoService;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @Value("${app.max.preview}")
  private Integer maxPreview;

  public byte[] obtenerPrevisualizacionDocumentoReducida(String numeroSade)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPrevisualizacionDocumentoReducida(String) - start"); //$NON-NLS-1$
    }

    byte[] contenido = obtenerPDF(this.buscarDocumentosGedoService
        .buscarDocumentoPorNumero(numeroSade).getIdGuardaDocumental(), numeroSade);
    byte[] returnbyteArray = generarPdfPrevisualizacion(contenido);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPrevisualizacionDocumentoReducida(String) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  private byte[] obtenerPDF(String idGuardaDocumental, String numeroSade)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPDF(String, String) - start"); //$NON-NLS-1$
    }

    byte[] contenido;

    // WEBDAV
    contenido = this.gestionArchivosWebDavService.obtenerArchivoDeTrabajoWebDav(
        this.gestionArchivosWebDavService.obtenerUbicacionDescarga(numeroSade));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPDF(String, String) - end"); //$NON-NLS-1$
    }
    return contenido;
  }

  private byte[] generarPdfPrevisualizacion(byte[] pdf) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarPdfPrevisualizacion(byte[]) - start"); //$NON-NLS-1$
    }

    PdfReader reader;
    byte[] returnbyteArray = new byte[0];
    try {
      reader = new PdfReader(pdf);
      reader.selectPages("1-" + maxPreview);
      PdfStamper stamper;
      ByteArrayOutputStream baos;
      baos = new ByteArrayOutputStream();
      stamper = new PdfStamper(reader, baos);
      stamper.close();
      reader.close();
      returnbyteArray = baos.toByteArray();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("generarPdfPrevisualizacion(byte[]) - end"); //$NON-NLS-1$
      }
    } catch (IOException e) {
      LOGGER.warn("generarPdfPrevisualizacion(byte[]) - exception ignored", e); //$NON-NLS-1$

    } catch (DocumentException e) {
      LOGGER.warn("generarPdfPrevisualizacion(byte[]) - exception ignored", e); //$NON-NLS-1$
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarPdfPrevisualizacion(byte[]) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  public byte[] obtenerPrevisualizacionDocumentoReducidaBytes(byte[] pdf)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPrevisualizacionDocumentoReducidaBytes(byte[]) - start"); //$NON-NLS-1$
    }

    byte[] returnbyteArray = generarPdfPrevisualizacion(pdf);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPrevisualizacionDocumentoReducidaBytes(byte[]) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  public int obtenerMaximoPrevisualizacion() {
    return maxPreview;
  }

}
