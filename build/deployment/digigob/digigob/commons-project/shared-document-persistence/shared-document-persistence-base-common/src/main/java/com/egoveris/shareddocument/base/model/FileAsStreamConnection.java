/**
 * 
 */
package com.egoveris.shareddocument.base.model;

import java.io.BufferedInputStream;

/**
 * @author pfolgar
 *
 */
public abstract class FileAsStreamConnection {
	
	public FileAsStreamConnection(BufferedInputStream fileAsStream) {
		super();
		this.fileAsStream = fileAsStream;
	}

	public abstract void closeConnection();
	
	private BufferedInputStream fileAsStream;

	/**
	 * El metodo obtiene una instancia de un bufferesInputStream
	 * Es un metodo final para q no se sobreescriba
	 * @return BufferedInputStream
	 */
	public final BufferedInputStream getFileAsStream() {
		return fileAsStream;
	}

}
