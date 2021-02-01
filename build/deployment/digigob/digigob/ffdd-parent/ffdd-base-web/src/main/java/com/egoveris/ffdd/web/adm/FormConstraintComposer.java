package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

import com.egoveris.ffdd.base.util.ConstantesConstraint;
import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.ConstraintDTO;


public class FormConstraintComposer extends FormCondicionComposer{

	private static final long serialVersionUID = -5344834912990842034L;
	
	protected Textbox descripcionConstraint;

	/**
	 * Permite generar para cada ventana un constraintDTO ,con la lista de constraints asociadas.
	 * Envia un evento a FormConstraintGenericoComposer ,pasandole el mapa
	 * @param grid
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	private void enviarMapConstraints(Grid grid) throws InterruptedException {
		List<CondicionDTO> list = (List<CondicionDTO>) grid.getListModel();
		List<ConstraintDTO> listConstraintDTO = new ArrayList<ConstraintDTO>();
		ConstraintDTO constraintDTO = new ConstraintDTO();
		constraintDTO.setMensaje(obtenerMensajeConstraint());
		try{
			constraintDTO.setNombreComponente(obtenerNombreComponente());
		}catch(WrongValueException e){
			this.mapaConstraints.clear(); // si el campo nombre componente es nulo, se vacia el mapa y corta el flujo de ejecucion
			throw e;
		}
		constraintDTO.setCondiciones(new ArrayList<CondicionDTO>(list));
		if (this.mapaConstraints.containsKey(obtenerNombreComponente())) {
			listConstraintDTO = (List<ConstraintDTO>) this.mapaConstraints
					.get(obtenerNombreComponente());
		}
		listConstraintDTO.add(constraintDTO);
		this.mapaConstraints.put(obtenerNombreComponente(), listConstraintDTO);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mapaConstraints", this.mapaConstraints);
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));
	}
	
	private String obtenerMensajeConstraint(){
		return this.descripcionConstraint.getValue();
	}
	
	final class FormConstraintGenericoComposerListener implements EventListener {

		private FormConstraintComposer composer;

		public FormConstraintGenericoComposerListener(
				FormConstraintComposer composer) {
			this.composer = composer;
		}
		
		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				Map<String, Object> mapa = (Map<String, Object>) event.getData();
				String accion = (String) mapa.get("accion");
				if(accion.equals(ConstantesConstraint.CONSTRAINT_COMPONENTES_TEMPORALES_GUARDAR)){
					Grid grid = (Grid) this.composer.self.getFellow("grillaConstraint");
					this.composer.enviarMapConstraints(grid);
				}					
			}
		}

	
	}
}
