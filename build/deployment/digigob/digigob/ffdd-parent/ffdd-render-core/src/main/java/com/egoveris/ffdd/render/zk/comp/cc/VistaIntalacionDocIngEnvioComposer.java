
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaIntalacionDocIngEnvioComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 1L;

	Cell nombreContactoEstablecimientoDiv;
	Cell nombreDirectorTecnicoDiv;
	Cell tipoEstablecimientoDiv;
	Cell codigoEstablecimientoDiv;
	Cell nombreEstablecimientoDiv;
	Cell establecimientoAutorizadoDiv;
	Cell razonSocialEstablecimientoDiv;
	Cell numeroResolucionAutorizacionDiv;
	Cell fechaEmisionResolucionDiv;
	Cell entidadEmisoraDiv;
	Cell telefonoInstalacionDiv;
	Cell direccionEstablecimientoDiv;
	Cell regionInstalacionDiv;
	Cell comunaInstalacionDiv;

	InputElement nombreContactoEstablecimiento;
	InputElement nombreDirectorTecnico;
	InputElement tipoEstablecimiento;
	InputElement codigoEstablecimiento;
	InputElement nombreEstablecimiento;
	InputElement establecimientoAutorizado;
	InputElement razonSocialEstablecimiento;
	InputElement numeroResolucionAutorizacion;
	InputElement fechaEmisionResolucion;
	InputElement entidadEmisora;
	InputElement telefonoInstalacion;
	InputElement direccionEstablecimiento;
	InputElement regionInstalacion;
	InputElement comunaInstalacion;

	@Override
	protected String getName() {
		return "intalaciondocingenvio";
	}

	@Override
	protected void setDefaultValues(final String name) {

	}

}
