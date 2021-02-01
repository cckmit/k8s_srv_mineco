package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TruncatedResults;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesBusquedaBandboxNumeroEspecial extends
		GenericForwardComposer implements TruncatedResults {

	private static final long serialVersionUID = 8851443000063616630L;
	@Autowired
	private Listbox reparticionesBusquedaDocumentoListboxNumeroEspecial;
	@Autowired
	private Textbox textoReparticionBusquedaDocumentoNumeroEspecial;
	@Autowired
	private Bandbox reparticionBusquedaDocumentoNumeroEspecial;
	
	@WireVariable(ConstantesServicios.REPARTICION_SERVICE)
	private ReparticionServ reparticionServ;
	
	@Autowired
	private AnnotateDataBinder binder;

	private List<ReparticionBean> listaReparticionDocumentoSeleccionadaNumeroEspecial;
	private ReparticionBean reparticionSeleccionadaDocumentoNumeroEspecial;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		this.binder = new AnnotateDataBinder(
				reparticionesBusquedaDocumentoListboxNumeroEspecial);

		binder.bindBean("reparticionSeleccionadaDocumentoNumeroEspecial",
				this.reparticionSeleccionadaDocumentoNumeroEspecial);
		binder.loadAll();
		
		this.listaReparticionDocumentoSeleccionadaNumeroEspecial = new ArrayList<ReparticionBean>();

		binder.bindBean(
				"listaReparticionSADEDocumentoSeleccionadaNumeroEspecial",
				this.listaReparticionDocumentoSeleccionadaNumeroEspecial);
	}

	public void onChanging$textoReparticionBusquedaDocumentoNumeroEspecial(
			InputEvent e) {
		String matchingText = e.getValue();
		if (StringUtils.isNotEmpty(matchingText)
				&& (matchingText.length() >= 3)) {
			List<ReparticionBean> listaReparticionesDocumentoCompleta = this.reparticionServ.buscarTodasLasReparticiones(); 
			this.listaReparticionDocumentoSeleccionadaNumeroEspecial
					.clear();
			if (listaReparticionesDocumentoCompleta != null) {
				matchingText = matchingText.toUpperCase();
				Iterator<ReparticionBean> iterator = listaReparticionesDocumentoCompleta.iterator();
				ReparticionBean reparticion = null;
				while ((iterator.hasNext())
						&& (this.listaReparticionDocumentoSeleccionadaNumeroEspecial
								.size() <= TruncatedResults.MAX_REPARTICION_RESULTS)) {
					reparticion = iterator.next();
					if ((reparticion != null)
							&& (StringUtils.isNotEmpty(reparticion.getNombre()) && (StringUtils
									.isNotEmpty(reparticion.getCodigo())))) {
						if ((reparticion.getNombre().contains(matchingText))
								|| (reparticion.getCodigo()
										.contains(matchingText))) {
							listaReparticionDocumentoSeleccionadaNumeroEspecial
									.add(reparticion);
						}
					}
				}
			}
			this.binder
					.loadComponent(reparticionesBusquedaDocumentoListboxNumeroEspecial);

		}
	}

	public void onBlur$reparticionBusquedaDocumentoNumeroEspecial() {
		String value = this.reparticionBusquedaDocumentoNumeroEspecial
				.getValue();
		if (StringUtils.isNotEmpty(value)) {
			if (!(this.reparticionServ.validarCodigoReparticion(value
					.trim()))) {
				this.reparticionBusquedaDocumentoNumeroEspecial.focus();
				throw new WrongValueException(
						this.reparticionBusquedaDocumentoNumeroEspecial, Labels
								.getLabel("ee.general.reparticionInvalida"));
			}
			this.reparticionBusquedaDocumentoNumeroEspecial.setValue(value
					.toUpperCase());
		}
	}

	public void onSelect$reparticionesBusquedaDocumentoListboxNumeroEspecial() {
		ReparticionBean rsb = this.listaReparticionDocumentoSeleccionadaNumeroEspecial
				.get(reparticionesBusquedaDocumentoListboxNumeroEspecial
						.getSelectedIndex());
		this.reparticionBusquedaDocumentoNumeroEspecial.setValue(rsb
				.getCodigo());
		this.textoReparticionBusquedaDocumentoNumeroEspecial.setValue(null);
		this.listaReparticionDocumentoSeleccionadaNumeroEspecial.clear();
		this.binder
				.loadComponent(this.reparticionBusquedaDocumentoNumeroEspecial);
		this.textoReparticionBusquedaDocumentoNumeroEspecial.setValue(null);
		reparticionBusquedaDocumentoNumeroEspecial.close();
	}
	
}
