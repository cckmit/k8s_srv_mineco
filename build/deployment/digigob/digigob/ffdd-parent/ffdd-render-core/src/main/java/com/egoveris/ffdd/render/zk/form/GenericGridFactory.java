package com.egoveris.ffdd.render.zk.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.terasoluna.plus.core.util.ApplicationContextUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;
import com.egoveris.ffdd.render.model.ComponentZkExt;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IComponentFactory;
import com.egoveris.ffdd.render.zk.comp.ext.ComplexComponenLayout;
import com.egoveris.ffdd.render.zk.comp.ext.ConstrInputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.MultiConstrData;
import com.egoveris.ffdd.render.zk.util.ComponentUtils;

public class GenericGridFactory implements IFormFactory {

	

	private Map<String, IComponentFactory<ComponentZkExt>> componentFactoriesMap;
	private Map<String, ComponentZkExt> inputCompZkMap;

	@Autowired
	@Qualifier("complexComponentFactory")
	private IComponentFactory<ComponentZkExt> complexFactory;

	
	private static void addVisibilityListener(final Component grilla,
			final List<VisibilidadComponenteDTO> restriccionDTOs) {

		final Component rootComp = ComponentUtils.obtenerVlayoutRoot(grilla);
		if (restriccionDTOs != null) {
			for (final VisibilidadComponenteDTO restriccionDTO : restriccionDTOs) {
				VisibilityListener visible = new VisibilityListener(rootComp, restriccionDTO);
				visible.addListenerToParticipants();
				
			}
		}
	}
	
	public static void dispararEventos(final Component grilla, final List<VisibilidadComponenteDTO> restriccionDTOs) {
		
		final Component rootComp = ComponentUtils.obtenerVlayoutRoot(grilla);
		if (restriccionDTOs != null) {
			for (final VisibilidadComponenteDTO restriccionDTO : restriccionDTOs) {
				VisibilityListener visible = new VisibilityListener(rootComp, restriccionDTO);
				visible.triggerToParticipants();
				
			}
	}		
		
	}
	
	
	private void buildConstraints() {
		for (final ComponentZkExt input : this.inputCompZkMap.values()) {
			if (input instanceof ComplexComponenLayout) {
				buildConstraintsChildren(input);
			}
			ComponentUtils.buildConstraint(input);
		}
	}

	private void buildConstraintsChildren(ComponentZkExt input) {
		if (input.getChildren() != null) {
			for (final Object obj : input.getChildren()) {
				if (obj instanceof ConstrInputComponent) {
					ComponentUtils.buildConstraint((ConstrInputComponent) obj);
				}
				buildConstraintsChildren((Component) obj, ((ComplexComponenLayout) input).getMultiConstrData());
			}
		}
	}
	
	private void buildConstraintsChildren(Component input, MultiConstrData multiConstrData) {
		if (input.getChildren() != null) {
			for (final Object obj : input.getChildren()) {
				if (obj instanceof ConstrInputComponent) {
					ConstrInputComponent constr = (ConstrInputComponent) obj;
					constr.setMultiConstrData(multiConstrData);
					ComponentUtils.buildConstraint(constr);
				}
				buildConstraintsChildren((Component) obj, multiConstrData);
			}
		}
	}

	/**
	 * Genera el layout con las grillas contenedoras de los componentes zk.
	 */
	@Override
	public Component create(final FormularioDTO form) {

		this.inputCompZkMap = new LinkedHashMap<>();

		final Component layoutComponent = new Vlayout();
		Component grilla = null;

		final List<FormularioComponenteDTO> list = new ArrayList<>(form.getFormularioComponentes());
		Collections.sort(list);

		for (final FormularioComponenteDTO formComponent : list) {

			boolean esSeparatorExt = ComponentUtils.SEPARATOR.equals(formComponent.getComponente().getTipoComponente())
					&& !ComponentUtils.SEPARATOR_INTERNO.equals(formComponent.getComponente().getNombre());

			if (esSeparatorExt) {
				grilla = nuevaGrilla(layoutComponent);
				ComponentUtils.generarSeparatorExt(formComponent).setParent(grilla.getParent());
			} else {
				if (grilla == null) {
					grilla = nuevaGrilla(layoutComponent);
				}
				if (formComponent.getComponente().getNombre().equals(ComponentUtils.SEPARATOR_INTERNO)) {
					ComponentUtils.generarHeaderInterno(formComponent).setParent(grilla);
				} else {
					generarRowComp(formComponent).setParent(grilla);
				}
			}
		}

		buildConstraints();
		addVisibilityListener(grilla, form.getListaComponentesOcultos());
		
		return layoutComponent;
	}

	/**
	 * modifico el estado visible de los componentes ocultos para que sea visualizado o no
	 * @param grilla
	 * @param listaComponentesOcultos
	 */
	private void manejarCamposOcultos(Component grilla, List<VisibilidadComponenteDTO> listaComponentesOcultos) {
		
		List<Component> componentesOcultos = new ArrayList<>();
		for(VisibilidadComponenteDTO  visibilidadComponente :listaComponentesOcultos) {
			
			
			for(String nombreComponente : visibilidadComponente.getComponentesOcultos()) {
				 Row row = (Row) grilla.getFellowIfAny(nombreComponente);
				  componentesOcultos.add(row.getChildren().get(1));
				  
			}
			
			
		}
		
		
	}


	/**
	 * 
	 * Genera una fila para el componente.
	 * 
	 * @param formComp
	 * @return
	 */
	private Row generarRowComp(final FormularioComponenteDTO formComp) {

		final ComponenteDTO comp = formComp.getComponente();

		//Se busca la factoría zk asociada al componente y se crea un componente zk a partir del componente de formulario
		final ComponentZkExt zkComponent = obtenerCompFactory(comp.getTipoComponenteEnt()).create(formComp);

		this.inputCompZkMap.put(formComp.getNombre(), zkComponent);

		final Row row = new Row();
		
		row.setId(formComp.getNombre());
		
		if(formComp.getComponente().getNombreXml()!=null) {
			row.setValue(formComp.getNombre());
		}

		if (formComp.getTextoMultilinea() != null || formComp.isMultilineaEditable()) {
			final Cell celda = new Cell();
			celda.setColspan(2);
			celda.setAlign("center");
			((Textbox) zkComponent).setWidth("99%");
			((Textbox) zkComponent).setHeight("175px");
			((Textbox) zkComponent).setRows(3);
			((Textbox) zkComponent).setReadonly(!formComp.isMultilineaEditable());
			((Textbox) zkComponent).setValue(formComp.getTextoMultilinea());
			celda.appendChild(zkComponent);
			celda.setParent(row);
		} else {
			if (StringUtils.isEmpty(formComp.getComponente().getNombreXml())) {
				new Label(formComp.getEtiqueta()).setParent(row);
				zkComponent.setParent(row);
			} else {
				final Cell celda = new Cell();
				celda.setColspan(2);
				celda.setAlign("center");
				celda.appendChild(zkComponent);
				celda.setParent(row);
				if (zkComponent instanceof ComplexComponenLayout) {
					visiblidadChildren(zkComponent, zkComponent.isVisible());
				}
			}
		}
		
		row.setVisible(zkComponent.isVisible());

		return row;
	}
	
	private void visiblidadChildren(Component componente, boolean visible) {
		if (componente instanceof InputComponent){
			componente.setVisible(visible);
		}
		
		for(Component child : componente.getChildren()) {
			visiblidadChildren(child, visible);
		}
	}

	private Rows nuevaGrilla(final Component layoutComponent) {
		final Rows rows = ComponentUtils.nuevaGrilla();
		rows.getGrid().setParent(layoutComponent);
		return rows;
	}

	@Override
	public IComponentFactory<ComponentZkExt> obtenerCompFactory(final TipoComponenteDTO tipo) {
		IComponentFactory<ComponentZkExt> cfact = complexFactory;
		if (null != tipo.getFactory()) {
		  final Object bean = ApplicationContextUtil.getBean(tipo.getFactory());
			if (bean instanceof IComponentFactory) {
				cfact = (IComponentFactory<ComponentZkExt>) bean;
			}
		}
		return cfact;
	}

	public Map<String, IComponentFactory<ComponentZkExt>> getComponentFactoriesMap() {
		return componentFactoriesMap;
	}

	public void setComponentFactoriesMap(final Map<String, IComponentFactory<ComponentZkExt>> componentFactoriesMap) {
		this.componentFactoriesMap = componentFactoriesMap;
	}

}
