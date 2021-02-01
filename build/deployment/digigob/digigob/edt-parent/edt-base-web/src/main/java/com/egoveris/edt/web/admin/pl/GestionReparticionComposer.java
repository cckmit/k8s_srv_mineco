package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

@SuppressWarnings("rawtypes")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GestionReparticionComposer extends GenericForwardComposer {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(GestionReparticionComposer.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3653535674201948178L;

	/** The Constant WIN_ABM_REPARTICION. */
	private static final String WIN_ABM_REPARTICION = "win_AbmReparticion";

	@Autowired
	protected AnnotateDataBinder binder;
	private List<ReparticionDTO> listaResultadoReparticiones;
	private Integer resultados;
	private ReparticionDTO selectedReparticion;
	@Autowired
	private Textbox txbx_reparticionBuscada;

	@WireVariable("reparticionEDTService")
	private IReparticionEDTService reparticionService;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.binder = new AnnotateDataBinder(comp);

		selectedReparticion = new ReparticionDTO();
		listaResultadoReparticiones = new ArrayList<>();
		resultados = listaResultadoReparticiones.size();
		comp.addEventListener(Events.ON_USER, new GestionReparticionComposerListener(this));
		comp.addEventListener(Events.ON_NOTIFY, new GestionReparticionComposerListener(this));
	}

	/**
	 * On click$tbbtn alta reparticion.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onClick$tbbtn_altaReparticion() throws InterruptedException {
		try {
			Utilitarios.closePopUps(WIN_ABM_REPARTICION);
			Map<String, Object> parametros = new HashMap<>();
			parametros.put(ConstantesSesion.KEY_ALTA, true);
			Window windowReparticion = (Window) Executions.createComponents(ABMReparticionComposer.ABM_REPARTICION_ZUL,
					this.self, parametros);
			windowReparticion.setPosition("center");
			windowReparticion.setVisible(true);
			windowReparticion.doModal();

		} catch (SuspendNotAllowedException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * On click$btn buscar.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onClick$btn_buscar() throws InterruptedException {
		busquedaReparticiones();
	}

	/**
	 * Busqueda reparticiones.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	private void busquedaReparticiones() throws InterruptedException {
		if (txbx_reparticionBuscada.getValue().trim().equals("*") || txbx_reparticionBuscada.getValue().length() >= 2) {
			buscarReparticiones();
		} else {
			throw new WrongValueException(txbx_reparticionBuscada, Labels.getLabel("eu.adminSade.validacion.minimoBusqueda"));
		}
	}

	/**
	 * Buscar reparticiones.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void buscarReparticiones() throws InterruptedException {
		try {
			if (!txbx_reparticionBuscada.getValue().isEmpty() 
					&& txbx_reparticionBuscada.getValue().trim().equals("*")) {
				listaResultadoReparticiones = reparticionService.listReparticiones();

				resultados = listaResultadoReparticiones.size();
				this.binder.loadAll();

			}else if(!txbx_reparticionBuscada.getValue().isEmpty()) {
				listaResultadoReparticiones = reparticionService
						.buscarReparticionByCodigoYNombreComodin(txbx_reparticionBuscada.getValue());

				resultados = listaResultadoReparticiones.size();
				this.binder.loadAll();

				
			}
		} catch (NegocioException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * On visualizar reparticion.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onVisualizarReparticion() throws InterruptedException {
		try {
			Utilitarios.closePopUps(WIN_ABM_REPARTICION);
			Map<String, Object> parametros = new HashMap<>();
			parametros.put(ConstantesSesion.KEY_REPARTICION, selectedReparticion);
			parametros.put(ConstantesSesion.KEY_VISUALIZAR, true);
			Window win = (Window) Executions.createComponents(ABMReparticionComposer.ABM_REPARTICION_ZUL, this.self,
					parametros);
			win.setMode("modal");
			win.setClosable(true);
			win.setTitle(Labels.getLabel("eu.gestionReparticionComposer.winTitle.visOrg"));
			win.setWidth("500px");
			win.setPosition("center");
			win.setVisible(true);
			win.setBorder("normal");
			win.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * On modificar reparticion.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onModificarReparticion() throws InterruptedException {
		try {
			Utilitarios.closePopUps(WIN_ABM_REPARTICION);
			Map<String, Object> parametros = new HashMap<>();
			parametros.put(ConstantesSesion.KEY_REPARTICION, selectedReparticion);
			parametros.put(ConstantesSesion.KEY_MODIFICAR, true);
			Window win = (Window) Executions.createComponents(ABMReparticionComposer.ABM_REPARTICION_ZUL, this.self,
					parametros);
			win.setMode("modal");
			win.setClosable(true);
			win.setTitle(Labels.getLabel("eu.gestionReparticionComposer.winTitle.modOrg"));
			win.setWidth("700px");
			win.setPosition("center");
			win.setVisible(true);
			win.setBorder("normal");
			win.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void actReparticion(ReparticionDTO reparticion) {
		this.listaResultadoReparticiones.remove(reparticionIndex(this.selectedReparticion));
		this.listaResultadoReparticiones.add(reparticion);
	}

	private ReparticionDTO reparticionIndex(ReparticionDTO selectedReparticion2) {
		for (ReparticionDTO reparticion : this.listaResultadoReparticiones) {
			if (reparticion.getNombre().trim().equals(selectedReparticion2.getNombre().trim())) {
				return reparticion;
			}
		}
		return null;
	}

	public void onOK$txbx_reparticionBuscada() throws InterruptedException {
		busquedaReparticiones();
	}

	public void setResultados(Integer resultados) {
		this.resultados = resultados;
	}

	public Integer getResultados() {
		return resultados;
	}

	public List<ReparticionDTO> getListaResultadoReparticiones() {
		return listaResultadoReparticiones;
	}

	public void setListaResultadoReparticiones(List<ReparticionDTO> listaResultadoReparticiones) {
		this.listaResultadoReparticiones = listaResultadoReparticiones;
	}

	public ReparticionDTO getSelectedReparticion() {
		return selectedReparticion;
	}

	public void setSelectedReparticion(ReparticionDTO selectedReparticion) {
		this.selectedReparticion = selectedReparticion;
	}

}

final class GestionReparticionComposerListener implements EventListener {
	private GestionReparticionComposer composer;

	public GestionReparticionComposerListener(GestionReparticionComposer comp) {
		this.composer = comp;
	}

	@Override
	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_USER)) {
			this.composer.buscarReparticiones();
		}
		if (event.getName().equals(Events.ON_NOTIFY)) {
			composer.binder.loadAll();
		}
	}
}
