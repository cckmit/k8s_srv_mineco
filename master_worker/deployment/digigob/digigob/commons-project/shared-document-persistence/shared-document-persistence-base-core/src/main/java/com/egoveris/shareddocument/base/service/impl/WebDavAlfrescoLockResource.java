package com.egoveris.shareddocument.base.service.impl;

import com.egoveris.shareddocument.base.service.ILockResource;


public class WebDavAlfrescoLockResource implements ILockResource {
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
