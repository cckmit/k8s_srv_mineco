package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;

public class ChapterDTO extends AbstractCComplejoDTO implements Serializable {
	
 private static final long serialVersionUID = -1478357815375217051L;
 
 protected Long chapterId;
	protected String chapterCode;
	protected String chapterDesc;
	protected String chapterDescSP;
	protected String chapterStatus;
	protected List<HeadingDTO> headings;
	protected Long year;

	public Long getChapterId() {
		return this.chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public String getChapterCode() {
		return this.chapterCode;
	}

	public void setChapterCode(String chapterCode) {
		this.chapterCode = chapterCode;
	}

	public String getChapterDesc() {
		return this.chapterDesc;
	}

	public void setChapterDesc(String chapterDesc) {
		this.chapterDesc = chapterDesc;
	}

	public String getChapterDescSP() {
		return this.chapterDescSP;
	}

	public void setChapterDescSP(String chapterDescSP) {
		this.chapterDescSP = chapterDescSP;
	}

	public String getChapterStatus() {
		return this.chapterStatus;
	}

	public void setChapterStatus(String chapterStatus) {
		this.chapterStatus = chapterStatus;
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

	public List<HeadingDTO> getHeadings() {
		return this.headings;
	}

	public void setHeadings(List<HeadingDTO> headings) {
		this.headings = headings;
	}

	public Long getYear() {
		return this.year;
	}

	public void setYear(Long year) {
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

		if (this.chapterCode != null) {
			model.append(this.chapterCode);
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

		model.append("\nYear: ");
		if (this.year != null) {
			model.append(this.year);
		}

		return model.toString();
	}
}
