package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;


public class TipoDocumentoExpedienteComposer extends GenericForwardComposer  {

	private static final long serialVersionUID = 6815720791181817264L;

	private TreeModel treeModel;
	protected TipoDocumentoService tipoDocumentoService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.tipoDocumentoService = (TipoDocumentoService) SpringUtil
				.getBean(ConstantesServicios.TIPO_DOCUMENTO_SERVICE);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public void filtro(List<TipoDocumentoDTO> tiposDocumentos, Tree arbol) {
		List<DefaultTreeNode<TipoDocumentoDTO>> listaEstructuradaTree = new ArrayList<DefaultTreeNode<TipoDocumentoDTO>>();
		List<DefaultTreeNode<TipoDocumentoDTO>> listaTiposDocumentoAux = new ArrayList<DefaultTreeNode<TipoDocumentoDTO>>();
		if (tiposDocumentos != null) {
			listaEstructuradaTree.clear();
			TipoDocumentoDTO tipoDocumento = null;
			String codigoSadeAux;

			int indice = 0;
			int fin = tiposDocumentos.size();

			while (indice < fin) {
				listaTiposDocumentoAux.clear();
				codigoSadeAux = tiposDocumentos.get(indice)
						.getCodigoTipoDocumentoSade();
				while (indice < fin
						&& codigoSadeAux.equals(tiposDocumentos.get(indice)
								.getCodigoTipoDocumentoSade())) {
					tipoDocumento = tiposDocumentos.get(indice);
					listaTiposDocumentoAux.add(new DefaultTreeNode<TipoDocumentoDTO>(tipoDocumento));
					indice++;
				}
				listaEstructuradaTree.add(new DefaultTreeNode(codigoSadeAux,
						new ArrayList<DefaultTreeNode<TipoDocumentoDTO>>(listaTiposDocumentoAux)));
			}
			this.treeModel = new DefaultTreeModel(new DefaultTreeNode("ROOT",
					listaEstructuradaTree));
		}
		arbol.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data, int arg1) throws Exception {
				DefaultTreeNode nodo = (DefaultTreeNode) data;
				if (nodo.getData() instanceof String) {
					Treerow tr = new Treerow();
					tr.setParent(item);
					tr.setId(null);
					tr.appendChild(new Treecell((String) nodo.getData()));
				}
				if (nodo.getData() instanceof TipoDocumentoDTO) {
					TipoDocumentoDTO td = (TipoDocumentoDTO) nodo.getData();
					Treerow tr = new Treerow();
					tr.setParent(item);
					if (td.getIdTipoDocumentoGedo() != null) {
						tr.setId(td.getIdTipoDocumentoGedo().toString());
						tr.appendChild(new Treecell(td
								.getDescripcionTipoDocumentoSade()));
						tr.appendChild(new Treecell(td.getAcronimo()));
					} else {
						tr.setId(td.getCodigoTipoDocumentoSade());
						tr.appendChild(new Treecell(td
								.getDescripcionTipoDocumentoSade()));
						tr.appendChild(new Treecell(td
								.getCodigoTipoDocumentoSade()));
					}
				}
			}
		});
		arbol.setModel(this.treeModel);
	}

	public void recargarComponente(Component comp, AnnotateDataBinder db) {
		db = new AnnotateDataBinder(comp);
		db.loadComponent(comp);
	}

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
		return valor;
	}

	public List<TipoDocumentoDTO> cargarlistaTiposDocumentosFiltrada(
			List<TipoDocumentoDTO> listaTiposDocumentoEspecialCompleta,
			String criterio) {
		List<TipoDocumentoDTO> listaFiltrada = new ArrayList<TipoDocumentoDTO>();
		String codig;
		String acron;
		String descr;
		String descrCodig;
		String descrAcron;
		for (TipoDocumentoDTO td : listaTiposDocumentoEspecialCompleta) {
			codig = "";
			acron = "";
			descr = "";
			if (td.getCodigoTipoDocumentoSade() != null)
				codig = td.getCodigoTipoDocumentoSade().toLowerCase();
			if (td.getAcronimo() != null)
				acron = td.getAcronimo().toLowerCase();
			if (td.getDescripcionTipoDocumentoSade() != null)
				descr = td.getDescripcionTipoDocumentoSade().toLowerCase();
							
			descrCodig = descr + " - " + codig;
			descrAcron = descr + " - " + acron;

			if (codig.contains(criterio) || acron.contains(criterio)
					|| descr.contains(criterio)
					|| descrCodig.toLowerCase().equals(criterio.toLowerCase())
					|| descrAcron.toLowerCase().equals(criterio.toLowerCase()))
				listaFiltrada.add(td);

		}
		return listaFiltrada;
	}

	public String autocompletarTipoDocumento(String criterio) {
		if (criterio != null && !criterio.trim().isEmpty()) {
			String codig;
			String descr;
			String descrCodig;
			List<TipoDocumentoDTO> listaTiposDocumentoEspecialCompleta = this.tipoDocumentoService
					.obtenerTiposDocumento();
			for (TipoDocumentoDTO td : listaTiposDocumentoEspecialCompleta) {
				codig = "";
				descr = "";
				if (td.getCodigoTipoDocumentoSade() != null)
					codig = td.getCodigoTipoDocumentoSade();
				if (td.getDescripcionTipoDocumentoSade() != null)
					descr = td.getDescripcionTipoDocumentoSade();

				descrCodig = descr + " - " + codig;

				if (codig.toLowerCase().contains(criterio.toLowerCase())
						|| descrCodig.toLowerCase().equals(
								criterio.toLowerCase()))
					return descrCodig;
			}
		}
		return null;

	}
	
	public String autocompletarTipoDocumentoEspecial(String criterio) {
		if (criterio != null && !criterio.trim().isEmpty()) {
			String acron;
			String descr;
			String descrAcron;
			List<TipoDocumentoDTO> listaTiposDocumentoEspecialCompleta = this.tipoDocumentoService
					.obtenerTiposDocumentoGEDOEspecial();
			for (TipoDocumentoDTO  td : listaTiposDocumentoEspecialCompleta) {
				acron = "";
				descr = "";
				if (td.getCodigoTipoDocumentoSade() != null)
					acron = td.getAcronimo();
				if (td.getDescripcionTipoDocumentoSade() != null)
					descr = td.getDescripcionTipoDocumentoSade();

				descrAcron = descr + " - " + acron;

				if (acron.toLowerCase().contains(criterio.toLowerCase()) || descrAcron.toLowerCase().equals(
								criterio.toLowerCase()))
					return descrAcron;
			}
		}
		return null;

	}
	
	public boolean validarTipoDocumentoEspecial(String criterio){
		String acron;
		String descr;
		String descrAcron;
		List<TipoDocumentoDTO> listaTiposDocumentoEspecialCompleta = this.tipoDocumentoService.obtenerTiposDocumentoGEDOEspecial();
		for(TipoDocumentoDTO td : listaTiposDocumentoEspecialCompleta){
			acron="";
			descr="";
			if(td.getAcronimo()!=null)
				acron= td.getAcronimo().toLowerCase();
			if(td.getDescripcionTipoDocumentoSade()!=null)
				descr=td.getDescripcionTipoDocumentoSade().toLowerCase();
			descrAcron =   descr + " - " + acron;
			if(acron.toLowerCase().equals(criterio.toLowerCase()) || descrAcron.toLowerCase().equals(criterio.toLowerCase()))
				return true;
		}
		return false;
	}
	
	public boolean validarTipoDocumento(String criterio){
		String codig;
		String descr;
		String descrCodig;
		List<TipoDocumentoDTO> listaTiposDocumentoEspecialCompleta = this.tipoDocumentoService.obtenerTiposDocumento();
		for(TipoDocumentoDTO td : listaTiposDocumentoEspecialCompleta){
			codig="";
			descr="";
			if(td.getCodigoTipoDocumentoSade()!=null)
				codig= td.getCodigoTipoDocumentoSade().toLowerCase();
			if(td.getDescripcionTipoDocumentoSade()!=null)
				descr=td.getDescripcionTipoDocumentoSade().toLowerCase();
			descrCodig =   descr + " - " + codig;
			if(codig.toLowerCase().equals(criterio.toLowerCase()) || descrCodig.toLowerCase().equals(criterio.toLowerCase()))
				return true;
		}
		return false;
	}
}
