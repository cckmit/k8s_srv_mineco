package com.egoveris.ccomplejos.base.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cc_subheading")
public class SubHeading extends AbstractCComplejoJPA {

  @Column(name = "SUBHEADING_ID")
  Long subheadingId;
  
  @OneToOne(cascade=CascadeType.ALL)
  @JoinColumn(name="HEADING")
  Heading headingDTO;

  @Column(name = "HEADING_ID")
  Long headingId;

  @Column(name = "SUBHEADING_CODE")
  String subheadingCode;

  @Column(name = "SUBHEADING_DESC")
  String subheadingDesc;

  @Column(name = "SUBHEADING_DESC_SP")
  String subheadingdescSP;

  @Column(name = "SUBHEADING_STATUS")
  String subheadingStatus;

  @Column(name = "SUBHEADING_TEXT")
  String subheadingText;

  @Column(name = "YEAR")
  Long year;

  public Long getSubheadingId() {
    return subheadingId;
  }

  public void setSubheadingId(Long subheadingId) {
    this.subheadingId = subheadingId;
  }

  public Long getHeadingId() {
    return headingId;
  }

  public void setHeadingId(Long headingId) {
    this.headingId = headingId;
  }

  public String getSubheadingCode() {
    return subheadingCode;
  }

  public void setSubheadingCode(String subheadingCode) {
    this.subheadingCode = subheadingCode;
  }

  public String getSubheadingDesc() {
    return subheadingDesc;
  }

  public void setSubheadingDesc(String subheadingDesc) {
    this.subheadingDesc = subheadingDesc;
  }

  public String getSubheadingdescSP() {
    return subheadingdescSP;
  }

  public void setSubheadingdescSP(String subheadingdescSP) {
    this.subheadingdescSP = subheadingdescSP;
  }

  public String getSubheadingStatus() {
    return subheadingStatus;
  }

  public void setSubheadingStatus(String subheadingStatus) {
    this.subheadingStatus = subheadingStatus;
  }

  public String getSubheadingText() {
    return subheadingText;
  }

  public void setSubheadingText(String subheadingText) {
    this.subheadingText = subheadingText;
  }

  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }

  public Heading getHeadingDTO() {
    return headingDTO;
  }

  public void setHeadingDTO(Heading headingDTO) {
    this.headingDTO = headingDTO;
  }
  
  

}
