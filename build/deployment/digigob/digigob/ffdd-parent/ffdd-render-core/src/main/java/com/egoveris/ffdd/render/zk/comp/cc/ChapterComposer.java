
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ChapterComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell chapterIdDiv;

 Cell chapterCodeDiv;

 Cell chapterDescDiv;

 Cell chapterDescSPDiv;

 Cell chapterStatusDiv;

 Cell yearDiv;

 

 

 InputElement chapterId;

 InputElement chapterCode;

 InputElement chapterDesc;

 InputElement chapterDescSP;

 InputElement chapterStatus;

 InputElement year;


 
 

	@Override
	protected String getName() {
		return "chapter";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  this.chapterId.setText("80");
	  this.chapterCode.setText("erer");
	  this.chapterDesc.setText("sf");
	  this.chapterDescSP.setText("sdf");
	  this.chapterStatus.setText("jkjk");
	  this.year.setText("455");

	}
	
}
