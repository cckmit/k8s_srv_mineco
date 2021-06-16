package com.egoveris.vucfront.base.repository;

import com.egoveris.vucfront.base.model.Persona;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
  
  Persona findByCuit(String cuit);

  @Query("select p From Persona p where p.estado = 'PENDIENTE'")
  public List<Persona> findPersonasPendientes();
  
}
