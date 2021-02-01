package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class UsuarioSolr implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4613499927200834337L;

	
	@Field("username")
	private String username;
	
	@Field("is_account_non_Expired")
	private Boolean isAccountNonExpired;
	
	@Field("is_account_non_locked")
	private Boolean isAccountNonLocked;
	
	@Field("is_credentials_non_expired")
	private Boolean isCredentialsNonExpired;
	
	@Field("is_enabled")
	private Boolean isEnabled;
	
	@Field("email")
	private String email;
	
	@Field("codigo_reparticion")
	private String codigoReparticion;
	
	@Field("codigo_reparticion_original")
	private String codigoReparticionOriginal;
	
	@Field("nombre_reparticion_original")
	private String nombreReparticionOriginal;
	
	@Field("nombre_apellido")
	private String nombreApellido;
	
	@Field("supervisor")
	private String supervisor;
	
	@Field("cuit")
	private String cuit;
	
	@Field("codigo_sector_interno")
	private String codigoSectorInterno;
	
	@Field("codigo_sector_interno_original")
	private String codigoSectorInternoOriginal;
	
	@Field("is_multireparticion")
	private Boolean isMultireparticion;
	
	@Field("apoderado")
	private String apoderado;
	
	@Field("externalizar_firma_en_gedo")
	private Boolean externalizarFirmaGEDO;
	
	@Field("externalizar_firma_en_loys")
	private Boolean externalizarFirmaLOYS;
	
	@Field("externalizar_firma_en_ccoo")
	private Boolean externalizarFirmaCCOO;
	
	@Field("externalizar_firma_en_siga")
	private Boolean externalizarFirmaSIGA;
	
	@Field("cargo")
	private String cargo;
	
	@Field("usuario_revisor")
	private String usuarioRevisor;
	
	@Field("aceptacion_tyc")
	private Boolean aceptacionTYC;
	
	@Field("sector_mesa")
	private String sectorMesa;
	
	@Field("notificar_solicitud_pf")
	private Boolean notificarSolicitudPF;
	
	public UsuarioSolr() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getIsAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setIsAccountNonExpired(Boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public Boolean getIsAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setIsAccountNonLocked(Boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public String getCodigoSectorInterno() {
		return codigoSectorInterno;
	}

	public void setCodigoSectorInterno(String codigoSectorInterno) {
		this.codigoSectorInterno = codigoSectorInterno;
	}

	public Boolean getIsMultireparticion() {
		return isMultireparticion;
	}

	public void setIsMultireparticion(Boolean isMultireparticion) {
		this.isMultireparticion = isMultireparticion;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getNombreApellido() {
		return nombreApellido;
	}

	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}

	public Boolean getIsCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodigoReparticion() {
		return codigoReparticion;
	}

	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}

	public void setCodigoReparticionOriginal(String codigoReparticionOriginal) {
		this.codigoReparticionOriginal = codigoReparticionOriginal;
	}

	public String getCodigoReparticionOriginal() {
		return codigoReparticionOriginal;
	}

	public void setNombreReparticionOriginal(String nombreReparticionOriginal) {
		this.nombreReparticionOriginal = nombreReparticionOriginal;
	}

	public String getNombreReparticionOriginal() {
		return nombreReparticionOriginal;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getApoderado() {
		return apoderado;
	}

	public void setApoderado(String apoderado) {
		this.apoderado = apoderado;
	}
	
	
	public Boolean getExternalizarFirmaGEDO() {
		return externalizarFirmaGEDO;
	}

	public void setExternalizarFirmaGEDO(Boolean externalizarFirmaGEDO) {
		this.externalizarFirmaGEDO = externalizarFirmaGEDO;
	}

	public Boolean getExternalizarFirmaLOYS() {
		return externalizarFirmaLOYS;
	}

	public void setExternalizarFirmaLOYS(Boolean externalizarFirmaLOYS) {
		this.externalizarFirmaLOYS = externalizarFirmaLOYS;
	}

	public Boolean getExternalizarFirmaCCOO() {
		return externalizarFirmaCCOO;
	}

	public void setExternalizarFirmaCCOO(Boolean externalizarFirmaCCOO) {
		this.externalizarFirmaCCOO = externalizarFirmaCCOO;
	}

	public Boolean getExternalizarFirmaSIGA() {
		return externalizarFirmaSIGA;
	}

	public void setExternalizarFirmaSIGA(Boolean externalizarFirmaSIGA) {
		this.externalizarFirmaSIGA = externalizarFirmaSIGA;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getUsuarioRevisor() {
		return usuarioRevisor;
	}

	public void setUsuarioRevisor(String usuarioRevisor) {
		this.usuarioRevisor = usuarioRevisor;
	}

	public void setAceptacionTYC(Boolean aceptacionTYC) {
		this.aceptacionTYC = aceptacionTYC;
	}

	public Boolean getAceptacionTYC() {
		return aceptacionTYC;
	}
	
	public void setSectorMesa(String sectorMesa) {
		this.sectorMesa = sectorMesa;
	}

	public String getSectorMesa() {
		return sectorMesa;
	}

	public String getCodigoSectorInternoOriginal() {
		return codigoSectorInternoOriginal;
	}

	public void setCodigoSectorInternoOriginal(String codigoSectorInternoOriginal) {
		this.codigoSectorInternoOriginal = codigoSectorInternoOriginal;
	}

	public void setNotificarSolicitudPF(Boolean notificarSolicitudPF) {
		this.notificarSolicitudPF = notificarSolicitudPF;
	}

	public Boolean getNotificarSolicitudPF() {
		return notificarSolicitudPF;
	}	
}
