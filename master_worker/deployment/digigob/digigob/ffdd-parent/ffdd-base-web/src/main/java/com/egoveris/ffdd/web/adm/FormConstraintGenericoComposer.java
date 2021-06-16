package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.ListModelList;

import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.web.render.AbmConstraintRowRender;

public class FormConstraintGenericoComposer extends FormConstraintComposer{

	private static final long serialVersionUID = 8005293404464491200L;
		
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.lstConstraint = new ArrayList<CondicionDTO>();
		this.setListaFormularioComponente((List<FormularioComponenteDTO>) Executions.getCurrent().getArg().get("listComponentesSeleccionados"));
		this.mapaConstraints = (Map<String, Object>) Executions.getCurrent().getArg().get("mapaConstraints");
		inicializarModelos();
		super.grillaConstraint.setRowRenderer(new AbmConstraintRowRender(this.listaFormularioComponente));
		this.self.addEventListener(Events.ON_NOTIFY,new FormConstraintGenericoComposerListener(this) );
	}
	
	private void inicializarModelos() {
		this.nombreComponente.setModel(new ListModelArray(removerComponentes()));
		CondicionDTO constraint = new CondicionDTO();
		super.lstConstraint.add(constraint);
		super.grillaConstraint.setModel(new ListModelList(super.lstConstraint));
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
	
}