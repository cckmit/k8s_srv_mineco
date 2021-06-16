package com.egoveris.te.base.util;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;

public class Utils {

  public static void doLogout(String urlLogOut) {
    Executions.getCurrent().getSession().invalidate();
    Executions.sendRedirect(urlLogOut);
  }

  public static void doLogout() {
    String urlLogOutCAS = "/j_spring_security_logout";
    Executions.getCurrent().getSession().invalidate();
    Executions.sendRedirect(urlLogOutCAS);
  }

  public static String parsearTipoDocumentoAcronimo(String codigoDocumento) {
    String acronimoDelCodigoDocumento = codigoDocumento.substring(0, codigoDocumento.indexOf("-"));
    return acronimoDelCodigoDocumento;
  }

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
      Utils.isAlphanumericLength(partesDelCodigo[4]);
      Utils.isAlphanumericLength(partesDelCodigo[5]);

      response = Utils.isAlphanumericLength(partesDelCodigo[0]) == 2
          && Utils.isNumericLength(partesDelCodigo[1]) == 4
          && Utils.isNumericLength(partesDelCodigo[2]) <= 8
          && Utils.isWhitespaceLength(partesDelCodigo[3]) == 3;
    }

    return response;

  }

  public static String toStringValue(Integer i) {

    if (i > 999) {
      return i.toString();
    }

    if (i > 99) {
      return "0" + i.toString();
    }

    if (i > 9) {
      return "00" + i.toString();
    }

    return "000" + i.toString();
  }

  /**
   * Metodo que formatea una ruta de zul para que empiece con "/". Si empieza
   * con "./" queda como "/". Si no empieza con "/", se lo agrega.
   * 
   * @param zulPath
   *          Ruta zul
   * @return Ruta zul formateada
   */
  public static String formatZulPath(String zulPath) {
    String retorno = zulPath;

    if (zulPath != null) {
      retorno = zulPath.replace("./", "/");

      if (!retorno.startsWith("/")) {
        retorno = "/".concat(retorno);
      }
    }

    return retorno;
  }

}
