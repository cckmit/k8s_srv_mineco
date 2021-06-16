package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.EstructuraBean;

import java.util.List;

/**
 * @author paGarcia
 */
public interface IEstructuraService {
	
	/**
	 * Este metodo busca todas las estructuras vigentes y activas
	 * @return La lista de estructuras vigentes y activas existentes
	 */
	public List<EstructuraBean> buscarEstructurasVigentesActivas();
	
	/**
	 * Este metodo realiza una busqueda por el ID de estructura entre la lista de estructuras vigentes y activas
	 * @param id de la estructura a buscar 
	 * @return Devuelve la estructura correspondiente al ID de busqueda o null si no se logro encontrar
	 */
	public EstructuraBean buscarEstructuraPorId(int id);
}