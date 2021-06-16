package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.TipoReserva;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoReservaRepository extends JpaRepository<TipoReserva, Integer> {

  TipoReserva findById(Integer id);

  TipoReserva findByReserva(String reserva);
}
