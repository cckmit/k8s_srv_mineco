package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.AuditoriaPaseResult;
import com.egoveris.te.base.model.PaseDetalleResult;


public interface PaseServiceSatra {

	 
	
	public List<PaseDetalleResult> consultaDetalleDePaseByIdExpediente(Long id);
	public List<AuditoriaPaseResult>consultaPaseAuditoria(Integer idPase);
	
}
