
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.zkoss.zul.Cell;

public class FinancieroComposer extends ComplexComponentComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 8989623688678301110L;

  Cell valorLiquidoRetornoDiv;
  Cell valorFleteDiv;
  Cell valorExFabricaDiv;
  Cell valorClausulaVentaDiv;
  Cell valorCIFDiv;
  Cell totalValorFOBDiv;
  Cell totalDiferidoDiv;
  Cell tasaInteresDiv;
  Cell tasaCambioDiv;
  Cell seguroTeoricoDiv;
  Cell regimenImportacionDiv;
  Cell proveedorOfabricanteDiv;
  Cell plazoPagoDiv;
  Cell paisAdquisicionDiv;
  Cell otrosGastosDeduciblesDiv;
  Cell origenDivisasDiv;
  Cell obsBancoSNADiv;
  Cell numFacturaDiv;
  Cell numeroCuotasPagoDiferidoDiv;
  Cell montoTotalAdvaloremDiv;
  Cell montoTotalDiv;
  Cell montoSeguroDiv;
  Cell monedaDiv;
  Cell modalidadVentaDiv;
  Cell ivaDiv;
  Cell idPagoDiferidoDiv;
  Cell gastosHastaFOBDiv;
  Cell formaPagoGravamenesDiv;
  Cell formaPagoDiv;
  Cell fleteTeoricoDiv;
  Cell fechaPagoDiferidoDiv;
  Cell fechaPagoDiv;
  Cell fecFacturaDiv;
  Cell facturaIdDiv;
  Cell facturaComercialDefinitivaDiv;
  Cell descuentoDiv;
  Cell declaracionIdDiv;
  Cell cuotasDiv;
  Cell cuotaContadoDiv;
  Cell comisionesExteriorDiv;
  Cell codigoTotalAdvaloremDiv;
  Cell codigoSeguroDiv;
  Cell codigoFleteDiv;
  Cell codigoBancoComercialDiv;
  Cell clausulaVentaDiv;
  Cell clausulaCompraDiv;
  Cell aduanaDIPagoDifDiv;

  InputComponent valorLiquidoRetorno;
  InputComponent valorFlete;
  InputComponent valorExFabrica;
  InputComponent valorClausulaVenta;
  InputComponent valorCIF;
  InputComponent totalValorFOB;
  InputComponent totalDiferido;
  InputComponent tasaInteres;
  InputComponent tasaCambio;
  InputComponent seguroTeorico;
  InputComponent regimenImportacion;
  SeparatorComplex proveedorOfabricante;
  InputComponent plazoPago;
  InputComponent paisAdquisicion;
  InputComponent otrosGastosDeducibles;
  InputComponent origenDivisas;
  InputComponent obsBancoSNA;
  InputComponent numFactura;
  InputComponent numeroCuotasPagoDiferido;
  InputComponent montoTotalAdvalorem;
  InputComponent montoTotal;
  InputComponent montoSeguro;
  InputComponent moneda;
  InputComponent modalidadVenta;
  InputComponent iva;
  InputComponent idPagoDiferido;
  InputComponent gastosHastaFOB;
  InputComponent formaPagoGravamenes;
  InputComponent formaPago;
  InputComponent fleteTeorico;
  InputComponent fechaPagoDiferido;
  InputComponent fechaPago;
  InputComponent fecFactura;
  InputComponent facturaId;
  InputComponent facturaComercialDefinitiva;
  InputComponent descuento;
  InputComponent declaracionId;
  SeparatorComplex cuotas;
  InputComponent cuotaContado;
  InputComponent comisionesExterior;
  InputComponent codigoTotalAdvalorem;
  InputComponent codigoSeguro;
  InputComponent codigoFlete;
  InputComponent codigoBancoComercial;
  InputComponent clausulaVenta;
  InputComponent clausulaCompra;
  InputComponent aduanaDIPagoDif;

  @Override
  protected String getName() {
    return "financiero";
  }

  @Override
  protected void setDefaultValues(final String name) {

    final DateFormat df = new SimpleDateFormat("yyyyMMdd");
    try {
      this.fecFactura.setRawValue(df.parse("20170926"));
    } catch (final ParseException e) {

    }


  }

}
