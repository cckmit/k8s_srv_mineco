package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TomaVistaResponse implements Serializable {

	private static final long serialVersionUID = -8989237014835045597L;
	
	private List<DocumentoTVDTO> listDocumentosOficiales;

	private String msg;

	public List<DocumentoTVDTO> getListDocumentosOficiales() {
		if (null == this.listDocumentosOficiales) {
			this.listDocumentosOficiales = new ArrayList<DocumentoTVDTO>();
		}
		return this.listDocumentosOficiales;
	}

	public void setListDocumentosOficiales(List<DocumentoTVDTO> listDocumentosOficiales) {
		this.listDocumentosOficiales = listDocumentosOficiales;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
