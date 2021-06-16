package com.egoveris.ffdd.web.adm;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;


public class ABMConstraintComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -3243183650656830983L;

	protected List<FormularioComponenteDTO> listaFormularioComponente;

	protected Map<String, Object> mapaConstraints = new HashMap<String, Object>();

	@SuppressWarnings({ "unchecked" })
	protected <T> List<T> crearObjetoCondicionalDto(Window ventanaHijo) {
		String nombreComp = obtenerCondicionesComponente(ventanaHijo);
		return (List<T>) this.getMapaConstraints().get(nombreComp);
	}

	protected void vaciarMapa(Window ventanaHijo) {
		String nombreComp = obtenerCondicionesComponente(ventanaHijo);
		if (this.getMapaConstraints().containsKey(nombreComp)) {
			this.getMapaConstraints().remove(nombreComp);
		}
	}

	private String obtenerCondicionesComponente(Window ventanaHijo) {
		String nombreComp = "";
		@SuppressWarnings("unchecked")
		Collection<Component> colecction = ventanaHijo.getFellows();
		for (Component component : colecction) {
			if (component != null && component.getId().equals("nombreComponente")) {
				Combobox nombreComponente = (Combobox) component;
				nombreComp = nombreComponente.getValue();
			}
		}
		return nombreComp;
	}

	protected boolean validarCondicionVacia(CondicionDTO condicion) {
		if (condicion.getNombreComponente() != null && condicion.getCondicion() != null) {
			switch (condicion.getCondicion()) {
			case IGUAL:
			case DISTINTO:
			case MENOR:
			case MAYOR: {
				if (condicion.getNombreCompComparacion() != null || condicion.getValorComparacion() != null) {
					return true;
				} else {
					return false;
				}

			}
			}
			return true;
		}
		return false;
	}

	public List<FormularioComponenteDTO> getListaFormularioComponente() {
		return listaFormularioComponente;
	}

	public void setListaFormularioComponente(List<FormularioComponenteDTO> listaFormularioComponente) {
		this.listaFormularioComponente = listaFormularioComponente;
	}

	public Map<String, Object> getMapaConstraints() {
		return mapaConstraints;
	}

	public void setMapaConstraints(Map<String, Object> mapaConstraints) {
		this.mapaConstraints = mapaConstraints;
	}

}
