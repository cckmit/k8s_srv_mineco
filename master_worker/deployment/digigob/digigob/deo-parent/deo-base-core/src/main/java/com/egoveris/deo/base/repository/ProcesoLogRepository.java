package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.ProcesoLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcesoLogRepository extends JpaRepository<ProcesoLog, Integer> {

	List<ProcesoLog> findByProcesoAndEstado(String proceso, String estado);
}
