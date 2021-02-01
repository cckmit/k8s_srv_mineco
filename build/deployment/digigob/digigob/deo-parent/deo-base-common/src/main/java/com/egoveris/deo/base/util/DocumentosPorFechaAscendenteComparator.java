package com.egoveris.deo.base.util;

import com.egoveris.deo.model.model.DocumentoDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compara dos documentos y devuelve el orden de acuerdo a la fecha de creación
 * en orden descendente con lo cual la lista quedará ordenada con el documento
 * más reciente primero.
 * 
 * @author bfontana
 * 
 */
public class DocumentosPorFechaAscendenteComparator implements Comparator<DocumentoDTO>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3838407165066507422L;

	@Override
	public int compare(DocumentoDTO d1, DocumentoDTO d2) {
		return -(d1.getFechaCreacion().compareTo(d2.getFechaCreacion()));
	}
}
