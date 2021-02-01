package com.egoveris.deo.base.service.impl;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.base.model.DobleFactor;
import com.egoveris.deo.base.repository.DobleFactorRepository;
import com.egoveris.deo.base.service.DobleFactorService;
import com.egoveris.deo.model.model.DobleFactorDTO;

@Service
@Transactional
public class DobleFactorServiceImpl implements DobleFactorService{

	@Autowired
	private DobleFactorRepository dobleFactorRepo;
	private DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public void guardar(DobleFactorDTO dobleFactor) {
		DobleFactor df = this.mapper.map(dobleFactor, DobleFactor.class);
		this.dobleFactorRepo.save(df);
	}

	@Override
	public String obtenerCodigo(String documento) {
		return this.dobleFactorRepo.obtenerCodigo(documento);
	}

	@Override
	public void actualizarEstado(String estado, String documento) {
		this.dobleFactorRepo.actualizarEstado(estado, documento);
	}
	
	@Override
	public void actualizarCodigo(String codigo, String documento) {
		this.dobleFactorRepo.actualizarCodigo(codigo, documento);
	}

}
