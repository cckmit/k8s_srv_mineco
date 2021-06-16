package com.egoveris.te.base.repository.trata;

import com.egoveris.te.base.model.trata.TrataAuditoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrataAuditoriaRepository extends JpaRepository<TrataAuditoria, Long> {

  List<TrataAuditoria> findByCodigoTrata(String codigoTrata);

}