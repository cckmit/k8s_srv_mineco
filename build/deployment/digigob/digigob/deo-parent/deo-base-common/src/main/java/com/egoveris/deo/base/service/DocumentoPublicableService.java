package com.egoveris.deo.base.service;

public interface DocumentoPublicableService {

	public String getIdPublicableByNombreArchivoTemporal(String nombreArchivoTemporal);
	public void guardarRelacionArchivoTemporalIdPublicable(String nombreArchivoTemporal, String idPublicable);
	
}
