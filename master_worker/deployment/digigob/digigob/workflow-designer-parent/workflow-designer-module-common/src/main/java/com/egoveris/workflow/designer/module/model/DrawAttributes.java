/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;

public class DrawAttributes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3959576277312306316L;
	private int angle;
	private String id;
	private String name;
	private Position position;
	private Size size;
	private String type;
	private int z;
	/**
	 * @return the angle
	 */
	public int getAngle() {
		return angle;
	}
	/**
	 * @param angle the angle to set
	 */
	public void setAngle(int angle) {
		this.angle = angle;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the position
	 */
	public Position getPosition() {
		if (position==null) {
			position=new Position();
		}
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	/**
	 * @return the size
	 */
	public Size getSize() {
		if (size==null) {
			size = new Size();
		}
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(Size size) {
		this.size = size;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
	/**
	 * @param z the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
	
	
	/**
	 * Metodo para generar a mano el json correspondiente
	 * @return
	 */
	public String toJSON(){
		String dataStr = "\"%s\":\"%s\"";
		String dataObj = "\"%s\":%s";
		StringBuilder aux = new StringBuilder("{");
		aux.append(String.format(dataStr, "angle",this.angle)).append(",");
		aux.append(String.format(dataStr, "id",this.id)).append(",");
		aux.append(String.format(dataStr, "name",this.name)).append(",");
		aux.append(String.format(dataObj, "position",this.position.toJSON())).append(",");
		aux.append(String.format(dataObj, "size",this.size.toJSON())).append(",");
		aux.append(String.format(dataStr, "z",this.z));
		aux.append("}");
		
		return aux.toString();
	}
	
}
