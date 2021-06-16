package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface GestionArchivosEmbebidosWebDavService {

	
	
	public void subirArchivoEmbebidoTemporalWebDav(ArchivoEmbebidoDTO archivoEmbebido, byte[] contenido) throws ApplicationException;

	public byte[] obtenerArchivosEmbebidosWebDav(String pathRelativo,
			String nombreArchivo);
	
	public void borrarArchivoEmbebidoWebDav(String pathRelativo,
			String nombreArchivo);
	public String crearEstructuraArchivosEmbebidos(String pathRelativo);
	public String obtenerUrlTemporalArchivoEmbebido(String nombreArchivo);
}
