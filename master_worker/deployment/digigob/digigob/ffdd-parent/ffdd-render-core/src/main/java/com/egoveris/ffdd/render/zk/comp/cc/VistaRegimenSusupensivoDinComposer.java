
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaRegimenSusupensivoDinComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;

	Cell direccionAlmacenamientoDiv;
	Cell codigoComunaAlmacenDiv;
	Cell codigoAduanaDeControlDiv;
	Cell plazoVigenciaDiv;
	Cell numeroHojasInsumosDiv;
	Cell totalInsumosDiv;
	Cell numeroDeRegimenSuspensivoDiv;
	Cell fechaRegimenSuspensivoDiv;
	Cell aduanaRegimenSuspensivoDiv;
	Cell numeroHojasAnexasDiv;
	Cell cantidadSecuenciasDiv;
	Cell plazoDiv;

	
	InputElement direccionAlmacenamiento;
	InputElement codigoComunaAlmacen;
	InputElement codigoAduanaDeControl;
	InputElement plazoVigencia;
	InputElement numeroHojasInsumos;
	InputElement totalInsumos;
	InputElement numeroDeRegimenSuspensivo;
	InputElement fechaRegimenSuspensivo;
	InputElement aduanaRegimenSuspensivo;
	InputElement numeroHojasAnexas;
	InputElement cantidadSecuencias;
	InputElement plazo;




	@Override
	protected String getName() {
		return "VistaRegimenSusupensivoDinComposer";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  
	}
	

}
