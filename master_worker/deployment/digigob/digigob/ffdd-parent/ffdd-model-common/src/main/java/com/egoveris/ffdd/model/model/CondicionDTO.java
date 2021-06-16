package com.egoveris.ffdd.model.model;

import java.io.Serializable;

public class CondicionDTO implements Serializable{

	private static final long serialVersionUID = -2049572856689480190L;

	public enum Condicion {
		VACIO("Vacio"),
		NO_VACIO("No vacio"),
		DISTINTO("Distinto"),
		IGUAL("Igual"),
		MAYOR("Mayor"),
		MENOR("Menor");
		
		final String value;
	    
		Condicion(String value) {
	        this.value = value;
	    }
	};

	private String nombreComponente;
	private String nombreCompComparacion;
	private String valorComparacion;
	private Condicion condicion;
	private boolean and = false;
	private boolean or = false;
	private boolean primero = true;

	public String getNombreComponente() {
		return nombreComponente;
	}

	public void setNombreComponente(String nombreComponente) {
		this.nombreComponente = nombreComponente;
	}

	public String getNombreCompComparacion() {
		return nombreCompComparacion;
	}

	public void setNombreCompComparacion(String nombreCompComparacion) {
		this.nombreCompComparacion = nombreCompComparacion;
	}

	public String getValorComparacion() {
		return valorComparacion;
	}

	public void setValorComparacion(String valorComparacion) {
		this.valorComparacion = valorComparacion;
	}

	public Condicion getCondicion() {
		return condicion;
	}

	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}

	public boolean isAnd() {
		return and;
	}

	public void setAnd(boolean and) {
		this.and = and;
	}

	public boolean isOr() {
		return or;
	}

	public void setOr(boolean or) {
		this.or = or;
	}

	public boolean isPrimero() {
		return primero;
	}

	public void setPrimero(boolean primero) {
		this.primero = primero;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CondicionDTO [nombreComponente=").append(nombreComponente).append(", nombreCompComparacion=")
				.append(nombreCompComparacion).append(", valorComparacion=").append(valorComparacion)
				.append(", condicion=").append(condicion).append(", and=").append(and).append(", or=").append(or)
				.append(", primero=").append(primero).append("]");
		return builder.toString();
	}

}
