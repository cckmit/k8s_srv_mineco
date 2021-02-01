package com.egoveris.deo.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsDate {

  private static final Logger logger = LoggerFactory.getLogger(UtilsDate.class);

  private static String[] months = new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
      "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };

  private static Map<Integer, String> daysOfWeek = new HashMap<Integer, String>();

  static {
    daysOfWeek.put(Calendar.MONDAY, "Lunes");
    daysOfWeek.put(Calendar.TUESDAY, "Martes");
    daysOfWeek.put(Calendar.WEDNESDAY, "Miércoles");
    daysOfWeek.put(Calendar.THURSDAY, "Jueves");
    daysOfWeek.put(Calendar.FRIDAY, "Viernes");
    daysOfWeek.put(Calendar.SATURDAY, "Sábado");
    daysOfWeek.put(Calendar.SUNDAY, "Domingo");
  }

  /**
   * Convierte un objeto Date en una fecha en formato "formal". Ej. "Lunes 26 de
   * Agosto de 2010"
   * 
   * @param dateToConvertFrom
   *          Fecha a convertir
   * @return un String conteniendo la fecha convertida a formato "formal"
   */
  public static String convertDateToFormalString(Date dateToConvertFrom) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateToConvertFrom);
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    int month = calendar.get(Calendar.MONTH);
    String fechaFormateada = daysOfWeek.get(dayOfWeek);
    fechaFormateada += " " + calendar.get(Calendar.DAY_OF_MONTH);
    fechaFormateada += " de " + months[month] + " de ";
    fechaFormateada += calendar.get(Calendar.YEAR);
    return fechaFormateada;
  }

  public static String convertDateToFechaCierreParaArchivoDefinitivo(Date dateToConvertFrom) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateToConvertFrom);
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:MM:ss");
    String fechaFormateada = formatoFecha.format(dateToConvertFrom).toString();
    return fechaFormateada;
  }

  @SuppressWarnings("deprecation")
  public static java.sql.Date obtenerFechaActualVigencia() {
    java.util.Date f1 = new java.util.Date();
    String cad = convertirFechas(f1);
    String anio = cad.substring(0, 4);
    String mes = cad.substring(5, 7);
    String dia = cad.substring(8, 10);

    int a = Integer.parseInt(anio);
    int b = Integer.parseInt(mes);
    int c = Integer.parseInt(dia);

    java.sql.Date fechaActual = new java.sql.Date(a - 1900, b - 1, c);

    return fechaActual;
  }

  private static String convertirFechas(java.util.Date fecha) {
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
    return formato.format(fecha);
  }

  /**
   * Devuelve un booleano que indica si la FECHA DESDE es posterior a la FECHA
   * HASTA
   * 
   * @param fechaDesde
   * @param fechaHasta
   * @return Boolean
   */
  public static Boolean fechaDesdePosteriorFechaHasta(Date fechaDesde, Date fechaHasta) {
    if (fechaHasta != null) {
      Calendar cal1 = Calendar.getInstance();
      Calendar cal2 = Calendar.getInstance();
      cal1.setTime(fechaDesde);
      cal2.setTime(fechaHasta);

      if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
          && cal1.get(Calendar.MONTH) + 1 == cal2.get(Calendar.MONTH) + 1
          && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
        return false;
      } else {
        if (fechaDesde.after(fechaHasta)) {
          return true;
        } else {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Devuelve la diferencia entre dos fechas
   * 
   * @param fechaHasta
   *          que se quiere comparar
   * @return 0 es que no hay diferencia
   */
  public static int diasEntreFechas(Date fechaHasta) {
    String remito = convertirFechas(fechaHasta);
    String actual = convertirFechas(new Date());

    // actual
    String anio = actual.substring(0, 4);
    String mes = actual.substring(5, 7);
    String dia = actual.substring(8, 10);
    // remito
    String anio2 = remito.substring(0, 4);
    String mes2 = remito.substring(5, 7);
    String dia2 = remito.substring(8, 10);
    // paso a entero actual
    int a = Integer.valueOf(anio);
    int m = Integer.valueOf(mes);
    int d = Integer.valueOf(dia);
    // paso a entero
    int a2 = Integer.valueOf(anio2);
    int m2 = Integer.valueOf(mes2);
    int d2 = Integer.valueOf(dia2);

    Calendar cal1 = Calendar.getInstance();
    cal1.set(a, m, d);
    Calendar cal2 = Calendar.getInstance();
    cal2.set(a2, m2, d2);
    Date date1 = cal1.getTime();
    Date date2 = cal2.getTime();
    long dif = date2.getTime() - date1.getTime();
    // Un dia tiene 86400000 milisegundos
    return (int) (dif / 86400000);
  }

  /**
   * Compara que dos fechas sean iguales para esto le da un formato y compara
   * los string
   * 
   * @param fechaDesde
   * @param fechaHasta
   * @return trye si son iguales y false si son distintos
   */
  @SuppressWarnings("deprecation")
  public static Boolean fechaDesdeEqualFechaHasta(Date fechaDesde, Date fechaHasta) {
    fechaHasta.setHours(0);
    fechaHasta.setMinutes(0);
    fechaHasta.setSeconds(0);
    SimpleDateFormat d = new SimpleDateFormat("ddmmyyyy");
    String nuevaFechaDesde = d.format(fechaDesde);
    String nuevaFechaHasta = d.format(fechaHasta);

    return nuevaFechaDesde.equals(nuevaFechaHasta);
  }

  @SuppressWarnings("deprecation")
  public static boolean grisarFechas(Date vigenciaDesde) {
    boolean retorno = false;
    // inicializo la fecha de hoy
    Date hoy = new Date();
    hoy.setHours(0);
    hoy.setMinutes(0);
    hoy.setSeconds(0);
    if (!fechaDesdeEqualFechaHasta(vigenciaDesde, hoy)
        && !fechaDesdePosteriorFechaHasta(vigenciaDesde, hoy)) {
      retorno = true;
    }
    return retorno;

  }

  /**
   * Comparo dos fechas para ver si son las mismas
   * 
   * @param fecha_1
   * @param fecha_2
   * 
   * @return un boolean que dice si son iguales o no
   */
  public static Boolean fechasIguales(Date fecha_1, Date fecha_2) {
    Calendar cal_1 = Calendar.getInstance();
    Calendar cal_2 = Calendar.getInstance();
    cal_1.setTime(fecha_1);
    cal_2.setTime(fecha_2);
    return cal_1.get(Calendar.YEAR) == cal_2.get(Calendar.YEAR)
        && cal_1.get(Calendar.MONTH) + 1 == cal_2.get(Calendar.MONTH) + 1
        && cal_1.get(Calendar.DAY_OF_MONTH) == cal_2.get(Calendar.DAY_OF_MONTH);
  }

  public static String getShortDateToString(Date date) {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    return formato.format(date);
  }

  public static String getLongDateToString(Date date) {
    DateFormat dfDefault = DateFormat.getInstance();
    return dfDefault.format(date);
  }

  public static String getLongDateToStringWithSeconds(Date date) {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
    return formato.format(date);
  }

  /**
   * Obtiene la fecha actual
   */
  public static Date getFechaActual() {
    Calendar cal = Calendar.getInstance();
    int anio = cal.get(Calendar.YEAR);
    int mes = cal.get(Calendar.MONTH);
    int dia = cal.get(Calendar.DATE);
    cal.set(anio, mes, dia);
    return cal.getTime();
  }

  public static String tranformarFechaMesEnLetrasLong(Date fecha) {
    if (fecha != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
      return formatter.format(fecha);
    }
    return "";
  }

  public static Date tranformarFechaMesEnDateLong(String fecha) {
    if (!StringUtils.isEmpty(fecha)) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

      try {
        Date d;
        d = formatter.parse(fecha);
        return d;
      } catch (ParseException e) {
        logger.error("Mensaje de error", e);
      }
    }
    return null;
  }

  public static String tranformarFechaMesEnLetrasShort(Date fecha) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    return formatter.format(fecha);
  }

  public static Boolean periodoMayorAlAnio(Date fechaDesde, Date fechaHasta) {
    String fin = convertirFechas(fechaHasta);
    String inicio = convertirFechas(fechaDesde);

    // inicio
    String anio = inicio.substring(0, 4);
    String mes = inicio.substring(5, 7);
    String dia = inicio.substring(8, 10);
    // fin
    String anio2 = fin.substring(0, 4);
    String mes2 = fin.substring(5, 7);
    String dia2 = fin.substring(8, 10);
    // paso a entero inicio
    int a = new Integer(anio);
    int m = new Integer(mes);
    int d = new Integer(dia);
    // paso a entero fin
    int a2 = new Integer(anio2);
    int m2 = new Integer(mes2);
    int d2 = new Integer(dia2);

    Calendar cal1 = Calendar.getInstance();
    cal1.set(a, m, d);
    Calendar cal2 = Calendar.getInstance();
    cal2.set(a2, m2, d2);
    Date date1 = cal1.getTime();
    Date date2 = cal2.getTime();

    long dif = date2.getTime() - date1.getTime();
    // Un dia tiene 86400000 milisegundos
    int resultado = (int) (dif / 86400000);
    // Considero si es bisiesto
    if (resultado > 366) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Obtiene la fecha actual.
   * 
   * @return String con la fecha actual, en el formato "yyyy-MM-dd HH:mm:ss"
   */
  public static String getCurrentJavaSqlDate() {
    java.util.Date today = new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = sdf.format(today);
    return date;
  }

  /**
   * Obtiene el anio actual.
   * 
   * @return
   */
  public static String obtenerAnioActual() {
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
    String anio = simpleDateFormat.format(date);
    return anio;
  }
}