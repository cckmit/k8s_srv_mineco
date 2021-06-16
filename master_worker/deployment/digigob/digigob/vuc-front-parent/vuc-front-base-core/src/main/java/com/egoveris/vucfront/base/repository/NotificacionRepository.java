package com.egoveris.vucfront.base.repository;

import com.egoveris.vucfront.base.model.ExpedienteBase;
import com.egoveris.vucfront.base.model.Notificacion;
import com.egoveris.vucfront.base.model.Persona;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

  List<Notificacion> findByPersonaOrderByFechaNotificacionDesc(Persona persona);

  Notificacion findByExpedienteAndCodSade(ExpedienteBase expediente, String codigoSade);
}