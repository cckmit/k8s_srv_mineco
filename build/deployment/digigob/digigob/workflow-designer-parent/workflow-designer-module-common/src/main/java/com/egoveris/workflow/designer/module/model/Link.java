/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;


public class Link implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5929910995683356534L;
	private String id;
	private String origin;
	private String destiny;
	private boolean doubleLink;
	
	/**
	 * @param origin
	 * @param destiny
	 */
	public Link(String id, String origin, String destiny) {
		this.id = id;
		this.origin = origin;
		this.destiny = destiny;
	}

	/**
	 * Default constructor 
	 */
	public Link() {
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the destiny
	 */
	public String getDestiny() {
		return destiny;
	}

	/**
	 * @param destiny the destiny to set
	 */
	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	/**
	 * @return the doubleLink
	 */
	public boolean isDoubleLink() {
		return doubleLink;
	}

	/**
	 * @param doubleLink the doubleLink to set
	 */
	public void setDoubleLink(boolean doubleLink) {
		this.doubleLink = doubleLink;
	}

	/**
	 * Metodo para generar a mano el json correspondiente
	 * @return
	 */
	public String toJSON(){
		String dataStr = "\"%s\":\"%s\"";
		StringBuilder aux = new StringBuilder("{");
		aux.append(String.format(dataStr, "id",this.id)).append(",");
		aux.append(String.format(dataStr, "origin",this.origin)).append(",");
		aux.append(String.format(dataStr, "destinty",this.destiny)).append(",");
		aux.append(String.format(dataStr, "doubleLink",this.doubleLink));
		aux.append("}");
		
		return aux.toString();
	}
}
