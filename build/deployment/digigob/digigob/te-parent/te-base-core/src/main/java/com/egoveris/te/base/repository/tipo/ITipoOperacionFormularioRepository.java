package com.egoveris.te.base.repository.tipo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.te.base.model.tipo.TipoOperacionFormulario;

public interface ITipoOperacionFormularioRepository extends JpaRepository<TipoOperacionFormulario, Long> {
	
	/**
	 * Obtiene los ids de formularios dinamicos asociados a un tipo de operacion
	 * 
	 * @param idTipoOperacion Identificador tipo operacion
	 * @return
	 */ 
	List<TipoOperacionFormulario> findByIdTipoOperacion(Long idTipoOperacion); 
	
}
