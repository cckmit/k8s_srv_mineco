
package com.egoveris.ffdd.render.zk.comp.cc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class AuditComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell updatedByDiv;

	Cell updatedDateDiv;

 Cell createdByDiv;

 Cell createdDateDiv;
 
 

	InputElement updatedBy;

	InputElement updatedDate;

 InputElement createdBy;

 InputElement createdDate;
 

	@Override
	protected String getName() {
		return "audit";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.updatedBy.setRawValue("update default");
		final DateFormat d = new SimpleDateFormat("ddmmyyyy");
  try {
   this.updatedDate.setRawValue(d.parse("07042017"));
   this.createdDate.setRawValue(d.parse("07042017"));
  } catch (final ParseException e) { 
  }
	 this.createdBy.setRawValue("created default");

	}

}
