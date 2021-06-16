package com.egoveris.deo.base.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.base.dao.DocumentoTemporalDao;
import com.egoveris.deo.base.exception.ExistenDocumentosDelTipoException;
import com.egoveris.deo.base.exception.ExistenDocumentosDelTipoJbpmException;
import com.egoveris.deo.base.exception.GEDOServicesExceptions;
import com.egoveris.deo.base.model.FamiliaTipoDocumento;
import com.egoveris.deo.base.model.ReparticionHabilitada;
import com.egoveris.deo.base.model.TipoDocumento;
import com.egoveris.deo.base.model.TipoDocumentoAuditoria;
import com.egoveris.deo.base.model.TipoDocumentoEmbebidos;
import com.egoveris.deo.base.model.TipoDocumentoTemplate;
import com.egoveris.deo.base.repository.FamiliaTipoDocumentoRepository;
import com.egoveris.deo.base.repository.FormatoTamanoArchivoRepository;
import com.egoveris.deo.base.repository.ReparticionHabilitadaRepository;
import com.egoveris.deo.base.repository.TipoDocumentoAuditoriaRepository;
import com.egoveris.deo.base.repository.TipoDocumentoEmbebidosRepository;
import com.egoveris.deo.base.repository.TipoDocumentoRepository;
import com.egoveris.deo.base.repository.TipoDocumentoTemplateRepository;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.NumeracionEspecialService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.model.model.FormatoTamanoArchivoDTO;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.model.model.TipoDocumentoAuditoriaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.model.model.TipoDocumentoReducidoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TipoDocumentoServiceImpl.class);
	private static final String DESC = "descripcion";
	private static final String ACRONIMO = "acronimo";
	private static final String NOMBRE = "nombre";
	private static final String COMUNICABLE = "comunicable";
	private static final String ESTADO = "estado";
	private static final String MANUAL = "manual";
	private static final String FAMILIA = "familia";

	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepo;
	@Autowired
	private TipoDocumentoTemplateRepository tipoDocumentoTemplateRepo;
	@Autowired
	private FamiliaTipoDocumentoRepository familiatipoDocumentoRepo;
	@Autowired
	private BuscarDocumentosGedoService buscarDocumentosGedoService;
	@Autowired
	private TipoDocumentoAuditoriaRepository tipoDocumentoAuditoriaRepo;
	@Autowired
	private NumeracionEspecialService numeracionEspecialService;
	@Autowired
	private ReparticionHabilitadaRepository reparticionHabilitadaRepo;
	@Autowired
	private FormatoTamanoArchivoRepository formatoTamanoArchivoRepo;
	@Autowired
	private TipoDocumentoEmbebidosRepository tipoDocumentoEmbebidosRepo;
	@Autowired
	private DocumentoTemporalDao documentoTemporalDao;

	private DozerBeanMapper mapper = new DozerBeanMapper();

	@Override
	public void actualizarTareasExistentesConNuevaVersion(TipoDocumentoDTO tipoDocumento,
			TipoDocumentoDTO tipoDocumentoAnterior) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarTareasExistentesConNuevaVersion(TipoDocumentoDTO, TipoDocumentoDTO) - start"); //$NON-NLS-1$
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("actualizarTareasExistentesConNuevaVersion(TipoDocumentoDTO, TipoDocumentoDTO) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public TipoDocumentoAuditoriaDTO crearTipoDocumento(TipoDocumentoDTO tipoDocumento, String userName) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("crearTipoDocumento(TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
		}

		TipoDocumento docDb = null;
		// Crear reparticionHabilitada TODAS.
		if (tipoDocumento.getListaReparticiones() == null || tipoDocumento.getListaReparticiones().isEmpty()
				|| (tipoDocumento.getId() != null)) {
			tipoDocumento.setListaReparticiones(new HashSet<ReparticionHabilitadaDTO>());
			tipoDocumento.getListaReparticiones().add(crearReparticionTodas(tipoDocumento));
		}

		try {
			if (tipoDocumento.getListaTemplates() != null && !tipoDocumento.getListaTemplates().isEmpty()) {
				// save template
				Set<TipoDocumentoTemplateDTO> listaTemplate = tipoDocumento.getListaTemplates();
				// delete
				tipoDocumento.setListaTemplates(null);

				tipoDocumento.setAcronimo(tipoDocumento.getAcronimo().toUpperCase());
				tipoDocumento.setEstado("ALTA");
				docDb = this.tipoDocumentoRepo.save(this.mapper.map(tipoDocumento, TipoDocumento.class));
				tipoDocumento.setListaTemplates(listaTemplate);
			} else {
				Set<TipoDocumentoEmbebidosDTO> listaEmbebidos = null;
				if (tipoDocumento.getTipoDocumentoEmbebidos() != null && !tipoDocumento.getTipoDocumentoEmbebidos().isEmpty()) {
					
					listaEmbebidos = tipoDocumento.getTipoDocumentoEmbebidos();
					tipoDocumento.setTipoDocumentoEmbebidos(null);

				}
				tipoDocumento.setAcronimo(tipoDocumento.getAcronimo().toUpperCase());
				tipoDocumento.setEstado("ALTA");
				docDb = this.tipoDocumentoRepo.save(this.mapper.map(tipoDocumento, TipoDocumento.class));
				
				if (listaEmbebidos != null && !listaEmbebidos.isEmpty()) {
					tipoDocumento.setTipoDocumentoEmbebidos(listaEmbebidos);
				}
				
			}
			if (docDb == null || docDb.getId() == null) {
				throw new RuntimeException("not insert tipodocumento");
			}
		} catch (RuntimeException e) {
			LOGGER.error("crearTipoDocumento(TipoDocumentoDTO, String)", e.getMessage()); //$NON-NLS-1$

			throw new GEDOServicesExceptions("Error al dar de alta el tipo de documento. " + e.getMessage(), e);

		}

		if (tipoDocumento.getListaTemplates() != null && !tipoDocumento.getListaTemplates().isEmpty()) {
			TipoDocumentoTemplateDTO tdt = tipoDocumento.getListaTemplates().iterator().next();
			tdt.getTipoDocumentoTemplatePK().setIdTipoDocumento(docDb.getId());
			tdt.getTipoDocumentoTemplatePK().setVersion(Double.valueOf(docDb.getVersion()).doubleValue());
			this.tipoDocumentoTemplateRepo.save(this.mapper.map(tdt, TipoDocumentoTemplate.class));
		}
		
		if (tipoDocumento.getTipoDocumentoEmbebidos() != null && !tipoDocumento.getTipoDocumentoEmbebidos().isEmpty()) {
	    for (TipoDocumentoEmbebidosDTO tdEMb: tipoDocumento.getTipoDocumentoEmbebidos()) {
	    	tdEMb.getTipoDocumentoEmbebidosPK().getTipoDocumentoId().setId(docDb.getId());
	    	this.tipoDocumentoEmbebidosRepo.save(this.mapper.map(tdEMb, TipoDocumentoEmbebidos.class));
	    }
		}

		TipoDocumentoAuditoriaDTO tipoDocumentoAuditoria = new TipoDocumentoAuditoriaDTO(tipoDocumento, userName);

		tipoDocumentoAuditoria.setTipoOperacion(Constantes.AUDITORIA_OP_ALTA);

		return tipoDocumentoAuditoria;
	}

	/**
	 * @param tipoDocumentoAuditoria
	 */
	public void saveTipoDocumento(TipoDocumentoAuditoriaDTO tipoDocumentoAuditoria) {
		TipoDocumentoAuditoria docAud = this.mapper.map(tipoDocumentoAuditoria, TipoDocumentoAuditoria.class);
		this.tipoDocumentoAuditoriaRepo.save(docAud);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("crearTipoDocumento(TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void eliminarTipoDocumento(TipoDocumentoDTO tipoDocumento, String userName) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("eliminarTipoDocumento(TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
		}

		if (!(this.buscarDocumentosGedoService.existenDocumentosTipo(tipoDocumento))) {
			if (!this.existeTipoDocumentoVariables(tipoDocumento.getId())) {
				if (tipoDocumento.getEsEspecial()) {
					numeracionEspecialService.eliminarNumeracionEspecial(tipoDocumento, userName);
				}
				if (!tipoDocumento.getListaReparticiones().isEmpty()) {
					List<ReparticionHabilitadaDTO> reparticiones = new ArrayList<>();
					reparticiones.addAll(tipoDocumento.getListaReparticiones());
					this.reparticionHabilitadaRepo
							.delete(ListMapper.mapList(reparticiones, this.mapper, ReparticionHabilitada.class));
				}
				if (!tipoDocumento.getListaTemplates().isEmpty()) {
					for (TipoDocumentoTemplateDTO template : tipoDocumento.getListaTemplates()) {
						tipoDocumentoTemplateRepo.delete(this.mapper.map(template, TipoDocumentoTemplate.class));
					}
				}
				if (!tipoDocumento.getTipoDocumentoEmbebidos().isEmpty()) {
					for (TipoDocumentoEmbebidosDTO embebidos : tipoDocumento.getTipoDocumentoEmbebidos()) {
						this.tipoDocumentoEmbebidosRepo
								.delete(this.mapper.map(embebidos, TipoDocumentoEmbebidos.class));
					}
				}
				this.tipoDocumentoRepo.delete(this.mapper.map(tipoDocumento, TipoDocumento.class));
				TipoDocumentoAuditoriaDTO tipoDocAuditoria = new TipoDocumentoAuditoriaDTO(tipoDocumento, userName);
				tipoDocAuditoria.setTipoOperacion(Constantes.AUDITORIA_OP_BAJA);
				this.tipoDocumentoAuditoriaRepo.save(this.mapper.map(tipoDocAuditoria, TipoDocumentoAuditoria.class));
			} else {
				throw new ExistenDocumentosDelTipoJbpmException();
			}
		} else {
			throw new ExistenDocumentosDelTipoException();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("eliminarTipoDocumento(TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void modificarTipoDocumentoReparticiones(TipoDocumentoDTO tipoDocumento, String userName) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarTipoDocumentoReparticiones(TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
		}

		/*
		 * Se requiere recorrer la lista de reparticiones habilitadas para
		 * identificar si es necesario crear el número en la tabla de números
		 * especiales, de esta manera la creación del registro se incluiría en
		 * la transacción de modificación del tipo de documento.
		 */
		NumeracionEspecialDTO numeracionEspecial;

		for (ReparticionHabilitadaDTO reparticion : tipoDocumento.getListaReparticiones()) {
			if (reparticion.getEdicionNumeracionEspecial()) {
				if (reparticion.getNumeracionEspecial().getId() == null) {
					numeracionEspecial = new NumeracionEspecialDTO();
				} else {
					numeracionEspecial = numeracionEspecialService
							.buscarNumeracionEspecialById(reparticion.getNumeracionEspecial().getId());
				}

				numeracionEspecial.setAnio(reparticion.getNumeracionEspecial().getAnio());
				numeracionEspecial.setCodigoReparticion(reparticion.getCodigoReparticion());
				numeracionEspecial.setNumero(reparticion.getNumeracionEspecial().getNumero());
				numeracionEspecial.setNumeroInicial(reparticion.getNumeracionEspecial().getNumero());
				numeracionEspecial.setIdTipoDocumento(reparticion.getTipoDocumento().getId());
				String tipoOperacion = (numeracionEspecial.getId() == null) ? Constantes.AUDITORIA_OP_ALTA
						: Constantes.AUDITORIA_OP_MODIFICACION;
				numeracionEspecial.setCodigoEcosistema(null);
				numeracionEspecialService.guardar(numeracionEspecial);

				numeracionEspecialService.numeracionEspecialAuditoria(numeracionEspecial, userName, tipoOperacion);
			}
		}
		this.tipoDocumentoRepo.save(this.mapper.map(tipoDocumento, TipoDocumento.class));
		TipoDocumentoAuditoriaDTO tipoDAuditoria = new TipoDocumentoAuditoriaDTO(tipoDocumento, userName);
		tipoDAuditoria.setTipoOperacion(Constantes.AUDITORIA_OP_MODIFICACION);
		this.tipoDocumentoAuditoriaRepo.save(this.mapper.map(tipoDAuditoria, TipoDocumentoAuditoria.class));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarTipoDocumentoReparticiones(TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void modificarTipoDocumento(TipoDocumentoDTO tipoDocumento, String userName) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarTipoDocumento(TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
		}

		this.tipoDocumentoRepo.save(this.mapper.map(tipoDocumento, TipoDocumento.class));
		TipoDocumentoAuditoriaDTO tipoDAuditoria = new TipoDocumentoAuditoriaDTO(tipoDocumento, userName);
		tipoDAuditoria.setTipoOperacion(Constantes.AUDITORIA_OP_MODIFICACION);
		this.tipoDocumentoAuditoriaRepo.save(this.mapper.map(tipoDAuditoria, TipoDocumentoAuditoria.class));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("modificarTipoDocumento(TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public TipoDocumentoDTO buscarTipoDocumentoByAcronimo(String acronimo) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByAcronimo(String) - start"); //$NON-NLS-1$
		}

		TipoDocumentoDTO returnTipoDocumentoDTO = null;
		List<TipoDocumento> resultList = this.tipoDocumentoRepo.buscarTipoDocumentoByAcronimoOrNombre(acronimo);
		TipoDocumento tipoDocumento = null;
		if (CollectionUtils.isNotEmpty(resultList)) {
			tipoDocumento = resultList.get(0);
		}
		if (tipoDocumento != null) {
			returnTipoDocumentoDTO = mapper.map(tipoDocumento, TipoDocumentoDTO.class);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByAcronimo(String) - end"); //$NON-NLS-1$
		}
		return returnTipoDocumentoDTO;
	}

	@Override
	public TipoDocumentoDTO buscarTipoDocumentoManualByAcronimo(String acronimo) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoManualByAcronimo(String) - start"); //$NON-NLS-1$
		}

		TipoDocumentoDTO returnTipoDocumentoDTO = mapper
				.map(this.tipoDocumentoRepo.buscarTipoDocumentoManualByAcronimo(acronimo), TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoManualByAcronimo(String) - end"); //$NON-NLS-1$
		}
		return returnTipoDocumentoDTO;
	}

	@Override
	public List<TipoDocumentoReducidoDTO> buscarTipoDocumentoReducido() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoReducido() - start"); //$NON-NLS-1$
		}

		List<TipoDocumento> documentos = this.tipoDocumentoRepo.findAll();
		List<TipoDocumentoReducidoDTO> lista = ListMapper.mapList(documentos, this.mapper,
				TipoDocumentoReducidoDTO.class);

		Map<String, TipoDocumentoReducidoDTO> map = new TreeMap<>();

		for (TipoDocumentoReducidoDTO tDoc : lista) {
			if (map.containsKey(tDoc.getAcronimo())) {
				Double version = Double.parseDouble(tDoc.getVersion());
				Double version2 = Double.parseDouble(map.get(tDoc.getAcronimo()).getVersion());

				if (version.compareTo(version2) > 0) {
					map.put(tDoc.getAcronimo(), tDoc);
				}
			} else {
				map.put(tDoc.getAcronimo(), tDoc);
			}
		}
		lista.clear();

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String acro = it.next();
			lista.add(map.get(acro));
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoReducido() - end"); //$NON-NLS-1$
		}
		return lista;
	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumento() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumento() - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> lista = ListMapper.mapList(this.tipoDocumentoRepo.buscarTipoDocumento(), this.mapper,
				TipoDocumentoDTO.class);

		Map<String, TipoDocumentoDTO> map = new TreeMap<>();

		for (TipoDocumentoDTO tDoc : lista) {
			if (map.containsKey(tDoc.getAcronimo())) {
				Double version = Double.parseDouble(tDoc.getVersion());
				Double version2 = Double.parseDouble(map.get(tDoc.getAcronimo()).getVersion());

				if (version.compareTo(version2) > 0) {
					map.put(tDoc.getAcronimo(), tDoc);
				}
			} else {
				map.put(tDoc.getAcronimo(), tDoc);
			}
		}

		lista.clear();

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String acro = it.next();
			lista.add(map.get(acro));
		}

		return lista;
	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoOrdenadoPorFamiliaYTipo() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoOrdenadoPorFamiliaYTipo() - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = reduccionPorVersion(
				ListMapper.mapList(this.tipoDocumentoRepo.buscarTipoDocumentoOrdenadoPorFamiliaYTipo(), this.mapper,
						TipoDocumentoDTO.class));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoOrdenadoPorFamiliaYTipo() - end"); //$NON-NLS-1$
		}
		return returnList;

	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoByEstadoFiltradosManual(String estado, boolean esManual) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByEstadoFiltradosManual(String, boolean) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = reduccionPorVersion(
				ListMapper.mapList(this.tipoDocumentoRepo.buscarTipoDocumentoByEstadosFiltradosManual(estado, esManual),
						this.mapper, TipoDocumentoDTO.class));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByEstadoFiltradosManual(String, boolean) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoByFamilia(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFamilia(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = ListMapper.mapList(
				this.tipoDocumentoRepo.buscarTipoDocumentoByFamilia((String) parametros.get(ESTADO),
						(Boolean) parametros.get(MANUAL), (Integer) parametros.get(FAMILIA)),
				this.mapper, TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFamilia(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	
	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoSinFiltroByFamilia(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoSinFiltroByFamilia(Map<String,Object>) - start"); //$NON-NLS-1$
		}
		List<TipoDocumentoDTO> returnList = null;
		Integer familia =  (Integer) parametros.get(FAMILIA);
		if(familia != null) {
			returnList = ListMapper.mapList(
					this.tipoDocumentoRepo.buscarTipoDocumentoSinFiltroByFamilia(familia),
					this.mapper, TipoDocumentoDTO.class);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoSinFiltroByFamilia(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;

	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoByFamiliayComunicable(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFamiliayComunicable(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = ListMapper.mapList(
				this.tipoDocumentoRepo.buscarTipoDocumentoByFamiliayComunicable((String) parametros.get(ESTADO),
						(Boolean) parametros.get(MANUAL), parametros.get(FAMILIA).toString(),
						(Boolean) parametros.get(COMUNICABLE)),
				this.mapper, TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFamiliayComunicable(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoSinFiltroByFamiliayComunicable(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoSinFiltroByFamiliayComunicable(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = ListMapper.mapList(
				this.tipoDocumentoRepo.buscarTipoDocumentoSinFiltroByFamiliayComunicable(
						(Boolean) parametros.get(COMUNICABLE), parametros.get(FAMILIA).toString()),
				this.mapper, TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoSinFiltroByFamiliayComunicable(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoByFamiliaYAcronimo(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFamiliaYAcronimo(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = ListMapper
				.mapList(this.tipoDocumentoRepo.buscarTipoDocumentoByFamiliaYAcronimo((Integer) parametros.get(FAMILIA),
						parametros.get(ESTADO).toString(), (Boolean) parametros.get(MANUAL),
						parametros.get(ACRONIMO).toString(), parametros.get(NOMBRE).toString(),
						parametros.get(DESC).toString()), this.mapper, TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFamiliaYAcronimo(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoByFamiliaAcronimoYComunicable(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFamiliaAcronimoYComunicable(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = ListMapper.mapList(this.tipoDocumentoRepo
				.buscarTipoDocumentoByFamiliaAcronimoYComunicable(parametros.get(FAMILIA).toString(),
						(Boolean) parametros.get(COMUNICABLE), parametros.get(ESTADO).toString(),
						(Boolean) parametros.get(MANUAL), parametros.get(ACRONIMO).toString(),
						parametros.get(NOMBRE).toString(), parametros.get(DESC).toString()),
				this.mapper, TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFamiliaAcronimoYComunicable(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<FamiliaTipoDocumentoDTO> buscarFamilias() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFamilias() - start"); //$NON-NLS-1$
		}

		List<FamiliaTipoDocumentoDTO> returnList = ListMapper.mapList(this.familiatipoDocumentoRepo.findAll(),
				this.mapper, FamiliaTipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFamilias() - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<FamiliaTipoDocumentoDTO> buscarFamiliasByComunicable(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFamiliasByComunicable(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<FamiliaTipoDocumentoDTO> returnList = ListMapper.mapList(
				this.familiatipoDocumentoRepo.buscarFamiliasByComunicable((Boolean) parametros.get(COMUNICABLE)),
				this.mapper, FamiliaTipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFamiliasByComunicable(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<FamiliaTipoDocumentoDTO> buscarFamiliaPorTipoDocumento(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFamiliaPorTipoDocumento(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<FamiliaTipoDocumento> families = this.familiatipoDocumentoRepo.buscarFamiliaPorTipoDocumento(
				parametros.get(ESTADO).toString(), (Boolean) parametros.get(MANUAL),
				parametros.get(ACRONIMO).toString(), parametros.get(NOMBRE).toString(),
				parametros.get(DESC).toString());

		List<FamiliaTipoDocumentoDTO> returnList = new ArrayList<>();

		for (FamiliaTipoDocumento f : families) {
			returnList.add(this.mapper.map(f, FamiliaTipoDocumentoDTO.class));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFamiliaPorTipoDocumento(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<FamiliaTipoDocumentoDTO> buscarFamiliaPorTipoDocumentoYComunicable(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFamiliaPorTipoDocumentoYComunicable(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<FamiliaTipoDocumentoDTO> returnList = ListMapper.mapList(
				this.familiatipoDocumentoRepo.buscarFamiliaPorTipoDocumentoYComunicable(
						parametros.get(ESTADO).toString(), (Boolean) parametros.get(MANUAL),
						parametros.get(ACRONIMO).toString(), (Boolean) parametros.get(COMUNICABLE),
						parametros.get(NOMBRE).toString(), parametros.get(DESC).toString(),
						parametros.get("nombrefamilia").toString()),
				this.mapper, FamiliaTipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFamiliaPorTipoDocumentoYComunicable(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}


	@Override
	public List<FamiliaTipoDocumentoDTO> buscarTodasLasFamiliasByComunicable(Map<String, Object> parametros) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTodasLasFamiliasByComunicable(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		List<FamiliaTipoDocumentoDTO> returnList = ListMapper.mapList(
				this.familiatipoDocumentoRepo.buscarTodasLasFamiliasByComunicable(parametros.get(ESTADO).toString(),
						(Boolean) parametros.get(MANUAL), (Boolean) parametros.get(COMUNICABLE)),
				this.mapper, FamiliaTipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTodasLasFamiliasByComunicable(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	
	@Override
	public List<FamiliaTipoDocumentoDTO> buscarTodasLasFamilias() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTodasLasFamilias() - start"); //$NON-NLS-1$
		}

		List<FamiliaTipoDocumento> families = this.familiatipoDocumentoRepo.buscarTodasLasFamilias("ALTA", true);
		List<FamiliaTipoDocumentoDTO> returnList = ListMapper.mapList(families, this.mapper,
				FamiliaTipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTodasLasFamilias() - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<TipoDocumentoDTO> buscarTipoDocumentoByFiltradoComunicable(String estado, boolean esManual) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFiltradoComunicable(String, boolean) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = reduccionPorVersion(ListMapper.mapList(
				this.tipoDocumentoRepo.buscarTipoDocumentoByFiltradoComunicable(estado, (Boolean) esManual),
				this.mapper, TipoDocumentoDTO.class));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoByFiltradoComunicable(String, boolean) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/**
	 * Crea un objeto ReparticionHabilitada identificada por el código "TODAS",
	 * para indicar que por defecto están todas las reparticiones habilitadas
	 * con todos los permisos para el tipo de documento dado.
	 * 
	 * @param tipoDocumento
	 *            : Tipo de documento.
	 * @return Objeto ReparticionHabilitada.
	 */
	private ReparticionHabilitadaDTO crearReparticionTodas(TipoDocumentoDTO tipoDocumento) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("crearReparticionTodas(TipoDocumentoDTO) - start"); //$NON-NLS-1$
		}

		ReparticionHabilitadaDTO reparticionHabilitada = new ReparticionHabilitadaDTO();
		reparticionHabilitada.setTipoDocumento(tipoDocumento);
		reparticionHabilitada.setCodigoReparticion(Constantes.TODAS_REPARTICIONES_HABILITADAS);
		reparticionHabilitada.setEstado(true);
		// Sí el documento tiene numeración especial, no se habilitan todas las
		// reparticiones con el fin de
		// hacer obligatoria la configuración de al menos una repartición.
		if (tipoDocumento.getEsEspecial()) {
			reparticionHabilitada.setPermisoIniciar(true);
			reparticionHabilitada.setPermisoFirmar(false);
		} else {
			reparticionHabilitada.setPermisoIniciar(true);
			reparticionHabilitada.setPermisoFirmar(true);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("crearReparticionTodas(TipoDocumentoDTO) - end"); //$NON-NLS-1$
		}
		return reparticionHabilitada;
	}

	@Override
	public boolean existeTipoDocumentoVariables(int idTipoDocumento) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("existeTipoDocumentoVariables(int) - start"); //$NON-NLS-1$
		}

		boolean returnboolean = this.documentoTemporalDao.existeJBPMVariable(String.valueOf(idTipoDocumento));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("existeTipoDocumentoVariables(int) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	@Override
	public Set<ReparticionHabilitadaDTO> cargarReparticionesHabilitadas(TipoDocumentoDTO tipoDocumento) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("cargarReparticionesHabilitadas(TipoDocumentoDTO) - start"); //$NON-NLS-1$
		}

		Set<ReparticionHabilitadaDTO> reparticiones = new HashSet<>();
		reparticiones.addAll(ListMapper.mapList(
				reparticionHabilitadaRepo.findByTipoDocumento(this.mapper.map(tipoDocumento, TipoDocumento.class)),
				this.mapper, ReparticionHabilitadaDTO.class));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("cargarReparticionesHabilitadas(TipoDocumentoDTO) - end"); //$NON-NLS-1$
		}
		return reparticiones;

	}

	@Override
	public List<FormatoTamanoArchivoDTO> buscarFormatosArchivos() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFormatosArchivos() - start"); //$NON-NLS-1$
		}

		List<FormatoTamanoArchivoDTO> returnList = ListMapper.mapList(this.formatoTamanoArchivoRepo.findAll(),
				this.mapper, FormatoTamanoArchivoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarFormatosArchivos() - end"); //$NON-NLS-1$
		}
		return returnList;

	}

	@Override
	public void borrarTipoDocEmbebidosByTipoDoc(List<TipoDocumentoEmbebidosDTO> listaExtensionesPermitidas) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarTipoDocEmbebidosByTipoDoc(List<TipoDocumentoEmbebidosDTO>) - start"); //$NON-NLS-1$
		}

		this.tipoDocumentoEmbebidosRepo
				.delete(ListMapper.mapList(listaExtensionesPermitidas, this.mapper, TipoDocumentoEmbebidos.class));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarTipoDocEmbebidosByTipoDoc(List<TipoDocumentoEmbebidosDTO>) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void borrarTipoDocEmbebido(TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarTipoDocEmbebido(TipoDocumentoEmbebidosDTO) - start"); //$NON-NLS-1$
		}

		tipoDocumentoEmbebidosRepo.delete(this.mapper.map(tipoDocumentoEmbebidos, TipoDocumentoEmbebidos.class));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("borrarTipoDocEmbebido(TipoDocumentoEmbebidosDTO) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public TipoDocumentoDTO buscarTipoDocumentoPorAcronimoYVersion(String acronimo, String version) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoPorAcronimoYVersion(String, String) - start"); //$NON-NLS-1$
		}

		TipoDocumentoDTO returnTipoDocumentoDTO = this.mapper
				.map(this.tipoDocumentoRepo.findByAcronimoAndVersion(acronimo, version), TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoPorAcronimoYVersion(String, String) - end"); //$NON-NLS-1$
		}
		return returnTipoDocumentoDTO;
	}

	@Override
	public TipoDocumentoDTO buscarTipoDocumentoPorAcronimoConFamilia(String acronimo) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoPorAcronimoConFamilia(String) - start"); //$NON-NLS-1$
		}

		TipoDocumentoDTO returnTipoDocumentoDTO = this.mapper.map(
				this.tipoDocumentoRepo.buscarTipoDocumentoByAcronimoConFamilia(acronimo).get(0),
				TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoPorAcronimoConFamilia(String) - end"); //$NON-NLS-1$
		}
		return returnTipoDocumentoDTO;
	}

	private List<TipoDocumentoDTO> reduccionPorVersion(List<TipoDocumentoDTO> lista) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("reduccionPorVersion(List<TipoDocumentoDTO>) - start"); //$NON-NLS-1$
		}

		String acronimo = "";
		List<TipoDocumentoDTO> listaFinal = new ArrayList<>();
		for (TipoDocumentoDTO list : lista) {
			if (!acronimo.equals(list.getAcronimo())) {
				listaFinal.add(list);
			}
			acronimo = list.getAcronimo();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("reduccionPorVersion(List<TipoDocumentoDTO>) - end"); //$NON-NLS-1$
		}
		return listaFinal;
	}

	@Override
	public TipoDocumentoDTO buscarTipoDocumentoPorId(int id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoPorId(int) - start"); //$NON-NLS-1$
		}
		TipoDocumento tipoRepo = this.tipoDocumentoRepo.findOne(id);
		TipoDocumentoDTO returnTipoDocumentoDTO = new TipoDocumentoDTO();
		if (null != tipoRepo) {
			returnTipoDocumentoDTO = this.mapper.map(this.tipoDocumentoRepo.findOne(id), TipoDocumentoDTO.class);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarTipoDocumentoPorId(int) - end"); //$NON-NLS-1$
		}
		return returnTipoDocumentoDTO;
	}

	@Override
	public List<TipoDocumentoDTO> buscarDocumentosPorVersiones(String acronimo) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarDocumentosPorVersiones(String) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = ListMapper.mapList(
				this.tipoDocumentoRepo.buscarDocumentoPorVersiones(acronimo), this.mapper, TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarDocumentosPorVersiones(String) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	@Override
	public List<TipoDocumentoDTO> getDocumentTypeByProduction(ProductionEnum productionType) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getDocumentTypeByProduction(ProductionEnum) - start");
		}

		List<TipoDocumentoDTO> returnList = ListMapper.mapList(
				this.tipoDocumentoRepo.getDocumentTypeByProduction(productionType.getValue()), this.mapper,
				TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getDocumentTypeByProduction(ProductionEnum) - end");
		}
		return returnList;
	}

	
	@Override
	public List<TipoDocumentoDTO> getTipoDocumentoByFamiliaNombre(String famNombre) {
		List<TipoDocumentoDTO> retorno = new ArrayList<>();
		List<TipoDocumento> resultado = tipoDocumentoRepo.getTipoDocumentoByFamiliaNombre(famNombre);
		if (resultado != null) {
			retorno = ListMapper.mapList(resultado, mapper, TipoDocumentoDTO.class);
		}

		return retorno;
	}

	
	@Override
	public List<TipoDocumentoDTO> getTiposDocumentoHabilitados() {
		List<TipoDocumentoDTO> retorno = new ArrayList<>();
		List<TipoDocumento> resultado = tipoDocumentoRepo.getTiposDocumentoHabilitados();
		if (resultado != null) {
			retorno = ListMapper.mapList(resultado, mapper, TipoDocumentoDTO.class);
		}

		return retorno;
	}

	
	@Override
	public List<TipoDocumentoDTO> getTipoDocumentoEspecial() {
		final String estado = "ALTA";
		List<TipoDocumentoDTO> retorno = new ArrayList<>();
		List<TipoDocumento> resultado = tipoDocumentoRepo.getTipoDocumentoEspecial(true, estado);
		if (resultado != null) {
			retorno = ListMapper.mapList(resultado, mapper, TipoDocumentoDTO.class);
		}

		return retorno;
	}

	
	@Override
	public List<TipoDocumentoDTO> getTiposDocumento() {
		final Boolean esAutomatica = true;
		final Boolean tieneTemplate = true;
		final String estado = "ALTA";
		final Boolean esEspecial = false;
		final Boolean tieneToken = false;
		final Boolean esFirmaconjunta = false;

		List<TipoDocumentoDTO> retorno = new ArrayList<>();
		List<TipoDocumento> resultado = tipoDocumentoRepo.getTiposDocumento(esAutomatica, tieneTemplate, estado,
				esEspecial, tieneToken, esFirmaconjunta);
		if (resultado != null) {
			retorno = ListMapper.mapList(resultado, mapper, TipoDocumentoDTO.class);
		}

		return retorno;
	}

	@Override
	public boolean existsAcronymDocumentType(String acronym) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("existsAcronymDocumentType(acronym) - start");
		}

		boolean exists = false;

		if (acronym != null) {
			List<TipoDocumento> listDocumentType = tipoDocumentoRepo.findByAcronimo(acronym);

			if (listDocumentType != null && !listDocumentType.isEmpty()) {
				exists = true;
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getDocumentTypeByProduction(acronym) - end");
		}

		return exists;
	}

	
	@Override
	public List<TipoDocumentoDTO> getTiposDocumentoResultado() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTiposDocumentoResultado() - start");
		}

		List<TipoDocumentoDTO> tipoDocListDTO = new ArrayList<>();
		List<TipoDocumento> tipoDocListEntity = tipoDocumentoRepo.getTiposDocumentoByResultado(true);

		if (tipoDocListEntity != null && !tipoDocListEntity.isEmpty()) {
			tipoDocListDTO = ListMapper.mapList(tipoDocListEntity, mapper, TipoDocumentoDTO.class);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTiposDocumentoResultado() - end");
		}

		return tipoDocListDTO;
	}
	
	
	@Override
	public List<TipoDocumentoDTO> buscarDocumentosPorIdSade(Integer id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarDocumentosPorVersiones(String) - start"); //$NON-NLS-1$
		}

		List<TipoDocumentoDTO> returnList = ListMapper.mapList(
				this.tipoDocumentoRepo.buscarDocumentosPorIdSade(id), this.mapper, TipoDocumentoDTO.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buscarDocumentosPorVersiones(String) - end"); //$NON-NLS-1$
		}
		return returnList;
	}
	

}