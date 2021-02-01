package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.model.EstructuraBean;

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
