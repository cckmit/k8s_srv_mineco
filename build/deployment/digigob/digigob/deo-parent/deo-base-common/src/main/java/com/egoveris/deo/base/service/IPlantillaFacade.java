package com.egoveris.deo.base.service;



import com.egoveris.deo.model.model.PlantillaDTO;

import java.util.List;

public interface IPlantillaFacade {
	
	/**
	 * Crea el registro de la Plantilla y el registro UsuarioPlantilla con el que se relaciona.
	 * Agrega la fecha de creación a la plantilla.
	 * @param usuario
	 * @param plantilla
	 */
	public void crear(String usuario, PlantillaDTO plantilla);
	
	/**
	 * Actualiza la plantilla.
	 * Cambia la fecha de modificación de la plantilla.
	 * @param plantilla
	 */
	public void actualizar(PlantillaDTO plantilla);
	
	/**
	 * Elimina el registro de la Plantilla y los registros UsuarioPlantilla con los que se relaciona
	 * @param plantilla
	 */
	public void eliminar(PlantillaDTO plantilla);
	
	/**
	 * Devuelve las plantillas de un usuario sin el contenido de las plantillas.
	 * @param usuario
	 * @return
	 */
	public List<PlantillaDTO> buscarPlantillaPorUsuarioPlantilla(String usuario);
	
	/**
	 * 
	 * @param idPlantilla
	 * @return
	 */
	public PlantillaDTO buscarPlantillaPorId(Integer idPlantilla);
   
}
