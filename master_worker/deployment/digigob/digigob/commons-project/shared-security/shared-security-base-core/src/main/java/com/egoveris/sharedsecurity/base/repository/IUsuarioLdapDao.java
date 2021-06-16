package com.egoveris.sharedsecurity.base.repository;

import com.egoveris.sharedsecurity.base.model.UsuarioReducido;

import java.util.List;

public interface IUsuarioLdapDao {

	public static String ADMINISTRADOR_CENTRAL = "ou=admin.central,ou=grupos,dc=gob,dc=ar";

	public static String DOCUMENTACION_CONFIDENCIAL = "ou=gedo.confidencial,ou=grupos,dc=gob,dc=ar";

	public List<String> buscarUsuarioPorUid(String stringNombreUsuario);

	public List<UsuarioReducido> buscarUsuarioPorCn(String user);

	public boolean login(String username, String password);

	public boolean isAdministradorCentral(String user);	

	public boolean puedeVerDocumentosConfidenciales(String username);

	public List<UsuarioReducido> buscarUsuariosPorRol(String rol);
	
	public List<UsuarioReducido> buscarTodosUsuariosLdap();

	Boolean existeUsuario(String username);

	void cambiarPasswordUsuario(String username, String newPassword);

	boolean esPasswordUsuarioValido(String base, String filter, String password);

	void cambiarCN(String username);

}