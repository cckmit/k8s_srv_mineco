package com.egoveris.te.ws.service;

import javax.jws.WebParam;


/**
 * La presente interfaz solo será utilizada para ser invocada por el jar. de regularizacion de permisos de LDAP
 * @author joflores
 *
 */
public interface ITrataReservaReparticionService {

	
	public boolean reparticionDeUsuarioTienePermisosDeReservaEnEE(@WebParam(name="usuario")String usuario);
}
