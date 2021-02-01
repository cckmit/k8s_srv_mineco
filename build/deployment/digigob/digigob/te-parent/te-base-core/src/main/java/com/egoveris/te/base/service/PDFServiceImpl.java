package com.egoveris.te.base.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.transaction.NotSupportedException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.exception.GenerarPdfException;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Deprecated
@Service
@Transactional
public class PDFServiceImpl {

  private static transient Logger logger = LoggerFactory.getLogger(PDFServiceImpl.class);

  private Configuration freeMarker = new Configuration();
  
  // @Autowired
  // private GeneratePdfServiceConstructor generatePdfService;

  public byte[] generarPDF(final Map<String, Object> parametros) throws GenerarPdfException, NotSupportedException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPDF(parametros={}) - start", parametros);
    }

    throw new NotSupportedException(
        "Generación de PDF no está soportado si no es através de DEO.");
  }

  private byte[] generarTemplate(final Map<String, Object> parametros) throws IOException {

    logger.info("Generando el PDF que informa de los pases del Expediente Track: "
        + parametros.get("letra") + " " + parametros.get("anio") + " " + parametros.get("numero")
        + " " + parametros.get("reparticionActuacion") + " "
        + parametros.get("reparticionUsuario"));
    final Template template = freeMarker.getTemplate("pdfTemplate");
    final StringWriter processedTemplateWriter = new StringWriter();
    try {
      template.process(parametros, processedTemplateWriter);
      final byte[] returnbyteArray = String.valueOf(processedTemplateWriter.getBuffer())
          .getBytes();
      if (logger.isDebugEnabled()) {
        logger.debug("generarTemplate(Map<String,Object>) - end - return value={}",
            returnbyteArray);
      }
      return returnbyteArray;
    } catch (final Exception e) {
      logger.error("Error al generar el PDF ", e);
      throw new IOException("Ha ocurrido un error en la generacion del PDF");
    }
  }

  public void initFreeMarker() {
    if (logger.isDebugEnabled()) {
      logger.debug("initFreeMarker() - start");
    }

    freeMarker = new Configuration();
    final StringTemplateLoader stringLoader = new StringTemplateLoader();
    final StringBuilder cadenaLogo = new StringBuilder();
    cadenaLogo.append("/templates");
    cadenaLogo.append("/pdfTemplate.ftl");
    final InputStream template = getClass().getResourceAsStream(cadenaLogo.toString());
    stringLoader.putTemplate("pdfTemplate", transformarInToString(template));

    freeMarker.setTemplateLoader(stringLoader);

    if (logger.isDebugEnabled()) {
      logger.debug("initFreeMarker() - end");
    }
  }

  private String transformarInToString(final InputStream co) {
    if (logger.isDebugEnabled()) {
      logger.debug("transformarInToString(co={}) - start", co);
    }

    final StringBuilder sb = new StringBuilder();
    String line;

    try {
      final BufferedReader reader = new BufferedReader(new InputStreamReader(co, "UTF-8"));
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (final UnsupportedEncodingException uee) {
      logger.error("Encoding no soportado", uee);
    } catch (final IOException ioe) {
      logger.error("No se pudo cerrar el archivo", ioe);
    } finally {
      try {
        co.close();
      } catch (final IOException ioe) {
        logger.error("No se pudo cerrar el archivo", ioe);
      }
    }
    final String returnString = sb.toString();
    if (logger.isDebugEnabled()) {
      logger.debug("transformarInToString(InputStream) - end - return value={}", returnString);
    }
    return returnString;
  }

}
