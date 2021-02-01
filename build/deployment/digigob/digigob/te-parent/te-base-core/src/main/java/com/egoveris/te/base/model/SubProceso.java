package com.egoveris.te.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.egoveris.te.base.model.trata.Trata;

@Entity
@Table(name = "TE_SUBPROCESS_PRODUCTION")
public class SubProceso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROCEDURE")
	private Trata tramite;

	@Column(name = "STATEFLOW")
	private String stateFlow;

	@Column(name = "STATENAME")
	private String stateName;

	@Column(name = "LOCK_TYPE")
	private String lockType;

	@Column(name = "VERSION")
	private Integer version;

	@Column(name = "START_TYPE")
	private String startType;

	@Column(name = "VERSION_PROCEDURE")
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

	public Trata getTramite() {
		return tramite;
	}

	public void setTramite(Trata tramite) {
		this.tramite = tramite;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
