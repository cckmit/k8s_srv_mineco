package com.egoveris.te.base.model;

import java.io.Serializable;

public class UsuarioMigracion implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5485477420164747256L;
	private Integer id;
	private String nombreUsuario;
	private String codigoReparticionOriginal;
	private String codigoSectorInterno;
	
	
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuarioAMigrar) {
		this.nombreUsuario = nombreUsuarioAMigrar;
	}
	public String getCodigoReparticionOriginal() {
		return codigoReparticionOriginal;
	}
	public void setCodigoReparticionOriginal(String codigoReparticionOriginal) {
		this.codigoReparticionOriginal = codigoReparticionOriginal;
	}
	public String getCodigoSectorInterno() {
		return codigoSectorInterno;
	}
	public void setCodigoSectorInterno(String codigoSectorInterno) {
		this.codigoSectorInterno = codigoSectorInterno;
	}
	

	
	
	
	
	
	
	
	
	

}
