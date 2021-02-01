package com.egoveris.te.base.composer;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.te.base.composer.genrico.BandBoxUsuarioComposer;
import com.egoveris.te.base.rendered.OrgaismosRenderer;
import com.egoveris.te.base.util.ConstantesServicios;


@SuppressWarnings({ "rawtypes", "deprecation" })
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BandboxOrganismoComposer extends GenericForwardComposer {

	/**	
	 * 
	 */
	private static final long serialVersionUID = 2825309019932584527L;
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(BandBoxUsuarioComposer.class);
	
	public static final String ON_SELECT_ORGANISMO = "onElegirOrganismo";
	
	public static final String COMPONENTE_PADRE="padre";
	
	public static final String DISABLED_BANDBOX = "disabledBandboxOrganismo";
	
	public static final String EVENT_DISABLED = "onDisabledBandboxOrganismo";
	
	public static final String EVENT_CLEAN = "onCleanBandboxOrganismo";
	
	public static final String EVENT_VALIDAR = "onValidarBandboxOrganismo";
	
	private AnnotateDataBinder binder;
	private Listbox listboxOrganismos;
	private Listfooter totalOrganismos;
	private Bandbox bandboxOrganismo;
	

	private ReparticionServ reparticionServ;

	
	private List<ReparticionBean> listaTodosLosOrganismos;
	
	private List<ReparticionBean> listaOrganismosSeleccionados;
	
	private ReparticionBean organismoSeleccionado;
	
	private Component componentePadre;
	
	@SuppressWarnings({ "unchecked" })
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		comp.addEventListener(EVENT_DISABLED, new BandboxOrganismoListener());
		comp.addEventListener(EVENT_CLEAN, new BandboxOrganismoListener());
		
		listboxOrganismos.setItemRenderer(new OrgaismosRenderer());
		
		
		configurarBandbox();
		inicializarListasDeOrganismos();
		
		this.binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		
	}

	
	private void configurarBandbox() throws Exception {
		listaOrganismosSeleccionados = new ArrayList<ReparticionBean>();
		componentePadre = (Component) Executions.getCurrent().getAttribute(COMPONENTE_PADRE);
		Boolean disabledBandbox = (Boolean) Executions.getCurrent().getAttribute(DISABLED_BANDBOX);
		if(componentePadre==null) {
			throw new Exception("El componente padre es una configuracion requerida para el bandbox de organismo");
		}
		bandboxOrganismo.setDisabled(disabledBandbox==null ? Boolean.FALSE : disabledBandbox);
		
	}

	private void inicializarListasDeOrganismos() throws SecurityNegocioException {
//		listaTodosLosOrganismos = reparticionServ.buscarReparticionesVigentesActivas();
		reparticionServ = (ReparticionServ) SpringUtil.getBean(ConstantesServicios.REPARTICION_SERVICE);
		listaTodosLosOrganismos = reparticionServ.buscarReparticionesVigentesActivas();
		listaOrganismosSeleccionados = new ArrayList<>();
	}
	
	public void onChanging$bandboxOrganismo(final InputEvent e) {
		if(!bandboxOrganismo.isOpen()){
			bandboxOrganismo.open();
		}
		
		this.cargarOrganismos(e, this.listaTodosLosOrganismos, listboxOrganismos, listaOrganismosSeleccionados,
				totalOrganismos);
	}
	
	private void cargarOrganismos(final InputEvent e, final List<ReparticionBean> listaTotalOrganismos,
			final Listbox listbox, final List<ReparticionBean> listaReducida, final Listfooter totalOrganismos) {
		String matchingText = e.getValue();
		if (matchingText.trim().length() > 2 && !"*".equals(matchingText.trim())) {
			listaReducida.clear();
			if (listaTotalOrganismos != null) {
				matchingText = matchingText.toUpperCase();
				for (final ReparticionBean organismoReducido : listaTotalOrganismos) {
					if ((organismoReducido != null)
							&& (StringUtils.isNotEmpty(organismoReducido.getNombre()) && (StringUtils.isNotEmpty(organismoReducido.getCodigo())))) {
						if (
								(organismoReducido != null)
								&& (StringUtils.isNotEmpty(organismoReducido.getNombre()) && (StringUtils
										.isNotEmpty(organismoReducido.getCodigo())))
								) {
							
							String normalizadoValorA = Normalizer.normalize(matchingText, 
									Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
							String normalizadoValorB = Normalizer.normalize(organismoReducido.getNombre(), 
									Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
							
							if (StringUtils.containsIgnoreCase(normalizadoValorB,normalizadoValorA)
									|| (organismoReducido.getCodigo().contains(matchingText))) {
								listaReducida.add(organismoReducido);
							}
							
							
						}
					}
				}
			}
		} else if ("*".equals(matchingText.trim())) {
			listaReducida.addAll(listaTotalOrganismos);
		} else if (matchingText.trim().length() < 2) {
			listaReducida.clear();
		}
		listbox.setActivePage(0);
		totalOrganismos.setLabel(
				Labels.getLabel("te.bandbox.label.totalOrganismos") + ": " + listaReducida.size());
		this.binder.loadComponent(listbox);

	}
	
	public class BandboxOrganismoListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			
				if(event.getName().equals(EVENT_DISABLED)) {
					bandboxOrganismo.setDisabled((boolean) event.getData());
					binder.loadComponent(bandboxOrganismo);
				}else if(event.getName().equals(EVENT_CLEAN)) {
					bandboxOrganismo.setValue(StringUtils.EMPTY);
					listboxOrganismos.clearSelection();
					listaOrganismosSeleccionados.clear();
				}else if(event.getName().equals(EVENT_VALIDAR)) {
					String mensajeError = (String) event.getData();
					throw new WrongValueException(bandboxOrganismo, mensajeError);
				}
		}
		
	}

	public void onBlur$bandboxOrganismo() {
		if(organismoSeleccionado!=null) {
			bandboxOrganismo.setValue(organismoSeleccionado.getNombre());
		}
	}
	
	public void onElegirOrganismo(ForwardEvent event) {
		this.organismoSeleccionado = (ReparticionBean) event.getData();
	
		bandboxOrganismo.setValue(organismoSeleccionado.getNombre());
		Events.sendEvent(ON_SELECT_ORGANISMO, componentePadre, organismoSeleccionado);
		this.binder.loadComponent(bandboxOrganismo);
		bandboxOrganismo.close();
	}
	
	public final AnnotateDataBinder getBinder() {
		return binder;
	}


	public final void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}


	public final Listfooter getTotalOrganismos() {
		return totalOrganismos;
	}


	public final void setTotalOrganismos(Listfooter totalOrganismos) {
		this.totalOrganismos = totalOrganismos;
	}


	public final List<ReparticionBean> getListaTodosLosOrganismos() {
		return listaTodosLosOrganismos;
	}


	public final void setListaTodosLosOrganismos(List<ReparticionBean> listaTodosLosOrganismos) {
		this.listaTodosLosOrganismos = listaTodosLosOrganismos;
	}


	public final List<ReparticionBean> getListaOrganismosSeleccionados() {
		return listaOrganismosSeleccionados;
	}


	public final void setListaOrganismosSeleccionados(List<ReparticionBean> listaOrganismosSeleccionados) {
		this.listaOrganismosSeleccionados = listaOrganismosSeleccionados;
	}


	public final ReparticionBean getOrganismoSeleccionado() {
		return organismoSeleccionado;
	}


	public final void setOrganismoSeleccionado(ReparticionBean organismoSeleccionado) {
		this.organismoSeleccionado = organismoSeleccionado;
	}



}
