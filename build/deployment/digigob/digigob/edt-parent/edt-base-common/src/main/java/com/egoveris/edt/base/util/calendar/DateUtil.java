package com.egoveris.edt.base.util.calendar;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The Class DateUtil.
 *
 * @author lfishkel
 */
public class DateUtil {

  /**
   * Devuelve una instancia de java.util.Date que solo contenga día, mes y año
   *
   * @param date
   *          the date
   * @return the date
   */
  public static Date beginning(Date date) {
    GregorianCalendar calendar = DateUtil.getCalendar(date);
    int dia = calendar.get(GregorianCalendar.DATE);
    int mes = calendar.get(GregorianCalendar.MONTH);
    int anio = calendar.get(GregorianCalendar.YEAR);
    calendar = new GregorianCalendar(anio, mes, dia);
    return new Date(calendar.getTimeInMillis());
  }

  /**
   * Devuelve el día de la semana de una instanca de java.util.Date
   *
   * @param fecha
   *          the fecha
   * @return the int
   */
  public static int diaDeLaSemana(Date fecha) {
    GregorianCalendar calendar = DateUtil.getCalendar(fecha);
    return calendar.get(GregorianCalendar.DAY_OF_WEEK);
  }

  private static GregorianCalendar getCalendar(Date fecha) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(fecha);
    return calendar;
  }

}
