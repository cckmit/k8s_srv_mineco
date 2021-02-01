package com.egoveris.deo.web.satra.macros;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Window;

public class PrevisualizacionDocumento {

	public static final String VAR_ARCHIVO_PREVISUALIZACION = "archivoPrevisualizacion";
	public static final String VAR_MOSTRAR_LEYENDA_PREVISUALIZACION = "mostrarLeyenda";
	
	public static void window(byte[] data, Boolean mostrarLeyenda, Component target) throws SuspendNotAllowedException, InterruptedException {
		
		Map<String, Object> documentoAVisualizar = new HashMap<String, Object>();
		documentoAVisualizar.put(VAR_ARCHIVO_PREVISUALIZACION, data);
		documentoAVisualizar.put(VAR_MOSTRAR_LEYENDA_PREVISUALIZACION, mostrarLeyenda);
		
		Window previsualizacion = (Window) Executions.createComponents(
				"previsualizacion.zul", target, documentoAVisualizar);
		previsualizacion.setParent(target);
		previsualizacion.doModal();
		
	}
}
