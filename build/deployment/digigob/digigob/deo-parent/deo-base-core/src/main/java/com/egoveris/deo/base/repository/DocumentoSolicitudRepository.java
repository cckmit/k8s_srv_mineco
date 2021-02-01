package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.DocumentoSolicitud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoSolicitudRepository extends JpaRepository<DocumentoSolicitud, Integer> {

  DocumentoSolicitud findByWorkflowId(String workflowId);
  
  DocumentoSolicitud findByNumeroSade(String numeroSade);
}
