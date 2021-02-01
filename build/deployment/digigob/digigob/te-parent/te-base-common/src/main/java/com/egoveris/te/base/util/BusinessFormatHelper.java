package com.egoveris.te.base.util;

import com.egoveris.te.model.exception.ParametroIncorrectoException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hace las conversiones entre formatos necesarias por cuestiones de negocio.
 * TODO: revisar las implementaciones, fueron copiadas de otra clase.
 *
 * @author rgalloci
 *
 */
public class BusinessFormatHelper {

  private static Logger logger = LoggerFactory.getLogger(BusinessFormatHelper.class);

  /**
   * Realiza un padding a izquierda con 0 del numero de actuacion.
   *
   * @param numActuacion
   * @return
   */
  public static String completarConCerosNumActuacion(final Integer numActuacion) {
    if (logger.isDebugEnabled()) {
      logger.debug("completarConCerosNumActuacion(numActuacion={}) - start", numActuacion);
    }

    String aux = numActuacion.toString();
    while (aux.length() < 8) {
      aux = "0" + aux;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("completarConCerosNumActuacion(Integer) - end - return value={}", aux);
    }
    return aux;
  }

  /**
   * Obtengo el codigo del expediente de la fomra que se esta guardando en el
   * HISTORIAL DE OPERACIONES (EX20111542MGEYA-MGEYA)
   *
   * @param codigoExpediente
   *          (con ceros)
   * @return
   */
  public static String formarCodigoExpedienteParaHistorialDeOperaciones(
      final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("formarCodigoExpedienteParaHistorialDeOperaciones(codigoExpediente={}) - start",
          codigoExpediente);
    }

    final StringBuilder strb = new StringBuilder();
    strb.append(obtenerActuacion(codigoExpediente));
    strb.append(obtenerAnio(codigoExpediente));
    strb.append(quitaCerosNumeroActuacion(obtenerNumeroSade(codigoExpediente)));
    strb.append(obtenerReparticionActuacion(codigoExpediente)); // strb.append("MGEYA-");
    strb.append("-");
    strb.append(obtenerReparticionUsuario(codigoExpediente));

    final String returnString = strb.toString();
    if (logger.isDebugEnabled()) {
      logger.debug(
          "formarCodigoExpedienteParaHistorialDeOperaciones(String) - end - return value={}",
          returnString);
    }
    return returnString;
  }

  /**
   * Obtengo el nuevo path para guardar y buscar los archivos/documentos en
   * WebDavApache
   * 
   * @param codigoExpediente
   * @return
   */
  public static String formarPathDocumentoDeTrabajoWebDavApache(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("formarPathDocumentoDeTrabajoWebDavApache(codigoExpediente={}) - start",
          codigoExpediente);
    }

    final StringBuilder path = new StringBuilder();
    path.append("Documentos_De_Trabajo");
    path.append("/");
    path.append(obtenerAnio(codigoExpediente));
    path.append("/");
    path.append(obtenerReparticionUsuario(codigoExpediente));
    path.append("/");
    path.append(
        completarConCerosNumActuacion(obtenerNumeroSade(codigoExpediente)).substring(0, 2));
    path.append("/");
    path.append(
        completarConCerosNumActuacion(obtenerNumeroSade(codigoExpediente)).substring(2, 5));
    path.append("/");
    path.append(codigoExpediente);

    final String returnString = path.toString();
    if (logger.isDebugEnabled()) {
      logger.debug("formarPathDocumentoDeTrabajoWebDavApache(String) - end - return value={}",
          returnString);
    }
    return returnString;

  }

  /**
   * Obtengo el nuevo path para guardar y buscar los archivos/documentos en
   * WebDavApache
   *
   * @param codigoExpediente
   * @return
   */
  public static String formarPathWebDavApache(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("formarPathWebDavApache(codigoExpediente={}) - start", codigoExpediente);
    }

    final StringBuilder path = new StringBuilder();
    path.append(obtenerAnio(codigoExpediente));
    path.append("/");
    path.append(obtenerReparticionUsuario(codigoExpediente));
    path.append("/");
    path.append(
        completarConCerosNumActuacion(obtenerNumeroSade(codigoExpediente)).substring(0, 2));
    path.append("/");
    path.append(
        completarConCerosNumActuacion(obtenerNumeroSade(codigoExpediente)).substring(2, 5));
    path.append("/");
    path.append(codigoExpediente);

    final String returnString = path.toString();
    if (logger.isDebugEnabled()) {
      logger.debug("formarPathWebDavApache(String) - end - return value={}", returnString);
    }
    return returnString;

  }

  public static String formarPathWebDavApacheSinEspacio(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("formarPathWebDavApacheSinEspacio(codigoExpediente={}) - start",
          codigoExpediente);
    }

    final String returnString = StringUtils
        .deleteWhitespace(formarPathWebDavApache(codigoExpediente));
    if (logger.isDebugEnabled()) {
      logger.debug("formarPathWebDavApacheSinEspacio(String) - end - return value={}",
          returnString);
    }
    return returnString;
  }

  /**
   * Agrega 0 a la izquierda al numero del expediente
   * 
   * @param codigoEE
   * @return
   */
  public static String formatearCaratula(final String codigoEE) {
    if (logger.isDebugEnabled()) {
      logger.debug("formatearCaratula(codigoEE={}) - start", codigoEE);
    }

    final String[] partesCodigo = codigoEE.split("-");
    final String caratula = StringUtils.leftPad(partesCodigo[2], 8, "0");
    final String returnString = partesCodigo[0] + "-" + partesCodigo[1] + "-" + caratula + "-"
        + partesCodigo[3] + "-" + partesCodigo[4] + "-" + partesCodigo[5];
    if (logger.isDebugEnabled()) {
      logger.debug("formatearCaratula(String) - end - return value={}", returnString);
    }
    return returnString;
  }

  /**
   * Agrega '-' al codigo de caratula para darle el formato usado por el
   * gobierno.
   *
   * @param codigoCaratula
   * @return
   */
  public static String formatearCodigoCaratulaFromSade(final String codigoCaratula) {
    if (logger.isDebugEnabled()) {
      logger.debug("formatearCodigoCaratulaFromSade(codigoCaratula={}) - start", codigoCaratula);
    }

    String result = null;
    if (StringUtils.isNotEmpty(codigoCaratula) && codigoCaratula.length() > 17) {
      result = codigoCaratula.substring(0, 2) + '-' + codigoCaratula.substring(2, 6) + '-'
          + codigoCaratula.substring(6, 14) + '-' + codigoCaratula.substring(14, 17) + '-'
          + codigoCaratula.substring(17, codigoCaratula.length());
    }
    if (result != null) {
      final String returnString = result.trim();
      if (logger.isDebugEnabled()) {
        logger.debug("formatearCodigoCaratulaFromSade(String) - end - return value={}",
            returnString);
      }
      return returnString;
    } else {
      final String resultConsulta = "No se encontro ningun codigo de reparticion";

      if (logger.isDebugEnabled()) {
        logger.debug("formatearCodigoCaratulaFromSade(String) - end - return value={}",
            resultConsulta);
      }
      return resultConsulta;
    }
  }

  public static String nombreCarpetaWebDav(final String numeroSadeConEspacio) {
    if (logger.isDebugEnabled()) {
      logger.debug("nombreCarpetaWebDav(numeroSadeConEspacio={}) - start", numeroSadeConEspacio);
    }

    /*
     * 
     * String numeroSadeSinEspacio = "";
     * 
     * for (int x = 0; x < numeroSadeConEspacio.length(); x++) { if
     * (numeroSadeConEspacio.charAt(x) != ' ') numeroSadeSinEspacio +=
     * numeroSadeConEspacio.charAt(x); }
     * 
     * return numeroSadeSinEspacio;
     */

    final StringBuilder buf = new StringBuilder();

    for (int x = 0; x < numeroSadeConEspacio.length(); x++) {

      if (numeroSadeConEspacio.charAt(x) != ' ') {
        buf.append(numeroSadeConEspacio.charAt(x));
      }
    }

    final String numeroSadeSinEspacio = buf.toString();

    if (logger.isDebugEnabled()) {
      logger.debug("nombreCarpetaWebDav(String) - end - return value={}", numeroSadeSinEspacio);
    }
    return numeroSadeSinEspacio;
  }

  public static String nombreCarpetaWebDavComunicaciones(final String numeroSadeConEspacio) {
    if (logger.isDebugEnabled()) {
      logger.debug("nombreCarpetaWebDavComunicaciones(numeroSadeConEspacio={}) - start",
          numeroSadeConEspacio);
    }

    final String nombreCarpeta = numeroSadeConEspacio.replace("-   -", "--");

    final String returnString = formarPathWebDavApache(nombreCarpeta);
    if (logger.isDebugEnabled()) {
      logger.debug("nombreCarpetaWebDavComunicaciones(String) - end - return value={}",
          returnString);
    }
    return returnString;

  }

  public static String nombreCarpetaWebDavGedo(final String numeroSadeConEspacio) {
    if (logger.isDebugEnabled()) {
      logger.debug("nombreCarpetaWebDavGedo(numeroSadeConEspacio={}) - start",
          numeroSadeConEspacio);
    }

    // String nombreCarpeta = numeroSadeConEspacio.replace("- -","- -");

    final String returnString = formarPathWebDavApache(numeroSadeConEspacio);
    if (logger.isDebugEnabled()) {
      logger.debug("nombreCarpetaWebDavGedo(String) - end - return value={}", returnString);
    }
    return returnString;

  }

  /**
   * Obtengo con el codigoExpediente la Actuación
   *
   * @param codigoExpediente
   * @return
   */
  public static String obtenerActuacion(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerActuacion(codigoExpediente={}) - start", codigoExpediente);
    }

    final StringBuilder strb = new StringBuilder(codigoExpediente);
    final String returnString = strb.substring(0, strb.indexOf("-"));
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerActuacion(String) - end - return value={}", returnString);
    }
    return returnString;
  }

  /**
   * Obtengo con el codigoExpediente el Año
   *
   * @param codigoExpediente
   * @return
   */
  public static Integer obtenerAnio(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerAnio(codigoExpediente={}) - start", codigoExpediente);
    }

    final String[] partesCodigo = codigoExpediente.split("-");
    final Integer returnInteger = Integer.valueOf(partesCodigo[1]);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerAnio(String) - end - return value={}", returnInteger);
    }
    return returnInteger;
  }

  /**
   * Obtiene el codigo de reparticion a partir de un codigo de caratula.
   *
   * @param codigoCaratula
   * @return
   */
  public static String obtenerCodigoReparticion(final String codigoCaratula) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerCodigoReparticion(codigoCaratula={}) - start", codigoCaratula);
    }

    String result = null;
    if (StringUtils.isNotEmpty(codigoCaratula) && codigoCaratula.length() > 17) {
      result = codigoCaratula.substring(21, codigoCaratula.length());
    }
    if (result != null) {
      final String returnString = result.trim();
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerCodigoReparticion(String) - end - return value={}", returnString);
      }
      return returnString;
    } else {
      final String resultConsulta = "No se encontro ningun codigo de reparticion";

      if (logger.isDebugEnabled()) {
        logger.debug("obtenerCodigoReparticion(String) - end - return value={}", resultConsulta);
      }
      return resultConsulta;
    }
  }

  /**
   * Método que retorna una lista de String, conteniendo las partes que
   * conforman un código de un documento Gedo en formato SADE. lista[0] - Tipo
   * de Documento lista[1] - Año del Documento lista[2] - Número de documento
   * sin ceros lista[3] - Secuencia lista[4] - Repartición Actuación lista[5] -
   * Repartición Usuario
   *
   * @param strCodigoDocumento
   *          : código del documento gedo en formato SADE. Ej.
   *          "IF-2012-00001545- -MGEYA"
   * @throws ParametroIncorrectoException
   *           : el parámetro otorgado no coincide con un código de documento
   *           SADE
   */
  public static List<String> obtenerDesgloseCodigoDocumento(final String strCodigoDocumento)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDesgloseCodigoDocumento(strCodigoDocumento={}) - start",
          strCodigoDocumento);
    }

    final List<String> listDesgloseCodigoDocumentoGedo = new ArrayList<>();

    final StringTokenizer strTokenizer = new StringTokenizer(strCodigoDocumento, "-");

    if (strTokenizer.hasMoreElements()) {// TipoDocumento
      listDesgloseCodigoDocumentoGedo.add(0, (String) strTokenizer.nextElement());
    } else {
      throw new ParametroIncorrectoException("No se puede resolver el tipo de documento", null);
    }
    if (strTokenizer.hasMoreElements()) {
      // Año Documento
      try {
        Integer numero;
        numero = java.lang.Integer.valueOf((String) strTokenizer.nextElement());
        listDesgloseCodigoDocumentoGedo.add(1, numero.toString());
      } catch (final NumberFormatException nfe) {
        logger.error("obtenerDesgloseCodigoDocumento(String)", nfe);

        throw new ParametroIncorrectoException("No se puede resolver el año del documento", null);
      }
    } else {
      throw new ParametroIncorrectoException("No se puede resolver el año del documento", null);
    }
    if (strTokenizer.hasMoreElements()) {// Número Documento
      try {
        Integer numero;
        numero = java.lang.Integer.valueOf((String) strTokenizer.nextElement());
        listDesgloseCodigoDocumentoGedo.add(2, numero.toString());
      } catch (final NumberFormatException nfe) {
        logger.error("obtenerDesgloseCodigoDocumento(String)", nfe);

        throw new ParametroIncorrectoException("No se puede resolver el número de documento",
            null);
      }
    } else {
      throw new ParametroIncorrectoException("No se puede resolver el número de documento", null);
    }
    if (strTokenizer.hasMoreElements()) {// Secuencia
      listDesgloseCodigoDocumentoGedo.add(3, (String) strTokenizer.nextElement());
    } else {
      throw new ParametroIncorrectoException("No se puede resolver la secuencia del documento",
          null);
    }
    if (strTokenizer.hasMoreElements()) {// Repartición Actuación
      listDesgloseCodigoDocumentoGedo.add(4, (String) strTokenizer.nextElement());
    } else {
      throw new ParametroIncorrectoException(
          "No se puede resolver la repartición actuación del documento", null);
    }
    // if (strTokenizer.hasMoreElements()) {// Repartición Usuario
    // listDesgloseCodigoDocumentoGedo.add(5, (String)
    // strTokenizer.nextElement());
    // } else {
    // throw new ParametroIncorrectoException(
    // "No se puede resolver la repartición usuario del documento");
    // }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDesgloseCodigoDocumento(String) - end - return value={}",
          listDesgloseCodigoDocumentoGedo);
    }
    return listDesgloseCodigoDocumentoGedo;
  }

  /**
   * Método que retorna una lista de String, conteniendo las partes que
   * conforman un código de expediente en formato SADE. lista[0] - Tipo de
   * Expediente lista[1] - Año del Expediente lista[2] - Número de Expediente
   * sin ceros lista[3] - Secuencia lista[4] - Repartición Actuación lista[5] -
   * Repartición Usuario
   *
   * @param strCodigoEE
   *          : código del expediente en formato SADE. Ej. "ER-2012-00001545-
   *          -MGEYA-MGEYA"
   * @throws ParametroIncorrectoException
   *           : el parámetro otorgado no coincide con un código de expediente
   *           SADE
   */
  public static List<String> obtenerDesgloseCodigoEE(final String strCodigoEE)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDesgloseCodigoEE(strCodigoEE={}) - start", strCodigoEE);
    }

    final List<String> listDesgloseCodigoEE = new ArrayList<>();

    // <tipoDocumento>-<año>-<numeroDocumento>-<secuencia>-<repActuacion>-<repUsuario>
    if (strCodigoEE == null) {
      throw new ParametroIncorrectoException("Debe ingresar un Expediente valido", null);
    }
    final StringTokenizer strTokenizer = new StringTokenizer(strCodigoEE, "-");

    if (strTokenizer.hasMoreElements()) {// TipoExpediente
      listDesgloseCodigoEE.add(0, (String) strTokenizer.nextElement());
    } else {
      throw new ParametroIncorrectoException("No se puede resolver el tipo de expediente", null);
    }
    if (strTokenizer.hasMoreElements()) {// Año Expediente
      try {
        Integer numero;
        numero = java.lang.Integer.valueOf((String) strTokenizer.nextElement());
        listDesgloseCodigoEE.add(1, numero.toString());
      } catch (final NumberFormatException nfe) {
        logger.error("obtenerDesgloseCodigoEE(String)", nfe);

        throw new ParametroIncorrectoException("No se puede resolver el año de expediente", null);
      }
    } else {
      throw new ParametroIncorrectoException("No se puede resolver el año de expediente", null);
    }
    if (strTokenizer.hasMoreElements()) {// NúmeroExpediente
      try {
        Integer numero;
        numero = java.lang.Integer.valueOf((String) strTokenizer.nextElement());
        listDesgloseCodigoEE.add(2, numero.toString());
      } catch (final NumberFormatException nfe) {
        logger.error("obtenerDesgloseCodigoEE(String)", nfe);

        throw new ParametroIncorrectoException("No se puede resolver el número de expediente",
            null);
      }
    } else {
      throw new ParametroIncorrectoException("No se puede resolver el número de expediente", null);
    }
    if (strTokenizer.hasMoreElements()) {// Secuencia
      listDesgloseCodigoEE.add(3, (String) strTokenizer.nextElement());
    } else {
      throw new ParametroIncorrectoException("No se puede resolver la secuencia del expediente",
          null);
    }
    if (strTokenizer.hasMoreElements()) {// Repartición Actuación
      listDesgloseCodigoEE.add(4, (String) strTokenizer.nextElement());
    } else {
      throw new ParametroIncorrectoException(
          "No se puede resolver la repartición actuación del expediente", null);
    }
    if (strTokenizer.hasMoreElements()) {// Repartición Usuario
      listDesgloseCodigoEE.add(5, (String) strTokenizer.nextElement());
    } else {
      throw new ParametroIncorrectoException(
          "No se puede resolver la repartición usuario del expediente", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDesgloseCodigoEE(String) - end - return value={}",
          listDesgloseCodigoEE);
    }
    return listDesgloseCodigoEE;
  }

  /**
   * obtiene el nombre de un usuario a partir de una cadena q tiene eso y el
   * username
   *
   * @param nombreUsuario
   * @return
   */
  public static String obtenerNombreUsuario(final String nombreUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerNombreUsuario(nombreUsuario={}) - start", nombreUsuario);
    }

    String result = "";
    if (StringUtils.isNotEmpty(nombreUsuario)) {
      final String[] ual = StringUtils.split(nombreUsuario, '(');
      if (ual.length > 0) {
        result = ual[0];
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerNombreUsuario(String) - end - return value={}", result);
    }
    return result;
  }

  /**
   * Obtengo con el codigoExpediente el número SADE
   *
   * @param codigoExpediente
   * @return
   */
  public static Integer obtenerNumeroSade(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerNumeroSade(codigoExpediente={}) - start", codigoExpediente);
    }

    final String[] partesCodigo = codigoExpediente.split("-");
    final Integer returnInteger = Integer.valueOf(partesCodigo[2]);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerNumeroSade(String) - end - return value={}", returnInteger);
    }
    return returnInteger;
  }

  /**
   * Obtengo con el codigoExpediente la Rpartición de la Actuación
   *
   * @param codigoExpediente
   * @return
   */
  public static String obtenerReparticionActuacion(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionActuacion(codigoExpediente={}) - start", codigoExpediente);
    }

    final StringBuilder strb = new StringBuilder(codigoExpediente);
    final int index = strb.indexOf("-");
    final String strAux = strb.substring(index + 1);
    final int index2 = strAux.indexOf("-");
    final String strAux2 = strAux.substring(index2 + 1);
    final int index3 = strAux2.indexOf("-");
    final String strAux3 = strAux2.substring(index3 + 1);
    final int index4 = strAux3.indexOf("-");
    final String strAux4 = strAux3.substring(index4 + 1);
    final String returnString = (String) strAux4.subSequence(0, strAux4.indexOf("-"));
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionActuacion(String) - end - return value={}", returnString);
    }
    return returnString;
  }

  /**
   * Obtengo con el codigoExpediente la Repartición del Usuario
   *
   * @param codigoExpediente
   * @return
   */
  public static String obtenerReparticionUsuario(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionUsuario(codigoExpediente={}) - start", codigoExpediente);
    }

    final StringBuilder strb = new StringBuilder(codigoExpediente);
    final String returnString = strb.substring(strb.lastIndexOf("-") + 1);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionUsuario(String) - end - return value={}", returnString);
    }
    return returnString;
  }

  /**
   * Obtiene el username en el sistema de un determinado usuario.
   *
   * @param nombreUsuario
   * @return
   */
  public static String obtenerUserName(final String nombreUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUserName(nombreUsuario={}) - start", nombreUsuario);
    }

    String result = "";
    if (StringUtils.isNotEmpty(nombreUsuario)) {
      final String[] ual = StringUtils.split(nombreUsuario, '(');
      if (ual.length > 1) {
        final String[] eul = StringUtils.split(ual[2], ')');
        if (eul.length > 0) {
          result = eul[0];
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUserName(String) - end - return value={}", result);
    }
    return result;
  }

  public static String quitaCerosCodigoExpediente(final String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("quitaCerosCodigoExpediente(codigoExpediente={}) - start", codigoExpediente);
    }

    String codigoSinCeros;
    final Integer numero = Integer
        .valueOf(BusinessFormatHelper.obtenerNumeroSade(codigoExpediente));

    final StringTokenizer st = new StringTokenizer(codigoExpediente, "-");

    codigoSinCeros = st.nextToken();
    codigoSinCeros = codigoSinCeros + "-" + st.nextToken();
    codigoSinCeros = codigoSinCeros + "-" + numero;
    st.nextToken();
    while (st.hasMoreElements()) {
      codigoSinCeros = codigoSinCeros + "-" + st.nextToken();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("quitaCerosCodigoExpediente(String) - end - return value={}", codigoSinCeros);
    }
    return codigoSinCeros;
  }

  public static String quitaCerosNumeroActuacion(final Integer numero) {
    if (logger.isDebugEnabled()) {
      logger.debug("quitaCerosNumeroActuacion(numero={}) - start", numero);
    }

    String numeroActuacion = null;
    final String numeroAux = numero.toString();
    for (int x = 0; x < numeroAux.length(); x++) {

      final char caracter = numeroAux.charAt(x);

      if (caracter == '0') {

      } else {

        numeroActuacion = numeroAux.substring(x, numeroAux.length());

        break;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("quitaCerosNumeroActuacion(Integer) - end - return value={}", numeroActuacion);
    }
    return numeroActuacion;

  }

}
