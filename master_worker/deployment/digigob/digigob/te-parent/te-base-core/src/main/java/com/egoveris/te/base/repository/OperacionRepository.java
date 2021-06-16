package com.egoveris.te.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.Operacion;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.SubProcesoOperacion;

public interface OperacionRepository extends  JpaRepository<Operacion, Long>{
	
	public List<Operacion> findByIdSectorInternoOrderByFechaInicioDesc(Integer idSector); 
	public Operacion findById(Long id);
	public Operacion findByNumOficial(String numOficial);
	public List<Operacion> findByIdSectorInternoAndNumOficial(Integer idSector, String numOficial); 
	public List<Operacion> findByIdSectorInternoAndEstadoOperacion(Integer idSector, String estadoOpe); 
	public List<Operacion> findByIdSectorInternoAndNumOficialAndEstadoOperacion(Integer idSector, String numOficial, String estadoOpe); 
	
	@Query("select s from SubProcesoOperacion s join s.pk  p where p.idOperacion = :idOperacion")
	public List<SubProcesoOperacion> findAllSubProcess(@Param("idOperacion") Long idOperacion);
//	public Operacion findByRuce(String ruce); 
	
}
