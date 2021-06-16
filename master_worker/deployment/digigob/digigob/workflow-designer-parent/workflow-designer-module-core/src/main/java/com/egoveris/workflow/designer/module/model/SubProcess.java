package com.egoveris.workflow.designer.module.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TE_SUBPROCESS")
public class SubProcess {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ID_PROCEDURE")
	private Long idProcedure;

	@Column(name = "STATEFLOW")
	private String stateFlow;

	@Column(name = "STATENAME")
	private String stateName;

	@Column(name = "LOCK_TYPE")
	private String lockType;

	@Column(name = "VERSION")
	private int version;

	@Column(name = "PROCEDURE_NAME")
	private String procedureName;

	@Column(name = "START_TYPE")
	private String startType;
	
	@Column(name="VERSION_PROCEDURE")
	private Integer versionProcedure;

	@Column(name = "SCRIPT_START")
	private String scriptStart;

	@Column(name = "SCRIPT_END")
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
	
	public Integer getVersionProcedure() {
		return versionProcedure;
	}
	public void setVersionProcedure(Integer versionProcedure) {
		this.versionProcedure = versionProcedure;
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
