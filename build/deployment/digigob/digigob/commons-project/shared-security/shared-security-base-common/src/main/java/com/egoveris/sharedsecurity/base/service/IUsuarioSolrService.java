package com.egoveris.sharedsecurity.base.service;

import com.egoveris.sharedsecurity.base.exception.SecurityAccesoDatosException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.UsuarioSolr;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface IUsuarioSolrService {
	
	public UsuarioSolr searchByUsername (String username) throws SecurityAccesoDatosException;
	
	public List<UsuarioSolr> searchByQuery (String value) throws SecurityAccesoDatosException;

	public boolean addToIndex(UsuarioSolr usuario);

	public boolean addToIndex(Collection<UsuarioSolr> usuarios);

	HashMap<String, Usuario> obtenerTodos () throws SecurityAccesoDatosException;
	
	public List<UsuarioReducido> searchByQueryUsuarioReducido(String query) throws SecurityAccesoDatosException;

	public void limpiarIndice();
}
