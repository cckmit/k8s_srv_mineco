package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_CHAPTER")
public class Chapter extends AbstractCComplejoJPA {

  @Column(name = "ID_CHAPTER")
  Long chapterId;

  @Column(name = "CODE_CHAPTER")
  String chapterCode;

  @Column(name = "DESC_CHAPTER")
  String chapterDesc;

  @Column(name = "DESC_SP_CHAPTER")
  String chapterDescSP;

  @Column(name = "STATUS_CHAPTER")
  String chapterStatus;

  @Column(name = "YEAR")
  Long year;

  public Long getChapterId() {
    return chapterId;
  }

  public void setChapterId(Long chapterId) {
    this.chapterId = chapterId;
  }

  public String getChapterCode() {
    return chapterCode;
  }

  public void setChapterCode(String chapterCode) {
    this.chapterCode = chapterCode;
  }

  public String getChapterDesc() {
    return chapterDesc;
  }

  public void setChapterDesc(String chapterDesc) {
    this.chapterDesc = chapterDesc;
  }

  public String getChapterDescSP() {
    return chapterDescSP;
  }

  public void setChapterDescSP(String chapterDescSP) {
    this.chapterDescSP = chapterDescSP;
  }

  public String getChapterStatus() {
    return chapterStatus;
  }

  public void setChapterStatus(String chapterStatus) {
    this.chapterStatus = chapterStatus;
  }

  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }

}
