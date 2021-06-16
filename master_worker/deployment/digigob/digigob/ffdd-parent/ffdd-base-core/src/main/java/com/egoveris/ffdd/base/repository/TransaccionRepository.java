package com.egoveris.ffdd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.egoveris.ffdd.base.model.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer>, QueryDslPredicateExecutor<Transaccion> {

	Transaccion findByUuid(Integer uuid);
	
	List<Transaccion> findByNombreFormulario(String nombreFormulario);
}
