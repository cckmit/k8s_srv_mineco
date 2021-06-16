package com.egoveris.vucfront.model.util;

public enum DocumentoEstadoEnum {
	
	PENDIENTESUBSANACION(0L,"Pendiente de subsanacion"), 
	SUBSANADO(1L, "Subsanado");
	
	private final Long id;
	private String estado;

	private DocumentoEstadoEnum(final Long id, String estado) {
		this.id = id;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}
	
	public String getEstado() {
		return estado;
	}

}
