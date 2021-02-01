package com.egoveris.te.base.repository.tipo;

import com.egoveris.te.base.model.tipo.TipoReserva;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoReservaRepository extends JpaRepository<TipoReserva, Long> {  

	
	TipoReserva findByTipoReserva(String reserva);
	
	TipoReserva findById(Long id);
	
	 
}
