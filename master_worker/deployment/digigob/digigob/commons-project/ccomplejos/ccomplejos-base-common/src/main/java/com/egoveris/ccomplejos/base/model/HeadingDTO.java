package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;

public class HeadingDTO extends AbstractCComplejoDTO implements Serializable {
 
 private static final long serialVersionUID = -5003422259285478068L;
 
 protected Long headingId;
	protected ChapterDTO chapterDTO;
	protected Long chapterId;
	protected List<SubHeadingDTO> subHeadings;
	protected String headingCode;
	protected String headingDesc;
	protected String headingdescSP;
	protected String headingStatus;

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	private String headingText = "11";

	private Integer year;

	public HeadingDTO() {
	}

	public HeadingDTO(Long id, String code) {
		this.headingId = id;
		this.headingCode = code;
	}

	public Long getHeadingId() {
		return this.headingId;
	}

	public void setHeadingId(Long headingId) {
		this.headingId = headingId;
	}

	public ChapterDTO getChapterDTO() {
		return this.chapterDTO;
	}

	public void setChapterDTO(ChapterDTO chapterDTO) {
		this.chapterDTO = chapterDTO;
	}

	public Long getChapterId() {
		return this.chapterId;
	}

	public String getHeadingCode() {
		return this.headingCode;
	}

	public void setHeadingCode(String headingCode) {
		this.headingCode = headingCode;
	}

	public String getHeadingDesc() {
		return this.headingDesc;
	}

	public void setHeadingDesc(String headingDesc) {
		this.headingDesc = headingDesc;
	}

	public String getHeadingdescSP() {
		return this.headingdescSP;
	}

	public void setHeadingdescSP(String headingdescSP) {
		this.headingdescSP = headingdescSP;
	}

	public String getHeadingStatus() {
		return this.headingStatus;
	}

	public void setHeadingStatus(String headingStatus) {
		this.headingStatus = headingStatus;
	}

//	public String getCreatedBy() {
//		return this.createdBy;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public Date getCreatedDate() {
//		return this.createdDate;
//	}
//
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//
//	public String getUpdatedBy() {
//		return this.updatedBy;
//	}
//
//	public void setUpdatedBy(String updatedBy) {
//		this.updatedBy = updatedBy;
//	}
//
//	public Date getUpdatedDate() {
//		return this.updatedDate;
//	}
//
//	public void setUpdatedDate(Date updatedDate) {
//		this.updatedDate = updatedDate;
//	}

	public List<SubHeadingDTO> getSubHeadings() {
		return this.subHeadings;
	}

	public void setSubHeadings(List<SubHeadingDTO> subHeadings) {
		this.subHeadings = subHeadings;
	}

	public String getHeadingText() {
		return this.headingText;
	}

	public void setHeadingText(String headingText) {
		this.headingText = headingText;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String toString() {
		StringBuffer model = new StringBuffer(
				"\n***======= ChapterDTO =======***");

		model.append("\nChapterCode Id: ");

		if (this.chapterId != null) {
			model.append(this.chapterId);
		}
		model.append("\nChapter Code: ");

		if (this.headingCode != null) {
			model.append(this.headingCode);
		}
		model.append("\nCreated By: ");

//		if (this.createdBy != null) {
//			model.append(this.createdBy);
//		}
//		model.append("\nCreated Date: ");
//
//		if (this.createdDate != null) {
//			model.append(this.createdDate);
//		}
//		model.append("\nUpdated By: ");
//
//		if (this.updatedBy != null) {
//			model.append(this.updatedBy);
//		}
//		model.append("\nUpdated Date: ");
//
//		if (this.updatedDate != null) {
//			model.append(this.updatedDate);
//		}
		model.append("\n");

		model.append("\n year By: ");
		if (this.year != null) {
			model.append(this.year);
		}

		return model.toString();
	}
}
