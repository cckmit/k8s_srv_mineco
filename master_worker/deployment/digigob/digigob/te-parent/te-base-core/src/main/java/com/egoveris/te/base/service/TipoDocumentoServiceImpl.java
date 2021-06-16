package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.ResponseMetadata;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.exception.ErrorConsultaTipoDocumentoException;
import com.egoveris.deo.ws.exception.TipoDocumentoNoExisteException;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.edt.model.model.ActuacionSadeDTO;
import com.egoveris.edt.ws.service.IActuacionService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.FamiliaTipoDocumentoDTO;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.model.tipo.TipoDocumento;
import com.egoveris.te.base.model.tipo.TipoDocumentoGenerador;
import com.egoveris.te.base.model.trata.Trata;
import com.egoveris.te.base.model.trata.TrataTipoDocumento;
import com.egoveris.te.base.model.trata.TrataTipoDocumentoAuditoria;
import com.egoveris.te.base.repository.tipo.TipoDocumentoGeneradorRepository;
import com.egoveris.te.base.repository.tipo.TipoDocumentoRepository;
import com.egoveris.te.base.repository.trata.TrataTipoDocumentoAuditoriaRepository;
import com.egoveris.te.base.repository.trata.TrataTipoDocumentoRepository;
import com.egoveris.te.base.util.ComparadorTipoDocumento;
import com.egoveris.te.base.util.ComunicacionesServiceDummy;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;

@Service
@Transactional
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	private Logger logger = LoggerFactory.getLogger(TipoDocumentoServiceImpl.class);

	private DozerBeanMapper mapper = new DozerBeanMapper();

	@Autowired
	@Qualifier(ConstantesServicios.EXTERNAL_TIPO_DOC_SERVICE)
	private IExternalTipoDocumentoService externalTipoDocumentoService;
	@Autowired
	private IActuacionService externalActuacionService;
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	@Autowired
	private TrataTipoDocumentoRepository trataTipoDocumentoRepository;
	@Autowired
	private TrataTipoDocumentoAuditoriaRepository trataTipoDocumentoAuditoriaRepository;
	@Autowired
	private TipoDocumentoGeneradorRepository tipoDocumentoGeneradorRepository;

	@Override // listo
	public List<TrataTipoDocumentoDTO> buscarTrataTipoDocumentoPorTrata(final TrataDTO trata) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTrataTipoDocumentoPorTrata(trata={}) - start", trata);
		}

		final Trata trataSearch = mapper.map(trata, Trata.class);

		@SuppressWarnings("unchecked")
		final List<TrataTipoDocumentoDTO> returnList = ListMapper
				.mapList(trataTipoDocumentoRepository.findByTrata(trataSearch), mapper, TrataTipoDocumentoDTO.class);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTrataTipoDocumentoPorTrata(Trata) - end - return value={}", returnList);
		}
		return returnList;

	}

	@Override
	public void actualizarTrataTipoDocumentos(final List<TrataTipoDocumentoDTO> ttdModificados, // listo
			final String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTrataTipoDocumentos(ttdModificados={}, usuario={}) - start", ttdModificados,
					usuario);
		}

		for (TrataTipoDocumentoDTO aux : ttdModificados) {
			if (aux.getEstaHabilitado()) {
				logger.debug("Actualizando TrataTipoDocumento: " + aux.getIdTrataTipoDocumento());
				trataTipoDocumentoRepository.save(mapper.map(aux, TrataTipoDocumento.class));
				cargarAuditoria(aux, TrataTipoDocumentoDTO.ESTADO_ACTIVO, usuario);
			} else {
				logger.debug("Borrando TrataTipoDocumento: " + aux.getIdTrataTipoDocumento());
				trataTipoDocumentoRepository.deleteByAcronimoGedoAndTrataId(aux.getAcronimoGEDO(),
						aux.getTrata().getId());
				cargarAuditoria(aux, TrataTipoDocumentoDTO.ESTADO_INACTIVO, usuario);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTrataTipoDocumentos(List<TrataTipoDocumento>, String) - end");
		}
	}

	@Override // listo
	public List<TipoDocumentoDTO> buscarTipoDocumentoByEstadoFiltradosManual(final String estado,
			final boolean esManual) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTipoDocumentoByEstadoFiltradosManual(estado={}, esManual={}) - start", estado,
					esManual);
		}

		try {
			final List<ResponseTipoDocumento> resultExternal = externalTipoDocumentoService
					.buscarTipoDocumentoByEstadoFiltradosManual("ALTA", true);
			final List<TipoDocumentoDTO> returnList = convertExternalToTipoDocList(resultExternal);

			if (logger.isDebugEnabled()) {
				logger.debug("buscarTipoDocumentoByEstadoFiltradosManual(String, boolean) - end - return value={}",
						returnList);
			}
			return returnList;
		} catch (final ErrorConsultaTipoDocumentoException e) {
			logger.error("Error al obtener datos de GEDO", e);
			throw new TeRuntimeException("Error al obtener datos de GEDO", e);
		}

	}

	@Override // listo
	public TipoDocumentoDTO buscarTipoDocumentoByUso(final String uso) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTipoDocumentoByUso(uso={}) - start", uso);
		}

		final TipoDocumentoDTO returnTipoDocumento = mapper.map(tipoDocumentoRepository.findByUsoEnEE(uso),
				TipoDocumentoDTO.class);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTipoDocumentoByUso(String) - end - return value={}", returnTipoDocumento);
		}
		return returnTipoDocumento;
	}

	@Override // listo
	public void cargarAuditoria(final TrataTipoDocumentoDTO trataTipoDocumento, final String estado,
			final String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarAuditoria(trataTipoDocumento={}, estado={}, usuario={}) - start", trataTipoDocumento,
					estado, usuario);
		}

		final TrataTipoDocumentoAuditoria auditoria = new TrataTipoDocumentoAuditoria();
		auditoria.setAcronimoGEDO(trataTipoDocumento.getAcronimoGEDO());
		auditoria.setEstado(estado);
		auditoria.setFechaModif(new Date());
		auditoria.setNombre(usuario);
		auditoria.setTrata(mapper.map(trataTipoDocumento.getTrata(), Trata.class));
		trataTipoDocumentoAuditoriaRepository.save(auditoria);

		if (logger.isDebugEnabled()) {
			logger.debug("cargarAuditoria(TrataTipoDocumento, String, String) - end");
		}
	}

	@Override // listo
	public TipoDocumentoDTO consultarTipoDocumentoPorAcronimo(final String acronimo) {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarTipoDocumentoPorAcronimo(acronimo={}) - start", acronimo);
		}

		TipoDocumentoDTO tipoDocumento = new TipoDocumentoDTO();
		try {
			tipoDocumento = this
					.convertExternalToTipoDoc(externalTipoDocumentoService.consultarTipoDocumentoPorAcronimo(acronimo));
		} catch (final ErrorConsultaTipoDocumentoException e) {
			logger.error("consultarTipoDocumentoPorAcronimo ErrorConsultaTipoDocumentoException " + e);
		} catch (final TipoDocumentoNoExisteException e) {
			logger.error("consultarTipoDocumentoPorAcronimo TipoDocumentoNoExisteException " + e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("consultarTipoDocumentoPorAcronimo(String) - end - return value={}", tipoDocumento);
		}
		return tipoDocumento;
	}

	/**
	 * Convert external to tipo doc.
	 *
	 * @param responseTipoDocumento
	 *            the response tipo documento
	 * @return the tipo documento DTO
	 */
	private TipoDocumentoDTO convertExternalToTipoDoc( // listo
			final ResponseTipoDocumento responseTipoDocumento) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertExternalToTipoDoc(responseTipoDocumento={}) - start", responseTipoDocumento);
		}

		final TipoDocumentoDTO tipoDoc = new TipoDocumentoDTO();
		tipoDoc.setAcronimo(responseTipoDocumento.getAcronimo());
		tipoDoc.setCodigoTipoDocumentoSade(responseTipoDocumento.getCodigoTipoDocumentoSade());
		// Mantengo la misma logica que en mapearResultSetTipoDoc
		tipoDoc.setDescripcionTipoDocumentoSade(responseTipoDocumento.getNombre());
		tipoDoc.setEsEspecial(responseTipoDocumento.getEsEspecial());
		tipoDoc.setEsConfidencial(responseTipoDocumento.getEsConfidencial());
		tipoDoc.setEstado(responseTipoDocumento.getEstado());

		tipoDoc.setIdFormulario(responseTipoDocumento.getIdFormulario());
		tipoDoc.setNombre(responseTipoDocumento.getNombre());
		tipoDoc.setTipoProduccion(responseTipoDocumento.getTipoProduccion());

		tipoDoc.setEsManual(responseTipoDocumento.getEsManual());
		tipoDoc.setDescripcionGedo(responseTipoDocumento.getDescripcion());

		tipoDoc.setTieneTemplate(responseTipoDocumento.getTieneTemplate());
		tipoDoc.setTieneToken(responseTipoDocumento.getTieneToken());

		tipoDoc.setEsFirmaExterna(responseTipoDocumento.getEsFirmaExterna());

		tipoDoc.setEsFirmaConjunta(responseTipoDocumento.getEsFirmaExterna());

		tipoDoc.setFamilia(new FamiliaTipoDocumentoDTO(responseTipoDocumento.getFamilia()));

		tipoDoc.setEsNotificable(responseTipoDocumento.getEsNotificable());

		tipoDoc.setDescripcion(responseTipoDocumento.getDescripcion());

		tipoDoc.setListaDatosVariables(convertResponseMetadaToMetaData(responseTipoDocumento.getListaDatosVariables()));

		if (logger.isDebugEnabled()) {
			logger.debug("convertExternalToTipoDoc(ResponseTipoDocumento) - end - return value={}", tipoDoc);
		}
		return tipoDoc;
	}

	/**
	 * Convert external to tipo doc list.
	 *
	 * @param resultExternal
	 *            the result external
	 * @return the list
	 */
	private List<TipoDocumentoDTO> convertExternalToTipoDocList( // listo
			final List<ResponseTipoDocumento> resultExternal) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertExternalToTipoDocList(resultExternal={}) - start", resultExternal);
		}

		final List<TipoDocumentoDTO> result = new ArrayList<TipoDocumentoDTO>();
		for (final ResponseTipoDocumento responseTipoDocumento : resultExternal) {
			final TipoDocumentoDTO tipoDoc = new TipoDocumentoDTO();
			
			tipoDoc.setIdTipoDocumentoGedo(responseTipoDocumento.getId());
			
			tipoDoc.setAcronimo(responseTipoDocumento.getAcronimo());
			tipoDoc.setCodigoTipoDocumentoSade(responseTipoDocumento.getCodigoTipoDocumentoSade());
			// Mantengo la misma logica que en mapearResultSetTipoDoc
			tipoDoc.setDescripcionTipoDocumentoSade(responseTipoDocumento.getNombre());
			tipoDoc.setEsEspecial(responseTipoDocumento.getEsEspecial());
			tipoDoc.setEsConfidencial(responseTipoDocumento.getEsConfidencial());
			tipoDoc.setEstado(responseTipoDocumento.getEstado());

			tipoDoc.setIdFormulario(responseTipoDocumento.getIdFormulario());
			tipoDoc.setNombre(responseTipoDocumento.getNombre());
			tipoDoc.setTipoProduccion(responseTipoDocumento.getTipoProduccion());

			tipoDoc.setEsManual(responseTipoDocumento.getEsManual());
			tipoDoc.setDescripcionGedo(responseTipoDocumento.getDescripcion());

			tipoDoc.setTieneTemplate(responseTipoDocumento.getTieneTemplate());
			tipoDoc.setTieneToken(responseTipoDocumento.getTieneToken());

			tipoDoc.setEsFirmaExterna(responseTipoDocumento.getEsFirmaExterna());

			tipoDoc.setEsFirmaConjunta(responseTipoDocumento.getEsFirmaExterna());

			tipoDoc.setFamilia(new FamiliaTipoDocumentoDTO(responseTipoDocumento.getFamilia()));

			tipoDoc.setEsNotificable(responseTipoDocumento.getEsNotificable());

			tipoDoc.setDescripcion(responseTipoDocumento.getDescripcion());

			result.add(tipoDoc);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("convertExternalToTipoDocList(List<ResponseTipoDocumento>) - end - return value={}", result);
		}
		return result;
	}

	/**
	 * Convert response metada to meta data.
	 *
	 * @param responseMetadataList
	 *            the response metadata list
	 * @return the list
	 */
	private List<MetadataDTO> convertResponseMetadaToMetaData(final List<ResponseMetadata> responseMetadataList) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertResponseMetadaToMetaData(responseMetadataList={}) - start", responseMetadataList);
		}

		final List<MetadataDTO> metadataList = new ArrayList<MetadataDTO>();
		for (final ResponseMetadata responseMetadata : responseMetadataList) {
			final MetadataDTO metadata = new MetadataDTO();
			metadata.setNombre(responseMetadata.getNombre());
			metadata.setTipo(responseMetadata.getTipo() == null ? null : new Integer(responseMetadata.getTipo()));
			metadata.setObligatoriedad(responseMetadata.isObligatoriedad());
			metadataList.add(metadata);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("convertResponseMetadaToMetaData(List<ResponseMetadata>) - end - return value={}",
					metadataList);
		}
		return metadataList;
	}

	@Override
	public List<TipoDocumentoDTO> obtenerNotificables() { // listo
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNotificables() - start");
		}
		// This block is the old threaded method recargarTipoDocs()
		final List<TipoDocumentoDTO> listaResult = obtenerTodosTipoDocumentosGedoExternal();
		listaResult.addAll(ComunicacionesServiceDummy.obtenerTiposDocumentoComunicaciones());
		Collections.<TipoDocumentoDTO>sort(listaResult, new ComparadorTipoDocumento());

		final List<TipoDocumentoDTO> listaDocumentosFiltrada = new ArrayList<>();

		for (final TipoDocumentoDTO tipoDocumento : listaResult) {
			if (tipoDocumento.getEsNotificable() != null && tipoDocumento.getEsNotificable()) {
				listaDocumentosFiltrada.add(tipoDocumento);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNotificables() - end - return value={}", listaDocumentosFiltrada);
		}
		return listaDocumentosFiltrada;
	}

	@Override
	public List<TipoDocumentoDTO> obtenerTemplates() { // listo
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTemplates() - start");
		}

		// This block is the old threaded method recargarTipoDocs()
		final List<TipoDocumentoDTO> listaResult = obtenerTodosTipoDocumentosGedoExternal();
		listaResult.addAll(ComunicacionesServiceDummy.obtenerTiposDocumentoComunicaciones());
		Collections.<TipoDocumentoDTO>sort(listaResult, new ComparadorTipoDocumento());

		final List<TipoDocumentoDTO> listaDocumentosFiltrada = new ArrayList<>();
		for (final TipoDocumentoDTO tipoDocumento : listaResult) {
			if (tipoDocumento.getTipoProduccion() != null
					&& tipoDocumento.getTipoProduccion().equals(TipoDocumentoDTO.TEMPLATE)) {
				listaDocumentosFiltrada.add(tipoDocumento);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTemplates() - end - return value={}", listaDocumentosFiltrada);
		}
		return listaDocumentosFiltrada;
	}

	@Override
	public TipoDocumentoDTO obtenerTipoDocumento(final String acronimo) { // listo
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTipoDocumento(tipo={}) - start", acronimo);
		}
		ResponseTipoDocumento responseTipoDocumento = null;
		if (null != acronimo) {
		  responseTipoDocumento = externalTipoDocumentoService
				.buscarTipoDocumentoByAcronimo(acronimo);
		}
		TipoDocumentoDTO retorno = null;

		if (responseTipoDocumento != null) {
			retorno = convertExternalToTipoDoc(responseTipoDocumento);
		}

		return retorno;
	}

	/**
	 * Obtiene todos los documentos electronicos que se pueden incorporar a un
	 * expediente.
	 *
	 * @return the list
	 */
	@Override
	public List<TipoDocumentoDTO> obtenerTiposDocumento() { // listo
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumento() - start");
		}

		final ArrayList<TipoDocumentoDTO> listaTipoDocumento = new ArrayList<TipoDocumentoDTO>();
		
		List<ActuacionSadeDTO> resultado = externalActuacionService
				.todasLasActuaciones();

		for (final ActuacionSadeDTO actuacion : resultado) {
			TipoDocumentoDTO tipo = new TipoDocumentoDTO();
			tipo.setCodigoTipoDocumentoSade(actuacion.getCodigoActuacion());
			tipo.setIdTipoDocumentoSade(actuacion.getId());
			tipo.setDescripcionTipoDocumentoSade(actuacion.getNombreActuacion());

			listaTipoDocumento.add(tipo);
		}
		
		//listaTipoDocumento.addAll(ComunicacionesServiceDummy.obtenerTiposDocumentoComunicaciones());

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumento() - end - return value={}", listaTipoDocumento);
		}
		return listaTipoDocumento;
	}

	@Override
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGedo() {
		final List<TipoDocumentoDTO> retorno = new ArrayList<>();
		final List<ResponseTipoDocumento> resultado = externalTipoDocumentoService.getTipoDocumentoEspecial();

		if (resultado != null) {
			for (ResponseTipoDocumento aux : resultado) {
				retorno.add(responseToTipoDocumento(aux));
			}
		}

		return retorno;
	}


	@Override
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGedo(final String estado, // listo
			final boolean esManual) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumentoGedo(estado={}, esManual={}) - start", estado, esManual);
		}

		// This block is the old threaded method recargarTipoDocs()
		final List<TipoDocumentoDTO> listaResult = obtenerTodosTipoDocumentosGedoExternal();
		listaResult.addAll(ComunicacionesServiceDummy.obtenerTiposDocumentoComunicaciones());
		Collections.<TipoDocumentoDTO>sort(listaResult, new ComparadorTipoDocumento());

		final List<TipoDocumentoDTO> listafiltrada = new ArrayList<>();
		for (final TipoDocumentoDTO tipoDocumento : listaResult) {
			if (tipoDocumento.getEstado() != null && tipoDocumento.getEstado().equals(ConstantesWeb.ESTADO_ALTA)
					&& tipoDocumento.getEsManual() != null && tipoDocumento.getEsManual().equals(esManual)) {
				listafiltrada.add(tipoDocumento);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumentoGedo(String, boolean) - end - return value={}", listafiltrada);
		}
		return listafiltrada;
	}

	/**
	 * Obtiene todos los tipos de documento electronicos posibles que estan
	 * gestionados a travez de GEDO.
	 *
	 * @return the list
	 */
	@SuppressWarnings("unused")
	private List<TipoDocumentoDTO> obtenerTiposDocumentoGEDO() {
		final List<TipoDocumentoDTO> retorno = new ArrayList<>();
		final List<ResponseTipoDocumento> resultado = externalTipoDocumentoService.getTiposDocumento();

		if (resultado != null) {
			for (ResponseTipoDocumento aux : resultado) {
				retorno.add(responseToTipoDocumento(aux));
			}
		}

		return retorno;
	}

	/**
	 * BUSCO EL TIPO DE DOCUMENTO CORRESPONDIENTE CON EL ID de TIPO DOCUMENTO DE
	 * GEDO DEL DOCUMENTO ENCONTRADO EN GEDO ANTERIORMENTE.
	 *
	 * @param tipoDocGedo
	 *            the tipo doc gedo
	 * @return the tipo documento DTO
	 */
	@Override
	public TipoDocumentoDTO obtenerTiposDocumentoGEDO(final Long tipoDocGedo) {
		return responseToTipoDocumento(externalTipoDocumentoService.buscarTipoDocumentoPorId(tipoDocGedo.intValue()));
	}

	/**
	 * Obtiene los tipos de documentos Gedo filtrados por aquellos especiales.
	 *
	 * @return the list
	 */
	@Override
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGEDOEspecial() {
		final List<TipoDocumentoDTO> retorno = new ArrayList<>();
		final List<ResponseTipoDocumento> resultado = externalTipoDocumentoService.getTipoDocumentoEspecial();

		if (resultado != null) {
			for (ResponseTipoDocumento aux : resultado) {
				retorno.add(responseToTipoDocumento(aux));
			}
		}

		return retorno;
	}

	@Override
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGEDOHabilitados() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumentoGEDOHabilitados() - start"); //$NON-NLS-1$
		}
		final List<ResponseTipoDocumento> resultado = externalTipoDocumentoService.getTiposDocumentoHabilitados();
		
		final List<TipoDocumentoDTO> returnList = convertExternalToTipoDocList(resultado);
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumentoGEDOHabilitados() - end - return value={}", returnList);
		}
		return returnList;
	}

	/**
	 * Obtiene los tipos de documentos Gedo filtrados por familia.
	 *
	 * @param nombreFamilia
	 *            the nombre familia
	 * @return the list
	 */
	@Override
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGEDOPorFamilia(final String nombreFamilia) {

		final List<TipoDocumentoDTO> retorno = new ArrayList<>();
		final List<ResponseTipoDocumento> resultado = externalTipoDocumentoService
				.getTipoDocumentoByFamilia(nombreFamilia);

		if (resultado != null) {
			for (ResponseTipoDocumento aux : resultado) {
				retorno.add(responseToTipoDocumento(aux));
			}
		}

		return retorno;
	}

	/**
	 * Obtiene todos los tipos de documento electronicos posibles que estan
	 * gestionados a traves de GEDO de la cache!.
	 *
	 * @return the list
	 */
	@Override
	public List<TipoDocumentoDTO> obtenerTodosDocumentosGedo() {
		// This block is the old threaded method recargarTipoDocs()
		final List<TipoDocumentoDTO> listaResult = obtenerTodosTipoDocumentosGedoExternal();
		listaResult.addAll(ComunicacionesServiceDummy.obtenerTiposDocumentoComunicaciones());
		Collections.<TipoDocumentoDTO>sort(listaResult, new ComparadorTipoDocumento());
		return listaResult;
	}

	/**
	 * Obtener todos tipo documentos gedo external.
	 *
	 * @return the list
	 */
	private List<TipoDocumentoDTO> obtenerTodosTipoDocumentosGedoExternal() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodosTipoDocumentosGedoExternal() - start");
		}

		try {
			final List<ResponseTipoDocumento> resultExternal = externalTipoDocumentoService.consultarTiposDocumento();
			final List<TipoDocumentoDTO> returnList = convertExternalToTipoDocList(resultExternal);
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerTodosTipoDocumentosGedoExternal() - end - return value={}", returnList);
			}
			return returnList;
		} catch (final ErrorConsultaTipoDocumentoException e) {
			logger.error("Error al obtener datos de GEDO", e);
			throw new TeRuntimeException("Error al obtener datos de GEDO", e);
		}
	}

	@Override
	public String tipoDocumentoGeneracion(final Long tipoDocGenerado) { // listo
		if (logger.isDebugEnabled()) {
			logger.debug("tipoDocumentoGeneracion(tipoDocGenerado={}) - start", tipoDocGenerado);
		}

		final TipoDocumentoGenerador resultado = tipoDocumentoGeneradorRepository.findOne(tipoDocGenerado);
		final String retorno = resultado.getTipoDocGenerador();

		if (logger.isDebugEnabled()) {
			logger.debug("tipoDocumentoGeneracion(Integer) - end - return value={}", retorno);
		}
		return retorno;
	}

	/**
	 * Convdrt ResponseTipoDocumento from DEO to TipoDocumento TE.
	 *
	 * @param ResponseTipoDocumento
	 *            from DEO
	 * @return TipoDocumentoDTO from TE
	 */
	private TipoDocumentoDTO responseToTipoDocumento(final ResponseTipoDocumento rtd) {

		final TipoDocumentoDTO tipo = new TipoDocumentoDTO();
		tipo.setAcronimo(rtd.getAcronimo());
		tipo.setDescripcion(rtd.getDescripcion());
		tipo.setCodigoTipoDocumentoSade(rtd.getCodigoTipoDocumentoSade());
		tipo.setEsEspecial(rtd.getEsEspecial());
		tipo.setIdTipoDocumentoSade(rtd.getIdTipoDocumentoSade());
		tipo.setIdTipoDocumentoGedo(rtd.getId());
		tipo.setNombre(rtd.getNombre());
		tipo.setDescripcionTipoDocumentoSade(rtd.getNombre());
		tipo.setEstado(rtd.getEstado());
		tipo.setEsConfidencial(rtd.getEsConfidencial());
		tipo.setEsNotificable(rtd.getEsNotificable());

		return tipo;
	}

	@Override
	public void guardar(TipoDocumentoDTO tipoDocumento) {
		tipoDocumentoRepository.save(mapper.map(tipoDocumento, TipoDocumento.class));
	}

	@Override
	public List<TipoDocumentoDTO> obtnerTiposDocumentoGEDOHabilitadosFamilia() {
		return null;
	}

	@Override
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGedoImportados() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiposDocumentoGedoImportado() - start");
		}
		
		try {
			final List<ResponseTipoDocumento> resultExternal = externalTipoDocumentoService.getDocumentTypeByProduction(ProductionEnum.IMPORT);
			final List<TipoDocumentoDTO> returnList = convertExternalToTipoDocList(resultExternal);
			
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerTiposDocumentoGedoImportado() - end - return value={}", returnList);
			}
			
			return returnList;
		} catch (final ErrorConsultaTipoDocumentoException e) {
			logger.error("Error al obtener datos de GEDO", e);
			throw new TeRuntimeException("Error al obtener datos de GEDO", e);
		}
	}

}