package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_PRODUCT_ATTRIBUTES")
public class ProductAttributes extends AbstractCComplejoJPA {

	@Column(name = "PRODUCT_ATTRIBUTE_ID")
	Long productAttributeId;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "id")
	Product product;

	@Column(name = "PRODUCT_CODE")
	String productCode;

	@Column(name = "ATTRIBUTE_SEQUENCE")
	Long attributeSeq;

	@Column(name = "ATTRIBUTE_CODE_NAME")
	String attributeCodeName;

	@Column(name = "ATTRIBUTE_CODE_NAME_ESP")
	String attributeCodeNameESP;

	@Column(name = "ATTRIBUTE_VALUE")
	String attributeValue;

	@Column(name = "ATTRIBUTE_DATA_TYPE")
	String attributeDataType;

	@Column(name = "ATTRIBUTE_DATA_SIZE")
	String attributeDataSize;

	@Column(name = "MASTER_CODE_TYPE")
	String masterCodeType;

	@Column(name = "IS_FIXED")
	String isFixed;

	@Column(name = "IS_MANDATORY")
	String isMandatory;

	@Column(name = "STATUS")
	String status;

	@Column(name = "DESTINACION")
	String destinacionEnum;

	@Column(name = "ATTRIBUTE_UNIQUE_CODE")
	String attributeUniqueCode;

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

	public Long getAttributeSeq() {
		return attributeSeq;
	}

	public void setAttributeSeq(Long attributeSeq) {
		this.attributeSeq = attributeSeq;
	}

	public String getAttributeCodeName() {
		return attributeCodeName;
	}

	public void setAttributeCodeName(String attributeCodeName) {
		this.attributeCodeName = attributeCodeName;
	}

	public String getAttributeCodeNameESP() {
		return attributeCodeNameESP;
	}

	public void setAttributeCodeNameESP(String attributeCodeNameESP) {
		this.attributeCodeNameESP = attributeCodeNameESP;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeDataType() {
		return attributeDataType;
	}

	public void setAttributeDataType(String attributeDataType) {
		this.attributeDataType = attributeDataType;
	}

	public String getAttributeDataSize() {
		return attributeDataSize;
	}

	public void setAttributeDataSize(String attributeDataSize) {
		this.attributeDataSize = attributeDataSize;
	}

	public String getMasterCodeType() {
		return masterCodeType;
	}

	public void setMasterCodeType(String masterCodeType) {
		this.masterCodeType = masterCodeType;
	}

	public String getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(String isFixed) {
		this.isFixed = isFixed;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDestinacionEnum() {
		return destinacionEnum;
	}

	public void setDestinacionEnum(String destinacionEnum) {
		this.destinacionEnum = destinacionEnum;
	}

	public String getAttributeUniqueCode() {
		return attributeUniqueCode;
	}

	public void setAttributeUniqueCode(String attributeUniqueCode) {
		this.attributeUniqueCode = attributeUniqueCode;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
