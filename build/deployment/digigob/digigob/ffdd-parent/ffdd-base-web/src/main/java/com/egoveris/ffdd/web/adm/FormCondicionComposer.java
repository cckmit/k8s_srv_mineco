package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;


public class FormCondicionComposer extends GenericForwardComposer{


	private static final long serialVersionUID = 1522258356166261489L;
	
	protected List<CondicionDTO> lstConstraint = new ArrayList<CondicionDTO>();
	protected Grid grillaConstraint;
	protected Combobox nombreComponente;
	protected List<FormularioComponenteDTO> listaFormularioComponente;
	protected Map<String, Object> mapaConstraints;
	
	public void onConstraintADD() {
		CondicionDTO constraint = new CondicionDTO();
		ListModelList lista = (ListModelList) this.grillaConstraint.getModel();
		if(!lista.isEmpty()){
			constraint.setPrimero(false);
		}
		this.lstConstraint.add(constraint);
		lista.add(constraint);
	}
	
	public void onEliminarConstraint(){
		Window ventana = (Window) this.self;
		ventana.onClose();
	}
		
	protected boolean existeNombreComponente(String nombreConstraint){
		for(FormularioComponenteDTO formComp : this.listaFormularioComponente){
			if (formComp.getNombre().equals(nombreConstraint)){
				return true;
			}
		}
		return false;
	}
	
	protected List<FormularioComponenteDTO> removerComponentes(){
		List<FormularioComponenteDTO> listaRetorno = new ArrayList<FormularioComponenteDTO>();
		for(FormularioComponenteDTO formComp : this.listaFormularioComponente){
			if(!formComp.getComponente().getTipoComponente().equals("CHECKBOX")){
				listaRetorno.add(formComp);
			}
		}
		return listaRetorno;
		
	}
	
	protected String obtenerNombreComponente(){
		return this.nombreComponente.getValue();
	}
	
	protected List<FormularioComponenteDTO> getListaFormularioComponente() {
		return this.listaFormularioComponente;
	}

	protected void setListaFormularioComponente(
			List<FormularioComponenteDTO> listaFormularioComponente) {
		this.listaFormularioComponente = listaFormularioComponente;
	}
	
	protected Combobox getNombreComponente() {
		return this.nombreComponente;
	}

	protected void setNombreComponente(Combobox nombreComponente) {
		this.nombreComponente = nombreComponente;
	}
	
	protected Map<String, Object> getMapaConstraints() {
		return mapaConstraints;
	}

	protected void setMapaConstraints(Map<String, Object> mapaConstraints) {
		this.mapaConstraints = mapaConstraints;
	}
}
