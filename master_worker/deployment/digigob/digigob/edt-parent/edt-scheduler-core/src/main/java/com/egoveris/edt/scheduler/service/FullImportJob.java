package com.egoveris.edt.scheduler.service;

import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class FullImportJob {

  @Autowired
  private IUsuarioService usuarioService;
  private static final Logger logger = LoggerFactory.getLogger(FullImportJob.class);

  @Scheduled(cron = "${fullimportUsuarios.cronExpression}")
  public void fullImportUsuarios() {
    logger.info("INICIO JOB FULLIMPORT SOLR");
    usuarioService.fullImportUsuarios();
    logger.info("FIN JOB FULLIMPORT SOLR");
  }

}