package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.TipoDocumentoGedoDTO;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionDocDTO;
import com.egoveris.te.base.model.tipo.TipoOperacionDocumento;
import com.egoveris.te.base.model.tipo.TipoOperacionDocumentoPK;
import com.egoveris.te.base.repository.tipo.TipoOperacionDocRepository;
import com.egoveris.te.base.service.iface.ITipoOperacionDocService;
import com.egoveris.te.base.util.ConstantesServicios;

@Service(ConstantesServicios.TIPO_OPERACION_DOC_SERVICE)
@Transactional
public class TipoOperacionDocService implements ITipoOperacionDocService {
	
	private static final Logger logger = LoggerFactory.getLogger(TipoOperacionDocService.class);
	
	@Autowired
	private TipoOperacionDocRepository tipoOperacionDocRepository;
	
	@Autowired
	private IExternalTipoDocumentoService tipoDocumentoService;
	
	@Autowired
	@Qualifier("teCoreMapper")
	private Mapper mapper;
	
	@Override
	public List<TipoOperacionDocDTO> getPosiblesTiposDocumentos(TipoOperacionDTO tipoOperacion) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("getPosiblesTiposDocumentos() - start");
		}
		
		List<TipoOperacionDocDTO> tipoOperacionDocsDto = new ArrayList<>();
		List<ResponseTipoDocumento> responseTiposDocumentos = null;
		
		try {
			responseTiposDocumentos = tipoDocumentoService.getDocumentTypeByProduction(ProductionEnum.IMPORT);
		} catch (Exception e) { // Si bien el metodo tiene una clase de exception definida, no la arroja cuando por ej esta caido el servicio
			throw new ServiceException("Se ha producido el siguiente error al cargar los tipos de documento (por produccion): ", e);
		}
		
		if (responseTiposDocumentos != null) {
			for (ResponseTipoDocumento responseTipoDocumento : responseTiposDocumentos) {
				TipoOperacionDocDTO tipoOperacionDocumento = new TipoOperacionDocDTO();
				tipoOperacionDocumento.setTipoDocumentoGedo(responseToDtoMapper(responseTipoDocumento));
				tipoOperacionDocumento.setTipoOperacion(tipoOperacion);
				
				tipoOperacionDocsDto.add(tipoOperacionDocumento);
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("getPosiblesTiposDocumentos(TipoOperacionDocumentoDTO) - end - return value={}", tipoOperacionDocsDto);
		}
		
		return tipoOperacionDocsDto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoOperacionDocDTO> getTiposDocumentosByTipoOperacion(Long idTipoOperacion)
			throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("getTiposDocumentosByTipoOperacion(idTipoOperacion={}) - start", idTipoOperacion);
		}
		
		List<TipoOperacionDocDTO> tipoOperacionDocsDto = new ArrayList<>();
		List<TipoOperacionDocumento> tiposDocumentos = tipoOperacionDocRepository.getTiposDocumentosByTipoOperacion(idTipoOperacion);
		
		if (tiposDocumentos != null) {
			tipoOperacionDocsDto.addAll(ListMapper.mapList(tiposDocumentos, mapper, TipoOperacionDocDTO.class));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("getTiposDocumentosByTipoOperacion(TipoOperacionDocumentoDTO) - end - return value={}", tipoOperacionDocsDto);
		}
		
		return tipoOperacionDocsDto;
	}
	
	@Override
	public void saveDocumentosTipoOperacion(Long idTipoOperacion, List<TipoOperacionDocDTO> tipoDocsOper) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("saveDocumentosTipoOperacion(tipoDocsOper={}) - start", tipoDocsOper);
		}
		
		if (tipoDocsOper != null) {
			List<TipoOperacionDocumento> listaEntityTipoDocs = new ArrayList<>();
			
			for (TipoOperacionDocDTO tipoOperacionDocDTO : tipoDocsOper) {
				TipoOperacionDocumentoPK pk = new TipoOperacionDocumentoPK();
				pk.setIdTipoDocumento(tipoOperacionDocDTO.getTipoDocumentoGedo().getId());
				pk.setIdTipoOperacion(idTipoOperacion);
				
				TipoOperacionDocumento entityTipoOperDoc = new TipoOperacionDocumento();
				entityTipoOperDoc.setPk(pk);
				entityTipoOperDoc.setObligatorio(tipoOperacionDocDTO.isObligatorio());
				entityTipoOperDoc.setOpcional(tipoOperacionDocDTO.isOpcional());
				
				listaEntityTipoDocs.add(entityTipoOperDoc);
			}
			
			tipoOperacionDocRepository.save(listaEntityTipoDocs);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("saveDocumentosTipoOperacion() - end");
		}
	}

	@Override
	public void deleteTiposDocsTipoOper(Long idTipoOperacion) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteTiposDocsTipoOper(idTipoOperacion={}) - start", idTipoOperacion);
		}
		
		List<TipoOperacionDocumento> tipoDocsTipoOp = tipoOperacionDocRepository.getTiposDocumentosByTipoOperacion(idTipoOperacion);
		
		if (tipoDocsTipoOp != null && !tipoDocsTipoOp.isEmpty()) {
			tipoOperacionDocRepository.delete(tipoDocsTipoOp);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("deleteTiposDocsTipoOper() - end");
		}
	}
	
	/**
	 * Metodo que mapea de ResponseTipoDocumento a TipoDocumentoGedo
	 * (para ser utilizado en la relacion de tipo operacion con tipo de documentos)
	 * 
	 * Se hace manualmente en lugar de utilizar mapper, porque las clases
	 * no son exactamente iguales
	 * 
	 * @param responseTipoDocumento
	 * @return
	 */
	private TipoDocumentoGedoDTO responseToDtoMapper(ResponseTipoDocumento responseTipoDocumento) {
		TipoDocumentoGedoDTO tipoDocumentoGedoDTO = new TipoDocumentoGedoDTO();
		tipoDocumentoGedoDTO.setAcronimo(responseTipoDocumento.getAcronimo());
		tipoDocumentoGedoDTO.setCodigoTipoDocumentoSade(responseTipoDocumento.getCodigoTipoDocumentoSade());
		tipoDocumentoGedoDTO.setDescripcion(responseTipoDocumento.getDescripcion());
		tipoDocumentoGedoDTO.setEsAutomatica(responseTipoDocumento.getEsAutomatica());
		tipoDocumentoGedoDTO.setEsConfidencial(responseTipoDocumento.getEsConfidencial());
		tipoDocumentoGedoDTO.setEsEspecial(responseTipoDocumento.getEsEspecial());
		tipoDocumentoGedoDTO.setEsFirmaConjunta(responseTipoDocumento.getEsFirmaConjunta());
		tipoDocumentoGedoDTO.setEsFirmaExterna(responseTipoDocumento.getEsFirmaExterna());
		tipoDocumentoGedoDTO.setEsManual(responseTipoDocumento.getEsManual());
		tipoDocumentoGedoDTO.setEsNotificable(responseTipoDocumento.getEsNotificable());
		tipoDocumentoGedoDTO.setEstado(responseTipoDocumento.getEstado());
		tipoDocumentoGedoDTO.setId(responseTipoDocumento.getId().longValue());
		tipoDocumentoGedoDTO.setNombre(responseTipoDocumento.getNombre());
		tipoDocumentoGedoDTO.setTieneTemplate(responseTipoDocumento.getTieneTemplate());
		tipoDocumentoGedoDTO.setTieneToken(responseTipoDocumento.getTieneToken());
		tipoDocumentoGedoDTO.setTipoProduccion(2); // 2 => Importado
		
		return tipoDocumentoGedoDTO;
	}
	
}
