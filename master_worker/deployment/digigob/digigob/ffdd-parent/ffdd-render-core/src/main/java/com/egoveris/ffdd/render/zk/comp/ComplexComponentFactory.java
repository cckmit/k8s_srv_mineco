package com.egoveris.ffdd.render.zk.comp;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.render.model.ComplexComponent;
import com.egoveris.ffdd.render.model.ComponentZkExt;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.egoveris.ffdd.render.service.IComponentFactory;
import com.egoveris.ffdd.render.zk.comp.ext.ComplexComponenLayout;
import com.egoveris.ffdd.render.zk.comp.ext.ConstrInputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.MultiConstrData;
import com.egoveris.ffdd.render.zk.constr.TextConstraint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplexComponentFactory implements IComponentFactory<ComponentZkExt> {

	@Autowired
	private IComplexComponentService compService;

	@Override
	public ConstrInputComponent create(final FormularioComponenteDTO formComp) {
		final ComplexComponent complex = compService.getComponent(formComp.getComponente().getNombreXml());
		if (complex == null) {
			throw new NullPointerException(
					"No se ha encontrado el componente complejo " + formComp.getComponente().getNombreXml());
		}
		
		ConstrInputComponent cze = new ComplexComponenLayout(formComp, complex.getZul());
		
		if (cze.getMultiConstrData() == null) {
			cze.setMultiConstrData(new MultiConstrData());
		} else if (cze.getMultiConstrData().getConstraintEL() != null && !cze.getMultiConstrData().getConstraintEL().isEmpty()) {
			agregarConstraint(cze);
		}
		
		cze.getMultiConstrData().setConstraintDTOList(formComp.getConstraintList());
		cze.getMultiConstrData().setNoEmpty(formComp.getObligatorio() == null ? false : formComp.getObligatorio());
		
		return cze;
	}
	
	private void agregarConstraint(ConstrInputComponent c) {
		String constraint = c.getMultiConstrData().getConstraintEL();
		String[] splitData = { constraint.substring(0, constraint.lastIndexOf(':')),
				constraint.substring(constraint.lastIndexOf(':') + 1) };
		c.setConstraint(new TextConstraint(splitData));
	}

}
