package com.egoveris.te.base.service;

import org.zkoss.util.media.Media;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.DocumentoDTO;

public interface DocumentoManagerService {
	
	/**
	 * Busca un documento especial en Gedo a partir de los parametros provistos,
	 * con los que arma el numero de documento especial. Todos los parametros 
	 * deben ser provistos para poder encontrar una coincidencia. 
	 * Para evitar errores se debe validar previamente que el tipo de 
	 * documento sea un documento especial
	 * 
	 * @param tipo
	 * @param numero
	 * @param anio
	 * @param reparticion
	 * @return documento encontrado correspondiente al criterio, o null
	 */
	public DocumentoDTO buscarDocumentoEspecial(String tipo, String anio ,String numero , String reparticion);

	public DocumentoDTO buscarDocumentoEstandar(String codSade);
	
	/**
	 * Analiza si el tipo de documento es propio de Comunicaciones o de GEDO y luego de eso
	 * busca en la base del sistema correspondiente los datos del documento.
	 * 
	 * @param tipo
	 * @param anioDocumento
	 * @param numeroDocumento
	 * @param reparticion
	 * @return
	 */
	public DocumentoDTO buscarDocumentoEstandar(String tipo, Integer anioDocumento,Integer numeroDocumento, String reparticion);
	
	/**
	 * Crea documento en DEO
	 * 
	 * @param mediaUpld
	 * @return
	 */
	public String generarDocumentoGedo(Media mediaUpld, String acronimo, Usuario usuario);
}
