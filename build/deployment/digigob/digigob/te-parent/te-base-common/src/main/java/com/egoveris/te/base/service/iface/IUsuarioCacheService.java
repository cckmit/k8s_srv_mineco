package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.UsuarioReducido;

public interface IUsuarioCacheService {
	
	public List<UsuarioReducido> obtenerTodosUsuarios();
	
	public void cachearListaUsuarios();

}
