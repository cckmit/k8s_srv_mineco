package com.egoveris.te.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.te.base.model.SubProcesoDesginer;

public interface SubProcesoDesginerRepository extends JpaRepository<SubProcesoDesginer, Long>{
	
    List<SubProcesoDesginer> findByStateFlowAndVersion(String stateFlow, int version);
    
}
