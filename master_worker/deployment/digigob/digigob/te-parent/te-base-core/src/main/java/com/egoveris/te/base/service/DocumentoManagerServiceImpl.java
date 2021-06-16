package com.egoveris.te.base.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.util.media.Media;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumentoUsuarioExterno;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesServicios;

@Service(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE)
public class DocumentoManagerServiceImpl implements DocumentoManagerService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoManagerServiceImpl.class);

	@Autowired
	private DocumentoGedoService documentoGedoService;

	@Autowired
	private String ecosistema;

	@Autowired
	private IExternalGenerarDocumentoService externalGenerarDocumentoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.vucfront.base.service.SolDocumentoService#
	 * generarDocumentoGedo(com.egoveris.vucfront.model.model.SolDocumentoDTO,
	 * com.egoveris.vucfront.model.model.SegUsuarioDTO)
	 */
	@Override
	public String generarDocumentoGedo(Media mediaUpld, String acronimo, Usuario usuario) {
		RequestExternalGenerarDocumentoUsuarioExterno request = new RequestExternalGenerarDocumentoUsuarioExterno();
		request.setSistemaOrigen("TE");
		request.setAcronimoTipoDocumento(acronimo);
	
		request.setReferencia(acronimo);

		
		request.setUsuario(usuario.getUsername());
		request.setData(mediaUpld.getByteData());

		request.setNombreYApellido(usuario.getNombreApellido());
		request.setCargo(usuario.getCargo());
		request.setReparticion(usuario.getCodigoReparticion());
		ResponseExternalGenerarDocumento response = this.externalGenerarDocumentoService.generarDocumentoGEDO(request);
		String acronimoGedo = response.getNumero();
		return acronimoGedo;
	}

	@Override
	public DocumentoDTO buscarDocumentoEspecial(String tipo, String anio, String numero, String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarDocumentoEspecial(tipo={}, anio={}, numero={}, reparticion={}) - start", tipo, anio,
					numero, reparticion);
		}

		String numeroEspecial = armarNumeracionEspecial(tipo, anio, numero, reparticion);
		DocumentoDTO documentoEncontrado = documentoGedoService.obtenerDocumentoPorNumeroEspecial(numeroEspecial);

		if (documentoEncontrado != null) {
			String relativeUri = documentoEncontrado.getNumeroSade() + ".pdf";
			documentoEncontrado.setNombreArchivo(relativeUri);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarDocumentoEspecial(String, String, String, String) - end - return value={}",
					documentoEncontrado);
		}

		return documentoEncontrado;
	}

	@Override
	public DocumentoDTO buscarDocumentoEstandar(String codSade) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarDocumentoEstandar(codSade={}) - start", codSade);
		}

		DocumentoDTO returnDocumento = documentoGedoService.obtenerDocumentoPorNumeroEstandar(codSade);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarDocumentoEstandar(String) - end - return value={}", returnDocumento);
		}

		return returnDocumento;
	}

	@Override
	public DocumentoDTO buscarDocumentoEstandar(String tipo, Integer anioDocumento, Integer numeroDocumento,
			String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarDocumentoEstandar(tipo={}, anioDocumento={}, numeroDocumento={}, reparticion={}) - start",
					tipo, anioDocumento, numeroDocumento, reparticion);
		}

		String numeroSADE = armarNumeracionEstandar(tipo, anioDocumento, numeroDocumento, reparticion);
		
		DocumentoDTO documentoEncontrado = documentoGedoService.obtenerDocumentoPorNumeroEstandar(numeroSADE);

		if (documentoEncontrado != null) {
			String relativeUri = documentoEncontrado.getNumeroSade() + ".pdf";
			documentoEncontrado.setNombreArchivo(relativeUri);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarDocumentoEstandar(String, Integer, Integer, String) - end - return value={}",
					documentoEncontrado);
		}

		return documentoEncontrado;
	}

	/**
	 * Utiliza los parametros para armar la numeracion especial que utilizan por
	 * ejemplo las comunicaciones.
	 * 
	 * @param acronimo
	 * @param anio
	 * @param numero
	 * @param codigoReparticion
	 * @return
	 */
	private String armarNumeracionEspecial(String acronimo, String anio, String numero, String codigoReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("armarNumeracionEspecial(acronimo={}, anio={}, numero={}, codigoReparticion={}) - start",
					acronimo, anio, numero, codigoReparticion);
		}

		String returnString = "";

		if (ecosistema != null && !StringUtils.isEmpty(ecosistema.trim())) {
			returnString = acronimo + "-" + anio + "-" + numero + "-" + ecosistema.trim() + "-"
					+ codigoReparticion.trim();
		} else {
			returnString = acronimo + "-" + anio + "-" + numero + "-" + codigoReparticion.trim();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("armarNumeracionEspecial(String, String, String, String) - end - return value={}",
					returnString);
		}

		return returnString;
	}

	/**
	 * Utiliza los parametros para armar la numeracion de SADE.
	 * 
	 * @param codigoActuacionSade
	 * @param anioDocumento
	 * @param numeroDocumento
	 * @param codigoReparticion
	 * @return
	 */
	private String armarNumeracionEstandar(String codigoActuacionSade, Integer anioDocumento, Integer numeroDocumento,
			String codigoReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"armarNumeracionEstandar(codigoActuacionSade={}, anioDocumento={}, numeroDocumento={}, codigoReparticion={}) - start",
					codigoActuacionSade, anioDocumento, numeroDocumento, codigoReparticion);
		}

		// FIX para caracter que aparenta ser un espacio, pero no lo es
		char charPosibleSecuencia[] = ecosistema.toCharArray();

		for (int j = 0; j < charPosibleSecuencia.length; j++) {
			if (Character.getNumericValue(charPosibleSecuencia[j]) == -1) {
				charPosibleSecuencia[j] = ' ';
			}
		}

		ecosistema = String.valueOf(charPosibleSecuencia);

		String numeroFormateado = BusinessFormatHelper.completarConCerosNumActuacion(numeroDocumento);
		String returnString = codigoActuacionSade + "-" + anioDocumento.toString() + "-" + numeroFormateado + "-"
				+ ecosistema + "-" + codigoReparticion.trim();

		if (logger.isDebugEnabled()) {
			logger.debug("armarNumeracionEstandar(String, Integer, Integer, String) - end - return value={}",
					returnString);
		}

		return returnString;
	}

}
