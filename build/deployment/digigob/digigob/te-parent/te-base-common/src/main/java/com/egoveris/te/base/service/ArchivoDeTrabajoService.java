package com.egoveris.te.base.service;

import java.io.File;
import java.util.List;

import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.TipoArchivoTrabajoDTO;


/**
 * Interface ArchivoDeTrabajoService.
 *
 * @author eduavega 
 * Interface que posee la definicion de metodos 
 * para su implementacion.
 */

public interface ArchivoDeTrabajoService {
	
	/**
	 * Grabar archivo de trabajo.
	 *
	 * @param archivo the archivo
	 */
	public void grabarArchivoDeTrabajo(ArchivoDeTrabajoDTO archivo);
	
	/**
	 * Eliminar achivo de trabajo.
	 *
	 * @param archivo the archivo
	 */
	public void eliminarAchivoDeTrabajo(ArchivoDeTrabajoDTO archivo);
	
	/**
	 * Subir archivo de trabajo.
	 *
	 * @param archivo the archivo
	 * @param nombreSpace the nombre space
	 * @return the string
	 */
	public String subirArchivoDeTrabajo(ArchivoDeTrabajoDTO archivo,String nombreSpace);
	
	/**
	 * Subir archivo de trabajo por webDavService.
	 *
	 * @param archivo the archivo
	 * @param file the file
	 * @param nombreSpace the nombre space
	 */
	public void subirArchivoDeTrabajoPorWebDav(ArchivoDeTrabajoDTO archivo,File file,String nombreSpace);
	
	/**
	 * Eliminar archivo de trabajo por web dav.
	 *
	 * @param nombreArchivo the nombre archivo
	 * @param nombreSpace the nombre space
	 */
	public void eliminarArchivoDeTrabajoPorWebDav(String nombreArchivo, String nombreSpace);
	
	/**
	 * Mostrar todos tipo archivo trabajo.
	 *
	 * @return the list
	 */
	public List<TipoArchivoTrabajoDTO> mostrarTodosTipoArchivoTrabajo();
	
	/**
	 * Buscar tipo archivo trabajo por nombre.
	 *
	 * @param nombreTipoArchivo the nombre tipo archivo
	 * @return the tipo archivo trabajo
	 */
	public TipoArchivoTrabajoDTO buscarTipoArchivoTrabajoPorNombre(String nombreTipoArchivo);
}
