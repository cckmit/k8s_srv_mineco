package com.egoveris.edt.base.util.zk;

import com.egoveris.edt.base.exception.DateException;
import com.egoveris.edt.base.util.ApplicationContextProvider;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapAccessor;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class UtilsDate.
 */
public class UtilsDate {

  /**
   * Instantiates a new utils date.
   */
  private UtilsDate() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }

	/** The Constant INT_23. */
	public static final int INT_23 = 23;
	/** The Constant INT_59. */
	private static final int INT_59 = 59;
	/** The Constant INT_58. */
	public static final int INT_58 = 58;
	/** The Constant INT_999. */
	public static final int INT_999 = 999;
	
  private static String[] months = new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
      "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };

  /** The days of week. */
  private static HashMap<Integer, String> daysOfWeek = new HashMap<Integer, String>();

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(UtilsDate.class);

  @Autowired
  private static ILdapAccessor sadeLdapAccessor;

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
    fechaFormateada += " de " + months[month] + " de";
    fechaFormateada += " " + calendar.get(Calendar.YEAR);
    return fechaFormateada;
  }

  /**
   * Obtener fecha actual vigencia.
   *
   * @return the java.sql. date
   */
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
    String fechaFormateada = formato.format(fecha);
    return fechaFormateada;

  }

  /**
   * Devuelve un booleano que indica si la FECHA DESDE es posterior a la FECHA
   * HASTA.
   *
   * @param fechaDesde the fecha desde
   * @param fechaHasta the fecha hasta
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
   * Devuelve la diferencia entre dos fechas.
   *
   * @param fechaHasta          que se quiere comparar
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
    int a = Integer.parseInt(anio);
    int m = Integer.parseInt(mes);
    int d = Integer.parseInt(dia);
    // paso a entero
    int a2 = Integer.parseInt(anio2);
    int m2 = Integer.parseInt(mes2);
    int d2 = Integer.parseInt(dia2);

    Calendar cal1 = Calendar.getInstance();
    cal1.set(a, m, d);
    Calendar cal2 = Calendar.getInstance();
    cal2.set(a2, m2, d2);
    Date date1 = cal1.getTime();
    Date date2 = cal2.getTime();
    long dif = date2.getTime() - date1.getTime();
    // Un dia tiene 86400000 milisegundos
    int resultado = (int) (dif / 86400000);
    return resultado;
  }

  /**
   * Compara que dos fechas sean iguales para esto le da un formato y compara
   * los string.
   *
   * @param fechaDesde the fecha desde
   * @param fechaHasta the fecha hasta
   * @return trye si son iguales y false si son distintos
   */
  @SuppressWarnings("deprecation")
  public static Boolean fechaDesdeEqualFechaHasta(Date fechaDesde, Date fechaHasta) {
    fechaHasta.setHours(0);
    fechaHasta.setMinutes(0);
    fechaHasta.setSeconds(0);
    SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyyy");
    String nuevaFechaDesde = sdf.format(fechaDesde);
    String nuevaFechaHasta = sdf.format(fechaHasta);
    if (nuevaFechaDesde.equals(nuevaFechaHasta)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Validar fechas desde hasta vigencias.
   *
   * @param vigenciaDesde the vigencia desde
   * @param vigenciaHasta the vigencia hasta
   * @throws DateException the date exception
   */
  public static void validarFechasDesdeHastaVigencias(Date vigenciaDesde, Date vigenciaHasta)
      throws DateException {
    validarFecha(vigenciaDesde);
    validarDesdeMenorHasta(vigenciaDesde, vigenciaHasta);
  }

  /**
   * Validar fechas grisado.
   *
   * @param vigenciaDesde the vigencia desde
   * @param vigenciaHasta the vigencia hasta
   * @param anterior the anterior
   * @throws DateException the date exception
   */
  public static void validarFechasGrisado(Date vigenciaDesde, Date vigenciaHasta, boolean anterior)
      throws DateException {
    if (anterior == false) {
      validarFecha(vigenciaDesde);
    }
    validarDesdeMenorHasta(vigenciaDesde, vigenciaHasta);
    validarFechaHastaMayorActual(vigenciaHasta);
  }

  /**
   * Validar desde menor hasta.
   *
   * @param vigenciaDesde the vigencia desde
   * @param vigenciaHasta the vigencia hasta
   * @throws DateException the date exception
   */
  public static void validarDesdeMenorHasta(Date vigenciaDesde, Date vigenciaHasta)
      throws DateException {
    if (fechaDesdePosteriorFechaHasta(vigenciaDesde, vigenciaHasta) == true) {
      throw new DateException(
          "La fecha de Vigencia Desde debe ser anterior a la " + "fecha de Vigencia Hasta");
    }
  }

  private static void validarFecha(Date vigenciaDesde) throws DateException {
    if (!fechaDesdeEqualFechaHasta(vigenciaDesde, new Date())
        && fechaDesdePosteriorFechaHasta(new Date(), vigenciaDesde)) {
      throw new DateException("Fecha Desde debe ser mayor o igual a la Fecha Actual ");
    }
  }

  private static void validarFechaHastaMayorActual(Date vigenciaHasta) throws DateException {
    if (!fechaDesdeEqualFechaHasta(vigenciaHasta, new Date())
        && fechaDesdePosteriorFechaHasta(new Date(), vigenciaHasta)) {
      throw new DateException("Fecha Hasta debe ser mayor o igual a Fecha Actual ");
    }
  }

  /**
   * Grisar fechas.
   *
   * @param vigenciaDesde the vigencia desde
   * @return true, if successful
   */
  @SuppressWarnings("deprecation")
  public static boolean grisarFechas(Date vigenciaDesde) {
    boolean retorno = false;
    // inicializo la fecha de hoy
    Date hoy = new Date();
    hoy.setHours(0);
    hoy.setMinutes(0);
    hoy.setSeconds(0);
    if (fechaDesdeEqualFechaHasta(vigenciaDesde, hoy) == false
        && fechaDesdePosteriorFechaHasta(vigenciaDesde, hoy) == false) {
      retorno = true;
    }
    return retorno;

  }

  /**
   * Comparo dos fechas para ver si son las mismas.
   *
   * @param fecha1 the fecha 1
   * @param fecha2 the fecha 2
   * @return un boolean que dice si son iguales o no
   */
  public static Boolean fechasIguales(Date fecha1, Date fecha2) {
    Boolean result;
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(fecha1);
    cal2.setTime(fecha2);
    if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
        && cal1.get(Calendar.MONTH) + 1 == cal2.get(Calendar.MONTH) + 1
        && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
      result = true;
    } else {
      result = false;
    }
    return result;
  }

  /**
   * Gets the short date to string.
   *
   * @param date the date
   * @return the short date to string
   */
  public static String getShortDateToString(Date date) {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    return formato.format(date);
  }

  /**
   * Gets the long date to string.
   *
   * @param date the date
   * @return the long date to string
   */
  public static String getLongDateToString(Date date) {
    DateFormat dfDefault = DateFormat.getInstance();
    return dfDefault.format(date);
  }

  /**
   * Gets the long date to string with seconds.
   *
   * @param date the date
   * @return the long date to string with seconds
   */
  public static String getLongDateToStringWithSeconds(Date date) {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
    return formato.format(date);
  }

  /**
   * Validar fecha desde hasta.
   *
   * @param vigenciaDesde the vigencia desde
   * @throws DateException the date exception
   */
  public static void validarFechaDesdeHasta(Date vigenciaDesde) throws DateException {
    if (!fechaDesdeEqualFechaHasta(vigenciaDesde, new Date())
        && fechaDesdePosteriorFechaHasta(new Date(), vigenciaDesde)) {
      throw new DateException("Fecha Desde debe ser mayor o igual a la Fecha Actual ");
    }
  }

  /**
   * Obtiene la fecha actual.
   *
   * @return the fecha actual
   */
  public static Date getFechaActual() {
    Calendar cal = Calendar.getInstance();
    int anio = cal.get(Calendar.YEAR);
    int mes = cal.get(Calendar.MONTH);
    int dia = cal.get(Calendar.DATE);
    cal.set(anio, mes, dia);
    Date fechaActual = (Date) cal.getTime();
    return fechaActual;
  }

  /**
   * Tranformar fecha mes en letras long.
   *
   * @param fecha the fecha
   * @return the string
   */
  public static String tranformarFechaMesEnLetrasLong(Date fecha) {
    if (fecha != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
      return formatter.format(fecha);
    }
    return "";
  }

  /**
   * Tranformar fecha mes en date long.
   *
   * @param fecha the fecha
   * @return the date
   */
  public static Date tranformarFechaMesEnDateLong(String fecha) {
    if (!StringUtils.isEmpty(fecha)) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

      try {
        Date date;
        date = formatter.parse(fecha);
        return date;
      } catch (ParseException ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
      }
    }
    return null;
  }

  /**
   * Tranformar fecha mes en letras short.
   *
   * @param fecha the fecha
   * @return the string
   */
  public static String tranformarFechaMesEnLetrasShort(Date fecha) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    return formatter.format(fecha);
  }

  /**
   * Periodo mayor al anio.
   *
   * @param fechaDesde the fecha desde
   * @param fechaHasta the fecha hasta
   * @return the boolean
   */
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
    int a = Integer.parseInt(anio);
    int m = Integer.parseInt(mes);
    int d = Integer.parseInt(dia);
    // paso a entero fin
    int a2 = Integer.parseInt(anio2);
    int m2 = Integer.parseInt(mes2);
    int d2 = Integer.parseInt(dia2);

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
   * Read input stream.
   *
   * @param in the in
   * @return the byte[]
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static byte[] readInputStream(InputStream in) throws IOException {
    BufferedInputStream bis = new BufferedInputStream(in);
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    int result = bis.read();
    while (result != -1) {
      byte res = (byte) result;
      buf.write(res);
      result = bis.read();
    }
    return buf.toByteArray();
  }

  /**
   * Descargar archivo temporal.
   *
   * @param identificador the identificador
   * @param nombreArch the nombre arch
   * @return the byte[]
   * @throws IOException 
   */
  @SuppressWarnings("resource")
  public static byte[] descargarArchivoTemporal(String identificador, String nombreArch) throws IOException {
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(System.getProperty("java.io.tmpdir")
          + System.getProperty("file.separator") + identificador + "_" + nombreArch);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      int read;
      read = inputStream.read();
      while (read != -1) {
        bos.write(read);
        read = inputStream.read();
      }
      inputStream.close();
      return bos.toByteArray();
    } catch (FileNotFoundException ex) {
      LOGGER.error(ex.getMessage(), ex);
    } catch (IOException ex) {
      LOGGER.error(ex.getMessage(), ex);      
    }finally {
       if(inputStream != null){
         inputStream.close();
       }
    }
    return new byte[0];
  }

  /**
   * devuelve el nombre del archivo con extension .pdf para poder abrirlo
   *
   * @param nombre the nombre
   * @return the string
   */
  public static String convertirNombreAPDF(String nombre) {
    int pos = nombre.indexOf('.');
    return nombre.substring(0, pos) + ".pdf";

  }

  /**
   * Usuario ldap valido.
   *
   * @param nombre the nombre
   * @return the string
   */
  public static String usuarioLdapValido(String nombre) {
    sadeLdapAccessor = (ILdapAccessor) ApplicationContextProvider.getApplicationContext()
        .getBean("sadeLdapAccessor");
    return sadeLdapAccessor.buscarUsuarioLdap(nombre);
  }

  /**
   * Encode pwd.
   *
   * @param typePwd the type pwd
   * @param pwd the pwd
   * @return the string
   */
  public static String encodePwd(String typePwd, String pwd) {
    try {
      byte[] b = MessageDigest.getInstance(typePwd).digest(pwd.getBytes());
      return "{" + typePwd + "}" + new String(Base64.encodeBase64(b));
    } catch (NoSuchAlgorithmException ex) {
      LOGGER.error(ex.getMessage(), ex);
    }
    return null;
  }

  /**
   * Convert byte to file.
   *
   * @param bytes the bytes
   * @param nombre the nombre
   * @return the file
   * @throws IOException 
   */
  public static File convertByteToFile(byte[] bytes, String nombre) throws IOException {
    FileOutputStream outputStream = null;
    try {
      ByteArrayInputStream fis = new ByteArrayInputStream(bytes);
      int read;
      File salida = File.createTempFile(nombre, ".pdf");
      outputStream = new FileOutputStream(salida);
      while ((read = fis.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }
      outputStream.flush();
      return salida;
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }finally {
       if(outputStream != null){
         outputStream.close();
       }
    }
    return null;
  }

  /**
   * Convert input stream to byte.
   *
   * @param file the file
   * @return the input stream
   */
  @SuppressWarnings("unused")
  public static InputStream convertInputStreamToByte(File file) {
    try {
      InputStream inputStream = new FileInputStream(file);
      byte[] buf = new byte[307200];
      inputStream.close();
      final InputStream mediais = new ByteArrayInputStream(buf);
      return mediais;
    } catch (IOException ex) {
      LOGGER.error(ex.getMessage(), ex);
    }
    return null;

  }

  /**
   * Formatear codigo caratula from sade.
   *
   * @param codigoCaratula the codigo caratula
   * @return the string
   */
  public static String formatearCodigoCaratulaFromSade(String codigoCaratula) {
    String result = null;
    if (StringUtils.isNotEmpty(codigoCaratula) && codigoCaratula.length() > 17) {
      result = codigoCaratula.substring(0, 2) + '-' + codigoCaratula.substring(2, 6) + '-'
          + codigoCaratula.substring(6, 14) + '-' + codigoCaratula.substring(14, 17) + '-'
          + codigoCaratula.substring(17, codigoCaratula.length());
    } 
    if(result != null){
      return result.trim();
    }
    
    return result;  
  }

  /**
   * Obtener codigo reparticion.
   *
   * @param codigoCaratula the codigo caratula
   * @return the string
   */
  public static String obtenerCodigoReparticion(String codigoCaratula) {
    String result = null;
    if (StringUtils.isNotEmpty(codigoCaratula) && codigoCaratula.length() > 17) {
      result = codigoCaratula.substring(21, codigoCaratula.length());
    }
    if(result != null){
      return result.trim();
    }
  
    return result;
  }

  /**
   * Obtener user name.
   *
   * @param nombreUsuario the nombre usuario
   * @return the string
   */
  public static String obtenerUserName(String nombreUsuario) {
    String result = "";
    if (StringUtils.isNotEmpty(nombreUsuario)) {
      String[] ual = StringUtils.split(nombreUsuario, '(');
      if (ual.length > 1) {
        String[] eul = StringUtils.split(ual[2], ')');
        if (eul.length > 0) {
          result = eul[0];
        }
      }
    }
    return result;
  }

  /**
   * Obtener nombre usuario.
   *
   * @param nombreUsuario the nombre usuario
   * @return the string
   */
  public static String obtenerNombreUsuario(String nombreUsuario) {
    String result = "";
    if (StringUtils.isNotEmpty(nombreUsuario)) {
      String[] ual = StringUtils.split(nombreUsuario, '(');
      if (ual.length > 0) {
        result = ual[0];
      }
    }
    return result;

  }

  /**
   * Completar con ceros num actuacion.
   *
   * @param numActuacion the num actuacion
   * @return the string
   */
  public static String completarConCerosNumActuacion(String numActuacion) {
    String aux = numActuacion;
    while (aux.length() < 8) {
      aux = "0" + aux;
    }
    return aux;
  }
  
	/**
	 * Devuelve una fecha en 23:59:59:999.
	 * 
	 * @param fecha Date.
	 * @return Date
	 */
	public static Date formatoFechaFin(Date fecha) {
		if (fecha == null) {
			return null;
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		gc.set(Calendar.HOUR_OF_DAY, INT_23);
		gc.set(Calendar.MINUTE, INT_59);
		gc.set(Calendar.SECOND, INT_58);
		gc.set(Calendar.MILLISECOND, INT_999);
		return gc.getTime();
	}

	/**
	 * Devuelve una fecha en 00:00:00:000.
	 * 
	 * @param fecha Date.
	 * @return Date
	 */
	public static Date formatoFechaInicio(Date fecha) {
		if (fecha == null) {
			return null;
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fecha);
		gc.set(Calendar.HOUR_OF_DAY, 00);
		gc.set(Calendar.MINUTE, 00);
		gc.set(Calendar.SECOND, 00);
		gc.set(Calendar.MILLISECOND, 000);
		return gc.getTime();
	}

}