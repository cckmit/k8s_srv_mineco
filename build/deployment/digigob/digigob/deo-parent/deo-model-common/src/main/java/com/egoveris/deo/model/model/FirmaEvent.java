package com.egoveris.deo.model.model;

import java.util.Map;

public class FirmaEvent {
	private String eventName;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	private Map data;

}
