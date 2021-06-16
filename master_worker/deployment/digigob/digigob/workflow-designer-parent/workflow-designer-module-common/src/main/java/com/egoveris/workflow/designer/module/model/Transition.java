/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

/**
 * @author difarias
 *
 */
public class Transition {
	private String g;
	private String to;
	
	/**
	 * @return the g
	 */
	public String getG() {
		return g;
	}
	/**
	 * @param g the g to set
	 */
	public void setG(String g) {
		this.g = g;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String aux = "        <transition g=\""+this.g+"\" name=\""+this.to+"\" to=\""+this.to+"\"/>\n";
		return aux;
	}
	
	
}
