
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

public class VistaItemsDocIngresoEnvioComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 1L;
	Cell nroItemDiv;
	Cell codigoTratadoArancelDiv;
	Cell usoPrevistoDiv;
	Cell codigoPaisProduccionDiv;
	Cell valorCIFDiv;
	Cell montoAjusteDiv;
	Cell signoAjusteDiv;
	Cell cantidadDiv;
	Cell codigoUnidadMedidaDiv;
	Cell cantidadDeMercanciasEstimadaDiv;
	Cell precioUnitarioFOBDiv;
	Cell codigoArancelDelTratadoDiv;
	Cell numeroCorrelativoArancelDiv;
	Cell codigoAcuerdoComercialDiv;
	Cell sujetoACupoDiv;
	Cell porcentajeAdvaloremDiv;
	Cell codigoCuentaAdvaloremDiv;
	Cell montoCuentaAdvaloremDiv;
	Cell caracteristicaEspecialDiv;
	Cell pesoUnitarioDiv;
	Cell otraDescripcionDiv;
	Cell observacionOIGDiv;
	Cell loteDiv;
	Cell nombreProductorDiv;
	Cell codigoArancelarioFinalDiv;
	Cell mercadoDestinoDiv;
	Cell pesoNetoDiv;
	Cell pesoBrutoDiv;
	Cell direccionParticipanteDiv;
	Cell cuarentenaPostFronteraDiv;
	Cell nombreProductoDiv;
	Cell productoDiv;
	Cell observacionDiv;
	Cell establecimientoOrigenDiv;
	Cell documentoAdjuntoDiv;
	Cell codigoProductoDiv;
	Cell otroImpuestoDiv;

	InputElement nroItem;
	InputElement codigoTratadoArancel;
	InputElement usoPrevisto;
	InputElement codigoPaisProduccion;
	InputElement valorCIF;
	InputElement montoAjuste;
	InputElement signoAjuste;
	InputElement cantidad;
	InputElement codigoUnidadMedida;
	InputElement cantidadDeMercanciasEstimada;
	InputElement precioUnitarioFOB;
	InputElement codigoArancelDelTratado;
	InputElement numeroCorrelativoArancel;
	InputElement codigoAcuerdoComercial;
	InputElement sujetoACupo;
	InputElement porcentajeAdvalorem;
	InputElement codigoCuentaAdvalorem;
	InputElement montoCuentaAdvalorem;
	InputElement caracteristicaEspecial;
	InputElement pesoUnitario;
	InputElement otraDescripcion;
	InputElement observacionOIG;
	SeparatorComplex lote;
	InputElement nombreProductor;
	InputElement codigoArancelarioFinal;
	InputElement mercadoDestino;
	InputElement pesoNeto;
	InputElement pesoBruto;
	InputElement direccionParticipante;
	InputElement cuarentenaPostFrontera;
	InputElement nombreProducto;
	SeparatorComplex producto;
	SeparatorComplex observacion;
	SeparatorComplex establecimientoOrigen;
	SeparatorComplex documentoAdjunto;
	InputElement codigoProducto;
	SeparatorComplex otroImpuesto;

	@Override
	protected String getName() {
		return "VistaItemsDocIngresoEnvioComposer";
	}

	@Override
	protected void setDefaultValues(final String name) {

	}

}
