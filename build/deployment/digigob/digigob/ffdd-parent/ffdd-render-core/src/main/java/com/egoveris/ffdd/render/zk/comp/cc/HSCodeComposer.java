package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class HSCodeComposer extends ComplexComponentComposer {

  /**
   * 
   */
  private static final long serialVersionUID = 8166809515747971920L;

  Cell subheading2Div;
  Cell descriptionDiv;
  Cell descriptionOtherDiv;
  Cell createdByDiv;
  Cell modifiedByDiv;
  Cell modifiedDateDiv;
  Cell controlledIndicatorDiv;
  Cell hsCodeIdDiv;
  Cell hsCodeDiv;
  Cell dutiableDiv;
  Cell uomCodeDiv;
  Cell subheadingIdDiv;
  Cell yearDiv;
  Cell chapterDtoDiv;
  Cell headingDtoDiv;
  Cell subHeadingDtoDiv;
  Cell effectiveDateDiv;
  Cell createdDateDiv;
  Cell statusDiv;
  
  InputElement subheading2;
  InputElement description;
  InputElement descriptionOther;
  InputElement createdBy;
  InputElement modifiedBy;
  InputElement modifiedDate;
  InputElement controlledIndicator;
  InputElement hsCodeId;
  InputElement hsCode;
  InputElement dutiable;
  InputElement uomCode;
  InputElement subheadingId;
  InputElement year;
  SeparatorComplex chapterDto;
  SeparatorComplex headingDto;
  SeparatorComplex subHeadingDto;
  InputElement effectiveDate;
  InputElement createdDate;
  InputElement status;
  
  @Override
  protected void setDefaultValues(String prefixName) {
    this.subheading2.setText("99");
    this.description.setText("DESC 0289");
    this.descriptionOther.setText("DescO 682");
    this.createdBy.setText("root");
    this.modifiedBy.setText("MB me");
    final DateFormat df = new SimpleDateFormat("yyyyMMdd");
    try {
     this.modifiedDate.setRawValue(df.parse("20170926"));
    } catch (final ParseException e) {
    }
    this.controlledIndicator.setText("");
    this.hsCode.setText("HSC08363");
    this.dutiable.setText("D383737");
    this.uomCode.setText("UC8828872");
    this.subheadingId.setText("43");
    this.year.setText("2017");
    this.effectiveDate.setText("");
    try {
     this.effectiveDate.setRawValue(df.parse("20170926"));
    } catch (final ParseException e) {
    }
    try {
      this.createdDate.setRawValue(df.parse("20170926"));
     } catch (final ParseException e) {
     }
    this.status.setText("S2");
  }

  
}
