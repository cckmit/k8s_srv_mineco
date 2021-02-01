package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class SubHeadingComposer extends ComplexComponentComposer {

  /**
   * 
   */
  private static final long serialVersionUID = -615605579464248829L;
  Cell subheadingIdDiv;
  Cell headingDTODiv;
  Cell headingIdDiv;
  Cell subheadingCodeDiv;
  Cell subheadingDescDiv;
  Cell subheadingdescSPDiv;
  Cell subheadingStatusDiv;
  Cell subheadingTextDiv;
  Cell yearDiv;
  
  InputElement subheadingId;
  SeparatorComplex headingDTO;
  InputElement headingId;
  InputElement subheadingCode;
  InputElement subheadingDesc;
  InputElement subheadingdescSP;
  InputElement subheadingStatus;
  InputElement subheadingText;
  InputElement year;
  
  @Override
  protected void setDefaultValues(String prefixName) {
    this.subheadingId.setText("0");
    this.headingId.setText("2");
    this.subheadingCode.setText("SHC");
    this.subheadingDesc.setText("SHD");
    this.subheadingdescSP.setText("SHDSP");
    this.subheadingStatus.setText("SHS");
    this.subheadingText.setText("SHT");  
    this.year.setText("2017");
  }
  
}
