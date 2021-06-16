
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;

public class TransportComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 8989623688678301110L;

	Cell idDeclaracionDiv;
	Cell idTransporteDiv;
	Cell mercanciaDiv;
	Cell emisordocumentotransporteDiv;
	Cell transportistaDiv;
	Cell agenciaTransportistaDiv;
	Cell reservaDiv;
	Cell medioDeTransporteDiv;
	Cell documentoDiv;

	InputComponent idDeclaracion;
	InputComponent idTransporte;
	SeparatorComplex mercancia;
	SeparatorComplex emisordocumentotransporte;
	SeparatorComplex transportista;
	SeparatorComplex agenciaTransportista;
	SeparatorComplex reserva;
	InputComponent medioDeTransporte;
	SeparatorComplex documento;

  @Override
  protected String getName() {
    return "transport";
  }

  @Override
  protected void setDefaultValues(final String name) {
  }

}
