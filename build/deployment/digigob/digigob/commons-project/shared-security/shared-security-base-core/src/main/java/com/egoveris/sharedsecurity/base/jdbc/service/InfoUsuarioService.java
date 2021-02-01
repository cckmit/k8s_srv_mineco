package com.egoveris.sharedsecurity.base.jdbc.service;

import com.egoveris.sharedsecurity.base.jdbc.dto.InfoUsuarioLoginDTO;

public interface InfoUsuarioService {

	InfoUsuarioLoginDTO getDatosUsuarioByUsuario(String usuario);
}
