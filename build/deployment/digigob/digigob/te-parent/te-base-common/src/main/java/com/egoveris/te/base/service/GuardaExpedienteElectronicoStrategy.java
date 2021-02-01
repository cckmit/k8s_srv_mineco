package com.egoveris.te.base.service;

import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

public interface GuardaExpedienteElectronicoStrategy {

	public ExpedienteElectronicoDTO guardar(ExpedienteElectronicoDTO o) throws DataAccessLayerException;
}
