package com.egoveris.vucfront.base.repository;

import com.egoveris.vucfront.base.model.EstadoTarea;
import com.egoveris.vucfront.base.model.Persona;
import com.egoveris.vucfront.base.model.Tarea;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

  List<Tarea> findByEstadoAndExpedienteBase_PersonaOrderByFechaDesc(EstadoTarea estado, Persona persona);

}