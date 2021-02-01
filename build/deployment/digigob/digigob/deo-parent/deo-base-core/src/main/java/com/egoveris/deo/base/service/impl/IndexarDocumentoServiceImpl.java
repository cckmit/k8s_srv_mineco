package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.DocumentoSolicitud;
import com.egoveris.deo.base.repository.DocumentoSolicitudRepository;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.IndexarDocumentoService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.solr.DynamicSearchRepository;
import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.DocumentoSolr;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class IndexarDocumentoServiceImpl implements IndexarDocumentoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexarDocumentoServiceImpl.class);

	@Autowired
	public ExternalFormularioService formularioService;
	@Autowired
	public ExternalTransaccionService transaccionService;
	@Autowired
	private BuscarDocumentosGedoService buscarDocumentoGedoService;
	@Autowired
	private DocumentoSolicitudRepository documentoSolicitudRepo;
	@Autowired
	private ProcesamientoTemplate procesamientoTemplate;
	@Autowired
	public DynamicSearchRepository<DocumentoSolr> dynamicSearchRepository;

	public void indexarEnSolr(String nroDocSade) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("indexarEnSolr(String) - start"); //$NON-NLS-1$
    }

		try {
			SolrInputDocument docSolr = armarDocumentoSolr(nroDocSade);
			dynamicSearchRepository.save(docSolr);
		} catch (Exception e) {
			LOGGER.error("Error al indexar el documento " + nroDocSade, e);
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("indexarEnSolr(String) - end"); //$NON-NLS-1$
    }
 	}

	private SolrInputDocument armarDocumentoSolr(String nroDocSade) throws Exception, DynFormException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("armarDocumentoSolr(String) - start"); //$NON-NLS-1$
    }

		SolrInputDocument docSolr = new SolrInputDocument();

		DocumentoDTO doc = buscarDocumentoGedoService.buscarDocumentoPorNumero(nroDocSade);
		Map<String, Object> mapaFc = mapValuesFC(nroDocSade, doc);

		docSolr.setField("id", doc.getId().longValue());
		docSolr.setField("nro_sade", doc.getNumero());
		docSolr.setField("nro_sade_papel", doc.getNumeroSadePapel());
		docSolr.setField("nro_especial_sade", doc.getNumeroEspecial());
		docSolr.setField("fecha_creacion", doc.getFechaCreacion());
		docSolr.setField("usuario_generador", doc.getUsuarioGenerador());
		docSolr.setField("reparticion_generador", doc.getReparticion());

		docSolr.setField("referencia", doc.getMotivo());
		docSolr.setField("tipo_doc_acr", doc.getTipo() != null ? doc.getTipo().getAcronimo() : null);
		docSolr.setField("tipo_doc_nombre", doc.getTipo() != null ? doc.getTipo().getNombre() : null);

		docSolr.setField("anio_doc", Integer.valueOf(doc.getAnio()));

		docSolr.setField("actuacion_acr", doc.getTipo() != null ? doc.getTipo().getCodigoTipoDocumentoSade() : null);

		if (doc.getFirmantes() != null && !doc.getFirmantes().isEmpty()) {
			docSolr.setField("usuario_firmante", doc.getFirmantes().toArray());
		}

		if (doc.getListaMetadatos() != null && !doc.getListaMetadatos().isEmpty()) {
			for (DocumentoMetadataDTO docMData : doc.getListaMetadatos()) {
				docSolr.setField(docMData.getNombre() + ConsultaSolrRequest.DYN_FIELD_SUF_LEG, docMData.getValor());
			}
		}

		for (Map.Entry<String, Object> entry : mapaFc.entrySet()) {
			docSolr.setField(entry.getKey(), entry.getValue());
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("armarDocumentoSolr(String) - end"); //$NON-NLS-1$
    }
  		return docSolr;
	}

	private Map<String, Object> mapValuesFC(String nroDocSade, DocumentoDTO doc) throws Exception, DynFormException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("mapValuesFC(String, DocumentoDTO) - start"); //$NON-NLS-1$
    }

		Integer idTrans = obtenerIdTransaccion(nroDocSade);
		Map<String, Object> result = new HashMap<>();
		if (idTrans != null) {
			String nameForm = obtenerIdFormulario(doc.getTipo());
			if (nameForm != null) {
				FormularioDTO formDTO = formularioService.buscarFormularioPorNombre(nameForm);
				TransaccionDTO transDTO = transaccionService.buscarTransaccionPorUUID(idTrans);
				for (FormularioComponenteDTO formCompDTO : formDTO.getFormularioComponentes()) {
					if (formCompDTO.getRelevanciaBusqueda() > 0) {
						for (ValorFormCompDTO valorTransDTO : transDTO.getValorFormComps()) {
							if (valorTransDTO.getInputName().equals(formCompDTO.getNombre())) {
								result.put(valorTransDTO.getInputName() + sufix(valorTransDTO.getValor()),
										valorTransDTO.getValor());
								break;
							}
						}
					}
				}
			}
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("mapValuesFC(String, DocumentoDTO) - end"); //$NON-NLS-1$
    }
  		return result;
	}

	private String sufix(Object value) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("sufix(Object) - start"); //$NON-NLS-1$
    }

		if (value instanceof String) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("sufix(Object) - end"); //$NON-NLS-1$
      }
   			return ConsultaSolrRequest.DYN_FIELD_SUF_STR;
		} else if (value instanceof Date) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("sufix(Object) - end"); //$NON-NLS-1$
      }
   			return ConsultaSolrRequest.DYN_FIELD_SUF_DATE;
		} else if (value instanceof Integer || value instanceof Long) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("sufix(Object) - end"); //$NON-NLS-1$
      }
   			return ConsultaSolrRequest.DYN_FIELD_SUF_INT;
		} else if (value instanceof Boolean) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("sufix(Object) - end"); //$NON-NLS-1$
      }
   			return ConsultaSolrRequest.DYN_FIELD_SUF_BOOLEAN;
		} else if (value instanceof Double) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("sufix(Object) - end"); //$NON-NLS-1$
      }
   			return ConsultaSolrRequest.DYN_FIELD_SUF_DOUBLE;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("sufix(Object) - end"); //$NON-NLS-1$
    }
  		return null;
	}

	private Integer obtenerIdTransaccion(String numeroSade) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerIdTransaccion(String) - start"); //$NON-NLS-1$
    }
    
    DocumentoSolicitud docuEntity = documentoSolicitudRepo.findByNumeroSade(numeroSade);
    
    Integer returnInteger = null;
    if (docuEntity != null && docuEntity.getIdTransaccion() != null) {
    	  returnInteger = docuEntity.getIdTransaccion().intValue();
    }
		
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerIdTransaccion(String) - end"); //$NON-NLS-1$
    }
  		return returnInteger;
	}

	private String obtenerIdFormulario(TipoDocumentoDTO tipo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerIdFormulario(TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

		TipoDocumentoTemplateDTO ultimoTemplatePorTipoDocumento = procesamientoTemplate
				.obtenerUltimoTemplatePorTipoDocumento(tipo);
		if (ultimoTemplatePorTipoDocumento != null) {
      String returnString = ultimoTemplatePorTipoDocumento.getIdFormulario();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("obtenerIdFormulario(TipoDocumentoDTO) - end"); //$NON-NLS-1$
      }
   			return returnString;
		} else {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("obtenerIdFormulario(TipoDocumentoDTO) - end"); //$NON-NLS-1$
      }
   			return null;
		}
	}

}
