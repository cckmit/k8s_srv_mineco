package com.egoveris.ffdd.web.adm;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import com.egoveris.ffdd.model.model.ConstraintDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.web.render.AbmConstraintRowRender;

public class FormConstraintGenericEditComposer extends FormConstraintComposer{
	
	private static final long serialVersionUID = 3675643323275899811L;

	private ConstraintDTO constraintDto;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);		
		this.self.addEventListener(Events.ON_NOTIFY,new FormConstraintGenericoComposerListener(this) );
		this.setListaFormularioComponente((List<FormularioComponenteDTO>) Executions.getCurrent().getArg().get("listComponentesSeleccionados"));
		this.mapaConstraints = (Map<String, Object>) Executions.getCurrent().getArg().get("mapaConstraints");
		this.constraintDto = (ConstraintDTO) Executions.getCurrent().getArg().get("constraint");
		crearVentana();
	}	
	
	private void crearVentana(){
		if(super.existeNombreComponente(this.constraintDto.getNombreComponente())){
			this.nombreComponente.setValue(this.constraintDto.getNombreComponente());
		}	
		this.nombreComponente.setModel(new ListModelList(super.removerComponentes()));
		this.descripcionConstraint.setValue(this.constraintDto.getMensaje());
		this.grillaConstraint.setRowRenderer(new AbmConstraintRowRender(listaFormularioComponente));
		this.grillaConstraint.setModel(new ListModelList(this.constraintDto.getCondiciones()));
	}
	
	public Combobox getNombreComponente() {
		return nombreComponente;
	}

	public void setNombreComponente(Combobox nombreComponente) {
		this.nombreComponente = nombreComponente;
	}

	public Textbox getDescripcionConstraint() {
		return descripcionConstraint;
	}

	public void setDescripcionConstraint(Textbox descripcionConstraint) {
		this.descripcionConstraint = descripcionConstraint;
	}

	public Grid getGrillaConstraint() {
		return grillaConstraint;
	}

	public void setGrillaConstraint(Grid grillaConstraint) {
		this.grillaConstraint = grillaConstraint;
	}
	
}