package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

@Service
@Transactional
public class GuardarExpedienteElectronicoPredicate implements Predicate {
  private static final Logger logger = LoggerFactory
      .getLogger(GuardarExpedienteElectronicoPredicate.class);

  //private Exception exception = null;

  @Autowired
  @Qualifier("guardaExpedienteElectronicoRegularStrategyImpl")
  private GuardaExpedienteElectronicoStrategy guardaExpedienteElectronicoRegularStrategy;
  
  @Autowired
  @Qualifier("guardaExpedienteElectronicoIndexSolrStrategyImpl")
  private GuardaExpedienteElectronicoStrategy guardaExpedienteElectronicoIndexSolrStrategy;

  @Override
  public boolean evaluate(Object o){
	 return true;
  }
  
  public ExpedienteElectronicoDTO evaluate(ExpedienteElectronicoDTO o) {
    if (logger.isDebugEnabled()) {
      logger.debug("evaluate(o={}) - start", o);
    }
    try {
      if(valid(o)) {
         return getGuardaExpedienteElectronicoRegularStrategy().guardar(o);
      } else {
         return getGuardaExpedienteElectronicoIndexSolrStrategy().guardar(o);
      }
    } catch (Exception e) {
      logger.error("evaluate(Object)", e);
      throw(e);
   }
  }

  public GuardaExpedienteElectronicoStrategy getGuardaExpedienteElectronicoRegularStrategy() {
    return guardaExpedienteElectronicoRegularStrategy;
  }

  public void setGuardaExpedienteElectronicoRegularStrategy(
      GuardaExpedienteElectronicoRegularStrategyImpl guardaExpedienteElectronicoRegularStrategy) {
    this.guardaExpedienteElectronicoRegularStrategy = guardaExpedienteElectronicoRegularStrategy;
  }

  public GuardaExpedienteElectronicoStrategy getGuardaExpedienteElectronicoIndexSolrStrategy() {
    return guardaExpedienteElectronicoIndexSolrStrategy;
  }

  public void setGuardaExpedienteElectronicoIndexSolrStrategy(
      GuardaExpedienteElectronicoIndexSolrStrategyImpl guardaExpedienteElectronicoIndexSolrStrategyImpl) {
    this.guardaExpedienteElectronicoIndexSolrStrategy = guardaExpedienteElectronicoIndexSolrStrategyImpl;
  }
  
  private boolean valid(ExpedienteElectronicoDTO o){
	  return (!"Guarda Temporal".equalsIgnoreCase(o.getEstado())
	          && o.getId() == null
	          && (o.getIdWorkflow() == null
	              || "".equalsIgnoreCase(o.getIdWorkflow().trim()))
	          || "null".equalsIgnoreCase(o.getIdWorkflow().trim()));
  }
  /*
  public Exception getException() {
    return this.exception;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }
  */

}
