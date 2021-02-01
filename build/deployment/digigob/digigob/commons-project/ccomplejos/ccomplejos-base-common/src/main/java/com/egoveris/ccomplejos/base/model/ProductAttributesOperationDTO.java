package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ProductAttributesOperationDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long productoOperacionId;
	protected Long productId;
	protected Long attributeSeq;
	protected String attributeValue;
	protected Long productAttributeOperacionId;
	protected Long productAttributeId;
	protected String productCode;
	protected String attributeCodeName;

	public Long getProductoOperacionId() {
		return productoOperacionId;
	}

	public void setProductoOperacionId(Long productoOperacionId) {
		this.productoOperacionId = productoOperacionId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getAttributeSeq() {
		return attributeSeq;
	}

	public void setAttributeSeq(Long attributeSeq) {
		this.attributeSeq = attributeSeq;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public Long getProductAttributeOperacionId() {
		return productAttributeOperacionId;
	}

	public void setProductAttributeOperacionId(Long productAttributeOperacionId) {
		this.productAttributeOperacionId = productAttributeOperacionId;
	}

	public Long getProductAttributeId() {
		return productAttributeId;
	}

	public void setProductAttributeId(Long productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getAttributeCodeName() {
		return attributeCodeName;
	}

	public void setAttributeCodeName(String attributeCodeName) {
		this.attributeCodeName = attributeCodeName;
	}

}
