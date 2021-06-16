package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.web.satra.produccion.NuevaFamiliaTipoDocumentoComposer;

import java.util.ArrayList;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;



public class InboxFamiliaTipoDocumentoRenderer implements ListitemRenderer {

	public InboxFamiliaTipoDocumentoRenderer(NuevaFamiliaTipoDocumentoComposer nuevaFamiliaTipoDocumentoComposer) {

	}

	public void render(Listitem item, Object data, int arg2) throws Exception {

		FamiliaTipoDocumentoDTO familiaTipoDocumento = (FamiliaTipoDocumentoDTO) data;
		ArrayList<String> listaDocumentos = cargaListaDocumentosAsociados(familiaTipoDocumento);


//		Instancio los Listcell
		Listcell nombre = new Listcell(familiaTipoDocumento.getNombre());
		nombre.setParent(item);

		Listcell descripcion = new Listcell(familiaTipoDocumento.getDescripcion());
		descripcion.setParent(item);

		Listcell documentosAsociados = new Listcell("");
		documentosAsociados.setParent(item);

//		Si la lista de documentos esta vacia no muestro el combo
		if (listaDocumentos.size() > 0){
//			Combo con los Documentos Asociados
			Hbox documentoAsociado = new Hbox();

			Combobox cmbDocumentosAsociados = new Combobox();
			cmbDocumentosAsociados.setValue(listaDocumentos.get(0));
			cmbDocumentosAsociados.setModel(new ListModelList(listaDocumentos));
			cmbDocumentosAsociados.setReadonly(true);
			cmbDocumentosAsociados.setParent(documentoAsociado);

			documentoAsociado.setAlign("center");
			documentoAsociado.setParent(documentosAsociados);
		}

		Listcell currentCell;
		currentCell = new Listcell();
		currentCell.setParent(item);

		if (familiaTipoDocumento.getId() == null || familiaTipoDocumento.getId() != 1) {
			Hbox buttons = new Hbox();
			Image editarImage = new Image("/imagenes/pencil.png");
			Image eliminarImage = new Image("/imagenes/Eliminar.png");
			editarImage.setTooltiptext(Labels.getLabel("gedo.familiaTipoDocumento.tooltiptext.editar"));
			eliminarImage.setTooltiptext(Labels.getLabel("gedo.familiaTipoDocumento.tooltiptext.eliminar"));
			editarImage.setParent(buttons);
			eliminarImage.setParent(buttons);
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(editarImage,"onClick=nuevaFamiliaTipoDocumentoWindow$composer.onEditarFamilia");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminarImage,"onClick=nuevaFamiliaTipoDocumentoWindow$composer.onEliminarFamilia");
			buttons.setParent(currentCell);
		}
	}

	private ArrayList<String> cargaListaDocumentosAsociados(FamiliaTipoDocumentoDTO familiaTipoDocumento) {
//				Cargo la lista de documentos asociados
				ArrayList<String> listaDocumentos = new ArrayList<String>();
				String descripcionCombo;

				for (TipoDocumentoDTO listaTipoDocumento : familiaTipoDocumento.getListaTipoDocumento()){
					listaTipoDocumento.setDescripcion(listaTipoDocumento.getDescripcion()!=null?listaTipoDocumento.getDescripcion():"");
					if(!listaTipoDocumento.getAcronimo().toString().equals("") && !listaTipoDocumento.getNombre().equals("") && !listaTipoDocumento.getDescripcion().equals("")){
						if(listaTipoDocumento.getDescripcion().length()<10){
							descripcionCombo = listaTipoDocumento.getDescripcion();
						} else {
							descripcionCombo = listaTipoDocumento.getDescripcion().substring(0,10);
						}
						listaDocumentos.add(listaTipoDocumento.getAcronimo().toString() + " - " + listaTipoDocumento.getNombre() + " - " + descripcionCombo);
					}
				}
		return listaDocumentos;
	}
}