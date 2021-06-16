package com.egoveris.te.base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.SubProceso;

@Transactional(propagation = Propagation.MANDATORY)
public interface SubProcesoRepository extends JpaRepository<SubProceso, Long> {

  @Modifying
  @Query("delete from SubProceso where stateFlow = :stateFlow and version = :version")
  public void deleteSubProcess(@Param("stateFlow") String stateFlow, @Param("version") Integer version);

  @Query("from SubProceso where stateFlow = :stateFlow and stateName = :stateName and "
      + " version = :version and startType = :startType and (versionProcedure = :versionProcedure or :versionProcedure is null or :versionProcedure = '' )")
  public List<SubProceso> findSubProcess(@Param("stateFlow") String stateFlow, @Param("stateName") String stateName,
      @Param("version") Integer version, @Param("startType") String startType,
      @Param("versionProcedure") Integer versionProcedure);

  @Query("from SubProceso where stateFlow = :stateFlow and stateName = :stateName and "
      + " version = :version and startType = :startType and versionProcedure  is null ")
  public List<SubProceso> findSubProcessWhitOutVersionP(@Param("stateFlow") String stateFlow,
      @Param("stateName") String stateName, @Param("version") Integer version, @Param("startType") String startType);

  @Query("select  s from SubProceso s join s.tramite t where t.codigoTrata = :nameProcedure")
  public SubProceso findByNameTrata(@Param("nameProcedure") String nameProcedure);

  @Query("select  s from SubProceso s where s.stateFlow = :stateflow order by s.version desc ")
  public Page<SubProceso> findByNameSubprocess(@Param("stateflow") String stateflow, Pageable pageable);

  @Query("select versionProcedure from SubProceso where stateFlow = :stateFlow "
      + " group by versionProcedure  order by versionProcedure desc")
  public List<Integer> findLastVersionOfProcedure(@Param("stateFlow") String stateFlow);

  public List<SubProceso> findByStateFlowAndVersion(String stateFlow, int version);

  public SubProceso findById(Long id);

  // Added for product request
  @Query("SELECT s FROM SubProceso s JOIN s.tramite t WHERE s.stateFlow = :stateFlow "
      + "AND s.stateName = :stateName AND s.startType = :startType AND t.codigoTrata = :nameProcedure")
  public List<SubProceso> findSolicitudSubprocess(@Param("stateFlow") String stateFlow,
      @Param("stateName") String stateName, @Param("startType") String startType,
      @Param("nameProcedure") String nameProcedure);

}
