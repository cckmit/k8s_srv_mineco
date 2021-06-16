package com.egoveris.te.base.util;

public enum TipoDocumentoPosible {
	RUT("RUT"), DNI("DOCUMENTO DE IDENTIDAD"), OT("OTROS"), PA("PASAPORTE");

	private String descripcion;

	TipoDocumentoPosible(final String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getDescripcionCombo() {
		final String returnString = this.name() + " - " + this.descripcion;
		return returnString;
	}
	
	public static TipoDocumentoPosible fromValue(String descripcion) {
		for (TipoDocumentoPosible tipoDocPosible : values()) {
			if (tipoDocPosible.getDescripcion().equalsIgnoreCase(descripcion)) {
				return tipoDocPosible;
			}
		}

	    return null;
	}
}
