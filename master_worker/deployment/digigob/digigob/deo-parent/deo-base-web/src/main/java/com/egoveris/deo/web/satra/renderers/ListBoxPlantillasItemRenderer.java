package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.PlantillaDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;



public class ListBoxPlantillasItemRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data, int arg2) throws Exception {

		PlantillaDTO plantilla = (PlantillaDTO) data;

		Listcell nombre = new Listcell(plantilla.getNombre());
		nombre.setParent(item);
        if(plantilla.getDescripcion()!=null){
		Listcell descricion = new Listcell((plantilla.getDescripcion().length()<15)?plantilla.getDescripcion(): plantilla.getDescripcion().substring(0, 10) + "...");
		descricion.setTooltiptext(plantilla.getDescripcion());
		descricion.setParent(item);
        } else {
        	Listcell descricion = new Listcell();
        	descricion.setParent(item);
        }

		Hbox botones = new Hbox();
		Listcell cell = new Listcell();
		cell.setParent(item);
		botones.setParent(cell);

		Image image = new Image("/imagenes/pencil.png");
		image.setTooltiptext(
				Labels.getLabel("gedo.listboxPlantillasItemRenderer.tooltip.editarPlantilla"));
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(image, "onClick=plantillasWindow$PlantillasComposer.onEditarPlantilla");
		image.setParent(botones);

		Image eliminar = new Image("/imagenes/Eliminar.png");
		eliminar.setTooltiptext(
				Labels.getLabel("gedo.listboxPlantillasItemRenderer.tooltip.eliminarPlantilla"));
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminar, "onClick=plantillasWindow$PlantillasComposer.onEliminarPlantilla");
		eliminar.setParent(botones);

	}

}
