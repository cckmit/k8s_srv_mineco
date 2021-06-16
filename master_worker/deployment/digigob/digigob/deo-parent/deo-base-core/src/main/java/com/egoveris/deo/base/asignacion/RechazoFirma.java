package com.egoveris.deo.base.asignacion;

import com.egoveris.deo.util.Constantes;

public class RechazoFirma extends AbstractAsignarTarea {

	private static final long serialVersionUID = 1L;

	public RechazoFirma() {
		super();
		super.varAsignacionUsuario = Constantes.VAR_USUARIO_REVISOR;
		super.mensaje = Constantes.VAR_MOTIVO_RECHAZO;
		super.referenciaDocumento = "revisar ya que ha sido rechazado";
		super.referenciaDocumentoSub = "Rechazo Documento";
	}
}
