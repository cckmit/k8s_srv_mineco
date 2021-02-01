package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.BuzonAsignacionBean;

/**
 * @author erove
 */
public interface IMailDepuracionService {
		
	public void enviarMailProcesoDepuracionAsignacion(List<BuzonAsignacionBean> solicitudes, Usuario usuarioDestino, Integer cantidad, long diasVencimiento) throws NegocioException ;
}	
