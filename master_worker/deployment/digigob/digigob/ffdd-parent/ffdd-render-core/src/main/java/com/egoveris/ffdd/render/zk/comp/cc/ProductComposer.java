package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.impl.InputElement;

public class ProductComposer extends ComplexComponentComposer {

  /**
   * 
   */
  private static final long serialVersionUID = -4942440361802209694L;
  
  Cell productIdDiv;
  Cell productCodeDiv;
  Cell hsCodeDiv;
  Cell ssppEnumDiv;
  Cell commonRegNameDiv;
  Cell commonRegNameSpanishDiv;
  Cell prodDescDiv;
  Cell prodDescSpanishDiv;
  Cell businessNameDiv;
  Cell businessNameSpanishDiv;
  Cell scientificNameDiv;
  Cell prodCodeSSPPDiv;
  Cell physicalStateDiv;
  Cell statusDiv;
  Cell alertMessageDiv;
  Cell destinacionEnumDiv;
  Cell productVersionDiv;
  Cell productAttributesDTODiv;

  InputElement productId;
  InputElement productCode;
  InputElement hsCode;
  InputElement ssppEnum;
  InputElement commonRegName;
  InputElement commonRegNameSpanish;
  InputElement prodDesc;
  InputElement prodDescSpanish;
  InputElement businessName;
  InputElement businessNameSpanish;
  InputElement scientificName;
  InputElement prodCodeSSPP;
  InputElement physicalState;
  InputElement status;
  InputElement alertMessage;
  InputElement destinacionEnum;
  InputElement productVersion;
  SeparatorComplex productAttributesDTO;

  @Override
  protected void setDefaultValues(String prefixName) {
    this.productId.setText("0");
    this.productCode.setText("PC00023");
    this.hsCode.setText("HSC78");
    ((Combobox) this.ssppEnum).setSelectedIndex(0);
    this.commonRegName.setText("CRN");
    this.commonRegNameSpanish.setText("CRNS");
    this.prodDesc.setText("PD9292");
    this.prodDescSpanish.setText("PDS 987");
    this.businessName.setText("BN 32");
    this.businessNameSpanish.setText("BNS 98");
    this.scientificName.setText("SN");
    this.prodCodeSSPP.setText("PCSSPP9028");
    this.physicalState.setText("PHS1");
    this.status.setText("S2");
    this.alertMessage.setText("AM34");
    ((Combobox) this.destinacionEnum).setSelectedIndex(0);
    this.productVersion.setText("PV 1.0");
    
  }

}
