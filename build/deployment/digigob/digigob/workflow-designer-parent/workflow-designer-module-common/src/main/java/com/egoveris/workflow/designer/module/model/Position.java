/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

public class Position {
	private Integer x;
	private Integer y;
	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(Integer x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * 
	 * @return
	 */
	public String toJSON(){
		String dataStr = "\"%s\":\"%s\"";
		StringBuilder aux = new StringBuilder("{");
		aux.append(String.format(dataStr, "x",this.x)).append(",");
		aux.append(String.format(dataStr, "y",this.y));
		aux.append("}");
		
		return aux.toString();
	}
	
}
