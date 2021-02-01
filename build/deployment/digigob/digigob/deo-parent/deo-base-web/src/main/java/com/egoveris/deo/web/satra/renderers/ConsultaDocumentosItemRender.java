package com.egoveris.deo.web.satra.renderers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.deo.model.model.DocumentoSolr;
import com.egoveris.deo.web.utils.Utilitarios;



@SuppressWarnings("rawtypes")
public class ConsultaDocumentosItemRender implements ListitemRenderer{

	public void render(Listitem item, Object data, int arg2) throws Exception {
		DocumentoSolr documento = (DocumentoSolr)data;
		String nroDocumento = Utilitarios.motivoParseado(documento.getNroSade(), 30);
		item.appendChild(new Listcell(nroDocumento));
		String nroDocEspecial = null;
		if(documento.getNroEspecialSade() != null){			
			nroDocEspecial = Utilitarios.motivoParseado(documento.getNroEspecialSade(), 30);
		}
		item.appendChild(new Listcell(nroDocEspecial));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (null != documento.getFechaCreacion()) {
		item.appendChild(   
				new Listcell(df.format(documento.getFechaCreacion())));
		}
//		} else {
//			item.appendChild(null);
//		}
		 
		item.appendChild(new Listcell(documento.getUsuarioGenerador()));
		
		item.appendChild(new Listcell(Utilitarios.motivoParseado(documento.getReferencia(), 30)));
		
		item.appendChild(new Listcell(documento.getTipoDocNombre()));
		
		Hbox divAcciones = new Hbox();
		Image imagenDescarga = new Image();
		imagenDescarga = new Image("/imagenes/Descargar.png");
		imagenDescarga.setTooltiptext(Labels
				.getLabel("gedo.inbox.avisos.descargar"));
		org.zkoss.zk.ui.sys.ComponentsCtrl
				.applyForward(imagenDescarga,
						"onClick=consultaDocumentosWindow$ConsultaDocumentosComposer.onDescargaDoc");
		
		Image imagenConsulta = new Image();
		imagenConsulta = new Image("/imagenes/edit-find.png");
		imagenConsulta.setTooltiptext(Labels
				.getLabel("gedo.consultaDocumentos.popupDoc"));
		org.zkoss.zk.ui.sys.ComponentsCtrl
				.applyForward(imagenConsulta,
						"onClick=consultaDocumentosWindow$ConsultaDocumentosComposer.onVisualizarDoc");
		divAcciones.appendChild(imagenDescarga);
		divAcciones.appendChild(imagenConsulta);
		
		Listcell celdaAcciones = new Listcell();
		celdaAcciones.appendChild(divAcciones);
		
		item.appendChild(celdaAcciones);
	}

}
