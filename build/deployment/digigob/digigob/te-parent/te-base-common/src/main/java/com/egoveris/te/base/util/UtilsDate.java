package com.egoveris.te.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.shared.date.DateUtil;
import com.egoveris.te.base.exception.external.TeException;

/**
 * Utilidades especificas para el manejo de fechas.
 * 
 * 
 */
public class UtilsDate {
	
	private static Logger logger = LoggerFactory.getLogger(UtilsDate.class);

	private static String[] months = new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
			"Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };

	private static HashMap<Integer, String> daysOfWeek = new HashMap<Integer, String>();
	
  private static final int SABADO = 7;
  private static final int DOMINGO = 1;

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
	 * Convierte un objeto Date en una fecha en formato "formal". Ej.
	 * "Lunes 26 de Agosto de 2010"
	 * 
	 * @param dateToConvertFrom
	 *            Fecha a convertir
	 * @return un String conteniendo la fecha convertida a formato "formal"
	 */
	public static String convertDateToFormalString(Date dateToConvertFrom) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertDateToFormalString(dateToConvertFrom={}) - start", dateToConvertFrom);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToConvertFrom);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int month = calendar.get(Calendar.MONTH);
		String fechaFormateada = daysOfWeek.get(dayOfWeek);
		fechaFormateada += " " + calendar.get(Calendar.DAY_OF_MONTH);
		fechaFormateada += " de " + months[month] + " de ";
		fechaFormateada += calendar.get(Calendar.YEAR);

		if (logger.isDebugEnabled()) {
			logger.debug("convertDateToFormalString(Date) - end - return value={}", fechaFormateada);
		}
		return fechaFormateada;
	}

	/**
	 * Obtiene la fecha actual de vigencia.
	 * 
	 * TODO: consultar si es necesario la perdida de precision en la fecha y el
	 * porque de la imprementacion anterior.
	 * 
	 * @return
	 */
	// @SuppressWarnings("deprecation")
	public static java.sql.Date obtenerFechaActualVigencia() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerFechaActualVigencia() - start");
		}

		/*
		 * java.util.Date f1= new java.util.Date(); String
		 * cad=convertirFechas(f1); String anio = cad.substring(0,4); String
		 * mes=cad.substring(5, 7); String dia=cad.substring(8, 10); int a=
		 * Integer.parseInt(anio); int b=Integer.parseInt(mes); int
		 * c=Integer.parseInt(dia); java.sql.Date fechaActual= new
		 * java.sql.Date(a-1900,b-1,c);
		 */
		java.sql.Date fechaActual = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerFechaActualVigencia() - end - return value={}", fechaActual);
		}
		return fechaActual;
	}

	/**
	 * Le aplica el formato yyy/mm/dd a una fecha.
	 * 
	 * @param fecha
	 * @return
	 */
	private static String formatearFecha(java.util.Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("formatearFecha(fecha={}) - start", fecha);
		}

		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
		String fechaFormateada = formato.format(fecha);

		if (logger.isDebugEnabled()) {
			logger.debug("formatearFecha(java.util.Date) - end - return value={}", fechaFormateada);
		}
		return fechaFormateada;
	}

	public static String formatearFechaHora(java.util.Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("formatearFechaHora(fecha={}) - start", fecha);
		}

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		;

		String fechaFormateada = formato.format(fecha);

		if (logger.isDebugEnabled()) {
			logger.debug("formatearFechaHora(java.util.Date) - end - return value={}", fechaFormateada);
		}
		return fechaFormateada;
	}

	/**
	 * Devuelve el anio en curso.
	 */
	public static int obtenerAnioActual() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerAnioActual() - start");
		}

		Calendar calendar = Calendar.getInstance();
		int returnint = calendar.get(Calendar.YEAR);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerAnioActual() - end - return value={}", returnint);
		}
		return returnint;
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
		if (logger.isDebugEnabled()) {
			logger.debug("fechaDesdePosteriorFechaHasta(fechaDesde={}, fechaHasta={}) - start", fechaDesde, fechaHasta);
		}

		if (fechaHasta != null) {
			Calendar cal_1 = Calendar.getInstance();
			Calendar cal_2 = Calendar.getInstance();
			cal_1.setTime(fechaDesde);
			cal_2.setTime(fechaHasta);

			if (cal_1.get(Calendar.YEAR) == cal_2.get(Calendar.YEAR)
					&& cal_1.get(Calendar.MONTH) + 1 == cal_2.get(Calendar.MONTH) + 1
					&& cal_1.get(Calendar.DAY_OF_MONTH) == cal_2.get(Calendar.DAY_OF_MONTH))
				return false;
			else {
				if (fechaDesde.after(fechaHasta))
					return true;
				else
					return false;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("fechaDesdePosteriorFechaHasta(Date, Date) - end - return value={}", true);
		}
		return true;
	}

	/**
	 * Devuelve la cantidad de dias entre el dia actual y otra fecha provista.
	 * La fecha se convierte a calendar para evitar una diferencia de formatos.
	 * 
	 * @param otraFecha
	 *            que se quiere comparar
	 * @return 0 es que no hay diferencia
	 */
	public static int diasEntreHoyYOtraFecha(Date otraFecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("diasEntreHoyYOtraFecha(otraFecha={}) - start", otraFecha);
		}

		int returnint = diasEntreHoyYOtraFecha(otraFecha, false);
		if (logger.isDebugEnabled()) {
			logger.debug("diasEntreHoyYOtraFecha(Date) - end - return value={}", returnint);
		}
		return returnint;
	}

	/**
	 * Devuelve la cantidad de dias entre el dia actual y otra fecha provista.
	 * La fecha se convierte a calendar para evitar una diferencia de formatos.
	 * 
	 * @param otraFecha
	 *            que se quiere comparar
	 * @param sinHora
	 *            cálculo solo de días, sin hora minutos y segundos
	 * @return 0 es que no hay diferencia
	 */
	public static int diasEntreHoyYOtraFecha(Date otraFecha, boolean sinHora) {
		if (logger.isDebugEnabled()) {
			logger.debug("diasEntreHoyYOtraFecha(otraFecha={}, sinHora={}) - start", otraFecha, sinHora);
		}

		Calendar hoy = Calendar.getInstance();
		Calendar otroDia = Calendar.getInstance();
		otroDia.setTime(otraFecha);

		if (sinHora) {
			hoy = removeTime(hoy);
			otroDia = removeTime(otroDia);
		}

		long dif = hoy.getTimeInMillis() - otroDia.getTimeInMillis();
		// Un dia tiene 86400000 milisegundos
		int returnint = (int) (dif / 86400000);
		if (logger.isDebugEnabled()) {
			logger.debug("diasEntreHoyYOtraFecha(Date, boolean) - end - return value={}", returnint);
		}
		return returnint;
	}
	
	public static int diasEntreOtraFechayHoy(Date unaFecha){
		if (logger.isDebugEnabled()) {
			logger.debug("diasEntreOtraFechayHoy(unaFecha={}) - start", unaFecha);
		}
		
		Calendar hoy = Calendar.getInstance();
		Calendar otroDia = Calendar.getInstance();
		otroDia.setTime(unaFecha);
		hoy = removeTime(hoy);
		otroDia = removeTime(otroDia);
		
		long dif = otroDia.getTimeInMillis() - hoy.getTimeInMillis();
		int returnint = (int) (dif / 86400000);
		if (logger.isDebugEnabled()) {
			logger.debug("diasEntreOtraFechayHoy(Date) - end - return value={}", returnint);
		}
		return returnint;
		
	}

	private static Calendar removeTime(Calendar cal) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeTime(cal={}) - start", cal);
		}

		// Set time fields to zero
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		if (logger.isDebugEnabled()) {
			logger.debug("removeTime(Calendar) - end - return value={}", cal);
		}
		return cal;
	}

	/**
	 * Compara que dos fechas sean iguales para esto le da un formato y compara
	 * los string.
	 * 
	 * TODO: revisar si esto sirve para algo.
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return true si son iguales y false si son distintos
	 */
	@SuppressWarnings("deprecation")
	public static Boolean fechaDesdeEqualFechaHasta(Date fechaDesde, Date fechaHasta) {
		if (logger.isDebugEnabled()) {
			logger.debug("fechaDesdeEqualFechaHasta(fechaDesde={}, fechaHasta={}) - start", fechaDesde, fechaHasta);
		}

		fechaHasta.setHours(0);
		fechaHasta.setMinutes(0);
		fechaHasta.setSeconds(0);
		SimpleDateFormat d = new SimpleDateFormat("ddmmyyyy");
		String nuevaFechaDesde = d.format(fechaDesde);
		String nuevaFechaHasta = d.format(fechaHasta);
		if (nuevaFechaDesde.equals(nuevaFechaHasta)) {
			if (logger.isDebugEnabled()) {
				logger.debug("fechaDesdeEqualFechaHasta(Date, Date) - end - return value={}", true);
			}
			return true;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("fechaDesdeEqualFechaHasta(Date, Date) - end - return value={}", false);
			}
			return false;
		}
	}

	// TODO: intent?
	public static void validarFechasDesdeHastaVigencias(Date vigenciaDesde, Date vigenciaHasta) throws DateParseException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarFechasDesdeHastaVigencias(vigenciaDesde={}, vigenciaHasta={}) - start", vigenciaDesde, vigenciaHasta);
		}

		validarFecha(vigenciaDesde);
		validarDesdeMenorHasta(vigenciaDesde, vigenciaHasta);

		if (logger.isDebugEnabled()) {
			logger.debug("validarFechasDesdeHastaVigencias(Date, Date) - end");
		}
	}

	// TODO: intent?
	public static void validarFechasGrisado(Date vigenciaDesde, Date vigenciaHasta, boolean anterior) throws DateParseException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarFechasGrisado(vigenciaDesde={}, vigenciaHasta={}, anterior={}) - start", vigenciaDesde, vigenciaHasta, anterior);
		}

		if (anterior == false)
			validarFecha(vigenciaDesde);
		validarDesdeMenorHasta(vigenciaDesde, vigenciaHasta);
		validarFechaHastaMayorActual(vigenciaDesde, vigenciaHasta);

		if (logger.isDebugEnabled()) {
			logger.debug("validarFechasGrisado(Date, Date, boolean) - end");
		}
	}

	public static void validarDesdeMenorHasta(Date vigenciaDesde, Date vigenciaHasta) throws DateParseException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarDesdeMenorHasta(vigenciaDesde={}, vigenciaHasta={}) - start", vigenciaDesde, vigenciaHasta);
		}

		if (fechaDesdePosteriorFechaHasta(vigenciaDesde, vigenciaHasta) == true)
			throw new TeException("La fecha de Vigencia Desde debe ser anterior a la " + "fecha de Vigencia Hasta", null);

		if (logger.isDebugEnabled()) {
			logger.debug("validarDesdeMenorHasta(Date, Date) - end");
		}
	}

	private static void validarFecha(Date vigenciaDesde) throws DateParseException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarFecha(vigenciaDesde={}) - start", vigenciaDesde);
		}

		if (!fechaDesdeEqualFechaHasta(vigenciaDesde, new Date()))
			if (fechaDesdePosteriorFechaHasta(new Date(), vigenciaDesde))
				throw new TeException("Fecha Desde debe ser mayor o igual a la Fecha Actual ", null);

		if (logger.isDebugEnabled()) {
			logger.debug("validarFecha(Date) - end");
		}
	}

	private static void validarFechaHastaMayorActual(Date vigenciaDesde, Date vigenciaHasta) throws DateParseException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarFechaHastaMayorActual(vigenciaDesde={}, vigenciaHasta={}) - start", vigenciaDesde, vigenciaHasta);
		}

		if (fechaDesdeEqualFechaHasta(vigenciaHasta, new Date()) == false)
			if (fechaDesdePosteriorFechaHasta(new Date(), vigenciaHasta))
				throw new TeException("Fecha Hasta debe ser mayor o igual a Fecha Actual ", null);

		if (logger.isDebugEnabled()) {
			logger.debug("validarFechaHastaMayorActual(Date, Date) - end");
		}
	}

	// TODO: intent?
	@SuppressWarnings("deprecation")
	public static boolean grisarFechas(Date vigenciaDesde) {
		if (logger.isDebugEnabled()) {
			logger.debug("grisarFechas(vigenciaDesde={}) - start", vigenciaDesde);
		}

		boolean retorno = false;
		// inicializo la fecha de hoy
		Date hoy = new Date();
		hoy.setHours(0);
		hoy.setMinutes(0);
		hoy.setSeconds(0);
		if (fechaDesdeEqualFechaHasta(vigenciaDesde, hoy) == false)
			if (fechaDesdePosteriorFechaHasta(vigenciaDesde, hoy) == false)
				retorno = true;

		if (logger.isDebugEnabled()) {
			logger.debug("grisarFechas(Date) - end - return value={}", retorno);
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
		if (logger.isDebugEnabled()) {
			logger.debug("fechasIguales(fecha_1={}, fecha_2={}) - start", fecha_1, fecha_2);
		}

		Boolean result;
		Calendar cal_1 = Calendar.getInstance();
		Calendar cal_2 = Calendar.getInstance();
		cal_1.setTime(fecha_1);
		cal_2.setTime(fecha_2);
		if (cal_1.get(Calendar.YEAR) == cal_2.get(Calendar.YEAR)
				&& cal_1.get(Calendar.MONTH) + 1 == cal_2.get(Calendar.MONTH) + 1
				&& cal_1.get(Calendar.DAY_OF_MONTH) == cal_2.get(Calendar.DAY_OF_MONTH))
			result = true;
		else
			result = false;

		if (logger.isDebugEnabled()) {
			logger.debug("fechasIguales(Date, Date) - end - return value={}", result);
		}
		return result;
	}

	public static String getShortDateToString(Date date) {
		if (logger.isDebugEnabled()) {
			logger.debug("getShortDateToString(date={}) - start", date);
		}

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String returnString = formato.format(date);
		if (logger.isDebugEnabled()) {
			logger.debug("getShortDateToString(Date) - end - return value={}", returnString);
		}
		return returnString;
	}

	public static String getLongDateToString(Date date) {
		if (logger.isDebugEnabled()) {
			logger.debug("getLongDateToString(date={}) - start", date);
		}

		DateFormat dfDefault = DateFormat.getInstance();
		String returnString = dfDefault.format(date);
		if (logger.isDebugEnabled()) {
			logger.debug("getLongDateToString(Date) - end - return value={}", returnString);
		}
		return returnString;
	}

	public static String getLongDateToStringWithSeconds(Date date) {
		if (logger.isDebugEnabled()) {
			logger.debug("getLongDateToStringWithSeconds(date={}) - start", date);
		}

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
		String returnString = formato.format(date);
		if (logger.isDebugEnabled()) {
			logger.debug("getLongDateToStringWithSeconds(Date) - end - return value={}", returnString);
		}
		return returnString;
	}

	public static void validarFechaDesdeHasta(Date vigenciaDesde) throws DateParseException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarFechaDesdeHasta(vigenciaDesde={}) - start", vigenciaDesde);
		}

		if (!fechaDesdeEqualFechaHasta(vigenciaDesde, new Date()))
			if (fechaDesdePosteriorFechaHasta(new Date(), vigenciaDesde))
				throw new TeException("Fecha Desde debe ser mayor o igual a la Fecha Actual ", null);

		if (logger.isDebugEnabled()) {
			logger.debug("validarFechaDesdeHasta(Date) - end");
		}
	}

	/**
	 * Obtiene la fecha actual
	 */
	public static Date getFechaActual() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaActual() - start");
		}

		Calendar cal = Calendar.getInstance();
		int anio = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		int dia = cal.get(Calendar.DATE);
		cal.set(anio, mes, dia);
		Date fechaActual = (Date) cal.getTime();

		if (logger.isDebugEnabled()) {
			logger.debug("getFechaActual() - end - return value={}", fechaActual);
		}
		return fechaActual;
	}

	public static String tranformarFechaMesEnLetrasLong(Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("tranformarFechaMesEnLetrasLong(fecha={}) - start", fecha);
		}

		if (fecha != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			String returnString = formatter.format(fecha);
			if (logger.isDebugEnabled()) {
				logger.debug("tranformarFechaMesEnLetrasLong(Date) - end - return value={}", returnString);
			}
			return returnString;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("tranformarFechaMesEnLetrasLong(Date) - end - return value={}", "");
		}
		return "";
	}

	public static Date tranformarFechaMesEnDateLong(String fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("tranformarFechaMesEnDateLong(fecha={}) - start", fecha);
		}

		if (!StringUtils.isEmpty(fecha)) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			try {
				Date d;
				d = formatter.parse(fecha);

				if (logger.isDebugEnabled()) {
					logger.debug("tranformarFechaMesEnDateLong(String) - end - return value={}", d);
				}
				return d;
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("tranformarFechaMesEnDateLong(String) - end - return value={null}");
		}
		return null;
	}

	public static String tranformarFechaMesEnLetrasShort(Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("tranformarFechaMesEnLetrasShort(fecha={}) - start", fecha);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String returnString = formatter.format(fecha);
		if (logger.isDebugEnabled()) {
			logger.debug("tranformarFechaMesEnLetrasShort(Date) - end - return value={}", returnString);
		}
		return returnString;
	}

	public static Boolean periodoMayorAlAnio(Date fechaDesde, Date fechaHasta) {
		if (logger.isDebugEnabled()) {
			logger.debug("periodoMayorAlAnio(fechaDesde={}, fechaHasta={}) - start", fechaDesde, fechaHasta);
		}

		String fin = formatearFecha(fechaHasta);
		String inicio = formatearFecha(fechaDesde);
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
			if (logger.isDebugEnabled()) {
				logger.debug("periodoMayorAlAnio(Date, Date) - end - return value={}", true);
			}
			return true;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("periodoMayorAlAnio(Date, Date) - end - return value={}", false);
			}
			return false;
		}
	}
	
  /**
   * Devuelve la fecha y hora del día calculado a partir de la fecha 'fecha'
   * despues de 'cantidadDeDiasHabiles' salteando los dias no hábiles. Devuelve
   * null si alguno de los parámetros es null
   *
   * @param fecha
   *          the fecha
   * @param cantidadDeDiasHabiles
   *          the cantidad de dias habiles
   * @return the date
   */
  public static Date fechaApartirDe(Date fecha, Integer cantidadDeDiasHabiles) {
		if (logger.isDebugEnabled()) {
			logger.debug("fechaApartirDe(fecha={}, cantidadDeDiasHabiles={}) - start", fecha, cantidadDeDiasHabiles);
		}

    Date ret = null;
    Integer cant = cantidadDeDiasHabiles;
    if (fecha != null && cantidadDeDiasHabiles != null) {
      ret = fecha;
      if (cantidadDeDiasHabiles > 0) {
        while (cant > 0) {
          aumentarUnDia(ret);
          if (!esDiaNoHabil(ret)) {
            cant--;
          }
        }
      } else if (cantidadDeDiasHabiles < 0) {
        while (cant < 0) {
          disminuirUnDia(ret);
          if (!esDiaNoHabil(ret)) {
            cant++;
          }
        }
      }
    }
    
		if (logger.isDebugEnabled()) {
			logger.debug("fechaApartirDe(Date) - end - return value={}", ret);
		}
		return ret;
  }
  
  /**
   * Aumentar un dia.
   *
   * @param currentDate
   *          the current date
   */
  public static void aumentarUnDia(Date currentDate) {
    currentDate.setTime(currentDate.getTime() + 86400000);
  }

  /**
   * Disminuir un dia.
   *
   * @param currentDate
   *          the current date
   */
  public static void disminuirUnDia(Date currentDate) {
    currentDate.setTime(currentDate.getTime() - 86400000);
  }
  
  /**
   * Devuelve true si una fecha es día no hábil y false si es dia hábil.
   *
   * @param fecha
   *          the fecha
   * @return true, if successful
   */
  public static boolean esDiaNoHabil(Date fecha) {
    int diaDeLaSemana = DateUtil.diaDeLaSemana(fecha);
    return diaDeLaSemana == SABADO || diaDeLaSemana == DOMINGO;
  }
  
}