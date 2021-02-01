package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Calendar;

public class FirmaTokenRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  // assigne de la tarea
  private String currentUser;
  private String executionId;
  private String fieldName;

  // firma con token
  private byte[] certs;
  private Calendar signDate;
  private byte[] signedBytes;
  private byte[] hash;
  private byte[] signedBytesRect;
  private byte[] hashRect;

  public FirmaTokenRequest(String executionId, String currentUser, String fieldName) {
    this(executionId, currentUser, fieldName, null, null, null, null, null, null);
  }

  public FirmaTokenRequest(String executionId, String currentUser, String fieldName, byte[] certs,
      byte[] signedBytes, byte[] signedBytesRect, byte[] hash, byte[] hashRect,
      Calendar signDate) {
    this.signedBytes = signedBytes;
    this.signedBytesRect = signedBytesRect;
    this.fieldName = fieldName;
    this.currentUser = currentUser;
    this.executionId = executionId;
    this.certs = certs;
    this.hash = hash;
    this.hashRect = hashRect;
    this.signDate = signDate;
  }

  public String getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(String currentUser) {
    this.currentUser = currentUser;
  }

  public String getExecutionId() {
    return executionId;
  }

  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }

  public byte[] getCerts() {
    return certs;
  }

  public void setCerts(byte[] certs) {
    this.certs = certs;
  }

  public byte[] getSignedBytes() {
    return signedBytes;
  }

  public void setSignedBytes(byte[] signedBytes) {
    this.signedBytes = signedBytes;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public byte[] getHash() {
    return hash;
  }

  public void setHash(byte[] hash) {
    this.hash = hash;
  }

  public Calendar getSignDate() {
    return signDate;
  }

  public void setSignDate(Calendar signDate) {
    this.signDate = signDate;
  }

  public byte[] getSignedBytesRect() {
    return signedBytesRect;
  }

  public void setSignedBytesRect(byte[] signedBytesRect) {
    this.signedBytesRect = signedBytesRect;
  }

  public byte[] getHashRect() {
    return hashRect;
  }

  public void setHashRect(byte[] hashRect) {
    this.hashRect = hashRect;
  }
}