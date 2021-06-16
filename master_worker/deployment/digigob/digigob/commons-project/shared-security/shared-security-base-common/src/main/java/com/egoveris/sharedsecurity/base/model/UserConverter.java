package com.egoveris.sharedsecurity.base.model;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;


@Component("userConverter")
public class UserConverter  {
	
	@Resource (name = "userConverterMapper")
	private Mapper mapper;

	public UsuarioSolr cargarDTO(Usuario usuario) {
		UsuarioSolr usuarioSolr = null;
		try {
			if (usuario != null) {
				usuarioSolr = mapper.map(usuario, UsuarioSolr.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuarioSolr;
	}

	public Usuario cargarUsuario(UsuarioSolr usuarioSolr) {		
		try {
			Usuario usuario = mapper.map(usuarioSolr, Usuario.class);
	
			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
