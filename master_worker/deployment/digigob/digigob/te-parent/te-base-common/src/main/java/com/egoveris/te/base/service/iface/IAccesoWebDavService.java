package com.egoveris.te.base.service.iface;

import java.io.BufferedInputStream;

import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;

/**
 * The Interface IAccesoWebDavService.
 */
public interface IAccesoWebDavService {
	
	/**
	 * Visualizar documento.
	 *
	 * @param path the path
	 * @return the buffered input stream
	 */
	public BufferedInputStream  visualizarDocumento(String path);
	
	/**
	 * Obtener archivo de trabajo web dav.
	 *
	 * @param path the path
	 * @param nombre the nombre
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public byte[] obtenerArchivoDeTrabajoWebDav(String path, String nombre) throws Exception;
	
	/**
	 * Obtener documento.
	 *
	 * @param numeroDocumento the numero documento
	 * @return the file as stream connection web dav
	 */
	public FileAsStreamConnectionWebDav obtenerDocumento(String numeroDocumento);

}
