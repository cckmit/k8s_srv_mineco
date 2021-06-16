
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class ParticipantesComposer extends ComplexComponentComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 8989623688678301110L;

  Cell participanteIdDiv;
  Cell participanteTypeDiv;
  Cell personaTypeEnumDiv;
  Cell dispatcherCodeDiv;
  Cell docPersonaTypeDiv;
  Cell docPersonaNumDiv;
  Cell participanteNombreDiv;
  Cell participanteApellidoDiv;
  Cell participantePorcentajeDiv;
  Cell participanteAddressDiv;
  Cell participanteEmailDiv;
  Cell participanteNumeroTelefonoFijoDiv;
  Cell participanteNumeroTelefonoMovilDiv;
  Cell contactoDiv;

  InputElement participanteId;
  InputElement participanteType;
  InputElement dispatcherCode;
  InputElement personaTypeEnum;
  InputElement docPersonaType;
  InputElement docPersonaNum;
  InputElement participanteNombre;
  InputElement participanteApellido;
  InputElement participantePorcentaje;
  InputElement participanteEmail;
  InputElement participanteNumeroTelefonoFijo;
  InputElement participanteNumeroTelefonoMovil;
  SeparatorComplex participanteAddress;
  SeparatorComplex contacto;

  @Override
  protected String getName() {
    return "participante";
  }

  @Override
  protected void setDefaultValues(final String name) {

    
  }

}
