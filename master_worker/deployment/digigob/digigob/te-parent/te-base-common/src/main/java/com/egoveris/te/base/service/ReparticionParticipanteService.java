package com.egoveris.te.base.service;

import com.egoveris.te.base.model.ReparticionParticipanteDTO;


/**
 * The Interface ReparticionParticipanteService.
 *
 * @author dpadua
 * Interface que posee la definicion de metodos 
 * para su implementacion.
 */
public interface ReparticionParticipanteService {
	
	/**
	 * Grabar reparticion participante.
	 *
	 * @param reparticion the reparticion
	 */
	public void grabarReparticionParticipante(ReparticionParticipanteDTO reparticion);
	
	/**
	 * Eliminar reparticion participante.
	 *
	 * @param reparticion the reparticion
	 */
	public void eliminarReparticionParticipante(ReparticionParticipanteDTO reparticion);

}
