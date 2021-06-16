package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;



public class HistorialEmbebidosComposer extends GEDOGenericForwardComposer{


	private Window historialEmbebidosWindow;
	private List<TipoDocumentoEmbebidosDTO> listaTipoDocumentoEmbebidos;
	private TipoDocumentoDTO tipoDocumento;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.tipoDocumento = (TipoDocumentoDTO) Executions.getCurrent().getArg()
				.get("tipoDocumento");
		this.listaTipoDocumentoEmbebidos = new ArrayList<>(
				this.tipoDocumento.getTipoDocumentoEmbebidos());
		
	}

	public Window getHistorialEmbebidosWindow() {
		return historialEmbebidosWindow;
	}

	public void setHistorialEmbebidosWindow(Window historialEmbebidosWindow) {
		this.historialEmbebidosWindow = historialEmbebidosWindow;
	}

	public List<TipoDocumentoEmbebidosDTO> getListaTipoDocumentoEmbebidos() {
		return listaTipoDocumentoEmbebidos;
	}

	public void setListaTipoDocumentoEmbebidos(
			List<TipoDocumentoEmbebidosDTO> listaTipoDocumentoEmbebidos) {
		this.listaTipoDocumentoEmbebidos = listaTipoDocumentoEmbebidos;
	}

	public TipoDocumentoDTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
}
