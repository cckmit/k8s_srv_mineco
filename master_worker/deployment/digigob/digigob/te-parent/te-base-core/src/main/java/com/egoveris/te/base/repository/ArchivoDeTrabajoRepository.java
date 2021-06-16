package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.ArchivoDeTrabajo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author eduavega Interface con los metodos para la persistencia de un Archivo
 *         De Trabajo
 */
public interface ArchivoDeTrabajoRepository extends JpaRepository<ArchivoDeTrabajo, Long> {

  List<ArchivoDeTrabajo> findByIdTaskLike(String workflowId);

}
