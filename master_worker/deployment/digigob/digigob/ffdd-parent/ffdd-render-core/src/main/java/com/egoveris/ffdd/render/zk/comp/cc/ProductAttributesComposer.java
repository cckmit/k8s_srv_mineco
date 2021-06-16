package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.impl.InputElement;

public class ProductAttributesComposer extends ComplexComponentComposer {

  /**
   * 
   */
  private static final long serialVersionUID = 136567487399804107L;

  Cell productAttributeIdDiv;
  Cell productIdDiv;
  Cell productCodeDiv;
  Cell attributeSeqDiv;
  Cell attributeCodeNameDiv;
  Cell attributeCodeNameESPDiv;
  Cell attributeValueDiv;
  Cell attributeDataTypeDiv;
  Cell attributeDataSizeDiv;
  Cell masterCodeTypeDiv;
  Cell isFixedDiv;
  Cell isMandatoryDiv;
  Cell statusDiv;
  Cell destinacionEnumDiv;
  Cell attributeUniqueCodeDiv;

  InputElement productAttributeId;
  InputElement productId;
  InputElement productCode;
  InputElement attributeSeq;
  InputElement attributeCodeName;
  InputElement attributeCodeNameESP;
  InputElement attributeValue;
  InputElement attributeDataType;
  InputElement attributeDataSize;
  InputElement masterCodeType;
  InputElement isFixed;
  InputElement isMandatory;
  InputElement status;
  InputElement destinacionEnum;
  InputElement attributeUniqueCode;

  @Override
  protected void setDefaultValues(String prefixName) {
    this.productAttributeId.setText("23");
    this.productId.setText("32");
    this.productCode.setText("PC02929");
    this.attributeSeq.setText("263");
    this.attributeCodeName.setText("ACN 093");
    this.attributeCodeNameESP.setText("ACNS 043");
    this.attributeValue.setText("AV");
    this.attributeDataType.setText("ADT");
    this.attributeDataSize.setText("ADS");
    this.masterCodeType.setText("MCT");
    this.isFixed.setText("TRUE");
    this.isMandatory.setText("FALSE");
    this.status.setText("S3");
    ((Combobox) this.destinacionEnum).setSelectedIndex(0);
    this.attributeUniqueCode.setText("AUC 90272357");
  }

}
