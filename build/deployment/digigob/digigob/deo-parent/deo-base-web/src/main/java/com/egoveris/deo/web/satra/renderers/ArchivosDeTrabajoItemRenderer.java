package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;



public class ArchivosDeTrabajoItemRenderer implements ListitemRenderer {
	@Override
	public void render(Listitem item, Object data, int arg2) throws Exception {
		
		ArchivoDeTrabajoDTO archivoDeTrabajo = (ArchivoDeTrabajoDTO) data;
		
		Listcell currentCell;
		
		int ordenIncial = 1;
		int numOrden = item.getIndex()+ordenIncial;
		String numeroDeOrden = Integer.toString(numOrden);
		
		//Orden
		new Listcell(numeroDeOrden).setParent(item);
		
		//Archivo
		Listcell nombreArchivo = new Listcell();
		StringBuilder nombreTruncado= new StringBuilder("");
		if(archivoDeTrabajo.getNombreArchivo()!=null && archivoDeTrabajo.getNombreArchivo().length()>30){
			nombreTruncado.append(archivoDeTrabajo.getNombreArchivo().substring(0, 30));
			nombreTruncado.append("...");
			nombreArchivo.setLabel(nombreTruncado.toString());
		}else{
			nombreArchivo.setLabel(archivoDeTrabajo.getNombreArchivo());
		}
		
		nombreArchivo.setTooltiptext(archivoDeTrabajo.getNombreArchivo());
		nombreArchivo.setParent(item);
		
		
		currentCell=new Listcell();

		currentCell.setParent(item);
		
		Hbox hbox=new Hbox();
		
		Image descargarImage  = new Image("/imagenes/Descargar.png");
		
		Label visualizar = new Label(Labels.getLabel("gedo.archivosDeTrabajo.label.visualizar"));
		
		descargarImage.setParent(hbox);				
		
		visualizar.setParent(hbox);
		
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,"onClick=archivoTrabajoWindow$composer.onVisualizarArchivosDeTrabajo");
		
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(descargarImage,"onClick=archivoTrabajoWindow$composer.onVisualizarArchivosDeTrabajo");
		
		if(!archivoDeTrabajo.isDefinitivo()){
			
				Image eliminarImage  = new Image("/imagenes/Eliminar.png");
			
				Label ejecutar = new Label(Labels.getLabel("ccoo.alertaCO.eliminar"));
				
				org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,"onClick=archivoTrabajoWindow$composer.onEliminarArchivoDeTrabajo");
				
				org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminarImage,"onClick=archivoTrabajoWindow$composer.onEliminarArchivoDeTrabajo");
				
				eliminarImage.setParent(hbox);
				
				ejecutar.setParent(hbox);
		}
	
		hbox.setParent(currentCell);
		
	}
	
}
