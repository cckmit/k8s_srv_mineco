package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.DobleFactorDTO;

public interface DobleFactorService {
	
	public void guardar(DobleFactorDTO dobleFactor);
	public String obtenerCodigo(String documento);
	public void actualizarEstado(String estado, String documento);
	public void actualizarCodigo(String codigo, String documento);

}
