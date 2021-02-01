package com.egoveris.ffdd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.ffdd.base.model.Componente;
import com.egoveris.ffdd.base.model.Formulario;
import com.egoveris.ffdd.base.model.FormularioComponente;

public interface FormularioComponenteRepository extends JpaRepository<FormularioComponente, Integer> {
	
	List<FormularioComponente> findByFormulario(Formulario formulario);

	FormularioComponente findByComponente(Componente componente);
	
}
