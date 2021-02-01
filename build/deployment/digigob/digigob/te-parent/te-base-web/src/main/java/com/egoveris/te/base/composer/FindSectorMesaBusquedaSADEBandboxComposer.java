package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindSectorMesaBusquedaSADEBandboxComposer extends
		GenericForwardComposer  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6771581427613301068L;
	
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	private ProcessEngine processEngine;
	@Autowired
	private Bandbox sectorBusquedaSADE;
	@Autowired
	private Textbox textoSectorBusquedaSADE;
	@Autowired
	private Listbox sectoresBusquedaSADEListbox;
	@Autowired
	private Combobox sector;
	@Autowired
	private AnnotateDataBinder binder;
	private List<SectorInternoBean> listaSectorSeleccionado;
	private SectorInternoBean sectorSeleccionado;
	
	@WireVariable(ConstantesServicios.SECTOR_INTERNO_SERVICE)
	private SectorInternoServ sectorInternoServ;
	
	//TRAER DATOS SLECCIONADOS DE LA REPARTICION
	private ReparticionBean sectorReparticionSeleccionada;
	
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		this.binder = new AnnotateDataBinder(sectoresBusquedaSADEListbox);
		this.listaSectorSeleccionado =  new ArrayList<SectorInternoBean>();
		binder.bindBean("listaSectorSADESeleccionado",
				this.listaSectorSeleccionado);
		binder.bindBean("sectorSeleccionado",
				this.sectorSeleccionado);
		
		binder.loadAll();
	}
	
	public void onFocus$sectorBusquedaSADE() {
		this.sectorReparticionSeleccionada =(ReparticionBean)Executions.getCurrent().getDesktop().getAttribute("reparticion");
	}
	
	public void onChanging$textoSectorBusquedaSADE(InputEvent e) {
		String matchingText = e.getValue();
		if (StringUtils.isNotEmpty(matchingText)
				&& (matchingText.length() >= 1)) {
			List<SectorInternoBean> listaSectoresSADECompleta = sectorInternoServ.buscarSectoresPorReparticionOrderByMesa(this.sectorReparticionSeleccionada.getId());
			this.listaSectorSeleccionado.clear();
			if (listaSectoresSADECompleta != null) {
				matchingText = matchingText.toUpperCase();
				Iterator<SectorInternoBean> iterator = listaSectoresSADECompleta
						.iterator();
				SectorInternoBean sector = null;
				while ((iterator.hasNext())
						&& (this.listaSectorSeleccionado.size() <= ConstantesWeb.MAX_REPARTICION_RESULTS)) {
					sector = iterator.next();
					if ((sector != null)
							&& (StringUtils.isNotEmpty(sector.getNombre()) && (StringUtils
									.isNotEmpty(sector.getCodigo())))) {
						if ((sector.getNombre().contains(matchingText))
								|| (sector.getCodigo().contains(matchingText))) {
							listaSectorSeleccionado.add(sector);
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
			if (!(sectorInternoServ.validarCodigoSectorMesa(value.trim(), this.sectorReparticionSeleccionada.getId()))) {
				this.sectorBusquedaSADE.focus();
				throw new WrongValueException(this.sectorBusquedaSADE, Labels
						.getLabel("ee.general.sectorInvalido"));
			}
			this.sectorBusquedaSADE.setValue(value.toUpperCase());
			
		}
	}

	public void onSelect$sectoresBusquedaSADEListbox() {
		SectorInternoBean sisb = this.listaSectorSeleccionado
				.get(sectoresBusquedaSADEListbox.getSelectedIndex());
		this.sectorBusquedaSADE.setValue(sisb.getCodigo());
		this.textoSectorBusquedaSADE.setValue(null);
		this.listaSectorSeleccionado.clear();
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

	public void setSector(Combobox sector) {
		this.sector = sector;
	}

	public Combobox getSector() {
		return sector;
	}

}
