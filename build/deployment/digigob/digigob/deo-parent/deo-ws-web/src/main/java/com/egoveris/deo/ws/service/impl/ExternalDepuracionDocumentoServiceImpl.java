package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.service.DocumentoService;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.RequestExternalDepuracionDocumento;
import com.egoveris.deo.model.model.ResponseExternalDepuracionDocumento;
import com.egoveris.deo.ws.exception.ErrorDepuracionDocumentoException;
import com.egoveris.deo.ws.service.IExternalDepuracionDocumentoService;

import javax.jws.WebParam;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalDepuracionDocumentoServiceImpl
    implements IExternalDepuracionDocumentoService {
  @Autowired
  private DocumentoService documentoService;
  @Autowired
  private Mapper mapper;

  private static final Logger logger = LoggerFactory
      .getLogger(ExternalDepuracionDocumentoServiceImpl.class);

  public ResponseExternalDepuracionDocumento depuracionDocumento(
      RequestExternalDepuracionDocumento request)
      throws ErrorDepuracionDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("depuracionDocumento(RequestExternalDepuracionDocumento) - start"); //$NON-NLS-1$
    }

    ResponseExternalDepuracionDocumento response = new ResponseExternalDepuracionDocumento();

    for (String nroSADE : request.getListaNroSADE()) {
      DocumentoDTO documento = this.mapper.map(documentoService.findByNumero(nroSADE),
          DocumentoDTO.class);
      if (documento == null) {
        logger.error("El documento con número SADE " + nroSADE + " no existe en GEDO");
      } else {
        if (documento.getMotivoDepuracion() == null) {
          documento.setMotivoDepuracion(request.getMotivoDepuracion());
          documentoService.save(documento);
          logger.info("Se persistio el documento con número SADE " + nroSADE
              + " con el siguiente motivo de depuracion : " + request.getMotivoDepuracion());
        } else if (documento.getMotivoDepuracion() != null) {
          logger
              .error("El documento con número SADE " + nroSADE + " ya fue previamente depurado.");
        }
      }
    }

    response.setEstado("Ok");

    if (logger.isDebugEnabled()) {
      logger.debug("depuracionDocumento(RequestExternalDepuracionDocumento) - end"); //$NON-NLS-1$
    }
    return response;
  }
}
