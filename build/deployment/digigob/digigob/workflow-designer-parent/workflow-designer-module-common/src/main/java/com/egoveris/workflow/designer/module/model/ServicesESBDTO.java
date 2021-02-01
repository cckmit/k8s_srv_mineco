package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;

public class ServicesESBDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -739580624930621047L;
	private Long id;
	private String name;
	private String url;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	

}
