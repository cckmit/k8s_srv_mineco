package com.egoveris.te.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * 
 * @author lfishkel
 *
 */
public class Zip {
	
	private ZipOutputStream out ;
	private ByteArrayOutputStream baos;
	
	public Zip(String nombre) throws IOException{
		baos = new ByteArrayOutputStream();
		out = new ZipOutputStream(baos);
	}
	/**
	 * Agrega archivo al zip pasando como parámetro el contenido en array de bytes y el nombre incluyendo la extensión
	 * @param contenido
	 * @param nombreArchivo
	 * @throws IOException
	 */
	public void agregarEntrada(byte[] contenido, String nombreArchivo) throws IOException{
		 ZipEntry e = new ZipEntry(nombreArchivo);
		 out.putNextEntry(e);
		 out.write(contenido);
		 out.closeEntry();
	}
	
	public void finish() throws IOException{
		this.out.close();
	}
	
	public byte[] getZip() throws IOException{
		out.close();
		return baos.toByteArray();
	}
	
}
