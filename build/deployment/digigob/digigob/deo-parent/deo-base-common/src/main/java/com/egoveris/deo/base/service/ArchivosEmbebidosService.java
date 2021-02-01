package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.ArchivoDuplicadoException;
import com.egoveris.deo.base.exception.ExtensionesFaltantesException;
import com.egoveris.deo.base.exception.FormatoInvalidoException;
import com.egoveris.deo.base.exception.TamanoInvalidoException;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;

import java.util.List;
import java.util.Set;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface ArchivosEmbebidosService {

	public void subirArchivoEmbebidoTemporal(ArchivoEmbebidoDTO archivoEmbebido) throws ApplicationException;

	public void verificarArchivo(byte[] dataFile, TipoDocumentoDTO tipoDocumento) throws FormatoInvalidoException,
			TamanoInvalidoException;

	public List<ArchivoEmbebidoDTO> buscarArchivosEmbebidosPorProceso(String workflowId);

	public byte[] obtenerArchivoEmbebidoWebDav(String pathRelativo, String nombreArchivo) throws ApplicationException;

	public void borrarArchivoEmbebidoWebDav(String pathRelativo, String nombreArchivo);

	public void eliminarAchivoEmbebido(ArchivoEmbebidoDTO archivoEmbebido);

	public void validarNombre(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos, ArchivoEmbebidoDTO archivoEmbebidoComposer)
			throws ArchivoDuplicadoException;

	public Set<TipoDocumentoEmbebidosDTO> buscarTipoDocEmbebidos(TipoDocumentoDTO tipoDocumento);

	public String getMimetype(byte[] dataFile);

	public void verificarObligatoriedadExtensiones(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos,
			TipoDocumentoDTO tipoDocumento) throws ExtensionesFaltantesException;

	public Integer guardarArchivoEmbebido(ArchivoEmbebidoDTO archivoEmbebido);
}
