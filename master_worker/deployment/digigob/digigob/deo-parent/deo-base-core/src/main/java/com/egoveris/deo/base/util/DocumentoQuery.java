package com.egoveris.deo.base.util;

import com.egoveris.deo.base.model.Documento;
import com.egoveris.deo.base.model.TipoDocumento;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DocumentoQuery {

	public DocumentoQuery desdeHasta(Date desde, Date hasta);

	public DocumentoQuery reparticion(String reparticion);

	public DocumentoQuery usuario(String usuario);

	public DocumentoQuery criterioOrden(String orden, String criterio);

	public DocumentoQuery usuarioFirmante(String usuarioFirmante);

	public DocumentoQuery documentoMetaData(DocumentoMetadataDTO documentoMetaData);

	public DocumentoQuery tipoDocumento(TipoDocumento tipoDocumento);

	public DocumentoQuery numeroSade(String numeroSade);

	public DocumentoQuery numeroSadePapel(String numeroSadePapel);

	public DocumentoQuery numeroEspecial(String numeroEspecial);

	public DocumentoQuery paginar(int paginaActual, int cantidadResultados);

	public List<Documento> listar();

	public long contar();

	/**
	 * Arma un criterio de <code>Documento</code>
	 * 
	 * @return <code>Criteria</code>
	 * @throws IllegalAccessError
	 */
	public List<Documento> getDocumentosPorCriteriaQuery(Map inputMap) throws IllegalAccessError;

}
