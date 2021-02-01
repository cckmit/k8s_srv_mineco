package com.egoveris.te.base.repository.tipo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.tipo.TipoOperacionDocumento;

public interface TipoOperacionDocRepository extends JpaRepository<TipoOperacionDocumento, Long> {

	@Query("SELECT tOpDoc FROM TipoOperacionDocumento tOpDoc WHERE tOpDoc.tipoOperacion.id = :idTipoOperacion")
	public List<TipoOperacionDocumento> getTiposDocumentosByTipoOperacion(
			@Param("idTipoOperacion") Long idTipoOperacion);

}
