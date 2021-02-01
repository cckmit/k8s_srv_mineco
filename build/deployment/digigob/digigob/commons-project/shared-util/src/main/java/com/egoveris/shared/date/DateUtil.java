package com.egoveris.shared.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

  private DateUtil() {
  }

  /**
   * Set the time of a Date to 00:00:00 for comparison
   * 
   * @param fecha
   * @return
   */
  public static Date getZeroTimeDate(Date fecha) {
    Date res;
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(fecha);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    res = calendar.getTime();

    return res;
  }

  /**
   * Get the date formatted as String.
   * 
   * @param fecha
   * @return Formatted date (dd/MM/yyyy)
   */
  public static String getFormattedDate(Date fecha) {
    LocalDateTime ldt = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
    return ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
  
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

  /**
   * Gets the calendar.
   *
   * @param fecha the fecha
   * @return the calendar
   */
  private static GregorianCalendar getCalendar(Date fecha) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(fecha);
    return calendar;
  }
  
}