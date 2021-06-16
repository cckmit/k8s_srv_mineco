package com.egoveris.workflow.designer.module.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.te.base.util.TruncatedResults;

@SuppressWarnings("deprecation")
public class FindSectorReparticionesBusquedaSADEBandboxComposer extends GenericForwardComposer<Component>
		implements TruncatedResults {

	/**
	 *
	 */
	private static final long serialVersionUID = 2825309019932584527L;
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
	private SectorInternoServ sectorInternoServ;

	private ReparticionServ reparticionServ;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);

		this.processEngine = (ProcessEngine) SpringUtil.getBean("processEngine");
		this.sectorInternoServ = (SectorInternoServ) SpringUtil.getBean("sectorInternoServImpl");
		this.binder = new AnnotateDataBinder(sectoresReparticionesBusquedaSADEListbox);
		binder.bindBean("sectorReparticionSeleccionada", this.sectorReparticionSeleccionada);
		binder.loadAll();
		this.reparticionServ = (ReparticionServ) SpringUtil.getBean("reparticionServImpl");
		this.listaSectorReparticionSeleccionada = new ArrayList<>();
		binder.bindBean("listaSectorReparticionSADESeleccionada", this.listaSectorReparticionSeleccionada);

	}

	public void onChanging$textoSectorReparticionBusquedaSADE(final InputEvent e) {
		String matchingText = e.getValue();
		if (StringUtils.isNotEmpty(matchingText)) {
			final List<ReparticionBean> listaReparticionesCompleta = this.reparticionServ
					.buscarReparticionesVigentesActivas();
			this.listaSectorReparticionSeleccionada.clear();
			if (listaReparticionesCompleta != null) {
				matchingText = matchingText.toUpperCase();
				final Iterator<ReparticionBean> iterator = listaReparticionesCompleta.iterator();
				ReparticionBean reparticion = null;
				while (iterator.hasNext()
						&& this.listaSectorReparticionSeleccionada.size() <= TruncatedResults.MAX_REPARTICION_RESULTS) {
					reparticion = iterator.next();
					if (reparticion != null && StringUtils.isNotEmpty(reparticion.getNombre())
							&& StringUtils.isNotEmpty(reparticion.getCodigo())) {
						if (reparticion.getNombre().contains(matchingText)
								|| reparticion.getCodigo().contains(matchingText)) {
							listaSectorReparticionSeleccionada.add(reparticion);
						}
					}
				}
			}
			this.binder.loadComponent(sectoresReparticionesBusquedaSADEListbox);
		}
	}

	public void onBlur$sectorReparticionBusquedaSADE() {
		final String value = this.sectorReparticionBusquedaSADE.getValue();
		if (StringUtils.isNotEmpty(value)) {
			this.sectorReparticionBusquedaSADE.setValue(value.toUpperCase());

			final ReparticionBean rsb = this.reparticionServ.buscarReparticionPorCodigo(value);
			if (rsb == null || rsb.getVigenciaHasta() == null || rsb.getVigenciaHasta().before(new Date())
					|| rsb.getVigenciaDesde().after(new Date())) {
				this.sectorReparticionBusquedaSADE.focus();
				throw new WrongValueException(this.sectorReparticionBusquedaSADE,
						Labels.getLabel("ee.general.reparticionInvalida"));
			}
			this.sectorBusquedaSADE.setDisabled(false);
			this.sectorBusquedaSADE.setFocus(true);
			Executions.getCurrent().getDesktop().setAttribute("reparticion", rsb);
		}
	}

	public void onSelect$sectoresReparticionesBusquedaSADEListbox() {
		final ReparticionBean rsb = this.listaSectorReparticionSeleccionada
				.get(sectoresReparticionesBusquedaSADEListbox.getSelectedIndex());
		this.sectorReparticionBusquedaSADE.setValue(rsb.getCodigo());
		this.textoSectorReparticionBusquedaSADE.setValue(null);
		this.listaSectorReparticionSeleccionada.clear();
		this.binder.loadComponent(this.sectorReparticionBusquedaSADE);
		this.textoSectorReparticionBusquedaSADE.setValue(null);
		sectorReparticionBusquedaSADE.close();

		this.sectorBusquedaSADE.setDisabled(false);
		// this.sector.setDisabled(false);

		Executions.getCurrent().getDesktop().setAttribute("reparticion", rsb);
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(final ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public void setListaSectorReparticionSADESeleccionada(
			final List<ReparticionBean> listaSectorReparticionSADESeleccionada) {
		this.listaSectorReparticionSADESeleccionada = listaSectorReparticionSADESeleccionada;
	}

	public List<ReparticionBean> getListaSectorReparticionSADESeleccionada() {
		return listaSectorReparticionSADESeleccionada;
	}

	public void setSectorInternoServ(final SectorInternoServ sectorInternoServ) {
		this.sectorInternoServ = sectorInternoServ;
	}

	public SectorInternoServ getSectorInternoServ() {
		return sectorInternoServ;
	}

}
