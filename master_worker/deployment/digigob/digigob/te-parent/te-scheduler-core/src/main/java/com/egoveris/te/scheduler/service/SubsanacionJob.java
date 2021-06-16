package com.egoveris.te.scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;

@Component
public class SubsanacionJob {

	private static final Logger logger = LoggerFactory.getLogger(SubsanacionJob.class);

	/** The expediente electronico service. */
	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;

	/**
	 * Guardado automatico de expedientes a guarda temporal.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Scheduled(cron = "${subsanacion.cronExpression}")
	public void changeExpedientesState() throws InterruptedException {
//		logger.info("**** INICIO JOB SUBSANACION ****");
		//expedienteElectronicoService.guardadoTemporalExpedientes();
//		logger.info("**** FIN JOB SUBSANACION ****");
	}
}
