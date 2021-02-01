package com.egoveris.ffdd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.egoveris.ffdd.base.model.Formulario;
import com.egoveris.ffdd.base.model.FormularioSinRelaciones;

public interface FormularioSinRelacionesRepository extends JpaRepository<FormularioSinRelaciones, Integer>, QueryDslPredicateExecutor<FormularioSinRelaciones> {

	List<Formulario> deleteByNombreContains(String string);
	
}
