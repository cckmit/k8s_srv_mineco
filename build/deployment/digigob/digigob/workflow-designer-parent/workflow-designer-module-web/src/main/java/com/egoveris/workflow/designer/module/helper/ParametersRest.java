package com.egoveris.workflow.designer.module.helper;

import java.util.List;

public class ParametersRest {

	private String name;
	private String type;
	private boolean header;
	private boolean requerido;
	private List<ParametersRest> children;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isHeader() {
		return header;
	}
	public void setHeader(boolean header) {
		this.header = header;
	}
	public List<ParametersRest> getChildren() {
		return children;
	}
	public void setChildren(List<ParametersRest> children) {
		this.children = children;
	}
	
	public boolean isRequerido() {
		return requerido;
	}
	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}
	@Override
	public String toString(){
		return "";
	}
	
	
	
	
	
}
