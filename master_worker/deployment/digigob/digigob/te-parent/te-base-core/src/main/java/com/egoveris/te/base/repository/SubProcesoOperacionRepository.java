package com.egoveris.te.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.SubProcesoOperacion;

public interface SubProcesoOperacionRepository extends JpaRepository<SubProcesoOperacion, Long>{
	
	public List<SubProcesoOperacion> findByPkIdOperacion(Long idOperacion);
	
	@Query("SELECT s FROM SubProcesoOperacion s JOIN s.expediente e WHERE e.id = :idExpediente")
	public SubProcesoOperacion findByExpedienteElectronico(@Param("idExpediente") Long idExpediente);
	
	@Query("SELECT s FROM SubProcesoOperacion s JOIN s.operacion o JOIN s.subproceso e WHERE o.id =:idOperation"
			+ " and e.id = :idSubprocess")
	public SubProcesoOperacion getSubProcessByOperation(@Param("idSubprocess") Long idSubprocess
			, @Param("idOperation") Long idOperation);
	
}
