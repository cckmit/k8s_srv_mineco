package com.egoveris.te.base.service.iface;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

/**
 * @author dpadua
 */
public interface IMailService {
	
	public void enviarMailSubsanacionDeDocumentos(ExpedienteElectronicoDTO ee) throws NegocioException;
	
	public void enviarMailInicioDocumentoDeGedo(ExpedienteElectronicoDTO ee,String mailDestino,boolean fueVinculado) throws NegocioException;
	
}
