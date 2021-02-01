package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.egoveris.shared.collection.CollectionUtils;

public class PrevisualizacionComboComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -3977759528950818764L;

	// Componentes ZK
	private Window previsualizacionCombo;
	private Label nombreCombo;
	private Combobox combobox;
	private Bandbox bandbox;

	// Atributos
	private List<ComboboxDTO> listaItems;
	private List<String> listaItemsDescripciones;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		listaItemsDescripciones = new ArrayList<String>();
		mostrarCombo();
	}

	public void mostrarCombo() {
		nombreCombo.setValue((String) Executions.getCurrent().getArg()
				.get("nombreCombobox"));
		setListaItems(CollectionUtils.asList(Executions.getCurrent().getArg()
				.get("items"), ComboboxDTO.class));
		boolean mostrar = (Boolean)Executions.getCurrent().getArg().get("mostrar");
		if(mostrar){
			combobox.setVisible(false);
			bandbox.setVisible(true);
		}
		for (ComboboxDTO dato : listaItems) {
			this.listaItemsDescripciones.add(dato.getDescripcion());
		}

	}

	public void onClose() {
		this.previsualizacionCombo.onClose();
	}

	public List<ComboboxDTO> getListaItems() {
		return listaItems;
	}

	public void setListaItems(List<ComboboxDTO> listaItems) {
		this.listaItems = listaItems;
	}

	public List<String> getListaItemsDescripciones() {
		return listaItemsDescripciones;
	}

	public void setListaItemsDescripciones(List<String> listaItemsDescripciones) {
		this.listaItemsDescripciones = listaItemsDescripciones;
	}

}
