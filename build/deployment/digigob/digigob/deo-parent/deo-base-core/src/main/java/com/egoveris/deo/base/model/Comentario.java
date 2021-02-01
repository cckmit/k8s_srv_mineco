package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_COMENTARIOS")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "id_task")
	private String idTask;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "WORKFLOWORIGEN")
	private String workflowOrigen;
	
	@Column(name = "COMENTARIO")
	private String mensaje;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getWorkflowOrigen() {
		return workflowOrigen;
	}
	public void setWorkflowOrigen(String workflowOrigen) {
		this.workflowOrigen = workflowOrigen;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
