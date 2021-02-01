package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.TareaBusquedaDTO;
import com.egoveris.deo.util.Constantes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class ConsultaTareasItemRender implements ListitemRenderer{

	public void render(Listitem item, Object data, int arg2) throws Exception {
		TareaBusquedaDTO tareaBusqueda = (TareaBusquedaDTO)data;
		
		Listcell celdaUsuario = new Listcell(tareaBusqueda.getUsuarioIniciador());
		item.appendChild(celdaUsuario);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		item.appendChild(new Listcell(df.format(tareaBusqueda.getFechaAlta())));
		
		Listcell celdaTipoDoc = new Listcell(tareaBusqueda.getTipoDocumento());
		item.appendChild(celdaTipoDoc);
		
		Listcell celdaReferencia = new Listcell(tareaBusqueda.getReferencia());
		item.appendChild(celdaReferencia);
		
		Listcell celdaTarea = new Listcell(tareaBusqueda.getTipoTarea());
		item.appendChild(celdaTarea);
		
		Listcell celdaUsuarioDestino = new Listcell(tareaBusqueda.getUsuarioDestino());
		item.appendChild(celdaUsuarioDestino);
		
		Hbox hbox = new Hbox();
		Image runImage = new Image("/imagenes/play.png");
		runImage.setParent(hbox);
		Label runLabel = new Label(Labels.getLabel("gedo.inbox.ejecutar"));
		runLabel.setParent(hbox);
		if (!tareaBusqueda.getTipoTarea().equals(Constantes.ENVIO_PORTA_FIRMA)) {
			org.zkoss.zk.ui.sys.ComponentsCtrl
					.applyForward(runLabel,
							"onClick=consultaDocumentosWindow$ConsultaDocumentosComposer.onExecuteTask");
			org.zkoss.zk.ui.sys.ComponentsCtrl
					.applyForward(runImage,
							"onClick=consultaDocumentosWindow$ConsultaDocumentosComposer.onExecuteTask");
		}else{
			org.zkoss.zk.ui.sys.ComponentsCtrl
					.applyForward(runLabel,
							"onClick=consultaDocumentosWindow$ConsultaDocumentosComposer.onMostrarTareaPortafirma");
			org.zkoss.zk.ui.sys.ComponentsCtrl
					.applyForward(runImage,
							"onClick=consultaDocumentosWindow$ConsultaDocumentosComposer.onMostrarTareaPortafirma");
		}
		
		Listcell celdaEjecutar = new Listcell();
		celdaEjecutar.appendChild(hbox);
		item.appendChild(celdaEjecutar);
	}

}
