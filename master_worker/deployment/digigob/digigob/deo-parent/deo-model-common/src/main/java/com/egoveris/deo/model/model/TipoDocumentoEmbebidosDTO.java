package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class TipoDocumentoEmbebidosDTO implements Serializable {

	private static final long serialVersionUID = -8616704047064883401L;
	
	private TipoDocumentoEmbebidosPKDTO tipoDocumentoEmbebidosPK;
	private String descripcion;
	private boolean obligatorio;
	private Date fechaCreacion;
	private String userName;
	private Integer sizeArchivoEmb;

	
	public Integer getSizeArchivoEmb() {
		return sizeArchivoEmb;
	}
	public void setSizeArchivoEmb(Integer sizeArchivoEmb) {
		this.sizeArchivoEmb = sizeArchivoEmb;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean getObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public TipoDocumentoEmbebidosPKDTO getTipoDocumentoEmbebidosPK() {
		return tipoDocumentoEmbebidosPK;
	}
	public void setTipoDocumentoEmbebidosPK(TipoDocumentoEmbebidosPKDTO tipoDocumentoEmbebidosPK) {
		this.tipoDocumentoEmbebidosPK = tipoDocumentoEmbebidosPK;
	}


}


