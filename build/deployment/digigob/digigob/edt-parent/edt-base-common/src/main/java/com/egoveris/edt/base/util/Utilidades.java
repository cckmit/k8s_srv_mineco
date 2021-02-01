package com.egoveris.edt.base.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;

import com.egoveris.edt.base.model.TareasPorSistemaBean;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;


public class Utilidades {

	
  /**
   * Este metodo procesa la lista de fechas que recibe y las agrupa de acuerdo
   * al tiempo transcurrido entre la cada fecha y la fecha actual.
   *
   * @param maxSizeZeros
   *          the max size zeros
   * @param valueBaseToAdd
   *          the value base to add
   * @return un objeto de tareas pendientes
   */
  public static String completarConCeros(int maxSizeZeros, String valueBaseToAdd) {
    int diferencia = maxSizeZeros - valueBaseToAdd.length();
    String fillCeros = new String();
    for (int i = 0; i < diferencia; i++) {
      fillCeros += "0";
    }

    return fillCeros;
  }

  /**
   * Procesar tareas pendientes.
   *
   * @param listaFechasTareasPendientes
   *          the lista fechas tareas pendientes
   * @param usuarioFrecuencia
   *          the usuario frecuencia
   * @return the tareas por sistema bean
   */
  public static TareasPorSistemaBean procesarTareasPendientes(
      List<Date> listaFechasTareasPendientes, UsuarioFrecuenciaDTO usuarioFrecuencia) {
    TareasPorSistemaBean tareasPendientes = new TareasPorSistemaBean();

    // Contadores para cada frecuencia
    Integer cantFrec1 = 0;
    Integer cantFrec2 = 0;
    Integer cantFrec3 = 0;
    Integer cantFrec4 = 0;

    // Variable para contener la diferencia para cada fecha de la lista
    // int diferencia;
    long diferencia;
    int diferenciaAcumuladaEnDiasMenoresFrecMayor = 0;
    int diferenciaAcumuladaEnDiasMayoresFrecMayor = 0;

    if (listaFechasTareasPendientes != null && !listaFechasTareasPendientes.isEmpty()) {
      // La cantidad de tareas pendientes es el tamaño de la lista de
      // tareas pendientes
      tareasPendientes.setTareasPendientes(listaFechasTareasPendientes.size());

      // Recorro la lista de fecha de tareas pendidentes para comparar por
      // fechas y agruparlas
      // de acuerdo a la frecuencia
      for (Date date : listaFechasTareasPendientes) {
        diferencia = diasEntreFechas(date, new Date());

        if (diferencia <= usuarioFrecuencia.getFrecuenciaMenor()) {
          cantFrec1++;
          diferenciaAcumuladaEnDiasMenoresFrecMayor += diferencia;
        } else if (diferencia <= usuarioFrecuencia.getFrecuenciaMedia()) {
          cantFrec2++;
          diferenciaAcumuladaEnDiasMenoresFrecMayor += diferencia;
        } else if (diferencia <= usuarioFrecuencia.getFrecuenciaMayor()) {
          cantFrec3++;
          diferenciaAcumuladaEnDiasMenoresFrecMayor += diferencia;
        } else {
          cantFrec4++;
          diferenciaAcumuladaEnDiasMayoresFrecMayor += diferencia;
        }
      }
      tareasPendientes.setFrecuencia1(cantFrec1);
      tareasPendientes.setFrecuencia2(cantFrec2);
      tareasPendientes.setFrecuencia3(cantFrec3);
      tareasPendientes.setFrecuencia4(cantFrec4);

      if (cantFrec1 + cantFrec2 + cantFrec3 == 0) {
        tareasPendientes.setPorcentajeFrecuenciaMenor(0);
      } else {
        tareasPendientes.setPorcentajeFrecuenciaMenor(
            diferenciaAcumuladaEnDiasMenoresFrecMayor / (cantFrec1 + cantFrec2 + cantFrec3));
      }

      if (cantFrec4 == 0) {
        tareasPendientes.setPorcentajeFrecuenciaMayor(0);
      } else {
        tareasPendientes
            .setPorcentajeFrecuenciaMayor(diferenciaAcumuladaEnDiasMayoresFrecMayor / cantFrec4);
      }
    } else {
      tareasPendientes.setTareasPendientes(0);
      tareasPendientes.setFrecuencia1(0);
      tareasPendientes.setFrecuencia2(0);
      tareasPendientes.setFrecuencia3(0);
      tareasPendientes.setFrecuencia4(0);
      tareasPendientes.setPorcentajeFrecuenciaMenor(0);
      tareasPendientes.setPorcentajeFrecuenciaMayor(0);
    }

    return tareasPendientes;
  }

  /**
   * Este metodo compara las dos fechas pasadas como parametro y devuelve la
   * cantidad de dias de diferencia entre ellas.
   *
   * @param fecha1
   *          the fecha 1
   * @param fecha2
   *          the fecha 2
   * @return un entero que representa la diferencia en días entre dos fechas
   */
  public static long diasEntreFechas(Date fecha1, Date fecha2) {

    long resultado = fecha2.getTime() - fecha1.getTime();
    long dias = resultado / (86400000);
    return dias;
  }

  /**
   * Diferencia en minutos.
   *
   * @param fechaActual
   *          the fecha actual
   * @param fechaIngresada
   *          the fecha ingresada
   * @return the long
   */
  public static long diferenciaEnMinutos(Date fechaActual, Date fechaIngresada) {
    long resultado = fechaIngresada.getTime() - fechaActual.getTime();
    long segundos = resultado / 1000;
    long minutos = segundos / 60;
    return minutos;
  }

  /**
   * Dar formato fecha.
   *
   * @param fecha
   *          the fecha
   * @return the string
   */
  public static String darFormatoFecha(Date fecha) {
    SimpleDateFormat formato = new SimpleDateFormat(ConstantesSesion.DATEFORMAT);
    String cadenaFecha = formato.format(fecha);

    return cadenaFecha;

  }

  /**
   * Formatear reparticiones solr.
   *
   * @param reparticion
   *          the reparticion
   * @return the string
   */
  public static String formatearReparticionesSolr(List<String> reparticion) {
    int i = 0;
    String aux = null;
    while (i <= reparticion.size() - 1) {
      if (aux == null) {
        aux = "reparticion_usuario:(".concat((String) reparticion.get(i).trim());
      } else {
        aux = aux.concat(" OR ").concat((String) reparticion.get(i).trim());
      }
      i++;
    }
    if(aux != null){
    aux = aux.concat(")");
    }
    return aux;
  }

  /**
   * Formatear fecha solr.
   *
   * @param fecha
   *          the fecha
   * @return the string
   */
  public static String formatearFechaSolr(Date fecha) {
    SimpleDateFormat sdf = new SimpleDateFormat(ConstantesSesion.DATEFORMAT);
    String dateString = sdf.format(fecha.getTime());

    return dateString;
  }

  /**
   * Formatear fecha SQL agregando un dia.
   *
   * @param fecha
   *          the fecha
   * @return the string
   */
  public static String formatearFechaSQLAgregandoUnDia(Date fecha) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fecha);
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    Date aux = calendar.getTime();
    java.sql.Date salida = new java.sql.Date(aux.getTime());
    SimpleDateFormat sdf = new SimpleDateFormat(ConstantesSesion.DATEFORMAT);
    String dateString = sdf.format(salida);

    return dateString;
  }

  /**
   * Date convert.
   *
   * @param fecha
   *          the fecha
   * @return the string
   */
  public static String dateConvert(Date fecha) {
    String ret = null;
    if (fecha != null) {
      DateFormat df = (DateFormat) getSdf().clone();
      ret = df.format(fecha);
    }
    return ret;
  }

  private static SimpleDateFormat getSdf() {
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf;
  }

  /**
   * Date filter.
   *
   * @param fechaDesde
   *          the fecha desde
   * @param fechaHasta
   *          the fecha hasta
   * @return the string
   */
  public static String dateFilter(Date fechaDesde, Date fechaHasta) {
    String desde = dateConvert(fechaDesde);
    String hasta = dateConvert(fechaHasta);

    if (desde == null) {
      desde = "*";
    }

    if (hasta == null) {
      hasta = "NOW";
    }
    return "[" + desde + " TO " + hasta + "]";
  }

  /**
   * Get the end of a specified day.
   * For example, given a date '23-06-2017' it will return '23-06-2017 23:59:58
   * 
   * @param date
   * @return
   */
  public static Date getEndOfDay(Date date) {
  	Calendar calendar = Calendar.getInstance();
  	calendar.setTime(date);
  	
  	int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DATE);
    calendar.set(year, month, day, 23, 59, 58);
    
    return calendar.getTime();
  }
  /**
   * 
   * @param cadena
   * @return true, si la cadena pasada tiene carecteres especiales
   */
  public static boolean validarCaracteresEspeciales(String cadena) {
  	
    Pattern regex = Pattern.compile("[a-zA-Z0-9]*");
    Matcher match = regex.matcher(cadena);

    return !match.matches();
    
  }
  
	/**
	 * pasar el formato por que quiere recuperar </br>
	 * Ejemplo de formatos: </br>
	 * "dd/MM/yyyy", "yyyy-MM-dd HH:mm:ss", etc
	 * @param fecha
	 * @param formato
	 * @return fecha en formato string
	 */
	public static String darFomatoFecha(Date fecha,String formato) {
		SimpleDateFormat formatCast= new SimpleDateFormat(formato);
		return formatCast.format(fecha);
	}
  
}
