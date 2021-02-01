package com.egoveris.deo.model.model;

import java.io.Serializable;

public class TareaJBPMDTO implements Serializable {

	private static final long serialVersionUID = 3821684986863294958L;
	
	private Integer dbID;
	
	private String assignee;
	
	private String executionID;
	
	private String activityName;

	private String execution;
	
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getExecutionID() {
		return executionID;
	}

	public void setExecutionID(String executionID) {
		this.executionID = executionID;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getDbID() {
		return dbID;
	}

	public void setDbID(Integer dbID) {
		this.dbID = dbID;
	}

	public String getExecution() {
		return execution;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}
	
}
