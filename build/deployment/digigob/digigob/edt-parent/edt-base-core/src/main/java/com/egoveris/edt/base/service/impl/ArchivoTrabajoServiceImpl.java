package com.egoveris.edt.base.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.IArchivoTrabajoService;

/**
 * Implementación de la gestión de archivos de trabajo, para un registro
 * temporal o definitivo.
 *
 */
@Service("archivoTrabajoService")
public class ArchivoTrabajoServiceImpl implements IArchivoTrabajoService {

	private static Logger log = LoggerFactory.getLogger(ArchivoTrabajoServiceImpl.class);

	@Autowired
	@Qualifier("consultaDocumento3Service")
	private IExternalConsultaDocumentoService consultaDocumentoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.gob.gcaba.rib.services.impl.IArchivoTrabajoService#
	 * obtenerArchivoTrabajo (ar.gob.gcaba.rib.common.domain.ArchivoTrabajo)
	 */
	@Override
	public byte[] obtenerArchivoTrabajo(final String numeroGEDO) {
		byte[] contenido = null;
		final RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
		request.setNumeroDocumento(numeroGEDO);
		try {
			contenido = consultaDocumentoService.consultarDocumentoPdf(request);
		} catch (final ParametroInvalidoConsultaException ex) {
			log.error(ex.getMessage(), ex);
		} catch (final DocumentoNoExisteException ex) {
			log.error(ex.getMessage(), ex);
		} catch (final SinPrivilegiosException ex) {
			log.error(ex.getMessage(), ex);
		} catch (final ErrorConsultaDocumentoException ex) {
			log.error(ex.getMessage(), ex);
		}
		return contenido;

	}

	@Override
	public byte[] obtenerArchivoTrabajo(final String numeroGEDO, final String nombreUsuario) throws NegocioException {

		byte[] contenido = null;
		final RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
		request.setNumeroDocumento(numeroGEDO);
		request.setUsuarioConsulta(nombreUsuario);

		try {
			contenido = consultaDocumentoService.consultarDocumentoPdf(request);
		} catch (final ParametroInvalidoConsultaException ex) {
			log.error(ex.getMessage(), ex);
		} catch (final DocumentoNoExisteException ex) {
			log.error(ex.getMessage(), ex);
		} catch (final SinPrivilegiosException ex) {
			log.error(ex.getMessage(), ex);
		} catch (final ErrorConsultaDocumentoException ex) {
			log.error(ex.getMessage(), ex);
		}
		return contenido;
	}
}
