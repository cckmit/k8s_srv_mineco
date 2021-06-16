package com.egoveris.te.base.service;

import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.te.base.model.BuzonBean;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Grupo;

/**
 * Interfaz para encapsular la transacccion de envioPase y la creacion del workflow.
 *
 * @author eroveda
 * g
 */
public interface ControlTransaccionService {
	
	/**
	 * Save.
	 *
	 * @param processEngine the process engine
	 * @param workingTask the working task
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param estado the estado
	 * @param motivo the motivo
	 * @param detalles the detalles
	 * @throws Exception the exception
	 */
	public void save(ProcessEngine processEngine, Task workingTask,ExpedienteElectronicoDTO expedienteElectronico,String loggedUsername,String estado, String motivo,Map<String, String>  detalles) throws DataAccessLayerException;
	
	/**
	 * Save.
	 *
	 * @param buzonBean the buzon bean
	 * @param roundRobin the round robin
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(BuzonBean buzonBean,Map<String,Grupo> roundRobin) throws DataAccessLayerException;
}
