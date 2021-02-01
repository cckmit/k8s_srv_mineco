package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.TipoDocumentoDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;


public class TipoDocumentoEspecialExpedienteBandboxComposer extends
		TipoDocumentoExpedienteComposer {

	private static final long serialVersionUID = 5712501002999884499L;
	// componentes y variables
	// componentes
	@Autowired
	private Bandbox tiposDocumentoEspecialBandbox;
	@Autowired
	private Textbox textoTipoDocumentoEspcial;
	@Autowired
	private Tree specialDocumentTypeTree;
	// variables
	private List<TipoDocumentoDTO> listaTiposDocumentoEspecialCompleta;
	private List<TipoDocumentoDTO> listaTiposDocumentoEspecialFiltrada;
	@Autowired
	private AnnotateDataBinder binder;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.listaTiposDocumentoEspecialCompleta = super.tipoDocumentoService
				.obtenerTiposDocumentoGEDOEspecial();
		filtro(listaTiposDocumentoEspecialCompleta, specialDocumentTypeTree);
		recargarComponente(specialDocumentTypeTree, binder);
	}

	@SuppressWarnings("unchecked")
	public void onSelect$specialDocumentTypeTree() throws InterruptedException {
		String idTipoDocumento = specialDocumentTypeTree.getSelectedItem()
				.getTreerow().getId();
		List<Component> treeSelectedItem = (List<Component>) specialDocumentTypeTree
				.getSelectedItem().getTreerow().getChildren();
		if (idTipoDocumento != null && !idTipoDocumento.equals("")) {
			String valorBandbox = contenidoSelectedItem(treeSelectedItem);
			tiposDocumentoEspecialBandbox.setTooltip(idTipoDocumento);
			tiposDocumentoEspecialBandbox.setValue(valorBandbox);
			InputEvent e = new InputEvent("onChanging",
					textoTipoDocumentoEspcial, "",
					textoTipoDocumentoEspcial.getValue());
			this.onChanging$textoTipoDocumentoEspcial(e);
			recargarComponente(textoTipoDocumentoEspcial, binder);
			recargarComponente(tiposDocumentoEspecialBandbox, binder);
			this.tiposDocumentoEspecialBandbox.close();
		}
	}

	public void onChanging$tiposDocumentoEspecialBandbox(InputEvent e) {
		InputEvent ev = new InputEvent("onChanging", textoTipoDocumentoEspcial,
				e.getValue(), textoTipoDocumentoEspcial.getValue());
		this.textoTipoDocumentoEspcial.setValue(e.getValue());
		this.onChanging$textoTipoDocumentoEspcial(ev);
	}

	public void onChanging$textoTipoDocumentoEspcial(InputEvent e) {
		this.textoTipoDocumentoEspcial.clearErrorMessage();
		String matchingText = e.getValue().trim();
		if (!matchingText.equals("")
				&& (matchingText.length() >= 2 || (validarTipoDocumentoEspecial(matchingText)))) {
			this.listaTiposDocumentoEspecialFiltrada = super
					.cargarlistaTiposDocumentosFiltrada(
							this.listaTiposDocumentoEspecialCompleta,
							matchingText.toLowerCase());
			if (!this.listaTiposDocumentoEspecialFiltrada.isEmpty()) {
				filtro(this.listaTiposDocumentoEspecialFiltrada,
						specialDocumentTypeTree);
				recargarComponente(specialDocumentTypeTree, binder);
			} else {
				throw new WrongValueException(
						this.textoTipoDocumentoEspcial,
						Labels.getLabel("ee.busquedaTipoDocumentoEspecial.textoInexistente"));
			}
		} else if (matchingText.equals("")) {
			filtro(this.listaTiposDocumentoEspecialCompleta,
					specialDocumentTypeTree);
			recargarComponente(specialDocumentTypeTree, binder);
		}
	}

	public void onBlur$tiposDocumentoEspecialBandbox() {
		if (validarTipoDocumentoEspecial(this.tiposDocumentoEspecialBandbox
				.getValue())){
			this.tiposDocumentoEspecialBandbox
					.setValue(autocompletarTipoDocumentoEspecial(this.tiposDocumentoEspecialBandbox
							.getValue()));
		}else if(!this.tiposDocumentoEspecialBandbox
				.getValue().trim().isEmpty()){
			throw new WrongValueException(
					this.tiposDocumentoEspecialBandbox,
					Labels.getLabel("ee.busquedaTipoDocumentoEspecial.textoInexistente"));
		}
//		this.textoTipoDocumentoEspcial.setValue("");
	}

}
