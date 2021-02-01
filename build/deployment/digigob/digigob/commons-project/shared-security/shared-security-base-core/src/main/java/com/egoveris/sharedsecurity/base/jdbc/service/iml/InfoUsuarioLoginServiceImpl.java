package com.egoveris.sharedsecurity.base.jdbc.service.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.jdbc.dto.InfoUsuarioLoginDTO;
import com.egoveris.sharedsecurity.base.jdbc.repository.InfoUsuarioLoginRepository;
import com.egoveris.sharedsecurity.base.jdbc.service.InfoUsuarioService;

@Service("infoUsuarioService")
public class InfoUsuarioLoginServiceImpl implements InfoUsuarioService {

	@Autowired
	private InfoUsuarioLoginRepository infoUsuarioRepository;
	
	@Override
	public InfoUsuarioLoginDTO getDatosUsuarioByUsuario(String usuario) {
		return infoUsuarioRepository.getDatosUsuarioByNombre(usuario);
	}

}
