package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.SolicitudExpediente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudExpedienteRepository extends JpaRepository<SolicitudExpediente, Long> {
  
	SolicitudExpediente findById(Long id); 
	
}
