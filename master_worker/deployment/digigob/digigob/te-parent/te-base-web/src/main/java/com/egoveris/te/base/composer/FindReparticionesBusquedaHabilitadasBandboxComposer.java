package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.te.base.model.EstructuraBean;
import com.egoveris.te.base.service.iface.IEstructuraService;
import com.egoveris.te.base.util.FindReparticionesImportar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;


/**
 * 
 * Crea el arbol de ADM en la busqueda
 *
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesBusquedaHabilitadasBandboxComposer extends FindReparticionesImportar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2520587903892655953L;

	
	@Autowired
	private Radio busquedaPorNombreRadioButton;
	@Autowired
	private Tree reparticionesImportarDocumentoSADEPorEstructuraTree;
	@Autowired
	private Paging paginatorIncorporarSADE;
	
	private TreeModel treeModel;
	private AnnotateDataBinder binder;
	
	@WireVariable("estructuraServiceImpl")
	private IEstructuraService estructuraService;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.binder = new AnnotateDataBinder(reparticionesImportarDocumentoSADEPorEstructuraTree);

		
		cargarReparticionesPorEstructura(super.listaReparticionesSADECompleta);
		this.binder.loadAll();
	}
	
	public void onChanging$reparticionImportarDocumentoSADE(InputEvent e){
		super.textoReparticionImportarDocumentoSADE.setValue(e.getValue());
		this.onChanging$textoReparticionImportarDocumentoSADE(e);
	}
	
	@Override
	public void onChanging$textoReparticionImportarDocumentoSADE(InputEvent e) {
		String matchingText = e.getValue();
		super.cargarReparticiones(e);
		if (!matchingText.equals("") && (matchingText.length() >= 3))
			cargarReparticionesPorEstructura(super.listaReparticionSADESeleccionada);
		else if(matchingText.equals("")){
			cargarReparticionesPorEstructura(super.listaReparticionesSADECompleta);
		}
	}
	
	public void cargarReparticionesPorEstructura(List<ReparticionBean> listaReparticionesACargar){
		List<DefaultTreeNode> listaEstructuradaTree = new ArrayList<DefaultTreeNode>();
		List<DefaultTreeNode> listaReparticionesAux = new ArrayList<DefaultTreeNode>();
		
		if (listaReparticionesACargar != null) {
			listaEstructuradaTree.clear();
			ReparticionBean reparticion = null;
			EstructuraBean nombreEstructuraAux;
			int IdEstructuraAux;
			
			int indice = 0;
			int fin = listaReparticionesACargar.size();
			
			while(indice<fin){
				listaReparticionesAux.clear();
				nombreEstructuraAux = estructuraService.buscarEstructuraPorId(listaReparticionesACargar.get(indice).getIdEstructura());
				IdEstructuraAux = listaReparticionesACargar.get(indice).getIdEstructura();
				while(indice<fin && listaReparticionesACargar.get(indice).getIdEstructura() == IdEstructuraAux){
					reparticion = listaReparticionesACargar.get(indice);
					listaReparticionesAux.add(new DefaultTreeNode(reparticion));
					indice ++;
				}
				listaEstructuradaTree.add(new DefaultTreeNode(nombreEstructuraAux, listaReparticionesAux));
			}
			this.treeModel = new DefaultTreeModel(new DefaultTreeNode("ROOT", listaEstructuradaTree));
		}
		reparticionesImportarDocumentoSADEPorEstructuraTree.setTreeitemRenderer(new TreeitemRenderer() {
			public void render(Treeitem item, Object data,int arg1) throws Exception {
				DefaultTreeNode tn = (DefaultTreeNode) data; 
				if (tn.getData() instanceof String){
					Treerow tr = new Treerow();
			        tr.setParent(item);
			        tr.setId(null);
			        tr.appendChild(new Treecell((String) tn.getData() ));
				}
				if (tn.getData()  instanceof ReparticionBean){
					ReparticionBean repa = (ReparticionBean)tn.getData() ;
					Treerow tr = new Treerow();
					tr.setParent(item);
					tr.setId(repa.getCodigo());
					tr.appendChild(new Treecell(repa.getCodigo()));
					tr.appendChild(new Treecell(repa.getNombre()));
					
				}
			}
		});
		reparticionesImportarDocumentoSADEPorEstructuraTree.setModel(this.treeModel);
		this.binder.loadComponent(reparticionesImportarDocumentoSADEPorEstructuraTree);
	}
	
	public void onSelect$reparticionesImportarDocumentoSADEPorEstructuraTree() throws InterruptedException{
		String codigoRepa = reparticionesImportarDocumentoSADEPorEstructuraTree.getSelectedItem().getTreerow().getId();
		if(codigoRepa != null && !codigoRepa.equals("")){
			super.reparticionImportarDocumentoSADE.setValue(codigoRepa);
			super.textoReparticionImportarDocumentoSADE.setValue(null);
			super.listaReparticionSADESeleccionada.clear();
			this.binder.loadComponent(super.reparticionImportarDocumentoSADE);
			cargarReparticionesPorEstructura(super.listaReparticionesSADECompleta);
			super.reparticionImportarDocumentoSADE.close();
		}
	}
	
	public void onCheck$busquedaRadioGroup(){
		if(busquedaPorNombreRadioButton.isChecked()){
			this.paginatorIncorporarSADE.setVisible(true);
			super.reparticionesImportarDocumentoSADEListbox.setVisible(true);
			this.reparticionesImportarDocumentoSADEPorEstructuraTree.setVisible(false);
		}
		else {
			this.paginatorIncorporarSADE.setVisible(false);
			super.reparticionesImportarDocumentoSADEListbox.setVisible(false);
			this.reparticionesImportarDocumentoSADEPorEstructuraTree.setVisible(true);
		}
	}
}
	
	
	
	
	
	
	
	
	
	
	

