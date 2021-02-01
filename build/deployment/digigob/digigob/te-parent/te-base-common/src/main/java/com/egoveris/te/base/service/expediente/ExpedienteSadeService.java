package com.egoveris.te.base.service.expediente;

import java.util.List;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ReparticionDTO;

/**
 * Interfaz que expone un metodo para obtener un expediente de Sade.
 *
 * @author eduavega
 */
public interface ExpedienteSadeService {

	/**
	 * Obtener expediente sade.
	 *
	 * @param tipoDocumento the tipo documento
	 * @param anio the anio
	 * @param numero the numero
	 * @param reparticionUsuario the reparticion usuario
	 * @return the expediente asociado
	 */
	public ExpedienteAsociadoEntDTO obtenerExpedienteSade(String tipoDocumento,
            Integer anio,
            Integer numero, String reparticionUsuario);
	
	/**
	 * Obtener codigo trata SADE.
	 *
	 * @param expedienteAsociado the expediente asociado
	 * @return the string
	 */
	public String obtenerCodigoTrataSADE(ExpedienteAsociadoEntDTO expedienteAsociado);

	/**
	 * Buscar reparticiones.
	 *
	 * @param idEstructura the id estructura
	 * @return the list
	 */
	public List<ReparticionDTO> buscarReparticiones(int idEstructura);
	
	
	
}
