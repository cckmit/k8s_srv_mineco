package com.egoveris.deo.model.model;

import java.io.Serializable;

public class ExtensionMimetypePKDTO implements Serializable {
	private static final long serialVersionUID = -6849905919051367768L;	
	
	private FormatoTamanoArchivoDTO formatoTamanoId;
	
	private String mimetype;
	
	public FormatoTamanoArchivoDTO getFormatoTamanoId() {
		return formatoTamanoId;
	}
	public void setFormatoTamanoId(FormatoTamanoArchivoDTO formatoTamanoId) {
		this.formatoTamanoId = formatoTamanoId;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
}
