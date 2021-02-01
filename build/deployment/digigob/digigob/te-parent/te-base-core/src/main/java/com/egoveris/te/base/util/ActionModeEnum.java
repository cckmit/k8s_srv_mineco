package com.egoveris.te.base.util;

public enum ActionModeEnum {
	NEW("NEW"),
	EDIT("EDIT");
	
	private String value;
	
	ActionModeEnum(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}	
	
}
