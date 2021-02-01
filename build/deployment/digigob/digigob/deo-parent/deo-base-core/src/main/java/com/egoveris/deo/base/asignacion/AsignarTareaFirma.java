package com.egoveris.deo.base.asignacion;

import com.egoveris.deo.util.Constantes;

public class AsignarTareaFirma extends AbstractAsignarTarea {
	
	private static final long serialVersionUID = 6956326312900513650L;

	public AsignarTareaFirma() {
		super();
		super.mensaje = null;
		super.varAsignacionUsuario = Constantes.VAR_USUARIO_FIRMANTE;
		super.referenciaDocumento = "firmar";
		super.referenciaDocumentoSub="Firmar Documento";
	}
	
}