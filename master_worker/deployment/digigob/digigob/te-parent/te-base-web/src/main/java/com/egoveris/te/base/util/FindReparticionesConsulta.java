package com.egoveris.te.base.util;


import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.te.base.composer.AbstracFindReparticionesBusquedaBandboxComposer;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesConsulta extends AbstracFindReparticionesBusquedaBandboxComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7420446970613798790L;
	@Autowired
	private Listbox reparticionesBusquedaUsuarioListbox;
	@Autowired
	private Textbox textoReparticionBusquedaUsuario;
	@Autowired
	private Bandbox reparticionBusquedaUsuario;

	
public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		comp.addEventListener(Events.ON_CHANGING, new FindReparticionesConsultaListener());
		
		this.binder = new AnnotateDataBinder(reparticionesBusquedaUsuarioListbox);
		this.listaReparticionSeleccionada = new ArrayList<ReparticionBean>();
		binder.bindBean("listaReparticionSADESeleccionada",
				this.listaReparticionSADESeleccionada);
		binder.bindBean("reparticionSeleccionada",
						this.reparticionSeleccionada);
		binder.loadAll();
		
		//this.reparticionServ = (ReparticionBaseServ) SpringUtil.getBean(ConstantesServicios.REPARTICION_SERVICE);
		
		this.listaReparticionSeleccionada = new ArrayList<ReparticionBean>();
		binder.bindBean("listaReparticionSADESeleccionada",
				this.listaReparticionSeleccionada);
		
	}

	public void onChanging$textoReparticionBusquedaUsuario(InputEvent e) {
		String matchingText = e.getValue();
		if (StringUtils.isNotEmpty(matchingText)
				&& (matchingText.length() >= 3)) {
			List<ReparticionBean> listaReparticionesCompleta = this.reparticionServ.buscarTodasLasReparticiones();
			this.listaReparticionSeleccionada.clear();
			if (listaReparticionesCompleta != null) {
				matchingText = matchingText.toUpperCase();
				Iterator<ReparticionBean> iterator = listaReparticionesCompleta
						.iterator();
				ReparticionBean reparticion;
				while ((iterator.hasNext())
						&& (this.listaReparticionSeleccionada.size() <= TruncatedResults.MAX_REPARTICION_RESULTS)) {
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
							listaReparticionSeleccionada.add(reparticion);
						}
					}
				}
			}
			this.binder.loadComponent(reparticionesBusquedaUsuarioListbox);
		}
	}

	public void onBlur$reparticionBusquedaUsuario() {
		String value = this.reparticionBusquedaUsuario.getValue();
		if (StringUtils.isNotEmpty(value)) {
			if (!(this.reparticionServ.validarCodigoReparticion(value.trim()))) {
				this.reparticionBusquedaUsuario.focus();
				throw new WrongValueException(this.reparticionBusquedaUsuario, Labels
						.getLabel("ee.general.reparticionInvalida"));
			}
			this.reparticionBusquedaUsuario.setValue(value.toUpperCase());
		}
	}
	
	

	public void onSelect$reparticionesBusquedaUsuarioListbox() {
		ReparticionBean rsb = this.listaReparticionSeleccionada
				.get(reparticionesBusquedaUsuarioListbox.getSelectedIndex());
		

		if (rsb != null) {
			Executions.getCurrent().getDesktop().setAttribute("reparticion", rsb);
			this.reparticionBusquedaUsuario.setValue(rsb.getCodigo());
			this.textoReparticionBusquedaUsuario.setValue(null);
			this.listaReparticionSeleccionada.clear();
			this.binder.loadComponent(this.reparticionBusquedaUsuario);
			this.textoReparticionBusquedaUsuario.setValue(null);
			reparticionBusquedaUsuario.close();
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public class FindReparticionesConsultaListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(Events.ON_CHANGING)) {
				InputEvent input = (InputEvent) event.getData();
				textoReparticionBusquedaUsuario.setValue(input.getValue());
				onChanging$textoReparticionBusquedaUsuario(input);
			}
		}
		
	}


}



