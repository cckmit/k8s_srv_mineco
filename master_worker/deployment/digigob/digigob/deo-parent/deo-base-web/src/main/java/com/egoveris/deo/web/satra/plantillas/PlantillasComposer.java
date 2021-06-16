package com.egoveris.deo.web.satra.plantillas;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.deo.base.service.IPlantillaFacade;
import com.egoveris.deo.model.model.PlantillaDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PlantillasComposer extends GEDOGenericForwardComposer {

	private static final long serialVersionUID = 460979738592483438L;
	private static final Logger logger = LoggerFactory.getLogger(PlantillasComposer.class);
	private static final String ERRGEN = "gedo.general.error";
	private AnnotateDataBinder binder;

	private Window plantillaWindow;

	@WireVariable("plantillaFacadeImpl")
	private IPlantillaFacade plantillaFacade;

	private List<PlantillaDTO> listaPlantilla;

	private PlantillaDTO selectedPlantilla;

	private Listbox listboxPlantilla;

	@Override
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.plantillaWindow = new Window();
		this.listaPlantilla = this.plantillaFacade.buscarPlantillaPorUsuarioPlantilla(
				(String) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME));

		this.setListaPlantilla(this.listaPlantilla);
		this.binder = new AnnotateDataBinder(component);
		this.binder.loadAll();

		component.addEventListener(Events.ON_NOTIFY, new ActualizarPlantillasListener(this));

	}

	public void onClick$nuevaPlantilla() throws InterruptedException {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("accion", "crear");
		hm.put("listadoPlantillas", listaPlantilla);
		setearPlantilla(hm);
	}

	private void setearPlantilla(HashMap<String, Object> hm) {

		if (this.plantillaWindow != null) {
			this.plantillaWindow.detach();
			this.plantillaWindow = (Window) Executions.createComponents("/inbox/plantilla.zul", this.self, hm);
			this.plantillaWindow.setParent(this.self);
			this.plantillaWindow.setVisible(true);
			this.plantillaWindow.doModal();
		} else {
			Messagebox.show(Labels.getLabel("gedo.general.imposibleIniciarVista"), Labels.getLabel(ERRGEN),
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onEditarPlantilla() throws InterruptedException {
		HashMap<String, Object> hm = new HashMap<>();
		try {
			this.selectedPlantilla = this.plantillaFacade.buscarPlantillaPorId(this.selectedPlantilla.getId());
		} catch (Exception e) {
			logger.error("Error al editar pantalla " + e.getMessage(), e);
			Messagebox.show(Labels.getLabel("gedo.plantillas.error.consultar.plantilla"), Labels.getLabel(ERRGEN),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		hm.put("selectedPlantilla", this.selectedPlantilla);
		hm.put("accion", "editar");
		setearPlantilla(hm);

	}

	public void onEliminarPlantilla() {
		Messagebox.show(Labels.getLabel("gedo.plantillas.error.abm.eliminarPlantilla"),
				Labels.getLabel("gedo.plantillas.error.abm.eliminarPlantilla.titulo"), Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) {
						if ("onYes".equals(e.getName())) {
							eliminarPlantilla();
						}
					}

				});
	}

	public void eliminarPlantilla() {
		try {
			this.selectedPlantilla = this.plantillaFacade.buscarPlantillaPorId(this.selectedPlantilla.getId());
			this.plantillaFacade.eliminar(this.selectedPlantilla);
		} catch (Exception e) {
			logger.error("Error al eliminar plantilla " + e.getMessage(), e);
			Messagebox.show(Labels.getLabel("gedo.plantillas.error.consultar.plantilla"), Labels.getLabel(ERRGEN),
					Messagebox.OK, Messagebox.ERROR);
		}
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self, null));
	}

	public void setListaPlantilla(List<PlantillaDTO> listaPlantilla) {
		this.listaPlantilla = listaPlantilla;
	}

	public List<PlantillaDTO> getListaPlantilla() {
		return listaPlantilla;
	}

	public void setSelectedPlantilla(PlantillaDTO selectedPlantilla) {
		this.selectedPlantilla = selectedPlantilla;
	}

	public PlantillaDTO getSelectedPlantilla() {
		return selectedPlantilla;
	}

	public void refreshComposer() {
		this.listaPlantilla = this.plantillaFacade.buscarPlantillaPorUsuarioPlantilla(
				(String) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME));
		this.binder.loadComponent(this.listboxPlantilla);
	}

}

final class ActualizarPlantillasListener implements EventListener {

	private PlantillasComposer composer;

	public ActualizarPlantillasListener(PlantillasComposer composer) {
		this.composer = composer;
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_NOTIFY)) {
			this.composer.refreshComposer();
		}
	}
}
