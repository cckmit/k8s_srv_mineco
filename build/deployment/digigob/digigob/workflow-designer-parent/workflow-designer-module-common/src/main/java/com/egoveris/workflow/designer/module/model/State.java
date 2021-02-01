/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;


public class State implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 849814139944966335L;
	private DrawAttributes attributes;
	private StateProperties properties;
	private boolean hasProperties;
	private String[] allowedConnections;
	private String hint;
	
	/**
	 * @return the properties
	 */
	public StateProperties getProperties() {
		if (properties==null) {
			properties=new StateProperties();
		}
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(StateProperties properties) {
		this.properties = properties;
	}
	
	/**
	 * @return the attributes
	 */
	public DrawAttributes getAttributes() {
		if (attributes==null) {
			attributes = new DrawAttributes();
		}
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(DrawAttributes attributes) {
		this.attributes = attributes;
	}

	public boolean showProperties() {
		return true;
	}
	
	/**
	 * @return the hasProperties
	 */
	public boolean isHasProperties() {
		return hasProperties;
	}

	/**
	 * @param hasProperties the hasProperties to set
	 */
	public void setHasProperties(boolean hasProperties) {
		this.hasProperties = hasProperties;
	}
	
	/**
	 * @return the allowedConnections
	 */
	public String[] getAllowedConnections() {
		return allowedConnections;
	}

	/**
	 * @param allowedConnections the allowedConnections to set
	 */
	public void setAllowedConnections(String[] allowedConnections) {
		this.allowedConnections = allowedConnections;
	}
	
	/**
	 * @return the hint
	 */
	public String getHint() {
		return hint;
	}

	/**
	 * @param hint the hint to set
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}

	/**
	 * Metodo para generar a mano el json correspondiente
	 * @return
	 */
	public String toJSON(){
		String dataObj = "\"%s\":%s";
		StringBuilder aux = new StringBuilder("{");
		aux.append(String.format(dataObj, "attributes",this.attributes.toJSON())).append(",");
		aux.append(String.format(dataObj, "properties",this.properties.toJSON()));
		aux.append("}");
		
		return aux.toString();
	}
	
}
