/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

public class Size {
	private Integer height;
	private Integer width;
	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	/**
	 * 
	 * @return
	 */
	public String toJSON(){
		String dataStr = "\"%s\":\"%s\"";
		StringBuilder aux = new StringBuilder("{");
		aux.append(String.format(dataStr, "height",this.height)).append(",");
		aux.append(String.format(dataStr, "width",this.width));
		aux.append("}");
		
		return aux.toString();
	}
	
}
