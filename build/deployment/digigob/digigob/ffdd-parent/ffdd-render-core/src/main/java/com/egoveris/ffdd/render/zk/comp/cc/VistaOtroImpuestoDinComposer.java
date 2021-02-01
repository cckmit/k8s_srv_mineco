
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaOtroImpuestoDinComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 1L;
  
  Cell secuencialOtroImpuestoDiv;
  Cell porcentajeDiv;
  Cell codigoCuentaDiv;
  Cell signoCuentaDiv;
  Cell montoImpuestoDiv;

  
  InputElement secuencialOtroImpuesto;
  InputElement porcentaje;
  InputElement codigoCuenta;
  InputElement signoCuenta;
  InputElement montoImpuesto;

  

 @Override
	protected String getName() {
		return "vistaotroimpuestodin";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  
	}

}
