package com.egoveris.deo.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.service.TipoProduccionService;
import com.egoveris.deo.model.model.MetadataDTO;
import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.ResponseMetadata;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.ws.exception.ErrorConsultaTipoDocumentoException;
import com.egoveris.deo.ws.exception.TipoDocumentoNoExisteException;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;

@Service
public class ExternalTipoDocumentoServiceImpl implements IExternalTipoDocumentoService {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(ExternalTipoDocumentoServiceImpl.class);

  @Autowired
  private ProcesamientoTemplate procesamientoTemplate;
  @Autowired
  private TipoProduccionService tipoProduccionService;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;

  public List<ResponseTipoDocumento> consultarTiposDocumento()
      throws ErrorConsultaTipoDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarTiposDocumento() - start"); //$NON-NLS-1$
    }

    try {
      List<TipoDocumentoDTO> listaTiposDocumento = tipoDocumentoService.buscarTipoDocumento();
      List<ResponseTipoDocumento> listaRetorno = new ArrayList<ResponseTipoDocumento>(
          listaTiposDocumento.size());
      for (TipoDocumentoDTO tipo : listaTiposDocumento) {
        ResponseTipoDocumento temp = this.tipoToResponse(tipo);
        listaRetorno.add(temp);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("consultarTiposDocumento() - end"); //$NON-NLS-1$
      }
      return listaRetorno;
    } catch (RuntimeException e) {
      logger.error("consultarTiposDocumento()", e); //$NON-NLS-1$

      throw new ErrorConsultaTipoDocumentoException("Error al obtener tipos de documento", e);
    }

  }

  public ResponseTipoDocumento consultarTipoDocumentoPorAcronimo(String acronimo)
      throws ErrorConsultaTipoDocumentoException, TipoDocumentoNoExisteException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultarTipoDocumentoPorAcronimo(String) - start"); //$NON-NLS-1$
    }

    try {
      TipoDocumentoDTO tipoDocumento = tipoDocumentoService
          .buscarTipoDocumentoPorAcronimoConFamilia(acronimo);
      if (tipoDocumento != null) {

        logger.info("retornando tipo de documento " + acronimo);
        return this.tipoToResponse(tipoDocumento);
      } else {
        throw new TipoDocumentoNoExisteException(acronimo);
      }
    } catch (RuntimeException e) {
      logger.error("consultarTipoDocumentoPorAcronimo(String)", e); //$NON-NLS-1$

      throw new ErrorConsultaTipoDocumentoException(
          "Error al obtener tipo de documento con acrónimo " + acronimo, e);
    }
  }

  private ResponseTipoDocumento tipoToResponse(TipoDocumentoDTO tipo) {
    if (logger.isDebugEnabled()) {
      logger.debug("tipoToResponse(TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    ResponseTipoDocumento temp = new ResponseTipoDocumento();

    temp.setAcronimo(tipo.getAcronimo());
    temp.setCodigoTipoDocumentoSade(tipo.getCodigoTipoDocumentoSade());
    temp.setIdTipoDocumentoSade(tipo.getIdTipoDocumentoSade());
    temp.setId(tipo.getId());
    temp.setDescripcion(tipo.getDescripcion());
    temp.setEsAutomatica(tipo.getEsAutomatica());
    temp.setEsConfidencial(tipo.getEsConfidencial());
    temp.setEsEspecial(tipo.getEsEspecial());
    temp.setEsFirmaConjunta(tipo.getEsFirmaConjunta());
    temp.setEsFirmaExterna(tipo.getEsFirmaExterna());
    temp.setEsManual(tipo.getEsManual());
    temp.setEsEmbebido(tipo.getPermiteEmbebidos());
    temp.setFamilia(tipo.getFamilia().getNombre());
    temp.setNombre(tipo.getNombre());
    temp.setTipoProduccion(tipoProduccionService.findById(tipo.getTipoProduccion()).getNombre());
    temp.setTieneTemplate(tipo.getTieneTemplate());
    temp.setTieneToken(tipo.getTieneToken());
    temp.setEstado(tipo.getEstado());
    temp.setEsNotificable(tipo.getEsNotificable());
    temp.setEsComunicable(tipo.getEsComunicable());
    temp.setResultado(tipo.getResultado());

    if (tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
      temp.setTieneTemplate(Boolean.FALSE);
      temp.setIdFormulario(null);
    } else if (tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
        || tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      temp.setTieneTemplate(Boolean.TRUE);
      
      TipoDocumentoTemplateDTO tipoDocumentoTemplate = procesamientoTemplate.obtenerUltimoTemplatePorTipoDocumento(tipo);
      
      if (tipoDocumentoTemplate != null) {
    	  temp.setIdFormulario(tipoDocumentoTemplate.getIdFormulario());
      }
    } else {
      temp.setTieneTemplate(Boolean.TRUE);
      temp.setIdFormulario(null);
    }

    List<MetadataDTO> listaMetadaData = tipo.getListaDatosVariables();
    List<ResponseMetadata> listaResponseMetadata = new ArrayList<ResponseMetadata>();
    for (MetadataDTO metadata : listaMetadaData) {
      ResponseMetadata tempMetaData = new ResponseMetadata();
      tempMetaData.setNombre(metadata.getNombre());
      tempMetaData.setObligatoriedad(metadata.isObligatoriedad());
      tempMetaData.setTipo(metadata.getTipo());

      listaResponseMetadata.add(tempMetaData);
    }
    temp.setListaDatosVariables(listaResponseMetadata);

    if (logger.isDebugEnabled()) {
      logger.debug("tipoToResponse(TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return temp;
  }

  public List<ResponseTipoDocumento> buscarTipoDocumentoByEstadoFiltradosManual(String estado,
      boolean esManual) throws ErrorConsultaTipoDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTipoDocumentoByEstadoFiltradosManual(String, boolean) - start"); //$NON-NLS-1$
    }

    try {

      List<TipoDocumentoDTO> listaTiposDocumento = this.tipoDocumentoService
          .buscarTipoDocumentoByEstadoFiltradosManual(estado, esManual);
      List<ResponseTipoDocumento> listaRetorno = new ArrayList<ResponseTipoDocumento>(
          listaTiposDocumento.size());
      for (TipoDocumentoDTO tipo : listaTiposDocumento) {
        ResponseTipoDocumento temp = this.tipoToResponse(tipo);
        listaRetorno.add(temp);
      }

      if (logger.isDebugEnabled()) {
        logger.debug("buscarTipoDocumentoByEstadoFiltradosManual(String, boolean) - end"); //$NON-NLS-1$
      }
      return listaRetorno;
    } catch (RuntimeException e) {
      logger.error("buscarTipoDocumentoByEstadoFiltradosManual(String, boolean)", e); //$NON-NLS-1$

      throw new ErrorConsultaTipoDocumentoException("Error al obtener tipos de documento", e);
    }
  }

  public ResponseTipoDocumento buscarTipoDocumentoByAcronimo(String acronimo)
      throws ErrorConsultaTipoDocumentoException, TipoDocumentoNoExisteException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTipoDocumentoByAcronimo(String) - start"); //$NON-NLS-1$
    }

    try {

      TipoDocumentoDTO tipoDocumento = this.tipoDocumentoService
          .buscarTipoDocumentoByAcronimo(acronimo);
      if (tipoDocumento != null) {
        ResponseTipoDocumento returnResponseTipoDocumento = this.tipoToResponse(tipoDocumento);
        if (logger.isDebugEnabled()) {
          logger.debug("buscarTipoDocumentoByAcronimo(String) - end"); //$NON-NLS-1$
        }
        return returnResponseTipoDocumento;
      } else {
        throw new TipoDocumentoNoExisteException(acronimo);
      }
    } catch (RuntimeException e) {
      logger.error("buscarTipoDocumentoByAcronimo(String)", e); //$NON-NLS-1$

      throw new ErrorConsultaTipoDocumentoException(
          "Error al obtener tipo de documento con acrónimo " + acronimo, e);
    }
  }

  @Override
  public List<ResponseTipoDocumento> getDocumentTypeByProduction(ProductionEnum productionType)
      throws ErrorConsultaTipoDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("getDocumentTypeByProduction(ProductionEnum) - start"); //$NON-NLS-1$
    }
    List<ResponseTipoDocumento> response = new ArrayList<>();
    final List<TipoDocumentoDTO> documentType = this.tipoDocumentoService
        .getDocumentTypeByProduction(productionType);
    if (CollectionUtils.isNotEmpty(documentType)) {
      for (TipoDocumentoDTO dto : documentType) {
        response.add(tipoToResponse(dto));
      }
    }
    if (logger.isDebugEnabled()) {
      logger.debug("getDocumentTypeByProduction(ProductionEnum) - end"); //$NON-NLS-1$
    }
    return response;
  }

  @Override
  public List<ResponseTipoDocumento> getTipoDocumentoByFamilia(String famNombre) {
    if (logger.isDebugEnabled()) {
      logger.debug("getTipoDocumentoByFamilia(String) - start");
    }

    List<ResponseTipoDocumento> response = new ArrayList<>();
    final List<TipoDocumentoDTO> documentType = tipoDocumentoService
        .getTipoDocumentoByFamiliaNombre(famNombre);
    if (CollectionUtils.isNotEmpty(documentType)) {
      for (TipoDocumentoDTO dto : documentType) {
        response.add(tipoToResponse(dto));
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getTipoDocumentoByFamilia(String) - end");
    }
    return response;
  }

  @Override
  public List<ResponseTipoDocumento> getTiposDocumentoHabilitados() {
    if (logger.isDebugEnabled()) {
      logger.debug("getTiposDocumentoHabilitados() - start"); //$NON-NLS-1$
    }

    List<ResponseTipoDocumento> response = new ArrayList<>();
    final List<TipoDocumentoDTO> documentType = tipoDocumentoService
        .getTiposDocumentoHabilitados();
    if (CollectionUtils.isNotEmpty(documentType)) {
      for (TipoDocumentoDTO dto : documentType) {
        response.add(tipoToResponse(dto));
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getTiposDocumentoHabilitados() - end"); //$NON-NLS-1$
    }
    return response;
  }

  @Override
  public List<ResponseTipoDocumento> getTipoDocumentoEspecial() {
    if (logger.isDebugEnabled()) {
      logger.debug("getTipoDocumentoEspecial() - start"); //$NON-NLS-1$
    }

    List<ResponseTipoDocumento> response = new ArrayList<>();
    final List<TipoDocumentoDTO> documentType = tipoDocumentoService.getTipoDocumentoEspecial();
    if (CollectionUtils.isNotEmpty(documentType)) {
      for (TipoDocumentoDTO dto : documentType) {
        response.add(tipoToResponse(dto));
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getTipoDocumentoEspecial() - end"); //$NON-NLS-1$
    }
    return response;
  }

  @Override
  public List<ResponseTipoDocumento> getTiposDocumento() {
    List<ResponseTipoDocumento> response = new ArrayList<>();
    final List<TipoDocumentoDTO> documentType = tipoDocumentoService.getTiposDocumento();
    if (CollectionUtils.isNotEmpty(documentType)) {
      for (TipoDocumentoDTO dto : documentType) {
        response.add(tipoToResponse(dto));
      }
    }

    return response;
  }

  @Override
  public ResponseTipoDocumento buscarTipoDocumentoPorId(Integer id) {
    ResponseTipoDocumento retorno = null;
    TipoDocumentoDTO resultado = tipoDocumentoService.buscarTipoDocumentoPorId(id);
    if (resultado != null) {
      retorno = tipoToResponse(resultado);
    }
    return retorno;
  }

  @Override
  public List<ResponseTipoDocumento> getTiposDocumentoResultado() {
    if (logger.isDebugEnabled()) {
      logger.debug("getTiposDocumentoResultado() - start");
    }
    
    List<ResponseTipoDocumento> responseTiposDocumento = new ArrayList<>();
    List<TipoDocumentoDTO> tiposDocumentoResultado = tipoDocumentoService.getTiposDocumentoResultado();
    
    if (tiposDocumentoResultado != null && !tiposDocumentoResultado.isEmpty()) {
      for (TipoDocumentoDTO tipoDocumentoDTO : tiposDocumentoResultado) {
        responseTiposDocumento.add(tipoToResponse(tipoDocumentoDTO));
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getTiposDocumentoResultado() - end");
    }
    
    return responseTiposDocumento;
  }

	@Override
	public List<ResponseTipoDocumento> getTipoDocumentoByIdSade(Integer id) {
		 if (logger.isDebugEnabled()) {
	      logger.debug("getTipoDocumentoByIdSade() - start");
	    }
	    
	    List<ResponseTipoDocumento> responseTiposDocumento = new ArrayList<>();
	    List<TipoDocumentoDTO> tiposDocumentoResultado = tipoDocumentoService.buscarDocumentosPorIdSade(id);
	    
	    if (tiposDocumentoResultado != null && !tiposDocumentoResultado.isEmpty()) {
	      for (TipoDocumentoDTO tipoDocumentoDTO : tiposDocumentoResultado) {
	        responseTiposDocumento.add(tipoToResponse(tipoDocumentoDTO));
	      }
	    }
	    
	    if (logger.isDebugEnabled()) {
	      logger.debug("getTipoDocumentoByIdSade() - end");
	    }
	    
	    return responseTiposDocumento;
	}

	@Override
	public boolean existeDocumentosAsociados(Integer idActuacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoDocumentoByIdSade() - start");
		}

		List<TipoDocumentoDTO> tiposDocumentoResultado = tipoDocumentoService.buscarDocumentosPorIdSade(idActuacion);

		if (tiposDocumentoResultado != null && !tiposDocumentoResultado.isEmpty()) {
			return true;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoDocumentoByIdSade() - end");
		}

		return false;
	}
}
