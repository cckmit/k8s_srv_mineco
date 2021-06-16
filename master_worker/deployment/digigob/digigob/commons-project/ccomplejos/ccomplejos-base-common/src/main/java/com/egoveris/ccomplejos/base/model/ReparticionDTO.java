package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ReparticionDTO extends AbstractCComplejoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	String organismo;

	public String getOrganismo() {
		return organismo;
	}

	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}

}
