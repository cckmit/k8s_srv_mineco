package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.DocumentoAdjunto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoAdjuntoRepository extends JpaRepository<DocumentoAdjunto, Integer> {

	List<DocumentoAdjunto> findByIdTask(String idTask);
}
