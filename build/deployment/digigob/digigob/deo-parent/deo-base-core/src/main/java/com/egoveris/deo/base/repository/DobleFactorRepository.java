package com.egoveris.deo.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.deo.base.model.DobleFactor;

public interface DobleFactorRepository extends JpaRepository<DobleFactor, Integer>{

	@Query("select df.codigo from DobleFactor df where df.documento = ?1")
	String obtenerCodigo(String documento);
	
	@Modifying
	@Query("update DobleFactor set estado = ?1 where documento = ?2")
	void actualizarEstado(String estado, String documento);
	
	@Modifying
	@Query("update DobleFactor set codigo = ?1 where documento = ?2")
	void actualizarCodigo(String estado, String documento);
}
