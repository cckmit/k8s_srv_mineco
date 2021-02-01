package com.egoveris.te.base.model;

import com.egoveris.te.base.util.ConstantesCommon;

public class GenerarCopiaBeanDTO {

	private Long id;
	private String username;
	private String idWorkflow;
	private String codigoExpediente;
	private Integer reintentos;
	private Long idActividad;
	private String estadoDeReintento;
	
	
	
	public GenerarCopiaBeanDTO(){
		
	}
	
	public GenerarCopiaBeanDTO(String username, String idWorkflow, String codigoExpediente, Long idActividad){
		this.username = username;
		this.idWorkflow = idWorkflow;
		this.codigoExpediente = codigoExpediente;
		this.idActividad = idActividad;
		this.reintentos = 0;
		this.estadoDeReintento = ConstantesCommon.GENERAR_COPIA_ESTADO_PENDIENTE;
		
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdWorkflow() {
		return idWorkflow;
	}
	public void setIdWorkflow(String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}
	public String getCodigoExpediente() {
		return codigoExpediente;
	}
	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}
	public Integer getReintentos() {
		return reintentos;
	}
	public void setReintentos(Integer reintentos) {
		this.reintentos = reintentos;
	}
	public Long getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(Long idActividad) {
		this.idActividad = idActividad;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEstadoDeReintento() {
		return estadoDeReintento;
	}
	public void setEstadoDeReintento(String estadoDeReintento) {
		this.estadoDeReintento = estadoDeReintento;
	}
	
	
	
	
	
	
	
	
	
	
}
