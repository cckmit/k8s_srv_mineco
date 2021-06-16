package com.egoveris.edt.ws.service;

import java.util.List;
import java.util.Map;

public interface IExternalDatosUsuarioService {
	
	
		public Map<String, String> datosUsuarioParaFormulario(String usuario);
		public List<String> obtenerUsuarios();
}
