package com.egoveris.ffdd.render.zk.form;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Row;

import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.ComplexComponenLayout;
import com.egoveris.ffdd.render.zk.constr.DynamicConstraint.ConstraintMember;
import com.egoveris.ffdd.render.zk.util.ComponentUtils;

public class VisibilityListener implements EventListener {
	
	private ConditionImpl condition;
	private Set<Component> listModifyVis;
	boolean estadoDefault = true;
	private VisibilidadComponenteDTO visibilidadComponenteDTO;

	public VisibilityListener(Component rootComp, VisibilidadComponenteDTO restriccionDTO) {
		this(rootComp, restriccionDTO, null);
	}
	
	public VisibilityListener(Component rootComp, VisibilidadComponenteDTO visibilidadComponenteDTO, String sufijoRepetitivo) {
		this.visibilidadComponenteDTO = visibilidadComponenteDTO;
		listModifyVis = obtenerComponentes(rootComp, visibilidadComponenteDTO.getComponentesOcultos(), sufijoRepetitivo);
		condition = new ConditionImpl(visibilidadComponenteDTO.getCondiciones(), rootComp, sufijoRepetitivo);
	}
	
	public void addListenerToParticipants() {
		for (Component part : condition.getParticipantsComps()) {
			ComponentUtils.addEventListener(part, this);
		}
	}
	
	public void triggerToParticipants() {
		for (Component part : condition.getParticipantsComps()) {
			ComponentUtils.dispararValidacion(part);
		}
	}
	
	private Set<Component> obtenerComponentes(Component rootComp, List<String> valores, String sufijoRepetitivo) {
		listModifyVis = new HashSet<Component>();
		for (String nombreComp : valores) {
			listModifyVis.add(ComponentUtils.findComponent(rootComp, nombreComp, sufijoRepetitivo));	
		}
		return listModifyVis;
	}
	
	@Override
	public void onEvent(Event event) throws Exception {
		boolean cond = condition.validar();
		if (cond == estadoDefault) {
			invertirVisibilidad();
			estadoDefault = !estadoDefault;
		}
	}
	
	private void invertirVisibilidad() {
		for (Component component : listModifyVis) {
			
			
			
			component.setVisible(!component.isVisible());
			component.invalidate();
			// row
			
			if(component instanceof Row) {
				((ComplexComponenLayout) component
						.getChildren().get(0).getChildren()
						.get(0)).setVisible(component.isVisible());
				
//				if(!component.isVisible()) {
					cleanInputElement(component
							.getChildren().get(0).getChildren()
							.get(0), component.isVisible());
//				}
				
			}
			
			if(component instanceof InputComponent) {
				
				if(!component.isVisible()) {
					((InputComponent) component).setRawValue(null);
				}
				
				// Textbox
				component.getParent().setVisible(component.isVisible());
				
				
				
				// fuerza las validaciones
				reValidate((InputComponent) component);

				// Label
				Component row = component.getParent().getParent();
				//TODO condicionar solo cuando sean componentes complejos
				for (Component componenteRow : row.getChildren()) {
					if (componenteRow instanceof Hbox) {
						if(!componenteRow.getNextSibling().isVisible() && !component.isVisible()){
							componenteRow.setVisible(false);
						}else{
							componenteRow.setVisible(true);
						}
						
					}
				}
			}
			

			

		}
	}
	/**
	 * limpia todos los elementos de los input complejos
	 * @param visible 
	 * @param complexComponenLayout
	 */
	private void cleanInputElement(Component component, boolean visible) {
		
		if(component instanceof InputComponent) {
			component.setVisible(visible);
			if (!visible) {
				((InputComponent) component).setRawValue(null);				
			}
		}

		for(Component child : component.getChildren()) {
			cleanInputElement(child, visible);
		}
	}

	@SuppressWarnings("rawtypes")
	private void reValidate(InputComponent component) {
		Iterator iterator = component.getListenerIterator(ComponentUtils.eventForComp(component));
		while (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			if (object instanceof ConstraintMember) {
				((ConstraintMember) object).reValidate();
			}
		}
	}

	public VisibilidadComponenteDTO getVisibilidadComponenteDTO() {
		return visibilidadComponenteDTO;
	}
}

