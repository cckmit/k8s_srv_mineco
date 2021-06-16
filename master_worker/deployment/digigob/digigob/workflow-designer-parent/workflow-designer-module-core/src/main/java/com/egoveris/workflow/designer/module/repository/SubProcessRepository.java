/**
 * 
 */
package com.egoveris.workflow.designer.module.repository;

import java.util.List;


/*
 * #%L
 * j-everis persistence: JPA (Hibernate provider) integration tests
 * %%
 * Copyright (C) 2016 everis Spain, S.L.U.
 * %%
 * All rights reserved.
 * Todos los derechos reservados.
 * #L%
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.egoveris.workflow.designer.module.model.SubProcess;
import com.egoveris.workflow.designer.module.util.ConstantsQuery;

@Transactional(propagation = Propagation.MANDATORY)
public interface SubProcessRepository extends JpaRepository<SubProcess, Long> {

	public List<SubProcess> findSubProcessByStateFlow(String stateFlow);
	
	@Query(ConstantsQuery.FIND_BY_STATE_NAME_VERSION)
	public List<SubProcess> findAllByStateNameVersion(@Param("stateFlow") String stateFlow, 
			@Param("stateName") String stateName, @Param("version") int version);
	
	
	@Query(ConstantsQuery.UPDATE_SUBPROCESS_VERSION)
	@Modifying
	public void updateSubProcessVersion(@Param("projectName") String projectName, @Param("projectVersion") int projectVersion);

	
	@Query(ConstantsQuery.UPDATE_SUBPROCESS_PROJECT)
	@Modifying
	public void updateSubProcessProject(@Param("projectName") String projectName, @Param("projectNameOld") String projectNameOld , 
							@Param("projectVersion") int projectVersion);
	
	
	@Query(ConstantsQuery.UPDATE_SUBPROCESS_PROJECT_PROCEDURE)
	@Modifying
	public void updateSubProcessVersionProcedure(@Param("projectName") String projectName, 
			@Param("versionProcedure") int versionProcedure);
	
	
	@Query(ConstantsQuery.FIND_LAST_VERSION_BY_PROJECTNAME)
	public List<Integer> getLastVersionProcedure(@Param("projectName") String projectName, Pageable pageable);
	
	
}
