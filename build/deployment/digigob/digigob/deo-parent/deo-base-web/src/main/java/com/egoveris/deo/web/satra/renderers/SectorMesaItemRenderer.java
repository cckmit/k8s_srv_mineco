package com.egoveris.deo.web.satra.renderers;


import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class SectorMesaItemRenderer implements ListitemRenderer {

	
	public void render(Listitem item, Object data, int arg2) throws Exception {
				
		
		
	    Listcell celdaEspecial = new Listcell();	
		item.appendChild(celdaEspecial);						
				

		Listcell celdaAcciones = new Listcell();
		Hbox hbox = new Hbox();
		
		Image redirigir = new Image("/imagenes/Redirigir.png"); 
		Image descargar = new Image("/imagenes/Descargar.png");
		Image eliminar = new Image("/imagenes/Eliminar.png");
		redirigir.setTooltiptext(Labels.getLabel("ccoo.sectorMesa.redirigir"));
		descargar.setTooltiptext(Labels.getLabel("ccoo.sectorMesa.descargarDocumento"));
		eliminar.setTooltiptext(Labels.getLabel("ccoo.sectorMesa.eliminarComunicacion"));
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(redirigir,
				"onClick=sectorMesaWindow$SectorMesaCoComposer.onExecuteRedirect");
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(descargar,
			"onClick=sectorMesaWindow$SectorMesaCoComposer.onExecuteDownLoad");
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminar,
				"onClick=sectorMesaWindow$SectorMesaCoComposer.onExecuteDelete");

		hbox.appendChild(redirigir);	
		hbox.appendChild(descargar);
		hbox.appendChild(eliminar);
		celdaAcciones.appendChild(hbox);
		item.appendChild(celdaAcciones);
		
	}
}
