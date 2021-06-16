package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class HSCodeDTO extends AuditDTO implements Serializable {
 
 private static final long serialVersionUID = 573709273660183670L;
 
 protected Long subheading2;
	protected String description;
	protected String descriptionOther;
	protected String createdBy;
	protected String modifiedBy;
	protected Date modifiedDate;
	protected String controlledIndicator;
	protected Long hsCodeId;
	protected String hsCode;
	protected String dutiable;
	protected String uomCode;
	protected Long subheadingId;
	protected ChapterDTO chapterDto;
	protected HeadingDTO headingDto;
	protected SubHeadingDTO subHeadingDto;
	protected Long year;
	protected Date effectiveDate;
	protected Date createdDate;
	protected String status;

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Long getYear() {
		return this.year;
	}

	public void setYear(Long year) {
		this.year = year;
	}
	
	public HSCodeDTO() {		
	}

	public HSCodeDTO(Long id, String code) {
		setHsCodeId(id);
		setHsCode(code);
	}

	public Long getSubheadingId() {
		return this.subheadingId;
	}

	public void setSubheadingId(Long subheadingId) {
		this.subheadingId = subheadingId;
	}

	public ChapterDTO getChapterDto() {
		return this.chapterDto;
	}

	public void setChapterDto(ChapterDTO chapterDto) {
		this.chapterDto = chapterDto;
	}

	public HeadingDTO getHeadingDto() {
		return this.headingDto;
	}

	public void setHeadingDto(HeadingDTO headingDto) {
		this.headingDto = headingDto;
	}

	public SubHeadingDTO getSubHeadingDto() {
		return this.subHeadingDto;
	}

	public void setSubHeadingDto(SubHeadingDTO subHeadingDto) {
		this.subHeadingDto = subHeadingDto;
	}

	public Long getSubheading2() {
		return this.subheading2;
	}

	public void setSubheading2(Long subheading2) {
		this.subheading2 = subheading2;
	}

	public String getControlledIndicator() {
		return this.controlledIndicator;
	}

	public void setControlledIndicator(String controlledIndicator) {
		this.controlledIndicator = controlledIndicator;
	}

	public String getUomCode() {
		return this.uomCode;
	}

	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String toString() {
		StringBuffer model = new StringBuffer(
				"\n***======= HSCodeDTO =======***");

		model.append("\nHSCode Id: ");

		if (this.hsCodeId != null) {
			model.append(this.hsCodeId);
		}

		model.append("\nHS Code: ");

		if (this.hsCode != null) {
			model.append(this.hsCode);
		}

		model.append("\nDutiable: ");

		if (this.dutiable != null) {
			model.append(this.dutiable);
		}

		model.append("\nCreated By: ");

		if (this.createdBy != null) {
			model.append(this.createdBy);
		}

		model.append("\nCreated Date: ");

		if (this.createdDate != null) {
			model.append(this.createdDate);
		}

		model.append("\nUpdated By: ");

		if (this.updatedBy != null) {
			model.append(this.updatedBy);
		}

		model.append("\nUpdated Date: ");

		if (this.updatedDate != null) {
			model.append(this.updatedDate);
		}

		model.append("\n");

		return model.toString();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionOther() {
		return this.descriptionOther;
	}

	public void setDescriptionOther(String descriptionOther) {
		this.descriptionOther = descriptionOther;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getHsCodeId() {
		return this.hsCodeId;
	}

	public void setHsCodeId(Long hsCodeId) {
		this.hsCodeId = hsCodeId;
	}

	public String getHsCode() {
		return this.hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getDutiable() {
		return this.dutiable;
	}

	public void setDutiable(String dutiable) {
		this.dutiable = dutiable;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
