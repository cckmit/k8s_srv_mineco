package com.egoveris.te.base.service;

import java.util.Map;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

public interface IEEWorkflowUtils {

	public String generarDocumentoRespaldo(String acronimo,
			String referencia, 
			Object json, ExpedienteElectronicoDTO ee);
	
	public boolean vincularDocumentoRespaldo(ExpedienteElectronicoDTO ee, String nroDocumento);
	
	public Map<String, Object> getMap(String numeroDEO, ExpedienteElectronicoDTO ee);
	
	public String obtenerUltimoDoc(ExpedienteElectronicoDTO ee, String acronimo);
	
	public boolean notificarTad(ExpedienteElectronicoDTO ee, String acronimo, String referencia);
	
	public String obtenerValorBBDD(String clave);
	
	public String getAssignee(ExpedienteElectronicoDTO ee);
	
	public String getAssignee(String workflowId);
	
	public String get(String metodo);
	
	public String post(String metodo, Object json);
	
	public boolean enviarMail(String usuario, String asunto, Object json, String plantilla);

}
