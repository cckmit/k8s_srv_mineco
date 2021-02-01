package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TruncatedResults;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesBusquedaDocumentoBandboxComposer extends GenericForwardComposer implements TruncatedResults {

	private static final long serialVersionUID = -7399418888343842438L;
	
	@Autowired
	private Listbox reparticionesBusquedaDocumentoListbox;
	@Autowired
	private Textbox textoReparticionBusquedaDocumento;
	@Autowired
	private Bandbox reparticionBusquedaDocumento;
	 
	@Autowired
	private AnnotateDataBinder binder;
	private List<ReparticionBean> listaReparticionSADEDocumentoSeleccionada;
	private List<ReparticionBean> listaReparticionDocumentoSeleccionada;
	private ReparticionBean reparticionSeleccionadaDocumento;
	@Autowired
	
	@WireVariable(ConstantesServicios.REPARTICION_SERVICE)
	private ReparticionServ reparticionServ;


	@SuppressWarnings({ "unchecked", "deprecation" })
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		this.binder = new AnnotateDataBinder(reparticionesBusquedaDocumentoListbox);
		
		comp.addEventListener(Events.ON_NOTIFY, new FindReparticionesBusquedaDocumentoListener());
		comp.addEventListener(Events.ON_CHANGING, new FindReparticionesBusquedaDocumentoListener());

//		this.listaReparticionSADEDocumentoSeleccionada = new ArrayList<ReparticionSADEBean>();
//		
//		binder.bindBean("listaReparticionSADEDocumentoSeleccionada",
//				this.listaReparticionSADEDocumentoSeleccionada);
		
		binder.bindBean("reparticionSeleccionadaDocumento",
						this.reparticionSeleccionadaDocumento);

		binder.loadAll();
		
		this.listaReparticionDocumentoSeleccionada = new ArrayList<ReparticionBean>();
		
		binder.bindBean("listaReparticionSADEDocumentoSeleccionada",
				this.listaReparticionDocumentoSeleccionada);
	}

	public void onChanging$textoReparticionBusquedaDocumento(InputEvent e) {
		String matchingText = e.getValue();
		if (StringUtils.isNotEmpty(matchingText)
				&& (matchingText.length() >= 3)) {
			List<ReparticionBean> listaReparticionesDocumentoCompleta = this.reparticionServ.buscarTodasLasReparticiones();
			this.listaReparticionDocumentoSeleccionada.clear();
			if (listaReparticionesDocumentoCompleta != null) {
				matchingText = matchingText.toUpperCase();
				Iterator<ReparticionBean> iterator = listaReparticionesDocumentoCompleta
						.iterator();
				ReparticionBean reparticion = null;
				while ((iterator.hasNext())
						&& (this.listaReparticionDocumentoSeleccionada.size() <= ConstantesWeb.MAX_REPARTICION_RESULTS)) {
					reparticion = iterator.next();
					if ((reparticion != null)
							&& (StringUtils.isNotEmpty(reparticion.getNombre()) && (StringUtils
									.isNotEmpty(reparticion.getCodigo())))) {
						
						String normalizadoValorA = Normalizer.normalize(matchingText, 
								Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						String normalizadoValorB = Normalizer.normalize(reparticion.getNombre(), 
								Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						
						if (StringUtils.containsIgnoreCase(normalizadoValorB,normalizadoValorA)
								|| (reparticion.getCodigo().contains(matchingText))) {
							listaReparticionDocumentoSeleccionada.add(reparticion);
						}
					}
				}
			}
			this.binder.loadComponent(reparticionesBusquedaDocumentoListbox);
		}
	}
	

	public void onBlur$reparticionBusquedaDocumento() {
		String value = this.reparticionBusquedaDocumento.getValue();
		if (StringUtils.isNotEmpty(value)) {
			if (!(this.reparticionServ.validarCodigoReparticion(value.trim()))) {
				this.reparticionBusquedaDocumento.focus();
				throw new WrongValueException(this.reparticionBusquedaDocumento, Labels
						.getLabel("ee.general.reparticionInvalida"));
			}
			this.reparticionBusquedaDocumento.setValue(value.toUpperCase());
		}
	}
	

	public void onSelect$reparticionesBusquedaDocumentoListbox() {
		
		if(reparticionesBusquedaDocumentoListbox.getSelectedIndex() < 0) {
			return;
		}
		
		ReparticionBean rsb = this.listaReparticionDocumentoSeleccionada
				.get(reparticionesBusquedaDocumentoListbox.getSelectedIndex());
		
		reparticionesBusquedaDocumentoListbox.clearSelection();
		
		
		this.reparticionBusquedaDocumento.setValue(rsb.getCodigo());
		this.textoReparticionBusquedaDocumento.setValue(null);
		 this.listaReparticionDocumentoSeleccionada.clear();
		this.binder.loadComponent(this.reparticionBusquedaDocumento);
		this.textoReparticionBusquedaDocumento.setValue(null);
		reparticionBusquedaDocumento.close();
	}
 

	public void setListaReparticionSADEDocumentoSeleccionada(
			List<ReparticionBean> listaReparticionSADEDocumentoSeleccionada) {
		this.listaReparticionSADEDocumentoSeleccionada = listaReparticionSADEDocumentoSeleccionada;
	}

	public List<ReparticionBean> getListaReparticionSADEDocumentoSeleccionada() {
		return listaReparticionSADEDocumentoSeleccionada;
	}
	
	public void onOpen$reparticionBusquedaDocumento() {
		
		//reparticionesBusquedaDocumentoListbox.setModel(new ListModelList<>(listaReparticionDocumentoSeleccionada));		
	}
	
	@SuppressWarnings("rawtypes")
	public class FindReparticionesBusquedaDocumentoListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(Events.ON_NOTIFY)) {
				onOpen$reparticionBusquedaDocumento();
			}else if(event.getName().equals(Events.ON_CHANGING)) {
				InputEvent input = (InputEvent) event.getData();
				textoReparticionBusquedaDocumento.setValue(input.getValue());
				onChanging$textoReparticionBusquedaDocumento(input);
			}
		}
		
	}

}