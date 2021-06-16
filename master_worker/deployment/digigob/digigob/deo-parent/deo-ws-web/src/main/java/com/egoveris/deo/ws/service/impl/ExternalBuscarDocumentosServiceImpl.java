package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentoSolr;
import com.egoveris.deo.model.model.DocumentoSolrResponse;
import com.egoveris.deo.model.model.RequestExternalBuscarDocumentos;
import com.egoveris.deo.model.model.ResponseExternalBuscarDocumentos;
import com.egoveris.deo.ws.exception.ErrorConsultaNumeroSadeException;
import com.egoveris.deo.ws.service.IExternalBuscarDocumentoService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author MAGARCES
 *
 */
@Service
public class ExternalBuscarDocumentosServiceImpl implements IExternalBuscarDocumentoService {

  @Autowired
  private BuscarDocumentosGedoService buscarDocGedoSer;
  private ConsultaSolrRequest consultaSolrRequest;

  @Override
  public ResponseExternalBuscarDocumentos buscarDocumentoEnGedo(
      RequestExternalBuscarDocumentos request) throws ErrorConsultaNumeroSadeException {

    // Carga el objeto ConsultaSolrRequest con los datos de entrada
    this.setConsultaSolr(
        this.convertRequestExternalBuscarDocumentosToConsultaSolrRequest(request));

    // Realiza la busqueda

    Page<DocumentoSolr> page = this.buscarDocGedoSer.buscarDocumentos(this.getConsultaSolr());

    ResponseExternalBuscarDocumentos response = null;

    // Si la lista es == 0 significa que no encontro nada con el criterio
    // ingresado
    if (page.getContent().size() != 0) {

      // Carga el objecto ResponseExternalBuscarDocumentos
      response = new ResponseExternalBuscarDocumentos();

      response.setIndexSortColumn(getConsultaSolr().getIndexSortColumn());
      response.setEquals(getConsultaSolr().getCriteria().equals(ConsultaSolrRequest.ORDER_ASC));
      response.setFoot((int) page.getTotalElements());
      response.setDocumentos(
          this.convertConsultaSolrRequestToRequestExternalBuscarDocumentos(page.getContent()));
      response.setTotalSize((int) page.getTotalElements());
    }

    return response;
  }

  private void setConsultaSolr(ConsultaSolrRequest consultaSolrRequest) {
    this.consultaSolrRequest = consultaSolrRequest;
  }

  private ConsultaSolrRequest getConsultaSolr() {
    return this.consultaSolrRequest;
  }

  private ConsultaSolrRequest convertRequestExternalBuscarDocumentosToConsultaSolrRequest(
      RequestExternalBuscarDocumentos request) {
    String tipoBusqueda = request.getTipoBusqueda();

    ConsultaSolrRequest requestSolr = new ConsultaSolrRequest();

    requestSolr.setPageIndex(request.getPageIndex());
    requestSolr.setPageSize(request.getPageSize());

    requestSolr.setFechaDesde(request.getFechaDesde());
    requestSolr.setFechaHasta(request.getFechaHasta());

    if (request.getAcronimo() != null) {
      requestSolr.setTipoDocAcr(request.getAcronimo());
    }

    if (request.getNombre() != null) {
      requestSolr.setTipoDocDescr(request.getNombre());
    }

    if (request.getGeneradosPorMi()) {
      requestSolr.setUsuarioGenerador(request.getUsuarioGenerador());
    }

    if (request.getGeneradosPorMiReparticion()) {
      requestSolr.setReparticion(request.getCodigoReparticion());
    }

    if (request.getUsuarioFirmante() != null) {
      requestSolr.setUsuarioFirmante(request.getUsuarioFirmante());
    }

    if (request.getContainsMap().size() > 0) {
      requestSolr.getContainsMap().putAll(request.getContainsMap());
    }

    if (request.getEqualsMap().size() > 0) {
      requestSolr.getEqualsMap().putAll(request.getEqualsMap());
    }

    requestSolr.setTipoBusqueda(tipoBusqueda);
    return requestSolr;

  }

  private List<DocumentoSolrResponse> convertConsultaSolrRequestToRequestExternalBuscarDocumentos(
      List<DocumentoSolr> documentoSolrList) {

    List<DocumentoSolrResponse> documentoResponseList = new ArrayList<DocumentoSolrResponse>();

    for (DocumentoSolr documentoSolr : documentoSolrList) {

      DocumentoSolrResponse documentoResponse = new DocumentoSolrResponse();

      documentoResponse.setActuacionAcr(documentoSolr.getActuacionAcr());
      documentoResponse.setAnioDoc(documentoSolr.getAnioDoc());
      documentoResponse.setFechaCreacion(documentoSolr.getFechaCreacion());
      documentoResponse.setId(documentoSolr.getId());
      documentoResponse.setNroDoc(documentoSolr.getNroDoc());
      documentoResponse.setNroEspecialSade(documentoSolr.getNroEspecialSade());
      documentoResponse.setNroSade(documentoSolr.getNroSade());
      documentoResponse.setNroSadePapel(documentoSolr.getNroSadePapel());
      documentoResponse.setReferencia(documentoSolr.getReferencia());
      documentoResponse.setReparticion(documentoSolr.getReparticion());
      documentoResponse.setTipoDocAcr(documentoSolr.getTipoDocAcr());
      documentoResponse.setTipoDocNombre(documentoSolr.getTipoDocNombre());
      documentoResponse.setUsuarioGenerador(documentoSolr.getUsuarioGenerador());
      documentoResponse.setNombre(documentoSolr.getNroSade().concat(".pdf"));
      documentoResponseList.add(documentoResponse);

    }

    return documentoResponseList;
  }

}
