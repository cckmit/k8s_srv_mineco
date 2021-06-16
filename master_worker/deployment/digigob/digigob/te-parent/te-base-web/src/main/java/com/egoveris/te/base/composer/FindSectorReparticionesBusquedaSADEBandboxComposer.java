package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;



@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindSectorReparticionesBusquedaSADEBandboxComposer extends
		GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825309019932584527L;
	
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	private ProcessEngine processEngine;
	@Autowired
	private Bandbox sectorReparticionBusquedaSADE;
	@Autowired
	private Textbox textoSectorReparticionBusquedaSADE;
	@Autowired
	private Listbox sectoresReparticionesBusquedaSADEListbox;
	@Autowired
	private Bandbox sectorBusquedaSADE;
 
	@Autowired
	private AnnotateDataBinder binder;
	private List<ReparticionBean> listaSectorReparticionSADESeleccionada;
	private List<ReparticionBean> listaSectorReparticionSeleccionada;
	private ReparticionBean sectorReparticionSeleccionada;
	
	@WireVariable(ConstantesServicios.SECTOR_INTERNO_SERVICE)
	private SectorInternoServ sectorInternoServ;

	@WireVariable(ConstantesServicios.REPARTICION_SERVICE)
	private ReparticionServ reparticionServ;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		comp.addEventListener(Events.ON_CHANGING, new FindSectorReparticionesBusquedaSADEBandboxListener());

		this.binder = new AnnotateDataBinder(
				sectoresReparticionesBusquedaSADEListbox);
		
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		binder.bindBean("sectorReparticionSeleccionada",
				this.sectorReparticionSeleccionada);

		binder.loadAll();
		
		this.listaSectorReparticionSeleccionada = new ArrayList<ReparticionBean>();
		binder.bindBean("listaSectorReparticionSADESeleccionada",
				this.listaSectorReparticionSeleccionada);

	}

	public void onChanging$textoSectorReparticionBusquedaSADE(InputEvent e) {
		String matchingText = e.getValue();
		if (StringUtils.isNotEmpty(matchingText)
				&& (matchingText.length() >= 3)) {
			List<ReparticionBean> listaReparticionesCompleta = this.reparticionServ
					.buscarReparticionesVigentesActivas();
			this.listaSectorReparticionSeleccionada.clear();
			if (listaReparticionesCompleta != null) {
				matchingText = matchingText.toUpperCase();
				Iterator<ReparticionBean> iterator = listaReparticionesCompleta
						.iterator();
				ReparticionBean reparticion = null;
				while ((iterator.hasNext())
						&& (this.listaSectorReparticionSeleccionada.size() <= ConstantesWeb.MAX_REPARTICION_RESULTS)) {
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
							listaSectorReparticionSeleccionada.add(reparticion);
						}
					}
				}
			}
			this.binder.loadComponent(sectoresReparticionesBusquedaSADEListbox);
		}
	}

	public void onBlur$sectorReparticionBusquedaSADE() {
		String value = this.sectorReparticionBusquedaSADE.getValue();
		if (StringUtils.isNotEmpty(value)) {
			this.sectorReparticionBusquedaSADE.setValue(value.toUpperCase());

			ReparticionBean rsb = this.reparticionServ.buscarReparticionPorCodigo(value);
			if(rsb == null || rsb.getVigenciaHasta() == null || rsb.getVigenciaHasta().before(new Date()) 
			|| rsb.getVigenciaDesde().after(new Date())){
				this.sectorReparticionBusquedaSADE.focus();
				throw new WrongValueException(
						this.sectorReparticionBusquedaSADE,
						Labels.getLabel("ee.general.reparticionInvalida"));
			}
				this.sectorBusquedaSADE.setDisabled(false);
				this.sectorBusquedaSADE.setFocus(true);
			Executions.getCurrent().getDesktop()
					.setAttribute("reparticion", rsb);
		}
	}

	public void onSelect$sectoresReparticionesBusquedaSADEListbox() {
		ReparticionBean rsb = this.listaSectorReparticionSeleccionada
				.get(sectoresReparticionesBusquedaSADEListbox
						.getSelectedIndex());
		this.sectorReparticionBusquedaSADE.setValue(rsb.getCodigo());
		this.textoSectorReparticionBusquedaSADE.setValue(null);
		this.listaSectorReparticionSeleccionada.clear();
		this.binder.loadComponent(this.sectorReparticionBusquedaSADE);
		this.textoSectorReparticionBusquedaSADE.setValue(null);
		sectorReparticionBusquedaSADE.close();

		this.sectorBusquedaSADE.setDisabled(false);

		Executions.getCurrent().getDesktop().setAttribute("reparticion", rsb);
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	 

	public void setListaSectorReparticionSADESeleccionada(
			List<ReparticionBean> listaSectorReparticionSADESeleccionada) {
		this.listaSectorReparticionSADESeleccionada = listaSectorReparticionSADESeleccionada;
	}

	public List<ReparticionBean> getListaSectorReparticionSADESeleccionada() {
		return listaSectorReparticionSADESeleccionada;
	}

	public void setSectorInternoServ(SectorInternoServ sectorInternoServ) {
		this.sectorInternoServ = sectorInternoServ;
	}

	public SectorInternoServ getSectorInternoServ() {
		return sectorInternoServ;
	}
	
	@SuppressWarnings("rawtypes")
	public class FindSectorReparticionesBusquedaSADEBandboxListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(Events.ON_CHANGING)) {
				InputEvent input = (InputEvent) event.getData();
				textoSectorReparticionBusquedaSADE.setValue(input.getValue());
				onChanging$textoSectorReparticionBusquedaSADE(input);
			}
		}
		
	}

}
