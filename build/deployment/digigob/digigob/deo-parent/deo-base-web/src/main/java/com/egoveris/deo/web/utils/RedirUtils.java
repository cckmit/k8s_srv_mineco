package com.egoveris.deo.web.utils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;

/**
 * 
 * @author lfishkel
 *
 */

public class RedirUtils {

  /**
   * @param str
   * @return - la cantidad de caracteres
   * @throws WrongValueException
   *           en caso de que no sea alfanumérico
   */
  public static int isAlphanumericLength(String str) {
    if (str == null) {
      return 0;
    }
    int sz = str.length();
    for (int i = 0; i < sz; i++) {
      if (!Character.isLetterOrDigit(str.charAt(i))) {
        throw new WrongValueException(
            Labels.getLabel("gedo.redirUtils.exception.caracteresSinEspaciosAlfanum"));
      }
    }
    return str.length();
  }

  /**
   * @param str
   * @return - la cantidad de caracteres
   * @throws WrongValueException
   *           en caso de que no sea numérico
   */
  public static int isNumericLength(String str) throws WrongValueException {
    if (str == null) {
      return 0;
    }
    int sz = str.length();
    for (int i = 0; i < sz; i++) {
      if (!Character.isDigit(str.charAt(i))) {
        throw new WrongValueException(
            Labels.getLabel("gedo.redirUtils.exception.cadCaracteresSoloNum"));
      }
    }
    return str.length();
  }

  /**
   * @param str
   * @return - la cantidad de caracteres
   * @throws WrongValueException
   *           en caso de que no sea numérico
   */
  public static int isWhitespaceLength(String str) {
    if (str == null) {
      return 0;
    }
    int sz = str.length();
    for (int i = 0; i < sz; i++) {
      if ((!Character.isWhitespace(str.charAt(i)))) {
        throw new WrongValueException(
            Labels.getLabel("gedo.redirUtils.exception.cadSoloEspaciosBlanco"));
      }
    }
    return str.length();
  }

  public static boolean esNumeroDocumento(String numeroDocumento) {

    boolean response = false;
    String[] partesDelCodigo = numeroDocumento.split("-");
    if (partesDelCodigo.length == 5) {
      RedirUtils.isAlphanumericLength(partesDelCodigo[0]);
      RedirUtils.isAlphanumericLength(partesDelCodigo[4]);

      response = RedirUtils.isNumericLength(partesDelCodigo[1]) == 4
          && RedirUtils.isNumericLength(partesDelCodigo[2]) == 8
          && RedirUtils.isWhitespaceLength(partesDelCodigo[3]) == 3;
    } else {
      throw new WrongValueException(Labels.getLabel("gedo.redirUtils.exception.numDocIncompleto"));
    }

    return response;
  }
}
