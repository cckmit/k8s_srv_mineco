package com.egoveris.vucfront.model.util;

import com.egoveris.vucfront.model.exception.ValidacionException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

/**
 * Hace las conversiones entre formatos necesarias por cuestiones de negocio.
 * 
 * @author paGarcia
 */
public class BusinessFormatHelper {
  private BusinessFormatHelper() {

  }

  /**
   * Método que retorna una lista de String, conteniendo las partes que
   * conforman un código de formato SADE. <br>
   * <b>NOTAS</b> : <br>
   * Al ser del formato SADE solamente se contempla como separador de cadenas al
   * "-" <br>
   * No contempla el agregado ni la quita de 0(ceros) al Número de resgistro
   * para que su uso sea generico <br>
   * Si el código posee la subcadena "secuencia" esta no es tenida en cuenta en
   * el resultado
   * 
   * @param strCodigoSADE
   *          : código de formato SADE. <br>
   *          Ej.<b>'tipoDocumento'-'año'-'numeroDocumento'-'repUsuario'</b>
   *          <br>
   *          Ej."RIB-2012-00001545-MGEYA" <br>
   *          Ej."IF-2012-00001545- -MGEYA" <br>
   *          Ej."EX-2012-00001578- -MGEYA-DGIYE" ó <br>
   *          Ej."LOTE-FIT-20120905-151059"
   * @return una <b> List de String </b> con el formato (ej de un Documento GEDO
   *         o un Expediente): <br>
   *         lista[0] - Tipo de Documento o Actuación<br>
   *         lista[1] - Año del registro <br>
   *         lista[2] - Número de registro <br>
   *         lista[3] - Repartición Usuario o Actuación(solo para expedientes)
   *         <br>
   *         lista[4] - Repartición Usuario(solo para expedientes)
   * @throws ValidacionException
   *           : el parámetro otorgado es nulo o un String vacio
   * @author paGarcia
   */
  public static List<String> obtenerDesgloseCodigoSADE(String strCodigoSADE)
      throws ValidacionException {

    List<String> listDesgloseCodigoRegistro = new ArrayList<>();

    if (!StringUtils.hasText(strCodigoSADE)) {
      throw new ValidacionException("El código de registro es nulo.");
    }
    StringTokenizer strTokenizer = new StringTokenizer(strCodigoSADE, "-");

    int i = 0;
    while (strTokenizer.hasMoreElements()) {
      if (i != 3) {
        listDesgloseCodigoRegistro.add(i, (String) strTokenizer.nextElement());
      } else {
        String posibleSecuencia = (String) strTokenizer.nextElement();
        if ("".equals(posibleSecuencia.trim())) {
          i--;
        } else {
          listDesgloseCodigoRegistro.add(i, posibleSecuencia);
        }
      }
      i++;
    }
    return listDesgloseCodigoRegistro;
  }

  /**
   * Utiliza los parametros para armar la numeracion de SADE. <br>
   * <b>NOTAS :</b> <br>
   * Si se desea armar la numeración de un documento GEDO, poner <b>null</b> en
   * <b>codigoReparticionActuacion</b> <br>
   * Contempla el agregado de los O(ceros) en el número <br>
   * La numeración se arma con o sin secuencia segun la variable
   * <b>conSecuencia</b>
   * 
   * @param codigoActuacionSade
   * @param anio
   * @param numero
   * @param codigoReparticionActuacion
   * @param codigoReparticionUsuario
   * @param conSecuencia
   * @return Un String con el código de formato SADE. <br>
   *         Ej.<b>'tipoDoc'-'año'-'numDocConCeros'-' '-'repUsuario'</b>(con
   *         secuencia)<br>
   *         Ej.<b>'tipoDoc'-'año'-'numDocConCeros'-'repUsuario'</b>(sin
   *         secuencia)
   */
  public static String armarNumeracionSADE(String codigoActuacionSade, Integer anio,
      Integer numero, String codigoReparticionActuacion, String codigoReparticionUsuario,
      boolean conSecuencia) {
    try {
      StringBuilder codigoSADE = new StringBuilder();
      codigoSADE.append(codigoActuacionSade);
      codigoSADE.append("-");
      codigoSADE.append(anio);
      codigoSADE.append("-");
      codigoSADE.append(formatearNumeroParaNumeracion(numero));
      if (conSecuencia) {
        codigoSADE.append("-   -");
      } else {
        codigoSADE.append("-");
      }
      if (codigoReparticionActuacion != null && !codigoReparticionActuacion.trim().equals("")) {
        codigoSADE.append(codigoReparticionActuacion.trim());
        codigoSADE.append("-");
      }
      codigoSADE.append(codigoReparticionUsuario.trim());
      return codigoSADE.toString();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Fuerza un formato de 8 digitos al numero proporcionado, completando el
   * mismo con padding de 0s a izquierda.
   */
  public static String formatearNumeroParaNumeracion(Integer numero) {
    DecimalFormat format = new DecimalFormat("00000000");
    String numeroFormateado = format.format(Integer.valueOf(numero));
    return numeroFormateado;
  }

}