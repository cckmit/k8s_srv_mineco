package com.egoveris.ffdd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.ffdd.base.model.DynamicComponentField;
import com.egoveris.ffdd.base.model.Item;

public interface DynamicComponenteRepository extends JpaRepository<DynamicComponentField, Integer>{

	@Query("SELECT d FROM Item d WHERE d.componente.nombre = :componentName ORDER BY d.orden")
	List<Item> findMultivaloresComponente(@Param("componentName") final String componentName);
}


