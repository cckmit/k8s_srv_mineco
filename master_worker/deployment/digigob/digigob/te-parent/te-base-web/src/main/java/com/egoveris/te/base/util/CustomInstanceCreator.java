package com.egoveris.te.base.util;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

public class CustomInstanceCreator implements InstanceCreator<GrantedAuthority> {
	
	@Autowired
	GrantedAuthority grantedAutority;
	
	@Override
	public GrantedAuthority createInstance(Type arg0) {
		return grantedAutority;
	}
}
