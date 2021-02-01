package com.egoveris.deo.base.dao;

import com.egoveris.sharedsecurity.base.model.Usuario;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author bfontana
 * 
 */
@Deprecated
@Repository
public interface IDatosUsuarioDAO {
	List<Usuario> obtenerListaTodosUsuarios() throws SQLException ;
	Usuario getDatosUsuario(String username) throws SQLException;
	List<Usuario> obtenerUsuariosSupervisados(String username) throws SQLException;
	public List<Usuario> obtenerUsuariosJefe(String username) throws SQLException;
	void setUsuarioPortaFirma(String username, boolean tienePortafirma) throws SQLException;
}
