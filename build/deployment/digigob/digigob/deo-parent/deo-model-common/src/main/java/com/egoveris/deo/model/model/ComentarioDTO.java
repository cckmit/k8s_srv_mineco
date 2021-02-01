package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class ComentarioDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1285508711809352072L;

	private Integer id;
	private String idTask;
	private String usuario;
	private Date fecha;
	private String workflowOrigen;
	private String mensaje;
	
	
	public ComentarioDTO(){
	}
	public ComentarioDTO(String idTask, String usuario, Date fecha,
			String workflowOrigen, String mensaje) {
		super();
		this.idTask = idTask;
		this.usuario = usuario;
		this.fecha = fecha;
		this.workflowOrigen = workflowOrigen;
		this.mensaje = mensaje;
	}
	
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
