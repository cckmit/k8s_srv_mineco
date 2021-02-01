package com.egoveris.te.model.model;

import java.io.Serializable;

public class ArchivoDeTrabajoResponse  implements Serializable{
	
	private static final long serialVersionUID = 8495776573230135555L;

	private byte[] dataArchivo;
	
	public byte[] getDataArchivo() {
		return dataArchivo;
	}
	public void setDataArchivo(byte[] dataArchivo) {
		this.dataArchivo = dataArchivo;
	}
}
