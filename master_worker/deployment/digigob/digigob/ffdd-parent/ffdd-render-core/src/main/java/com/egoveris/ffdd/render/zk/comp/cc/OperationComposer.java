
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;

public class OperationComposer extends ComplexComponentComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 8989623688678301110L;

	Cell codigoOficinaAduanaDiv;
	Cell codOperacionDiv;
	Cell nombreAgenciaAduanaDiv;
	Cell processingStatusDiv;
	Cell codigoDestinacionDiv;
	Cell codigoAgenciaAduanasDiv;
	Cell codigoTransbordoDiv;
	Cell actualizadoPorDiv;
	Cell creadoPorDiv;

	Cell listProductOperacionDiv;
	Cell listaItemDiv;
	Cell listaBultoDiv;
	Cell listAutorizacionDiv;

	Cell transportDiv;
	Cell contactoDiv;
	Cell importadorDiv;
	Cell consignatarioDiv;
	Cell intermediarioDiv;
	Cell exportadorPrincipalDiv;
	Cell representanteDiv;
	Cell exportadorSecundarioDiv;
	Cell interopHeaderDiv;
	Cell headerDiv;
	Cell financieroDiv;
	Cell detallesPuertoDiv;
	Cell dinDiv;
	Cell dusAtDiv;
	Cell dusLegDiv;
	
	Cell fechaCreacionDiv;
	Cell fechaActualizacionDiv;

	InputComponent codigoOficinaAduana;
	InputComponent codOperacion;
	InputComponent nombreAgenciaAduana;
	InputComponent processingStatus;
	InputComponent codigoDestinacion;
	InputComponent codigoAgenciaAduanas;
	InputComponent codigoTransbordo;
	InputComponent actualizadoPor;
	InputComponent creadoPor;

	SeparatorComplex listProductOperacion;
	SeparatorComplex listaItem;
	SeparatorComplex listaBulto;
	SeparatorComplex listAutorizacion;

	SeparatorComplex transport;
	SeparatorComplex contacto;
	SeparatorComplex importador;
	SeparatorComplex consignatario;
	SeparatorComplex intermediario;
	SeparatorComplex exportadorPrincipal;
	SeparatorComplex representante;
	SeparatorComplex exportadorSecundario;
	SeparatorComplex interopHeader;
	SeparatorComplex header;
	SeparatorComplex financiero;
	SeparatorComplex detallesPuerto;
	SeparatorComplex din;
	SeparatorComplex dusAt;
	SeparatorComplex dusLeg;

	InputComponent fechaCreacion;
	InputComponent fechaActualizacion;
  
  @Override
  protected void setDefaultValues(final String name) {


  }

}
