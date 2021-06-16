
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

public class VistaItemDAComposer extends ComplexComponentComposer {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Cell pesoBrutoItemDiv;
	Cell codigoArancelProdDiv;
	Cell unidadMedidaArancelProdDiv;
	Cell secuencialItemDiv;
	Cell montoFOBItemDiv;
	Cell valorMercanciaItemDiv;
	Cell numeroDeItemDiv;
	Cell fobUnitarioDiv;
	Cell fobUSDDiv;
	Cell cantidadKilosNetoProdDiv;
	Cell cantidadKilosBrutosProdDiv;
	Cell estadoItemDiv;
	Cell cantidadItemProductoDiv;
	Cell unidadDeMedidaDiv;
	Cell cantidadDeMercanciaDiv;
	Cell productoDiv;
	Cell numeroSolicitudAutorizacionDiv;
	// Cell codigoLoteDiv;
	Cell documentosRelacionadosDiv;
	Cell observacionesDiv;

	InputElement pesoBrutoItem;
	InputElement codigoArancelProd;
	InputElement unidadMedidaArancelProd;
	InputElement secuencialItem;
	InputElement montoFOBItem;
	InputElement valorMercanciaItem;
	InputElement numeroDeItem;
	InputElement fobUnitario;
	InputElement fobUSD;
	InputElement cantidadKilosNetoProd;
	InputElement cantidadKilosBrutosProd;
	InputElement estadoItem;
	InputElement cantidadItemProducto;
	InputElement unidadDeMedida;
	InputElement cantidadDeMercancia;
	SeparatorComplex producto;
	InputElement numeroSolicitudAutorizacion;
	// SeparatorComplex codigoLote;
	SeparatorComplex documentosRelacionados;
	SeparatorComplex observaciones;




	@Override
	protected String getName() {
		return "Login Usuario";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

}
