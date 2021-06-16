/**
 * 
 */
package com.egoveris.plugins.manager.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author difarias
 *
 */
public class PluginInfo {
	private String jarFilename;
	private String name;
	private String author;
	private String version;
	private String description;
	private String type;
	private String activationClass;
	private Map<String, String> dataManifest;

	/**
	 * @return the jarFilename
	 */
	public String getJarFilename() {
		return jarFilename;
	}
	/**
	 * @param jarFilename the jarFilename to set
	 */
	public void setJarFilename(String jarFilename) {
		this.jarFilename = jarFilename;
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
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the activationClass
	 */
	public String getActivationClass() {
		return activationClass;
	}
	/**
	 * @param activationClass the activationClass to set
	 */
	public void setActivationClass(String activationClass) {
		this.activationClass = activationClass;
	}
	/**
	 * @return the dataManifest
	 */
	public Map<String, String> getDataManifest() {
		if (dataManifest==null) {
			dataManifest = new HashMap<String,String>();
		}
		return dataManifest;
	}
	/**
	 * @param dataManifest the dataManifest to set
	 */
	public void setDataManifest(Map<String, String> dataManifest) {
		this.dataManifest = dataManifest;
	}
}
