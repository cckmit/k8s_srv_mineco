package com.egoveris.deo.web.satra;

import com.egoveris.sharedsecurity.base.model.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;

public class Utilitarios {

	private static final Logger logger = LoggerFactory.getLogger(Utilitarios.class);

	/**
	 * Convertir fecha a String con el formato dado.
	 * 
	 * @param Fecha
	 * @return
	 */
	public static String fechaToString(Date Fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(Fecha);
	}

	/**
	 * Convertir fecha a String con el formato dado.
	 * 
	 * @param Fecha
	 * @param formato
	 *            : Correspondiente al formato en el que se quiere mostrar la
	 *            fecha.
	 * @return
	 */
	public static String fechaToString(Date Fecha, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(Fecha);
	}

	/**
	 * Configura el formato de una fecha dada.
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date formatFecha(Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fechaFormat = null;
		try {
			fechaFormat = sdf.parse(sdf.format(fecha));
		} catch (ParseException e) {
			logger.error("Error al pasear", e);
		}
		return fechaFormat;

	}

	/**
	 * Valida el tamaño del string, para visualización, cortandolo en el caso de
	 * que sobrepase un limite de tamaño.
	 * 
	 * @param motivo
	 * @param cantidadCaracteres
	 *            : Límite de tamaño
	 * @return
	 */
	public static String motivoParseado(String motivo, int cantidadCaracteres) {
		String substringMotivo;
		if (motivo != null) {
			if (motivo.length() > cantidadCaracteres) {
				substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
			} else {
				substringMotivo = motivo;
			}
		} else {
			substringMotivo = "sin motivo";
		}
		return substringMotivo;
	}

	public static void doLogout(String urlLogOut) {
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect(urlLogOut);
	}

	public static void doLogout() {
		// String urlLogOutCAS = (String) SpringUtil.getBean("urlLogOutCAS");
		// SPRING SECURITY:
		String urlLogOutCAS = "/logout";
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect(urlLogOutCAS);
	}

	/**
	 * Reemplaza los retornos de linea por espacios en blanco.
	 * 
	 * @param cadena
	 *            String con retornos de linea.
	 * @return cadena sin retornos de linea.
	 */
	public static String reemplazarSaltosLinea(String cadena) {
		return cadena.replace('\n', ' ');
	}

	public static Usuario obtenerUsuarioActual() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
