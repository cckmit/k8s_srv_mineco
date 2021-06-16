package com.egoveris.te.base.model;

import java.io.Serializable;

import com.egoveris.te.model.model.TrataEE;

public class SubProcesoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9160057588017761322L;
	private Long id;
	private TrataEE tramite;
	private String stateFlow;
	private String stateName;
	private String lockType;
	private int version;
	private String startType;
	private Integer versionProcedure;
	private String scriptStart;
	private String scriptEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TrataEE getTramite() {
		return tramite;
	}

	public void setTramite(TrataEE tramite) {
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
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
