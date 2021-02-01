package com.egoveris.deo.base.decision;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.egoveris.deo.base.dao.IDatosUsuarioDAO;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.IDecisionUsoPortaFirmaService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.model.Usuario;

@Service
@Transactional
public class DecisionUsoPortaFirmaService implements DecisionHandler, IDecisionUsoPortaFirmaService {

	private static final long serialVersionUID = -2362279574110942906L;

	private static final Logger logger = LoggerFactory.getLogger(DecisionUsoPortaFirmaService.class);

	@SuppressWarnings("deprecation")
	@Autowired
	private IDatosUsuarioDAO usuariosCCOODAO;
	@Autowired

	private FirmaConjuntaService firmaConjuntaService;

	@Autowired
	private TipoDocumentoService tipoDocumentoService;

	@Autowired
	@Qualifier("gestionArchivosWebDavServiceImpl")
	private GestionArchivosWebDavService gestionArchivosWebDavService;

	@Autowired
	protected ProcessEngine processEngine;
	@Value("${app.sistema.gestor.documental}")
	private String gestorDocumental;

	@SuppressWarnings("deprecation")
	public String decide(OpenExecution execution) {

		String executionId = execution.getId();

		TipoDocumentoDTO tipoDocumento = tipoDocumentoService.buscarTipoDocumentoPorId(
				Integer.valueOf((String) execution.getVariable(Constantes.VAR_TIPO_DOCUMENTO)));
		
		String firmante = (String) execution.getVariable(Constantes.VAR_USUARIO_FIRMANTE);
		String revisor = (String) execution.getVariable(Constantes.VAR_USUARIO_REVISOR);
		
		Boolean etapaFirmaConjunta =  false;
		Boolean etapaFirma = false;
		Boolean etapaFirmaConCertificado = false;
		
		etapaFirmaConjunta = (Boolean) execution.getVariable(Constantes.REVISAR_DOCUMENTO_CON_FIRMA_CONJUNTA);
		etapaFirma = (Boolean) execution.getVariable(Constantes.REVISAR_FIRMA);
		etapaFirmaConCertificado = (Boolean) execution.getVariable(Constantes.REVISAR_DOCUMENTO_CON_CERTIFICADO);
		
		if (tipoDocumento.getEsFirmaConjunta() && (etapaFirmaConjunta && !etapaFirma && !etapaFirmaConCertificado) && (revisor != null )) {


			execution.setVariable(Constantes.VAR_USUARIO_VERIFICADOR, revisor);
			execution.setVariable(Constantes.VAR_USUARIO_REVISOR, revisor);
			execution.setVariable(Constantes.VAR_USUARIO_VERIFICADOR_ORIGINAL, revisor);

			return Constantes.TRANSICION_REVISAR_DOCUMENTO_FIRMA_CONJUNTA;
			
		} else if (tipoDocumento.getEsFirmaConjunta() && (etapaFirmaConjunta && etapaFirma && etapaFirmaConCertificado) && (firmante != null)){
			
			List<FirmanteDTO> destinanteRevisor = this.firmaConjuntaService.buscarRevisorFirmante(executionId);
			for(FirmanteDTO firm : destinanteRevisor) {
				if(!firm.getEstadoFirma()) {
					execution.setVariable(Constantes.VAR_USUARIO_VERIFICADOR, firm.getUsuarioRevisor());
					break;
				}
			}
			
			execution.setVariable(Constantes.VAR_USUARIO_REVISOR, firmante);
			execution.setVariable(Constantes.VAR_USUARIO_VERIFICADOR_ORIGINAL, firmante);
			
			if (tipoDocumentoService
					.buscarTipoDocumentoPorId(
							Integer.valueOf((String) execution.getVariable(Constantes.VAR_TIPO_DOCUMENTO)))
					.getEsFirmaConjunta()) {
				subirRecursoRevisionWebDav(execution);
			}
			
			return Constantes.TRANSICION_REVISAR_DOCUMENTO_FIRMA_CONJUNTA;
		}

		else {
			
			if (tipoDocumentoService
					.buscarTipoDocumentoPorId(
							Integer.valueOf((String) execution.getVariable(Constantes.VAR_TIPO_DOCUMENTO)))
					.getEsFirmaConjunta()) {
				subirRecursoRevisionWebDav(execution);
			}

			if (tipoDocumentoService
					.buscarTipoDocumentoPorId(
							Integer.valueOf((String) execution.getVariable(Constantes.VAR_TIPO_DOCUMENTO)))
					.getEsFirmaExternaConEncabezado()) {
				return Constantes.TRANSICION_FIRMA_GEDO;
			}

			if (tipoDocumentoService
					.buscarTipoDocumentoPorId(
							Integer.valueOf((String) execution.getVariable(Constantes.VAR_TIPO_DOCUMENTO)))
					.getEsFirmaExterna()) {
				return Constantes.TRANSICION_FIRMA_GEDO;
			}

			return Constantes.TRANSICION_FIRMA_GEDO;
		}
	}

	public boolean firmaEnPortaFirma(String username) {
		Usuario datosUsuario = null;
		try {
			datosUsuario = usuariosCCOODAO.getDatosUsuario(username);
		} catch (SQLException e) {
			logger.error("Error al acceder a la capa de Datos", e);
		}
		if (datosUsuario != null && datosUsuario.getExternalizarFirmaGEDO()) {
			return true;
		}
		return false;
	}

	/**
	 * Sets the usuario porta firma.
	 *
	 * @param userName
	 *            the user name
	 * @param tienePortafirma
	 *            the tiene portafirma
	 * @the SQL exception
	 */
	public void setUsuarioPortaFirma(String userName, boolean tienePortafirma) {
		try {
			this.usuariosCCOODAO.setUsuarioPortaFirma(userName, tienePortafirma);
		} catch (SQLException e) {
			logger.error("Error al acceder a la capa de Datos", e);
		}

	}

	/**
	 * Subir recurso revision web dav.
	 *
	 * @param execution
	 *            the execution
	 */
	public void subirRecursoRevisionWebDav(OpenExecution execution) {
		String nombreTemp = (String) execution.getVariable(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
		Integer idFirmante = (Integer) execution.getVariable(Constantes.ID_FIRMANTE);

		String directorioSalida = nombreTemp.substring(0, nombreTemp.lastIndexOf('.'));
		String archivoSalida = null;
		if (idFirmante == null) {
			idFirmante = 1;
		}
		if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
			// WEBDAV
			archivoSalida = nombreTemp.substring(0, nombreTemp.lastIndexOf('.'));
			archivoSalida = archivoSalida.concat("v" + idFirmante);
			archivoSalida = archivoSalida.concat(".pdf");
		}

		byte[] contenidoTemporal = null;
		try {

			// WEBDAV
			contenidoTemporal = this.gestionArchivosWebDavService.obtenerRecursoTemporalWebDav(nombreTemp);

		} catch (Exception e) {
			logger.error("Error al obtener el contenido temporal ", e);
		}
		try {

			// WEBDAV
			this.gestionArchivosWebDavService.subirArchivoDirectorioTemporalRevisionWebDav(directorioSalida,
					archivoSalida, contenidoTemporal);

		} catch (Exception e) {
			logger.error("Error al subir el directorio temporal", e);
		}

		idFirmante++;
		execution.setVariable(Constantes.ID_FIRMANTE, idFirmante);
	}

}
