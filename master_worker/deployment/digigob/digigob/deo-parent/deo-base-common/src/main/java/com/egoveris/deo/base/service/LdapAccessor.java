package com.egoveris.deo.base.service;

import java.util.List;

public interface LdapAccessor {
	
	public static String ADMINISTRADOR_CENTRAL="ou=admin.central,ou=grupos,dc=egoveris,dc=com";
	public static String DOCUMENTACION_CONFIDENCIAL="ou=gedo.confidencial,ou=grupos,dc=egoveris,dc=com";
	
	public List<String> buscarUsuarios(String stringNombreUsuario);

	public List<String> buscarUsuarioPorUid(String stringNombreUsuario);

	public List<String> buscarUsuarioPorCn(String user);

	public void cambiarPasswordUsuario(String userWF,
			String newPassword);

	public String buscarUsuarioLdap(String stringNombreUsuario);
	
	public boolean login(String username, String password);
	
	public boolean isAdministradorCentral(String user);
	
	public String getNombreYApellido(String username);
	
	/**
	 * Este metodo informa si el usuario tiene permisos para ver documentos confidenciales
	 * @param username
	 * @return un booleano q indica si el usuario tiene permisos sobre documentos confidenciales
	 */
	public boolean puedeVerDocumentosConfidenciales(String username);
}
