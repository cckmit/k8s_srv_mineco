package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.ArchivoDeTrabajoVisualizacionDTO;


/**
 * The Interface PermisoVisualizacionService.
 *
 * @author eroveda
 * Interface que posee la definicion de metodos 
 * para su implementacion.
 */
public interface PermisoVisualizacionService {
	
	/**
	 * Tiene permiso visualizacion.
	 *
	 * @param usuario the usuario
	 * @param archivo the archivo
	 * @return true, if successful
	 */
	public boolean tienePermisoVisualizacion(Usuario usuario,ArchivoDeTrabajoDTO archivo);
	
	/**
	 * Buscar por rectora.
	 *
	 * @param reparticion the reparticion
	 * @param archivo the archivo
	 * @return true, if successful
	 */
	public boolean buscarPorRectora(String reparticion, ArchivoDeTrabajoDTO archivo);
	
	/**
	 * Buscar por rectora todas.
	 *
	 * @param reparticion the reparticion
	 * @return true, if successful
	 */
	public boolean buscarPorRectoraTodas(String reparticion);
	
	/**
	 * Buscar por reparticion.
	 *
	 * @param archivo the archivo
	 * @param reparticion the reparticion
	 * @return true, if successful
	 */
	public boolean buscarPorReparticion(ArchivoDeTrabajoDTO archivo,String reparticion);
	
	/**
	 * Buscar por usuario.
	 *
	 * @param usuario the usuario
	 * @param archivo the archivo
	 * @return true, if successful
	 */
	public boolean buscarPorUsuario(String usuario, ArchivoDeTrabajoDTO archivo);
	
	/**
	 * Buscar por sector.
	 *
	 * @param sector the sector
	 * @param archivo the archivo
	 * @param reparticion the reparticion
	 * @return true, if successful
	 */
	public boolean buscarPorSector(String sector, ArchivoDeTrabajoDTO archivo,String reparticion);

	/**
	 * Buscar permisos by id archivo.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<ArchivoDeTrabajoVisualizacionDTO> buscarPermisosByIdArchivo(
			Long id);	
	
	
	/**
	 * Guardar permisos.
	 *
	 * @param permisos the permisos
	 */
	public void guardarPermisos( List<ArchivoDeTrabajoVisualizacionDTO> permisos);
}
