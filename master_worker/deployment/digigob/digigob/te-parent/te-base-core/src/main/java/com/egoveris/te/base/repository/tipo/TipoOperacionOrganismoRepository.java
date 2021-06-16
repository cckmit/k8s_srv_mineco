package com.egoveris.te.base.repository.tipo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.tipo.TipoOperacionOrganismo;

public interface TipoOperacionOrganismoRepository extends JpaRepository<TipoOperacionOrganismo, Long> {

	@Query("SELECT tOpOrg FROM TipoOperacionOrganismo tOpOrg WHERE tOpOrg.tipoOperacion.id = :idTipoOperacion")
	public List<TipoOperacionOrganismo> getOrganismosByTipoOperacion(@Param("idTipoOperacion") Long idTipoOperacion);
}
