package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.NumeracionEspecialAuditoria;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NumeroEspecialAuditoriaRepository
    extends JpaRepository<NumeracionEspecialAuditoria, Integer> {

}
