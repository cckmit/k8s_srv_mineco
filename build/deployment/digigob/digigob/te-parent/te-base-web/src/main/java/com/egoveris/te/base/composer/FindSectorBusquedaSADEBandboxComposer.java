package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TruncatedResults;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
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
public class FindSectorBusquedaSADEBandboxComposer extends
		GenericForwardComposer implements TruncatedResults {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7391248858610154498L;
	
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	private ProcessEngine processEngine;
	private Bandbox sectorBusquedaSADE;
	private Textbox textoSectorBusquedaSADE;
	private Listbox sectoresBusquedaSADEListbox;
	private AnnotateDataBinder binder;
	private List<SectorInternoBean> listaSectorSADESeleccionado;
	private SectorInternoBean sectorSeleccionado;
	
	@WireVariable(ConstantesServicios.SECTOR_INTERNO_SERVICE)
	private SectorInternoServ sectorInternoServ;

	// TRAER DATOS SLECCIONADOS DE LA REPARTICION
	private ReparticionBean sectorReparticionSeleccionada;
	
	public static final String ONSEND_REPARTICION_BEAN = "onSendReparticionBean";

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		comp.addEventListener(Events.ON_CHANGING, new FindSectorBusquedaSADEBandboxListener());
		comp.addEventListener(ONSEND_REPARTICION_BEAN, new FindSectorBusquedaSADEBandboxListener());
		
		this.binder = new AnnotateDataBinder(sectoresBusquedaSADEListbox);
		this.listaSectorSADESeleccionado = new ArrayList<SectorInternoBean>();
		binder.bindBean("listaSectorSADESeleccionado",
				this.listaSectorSADESeleccionado);
		binder.bindBean("sectorSeleccionado", this.sectorSeleccionado);

		binder.loadAll();
	}

	public void onFocus$sectorBusquedaSADE() {
		
		if((ReparticionBean) Executions.getCurrent().getDesktop().getAttribute("reparticion") != null) {
			this.sectorReparticionSeleccionada = (ReparticionBean) Executions.getCurrent().getDesktop().getAttribute("reparticion");
		}
		
	}

	public void onChanging$textoSectorBusquedaSADE(InputEvent e) {
		String matchingText = e.getValue();
		if (this.sectorReparticionSeleccionada!=null && StringUtils.isNotEmpty(matchingText)
				&& (matchingText.length() >= 1)) {
			List<SectorInternoBean> listaSectoresCompleta = sectorInternoServ
					.buscarSectoresPorReparticion(this.sectorReparticionSeleccionada
							.getId());
			this.listaSectorSADESeleccionado.clear();
			if (listaSectoresCompleta != null) {
				matchingText = matchingText.toUpperCase();
				Iterator<SectorInternoBean> iterator = listaSectoresCompleta
						.iterator();
				SectorInternoBean sector = null;
				while ((iterator.hasNext())
						&& (this.listaSectorSADESeleccionado.size() <= ConstantesWeb.MAX_REPARTICION_RESULTS)) {
					sector = iterator.next();
					if ((sector != null)
							&& (StringUtils.isNotEmpty(sector.getNombre()) && (StringUtils
									.isNotEmpty(sector.getCodigo())))) {
						
						String normalizadoValorA = Normalizer.normalize(matchingText, 
								Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						String normalizadoValorB = Normalizer.normalize(sector.getNombre(), 
								Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
						
						if (StringUtils.containsIgnoreCase(normalizadoValorB,normalizadoValorA)
								|| (sector.getCodigo().contains(matchingText))) {
							listaSectorSADESeleccionado.add(sector);
						}
					}
				}
			}
			this.binder.loadComponent(sectoresBusquedaSADEListbox);
		}
	}

	public void onBlur$sectorBusquedaSADE() {
		String value = this.sectorBusquedaSADE.getValue();
		if (StringUtils.isNotEmpty(value)) {
			if (!(sectorInternoServ.validarCodigoSector(value.trim(),
					this.sectorReparticionSeleccionada.getId()))) {
				this.sectorBusquedaSADE.focus();
				throw new WrongValueException(this.sectorBusquedaSADE,
						Labels.getLabel("ee.general.sectorInvalido"));
			}
			this.sectorBusquedaSADE.setValue(value.toUpperCase());
		}
	}

	public void onSelect$sectoresBusquedaSADEListbox() {
		SectorInternoBean sisb = this.listaSectorSADESeleccionado
				.get(sectoresBusquedaSADEListbox.getSelectedIndex());
		this.sectorBusquedaSADE.setValue(sisb.getCodigo());
		this.textoSectorBusquedaSADE.setValue(null);
		this.listaSectorSADESeleccionado.clear();
		this.binder.loadComponent(this.sectorBusquedaSADE);
		this.textoSectorBusquedaSADE.setValue(null);
		sectorBusquedaSADE.close();
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	
	@SuppressWarnings("rawtypes")
	public class FindSectorBusquedaSADEBandboxListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(Events.ON_CHANGING)) {
				InputEvent input = (InputEvent) event.getData();
				textoSectorBusquedaSADE.setValue(input.getValue());
				onChanging$textoSectorBusquedaSADE(input);
			}else if(event.getName().equals(ONSEND_REPARTICION_BEAN) && event.getData() instanceof ReparticionBean) {
					sectorReparticionSeleccionada = (ReparticionBean) event.getData();
			}
		}
		
	}
	
}
