package com.egoveris.ccomplejos.base.model;

import java.util.Comparator;

public class ProductAttributesDTO extends AuditDTO
    implements Comparator<ProductAttributesDTO>, Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = 2102539321032759486L;

  protected Long productAttributeId;
  protected Long productId;
  protected String productCode;
  protected Long attributeSeq;
  protected String attributeCodeName;
  protected String attributeCodeNameESP;
  protected String attributeValue;
  protected String attributeDataType;
  protected String attributeDataSize;
  protected String masterCodeType;
  protected String isFixed;
  protected String isMandatory;
  protected String status;
  protected String destinacionEnum;
  protected String attributeUniqueCode;

  @Override
  public int compare(ProductAttributesDTO o1, ProductAttributesDTO o2) {
    // TODO Auto-generated method stub
    return 0;
  }

  public Long getProductAttributeId() {
    return productAttributeId;
  }

  public void setProductAttributeId(Long productAttributeId) {
    this.productAttributeId = productAttributeId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
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
}
