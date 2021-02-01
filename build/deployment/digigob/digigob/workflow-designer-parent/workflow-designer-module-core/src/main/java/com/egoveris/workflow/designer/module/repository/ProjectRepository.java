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

import com.egoveris.workflow.designer.module.model.ProjectDesginer;
import com.egoveris.workflow.designer.module.util.ConstantsQuery;

@Transactional(propagation = Propagation.MANDATORY)
public interface ProjectRepository extends JpaRepository<ProjectDesginer, Long> {

	 public List<ProjectDesginer> findAllByTypeWorkFlow(String type);
	 
	 
	 @Query(ConstantsQuery.UPDATE_PROJECT)
	 @Modifying
	 public void changeNameProject(@Param("projectName") String projectName, @Param("projectNameOld") String projectNameOld);
}
