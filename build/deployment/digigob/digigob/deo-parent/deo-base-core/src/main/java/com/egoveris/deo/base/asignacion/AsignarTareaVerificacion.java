package com.egoveris.deo.base.asignacion;

import com.egoveris.deo.util.Constantes;

public class AsignarTareaVerificacion extends AbstractAsignarTarea {
	
	private static final long serialVersionUID = -1121204654009110828L;

	public AsignarTareaVerificacion() {
		super();
		super.mensaje = null;
		super.varAsignacionUsuario = Constantes.VAR_USUARIO_VERIFICADOR;
		super.referenciaDocumento = "verificar";
		super.referenciaDocumentoSub="Revisar Documento con Firma Conjunta";
	}
	
}