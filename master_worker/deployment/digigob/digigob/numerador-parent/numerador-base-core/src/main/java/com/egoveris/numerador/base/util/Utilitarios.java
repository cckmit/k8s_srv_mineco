package com.egoveris.numerador.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class Utilitarios {
	/**
	 * Obtiene el anio actual.
	 * @return
	 */
	public static String obtenerAnioActual() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		String anio = simpleDateFormat.format(date);
		return anio;
	}
	/**
	 * Valida el tamaño del string, para visualización, cortandolo en el caso de que sobrepase un 
	 * limite de tamaño.
	 * @param motivo
	 * @param cantidadCaracteres: Límite de tamaño
	 * @return
	 */
	public static String mensajeParseado(String mensaje, int cantidadCaracteres) {
		String substringMotivo;
		if(StringUtils.isNotEmpty(mensaje))	{
				if (mensaje.length() > cantidadCaracteres) {
					substringMotivo = mensaje.substring(0, cantidadCaracteres) + "...";
				} else {
					
					substringMotivo = mensaje.substring(0, mensaje.length());
				}
		
		}else{
			substringMotivo="No hay mensaje de error disponible";	
		}
	return substringMotivo;
	}
}
