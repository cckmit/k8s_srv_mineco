package com.egoveris.te.ws.service;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.VincularFormularioRequest;

/**
 * La presente interfaz de servicio, expone los métodos necesarios para el
 * bloqueo y/o desbloqueo de expedientes electrónicos.
 * 
 * @author cearagon
 * 
 */
public interface IVincularFormularioService {


	public void vincularExpediente(VincularFormularioRequest vincularFormularioRequest)
	throws ProcesoFallidoException, ParametroIncorrectoException;
}
