/**
 * 
 */
package com.egoveris.te.base.service;

import org.apache.solr.client.solrj.SolrServerException;

import com.egoveris.te.base.model.ExpedienteTrack;
import com.egoveris.te.model.exception.ProcesoFallidoException;

/**
 * The Interface SolrServiceTrack.
 *
 * @author joflores
 * Interfaz que declara los metodos de consulta al solar de Track
 */
public interface SolrServiceTrack {
	
	
	/**
	 * Buscar expediente papel.
	 *
	 * @param letra the letra
	 * @param anio the anio
	 * @param numero the numero
	 * @param reparticionActuacion the reparticion actuacion
	 * @param reparticioUsuario the reparticio usuario
	 * @return the expediente track
	 * @throws SolrServerException the solr server exception
	 * @throws ProcesoFallidoException the proceso fallido exception
	 */
	public ExpedienteTrack buscarExpedientePapel(String letra,Integer anio,Integer numero,String reparticionActuacion,String reparticioUsuario) throws SolrServerException, ProcesoFallidoException;
	
}
