package com.egoveris.te.base.util;

import com.egoveris.te.base.model.TipoDocumentoDTO;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Emula los datos que deberia devolver la aplicacion de 
 * comunicaciones oficiales.
 * 
 * @author Rocco Gallo Citera
 *
 */
public class ComunicacionesServiceDummy {
	private static final Logger logger = LoggerFactory.getLogger(ComunicacionesServiceDummy.class);

	/**
	 * Metodo que emula la respuesta de comunicaciones respecto a los 
	 * tipos de documento.
	 * 
	 * @return
	 */
	public static List<TipoDocumentoDTO> obtenerTiposDocumentoComunicaciones(){
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumentoComunicaciones() - start");
		}

		ArrayList<TipoDocumentoDTO> tiposDocumento = new ArrayList<TipoDocumentoDTO>();
		TipoDocumentoDTO nota = new TipoDocumentoDTO();
		nota.setAcronimo("NO");
		nota.setCodigoTipoDocumentoSade("NO");
		nota.setEsEspecial(false);
		//nota.setId();
		nota.setIdTipoDocumentoSade(16);
		nota.setEsConfidencial(Boolean.FALSE);
		nota.setNombre("Notas");
		nota.setDescripcionTipoDocumentoSade("Nota");
		tiposDocumento.add(nota);
		TipoDocumentoDTO memo = new TipoDocumentoDTO();
		memo.setAcronimo("ME");
		memo.setCodigoTipoDocumentoSade("ME");
		memo.setEsEspecial(false);
		//memo.setId(16);
		memo.setIdTipoDocumentoSade(15);
		memo.setNombre("Memorandum");
		memo.setEsConfidencial(Boolean.FALSE);
		memo.setDescripcionTipoDocumentoSade("Memorandum");
		tiposDocumento.add(memo);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumentoComunicaciones() - end - return value={}", tiposDocumento);
		}
		return tiposDocumento;
	}
}
