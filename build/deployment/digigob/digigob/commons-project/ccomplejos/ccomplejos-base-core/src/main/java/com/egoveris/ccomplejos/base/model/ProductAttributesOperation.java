package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_PRODUCT_ATTRIBUTES_OPERATION")
public class ProductAttributesOperation extends AbstractCComplejoJPA {

	@Column(name = "PRODUCT_ID")
	protected Long productId;
	@Column(name = "ATTRIBUTE_SEQ")
	protected Long attributeSeq;
	@Column(name = "ATTRIBUTE_VALUE")
	protected String attributeValue;
	@Column(name = "PRODUCT_ATTRIBUTE_OPERACION_ID")
	protected Long productAttributeOperacionId;
	@Column(name = "PRODUCT_ATTRIBUTE_ID")
	protected Long productAttributeId;
	@Column(name = "PRODUCT_CODE")
	protected String productCode;
	@Column(name = "ATTRIBUTE_CODE_NAME")
	protected String attributeCodeName;
	

	@ManyToOne
	@JoinColumn(name = "PRODUCTO_OPERACION_ID", referencedColumnName = "id")
	protected ProductOperation productOperation;
	
	/**
	 * @return the productOperation
	 */
	public ProductOperation getProductOperation() {
		return productOperation;
	}
	/**
	 * @param productOperation the productOperation to set
	 */
	public void setProductOperation(ProductOperation productOperation) {
		this.productOperation = productOperation;
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
