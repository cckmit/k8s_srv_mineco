package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.List;

import org.jbpm.api.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatosUsuarioBean implements Serializable, User, Comparable<DatosUsuarioBean> {
	private static final Logger logger = LoggerFactory.getLogger(DatosUsuarioBean.class);

	private static final long serialVersionUID = 9183337433448340591L;

	private String mail;
	private String cargo;
	private String usuario;
	private String apellidoYNombre;
	private String userApoderado;
	private String codigoSectorInterno;
	private String codigoReparticion;
	private String usuarioSuperior;
	private List<String> roles;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getApellidoYNombre() {
		return apellidoYNombre;
	}

	public void setApellidoYNombre(String apellidoYNombre) {
		this.apellidoYNombre = apellidoYNombre;
	}
	@Override
	public String toString() {
		return this.apellidoYNombre + "  (" + this.usuario + ")";
	}

	public String getBusinessEmail() {
		if (logger.isDebugEnabled()) {
			logger.debug("getBusinessEmail() - start");
		}

		String returnString = this.getMail();
		if (logger.isDebugEnabled()) {
			logger.debug("getBusinessEmail() - end - return value={}", returnString);
		}
		return returnString;
	}

	public String getFamilyName() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFamilyName() - start");
		}

		String returnString = this.getApellidoYNombre();
		if (logger.isDebugEnabled()) {
			logger.debug("getFamilyName() - end - return value={}", returnString);
		}
		return returnString;
	}

	public String getGivenName() {
		if (logger.isDebugEnabled()) {
			logger.debug("getGivenName() - start");
		}

		String returnString = this.getApellidoYNombre();
		if (logger.isDebugEnabled()) {
			logger.debug("getGivenName() - end - return value={}", returnString);
		}
		return returnString;
	}

	public String getId() {
		if (logger.isDebugEnabled()) {
			logger.debug("getId() - start");
		}

		String returnString = this.getUsuario();
		if (logger.isDebugEnabled()) {
			logger.debug("getId() - end - return value={}", returnString);
		}
		return returnString;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof DatosUsuarioBean) {
			DatosUsuarioBean dub = (DatosUsuarioBean) o;
			if (dub.getId().equals(this.getId())) {
				return true;
			} else {
				if (dub.getUsuario().equals(this.getUsuario())) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		
		return result;
	}
	
	public String getUserApoderado() {
		return userApoderado;
	}

	public void setUserApoderado(String userApoderado) {
		this.userApoderado = userApoderado;
	}

	public String getCodigoSectorInterno() {
		return codigoSectorInterno;
	}

	public void setCodigoSectorInterno(String codigoSectorInterno) {
		this.codigoSectorInterno = codigoSectorInterno;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getCodigoReparticion() {
		return codigoReparticion;
	}

	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}
	
	public String getUsuarioSuperior() {
		return usuarioSuperior;
	}

	public void setUsuarioSuperior(String usuarioSuperior) {
		this.usuarioSuperior = usuarioSuperior;
	}

	public boolean hasRole(String role) {
		if (logger.isDebugEnabled()) {
			logger.debug("hasRole(role={}) - start", role);
		}

		if (role != null) {
			String roles = role.toUpperCase().trim();
			if(roles == null){
				if (logger.isDebugEnabled()) {
					logger.debug("hasRole(String) - end - return value={}", false);
				}
				return false;
			}
			for (String rol: this.roles) {
				if (rol.equals(role)){
					if (logger.isDebugEnabled()) {
						logger.debug("hasRole(String) - end - return value={}", true);
					}
					return true;
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hasRole(String) - end - return value={}", false);
		}
		return false;
	}

	@Override
	public int compareTo(DatosUsuarioBean o) {
		if (logger.isDebugEnabled()) {
			logger.debug("compareTo(o={}) - start", o);
		}

		if(o != null){
			int returnint = getUsuario().compareTo(o.getUsuario());
			if (logger.isDebugEnabled()) {
				logger.debug("compareTo(DatosUsuarioBean) - end - return value={}", returnint);
			}
            return returnint;
        }

		if (logger.isDebugEnabled()) {
			logger.debug("compareTo(DatosUsuarioBean) - end - return value={}", 1);
		}
        return 1;
	}
}
