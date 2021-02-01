package com.egoveris.te.model.model;

import java.io.Serializable;

/** 
 * La presente clase es a los efectos de armar el tipo de dato ExpedienteMetadataExternal.
 * @author cearagon 
 */
public class ExpedienteMetadataExternal implements Serializable{		
	
	private static final long serialVersionUID = 1294861407529858349L;
	/**
	 * Nombre otorgado oportunamente al metadata. 
	 */
	private String nombreMetadata;
	/**
	 * True, el metadata debe completarse en forma obligatoria. False, en caso contrario.
	 */
	private boolean obligatoriedadMetadata;
	/**
	 * No es utilizado. Aparece como null.
	 */
	private Integer tipoMetadata;//no es utilizado
	/**
	 * Órden posicional del metadata.
	 */
	private int ordenMetadata;
	/**
	 * Valor que lleva el matadata en la trata que lo contiene.
	 */
	private String valorMetadata;
	
	//Getters and Setters
	public String getNombreMetadata() {
		return nombreMetadata;
	}
	public void setNombreMetadata(String nombreMetadata) {
		this.nombreMetadata = nombreMetadata;
	}
	public boolean isObligatoriedadMetadata() {
		return obligatoriedadMetadata;
	}
	public void setObligatoriedadMetadata(boolean obligatoriedadMetadata) {
		this.obligatoriedadMetadata = obligatoriedadMetadata;
	}
	public Integer getTipoMetadata() {
		return tipoMetadata;
	}
	public void setTipoMetadata(Integer tipoMetadata) {
		this.tipoMetadata = tipoMetadata;
	}
	public int getOrdenMetadata() {
		return ordenMetadata;
	}
	public void setOrdenMetadata(int ordenMetadata) {
		this.ordenMetadata = ordenMetadata;
	}
	public String getValorMetadata() {
		return valorMetadata;
	}
	public void setValorMetadata(String valorMetadata) {
		this.valorMetadata = valorMetadata;
	}				
}//Fin class ExpedienteMetadataExternal
