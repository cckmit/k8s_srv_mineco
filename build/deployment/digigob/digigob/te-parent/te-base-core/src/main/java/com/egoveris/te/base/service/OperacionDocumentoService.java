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

import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.OperacionDocumento;
import com.egoveris.te.base.model.OperacionDocumentoDTO;
import com.egoveris.te.base.model.OperacionDocumentoPK;
import com.egoveris.te.base.repository.tipo.OperacionDocumentoRepository;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IOperacionDocumentoService;
import com.egoveris.te.base.util.ConstantesServicios;

@Service(ConstantesServicios.OPERACION_DOC_SERVICE)
@Transactional
public class OperacionDocumentoService implements IOperacionDocumentoService {
	
	private static final Logger logger = LoggerFactory.getLogger(OperacionDocumentoService.class);
	
	@Autowired
	private OperacionDocumentoRepository operacionDocumentoRepository;
	
	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	@Autowired
  private IExternalTipoDocumentoService externalTipoDocumentoService;
	
	@Autowired
	@Qualifier("teCoreMapper")
	private Mapper mapper;
	
	@Override
	public void saveDocumentoOperacion(OperacionDocumentoDTO operacionDocumentoDTO) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("saveDocumentoOperacion(operacionDocumentoDTO={}) - start", operacionDocumentoDTO);
		}
		
		if (operacionDocumentoDTO != null) {
			OperacionDocumento operacionDocumento = mapper.map(operacionDocumentoDTO, OperacionDocumento.class);
			
			OperacionDocumentoPK pk = new OperacionDocumentoPK();
			pk.setIdOperacion(operacionDocumentoDTO.getOperacion().getId());
			pk.setIdTipoDocumento(operacionDocumento.getTipoDocumentoGedo().getId().longValue());
			operacionDocumento.setPk(pk);
			
			operacionDocumentoRepository.save(operacionDocumento);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("saveDocumentoOperacion() - end");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperacionDocumentoDTO> getDocumentosOperacion(Long idOperacion) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentosOperacion(idOperacion={}) - start", idOperacion);
		}
		
		List<OperacionDocumentoDTO> documentosOperacionDto = new ArrayList<>();
		List<OperacionDocumento> documentosOperacion = operacionDocumentoRepository.getDocumentosOperacion(idOperacion);
		
		if (documentosOperacion != null) {
			documentosOperacionDto.addAll(ListMapper.mapList(documentosOperacion, mapper, OperacionDocumentoDTO.class));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentosOperacion() - end - return value={}", documentosOperacionDto);
		}
		
		return documentosOperacionDto;
	}

  @Override
  public void deleteDocumentoOperacion(OperacionDocumentoDTO operacionDocumentoDTO) {
    if (logger.isDebugEnabled()) {
      logger.debug("deleteDocumentoOperacion(operacionDocumentoDTO={}) - start", operacionDocumentoDTO);
    }
    
    if (operacionDocumentoDTO != null) {
      OperacionDocumento operacionDocEnt = mapper.map(operacionDocumentoDTO, OperacionDocumento.class);
      operacionDocEnt.setPk(new OperacionDocumentoPK());
      operacionDocEnt.getPk().setIdOperacion(operacionDocEnt.getOperacion().getId());
      operacionDocEnt.getPk().setIdTipoDocumento(operacionDocEnt.getTipoDocumentoGedo().getId());
      
      operacionDocumentoRepository.delete(operacionDocEnt);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("deleteDocumentoOperacion() - end");
    }
  }

  @Override
  public void copiarDocsResultadoFromEE(Long idEE, Long idOperacion) {
    if (logger.isDebugEnabled()) {
      logger.debug("copiarDocsResultadoFromEE(idEE={}, idOperacion={}) - start", idEE, idOperacion);
    }
    
    ExpedienteElectronicoDTO expedienteElectronicoDTO = expedienteElectronicoService.buscarExpedienteElectronico(idEE);
    List<ResponseTipoDocumento> responseTipoDocumentoList = externalTipoDocumentoService.getTiposDocumentoResultado();
    List<DocumentoDTO> documentosVincular = new ArrayList<>();
    
    if (expedienteElectronicoDTO != null && expedienteElectronicoDTO.getDocumentos() != null) {
      for (DocumentoDTO documentoDTO : expedienteElectronicoDTO.getDocumentos()) {
        if (esTipoDocumentoResultado(documentoDTO.getTipoDocAcronimo(), responseTipoDocumentoList)) {
          documentosVincular.add(documentoDTO);
        }
      }
    }
    
    if (!documentosVincular.isEmpty()) {
      // TODO: Do document vinculation
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("copiarDocsResultadoFromEE() - end");
    }
  }
	
  /**
   * A partir del acronimo dado, busca en la lista 
   * responseTipoDocumentoList y verifica si ese tipo de documento
   * posee resultado = true
   * 
   * @param acronimo
   * @param responseTipoDocumentoList
   * @return resultado
   */
  private boolean esTipoDocumentoResultado(String acronimo, List<ResponseTipoDocumento> responseTipoDocumentoList) {
    boolean esResultado = false;
    ResponseTipoDocumento tipoDocumento = null;
    
    if (responseTipoDocumentoList != null && !responseTipoDocumentoList.isEmpty()) {
      for (ResponseTipoDocumento responseTipoDocumento : responseTipoDocumentoList) {
        if (responseTipoDocumento.getAcronimo() != null && responseTipoDocumento.getAcronimo().equalsIgnoreCase(acronimo)) {
          tipoDocumento = responseTipoDocumento;
          break;
        }
      }
    }
    
    if (tipoDocumento != null && tipoDocumento.getResultado()) {
      esResultado = true;
    }
    
    return esResultado;
  }
  
}
