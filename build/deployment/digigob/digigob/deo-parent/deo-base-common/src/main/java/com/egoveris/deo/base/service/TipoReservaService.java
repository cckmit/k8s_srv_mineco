package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.TipoReservaNoExisteException;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.TipoReservaDTO;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

public interface TipoReservaService {

	@Transactional(propagation=Propagation.REQUIRES_NEW, timeout = 1200, rollbackFor = Exception.class)
	public void actualizarReservaReparticionDocumento 
		(DocumentoDTO documento, TipoReservaDTO tipoReserva, String reparticion, 
		    List<String> reparticionesRectoras, String usuarioOReparticionAlta) 
		        throws ApplicationException;
	
	public TipoReservaDTO obtenerReserva (String reserva) throws TipoReservaNoExisteException;
}
