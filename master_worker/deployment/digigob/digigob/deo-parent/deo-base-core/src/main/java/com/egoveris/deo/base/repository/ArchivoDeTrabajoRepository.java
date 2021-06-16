package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.ArchivoDeTrabajo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoDeTrabajoRepository extends JpaRepository<ArchivoDeTrabajo, Integer> {

	List<ArchivoDeTrabajo> findByIdTask(String idTask);
}
