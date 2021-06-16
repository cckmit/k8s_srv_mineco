package com.egoveris.vucfront.ws.service;

import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;

import java.util.List;

public interface ExternalDocumentoService {

  /**
   * Get all Documentos from an Expediente and excludes Documentos from the
   * Tramite.
   * 
   * @param codigoExpediente
   * @param codigoTramite
   * @return
   */
  List<ExternalDocumentoVucDTO> getDocumentosByCodigoExpediente(String codigoExpediente,
      String codigoTramite);

  ExternalTipoDocumentoVucDTO getTipoDocumentoByAcronimoVuc(String acronimoVuc);

  List<ExternalDocumentoVucDTO> getDocumentosByCodigoTramite(String trata);
  
  void vincularDocVuc(String expediente, String documento);
}