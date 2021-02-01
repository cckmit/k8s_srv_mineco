package com.egoveris.deo.model.model;

import java.io.Serializable;

public class ExtensionMimetypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6849905919051367768L;	
	
	private ExtensionMimetypePKDTO extensionMimetypePK;

	public ExtensionMimetypePKDTO getExtensionMimetypePK() {
		return extensionMimetypePK;
	}

	public void setExtensionMimetypePK(ExtensionMimetypePKDTO extensionMimetypePK) {
		this.extensionMimetypePK = extensionMimetypePK;
	}
}
