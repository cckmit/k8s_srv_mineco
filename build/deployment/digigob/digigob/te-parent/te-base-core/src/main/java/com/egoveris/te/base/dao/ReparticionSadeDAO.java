package com.egoveris.te.base.dao;

import java.util.List;

import org.jbpm.api.identity.Group;

public interface ReparticionSadeDAO {	
	
 
	public List<Group> buscarReparticionesPorUsuario(String userName);
	
	public boolean validarCodigoReparticion(String value);
	
	public Group buscarReparticionesByUsuario(String userName);

}
