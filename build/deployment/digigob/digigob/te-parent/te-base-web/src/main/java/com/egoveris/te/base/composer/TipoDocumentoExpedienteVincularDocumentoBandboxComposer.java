package com.egoveris.te.base.composer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecell;

import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesServicios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TipoDocumentoExpedienteVincularDocumentoBandboxComposer extends TipoDocumentoExpedienteComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8091292339356714696L;
	
	private static final String PUNTOS_SUSPENSIVOS = "...";
	private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_DESC = 23;
	private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_NOMBRE = 21;

	@Autowired
	public Tree documentTypeTree;
	@Autowired
	private AnnotateDataBinder binder;
	@Autowired
	private TreeModel treeModel;
//	private ArrayList<TipoDocumentoDTO> listaFamiliasSeleccionadas;
//	private ArrayList<TipoDocumentoDTO> listaFamiliasTotal;
	@Autowired
	private Textbox	tipoDocumentoTexto ;
	@Autowired
	protected Bandbox tiposDocumentoBandbox;

	
//	@Autowired
//	private Window inicioDocumentoWindow;
	
  private List<TipoDocumentoDTO> listaTiposDocumentoCompleta;
  private List<TipoDocumentoDTO> listaTiposDocumentoFiltrada;
  
 
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		 this.listaTiposDocumentoCompleta = super.tipoDocumentoService.obtenerTiposDocumento();
    filtro(listaTiposDocumentoCompleta, documentTypeTree);
    recargarComponente(documentTypeTree, binder);
		
	}
	@Override
	public String contenidoSelectedItem(List<Component> selectedItem) {
		String valor = "";
		for (Component tc : selectedItem) {
			if (tc instanceof Treecell){
				Treecell tc1 = (Treecell) tc;
				if (valor.equals(""))
					valor = valor + tc1.getLabel();
				else
					valor = valor + " - " + tc1.getLabel();
			}
		}
		return valor.split("-")[1].trim().toUpperCase();
	}
	
	@SuppressWarnings("unchecked")
	public void onSelect$documentTypeTree() throws InterruptedException {
		String idTipoDocumento = documentTypeTree.getSelectedItem()
				.getTreerow().getId();
		List<Component> treeSelectedItem = (List<Component>) documentTypeTree
				.getSelectedItem().getTreerow().getChildren();
		if (idTipoDocumento != null && !idTipoDocumento.equals("")) {
			String valorBandbox = contenidoSelectedItem(treeSelectedItem);
			tiposDocumentoBandbox.setTooltip(idTipoDocumento);
			tiposDocumentoBandbox.setValue(valorBandbox);
			InputEvent e = new InputEvent("onChanging",
					tipoDocumentoTexto, "",
					tipoDocumentoTexto.getValue());
			this.onChanging$tipoDocumentoTexto(e);
			recargarComponente(tipoDocumentoTexto, binder);
			recargarComponente(tiposDocumentoBandbox, binder);
			this.tiposDocumentoBandbox.close();
		}
	}
	
	public void onChanging$tiposDocumentoBandbox(InputEvent e) {
		InputEvent ev = new InputEvent("onChanging", tipoDocumentoTexto,
				e.getValue(), tipoDocumentoTexto.getValue());
		this.tipoDocumentoTexto.setValue(e.getValue());
		this.onChanging$tipoDocumentoTexto(ev);
	}
	
	public void onChanging$tipoDocumentoTexto(InputEvent e) {
		this.tipoDocumentoTexto.clearErrorMessage();
		String matchingText = e.getValue().trim();
		if (!matchingText.equals("")
				&& (matchingText.length() >= 2 || (validarTipoDocumentoEspecial(matchingText)))) {
			this.listaTiposDocumentoFiltrada = super
					.cargarlistaTiposDocumentosFiltrada(
							this.listaTiposDocumentoCompleta,
							matchingText.toLowerCase());
			if (!this.listaTiposDocumentoFiltrada.isEmpty()) {
				filtro(this.listaTiposDocumentoFiltrada,
						documentTypeTree);
				recargarComponente(documentTypeTree, binder);
			} else {
				throw new WrongValueException(
						this.tipoDocumentoTexto,
						Labels.getLabel("ee.busquedaTipoDocumento.textoInexistente"));
			}
		} else if (matchingText.equals("")) {
			filtro(this.listaTiposDocumentoCompleta,
					documentTypeTree);
			recargarComponente(documentTypeTree, binder);
		}
	}
	
	public void onBlur$tiposDocumentoBandbox() {
		if (validarTipoDocumento(this.tiposDocumentoBandbox
				.getValue())){
			this.tiposDocumentoBandbox
					.setValue(autocompletarTipoDocumento(this.tiposDocumentoBandbox
							.getValue()));
		}else if(!this.tiposDocumentoBandbox
				.getValue().trim().isEmpty()){
			throw new WrongValueException(
					this.tiposDocumentoBandbox,
					Labels.getLabel("ee.busquedaTipoDocumento.textoInexistente"));
		}
	}
	
	@Override
	public String autocompletarTipoDocumento(String criterio) {
		if (criterio != null && !criterio.trim().isEmpty()) {
			String codig;
			String descr;
			String descrCodig;
			List<TipoDocumentoDTO> listaTiposDocumentoEspecialCompleta = super.tipoDocumentoService
					.obtenerTiposDocumento();
			for (TipoDocumentoDTO td : listaTiposDocumentoEspecialCompleta) {
				codig = "";
				descr = "";
				if (td.getCodigoTipoDocumentoSade() != null)
					codig = td.getCodigoTipoDocumentoSade();
				if (td.getDescripcionTipoDocumentoSade() != null)
					descr = td.getDescripcionTipoDocumentoSade();

				descrCodig =  codig.toUpperCase();

				if (codig.toLowerCase().contains(criterio.toLowerCase())
						|| descrCodig.toLowerCase().equals(
								criterio.toLowerCase()))
					return descrCodig;
			}
		}
		return null;

	}


}