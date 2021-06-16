package com.egoveris.te.base.dao;

import com.egoveris.te.base.model.EstructuraBean;

import java.util.List;


public interface IEstructuraDAO {

	

	/**
	 * Este metodo busca todas las estructuras vigentes y activas
	 * @return La lista de estructuras vigentes y activas existentes
	 */
	public List<EstructuraBean> buscarEstructurasVigentesActivas();
	
	
	
}
