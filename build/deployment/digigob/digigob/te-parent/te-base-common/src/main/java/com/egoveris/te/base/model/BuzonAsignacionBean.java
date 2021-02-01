package com.egoveris.te.base.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuzonAsignacionBean {
	
	String Id;
	String executeID;
	Timestamp fecha;
	String usuario;
	String workflowID;
	String estado;
	String grupo;
	
	List<Map<String,Object>> detalles = new ArrayList<Map<String, Object>>();
	
	public List<Map<String, Object>> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<Map<String, Object>> detalles) {
		this.detalles = detalles;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getExecuteID() {
		return executeID;
	}
	public void setExecuteID(String executeID) {
		this.executeID = executeID;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getWorkflowID() {
		return workflowID;
	}
	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}
}
