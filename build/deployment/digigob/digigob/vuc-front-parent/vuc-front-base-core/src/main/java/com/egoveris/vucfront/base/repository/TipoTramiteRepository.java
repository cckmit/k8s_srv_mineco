package com.egoveris.vucfront.base.repository;

import com.egoveris.vucfront.base.model.TipoTramite;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoTramiteRepository extends JpaRepository<TipoTramite, Long> {

  public TipoTramite findByTrata(String trata);
}
