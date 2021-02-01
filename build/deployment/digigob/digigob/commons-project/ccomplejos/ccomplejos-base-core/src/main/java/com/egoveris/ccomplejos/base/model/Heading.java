package com.egoveris.ccomplejos.base.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_HEADING")
public class Heading extends AbstractCComplejoJPA {

  @Column(name = "ID_HEADING")
  Long headingId;
  
  @OneToOne(cascade=CascadeType.ALL)
  @JoinColumn(name="CHAPTER")
  Chapter chapterDTO;

  @Column(name = "ID_CHAPTER")
  Long chapterId;

  @Column(name = "CODE_HEADING")
  String headingCode;

  @Column(name = "DESC_HEADING")
  String headingDesc;

  @Column(name = "DESC_SP_HEADING")
  String headingdescSP;

  @Column(name = "STATUS_HEADING")
  String headingStatus;

  public Long getHeadingId() {
    return headingId;
  }

  public void setHeadingId(Long headingId) {
    this.headingId = headingId;
  }

  public Long getChapterId() {
    return chapterId;
  }

  public void setChapterId(Long chapterId) {
    this.chapterId = chapterId;
  }

  public String getHeadingCode() {
    return headingCode;
  }

  public void setHeadingCode(String headingCode) {
    this.headingCode = headingCode;
  }

  public String getHeadingDesc() {
    return headingDesc;
  }

  public void setHeadingDesc(String headingDesc) {
    this.headingDesc = headingDesc;
  }

  public String getHeadingdescSP() {
    return headingdescSP;
  }

  public void setHeadingdescSP(String headingdescSP) {
    this.headingdescSP = headingdescSP;
  }

  public String getHeadingStatus() {
    return headingStatus;
  }

  public void setHeadingStatus(String headingStatus) {
    this.headingStatus = headingStatus;
  }

  public Chapter getChapterDTO() {
    return chapterDTO;
  }

  public void setChapterDTO(Chapter chapterDTO) {
    this.chapterDTO = chapterDTO;
  }
  
  

}
