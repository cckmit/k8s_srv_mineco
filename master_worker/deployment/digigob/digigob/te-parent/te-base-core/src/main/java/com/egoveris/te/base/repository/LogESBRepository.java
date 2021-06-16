package com.egoveris.te.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.LogESB;

public interface LogESBRepository extends JpaRepository<LogESB, Long>{

	@Query("SELECT COUNT(*) FROM LogESB WHERE idExpediente = :idExpediente ")
	public int existLog(@Param("idExpediente") Long idExpediente);
}
