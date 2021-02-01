package com.egoveris.deo.model.model;

/**
 * * eNUM REPRESENTING THE AVALIABLE METHOS TO GENERATE A DOCUMENT * @author
 * lmancild *
 */
public enum ProductionEnum {

	FREE(1), TEMPLATE(3), IMPORT(2), TEMPLATE_IMPORT(4);

	private Integer value;

	private ProductionEnum(Integer value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

}
