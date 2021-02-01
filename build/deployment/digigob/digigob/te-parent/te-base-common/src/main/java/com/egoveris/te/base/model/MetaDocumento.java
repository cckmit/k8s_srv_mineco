package com.egoveris.te.base.model;


public class MetaDocumento {

	public enum TipoConsulta {
		LEGACY_CONSULTA, IS_CONSULTA, CONTAIN_CONSULTA
	}

	private String nombre;
	private TipoConsulta tipoConsulta;
	private Object valor;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoConsulta getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(TipoConsulta tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

}
