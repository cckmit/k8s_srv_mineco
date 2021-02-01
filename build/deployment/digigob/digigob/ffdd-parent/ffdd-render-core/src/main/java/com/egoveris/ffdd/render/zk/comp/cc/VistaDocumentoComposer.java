
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaDocumentoComposer extends ComplexComponentComposer {


  private static final long serialVersionUID = 8989623688678301110L;

  Cell emisorDocumentoComercialDiv;
  Cell numeroDocumentoComercialDiv;
  Cell codigoPaisEmisorDocumentoComercialDiv;
  Cell tipoDocumentoComercialDiv;
  Cell fechaDocumentoComercialDiv;

  InputElement emisorDocumentoComercial;
  InputElement numeroDocumentoComercial;
  InputElement codigoPaisEmisorDocumentoComercial;
  InputElement tipoDocumentoComercial;
  InputElement fechaDocumentoComercial;
  


  
  @Override
  protected String getName() {
    return "vistadocumento";
  }

  @Override
  protected void setDefaultValues(final String name) {

  }

}
