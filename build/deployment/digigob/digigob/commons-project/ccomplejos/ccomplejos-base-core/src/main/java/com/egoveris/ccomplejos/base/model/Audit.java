package com.egoveris.ccomplejos.base.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_AUDIT")
public class Audit extends AbstractCComplejoJPA {

	@Column(name="UPDATE_BY")
	String updatedBy;

	@Column(name="UPDATE_DATE")
	Date updatedDate;

 @Column(name="CREATE_BY")
 String createdBy;
 
 @Column(name="CREATE_DATE")
 Date createdDate;
 

public String getUpdatedBy() {
  return updatedBy;
}

public void setUpdatedBy(String updatedBy) {
  this.updatedBy = updatedBy;
}

public Date getUpdatedDate() {
  return updatedDate;
}

public void setUpdatedDate(Date updatedDate) {
  this.updatedDate = updatedDate;
}

public String getCreatedBy() {
  return createdBy;
}

public void setCreatedBy(String createdBy) {
  this.createdBy = createdBy;
}

public Date getCreatedDate() {
  return createdDate;
}

public void setCreatedDate(Date createdDate) {
  this.createdDate = createdDate;
}
 
}
