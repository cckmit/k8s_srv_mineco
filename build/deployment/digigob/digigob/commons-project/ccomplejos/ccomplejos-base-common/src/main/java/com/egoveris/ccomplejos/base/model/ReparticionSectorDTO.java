package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ReparticionSectorDTO extends AbstractCComplejoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	String organismo;
	String sector;

	public String getOrganismo() {
		return organismo;
	}

	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

}
