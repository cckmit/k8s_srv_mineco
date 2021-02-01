package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;


public class TareaParaleloDTO implements Serializable{
	
	private static final long serialVersionUID = -6838276362923882254L;
	public transient static final String ESTADO_TERMINADO = "Terminado";
	
	private Long id;
	private String idTask;
	private Long idExp;
	private Date fechaPase;
	private String usuarioOrigen;
	private String usuarioGrupoDestino;
	private String motivo;
	private String estado;
	private String estadoAnterior;
	private String motivoRespuesta;
	private String usuarioGrupoAnterior;
	private boolean tareaGrupal;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaPase() {
		return fechaPase;
	}
	public void setFechaPase(Date fechaPase) {
		this.fechaPase = fechaPase;
	}
	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}
	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}
	public String getUsuarioGrupoDestino() {
		return usuarioGrupoDestino;
	}
	public void setUsuarioGrupoDestino(String usuarioGrupoDestino) {
		this.usuarioGrupoDestino = usuarioGrupoDestino;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Long getIdExp() {
		return idExp;
	}
	public void setIdExp(Long idExp) {
		this.idExp = idExp;
	}
	
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	public String getEstadoAnterior() {
		return estadoAnterior;
	}
	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}
	public String getMotivoRespuesta() {
		return motivoRespuesta;
	}
	public void setMotivoRespuesta(String motivoRespuesta) {
		this.motivoRespuesta = motivoRespuesta;
	}
	public String getUsuarioGrupoAnterior() {
		return usuarioGrupoAnterior;
	}
	public void setUsuarioGrupoAnterior(String usuarioGrupoAnterior) {
		this.usuarioGrupoAnterior = usuarioGrupoAnterior;
	}
	public boolean getTareaGrupal() {
		return tareaGrupal;
	}
	public void setTareaGrupal(boolean tareaGrupal) {
		this.tareaGrupal = tareaGrupal;
	}

	
}
