
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class InteropHeaderComposer extends ComplexComponentComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 8989623688678301110L;

  Cell idTransaccionDiv;
  Cell idMensajeDiv;
  Cell destinatarioDiv;
   
  InputElement idTransaccion;
  InputElement idMensaje;
  InputElement destinatario;
    
  @Override
  protected String getName() {
    return "bulto";
  }

  @Override
  protected void setDefaultValues(final String name) {
    this.idTransaccion.setText("IDT");
    this.idMensaje.setText("IDM");
    this.destinatario.setText("Dest002");
  }

}
