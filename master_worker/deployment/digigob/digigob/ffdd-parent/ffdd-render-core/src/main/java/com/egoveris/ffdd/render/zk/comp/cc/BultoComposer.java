
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class BultoComposer extends ComplexComponentComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 8989623688678301110L;

  Cell idBultoDiv;
  Cell secuencialBultoDiv;
  Cell tipoBultoDiv;
  Cell cantidadPaquetesDiv;
  Cell identificadorBultoDiv;
  Cell subContinenteDiv;
  Cell listaItemsDTODiv;
 
  InputElement idBulto;
  InputElement secuencialBulto;
  InputElement tipoBulto;
  InputElement cantidadPaquetes;
  InputElement identificadorBulto;
  InputElement subContinente;
  SeparatorComplex listaItemsDTO;
  
  @Override
  protected String getName() {
    return "bulto";
  }

  @Override
  protected void setDefaultValues(final String name) {

  }

}
