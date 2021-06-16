/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TE_PROJECT")
public class ProjectDesginer{
	
	@Id
	@Column(name="NAME")
	private String name;
	
	@Column(name="AUTHOR")
	private String author;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="VERSION")
	private int version;
	
	@Column(name="JSON_MODEL")
	private String jsonModel;
	
	@Column(name="TYPE_WORKFLOW")
	private String typeWorkFlow;
	
	@Column(name="VERSION_PROCEDURE")
	private Integer versionProcedure;
	
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

	public void setTypeWorkFlow(String  typeWorkFlow) {
		this.typeWorkFlow = typeWorkFlow;
	}

	public Integer getVersionProcedure() {
		return versionProcedure;
	}

	public void setVersionProcedure(Integer versionProcedure) {
		this.versionProcedure = versionProcedure;
	}
}

