package com.egoveris.ccomplejos.base.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cc_product")
public class Product extends AbstractCComplejoJPA {

  @Column(name = "PRODUCT_ID")
  Long productId;

  @Column(name = "PRODUCT_CODE")
  String productCode;

  @Column(name = "HS_CODE")
  String hsCode;

  @Column(name = "SSPP")
  String ssppEnum;

  @Column(name = "COMMON_REG_NAME")
  String commonRegName;

  @Column(name = "COMON_REG_NAME_SPA")
  String commonRegNameSpanish;

  @Column(name = "PROD_DESC")
  String prodDesc;

  @Column(name = "PROD_DESC_SPA")
  String prodDescSpanish;

  @Column(name = "SCIENTIFIC_NAME")
  String scientificName;

  @Column(name = "BUSINESS_NAME")
  String businessName;

  @Column(name = "BUSINESS_NAME_SPA")
  String businessNameSpanish;

  @Column(name = "PROD_CODE_SSPP")
  String prodCodeSSPP;

  @Column(name = "PHYSICAL_STATE")
  String physicalState;

  @Column(name = "STATUS")
  String status;

  @Column(name = "ALERT_MESSAGE")
  String alertMessage;

  @Column(name = "DESTINACION")
  String destinacionEnum;

  @Column(name = "PRODUCT_VERSION")
  String productVersion;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  List<ProductAttributes> productAttributes;

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

	public List<ProductAttributes> getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(List<ProductAttributes> productAttributes) {
		this.productAttributes = productAttributes;
	}

}
