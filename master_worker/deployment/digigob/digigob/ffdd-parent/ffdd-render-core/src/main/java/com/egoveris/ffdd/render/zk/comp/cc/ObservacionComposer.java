
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ObservacionComposer extends ComplexComponentComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 8989623688678301110L;

  Cell observacionCodeDiv;
  Cell observacionDescDiv;  
 
  InputElement observacionCode;
  InputElement observacionDesc;
   
  @Override
  protected String getName() {
    return "observacion";
  }

  @Override
  protected void setDefaultValues(final String name) {
    this.observacionCode.setText("0");
    this.observacionDesc.setText("OBsDesc");    
  }

}
