package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class SolicitudQuartzDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombreJob;
	private String nombreTrigger;
	private String estado;
	private String cronExpression;
	private Date startTime;
	private Date previousFireTime;
	private Date nextFireTime;
	private Date endTime;
	private String proximoReintento;
	private String grupo;

	public String getNombreJob() {
		return nombreJob;
	}
	public void setNombreJob(String nombreJob) {
		this.nombreJob = nombreJob;
	}
	public String getNombreTrigger() {
		return nombreTrigger;
	}
	public void setNombreTrigger(String nombreTrigger) {
		this.nombreTrigger = nombreTrigger;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getPreviousFireTime() {
		return previousFireTime;
	}
	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}
	public Date getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getProximoReintento() {
		return proximoReintento;
	}
	public void setProximoReintento(String proximoReintento) {
		this.proximoReintento = proximoReintento;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
}
