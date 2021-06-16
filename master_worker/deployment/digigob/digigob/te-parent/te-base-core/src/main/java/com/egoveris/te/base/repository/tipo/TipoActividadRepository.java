package com.egoveris.te.base.repository.tipo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.te.base.model.TipoActividad;

public interface TipoActividadRepository extends JpaRepository<TipoActividad, Long> {

	List<TipoActividad> findByNombre(String tipoAct);
	
	 
}
