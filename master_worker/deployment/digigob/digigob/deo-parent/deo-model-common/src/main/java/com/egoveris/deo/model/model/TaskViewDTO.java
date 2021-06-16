/**
 * 
 */
package com.egoveris.deo.model.model;

import java.util.Date;

import org.jbpm.api.task.Task;

/**
 * @author pfolgar
 *
 */
public class TaskViewDTO {
	
	private String nombreTarea;
	private Date fechaUltModifi;
	private String enviadoPor;
	private String derivadoPor;
	private String motivoDocumento;
	private String tipoDocumento;
	private Task tarea;
	
	public String getNombreTarea() {
		return nombreTarea;
	}
	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}
	public String getEnviadoPor() {
		return enviadoPor;
	}
	public void setEnviadoPor(String enviadoPor) {
		this.enviadoPor = enviadoPor;
	}
	public String getDerivadoPor() {
		return derivadoPor;
	}
	public void setDerivadoPor(String derivadoPor) {
		this.derivadoPor = derivadoPor;
	}
	public String getMotivoDocumento() {
		return motivoDocumento;
	}
	public void setMotivoDocumento(String motivoDocumento) {
		this.motivoDocumento = motivoDocumento;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Task getTarea() {
		return tarea;
	}
	public void setTarea(Task tarea) {
		this.tarea = tarea;
	}
	public Date getFechaUltModifi() {
		return fechaUltModifi;
	}
	public void setFechaUltModifi(Date fechaUltModifi) {
		this.fechaUltModifi = fechaUltModifi;
	}

}
