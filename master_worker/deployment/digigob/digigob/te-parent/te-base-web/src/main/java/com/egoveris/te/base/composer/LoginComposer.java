package com.egoveris.te.base.composer;

import com.egoveris.te.base.service.UsuariosSADEService;

import org.springframework.beans.factory.annotation.Autowired;




@Deprecated
public class LoginComposer extends EEGenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5999274090235026033L;

	@Autowired
	private UsuariosSADEService usuariosSADEService ;
	 

	public void onLogin() throws InterruptedException {

	}

	public void onOK$username() throws InterruptedException {
		this.onLogin();
	}

	public void onOK$password() throws InterruptedException {
		this.onLogin();
	}

	 

	public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
		this.usuariosSADEService = usuariosSADEService;
	}

	public UsuariosSADEService getUsuariosSADEService() {
		return usuariosSADEService;
	}

	
}
