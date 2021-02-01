package com.egoveris.sharedsecurity.base.jdbc.repository;

import com.egoveris.sharedsecurity.base.jdbc.dto.InfoUsuarioLoginDTO;

public interface InfoUsuarioLoginRepository {

	public InfoUsuarioLoginDTO getDatosUsuarioByNombre(String usuario);
}
