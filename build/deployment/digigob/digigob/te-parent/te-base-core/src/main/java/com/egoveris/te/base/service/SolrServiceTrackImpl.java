package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.ExpedienteTrack;
import com.egoveris.te.model.exception.ProcesoFallidoException;

@Service
@Transactional
public class SolrServiceTrackImpl implements SolrServiceTrack {

  @Autowired
  private SolrServer solrServerTrack;
  private Boolean activarIndexacion;
  private Logger logger = LoggerFactory.getLogger(SolrServiceTrackImpl.class);

  @Override
  public ExpedienteTrack buscarExpedientePapel(String letra, Integer anio, Integer numero,
      String reparticionActuacion, String reparticionUsuario)
      throws SolrServerException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "buscarExpedientePapel(letra={}, anio={}, numero={}, reparticionActuacion={}, reparticionUsuario={}) - start",
          letra, anio, numero, reparticionActuacion, reparticionUsuario);
    }

    String query = armarQuery(letra, anio, numero, reparticionActuacion, reparticionUsuario);
    SolrQuery solrQuery = new SolrQuery(query);

    logger.info("Ejecutando SolrQuery! ->  VALORES: " + letra + " " + anio + " " + numero + " "
        + reparticionActuacion + " " + reparticionUsuario);

    QueryResponse response = solrServerTrack.query(solrQuery);
    SolrDocumentList results = response.getResults();

    logger.info("QUERY EJECUTADA SIN PROBLEMAS! -> TRANSFORMANDO RESULTADO A BEAN");

    if (results.size() == 0) {
      logger.error("La QUERY NO HA RETORNADO NINGUN RESULTADO");
      throw new ProcesoFallidoException("No se han encontrado expedientes!", null);
    }

    if (results.size() > 1) {
      logger.error("El resultado de la query: " + query + " retorno mas de un resultado");
      throw new ProcesoFallidoException(
          "Internal Server error!. La busqueda retorno mas de un resultado", null);
    }

    ExpedienteTrack expediente = new ExpedienteTrack(results.get(0));

    if (logger.isDebugEnabled()) {
      logger.debug(
          "buscarExpedientePapel(String, Integer, Integer, String, String) - end - return value={}",
          expediente);
    }
    return expediente;
  }

  private String armarQuery(String letra, Integer anio, Integer numero,
      String reparticionActuacion, String reparticionUsuario) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarQuery(letra={}, anio={}, numero={}, reparticionActuacion={}, reparticionUsuario={}) - start",
          letra, anio, numero, reparticionActuacion, reparticionUsuario);
    }

    StringBuilder query = new StringBuilder("");
    query.append("letra:" + letra);
    query.append(" AND anio:" + anio);
    query.append(" AND numeroactuacion:" + numero);

    if (reparticionUsuario != null) {
      String repUsu = reparticionUsuario.trim();
      if (!StringUtils.isEmpty(repUsu)) {
        query.append(" AND reparticionusuario:" + repUsu);
      }
    }

    if (reparticionActuacion != null) {
      String repAct = reparticionActuacion.trim();
      if (!StringUtils.isEmpty(repAct)) {
        query.append(" AND reparticionactuacion:" + repAct);
      }
    }

    String returnString = query.toString();
    if (logger.isDebugEnabled()) {
      logger.debug("armarQuery(String, Integer, Integer, String, String) - end - return value={}",
          returnString);
    }
    return returnString;
  }

  public boolean getActivarIndexacion() {
    return activarIndexacion;
  }

  public void setActivarIndexacion(boolean activarIndexacion) {
    this.activarIndexacion = activarIndexacion;
  }

  public SolrServer getSolrServerTrack() {
    return solrServerTrack;
  }

  public void setSolrServerTrack(SolrServer solrServerTrack) {
    this.solrServerTrack = solrServerTrack;
  }

}
