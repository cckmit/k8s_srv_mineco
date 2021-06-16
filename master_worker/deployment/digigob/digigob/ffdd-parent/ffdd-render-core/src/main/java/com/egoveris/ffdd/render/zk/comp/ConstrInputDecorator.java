package com.egoveris.ffdd.render.zk.comp;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.render.service.IComponentFactory;
import com.egoveris.ffdd.render.zk.comp.ext.ConstrInputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.MultiConstrData;
import com.egoveris.ffdd.render.zk.constr.TextConstraint;

/**
 * Decorator de ComponentFactory
 * 
 * @author ilupi
 * 
 */
public class ConstrInputDecorator implements IComponentFactory<ConstrInputComponent> {

	IComponentFactory<ConstrInputComponent> compFact;

	public ConstrInputDecorator(IComponentFactory<ConstrInputComponent> compFact) {
		this.compFact = compFact;
	}

	public ConstrInputComponent create(FormularioComponenteDTO formComp) {
		ConstrInputComponent c = this.compFact.create(formComp);
		agregarPropiedadesDeInstancia(formComp, c);
		return c;
	}

	private void agregarPropiedadesDeInstancia(FormularioComponenteDTO formComp, ConstrInputComponent c) {
		if (c.getMultiConstrData() == null) {
			c.setMultiConstrData(new MultiConstrData());
		} else if (c.getMultiConstrData().getConstraintEL() != null && !c.getMultiConstrData().getConstraintEL().isEmpty()) {
			agregarConstraint(c);
		}
		
		c.getMultiConstrData().setConstraintDTOList(formComp.getConstraintList());
		c.getMultiConstrData().setNoEmpty(formComp.getObligatorio() == null ? false : formComp.getObligatorio());
	}

	private void agregarConstraint(ConstrInputComponent c) {
		String constraint = c.getMultiConstrData().getConstraintEL();
		String[] splitData = { constraint.substring(0, constraint.lastIndexOf(':')),
				constraint.substring(constraint.lastIndexOf(':') + 1) };
		c.setConstraint(new TextConstraint(splitData));
	}
}