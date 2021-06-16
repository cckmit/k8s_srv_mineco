package com.egoveris.te.base.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DatosDeBusqueda;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoIndex;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.iface.ISolrService;
import com.egoveris.te.base.util.SolrUtils;

@Service
@Transactional
public class SolrServiceImpl implements ISolrService {

  private static Logger logger = LoggerFactory.getLogger(SolrServiceImpl.class);
  @Autowired
  private SolrServer solrServerEE;
 

  @Override
  @Transactional(readOnly = true)
  public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(String username,
      String reparticion, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList,
      Date desde, Date hasta, String tipoDocumento, String numeroDocumento, String cuitCuil,
      String domicilio, String piso, String departamento, String codigoPostal, String estado) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(username={}, reparticion={}, trata={}, expedienteMetaDataList={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, domicilio={}, piso={}, departamento={}, codigoPostal={}, estado={}) - start",
          username, reparticion, trata, expedienteMetaDataList, desde, hasta, tipoDocumento,
          numeroDocumento, cuitCuil, domicilio, piso, departamento, codigoPostal, estado);
    }

    List<ExpedienteElectronicoIndex> listaExpedienteElectronicoIndex = new ArrayList<>();
    StringBuilder queryString = new StringBuilder();
    queryString.append("");

    if (username != null) {
      queryString.append("usuario_creador:");
      queryString.append(username);
    }
    if (reparticion != null) {
      queryString.append("codigo_reparticion_usuario:");
      queryString.append(reparticion);
    }

    if (domicilio != null) {
      queryString.append("domicilio:*");
      queryString.append(domicilio.toLowerCase().replaceAll("\\s", ""));
      queryString.append("*");
    }

    if (trata != null && trata.getCodigoTrata() != null) {
      queryString.append(" AND codigo_trat:");
      queryString.append(trata.getCodigoTrata());
    }
    if ((desde != null) && (hasta != null)) {
      Calendar calendarDate = Calendar.getInstance();
      calendarDate.setTime(hasta);
      calendarDate.add(Calendar.DATE, 1);
      hasta = calendarDate.getTime();
      queryString.append(" AND fecha_creacion:");
      queryString.append(SolrUtils.dateFilter(desde, hasta));
    }
    if (tipoDocumento != null) {
      queryString.append(" AND tipo_doc_ident:");
      queryString.append(tipoDocumento.substring(0, 2));
      queryString.append(" AND numero_documento:");
      queryString.append(numeroDocumento);
    }

    if (cuitCuil != null) {
      queryString.append(" AND cuit_cuil:");
      queryString.append(cuitCuil);
    }

    if (piso != null && !piso.equals("")) {
      queryString.append(" AND piso:");
      queryString.append(piso);
    }

    if (departamento != null && !departamento.equals("")) {
      queryString.append(" AND departamento:");
      queryString.append(departamento);
    }

    if (codigoPostal != null && !codigoPostal.equals("")) {
      queryString.append(" AND codigo_postal:");
      queryString.append(codigoPostal);
    }

    if (estado != null) {
      queryString.append(" AND estado:");
      queryString.append("\"");
      queryString.append(estado);
      queryString.append("\"");
    }

    try {
      listaExpedienteElectronicoIndex = obtenerExpedientesElectronicosSolr(queryString.toString());
    } catch (NegocioException e) {
      
      logger.error("error al obtener expediente electronico solr", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(String, String, Trata, List<ExpedienteMetadata>, Date, Date, String, String, String, String, String, String, String, String) - end - return value={}",
          listaExpedienteElectronicoIndex);
    }
    return listaExpedienteElectronicoIndex;
  }

  @Transactional(readOnly = true)
  public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(String username,
      String reparticion, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList,
      List<DatosDeBusqueda> metaDatos, Date desde, Date hasta, String tipoDocumento,
      String numeroDocumento, String cuitCuil, String estado) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(username={}, reparticion={}, trata={}, expedienteMetaDataList={}, metaDatos={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start",
          username, reparticion, trata, expedienteMetaDataList, metaDatos, desde, hasta,
          tipoDocumento, numeroDocumento, cuitCuil, estado);
    }

    List<ExpedienteElectronicoIndex> returnList = this.obtenerExpedientesElectronicosSolr(username,
        reparticion, trata, expedienteMetaDataList, metaDatos, desde, hasta, tipoDocumento,
        numeroDocumento, cuitCuil, null, null, null, null, estado);
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(String, String, Trata, List<ExpedienteMetadata>, List<DatosDeBusqueda>, Date, Date, String, String, String, String) - end - return value={}",
          returnList);
    }
    return returnList;
  }

  @Transactional(readOnly = true)
  public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(Date desde,
      Date hasta, String domicilio, String piso, String departamento, String codigoPostal,
      String estado) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(desde={}, hasta={}, domicilio={}, piso={}, departamento={}, codigoPostal={}, estado={}) - start",
          desde, hasta, domicilio, piso, departamento, codigoPostal, estado);
    }

    // Corregir
    List<ExpedienteElectronicoIndex> returnList = this.obtenerExpedientesElectronicosSolr(null,
        null, null, null, desde, hasta, null, null, null, domicilio, piso, departamento,
        codigoPostal, estado);
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(Date, Date, String, String, String, String, String) - end - return value={}",
          returnList);
    }
    return returnList;
  }

  @Transactional(readOnly = true)
  public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(String username,
      String reparticion, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList,
      List<DatosDeBusqueda> metaDatos, Date desde, Date hasta, String tipoDocumento,
      String numeroDocumento, String cuitCuil, String domicilio, String piso, String departamento,
      String codigoPostal, String estado) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(username={}, reparticion={}, trata={}, expedienteMetaDataList={}, metaDatos={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, domicilio={}, piso={}, departamento={}, codigoPostal={}, estado={}) - start",
          username, reparticion, trata, expedienteMetaDataList, metaDatos, desde, hasta,
          tipoDocumento, numeroDocumento, cuitCuil, domicilio, piso, departamento, codigoPostal,
          estado);
    }

    List<ExpedienteElectronicoIndex> listaExpedienteElectronicoIndex;
    StringBuilder queryBuffer = new StringBuilder();

    if (username != null) {
      queryBuffer.append("usuario_creador:");
      queryBuffer.append("\"");
      queryBuffer.append(username);
      queryBuffer.append("\"");
    }
    if (reparticion != null) {
      queryBuffer.append("codigo_reparticion_usuario:");
      queryBuffer.append(reparticion);
    }

    if (domicilio != null) {
      queryBuffer.append(" domicilio");
      queryBuffer.append(":");
      queryBuffer.append("*");
      queryBuffer.append(domicilio.toLowerCase().replaceAll("\\s", ""));
      queryBuffer.append("*");
    }

    if (trata != null && trata.getCodigoTrata() != null) {
      queryBuffer.append(" AND codigo_trata");
      queryBuffer.append(":");
      queryBuffer.append(trata.getCodigoTrata());
    }
    if ((desde != null) && (hasta != null)) {
      Calendar calendarDate = Calendar.getInstance();
      calendarDate.setTime(hasta);
      calendarDate.add(Calendar.DATE, 1);
      Date hastaAux = calendarDate.getTime();
      queryBuffer.append(" AND fecha_creacion");
      queryBuffer.append(":");
      queryBuffer.append(SolrUtils.dateFilter(desde, hastaAux));
    }
    if (tipoDocumento != null) {
      queryBuffer.append(" AND tipo_doc_ident");
      queryBuffer.append(":");
      queryBuffer.append(tipoDocumento.substring(0, 2));
      queryBuffer.append(" AND numero_documento");
      queryBuffer.append(":");
      queryBuffer.append(numeroDocumento);
    }

    // if(expedienteMetaDataList!=null){
    // for (int i = 0; i < expedienteMetaDataList.size(); i++) {
    // queryBuffer.append(" AND valor_metadato");
    // queryBuffer.append(":");
    // queryBuffer.append("\"");
    // queryBuffer.append(expedienteMetaDataList.get(i).getValor());
    // queryBuffer.append("\"");
    // }
    // }

    if (cuitCuil != null) {
      queryBuffer.append(" AND cuit_cuil");
      queryBuffer.append(":");
      queryBuffer.append(cuitCuil);
    }

    if (piso != null && !piso.equals("")) {
      queryBuffer.append(" AND piso");
      queryBuffer.append(":");
      queryBuffer.append(piso);
    }

    if (departamento != null && !departamento.equals("")) {
      queryBuffer.append(" AND departamento");
      queryBuffer.append(":");
      queryBuffer.append(departamento);
    }

    if (codigoPostal != null && !codigoPostal.equals("")) {
      queryBuffer.append(" AND codigo_postal");
      queryBuffer.append(":");
      queryBuffer.append(codigoPostal);
    }

    for (DatosDeBusqueda d : metaDatos) {

      queryBuffer.append(" AND (value_str:");
      agregarAQuery(queryBuffer, d);
      queryBuffer.append(" OR valor_metadato:");
      agregarAQuery(queryBuffer, d);
      queryBuffer.append(")");
    }

    if (estado != null) {
      queryBuffer.append(" AND estado:");
      queryBuffer.append("\"");
      queryBuffer.append(estado);
      queryBuffer.append("\"");
    }

    listaExpedienteElectronicoIndex = obtenerExpedientesElectronicosSolr(queryBuffer.toString());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(String, String, Trata, List<ExpedienteMetadata>, List<DatosDeBusqueda>, Date, Date, String, String, String, String, String, String, String, String) - end - return value={}",
          listaExpedienteElectronicoIndex);
    }
    return listaExpedienteElectronicoIndex;
  }

  private void agregarAQuery(StringBuilder queryBuffer, DatosDeBusqueda db) {
    if (logger.isDebugEnabled()) {
      logger.debug("agregarAQuery(queryBuffer={}, db={}) - start", queryBuffer, db);
    }

    if (db.getCampo().equalsIgnoreCase("fecha") || db.getCampo().contains("fecha")) {
      DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
      String fecha = format.format(db.getFecha());
      queryBuffer.append((db.getCampo().replaceAll("\\s", "*") + "*/" + fecha));
    } else {
      queryBuffer.append(db.getCampo().replaceAll("\\s", "*") + "*/*");
      queryBuffer.append(db.getValor().replaceAll("\\s", "*") + "*");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("agregarAQuery(StringBuffer, DatosDeBusqueda) - end");
    }
  }

  public static boolean isInteger(String nombreMetadato) {
    if (logger.isDebugEnabled()) {
      logger.debug("isInteger(nombreMetadato={}) - start", nombreMetadato);
    }

    try {
      Integer.parseInt(nombreMetadato);
    } catch (NumberFormatException e) {
      logger.error("isInteger(String)", e);

      if (logger.isDebugEnabled()) {
        logger.debug("isInteger(String) - end - return value={}", false);
      }
      return false;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("isInteger(String) - end - return value={}", true);
    }
    return true;
  }

  @Transactional(readOnly = true)
  public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosUsuarioTramitacionSolr(
      String username, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList,
      List<DatosDeBusqueda> metadatos, Date desde, Date hasta, String tipoDocumento,
      String numeroDocumento, String cuitCuil, String estado) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosUsuarioTramitacionSolr(username={}, trata={}, expedienteMetaDataList={}, metadatos={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start",
          username, trata, expedienteMetaDataList, metadatos, desde, hasta, tipoDocumento,
          numeroDocumento, cuitCuil, estado);
    }

    List<ExpedienteElectronicoIndex> listaExpedienteElectronicoIndex;
    String queryString;
    StringBuilder queryBuffer = new StringBuilder();

    if (username != null) {
      queryBuffer.append("assignee:");
      queryBuffer.append(username);
    }
    if (trata != null && trata.getCodigoTrata() != null) {
      queryBuffer.append(" AND codigo_trata");
      queryBuffer.append(":");
      queryBuffer.append(trata.getCodigoTrata());

    }
    if ((desde != null) && (hasta != null)) {
      Calendar calendarDate = Calendar.getInstance();
      calendarDate.setTime(hasta);
      calendarDate.add(Calendar.DATE, 1);
      hasta = calendarDate.getTime();
      queryBuffer.append(" AND fecha_creacion");
      queryBuffer.append(":");
      queryBuffer.append(SolrUtils.dateFilter(desde, hasta));
    }
    if (tipoDocumento != null) {
      queryBuffer.append(" AND tipo_doc_ident");
      queryBuffer.append(":");
      queryBuffer.append(tipoDocumento.substring(0, 2));
      queryBuffer.append(" AND numero_documento");
      queryBuffer.append(":");
      queryBuffer.append(numeroDocumento);
    }

    if (cuitCuil != null) {
      queryBuffer.append(" AND cuit_cuil");
      queryBuffer.append(":");
      queryBuffer.append(cuitCuil);
    }

    if (estado != null) {
      queryBuffer.append(" AND estado:");
      queryBuffer.append("\"");
      queryBuffer.append(estado);
      queryBuffer.append("\"");
    }

    for (DatosDeBusqueda d : metadatos) {
      queryBuffer.append(" AND (value_str:");
      agregarAQuery(queryBuffer, d);
      queryBuffer.append(" OR valor_metadato:");
      agregarAQuery(queryBuffer, d);
      queryBuffer.append(")");
    }

    listaExpedienteElectronicoIndex = obtenerExpedientesElectronicosSolr(queryBuffer.toString());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosUsuarioTramitacionSolr(String, Trata, List<ExpedienteMetadata>, List<DatosDeBusqueda>, Date, Date, String, String, String, String) - end - return value={}",
          listaExpedienteElectronicoIndex);
    }
    return listaExpedienteElectronicoIndex;
  }

  @Transactional(readOnly = true)
  public ExpedienteElectronicoIndex obtenerExpedientesElectronicosSolr(String tipoExpediente,
      Integer anio, Integer numero, String reparticion) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesElectronicosSolr(tipoExpediente={}, anio={}, numero={}, reparticion={}) - start",
          tipoExpediente, anio, numero, reparticion);
    }

    List<ExpedienteElectronicoIndex> listaExpedienteElectronicoIndex;
    String queryString = "";

    if (tipoExpediente != null)
      queryString = "tipo_documento:" + tipoExpediente;

    if (anio != null)
      queryString += " AND anio:" + anio;

    if (numero != null)
      queryString += " AND numero" + ":" + numero;

    if (reparticion != null)
      queryString += " AND codigo_reparticion_usuario:" + reparticion;

    listaExpedienteElectronicoIndex = obtenerExpedientesElectronicosSolr(queryString);

    if (listaExpedienteElectronicoIndex.isEmpty()) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "obtenerExpedientesElectronicosSolr(String, Integer, Integer, String) - end - return value={}, return null");
      }
      return null;
    } else {
      ExpedienteElectronicoIndex returnExpedienteElectronicoIndex = listaExpedienteElectronicoIndex
          .get(0);
      if (logger.isDebugEnabled()) {
        logger.debug(
            "obtenerExpedientesElectronicosSolr(String, Integer, Integer, String) - end - return value={}",
            returnExpedienteElectronicoIndex);
      }
      return returnExpedienteElectronicoIndex;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(String queryString)
      throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedientesElectronicosSolr(queryString={}) - start", queryString);
    }

    List<ExpedienteElectronicoIndex> listaExpedienteElectronicoIndex = null;

    try {

      SolrQuery query = new SolrQuery(queryString);
      query.setRows(300);

      QueryResponse response = solrServerEE.query(query);

      SolrDocumentList docList = response.getResults();
      listaExpedienteElectronicoIndex = new ArrayList<>();

      for (SolrDocument document : docList) {
        ExpedienteElectronicoIndex reg;
        reg = this.convertirSolrDocument(document);
        listaExpedienteElectronicoIndex.add(reg);
      }

    } catch (SolrServerException e) {
      logger.error(e.getMessage(), e);
      throw new NegocioException("Error al obtener los registros", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedientesElectronicosSolr(String) - end - return value={}",
          listaExpedienteElectronicoIndex);
    }
    return listaExpedienteElectronicoIndex;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(String queryString,
      boolean todos) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedientesElectronicosSolr(queryString={}, todos={}) - start",
          queryString, todos);
    }

    List<ExpedienteElectronicoIndex> listaExpedienteElectronicoIndex = null;

    try {

      SolrQuery query = new SolrQuery(queryString);
      if (todos) {
        query.setRows(100000000);
      } else {
        query.setRows(300);
      }

      QueryResponse response = solrServerEE.query(query);

      SolrDocumentList docList = response.getResults();
      listaExpedienteElectronicoIndex = new ArrayList<>();

      for (SolrDocument document : docList) {
        ExpedienteElectronicoIndex reg;
        reg = this.convertirSolrDocument(document);
        listaExpedienteElectronicoIndex.add(reg);
      }

    } catch (SolrServerException e) {
      logger.error(e.getMessage(), e);
      throw new NegocioException("Error al obtener los registros", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedientesElectronicosSolr(String, boolean) - end - return value={}",
          listaExpedienteElectronicoIndex);
    }
    return listaExpedienteElectronicoIndex;
  }

  @SuppressWarnings("unchecked")
  private ExpedienteElectronicoIndex convertirSolrDocument(SolrDocument document)
      throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("convertirSolrDocument(document={}) - start", document);
    }

    ExpedienteElectronicoIndex reg = new ExpedienteElectronicoIndex();
    reg.setId(Long.valueOf(document.get("id").toString()));

    if (document.get("fecha_creacion") != null) {
      reg.setFechaCreacion((Date) document.get("fecha_creacion"));
    }
    if (document.get("usuario_creador") != null) {
      reg.setUsuarioCreador((String) document.get("usuario_creador"));
    }
    if (document.get("tipo_documento") != null) {
      reg.setTipoDocumento((String) document.get("tipo_documento"));
    }
    if (document.get("anio") != null) {
      reg.setAnio(Integer.valueOf((String) document.get("anio")));
    }
    if (document.get("numero") != null) {
      reg.setNumero(Integer.valueOf((String) document.get("numero")));
    }
    if (document.get("codigo_reparticion_actuacion") != null) {
      reg.setCodigoReparticionActuacion((String) document.get("codigo_reparticion_actuacion"));
    }
    if (document.get("codigo_reparticion_usuario") != null) {
      reg.setCodigoReparticionUsuario((String) document.get("codigo_reparticion_usuario"));
    }
    if (document.get("motivo") != null) {
      reg.setMotivo((String) document.get("motivo"));
    }
    if (document.get("assignee") != null) {
      reg.setListaAssignee((List<String>) document.get("assignee"));
    }
    if (document.get("estado") != null) {
      reg.setEstado((String) document.get("estado"));
    }
    if (document.get("id_workflow") != null) {
      reg.setIdWorkflow((String) document.get("id_workflow"));
    }
    if (document.get("codigo_trata") != null) {
      reg.setCodigoTrata((String) document.get("codigo_trata"));
    }

    if (document.get("value_str") != null) {
      reg.setValueStr((ArrayList<String>) document.get("value_str"));
    }
    if (document.get("value_int") != null) {
      reg.setValueStr((ArrayList<String>) document.get("value_int"));
    }
    if (document.get("value_date") != null) {
      reg.setValueStr((ArrayList<String>) document.get("value_date"));
    }
    if (document.get("value_double") != null) {
      reg.setValueStr((ArrayList<String>) document.get("value_double"));
    }
    if (document.get("cuit_cuil") != null) {
      reg.setCuitCuil((String) document.get("cuit_cuil"));

    }
    if (document.get("fecha_modificacion") != null) {
      reg.setFechamodificacion((Date) document.get("fecha_modificacion"));

    }
    if (document.get("TIPO_DOCUMENTO_SOLICITANTE") != null) {
      reg.setTipoDocumentoSolicitante((String) document.get("TIPO_DOCUMENTO_SOLICITANTE"));

    }
    if (document.get("NUMERO_DOCUMENTO_SOLICITANTE") != null) {
      reg.setNumeroDocumentoSolicitante((String) document.get("NUMERO_DOCUMENTO_SOLICITANTE"));

    }
    if (document.get("APELLIDO_SOLICITANTE") != null) {
      reg.setApellidoSolicitante((String) document.get("APELLIDO_SOLICITANTE"));

    }
    if (document.get("CUIT_CUIL_SOLICITANTE") != null) {
      reg.setCuitCuilSolicitante((String) document.get("CUIT_CUIL_SOLICITANTE"));

    }
    if (document.get("DEPARTAMENTO") != null) {
      reg.setDepartamentoSolicitante((String) document.get("DEPARTAMENTO"));

    }
    if (document.get("DOMICILIO") != null) {
      reg.setDomicilioSolicitante((String) document.get("DOMICILIO"));

    }
    if (document.get("EMAIL") != null) {
      reg.setEmailSolicitante((String) document.get("EMAIL"));

    }
    if (document.get("ID_DOCUMENTO") != null) {
      reg.setIdTipoDocumentoSolicitante((String) document.get("ID_DOCUMENTO"));

    }
    if (document.get("NOMBRE_SOLICITANTE") != null) {
      reg.setNombreSolicitante((String) document.get("NOMBRE_SOLICITANTE"));

    }
    if (document.get("PISO") != null) {
      reg.setPisoSolicitante((String) document.get("PISO"));

    }
    if (document.get("RAZON_SOCIAL_SOLICITANTE") != null) {
      reg.setRazonSocialSolicitante((String) document.get("RAZON_SOCIAL_SOLICITANTE"));

    }
    if (document.get("SEGUNDO_APELLIDO_SOLICITANTE") != null) {
      reg.setSegundoApellidoSolicitante((String) document.get("SEGUNDO_APELLIDO_SOLICITANTE"));

    }
    if (document.get("SEGUNDO_NOMBRE_SOLICITANTE") != null) {
      reg.setSegundoNombreSolicitante((String) document.get("SEGUNDO_NOMBRE_SOLICITANTE"));

    }
    if (document.get("TERCER_APELLIDO_SOLICITANTE") != null) {
      reg.setTercerApellidoSolicitante((String) document.get("TERCER_APELLIDO_SOLICITANTE"));

    }
    if (document.get("CODIGO_POSTAL") != null) {
      reg.setCodigoPostalSolicitante((String) document.get("CODIGO_POSTAL"));

    }
    if (document.get("TERCER_NOMBRE_SOLICITANTE") != null) {
      reg.setTercerNombreSolicitante((String) document.get("TERCER_NOMBRE_SOLICITANTE"));

    }
    if (document.get("ID_SOLICITANTE") != null) {
      reg.setSolicitante((String) document.get("ID_SOLICITANTE"));

    }
    if (document.get("MOTIVO_INTERNO") != null) {
      reg.setMotivoInternoSolicitud((String) document.get("MOTIVO_INTERNO"));

    }
    if (document.get("MOTIVO_EXTERNO") != null) {
      reg.setMotivoExternoSolicitud((String) document.get("MOTIVO_EXTERNO"));

    }
    if (document.get("TELEFONO") != null) {
      reg.setTelefono((String) document.get("TELEFONO"));

    }
    if (document.get("INTERNA") != null) {
      reg.setIsInterna((String) document.get("INTERNA"));

    }

    if (logger.isDebugEnabled()) {
      logger.debug("convertirSolrDocument(SolrDocument) - end - return value={}", reg);
    }
    return reg;
  }

  public void indexar(ExpedienteElectronicoDTO expedienteElectronico) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("indexar(expedienteElectronico={}) - start", expedienteElectronico);
    }

    this.indexarFormularioControlado(expedienteElectronico, null);

    if (logger.isDebugEnabled()) {
      logger.debug("indexar(ExpedienteElectronico) - end");
    }
  }

  public void indexarFormularioControlado(ExpedienteElectronicoDTO expedienteElectronico,
      Map<String, Object> camposFC) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("indexarFormularioControlado(expedienteElectronico={}, camposFC={}) - start",
          expedienteElectronico, camposFC);
    }

    ExpedienteElectronicoIndex eeIndex = new ExpedienteElectronicoIndex();
    HashMap<String, Object> campos;
    Iterator<?> camposIt;
    String fieldFC;
    Object valueFc;
    String tipo;
    ArrayList<String> tipos = new ArrayList<>();
    List<String> camposS = new ArrayList<>();

    ExpedienteElectronicoIndex eeIndexAux = this.obtenerExpedientesElectronicosSolr(
        expedienteElectronico.getTipoDocumento(), expedienteElectronico.getAnio(),
        expedienteElectronico.getNumero(), expedienteElectronico.getCodigoReparticionUsuario());

    eeIndex.setId(expedienteElectronico.getId());
    eeIndex.setIdWorkflow(expedienteElectronico.getIdWorkflow());

    if (eeIndexAux != null) {
      for (int i = 0; i < eeIndexAux.getListaAssignee().size(); i++) {
        eeIndex.addListaAssignee(eeIndexAux.getListaAssignee().get(i));
      }
      if (!eeIndex.getListaAssignee().contains(expedienteElectronico.getUsuarioModificacion())) {
        eeIndex.addListaAssignee(expedienteElectronico.getUsuarioModificacion());
      }
    } else {
      eeIndex.addListaAssignee(expedienteElectronico.getUsuarioCreador());
    }

    if (null != camposFC) {

      campos = (HashMap<String, Object>) camposFC;
      camposIt = campos.entrySet().iterator();

      while (camposIt.hasNext()) {
        Map.Entry<String, Object> fields = (Entry<String, Object>) camposIt.next();

        fieldFC = fields.getKey();
        valueFc = fields.getValue();
        camposS.add(fieldFC);

        tipo = obtenerTipo(valueFc);
        camposIt.remove();
        String valorStr;
        if (tipo.equalsIgnoreCase("Integer")) {
          valorStr = Integer.toString((Integer) valueFc);
          eeIndex.setValueInt(valorStr);
        } else if (tipo.equalsIgnoreCase("Double")) {
          valorStr = Double.toString((Double) valueFc);
          eeIndex.setValueDouble(valorStr);
        } else if (tipo.equalsIgnoreCase("Date")) {
          SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
          valorStr = format.format((Date) valueFc);
        } else {
          valorStr = String.valueOf(valueFc);
        }
        String campoValor = fieldFC + "/" + valorStr;
        tipos.add(campoValor);
      }
      eeIndex.setValueStr(tipos);
      loadIndex(expedienteElectronico, eeIndex);

    } else {
      if (eeIndexAux != null)
        eeIndex.setValueStr(eeIndexAux.getValueStr());
      loadIndex(expedienteElectronico, eeIndex);
    }

    try {
      this.solrServerEE.addBean(eeIndex);
      this.solrServerEE.commit();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new NegocioException("Error al indexar el registro", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("indexarFormularioControlado(ExpedienteElectronico, Map<String,Object>) - end");
    }
  }

  // Este Metodo Solo se debe utilizar en el full-import desde EE
  public void clearIndex() {
    if (logger.isDebugEnabled()) {
      logger.debug("clearIndex() - start");
    }

    try {
      this.solrServerEE.deleteByQuery("*:*");
      this.solrServerEE.commit();
    }

    catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("clearIndex() - end");
    }
  };

  public void executeDeltaImport() {
    if (logger.isDebugEnabled()) {
      logger.debug("executeDeltaImport() - start");
    }

    ModifiableSolrParams params = new ModifiableSolrParams();
    params.set("command", "delta-import");
    QueryRequest request = new QueryRequest(params);
    request.setPath("/dataimport");

    try {

      this.solrServerEE.request(request);
    } catch (Exception e) {
      logger.error("error executeDeltaImport", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("executeDeltaImport() - end");
    }
  }

  /**
   * @param expedienteElectronico
   * @param eeIndex
   */
  private void loadIndex(ExpedienteElectronicoDTO expedienteElectronico,
      ExpedienteElectronicoIndex eeIndex) {
    if (logger.isDebugEnabled()) {
      logger.debug("loadIndex(expedienteElectronico={}, eeIndex={}) - start",
          expedienteElectronico, eeIndex);
    }

    eeIndex.setEstado(expedienteElectronico.getEstado());
    eeIndex.setFechaCreacion(expedienteElectronico.getFechaCreacion());
    eeIndex.setDescripcion(expedienteElectronico.getDescripcion());
    eeIndex.setUsuarioCreador(expedienteElectronico.getUsuarioCreador());
    eeIndex.setTipoDocumento(expedienteElectronico.getTipoDocumento());
    eeIndex.setAnio(expedienteElectronico.getAnio());
    eeIndex.setNumero(expedienteElectronico.getNumero());
    eeIndex.setCodigoReparticionActuacion(expedienteElectronico.getCodigoReparticionActuacion());
    eeIndex.setCodigoReparticionUsuario(expedienteElectronico.getCodigoReparticionUsuario());
    eeIndex.setIdTrata(expedienteElectronico.getTrata().getId());
    eeIndex.setCodigoTrata(expedienteElectronico.getTrata().getCodigoTrata());

    if (null != expedienteElectronico.getMetadatosDeTrata()) {

      for (int i = 0; i < expedienteElectronico.getMetadatosDeTrata().size(); i++) {
        eeIndex
            .addListNombreMetadato(expedienteElectronico.getMetadatosDeTrata().get(i).getNombre());
      }

      for (int i = 0; i < expedienteElectronico.getMetadatosDeTrata().size(); i++) {
        String valor = expedienteElectronico.getMetadatosDeTrata().get(i).getNombre() + "/"
            + expedienteElectronico.getMetadatosDeTrata().get(i).getValor();
        eeIndex.addListValorMetadato(valor);
      }

    }

    eeIndex.setMotivo(expedienteElectronico.getSolicitudIniciadora().getMotivo());
    eeIndex.setFechamodificacion(expedienteElectronico.getFechaModificacion());
    if (expedienteElectronico.getSolicitudIniciadora() != null
        && expedienteElectronico.getSolicitudIniciadora().getSolicitante() != null) {
      if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento() != null) {
        eeIndex.setTipoDocumentoSolicitante(expedienteElectronico.getSolicitudIniciadora()
            .getSolicitante().getDocumento().getTipoDocumento());
        eeIndex.setNumeroDocumentoSolicitante(expedienteElectronico.getSolicitudIniciadora()
            .getSolicitante().getDocumento().getNumeroDocumento());
        eeIndex.setIdTipoDocumentoSolicitante(String.valueOf(expedienteElectronico
            .getSolicitudIniciadora().getSolicitante().getDocumento().getId()));
      }
      eeIndex.setIdSolicitanteSolr(
          String.valueOf(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getId()));
      eeIndex.setApellidoSolicitante(expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getApellidoSolicitante());
      eeIndex.setCodigoPostalSolicitante(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCodigoPostal());
      eeIndex.setCuitCuilSolicitante(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCuitCuil());
      //eeIndex.setDepartamentoSolicitante(
      //    expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDepartamento());
      eeIndex.setDomicilioSolicitante(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDomicilio());
      eeIndex.setEmailSolicitante(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getEmail());
      eeIndex.setNombreSolicitante(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getNombreSolicitante());
      eeIndex.setPisoSolicitante(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getPiso());
      eeIndex.setRazonSocialSolicitante(expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getRazonSocialSolicitante());
      eeIndex.setSegundoApellidoSolicitante(expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getSegundoApellidoSolicitante());
      eeIndex.setSegundoNombreSolicitante(expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getSegundoNombreSolicitante());
      eeIndex.setTercerApellidoSolicitante(expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getTercerApellidoSolicitante());
      eeIndex.setTelefono(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getTelefono());
      eeIndex.setTercerNombreSolicitante(expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getTercerNombreSolicitante());
      eeIndex.setSolicitante(
          String.valueOf(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getId()));
      eeIndex
          .setMotivoInternoSolicitud(expedienteElectronico.getSolicitudIniciadora().getMotivo());
      eeIndex.setMotivoExternoSolicitud(
          expedienteElectronico.getSolicitudIniciadora().getMotivoExterno());
      eeIndex.setTelefono(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getTelefono());
      eeIndex.setIsInterna(
          String.valueOf(expedienteElectronico.getSolicitudIniciadora().isEsSolicitudInterna()));

    }
    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento() != null) {
      eeIndex.setIdDocumento(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento().getId());
      eeIndex.setTipoDocumentoDocIdent(expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getDocumento().getTipoDocumento());
      eeIndex.setNumeroDocumento(expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getDocumento().getNumeroDocumento());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getPiso() != null) {
      eeIndex.setPiso(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getPiso());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante()
        .getDepartamento() != null) {
      eeIndex.setDepartamento(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDepartamento());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDomicilio() != null) {
      eeIndex.setDomicilio(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDomicilio());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante()
        .getCodigoPostal() != null) {
      eeIndex.setCodigoPostal(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCodigoPostal());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCuitCuil() != null) {
      eeIndex.setCuitCuil(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCuitCuil());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento() != null) {
      eeIndex.setIdDocumento(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento().getId());
      eeIndex.setTipoDocumentoDocIdent(expedienteElectronico.getSolicitudIniciadora()
          .getSolicitante().getDocumento().getTipoDocumento());
      eeIndex.setNumeroDocumento(expedienteElectronico.getSolicitudIniciadora().getSolicitante()
          .getDocumento().getNumeroDocumento());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getPiso() != null) {
      eeIndex.setPiso(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getPiso());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante()
        .getDepartamento() != null) {
      eeIndex.setDepartamento(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDepartamento());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDomicilio() != null) {
      eeIndex.setDomicilio(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDomicilio());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante()
        .getCodigoPostal() != null) {
      eeIndex.setCodigoPostal(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCodigoPostal());
    }

    if (expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCuitCuil() != null) {
      eeIndex.setCuitCuil(
          expedienteElectronico.getSolicitudIniciadora().getSolicitante().getCuitCuil());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("loadIndex(ExpedienteElectronico, ExpedienteElectronicoIndex) - end");
    }
  }

  private String obtenerTipo(Object campoFc) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipo(campoFc={}) - start", campoFc);
    }

    String tipo = "NULL";

    if (null != campoFc && campoFc instanceof Integer) {
     
      tipo = "Integer";
    } else if (null != campoFc && campoFc instanceof Double) {

      tipo = "Double";
    }
    if (null != campoFc && campoFc instanceof Date) {
      tipo = "Date";
    } else {
      if (campoFc != null) {
        tipo = StringUtils.defaultIfEmpty(campoFc.toString(), tipo);
      } else {
        logger.info("El objeto buscado se encuentra nulo");
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipo(Object) - end - return value={}", tipo);
    }
    return tipo;
  }
  
  
 

}
