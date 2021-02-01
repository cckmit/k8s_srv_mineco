package com.egoveris.deo.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.base.model.DocumentoPublicable;
import com.egoveris.deo.base.repository.DocumentoPublicableRepository;
import com.egoveris.deo.base.service.DocumentoPublicableService;

@Service
public class DocumentoPublicableServiceImpl implements DocumentoPublicableService {

	@Autowired
	private DocumentoPublicableRepository repository;

	@Override
	public String getIdPublicableByNombreArchivoTemporal(String nombreArchivoTemporal) {
		String idPublicable = null;
		DocumentoPublicable reg = this.repository.findByNombreArchivoTemporal(nombreArchivoTemporal);
		if (reg != null) {
			idPublicable = reg.getIdPublicable();
		}
		return idPublicable;
	}

	@Override
	public void guardarRelacionArchivoTemporalIdPublicable(String nombreArchivoTemporal, String idPublicable) {
		DocumentoPublicable reg = new DocumentoPublicable();
		reg.setNombreArchivoTemporal(nombreArchivoTemporal);
		reg.setIdPublicable(idPublicable);
		this.repository.save(reg);
	}
	
}
