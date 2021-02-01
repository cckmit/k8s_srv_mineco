package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;

/**
 * @author sroidza
 * 
 */

import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ArchivosDeTrabajoTramitacionEspecialRenderer implements ListitemRenderer{
		
		public void render(Listitem item, Object data,int arg1) throws Exception {
			
			ArchivoDeTrabajoDTO archivoDeTrabajo = (ArchivoDeTrabajoDTO) data;
			
			Listcell currentCell;
					
			int ordenIncial = 1;
			int numOrden = item.getIndex()+ordenIncial;
			String numeroDeOrden = Integer.toString(numOrden);
			
			//Numero de Posicion
			new Listcell(numeroDeOrden).setParent(item);
			
			//Archivo a subir
			new Listcell(archivoDeTrabajo.getNombreArchivo()).setParent(item);

			currentCell=new Listcell();

			currentCell.setParent(item);
			
			Hbox hbox=new Hbox();
			
			Image runImage=new Image("/imagenes/play.png");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,"onClick=tramitacionEspecialWindow$composer.onVisualizarArchivosDeTrabajo");
			runImage.setParent(hbox);
			
			Label visualizar = new Label("Visualizar");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,"onClick=tramitacionEspecialWindow$composer.onVisualizarArchivosDeTrabajo");
			visualizar.setParent(hbox);

			hbox.setParent(currentCell);
			
		}
		
	}

	
	

