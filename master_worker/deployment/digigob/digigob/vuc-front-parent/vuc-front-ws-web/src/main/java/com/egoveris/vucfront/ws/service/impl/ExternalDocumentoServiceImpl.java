package com.egoveris.vucfront.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.ExpedienteDocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteDocumentoIDDTO;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.ExpedienteBaseService;
import com.egoveris.vucfront.model.service.ExpedienteService;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;
import com.egoveris.vucfront.ws.service.ExternalDocumentoService;

@Service
public class ExternalDocumentoServiceImpl implements ExternalDocumentoService {
	
  private Logger logger = LoggerFactory.getLogger(ExternalDocumentoServiceImpl.class);
  @Autowired
  @Qualifier("vucMapper")
  private Mapper mapper;

  @Autowired
  private DocumentoService documentoService;
  @Autowired
  private ExpedienteService expedienteService;
  @Autowired
  private ExpedienteBaseService expedienteBaseService;

  @Override
  public List<ExternalDocumentoVucDTO> getDocumentosByCodigoExpediente(String codigoExpediente,
      String codigoTramite) {
    List<ExternalDocumentoVucDTO> retorno = new ArrayList<>();
    List<DocumentoDTO> result = documentoService.getDocumentosByExpedienteCodigo(codigoExpediente);
    List<TipoDocumentoDTO> resultTipoDoc = documentoService
        .getTipoDocumentoByCodigoTramite(codigoTramite);

    if (result != null && !result.isEmpty()) {
      for (DocumentoDTO aux : result) {
        if (resultTipoDoc.contains(aux.getTipoDocumento())) {
			ExternalDocumentoVucDTO doc = mapper.map(aux, ExternalDocumentoVucDTO.class);
			doc.setNumeroSade(aux.getCodigoSade());
			doc.setMotivo(aux.getReferencia());
			retorno.add(doc);
        }
      }
    }
    return retorno;
  }

  @Override
  public ExternalTipoDocumentoVucDTO getTipoDocumentoByAcronimoVuc(String acronimoVuc) {
    TipoDocumentoDTO result = documentoService.getTipoDocumentoByAcronimoVuc(acronimoVuc);
    if (result != null) {
      return mapper.map(result, ExternalTipoDocumentoVucDTO.class);
    }
    return null;
  }

  @Override
  public List<ExternalDocumentoVucDTO> getDocumentosByCodigoTramite(String trata) {
    List<ExternalDocumentoVucDTO> retorno = new ArrayList<>();
    List<TipoDocumentoDTO> result = documentoService.getTipoDocumentoByCodigoTramite(trata);
    if (!result.isEmpty()) {
      for (TipoDocumentoDTO aux : result) {
        ExternalDocumentoVucDTO newVucDoc = new ExternalDocumentoVucDTO();
        newVucDoc.setTipoDocumento(mapper.map(aux, ExternalTipoDocumentoVucDTO.class));
        retorno.add(newVucDoc);
      }
    }
    return retorno;
  }

	@Override
	public void vincularDocVuc(String expediente, String documento) {
		ExpedienteBaseDTO ee = expedienteService.getExpedienteBaseByCodigo(expediente);
		DocumentoDTO doc = documentoService.getDocumentoByNumero(documento);
		ExpedienteDocumentoIDDTO eeDocID = new ExpedienteDocumentoIDDTO();
		ExpedienteDocumentoDTO eeDoc = new ExpedienteDocumentoDTO();
		eeDocID.setIdExpediente(ee.getId());
		eeDocID.setIdDocumento(doc.getId());
		eeDoc.setId(eeDocID);
		expedienteBaseService.saveExpedienteDocumento(eeDoc);
	}
}