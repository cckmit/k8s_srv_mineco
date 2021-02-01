package com.egoveris.te.base.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.te.base.model.rest.DocumentTypeWsDTO;

public class DocumentoWsDTO {
	private static final Logger logger = LoggerFactory.getLogger(DocumentoWsDTO.class);
	
	private String name;
	private String code;
	private DocumentTypeWsDTO documentType;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	public DocumentTypeWsDTO getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentTypeWsDTO documentType) {
		this.documentType = documentType;
	}

	
	
	
}
