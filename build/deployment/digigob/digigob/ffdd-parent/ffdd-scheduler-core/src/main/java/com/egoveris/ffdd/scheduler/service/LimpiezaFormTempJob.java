package com.egoveris.ffdd.scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.egoveris.ffdd.base.service.IFormularioService;

@Component
public class LimpiezaFormTempJob {

	@Autowired
	private IFormularioService formularioService;
	
	private static final Logger logger = LoggerFactory.getLogger(LimpiezaFormTempJob.class);
	
	@Scheduled(cron = "${job.limpiezaTemoralesFFDD}")
	public void cleanTempForms(){
		logger.info("INICIO JOB: LimpiezaFormTempJob");
		formularioService.eliminarFormulariosTemporales();
		logger.info("FIN JOB: LimpiezaFormTempJob");
	}
}