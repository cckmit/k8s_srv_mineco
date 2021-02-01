package com.egoveris.deo.base.service;



import com.egoveris.deo.model.model.HistorialDTO;

import java.util.List;



public interface IHistorialProcesosService {

	/**
	 * Obtiene la lista de todas las tareas que conforman al momento el proceso cuyo identificador se pasó como parámetro.
	 * 
	 * @param username
	 *            executionId sobre el cual obtener las tareas
	 * @return Una lista de Historial con todas las tareas del proceso o una lista vacía si tuviera aún.
	 */
	
	public List<HistorialDTO> getHistorial(String workflowOrigen);

}
