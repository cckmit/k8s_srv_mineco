
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ProductAttributesOperationComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 8989623688678301110L;

 
  Cell productoOperacionIdDiv;
  Cell productIdDiv;
  Cell attributeSeqDiv;
  Cell attributeValueDiv;
  Cell productAttributeOperacionIdDiv;
  Cell productAttributeIdDiv;
  Cell productCodeDiv;
  Cell attributeCodeNameDiv;
 

  InputElement productoOperacionId;
  InputElement productId;
  InputElement attributeSeq;
  InputElement attributeValue;
  InputElement productAttributeOperacionId;
  InputElement productAttributeId;
  InputElement productCode;
  InputElement attributeCodeName;
  
  @Override
  protected String getName() {
    return "product attributes operation";
  }

  @Override
  protected void setDefaultValues(final String name) {

  }

}
