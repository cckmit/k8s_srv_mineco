package com.egoveris.te.base.util;

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
        throw new WrongValueException("Deben ser caracteres sin espacios alfanumericos");
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
        throw new WrongValueException("La cadena de caracteres debe contener solo números");
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
            "La cadena de caracteres debe contener solo espacios en blanco");
      }
    }
    return str.length();
  }

  public static boolean esCodigoDeExpediente(String str) throws WrongValueException {
    boolean response = false;
    String[] partesDelCodigo = str.split("-");
    if (partesDelCodigo.length == 6) {
      RedirUtils.isAlphanumericLength(partesDelCodigo[4]);
      RedirUtils.isAlphanumericLength(partesDelCodigo[5]);

      response = RedirUtils.isAlphanumericLength(partesDelCodigo[0]) == 2
          && RedirUtils.isNumericLength(partesDelCodigo[1]) == 4
          && RedirUtils.isNumericLength(partesDelCodigo[2]) <= 8
          && RedirUtils.isWhitespaceLength(partesDelCodigo[3]) == 3;
    } else {
      throw new WrongValueException("codigo de expediente incompleto");
    }

    return response;

  }
}
