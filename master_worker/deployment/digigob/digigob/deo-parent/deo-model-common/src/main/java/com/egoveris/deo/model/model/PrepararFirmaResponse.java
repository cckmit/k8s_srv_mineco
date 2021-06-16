package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Calendar;

public class PrepararFirmaResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String fieldName;
  private byte[] attributeBytesToSign;
  private byte[] attributeBytesToSignRectif;
  private byte[] hash;
  private byte[] hashRect;
  private Calendar signDate;

  public PrepararFirmaResponse(String fieldName, byte[] attributeBytesToSign,
      byte[] attributeBytesToSignRectif, byte[] hash, byte[] hashRect, Calendar signDate) {
    this.hash = hash;
    this.setHashRect(hashRect);
    this.signDate = signDate;
    this.fieldName = fieldName;
    this.attributeBytesToSign = attributeBytesToSign;
    this.setAttributeBytesToSignRectif(attributeBytesToSignRectif);
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public byte[] getAttributeBytesToSign() {
    return attributeBytesToSign;
  }

  public void setAttributeBytesToSign(byte[] attributeBytesToSign) {
    this.attributeBytesToSign = attributeBytesToSign;
  }

  public Calendar getSignDate() {
    return signDate;
  }

  public void setSignDate(Calendar signDate) {
    this.signDate = signDate;
  }

  public byte[] getHash() {
    return hash;
  }

  public void setHash(byte[] hash) {
    this.hash = hash;
  }

  public byte[] getAttributeBytesToSignRectif() {
    return attributeBytesToSignRectif;
  }

  public void setAttributeBytesToSignRectif(byte[] attributeBytesToSignRectif) {
    this.attributeBytesToSignRectif = attributeBytesToSignRectif;
  }

  public byte[] getHashRect() {
    return hashRect;
  }

  public void setHashRect(byte[] hashRect) {
    this.hashRect = hashRect;
  }
}