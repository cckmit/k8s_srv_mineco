
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ItemComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell itemIdDiv;
	Cell productoDiv;
	Cell valorMinimoDiv;
	Cell acuerdoComercialDiv;
	Cell montoUnitarioFOBDiv;
	Cell montoFOBDiv;
	Cell pesoBrutoItemDiv;
	Cell unidadPesoBrutoItemDiv;
	Cell pesoNetoEmbarqueDiv;
	Cell unidadPesoNetoEmbarqueDiv;
	Cell volumenTotalDiv;
	Cell unidadVolumenDiv;
	Cell cantidadMercanciasDiv;
	Cell unidadCantidadMercanciaDiv;
	Cell listObservacionDTOsDiv;
	Cell listDocumentoApoyoDiv;
	Cell montoAjusteItemDiv;
	Cell signoAjusteDiv;
	Cell sujetoCupoDiv;
	Cell codigoTratadoArancelDiv;
	Cell numeroCorrelativoArancelDiv;
	Cell valorCIFDiv;
	Cell porcentajeAdvaloremDiv;
	Cell codigoCuentaAdvaloremDiv;
	Cell montoCuentaAdvaloremDiv;
	Cell paisProduccionDiv;
	Cell productorDiv;
	Cell cuarentenaPostFronteraDiv;
	Cell numeroResolucionDiv;
	Cell listaLoteDiv;
	Cell mercadoDestinoDiv;
	Cell listaInstalacionDiv;
	Cell listaTransportistaDiv;
	Cell listaDocumentoComercialDiv;

	InputElement itemId;
	SeparatorComplex producto;
	InputElement valorMinimo;
	InputElement acuerdoComercial;
	InputElement montoUnitarioFOB;
	InputElement montoFOB;
	InputElement pesoBrutoItem;
	InputElement unidadPesoBrutoItem;
	InputElement pesoNetoEmbarque;
	InputElement unidadPesoNetoEmbarque;
	InputElement volumenTotal;
	InputElement unidadVolumen;
	InputElement cantidadMercancias;
	InputElement unidadCantidadMercancia;
	SeparatorComplex listObservacionDTOs;
	SeparatorComplex listDocumentoApoyo;
	InputElement montoAjusteItem;
	InputElement signoAjuste;
	InputElement sujetoCupo;
	InputElement codigoTratadoArancel;
	InputElement numeroCorrelativoArancel;
	InputElement valorCIF;
	InputElement porcentajeAdvalorem;
	InputElement codigoCuentaAdvalorem;
	InputElement montoCuentaAdvalorem;
	InputElement paisProduccion;
	SeparatorComplex productor;
	InputElement cuarentenaPostFrontera;
	InputElement numeroResolucion;
	SeparatorComplex listaLote;
	InputElement mercadoDestino;
	SeparatorComplex listaInstalacion;
	SeparatorComplex listaTransportista;
	SeparatorComplex listaDocumentoComercial;

	@Override
	protected String getName() {
		return "item";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.valorMinimo.setText("456");
		this.acuerdoComercial.setText("ABC123");
		this.pesoNetoEmbarque.setText("789");
		this.codigoTratadoArancel.setText("ARN123");
		this.valorCIF.setText("987");
		this.cantidadMercancias.setText("654");
	}

}
