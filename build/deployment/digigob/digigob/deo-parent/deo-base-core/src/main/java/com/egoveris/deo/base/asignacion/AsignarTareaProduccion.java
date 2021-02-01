package com.egoveris.deo.base.asignacion;

import com.egoveris.deo.util.Constantes;

public class AsignarTareaProduccion extends AbstractAsignarTarea {

  private static final long serialVersionUID = 1L;

  public AsignarTareaProduccion() {
    super();
    super.mensaje = Constantes.VAR_MENSAJE_PRODUCTOR;
    super.varAsignacionUsuario = Constantes.VAR_USUARIO_PRODUCTOR;
    super.referenciaDocumento = "producir";
    super.referenciaDocumentoSub = "Producir Documento";
  }

}