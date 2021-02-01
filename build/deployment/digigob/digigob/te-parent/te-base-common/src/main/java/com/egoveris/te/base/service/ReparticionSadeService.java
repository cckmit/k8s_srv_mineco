package com.egoveris.te.base.service;

import java.util.List;

import org.jbpm.api.identity.Group;

public interface ReparticionSadeService {

	
	public List<Group> buscarReparticionesPorUsuario(String userName);
	
	public boolean validarCodigoReparticion(String value);
	
	public Group buscarReparticionesByUsuario(String userName);
}
