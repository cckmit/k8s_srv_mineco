package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

public class VistaOrigenDocIngComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3990358278917416966L;
	
	
	Cell viaDeTransporteDiv;
	Cell codigoPuertoDeEmbarqueDiv;
	Cell codigoPaisProcedenciaDiv;
	Cell codigoPuertoDesembarqueDiv;
	Cell fechaEmbarqueDiv;
	Cell fechaDesembarqueDiv;
	Cell manifiestoDiv;
	Cell nombreNaveDiv;
	Cell codigoPaisOrigenDiv;
	Cell nombreCompaniaTransporteDiv;
	Cell numeroDocumentoTransporteDiv;
	Cell fechaDocumentoTransporteDiv;
	Cell transbordoDiv;
	Cell emisorDiv;

	
	InputElement viaDeTransporte;
	InputElement codigoPuertoDeEmbarque;
	InputElement codigoPaisProcedencia;
	InputElement codigoPuertoDesembarque;
	InputElement fechaEmbarque;
	InputElement fechaDesembarque;
	InputElement manifiesto;
	InputElement nombreNave;
	InputElement codigoPaisOrigen;
	InputElement nombreCompaniaTransporte;
	InputElement numeroDocumentoTransporte;
	InputElement fechaDocumentoTransporte;
	SeparatorComplex transbordo;
	SeparatorComplex emisor;



	@Override
	protected void setDefaultValues(String prefixName) {
		// TODO Auto-generated method stub
		
	}

}
