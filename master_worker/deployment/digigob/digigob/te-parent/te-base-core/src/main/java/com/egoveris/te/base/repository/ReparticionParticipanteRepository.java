package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.ReparticionParticipante;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dpadua
 * Interface con los metodos para la persistencia de las Reparticiones Acumuladas
 */
	
public interface ReparticionParticipanteRepository extends  JpaRepository<ReparticionParticipante, Long>{
	 
	
}
