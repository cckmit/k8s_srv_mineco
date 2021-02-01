package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.te.base.util.ConstantesWeb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesBusquedaSADEBandboxComposer extends AbstracFindReparticionesBusquedaBandboxComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7420446970613798790L;
	@Autowired
	private Listbox reparticionesBusquedaSADEListbox;
	@Autowired
	private Textbox textoReparticionBusquedaSADE;
	@Autowired
	private Bandbox reparticionBusquedaSADE;
	

	
public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		this.binder = new AnnotateDataBinder(reparticionesBusquedaSADEListbox);
		this.listaReparticionSeleccionada = new ArrayList<ReparticionBean>();
		binder.bindBean("listaReparticionSADESeleccionada",
				this.listaReparticionSADESeleccionada);
		binder.bindBean("reparticionSeleccionada",
						this.reparticionSeleccionada);
		binder.loadAll();
		
		this.listaReparticionSeleccionada = new ArrayList<ReparticionBean>();
		binder.bindBean("listaReparticionSADESeleccionada",
				this.listaReparticionSeleccionada);
	}

	public void onChanging$textoReparticionBusquedaSADE(InputEvent e) {
		String matchingText = e.getValue();
		if (StringUtils.isNotEmpty(matchingText)
				&& (matchingText.length() >= 3)) {
			List<ReparticionBean> listaReparticionesCompleta = this.reparticionServ
					.buscarReparticionesVigentesActivas();
			this.listaReparticionSeleccionada.clear();
			if (listaReparticionesCompleta != null) {
				matchingText = matchingText.toUpperCase();
				Iterator<ReparticionBean> iterator = listaReparticionesCompleta
						.iterator();
				ReparticionBean reparticion = null;
				while ((iterator.hasNext())
						&& (this.listaReparticionSeleccionada.size() <= ConstantesWeb.MAX_REPARTICION_RESULTS)) {
					reparticion = iterator.next();
					if ((reparticion != null)
							&& (StringUtils.isNotEmpty(reparticion.getNombre()) && (StringUtils
									.isNotEmpty(reparticion.getCodigo())))) {
						if ((reparticion.getNombre().contains(matchingText))
								|| (reparticion.getCodigo()
										.contains(matchingText))) {
							listaReparticionSeleccionada.add(reparticion);
						}
					}
				}
			}
			this.binder.loadComponent(reparticionesBusquedaSADEListbox);
		}
	}

	public void onBlur$reparticionBusquedaSADE() {
		String value = this.reparticionBusquedaSADE.getValue();
		if (StringUtils.isNotEmpty(value)) {
			if (!(this.reparticionServ.validarCodigoReparticion(value.trim()))) {
				this.reparticionBusquedaSADE.focus();
				throw new WrongValueException(this.reparticionBusquedaSADE, Labels
						.getLabel("ee.general.reparticionInvalida"));
			}
			this.reparticionBusquedaSADE.setValue(value.toUpperCase());
		}
	}

	public ReparticionBean getReparticion(String codigoReparticion) {
		List<ReparticionBean> listaReparticionesCompleta = this.reparticionServ.buscarReparticionesVigentesActivas();
		
		for (ReparticionBean reparticion: listaReparticionesCompleta) 
		{
			if ((reparticion != null)
					&& (StringUtils.isNotEmpty(reparticion.getNombre()) && (StringUtils
							.isNotEmpty(reparticion.getCodigo())))) {
				if ((reparticion.getNombre().equalsIgnoreCase(codigoReparticion))
						|| (reparticion.getCodigo().equalsIgnoreCase(codigoReparticion))) {
					//listaReparticionSeleccionada.add(reparticion);
					return reparticion;
				}
			}
		}
		
		return null;
	}
	
	public void onChange$reparticionBusquedaSADE() {
		String value = this.reparticionBusquedaSADE.getValue();
		if (StringUtils.isNotEmpty(value)) {
			if (!(this.reparticionServ.validarCodigoReparticion(value.trim()))) {
				this.reparticionBusquedaSADE.focus();
				throw new WrongValueException(this.reparticionBusquedaSADE, Labels
						.getLabel("ee.general.reparticionInvalida"));
			}
			this.reparticionBusquedaSADE.setValue(value.toUpperCase());

			ReparticionBean rsb = getReparticion(value.toUpperCase());
			Executions.getCurrent().getDesktop().removeAttribute("reparticion");
			if (rsb!=null) {
				Executions.getCurrent().getDesktop().setAttribute("reparticion",rsb);
			}
				
		}
	}
	
	public void onSelect$reparticionesBusquedaSADEListbox() {
		ReparticionBean rsb = this.listaReparticionSeleccionada
				.get(reparticionesBusquedaSADEListbox.getSelectedIndex());
		
		if (rsb != null) {
			Executions.getCurrent().getDesktop().setAttribute("reparticion", rsb);
			this.reparticionBusquedaSADE.setValue(rsb.getCodigo());
			this.textoReparticionBusquedaSADE.setValue(null);
			this.listaReparticionSeleccionada.clear();
			this.binder.loadComponent(this.reparticionBusquedaSADE);
			this.textoReparticionBusquedaSADE.setValue(null);
			reparticionBusquedaSADE.close();
		}
	}

}