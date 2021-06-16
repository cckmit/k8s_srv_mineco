package com.egoveris.te.base.composer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InicioDocumentoFamiliaTipoDocumentoBandBoxComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8091292339356714696L;
	
	private static final String PUNTOS_SUSPENSIVOS = "...";
	private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_DESC = 23;
	private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_NOMBRE = 21;

	@Autowired
	public Tree familiaTipoTree;
	@Autowired
	private AnnotateDataBinder binder;
	@Autowired
	private TreeModel treeModel;
	private ArrayList<TipoDocumentoDTO> listaFamiliasSeleccionadas;
	private ArrayList<TipoDocumentoDTO> listaFamiliasTotal;
	@Autowired
	private Textbox	textoTipoDocumento ;
	@Autowired
	protected Bandbox familiaEstructuraTree;
	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	private TipoDocumentoService tipoDocumentoService;
	private Boolean docImportado = true;
	private Component padreEchoEvent;
	
//	@Autowired
//	private Window inicioDocumentoWindow;
	
	public static final String SET_COMPONENT_PARENT = "onSetParentComponent";
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		comp.addEventListener(Events.ON_NOTIFY, new InicioDocumentoFamiliaTipoDocumentoBandBoxComposerListener(this));
		comp.addEventListener(SET_COMPONENT_PARENT, new InicioDocumentoFamiliaTipoDocumentoBandBoxComposerListener(this));
		if (Executions.getCurrent().getArg().get("docImportado")  != null) {
			docImportado =  (Boolean) Executions.getCurrent().getArg().get("docImportado");
		}	
		// 27/04/2020: Debe traer los tipos de documento importados o todos dependeindo de la condicion
		if (docImportado){
			this.listaFamiliasTotal = new ArrayList<TipoDocumentoDTO>(tipoDocumentoService.obtenerTiposDocumentoGedoImportados());
		} else {
			this.listaFamiliasTotal = new ArrayList<TipoDocumentoDTO>(tipoDocumentoService.obtenerTiposDocumentoGEDOHabilitados());
		}		
		this.listaFamiliasSeleccionadas = new ArrayList<TipoDocumentoDTO>(this.listaFamiliasTotal);
		this.binder = new AnnotateDataBinder(this.familiaTipoTree);
		cargarReparticionesPorEstructura(this.listaFamiliasSeleccionadas);
		this.binder.loadAll();
	}

	@SuppressWarnings({ "null", "deprecation" })
	private void cargarReparticionesPorEstructura(ArrayList<TipoDocumentoDTO> familias) {
		
			List<DefaultTreeNode> listaEstructuradaTree = new ArrayList<DefaultTreeNode>();
			
			if(familias.size()==0){
				throw new WrongValueException(this.familiaEstructuraTree, Labels.getLabel("ee.general.tipoDocumentoInvalido"));
			}else {
				listaEstructuradaTree.clear();

				boolean agrego = false;
				
				for (TipoDocumentoDTO tipoDoc : familias) {
					//el arbol esta vacio agrego el primero
					if(listaEstructuradaTree.size()==0){
						List<TreeNode> listaNueva = new ArrayList<TreeNode>();
						listaNueva.add(new DefaultTreeNode(tipoDoc));
						listaEstructuradaTree.add(new DefaultTreeNode(tipoDoc.getFamilia().getNombre(), listaNueva));
						agrego = true;
					}
					//busca donde esta el mismo nombre y agrega al tree
					if(!agrego){
						for (DefaultTreeNode treeNode : listaEstructuradaTree) {
							if(((String)treeNode.getData()).equals(tipoDoc.getFamilia().getNombre())){
								 treeNode.getChildren().add(new DefaultTreeNode(tipoDoc));
								agrego = true;
								break;
							}
						}
					}
					//si no esta en el tree termina agregando una nueva rama..
					if(!agrego){
						List<DefaultTreeNode> listaNueva = new ArrayList<DefaultTreeNode>();
						listaNueva.add(new DefaultTreeNode(tipoDoc));
						listaEstructuradaTree.add(new DefaultTreeNode(tipoDoc.getFamilia().getNombre(), listaNueva));
						agrego=true;
					}
					agrego = false;
				}
				this.treeModel = new DefaultTreeModel(new DefaultTreeNode("ROOT", listaEstructuradaTree));
			}
		
			familiaTipoTree.setTreeitemRenderer(new TreeitemRenderer() {
				
				
		public void render(Treeitem item, Object data, int arg1) throws Exception {

				DefaultTreeNode nodo = (DefaultTreeNode)data;
					if (nodo.getData() instanceof String){
						String fi = (String)nodo.getData();
						Treerow tr = new Treerow();
				        tr.setParent(item);
				        tr.setId(null);
				        tr.appendChild(new Treecell(fi));
					}
					if (nodo.getData() instanceof TipoDocumentoDTO){
						TipoDocumentoDTO repa = (TipoDocumentoDTO)nodo.getData();
						Treerow tr = new Treerow();
						tr.setParent(item);
						tr.setId(repa.getAcronimo());
						
						if (repa.getNombre().length() > CANT_MAX_CARACTERES_A_MOSTRAR_EN_NOMBRE) {
							String nombreAMostrar = repa.getNombre().trim().substring(0, 20).concat(PUNTOS_SUSPENSIVOS);
							Treecell treeCell = new Treecell(nombreAMostrar);
							treeCell.setTooltiptext(repa.getNombre());
							tr.appendChild(treeCell);
						} else {
							tr.appendChild(new Treecell(repa.getNombre()));
						}
						
						tr.appendChild(new Treecell(repa.getAcronimo()));
						
						if (repa.getDescripcion().length() > CANT_MAX_CARACTERES_A_MOSTRAR_EN_DESC) {
							String descripcionAMostrar = repa.getDescripcion().trim().substring(0, 22).concat(PUNTOS_SUSPENSIVOS);
							Treecell treeCell = new Treecell(descripcionAMostrar);
							treeCell.setTooltiptext(repa.getDescripcion());
							tr.appendChild(treeCell);
						} else {
							tr.appendChild(new Treecell(repa.getNombre()));
						}
						
						tr.appendChild(obtenerCaracteristicasTipoDocumento(repa, tr));
					}
				}
			});
			familiaTipoTree.setModel(this.treeModel);
			this.binder.loadComponent(familiaTipoTree);
		}
		
		
	private Treecell obtenerCaracteristicasTipoDocumento(TipoDocumentoDTO repa, Treerow tr) throws IOException {
		Treecell tc = new Treecell();

		Image imagenDocumentoEspecial = null;
		Image imagenDocumentoFirmaConToken = null;
		Image imagenDocumentoProduccion = null;
		Image imagenDocumentoFirmaConjunta = new Image();
		Image imagenDocumentoFirmaExterna = null;
		Image imagenDocumentoReservado = null;

		if (repa.getTipoProduccion().equals(ConstantesWeb.TIPO_PRODUCCION_LIBRE)) {
			imagenDocumentoProduccion = new Image();
			imagenDocumentoProduccion.setSrc(ConstantesWeb.IMG_TIENE_LIBRE);
			imagenDocumentoProduccion.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.libre"));
			tc.appendChild(imagenDocumentoProduccion);
		}  else if (repa.getTipoProduccion().equals(ConstantesWeb.TIPO_PRODUCCION_IMPORTADO)) {
			imagenDocumentoProduccion = new Image();
			imagenDocumentoProduccion.setSrc(ConstantesWeb.IMG_TIENE_IMPORTADO);
			imagenDocumentoProduccion.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.importado"));
			tc.appendChild(imagenDocumentoProduccion);
		} else if(repa.getTipoProduccion().equals(ConstantesWeb.TIPO_PRODUCCION_TEMPLATE)){
			imagenDocumentoProduccion = new Image();
			imagenDocumentoProduccion.setSrc(ConstantesWeb.IMG_TIENE_TEMPLATE);
			imagenDocumentoProduccion.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.template"));
			tc.appendChild(imagenDocumentoProduccion);
		}else if(repa.getTipoProduccion().equals(ConstantesWeb.TIPO_PRODUCCION_IMPORTADO_TEMPLATE)){
			imagenDocumentoProduccion = new Image();
			imagenDocumentoProduccion.setSrc(ConstantesWeb.IMG_TIENE_IMPORTADO_TEMPLATE);
			imagenDocumentoProduccion.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.importadoTemplate"));
		}
		
		if (repa.getEsEspecial().equals(Boolean.TRUE)) {
			imagenDocumentoEspecial = new Image();
			imagenDocumentoEspecial.setSrc(ConstantesWeb.IMG_ES_ESPECIAL);
			imagenDocumentoEspecial.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.especial"));
			tc.appendChild(imagenDocumentoEspecial);
		}

		if (repa.getEsFirmaExterna().equals(Boolean.TRUE)) {
			imagenDocumentoFirmaExterna = new Image();
			imagenDocumentoFirmaExterna.setSrc(ConstantesWeb.IMG_ES_FIRMA_EXTERNA);
			imagenDocumentoFirmaExterna.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.firmaExterna"));
			tc.appendChild(imagenDocumentoFirmaExterna);
		}

		if (repa.getEsConfidencial().equals(Boolean.TRUE)) {
			imagenDocumentoReservado = new Image();
			imagenDocumentoReservado.setSrc(ConstantesWeb.IMG_ES_CONFIDENCIAL);
			imagenDocumentoReservado.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.reservado"));
			tc.appendChild(imagenDocumentoReservado);
		}

		if (repa.getTieneToken().equals(Boolean.TRUE)) {
			imagenDocumentoFirmaConToken = new Image();
			imagenDocumentoFirmaConToken.setSrc(ConstantesWeb.IMG_TIENE_TOKEN);
			imagenDocumentoFirmaConToken.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.token"));
			tc.appendChild(imagenDocumentoFirmaConToken);
		}

		if (repa.getEsFirmaConjunta().equals(Boolean.TRUE)) {
			imagenDocumentoFirmaConjunta = new Image();
			imagenDocumentoFirmaConjunta.setSrc(ConstantesWeb.IMG_ES_FIRMA_CONJUNTA);
			imagenDocumentoFirmaConjunta.setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.firmaConjunta"));
			tc.appendChild(imagenDocumentoFirmaConjunta);
		}
		return tc;
	}

	
	public void onSelect$familiaTipoTree() throws InterruptedException{
		String codigoId = familiaTipoTree.getSelectedItem().getTreerow().getId();
		
		if(codigoId != null && !codigoId.trim().equals("")){
		  TipoDocumentoDTO SelecDocumArbol = buscarDocumento(codigoId);
			this.textoTipoDocumento.setText(codigoId);
			this.familiaEstructuraTree.setValue(codigoId);
			this.familiaEstructuraTree.close();
			Event event = new Event(Events.ON_NOTIFY, this.self.getParent(), SelecDocumArbol);
			Events.sendEvent(event);
			
			Event eventParent = new Event(Events.ON_NOTIFY, this.getPadreEchoEvent(), SelecDocumArbol);
		}
	}
	
	private TipoDocumentoDTO buscarDocumento(String acronimo) {
		TipoDocumentoDTO salida ;
		salida = this.tipoDocumentoService.obtenerTipoDocumento(acronimo);
		return salida;
	}



	public void onChanging$familiaEstructuraTree(InputEvent e){
		this.familiaEstructuraTree.setValue(e.getValue());
		this.textoTipoDocumento.setText(e.getValue());
		this.onChanging$textoTipoDocumento(e);
	}
	
	public void onChanging$textoTipoDocumento(InputEvent e) {
		limpiarErrores();
		this.cargarFamilias(e);
	}
	
	private void limpiarErrores() {
		
	}


	public void cargarFamilias(InputEvent e){
		String matchingText = e.getValue();
		if (!matchingText.equals("") && (matchingText.length() >= 2)) {
			this.listaFamiliasSeleccionadas.clear();
			if (this.listaFamiliasTotal != null) {
				matchingText = matchingText.toUpperCase();
				Iterator<TipoDocumentoDTO> iterator = listaFamiliasTotal.iterator();
				TipoDocumentoDTO TipoDocumento = null;
				while ((iterator.hasNext()) ) {
					TipoDocumento = iterator.next();
					if ((TipoDocumento != null) ) {
						if ((TipoDocumento.getAcronimo().contains(matchingText))) {
							this.listaFamiliasSeleccionadas.add(TipoDocumento);
						}
					}
				}
			}
			this.cargarReparticionesPorEstructura(this.listaFamiliasSeleccionadas);
			
			this.binder.loadComponent(familiaTipoTree);
		}
		else if(matchingText.equals("")){
			this.listaFamiliasSeleccionadas.clear() ;
			
			this.listaFamiliasSeleccionadas = new ArrayList<TipoDocumentoDTO>(this.listaFamiliasTotal);
			this.cargarReparticionesPorEstructura(this.listaFamiliasSeleccionadas);
			this.binder.loadComponent(familiaTipoTree);
		}
	}
	
	
	public void recargarTiposDeDocumentos(boolean todosLosEstados){
		if (todosLosEstados){
			this.listaFamiliasTotal = new ArrayList<TipoDocumentoDTO>(tipoDocumentoService.obtenerTiposDocumentoGedo());
		}else{
			this.listaFamiliasTotal = new ArrayList<TipoDocumentoDTO>(tipoDocumentoService.obtenerTiposDocumentoGedo("ALTA", true));
		}
		this.textoTipoDocumento.setText(null);
		this.listaFamiliasSeleccionadas.clear();
		this.listaFamiliasSeleccionadas = new ArrayList<TipoDocumentoDTO>(this.listaFamiliasTotal);
		this.binder = new AnnotateDataBinder(this.familiaTipoTree);
		cargarReparticionesPorEstructura(this.listaFamiliasSeleccionadas);
		this.binder.loadAll();
	}

	public Tree getFamiliaTipoTree() {
		return familiaTipoTree;
	}

	public void setFamiliaTipoTree(Tree familiaTipoTree) {
		this.familiaTipoTree = familiaTipoTree;
	}



	public TipoDocumentoService getTipoDocumentoService() {
		return tipoDocumentoService;
	}

	public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
		this.tipoDocumentoService = tipoDocumentoService;
	}



	public ArrayList<TipoDocumentoDTO> getListaFamiliasSeleccionadas() {
		return listaFamiliasSeleccionadas;
	}



	public void setListaFamiliasSeleccionadas(
			ArrayList<TipoDocumentoDTO> listaFamiliasSeleccionadas) {
		this.listaFamiliasSeleccionadas = listaFamiliasSeleccionadas;
	}



	public ArrayList<TipoDocumentoDTO> getListaFamiliasTotal() {
		return listaFamiliasTotal;
	}



	public void setListaFamiliasTotal(ArrayList<TipoDocumentoDTO> listaFamiliasTotal) {
		this.listaFamiliasTotal = listaFamiliasTotal;
	}



	public Textbox getTextoTipoDocumento() {
		return textoTipoDocumento;
	}



	public void setTextoTipoDocumento(Textbox textoTipoDocumento) {
		this.textoTipoDocumento = textoTipoDocumento;
	}
	
	public Component getPadreEchoEvent() {
		return padreEchoEvent;
	}

	public void setPadreEchoEvent(Component padreEchoEvent) {
		this.padreEchoEvent = padreEchoEvent;
	}

	public void onBlur$familiaEstructuraTree(){
		this.familiaEstructuraTree.setText(this.familiaEstructuraTree.getValue().toUpperCase());
		if(!this.familiaEstructuraTree.getValue().trim().equals("")){
			//TODO cambiar el meotodo x el original
//			TipoDocumento tipoDocumento = this.tipoDocumentoService.buscarTipoDocumentoByAcronimo(this.familiaEstructuraTree.getValue());
			TipoDocumentoDTO tipoDocumento = this.tipoDocumentoService.obtenerTipoDocumento(this.familiaEstructuraTree.getValue());
			if(tipoDocumento !=null){
				Executions.getCurrent().getDesktop().setAttribute("TipoDocumento", tipoDocumento);
				Event event = new Event(Events.ON_NOTIFY, this.self.getParent(), tipoDocumento);
				Events.sendEvent(event);
			}else{
				throw new WrongValueException(this.familiaEstructuraTree, Labels.getLabel("ee.general.tipoDocumentoInvalido"));
			}
		}else{
			Executions.getCurrent().getDesktop().removeAttribute("TipoDocumento");
		}
	}

	public boolean isDocImportado() {
		return docImportado;
	}

	public void setDocImportado(boolean docImportado) {
		this.docImportado = docImportado;
	}
	
}

final class InicioDocumentoFamiliaTipoDocumentoBandBoxComposerListener implements EventListener {
	private  InicioDocumentoFamiliaTipoDocumentoBandBoxComposer composer;

	public InicioDocumentoFamiliaTipoDocumentoBandBoxComposerListener(InicioDocumentoFamiliaTipoDocumentoBandBoxComposer comp) {
		this.composer = comp;
	}

	@SuppressWarnings("unchecked")
	public void onEvent(Event event) throws Exception {
	
		if (event.getName().equals(Events.ON_NOTIFY)){
			if (event.getData() != null) {
				Object data = event.getData();
				this.composer.recargarTiposDeDocumentos((Boolean) data) ;
			}
		}else if(event.getName().equals(composer.SET_COMPONENT_PARENT)) {
			this.composer.setPadreEchoEvent((Component) event.getData());
		}
	}
}
