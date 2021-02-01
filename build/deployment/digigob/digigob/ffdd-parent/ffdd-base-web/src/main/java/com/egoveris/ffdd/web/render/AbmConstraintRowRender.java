package com.egoveris.ffdd.web.render;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.base.util.ConstantesValidarFormulario;
import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.CondicionDTO.Condicion;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;


public class AbmConstraintRowRender implements RowRenderer {

	private List<FormularioComponenteDTO> listaFormularioComponente;
	
	public final static int COMBO_CONDICIONAL = 0;
	public final static int COMBO_COMPONENTE = 1;
	public final static int COMBO_LOGICA = 2;
	public final static int COMBO_COMPARACION = 3;	
	public final static int DIV = 4;

	public AbmConstraintRowRender(
			List<FormularioComponenteDTO> listaFormularioComponente) {
		this.listaFormularioComponente = listaFormularioComponente;
	}

	private void agregarEvento(Component c) {
		c.addEventListener(Events.ON_CHANGE, new EventListener() {
			@Override
			public void onEvent(Event e) throws Exception {
			}

		});
	}
	
	@Override
	public void render(Row row, Object data,int index) throws Exception {
		
		CondicionDTO condicion = (CondicionDTO)data;
		FormularioComponenteDTO formCompConstraint = obtenerFormComp(condicion.getNombreComponente());
		FormularioComponenteDTO formCompConstraintCompara = null;
		if(condicion.getNombreCompComparacion() != null){
			formCompConstraintCompara = obtenerFormComp(condicion.getNombreCompComparacion());
			if(formCompConstraintCompara.getNombre() == null){
				condicion.setNombreCompComparacion(null);
				condicion.setValorComparacion(null);
				condicion.setCondicion(null);
				condicion.setNombreComponente(null);
			}
		}
			
		if(formCompConstraint.getNombre() == null){
			condicion.setNombreCompComparacion(null);
			condicion.setValorComparacion(null);
			condicion.setCondicion(null);
			condicion.setNombreComponente(null);
		}
		//Condicion Y/O
		Combobox comboCondicional = new Combobox();
		comboCondicional.addEventListener(Events.ON_SELECT,new AbmConstraintRowRenderEvent(this,data,0));
		comboCondicional.setName("comboCondicional");
		comboCondicional.setWidth("50px");
		comboCondicional.setReadonly(true);
		comboCondicional.appendItem(Labels.getLabel("abmConstraint.render.condicionAnd"));
		comboCondicional.appendItem(Labels.getLabel("abmConstraint.render.condicionOr"));
		if(condicion.isPrimero()){
			comboCondicional.setDisabled(true);	
		}else if(condicion.isOr() || condicion.isAnd()){
			if(condicion.isAnd()){
				comboCondicional.setValue(Labels.getLabel("abmConstraint.render.condicionAnd"));
			}else{
				comboCondicional.setValue(Labels.getLabel("abmConstraint.render.condicionOr"));
			}
		}
		row.appendChild(comboCondicional);
		
		//Componente que genera condicion
		Combobox comboComponente = new Combobox();
		comboComponente.setName("comboComponente");
		comboComponente.setWidth("155px");
		comboComponente.setValue(formCompConstraint.getNombre());
		comboComponente.setReadonly(true);
		comboComponente.addEventListener(Events.ON_SELECT,new AbmConstraintRowRenderEvent(this,data,0));
		comboComponente.setModel(new ListModelArray(filtrarComponentes(listaFormularioComponente)));
		agregarEvento(comboComponente);
		row.appendChild(comboComponente);
		
		//Componente para la logica de comparacion
		Combobox comboLogica = new Combobox();
		if(condicion.getCondicion()!=null){
			comboLogica.setValue(condicion.getCondicion().name());
		}
		comboLogica.setReadonly(true);
		comboLogica.addEventListener(Events.ON_SELECT,new AbmConstraintRowRenderEvent(this,data,0));
		comboLogica.setDisabled(true);
		comboLogica.setWidth("130px");
		comboLogica.setName("comboLogica");
		agregarEvento(comboLogica);
		row.appendChild(comboLogica);	
		
		//Componente donde se elige como comparar
		Combobox comboCompara = new Combobox();
		comboCompara.setWidth("150px");
		comboCompara.setDisabled(true);
		comboCompara.appendItem(Labels.getLabel("abmConstraint.render.comboCompara.valor"));
		comboCompara.appendItem(Labels.getLabel("abmFC.instComp.comp"));
		if (condicion.getValorComparacion() != null) {
			comboCompara.setValue("Valor");
		}
		if (condicion.getNombreCompComparacion() != null) {
			comboCompara.setValue("Componente");
		}
		
		comboCompara.addEventListener(Events.ON_SELECT,new AbmConstraintRowRenderEvent(this,data,0));
		comboCompara.setReadonly(true);
		comboCompara.setName("comboCompara");
		agregarEvento(comboCompara);
		row.appendChild(comboCompara);
		
		
		//Componente contra quien se compara
		Combobox comboComparaContra = new Combobox();
		comboComparaContra.setName("comboComparaContra");
		comboComparaContra.setWidth("160px");
		comboComparaContra.setVisible(false);
		comboComparaContra.setParent(row);
		comboComparaContra.setReadonly(true);
		comboComparaContra.addEventListener(Events.ON_SELECT,new AbmConstraintRowRenderEvent(this, data,0));
		if(condicion.getNombreCompComparacion()!=null){ 
			FormularioComponenteDTO formCompComparaConstraint = obtenerFormComp(condicion.getNombreCompComparacion());
			comboComparaContra.setValue(formCompComparaConstraint.getNombre());
			List<FormularioComponenteDTO> listaFormularioComponenteComparar = generarListaPorTipoComponente(condicion.getNombreCompComparacion());
			comboComparaContra.setModel(new ListModelList(filtrarComponentes(listaFormularioComponenteComparar)));
			comboComparaContra.setVisible(true);
			comboComparaContra.setDisabled(true);
		}	
		
		//setea el value al campo comparacion por valor
		Div div = new Div();
		div.appendChild(comboComparaContra);
		if(condicion.getValorComparacion() != null){
 			Component comp = evaluarTipoComponente(formCompConstraint.getComponente().getTipoComponente());
 			div.appendChild(comp);
 			if(comp instanceof Checkbox){
 				((Checkbox) comp).setChecked(Boolean.valueOf(condicion.getValorComparacion()));
 				((Checkbox) comp).setDisabled(true);
 			} else {
 				((InputElement) comp).setDisabled(true);
 				if (comp instanceof Datebox){
 					DateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy"); 
 					((InputElement) comp).setRawValue(dateTimeFormatter.parse(condicion.getValorComparacion()));
 				} else if (comp instanceof Intbox){
 					((InputElement) comp).setRawValue(Integer.valueOf(condicion.getValorComparacion()));
 				} else if (comp instanceof Doublebox){
 					((InputElement) comp).setRawValue(Double.valueOf(condicion.getValorComparacion()));
 				} else if(comp instanceof Textbox){
 					((InputElement) comp).setRawValue(condicion.getValorComparacion());
 				} else if (comp instanceof Longbox){
 					((InputElement) comp).setRawValue(Long.valueOf(condicion.getValorComparacion()));
 				} else if (comp instanceof Timebox){
 					((InputElement) comp).setRawValue(condicion.getValorComparacion());
 				}		
 			}
		}				
		row.appendChild(div);
		
		//Check OCultar/mostrar
		/*
		Checkbox checkOcultar = new Checkbox();
		Div divCheck = new Div();
		divCheck.setAlign("center");
		divCheck.appendChild(checkOcultar);
		row.appendChild(divCheck);
  	*/
		//boton para eliminar una condicion
		Toolbarbutton botonEliminar = new Toolbarbutton();
		botonEliminar.setImage("/imagenes/menosConstraint.png");
		botonEliminar.addEventListener(Events.ON_CLICK,new AbmConstraintRowRenderEvent(this,data,0));
		
		Div divBoton = new Div();
		divBoton.setAlign("center");	
		divBoton.appendChild(botonEliminar);
		row.appendChild(divBoton);

	}
	
	private List<FormularioComponenteDTO> filtrarComponentes(List<FormularioComponenteDTO> list){
		List<FormularioComponenteDTO> response = new ArrayList<FormularioComponenteDTO>();
		for(FormularioComponenteDTO componenteDTO : list){
			if(!componenteDTO.isMultilinea()){
				response.add(componenteDTO);
			}
		}
		return response;
	}
	
	private FormularioComponenteDTO obtenerFormComp(String nombreFormComp){
		FormularioComponenteDTO formComp = new FormularioComponenteDTO();
		for(FormularioComponenteDTO formCompConstraint: this.listaFormularioComponente){
			if(formCompConstraint.getNombre().equals(nombreFormComp)){
				formComp = formCompConstraint;
			}
		}
		return formComp;
	}

	public List<FormularioComponenteDTO> getListaFormularioComponente() {
		return listaFormularioComponente;
	}

	public void setListaFormularioComponente(
			List<FormularioComponenteDTO> listaFormularioComponente) {
		this.listaFormularioComponente = listaFormularioComponente;
	}
	
	/**
	 * Permite generar una lista, con los componentes del formulario compatibles
	 * para comparar.
	 * 
	 * @param tipoComponente
	 * @return
	 */
	public List<FormularioComponenteDTO> generarListaPorTipoComponente(
			String tipoComponente) {
		List<FormularioComponenteDTO> listaFormularioComparar = new ArrayList<FormularioComponenteDTO>();
		for (FormularioComponenteDTO formularioComponente : this.listaFormularioComponente) {
			if (formularioComponente.getComponente().getTipoComponente()
					.equals(tipoComponente)) {
				listaFormularioComparar.add(formularioComponente);
			}
		}
		return listaFormularioComparar;
	}
	
	private Component evaluarTipoComponente(String tipoComponente) {
		if (tipoComponente != null) {
			if (tipoComponente.equals(ConstantesValidarFormulario.TEXTBOX)
					|| tipoComponente
							.equals(ConstantesValidarFormulario.COMBOBOX)
					|| tipoComponente
							.equals(ConstantesValidarFormulario.COMPLEXBANDBOX)
					|| tipoComponente
							.equals(ConstantesValidarFormulario.USIGBOX)
					|| tipoComponente
							.equals(ConstantesValidarFormulario.NROSADEBOX)) {
				return new Textbox();
			}
			if (tipoComponente.equals(ConstantesValidarFormulario.DATEBOX)) {
				return new Datebox();
			}
			if (tipoComponente.equals(ConstantesValidarFormulario.LONGBOX)) {
				return new Longbox();
			}
			if (tipoComponente.equals(ConstantesValidarFormulario.DOUBLEBOX)) {
				return new Doublebox();
			}
			if (tipoComponente.equals(ConstantesValidarFormulario.INTBOX)) {
				return new Intbox();
			}
			if (tipoComponente.equals(ConstantesValidarFormulario.CHECKBOX)) {
				return new Checkbox();
			}
			if (tipoComponente.equals(ConstantesValidarFormulario.TIMEBOX)) {
				return new Timebox();
			}
		}
		return null;
	}
	
	/**
	 * Permite mostrar el combobox para comparar contra componente, o el textbox
	 * si es por valor
	 * 
	 * @param comboComparaContra
	 * @param comboCompara
	 * @param textbox
	 */
	public void mostrarComparacionVisible(Combobox comboComparaContra,
			Combobox comboCompara,String tipoComponente, Div div, CondicionDTO condicion) {
		Component componente = null;
		condicion.setValorComparacion(null);
		if (comboCompara.getSelectedItem().getLabel().equals("Valor")) {
			componente = evaluarTipoComponente(tipoComponente);
			if(componente instanceof Checkbox){
				condicion.setValorComparacion("false");
			}
			vaciarDiv(div);
			componente.addEventListener(Events.ON_BLUR, new AbmConstraintRowRenderEvent(this,condicion,0));
			componente.setVisible(true);
			div.appendChild(componente);
			comboComparaContra.setVisible(false);
			comboComparaContra.setValue(null);
			comboComparaContra.setDisabled(true);
		} else {
			vaciarDiv(div);
			comboComparaContra.setVisible(true);
			comboComparaContra.setDisabled(false);
		}
	}
	
	private void vaciarDiv(Div div){
		Combobox comboComponente = (Combobox)div.getFirstChild();
		if(div.getLastChild() instanceof Combobox){
			Combobox combo = (Combobox)div.getLastChild();
			if(!combo.getName().equals("comboComparaContra")){
				div.removeChild(div.getLastChild());
			}else{
				comboComponente.setValue(null);
				comboComponente.setVisible(false);
			}
		}else{
			div.removeChild(div.getLastChild());
		}
	}
	
	public void initComboComparaContra(Combobox combo,CondicionDTO condicionDTO) {
		Div div = (Div) combo.getParent();
		Combobox comboComparaContra = (Combobox) div.getFirstChild();
		FormularioComponenteDTO formularioComponenteDTO = (FormularioComponenteDTO) comboComparaContra.getSelectedItem().getValue();
		condicionDTO.setNombreCompComparacion(formularioComponenteDTO.getNombre());
		condicionDTO.setValorComparacion(null);
	}

	public void initComboLogica(Combobox combo,CondicionDTO condicionDTO) {
		Row row = (Row) combo.getParent();
		Combobox comboCompara = (Combobox) row.getChildren().get(AbmConstraintRowRender.COMBO_COMPARACION);
		Div div = (Div) row.getChildren().get(AbmConstraintRowRender.DIV);
		Combobox comboLogica = (Combobox) row.getChildren().get(AbmConstraintRowRender.COMBO_LOGICA);
		Combobox comboComparaContra = (Combobox) div.getFirstChild();
		condicionDTO.setCondicion((Condicion) comboLogica.getSelectedItem().getValue());
		switch ((Condicion) comboLogica.getSelectedItem().getValue()) {
			case VACIO: 
			case NO_VACIO: {
				comboComparaContra.setVisible(false);
				comboCompara.setVisible(false);
				comboComparaContra.setValue(null);
				condicionDTO.setNombreCompComparacion(null);
				condicionDTO.setValorComparacion(null);
				vaciarDiv(div);
				break;
			}
			default: {
				comboCompara.setDisabled(false);
				comboCompara.setVisible(true);
				break;
			}
		}
	}

	public void initComboCompara(Combobox combo, CondicionDTO condicionDTO) {
		Row row = (Row) combo.getParent();
		Combobox comboCompara = (Combobox) row.getChildren().get(	AbmConstraintRowRender.COMBO_COMPARACION);
		Div div = (Div) row.getChildren().get(AbmConstraintRowRender.DIV);
		Combobox comboComponente = (Combobox) row.getChildren().get(AbmConstraintRowRender.COMBO_COMPONENTE);
		Combobox comboComparaContra = (Combobox) div.getFirstChild();
		FormularioComponenteDTO formComp = (FormularioComponenteDTO) comboComponente.getSelectedItem().getValue();
		String tipoComponente = formComp.getComponente().getTipoComponente();
		mostrarComparacionVisible(comboComparaContra, comboCompara,tipoComponente,div,condicionDTO);
		condicionDTO.setNombreCompComparacion(null);
	}

	public void initComboComponente(Combobox combo,CondicionDTO condicionDTO) {
		Row row = (Row) combo.getParent();
		Combobox comboCompara = (Combobox) row.getChildren().get(AbmConstraintRowRender.COMBO_COMPARACION);	
		Combobox comboComponente = (Combobox) row.getChildren().get(AbmConstraintRowRender.COMBO_COMPONENTE);
		Combobox comboLogica = (Combobox) row.getChildren().get(AbmConstraintRowRender.COMBO_LOGICA);
		Div div = (Div) row.getChildren().get(AbmConstraintRowRender.DIV);
		Combobox comboCondicional = (Combobox) row.getChildren().get(AbmConstraintRowRender.COMBO_CONDICIONAL);
		FormularioComponenteDTO componenteDTO = (FormularioComponenteDTO) comboComponente.getSelectedItem().getValue();
		if(condicionDTO.getNombreComponente() != null && !componenteDTO.getNombre().equals(condicionDTO.getNombreComponente())){
			vaciarComponentes(comboCompara,div,comboLogica,comboCondicional,condicionDTO);
		}
		Combobox comboComparaContra = (Combobox) div.getFirstChild();
		comboLogica.setDisabled(false);
		condicionDTO.setNombreComponente(componenteDTO.getNombre());
		List<FormularioComponenteDTO> listaFormularioComponenteComparar = this.generarListaPorTipoComponente(componenteDTO.getComponente().getTipoComponente());
		listaFormularioComponenteComparar.remove(componenteDTO);
		comboComparaContra.setModel(new ListModelList(listaFormularioComponenteComparar));
		evaluarCondiciones(componenteDTO.getComponente().getTipoComponente(), comboLogica);
	}
	
	public void initEliminarCondicion(Component componente, CondicionDTO condicionDTO){
		Grid grid = (Grid) componente.getFellow("grillaConstraint");
		ListModelList lista = (ListModelList) grid.getModel();
		lista.remove(condicionDTO);
		if(!lista.isEmpty()){
			((CondicionDTO) lista.get(0)).setPrimero(true);
			Rows rows = (Rows)grid.getRows();
			Row row = (Row) rows.getFirstChild();
			Combobox comboCondicion = (Combobox)row.getChildren().get(AbmConstraintRowRender.COMBO_CONDICIONAL);
			comboCondicion.setDisabled(true);
		}
	}
	
	public void initComboCondicional(Combobox combo, CondicionDTO condicionDTO){
		if(combo.getSelectedItem().getLabel().equals("Y")){
			condicionDTO.setAnd(true);
			condicionDTO.setOr(false);
		}else{
			condicionDTO.setOr(true);
			condicionDTO.setAnd(false);
		}
	}
	
	private void vaciarComponentes(Combobox comboCompara, Div div,
			Combobox comboLogica, Combobox comboCondicional,
			CondicionDTO condicionDTO) {
		comboCompara.setValue(null);
		comboCompara.setDisabled(true);
		comboCompara.setVisible(true);
		comboLogica.setValue(null);
		comboCompara.setVisible(true);
		comboLogica.setDisabled(true);
		comboCondicional.setValue(null);
		condicionDTO.setNombreCompComparacion(null);
		condicionDTO.setValorComparacion(null);
		condicionDTO.setCondicion(null);
		vaciarDiv(div);
	}
	
	private void evaluarCondiciones(String tipoComponente, Combobox comboLogica){
		if(tipoComponente.equals("TEXTBOX")){
			//igual, distinto, vacio, no vacio
			cargarComboLogica2(comboLogica);
		}
		if(tipoComponente.equals("DATEBOX")){
			//igual, distinto, vacio, no vacio, menor, mayor
			cargarComboLogica1(comboLogica);
		}
		if(tipoComponente.equals("TIMEBOX")){
			//igual, distinto, vacio, no vacio, menor, mayor
			cargarComboLogica1(comboLogica);
		}
		if(tipoComponente.equals("LONGBOX")){
			//igual, distinto, vacio, no vacio, menor, mayor
			cargarComboLogica1(comboLogica);
		}
		if(tipoComponente.equals("COMBOBOX")){
			//igual, distinto, vacio, no vacio
			cargarComboLogica2(comboLogica);
		}
		if(tipoComponente.equals("COMPLEXBANDBOX")){
			//igual, distinto, vacio, no vacio
			cargarComboLogica2(comboLogica);
		}
		if(tipoComponente.equals("DOUBLEBOX")){
			//igual, distinto, vacio, no vacio, menor, mayor
			cargarComboLogica1(comboLogica);
		}
		if(tipoComponente.equals("INTBOX")){
			//igual, distinto, vacio, no vacio, menor, mayor
			cargarComboLogica1(comboLogica);
		}
		if(tipoComponente.equals("CHECKBOX")){
			//igual, distinto
			cargarComboLogica3(comboLogica);
		}
		if(tipoComponente.equals("NROSADEBOX")){
			//igual, distinto, vacio, no vacio
			cargarComboLogica2(comboLogica);
		}
	}
		
	private void cargarComboLogica1(Combobox comboLogica){
		List<Condicion> lista = new ArrayList<Condicion>();
		lista.add(Condicion.VACIO);
		lista.add(Condicion.NO_VACIO);
		lista.add(Condicion.IGUAL);
		lista.add(Condicion.DISTINTO);
		lista.add(Condicion.MAYOR);
		lista.add(Condicion.MENOR);
		comboLogica.setModel(new ListModelList(lista));
	}
	
	private void cargarComboLogica2(Combobox comboLogica){
		List<Condicion> lista = new ArrayList<Condicion>();
		lista.add(Condicion.VACIO);
		lista.add(Condicion.NO_VACIO);
		lista.add(Condicion.IGUAL);
		lista.add(Condicion.DISTINTO);
		comboLogica.setModel(new ListModelList(lista));
	}
	
	private void cargarComboLogica3(Combobox comboLogica){
		List<Condicion> lista = new ArrayList<Condicion>();
		lista.add(Condicion.IGUAL);
		lista.add(Condicion.DISTINTO);
		comboLogica.setModel(new ListModelList(lista));
	}

}

class NoEmptyConstraint implements Constraint {

	private List<FormularioComponenteDTO> listaAux = new ArrayList<FormularioComponenteDTO>();
	
	public NoEmptyConstraint(List<FormularioComponenteDTO> listaAux){
		this.listaAux = listaAux;
	}
	@Override
	public void validate(Component comp, Object value)
			throws WrongValueException {
		if (this.listaAux.isEmpty()) {
			throw new WrongValueException(comp,
					"No posee mas componentes para establecer comparacion por componente");
		}
	}
}

class AbmConstraintRowRenderEvent implements EventListener {

	private AbmConstraintRowRender constraintRowRender;
	private CondicionDTO condicionDTO;

	public AbmConstraintRowRenderEvent(
			AbmConstraintRowRender constraintRowRender, Object data,int arg1) {
		this.setConstraintRowRender(constraintRowRender);
		this.condicionDTO = (CondicionDTO) data;
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_SELECT)) {
			Combobox combo = (Combobox) event.getTarget();
			if (combo.getName().equals("comboComponente")) {
				constraintRowRender.initComboComponente(combo, condicionDTO);
			} else if (combo.getName().equals("comboCompara")) {
				constraintRowRender.initComboCompara(combo,condicionDTO);
			} else if (combo.getName().equals("comboLogica")) {
				constraintRowRender.initComboLogica(combo, condicionDTO);
			} else if (combo.getName().equals("comboComparaContra")) {
				constraintRowRender.initComboComparaContra(combo, condicionDTO);
			} else if (combo.getName().equals("comboCondicional")){
				constraintRowRender.initComboCondicional(combo, condicionDTO);
			}
		} else if (event.getName().equals(Events.ON_CLICK)) {
			constraintRowRender.initEliminarCondicion(event.getTarget(), condicionDTO);
		} else if (event.getName().equals(Events.ON_BLUR)) {
			Component component =  event.getTarget();
			String valor = "";
			if (component instanceof InputElement){
				if(component instanceof Datebox){
					Datebox dateBox = (Datebox)component;
					Date fecha = dateBox.getValue();
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					if(fecha!=null){
						valor = df.format(fecha);
					}			
				}else{
					valor = ((InputElement) component).getRawText();
				}
				
			} else if (component instanceof Checkbox){
				valor = String.valueOf(((Checkbox) component).isChecked());
			}
			condicionDTO.setValorComparacion(valor.isEmpty()?null:valor);
			condicionDTO.setNombreCompComparacion(null);
		}
	}

	public AbmConstraintRowRender getConstraintRowRender() {
		return constraintRowRender;
	}

	public void setConstraintRowRender(
			AbmConstraintRowRender constraintRowRender) {
		this.constraintRowRender = constraintRowRender;
	}
}