package com.egoveris.te.base.service.iface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DatosDeBusqueda;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoIndex;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.TrataDTO;

/**
 * The Interface ISolrService.
 */
public interface ISolrService {

	/**
	 * Obtener expedientes electronicos solr.
	 *
	 * @param queryString the query string
	 * @return the list
	 * @throws NegocioException the negocio exception
	 * @throws ar.gob.gcaba.co.services.Exceptions.NegocioException 
	 */
	public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(String queryString) throws NegocioException;
	
	/**
	 * Obtener expedientes electronicos solr.
	 *
	 * @param queryString the query string
	 * @param todos the todos
	 * @return the list
	 * @throws NegocioException the negocio exception
	 * @throws ar.gob.gcaba.co.services.Exceptions.NegocioException 
	 */
	public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(String queryString, boolean todos) throws NegocioException;
	
		
	/**
	 * Obtener expedientes electronicos solr.
	 *
	 * @param username the username
	 * @param reparticion the reparticion
	 * @param trata the trata
	 * @param expedienteMetaDataList the expediente meta data list
	 * @param metaDatos the meta datos
	 * @param desde the desde
	 * @param hasta the hasta
	 * @param tipoDocumento the tipo documento
	 * @param numeroDocumento the numero documento
	 * @param cuitCuil the cuit cuil
	 * @param estado the estado
	 * @return the list
	 * @throws NegocioException the negocio exception
	 * @throws ar.gob.gcaba.co.services.Exceptions.NegocioException 
	 */
	public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(
			String username, String reparticion, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda>metaDatos, Date desde, Date hasta, String tipoDocumento,
			String numeroDocumento, String cuitCuil,String estado) throws NegocioException;
	
	/**
	 * Obtener expedientes electronicos solr.
	 *
	 * @param tipoExpediente the tipo expediente
	 * @param anio the anio
	 * @param numero the numero
	 * @param reparticion the reparticion
	 * @return the expediente electronico index
	 * @throws NegocioException the negocio exception
	 * @throws ar.gob.gcaba.co.services.Exceptions.NegocioException 
	 */
	public ExpedienteElectronicoIndex obtenerExpedientesElectronicosSolr(String tipoExpediente, Integer anio, Integer numero,
			String reparticion) throws NegocioException;
	
	/**
	 * Obtener expedientes electronicos usuario tramitacion solr.
	 *
	 * @param username the username
	 * @param trata the trata
	 * @param expedienteMetaDataList the expediente meta data list
	 * @param metaDatos the meta datos
	 * @param desde the desde
	 * @param hasta the hasta
	 * @param tipoDocumento the tipo documento
	 * @param numeroDocumento the numero documento
	 * @param cuitCuil the cuit cuil
	 * @param estado the estado
	 * @return the list
	 * @throws NegocioException the negocio exception
	 * @throws ar.gob.gcaba.co.services.Exceptions.NegocioException 
	 */
	public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosUsuarioTramitacionSolr(
			String username, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda> metaDatos, Date desde, Date hasta, String tipoDocumento,
			String numeroDocumento, String cuitCuil, String estado) throws NegocioException;
	
	
	/**
	 * Indexar.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @throws NegocioException the negocio exception
	 * @throws ar.gob.gcaba.co.services.Exceptions.NegocioException 
	 */
	public void indexar(ExpedienteElectronicoDTO expedienteElectronico) throws NegocioException;
	
	/**
	 * Clear index.
	 */
	public void clearIndex();
	
	/**
	 * Indexar formulario controlado.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param camposFC the campos FC
	 * @throws NegocioException the negocio exception
	 * @throws ar.gob.gcaba.co.services.Exceptions.NegocioException 
	 */
	public void indexarFormularioControlado(ExpedienteElectronicoDTO expedienteElectronico, Map<String, Object> camposFC) throws NegocioException;
	
	/**
	 * Obtener expedientes electronicos solr.
	 *
	 * @param desde the desde
	 * @param hasta the hasta
	 * @param domicilio the domicilio
	 * @param piso the piso
	 * @param departamento the departamento
	 * @param codigoPostal the codigo postal
	 * @param estado the estado
	 * @return the list
	 * @throws NegocioException the negocio exception
	 * @throws ar.gob.gcaba.co.services.Exceptions.NegocioException 
	 */
	public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr( Date desde, Date hasta, String domicilio, String piso, String departamento, String codigoPostal, String estado) throws NegocioException;
		
	/**
	 * Obtener expedientes electronicos solr.
	 *
	 * @param username the username
	 * @param reparticion the reparticion
	 * @param trata the trata
	 * @param expedienteMetaDataList the expediente meta data list
	 * @param desde the desde
	 * @param hasta the hasta
	 * @param tipoDocumento the tipo documento
	 * @param numeroDocumento the numero documento
	 * @param cuitCuil the cuit cuil
	 * @param domicilio the domicilio
	 * @param piso the piso
	 * @param departamento the departamento
	 * @param codigoPostal the codigo postal
	 * @param estado the estado
	 * @return the list
	 * @throws NegocioException the negocio exception
	 */
	public List<ExpedienteElectronicoIndex> obtenerExpedientesElectronicosSolr(
			String username, String reparticion, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, Date desde, Date hasta, String tipoDocumento,
			String numeroDocumento, String cuitCuil,String domicilio, String piso, String departamento, String codigoPostal,String estado) throws NegocioException;
	
	
	 /**
 	 * Execute delta import.
 	 */
 	public void executeDeltaImport();
	
}
