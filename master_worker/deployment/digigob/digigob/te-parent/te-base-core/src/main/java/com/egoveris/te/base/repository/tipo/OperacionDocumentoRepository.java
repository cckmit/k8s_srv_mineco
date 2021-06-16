package com.egoveris.te.base.repository.tipo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.OperacionDocumento;

public interface OperacionDocumentoRepository extends JpaRepository<OperacionDocumento, Long> {
	
	@Query("SELECT opDoc FROM OperacionDocumento opDoc WHERE opDoc.operacion.id = :idOperacion")
	public List<OperacionDocumento> getDocumentosOperacion(@Param("idOperacion") Long idOperacion);
	
}
