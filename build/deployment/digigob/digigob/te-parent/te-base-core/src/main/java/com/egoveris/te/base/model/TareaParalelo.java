package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EE_TAREA_PARALELO")
public class TareaParalelo implements Serializable{
	
	private static final long serialVersionUID = -6838276362923882254L;
	public static final String ESTADO_TERMINADO = "Terminado";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name="ID_TASK")
	private String idTask;
	
	@Column(name="ID_EXP")
	private Long idExp;
	
	@Column(name="FECHA_PASE")
	private Date fechaPase;
	
	@Column(name="USUARIO_ORIGEN")
	private String usuarioOrigen;
	
	@Column(name="USUARIO_GRUPO_DESTINO")
	private String usuarioGrupoDestino;
	
	@Column(name="MOTIVO")
	private String motivo;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name="ESTADO_ANTERIOR")
	private String estadoAnterior;
	
	@Column(name="MOTIVO_RESPUESTA")
	private String motivoRespuesta;
	
	@Column(name="USUARIO_GRUPO_ANTERIOR")
	private String usuarioGrupoAnterior;
	
	@Column(name="TAREA_GRUPAL")
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
