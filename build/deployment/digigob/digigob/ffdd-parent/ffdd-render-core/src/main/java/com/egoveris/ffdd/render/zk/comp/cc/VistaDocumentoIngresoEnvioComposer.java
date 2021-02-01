
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaDocumentoIngresoEnvioComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 1L;
  
  
  Cell numeroDocumentoIngresoDiv;
  Cell tipoSolicitudDiv;
  Cell fechaSolicitudDiv;
  Cell fechaDocIngresoDiv;
  Cell idSolicitudDiv;
  Cell codiserviciosPublicosRelacionadosDiv;
  Cell totalBultosDiv;
  Cell idBultosDiv;

  InputElement numeroDocumentoIngreso;
  InputElement tipoSolicitud;
  InputElement fechaSolicitud;
  InputElement fechaDocIngreso;
  InputElement idSolicitud;
  InputElement codiserviciosPublicosRelacionados;
  InputElement totalBultos;
  InputElement idBultos;

  
  
  
	@Override
	protected String getName() {
		return "vistadocumentoingresoenvio";
	}

	@Override
	protected void setDefaultValues(final String name) {

	  
	}

}
