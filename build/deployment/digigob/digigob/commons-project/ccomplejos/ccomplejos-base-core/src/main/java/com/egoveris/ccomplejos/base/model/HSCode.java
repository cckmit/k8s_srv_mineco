package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Table(name = "cc_hscode")
public class HSCode extends AbstractCComplejoJPA {

  @Column(name = "SUBHEADING_2")
  Long subheading2;

  @Column(name = "DESCRIPTION")
  String description;

  @Column(name = "DESCRIPTION_OTHER")
  String descriptionOther;

  @Column(name = "CREATED_BY")
  String createdBy;

  @Column(name = "MODIFIED_BY")
  String modifiedBy;

  @Column(name = "MODIFIED_DATE")
  Date modifiedDate;

  @Column(name = "CONTROLLED_INDICATOR")
  String controlledIndicator;

  @Column(name = "HS_CODE_ID")
  Long hsCodeId;

  @Column(name = "HS_CODE")
  String hsCode;

  @Column(name = "DUTIABLE")
  String dutiable;

  @Column(name = "UOM_CODE")
  String uomCode;

  @Column(name = "SUBHEADING_ID")
  Long subheadingId;
  
  @OneToOne(cascade=CascadeType.ALL)
  @JoinColumn(name="CHAPTER")
  Chapter chapterDto;
  
  @OneToOne(cascade=CascadeType.ALL)
  @JoinColumn(name="HEADING")
  Heading headingDto;
  
  @OneToOne(cascade=CascadeType.ALL)
  @JoinColumn(name="SUBHEADING")
  SubHeading subHeadingDto;

  @Column(name = "YEAR")
  Long year;

  @Column(name = "EFFECTIVE_DATE")
  Date effectiveDate;

  @Column(name = "CREATED_DATE")
  Date createdDate;

  @Column(name = "STATUS")
  String status;

  public Long getSubheading2() {
    return subheading2;
  }

  public void setSubheading2(Long subheading2) {
    this.subheading2 = subheading2;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescriptionOther() {
    return descriptionOther;
  }

  public void setDescriptionOther(String descriptionOther) {
    this.descriptionOther = descriptionOther;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Date getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public String getControlledIndicator() {
    return controlledIndicator;
  }

  public void setControlledIndicator(String controlledIndicator) {
    this.controlledIndicator = controlledIndicator;
  }

  public Long getHsCodeId() {
    return hsCodeId;
  }

  public void setHsCodeId(Long hsCodeId) {
    this.hsCodeId = hsCodeId;
  }

  public String getHsCode() {
    return hsCode;
  }

  public void setHsCode(String hsCode) {
    this.hsCode = hsCode;
  }

  public String getDutiable() {
    return dutiable;
  }

  public void setDutiable(String dutiable) {
    this.dutiable = dutiable;
  }

  public String getUomCode() {
    return uomCode;
  }

  public void setUomCode(String uomCode) {
    this.uomCode = uomCode;
  }

  public Long getSubheadingId() {
    return subheadingId;
  }

  public void setSubheadingId(Long subheadingId) {
    this.subheadingId = subheadingId;
  }

  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }

  public Date getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(Date effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Chapter getChapterDto() {
    return chapterDto;
  }

  public void setChapterDto(Chapter chapterDto) {
    this.chapterDto = chapterDto;
  }

  public Heading getHeadingDto() {
    return headingDto;
  }

  public void setHeadingDto(Heading headingDto) {
    this.headingDto = headingDto;
  }

  public SubHeading getSubHeadingDto() {
    return subHeadingDto;
  }

  public void setSubHeadingDto(SubHeading subHeadingDto) {
    this.subHeadingDto = subHeadingDto;
  }
  
  

}
