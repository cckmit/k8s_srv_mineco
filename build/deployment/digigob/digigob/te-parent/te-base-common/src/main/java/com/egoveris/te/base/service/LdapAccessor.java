package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.DatosLdap;

/**
 * Funcionalidad que se puede utilizar del LDAP.
 * 
 * @author Juan Norverto
 *
 */
public interface LdapAccessor {
	
	public List<String> buscarUsuarios(String stringNombreUsuario);

	public List<String> buscarUsuarioPorUid(String stringNombreUsuario);

	public String buscarUsuarioLdap(String stringNombreUsuario);
	
	public boolean login(String username, String password);
	
	public List<String> buscarPerfilesDeUsuarioLdap(String stringNombreUsuario);
	
	public String getNombreYApellido(String username);
	
	public DatosLdap buscarDatos(String stringNombreUsuario);
	
	/**
	 * Buscar usuario por cn.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<String> buscarUsuarioPorCn(String user);
	
}
