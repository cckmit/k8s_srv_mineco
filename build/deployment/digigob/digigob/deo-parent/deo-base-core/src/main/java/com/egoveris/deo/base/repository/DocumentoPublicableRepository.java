package com.egoveris.deo.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.deo.base.model.DocumentoPublicable;

public interface DocumentoPublicableRepository extends JpaRepository<DocumentoPublicable, Long>{

	public DocumentoPublicable findByNombreArchivoTemporal(String nombreArchivoTemporal);
	
}
