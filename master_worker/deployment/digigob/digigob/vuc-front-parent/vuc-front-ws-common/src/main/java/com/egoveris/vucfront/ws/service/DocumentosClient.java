package com.egoveris.vucfront.ws.service;

import java.util.List;

import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;

public interface DocumentosClient {

	List<ExternalDocumentoVucDTO> getDocumentosByCodigoExpediente(String codigoExpediente, String codigoTramite);

	ExternalTipoDocumentoVucDTO getTipoDocumentoByAcronimoVuc(String acronimoVuc);

	List<ExternalDocumentoVucDTO> getDocumentosByCodigoTramite(String trata);

	void vincularDocVuc(String expediente, String documento);
}
