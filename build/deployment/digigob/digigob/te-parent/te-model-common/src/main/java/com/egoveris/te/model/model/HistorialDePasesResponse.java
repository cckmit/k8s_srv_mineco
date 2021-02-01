package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unchecked" )
public class HistorialDePasesResponse implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 3251955311253620666L;
	/**
	 * 
	 */
	

	
	
	@SuppressWarnings("rawtypes")
	private List <DocumentosVinculadosResponse> documentosVinculados = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List <ExpedienteAsociadoResponse> expedientesAsociados = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List <ExpedientesFusionResponse> expedientesFusionAsociados = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List <ExpedientesVinculadosResponse> expedientesVinculados = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List<HistorialDeOperacionResponse> historialDeOperacion = new ArrayList();


	public List<DocumentosVinculadosResponse> getDocumentosVinculados() {
		return documentosVinculados;
	}


	public List<ExpedienteAsociadoResponse> getExpedientesAsociados() {
		return expedientesAsociados;
	}


	public List<ExpedientesFusionResponse> getExpedientesFusionAsociados() {
		return expedientesFusionAsociados;
	}


	public List<ExpedientesVinculadosResponse> getExpedientesVinculados() {
		return expedientesVinculados;
	}


	public List<HistorialDeOperacionResponse> getHistorialDeOperacion() {
		return historialDeOperacion;
	}


	public void setDocumentosVinculados(
			List<DocumentosVinculadosResponse> documentosVinculados) {
		this.documentosVinculados = documentosVinculados;
	}


	public void setExpedientesAsociados(
			List<ExpedienteAsociadoResponse> expedientesAsociados) {
		this.expedientesAsociados = expedientesAsociados;
	}


	public void setExpedientesFusionAsociados(
			List<ExpedientesFusionResponse> expedientesFusionAsociados) {
		this.expedientesFusionAsociados = expedientesFusionAsociados;
	}


	public void setExpedientesVinculados(
			List<ExpedientesVinculadosResponse> expedientesVinculados) {
		this.expedientesVinculados = expedientesVinculados;
	}


	public void setHistorialDeOperacion(
			List<HistorialDeOperacionResponse> historialDeOperacion) {
		this.historialDeOperacion = historialDeOperacion;
	}
	
	
	
	
	


	
	
	
	
	
	
	
	

}
