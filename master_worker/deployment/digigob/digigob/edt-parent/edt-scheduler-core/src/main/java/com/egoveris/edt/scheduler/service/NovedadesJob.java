package com.egoveris.edt.scheduler.service;

import com.egoveris.edt.base.service.novedad.INovedadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NovedadesJob {

  @Autowired
  private INovedadService novedadService;

 private static final Logger logger = LoggerFactory.getLogger(NovedadesJob.class);

 @Scheduled(cron = "${novedades.cronExpression}")
 public void changeNovedadesState() {
    logger.info("**** INICIO JOB NOVEDADES ****");
    novedadService.cambiarEstadoJob();
    logger.info("**** FIN JOB NOVEDADES ****");
  }
}