package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.HistorialOperacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialOperacionRepository extends JpaRepository<HistorialOperacion, Long> {
  
  List<HistorialOperacion> findByIdExpediente(Long idExpediente);
  
  

}
