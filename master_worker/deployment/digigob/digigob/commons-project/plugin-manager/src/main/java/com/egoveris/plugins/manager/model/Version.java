/**
 * 
 */
package com.egoveris.plugins.manager.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author difarias
 *
 */
@Entity
public class Version implements Serializable {
	@Id @GeneratedValue
	private Long id;
	private String versionName;
	private int primary;
	private int secondary;
	private int tertiary;
	private String signal;
	private List<String> files;
	private boolean active;
	
	public Version(String name) {
		this.versionName = name;
		this.primary=1;
		this.secondary=0;
		this.tertiary=0;
		this.files = new ArrayList<String>();
		this.active=false;
	}
	
	public void reset() {
		this.primary=1;
		this.secondary=0;
		this.tertiary=0;
		this.files.clear();
		this.active=false;
	}
	
	/**
	 * @return the files
	 */
	public List<String> getFiles() {
		if (files==null) {
			files = new ArrayList<String>();
		}
		return files;
	}

	public Version inc() {
		Version ver = new Version(this.versionName);
		ver.primary=this.primary;
		ver.secondary=this.secondary;
		ver.tertiary=this.tertiary;
		ver.files = new ArrayList<String>();
		ver.active=false;
		
		ver.tertiary++;
		if (ver.tertiary>10) {
			ver.tertiary=0;
			ver.secondary++;
		}
		
		if (ver.secondary>10) {
			ver.secondary=0;
			ver.primary++;
		}
		
		ver.files.addAll(this.files);
		
		return ver;
	}
	
	/**
	 * @return the signal
	 */
	public String getSignal() {
		return signal;
	}

	/**
	 * @param signal the signal to set
	 */
	public void setSignal(String signal) {
		this.signal = signal;
	}

	public String getName() {
		String format="%s_%d.%d.%d";
		return String.format(format,this.versionName,this.primary,this.secondary,this.tertiary);
	}

	public String getNumber() {
		String format="%d.%d.%d";
		return String.format(format,this.primary,this.secondary,this.tertiary);
	}

	public void setFiles(List<String> pluginJar) {
		this.files.addAll(pluginJar);
	}
	
	public String getSnapshotDir() {
		return String.format("SNAPSHOT_%s", getName());
	}
	
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String aux = getName() + " \n";
		
		aux += "Files in version: \n";
		
		for (String filename: getFiles()) {
			aux += "- "+filename+"\n";
		}
		return aux;
	}
}
