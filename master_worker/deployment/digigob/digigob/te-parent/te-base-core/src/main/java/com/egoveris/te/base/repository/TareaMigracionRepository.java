package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.TareaMigracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaMigracionRepository extends  JpaRepository<TareaMigracion, Long>{
 
	List<TareaMigracion> findByTarea(String token); 
	   
}
