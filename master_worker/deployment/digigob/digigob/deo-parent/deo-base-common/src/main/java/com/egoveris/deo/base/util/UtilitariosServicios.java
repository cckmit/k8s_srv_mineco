package com.egoveris.deo.base.util;

import com.egoveris.deo.base.exception.ExtensionesFaltantesException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class UtilitariosServicios {

  private static final Logger logger = LoggerFactory.getLogger(UtilitariosServicios.class);

  public static byte[] readInputStream(InputStream in) throws IOException {
    BufferedInputStream bis = new BufferedInputStream(in);
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    int result = bis.read();
    while (result != -1) {
      byte b = (byte) result;
      buf.write(b);
      result = bis.read();
    }
    return buf.toByteArray();
  }

  public static byte[] descargarArchivoTemporal(String identificador, String nombreArch) {
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(System.getProperty("java.io.tmpdir")
          + System.getProperty("file.separator") + identificador + "_" + nombreArch);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      int read;
      read = inputStream.read();
      while (read != -1) {
        bos.write(read);
        read = inputStream.read();
      }
      return bos.toByteArray();
    } catch (FileNotFoundException e) {
      System.out.println("\n" + e + "\n");
    } catch (IOException e) {
      System.out.println("\n" + e + "\n");
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          logger.error("Error al cerrar el input stream", e);
        }
      }
    }
    return null;
  }

  /**
   * devuelve el nombre del archivo con extension .pdf para poder abrirlo
   * 
   * @param nombre
   * @return
   */
  public static String convertirNombreAPDF(String nombre) {
    int pos = nombre.lastIndexOf('.');
    return nombre.substring(0, pos) + ".pdf";

  }

  public static String encodePwd(String typePwd, String pwd) {
    try {
      byte[] b = (MessageDigest.getInstance(typePwd).digest(pwd.getBytes()));
      return "{" + typePwd + "}" + new String(Base64.encodeBase64(b));
    } catch (NoSuchAlgorithmException e) {
      logger.error("Mensaje de error", e);
    }
    return null;
  }

  public static String formatearCodigoCaratulaFromSade(String codigoCaratula) {
    String result = null;
    if (StringUtils.isNotEmpty(codigoCaratula) && codigoCaratula.length() > 17) {
      result = (codigoCaratula.substring(0, 2) + '-' + codigoCaratula.substring(2, 6) + '-'
          + codigoCaratula.substring(6, 14) + '-' + codigoCaratula.substring(14, 17) + '-'
          + codigoCaratula.substring(17, codigoCaratula.length())).trim();
    }
    return result;
  }

  public static String obtenerCodigoReparticion(String codigoCaratula) {
    String result = null;
    if (StringUtils.isNotEmpty(codigoCaratula) && codigoCaratula.length() > 17) {
      result = (codigoCaratula.substring(21, codigoCaratula.length())).trim();
    }
    return result;
  }

  public static String obtenerUserName(String nombreUsuario) {
    String result = "";
    if (StringUtils.isNotEmpty(nombreUsuario)) {
      String[] ual = StringUtils.split(nombreUsuario, '(');
      if (ual.length > 1) {
        String[] eul = StringUtils.split(ual[2], ')');
        if (eul.length > 0)
          result = eul[0];
      }
    }
    return result;
  }

  public static String obtenerNombreUsuario(String nombreUsuario) {
    String result = "";
    if (StringUtils.isNotEmpty(nombreUsuario)) {
      String[] ual = StringUtils.split(nombreUsuario, '(');
      if (ual.length > 0)
        result = ual[0];
    }
    return result;

  }

  public static String completarConCerosNumActuacion(String numActuacion) {
    String aux = numActuacion;
    while (aux.length() < 8) {
      aux = "0" + aux;
    }
    return aux;
  }

  /**
   * Identifica el content-type de la información almacenada en el arreglo de
   * bytes dado.
   * 
   * @param datos
   * @return String con el content-type identificado.
   * @throws IOException 
   */
  public static String obtenerTipoContenido(byte[] datos) throws ValidacionContenidoException, IOException {
    String extensionTipo = null;
    InputStream is = new ByteArrayInputStream(datos);
    ContentHandler contenthandler = new BodyContentHandler(-1);
    Metadata metadata = new Metadata();
    Parser parser = new AutoDetectParser();
    ParseContext parserContext = new ParseContext();
    TikaConfig config = TikaConfig.getDefaultConfig();
    try {
      parser.parse(is, contenthandler, metadata, parserContext);
      MimeType mimeType = config.getMimeRepository().forName(metadata.get(Metadata.CONTENT_TYPE));
      extensionTipo = StringUtils.removeStart(mimeType.getExtension(), ".");
    } catch (SAXException e) {
      logger.error("Error al decodificar el contenido", e);
      throw new ValidacionContenidoException("Error al decodificar el contenido", e);
    } catch (TikaException e) {
      logger.error("Error al identificar el contenido", e);
      throw new ValidacionContenidoException("Error al identificar el contenido", e);
    }
    return extensionTipo;
  }

  /**
   * Borra un archivo temporal, las excepciones ocurridas no se propagan, dado
   * que se define que los errores generados en el borrado de archivos
   * temporales no interrumpen el flujo de ejecución.
   * 
   * @param archivo
   */
  public static void borrarArchivoTemporal(File archivo) {
    if (archivo != null && archivo.exists()) {
      try {
        archivo.delete();
      } catch (Exception e) {
        logger.debug("No se permite borrar archivo temporal en importación", e);
      }
    }
  }

  /**
   * Obtiene la extensión del archivo correspondiente al tipo de contenido.
   * 
   * @param tipoContenido
   * @return
   * @throws MimeTypeException 
   * @throws Exception
   */
  public static String obtenerExtensionArchivo(String tipoContenido) throws ExtensionesFaltantesException, MimeTypeException {
    String extension = null;
    try {
      TikaConfig config = TikaConfig.getDefaultConfig();
      extension = config.getMimeRepository().forName(tipoContenido).getExtension();
    } catch (ExtensionesFaltantesException e) {
      logger.error("Error al identificar el contenido", e);
      throw new ExtensionesFaltantesException("Error al identificar el contenido", e);
    }
    return extension;

  }

  // *********************************numerador**************************
  public static String armarCodigoCaratula(String tipoActuacion, int numero, int anio,
      String codigoReparticion, String codigoEcosistema) {
    String nro = Integer.toString(numero);
    String anioActuacion = Integer.toString(anio);
    String nroExtra = completarConCerosNumActuacion(nro);
    String secuencia = codigoEcosistema;

    return tipoActuacion + "-" + anioActuacion + "-" + nroExtra + "-" + secuencia + "-"
        + codigoReparticion.trim();
  }

}