package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.egoveris.ffdd.base.util.ConstantesConstraint;
import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;


public class FormRestriccionComposer extends FormCondicionComposer{

	private static final long serialVersionUID = -5233157391868867203L;
	protected Bandbox componentesRestringidos;
	protected Listbox listaComponentesRestringidos;

	/**
	 * Permite generar para cada ventana un constraintDTO ,con la lista de constraints asociadas.
	 * Envia un evento a FormConstraintGenericoComposer ,pasandole el mapa
	 * @param grid
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	private void enviarMapConstraints(Grid grid) throws InterruptedException {
		List<CondicionDTO> list = (List<CondicionDTO>) grid.getListModel();
		List<VisibilidadComponenteDTO> listaRestriccionDto = new ArrayList<VisibilidadComponenteDTO>();
		VisibilidadComponenteDTO restriccionDto = new VisibilidadComponenteDTO();
		restriccionDto.setComponentesOcultos(obtenerComponentesOcultar());
		restriccionDto.setCondiciones(new ArrayList<CondicionDTO>(list));
		listaRestriccionDto.add(restriccionDto);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("listaComponentesOcultos", listaRestriccionDto);
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));
	}
	
	/**
	 * Permite obtener una lista de los componentes que el usuario selecciono 7para ocultar...
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	private List<String> obtenerComponentesOcultar() {
		List<String> listaComponentes = new ArrayList<String>();
		Set<Listitem> lista = listaComponentesRestringidos.getSelectedItems();
		
		if(lista.isEmpty()){
			String[] componentes= this.componentesRestringidos.getValue().split(",");
			for(String nombre : componentes){
				if(nombre.trim().isEmpty()){
					break;
				}
				listaComponentes.add(nombre.trim());
			}
		}else{
			List<Listitem> listaItems = new ArrayList<Listitem>(lista);
			for (Listitem listItem : listaItems) {
				FormularioComponenteDTO formComp = (FormularioComponenteDTO) listItem.getValue();
				listaComponentes.add(formComp.getNombre());
			}
		}

		return listaComponentes;
	}
	
	/**
	 * Permite cargar al componente bandbox los componentes que selecciono el usuario...
	 * @param listComponentes
	 */
	protected void cargarRestricciones(List<String> listComponentes){
		StringBuffer componentes = new StringBuffer();
		for(String comp : listComponentes){
			componentes.append(comp).append(",");
		}
		this.componentesRestringidos.setValue(componentes.toString());
	}
	
	public void cargarRestriccionesSeleccionadas(){
		List<String> componentes  = obtenerComponentesOcultar();
		cargarRestricciones(componentes);
	}
	

	final class FormRestriccionGenericoComposerListener implements EventListener {

		private FormRestriccionComposer composer;

		public FormRestriccionGenericoComposerListener(
				FormRestriccionComposer composer) {
			this.composer = composer;
		}
		
		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				Map<String, Object> mapa = (Map<String, Object>) event.getData();
				String accion = (String) mapa.get("accion");
				if(accion.equals(ConstantesConstraint.RESTRICCION_COMPONENTES_TEMPORALES_GUARDAR)){
					Grid grid = (Grid) this.composer.self.getFellow("grillaConstraint");
					this.composer.enviarMapConstraints(grid);
				}				
			}
			if(event.getName().equals(Events.ON_SELECT)){
				this.composer.cargarRestriccionesSeleccionadas();
			}
		}
	}
}