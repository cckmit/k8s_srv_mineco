
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class HeadingComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell headingIdDiv;
	
	Cell chapterDTODiv;

	Cell chapterIdDiv;
	
 Cell headingCodeDiv;

 Cell headingDescDiv;
 
 Cell headingdescSPDiv;

 Cell headingStatusDiv;



InputElement headingId;

SeparatorComplex chapterDTO;

InputElement chapterId;

InputElement headingCode;

InputElement headingDesc;

InputElement headingdescSP;

InputElement headingStatus;



	@Override
	protected String getName() {
		return "heading";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  this.headingId.setText("4");
	  this.chapterId.setText("6");
	  this.headingCode.setText("fdf");
	  this.headingDesc.setText("kjl");
	  this.headingdescSP.setText("ew");
	  this.headingStatus.setText("opop");
	}

}
