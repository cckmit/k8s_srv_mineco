package com.egoveris.te.base.model;

import com.egoveris.te.model.model.TrataEE;

public class SubProcesoDesginerDTO {

	private Long id;
	private TrataEE  tramite; 
	private String stateFlow;
	private String stateName;
	private String lockType;
	private int version;

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
	
}
