package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.base.util.UtilsDate;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;

import java.util.Date;

import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class ArchivosEmbebidosItemRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data, int arg2) throws Exception {
		
		ArchivoEmbebidoDTO archivoEmbebido = (ArchivoEmbebidoDTO) data;
		
		Listcell currentCell=null;
		
		int ordenIncial = 1;
		int numOrden = item.getIndex()+ordenIncial;
		String numeroDeOrden = Integer.toString(numOrden);
		
		//Orden
		new Listcell(numeroDeOrden).setParent(item);
		
		//Archivo
		Listcell nombreArchivo = new Listcell();
		StringBuffer nombreTruncado= new StringBuffer("");
		if(archivoEmbebido.getNombreArchivo()!=null && archivoEmbebido.getNombreArchivo().length()>50){
			nombreTruncado.append(archivoEmbebido.getNombreArchivo().substring(0, 50));
			nombreTruncado.append("...");
			nombreArchivo.setLabel(nombreTruncado.toString());
		}else{
			nombreArchivo.setLabel(archivoEmbebido.getNombreArchivo());
		}
		
		nombreArchivo.setTooltiptext(archivoEmbebido.getNombreArchivo());
		nombreArchivo.setParent(item);
		
		//Usuario Asociador
		Listcell usuarioAsociadorLc = new Listcell();
		usuarioAsociadorLc.setParent(item);
		Label usuarioAsociadorLabel = new Label();
		usuarioAsociadorLabel.setParent(usuarioAsociadorLc);
		usuarioAsociadorLabel.setValue(archivoEmbebido.getUsuarioAsociador());
		
		//Fecha
		Listcell fechaLc = new Listcell();
		fechaLc.setParent(item);
		Label fechaLabel = new Label();
		Date fecha= archivoEmbebido.getFechaAsociacion();
		fechaLabel.setParent(fechaLc);
		fechaLabel.setValue(UtilsDate.convertDateToFormalString(fecha));
		
		//Accion
		currentCell=new Listcell();

		currentCell.setParent(item);
		
		Hbox hbox=new Hbox();
		
		Image descargarImage  = new Image("/imagenes/Descargar.png");
		
		Label visualizar = new Label("Visualizar");
		
		descargarImage.setParent(hbox);				
		
		visualizar.setParent(hbox);
		
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,"onClick=archivoEmbebidoWindow$composer.onVisualizarArchivosEmbebido");
		
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(descargarImage,"onClick=archivoEmbebidoWindow$composer.onVisualizarArchivosEmbebido");
		
		Image eliminarImage  = new Image("/imagenes/Eliminar.png");
		
		Label ejecutar = new Label("Eliminar");
				
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,"onClick=archivoEmbebidoWindow$composer.onEliminarArchivoEmbebido");
				
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminarImage,"onClick=archivoEmbebidoWindow$composer.onEliminarArchivoEmbebido");
				
		eliminarImage.setParent(hbox);
				
		ejecutar.setParent(hbox);
	
		hbox.setParent(currentCell);
		
	}
		
}
