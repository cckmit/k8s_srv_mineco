
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class InstalacionComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell oigPropietariaDiv;
	Cell nombreInstalacionDiv;
	Cell direccionInstalacionDiv;
	Cell numeroResolucionAutDiv;
	Cell fechaEmisionResolucionDiv;
	Cell entidadEmisoraDiv;
	Cell directorTecnicoDiv;
	Cell tipoInstalacionDestinoDiv;
	Cell codigoInstalacionDestinoDiv;
	Cell telefonoInstalacionDestinoDiv;
	Cell contactoInstalacionDestinoDiv;
	Cell razonSocialDiv;

	InputElement oigPropietaria;
	InputElement nombreInstalacion;
	SeparatorComplex direccionInstalacion;
	InputElement numeroResolucionAut;
	InputElement fechaEmisionResolucion;
	InputElement entidadEmisora;
	SeparatorComplex directorTecnico;
	InputElement tipoInstalacionDestino;
	InputElement codigoInstalacionDestino;
	InputElement telefonoInstalacionDestino;
	InputElement contactoInstalacionDestino;
	InputElement razonSocial;



	@Override
	protected String getName() {
		return "instalacion";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.oigPropietaria.setText("ss");
		this.nombreInstalacion.setText("gg");
		this.entidadEmisora.setText("acknow");
		this.telefonoInstalacionDestino.setText("+56 9 87654321");
	}

}
