package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

public class VistaDUSDAComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8617570459425996472L;
	
	Cell tipoEnvioDiv;
	Cell comTrasnportadoraDiv;
	Cell totalItemDiv;
	Cell totalBultosDiv;
	Cell codigoDespachoDiv;
	Cell indicaMercanciaNacionalizadaDiv;
	Cell indicaMercanciaConsolidadaEnZonaPrimariaDiv;
	Cell viaTransporteDiv;
	Cell tipoOperacionAduaneraDiv;
	Cell codigoFleteDiv;
	Cell indicaDocumentoParcialDusDiv;
	Cell estadoDusDiv;
	Cell numeroInternoDeDespachoDiv;
	Cell regionOrigenDiv;

	InputElement tipoEnvio;
	SeparatorComplex comTrasnportadora;
	InputElement totalItem;
	InputElement totalBultos;
	InputElement codigoDespacho;
	InputElement indicaMercanciaNacionalizada;
	InputElement indicaMercanciaConsolidadaEnZonaPrimaria;
	InputElement viaTransporte;
	InputElement tipoOperacionAduanera;
	InputElement codigoFlete;
	InputElement indicaDocumentoParcialDus;
	InputElement estadoDus;
	InputElement numeroInternoDeDespacho;
	InputElement regionOrigen;



	@Override
	protected void setDefaultValues(String prefixName) {
		// TODO Auto-generated method stub
		
	}

}
