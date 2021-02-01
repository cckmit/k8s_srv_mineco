package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;

import java.util.List;



public interface ArchivoDeTrabajoService {
	/**
	 * @author eumolina 
	 * Se persiste un ArchivoDeTrabajo
	 * @param archivo
	 */
	public Integer grabarArchivoDeTrabajo(ArchivoDeTrabajoDTO archivo);
	/**
	 * Permite almacenar los archivos temporales en una carpeta temporal 
	 * en el servidor WebDav.
	 * @throws Exception 
	 */
	public void subirArchivoDeTrabajoTemporal(ArchivoDeTrabajoDTO archivoDeTrabajo);
	/**
	 * Permite eliminar un archivo temporal del servidor WebDav
	 * @param nombreArchivo
	 * @throws Exception
	 */
	public void borrarArchivoDeTrabajoTemporal(String pathRelativo, String nombreArchivo);
	
	
	/**
	 * Se obtiene un lista de archivos de trabajo segun un workFlowId
	 * @param workflowId
	 * @return
	 */
	public List<ArchivoDeTrabajoDTO> buscarArchivosDeTrabajoPorProceso(String workflowId);

	public String subirArchivoDeTrabajoWebDav(String numeroSade, byte[] contenido,String nombreArchivo);
	
	public void eliminarAchivoDeTrabajo(ArchivoDeTrabajoDTO archivo);
	
	/**
	 * 
	 * @param pathRelativo
	 * @param nombreArchivo
	 * @return
	 * @throws Exception
	 */
	public byte[] obtenerArchivoDeTrabajoTemporalWebDav(String pathRelativo, String nombreArchivo);
	
	/**
	 * 
	 * @param pathRelativo
	 * @param nombreArchivo
	 * @return
	 * @throws Exception
	 */
	public byte[] obtenerArchivoDeTrabajoWebDav(String pathRelativo,String nombreArchivo);
	
}	