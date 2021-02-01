package com.egoveris.te.model.model;

import java.io.Serializable;


/**
 * La presente clase da forma a los datos que se requiere ingresar a un servicio
 * externo, para que se pueda realizar una rehabilitacion de Expediente desde Guarda Temporal.
 * 
 * @author smuzychu
 */
public class RehabilitacionExpedienteRequest implements Serializable {
	/**
	 * 
	 */
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8831142643526654376L;
	/**
	 * Nombre del usuario logueado, realizando la operación.
	 */
	private String loggeduser;
	/**
	 * Nombre del sistema que solicita la generación del expediente electrónico.
	 */
	private String sistemaOrigen;

	/**
	 * Motivo del Desarchivo del Expediente
	 */
	private String motivo;
	/**
	 * Destino del Expediente Desarchivado
	 */
	private String destinatario;
	
	/**
	 * Codigo ExpedienteElectronico
	 */
	private String codExpediente;
	/**
	 * Estado Destino del Expediente (Tramitación, Comunicación o Ejecución) 
	 */
	private String estadoDestino;
	/**
	 *  
	 */
	public String getLoggeduser() {
		return loggeduser;
	}

	public String getMotivo() {
		return motivo;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public String getCodExpediente() {
		return codExpediente;
	}
	public String getEstadoDestino() {
		return estadoDestino;
	}
	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public void setCodExpediente(String codExpediente) {
		this.codExpediente = codExpediente;
	}
	public void setEstadoDestino(String estadoDestino) {
		this.estadoDestino = estadoDestino;
	}
	
	public String getSistemaOrigen() {
		return sistemaOrigen;
	}

	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}
	
	
	
	
	
	
	
	


}
