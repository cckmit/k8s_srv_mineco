package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class RegistroTemporalDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer dbid;
	private String workflowid;
	private Integer instance;
	private String activityName;
	private Date fechaCreacionTask;
	private String assignee;
	private String motivo;
	
	public Integer getDbid() {
		return dbid;
	}
	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}
	public String getWorkflowid() {
		return workflowid;
	}
	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}
	public Integer getInstance() {
		return instance;
	}
	public void setInstance(Integer instance) {
		this.instance = instance;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Date getFechaCreacionTask() {
		return fechaCreacionTask;
	}
	public void setFechaCreacionTask(Date fechaCreacionTask) {
		this.fechaCreacionTask = fechaCreacionTask;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
