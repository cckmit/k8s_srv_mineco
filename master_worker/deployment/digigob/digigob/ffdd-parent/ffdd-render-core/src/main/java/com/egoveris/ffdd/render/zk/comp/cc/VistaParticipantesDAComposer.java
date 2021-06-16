
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaParticipantesDAComposer extends ComplexComponentComposer {

  private static final long serialVersionUID = 1L;
  
  
  
  Cell nombreParticipanteDiv;
  Cell codigoDespachoDiv;
  Cell despachadorCodigoDiv;
  Cell indicaTipoDocumentoIdentificacionDiv;
  Cell rutDiv;
  Cell porcentajeDiv;
  Cell emailDiv;
  Cell direccionDiv;
  Cell comunaDiv;
	// Cell participanteDiv;
  Cell regionDiv;
  Cell numeroTelefonoFijoDiv;
  Cell numeroTelefonoMovilDiv;
  Cell paisDiv;

  InputElement nombreParticipante;
  InputElement codigoDespacho;
  InputElement despachadorCodigo;
  InputElement indicaTipoDocumentoIdentificacion;
  InputElement rut;
  InputElement porcentaje;
  InputElement email;
  InputElement direccion;
  InputElement comuna;
	// SeparatorComplex participante;
  InputElement region;
  InputElement numeroTelefonoFijo;
  InputElement numeroTelefonoMovil;
  InputElement pais;


  

 @Override
	protected String getName() {
		return "vistaparticipantesda";
	}

	@Override
	protected void setDefaultValues(final String name) {
	  
	}

}
