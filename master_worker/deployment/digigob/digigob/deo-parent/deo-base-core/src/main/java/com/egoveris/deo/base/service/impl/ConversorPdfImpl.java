package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.PDFConversionException;
import com.egoveris.deo.base.service.ConversorPdf;
import com.egoveris.tica.base.model.ResponseDocumento;
import com.egoveris.tica.base.service.ExternalTicaPdfService;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversorPdfImpl implements ConversorPdf {
  final static Logger logger = LoggerFactory.getLogger(ConversorPdfImpl.class);

  // tica
  @Autowired
  ExternalTicaPdfService externalTicaPdf;

  public byte[] crearPDF(byte[] data, String tipoArchivo) throws PDFConversionException {
    if (logger.isDebugEnabled()) {
      logger.debug("crearPDF(byte[], String) - start"); //$NON-NLS-1$
    }

    byte[] contenidoPDF = null;

    ResponseDocumento response = new ResponseDocumento();

    response.setData(data);
    try {
      FileUtils.writeByteArrayToFile(new File("c:\\firmadoHtml.pdf"), data);

      FileUtils.writeByteArrayToFile(new File("c:\\firmadoHtml2.pdf"), data);
    } catch (Exception e) {
      logger.error("crearPDF(byte[], String)", e); //$NON-NLS-1$

      throw new PDFConversionException(e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("crearPDF(byte[], String) - end"); //$NON-NLS-1$
    }
    return contenidoPDF;
  }

  public byte[] convertirHTMLaPDF(byte[] data) throws PDFConversionException {
    if (logger.isDebugEnabled()) {
      logger.debug("convertirHTMLaPDF(byte[]) - start"); //$NON-NLS-1$
    }

    byte[] contenidoPDF = null;

    try {
      contenidoPDF = externalTicaPdf.htmlToPdf(data);
    } catch (Exception e) {
      logger.error("Error en convertir HTML a PDF", e);
      throw new PDFConversionException("Error en convertir HTML a PDF", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("convertirHTMLaPDF(byte[]) - end"); //$NON-NLS-1$
    }
    return contenidoPDF;
  }

}
