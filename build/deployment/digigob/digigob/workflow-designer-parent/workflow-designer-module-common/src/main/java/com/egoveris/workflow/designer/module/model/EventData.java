/**
 * 
 */
package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;

/**
 * @author difarias
 *
 */
public class EventData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3841133291146100769L;
	private String commandName;
	private String jsonData;
	/**
	 * @return the commandName
	 */
	public String getCommandName() {
		return commandName;
	}
	/**
	 * @param commandName the commandName to set
	 */
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	/**
	 * @return the jsonData
	 */
	public String getJsonData() {
		return jsonData;
	}
	/**
	 * @param jsonData the jsonData to set
	 */
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
}
