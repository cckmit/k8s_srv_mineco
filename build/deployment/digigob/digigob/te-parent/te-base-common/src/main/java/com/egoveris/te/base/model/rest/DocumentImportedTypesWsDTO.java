package com.egoveris.te.base.model.rest;


/**
 * The Class DocumentImportedTypesWsDTO.
 */
public class DocumentImportedTypesWsDTO {

	/** The acronym. */
	// Acronimo
	private String acronym;

	/** The desc. */
	// Descripcion
	private String desc;

	public DocumentImportedTypesWsDTO(){
		
	}
	
	
	public DocumentImportedTypesWsDTO(String acronym, String desc) {
		super();
		this.acronym = acronym;
		this.desc = desc;
	}

	/**
	 * Gets the acronym.
	 *
	 * @return the acronym
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * Sets the acronym.
	 *
	 * @param acronym
	 *            the new acronym
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets the desc.
	 *
	 * @param desc
	 *            the new desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}


}
