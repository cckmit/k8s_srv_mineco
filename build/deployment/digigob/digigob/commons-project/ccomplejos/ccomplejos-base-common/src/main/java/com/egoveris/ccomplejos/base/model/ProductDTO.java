package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;

public class ProductDTO extends AuditDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  protected Long productId;

  protected String productCode;

  protected String hsCode;

  protected String ssppEnum;

  protected String commonRegName;

  protected String commonRegNameSpanish;

  protected String prodDesc;

  protected String prodDescSpanish;

  protected String scientificName;

  protected String businessName;

  protected String businessNameSpanish;

  protected String prodCodeSSPP;

  protected String physicalState;

  protected String status;

  protected String alertMessage;

  protected String destinacionEnum;

  protected String productVersion;

  protected List<ProductAttributesDTO> productAttributesDTO;

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

  public String getHsCode() {
    return hsCode;
  }

  public void setHsCode(String hsCode) {
    this.hsCode = hsCode;
  }

  public String getSsppEnum() {
    return ssppEnum;
  }

  public void setSsppEnum(String ssppEnum) {
    this.ssppEnum = ssppEnum;
  }

  public String getCommonRegName() {
    return commonRegName;
  }

  public void setCommonRegName(String commonRegName) {
    this.commonRegName = commonRegName;
  }

  public String getCommonRegNameSpanish() {
    return commonRegNameSpanish;
  }

  public void setCommonRegNameSpanish(String commonRegNameSpanish) {
    this.commonRegNameSpanish = commonRegNameSpanish;
  }

  public String getProdDesc() {
    return prodDesc;
  }

  public void setProdDesc(String prodDesc) {
    this.prodDesc = prodDesc;
  }

  public String getProdDescSpanish() {
    return prodDescSpanish;
  }

  public void setProdDescSpanish(String prodDescSpanish) {
    this.prodDescSpanish = prodDescSpanish;
  }

  public String getScientificName() {
    return scientificName;
  }

  public void setScientificName(String scientificName) {
    this.scientificName = scientificName;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getBusinessNameSpanish() {
    return businessNameSpanish;
  }

  public void setBusinessNameSpanish(String businessNameSpanish) {
    this.businessNameSpanish = businessNameSpanish;
  }

  public String getProdCodeSSPP() {
    return prodCodeSSPP;
  }

  public void setProdCodeSSPP(String prodCodeSSPP) {
    this.prodCodeSSPP = prodCodeSSPP;
  }

  public String getPhysicalState() {
    return physicalState;
  }

  public void setPhysicalState(String physicalState) {
    this.physicalState = physicalState;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAlertMessage() {
    return alertMessage;
  }

  public void setAlertMessage(String alertMessage) {
    this.alertMessage = alertMessage;
  }

  public String getDestinacionEnum() {
    return destinacionEnum;
  }

  public void setDestinacionEnum(String destinacionEnum) {
    this.destinacionEnum = destinacionEnum;
  }

  public String getProductVersion() {
    return productVersion;
  }

  public void setProductVersion(String productVersion) {
    this.productVersion = productVersion;
  }

  public List<ProductAttributesDTO> getProductAttributesDTO() {
    return productAttributesDTO;
  }

  public void setProductAttributesDTO(List<ProductAttributesDTO> productAttributesDTO) {
    this.productAttributesDTO = productAttributesDTO;
  }

}
