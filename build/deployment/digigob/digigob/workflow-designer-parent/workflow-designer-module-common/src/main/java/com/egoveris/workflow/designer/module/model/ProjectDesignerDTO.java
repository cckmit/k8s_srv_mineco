/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;

public class ProjectDesignerDTO implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -5616707636646849784L;
	private String name;
	private String author;
	private String description;
	private int version;
	private String jsonModel;
	private String typeWorkFlow;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getJsonModel() {
		return jsonModel;
	}
	public void setJsonModel(String jsonModel) {
		this.jsonModel = jsonModel;
	}
	public String getTypeWorkFlow() {
		return typeWorkFlow;
	}
	public void setTypeWorkFlow(String typeWorkFlow) {
		this.typeWorkFlow = typeWorkFlow;
	}

	
	
}

