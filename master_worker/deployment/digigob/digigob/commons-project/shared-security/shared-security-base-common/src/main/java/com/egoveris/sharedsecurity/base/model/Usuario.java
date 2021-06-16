package com.egoveris.sharedsecurity.base.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Usuario extends UsuarioReducido implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2467508236383406312L;
	
	private Collection<GrantedAuthority> authorities;
	private String password;
	//private String username;
	private Boolean isAccountNonExpired;
	private Boolean isAccountNonLocked;
	private Boolean isCredentialsNonExpired;
	private Boolean isEnabled;
	private String email;
	private String codigoReparticion;
	private String codigoReparticionOriginal;
	private String nombreReparticionOriginal;
	//private String nombreApellido;
	private String supervisor;
	private String cuit;
	private String codigoSectorInterno;
	private String codigoSectorInternoOriginal;
	private String ocupacion;
	private Boolean isMultireparticion;
	private String apoderado;
	private String sectorMesa;
	
	private Boolean externalizarFirmaGEDO;
	private Boolean externalizarFirmaLOYS;
	private Boolean externalizarFirmaCCOO;
	private Boolean externalizarFirmaSIGA;
	private String cargo;
	private String usuarioRevisor;
	private Boolean aceptacionTYC;
	
	private Boolean notificarSolicitudPF;

	public Usuario() {
	}
	
	@SuppressWarnings("unchecked")
	public Usuario(UserDetails userDetails) {
		if (userDetails != null) {
			this.authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
			if(this.isAccountNonExpired != null){
				this.isAccountNonExpired = userDetails.isAccountNonExpired();
			}
			if(this.isAccountNonLocked != null){
				this.isAccountNonLocked = userDetails.isAccountNonLocked();
			}
			if(this.isCredentialsNonExpired != null){
				this.isCredentialsNonExpired = userDetails
				.isCredentialsNonExpired();
			}
			if(this.isEnabled != null){
				this.isEnabled = userDetails.isEnabled();
			}
			this.password = userDetails.getPassword();
			this.username = userDetails.getUsername();			
		}		
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

	public String getNombreApellido() {
		return nombreApellido;
	}

	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getCuit() {
		return cuit;
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

	public Boolean getIsCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAuthorities(Collection<GrantedAuthority> collection) {
		this.authorities = collection;
	}
	
	@Override
    public String toString() {
	return this.nombreApellido + " ( " + this.username + " )";
    }
    
    
    @Override    
    public boolean equals (Object object){
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof Usuario))return false;
        
        Usuario otherUsuario = (Usuario) object;
     
        return (this.getUsername().contentEquals(otherUsuario.getUsername()));              
    }

	public String getCodigoSectorInterno() {
		return codigoSectorInterno;
	}

	public void setCodigoSectorInterno(String codigoSectorInterno) {
		this.codigoSectorInterno = codigoSectorInterno;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}
	
	public Boolean getIsMultireparticion() {
		return isMultireparticion;
	}

	public void setIsMultireparticion(Boolean isMultireparticion) {
		this.isMultireparticion = isMultireparticion;
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
	
	public String getGrupo() {
		return codigoReparticion + "-" + codigoSectorInterno;
	}
	
	public String getMesa() {
		return codigoReparticion + "-" + sectorMesa;
	}

	public void setPassword(String password) {
		this.password = password;
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
