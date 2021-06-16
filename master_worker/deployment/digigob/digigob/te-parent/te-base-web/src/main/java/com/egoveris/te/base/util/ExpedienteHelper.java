package com.egoveris.te.base.util;

import java.util.List;
import java.util.stream.Collectors;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

public class ExpedienteHelper {

	public static DocumentoDTO obtenerUltimoDoc(final String acronimo, ExpedienteElectronicoDTO ee) {
		List<DocumentoDTO> documentos = ee.getDocumentos()
						.stream()
							.filter(d -> d.getTipoDocAcronimo().equals(acronimo))
							.sorted((d1, d2) -> d1.getFechaAsociacion().compareTo(d2.getFechaAsociacion()))
							.collect(Collectors.toList());
		return (!documentos.isEmpty()) ? documentos.get(documentos.size() - 1) : null;
	}

}
