package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;

public class SubHeadingDTO extends AbstractCComplejoDTO implements Serializable {
 
  private static final long serialVersionUID = 4057335435703776327L;
  
  protected Long subheadingId;
  protected HeadingDTO headingDTO;
  protected List<HSCodeDTO> hscode;
  protected Long headingId;
  protected String subheadingCode;
  protected String subheadingDesc;
  protected String subheadingdescSP;
  protected String subheadingStatus;
  protected String subheadingText;
  protected Long year;

  public SubHeadingDTO() {
  }

  public SubHeadingDTO(Long id, String code) {    
    setSubheadingId(id);
    setSubheadingCode(code);
  }

  public Long getSubheadingId() {
    return this.subheadingId;
  }

  public void setSubheadingId(Long subheadingId) {
    this.subheadingId = subheadingId;
  }

  public HeadingDTO getHeadingDTO() {
    return this.headingDTO;
  }

  public void setHeadingDTO(HeadingDTO headingDTO) {
    this.headingDTO = headingDTO;
  }

  public Long getHeadingId() {
    return this.headingId;
  }

  public void setHeadingId(Long headingId) {
    this.headingId = headingId;
  }

  public String getSubheadingCode() {
    return this.subheadingCode;
  }

  public void setSubheadingCode(String subheadingCode) {
    this.subheadingCode = subheadingCode;
  }

  public String getSubheadingDesc() {
    return this.subheadingDesc;
  }

  public void setSubheadingDesc(String subheadingDesc) {
    this.subheadingDesc = subheadingDesc;
  }

  public String getSubheadingdescSP() {
    return this.subheadingdescSP;
  }

  public void setSubheadingdescSP(String subheadingdescSP) {
    this.subheadingdescSP = subheadingdescSP;
  }

  public String getSubheadingStatus() {
    return this.subheadingStatus;
  }

  public void setSubheadingStatus(String subheadingStatus) {
    this.subheadingStatus = subheadingStatus;
  }

  public List<HSCodeDTO> getHscode() {
    return this.hscode;
  }

  public void setHscode(List<HSCodeDTO> hscode) {
    this.hscode = hscode;
  }

  public String getSubheadingText() {
    return this.subheadingText;
  }

  public void setSubheadingText(String subheadingText) {
    this.subheadingText = subheadingText;
  }

  public Long getYear() {
    return this.year;
  }

  public void setYear(Long year) {
    this.year = year;
  }

  public String toString() {
    StringBuffer model = new StringBuffer("\n***======= SubHeading DTO =======***");

    model.append("\n Sub Heading Code: ");
    model.append(this.subheadingCode);
    model.append("\n Sub Heading Description : ");
    model.append(this.subheadingDesc);
    model.append("\n Year : ");
    model.append(this.year);

    return model.toString();
  }
}
