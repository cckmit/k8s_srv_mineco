
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaDestinoDocIngEnvioComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 1L;
  
  
  Cell patenteDiv;
  Cell rutaDiv;
  Cell tipoMedioTransporteDiv;
  Cell fechaEstimadaLlegadaDiv;
  Cell nombreTransportistaDiv;
  Cell tipoNombreDiv;
  Cell rutTransportistaDiv;
  Cell pasaporteDiv;

  InputElement patente;
  InputElement ruta;
  InputElement tipoMedioTransporte;
  InputElement fechaEstimadaLlegada;
  InputElement nombreTransportista;
  InputElement tipoNombre;
  InputElement rutTransportista;
  InputElement pasaporte;

  

 @Override
	protected String getName() {
		return "destinodocingenvio";
	}
 

	@Override
	protected void setDefaultValues(final String name) {

	}

}
