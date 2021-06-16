package com.egoveris.te.base.model.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentTypeWsDTO {
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentTypeWsDTO.class);
	
	// acronym
	private String acronym;
	// desc
	private String desc;
	/**
	 * @return the acronym
	 */
	public String getAcronym() {
		return acronym;
	}
	/**
	 * @param acronym the acronym to set
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	 
}
