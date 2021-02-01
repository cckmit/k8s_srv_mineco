package com.egoveris.workflow.designer.module.model;

public class SubProcessDTO {

	private Long id;
	private Long idProcedure;
	private String stateFlow;
	private String stateName;
	private String lockType;
	private int version;
	private String procedureName;
	private String startType;
	private String scriptStart;
	private String scriptEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProcedure() {
		return idProcedure;
	}

	public void setIdProcedure(Long idProcedure) {
		this.idProcedure = idProcedure;
	}

	public String getStateFlow() {
		return stateFlow;
	}

	public void setStateFlow(String stateFlow) {
		this.stateFlow = stateFlow;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getStartType() {
		return startType;
	}

	public void setStartType(String startType) {
		this.startType = startType;
	}

	public String getScriptStart() {
		return scriptStart;
	}

	public void setScriptStart(String scriptStart) {
		this.scriptStart = scriptStart;
	}

	public String getScriptEnd() {
		return scriptEnd;
	}

	public void setScriptEnd(String scriptEnd) {
		this.scriptEnd = scriptEnd;
	}

}
