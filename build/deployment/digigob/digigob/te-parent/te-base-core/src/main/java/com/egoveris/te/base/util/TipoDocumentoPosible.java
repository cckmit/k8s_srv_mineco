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

		final String returnString = this + " - " + this.descripcion;
		return returnString;
	}
}
