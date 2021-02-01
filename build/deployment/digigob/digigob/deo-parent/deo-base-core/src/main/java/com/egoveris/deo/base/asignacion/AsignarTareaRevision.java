package com.egoveris.deo.base.asignacion;

import com.egoveris.deo.util.Constantes;

public class AsignarTareaRevision extends AbstractAsignarTarea {

  private static final long serialVersionUID = -6571369186752895330L;

  public AsignarTareaRevision() {
    super();
    super.varAsignacionUsuario = Constantes.VAR_USUARIO_REVISOR;
    super.mensaje = Constantes.VAR_MENSAJE_A_REVISOR;
    super.referenciaDocumento = "revisar";
    super.referenciaDocumentoSub = "Revisar Documento";
  }
}