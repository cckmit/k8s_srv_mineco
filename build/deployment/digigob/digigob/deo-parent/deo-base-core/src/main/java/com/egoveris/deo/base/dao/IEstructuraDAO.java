package com.egoveris.deo.base.dao;

import com.egoveris.deo.model.model.EstructuraBean;

import java.util.List;

/**
 * @author paGarcia
 */
public interface IEstructuraDAO {
	
	/**
	 * Este metodo busca todas las estructuras vigentes y activas
	 * @return La lista de estructuras vigentes y activas existentes
	 */
	public List<EstructuraBean> buscarEstructurasVigentesActivas();
}
