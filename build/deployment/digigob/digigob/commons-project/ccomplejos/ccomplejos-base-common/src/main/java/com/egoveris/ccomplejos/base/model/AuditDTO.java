package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class AuditDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String updatedBy;
	protected Date updatedDate;
	protected String createdBy;
	protected Date createdDate;
	
	
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
